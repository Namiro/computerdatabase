 #-----------------------------------
 #USER RIGHTS MANAGEMENT
 #-----------------------------------
 CREATE USER 'travis'@'35.177.232.151' IDENTIFIED BY 'apql1161713';

 GRANT ALL PRIVILEGES ON `computer-database-db`.* TO 'travis'@'35.177.232.151' WITH GRANT OPTION;


 FLUSH PRIVILEGES;