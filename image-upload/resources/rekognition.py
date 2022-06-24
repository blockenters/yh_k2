from datetime import datetime
from http import HTTPStatus
from os import access
from flask import request
from flask_jwt_extended import create_access_token, get_jwt, get_jwt_identity, jwt_required
from flask_restful import Resource
from mysql.connector.errors import Error

import boto3

from config import Config

class ObjectDetectionResource(Resource) :

    def get(self) :

        # 1. 클라이언트로부터 데이터를 받아온다.

        filename = request.args['filename']

        # 2. 위의 파일은, S3에 저장되어 있어야 한다.
        # rekognition 을 이용해서, object detection 한다.

        client = boto3.client('rekognition', 
                                'ap-northeast-2',
                                aws_access_key_id=Config.ACCESS_KEY,
                                aws_secret_access_key = Config.SECRET_ACCESS)
        response = client.detect_labels(Image={'S3Object' : {
                                        'Bucket':Config.S3_BUCKET,        
                                        'Name':filename }} , 
                                        MaxLabels=10 )

        for label in response['Labels']:
            print ("Label: " + label['Name'])
            print ("Confidence: " + str(label['Confidence']))
            print ("Instances:")
            for instance in label['Instances']:
                print ("  Bounding box")
                print ("    Top: " + str(instance['BoundingBox']['Top']))
                print ("    Left: " + str(instance['BoundingBox']['Left']))
                print ("    Width: " +  str(instance['BoundingBox']['Width']))
                print ("    Height: " +  str(instance['BoundingBox']['Height']))
                print ("  Confidence: " + str(instance['Confidence']))
                print()

            print ("Parents:")
            for parent in label['Parents']:
                print ("   " + parent['Name'])
            print ("----------")
            print ()

        return {'result' : 'success' , 
                'Labels' : response['Labels']}, 200







