FROM debian:jessie
MAINTAINER Junior Burleon

USER root
RUN useradd -d "/var/jenkins_home" -u 124 -m -s /bin/bash jenkins
RUN mkdir -p /var/log/jenkins
RUN chown -R jenkins:jenkins /var/log/jenkins

RUN groupadd -g 999 docker && usermod -a -G docker jenkins

VOLUME ["/var/log/jenkins", "/var/jenkins_home"]

USER jenkins
 
CMD ["echo", "Data container for Jenkins"]
