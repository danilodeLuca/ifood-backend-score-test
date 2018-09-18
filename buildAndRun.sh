#!/bin/bash -e

#Remove containers created earlier
docker stop ifood/backend-danilo

docker-compose -f env_setup/docker-compose.yml stop
docker-compose -f env_setup/docker-compose.yml rm -f

docker rmi -f ifood/backend-danilo

## build projeto
mvn clean install
if [ "$?" -ne 0 ]; then
    echo "Build failed! Exiting....";
    exit 1;
fi

## Setup MongoDb and ActiveMq
docker-compose -f env_setup/docker-compose.yml up -d --force-recreate

## BuildApp Image
docker build -t ifood/backend-danilo score-module-application/

## Run Image
docker run -p 8080:8080 -d --network=host ifood/backend-danilo