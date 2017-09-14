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
insert into web_config (tag,value) values ('bindText','');
insert into web_config (tag,value) values ('supportDeliver','');
insert into web_config (tag,value) values ('domain','');
insert into web_config (tag,value) values ('hsMerchantCode','');


CREATE TABLE web_message (
  id int identity (1,1) primary key,
  oauth_id varchar(255) NOT NULL DEFAULT '',
  order_num varchar(255) not null DEFAULT '',
  time DATETIME DEFAULT(GETDATE()),
  money decimal(20,2) NOT NULL DEFAULT '0.00',
  pay_type varchar(255) not null DEFAULT '',
  is_send bit NOT NULL DEFAULT 0
);

CREATE TABLE web_comment (
  id int identity (1,1) primary key,
  user_id int not null,
  orderSerialNum varchar(255) NOT NULL,
  title varchar(1024) not null DEFAULT '',
  deliver int NOT NULL DEFAULT 5,
  service int NOT NULL DEFAULT 5,
  quality int NOT NULL DEFAULT 5,
  back_title varchar(1024) not null DEFAULT '',
  time DATETIME DEFAULT(GETDATE()),
  back_time DATETIME DEFAULT null
);

alter table web_recharge_config add amount int default 0;
alter table web_recharge_config add amount_second int default 0;
alter table web_recharge_config add amount_third int default 0;
alter table web_recharge_config add voucher_code_second varchar(255) default null;
alter table web_recharge_config add voucher_code_third varchar(255) default null;
alter table web_recharge_config add limit int default 0;
alter table web_recharge_config add start_time DATETIME DEFAULT null;
alter table web_recharge_config add end_time DATETIME DEFAULT null;

CREATE TABLE web_birth_voucher (
  id int identity (1,1) primary key,
  voucher_code varchar(255) NOT NULL DEFAULT '',
  amount int NOT NULL DEFAULT 0,
  time DATETIME DEFAULT(GETDATE())
);
CREATE TABLE web_shipping_full_cut (
  id int identity (1,1) primary key,
  s_limit float not null ,
  u_limit float not null ,
  condition decimal(15,2) not null ,
  time DATETIME DEFAULT(GETDATE()),
  start_time DATETIME DEFAULT null,
  end_time DATETIME DEFAULT null
);
CREATE TABLE web_recharge_rewards_record (
  id int identity (1,1) primary key,
  user_id int not null,
  config_id int not null,
  count int not null default 0
);
CREATE TABLE web_full_cut (
  id int identity (1,1) primary key,
  condition decimal(15,2) not null,
  cut decimal(15,2) not null ,
  time DATETIME DEFAULT(GETDATE()),
  start_time DATETIME DEFAULT null,
  end_time DATETIME DEFAULT null
);
CREATE TABLE web_binding_rewards (
  id int identity (1,1) primary key,
  point int not null default 0,
  voucher_code varchar(255) NOT NULL DEFAULT '',
  amount int NOT NULL DEFAULT 0,
  time DATETIME DEFAULT(GETDATE())
);

alter table web_orders add cut decimal(15,2) not null default 0 ; 