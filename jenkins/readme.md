# Jenkins docker cloud

## Volumes

```
docker volume create jenkins-data
```

```
docker volume create jenkins-docker-certs
```

## Networking 

```
docker network create jenkins
```

## Docker in Docker container

```
docker container run \
--name dind \
--detach \
--restart unless-stopped \
--privileged \
--network jenkins \
--network-alias docker \
--env DOCKER_TLS_CERTDIR="/certs" \
--volume jenkins-docker-certs:/certs/client \
--volume jenkins-data:/var/jenlins_home \
docker:dind
```

## Jenkins container

```
docker container run \
--name jenkins \
--detach \
--restart unless-stopped \
--network jenkins \
--env DOCKER_HOST="tcp://docker:2376" \
--env DOCKER_CERT_PATH=/certs/client \
--env DOCKER_TLS_VERIFY=1 \
--volume jenkins-docker-certs:/certs/client:ro \
--volume jenkins-data:/var/jenkins_home \
--publish 8080:8080 \
--publish 50000:50000 \
jenkins/jenkins
```

## Certificados para la configuración del secreto Jenkins Docker Cloud

```
docker exec dind cat /certs/client/key.pem
docker exec dind cat /certs/client/cert.pem
docker exec dind cat /certs/server/ca.pem
```
