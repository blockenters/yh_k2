# 환경설정
class Config :
    # 암호화 키
    JWT_SECRET_KEY = "yhacademy1029##heelo"

    # False : 한번 발급된 토큰은 계속 사용 가능, True : 그 반대
    JWT_ACCESS_TOKEN_EXPIRES = False
    # 예외처리를 JWT로 처리
    PROPAGATE_EXCEPTIONS = True