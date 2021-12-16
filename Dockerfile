###
# Base image specification build arguments
ARG BASE_REGISTRY="docker.io"
ARG BASE_IMAGE="ubuntu"
ARG BASE_TAG="18.04"

###
# Build image stage
FROM ${BASE_REGISTRY}/${BASE_IMAGE}:${BASE_TAG}

RUN apt-get update

#Install java 11
RUN apt-get install openjdk-8-jdk -y
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV JRE_HOME /usr/lib/jvm/java-8-openjdk-amd64




