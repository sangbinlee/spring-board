-- https://github.com/spring-projects/spring-batch/blob/4.3.x/spring-batch-core/src/main/resources/org/springframework/batch/core/schema-oracle10g.sql
DROP TABLE  t_USERS;


-- drop table if exists t_USERS;


create table t_USERS(
  ID int not null ,
  NAME varchar(100) not null,
  STATUS int,
  PRIMARY KEY ( ID )
);

