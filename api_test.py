from flask import Flask, jsonify, request
from http import HTTPStatus

app = Flask(__name__)

@app.route('/add_two_nums', methods = ['POST'])
def add():
    # 클라이언트로부터 두 수를 받는다.
    data = request.get_json()
    ret = data['x'] + data['y']
    result = {"result" : ret}

    return jsonify(result)

if __name__ == '__main__' :
    app.run(port=5001)
