#!/bin/sh
mvn clean
mvn com.fortify.sca.plugins.maven:sca-maven-plugin:20.1.4:clean
mvn package com.fortify.sca.plugins.maven:sca-maven-plugin:20.1.4:translate
mvn com.fortify.sca.plugins.maven:sca-maven-plugin:20.1.4:scan
