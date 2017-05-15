drop schema if exists `computer-database-db-test`;
  create schema if not exists `computer-database-db-test`;
  use `computer-database-db-test`;

  drop table if exists computer;
  drop table if exists company;

  create table company (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    constraint pk_company primary key (id))
  ;

  create table computer (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    introduced                timestamp NULL,
    discontinued              timestamp NULL,
    company_id                bigint default NULL,
    constraint pk_computer primary key (id))
  ;

  CREATE TABLE IF NOT EXISTS `user` (
  `username` varchar(60) NOT NULL,
  `password` varchar(200) NOT NULL,
  `accessLevel` varchar(60) NOT NULL,
`id` bigint(20) NOT NULL auto_increment,
constraint pk_user primary key (id))
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

  alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
  create index ix_computer_company_1 on computer (company_id);
