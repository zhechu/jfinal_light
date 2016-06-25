--	建库
drop database if exists light ;
create database	light default charset utf8;

--	创建用户
create user 'light'@'localhost' identified by 'light';


--	设置权限
grant all privileges on light.* to 'light'@'localhost' with grant option;

grant super on *.* to 'light'@'localhost' with grant option;

flush privileges; 

--  使用库
use light;

drop table if exists schema_version ;
--	创建第一个表
create table schema_version (
  version           varchar(14)     not null    comment '时间戳,版本号',
  description       varchar(100)                ,
  type              varchar(10)     not null    ,
  script            varchar(200)    not null    ,
  checksum          bigint                      ,
  installed_by      varchar(30)     not null    ,
  installed_on      timestamp       not null    ,
  execution_time    int(10)                     ,
  state             varchar(15)     not null    ,
  current_version   mediumint(4)    not null    ,
  primary key(version)
) engine=innodb default charset=utf8;

alter table schema_version comment = 'migrate 跑的相关信息';