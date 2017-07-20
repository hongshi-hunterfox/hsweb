use master
Go
if Object_id('web_datasource_info','U') Is Null
Begin
CREATE TABLE web_datasource_info(
  id int IDENTITY(1,1) NOT NULL primary key,
  driver_class_name varchar(255) NOT NULL,
  url varchar(255) NOT NULL,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  merchant_code varchar(255) NOT NULL,
  merchant_name varchar(255) not null default ''
)
End
Go
If Not Exists(Select * from web_datasource_info Wehre merchant_code='master')
Begin
  insert into web_datasource_info (driver_class_name,url,username,password,merchant_code,merchant_name) values ('net.sourceforge.jtds.jdbc.Driver','jdbc:jtds:sqlserver://112.74.108.24:9630/masterdb','sa','to119,0002','master','master');
End
Go


