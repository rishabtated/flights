#!/bin/sh
# ----------------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.
# Maven Wrapper Script (mvnw) - generated for flight-booking project
# ----------------------------------------------------------------------------

set -e

MAVEN_PROJECTBASEDIR="${MAVEN_BASEDIR:-$(pwd)}"
MAVEN_WRAPPER_PROPERTIES="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.properties"

if [ -r "$MAVEN_WRAPPER_PROPERTIES" ]; then
  DISTRIBUTION_URL=$(grep "distributionUrl" "$MAVEN_WRAPPER_PROPERTIES" | cut -d= -f2 | tr -d '\r')
fi

MAVEN_USER_HOME="${MAVEN_USER_HOME:-$HOME/.m2}"
MAVEN_WRAPPER_JAR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar"

if [ -r "$MAVEN_WRAPPER_JAR" ]; then
  exec "${JAVA_HOME}/bin/java" -jar "$MAVEN_WRAPPER_JAR" "$@"
fi

# Fallback: download maven directly
MAVEN_HOME="$MAVEN_USER_HOME/wrapper/dists/apache-maven-3.9.6"
if [ ! -d "$MAVEN_HOME" ]; then
  mkdir -p "$MAVEN_HOME"
  curl -sL "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.6/apache-maven-3.9.6-bin.tar.gz" | tar xz -C "$MAVEN_HOME" --strip-components=1
fi
exec "$MAVEN_HOME/bin/mvn" "$@"
