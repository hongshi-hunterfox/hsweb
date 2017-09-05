CREATE TABLE web_home_quick_navi (
  navi_id int identity (1,1) primary key,
  title varchar(255) NOT NULL DEFAULT '',
  image_url varchar(255) NOT NULL DEFAULT ''
);

CREATE TABLE web_quick_navi_product_link (
  id int identity (1,1) primary key,   
  navi_id int  NOT NULL, 
  product_id int  NOT NULL, 
);
alter table web_products add is_shipping_free bit not null DEFAULT 0;

insert into web_config (tag,value) values ('rechargeTmpId','');
insert into web_config (tag,value) values ('buyTmpId','');


CREATE TABLE web_message (
  id int identity (1,1) primary key,
  oauth_id varchar(255) NOT NULL DEFAULT '',
  order_num varchar(255) not null DEFAULT '',
  time DATETIME DEFAULT(GETDATE()),
  money decimal(20,2) NOT NULL DEFAULT '0.00',
  pay_type varchar(255) not null DEFAULT '',
  is_send bit NOT NULL DEFAULT 0
);