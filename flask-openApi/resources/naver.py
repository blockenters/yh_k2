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



class NaverSearchResource(Resource) :
    def get(self) :

        # 1. 클라이언트로부터 데이터를 받아온다.
        query = request.args['query']
        display = request.args['display']
        sort = request.args['sort']

        # 2. 네이버 API 를 호출한다.
        
        data = {'query' : query, 'display' : display , 'sort' : sort}

        headers = {'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8' ,
            'X-Naver-Client-Id' : Config.NAVER_CLIENT_ID , 
            'X-Naver-Client-Secret' : Config.NAVER_CLIENT_SECRET}

        res = requests.get(Config.NAVER_NEWS_SEARCH_URL ,
                        data , 
                        headers= headers)

        res = res.json()

        # print(res)

        # res 의 display 와 items 만 있으면 된다.

        return {'result' : 'success' , 
                'count' : res['display'] ,
                'items' : res['items']} , 200














