# Computer - Database
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


# Pipeline Configuration  
node {
    def mvnHome
    stage('Preparation') { // for display purposes
        // Clean up workspace
        step([$class: 'WsCleanup'])
        // Get some code from a GitHub repository
        git 'https://github.com/Namiro/computerdatabase.git'
        mvnHome = tool 'Maven3'
    }
    stage('Checkstyle') {
        step([$class: 'hudson.plugins.checkstyle.CheckStylePublisher', checkstyle: 'checkstyle.xml'])
    }
    withDockerContainer(args: '-d --name=javamavendock --net=TestNetwork --ip 172.18.0.4 ', image: 'javamavendock') {
        stage('Build') {
            // Run the maven build
            sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean compile"
        }
        stage('Test') {
            // Run the maven build
            sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean test"
        }
    }
    stage('Results') {
        junit '**/target/surfire-reports/TEST-*.xml'
        archive 'target/*.jar'
    }
}

# Docker commands
docker stop mysqldock
docker rm mysqldock
docker build -t mysqldock ./dockerfile/mysqldock
docker run -d --name=mysqldock --net=TestNetwork --ip 172.18.0.2 mysqldock
docker run -d --name=prodmysqldock --net=TestNetwork --ip 172.18.0.3 prodmysqldock
docker run -d --name=javamavendock --net=TestNetwork --ip 172.18.0.4 javamavendock

