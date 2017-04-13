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


# Pipeline Configuration (Jenkins) 
`
node {
    def mvnHome
    stage('Preparation') { 
        step([$class: 'WsCleanup'])
        git 'https://github.com/Namiro/computerdatabase.git'
        mvnHome = tool 'Maven3'
    }
    stage('Docker Preparation') { 
        sh "docker stop mysqldock || true"
        sh "docker rm mysqldock || true"
        sh "docker run -d --name=mysqldock --net=TestNetwork --ip 172.18.0.2 mysqldock"
        sh "docker volume create WarTransfer"
        sh "docker volume create WarTransferProd"
    }
    stage('Checkstyle') {
        step([$class: 'hudson.plugins.checkstyle.CheckStylePublisher', checkstyle: 'checkstyle.xml'])
    }
    withDockerContainer(args: '-d --name=javamavendock --net=TestNetwork --ip 172.18.0.4 -v WarTransfer:/war -u 0', image: 'javamavendock') {
        stage('Clean') {
            sh "'${mvnHome}/bin/mvn' clean"
        }
        stage('Build') {
            sh "'${mvnHome}/bin/mvn' compile"
        }
        stage('Test') {
            sh "'${mvnHome}/bin/mvn' -Dmaven.main.skip test"
        }
        stage('Package') {
            sh "'${mvnHome}/bin/mvn' -Dmaven.main.skip -DskipTests package"
        }
        stage('Transfer WAR') {
            sh "cp /var/jenkins_home/workspace/ComputerDatabase/target/*.war /war"
        }
    }
    stage('Deploy WAR') {
        sh "sudo cp /war/* /prod/"
    }
    stage('Results') {
        junit '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
    }
        stage('Docker finalization') {
        sh "docker stop mysqldock"
        sh "docker rm mysqldock"
    }
}
`

# Docker
## Les réseaux
### Pour le test
`docker network create --subnet 172.18.0.0/16 --gateway 172.18.0.1 --driver bridge TestNetwork`
### Pour la prod
`docker network create --subnet 172.20.0.0/16 --gateway 172.20.0.1 --driver bridge ProdNetwork`

## Les containers
### La Database de test (mysqldock)
On doit la construire l'image.
`docker build -t mysqldock ./dockerfile/mysqldock` 
Mais elle est run et remove a chaque build par jenkins.
`docker run -d --name=mysqldock --net=TestNetwork --ip 172.18.0.2 mysqldock`
`docker stop mysqldock; docker rm mysqldock;` 

### La Database de prod (prodmysqldock)
On construit l'image et la run une fois.
`docker build -t prodmysqldock ./dockerfile/prodmysqldock`
`docker run -d --name=prodmysqldock --net=ProdNetwork --ip 172.20.0.2 prodmysqldock`
`docker stop prodmysqldock; docker rm prodmysqldock;` 

### Maven et Java (javamavendock)
On doit la construire l'image.
`docker build -t javamavendock ./dockerfile/javamavendock`
Mais elle est run et remove a chaque build par jenkins.
`docker run -d --name=javamavendock --net=TestNetwork --ip 172.18.0.4 -v WarTransfer:/war javamavendock`
`docker stop javamavendock; docker rm javamavendock;` 

### Le Tomcat de prod (tomcatdock)
On construit l'image et la run une fois.
`docker build -t tomcatdock ./dockerfile/tomcatdock`
`docker run -d --name=tomcatdock --net=ProdNetwork --ip 172.20.0.4 -v WarTransferProd:/usr/local/tomcat/webapps tomcatdock`
`docker stop tomcatdock; docker rm tomcatdock;` 

## Jenkins
### Le Jenkins data (jenkinsdatadock)
Il permet de conserver les données relative a jenkins master. On le construit et le run une fois il est ensuite utiliser par jenkinsmaster.
Si il est supprimé il sera nécessaire de reconfigurer jenkins. Mais il est possible de sauver les fichiers avant de le supprimer si nécessaire.
`docker build -t jenkinsdatadock ./dockerfile/jenkinsdatadock`
`docker run --name=jenkinsdatadock jenkinsdatadock`

### Le Jenkins master (jenkinsmasterdock)
`docker build -t jenkinsmasterdock ./dockerfile/jenkinsmasterdock`
`docker run -p 8082:8080 -p 50000:50000 --name=jenkinsmasterdock --volumes-from=jenkinsdatadock -d -v /var/run/docker.sock:/var/run/docker.sock -v $(which docker):/usr/bin/docker -v WarTransfer:/war -v WarTransferProd:/prod jenkinsmasterdock`
`docker stop jenkinsmasterdock; docker rm jenkinsmasterdock;`

## Les volumes
Volume partagé entre javamavendock & jenkinsmasterdock
`docker volume create WarTransfer`
Volume partagé entre jenkinsmasterdock & tomcatdock
`docker volume create WarTransferProd`


`docker start jenkinsmasterdock; docker start tomcatdock; docker start prodmysqldock; docker ps -a;`

