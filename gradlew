#!/bin/sh
#
# Gradle start up script for UN*X
#

##############################################################################
##  Gradle start script for POSIX compatible shells (e.g. bash, sh)
##############################################################################

APP_NAME="Gradle"
APP_BASE_NAME=$(basename "$0")

# Resolve links: $0 may be a symlink
PRG="$0"
while [ -h "$PRG" ]; do
  ls=$(ls -ld "$PRG")
  link=$(expr "$ls" : '.*-> \(.*\)$')
  if expr "$link" : '/.*' >/dev/null; then
    PRG="$link"
  else
    PRG=$(dirname "$PRG")/"$link"
  fi
done

cd "$(dirname "$PRG")/" >/dev/null
APP_HOME=$(pwd -P)

DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'
JAVA_OPTS=${JAVA_OPTS:-}

# Add default JVM options and user-specified
exec java $DEFAULT_JVM_OPTS $JAVA_OPTS -jar "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" "$@"
