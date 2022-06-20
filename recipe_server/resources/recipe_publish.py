from http import HTTPStatus
from flask import request
from flask_restful import Resource
from mysql.connector.errors import Error
from mysql_connection import get_connection
import mysql.connector

class RecipePublishResource(Resource) :
    # 레시피를 공개한다
    def put(self, recipe_id):

        # 해당 레시피아이디를 가지고
        # 데이터베이스에서 publish 컬럼을 
        # 1로 바꿔준다.
        # 디비 업데이트 실행코드
        try :
            # 데이터 업데이트 
            # 1. DB에 연결
            connection = get_connection()

            # 2. 쿼리문 만들기
            query = '''update recipe
                        set is_publish = 1
                        where id = %s;'''
            
            record = (recipe_id , )

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

        return {'result' : 'success'} , 200

    # 레시피를 임시저장한다.
    def delete(self, recipe_id) :
        
        # is_publish 컬럼을 0으로 변경
        # 디비 업데이트 실행코드
        try :
            # 데이터 업데이트 
            # 1. DB에 연결
            connection = get_connection()

            # 2. 쿼리문 만들기
            query = '''update recipe
                        set is_publish = 0
                        where id = %s;'''
            
            record = (recipe_id , )

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

        return {'result' : 'success'} , 200

    



