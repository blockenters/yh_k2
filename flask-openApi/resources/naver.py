import datetime
from http import HTTPStatus
from os import access
from flask import request
from flask_jwt_extended import create_access_token, get_jwt, get_jwt_identity, jwt_required
from flask_restful import Resource

import requests

from config import Config

class NaverPapagoResource(Resource) :
    def get(self) :

        text = request.args['text']

        # 네이버 파파고 API 호출한다.

        headers = {'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8' ,
            'X-Naver-Client-Id' : Config.NAVER_CLIENT_ID , 
            'X-Naver-Client-Secret' : Config.NAVER_CLIENT_SECRET}

        data = {'source' : 'ko' , 'target':'zh-CN' , 
                    'text': text }

        res = requests.post(Config.NAVER_PAPAGO_URL, data, headers= headers)

        print(res.json())
        print(type(res.json()))

        res = res.json()

        print(res['message']['result']['translatedText'])

        result_text = res['message']['result']['translatedText']

        return  {'result' : result_text}, 200


