FROM spark320
USER 0
WORKDIR /opt
RUN curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
RUN chmod +x kubectl \
	&& mv kubectl /usr/local/bin/kubectl \
	&& mkdir -p ~/.kube
COPY config  ~/.kube
RUN mkdir /opt/livy
COPY livy /opt/livy
ENV LIVY_HOME /opt/livy
ENV SPARK_HOME /opt/spark
RUN mkdir -p /opt/livy/logs
RUN chmod g+w /opt/livy/logs
