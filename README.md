# Run in local
Build with mvn
```bash
mvn clean install
docker build -t session:1.0 .
```
Export credentials
```bash
export USERNAME=user
export PASSWORD=pass
```
Run infinity span
```bash
docker run --name infinispan$RANDOM -p 7800:7800 \
-p 11221:11221 \
-p 11222:11222 \
-p 45700:45700 \
-p 57600:57600 \
-e USER=$USERNAME \
-e PASS=$PASSWORD \
infinispan/server:latest
```
Run ear
```bash
docker run --name session$RANDOM -p 9080:9080 \
-e INFINISPAN_HOST=$(ifconfig | grep "inet " | grep -Fv 127.0.0.1 | head -1 | awk '{print $2}')  \
-e INFINISPAN_USER=$USERNAME \
-e INFINISPAN_PASSWORD=$PASSWORD \
session:1.0
```
Run redis
```bash
docker run -d \
  -h redis \
  -e REDIS_PASSWORD=redis \
  -p 6379:6379 \
  --name redis$RANDOM \
  redis:latest /bin/sh -c 'redis-server --appendonly yes --requirepass ${REDIS_PASSWORD}'
```
To get info from redis use ```redis-cli```
```bash
redis-cli -h localhost -p 6379 -a redis
 > KEYS *
 > SET KEY1 VALUE
 > GET KEY1
```

# Run in openshift
Create templates
```bash
oc create -f openshift/templates/infinispan
```
Create infinitispan-server
```bash
oc new-app --template infinispan-server
```
Create external-session app
```bash
oc new-app --template external-session
```
Call to app through endpoint in route

# References
* https://github.ibm.com/Experiential-Lab/http-session-externalization
* https://github.com/IBMAppModernization/simple-http-session-app