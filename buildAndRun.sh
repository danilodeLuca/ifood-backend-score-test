#!/bin/bash -e

## Setup MongoDb and ActiveMq
docker-compose -f env_setup/docker-compose.yml up -d

## BuildApp Image
docker build -t ifood/backend-danilo score-module-application/

## Run Image
docker run -p 8080:8080 --network=host ifood/backend-danilo