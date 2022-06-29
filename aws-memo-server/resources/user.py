import datetime
from http import HTTPStatus
from os import access
from flask import request
from flask_jwt_extended import create_access_token, get_jwt, jwt_required
from flask_restful import Resource
from mysql.connector.errors import Error
from mysql_connection import get_connection
import mysql.connector

from email_validator import validate_email, EmailNotValidError

from utils import check_password, hash_password

class UserRegisterResource(Resource) :

    def post(self) :
        # 1. 클라이언트로부터 넘어온 데이터를 받는다.
        # {
        #     "email": "abc@naver.com",
        #     "password": "1234",
        #     "nickname": "홍길동"
        # }

        data = request.get_json()

        # 2. 이메일 형식 체크 

        try :
            validate_email( data['email'] )
        except EmailNotValidError as e :
            return {'error' : str(e), 'error_no' : 1} , 400

        # 3. 비밀번호 길이 체크 
        # 4자리 이상 12자리 이하가 정상!
        if len( data['password'] ) < 4 or len(data['password']) >12 :
            return {'error' : '비번 길이확인하세요', 'error_no' : 2}, 400
        
        # 4. 비밀번호 암호화
        hashed_password = hash_password(data['password']) 

        # 5. 데이터베이스에 저장한다.
        try :
            # 데이터 insert 
            # 1. DB에 연결
            connection = get_connection()

            # 2. 쿼리문 만들기
            query = '''insert into user
                    (email, password, nickname)
                    values
                    (%s, %s , %s);'''
            
            record = (data['email'], hashed_password, 
                        data['nickname'] )

            # 3. 커서를 가져온다.
            cursor = connection.cursor()

            # 4. 쿼리문을 커서를 이용해서 실행한다.
            cursor.execute(query, record)

            # 5. 커넥션을 커밋해줘야 한다 => 디비에 영구적으로 반영하라는 뜻
            connection.commit()

            # 5-1. 디비에 저장된 아이디값 가져오기.
            user_id = cursor.lastrowid

            # 6. 자원 해제
            cursor.close()
            connection.close()

        except mysql.connector.Error as e :
            print(e)
            cursor.close()
            connection.close()
            return {"error" : str(e), 'error_no' : 3}, 503

        # 5. JWT억세스 토큰을 생성해서, 클라이언트에 응답해준다.
        access_token = create_access_token(user_id)

        return {'result' : 'success', 
                'access_token' : access_token}, 200


class UserLoginResource(Resource) : 
    def post(self) :
        # 1. 클라이언트로부터 데이터 받아온다.
        # {
        #     "email": "abc@naver.com",
        #     "password": "1234"
        # }
        data = request.get_json()

        # 2. 이 이메일로, 데이터베이스에서 셀렉트 한다.
        try :
            connection = get_connection()

            query = '''select *
                    from user
                    where email = %s;'''

            record = (data['email'] , )
            
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
                result_list[i]['created_at'] = record['created_at'].isoformat()
                i = i + 1                

            cursor.close()
            connection.close()

        except mysql.connector.Error as e :
            print(e)
            cursor.close()
            connection.close()

            return {"error" : str(e)}, 503

        # 3. 이 이메일의 유저가 없으면, 클라이언트에 
        #    이 이메일은 회원이 아니라고 응답해준다.
        if len( result_list ) == 0 :
            return {'error' : '회원가입 정보가 없습니다.',
                    'error_no' : 6}, 400

        # 4. 비번이 맞는지 확인한다.
        
        # 디비에 저장되어있는 유저 정보!
        user_info = result_list[0]

        check = check_password(data['password'], user_info['password'])

        if check == False :
            return {'error' : '비번이 안맞음',
                    'error_no' : 7}, 400

        # 5. JWT 억세스 토큰 생성해서 리턴해준다.
        access_token = create_access_token( user_info['id'] )

        return {'result' : 'success' , 
                'access_token' : access_token}, 200


jwt_blacklist = set()

class UserLogoutResource(Resource) :

    @jwt_required()
    def post(self) :

        jti = get_jwt()['jti']
        print(jti)

        jwt_blacklist.add(jti)

        return {'result' : 'success'}, 200




