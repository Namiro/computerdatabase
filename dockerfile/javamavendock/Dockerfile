FROM maven:3.3-jdk-8
MAINTAINER Junior Burleon

RUN cd /opt && wget "https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-2.1.1-linux-x86_64.tar.bz2" && tar -xf phantomjs-2.1.1-linux-x86_64.tar.bz2

CMD mvn test