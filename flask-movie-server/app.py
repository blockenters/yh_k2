from flask import Flask
from flask_jwt_extended import JWTManager
from flask_restful import Api
from config import Config
from resources.movie import MovieListResource, MovieResource, MovieSearchResource
from resources.rating import MovieRatingResource, RatingListResource
from resources.recommend import MovieRecomRealTimeResource, MovieRecomResource

from resources.user import UserLoginResource, UserLogoutResource, UserRegisterResource, jwt_blacklist

app = Flask(__name__)

# 환경변수 셋팅
app.config.from_object(Config)

# JWT 토큰 라이브러리 만들기
jwt = JWTManager(app)

# 로그아웃 된 토큰이 들어있는 set을, jwt 에 알려준다.
@jwt.token_in_blocklist_loader
def check_if_token_is_revoked(jwt_header, jwt_payload):
    jti = jwt_payload['jti']
    return jti in jwt_blacklist

api = Api(app)

# 경로와 리소스(API 코드)를 연결한다.
api.add_resource(UserRegisterResource, '/users/register')
api.add_resource(UserLoginResource, '/users/login')
api.add_resource(UserLogoutResource, '/users/logout')
api.add_resource(MovieListResource, '/movie')
api.add_resource(MovieResource, '/movie/<int:movie_id>')

api.add_resource(RatingListResource, '/rating')
api.add_resource(MovieRatingResource, '/movie/<int:movie_id>/rating')
api.add_resource(MovieSearchResource, '/movie/search')

api.add_resource(MovieRecomRealTimeResource, '/movie/recommend')

if __name__=="__main__" :
    app.run()