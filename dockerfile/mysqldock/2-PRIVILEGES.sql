 #-----------------------------------
 #USER RIGHTS MANAGEMENT
 #-----------------------------------
 CREATE USER 'travis'@'%' IDENTIFIED BY 'apql1161713';

 GRANT ALL PRIVILEGES ON `computer-database-db`.* TO 'travis'@'%' WITH GRANT OPTION;


 FLUSH PRIVILEGES;