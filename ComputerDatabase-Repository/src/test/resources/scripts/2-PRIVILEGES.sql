  #-----------------------------------
  #USER RIGHTS MANAGEMENT
  #-----------------------------------
  CREATE USER 'travis'@'localhost' IDENTIFIED BY 'apql1161713';

  GRANT ALL PRIVILEGES ON `computer-database-db-test`.* TO 'travis'@'localhost' WITH GRANT OPTION;


  FLUSH PRIVILEGES;
