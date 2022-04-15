FROM spark-py3.2.0
USER 0
WORKDIR /
RUN mkdir -p /opt/pgsql-9.3/bin
RUN pip install pyathena
RUN pip install pandas
COPY postgresql-42.2.15.jar ${SPARK_HOME}/jars
COPY com.amazonaws_aws-java-sdk-bundle-1.11.375.jar ${SPARK_HOME}/jars
COPY hadoop-aws-3.2.2.jar ${SPARK_HOME}/jars
COPY AthenaJDBC42_2.0.8.jar ${SPARK_HOME}/jars
COPY aws-athena.py ${SPARK_HOME}/work-dir
WORKDIR ${SPARK_HOME}/work-dir