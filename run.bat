
SET DOCKER_IMAGE=markhobson/maven-chrome:jdk-11
SET SCRIPT_DIR=%~dp0

docker pull %DOCKER_IMAGE%
docker run --rm --platform=linux/amd64 -it -v %SCRIPT_DIR%:/usr/src -w /usr/src %DOCKER_IMAGE% /bin/bash -c "mkdir -p /root/Downloads; mvn -Dsuite=selected clean verify"
