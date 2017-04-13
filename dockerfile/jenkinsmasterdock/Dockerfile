FROM jenkins:2.46.1
MAINTAINER Junior Burleon

USER root
RUN usermod -u 124 jenkins
RUN mkdir /var/log/jenkins
RUN mkdir /var/cache/jenkins
RUN mkdir /war
RUN chown -R jenkins:jenkins /var/log/jenkins
RUN chown -R jenkins:jenkins /var/cache/jenkins
RUN chown -R jenkins:jenkins /war
RUN groupadd -g 999 docker && usermod -a -G docker jenkins
RUN apt-get update \
     && apt-get install -y sudo \
     && rm -rf /var/lib/apt/lists/*
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers
USER jenkins

#VOLUME ["/var/run/docker.sock"]
#VOLUME ["/usr/bin/docker"]

ENV JAVA_OPTS="-Xmx1024m"
ENV JENKINS_OPTS="--logfile=/var/log/jenkins/jenkins.log --webroot=/var/cache/jenkins/war""

