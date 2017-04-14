# Computer - Database
======
[![Build Status](http://jenkinsmasterdock-1.0c2ef173.cont.dockerapp.io:32771/buildStatus/icon?job=ComputerDatabase)](http://jenkinsmasterdock-1.0c2ef173.cont.dockerapp.io:32771/job/ComputerDatabase/)
This project is just a simple web site to manage computers and companies.
Training project to learn and practice :
- Java JSP
- Servlet
- Maven
- JUnit
- Mockito
- Selenium
- Spring
- Hibernate
- JPA
- Hikari 
- Jackson
- Spring MVC
- Spring Security
- Gatling

Based on https://github.com/excilys/training-java

# Requirements
It's just a Java (1.8) project that use Maven (3).
To develop you also need a Tomcat server to deploy the Web site on your computer.

# Installation
You need to clone the project and import it in your preferred IDE.

# Continuous Integration
The CI is online with  Docker and Jenkins on Amazone Web Service and Docker Cloud.

[![N|Solid](https://camo.githubusercontent.com/7d379ba1092b02a9f418e6f7da08816df2d54a15/687474703a2f2f7333322e706f7374696d672e6f72672f69696f306c733636742f436f6e74696e756f75735f64656c69766572792e706e67)]

## Pipeline Configuration (Jenkins) 
Here is the configuration for the main Jenkins job. It's currently the full real configuration but it can change without any alert here.
```groovy
node {
    def mvnHome
    stage('Preparation') { 
        step([$class: 'WsCleanup'])
        git 'https://github.com/Namiro/computerdatabase.git'
        mvnHome = tool 'Maven3'
    }
    stage('Docker Preparation') { 
        //sh "docker network create --subnet 172.18.0.0/16 --gateway 172.18.0.1 --driver bridge TestNetwork"
        sh "docker stop mysqldock || true"
        sh "docker rm mysqldock || true"
        sh "docker run -d --name=mysqldock --net=TestNetwork --ip 172.18.0.2 jburleon/mysqldock"
    }
    stage('Checkstyle') {
        step([$class: 'hudson.plugins.checkstyle.CheckStylePublisher', checkstyle: 'checkstyle.xml'])
    }
    withDockerContainer(args: '-d --name=javamavendock --net=TestNetwork --ip 172.18.0.4 -v WarTransfer:/war -u 0', image: 'jburleon/javamavendock') {
        stage('Build') {
            sh "'${mvnHome}/bin/mvn' compile"
        }
        stage('Test') {
            sh "'${mvnHome}/bin/mvn' -Dmaven.test-compile.skip=true test"
        }
    }
    stage('Test rapport') {
        junit '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
    }
    withDockerContainer(args: '-d --name=javamavendock --net=TestNetwork --ip 172.18.0.4 -v /war:/war -u 0', image: 'jburleon/javamavendock') {
        stage('Package') {
            sh "'${mvnHome}/bin/mvn' -Dmaven.test.skip=true package"
        }
        stage('Transfer WAR') {
            sh "cp /var/jenkins_home/workspace/ComputerDatabase/target/*.war /war"
        }
    }
    stage('WAR Deployement') {
        sh "sudo cp /war/* /prod/"
    }
    stage('Docker finalization') {
        sh "docker stop mysqldock"
        sh "docker rm mysqldock"
    }
}
```

## Docker
Here you will find the the useful command to deploy docker and jenkins for the CI.
These comands are just here to remember how to do the actions and keep some information as the IP and subnet used.
### The containers
#### Test database (mysqldock)
This image represent the database that is used only for the test phase.
```sh
docker build -t mysqldock ./dockerfile/mysqldock
docker run -d --name=mysqldock --net=TestNetwork --ip 172.18.0.2 mysqldock
docker stop mysqldock; docker rm mysqldock;
docker tag mysqldock $DOCKER_ID_USER/mysqldock
```

#### Production database (prodmysqldock)
This image represent the database that is used only in production.
```sh
docker build -t prodmysqldock ./dockerfile/prodmysqldock
docker run -d --name=prodmysqldock --net=ProdNetwork --ip 172.20.0.2 prodmysqldock
docker stop prodmysqldock; docker rm prodmysqldock;
docker tag prodmysqldock $DOCKER_ID_USER/prodmysqldock
```

#### Maven & Java (javamavendock)
This image represent the environement for the build and test phase.
```sh
docker build -t javamavendock ./dockerfile/javamavendock
docker run -d --name=javamavendock --net=TestNetwork --ip 172.18.0.4 -v WarTransfer:/war javamavendock
docker stop javamavendock; docker rm javamavendock;
docker tag javamavendock $DOCKER_ID_USER/javamavendock
```

#### Production Tomacat (tomcatdock)
This image represent the tomcat in production.
```sh
docker build -t tomcatdock ./dockerfile/tomcatdock
docker run -d --name=tomcatdock --net=ProdNetwork --ip 172.20.0.4 -v WarTransferProd:/usr/local/tomcat/webapps tomcatdock
docker stop tomcatdock; docker rm tomcatdock;
docker tag tomcatdock $DOCKER_ID_USER/tomcatdock
```

### Jenkins
Jenkins is also in a docker. These images are for it. 
#### Data Jenkins (jenkinsdatadock)
This image allow to save the data relative to the Master Jenkins. This image allow only to save the data as jobs, plugins and others.
This image must be run only one time and that's all. If you remove the docker run with this image, you will lost all data from Master Jenkins.
```sh
docker build -t jenkinsdatadock ./dockerfile/jenkinsdatadock
docker run --name=jenkinsdatadock jenkinsdatadock
docker tag jenkinsdatadock $DOCKER_ID_USER/jenkinsdatadock
```

#### Master Jenkins (jenkinsmasterdock)
Master Jenkins, is just the simple Jenkins, but its data are stored in Data Jenkins. It allows to remove this one if necessary without lost whole data.
```sh
docker build -t jenkinsmasterdock ./dockerfile/jenkinsmasterdock
docker run -p 8082:8080 -p 50000:50000 --name=jenkinsmasterdock --volumes-from=jenkinsdatadock -d -v /var/run/docker.sock:/var/run/docker.sock -v $(which docker):/usr/bin/docker -v WarTransfer:/war -v WarTransferProd:/prod jenkinsmasterdock
docker stop jenkinsmasterdock; docker rm jenkinsmasterdock;
docker tag jenkinsmasterdock $DOCKER_ID_USER/jenkinsmasterdock
```

### Sonar
#### Sonar (sonardock)
The sonar server to check the quality code.
```sh
docker build ./dockerfile/sonardock  --tag="jburleon/sonardock" -t sonardock
docker run -p 8082:8080 -p 9000:9000 --link sonarmysqldock:db --name=sonardock  sonardock
docker stop sonardock; docker rm sonardock;
docker tag sonardock $DOCKER_ID_USER/sonardock
```

#### Sonar Mysql (sonarmysqldock)
The Mysql databse for sonar.
```sh
docker build -t sonarmysqldock ./dockerfile/sonarmysqldock
docker run -p 8082:8080 -p 3306:3306 --name=sonarmysqldock sonarmysqldock
docker stop sonarmysqldock; docker rm sonarmysqldock;
docker tag sonarmysqldock $DOCKER_ID_USER/sonarmysqldock
```

### Volumes
The volumes are used to share a directory between some dockers
```sh
docker volume create WarTransfer
docker volume create WarTransferProd
```

## Utils informations
| Website | URL |
| ------ | ------ |
| Jenkins | http://jenkinsmasterdock-1.0c2ef173.cont.dockerapp.io:32771 |
| Dockercloud | https://cloud.docker.com/app/jburleon/dashboard/onboarding/cloud-registry |
| Amazone Web Service | https://eu-west-2.console.aws.amazon.com/ec2/v2/home?region=eu-west-2# |
| Computer Database | http://tomcatdock-1.50fd90d9.cont.dockerapp.io:32772/ComputerDatabase/ |
