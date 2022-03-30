# Impala benchmarking tool

A Java project to benchmark Impala queries to compare them to similar Athena queries

1. You might have to add a library with the Impala jars that resides on the lib/ dir

java -jar /path/to/this.jar --spring.profiles.active=local [--aws_profile=xxxx] --instances={instance1,instance2,...,instanceN}

You can use the 'local' profile that is configured to use the DEV instance in Hadoop
