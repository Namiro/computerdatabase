 #-----------------------------------
 #USER RIGHTS MANAGEMENT
 #-----------------------------------
 CREATE USER 'travis'@'172.20.0.4' IDENTIFIED BY 'apql1161713';

 GRANT ALL PRIVILEGES ON `computer-database-db`.* TO 'travis'@'172.20.0.4' WITH GRANT OPTION;


 FLUSH PRIVILEGES;