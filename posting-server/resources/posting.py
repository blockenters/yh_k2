from datetime import datetime
from http import HTTPStatus
from flask import request
from flask_jwt_extended import create_access_token, get_jwt, get_jwt_identity, jwt_required
from flask_restful import Resource
from mysql.connector.errors import Error
from mysql_connection import get_connection
import mysql.connector

import boto3
from config import Config

class PostingResource(Resource) :
    @jwt_required()
    def post(self) :

        # 1. 클라이언트로부터 데이터 받아온다.
        # photo(file), content(text)

        user_id = get_jwt_identity()

        if 'photo' not in request.files:
            return {'error' : '파일을 업로드하세요'}, 400

        file = request.files['photo']
        content = request.form['content']

        # 2. S3 에 파일 업로드
        # 파일명을 우리가 변경해 준다.
        # 파일명은, 유니크하게 만들어야 한다.
        current_time = datetime.now()
        new_file_name = current_time.isoformat().replace(':', '_') + '.jpg'

        # 유저가 올린 파일의 이름을, 내가 만든 파일명으로 변경
        file.filename = new_file_name

        # S3 에 업로드 하면 된다.
        # AWS 의 라이브러리를 사용해야 한다.
        # 이 파이썬 라이브러리가 boto3 라이브러리다!
        # boto3 라이브러리 설치
        # pip install boto3 

        s3 = boto3.client('s3', 
                    aws_access_key_id = Config.ACCESS_KEY,
                    aws_secret_access_key = Config.SECRET_ACCESS)

        try :
            s3.upload_fileobj(file,
                                Config.S3_BUCKET,
                                file.filename,
                                ExtraArgs = {'ACL':'public-read', 'ContentType':file.content_type} )                 

        except Exception as e :
            return {'error' : str(e)}, 500

        # 3. DB에 저장
        try :
            # 데이터 insert 
            # 1. DB에 연결
            connection = get_connection()

            # 2. 쿼리문 만들기
            query = '''insert into posting
                    (content, imgUrl, userId)
                    values
                    (%s , %s, %s );'''
            
            record = (content, new_file_name, user_id)

            # 3. 커서를 가져온다.
            cursor = connection.cursor()

            # 4. 쿼리문을 커서를 이용해서 실행한다.
            cursor.execute(query, record)

            # 5. 커넥션을 커밋해줘야 한다 => 디비에 영구적으로 반영하라는 뜻
            connection.commit()

            # 이 포스팅의 아이디값을 가져온다.
            posting_id = cursor.lastrowid

            # 6. 자원 해제
            cursor.close()
            connection.close()

        except mysql.connector.Error as e :
            print(e)
            cursor.close()
            connection.close()
            return {"error" : str(e)}, 503


        # 3. 오브젝트 디텍션을 수행해서, 레이블의 Name을 가져온다.
        client = boto3.client('rekognition', 
                                'ap-northeast-2',
                                aws_access_key_id=Config.ACCESS_KEY,
                                aws_secret_access_key = Config.SECRET_ACCESS)
        response = client.detect_labels(Image={'S3Object' : {
                                        'Bucket':Config.S3_BUCKET,        
                                        'Name':new_file_name }} , 
                                        MaxLabels=5 )
        
        # 4. 레이블의 Name을 가지고, 태그를 만든다!!!!!!!

        # 4-1. label['Name'] 의 문자열을 tag_name 테이블에서 찾는다.
        #      테이블에 이 태그가 있으면, id 를 가져온다.
        #      이 태그 id와 위의 postingId 를 가지고, 
        #      tag 테이블에 저장한다.

        # 4-2. 만약 tag_name 테이블에 이 태그가 없으면, 
        #      tag_name  테이블에, 이 태그이름을 저장하고, 
        #      저장된 id 값과 위의 postingId 를 가지고,
        #      tag  테이블에 저장한다. 

        for label in response['Labels'] :
            # label['Name'] 이 값을 우리는 태그 이름으로 사용할것.
            try :
                connection = get_connection()

                query = '''select *
                        from tag_name
                        where name = %s;'''
                
                record = (label['Name'],)

                # select 문은, dictionary = True 를 해준다.
                cursor = connection.cursor(dictionary = True)

                cursor.execute(query, record)

                # select 문은, 아래 함수를 이용해서, 데이터를 가져온다.
                result_list = cursor.fetchall()

                if len(result_list) == 0 :
                    # 태그이름을 insert 해준다.
                    query = '''insert into tag_name
                    (name)
                    values
                    (%s );'''
            
                    record = (label['Name'],  )

                    # 3. 커서를 가져온다.
                    cursor = connection.cursor()

                    # 4. 쿼리문을 커서를 이용해서 실행한다.
                    cursor.execute(query, record)

                    # 5. 커넥션을 커밋해줘야 한다 => 디비에 영구적으로 반영하라는 뜻
                    connection.commit()

                    # 태그아이디를 가져온다.
                    tag_name_id = cursor.lastrowid
                    
                else :
                    tag_name_id = result_list[0]['id']

                
                # posting_id 와 tag_name_id 가 준비되었으니
                # tag 테이블에 insert 한다.

                query = '''insert into tag
                    (tagId, postingId)
                    values
                    (%s, %s );'''
            
                record = (tag_name_id, posting_id )

                # 3. 커서를 가져온다.
                cursor = connection.cursor()

                # 4. 쿼리문을 커서를 이용해서 실행한다.
                cursor.execute(query, record)

                # 5. 커넥션을 커밋해줘야 한다 => 디비에 영구적으로 반영하라는 뜻
                connection.commit()


                cursor.close()
                connection.close()

            except mysql.connector.Error as e :
                print(e)
                cursor.close()
                connection.close()
                return {"error" : str(e), 'error_no' : 20}, 503


        return {'result' : 'success'}

    @jwt_required()
    def get(self) :

        # 1. 클라이언트로부터 데이터를 받아온다.
        user_id = get_jwt_identity()
        offset = request.args.get('offset')
        limit = request.args.get('limit')

        if offset is None or limit is None :
            return {'error' : '쿼리스트링 셋팅해 주세요',
                    'error_no' : 123} , 400

        # 2. 디비로부터 리스트를 가져온다.
        try :
            connection = get_connection()

            query = '''select p.* , u.email, u.name , count(l.postingId) as likeCnt
                    from posting p
                    join user u
                    on p.userId = u.id
                    left join likes l
                    on p.id = l.postingId
                    where p.userId = %s
                    group by p.id 
                    limit '''+offset+''' , '''+limit+''';'''
            
            record = (user_id,)

            # select 문은, dictionary = True 를 해준다.
            cursor = connection.cursor(dictionary = True)

            cursor.execute(query, record)

            # select 문은, 아래 함수를 이용해서, 데이터를 가져온다.
            result_list = cursor.fetchall()

            print(result_list)

            # 중요! 디비에서 가져온 timestamp 는 
            # 파이썬의 datetime 으로 자동 변경된다.
            # 문제는! 이데이터를 json 으로 바로 보낼수 없으므로,
            # 문자열로 바꿔서 다시 저장해서 보낸다.
            i = 0
            for record in result_list :
                result_list[i]['createdAt'] = record['createdAt'].isoformat()
                result_list[i]['updatedAt'] = record['updatedAt'].isoformat()
                i = i + 1                

            cursor.close()
            connection.close()

        except mysql.connector.Error as e :
            print(e)
            cursor.close()
            connection.close()

            return {"error" : str(e), 'error_no' : 20}, 503

        return {'result' : 'success', 
                'count' : len(result_list),
                'items' : result_list}, 200


class PostingInfoResource(Resource) :
    @jwt_required()
    def put(self, posting_id) :       

        # 1. 클라이언트로부터 데이터 받아온다.
        # photo(file), content(text)

        user_id = get_jwt_identity()

        if 'photo' not in request.files:
            # 쿼리 부분을 만든다.
            
            content = request.form['content']

            # S3에 파일 업로드가 필요 없으므로, 디비에 저장한다.
            try :
                # 데이터 업데이트 
                # 1. DB에 연결
                connection = get_connection()

                # 2. 쿼리문 만들기
                query = '''update posting
                    set content = %s 
                    where id = %s and userId = %s;'''
                
                record = (content, posting_id, user_id)

                # 3. 커서를 가져온다.
                cursor = connection.cursor()

                # 4. 쿼리문을 커서를 이용해서 실행한다.
                cursor.execute(query, record)

                # 5. 커넥션을 커밋해줘야 한다 => 디비에 영구적으로 반영하라는 뜻
                connection.commit()

                # 6. 자원 해제
                cursor.close()
                connection.close()

            except mysql.connector.Error as e :
                print(e)
                cursor.close()
                connection.close()
                return {'error' : str(e)}, 503


        else :
            # 쿼리 부분을 만든다.
            file = request.files['photo']
            content = request.form['content']

            # 파일이 있으니까, 파일명을 새로 만들어서
            # S3 에 업로드한다.

            # 2. S3 에 파일 업로드
            # 파일명을 우리가 변경해 준다.
            # 파일명은, 유니크하게 만들어야 한다.
            current_time = datetime.now()
            new_file_name = current_time.isoformat().replace(':', '_') + '.jpg'

            # 유저가 올린 파일의 이름을, 내가 만든 파일명으로 변경
            file.filename = new_file_name

            # S3 에 업로드 하면 된다.
            # AWS 의 라이브러리를 사용해야 한다.
            # 이 파이썬 라이브러리가 boto3 라이브러리다!
            # boto3 라이브러리 설치
            # pip install boto3 

            s3 = boto3.client('s3', 
                        aws_access_key_id = Config.ACCESS_KEY,
                        aws_secret_access_key = Config.SECRET_ACCESS)

            try :
                s3.upload_fileobj(file,
                                    Config.S3_BUCKET,
                                    file.filename,
                                    ExtraArgs = {'ACL':'public-read', 'ContentType':file.content_type} )                 

            except Exception as e :
                return {'error' : str(e)}, 500

            # 데이터 베이스에 업데이트 해준다.

            try :
                # 데이터 업데이트 
                # 1. DB에 연결
                connection = get_connection()

                # 2. 쿼리문 만들기
                query = '''update posting
                    set content = %s , imgUrl = %s 
                    where id = %s and userId = %s;'''
                
                record = (content, new_file_name,
                         posting_id, user_id)

                # 3. 커서를 가져온다.
                cursor = connection.cursor()

                # 4. 쿼리문을 커서를 이용해서 실행한다.
                cursor.execute(query, record)

                # 5. 커넥션을 커밋해줘야 한다 => 디비에 영구적으로 반영하라는 뜻
                connection.commit()

                # 6. 자원 해제
                cursor.close()
                connection.close()

            except mysql.connector.Error as e :
                print(e)
                cursor.close()
                connection.close()
                return {'error' : str(e)}, 503


            # aws rekognition 이용해서 labels 정보 가져오기
            # 3. 오브젝트 디텍션을 수행해서, 레이블의 Name을 가져온다.
            client = boto3.client('rekognition', 
                                    'ap-northeast-2',
                                    aws_access_key_id=Config.ACCESS_KEY,
                                    aws_secret_access_key = Config.SECRET_ACCESS)
            response = client.detect_labels(Image={'S3Object' : {
                                            'Bucket':Config.S3_BUCKET,        
                                            'Name':new_file_name }} , 
                                            MaxLabels=5 )

            # 기존 태그를 삭제
            # 바뀐 태그 새로 저장
            try :
                connection = get_connection()

                query = '''delete from tag
                        where postingId = %s ;'''
                record = (posting_id , )
                cursor = connection.cursor()
                cursor.execute(query, record)


                for label in response['Labels'] :
                # label['Name'] 이 값을 우리는 태그 이름으로 사용할것.
                    

                    query = '''select *
                            from tag_name
                            where name = %s;'''
                    
                    record = (label['Name'],)

                    # select 문은, dictionary = True 를 해준다.
                    cursor = connection.cursor(dictionary = True)

                    cursor.execute(query, record)

                    # select 문은, 아래 함수를 이용해서, 데이터를 가져온다.
                    result_list = cursor.fetchall()

                    if len(result_list) == 0 :
                        # 태그이름을 insert 해준다.
                        query = '''insert into tag_name
                        (name)
                        values
                        (%s );'''
                
                        record = (label['Name'],  )

                        # 3. 커서를 가져온다.
                        cursor = connection.cursor()

                        # 4. 쿼리문을 커서를 이용해서 실행한다.
                        cursor.execute(query, record)

                        # 5. 커넥션을 커밋해줘야 한다 => 디비에 영구적으로 반영하라는 뜻
                        connection.commit()

                        # 태그아이디를 가져온다.
                        tag_name_id = cursor.lastrowid
                        
                    else :
                        tag_name_id = result_list[0]['id']

                    
                    # posting_id 와 tag_name_id 가 준비되었으니
                    # tag 테이블에 insert 한다.

                    query = '''insert into tag
                        (tagId, postingId)
                        values
                        (%s, %s );'''
                
                    record = (tag_name_id, posting_id )

                    # 3. 커서를 가져온다.
                    cursor = connection.cursor()

                    # 4. 쿼리문을 커서를 이용해서 실행한다.
                    cursor.execute(query, record)

                # 5. 커넥션을 커밋해줘야 한다 => 디비에 영구적으로 반영하라는 뜻
                connection.commit()


                cursor.close()
                connection.close()

            except mysql.connector.Error as e :
                print(e)
                cursor.close()
                connection.close()
                return {"error" : str(e), 'error_no' : 20}, 503



        return {'result' : 'success'}, 200


    @jwt_required()
    def delete(self, posting_id) :

        # 1. 클라이언트로부터 데이터를 받아온다.
        user_id = get_jwt_identity()

        # 2. 디비에서 삭제!!
        #    내가 작성한 포스팅만 삭제 가능해야 한다.
        try :
            # 데이터 삭제
            # 1. DB에 연결
            connection = get_connection()

            # 2. 쿼리문 만들기
            query = '''delete from posting
                    where id = %s and userId = %s;'''
            
            record = ( posting_id, user_id )

            # 3. 커서를 가져온다.
            cursor = connection.cursor()

            # 4. 쿼리문을 커서를 이용해서 실행한다.
            cursor.execute(query, record)

            # 2. 쿼리문 만들기
            query = '''delete from likes
                    where postingId = %s;'''
            
            record = ( posting_id, )

            # 3. 커서를 가져온다.
            cursor = connection.cursor()

            # 4. 쿼리문을 커서를 이용해서 실행한다.
            cursor.execute(query, record)

            # 2. 쿼리문 만들기
            query = '''delete from tag
                    where postingId = %s;'''
            
            record = ( posting_id, )

            # 3. 커서를 가져온다.
            cursor = connection.cursor()

            # 4. 쿼리문을 커서를 이용해서 실행한다.
            cursor.execute(query, record)

            # 5. 커넥션을 커밋해줘야 한다 => 디비에 영구적으로 반영하라는 뜻
            connection.commit()

            # 6. 자원 해제
            cursor.close()
            connection.close()

        except mysql.connector.Error as e :
            print(e)
            cursor.close()
            connection.close()
            return {'error' : str(e)}, 503

        return {'result' : 'success'}

    @jwt_required()
    def get(self, posting_id):
        return

