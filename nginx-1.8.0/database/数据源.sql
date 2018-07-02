create database masterdb;
use masterdb;

CREATE TABLE web_datasource_info(
  id int IDENTITY(1,1) NOT NULL primary key,
  driver_class_name varchar(255) NOT NULL,
  url varchar(255) NOT NULL,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  merchant_code varchar(255) NOT NULL,
  merchant_name varchar(255) not null default ''
);

insert into web_datasource_info (driver_class_name,url,username,password,merchant_code,merchant_name) values ('net.sourceforge.jtds.jdbc.Driver','jdbc:jtds:sqlserver://localhost:9630/master','sa','to119,000','master','master');

