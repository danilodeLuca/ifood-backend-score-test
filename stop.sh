#!/bin/bash -e

#Remove containers created earlier
docker stop -f ifood/backend-danilo

docker-compose -f env_setup/docker-compose.yml stop
docker-compose -f env_setup/docker-compose.yml rm -f

docker rmi -f ifood/backend-danilo