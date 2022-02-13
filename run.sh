#!/bin/bash

DOCKER_IMAGE=markhobson/maven-chrome:jdk-11

docker pull $DOCKER_IMAGE
docker run --rm --platform=linux/amd64 -it -v "$PWD":/usr/src -w /usr/src $DOCKER_IMAGE mvn -Dsuite=selected clean verify
