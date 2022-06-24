from flask import Flask
from flask_jwt_extended import JWTManager
from flask_restful import Api
from config import Config
from resources.image import FileUploadResource
from resources.posting import PostingResource
from resources.rekognition import ObjectDetectionResource

app = Flask(__name__)

# 환경변수 셋팅
app.config.from_object(Config)

# JWT 토큰 라이브러리 만들기
jwt = JWTManager(app)

api = Api(app)

# 경로와 리소스(API 코드)를 연결한다.
api.add_resource(FileUploadResource, '/upload')
api.add_resource(PostingResource, '/posting')
api.add_resource(ObjectDetectionResource, '/object_detection')

if __name__=="__main__" :
    app.run()