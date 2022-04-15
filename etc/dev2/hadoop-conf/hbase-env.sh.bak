export HBASE_OPTS="$HBASE_OPTS -Djava.security.auth.login.config=/etc/hbase/conf/jaas.conf"
# HBASE_CLASSPASTH={{HBASE_CLASSPATH}}
# JAVA_LIBRARY_PATH={{JAVA_LIBRARY_PATH}}
export HBASE_CLASSPATH=`echo $HBASE_CLASSPATH | sed -e "s|$ZOOKEEPER_CONF:||"`
export JAVA_HOME=/hadoop/java
export HBASE_OPTS="-Xms2g -Xmx8g -XX:+HeapDumpOnOutOfMemoryError -XX:+UseConcMarkSweepGC -XX:-CMSConcurrentMTEnabled -XX:+CMSIncrementalMode -Djava.net.preferIPv4Stack=true $HBASE_OPTS"
