# IMAGE: Get the base image for Liberty
FROM openliberty/open-liberty:latest

# Add Infinispan config
COPY wlp/config/jvm.options /opt/ol/wlp/usr/servers/defaultServer/jvm.options

# Add Infinispan jars
RUN mkdir -p /opt/ol/wlp/usr/shared/resources/infinispan
COPY wlp/usr/shared/resources/infinispan/*.jar /opt/ol/wlp/usr/shared/resources/infinispan/
COPY wlp/usr/shared/resources/redisson/* /opt/ol/wlp/usr/shared/resources/redisson/
USER root
RUN chown 1001:0 /opt/ol/wlp/usr/shared/resources/infinispan/*.jar
USER 1001

# Add server.xml
COPY wlp/config/server.xml /config/server.xml
USER root
RUN chown 1001:0 /config/server.xml
USER 1001

ADD external-session-ear/target/ear-1.0.ear /opt/ol/wlp/usr/servers/defaultServer/dropins
