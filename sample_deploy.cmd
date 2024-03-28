mvn clean package

java -jar target/ex-ms-demo-0.0.1-SNAPSHOT.jar
java -Dspring.profiles.active=local -jar target/ex-ms-demo-0.0.1-SNAPSHOT.jar

docker build --tag=ex-ms-demo:latest .
docker images
docker run -p8080:8080 --name ex-ms-demo -d ex-ms-demo:latest

docker inspect ex-ms-demo
docker stop ex-ms-demo
docker rm ex-ms-demo
docker image rm ex-ms-demo
==================
ENV
--------
Windows Terminal
--------

$Env:HOME
$Env:HOMEPATH
C:\Users\Alex
$Env:DOCKER_REMOTE
'192.168.1.149:2376'


echo $DOCKER_REMOTE
echo $HOMEPATH
echo $HOME
C:\Users\Alex


$Env:<variable-name> = "<new-value>"



------------
Windows cmd
----------

echo %DOCKER_REMOTE%
'192.168.1.149:2376'

set MOON=AAA

set MOON=

---------
Git bash
---------

$ echo $DOCKER_REMOTE
'192.168.1.149:2376'
$ echo $HOME
/c/Users/Alex

printenv - выводит список ENV
set - также выводит список, но  Shell Variables, в нем  много лишнего - необходимо использовать grep
set | grep TEST_VAR
printenv | grep TEST_VAR


установка Shell Variables
SUN=RRR
установка Environmental Variables
export TEST_VAR

unset TEST_VAR

================
docker --host 192.168.1.149:2376 ps
docker --host 192.168.1.149:2376 build --tag=ex-ms-demo:latest .
docker --host 192.168.1.149:2376 run -p8080:8080 --name ex-ms-demo ex-ms-demo:latest
docker --host 192.168.1.149:2376 stop ex-ms-demo
docker --host 192.168.1.149:2376 rm ex-ms-demo


================
ssh root@192.168.1.149
mkdir /mnt/ex-ms-demo/target
exit

не работает
scp R:\doc\sites\hiking\input\*.* root@192.168.1.149:/mnt/site/hiking/input/

scp target/ex-ms-demo-0.0.1-SNAPSHOT.jar root@192.168.1.149:/mnt/ex-ms-demo/target/ex-ms-demo-0.0.1-SNAPSHOT.jar
scp Dockerfile root@192.168.1.149:/mnt/ex-ms-demo/Dockerfile

ssh root@192.168.1.149
cd /mnt/ex-ms-demo
docker build --tag=ex-ms-demo:latest .
docker run -p8080:8080 --name ex-ms-demo ex-ms-demo:latest
=================
processors:
- add_docker_metadata:
    host: "unix:///var/run/docker.sock"


- decode_json_fields:
    fields: ["message"]
    target: "json"
    overwrite_keys: true
