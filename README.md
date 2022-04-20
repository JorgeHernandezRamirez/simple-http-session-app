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

# IBM Client Developer Advocacy App Modernization Series

## Simple app to demo HTTPSession replication in WebSphere Liberty on OpenShift

This small Java EE app supports the lab [HTTP Session Replication Lab for the App Modernization Dojo](https://github.com/IBMAppModernization/app-modernization-session-replication-openshift) on OpenShift.

You'll use an Open Source JCache provider to provide the implementation of the JCache support that is included in WebSphere Liberty.

This app can be used with the following JSR 107 providers:
- [Infinispan](https://infinispan.org) - Open Source, subset of [Red Hat Data Grid](https://www.redhat.com/en/technologies/jboss-middleware/data-grid) (comes with [IBM Cloud Pak for Applications](https://www.ibm.com/cloud/cloud-pak-for-applications))
- [Hazelcast](https://github.com/hazelcast/hazelcast) - Open Source - upstream version of Hazelcast's commercial offering

### Building the app

You'll need the following to build the app from source:
- A Java 8 (or later) JDK
- [Maven 3.3 (or later)](https://maven.apache.org/download.cgi)

To build the app from source  run the following command from the top level folder of a clone of this repo :
    ```
    mvn clean package
    ```

This will create the app's *.ear* file in the **target** subfolder.

### Running the app

Refer to the [lab instructions](https://github.com/IBMAppModernization/app-modernization-session-replication-openshift) in the accompanying lab exercise to run the app on OpenShift.
