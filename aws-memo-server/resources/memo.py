import datetime
from http import HTTPStatus
from os import access
from flask import request
from flask_jwt_extended import create_access_token, get_jwt, get_jwt_identity, jwt_required
from flask_restful import Resource
from mysql.connector.errors import Error
from mysql_connection import get_connection
import mysql.connector

class MemoListResource(Resource) :
    @jwt_required()
    def post(self) :

        # 1. 클라이언트로부터 데이터 받아온다. 
        # {
        #     "title": "커피",
        #     "date": "2022-07-01 11:00",
        #     "content": "커피마시면서 개발"
        # }

        data = request.get_json()
        user_id = get_jwt_identity()

        # 2. 메모를 데이터베이스에 저장
        try :
            # 데이터 insert 
            # 1. DB에 연결
            connection = get_connection()

            # 2. 쿼리문 만들기
            query = '''insert into memo
                    (title, date, content, user_id)
                    values
                    (%s , %s , %s, %s);'''
            
            record = (data['title'], data['date'], 
                    data['content'], user_id  )

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
            return {"error" : str(e)}, 503

        return {'result' : 'success'}, 200

    @jwt_required()
    def get(self) :
        # 1. 클라이언트로부터 데이터를 받아온다.
        # request.args 는 딕셔너리다.
        # offset = request.args['offset']
        # offset = request.args.get('offset')

        offset = request.args['offset']
        limit = request.args['limit']

        user_id = get_jwt_identity()

        # 2. 디비로부터 내 메모를 가져온다.
        try :
            connection = get_connection()

            query = '''select *
                    from memo
                    where user_id = %s
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
                result_list[i]['date'] = record['date'].isoformat()
                result_list[i]['created_at'] = record['created_at'].isoformat()
                result_list[i]['updated_at'] = record['updated_at'].isoformat()
                i = i + 1                

            cursor.close()
            connection.close()

        except mysql.connector.Error as e :
            print(e)
            cursor.close()
            connection.close()

            return {"error" : str(e), 'error_no' : 20}, 503
        
        return {'result' : 'success' ,
                'count' : len(result_list) ,
                'items' : result_list }, 200



class MemoInfoResource(Resource) :

    @jwt_required()
    def put(self, memo_id):
        # 1. 클라이언트로부터 데이터를 받아온다.
        # {
        #     "title": "점심먹자",
        #     "date": "2022-07-10 14:00",
        #     "content": "짜장면"
        # }
        data = request.get_json()
        user_id = get_jwt_identity()

        # 2. 디비 업데이트
        try :
            # 데이터 업데이트 
            # 1. DB에 연결
            connection = get_connection()

            # 2. 쿼리문 만들기
            query = '''update memo
                        set title = %s, 
                        date = %s,
                        content = %s
                        where id = %s and user_id = %s;'''
            
            record = (data['title'] , data['date'],
                        data['content'] ,
                        memo_id, user_id )

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


        return {'result' : 'success'}, 200

    @jwt_required()
    def delete(self, memo_id) :
        # 1. 클라이언트로부터 데이터를 받아온다.

        user_id = get_jwt_identity()

        # 2. 디비로부터 메모를 삭제한다.

        try :
            # 데이터 삭제
            # 1. DB에 연결
            connection = get_connection()

            # 2. 쿼리문 만들기
            query = '''delete from memo
                        where id = %s and user_id = %s;'''
            
            record = ( memo_id, user_id )

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


        return {'result' : 'success'}, 200


