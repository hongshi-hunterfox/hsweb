use 数据库名
go
If Object_ID('web_users','U') Is Null
CREATE TABLE web_users (
   user_id int identity (1,1) primary key, --用户id
   parent_id int  NOT NULL DEFAULT '0', --父id，微商城暂时不用
   password varchar(255) not null, --密码，微商城暂时没用
   serial_num varchar(255) not null --用户序列号
 );
If Object_ID('web_user_invited_link','U') Is Null
CREATE TABLE web_user_invited_link (
  user_id int  NOT NULL,    --用户id
  invited_id int  NOT NULL, --受邀用户id
  invite_time DATETIME DEFAULT(GETDATE()) --邀请时间
);

If Object_ID('web_oauth_login','U') Is Null
begin
CREATE TABLE web_oauth_login (
   login_id int identity (1,1) primary key, --主键id
   oauth_id varchar(255) NOT NULL,    --如微信openid
   user_id int NOT NULL,  --用户id
   oauth_name varchar(255) not null,  --登陆名信息
   oauth_access_token varchar(255) not null,--登陆名信息
   oauth_expires varchar(255) not null,--登陆名信息
   is_subcribe bit not null ,    --是否关注
   is_subscribe_show bit not null default 1, --微商城未使用
   is_point_send bit not null default 0, --微商城未使用
 );
end
CREATE NONCLUSTERED INDEX oauthIdx ON web_oauth_login(oauth_id)


If Object_ID('web_user_profiles','U') Is Null
begin
CREATE TABLE web_user_profiles(
   profile_id int identity (1,1) primary key, --主键id
   user_id int  NOT NULL, --用户id
   image varchar(255) NOT NULL default '', --用户头像
   email varchar(255) NOT NULL default '', --微商城未使用
   phone varchar(32) NOT NULL DEFAULT '',  --如果是老板，对应存手机登陆手机号
   name varchar(255) NOT NULL DEFAULT'',  --用户名
   nick_name varchar(255) NOT NULL DEFAULT'', --昵称
   login_count smallint NOT NULL DEFAULT 0, --微商城未使用
   regist_time  DATETIME DEFAULT(GETDATE()), --注册时间
   is_active bit NOT NULL DEFAULT '1', --微商城未使用
   ip_addr varchar(255) not null default '', --微商城未使用
   vip_image varchar(255) NOT NULL default '', --二维码地址
   vip_jbarcode varchar(255) NOT NULL default '', --条形码地址
   last_buy DATETIME DEFAULT null, --最后一次购买时间
);
end
CREATE NONCLUSTERED INDEX uidIdx ON web_oauth_login(user_id)


If Object_ID('web_payment_orders','U') Is Null
begin
CREATE TABLE web_payment_orders(
   payment_order_id int identity (1,1) primary key, --支付单号id
   user_id int  NOT NULL, --用户id
   payment_id int  NOT NULL, --支付方式id
   payment_serial_num varchar(255) NOT NULL, --支付单号
   transaction_id varchar(255) NOT NULL DEFAULT '', --外部支付交易号
   money decimal(20,2) NOT NULL DEFAULT '0.00', --支付金额
   transaction_type smallint not null default 1, --交易类型 1为商品订单，2为充值订单
   is_completed bit NOT NULL DEFAULT '0', --是否完成支付
   create_time DATETIME DEFAULT(GETDATE()), --创建时间
   complete_time DATETIME DEFAULT null, --完成时间
   is_sync bit not null default 0  --是否已同步订单到洪石系统
);
end
CREATE NONCLUSTERED INDEX uidIdx ON web_payment_orders(user_id)


If Object_ID('web_orders','U') Is Null
begin
CREATE TABLE web_orders(
   order_id int identity (1,1) primary key, --订单id
   order_serial_num varchar(255) NOT NULL DEFAULT '', --订单号
   user_id int  NOT NULL , --用户id
   store_id int NOT NULL,  --加盟店id
   payment_order_id int NOT NULL DEFAULT 0, --支付单id
   province VARCHAR(255) not null default '', --省份
   city VARCHAR(255) not null default '', --城市
   region VARCHAR(255) not null default '', --区域
   addr_detail VARCHAR(255) not null default '', --详细地址
   name VARCHAR(255) not null default '', --收货人
   phone VARCHAR(255) not null default '', --收货号码
   zip_code VARCHAR(255) not null default '', --邮编
   create_time DATETIME DEFAULT(GETDATE()), -- 创建时间
   pay_time DATETIME DEFAULT null, -- 支付时间
   shipping_cost decimal(20,2) NOT NULL DEFAULT '0.00', --运费
   total_price decimal(20,2) NOT NULL, --总价
   voucher_price decimal(20,2) NOT NULL default 0.00, --电子券使用金额
   voucher_code VARCHAR(255) not null default '',  --电子券编号
   status smallint NOT NULL DEFAULT '1', --状态，1未支付，2已支付
   remark VARCHAR(255) not null default '', --备注
   pick_time DATETIME DEFAULT null,--取货时间
   is_self_pick bit not null default 0,--是否自提
   first_dist_id int not null default 0, --第一分销者id
   first_dist_money decimal(20,2) NOT NULL default 0, --一级分销金额
   second_dist_id int not null default 0, --二级分销id
   second_dist_money decimal(20,2) NOT NULL default 0, --二级分销金额
   sync_status smallint NOT NULL DEFAULT '100',
);
end
CREATE NONCLUSTERED INDEX orderSerialNumIdx ON web_orders(order_serial_num)


If Object_ID('web_order_items','U') Is Null
begin
CREATE TABLE web_order_items(
   item_id int identity (1,1) primary key,
   item_serial_num varchar(255) NOT NULL DEFAULT '',
   order_id int NOT NULL ,
   store_id int NOT NULL,
   product_id int NOT NULL,
   image_url varchar(255) NOT NULL DEFAULT '',
   value_id int NOT NULL,
   value varchar(255) not null default '',
   amount smallint NOT NULL DEFAULT 0,
   price decimal(20,2) NOT NULL
);
end
CREATE NONCLUSTERED INDEX orderIdx ON web_order_items(order_id)


If Object_ID('web_payments','U') Is Null
CREATE TABLE web_payments(
   payment_id int identity (1,1) primary key,
   payment_name varchar(255) NOT NULL ,
   unit varchar(255) not null default '',
   platform varchar(255) not null default '',
   is_active bit NOT NULL DEFAULT '1',
   strategy_class_name varchar(255) not  NULL
);

If Object_ID('web_vars','U') Is Null
CREATE TABLE web_vars (
  var_id int identity (1,1) primary key,
  platform varchar(255) NOT NULL DEFAULT '',
  name varchar(255) NOT NULL DEFAULT '',
  storage_time DATETIME DEFAULT(GETDATE()),
  value varchar(1024) NOT NULL DEFAULT '',
);

If Object_ID('web_roles','U') Is Null
CREATE TABLE web_roles(
   role_id int identity (1,1) primary key,
   role varchar(255) NOT NULL ,
   parent_id int NOT NULL default 0,
   is_active bit NOT NULL DEFAULT '1',
   is_in_list bit not null default '1'
);

If Object_ID('web_user_role_link','U') Is Null
CREATE TABLE web_user_role_link(
   user_id int NOT NULL,
   role_id int NOT NULL
);

If Object_ID('web_permissions','U') Is Null
CREATE TABLE web_permissions(
   permission_id int identity (1,1) primary key,
   permission varchar(255) NOT NULL,
   tag VARCHAR(255) NOT NULL DEFAULT '',
   name varchar(255) NOT NULL,
   is_active bit NOT NULL DEFAULT '1'
);


If Object_ID('web_role_permission_link','U') Is Null
CREATE TABLE web_role_permission_link(
   role_id int  NOT NULL,
   permission_id int NOT NULL
);

If Object_ID('web_categories','U') Is Null
CREATE TABLE web_categories(
   category_id int identity (1,1) primary key,
   category  varchar(255) NOT NULL,
   parent_id int NOT NULL
);

If Object_ID('web_products_categories_link','U') Is Null
CREATE TABLE web_products_categories_link(
   product_id int NOT NULL,
   category_id int NOT NULL
);

If Object_ID('web_products','U') Is Null
CREATE TABLE web_products (
   product_id int identity (1,1) primary key,
   title varchar(255) NOT NULL DEFAULT '', 
   description varchar(255) NOT NULL DEFAULT '', 
   create_time DATETIME DEFAULT(GETDATE()), 
   last_modify DATETIME DEFAULT(GETDATE()),
   is_active bit NOT NULL DEFAULT '0',
   price decimal(20,2) NOT NULL DEFAULT '0.00',
 );
 alter table web_products add is_shipping_free bit not null DEFAULT 0;
 alter table web_products add is_show bit not null default 1;
 alter table web_products add Explain varchar(255);
 
 

If Object_ID('web_product_images_link','U') Is Null
 CREATE TABLE web_product_images_link(
   link_id int identity (1,1) primary key,
   product_id int NOT NULL ,
   image_url varchar(255) NOT NULL DEFAULT ''
);

If Object_ID('web_specifications','U') Is Null
 CREATE TABLE web_specifications(
   specification_id int identity (1,1) primary key,
   specification varchar(255) NOT NULL DEFAULT ''
);

If Object_ID('web_specification_values','U') Is Null
CREATE TABLE web_specification_values(
   value_id int identity (1,1) primary key,
   specification_id int not null,
   value varchar(255) NOT NULL DEFAULT '',
   hs_goods_code varchar(255) not null,
   hs_goods_price decimal(20,2) NOT NULL DEFAULT '0.00',
   hs_stock int not null,
   pre_price decimal(20,2) NOT NULL DEFAULT '0.00'
);

If Object_ID('web_products_specifications_values_link','U') Is Null
 CREATE TABLE web_products_specifications_values_link(
   specification_id int  NOT NULL,
   value_id int  NOT NULL,
   product_id int not null
);

If Object_ID('web_product_groups','U') Is Null
 CREATE TABLE web_product_groups (
  group_id int identity (1,1) primary key,
  group_name varchar(255) NOT NULL,
  tag varchar(255) not null default '',
  display_type varchar(255) not null default '',
  is_active bit NOT NULL DEFAULT '0'
);

If Object_ID('web_product_group_links','U') Is Null
CREATE TABLE web_product_group_links (
  group_id int not null,
  product_id int  NOT NULL,
  position int  NOT NULL default 0
);

If Object_ID('web_napa_stores','U') Is Null
 create table web_napa_stores(
   store_id int identity (1,1) primary key,
   user_id int not null,
   store_name varchar(255) not null,
   province varchar(255) not null,
   city varchar(255) not null,
   region varchar(255) not null,
   addr_detail varchar(255) not null,
   longitude varchar(255) not null,
   latitude varchar(255) not null,
   hs_code varchar(255) not null
);

If Object_ID('web_specification_value_store_link','U') Is Null
CREATE TABLE web_specification_value_store_link (
   value_id int not null,
   store_id int not null
 );

If Object_ID('web_province','U') Is Null
 CREATE TABLE web_province(
   province_id int primary key,
   province varchar(255) NOT NULL ,
);

If Object_ID('web_cities','U') Is Null
CREATE TABLE web_cities(
   city_id int primary key,
   province_id int NOT NULL,
   city varchar(255) NOT NULL 
);

If Object_ID('web_regions','U') Is Null
CREATE TABLE web_regions(
   region_id int primary key,
   city_id int  NOT NULL,
   region varchar(255) NOT NULL 
);

If Object_ID('web_deliver_addrs','U') Is Null
begin
CREATE TABLE web_deliver_addrs(
  
 deliverAddr_id int identity (1,1) primary key,
  
 user_id int  NOT NULL ,
  
 name varchar(255) NOT NULL DEFAULT '',
  
 phone varchar(32) NOT NULL DEFAULT '',
   
province varchar(32) NOT NULL DEFAULT '',
   
city varchar(32) NOT NULL DEFAULT '',
 
  region varchar(32) NOT NULL DEFAULT '',
 
  addr_detail varchar(255) NOT NULL DEFAULT'',
 
  zipCode varchar(32) NOT NULL DEFAULT '',
 
  is_default bit NOT NULL DEFAULT 0,
 
  longitude varchar(255) not null default '',
  
 latitude varchar(255) not null default ''

);
end
CREATE NONCLUSTERED INDEX userIdx ON web_deliver_addrs(user_id);


If Object_ID('web_cart','U') Is Null
begin
create table web_cart(
   cart_id int identity (1,1) primary key,
   user_id int not null,
   product_id int not null,
   specification_value_id int not null,
   amount int not null,
   create_time DATETIME DEFAULT(GETDATE())
);
end
CREATE NONCLUSTERED INDEX userIdx ON web_cart(user_id);

If Object_ID('web_datasource','U') Is Null
CREATE TABLE web_datasource (
  id int identity (1,1) primary key,
  driver_class_name varchar(255) NOT NULL,
  url varchar(255) NOT NULL,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  merchant_code varchar(255) NOT NULL
);

If Object_ID('web_config','U') Is Null
create table web_config(
  id int identity (1,1) primary key,
  tag varchar(255) not null,
  value varchar(255) not null
);

If Object_ID('web_sign_record','U') Is Null
create table web_sign_record(
  id int identity (1,1) primary key,
  user_id int not null,
  sign_time DATETIME DEFAULT(GETDATE()),
  point int not null default 0
);

If Object_ID('web_balance','U') Is Null
create table web_balance(
  id int identity (1,1) primary key,
  user_id int not null,
  balance decimal(20,2) NOT NULL,
);

If Object_ID('web_balance_log','U') Is Null
create table web_balance_log(
  id int identity (1,1) primary key,
  user_id int not null,
  money decimal(20,2) NOT NULL,
  balance decimal(20,2) NOT NULL,
  create_time DATETIME DEFAULT(GETDATE())
);

If Object_ID('web_freight','U') Is Null
create table web_freight(
  id int identity (1,1) primary key,
  condition float not null default 0.00,
  money decimal(20,2) NOT NULL
);

If Object_ID('web_recharge_config','U') Is Null
create table web_recharge_config(
  id int identity (1,1) primary key,
  money decimal(20,2) NOT NULL,
  rewards decimal(20,2) NOT NULL default 0,
  voucher_code varchar(255) not null default '',
  type int not null default 1,
  start_time DATETIME DEFAULT(GETDATE()),
  end_time DATETIME DEFAULT(GETDATE())
);

If Object_ID('web_lottery_draw_config','U') Is Null
create table web_lottery_draw_config(
  id int identity(1,1) primary key,
  voucher_code varchar(255) default null,
  money decimal(20,2) default null,
  product_id int not null default 0,
  code varchar(255) not null default '',
  rate float not null default 0.00,
  count int not null default 0,
  is_end bit not null default 0,
  limits int not null default 10000,
  start_time DATETIME DEFAULT(GETDATE()),
  end_time DATETIME DEFAULT(GETDATE())
);

If Object_ID('web_lottery_record','U') Is Null
create table web_lottery_record(
  id int identity(1,1) primary key,
  user_id int not null,
  time DATETIME DEFAULT(GETDATE())
);

If Object_ID('web_banner','U') Is Null
create table web_banner(
  id int identity(1,1) primary key,
  image_url varchar(255) not null default '',
  link varchar(255) not null default ''
);

If Object_ID('web_shake_record','U') Is Null
create table web_shake_record(
  id int identity(1,1) primary key,
  user_id int not null,
  time DATETIME DEFAULT(GETDATE()),
  is_show bit not null default 0
);

If Object_ID('web_winning_record','U') Is Null
create table web_winning_record(
  id int identity(1,1) primary key,
  user_id int not null,
  winning_level int not null,
  time DATETIME DEFAULT(GETDATE()) 
);

If Object_ID('web_store_info','U') Is Null
create table web_store_info(
  id int identity(1,1) primary key,
  description varchar(255) not null default '',
);

If Object_ID('web_product_sale','U') Is Null
create table web_product_sale(
  id int identity(1,1) primary key,
  product_id int not null ,
  count int not null default 0,
);

If Object_ID('web_msg_record','U') Is Null
create table web_msg_record(
  id int identity(1,1) primary key,
  user_id int not null ,
  type int not null default 0,
  time DATETIME DEFAULT(GETDATE()),
);
/*新增表*/
If Object_ID('web_home_quick_navi ','U') Is Null
CREATE TABLE web_home_quick_navi (
  navi_id int identity (1,1) primary key,
  title varchar(255) NOT NULL DEFAULT '',
  image_url varchar(255) NOT NULL DEFAULT ''
);

If Object_ID('web_quick_navi_product_link  ','U') Is Null
CREATE TABLE web_quick_navi_product_link (
  id int identity (1,1) primary key,   
  navi_id int  NOT NULL, 
  product_id int  NOT NULL, 
);


If Object_ID('web_message  ','U') Is Null
CREATE TABLE web_message (
  id int identity (1,1) primary key,
  oauth_id varchar(255) NOT NULL DEFAULT '',
  order_num varchar(255) not null DEFAULT '',
  time DATETIME DEFAULT(GETDATE()),
  money decimal(20,2) NOT NULL DEFAULT '0.00',
  pay_type varchar(255) not null DEFAULT '',
  is_send bit NOT NULL DEFAULT 0
);

If Object_ID('web_comment  ','U') Is Null
CREATE TABLE web_comment (
  id int identity (1,1) primary key,
  user_id int not null,
  order_serial_num varchar(255) NOT NULL,
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

If Object_ID('web_birth_voucher  ','U') Is Null
CREATE TABLE web_birth_voucher (
  id int identity (1,1) primary key,
  voucher_code varchar(255) NOT NULL DEFAULT '',
  amount int NOT NULL DEFAULT 0,
  time DATETIME DEFAULT(GETDATE())
);

If Object_ID('web_shipping_full_cut  ','U') Is Null
CREATE TABLE web_shipping_full_cut (
  id int identity (1,1) primary key,
  s_limit float not null ,
  u_limit float not null ,
  condition decimal(15,2) not null ,
  time DATETIME DEFAULT(GETDATE()),
  start_time DATETIME DEFAULT null,
  end_time DATETIME DEFAULT null
);

If Object_ID('web_recharge_rewards_record  ','U') Is Null
CREATE TABLE web_recharge_rewards_record (
  id int identity (1,1) primary key,
  user_id int not null,
  config_id int not null,
  count int not null default 0
);

If Object_ID('web_full_cut ','U') Is Null
CREATE TABLE web_full_cut (
  id int identity (1,1) primary key,
  condition decimal(15,2) not null,
  cut decimal(15,2) not null ,
  time DATETIME DEFAULT(GETDATE()),
  start_time DATETIME DEFAULT null,
  end_time DATETIME DEFAULT null
);

If Object_ID('web_binding_rewards  ','U') Is Null
CREATE TABLE web_binding_rewards (
  id int identity (1,1) primary key,
  point int not null default 0,
  voucher_code varchar(255) NOT NULL DEFAULT '',
  amount int NOT NULL DEFAULT 1,
  time DATETIME DEFAULT(GETDATE())
);

alter table web_orders add cut decimal(15,2) not null default 0 ;

alter table web_orders add pick_up_image varchar(255) not null default '';
alter table web_orders add pick_up_barcode varchar(255) not null default '';

/*2017/10/25*/
If Object_ID('web_napa_store_user_link','U') Is Null
create table web_napa_store_user_link(
  id int identity(1,1) primary key,
  user_id int not null ,
  store_id int not null 
);


insert into web_napa_store_user_link (store_id,user_id) select store_id,user_id from web_napa_stores;


alter table web_napa_stores drop column user_id;
 
/*web_banner初始数据*/
insert into web_banner (image_url) values ('http://120.25.193.220/group1/M00/31/AD/eBnB3Fk9GD6AIPnoAABjjVg2HRo57.file');
insert into web_banner (image_url) values ('http://120.25.193.220/group1/M00/31/AD/eBnB3Fk9GFaAEUgtAACT8LBtwhw95.file');
insert into web_banner (image_url) values ('http://120.25.193.220/group1/M00/31/AD/eBnB3Fk9GBSAPOnyAACmblVhynw90.file');

/*web_freight初始数据*/
insert into web_freight (condition,money) values (10,8);
  insert into web_freight (condition,money) values (20,10);
    insert into web_freight (condition,money) values (30,12);

/*web_product_groups初始数据*/
insert into web_product_groups(group_name,tag,display_type,is_active) values ('店铺推荐','recommend','horizontal','1');
insert into web_product_groups(group_name,tag,display_type,is_active) values ('店铺精品','hotProduct','vertical','1');

/*web_payments表初始数据*/
insert into web_payments (payment_name,unit,platform,is_active,strategy_class_name) values ('微信支付','WeChat','WAP',1,'WCJSAPIPaymentStrategy');
 insert into web_payments (payment_name,unit,platform,is_active,strategy_class_name) values ('会员卡支付','VipCard','WAP',1,'MemberCardPaymentStrategy');
 insert into web_payments (payment_name,unit,platform,is_active,strategy_class_name) values ('支付宝支付','Alipay','WAP',1,'AlipayPaymentStrategy');

/*web_recharge_config*/
insert into web_recharge_config (money,rewards) values (50,10);
insert into web_recharge_config (money,rewards) values (100,15);
insert into web_recharge_config (money,rewards) values (150,20);
insert into web_recharge_config (money,rewards) values (200,25);
insert into web_recharge_config (money,rewards) values (250,30);
insert into web_recharge_config (money,rewards) values (300,35);
insert into web_recharge_config (money,rewards) values (400,40);
insert into web_recharge_config (money,rewards) values (500,50);
insert into web_recharge_config (money,rewards) values (800,80);
insert into web_recharge_config (money,rewards) values (1000,100);

/*web_categories*/
insert into web_categories (category,parent_id) values ('下午茶',0);
insert into web_categories (category,parent_id) values ('面包',0);
insert into web_categories (category,parent_id) values ('生日蛋糕',0);
insert into web_categories (category,parent_id) values ('巧克力蛋糕',0);
insert into web_categories (category,parent_id) values ('鲜果蛋糕',0);
insert into web_categories (category,parent_id) values ('芝士慕斯蛋糕',0);
insert into web_categories (category,parent_id) values ('多层蛋糕',0);
insert into web_categories (category,parent_id) values ('栗子蛋糕',0);
insert into web_categories (category,parent_id) values ('节日蛋糕',0);


/*web_role*/
insert into web_roles (role,parent_id,is_active) values ('admin',0,1);
insert into web_roles (role,parent_id,is_active) values ('user',0,1);
insert into web_roles (role,parent_id,is_active) values ('business',0,1);

/*web_var*/
insert into web_vars (platform,name,storage_time,value) values ('WX','access_token','2017-01-01 00:00:00','CO4OuunYImgxUl1tZu6BDcyDlT2RhISAkfuv_rUE2RKKUhoRjtVWJeTLwkzq5DCO6TJwlhcLgIHTd8VsMzZzyC6Bui00l2eFBwoH_u-aVZxjBzy_Et77YqW0p71Klb5QXTPjADATPL');

/*web_specifications*/
insert into web_specifications(specification) values ('款式');

/*web_config表初始数据*/
insert into web_config (tag,value) values ('registPoint','10');
insert into web_config (tag,value) values ('signInPoint','10');
insert into web_config (tag,value) values ('drawPoint','10');
insert into web_config (tag,value) values ('firstDis','10');
insert into web_config (tag,value) values ('secondDis','10');
insert into web_config (tag,value) values ('appid','wx68abe3fb2a71dcc7');
insert into web_config (tag,value) values ('appsecret','00030b62032af67f83e535223616a0d6');
insert into web_config (tag,value) values ('appkey','00030b62032af67f83e535223616a0d6');
insert into web_config (tag,value) values ('merchantcode','1253393501');
insert into web_config (tag,value) values ('notifyurl','http://hs.uclee.com/seller/WCNotifyHandler');
insert into web_config (tag,value) values ('partner','');
insert into web_config (tag,value) values ('sellerId','');
insert into web_config (tag,value) values ('key','');
insert into web_config (tag,value) values ('alipayNotifyUrl','');
insert into web_config (tag,value) values ('firstPrize',2);
insert into web_config (tag,value) values ('secondPrize',10);
insert into web_config (tag,value) values ('thirdPrize',30);
insert into web_config (tag,value) values ('signName','洪石软件');
insert into web_config (tag,value) values ('firstCount',2);
insert into web_config (tag,value) values ('secondCount',10);
insert into web_config (tag,value) values ('thirdCount',30);
insert into web_config (tag,value) values ('aliAppkey','LTAIb36ti4sJYhwY');
insert into web_config (tag,value) values ('aliAppSecret','wDkzuBidUH6oog7jvdxW9A4JNS42br');
insert into web_config (tag,value) values ('templateCode','SMS_94630154');
insert into web_config (tag,value) values ('birthTmpId','EMzRY8T0fa90sGTBYZkINvxTGn_nvwKjHZUxtpTmVew');
insert into web_config (tag,value) values ('buyTmpId','EMzRY8T0fa90sGTBYZkINvxTGn_nvwKjHZUxtpTmVew');
insert into web_config (tag,value) values ('payTmpId','S3vfLhEEbVICFmwgpHedYUtlm7atyY3zl-GxJYY20ok');
insert into web_config (tag,value) values ('rechargeTmpId','TBY-Wrn9sQuOoM_BUNZO2aEjX56AG6RRNxHrEH8k_pI');
insert into web_config (tag,value) values ('bindText','');
insert into web_config (tag,value) values ('supportDeliver','');
insert into web_config (tag,value) values ('domain','');
insert into web_config (tag,value) values ('hsMerchantCode','');
insert into web_config (tag,value) values ('logoUrl','http://wsc.in80s.com/file/file1507708577132837.jpg');
insert into web_config (tag,value) values ('ucenterImg','http://wsc.in80s.com/file/file1507708606025464.jpg');
insert into web_config (tag,value) values ('birthText','');
insert into web_config (tag,value) values ('salesText','');
insert into web_config (tag,value) values ('restrictedDistance','50');






/*province初始数据*/
insert into web_province (province_id,province) values (11,'北京');
insert into web_province (province_id,province) values (12,'天津');
insert into web_province (province_id,province) values (13,'河北省');
insert into web_province (province_id,province) values (14,'山西省');
insert into web_province (province_id,province) values (15,'内蒙古自治区');
insert into web_province (province_id,province) values (21,'辽宁省');
insert into web_province (province_id,province) values (22,'吉林省');
insert into web_province (province_id,province) values (23,'黑龙江省');
insert into web_province (province_id,province) values (31,'上海');
insert into web_province (province_id,province) values (32,'江苏省');
insert into web_province (province_id,province) values (33,'浙江省');
insert into web_province (province_id,province) values (34,'安徽省');
insert into web_province (province_id,province) values (35,'福建省');
insert into web_province (province_id,province) values (36,'江西省');
insert into web_province (province_id,province) values (37,'山东省');
insert into web_province (province_id,province) values (41,'河南省');
insert into web_province (province_id,province) values (42,'湖北省');
insert into web_province (province_id,province) values (43,'湖南省');
insert into web_province (province_id,province) values (44,'广东省');
insert into web_province (province_id,province) values (45,'广西壮族自治区');
insert into web_province (province_id,province) values (46,'海南省');
insert into web_province (province_id,province) values (50,'重庆');
insert into web_province (province_id,province) values (51,'四川省');
insert into web_province (province_id,province) values (52,'贵州省');
insert into web_province (province_id,province) values (53,'云南省');
insert into web_province (province_id,province) values (54,'西藏自治区');
insert into web_province (province_id,province) values (61,'陕西省');
insert into web_province (province_id,province) values (62,'甘肃省');
insert into web_province (province_id,province) values (63,'青海省');
insert into web_province (province_id,province) values (64,'宁夏回族自治区');
insert into web_province (province_id,province) values (65,'新疆维吾尔自治区');
insert into web_province (province_id,province) values (71,'台湾');
insert into web_province (province_id,province) values (81,'香港特别行政区');
insert into web_province (province_id,province) values (82,'澳门特别行政区');
insert into web_province (province_id,province) values (90,'钓鱼岛');

/*web_city初始数据*/
insert into web_cities (city_id,province_id,city) values (1101,11,'北京市');
insert into web_cities (city_id,province_id,city) values (1201,12,'天津市');
insert into web_cities (city_id,province_id,city) values (1301,13,'石家庄市');
insert into web_cities (city_id,province_id,city) values (1302,13,'唐山市');
insert into web_cities (city_id,province_id,city) values (1303,13,'秦皇岛市');
insert into web_cities (city_id,province_id,city) values (1304,13,'邯郸市');
insert into web_cities (city_id,province_id,city) values (1305,13,'邢台市');
insert into web_cities (city_id,province_id,city) values (1306,13,'保定市');
insert into web_cities (city_id,province_id,city) values (1307,13,'张家口市');
insert into web_cities (city_id,province_id,city) values (1308,13,'承德市');
insert into web_cities (city_id,province_id,city) values (1309,13,'沧州市');
insert into web_cities (city_id,province_id,city) values (1310,13,'廊坊市');
insert into web_cities (city_id,province_id,city) values (1311,13,'衡水市');
insert into web_cities (city_id,province_id,city) values (1401,14,'太原市');
insert into web_cities (city_id,province_id,city) values (1402,14,'大同市');
insert into web_cities (city_id,province_id,city) values (1403,14,'阳泉市');
insert into web_cities (city_id,province_id,city) values (1404,14,'长治市');
insert into web_cities (city_id,province_id,city) values (1405,14,'晋城市');
insert into web_cities (city_id,province_id,city) values (1406,14,'朔州市');
insert into web_cities (city_id,province_id,city) values (1407,14,'晋中市');
insert into web_cities (city_id,province_id,city) values (1408,14,'运城市');
insert into web_cities (city_id,province_id,city) values (1409,14,'忻州市');
insert into web_cities (city_id,province_id,city) values (1410,14,'临汾市');
insert into web_cities (city_id,province_id,city) values (1411,14,'吕梁市');
insert into web_cities (city_id,province_id,city) values (1501,15,'呼和浩特市');
insert into web_cities (city_id,province_id,city) values (1502,15,'包头市');
insert into web_cities (city_id,province_id,city) values (1503,15,'乌海市');
insert into web_cities (city_id,province_id,city) values (1504,15,'赤峰市');
insert into web_cities (city_id,province_id,city) values (1505,15,'通辽市');
insert into web_cities (city_id,province_id,city) values (1506,15,'鄂尔多斯市');
insert into web_cities (city_id,province_id,city) values (1507,15,'呼伦贝尔市');
insert into web_cities (city_id,province_id,city) values (1508,15,'巴彦淖尔市');
insert into web_cities (city_id,province_id,city) values (1509,15,'乌兰察布市');
insert into web_cities (city_id,province_id,city) values (1522,15,'兴安盟');
insert into web_cities (city_id,province_id,city) values (1525,15,'锡林郭勒盟');
insert into web_cities (city_id,province_id,city) values (1529,15,'阿拉善盟');
insert into web_cities (city_id,province_id,city) values (2101,21,'沈阳市');
insert into web_cities (city_id,province_id,city) values (2102,21,'大连市');
insert into web_cities (city_id,province_id,city) values (2103,21,'鞍山市');
insert into web_cities (city_id,province_id,city) values (2104,21,'抚顺市');
insert into web_cities (city_id,province_id,city) values (2105,21,'本溪市');
insert into web_cities (city_id,province_id,city) values (2106,21,'丹东市');
insert into web_cities (city_id,province_id,city) values (2107,21,'锦州市');
insert into web_cities (city_id,province_id,city) values (2108,21,'营口市');
insert into web_cities (city_id,province_id,city) values (2109,21,'阜新市');
insert into web_cities (city_id,province_id,city) values (2110,21,'辽阳市');
insert into web_cities (city_id,province_id,city) values (2111,21,'盘锦市');
insert into web_cities (city_id,province_id,city) values (2112,21,'铁岭市');
insert into web_cities (city_id,province_id,city) values (2113,21,'朝阳市');
insert into web_cities (city_id,province_id,city) values (2114,21,'葫芦岛市');
insert into web_cities (city_id,province_id,city) values (2115,21,'金普新区');
insert into web_cities (city_id,province_id,city) values (2201,22,'长春市');
insert into web_cities (city_id,province_id,city) values (2202,22,'吉林市');
insert into web_cities (city_id,province_id,city) values (2203,22,'四平市');
insert into web_cities (city_id,province_id,city) values (2204,22,'辽源市');
insert into web_cities (city_id,province_id,city) values (2205,22,'通化市');
insert into web_cities (city_id,province_id,city) values (2206,22,'白山市');
insert into web_cities (city_id,province_id,city) values (2207,22,'松原市');
insert into web_cities (city_id,province_id,city) values (2208,22,'白城市');
insert into web_cities (city_id,province_id,city) values (2224,22,'延边朝鲜族自治州');
insert into web_cities (city_id,province_id,city) values (2301,23,'哈尔滨市');
insert into web_cities (city_id,province_id,city) values (2302,23,'齐齐哈尔市');
insert into web_cities (city_id,province_id,city) values (2303,23,'鸡西市');
insert into web_cities (city_id,province_id,city) values (2304,23,'鹤岗市');
insert into web_cities (city_id,province_id,city) values (2305,23,'双鸭山市');
insert into web_cities (city_id,province_id,city) values (2306,23,'大庆市');
insert into web_cities (city_id,province_id,city) values (2307,23,'伊春市');
insert into web_cities (city_id,province_id,city) values (2308,23,'佳木斯市');
insert into web_cities (city_id,province_id,city) values (2309,23,'七台河市');
insert into web_cities (city_id,province_id,city) values (2310,23,'牡丹江市');
insert into web_cities (city_id,province_id,city) values (2311,23,'黑河市');
insert into web_cities (city_id,province_id,city) values (2312,23,'绥化市');
insert into web_cities (city_id,province_id,city) values (2327,23,'大兴安岭地区');
insert into web_cities (city_id,province_id,city) values (3101,31,'上海市');
insert into web_cities (city_id,province_id,city) values (3201,32,'南京市');
insert into web_cities (city_id,province_id,city) values (3202,32,'无锡市');
insert into web_cities (city_id,province_id,city) values (3203,32,'徐州市');
insert into web_cities (city_id,province_id,city) values (3204,32,'常州市');
insert into web_cities (city_id,province_id,city) values (3205,32,'苏州市');
insert into web_cities (city_id,province_id,city) values (3206,32,'南通市');
insert into web_cities (city_id,province_id,city) values (3207,32,'连云港市');
insert into web_cities (city_id,province_id,city) values (3208,32,'淮安市');
insert into web_cities (city_id,province_id,city) values (3209,32,'盐城市');
insert into web_cities (city_id,province_id,city) values (3210,32,'扬州市');
insert into web_cities (city_id,province_id,city) values (3211,32,'镇江市');
insert into web_cities (city_id,province_id,city) values (3212,32,'泰州市');
insert into web_cities (city_id,province_id,city) values (3213,32,'宿迁市');
insert into web_cities (city_id,province_id,city) values (3301,33,'杭州市');
insert into web_cities (city_id,province_id,city) values (3302,33,'宁波市');
insert into web_cities (city_id,province_id,city) values (3303,33,'温州市');
insert into web_cities (city_id,province_id,city) values (3304,33,'嘉兴市');
insert into web_cities (city_id,province_id,city) values (3305,33,'湖州市');
insert into web_cities (city_id,province_id,city) values (3306,33,'绍兴市');
insert into web_cities (city_id,province_id,city) values (3307,33,'金华市');
insert into web_cities (city_id,province_id,city) values (3308,33,'衢州市');
insert into web_cities (city_id,province_id,city) values (3309,33,'舟山市');
insert into web_cities (city_id,province_id,city) values (3310,33,'台州市');
insert into web_cities (city_id,province_id,city) values (3311,33,'丽水市');
insert into web_cities (city_id,province_id,city) values (3312,33,'舟山群岛新区');
insert into web_cities (city_id,province_id,city) values (3401,34,'合肥市');
insert into web_cities (city_id,province_id,city) values (3402,34,'芜湖市');
insert into web_cities (city_id,province_id,city) values (3403,34,'蚌埠市');
insert into web_cities (city_id,province_id,city) values (3404,34,'淮南市');
insert into web_cities (city_id,province_id,city) values (3405,34,'马鞍山市');
insert into web_cities (city_id,province_id,city) values (3406,34,'淮北市');
insert into web_cities (city_id,province_id,city) values (3407,34,'铜陵市');
insert into web_cities (city_id,province_id,city) values (3408,34,'安庆市');
insert into web_cities (city_id,province_id,city) values (3410,34,'黄山市');
insert into web_cities (city_id,province_id,city) values (3411,34,'滁州市');
insert into web_cities (city_id,province_id,city) values (3412,34,'阜阳市');
insert into web_cities (city_id,province_id,city) values (3413,34,'宿州市');
insert into web_cities (city_id,province_id,city) values (3415,34,'六安市');
insert into web_cities (city_id,province_id,city) values (3416,34,'亳州市');
insert into web_cities (city_id,province_id,city) values (3417,34,'池州市');
insert into web_cities (city_id,province_id,city) values (3418,34,'宣城市');
insert into web_cities (city_id,province_id,city) values (3501,35,'福州市');
insert into web_cities (city_id,province_id,city) values (3502,35,'厦门市');
insert into web_cities (city_id,province_id,city) values (3503,35,'莆田市');
insert into web_cities (city_id,province_id,city) values (3504,35,'三明市');
insert into web_cities (city_id,province_id,city) values (3505,35,'泉州市');
insert into web_cities (city_id,province_id,city) values (3506,35,'漳州市');
insert into web_cities (city_id,province_id,city) values (3507,35,'南平市');
insert into web_cities (city_id,province_id,city) values (3508,35,'龙岩市');
insert into web_cities (city_id,province_id,city) values (3509,35,'宁德市');
insert into web_cities (city_id,province_id,city) values (3601,36,'南昌市');
insert into web_cities (city_id,province_id,city) values (3602,36,'景德镇市');
insert into web_cities (city_id,province_id,city) values (3603,36,'萍乡市');
insert into web_cities (city_id,province_id,city) values (3604,36,'九江市');
insert into web_cities (city_id,province_id,city) values (3605,36,'新余市');
insert into web_cities (city_id,province_id,city) values (3606,36,'鹰潭市');
insert into web_cities (city_id,province_id,city) values (3607,36,'赣州市');
insert into web_cities (city_id,province_id,city) values (3608,36,'吉安市');
insert into web_cities (city_id,province_id,city) values (3609,36,'宜春市');
insert into web_cities (city_id,province_id,city) values (3610,36,'抚州市');
insert into web_cities (city_id,province_id,city) values (3611,36,'上饶市');
insert into web_cities (city_id,province_id,city) values (3701,37,'济南市');
insert into web_cities (city_id,province_id,city) values (3702,37,'青岛市');
insert into web_cities (city_id,province_id,city) values (3703,37,'淄博市');
insert into web_cities (city_id,province_id,city) values (3704,37,'枣庄市');
insert into web_cities (city_id,province_id,city) values (3705,37,'东营市');
insert into web_cities (city_id,province_id,city) values (3706,37,'烟台市');
insert into web_cities (city_id,province_id,city) values (3707,37,'潍坊市');
insert into web_cities (city_id,province_id,city) values (3708,37,'济宁市');
insert into web_cities (city_id,province_id,city) values (3709,37,'泰安市');
insert into web_cities (city_id,province_id,city) values (3710,37,'威海市');
insert into web_cities (city_id,province_id,city) values (3711,37,'日照市');
insert into web_cities (city_id,province_id,city) values (3712,37,'莱芜市');
insert into web_cities (city_id,province_id,city) values (3713,37,'临沂市');
insert into web_cities (city_id,province_id,city) values (3714,37,'德州市');
insert into web_cities (city_id,province_id,city) values (3715,37,'聊城市');
insert into web_cities (city_id,province_id,city) values (3716,37,'滨州市');
insert into web_cities (city_id,province_id,city) values (3717,37,'菏泽市');
insert into web_cities (city_id,province_id,city) values (4101,41,'郑州市');
insert into web_cities (city_id,province_id,city) values (4102,41,'开封市');
insert into web_cities (city_id,province_id,city) values (4103,41,'洛阳市');
insert into web_cities (city_id,province_id,city) values (4104,41,'平顶山市');
insert into web_cities (city_id,province_id,city) values (4105,41,'安阳市');
insert into web_cities (city_id,province_id,city) values (4106,41,'鹤壁市');
insert into web_cities (city_id,province_id,city) values (4107,41,'新乡市');
insert into web_cities (city_id,province_id,city) values (4108,41,'焦作市');
insert into web_cities (city_id,province_id,city) values (4109,41,'濮阳市');
insert into web_cities (city_id,province_id,city) values (4110,41,'许昌市');
insert into web_cities (city_id,province_id,city) values (4111,41,'漯河市');
insert into web_cities (city_id,province_id,city) values (4112,41,'三门峡市');
insert into web_cities (city_id,province_id,city) values (4113,41,'南阳市');
insert into web_cities (city_id,province_id,city) values (4114,41,'商丘市');
insert into web_cities (city_id,province_id,city) values (4115,41,'信阳市');
insert into web_cities (city_id,province_id,city) values (4116,41,'周口市');
insert into web_cities (city_id,province_id,city) values (4117,41,'驻马店市');
insert into web_cities (city_id,province_id,city) values (4190,41,'直辖县级');
insert into web_cities (city_id,province_id,city) values (4201,42,'武汉市');
insert into web_cities (city_id,province_id,city) values (4202,42,'黄石市');
insert into web_cities (city_id,province_id,city) values (4203,42,'十堰市');
insert into web_cities (city_id,province_id,city) values (4205,42,'宜昌市');
insert into web_cities (city_id,province_id,city) values (4206,42,'襄阳市');
insert into web_cities (city_id,province_id,city) values (4207,42,'鄂州市');
insert into web_cities (city_id,province_id,city) values (4208,42,'荆门市');
insert into web_cities (city_id,province_id,city) values (4209,42,'孝感市');
insert into web_cities (city_id,province_id,city) values (4210,42,'荆州市');
insert into web_cities (city_id,province_id,city) values (4211,42,'黄冈市');
insert into web_cities (city_id,province_id,city) values (4212,42,'咸宁市');
insert into web_cities (city_id,province_id,city) values (4213,42,'随州市');
insert into web_cities (city_id,province_id,city) values (4228,42,'恩施土家族苗族自治州');
insert into web_cities (city_id,province_id,city) values (4290,42,'直辖县级');
insert into web_cities (city_id,province_id,city) values (4301,43,'长沙市');
insert into web_cities (city_id,province_id,city) values (4302,43,'株洲市');
insert into web_cities (city_id,province_id,city) values (4303,43,'湘潭市');
insert into web_cities (city_id,province_id,city) values (4304,43,'衡阳市');
insert into web_cities (city_id,province_id,city) values (4305,43,'邵阳市');
insert into web_cities (city_id,province_id,city) values (4306,43,'岳阳市');
insert into web_cities (city_id,province_id,city) values (4307,43,'常德市');
insert into web_cities (city_id,province_id,city) values (4308,43,'张家界市');
insert into web_cities (city_id,province_id,city) values (4309,43,'益阳市');
insert into web_cities (city_id,province_id,city) values (4310,43,'郴州市');
insert into web_cities (city_id,province_id,city) values (4311,43,'永州市');
insert into web_cities (city_id,province_id,city) values (4312,43,'怀化市');
insert into web_cities (city_id,province_id,city) values (4313,43,'娄底市');
insert into web_cities (city_id,province_id,city) values (4331,43,'湘西土家族苗族自治州');
insert into web_cities (city_id,province_id,city) values (4401,44,'广州市');
insert into web_cities (city_id,province_id,city) values (4402,44,'韶关市');
insert into web_cities (city_id,province_id,city) values (4403,44,'深圳市');
insert into web_cities (city_id,province_id,city) values (4404,44,'珠海市');
insert into web_cities (city_id,province_id,city) values (4405,44,'汕头市');
insert into web_cities (city_id,province_id,city) values (4406,44,'佛山市');
insert into web_cities (city_id,province_id,city) values (4407,44,'江门市');
insert into web_cities (city_id,province_id,city) values (4408,44,'湛江市');
insert into web_cities (city_id,province_id,city) values (4409,44,'茂名市');
insert into web_cities (city_id,province_id,city) values (4412,44,'肇庆市');
insert into web_cities (city_id,province_id,city) values (4413,44,'惠州市');
insert into web_cities (city_id,province_id,city) values (4414,44,'梅州市');
insert into web_cities (city_id,province_id,city) values (4415,44,'汕尾市');
insert into web_cities (city_id,province_id,city) values (4416,44,'河源市');
insert into web_cities (city_id,province_id,city) values (4417,44,'阳江市');
insert into web_cities (city_id,province_id,city) values (4418,44,'清远市');
insert into web_cities (city_id,province_id,city) values (4419,44,'东莞市');
insert into web_cities (city_id,province_id,city) values (4420,44,'中山市');
insert into web_cities (city_id,province_id,city) values (4451,44,'潮州市');
insert into web_cities (city_id,province_id,city) values (4452,44,'揭阳市');
insert into web_cities (city_id,province_id,city) values (4453,44,'云浮市');
insert into web_cities (city_id,province_id,city) values (4501,45,'南宁市');
insert into web_cities (city_id,province_id,city) values (4502,45,'柳州市');
insert into web_cities (city_id,province_id,city) values (4503,45,'桂林市');
insert into web_cities (city_id,province_id,city) values (4504,45,'梧州市');
insert into web_cities (city_id,province_id,city) values (4505,45,'北海市');
insert into web_cities (city_id,province_id,city) values (4506,45,'防城港市');
insert into web_cities (city_id,province_id,city) values (4507,45,'钦州市');
insert into web_cities (city_id,province_id,city) values (4508,45,'贵港市');
insert into web_cities (city_id,province_id,city) values (4509,45,'玉林市');
insert into web_cities (city_id,province_id,city) values (4510,45,'百色市');
insert into web_cities (city_id,province_id,city) values (4511,45,'贺州市');
insert into web_cities (city_id,province_id,city) values (4512,45,'河池市');
insert into web_cities (city_id,province_id,city) values (4513,45,'来宾市');
insert into web_cities (city_id,province_id,city) values (4514,45,'崇左市');
insert into web_cities (city_id,province_id,city) values (4601,46,'海口市');
insert into web_cities (city_id,province_id,city) values (4602,46,'三亚市');
insert into web_cities (city_id,province_id,city) values (4603,46,'三沙市');
insert into web_cities (city_id,province_id,city) values (4690,46,'直辖县级');
insert into web_cities (city_id,province_id,city) values (5001,50,'重庆市');
insert into web_cities (city_id,province_id,city) values (5003,50,'两江新区');
insert into web_cities (city_id,province_id,city) values (5101,51,'成都市');
insert into web_cities (city_id,province_id,city) values (5103,51,'自贡市');
insert into web_cities (city_id,province_id,city) values (5104,51,'攀枝花市');
insert into web_cities (city_id,province_id,city) values (5105,51,'泸州市');
insert into web_cities (city_id,province_id,city) values (5106,51,'德阳市');
insert into web_cities (city_id,province_id,city) values (5107,51,'绵阳市');
insert into web_cities (city_id,province_id,city) values (5108,51,'广元市');
insert into web_cities (city_id,province_id,city) values (5109,51,'遂宁市');
insert into web_cities (city_id,province_id,city) values (5110,51,'内江市');
insert into web_cities (city_id,province_id,city) values (5111,51,'乐山市');
insert into web_cities (city_id,province_id,city) values (5113,51,'南充市');
insert into web_cities (city_id,province_id,city) values (5114,51,'眉山市');
insert into web_cities (city_id,province_id,city) values (5115,51,'宜宾市');
insert into web_cities (city_id,province_id,city) values (5116,51,'广安市');
insert into web_cities (city_id,province_id,city) values (5117,51,'达州市');
insert into web_cities (city_id,province_id,city) values (5118,51,'雅安市');
insert into web_cities (city_id,province_id,city) values (5119,51,'巴中市');
insert into web_cities (city_id,province_id,city) values (5120,51,'资阳市');
insert into web_cities (city_id,province_id,city) values (5132,51,'阿坝藏族羌族自治州');
insert into web_cities (city_id,province_id,city) values (5133,51,'甘孜藏族自治州');
insert into web_cities (city_id,province_id,city) values (5134,51,'凉山彝族自治州');
insert into web_cities (city_id,province_id,city) values (5201,52,'贵阳市');
insert into web_cities (city_id,province_id,city) values (5202,52,'六盘水市');
insert into web_cities (city_id,province_id,city) values (5203,52,'遵义市');
insert into web_cities (city_id,province_id,city) values (5204,52,'安顺市');
insert into web_cities (city_id,province_id,city) values (5205,52,'毕节市');
insert into web_cities (city_id,province_id,city) values (5206,52,'铜仁市');
insert into web_cities (city_id,province_id,city) values (5223,52,'黔西南布依族苗族自治州');
insert into web_cities (city_id,province_id,city) values (5226,52,'黔东南苗族侗族自治州');
insert into web_cities (city_id,province_id,city) values (5227,52,'黔南布依族苗族自治州');
insert into web_cities (city_id,province_id,city) values (5301,53,'昆明市');
insert into web_cities (city_id,province_id,city) values (5303,53,'曲靖市');
insert into web_cities (city_id,province_id,city) values (5304,53,'玉溪市');
insert into web_cities (city_id,province_id,city) values (5305,53,'保山市');
insert into web_cities (city_id,province_id,city) values (5306,53,'昭通市');
insert into web_cities (city_id,province_id,city) values (5307,53,'丽江市');
insert into web_cities (city_id,province_id,city) values (5308,53,'普洱市');
insert into web_cities (city_id,province_id,city) values (5309,53,'临沧市');
insert into web_cities (city_id,province_id,city) values (5323,53,'楚雄彝族自治州');
insert into web_cities (city_id,province_id,city) values (5325,53,'红河哈尼族彝族自治州');
insert into web_cities (city_id,province_id,city) values (5326,53,'文山壮族苗族自治州');
insert into web_cities (city_id,province_id,city) values (5328,53,'西双版纳傣族自治州');
insert into web_cities (city_id,province_id,city) values (5329,53,'大理白族自治州');
insert into web_cities (city_id,province_id,city) values (5331,53,'德宏傣族景颇族自治州');
insert into web_cities (city_id,province_id,city) values (5333,53,'怒江傈僳族自治州');
insert into web_cities (city_id,province_id,city) values (5334,53,'迪庆藏族自治州');
insert into web_cities (city_id,province_id,city) values (5401,54,'拉萨市');
insert into web_cities (city_id,province_id,city) values (5402,54,'日喀则市');
insert into web_cities (city_id,province_id,city) values (5403,54,'昌都市');
insert into web_cities (city_id,province_id,city) values (5422,54,'山南地区');
insert into web_cities (city_id,province_id,city) values (5424,54,'那曲地区');
insert into web_cities (city_id,province_id,city) values (5425,54,'阿里地区');
insert into web_cities (city_id,province_id,city) values (5426,54,'林芝地区');
insert into web_cities (city_id,province_id,city) values (6101,61,'西安市');
insert into web_cities (city_id,province_id,city) values (6102,61,'铜川市');
insert into web_cities (city_id,province_id,city) values (6103,61,'宝鸡市');
insert into web_cities (city_id,province_id,city) values (6104,61,'咸阳市');
insert into web_cities (city_id,province_id,city) values (6105,61,'渭南市');
insert into web_cities (city_id,province_id,city) values (6106,61,'延安市');
insert into web_cities (city_id,province_id,city) values (6107,61,'汉中市');
insert into web_cities (city_id,province_id,city) values (6108,61,'榆林市');
insert into web_cities (city_id,province_id,city) values (6109,61,'安康市');
insert into web_cities (city_id,province_id,city) values (6110,61,'商洛市');
insert into web_cities (city_id,province_id,city) values (6111,61,'西咸新区');
insert into web_cities (city_id,province_id,city) values (6201,62,'兰州市');
insert into web_cities (city_id,province_id,city) values (6202,62,'嘉峪关市');
insert into web_cities (city_id,province_id,city) values (6203,62,'金昌市');
insert into web_cities (city_id,province_id,city) values (6204,62,'白银市');
insert into web_cities (city_id,province_id,city) values (6205,62,'天水市');
insert into web_cities (city_id,province_id,city) values (6206,62,'武威市');
insert into web_cities (city_id,province_id,city) values (6207,62,'张掖市');
insert into web_cities (city_id,province_id,city) values (6208,62,'平凉市');
insert into web_cities (city_id,province_id,city) values (6209,62,'酒泉市');
insert into web_cities (city_id,province_id,city) values (6210,62,'庆阳市');
insert into web_cities (city_id,province_id,city) values (6211,62,'定西市');
insert into web_cities (city_id,province_id,city) values (6212,62,'陇南市');
insert into web_cities (city_id,province_id,city) values (6229,62,'临夏回族自治州');
insert into web_cities (city_id,province_id,city) values (6230,62,'甘南藏族自治州');
insert into web_cities (city_id,province_id,city) values (6301,63,'西宁市');
insert into web_cities (city_id,province_id,city) values (6302,63,'海东市');
insert into web_cities (city_id,province_id,city) values (6322,63,'海北藏族自治州');
insert into web_cities (city_id,province_id,city) values (6323,63,'黄南藏族自治州');
insert into web_cities (city_id,province_id,city) values (6325,63,'海南藏族自治州');
insert into web_cities (city_id,province_id,city) values (6326,63,'果洛藏族自治州');
insert into web_cities (city_id,province_id,city) values (6327,63,'玉树藏族自治州');
insert into web_cities (city_id,province_id,city) values (6328,63,'海西蒙古族藏族自治州');
insert into web_cities (city_id,province_id,city) values (6401,64,'银川市');
insert into web_cities (city_id,province_id,city) values (6402,64,'石嘴山市');
insert into web_cities (city_id,province_id,city) values (6403,64,'吴忠市');
insert into web_cities (city_id,province_id,city) values (6404,64,'固原市');
insert into web_cities (city_id,province_id,city) values (6405,64,'中卫市');
insert into web_cities (city_id,province_id,city) values (6501,65,'乌鲁木齐市');
insert into web_cities (city_id,province_id,city) values (6502,65,'克拉玛依市');
insert into web_cities (city_id,province_id,city) values (6521,65,'吐鲁番地区');
insert into web_cities (city_id,province_id,city) values (6522,65,'哈密地区');
insert into web_cities (city_id,province_id,city) values (6523,65,'昌吉回族自治州');
insert into web_cities (city_id,province_id,city) values (6527,65,'博尔塔拉蒙古自治州');
insert into web_cities (city_id,province_id,city) values (6528,65,'巴音郭楞蒙古自治州');
insert into web_cities (city_id,province_id,city) values (6529,65,'阿克苏地区');
insert into web_cities (city_id,province_id,city) values (6530,65,'克孜勒苏柯尔克孜自治州');
insert into web_cities (city_id,province_id,city) values (6531,65,'喀什地区');
insert into web_cities (city_id,province_id,city) values (6532,65,'和田地区');
insert into web_cities (city_id,province_id,city) values (6540,65,'伊犁哈萨克自治州');
insert into web_cities (city_id,province_id,city) values (6542,65,'塔城地区');
insert into web_cities (city_id,province_id,city) values (6543,65,'阿勒泰地区');
insert into web_cities (city_id,province_id,city) values (6590,65,'直辖县级');
insert into web_cities (city_id,province_id,city) values (7101,71,'台北市');
insert into web_cities (city_id,province_id,city) values (7102,71,'高雄市');
insert into web_cities (city_id,province_id,city) values (7103,71,'基隆市');
insert into web_cities (city_id,province_id,city) values (7104,71,'台中市');
insert into web_cities (city_id,province_id,city) values (7105,71,'台南市');
insert into web_cities (city_id,province_id,city) values (7106,71,'新竹市');
insert into web_cities (city_id,province_id,city) values (7107,71,'嘉义市');
insert into web_cities (city_id,province_id,city) values (7108,71,'新北市');
insert into web_cities (city_id,province_id,city) values (7122,71,'宜兰县');
insert into web_cities (city_id,province_id,city) values (7123,71,'桃园县');
insert into web_cities (city_id,province_id,city) values (7124,71,'新竹县');
insert into web_cities (city_id,province_id,city) values (7125,71,'苗栗县');
insert into web_cities (city_id,province_id,city) values (7127,71,'彰化县');
insert into web_cities (city_id,province_id,city) values (7128,71,'南投县');
insert into web_cities (city_id,province_id,city) values (7129,71,'云林县');
insert into web_cities (city_id,province_id,city) values (7130,71,'嘉义县');
insert into web_cities (city_id,province_id,city) values (7133,71,'屏东县');
insert into web_cities (city_id,province_id,city) values (7134,71,'台东县');
insert into web_cities (city_id,province_id,city) values (7135,71,'花莲县');
insert into web_cities (city_id,province_id,city) values (7136,71,'澎湖县');
insert into web_cities (city_id,province_id,city) values (7137,71,'金门县');
insert into web_cities (city_id,province_id,city) values (7138,71,'连江县');
insert into web_cities (city_id,province_id,city) values (8101,81,'香港岛');
insert into web_cities (city_id,province_id,city) values (8102,81,'九龙');
insert into web_cities (city_id,province_id,city) values (8103,81,'新界');
insert into web_cities (city_id,province_id,city) values (8201,82,'澳门半岛');
insert into web_cities (city_id,province_id,city) values (8202,82,'氹仔岛');
insert into web_cities (city_id,province_id,city) values (8203,82,'路环岛');

/*web_regions初始数据*/
insert into web_regions (region_id,city_id,region) values (110101,1101,'东城区');
insert into web_regions (region_id,city_id,region) values (110102,1101,'西城区');
insert into web_regions (region_id,city_id,region) values (110105,1101,'朝阳区');
insert into web_regions (region_id,city_id,region) values (110106,1101,'丰台区');
insert into web_regions (region_id,city_id,region) values (110107,1101,'石景山区');
insert into web_regions (region_id,city_id,region) values (110108,1101,'海淀区');
insert into web_regions (region_id,city_id,region) values (110109,1101,'门头沟区');
insert into web_regions (region_id,city_id,region) values (110111,1101,'房山区');
insert into web_regions (region_id,city_id,region) values (110112,1101,'通州区');
insert into web_regions (region_id,city_id,region) values (110113,1101,'顺义区');
insert into web_regions (region_id,city_id,region) values (110114,1101,'昌平区');
insert into web_regions (region_id,city_id,region) values (110115,1101,'大兴区');
insert into web_regions (region_id,city_id,region) values (110116,1101,'怀柔区');
insert into web_regions (region_id,city_id,region) values (110117,1101,'平谷区');
insert into web_regions (region_id,city_id,region) values (110228,1101,'密云县');
insert into web_regions (region_id,city_id,region) values (110229,1101,'延庆县');
insert into web_regions (region_id,city_id,region) values (120101,1201,'和平区');
insert into web_regions (region_id,city_id,region) values (120102,1201,'河东区');
insert into web_regions (region_id,city_id,region) values (120103,1201,'河西区');
insert into web_regions (region_id,city_id,region) values (120104,1201,'南开区');
insert into web_regions (region_id,city_id,region) values (120105,1201,'河北区');
insert into web_regions (region_id,city_id,region) values (120106,1201,'红桥区');
insert into web_regions (region_id,city_id,region) values (120110,1201,'东丽区');
insert into web_regions (region_id,city_id,region) values (120111,1201,'西青区');
insert into web_regions (region_id,city_id,region) values (120112,1201,'津南区');
insert into web_regions (region_id,city_id,region) values (120113,1201,'北辰区');
insert into web_regions (region_id,city_id,region) values (120114,1201,'武清区');
insert into web_regions (region_id,city_id,region) values (120115,1201,'宝坻区');
insert into web_regions (region_id,city_id,region) values (120116,1201,'滨海新区');
insert into web_regions (region_id,city_id,region) values (120221,1201,'宁河县');
insert into web_regions (region_id,city_id,region) values (120223,1201,'静海县');
insert into web_regions (region_id,city_id,region) values (120225,1201,'蓟县');
insert into web_regions (region_id,city_id,region) values (130102,1301,'长安区');
insert into web_regions (region_id,city_id,region) values (130104,1301,'桥西区');
insert into web_regions (region_id,city_id,region) values (130105,1301,'新华区');
insert into web_regions (region_id,city_id,region) values (130107,1301,'井陉矿区');
insert into web_regions (region_id,city_id,region) values (130108,1301,'裕华区');
insert into web_regions (region_id,city_id,region) values (130109,1301,'藁城区');
insert into web_regions (region_id,city_id,region) values (130110,1301,'鹿泉区');
insert into web_regions (region_id,city_id,region) values (130111,1301,'栾城区');
insert into web_regions (region_id,city_id,region) values (130121,1301,'井陉县');
insert into web_regions (region_id,city_id,region) values (130123,1301,'正定县');
insert into web_regions (region_id,city_id,region) values (130125,1301,'行唐县');
insert into web_regions (region_id,city_id,region) values (130126,1301,'灵寿县');
insert into web_regions (region_id,city_id,region) values (130127,1301,'高邑县');
insert into web_regions (region_id,city_id,region) values (130128,1301,'深泽县');
insert into web_regions (region_id,city_id,region) values (130129,1301,'赞皇县');
insert into web_regions (region_id,city_id,region) values (130130,1301,'无极县');
insert into web_regions (region_id,city_id,region) values (130131,1301,'平山县');
insert into web_regions (region_id,city_id,region) values (130132,1301,'元氏县');
insert into web_regions (region_id,city_id,region) values (130133,1301,'赵县');
insert into web_regions (region_id,city_id,region) values (130181,1301,'辛集市');
insert into web_regions (region_id,city_id,region) values (130183,1301,'晋州市');
insert into web_regions (region_id,city_id,region) values (130184,1301,'新乐市');
insert into web_regions (region_id,city_id,region) values (130202,1302,'路南区');
insert into web_regions (region_id,city_id,region) values (130203,1302,'路北区');
insert into web_regions (region_id,city_id,region) values (130204,1302,'古冶区');
insert into web_regions (region_id,city_id,region) values (130205,1302,'开平区');
insert into web_regions (region_id,city_id,region) values (130207,1302,'丰南区');
insert into web_regions (region_id,city_id,region) values (130208,1302,'丰润区');
insert into web_regions (region_id,city_id,region) values (130209,1302,'曹妃甸区');
insert into web_regions (region_id,city_id,region) values (130223,1302,'滦县');
insert into web_regions (region_id,city_id,region) values (130224,1302,'滦南县');
insert into web_regions (region_id,city_id,region) values (130225,1302,'乐亭县');
insert into web_regions (region_id,city_id,region) values (130227,1302,'迁西县');
insert into web_regions (region_id,city_id,region) values (130229,1302,'玉田县');
insert into web_regions (region_id,city_id,region) values (130281,1302,'遵化市');
insert into web_regions (region_id,city_id,region) values (130283,1302,'迁安市');
insert into web_regions (region_id,city_id,region) values (130302,1303,'海港区');
insert into web_regions (region_id,city_id,region) values (130303,1303,'山海关区');
insert into web_regions (region_id,city_id,region) values (130304,1303,'北戴河区');
insert into web_regions (region_id,city_id,region) values (130321,1303,'青龙满族自治县');
insert into web_regions (region_id,city_id,region) values (130322,1303,'昌黎县');
insert into web_regions (region_id,city_id,region) values (130323,1303,'抚宁县');
insert into web_regions (region_id,city_id,region) values (130324,1303,'卢龙县');
insert into web_regions (region_id,city_id,region) values (130402,1304,'邯山区');
insert into web_regions (region_id,city_id,region) values (130403,1304,'丛台区');
insert into web_regions (region_id,city_id,region) values (130404,1304,'复兴区');
insert into web_regions (region_id,city_id,region) values (130406,1304,'峰峰矿区');
insert into web_regions (region_id,city_id,region) values (130421,1304,'邯郸县');
insert into web_regions (region_id,city_id,region) values (130423,1304,'临漳县');
insert into web_regions (region_id,city_id,region) values (130424,1304,'成安县');
insert into web_regions (region_id,city_id,region) values (130425,1304,'大名县');
insert into web_regions (region_id,city_id,region) values (130426,1304,'涉县');
insert into web_regions (region_id,city_id,region) values (130427,1304,'磁县');
insert into web_regions (region_id,city_id,region) values (130428,1304,'肥乡县');
insert into web_regions (region_id,city_id,region) values (130429,1304,'永年县');
insert into web_regions (region_id,city_id,region) values (130430,1304,'邱县');
insert into web_regions (region_id,city_id,region) values (130431,1304,'鸡泽县');
insert into web_regions (region_id,city_id,region) values (130432,1304,'广平县');
insert into web_regions (region_id,city_id,region) values (130433,1304,'馆陶县');
insert into web_regions (region_id,city_id,region) values (130434,1304,'魏县');
insert into web_regions (region_id,city_id,region) values (130435,1304,'曲周县');
insert into web_regions (region_id,city_id,region) values (130481,1304,'武安市');
insert into web_regions (region_id,city_id,region) values (130502,1305,'桥东区');
insert into web_regions (region_id,city_id,region) values (130503,1305,'桥西区');
insert into web_regions (region_id,city_id,region) values (130521,1305,'邢台县');
insert into web_regions (region_id,city_id,region) values (130522,1305,'临城县');
insert into web_regions (region_id,city_id,region) values (130523,1305,'内丘县');
insert into web_regions (region_id,city_id,region) values (130524,1305,'柏乡县');
insert into web_regions (region_id,city_id,region) values (130525,1305,'隆尧县');
insert into web_regions (region_id,city_id,region) values (130526,1305,'任县');
insert into web_regions (region_id,city_id,region) values (130527,1305,'南和县');
insert into web_regions (region_id,city_id,region) values (130528,1305,'宁晋县');
insert into web_regions (region_id,city_id,region) values (130529,1305,'巨鹿县');
insert into web_regions (region_id,city_id,region) values (130530,1305,'新河县');
insert into web_regions (region_id,city_id,region) values (130531,1305,'广宗县');
insert into web_regions (region_id,city_id,region) values (130532,1305,'平乡县');
insert into web_regions (region_id,city_id,region) values (130533,1305,'威县');
insert into web_regions (region_id,city_id,region) values (130534,1305,'清河县');
insert into web_regions (region_id,city_id,region) values (130535,1305,'临西县');
insert into web_regions (region_id,city_id,region) values (130581,1305,'南宫市');
insert into web_regions (region_id,city_id,region) values (130582,1305,'沙河市');
insert into web_regions (region_id,city_id,region) values (130602,1306,'新市区');
insert into web_regions (region_id,city_id,region) values (130603,1306,'北市区');
insert into web_regions (region_id,city_id,region) values (130604,1306,'南市区');
insert into web_regions (region_id,city_id,region) values (130621,1306,'满城县');
insert into web_regions (region_id,city_id,region) values (130622,1306,'清苑县');
insert into web_regions (region_id,city_id,region) values (130623,1306,'涞水县');
insert into web_regions (region_id,city_id,region) values (130624,1306,'阜平县');
insert into web_regions (region_id,city_id,region) values (130625,1306,'徐水县');
insert into web_regions (region_id,city_id,region) values (130626,1306,'定兴县');
insert into web_regions (region_id,city_id,region) values (130627,1306,'唐县');
insert into web_regions (region_id,city_id,region) values (130628,1306,'高阳县');
insert into web_regions (region_id,city_id,region) values (130629,1306,'容城县');
insert into web_regions (region_id,city_id,region) values (130630,1306,'涞源县');
insert into web_regions (region_id,city_id,region) values (130631,1306,'望都县');
insert into web_regions (region_id,city_id,region) values (130632,1306,'安新县');
insert into web_regions (region_id,city_id,region) values (130633,1306,'易县');
insert into web_regions (region_id,city_id,region) values (130634,1306,'曲阳县');
insert into web_regions (region_id,city_id,region) values (130635,1306,'蠡县');
insert into web_regions (region_id,city_id,region) values (130636,1306,'顺平县');
insert into web_regions (region_id,city_id,region) values (130637,1306,'博野县');
insert into web_regions (region_id,city_id,region) values (130638,1306,'雄县');
insert into web_regions (region_id,city_id,region) values (130681,1306,'涿州市');
insert into web_regions (region_id,city_id,region) values (130682,1306,'定州市');
insert into web_regions (region_id,city_id,region) values (130683,1306,'安国市');
insert into web_regions (region_id,city_id,region) values (130684,1306,'高碑店市');
insert into web_regions (region_id,city_id,region) values (130702,1307,'桥东区');
insert into web_regions (region_id,city_id,region) values (130703,1307,'桥西区');
insert into web_regions (region_id,city_id,region) values (130705,1307,'宣化区');
insert into web_regions (region_id,city_id,region) values (130706,1307,'下花园区');
insert into web_regions (region_id,city_id,region) values (130721,1307,'宣化县');
insert into web_regions (region_id,city_id,region) values (130722,1307,'张北县');
insert into web_regions (region_id,city_id,region) values (130723,1307,'康保县');
insert into web_regions (region_id,city_id,region) values (130724,1307,'沽源县');
insert into web_regions (region_id,city_id,region) values (130725,1307,'尚义县');
insert into web_regions (region_id,city_id,region) values (130726,1307,'蔚县');
insert into web_regions (region_id,city_id,region) values (130727,1307,'阳原县');
insert into web_regions (region_id,city_id,region) values (130728,1307,'怀安县');
insert into web_regions (region_id,city_id,region) values (130729,1307,'万全县');
insert into web_regions (region_id,city_id,region) values (130730,1307,'怀来县');
insert into web_regions (region_id,city_id,region) values (130731,1307,'涿鹿县');
insert into web_regions (region_id,city_id,region) values (130732,1307,'赤城县');
insert into web_regions (region_id,city_id,region) values (130733,1307,'崇礼县');
insert into web_regions (region_id,city_id,region) values (130802,1308,'双桥区');
insert into web_regions (region_id,city_id,region) values (130803,1308,'双滦区');
insert into web_regions (region_id,city_id,region) values (130804,1308,'鹰手营子矿区');
insert into web_regions (region_id,city_id,region) values (130821,1308,'承德县');
insert into web_regions (region_id,city_id,region) values (130822,1308,'兴隆县');
insert into web_regions (region_id,city_id,region) values (130823,1308,'平泉县');
insert into web_regions (region_id,city_id,region) values (130824,1308,'滦平县');
insert into web_regions (region_id,city_id,region) values (130825,1308,'隆化县');
insert into web_regions (region_id,city_id,region) values (130826,1308,'丰宁满族自治县');
insert into web_regions (region_id,city_id,region) values (130827,1308,'宽城满族自治县');
insert into web_regions (region_id,city_id,region) values (130828,1308,'围场满族蒙古族自治县');
insert into web_regions (region_id,city_id,region) values (130902,1309,'新华区');
insert into web_regions (region_id,city_id,region) values (130903,1309,'运河区');
insert into web_regions (region_id,city_id,region) values (130921,1309,'沧县');
insert into web_regions (region_id,city_id,region) values (130922,1309,'青县');
insert into web_regions (region_id,city_id,region) values (130923,1309,'东光县');
insert into web_regions (region_id,city_id,region) values (130924,1309,'海兴县');
insert into web_regions (region_id,city_id,region) values (130925,1309,'盐山县');
insert into web_regions (region_id,city_id,region) values (130926,1309,'肃宁县');
insert into web_regions (region_id,city_id,region) values (130927,1309,'南皮县');
insert into web_regions (region_id,city_id,region) values (130928,1309,'吴桥县');
insert into web_regions (region_id,city_id,region) values (130929,1309,'献县');
insert into web_regions (region_id,city_id,region) values (130930,1309,'孟村回族自治县');
insert into web_regions (region_id,city_id,region) values (130981,1309,'泊头市');
insert into web_regions (region_id,city_id,region) values (130982,1309,'任丘市');
insert into web_regions (region_id,city_id,region) values (130983,1309,'黄骅市');
insert into web_regions (region_id,city_id,region) values (130984,1309,'河间市');
insert into web_regions (region_id,city_id,region) values (131002,1310,'安次区');
insert into web_regions (region_id,city_id,region) values (131003,1310,'广阳区');
insert into web_regions (region_id,city_id,region) values (131022,1310,'固安县');
insert into web_regions (region_id,city_id,region) values (131023,1310,'永清县');
insert into web_regions (region_id,city_id,region) values (131024,1310,'香河县');
insert into web_regions (region_id,city_id,region) values (131025,1310,'大城县');
insert into web_regions (region_id,city_id,region) values (131026,1310,'文安县');
insert into web_regions (region_id,city_id,region) values (131028,1310,'大厂回族自治县');
insert into web_regions (region_id,city_id,region) values (131081,1310,'霸州市');
insert into web_regions (region_id,city_id,region) values (131082,1310,'三河市');
insert into web_regions (region_id,city_id,region) values (131102,1311,'桃城区');
insert into web_regions (region_id,city_id,region) values (131121,1311,'枣强县');
insert into web_regions (region_id,city_id,region) values (131122,1311,'武邑县');
insert into web_regions (region_id,city_id,region) values (131123,1311,'武强县');
insert into web_regions (region_id,city_id,region) values (131124,1311,'饶阳县');
insert into web_regions (region_id,city_id,region) values (131125,1311,'安平县');
insert into web_regions (region_id,city_id,region) values (131126,1311,'故城县');
insert into web_regions (region_id,city_id,region) values (131127,1311,'景县');
insert into web_regions (region_id,city_id,region) values (131128,1311,'阜城县');
insert into web_regions (region_id,city_id,region) values (131181,1311,'冀州市');
insert into web_regions (region_id,city_id,region) values (131182,1311,'深州市');
insert into web_regions (region_id,city_id,region) values (140105,1401,'小店区');
insert into web_regions (region_id,city_id,region) values (140106,1401,'迎泽区');
insert into web_regions (region_id,city_id,region) values (140107,1401,'杏花岭区');
insert into web_regions (region_id,city_id,region) values (140108,1401,'尖草坪区');
insert into web_regions (region_id,city_id,region) values (140109,1401,'万柏林区');
insert into web_regions (region_id,city_id,region) values (140110,1401,'晋源区');
insert into web_regions (region_id,city_id,region) values (140121,1401,'清徐县');
insert into web_regions (region_id,city_id,region) values (140122,1401,'阳曲县');
insert into web_regions (region_id,city_id,region) values (140123,1401,'娄烦县');
insert into web_regions (region_id,city_id,region) values (140181,1401,'古交市');
insert into web_regions (region_id,city_id,region) values (140202,1402,'城区');
insert into web_regions (region_id,city_id,region) values (140203,1402,'矿区');
insert into web_regions (region_id,city_id,region) values (140211,1402,'南郊区');
insert into web_regions (region_id,city_id,region) values (140212,1402,'新荣区');
insert into web_regions (region_id,city_id,region) values (140221,1402,'阳高县');
insert into web_regions (region_id,city_id,region) values (140222,1402,'天镇县');
insert into web_regions (region_id,city_id,region) values (140223,1402,'广灵县');
insert into web_regions (region_id,city_id,region) values (140224,1402,'灵丘县');
insert into web_regions (region_id,city_id,region) values (140225,1402,'浑源县');
insert into web_regions (region_id,city_id,region) values (140226,1402,'左云县');
insert into web_regions (region_id,city_id,region) values (140227,1402,'大同县');
insert into web_regions (region_id,city_id,region) values (140302,1403,'城区');
insert into web_regions (region_id,city_id,region) values (140303,1403,'矿区');
insert into web_regions (region_id,city_id,region) values (140311,1403,'郊区');
insert into web_regions (region_id,city_id,region) values (140321,1403,'平定县');
insert into web_regions (region_id,city_id,region) values (140322,1403,'盂县');
insert into web_regions (region_id,city_id,region) values (140402,1404,'城区');
insert into web_regions (region_id,city_id,region) values (140411,1404,'郊区');
insert into web_regions (region_id,city_id,region) values (140421,1404,'长治县');
insert into web_regions (region_id,city_id,region) values (140423,1404,'襄垣县');
insert into web_regions (region_id,city_id,region) values (140424,1404,'屯留县');
insert into web_regions (region_id,city_id,region) values (140425,1404,'平顺县');
insert into web_regions (region_id,city_id,region) values (140426,1404,'黎城县');
insert into web_regions (region_id,city_id,region) values (140427,1404,'壶关县');
insert into web_regions (region_id,city_id,region) values (140428,1404,'长子县');
insert into web_regions (region_id,city_id,region) values (140429,1404,'武乡县');
insert into web_regions (region_id,city_id,region) values (140430,1404,'沁县');
insert into web_regions (region_id,city_id,region) values (140431,1404,'沁源县');
insert into web_regions (region_id,city_id,region) values (140481,1404,'潞城市');
insert into web_regions (region_id,city_id,region) values (140502,1405,'城区');
insert into web_regions (region_id,city_id,region) values (140521,1405,'沁水县');
insert into web_regions (region_id,city_id,region) values (140522,1405,'阳城县');
insert into web_regions (region_id,city_id,region) values (140524,1405,'陵川县');
insert into web_regions (region_id,city_id,region) values (140525,1405,'泽州县');
insert into web_regions (region_id,city_id,region) values (140581,1405,'高平市');
insert into web_regions (region_id,city_id,region) values (140602,1406,'朔城区');
insert into web_regions (region_id,city_id,region) values (140603,1406,'平鲁区');
insert into web_regions (region_id,city_id,region) values (140621,1406,'山阴县');
insert into web_regions (region_id,city_id,region) values (140622,1406,'应县');
insert into web_regions (region_id,city_id,region) values (140623,1406,'右玉县');
insert into web_regions (region_id,city_id,region) values (140624,1406,'怀仁县');
insert into web_regions (region_id,city_id,region) values (140702,1407,'榆次区');
insert into web_regions (region_id,city_id,region) values (140721,1407,'榆社县');
insert into web_regions (region_id,city_id,region) values (140722,1407,'左权县');
insert into web_regions (region_id,city_id,region) values (140723,1407,'和顺县');
insert into web_regions (region_id,city_id,region) values (140724,1407,'昔阳县');
insert into web_regions (region_id,city_id,region) values (140725,1407,'寿阳县');
insert into web_regions (region_id,city_id,region) values (140726,1407,'太谷县');
insert into web_regions (region_id,city_id,region) values (140727,1407,'祁县');
insert into web_regions (region_id,city_id,region) values (140728,1407,'平遥县');
insert into web_regions (region_id,city_id,region) values (140729,1407,'灵石县');
insert into web_regions (region_id,city_id,region) values (140781,1407,'介休市');
insert into web_regions (region_id,city_id,region) values (140802,1408,'盐湖区');
insert into web_regions (region_id,city_id,region) values (140821,1408,'临猗县');
insert into web_regions (region_id,city_id,region) values (140822,1408,'万荣县');
insert into web_regions (region_id,city_id,region) values (140823,1408,'闻喜县');
insert into web_regions (region_id,city_id,region) values (140824,1408,'稷山县');
insert into web_regions (region_id,city_id,region) values (140825,1408,'新绛县');
insert into web_regions (region_id,city_id,region) values (140826,1408,'绛县');
insert into web_regions (region_id,city_id,region) values (140827,1408,'垣曲县');
insert into web_regions (region_id,city_id,region) values (140828,1408,'夏县');
insert into web_regions (region_id,city_id,region) values (140829,1408,'平陆县');
insert into web_regions (region_id,city_id,region) values (140830,1408,'芮城县');
insert into web_regions (region_id,city_id,region) values (140881,1408,'永济市');
insert into web_regions (region_id,city_id,region) values (140882,1408,'河津市');
insert into web_regions (region_id,city_id,region) values (140902,1409,'忻府区');
insert into web_regions (region_id,city_id,region) values (140921,1409,'定襄县');
insert into web_regions (region_id,city_id,region) values (140922,1409,'五台县');
insert into web_regions (region_id,city_id,region) values (140923,1409,'代县');
insert into web_regions (region_id,city_id,region) values (140924,1409,'繁峙县');
insert into web_regions (region_id,city_id,region) values (140925,1409,'宁武县');
insert into web_regions (region_id,city_id,region) values (140926,1409,'静乐县');
insert into web_regions (region_id,city_id,region) values (140927,1409,'神池县');
insert into web_regions (region_id,city_id,region) values (140928,1409,'五寨县');
insert into web_regions (region_id,city_id,region) values (140929,1409,'岢岚县');
insert into web_regions (region_id,city_id,region) values (140930,1409,'河曲县');
insert into web_regions (region_id,city_id,region) values (140931,1409,'保德县');
insert into web_regions (region_id,city_id,region) values (140932,1409,'偏关县');
insert into web_regions (region_id,city_id,region) values (140981,1409,'原平市');
insert into web_regions (region_id,city_id,region) values (141002,1410,'尧都区');
insert into web_regions (region_id,city_id,region) values (141021,1410,'曲沃县');
insert into web_regions (region_id,city_id,region) values (141022,1410,'翼城县');
insert into web_regions (region_id,city_id,region) values (141023,1410,'襄汾县');
insert into web_regions (region_id,city_id,region) values (141024,1410,'洪洞县');
insert into web_regions (region_id,city_id,region) values (141025,1410,'古县');
insert into web_regions (region_id,city_id,region) values (141026,1410,'安泽县');
insert into web_regions (region_id,city_id,region) values (141027,1410,'浮山县');
insert into web_regions (region_id,city_id,region) values (141028,1410,'吉县');
insert into web_regions (region_id,city_id,region) values (141029,1410,'乡宁县');
insert into web_regions (region_id,city_id,region) values (141030,1410,'大宁县');
insert into web_regions (region_id,city_id,region) values (141031,1410,'隰县');
insert into web_regions (region_id,city_id,region) values (141032,1410,'永和县');
insert into web_regions (region_id,city_id,region) values (141033,1410,'蒲县');
insert into web_regions (region_id,city_id,region) values (141034,1410,'汾西县');
insert into web_regions (region_id,city_id,region) values (141081,1410,'侯马市');
insert into web_regions (region_id,city_id,region) values (141082,1410,'霍州市');
insert into web_regions (region_id,city_id,region) values (141102,1411,'离石区');
insert into web_regions (region_id,city_id,region) values (141121,1411,'文水县');
insert into web_regions (region_id,city_id,region) values (141122,1411,'交城县');
insert into web_regions (region_id,city_id,region) values (141123,1411,'兴县');
insert into web_regions (region_id,city_id,region) values (141124,1411,'临县');
insert into web_regions (region_id,city_id,region) values (141125,1411,'柳林县');
insert into web_regions (region_id,city_id,region) values (141126,1411,'石楼县');
insert into web_regions (region_id,city_id,region) values (141127,1411,'岚县');
insert into web_regions (region_id,city_id,region) values (141128,1411,'方山县');
insert into web_regions (region_id,city_id,region) values (141129,1411,'中阳县');
insert into web_regions (region_id,city_id,region) values (141130,1411,'交口县');
insert into web_regions (region_id,city_id,region) values (141181,1411,'孝义市');
insert into web_regions (region_id,city_id,region) values (141182,1411,'汾阳市');
insert into web_regions (region_id,city_id,region) values (150102,1501,'新城区');
insert into web_regions (region_id,city_id,region) values (150103,1501,'回民区');
insert into web_regions (region_id,city_id,region) values (150104,1501,'玉泉区');
insert into web_regions (region_id,city_id,region) values (150105,1501,'赛罕区');
insert into web_regions (region_id,city_id,region) values (150121,1501,'土默特左旗');
insert into web_regions (region_id,city_id,region) values (150122,1501,'托克托县');
insert into web_regions (region_id,city_id,region) values (150123,1501,'和林格尔县');
insert into web_regions (region_id,city_id,region) values (150124,1501,'清水河县');
insert into web_regions (region_id,city_id,region) values (150125,1501,'武川县');
insert into web_regions (region_id,city_id,region) values (150202,1502,'东河区');
insert into web_regions (region_id,city_id,region) values (150203,1502,'昆都仑区');
insert into web_regions (region_id,city_id,region) values (150204,1502,'青山区');
insert into web_regions (region_id,city_id,region) values (150205,1502,'石拐区');
insert into web_regions (region_id,city_id,region) values (150206,1502,'白云鄂博矿区');
insert into web_regions (region_id,city_id,region) values (150207,1502,'九原区');
insert into web_regions (region_id,city_id,region) values (150221,1502,'土默特右旗');
insert into web_regions (region_id,city_id,region) values (150222,1502,'固阳县');
insert into web_regions (region_id,city_id,region) values (150223,1502,'达尔罕茂明安联合旗');
insert into web_regions (region_id,city_id,region) values (150302,1503,'海勃湾区');
insert into web_regions (region_id,city_id,region) values (150303,1503,'海南区');
insert into web_regions (region_id,city_id,region) values (150304,1503,'乌达区');
insert into web_regions (region_id,city_id,region) values (150402,1504,'红山区');
insert into web_regions (region_id,city_id,region) values (150403,1504,'元宝山区');
insert into web_regions (region_id,city_id,region) values (150404,1504,'松山区');
insert into web_regions (region_id,city_id,region) values (150421,1504,'阿鲁科尔沁旗');
insert into web_regions (region_id,city_id,region) values (150422,1504,'巴林左旗');
insert into web_regions (region_id,city_id,region) values (150423,1504,'巴林右旗');
insert into web_regions (region_id,city_id,region) values (150424,1504,'林西县');
insert into web_regions (region_id,city_id,region) values (150425,1504,'克什克腾旗');
insert into web_regions (region_id,city_id,region) values (150426,1504,'翁牛特旗');
insert into web_regions (region_id,city_id,region) values (150428,1504,'喀喇沁旗');
insert into web_regions (region_id,city_id,region) values (150429,1504,'宁城县');
insert into web_regions (region_id,city_id,region) values (150430,1504,'敖汉旗');
insert into web_regions (region_id,city_id,region) values (150502,1505,'科尔沁区');
insert into web_regions (region_id,city_id,region) values (150521,1505,'科尔沁左翼中旗');
insert into web_regions (region_id,city_id,region) values (150522,1505,'科尔沁左翼后旗');
insert into web_regions (region_id,city_id,region) values (150523,1505,'开鲁县');
insert into web_regions (region_id,city_id,region) values (150524,1505,'库伦旗');
insert into web_regions (region_id,city_id,region) values (150525,1505,'奈曼旗');
insert into web_regions (region_id,city_id,region) values (150526,1505,'扎鲁特旗');
insert into web_regions (region_id,city_id,region) values (150581,1505,'霍林郭勒市');
insert into web_regions (region_id,city_id,region) values (150602,1506,'东胜区');
insert into web_regions (region_id,city_id,region) values (150621,1506,'达拉特旗');
insert into web_regions (region_id,city_id,region) values (150622,1506,'准格尔旗');
insert into web_regions (region_id,city_id,region) values (150623,1506,'鄂托克前旗');
insert into web_regions (region_id,city_id,region) values (150624,1506,'鄂托克旗');
insert into web_regions (region_id,city_id,region) values (150625,1506,'杭锦旗');
insert into web_regions (region_id,city_id,region) values (150626,1506,'乌审旗');
insert into web_regions (region_id,city_id,region) values (150627,1506,'伊金霍洛旗');
insert into web_regions (region_id,city_id,region) values (150702,1507,'海拉尔区');
insert into web_regions (region_id,city_id,region) values (150703,1507,'扎赉诺尔区');
insert into web_regions (region_id,city_id,region) values (150721,1507,'阿荣旗');
insert into web_regions (region_id,city_id,region) values (150722,1507,'莫力达瓦达斡尔族自治旗');
insert into web_regions (region_id,city_id,region) values (150723,1507,'鄂伦春自治旗');
insert into web_regions (region_id,city_id,region) values (150724,1507,'鄂温克族自治旗');
insert into web_regions (region_id,city_id,region) values (150725,1507,'陈巴尔虎旗');
insert into web_regions (region_id,city_id,region) values (150726,1507,'新巴尔虎左旗');
insert into web_regions (region_id,city_id,region) values (150727,1507,'新巴尔虎右旗');
insert into web_regions (region_id,city_id,region) values (150781,1507,'满洲里市');
insert into web_regions (region_id,city_id,region) values (150782,1507,'牙克石市');
insert into web_regions (region_id,city_id,region) values (150783,1507,'扎兰屯市');
insert into web_regions (region_id,city_id,region) values (150784,1507,'额尔古纳市');
insert into web_regions (region_id,city_id,region) values (150785,1507,'根河市');
insert into web_regions (region_id,city_id,region) values (150802,1508,'临河区');
insert into web_regions (region_id,city_id,region) values (150821,1508,'五原县');
insert into web_regions (region_id,city_id,region) values (150822,1508,'磴口县');
insert into web_regions (region_id,city_id,region) values (150823,1508,'乌拉特前旗');
insert into web_regions (region_id,city_id,region) values (150824,1508,'乌拉特中旗');
insert into web_regions (region_id,city_id,region) values (150825,1508,'乌拉特后旗');
insert into web_regions (region_id,city_id,region) values (150826,1508,'杭锦后旗');
insert into web_regions (region_id,city_id,region) values (150902,1509,'集宁区');
insert into web_regions (region_id,city_id,region) values (150921,1509,'卓资县');
insert into web_regions (region_id,city_id,region) values (150922,1509,'化德县');
insert into web_regions (region_id,city_id,region) values (150923,1509,'商都县');
insert into web_regions (region_id,city_id,region) values (150924,1509,'兴和县');
insert into web_regions (region_id,city_id,region) values (150925,1509,'凉城县');
insert into web_regions (region_id,city_id,region) values (150926,1509,'察哈尔右翼前旗');
insert into web_regions (region_id,city_id,region) values (150927,1509,'察哈尔右翼中旗');
insert into web_regions (region_id,city_id,region) values (150928,1509,'察哈尔右翼后旗');
insert into web_regions (region_id,city_id,region) values (150929,1509,'四子王旗');
insert into web_regions (region_id,city_id,region) values (150981,1509,'丰镇市');
insert into web_regions (region_id,city_id,region) values (152201,1522,'乌兰浩特市');
insert into web_regions (region_id,city_id,region) values (152202,1522,'阿尔山市');
insert into web_regions (region_id,city_id,region) values (152221,1522,'科尔沁右翼前旗');
insert into web_regions (region_id,city_id,region) values (152222,1522,'科尔沁右翼中旗');
insert into web_regions (region_id,city_id,region) values (152223,1522,'扎赉特旗');
insert into web_regions (region_id,city_id,region) values (152224,1522,'突泉县');
insert into web_regions (region_id,city_id,region) values (152501,1525,'二连浩特市');
insert into web_regions (region_id,city_id,region) values (152502,1525,'锡林浩特市');
insert into web_regions (region_id,city_id,region) values (152522,1525,'阿巴嘎旗');
insert into web_regions (region_id,city_id,region) values (152523,1525,'苏尼特左旗');
insert into web_regions (region_id,city_id,region) values (152524,1525,'苏尼特右旗');
insert into web_regions (region_id,city_id,region) values (152525,1525,'东乌珠穆沁旗');
insert into web_regions (region_id,city_id,region) values (152526,1525,'西乌珠穆沁旗');
insert into web_regions (region_id,city_id,region) values (152527,1525,'太仆寺旗');
insert into web_regions (region_id,city_id,region) values (152528,1525,'镶黄旗');
insert into web_regions (region_id,city_id,region) values (152529,1525,'正镶白旗');
insert into web_regions (region_id,city_id,region) values (152530,1525,'正蓝旗');
insert into web_regions (region_id,city_id,region) values (152531,1525,'多伦县');
insert into web_regions (region_id,city_id,region) values (152921,1529,'阿拉善左旗');
insert into web_regions (region_id,city_id,region) values (152922,1529,'阿拉善右旗');
insert into web_regions (region_id,city_id,region) values (152923,1529,'额济纳旗');
insert into web_regions (region_id,city_id,region) values (210102,2101,'和平区');
insert into web_regions (region_id,city_id,region) values (210103,2101,'沈河区');
insert into web_regions (region_id,city_id,region) values (210104,2101,'大东区');
insert into web_regions (region_id,city_id,region) values (210105,2101,'皇姑区');
insert into web_regions (region_id,city_id,region) values (210106,2101,'铁西区');
insert into web_regions (region_id,city_id,region) values (210111,2101,'苏家屯区');
insert into web_regions (region_id,city_id,region) values (210112,2101,'浑南区');
insert into web_regions (region_id,city_id,region) values (210113,2101,'沈北新区');
insert into web_regions (region_id,city_id,region) values (210114,2101,'于洪区');
insert into web_regions (region_id,city_id,region) values (210122,2101,'辽中县');
insert into web_regions (region_id,city_id,region) values (210123,2101,'康平县');
insert into web_regions (region_id,city_id,region) values (210124,2101,'法库县');
insert into web_regions (region_id,city_id,region) values (210181,2101,'新民市');
insert into web_regions (region_id,city_id,region) values (210202,2102,'中山区');
insert into web_regions (region_id,city_id,region) values (210203,2102,'西岗区');
insert into web_regions (region_id,city_id,region) values (210204,2102,'沙河口区');
insert into web_regions (region_id,city_id,region) values (210211,2102,'甘井子区');
insert into web_regions (region_id,city_id,region) values (210212,2102,'旅顺口区');
insert into web_regions (region_id,city_id,region) values (210213,2102,'金州区');
insert into web_regions (region_id,city_id,region) values (210224,2102,'长海县');
insert into web_regions (region_id,city_id,region) values (210281,2102,'瓦房店市');
insert into web_regions (region_id,city_id,region) values (210282,2102,'普兰店市');
insert into web_regions (region_id,city_id,region) values (210283,2102,'庄河市');
insert into web_regions (region_id,city_id,region) values (210302,2103,'铁东区');
insert into web_regions (region_id,city_id,region) values (210303,2103,'铁西区');
insert into web_regions (region_id,city_id,region) values (210304,2103,'立山区');
insert into web_regions (region_id,city_id,region) values (210311,2103,'千山区');
insert into web_regions (region_id,city_id,region) values (210321,2103,'台安县');
insert into web_regions (region_id,city_id,region) values (210323,2103,'岫岩满族自治县');
insert into web_regions (region_id,city_id,region) values (210381,2103,'海城市');
insert into web_regions (region_id,city_id,region) values (210402,2104,'新抚区');
insert into web_regions (region_id,city_id,region) values (210403,2104,'东洲区');
insert into web_regions (region_id,city_id,region) values (210404,2104,'望花区');
insert into web_regions (region_id,city_id,region) values (210411,2104,'顺城区');
insert into web_regions (region_id,city_id,region) values (210421,2104,'抚顺县');
insert into web_regions (region_id,city_id,region) values (210422,2104,'新宾满族自治县');
insert into web_regions (region_id,city_id,region) values (210423,2104,'清原满族自治县');
insert into web_regions (region_id,city_id,region) values (210502,2105,'平山区');
insert into web_regions (region_id,city_id,region) values (210503,2105,'溪湖区');
insert into web_regions (region_id,city_id,region) values (210504,2105,'明山区');
insert into web_regions (region_id,city_id,region) values (210505,2105,'南芬区');
insert into web_regions (region_id,city_id,region) values (210521,2105,'本溪满族自治县');
insert into web_regions (region_id,city_id,region) values (210522,2105,'桓仁满族自治县');
insert into web_regions (region_id,city_id,region) values (210602,2106,'元宝区');
insert into web_regions (region_id,city_id,region) values (210603,2106,'振兴区');
insert into web_regions (region_id,city_id,region) values (210604,2106,'振安区');
insert into web_regions (region_id,city_id,region) values (210624,2106,'宽甸满族自治县');
insert into web_regions (region_id,city_id,region) values (210681,2106,'东港市');
insert into web_regions (region_id,city_id,region) values (210682,2106,'凤城市');
insert into web_regions (region_id,city_id,region) values (210702,2107,'古塔区');
insert into web_regions (region_id,city_id,region) values (210703,2107,'凌河区');
insert into web_regions (region_id,city_id,region) values (210711,2107,'太和区');
insert into web_regions (region_id,city_id,region) values (210726,2107,'黑山县');
insert into web_regions (region_id,city_id,region) values (210727,2107,'义县');
insert into web_regions (region_id,city_id,region) values (210781,2107,'凌海市');
insert into web_regions (region_id,city_id,region) values (210782,2107,'北镇市');
insert into web_regions (region_id,city_id,region) values (210802,2108,'站前区');
insert into web_regions (region_id,city_id,region) values (210803,2108,'西市区');
insert into web_regions (region_id,city_id,region) values (210804,2108,'鲅鱼圈区');
insert into web_regions (region_id,city_id,region) values (210811,2108,'老边区');
insert into web_regions (region_id,city_id,region) values (210881,2108,'盖州市');
insert into web_regions (region_id,city_id,region) values (210882,2108,'大石桥市');
insert into web_regions (region_id,city_id,region) values (210902,2109,'海州区');
insert into web_regions (region_id,city_id,region) values (210903,2109,'新邱区');
insert into web_regions (region_id,city_id,region) values (210904,2109,'太平区');
insert into web_regions (region_id,city_id,region) values (210905,2109,'清河门区');
insert into web_regions (region_id,city_id,region) values (210911,2109,'细河区');
insert into web_regions (region_id,city_id,region) values (210921,2109,'阜新蒙古族自治县');
insert into web_regions (region_id,city_id,region) values (210922,2109,'彰武县');
insert into web_regions (region_id,city_id,region) values (211002,2110,'白塔区');
insert into web_regions (region_id,city_id,region) values (211003,2110,'文圣区');
insert into web_regions (region_id,city_id,region) values (211004,2110,'宏伟区');
insert into web_regions (region_id,city_id,region) values (211005,2110,'弓长岭区');
insert into web_regions (region_id,city_id,region) values (211011,2110,'太子河区');
insert into web_regions (region_id,city_id,region) values (211021,2110,'辽阳县');
insert into web_regions (region_id,city_id,region) values (211081,2110,'灯塔市');
insert into web_regions (region_id,city_id,region) values (211102,2111,'双台子区');
insert into web_regions (region_id,city_id,region) values (211103,2111,'兴隆台区');
insert into web_regions (region_id,city_id,region) values (211121,2111,'大洼县');
insert into web_regions (region_id,city_id,region) values (211122,2111,'盘山县');
insert into web_regions (region_id,city_id,region) values (211202,2112,'银州区');
insert into web_regions (region_id,city_id,region) values (211204,2112,'清河区');
insert into web_regions (region_id,city_id,region) values (211221,2112,'铁岭县');
insert into web_regions (region_id,city_id,region) values (211223,2112,'西丰县');
insert into web_regions (region_id,city_id,region) values (211224,2112,'昌图县');
insert into web_regions (region_id,city_id,region) values (211281,2112,'调兵山市');
insert into web_regions (region_id,city_id,region) values (211282,2112,'开原市');
insert into web_regions (region_id,city_id,region) values (211302,2113,'双塔区');
insert into web_regions (region_id,city_id,region) values (211303,2113,'龙城区');
insert into web_regions (region_id,city_id,region) values (211321,2113,'朝阳县');
insert into web_regions (region_id,city_id,region) values (211322,2113,'建平县');
insert into web_regions (region_id,city_id,region) values (211324,2113,'喀喇沁左翼蒙古族自治县');
insert into web_regions (region_id,city_id,region) values (211381,2113,'北票市');
insert into web_regions (region_id,city_id,region) values (211382,2113,'凌源市');
insert into web_regions (region_id,city_id,region) values (211402,2114,'连山区');
insert into web_regions (region_id,city_id,region) values (211403,2114,'龙港区');
insert into web_regions (region_id,city_id,region) values (211404,2114,'南票区');
insert into web_regions (region_id,city_id,region) values (211421,2114,'绥中县');
insert into web_regions (region_id,city_id,region) values (211422,2114,'建昌县');
insert into web_regions (region_id,city_id,region) values (211481,2114,'兴城市');
insert into web_regions (region_id,city_id,region) values (211501,2115,'金州新区');
insert into web_regions (region_id,city_id,region) values (211502,2115,'普湾新区');
insert into web_regions (region_id,city_id,region) values (211503,2115,'保税区');
insert into web_regions (region_id,city_id,region) values (220102,2201,'南关区');
insert into web_regions (region_id,city_id,region) values (220103,2201,'宽城区');
insert into web_regions (region_id,city_id,region) values (220104,2201,'朝阳区');
insert into web_regions (region_id,city_id,region) values (220105,2201,'二道区');
insert into web_regions (region_id,city_id,region) values (220106,2201,'绿园区');
insert into web_regions (region_id,city_id,region) values (220112,2201,'双阳区');
insert into web_regions (region_id,city_id,region) values (220113,2201,'九台区');
insert into web_regions (region_id,city_id,region) values (220122,2201,'农安县');
insert into web_regions (region_id,city_id,region) values (220182,2201,'榆树市');
insert into web_regions (region_id,city_id,region) values (220183,2201,'德惠市');
insert into web_regions (region_id,city_id,region) values (220202,2202,'昌邑区');
insert into web_regions (region_id,city_id,region) values (220203,2202,'龙潭区');
insert into web_regions (region_id,city_id,region) values (220204,2202,'船营区');
insert into web_regions (region_id,city_id,region) values (220211,2202,'丰满区');
insert into web_regions (region_id,city_id,region) values (220221,2202,'永吉县');
insert into web_regions (region_id,city_id,region) values (220281,2202,'蛟河市');
insert into web_regions (region_id,city_id,region) values (220282,2202,'桦甸市');
insert into web_regions (region_id,city_id,region) values (220283,2202,'舒兰市');
insert into web_regions (region_id,city_id,region) values (220284,2202,'磐石市');
insert into web_regions (region_id,city_id,region) values (220302,2203,'铁西区');
insert into web_regions (region_id,city_id,region) values (220303,2203,'铁东区');
insert into web_regions (region_id,city_id,region) values (220322,2203,'梨树县');
insert into web_regions (region_id,city_id,region) values (220323,2203,'伊通满族自治县');
insert into web_regions (region_id,city_id,region) values (220381,2203,'公主岭市');
insert into web_regions (region_id,city_id,region) values (220382,2203,'双辽市');
insert into web_regions (region_id,city_id,region) values (220402,2204,'龙山区');
insert into web_regions (region_id,city_id,region) values (220403,2204,'西安区');
insert into web_regions (region_id,city_id,region) values (220421,2204,'东丰县');
insert into web_regions (region_id,city_id,region) values (220422,2204,'东辽县');
insert into web_regions (region_id,city_id,region) values (220502,2205,'东昌区');
insert into web_regions (region_id,city_id,region) values (220503,2205,'二道江区');
insert into web_regions (region_id,city_id,region) values (220521,2205,'通化县');
insert into web_regions (region_id,city_id,region) values (220523,2205,'辉南县');
insert into web_regions (region_id,city_id,region) values (220524,2205,'柳河县');
insert into web_regions (region_id,city_id,region) values (220581,2205,'梅河口市');
insert into web_regions (region_id,city_id,region) values (220582,2205,'集安市');
insert into web_regions (region_id,city_id,region) values (220602,2206,'浑江区');
insert into web_regions (region_id,city_id,region) values (220605,2206,'江源区');
insert into web_regions (region_id,city_id,region) values (220621,2206,'抚松县');
insert into web_regions (region_id,city_id,region) values (220622,2206,'靖宇县');
insert into web_regions (region_id,city_id,region) values (220623,2206,'长白朝鲜族自治县');
insert into web_regions (region_id,city_id,region) values (220681,2206,'临江市');
insert into web_regions (region_id,city_id,region) values (220702,2207,'宁江区');
insert into web_regions (region_id,city_id,region) values (220721,2207,'前郭尔罗斯蒙古族自治县');
insert into web_regions (region_id,city_id,region) values (220722,2207,'长岭县');
insert into web_regions (region_id,city_id,region) values (220723,2207,'乾安县');
insert into web_regions (region_id,city_id,region) values (220781,2207,'扶余市');
insert into web_regions (region_id,city_id,region) values (220802,2208,'洮北区');
insert into web_regions (region_id,city_id,region) values (220821,2208,'镇赉县');
insert into web_regions (region_id,city_id,region) values (220822,2208,'通榆县');
insert into web_regions (region_id,city_id,region) values (220881,2208,'洮南市');
insert into web_regions (region_id,city_id,region) values (220882,2208,'大安市');
insert into web_regions (region_id,city_id,region) values (222401,2224,'延吉市');
insert into web_regions (region_id,city_id,region) values (222402,2224,'图们市');
insert into web_regions (region_id,city_id,region) values (222403,2224,'敦化市');
insert into web_regions (region_id,city_id,region) values (222404,2224,'珲春市');
insert into web_regions (region_id,city_id,region) values (222405,2224,'龙井市');
insert into web_regions (region_id,city_id,region) values (222406,2224,'和龙市');
insert into web_regions (region_id,city_id,region) values (222424,2224,'汪清县');
insert into web_regions (region_id,city_id,region) values (222426,2224,'安图县');
insert into web_regions (region_id,city_id,region) values (230102,2301,'道里区');
insert into web_regions (region_id,city_id,region) values (230103,2301,'南岗区');
insert into web_regions (region_id,city_id,region) values (230104,2301,'道外区');
insert into web_regions (region_id,city_id,region) values (230108,2301,'平房区');
insert into web_regions (region_id,city_id,region) values (230109,2301,'松北区');
insert into web_regions (region_id,city_id,region) values (230110,2301,'香坊区');
insert into web_regions (region_id,city_id,region) values (230111,2301,'呼兰区');
insert into web_regions (region_id,city_id,region) values (230112,2301,'阿城区');
insert into web_regions (region_id,city_id,region) values (230113,2301,'双城区');
insert into web_regions (region_id,city_id,region) values (230123,2301,'依兰县');
insert into web_regions (region_id,city_id,region) values (230124,2301,'方正县');
insert into web_regions (region_id,city_id,region) values (230125,2301,'宾县');
insert into web_regions (region_id,city_id,region) values (230126,2301,'巴彦县');
insert into web_regions (region_id,city_id,region) values (230127,2301,'木兰县');
insert into web_regions (region_id,city_id,region) values (230128,2301,'通河县');
insert into web_regions (region_id,city_id,region) values (230129,2301,'延寿县');
insert into web_regions (region_id,city_id,region) values (230183,2301,'尚志市');
insert into web_regions (region_id,city_id,region) values (230184,2301,'五常市');
insert into web_regions (region_id,city_id,region) values (230202,2302,'龙沙区');
insert into web_regions (region_id,city_id,region) values (230203,2302,'建华区');
insert into web_regions (region_id,city_id,region) values (230204,2302,'铁锋区');
insert into web_regions (region_id,city_id,region) values (230205,2302,'昂昂溪区');
insert into web_regions (region_id,city_id,region) values (230206,2302,'富拉尔基区');
insert into web_regions (region_id,city_id,region) values (230207,2302,'碾子山区');
insert into web_regions (region_id,city_id,region) values (230208,2302,'梅里斯达斡尔族区');
insert into web_regions (region_id,city_id,region) values (230221,2302,'龙江县');
insert into web_regions (region_id,city_id,region) values (230223,2302,'依安县');
insert into web_regions (region_id,city_id,region) values (230224,2302,'泰来县');
insert into web_regions (region_id,city_id,region) values (230225,2302,'甘南县');
insert into web_regions (region_id,city_id,region) values (230227,2302,'富裕县');
insert into web_regions (region_id,city_id,region) values (230229,2302,'克山县');
insert into web_regions (region_id,city_id,region) values (230230,2302,'克东县');
insert into web_regions (region_id,city_id,region) values (230231,2302,'拜泉县');
insert into web_regions (region_id,city_id,region) values (230281,2302,'讷河市');
insert into web_regions (region_id,city_id,region) values (230302,2303,'鸡冠区');
insert into web_regions (region_id,city_id,region) values (230303,2303,'恒山区');
insert into web_regions (region_id,city_id,region) values (230304,2303,'滴道区');
insert into web_regions (region_id,city_id,region) values (230305,2303,'梨树区');
insert into web_regions (region_id,city_id,region) values (230306,2303,'城子河区');
insert into web_regions (region_id,city_id,region) values (230307,2303,'麻山区');
insert into web_regions (region_id,city_id,region) values (230321,2303,'鸡东县');
insert into web_regions (region_id,city_id,region) values (230381,2303,'虎林市');
insert into web_regions (region_id,city_id,region) values (230382,2303,'密山市');
insert into web_regions (region_id,city_id,region) values (230402,2304,'向阳区');
insert into web_regions (region_id,city_id,region) values (230403,2304,'工农区');
insert into web_regions (region_id,city_id,region) values (230404,2304,'南山区');
insert into web_regions (region_id,city_id,region) values (230405,2304,'兴安区');
insert into web_regions (region_id,city_id,region) values (230406,2304,'东山区');
insert into web_regions (region_id,city_id,region) values (230407,2304,'兴山区');
insert into web_regions (region_id,city_id,region) values (230421,2304,'萝北县');
insert into web_regions (region_id,city_id,region) values (230422,2304,'绥滨县');
insert into web_regions (region_id,city_id,region) values (230502,2305,'尖山区');
insert into web_regions (region_id,city_id,region) values (230503,2305,'岭东区');
insert into web_regions (region_id,city_id,region) values (230505,2305,'四方台区');
insert into web_regions (region_id,city_id,region) values (230506,2305,'宝山区');
insert into web_regions (region_id,city_id,region) values (230521,2305,'集贤县');
insert into web_regions (region_id,city_id,region) values (230522,2305,'友谊县');
insert into web_regions (region_id,city_id,region) values (230523,2305,'宝清县');
insert into web_regions (region_id,city_id,region) values (230524,2305,'饶河县');
insert into web_regions (region_id,city_id,region) values (230602,2306,'萨尔图区');
insert into web_regions (region_id,city_id,region) values (230603,2306,'龙凤区');
insert into web_regions (region_id,city_id,region) values (230604,2306,'让胡路区');
insert into web_regions (region_id,city_id,region) values (230605,2306,'红岗区');
insert into web_regions (region_id,city_id,region) values (230606,2306,'大同区');
insert into web_regions (region_id,city_id,region) values (230621,2306,'肇州县');
insert into web_regions (region_id,city_id,region) values (230622,2306,'肇源县');
insert into web_regions (region_id,city_id,region) values (230623,2306,'林甸县');
insert into web_regions (region_id,city_id,region) values (230624,2306,'杜尔伯特蒙古族自治县');
insert into web_regions (region_id,city_id,region) values (230702,2307,'伊春区');
insert into web_regions (region_id,city_id,region) values (230703,2307,'南岔区');
insert into web_regions (region_id,city_id,region) values (230704,2307,'友好区');
insert into web_regions (region_id,city_id,region) values (230705,2307,'西林区');
insert into web_regions (region_id,city_id,region) values (230706,2307,'翠峦区');
insert into web_regions (region_id,city_id,region) values (230707,2307,'新青区');
insert into web_regions (region_id,city_id,region) values (230708,2307,'美溪区');
insert into web_regions (region_id,city_id,region) values (230709,2307,'金山屯区');
insert into web_regions (region_id,city_id,region) values (230710,2307,'五营区');
insert into web_regions (region_id,city_id,region) values (230711,2307,'乌马河区');
insert into web_regions (region_id,city_id,region) values (230712,2307,'汤旺河区');
insert into web_regions (region_id,city_id,region) values (230713,2307,'带岭区');
insert into web_regions (region_id,city_id,region) values (230714,2307,'乌伊岭区');
insert into web_regions (region_id,city_id,region) values (230715,2307,'红星区');
insert into web_regions (region_id,city_id,region) values (230716,2307,'上甘岭区');
insert into web_regions (region_id,city_id,region) values (230722,2307,'嘉荫县');
insert into web_regions (region_id,city_id,region) values (230781,2307,'铁力市');
insert into web_regions (region_id,city_id,region) values (230803,2308,'向阳区');
insert into web_regions (region_id,city_id,region) values (230804,2308,'前进区');
insert into web_regions (region_id,city_id,region) values (230805,2308,'东风区');
insert into web_regions (region_id,city_id,region) values (230811,2308,'郊区');
insert into web_regions (region_id,city_id,region) values (230822,2308,'桦南县');
insert into web_regions (region_id,city_id,region) values (230826,2308,'桦川县');
insert into web_regions (region_id,city_id,region) values (230828,2308,'汤原县');
insert into web_regions (region_id,city_id,region) values (230833,2308,'抚远县');
insert into web_regions (region_id,city_id,region) values (230881,2308,'同江市');
insert into web_regions (region_id,city_id,region) values (230882,2308,'富锦市');
insert into web_regions (region_id,city_id,region) values (230902,2309,'新兴区');
insert into web_regions (region_id,city_id,region) values (230903,2309,'桃山区');
insert into web_regions (region_id,city_id,region) values (230904,2309,'茄子河区');
insert into web_regions (region_id,city_id,region) values (230921,2309,'勃利县');
insert into web_regions (region_id,city_id,region) values (231002,2310,'东安区');
insert into web_regions (region_id,city_id,region) values (231003,2310,'阳明区');
insert into web_regions (region_id,city_id,region) values (231004,2310,'爱民区');
insert into web_regions (region_id,city_id,region) values (231005,2310,'西安区');
insert into web_regions (region_id,city_id,region) values (231024,2310,'东宁县');
insert into web_regions (region_id,city_id,region) values (231025,2310,'林口县');
insert into web_regions (region_id,city_id,region) values (231081,2310,'绥芬河市');
insert into web_regions (region_id,city_id,region) values (231083,2310,'海林市');
insert into web_regions (region_id,city_id,region) values (231084,2310,'宁安市');
insert into web_regions (region_id,city_id,region) values (231085,2310,'穆棱市');
insert into web_regions (region_id,city_id,region) values (231102,2311,'爱辉区');
insert into web_regions (region_id,city_id,region) values (231121,2311,'嫩江县');
insert into web_regions (region_id,city_id,region) values (231123,2311,'逊克县');
insert into web_regions (region_id,city_id,region) values (231124,2311,'孙吴县');
insert into web_regions (region_id,city_id,region) values (231181,2311,'北安市');
insert into web_regions (region_id,city_id,region) values (231182,2311,'五大连池市');
insert into web_regions (region_id,city_id,region) values (231202,2312,'北林区');
insert into web_regions (region_id,city_id,region) values (231221,2312,'望奎县');
insert into web_regions (region_id,city_id,region) values (231222,2312,'兰西县');
insert into web_regions (region_id,city_id,region) values (231223,2312,'青冈县');
insert into web_regions (region_id,city_id,region) values (231224,2312,'庆安县');
insert into web_regions (region_id,city_id,region) values (231225,2312,'明水县');
insert into web_regions (region_id,city_id,region) values (231226,2312,'绥棱县');
insert into web_regions (region_id,city_id,region) values (231281,2312,'安达市');
insert into web_regions (region_id,city_id,region) values (231282,2312,'肇东市');
insert into web_regions (region_id,city_id,region) values (231283,2312,'海伦市');
insert into web_regions (region_id,city_id,region) values (232701,2327,'加格达奇区');
insert into web_regions (region_id,city_id,region) values (232702,2327,'新林区');
insert into web_regions (region_id,city_id,region) values (232703,2327,'松岭区');
insert into web_regions (region_id,city_id,region) values (232704,2327,'呼中区');
insert into web_regions (region_id,city_id,region) values (232721,2327,'呼玛县');
insert into web_regions (region_id,city_id,region) values (232722,2327,'塔河县');
insert into web_regions (region_id,city_id,region) values (232723,2327,'漠河县');
insert into web_regions (region_id,city_id,region) values (310101,3101,'黄浦区');
insert into web_regions (region_id,city_id,region) values (310104,3101,'徐汇区');
insert into web_regions (region_id,city_id,region) values (310105,3101,'长宁区');
insert into web_regions (region_id,city_id,region) values (310106,3101,'静安区');
insert into web_regions (region_id,city_id,region) values (310107,3101,'普陀区');
insert into web_regions (region_id,city_id,region) values (310108,3101,'闸北区');
insert into web_regions (region_id,city_id,region) values (310109,3101,'虹口区');
insert into web_regions (region_id,city_id,region) values (310110,3101,'杨浦区');
insert into web_regions (region_id,city_id,region) values (310112,3101,'闵行区');
insert into web_regions (region_id,city_id,region) values (310113,3101,'宝山区');
insert into web_regions (region_id,city_id,region) values (310114,3101,'嘉定区');
insert into web_regions (region_id,city_id,region) values (310115,3101,'浦东新区');
insert into web_regions (region_id,city_id,region) values (310116,3101,'金山区');
insert into web_regions (region_id,city_id,region) values (310117,3101,'松江区');
insert into web_regions (region_id,city_id,region) values (310118,3101,'青浦区');
insert into web_regions (region_id,city_id,region) values (310120,3101,'奉贤区');
insert into web_regions (region_id,city_id,region) values (310230,3101,'崇明县');
insert into web_regions (region_id,city_id,region) values (320102,3201,'玄武区');
insert into web_regions (region_id,city_id,region) values (320104,3201,'秦淮区');
insert into web_regions (region_id,city_id,region) values (320105,3201,'建邺区');
insert into web_regions (region_id,city_id,region) values (320106,3201,'鼓楼区');
insert into web_regions (region_id,city_id,region) values (320111,3201,'浦口区');
insert into web_regions (region_id,city_id,region) values (320113,3201,'栖霞区');
insert into web_regions (region_id,city_id,region) values (320114,3201,'雨花台区');
insert into web_regions (region_id,city_id,region) values (320115,3201,'江宁区');
insert into web_regions (region_id,city_id,region) values (320116,3201,'六合区');
insert into web_regions (region_id,city_id,region) values (320117,3201,'溧水区');
insert into web_regions (region_id,city_id,region) values (320118,3201,'高淳区');
insert into web_regions (region_id,city_id,region) values (320202,3202,'崇安区');
insert into web_regions (region_id,city_id,region) values (320203,3202,'南长区');
insert into web_regions (region_id,city_id,region) values (320204,3202,'北塘区');
insert into web_regions (region_id,city_id,region) values (320205,3202,'锡山区');
insert into web_regions (region_id,city_id,region) values (320206,3202,'惠山区');
insert into web_regions (region_id,city_id,region) values (320211,3202,'滨湖区');
insert into web_regions (region_id,city_id,region) values (320281,3202,'江阴市');
insert into web_regions (region_id,city_id,region) values (320282,3202,'宜兴市');
insert into web_regions (region_id,city_id,region) values (320302,3203,'鼓楼区');
insert into web_regions (region_id,city_id,region) values (320303,3203,'云龙区');
insert into web_regions (region_id,city_id,region) values (320305,3203,'贾汪区');
insert into web_regions (region_id,city_id,region) values (320311,3203,'泉山区');
insert into web_regions (region_id,city_id,region) values (320312,3203,'铜山区');
insert into web_regions (region_id,city_id,region) values (320321,3203,'丰县');
insert into web_regions (region_id,city_id,region) values (320322,3203,'沛县');
insert into web_regions (region_id,city_id,region) values (320324,3203,'睢宁县');
insert into web_regions (region_id,city_id,region) values (320381,3203,'新沂市');
insert into web_regions (region_id,city_id,region) values (320382,3203,'邳州市');
insert into web_regions (region_id,city_id,region) values (320402,3204,'天宁区');
insert into web_regions (region_id,city_id,region) values (320404,3204,'钟楼区');
insert into web_regions (region_id,city_id,region) values (320405,3204,'戚墅堰区');
insert into web_regions (region_id,city_id,region) values (320411,3204,'新北区');
insert into web_regions (region_id,city_id,region) values (320412,3204,'武进区');
insert into web_regions (region_id,city_id,region) values (320481,3204,'溧阳市');
insert into web_regions (region_id,city_id,region) values (320482,3204,'金坛市');
insert into web_regions (region_id,city_id,region) values (320505,3205,'虎丘区');
insert into web_regions (region_id,city_id,region) values (320506,3205,'吴中区');
insert into web_regions (region_id,city_id,region) values (320507,3205,'相城区');
insert into web_regions (region_id,city_id,region) values (320508,3205,'姑苏区');
insert into web_regions (region_id,city_id,region) values (320509,3205,'吴江区');
insert into web_regions (region_id,city_id,region) values (320581,3205,'常熟市');
insert into web_regions (region_id,city_id,region) values (320582,3205,'张家港市');
insert into web_regions (region_id,city_id,region) values (320583,3205,'昆山市');
insert into web_regions (region_id,city_id,region) values (320585,3205,'太仓市');
insert into web_regions (region_id,city_id,region) values (320602,3206,'崇川区');
insert into web_regions (region_id,city_id,region) values (320611,3206,'港闸区');
insert into web_regions (region_id,city_id,region) values (320612,3206,'通州区');
insert into web_regions (region_id,city_id,region) values (320621,3206,'海安县');
insert into web_regions (region_id,city_id,region) values (320623,3206,'如东县');
insert into web_regions (region_id,city_id,region) values (320681,3206,'启东市');
insert into web_regions (region_id,city_id,region) values (320682,3206,'如皋市');
insert into web_regions (region_id,city_id,region) values (320684,3206,'海门市');
insert into web_regions (region_id,city_id,region) values (320703,3207,'连云区');
insert into web_regions (region_id,city_id,region) values (320706,3207,'海州区');
insert into web_regions (region_id,city_id,region) values (320707,3207,'赣榆区');
insert into web_regions (region_id,city_id,region) values (320722,3207,'东海县');
insert into web_regions (region_id,city_id,region) values (320723,3207,'灌云县');
insert into web_regions (region_id,city_id,region) values (320724,3207,'灌南县');
insert into web_regions (region_id,city_id,region) values (320802,3208,'清河区');
insert into web_regions (region_id,city_id,region) values (320803,3208,'淮安区');
insert into web_regions (region_id,city_id,region) values (320804,3208,'淮阴区');
insert into web_regions (region_id,city_id,region) values (320811,3208,'清浦区');
insert into web_regions (region_id,city_id,region) values (320826,3208,'涟水县');
insert into web_regions (region_id,city_id,region) values (320829,3208,'洪泽县');
insert into web_regions (region_id,city_id,region) values (320830,3208,'盱眙县');
insert into web_regions (region_id,city_id,region) values (320831,3208,'金湖县');
insert into web_regions (region_id,city_id,region) values (320902,3209,'亭湖区');
insert into web_regions (region_id,city_id,region) values (320903,3209,'盐都区');
insert into web_regions (region_id,city_id,region) values (320921,3209,'响水县');
insert into web_regions (region_id,city_id,region) values (320922,3209,'滨海县');
insert into web_regions (region_id,city_id,region) values (320923,3209,'阜宁县');
insert into web_regions (region_id,city_id,region) values (320924,3209,'射阳县');
insert into web_regions (region_id,city_id,region) values (320925,3209,'建湖县');
insert into web_regions (region_id,city_id,region) values (320981,3209,'东台市');
insert into web_regions (region_id,city_id,region) values (320982,3209,'大丰市');
insert into web_regions (region_id,city_id,region) values (321002,3210,'广陵区');
insert into web_regions (region_id,city_id,region) values (321003,3210,'邗江区');
insert into web_regions (region_id,city_id,region) values (321012,3210,'江都区');
insert into web_regions (region_id,city_id,region) values (321023,3210,'宝应县');
insert into web_regions (region_id,city_id,region) values (321081,3210,'仪征市');
insert into web_regions (region_id,city_id,region) values (321084,3210,'高邮市');
insert into web_regions (region_id,city_id,region) values (321102,3211,'京口区');
insert into web_regions (region_id,city_id,region) values (321111,3211,'润州区');
insert into web_regions (region_id,city_id,region) values (321112,3211,'丹徒区');
insert into web_regions (region_id,city_id,region) values (321181,3211,'丹阳市');
insert into web_regions (region_id,city_id,region) values (321182,3211,'扬中市');
insert into web_regions (region_id,city_id,region) values (321183,3211,'句容市');
insert into web_regions (region_id,city_id,region) values (321202,3212,'海陵区');
insert into web_regions (region_id,city_id,region) values (321203,3212,'高港区');
insert into web_regions (region_id,city_id,region) values (321204,3212,'姜堰区');
insert into web_regions (region_id,city_id,region) values (321281,3212,'兴化市');
insert into web_regions (region_id,city_id,region) values (321282,3212,'靖江市');
insert into web_regions (region_id,city_id,region) values (321283,3212,'泰兴市');
insert into web_regions (region_id,city_id,region) values (321302,3213,'宿城区');
insert into web_regions (region_id,city_id,region) values (321311,3213,'宿豫区');
insert into web_regions (region_id,city_id,region) values (321322,3213,'沭阳县');
insert into web_regions (region_id,city_id,region) values (321323,3213,'泗阳县');
insert into web_regions (region_id,city_id,region) values (321324,3213,'泗洪县');
insert into web_regions (region_id,city_id,region) values (330102,3301,'上城区');
insert into web_regions (region_id,city_id,region) values (330103,3301,'下城区');
insert into web_regions (region_id,city_id,region) values (330104,3301,'江干区');
insert into web_regions (region_id,city_id,region) values (330105,3301,'拱墅区');
insert into web_regions (region_id,city_id,region) values (330106,3301,'西湖区');
insert into web_regions (region_id,city_id,region) values (330108,3301,'滨江区');
insert into web_regions (region_id,city_id,region) values (330109,3301,'萧山区');
insert into web_regions (region_id,city_id,region) values (330110,3301,'余杭区');
insert into web_regions (region_id,city_id,region) values (330122,3301,'桐庐县');
insert into web_regions (region_id,city_id,region) values (330127,3301,'淳安县');
insert into web_regions (region_id,city_id,region) values (330182,3301,'建德市');
insert into web_regions (region_id,city_id,region) values (330183,3301,'富阳区');
insert into web_regions (region_id,city_id,region) values (330185,3301,'临安市');
insert into web_regions (region_id,city_id,region) values (330203,3302,'海曙区');
insert into web_regions (region_id,city_id,region) values (330204,3302,'江东区');
insert into web_regions (region_id,city_id,region) values (330205,3302,'江北区');
insert into web_regions (region_id,city_id,region) values (330206,3302,'北仑区');
insert into web_regions (region_id,city_id,region) values (330211,3302,'镇海区');
insert into web_regions (region_id,city_id,region) values (330212,3302,'鄞州区');
insert into web_regions (region_id,city_id,region) values (330225,3302,'象山县');
insert into web_regions (region_id,city_id,region) values (330226,3302,'宁海县');
insert into web_regions (region_id,city_id,region) values (330281,3302,'余姚市');
insert into web_regions (region_id,city_id,region) values (330282,3302,'慈溪市');
insert into web_regions (region_id,city_id,region) values (330283,3302,'奉化市');
insert into web_regions (region_id,city_id,region) values (330302,3303,'鹿城区');
insert into web_regions (region_id,city_id,region) values (330303,3303,'龙湾区');
insert into web_regions (region_id,city_id,region) values (330304,3303,'瓯海区');
insert into web_regions (region_id,city_id,region) values (330322,3303,'洞头县');
insert into web_regions (region_id,city_id,region) values (330324,3303,'永嘉县');
insert into web_regions (region_id,city_id,region) values (330326,3303,'平阳县');
insert into web_regions (region_id,city_id,region) values (330327,3303,'苍南县');
insert into web_regions (region_id,city_id,region) values (330328,3303,'文成县');
insert into web_regions (region_id,city_id,region) values (330329,3303,'泰顺县');
insert into web_regions (region_id,city_id,region) values (330381,3303,'瑞安市');
insert into web_regions (region_id,city_id,region) values (330382,3303,'乐清市');
insert into web_regions (region_id,city_id,region) values (330402,3304,'南湖区');
insert into web_regions (region_id,city_id,region) values (330411,3304,'秀洲区');
insert into web_regions (region_id,city_id,region) values (330421,3304,'嘉善县');
insert into web_regions (region_id,city_id,region) values (330424,3304,'海盐县');
insert into web_regions (region_id,city_id,region) values (330481,3304,'海宁市');
insert into web_regions (region_id,city_id,region) values (330482,3304,'平湖市');
insert into web_regions (region_id,city_id,region) values (330483,3304,'桐乡市');
insert into web_regions (region_id,city_id,region) values (330502,3305,'吴兴区');
insert into web_regions (region_id,city_id,region) values (330503,3305,'南浔区');
insert into web_regions (region_id,city_id,region) values (330521,3305,'德清县');
insert into web_regions (region_id,city_id,region) values (330522,3305,'长兴县');
insert into web_regions (region_id,city_id,region) values (330523,3305,'安吉县');
insert into web_regions (region_id,city_id,region) values (330602,3306,'越城区');
insert into web_regions (region_id,city_id,region) values (330603,3306,'柯桥区');
insert into web_regions (region_id,city_id,region) values (330604,3306,'上虞区');
insert into web_regions (region_id,city_id,region) values (330624,3306,'新昌县');
insert into web_regions (region_id,city_id,region) values (330681,3306,'诸暨市');
insert into web_regions (region_id,city_id,region) values (330683,3306,'嵊州市');
insert into web_regions (region_id,city_id,region) values (330702,3307,'婺城区');
insert into web_regions (region_id,city_id,region) values (330703,3307,'金东区');
insert into web_regions (region_id,city_id,region) values (330723,3307,'武义县');
insert into web_regions (region_id,city_id,region) values (330726,3307,'浦江县');
insert into web_regions (region_id,city_id,region) values (330727,3307,'磐安县');
insert into web_regions (region_id,city_id,region) values (330781,3307,'兰溪市');
insert into web_regions (region_id,city_id,region) values (330782,3307,'义乌市');
insert into web_regions (region_id,city_id,region) values (330783,3307,'东阳市');
insert into web_regions (region_id,city_id,region) values (330784,3307,'永康市');
insert into web_regions (region_id,city_id,region) values (330802,3308,'柯城区');
insert into web_regions (region_id,city_id,region) values (330803,3308,'衢江区');
insert into web_regions (region_id,city_id,region) values (330822,3308,'常山县');
insert into web_regions (region_id,city_id,region) values (330824,3308,'开化县');
insert into web_regions (region_id,city_id,region) values (330825,3308,'龙游县');
insert into web_regions (region_id,city_id,region) values (330881,3308,'江山市');
insert into web_regions (region_id,city_id,region) values (330902,3309,'定海区');
insert into web_regions (region_id,city_id,region) values (330903,3309,'普陀区');
insert into web_regions (region_id,city_id,region) values (330921,3309,'岱山县');
insert into web_regions (region_id,city_id,region) values (330922,3309,'嵊泗县');
insert into web_regions (region_id,city_id,region) values (331002,3310,'椒江区');
insert into web_regions (region_id,city_id,region) values (331003,3310,'黄岩区');
insert into web_regions (region_id,city_id,region) values (331004,3310,'路桥区');
insert into web_regions (region_id,city_id,region) values (331021,3310,'玉环县');
insert into web_regions (region_id,city_id,region) values (331022,3310,'三门县');
insert into web_regions (region_id,city_id,region) values (331023,3310,'天台县');
insert into web_regions (region_id,city_id,region) values (331024,3310,'仙居县');
insert into web_regions (region_id,city_id,region) values (331081,3310,'温岭市');
insert into web_regions (region_id,city_id,region) values (331082,3310,'临海市');
insert into web_regions (region_id,city_id,region) values (331102,3311,'莲都区');
insert into web_regions (region_id,city_id,region) values (331121,3311,'青田县');
insert into web_regions (region_id,city_id,region) values (331122,3311,'缙云县');
insert into web_regions (region_id,city_id,region) values (331123,3311,'遂昌县');
insert into web_regions (region_id,city_id,region) values (331124,3311,'松阳县');
insert into web_regions (region_id,city_id,region) values (331125,3311,'云和县');
insert into web_regions (region_id,city_id,region) values (331126,3311,'庆元县');
insert into web_regions (region_id,city_id,region) values (331127,3311,'景宁畲族自治县');
insert into web_regions (region_id,city_id,region) values (331181,3311,'龙泉市');
insert into web_regions (region_id,city_id,region) values (331201,3312,'金塘岛');
insert into web_regions (region_id,city_id,region) values (331202,3312,'六横岛');
insert into web_regions (region_id,city_id,region) values (331203,3312,'衢山岛');
insert into web_regions (region_id,city_id,region) values (331204,3312,'舟山本岛西北部');
insert into web_regions (region_id,city_id,region) values (331205,3312,'岱山岛西南部');
insert into web_regions (region_id,city_id,region) values (331206,3312,'泗礁岛');
insert into web_regions (region_id,city_id,region) values (331207,3312,'朱家尖岛');
insert into web_regions (region_id,city_id,region) values (331208,3312,'洋山岛');
insert into web_regions (region_id,city_id,region) values (331209,3312,'长涂岛');
insert into web_regions (region_id,city_id,region) values (331210,3312,'虾峙岛');
insert into web_regions (region_id,city_id,region) values (340102,3401,'瑶海区');
insert into web_regions (region_id,city_id,region) values (340103,3401,'庐阳区');
insert into web_regions (region_id,city_id,region) values (340104,3401,'蜀山区');
insert into web_regions (region_id,city_id,region) values (340111,3401,'包河区');
insert into web_regions (region_id,city_id,region) values (340121,3401,'长丰县');
insert into web_regions (region_id,city_id,region) values (340122,3401,'肥东县');
insert into web_regions (region_id,city_id,region) values (340123,3401,'肥西县');
insert into web_regions (region_id,city_id,region) values (340124,3401,'庐江县');
insert into web_regions (region_id,city_id,region) values (340181,3401,'巢湖市');
insert into web_regions (region_id,city_id,region) values (340202,3402,'镜湖区');
insert into web_regions (region_id,city_id,region) values (340203,3402,'弋江区');
insert into web_regions (region_id,city_id,region) values (340207,3402,'鸠江区');
insert into web_regions (region_id,city_id,region) values (340208,3402,'三山区');
insert into web_regions (region_id,city_id,region) values (340221,3402,'芜湖县');
insert into web_regions (region_id,city_id,region) values (340222,3402,'繁昌县');
insert into web_regions (region_id,city_id,region) values (340223,3402,'南陵县');
insert into web_regions (region_id,city_id,region) values (340225,3402,'无为县');
insert into web_regions (region_id,city_id,region) values (340302,3403,'龙子湖区');
insert into web_regions (region_id,city_id,region) values (340303,3403,'蚌山区');
insert into web_regions (region_id,city_id,region) values (340304,3403,'禹会区');
insert into web_regions (region_id,city_id,region) values (340311,3403,'淮上区');
insert into web_regions (region_id,city_id,region) values (340321,3403,'怀远县');
insert into web_regions (region_id,city_id,region) values (340322,3403,'五河县');
insert into web_regions (region_id,city_id,region) values (340323,3403,'固镇县');
insert into web_regions (region_id,city_id,region) values (340402,3404,'大通区');
insert into web_regions (region_id,city_id,region) values (340403,3404,'田家庵区');
insert into web_regions (region_id,city_id,region) values (340404,3404,'谢家集区');
insert into web_regions (region_id,city_id,region) values (340405,3404,'八公山区');
insert into web_regions (region_id,city_id,region) values (340406,3404,'潘集区');
insert into web_regions (region_id,city_id,region) values (340421,3404,'凤台县');
insert into web_regions (region_id,city_id,region) values (340503,3405,'花山区');
insert into web_regions (region_id,city_id,region) values (340504,3405,'雨山区');
insert into web_regions (region_id,city_id,region) values (340506,3405,'博望区');
insert into web_regions (region_id,city_id,region) values (340521,3405,'当涂县');
insert into web_regions (region_id,city_id,region) values (340522,3405,'含山县');
insert into web_regions (region_id,city_id,region) values (340523,3405,'和县');
insert into web_regions (region_id,city_id,region) values (340602,3406,'杜集区');
insert into web_regions (region_id,city_id,region) values (340603,3406,'相山区');
insert into web_regions (region_id,city_id,region) values (340604,3406,'烈山区');
insert into web_regions (region_id,city_id,region) values (340621,3406,'濉溪县');
insert into web_regions (region_id,city_id,region) values (340702,3407,'铜官山区');
insert into web_regions (region_id,city_id,region) values (340703,3407,'狮子山区');
insert into web_regions (region_id,city_id,region) values (340711,3407,'郊区');
insert into web_regions (region_id,city_id,region) values (340721,3407,'铜陵县');
insert into web_regions (region_id,city_id,region) values (340802,3408,'迎江区');
insert into web_regions (region_id,city_id,region) values (340803,3408,'大观区');
insert into web_regions (region_id,city_id,region) values (340811,3408,'宜秀区');
insert into web_regions (region_id,city_id,region) values (340822,3408,'怀宁县');
insert into web_regions (region_id,city_id,region) values (340823,3408,'枞阳县');
insert into web_regions (region_id,city_id,region) values (340824,3408,'潜山县');
insert into web_regions (region_id,city_id,region) values (340825,3408,'太湖县');
insert into web_regions (region_id,city_id,region) values (340826,3408,'宿松县');
insert into web_regions (region_id,city_id,region) values (340827,3408,'望江县');
insert into web_regions (region_id,city_id,region) values (340828,3408,'岳西县');
insert into web_regions (region_id,city_id,region) values (340881,3408,'桐城市');
insert into web_regions (region_id,city_id,region) values (341002,3410,'屯溪区');
insert into web_regions (region_id,city_id,region) values (341003,3410,'黄山区');
insert into web_regions (region_id,city_id,region) values (341004,3410,'徽州区');
insert into web_regions (region_id,city_id,region) values (341021,3410,'歙县');
insert into web_regions (region_id,city_id,region) values (341022,3410,'休宁县');
insert into web_regions (region_id,city_id,region) values (341023,3410,'黟县');
insert into web_regions (region_id,city_id,region) values (341024,3410,'祁门县');
insert into web_regions (region_id,city_id,region) values (341102,3411,'琅琊区');
insert into web_regions (region_id,city_id,region) values (341103,3411,'南谯区');
insert into web_regions (region_id,city_id,region) values (341122,3411,'来安县');
insert into web_regions (region_id,city_id,region) values (341124,3411,'全椒县');
insert into web_regions (region_id,city_id,region) values (341125,3411,'定远县');
insert into web_regions (region_id,city_id,region) values (341126,3411,'凤阳县');
insert into web_regions (region_id,city_id,region) values (341181,3411,'天长市');
insert into web_regions (region_id,city_id,region) values (341182,3411,'明光市');
insert into web_regions (region_id,city_id,region) values (341202,3412,'颍州区');
insert into web_regions (region_id,city_id,region) values (341203,3412,'颍东区');
insert into web_regions (region_id,city_id,region) values (341204,3412,'颍泉区');
insert into web_regions (region_id,city_id,region) values (341221,3412,'临泉县');
insert into web_regions (region_id,city_id,region) values (341222,3412,'太和县');
insert into web_regions (region_id,city_id,region) values (341225,3412,'阜南县');
insert into web_regions (region_id,city_id,region) values (341226,3412,'颍上县');
insert into web_regions (region_id,city_id,region) values (341282,3412,'界首市');
insert into web_regions (region_id,city_id,region) values (341302,3413,'埇桥区');
insert into web_regions (region_id,city_id,region) values (341321,3413,'砀山县');
insert into web_regions (region_id,city_id,region) values (341322,3413,'萧县');
insert into web_regions (region_id,city_id,region) values (341323,3413,'灵璧县');
insert into web_regions (region_id,city_id,region) values (341324,3413,'泗县');
insert into web_regions (region_id,city_id,region) values (341502,3415,'金安区');
insert into web_regions (region_id,city_id,region) values (341503,3415,'裕安区');
insert into web_regions (region_id,city_id,region) values (341521,3415,'寿县');
insert into web_regions (region_id,city_id,region) values (341522,3415,'霍邱县');
insert into web_regions (region_id,city_id,region) values (341523,3415,'舒城县');
insert into web_regions (region_id,city_id,region) values (341524,3415,'金寨县');
insert into web_regions (region_id,city_id,region) values (341525,3415,'霍山县');
insert into web_regions (region_id,city_id,region) values (341602,3416,'谯城区');
insert into web_regions (region_id,city_id,region) values (341621,3416,'涡阳县');
insert into web_regions (region_id,city_id,region) values (341622,3416,'蒙城县');
insert into web_regions (region_id,city_id,region) values (341623,3416,'利辛县');
insert into web_regions (region_id,city_id,region) values (341702,3417,'贵池区');
insert into web_regions (region_id,city_id,region) values (341721,3417,'东至县');
insert into web_regions (region_id,city_id,region) values (341722,3417,'石台县');
insert into web_regions (region_id,city_id,region) values (341723,3417,'青阳县');
insert into web_regions (region_id,city_id,region) values (341802,3418,'宣州区');
insert into web_regions (region_id,city_id,region) values (341821,3418,'郎溪县');
insert into web_regions (region_id,city_id,region) values (341822,3418,'广德县');
insert into web_regions (region_id,city_id,region) values (341823,3418,'泾县');
insert into web_regions (region_id,city_id,region) values (341824,3418,'绩溪县');
insert into web_regions (region_id,city_id,region) values (341825,3418,'旌德县');
insert into web_regions (region_id,city_id,region) values (341881,3418,'宁国市');
insert into web_regions (region_id,city_id,region) values (350102,3501,'鼓楼区');
insert into web_regions (region_id,city_id,region) values (350103,3501,'台江区');
insert into web_regions (region_id,city_id,region) values (350104,3501,'仓山区');
insert into web_regions (region_id,city_id,region) values (350105,3501,'马尾区');
insert into web_regions (region_id,city_id,region) values (350111,3501,'晋安区');
insert into web_regions (region_id,city_id,region) values (350121,3501,'闽侯县');
insert into web_regions (region_id,city_id,region) values (350122,3501,'连江县');
insert into web_regions (region_id,city_id,region) values (350123,3501,'罗源县');
insert into web_regions (region_id,city_id,region) values (350124,3501,'闽清县');
insert into web_regions (region_id,city_id,region) values (350125,3501,'永泰县');
insert into web_regions (region_id,city_id,region) values (350128,3501,'平潭县');
insert into web_regions (region_id,city_id,region) values (350181,3501,'福清市');
insert into web_regions (region_id,city_id,region) values (350182,3501,'长乐市');
insert into web_regions (region_id,city_id,region) values (350203,3502,'思明区');
insert into web_regions (region_id,city_id,region) values (350205,3502,'海沧区');
insert into web_regions (region_id,city_id,region) values (350206,3502,'湖里区');
insert into web_regions (region_id,city_id,region) values (350211,3502,'集美区');
insert into web_regions (region_id,city_id,region) values (350212,3502,'同安区');
insert into web_regions (region_id,city_id,region) values (350213,3502,'翔安区');
insert into web_regions (region_id,city_id,region) values (350302,3503,'城厢区');
insert into web_regions (region_id,city_id,region) values (350303,3503,'涵江区');
insert into web_regions (region_id,city_id,region) values (350304,3503,'荔城区');
insert into web_regions (region_id,city_id,region) values (350305,3503,'秀屿区');
insert into web_regions (region_id,city_id,region) values (350322,3503,'仙游县');
insert into web_regions (region_id,city_id,region) values (350402,3504,'梅列区');
insert into web_regions (region_id,city_id,region) values (350403,3504,'三元区');
insert into web_regions (region_id,city_id,region) values (350421,3504,'明溪县');
insert into web_regions (region_id,city_id,region) values (350423,3504,'清流县');
insert into web_regions (region_id,city_id,region) values (350424,3504,'宁化县');
insert into web_regions (region_id,city_id,region) values (350425,3504,'大田县');
insert into web_regions (region_id,city_id,region) values (350426,3504,'尤溪县');
insert into web_regions (region_id,city_id,region) values (350427,3504,'沙县');
insert into web_regions (region_id,city_id,region) values (350428,3504,'将乐县');
insert into web_regions (region_id,city_id,region) values (350429,3504,'泰宁县');
insert into web_regions (region_id,city_id,region) values (350430,3504,'建宁县');
insert into web_regions (region_id,city_id,region) values (350481,3504,'永安市');
insert into web_regions (region_id,city_id,region) values (350502,3505,'鲤城区');
insert into web_regions (region_id,city_id,region) values (350503,3505,'丰泽区');
insert into web_regions (region_id,city_id,region) values (350504,3505,'洛江区');
insert into web_regions (region_id,city_id,region) values (350505,3505,'泉港区');
insert into web_regions (region_id,city_id,region) values (350521,3505,'惠安县');
insert into web_regions (region_id,city_id,region) values (350524,3505,'安溪县');
insert into web_regions (region_id,city_id,region) values (350525,3505,'永春县');
insert into web_regions (region_id,city_id,region) values (350526,3505,'德化县');
insert into web_regions (region_id,city_id,region) values (350527,3505,'金门县');
insert into web_regions (region_id,city_id,region) values (350581,3505,'石狮市');
insert into web_regions (region_id,city_id,region) values (350582,3505,'晋江市');
insert into web_regions (region_id,city_id,region) values (350583,3505,'南安市');
insert into web_regions (region_id,city_id,region) values (350602,3506,'芗城区');
insert into web_regions (region_id,city_id,region) values (350603,3506,'龙文区');
insert into web_regions (region_id,city_id,region) values (350622,3506,'云霄县');
insert into web_regions (region_id,city_id,region) values (350623,3506,'漳浦县');
insert into web_regions (region_id,city_id,region) values (350624,3506,'诏安县');
insert into web_regions (region_id,city_id,region) values (350625,3506,'长泰县');
insert into web_regions (region_id,city_id,region) values (350626,3506,'东山县');
insert into web_regions (region_id,city_id,region) values (350627,3506,'南靖县');
insert into web_regions (region_id,city_id,region) values (350628,3506,'平和县');
insert into web_regions (region_id,city_id,region) values (350629,3506,'华安县');
insert into web_regions (region_id,city_id,region) values (350681,3506,'龙海市');
insert into web_regions (region_id,city_id,region) values (350702,3507,'延平区');
insert into web_regions (region_id,city_id,region) values (350703,3507,'建阳区');
insert into web_regions (region_id,city_id,region) values (350721,3507,'顺昌县');
insert into web_regions (region_id,city_id,region) values (350722,3507,'浦城县');
insert into web_regions (region_id,city_id,region) values (350723,3507,'光泽县');
insert into web_regions (region_id,city_id,region) values (350724,3507,'松溪县');
insert into web_regions (region_id,city_id,region) values (350725,3507,'政和县');
insert into web_regions (region_id,city_id,region) values (350781,3507,'邵武市');
insert into web_regions (region_id,city_id,region) values (350782,3507,'武夷山市');
insert into web_regions (region_id,city_id,region) values (350783,3507,'建瓯市');
insert into web_regions (region_id,city_id,region) values (350802,3508,'新罗区');
insert into web_regions (region_id,city_id,region) values (350821,3508,'长汀县');
insert into web_regions (region_id,city_id,region) values (350822,3508,'永定区');
insert into web_regions (region_id,city_id,region) values (350823,3508,'上杭县');
insert into web_regions (region_id,city_id,region) values (350824,3508,'武平县');
insert into web_regions (region_id,city_id,region) values (350825,3508,'连城县');
insert into web_regions (region_id,city_id,region) values (350881,3508,'漳平市');
insert into web_regions (region_id,city_id,region) values (350902,3509,'蕉城区');
insert into web_regions (region_id,city_id,region) values (350921,3509,'霞浦县');
insert into web_regions (region_id,city_id,region) values (350922,3509,'古田县');
insert into web_regions (region_id,city_id,region) values (350923,3509,'屏南县');
insert into web_regions (region_id,city_id,region) values (350924,3509,'寿宁县');
insert into web_regions (region_id,city_id,region) values (350925,3509,'周宁县');
insert into web_regions (region_id,city_id,region) values (350926,3509,'柘荣县');
insert into web_regions (region_id,city_id,region) values (350981,3509,'福安市');
insert into web_regions (region_id,city_id,region) values (350982,3509,'福鼎市');
insert into web_regions (region_id,city_id,region) values (360102,3601,'东湖区');
insert into web_regions (region_id,city_id,region) values (360103,3601,'西湖区');
insert into web_regions (region_id,city_id,region) values (360104,3601,'青云谱区');
insert into web_regions (region_id,city_id,region) values (360105,3601,'湾里区');
insert into web_regions (region_id,city_id,region) values (360111,3601,'青山湖区');
insert into web_regions (region_id,city_id,region) values (360121,3601,'南昌县');
insert into web_regions (region_id,city_id,region) values (360122,3601,'新建县');
insert into web_regions (region_id,city_id,region) values (360123,3601,'安义县');
insert into web_regions (region_id,city_id,region) values (360124,3601,'进贤县');
insert into web_regions (region_id,city_id,region) values (360202,3602,'昌江区');
insert into web_regions (region_id,city_id,region) values (360203,3602,'珠山区');
insert into web_regions (region_id,city_id,region) values (360222,3602,'浮梁县');
insert into web_regions (region_id,city_id,region) values (360281,3602,'乐平市');
insert into web_regions (region_id,city_id,region) values (360302,3603,'安源区');
insert into web_regions (region_id,city_id,region) values (360313,3603,'湘东区');
insert into web_regions (region_id,city_id,region) values (360321,3603,'莲花县');
insert into web_regions (region_id,city_id,region) values (360322,3603,'上栗县');
insert into web_regions (region_id,city_id,region) values (360323,3603,'芦溪县');
insert into web_regions (region_id,city_id,region) values (360402,3604,'庐山区');
insert into web_regions (region_id,city_id,region) values (360403,3604,'浔阳区');
insert into web_regions (region_id,city_id,region) values (360421,3604,'九江县');
insert into web_regions (region_id,city_id,region) values (360423,3604,'武宁县');
insert into web_regions (region_id,city_id,region) values (360424,3604,'修水县');
insert into web_regions (region_id,city_id,region) values (360425,3604,'永修县');
insert into web_regions (region_id,city_id,region) values (360426,3604,'德安县');
insert into web_regions (region_id,city_id,region) values (360427,3604,'星子县');
insert into web_regions (region_id,city_id,region) values (360428,3604,'都昌县');
insert into web_regions (region_id,city_id,region) values (360429,3604,'湖口县');
insert into web_regions (region_id,city_id,region) values (360430,3604,'彭泽县');
insert into web_regions (region_id,city_id,region) values (360481,3604,'瑞昌市');
insert into web_regions (region_id,city_id,region) values (360482,3604,'共青城市');
insert into web_regions (region_id,city_id,region) values (360502,3605,'渝水区');
insert into web_regions (region_id,city_id,region) values (360521,3605,'分宜县');
insert into web_regions (region_id,city_id,region) values (360602,3606,'月湖区');
insert into web_regions (region_id,city_id,region) values (360622,3606,'余江县');
insert into web_regions (region_id,city_id,region) values (360681,3606,'贵溪市');
insert into web_regions (region_id,city_id,region) values (360702,3607,'章贡区');
insert into web_regions (region_id,city_id,region) values (360703,3607,'南康区');
insert into web_regions (region_id,city_id,region) values (360721,3607,'赣县');
insert into web_regions (region_id,city_id,region) values (360722,3607,'信丰县');
insert into web_regions (region_id,city_id,region) values (360723,3607,'大余县');
insert into web_regions (region_id,city_id,region) values (360724,3607,'上犹县');
insert into web_regions (region_id,city_id,region) values (360725,3607,'崇义县');
insert into web_regions (region_id,city_id,region) values (360726,3607,'安远县');
insert into web_regions (region_id,city_id,region) values (360727,3607,'龙南县');
insert into web_regions (region_id,city_id,region) values (360728,3607,'定南县');
insert into web_regions (region_id,city_id,region) values (360729,3607,'全南县');
insert into web_regions (region_id,city_id,region) values (360730,3607,'宁都县');
insert into web_regions (region_id,city_id,region) values (360731,3607,'于都县');
insert into web_regions (region_id,city_id,region) values (360732,3607,'兴国县');
insert into web_regions (region_id,city_id,region) values (360733,3607,'会昌县');
insert into web_regions (region_id,city_id,region) values (360734,3607,'寻乌县');
insert into web_regions (region_id,city_id,region) values (360735,3607,'石城县');
insert into web_regions (region_id,city_id,region) values (360781,3607,'瑞金市');
insert into web_regions (region_id,city_id,region) values (360802,3608,'吉州区');
insert into web_regions (region_id,city_id,region) values (360803,3608,'青原区');
insert into web_regions (region_id,city_id,region) values (360821,3608,'吉安县');
insert into web_regions (region_id,city_id,region) values (360822,3608,'吉水县');
insert into web_regions (region_id,city_id,region) values (360823,3608,'峡江县');
insert into web_regions (region_id,city_id,region) values (360824,3608,'新干县');
insert into web_regions (region_id,city_id,region) values (360825,3608,'永丰县');
insert into web_regions (region_id,city_id,region) values (360826,3608,'泰和县');
insert into web_regions (region_id,city_id,region) values (360827,3608,'遂川县');
insert into web_regions (region_id,city_id,region) values (360828,3608,'万安县');
insert into web_regions (region_id,city_id,region) values (360829,3608,'安福县');
insert into web_regions (region_id,city_id,region) values (360830,3608,'永新县');
insert into web_regions (region_id,city_id,region) values (360881,3608,'井冈山市');
insert into web_regions (region_id,city_id,region) values (360902,3609,'袁州区');
insert into web_regions (region_id,city_id,region) values (360921,3609,'奉新县');
insert into web_regions (region_id,city_id,region) values (360922,3609,'万载县');
insert into web_regions (region_id,city_id,region) values (360923,3609,'上高县');
insert into web_regions (region_id,city_id,region) values (360924,3609,'宜丰县');
insert into web_regions (region_id,city_id,region) values (360925,3609,'靖安县');
insert into web_regions (region_id,city_id,region) values (360926,3609,'铜鼓县');
insert into web_regions (region_id,city_id,region) values (360981,3609,'丰城市');
insert into web_regions (region_id,city_id,region) values (360982,3609,'樟树市');
insert into web_regions (region_id,city_id,region) values (360983,3609,'高安市');
insert into web_regions (region_id,city_id,region) values (361002,3610,'临川区');
insert into web_regions (region_id,city_id,region) values (361021,3610,'南城县');
insert into web_regions (region_id,city_id,region) values (361022,3610,'黎川县');
insert into web_regions (region_id,city_id,region) values (361023,3610,'南丰县');
insert into web_regions (region_id,city_id,region) values (361024,3610,'崇仁县');
insert into web_regions (region_id,city_id,region) values (361025,3610,'乐安县');
insert into web_regions (region_id,city_id,region) values (361026,3610,'宜黄县');
insert into web_regions (region_id,city_id,region) values (361027,3610,'金溪县');
insert into web_regions (region_id,city_id,region) values (361028,3610,'资溪县');
insert into web_regions (region_id,city_id,region) values (361029,3610,'东乡县');
insert into web_regions (region_id,city_id,region) values (361030,3610,'广昌县');
insert into web_regions (region_id,city_id,region) values (361102,3611,'信州区');
insert into web_regions (region_id,city_id,region) values (361121,3611,'上饶县');
insert into web_regions (region_id,city_id,region) values (361122,3611,'广丰县');
insert into web_regions (region_id,city_id,region) values (361123,3611,'玉山县');
insert into web_regions (region_id,city_id,region) values (361124,3611,'铅山县');
insert into web_regions (region_id,city_id,region) values (361125,3611,'横峰县');
insert into web_regions (region_id,city_id,region) values (361126,3611,'弋阳县');
insert into web_regions (region_id,city_id,region) values (361127,3611,'余干县');
insert into web_regions (region_id,city_id,region) values (361128,3611,'鄱阳县');
insert into web_regions (region_id,city_id,region) values (361129,3611,'万年县');
insert into web_regions (region_id,city_id,region) values (361130,3611,'婺源县');
insert into web_regions (region_id,city_id,region) values (361181,3611,'德兴市');
insert into web_regions (region_id,city_id,region) values (370102,3701,'历下区');
insert into web_regions (region_id,city_id,region) values (370103,3701,'市中区');
insert into web_regions (region_id,city_id,region) values (370104,3701,'槐荫区');
insert into web_regions (region_id,city_id,region) values (370105,3701,'天桥区');
insert into web_regions (region_id,city_id,region) values (370112,3701,'历城区');
insert into web_regions (region_id,city_id,region) values (370113,3701,'长清区');
insert into web_regions (region_id,city_id,region) values (370124,3701,'平阴县');
insert into web_regions (region_id,city_id,region) values (370125,3701,'济阳县');
insert into web_regions (region_id,city_id,region) values (370126,3701,'商河县');
insert into web_regions (region_id,city_id,region) values (370181,3701,'章丘市');
insert into web_regions (region_id,city_id,region) values (370202,3702,'市南区');
insert into web_regions (region_id,city_id,region) values (370203,3702,'市北区');
insert into web_regions (region_id,city_id,region) values (370211,3702,'黄岛区');
insert into web_regions (region_id,city_id,region) values (370212,3702,'崂山区');
insert into web_regions (region_id,city_id,region) values (370213,3702,'李沧区');
insert into web_regions (region_id,city_id,region) values (370214,3702,'城阳区');
insert into web_regions (region_id,city_id,region) values (370281,3702,'胶州市');
insert into web_regions (region_id,city_id,region) values (370282,3702,'即墨市');
insert into web_regions (region_id,city_id,region) values (370283,3702,'平度市');
insert into web_regions (region_id,city_id,region) values (370285,3702,'莱西市');
insert into web_regions (region_id,city_id,region) values (370286,3702,'西海岸新区');
insert into web_regions (region_id,city_id,region) values (370302,3703,'淄川区');
insert into web_regions (region_id,city_id,region) values (370303,3703,'张店区');
insert into web_regions (region_id,city_id,region) values (370304,3703,'博山区');
insert into web_regions (region_id,city_id,region) values (370305,3703,'临淄区');
insert into web_regions (region_id,city_id,region) values (370306,3703,'周村区');
insert into web_regions (region_id,city_id,region) values (370321,3703,'桓台县');
insert into web_regions (region_id,city_id,region) values (370322,3703,'高青县');
insert into web_regions (region_id,city_id,region) values (370323,3703,'沂源县');
insert into web_regions (region_id,city_id,region) values (370402,3704,'市中区');
insert into web_regions (region_id,city_id,region) values (370403,3704,'薛城区');
insert into web_regions (region_id,city_id,region) values (370404,3704,'峄城区');
insert into web_regions (region_id,city_id,region) values (370405,3704,'台儿庄区');
insert into web_regions (region_id,city_id,region) values (370406,3704,'山亭区');
insert into web_regions (region_id,city_id,region) values (370481,3704,'滕州市');
insert into web_regions (region_id,city_id,region) values (370502,3705,'东营区');
insert into web_regions (region_id,city_id,region) values (370503,3705,'河口区');
insert into web_regions (region_id,city_id,region) values (370521,3705,'垦利县');
insert into web_regions (region_id,city_id,region) values (370522,3705,'利津县');
insert into web_regions (region_id,city_id,region) values (370523,3705,'广饶县');
insert into web_regions (region_id,city_id,region) values (370602,3706,'芝罘区');
insert into web_regions (region_id,city_id,region) values (370611,3706,'福山区');
insert into web_regions (region_id,city_id,region) values (370612,3706,'牟平区');
insert into web_regions (region_id,city_id,region) values (370613,3706,'莱山区');
insert into web_regions (region_id,city_id,region) values (370634,3706,'长岛县');
insert into web_regions (region_id,city_id,region) values (370681,3706,'龙口市');
insert into web_regions (region_id,city_id,region) values (370682,3706,'莱阳市');
insert into web_regions (region_id,city_id,region) values (370683,3706,'莱州市');
insert into web_regions (region_id,city_id,region) values (370684,3706,'蓬莱市');
insert into web_regions (region_id,city_id,region) values (370685,3706,'招远市');
insert into web_regions (region_id,city_id,region) values (370686,3706,'栖霞市');
insert into web_regions (region_id,city_id,region) values (370687,3706,'海阳市');
insert into web_regions (region_id,city_id,region) values (370702,3707,'潍城区');
insert into web_regions (region_id,city_id,region) values (370703,3707,'寒亭区');
insert into web_regions (region_id,city_id,region) values (370704,3707,'坊子区');
insert into web_regions (region_id,city_id,region) values (370705,3707,'奎文区');
insert into web_regions (region_id,city_id,region) values (370724,3707,'临朐县');
insert into web_regions (region_id,city_id,region) values (370725,3707,'昌乐县');
insert into web_regions (region_id,city_id,region) values (370781,3707,'青州市');
insert into web_regions (region_id,city_id,region) values (370782,3707,'诸城市');
insert into web_regions (region_id,city_id,region) values (370783,3707,'寿光市');
insert into web_regions (region_id,city_id,region) values (370784,3707,'安丘市');
insert into web_regions (region_id,city_id,region) values (370785,3707,'高密市');
insert into web_regions (region_id,city_id,region) values (370786,3707,'昌邑市');
insert into web_regions (region_id,city_id,region) values (370811,3708,'任城区');
insert into web_regions (region_id,city_id,region) values (370812,3708,'兖州区');
insert into web_regions (region_id,city_id,region) values (370826,3708,'微山县');
insert into web_regions (region_id,city_id,region) values (370827,3708,'鱼台县');
insert into web_regions (region_id,city_id,region) values (370828,3708,'金乡县');
insert into web_regions (region_id,city_id,region) values (370829,3708,'嘉祥县');
insert into web_regions (region_id,city_id,region) values (370830,3708,'汶上县');
insert into web_regions (region_id,city_id,region) values (370831,3708,'泗水县');
insert into web_regions (region_id,city_id,region) values (370832,3708,'梁山县');
insert into web_regions (region_id,city_id,region) values (370881,3708,'曲阜市');
insert into web_regions (region_id,city_id,region) values (370883,3708,'邹城市');
insert into web_regions (region_id,city_id,region) values (370902,3709,'泰山区');
insert into web_regions (region_id,city_id,region) values (370911,3709,'岱岳区');
insert into web_regions (region_id,city_id,region) values (370921,3709,'宁阳县');
insert into web_regions (region_id,city_id,region) values (370923,3709,'东平县');
insert into web_regions (region_id,city_id,region) values (370982,3709,'新泰市');
insert into web_regions (region_id,city_id,region) values (370983,3709,'肥城市');
insert into web_regions (region_id,city_id,region) values (371002,3710,'环翠区');
insert into web_regions (region_id,city_id,region) values (371003,3710,'文登区');
insert into web_regions (region_id,city_id,region) values (371082,3710,'荣成市');
insert into web_regions (region_id,city_id,region) values (371083,3710,'乳山市');
insert into web_regions (region_id,city_id,region) values (371102,3711,'东港区');
insert into web_regions (region_id,city_id,region) values (371103,3711,'岚山区');
insert into web_regions (region_id,city_id,region) values (371121,3711,'五莲县');
insert into web_regions (region_id,city_id,region) values (371122,3711,'莒县');
insert into web_regions (region_id,city_id,region) values (371202,3712,'莱城区');
insert into web_regions (region_id,city_id,region) values (371203,3712,'钢城区');
insert into web_regions (region_id,city_id,region) values (371302,3713,'兰山区');
insert into web_regions (region_id,city_id,region) values (371311,3713,'罗庄区');
insert into web_regions (region_id,city_id,region) values (371312,3713,'河东区');
insert into web_regions (region_id,city_id,region) values (371321,3713,'沂南县');
insert into web_regions (region_id,city_id,region) values (371322,3713,'郯城县');
insert into web_regions (region_id,city_id,region) values (371323,3713,'沂水县');
insert into web_regions (region_id,city_id,region) values (371324,3713,'兰陵县');
insert into web_regions (region_id,city_id,region) values (371325,3713,'费县');
insert into web_regions (region_id,city_id,region) values (371326,3713,'平邑县');
insert into web_regions (region_id,city_id,region) values (371327,3713,'莒南县');
insert into web_regions (region_id,city_id,region) values (371328,3713,'蒙阴县');
insert into web_regions (region_id,city_id,region) values (371329,3713,'临沭县');
insert into web_regions (region_id,city_id,region) values (371402,3714,'德城区');
insert into web_regions (region_id,city_id,region) values (371403,3714,'陵城区');
insert into web_regions (region_id,city_id,region) values (371422,3714,'宁津县');
insert into web_regions (region_id,city_id,region) values (371423,3714,'庆云县');
insert into web_regions (region_id,city_id,region) values (371424,3714,'临邑县');
insert into web_regions (region_id,city_id,region) values (371425,3714,'齐河县');
insert into web_regions (region_id,city_id,region) values (371426,3714,'平原县');
insert into web_regions (region_id,city_id,region) values (371427,3714,'夏津县');
insert into web_regions (region_id,city_id,region) values (371428,3714,'武城县');
insert into web_regions (region_id,city_id,region) values (371481,3714,'乐陵市');
insert into web_regions (region_id,city_id,region) values (371482,3714,'禹城市');
insert into web_regions (region_id,city_id,region) values (371502,3715,'东昌府区');
insert into web_regions (region_id,city_id,region) values (371521,3715,'阳谷县');
insert into web_regions (region_id,city_id,region) values (371522,3715,'莘县');
insert into web_regions (region_id,city_id,region) values (371523,3715,'茌平县');
insert into web_regions (region_id,city_id,region) values (371524,3715,'东阿县');
insert into web_regions (region_id,city_id,region) values (371525,3715,'冠县');
insert into web_regions (region_id,city_id,region) values (371526,3715,'高唐县');
insert into web_regions (region_id,city_id,region) values (371581,3715,'临清市');
insert into web_regions (region_id,city_id,region) values (371602,3716,'滨城区');
insert into web_regions (region_id,city_id,region) values (371603,3716,'沾化区');
insert into web_regions (region_id,city_id,region) values (371621,3716,'惠民县');
insert into web_regions (region_id,city_id,region) values (371622,3716,'阳信县');
insert into web_regions (region_id,city_id,region) values (371623,3716,'无棣县');
insert into web_regions (region_id,city_id,region) values (371625,3716,'博兴县');
insert into web_regions (region_id,city_id,region) values (371626,3716,'邹平县');
insert into web_regions (region_id,city_id,region) values (371627,3716,'北海新区');
insert into web_regions (region_id,city_id,region) values (371702,3717,'牡丹区');
insert into web_regions (region_id,city_id,region) values (371721,3717,'曹县');
insert into web_regions (region_id,city_id,region) values (371722,3717,'单县');
insert into web_regions (region_id,city_id,region) values (371723,3717,'成武县');
insert into web_regions (region_id,city_id,region) values (371724,3717,'巨野县');
insert into web_regions (region_id,city_id,region) values (371725,3717,'郓城县');
insert into web_regions (region_id,city_id,region) values (371726,3717,'鄄城县');
insert into web_regions (region_id,city_id,region) values (371727,3717,'定陶县');
insert into web_regions (region_id,city_id,region) values (371728,3717,'东明县');
insert into web_regions (region_id,city_id,region) values (410102,4101,'中原区');
insert into web_regions (region_id,city_id,region) values (410103,4101,'二七区');
insert into web_regions (region_id,city_id,region) values (410104,4101,'管城回族区');
insert into web_regions (region_id,city_id,region) values (410105,4101,'金水区');
insert into web_regions (region_id,city_id,region) values (410106,4101,'上街区');
insert into web_regions (region_id,city_id,region) values (410108,4101,'惠济区');
insert into web_regions (region_id,city_id,region) values (410122,4101,'中牟县');
insert into web_regions (region_id,city_id,region) values (410181,4101,'巩义市');
insert into web_regions (region_id,city_id,region) values (410182,4101,'荥阳市');
insert into web_regions (region_id,city_id,region) values (410183,4101,'新密市');
insert into web_regions (region_id,city_id,region) values (410184,4101,'新郑市');
insert into web_regions (region_id,city_id,region) values (410185,4101,'登封市');
insert into web_regions (region_id,city_id,region) values (410202,4102,'龙亭区');
insert into web_regions (region_id,city_id,region) values (410203,4102,'顺河回族区');
insert into web_regions (region_id,city_id,region) values (410204,4102,'鼓楼区');
insert into web_regions (region_id,city_id,region) values (410205,4102,'禹王台区');
insert into web_regions (region_id,city_id,region) values (410212,4102,'祥符区');
insert into web_regions (region_id,city_id,region) values (410221,4102,'杞县');
insert into web_regions (region_id,city_id,region) values (410222,4102,'通许县');
insert into web_regions (region_id,city_id,region) values (410223,4102,'尉氏县');
insert into web_regions (region_id,city_id,region) values (410225,4102,'兰考县');
insert into web_regions (region_id,city_id,region) values (410302,4103,'老城区');
insert into web_regions (region_id,city_id,region) values (410303,4103,'西工区');
insert into web_regions (region_id,city_id,region) values (410304,4103,'瀍河回族区');
insert into web_regions (region_id,city_id,region) values (410305,4103,'涧西区');
insert into web_regions (region_id,city_id,region) values (410306,4103,'吉利区');
insert into web_regions (region_id,city_id,region) values (410311,4103,'洛龙区');
insert into web_regions (region_id,city_id,region) values (410322,4103,'孟津县');
insert into web_regions (region_id,city_id,region) values (410323,4103,'新安县');
insert into web_regions (region_id,city_id,region) values (410324,4103,'栾川县');
insert into web_regions (region_id,city_id,region) values (410325,4103,'嵩县');
insert into web_regions (region_id,city_id,region) values (410326,4103,'汝阳县');
insert into web_regions (region_id,city_id,region) values (410327,4103,'宜阳县');
insert into web_regions (region_id,city_id,region) values (410328,4103,'洛宁县');
insert into web_regions (region_id,city_id,region) values (410329,4103,'伊川县');
insert into web_regions (region_id,city_id,region) values (410381,4103,'偃师市');
insert into web_regions (region_id,city_id,region) values (410402,4104,'新华区');
insert into web_regions (region_id,city_id,region) values (410403,4104,'卫东区');
insert into web_regions (region_id,city_id,region) values (410404,4104,'石龙区');
insert into web_regions (region_id,city_id,region) values (410411,4104,'湛河区');
insert into web_regions (region_id,city_id,region) values (410421,4104,'宝丰县');
insert into web_regions (region_id,city_id,region) values (410422,4104,'叶县');
insert into web_regions (region_id,city_id,region) values (410423,4104,'鲁山县');
insert into web_regions (region_id,city_id,region) values (410425,4104,'郏县');
insert into web_regions (region_id,city_id,region) values (410481,4104,'舞钢市');
insert into web_regions (region_id,city_id,region) values (410482,4104,'汝州市');
insert into web_regions (region_id,city_id,region) values (410502,4105,'文峰区');
insert into web_regions (region_id,city_id,region) values (410503,4105,'北关区');
insert into web_regions (region_id,city_id,region) values (410505,4105,'殷都区');
insert into web_regions (region_id,city_id,region) values (410506,4105,'龙安区');
insert into web_regions (region_id,city_id,region) values (410522,4105,'安阳县');
insert into web_regions (region_id,city_id,region) values (410523,4105,'汤阴县');
insert into web_regions (region_id,city_id,region) values (410526,4105,'滑县');
insert into web_regions (region_id,city_id,region) values (410527,4105,'内黄县');
insert into web_regions (region_id,city_id,region) values (410581,4105,'林州市');
insert into web_regions (region_id,city_id,region) values (410602,4106,'鹤山区');
insert into web_regions (region_id,city_id,region) values (410603,4106,'山城区');
insert into web_regions (region_id,city_id,region) values (410611,4106,'淇滨区');
insert into web_regions (region_id,city_id,region) values (410621,4106,'浚县');
insert into web_regions (region_id,city_id,region) values (410622,4106,'淇县');
insert into web_regions (region_id,city_id,region) values (410702,4107,'红旗区');
insert into web_regions (region_id,city_id,region) values (410703,4107,'卫滨区');
insert into web_regions (region_id,city_id,region) values (410704,4107,'凤泉区');
insert into web_regions (region_id,city_id,region) values (410711,4107,'牧野区');
insert into web_regions (region_id,city_id,region) values (410721,4107,'新乡县');
insert into web_regions (region_id,city_id,region) values (410724,4107,'获嘉县');
insert into web_regions (region_id,city_id,region) values (410725,4107,'原阳县');
insert into web_regions (region_id,city_id,region) values (410726,4107,'延津县');
insert into web_regions (region_id,city_id,region) values (410727,4107,'封丘县');
insert into web_regions (region_id,city_id,region) values (410728,4107,'长垣县');
insert into web_regions (region_id,city_id,region) values (410781,4107,'卫辉市');
insert into web_regions (region_id,city_id,region) values (410782,4107,'辉县市');
insert into web_regions (region_id,city_id,region) values (410802,4108,'解放区');
insert into web_regions (region_id,city_id,region) values (410803,4108,'中站区');
insert into web_regions (region_id,city_id,region) values (410804,4108,'马村区');
insert into web_regions (region_id,city_id,region) values (410811,4108,'山阳区');
insert into web_regions (region_id,city_id,region) values (410821,4108,'修武县');
insert into web_regions (region_id,city_id,region) values (410822,4108,'博爱县');
insert into web_regions (region_id,city_id,region) values (410823,4108,'武陟县');
insert into web_regions (region_id,city_id,region) values (410825,4108,'温县');
insert into web_regions (region_id,city_id,region) values (410882,4108,'沁阳市');
insert into web_regions (region_id,city_id,region) values (410883,4108,'孟州市');
insert into web_regions (region_id,city_id,region) values (410902,4109,'华龙区');
insert into web_regions (region_id,city_id,region) values (410922,4109,'清丰县');
insert into web_regions (region_id,city_id,region) values (410923,4109,'南乐县');
insert into web_regions (region_id,city_id,region) values (410926,4109,'范县');
insert into web_regions (region_id,city_id,region) values (410927,4109,'台前县');
insert into web_regions (region_id,city_id,region) values (410928,4109,'濮阳县');
insert into web_regions (region_id,city_id,region) values (411002,4110,'魏都区');
insert into web_regions (region_id,city_id,region) values (411023,4110,'许昌县');
insert into web_regions (region_id,city_id,region) values (411024,4110,'鄢陵县');
insert into web_regions (region_id,city_id,region) values (411025,4110,'襄城县');
insert into web_regions (region_id,city_id,region) values (411081,4110,'禹州市');
insert into web_regions (region_id,city_id,region) values (411082,4110,'长葛市');
insert into web_regions (region_id,city_id,region) values (411102,4111,'源汇区');
insert into web_regions (region_id,city_id,region) values (411103,4111,'郾城区');
insert into web_regions (region_id,city_id,region) values (411104,4111,'召陵区');
insert into web_regions (region_id,city_id,region) values (411121,4111,'舞阳县');
insert into web_regions (region_id,city_id,region) values (411122,4111,'临颍县');
insert into web_regions (region_id,city_id,region) values (411202,4112,'湖滨区');
insert into web_regions (region_id,city_id,region) values (411221,4112,'渑池县');
insert into web_regions (region_id,city_id,region) values (411222,4112,'陕县');
insert into web_regions (region_id,city_id,region) values (411224,4112,'卢氏县');
insert into web_regions (region_id,city_id,region) values (411281,4112,'义马市');
insert into web_regions (region_id,city_id,region) values (411282,4112,'灵宝市');
insert into web_regions (region_id,city_id,region) values (411302,4113,'宛城区');
insert into web_regions (region_id,city_id,region) values (411303,4113,'卧龙区');
insert into web_regions (region_id,city_id,region) values (411321,4113,'南召县');
insert into web_regions (region_id,city_id,region) values (411322,4113,'方城县');
insert into web_regions (region_id,city_id,region) values (411323,4113,'西峡县');
insert into web_regions (region_id,city_id,region) values (411324,4113,'镇平县');
insert into web_regions (region_id,city_id,region) values (411325,4113,'内乡县');
insert into web_regions (region_id,city_id,region) values (411326,4113,'淅川县');
insert into web_regions (region_id,city_id,region) values (411327,4113,'社旗县');
insert into web_regions (region_id,city_id,region) values (411328,4113,'唐河县');
insert into web_regions (region_id,city_id,region) values (411329,4113,'新野县');
insert into web_regions (region_id,city_id,region) values (411330,4113,'桐柏县');
insert into web_regions (region_id,city_id,region) values (411381,4113,'邓州市');
insert into web_regions (region_id,city_id,region) values (411402,4114,'梁园区');
insert into web_regions (region_id,city_id,region) values (411403,4114,'睢阳区');
insert into web_regions (region_id,city_id,region) values (411421,4114,'民权县');
insert into web_regions (region_id,city_id,region) values (411422,4114,'睢县');
insert into web_regions (region_id,city_id,region) values (411423,4114,'宁陵县');
insert into web_regions (region_id,city_id,region) values (411424,4114,'柘城县');
insert into web_regions (region_id,city_id,region) values (411425,4114,'虞城县');
insert into web_regions (region_id,city_id,region) values (411426,4114,'夏邑县');
insert into web_regions (region_id,city_id,region) values (411481,4114,'永城市');
insert into web_regions (region_id,city_id,region) values (411502,4115,'浉河区');
insert into web_regions (region_id,city_id,region) values (411503,4115,'平桥区');
insert into web_regions (region_id,city_id,region) values (411521,4115,'罗山县');
insert into web_regions (region_id,city_id,region) values (411522,4115,'光山县');
insert into web_regions (region_id,city_id,region) values (411523,4115,'新县');
insert into web_regions (region_id,city_id,region) values (411524,4115,'商城县');
insert into web_regions (region_id,city_id,region) values (411525,4115,'固始县');
insert into web_regions (region_id,city_id,region) values (411526,4115,'潢川县');
insert into web_regions (region_id,city_id,region) values (411527,4115,'淮滨县');
insert into web_regions (region_id,city_id,region) values (411528,4115,'息县');
insert into web_regions (region_id,city_id,region) values (411602,4116,'川汇区');
insert into web_regions (region_id,city_id,region) values (411621,4116,'扶沟县');
insert into web_regions (region_id,city_id,region) values (411622,4116,'西华县');
insert into web_regions (region_id,city_id,region) values (411623,4116,'商水县');
insert into web_regions (region_id,city_id,region) values (411624,4116,'沈丘县');
insert into web_regions (region_id,city_id,region) values (411625,4116,'郸城县');
insert into web_regions (region_id,city_id,region) values (411626,4116,'淮阳县');
insert into web_regions (region_id,city_id,region) values (411627,4116,'太康县');
insert into web_regions (region_id,city_id,region) values (411628,4116,'鹿邑县');
insert into web_regions (region_id,city_id,region) values (411681,4116,'项城市');
insert into web_regions (region_id,city_id,region) values (411702,4117,'驿城区');
insert into web_regions (region_id,city_id,region) values (411721,4117,'西平县');
insert into web_regions (region_id,city_id,region) values (411722,4117,'上蔡县');
insert into web_regions (region_id,city_id,region) values (411723,4117,'平舆县');
insert into web_regions (region_id,city_id,region) values (411724,4117,'正阳县');
insert into web_regions (region_id,city_id,region) values (411725,4117,'确山县');
insert into web_regions (region_id,city_id,region) values (411726,4117,'泌阳县');
insert into web_regions (region_id,city_id,region) values (411727,4117,'汝南县');
insert into web_regions (region_id,city_id,region) values (411728,4117,'遂平县');
insert into web_regions (region_id,city_id,region) values (411729,4117,'新蔡县');
insert into web_regions (region_id,city_id,region) values (419001,4190,'济源市');
insert into web_regions (region_id,city_id,region) values (420102,4201,'江岸区');
insert into web_regions (region_id,city_id,region) values (420103,4201,'江汉区');
insert into web_regions (region_id,city_id,region) values (420104,4201,'硚口区');
insert into web_regions (region_id,city_id,region) values (420105,4201,'汉阳区');
insert into web_regions (region_id,city_id,region) values (420106,4201,'武昌区');
insert into web_regions (region_id,city_id,region) values (420107,4201,'青山区');
insert into web_regions (region_id,city_id,region) values (420111,4201,'洪山区');
insert into web_regions (region_id,city_id,region) values (420112,4201,'东西湖区');
insert into web_regions (region_id,city_id,region) values (420113,4201,'汉南区');
insert into web_regions (region_id,city_id,region) values (420114,4201,'蔡甸区');
insert into web_regions (region_id,city_id,region) values (420115,4201,'江夏区');
insert into web_regions (region_id,city_id,region) values (420116,4201,'黄陂区');
insert into web_regions (region_id,city_id,region) values (420117,4201,'新洲区');
insert into web_regions (region_id,city_id,region) values (420202,4202,'黄石港区');
insert into web_regions (region_id,city_id,region) values (420203,4202,'西塞山区');
insert into web_regions (region_id,city_id,region) values (420204,4202,'下陆区');
insert into web_regions (region_id,city_id,region) values (420205,4202,'铁山区');
insert into web_regions (region_id,city_id,region) values (420222,4202,'阳新县');
insert into web_regions (region_id,city_id,region) values (420281,4202,'大冶市');
insert into web_regions (region_id,city_id,region) values (420302,4203,'茅箭区');
insert into web_regions (region_id,city_id,region) values (420303,4203,'张湾区');
insert into web_regions (region_id,city_id,region) values (420304,4203,'郧阳区');
insert into web_regions (region_id,city_id,region) values (420322,4203,'郧西县');
insert into web_regions (region_id,city_id,region) values (420323,4203,'竹山县');
insert into web_regions (region_id,city_id,region) values (420324,4203,'竹溪县');
insert into web_regions (region_id,city_id,region) values (420325,4203,'房县');
insert into web_regions (region_id,city_id,region) values (420381,4203,'丹江口市');
insert into web_regions (region_id,city_id,region) values (420502,4205,'西陵区');
insert into web_regions (region_id,city_id,region) values (420503,4205,'伍家岗区');
insert into web_regions (region_id,city_id,region) values (420504,4205,'点军区');
insert into web_regions (region_id,city_id,region) values (420505,4205,'猇亭区');
insert into web_regions (region_id,city_id,region) values (420506,4205,'夷陵区');
insert into web_regions (region_id,city_id,region) values (420525,4205,'远安县');
insert into web_regions (region_id,city_id,region) values (420526,4205,'兴山县');
insert into web_regions (region_id,city_id,region) values (420527,4205,'秭归县');
insert into web_regions (region_id,city_id,region) values (420528,4205,'长阳土家族自治县');
insert into web_regions (region_id,city_id,region) values (420529,4205,'五峰土家族自治县');
insert into web_regions (region_id,city_id,region) values (420581,4205,'宜都市');
insert into web_regions (region_id,city_id,region) values (420582,4205,'当阳市');
insert into web_regions (region_id,city_id,region) values (420583,4205,'枝江市');
insert into web_regions (region_id,city_id,region) values (420602,4206,'襄城区');
insert into web_regions (region_id,city_id,region) values (420606,4206,'樊城区');
insert into web_regions (region_id,city_id,region) values (420607,4206,'襄州区');
insert into web_regions (region_id,city_id,region) values (420624,4206,'南漳县');
insert into web_regions (region_id,city_id,region) values (420625,4206,'谷城县');
insert into web_regions (region_id,city_id,region) values (420626,4206,'保康县');
insert into web_regions (region_id,city_id,region) values (420682,4206,'老河口市');
insert into web_regions (region_id,city_id,region) values (420683,4206,'枣阳市');
insert into web_regions (region_id,city_id,region) values (420684,4206,'宜城市');
insert into web_regions (region_id,city_id,region) values (420702,4207,'梁子湖区');
insert into web_regions (region_id,city_id,region) values (420703,4207,'华容区');
insert into web_regions (region_id,city_id,region) values (420704,4207,'鄂城区');
insert into web_regions (region_id,city_id,region) values (420802,4208,'东宝区');
insert into web_regions (region_id,city_id,region) values (420804,4208,'掇刀区');
insert into web_regions (region_id,city_id,region) values (420821,4208,'京山县');
insert into web_regions (region_id,city_id,region) values (420822,4208,'沙洋县');
insert into web_regions (region_id,city_id,region) values (420881,4208,'钟祥市');
insert into web_regions (region_id,city_id,region) values (420902,4209,'孝南区');
insert into web_regions (region_id,city_id,region) values (420921,4209,'孝昌县');
insert into web_regions (region_id,city_id,region) values (420922,4209,'大悟县');
insert into web_regions (region_id,city_id,region) values (420923,4209,'云梦县');
insert into web_regions (region_id,city_id,region) values (420981,4209,'应城市');
insert into web_regions (region_id,city_id,region) values (420982,4209,'安陆市');
insert into web_regions (region_id,city_id,region) values (420984,4209,'汉川市');
insert into web_regions (region_id,city_id,region) values (421002,4210,'沙市区');
insert into web_regions (region_id,city_id,region) values (421003,4210,'荆州区');
insert into web_regions (region_id,city_id,region) values (421022,4210,'公安县');
insert into web_regions (region_id,city_id,region) values (421023,4210,'监利县');
insert into web_regions (region_id,city_id,region) values (421024,4210,'江陵县');
insert into web_regions (region_id,city_id,region) values (421081,4210,'石首市');
insert into web_regions (region_id,city_id,region) values (421083,4210,'洪湖市');
insert into web_regions (region_id,city_id,region) values (421087,4210,'松滋市');
insert into web_regions (region_id,city_id,region) values (421102,4211,'黄州区');
insert into web_regions (region_id,city_id,region) values (421121,4211,'团风县');
insert into web_regions (region_id,city_id,region) values (421122,4211,'红安县');
insert into web_regions (region_id,city_id,region) values (421123,4211,'罗田县');
insert into web_regions (region_id,city_id,region) values (421124,4211,'英山县');
insert into web_regions (region_id,city_id,region) values (421125,4211,'浠水县');
insert into web_regions (region_id,city_id,region) values (421126,4211,'蕲春县');
insert into web_regions (region_id,city_id,region) values (421127,4211,'黄梅县');
insert into web_regions (region_id,city_id,region) values (421181,4211,'麻城市');
insert into web_regions (region_id,city_id,region) values (421182,4211,'武穴市');
insert into web_regions (region_id,city_id,region) values (421202,4212,'咸安区');
insert into web_regions (region_id,city_id,region) values (421221,4212,'嘉鱼县');
insert into web_regions (region_id,city_id,region) values (421222,4212,'通城县');
insert into web_regions (region_id,city_id,region) values (421223,4212,'崇阳县');
insert into web_regions (region_id,city_id,region) values (421224,4212,'通山县');
insert into web_regions (region_id,city_id,region) values (421281,4212,'赤壁市');
insert into web_regions (region_id,city_id,region) values (421303,4213,'曾都区');
insert into web_regions (region_id,city_id,region) values (421321,4213,'随县');
insert into web_regions (region_id,city_id,region) values (421381,4213,'广水市');
insert into web_regions (region_id,city_id,region) values (422801,4228,'恩施市');
insert into web_regions (region_id,city_id,region) values (422802,4228,'利川市');
insert into web_regions (region_id,city_id,region) values (422822,4228,'建始县');
insert into web_regions (region_id,city_id,region) values (422823,4228,'巴东县');
insert into web_regions (region_id,city_id,region) values (422825,4228,'宣恩县');
insert into web_regions (region_id,city_id,region) values (422826,4228,'咸丰县');
insert into web_regions (region_id,city_id,region) values (422827,4228,'来凤县');
insert into web_regions (region_id,city_id,region) values (422828,4228,'鹤峰县');
insert into web_regions (region_id,city_id,region) values (429004,4290,'仙桃市');
insert into web_regions (region_id,city_id,region) values (429005,4290,'潜江市');
insert into web_regions (region_id,city_id,region) values (429006,4290,'天门市');
insert into web_regions (region_id,city_id,region) values (429021,4290,'神农架林区');
insert into web_regions (region_id,city_id,region) values (430102,4301,'芙蓉区');
insert into web_regions (region_id,city_id,region) values (430103,4301,'天心区');
insert into web_regions (region_id,city_id,region) values (430104,4301,'岳麓区');
insert into web_regions (region_id,city_id,region) values (430105,4301,'开福区');
insert into web_regions (region_id,city_id,region) values (430111,4301,'雨花区');
insert into web_regions (region_id,city_id,region) values (430112,4301,'望城区');
insert into web_regions (region_id,city_id,region) values (430121,4301,'长沙县');
insert into web_regions (region_id,city_id,region) values (430124,4301,'宁乡县');
insert into web_regions (region_id,city_id,region) values (430181,4301,'浏阳市');
insert into web_regions (region_id,city_id,region) values (430202,4302,'荷塘区');
insert into web_regions (region_id,city_id,region) values (430203,4302,'芦淞区');
insert into web_regions (region_id,city_id,region) values (430204,4302,'石峰区');
insert into web_regions (region_id,city_id,region) values (430211,4302,'天元区');
insert into web_regions (region_id,city_id,region) values (430221,4302,'株洲县');
insert into web_regions (region_id,city_id,region) values (430223,4302,'攸县');
insert into web_regions (region_id,city_id,region) values (430224,4302,'茶陵县');
insert into web_regions (region_id,city_id,region) values (430225,4302,'炎陵县');
insert into web_regions (region_id,city_id,region) values (430281,4302,'醴陵市');
insert into web_regions (region_id,city_id,region) values (430302,4303,'雨湖区');
insert into web_regions (region_id,city_id,region) values (430304,4303,'岳塘区');
insert into web_regions (region_id,city_id,region) values (430321,4303,'湘潭县');
insert into web_regions (region_id,city_id,region) values (430381,4303,'湘乡市');
insert into web_regions (region_id,city_id,region) values (430382,4303,'韶山市');
insert into web_regions (region_id,city_id,region) values (430405,4304,'珠晖区');
insert into web_regions (region_id,city_id,region) values (430406,4304,'雁峰区');
insert into web_regions (region_id,city_id,region) values (430407,4304,'石鼓区');
insert into web_regions (region_id,city_id,region) values (430408,4304,'蒸湘区');
insert into web_regions (region_id,city_id,region) values (430412,4304,'南岳区');
insert into web_regions (region_id,city_id,region) values (430421,4304,'衡阳县');
insert into web_regions (region_id,city_id,region) values (430422,4304,'衡南县');
insert into web_regions (region_id,city_id,region) values (430423,4304,'衡山县');
insert into web_regions (region_id,city_id,region) values (430424,4304,'衡东县');
insert into web_regions (region_id,city_id,region) values (430426,4304,'祁东县');
insert into web_regions (region_id,city_id,region) values (430481,4304,'耒阳市');
insert into web_regions (region_id,city_id,region) values (430482,4304,'常宁市');
insert into web_regions (region_id,city_id,region) values (430502,4305,'双清区');
insert into web_regions (region_id,city_id,region) values (430503,4305,'大祥区');
insert into web_regions (region_id,city_id,region) values (430511,4305,'北塔区');
insert into web_regions (region_id,city_id,region) values (430521,4305,'邵东县');
insert into web_regions (region_id,city_id,region) values (430522,4305,'新邵县');
insert into web_regions (region_id,city_id,region) values (430523,4305,'邵阳县');
insert into web_regions (region_id,city_id,region) values (430524,4305,'隆回县');
insert into web_regions (region_id,city_id,region) values (430525,4305,'洞口县');
insert into web_regions (region_id,city_id,region) values (430527,4305,'绥宁县');
insert into web_regions (region_id,city_id,region) values (430528,4305,'新宁县');
insert into web_regions (region_id,city_id,region) values (430529,4305,'城步苗族自治县');
insert into web_regions (region_id,city_id,region) values (430581,4305,'武冈市');
insert into web_regions (region_id,city_id,region) values (430602,4306,'岳阳楼区');
insert into web_regions (region_id,city_id,region) values (430603,4306,'云溪区');
insert into web_regions (region_id,city_id,region) values (430611,4306,'君山区');
insert into web_regions (region_id,city_id,region) values (430621,4306,'岳阳县');
insert into web_regions (region_id,city_id,region) values (430623,4306,'华容县');
insert into web_regions (region_id,city_id,region) values (430624,4306,'湘阴县');
insert into web_regions (region_id,city_id,region) values (430626,4306,'平江县');
insert into web_regions (region_id,city_id,region) values (430681,4306,'汨罗市');
insert into web_regions (region_id,city_id,region) values (430682,4306,'临湘市');
insert into web_regions (region_id,city_id,region) values (430702,4307,'武陵区');
insert into web_regions (region_id,city_id,region) values (430703,4307,'鼎城区');
insert into web_regions (region_id,city_id,region) values (430721,4307,'安乡县');
insert into web_regions (region_id,city_id,region) values (430722,4307,'汉寿县');
insert into web_regions (region_id,city_id,region) values (430723,4307,'澧县');
insert into web_regions (region_id,city_id,region) values (430724,4307,'临澧县');
insert into web_regions (region_id,city_id,region) values (430725,4307,'桃源县');
insert into web_regions (region_id,city_id,region) values (430726,4307,'石门县');
insert into web_regions (region_id,city_id,region) values (430781,4307,'津市市');
insert into web_regions (region_id,city_id,region) values (430802,4308,'永定区');
insert into web_regions (region_id,city_id,region) values (430811,4308,'武陵源区');
insert into web_regions (region_id,city_id,region) values (430821,4308,'慈利县');
insert into web_regions (region_id,city_id,region) values (430822,4308,'桑植县');
insert into web_regions (region_id,city_id,region) values (430902,4309,'资阳区');
insert into web_regions (region_id,city_id,region) values (430903,4309,'赫山区');
insert into web_regions (region_id,city_id,region) values (430921,4309,'南县');
insert into web_regions (region_id,city_id,region) values (430922,4309,'桃江县');
insert into web_regions (region_id,city_id,region) values (430923,4309,'安化县');
insert into web_regions (region_id,city_id,region) values (430981,4309,'沅江市');
insert into web_regions (region_id,city_id,region) values (431002,4310,'北湖区');
insert into web_regions (region_id,city_id,region) values (431003,4310,'苏仙区');
insert into web_regions (region_id,city_id,region) values (431021,4310,'桂阳县');
insert into web_regions (region_id,city_id,region) values (431022,4310,'宜章县');
insert into web_regions (region_id,city_id,region) values (431023,4310,'永兴县');
insert into web_regions (region_id,city_id,region) values (431024,4310,'嘉禾县');
insert into web_regions (region_id,city_id,region) values (431025,4310,'临武县');
insert into web_regions (region_id,city_id,region) values (431026,4310,'汝城县');
insert into web_regions (region_id,city_id,region) values (431027,4310,'桂东县');
insert into web_regions (region_id,city_id,region) values (431028,4310,'安仁县');
insert into web_regions (region_id,city_id,region) values (431081,4310,'资兴市');
insert into web_regions (region_id,city_id,region) values (431102,4311,'零陵区');
insert into web_regions (region_id,city_id,region) values (431103,4311,'冷水滩区');
insert into web_regions (region_id,city_id,region) values (431121,4311,'祁阳县');
insert into web_regions (region_id,city_id,region) values (431122,4311,'东安县');
insert into web_regions (region_id,city_id,region) values (431123,4311,'双牌县');
insert into web_regions (region_id,city_id,region) values (431124,4311,'道县');
insert into web_regions (region_id,city_id,region) values (431125,4311,'江永县');
insert into web_regions (region_id,city_id,region) values (431126,4311,'宁远县');
insert into web_regions (region_id,city_id,region) values (431127,4311,'蓝山县');
insert into web_regions (region_id,city_id,region) values (431128,4311,'新田县');
insert into web_regions (region_id,city_id,region) values (431129,4311,'江华瑶族自治县');
insert into web_regions (region_id,city_id,region) values (431202,4312,'鹤城区');
insert into web_regions (region_id,city_id,region) values (431221,4312,'中方县');
insert into web_regions (region_id,city_id,region) values (431222,4312,'沅陵县');
insert into web_regions (region_id,city_id,region) values (431223,4312,'辰溪县');
insert into web_regions (region_id,city_id,region) values (431224,4312,'溆浦县');
insert into web_regions (region_id,city_id,region) values (431225,4312,'会同县');
insert into web_regions (region_id,city_id,region) values (431226,4312,'麻阳苗族自治县');
insert into web_regions (region_id,city_id,region) values (431227,4312,'新晃侗族自治县');
insert into web_regions (region_id,city_id,region) values (431228,4312,'芷江侗族自治县');
insert into web_regions (region_id,city_id,region) values (431229,4312,'靖州苗族侗族自治县');
insert into web_regions (region_id,city_id,region) values (431230,4312,'通道侗族自治县');
insert into web_regions (region_id,city_id,region) values (431281,4312,'洪江市');
insert into web_regions (region_id,city_id,region) values (431302,4313,'娄星区');
insert into web_regions (region_id,city_id,region) values (431321,4313,'双峰县');
insert into web_regions (region_id,city_id,region) values (431322,4313,'新化县');
insert into web_regions (region_id,city_id,region) values (431381,4313,'冷水江市');
insert into web_regions (region_id,city_id,region) values (431382,4313,'涟源市');
insert into web_regions (region_id,city_id,region) values (433101,4331,'吉首市');
insert into web_regions (region_id,city_id,region) values (433122,4331,'泸溪县');
insert into web_regions (region_id,city_id,region) values (433123,4331,'凤凰县');
insert into web_regions (region_id,city_id,region) values (433124,4331,'花垣县');
insert into web_regions (region_id,city_id,region) values (433125,4331,'保靖县');
insert into web_regions (region_id,city_id,region) values (433126,4331,'古丈县');
insert into web_regions (region_id,city_id,region) values (433127,4331,'永顺县');
insert into web_regions (region_id,city_id,region) values (433130,4331,'龙山县');
insert into web_regions (region_id,city_id,region) values (440103,4401,'荔湾区');
insert into web_regions (region_id,city_id,region) values (440104,4401,'越秀区');
insert into web_regions (region_id,city_id,region) values (440105,4401,'海珠区');
insert into web_regions (region_id,city_id,region) values (440106,4401,'天河区');
insert into web_regions (region_id,city_id,region) values (440111,4401,'白云区');
insert into web_regions (region_id,city_id,region) values (440112,4401,'黄埔区');
insert into web_regions (region_id,city_id,region) values (440113,4401,'番禺区');
insert into web_regions (region_id,city_id,region) values (440114,4401,'花都区');
insert into web_regions (region_id,city_id,region) values (440115,4401,'南沙区');
insert into web_regions (region_id,city_id,region) values (440117,4401,'从化区');
insert into web_regions (region_id,city_id,region) values (440118,4401,'增城区');
insert into web_regions (region_id,city_id,region) values (440119,4401,'萝岗区');
insert into web_regions (region_id,city_id,region) values (440203,4402,'武江区');
insert into web_regions (region_id,city_id,region) values (440204,4402,'浈江区');
insert into web_regions (region_id,city_id,region) values (440205,4402,'曲江区');
insert into web_regions (region_id,city_id,region) values (440222,4402,'始兴县');
insert into web_regions (region_id,city_id,region) values (440224,4402,'仁化县');
insert into web_regions (region_id,city_id,region) values (440229,4402,'翁源县');
insert into web_regions (region_id,city_id,region) values (440232,4402,'乳源瑶族自治县');
insert into web_regions (region_id,city_id,region) values (440233,4402,'新丰县');
insert into web_regions (region_id,city_id,region) values (440281,4402,'乐昌市');
insert into web_regions (region_id,city_id,region) values (440282,4402,'南雄市');
insert into web_regions (region_id,city_id,region) values (440303,4403,'罗湖区');
insert into web_regions (region_id,city_id,region) values (440304,4403,'福田区');
insert into web_regions (region_id,city_id,region) values (440305,4403,'南山区');
insert into web_regions (region_id,city_id,region) values (440306,4403,'宝安区');
insert into web_regions (region_id,city_id,region) values (440307,4403,'龙岗区');
insert into web_regions (region_id,city_id,region) values (440308,4403,'盐田区');
insert into web_regions (region_id,city_id,region) values (440309,4403,'光明新区');
insert into web_regions (region_id,city_id,region) values (440310,4403,'坪山新区');
insert into web_regions (region_id,city_id,region) values (440311,4403,'大鹏新区');
insert into web_regions (region_id,city_id,region) values (440312,4403,'龙华新区');
insert into web_regions (region_id,city_id,region) values (440402,4404,'香洲区');
insert into web_regions (region_id,city_id,region) values (440403,4404,'斗门区');
insert into web_regions (region_id,city_id,region) values (440404,4404,'金湾区');
insert into web_regions (region_id,city_id,region) values (440507,4405,'龙湖区');
insert into web_regions (region_id,city_id,region) values (440511,4405,'金平区');
insert into web_regions (region_id,city_id,region) values (440512,4405,'濠江区');
insert into web_regions (region_id,city_id,region) values (440513,4405,'潮阳区');
insert into web_regions (region_id,city_id,region) values (440514,4405,'潮南区');
insert into web_regions (region_id,city_id,region) values (440515,4405,'澄海区');
insert into web_regions (region_id,city_id,region) values (440523,4405,'南澳县');
insert into web_regions (region_id,city_id,region) values (440604,4406,'禅城区');
insert into web_regions (region_id,city_id,region) values (440605,4406,'南海区');
insert into web_regions (region_id,city_id,region) values (440606,4406,'顺德区');
insert into web_regions (region_id,city_id,region) values (440607,4406,'三水区');
insert into web_regions (region_id,city_id,region) values (440608,4406,'高明区');
insert into web_regions (region_id,city_id,region) values (440703,4407,'蓬江区');
insert into web_regions (region_id,city_id,region) values (440704,4407,'江海区');
insert into web_regions (region_id,city_id,region) values (440705,4407,'新会区');
insert into web_regions (region_id,city_id,region) values (440781,4407,'台山市');
insert into web_regions (region_id,city_id,region) values (440783,4407,'开平市');
insert into web_regions (region_id,city_id,region) values (440784,4407,'鹤山市');
insert into web_regions (region_id,city_id,region) values (440785,4407,'恩平市');
insert into web_regions (region_id,city_id,region) values (440802,4408,'赤坎区');
insert into web_regions (region_id,city_id,region) values (440803,4408,'霞山区');
insert into web_regions (region_id,city_id,region) values (440804,4408,'坡头区');
insert into web_regions (region_id,city_id,region) values (440811,4408,'麻章区');
insert into web_regions (region_id,city_id,region) values (440823,4408,'遂溪县');
insert into web_regions (region_id,city_id,region) values (440825,4408,'徐闻县');
insert into web_regions (region_id,city_id,region) values (440881,4408,'廉江市');
insert into web_regions (region_id,city_id,region) values (440882,4408,'雷州市');
insert into web_regions (region_id,city_id,region) values (440883,4408,'吴川市');
insert into web_regions (region_id,city_id,region) values (440902,4409,'茂南区');
insert into web_regions (region_id,city_id,region) values (440904,4409,'电白区');
insert into web_regions (region_id,city_id,region) values (440981,4409,'高州市');
insert into web_regions (region_id,city_id,region) values (440982,4409,'化州市');
insert into web_regions (region_id,city_id,region) values (440983,4409,'信宜市');
insert into web_regions (region_id,city_id,region) values (441202,4412,'端州区');
insert into web_regions (region_id,city_id,region) values (441203,4412,'鼎湖区');
insert into web_regions (region_id,city_id,region) values (441223,4412,'广宁县');
insert into web_regions (region_id,city_id,region) values (441224,4412,'怀集县');
insert into web_regions (region_id,city_id,region) values (441225,4412,'封开县');
insert into web_regions (region_id,city_id,region) values (441226,4412,'德庆县');
insert into web_regions (region_id,city_id,region) values (441283,4412,'高要市');
insert into web_regions (region_id,city_id,region) values (441284,4412,'四会市');
insert into web_regions (region_id,city_id,region) values (441302,4413,'惠城区');
insert into web_regions (region_id,city_id,region) values (441303,4413,'惠阳区');
insert into web_regions (region_id,city_id,region) values (441322,4413,'博罗县');
insert into web_regions (region_id,city_id,region) values (441323,4413,'惠东县');
insert into web_regions (region_id,city_id,region) values (441324,4413,'龙门县');
insert into web_regions (region_id,city_id,region) values (441402,4414,'梅江区');
insert into web_regions (region_id,city_id,region) values (441403,4414,'梅县区');
insert into web_regions (region_id,city_id,region) values (441422,4414,'大埔县');
insert into web_regions (region_id,city_id,region) values (441423,4414,'丰顺县');
insert into web_regions (region_id,city_id,region) values (441424,4414,'五华县');
insert into web_regions (region_id,city_id,region) values (441426,4414,'平远县');
insert into web_regions (region_id,city_id,region) values (441427,4414,'蕉岭县');
insert into web_regions (region_id,city_id,region) values (441481,4414,'兴宁市');
insert into web_regions (region_id,city_id,region) values (441502,4415,'城区');
insert into web_regions (region_id,city_id,region) values (441521,4415,'海丰县');
insert into web_regions (region_id,city_id,region) values (441523,4415,'陆河县');
insert into web_regions (region_id,city_id,region) values (441581,4415,'陆丰市');
insert into web_regions (region_id,city_id,region) values (441602,4416,'源城区');
insert into web_regions (region_id,city_id,region) values (441621,4416,'紫金县');
insert into web_regions (region_id,city_id,region) values (441622,4416,'龙川县');
insert into web_regions (region_id,city_id,region) values (441623,4416,'连平县');
insert into web_regions (region_id,city_id,region) values (441624,4416,'和平县');
insert into web_regions (region_id,city_id,region) values (441625,4416,'东源县');
insert into web_regions (region_id,city_id,region) values (441702,4417,'江城区');
insert into web_regions (region_id,city_id,region) values (441704,4417,'阳东区');
insert into web_regions (region_id,city_id,region) values (441721,4417,'阳西县');
insert into web_regions (region_id,city_id,region) values (441781,4417,'阳春市');
insert into web_regions (region_id,city_id,region) values (441802,4418,'清城区');
insert into web_regions (region_id,city_id,region) values (441803,4418,'清新区');
insert into web_regions (region_id,city_id,region) values (441821,4418,'佛冈县');
insert into web_regions (region_id,city_id,region) values (441823,4418,'阳山县');
insert into web_regions (region_id,city_id,region) values (441825,4418,'连山壮族瑶族自治县');
insert into web_regions (region_id,city_id,region) values (441826,4418,'连南瑶族自治县');
insert into web_regions (region_id,city_id,region) values (441881,4418,'英德市');
insert into web_regions (region_id,city_id,region) values (441882,4418,'连州市');
insert into web_regions (region_id,city_id,region) values (441901,4419,'莞城区');
insert into web_regions (region_id,city_id,region) values (441902,4419,'南城区');
insert into web_regions (region_id,city_id,region) values (441904,4419,'万江区');
insert into web_regions (region_id,city_id,region) values (441905,4419,'石碣镇');
insert into web_regions (region_id,city_id,region) values (441906,4419,'石龙镇');
insert into web_regions (region_id,city_id,region) values (441907,4419,'茶山镇');
insert into web_regions (region_id,city_id,region) values (441908,4419,'石排镇');
insert into web_regions (region_id,city_id,region) values (441909,4419,'企石镇');
insert into web_regions (region_id,city_id,region) values (441910,4419,'横沥镇');
insert into web_regions (region_id,city_id,region) values (441911,4419,'桥头镇');
insert into web_regions (region_id,city_id,region) values (441912,4419,'谢岗镇');
insert into web_regions (region_id,city_id,region) values (441913,4419,'东坑镇');
insert into web_regions (region_id,city_id,region) values (441914,4419,'常平镇');
insert into web_regions (region_id,city_id,region) values (441915,4419,'寮步镇');
insert into web_regions (region_id,city_id,region) values (441916,4419,'大朗镇');
insert into web_regions (region_id,city_id,region) values (441917,4419,'麻涌镇');
insert into web_regions (region_id,city_id,region) values (441918,4419,'中堂镇');
insert into web_regions (region_id,city_id,region) values (441919,4419,'高埗镇');
insert into web_regions (region_id,city_id,region) values (441920,4419,'樟木头镇');
insert into web_regions (region_id,city_id,region) values (441921,4419,'大岭山镇');
insert into web_regions (region_id,city_id,region) values (441922,4419,'望牛墩镇');
insert into web_regions (region_id,city_id,region) values (441923,4419,'黄江镇');
insert into web_regions (region_id,city_id,region) values (441924,4419,'洪梅镇');
insert into web_regions (region_id,city_id,region) values (441925,4419,'清溪镇');
insert into web_regions (region_id,city_id,region) values (441926,4419,'沙田镇');
insert into web_regions (region_id,city_id,region) values (441927,4419,'道滘镇');
insert into web_regions (region_id,city_id,region) values (441928,4419,'塘厦镇');
insert into web_regions (region_id,city_id,region) values (441929,4419,'虎门镇');
insert into web_regions (region_id,city_id,region) values (441930,4419,'厚街镇');
insert into web_regions (region_id,city_id,region) values (441931,4419,'凤岗镇');
insert into web_regions (region_id,city_id,region) values (441932,4419,'长安镇');
insert into web_regions (region_id,city_id,region) values (442001,4420,'石岐区');
insert into web_regions (region_id,city_id,region) values (442004,4420,'南区');
insert into web_regions (region_id,city_id,region) values (442005,4420,'五桂山区');
insert into web_regions (region_id,city_id,region) values (442006,4420,'火炬开发区');
insert into web_regions (region_id,city_id,region) values (442007,4420,'黄圃镇');
insert into web_regions (region_id,city_id,region) values (442008,4420,'南头镇');
insert into web_regions (region_id,city_id,region) values (442009,4420,'东凤镇');
insert into web_regions (region_id,city_id,region) values (442010,4420,'阜沙镇');
insert into web_regions (region_id,city_id,region) values (442011,4420,'小榄镇');
insert into web_regions (region_id,city_id,region) values (442012,4420,'东升镇');
insert into web_regions (region_id,city_id,region) values (442013,4420,'古镇镇');
insert into web_regions (region_id,city_id,region) values (442014,4420,'横栏镇');
insert into web_regions (region_id,city_id,region) values (442015,4420,'三角镇');
insert into web_regions (region_id,city_id,region) values (442016,4420,'民众镇');
insert into web_regions (region_id,city_id,region) values (442017,4420,'南朗镇');
insert into web_regions (region_id,city_id,region) values (442018,4420,'港口镇');
insert into web_regions (region_id,city_id,region) values (442019,4420,'大涌镇');
insert into web_regions (region_id,city_id,region) values (442020,4420,'沙溪镇');
insert into web_regions (region_id,city_id,region) values (442021,4420,'三乡镇');
insert into web_regions (region_id,city_id,region) values (442022,4420,'板芙镇');
insert into web_regions (region_id,city_id,region) values (442023,4420,'神湾镇');
insert into web_regions (region_id,city_id,region) values (442024,4420,'坦洲镇');
insert into web_regions (region_id,city_id,region) values (445102,4451,'湘桥区');
insert into web_regions (region_id,city_id,region) values (445103,4451,'潮安区');
insert into web_regions (region_id,city_id,region) values (445122,4451,'饶平县');
insert into web_regions (region_id,city_id,region) values (445202,4452,'榕城区');
insert into web_regions (region_id,city_id,region) values (445203,4452,'揭东区');
insert into web_regions (region_id,city_id,region) values (445222,4452,'揭西县');
insert into web_regions (region_id,city_id,region) values (445224,4452,'惠来县');
insert into web_regions (region_id,city_id,region) values (445281,4452,'普宁市');
insert into web_regions (region_id,city_id,region) values (445302,4453,'云城区');
insert into web_regions (region_id,city_id,region) values (445303,4453,'云安区');
insert into web_regions (region_id,city_id,region) values (445321,4453,'新兴县');
insert into web_regions (region_id,city_id,region) values (445322,4453,'郁南县');
insert into web_regions (region_id,city_id,region) values (445381,4453,'罗定市');
insert into web_regions (region_id,city_id,region) values (450102,4501,'兴宁区');
insert into web_regions (region_id,city_id,region) values (450103,4501,'青秀区');
insert into web_regions (region_id,city_id,region) values (450105,4501,'江南区');
insert into web_regions (region_id,city_id,region) values (450107,4501,'西乡塘区');
insert into web_regions (region_id,city_id,region) values (450108,4501,'良庆区');
insert into web_regions (region_id,city_id,region) values (450109,4501,'邕宁区');
insert into web_regions (region_id,city_id,region) values (450122,4501,'武鸣县');
insert into web_regions (region_id,city_id,region) values (450123,4501,'隆安县');
insert into web_regions (region_id,city_id,region) values (450124,4501,'马山县');
insert into web_regions (region_id,city_id,region) values (450125,4501,'上林县');
insert into web_regions (region_id,city_id,region) values (450126,4501,'宾阳县');
insert into web_regions (region_id,city_id,region) values (450127,4501,'横县');
insert into web_regions (region_id,city_id,region) values (450128,4501,'埌东新区');
insert into web_regions (region_id,city_id,region) values (450202,4502,'城中区');
insert into web_regions (region_id,city_id,region) values (450203,4502,'鱼峰区');
insert into web_regions (region_id,city_id,region) values (450204,4502,'柳南区');
insert into web_regions (region_id,city_id,region) values (450205,4502,'柳北区');
insert into web_regions (region_id,city_id,region) values (450221,4502,'柳江县');
insert into web_regions (region_id,city_id,region) values (450222,4502,'柳城县');
insert into web_regions (region_id,city_id,region) values (450223,4502,'鹿寨县');
insert into web_regions (region_id,city_id,region) values (450224,4502,'融安县');
insert into web_regions (region_id,city_id,region) values (450225,4502,'融水苗族自治县');
insert into web_regions (region_id,city_id,region) values (450226,4502,'三江侗族自治县');
insert into web_regions (region_id,city_id,region) values (450227,4502,'柳东新区');
insert into web_regions (region_id,city_id,region) values (450302,4503,'秀峰区');
insert into web_regions (region_id,city_id,region) values (450303,4503,'叠彩区');
insert into web_regions (region_id,city_id,region) values (450304,4503,'象山区');
insert into web_regions (region_id,city_id,region) values (450305,4503,'七星区');
insert into web_regions (region_id,city_id,region) values (450311,4503,'雁山区');
insert into web_regions (region_id,city_id,region) values (450312,4503,'临桂区');
insert into web_regions (region_id,city_id,region) values (450321,4503,'阳朔县');
insert into web_regions (region_id,city_id,region) values (450323,4503,'灵川县');
insert into web_regions (region_id,city_id,region) values (450324,4503,'全州县');
insert into web_regions (region_id,city_id,region) values (450325,4503,'兴安县');
insert into web_regions (region_id,city_id,region) values (450326,4503,'永福县');
insert into web_regions (region_id,city_id,region) values (450327,4503,'灌阳县');
insert into web_regions (region_id,city_id,region) values (450328,4503,'龙胜各族自治县');
insert into web_regions (region_id,city_id,region) values (450329,4503,'资源县');
insert into web_regions (region_id,city_id,region) values (450330,4503,'平乐县');
insert into web_regions (region_id,city_id,region) values (450331,4503,'荔浦县');
insert into web_regions (region_id,city_id,region) values (450332,4503,'恭城瑶族自治县');
insert into web_regions (region_id,city_id,region) values (450403,4504,'万秀区');
insert into web_regions (region_id,city_id,region) values (450405,4504,'长洲区');
insert into web_regions (region_id,city_id,region) values (450406,4504,'龙圩区');
insert into web_regions (region_id,city_id,region) values (450421,4504,'苍梧县');
insert into web_regions (region_id,city_id,region) values (450422,4504,'藤县');
insert into web_regions (region_id,city_id,region) values (450423,4504,'蒙山县');
insert into web_regions (region_id,city_id,region) values (450481,4504,'岑溪市');
insert into web_regions (region_id,city_id,region) values (450502,4505,'海城区');
insert into web_regions (region_id,city_id,region) values (450503,4505,'银海区');
insert into web_regions (region_id,city_id,region) values (450512,4505,'铁山港区');
insert into web_regions (region_id,city_id,region) values (450521,4505,'合浦县');
insert into web_regions (region_id,city_id,region) values (450602,4506,'港口区');
insert into web_regions (region_id,city_id,region) values (450603,4506,'防城区');
insert into web_regions (region_id,city_id,region) values (450621,4506,'上思县');
insert into web_regions (region_id,city_id,region) values (450681,4506,'东兴市');
insert into web_regions (region_id,city_id,region) values (450702,4507,'钦南区');
insert into web_regions (region_id,city_id,region) values (450703,4507,'钦北区');
insert into web_regions (region_id,city_id,region) values (450721,4507,'灵山县');
insert into web_regions (region_id,city_id,region) values (450722,4507,'浦北县');
insert into web_regions (region_id,city_id,region) values (450802,4508,'港北区');
insert into web_regions (region_id,city_id,region) values (450803,4508,'港南区');
insert into web_regions (region_id,city_id,region) values (450804,4508,'覃塘区');
insert into web_regions (region_id,city_id,region) values (450821,4508,'平南县');
insert into web_regions (region_id,city_id,region) values (450881,4508,'桂平市');
insert into web_regions (region_id,city_id,region) values (450902,4509,'玉州区');
insert into web_regions (region_id,city_id,region) values (450903,4509,'福绵区');
insert into web_regions (region_id,city_id,region) values (450904,4509,'玉东新区');
insert into web_regions (region_id,city_id,region) values (450921,4509,'容县');
insert into web_regions (region_id,city_id,region) values (450922,4509,'陆川县');
insert into web_regions (region_id,city_id,region) values (450923,4509,'博白县');
insert into web_regions (region_id,city_id,region) values (450924,4509,'兴业县');
insert into web_regions (region_id,city_id,region) values (450981,4509,'北流市');
insert into web_regions (region_id,city_id,region) values (451002,4510,'右江区');
insert into web_regions (region_id,city_id,region) values (451021,4510,'田阳县');
insert into web_regions (region_id,city_id,region) values (451022,4510,'田东县');
insert into web_regions (region_id,city_id,region) values (451023,4510,'平果县');
insert into web_regions (region_id,city_id,region) values (451024,4510,'德保县');
insert into web_regions (region_id,city_id,region) values (451025,4510,'靖西县');
insert into web_regions (region_id,city_id,region) values (451026,4510,'那坡县');
insert into web_regions (region_id,city_id,region) values (451027,4510,'凌云县');
insert into web_regions (region_id,city_id,region) values (451028,4510,'乐业县');
insert into web_regions (region_id,city_id,region) values (451029,4510,'田林县');
insert into web_regions (region_id,city_id,region) values (451030,4510,'西林县');
insert into web_regions (region_id,city_id,region) values (451031,4510,'隆林各族自治县');
insert into web_regions (region_id,city_id,region) values (451102,4511,'八步区');
insert into web_regions (region_id,city_id,region) values (451121,4511,'昭平县');
insert into web_regions (region_id,city_id,region) values (451122,4511,'钟山县');
insert into web_regions (region_id,city_id,region) values (451123,4511,'富川瑶族自治县');
insert into web_regions (region_id,city_id,region) values (451124,4511,'平桂管理区');
insert into web_regions (region_id,city_id,region) values (451202,4512,'金城江区');
insert into web_regions (region_id,city_id,region) values (451221,4512,'南丹县');
insert into web_regions (region_id,city_id,region) values (451222,4512,'天峨县');
insert into web_regions (region_id,city_id,region) values (451223,4512,'凤山县');
insert into web_regions (region_id,city_id,region) values (451224,4512,'东兰县');
insert into web_regions (region_id,city_id,region) values (451225,4512,'罗城仫佬族自治县');
insert into web_regions (region_id,city_id,region) values (451226,4512,'环江毛南族自治县');
insert into web_regions (region_id,city_id,region) values (451227,4512,'巴马瑶族自治县');
insert into web_regions (region_id,city_id,region) values (451228,4512,'都安瑶族自治县');
insert into web_regions (region_id,city_id,region) values (451229,4512,'大化瑶族自治县');
insert into web_regions (region_id,city_id,region) values (451281,4512,'宜州市');
insert into web_regions (region_id,city_id,region) values (451302,4513,'兴宾区');
insert into web_regions (region_id,city_id,region) values (451321,4513,'忻城县');
insert into web_regions (region_id,city_id,region) values (451322,4513,'象州县');
insert into web_regions (region_id,city_id,region) values (451323,4513,'武宣县');
insert into web_regions (region_id,city_id,region) values (451324,4513,'金秀瑶族自治县');
insert into web_regions (region_id,city_id,region) values (451381,4513,'合山市');
insert into web_regions (region_id,city_id,region) values (451402,4514,'江州区');
insert into web_regions (region_id,city_id,region) values (451421,4514,'扶绥县');
insert into web_regions (region_id,city_id,region) values (451422,4514,'宁明县');
insert into web_regions (region_id,city_id,region) values (451423,4514,'龙州县');
insert into web_regions (region_id,city_id,region) values (451424,4514,'大新县');
insert into web_regions (region_id,city_id,region) values (451425,4514,'天等县');
insert into web_regions (region_id,city_id,region) values (451481,4514,'凭祥市');
insert into web_regions (region_id,city_id,region) values (460105,4601,'秀英区');
insert into web_regions (region_id,city_id,region) values (460106,4601,'龙华区');
insert into web_regions (region_id,city_id,region) values (460107,4601,'琼山区');
insert into web_regions (region_id,city_id,region) values (460108,4601,'美兰区');
insert into web_regions (region_id,city_id,region) values (460202,4602,'海棠区');
insert into web_regions (region_id,city_id,region) values (460203,4602,'吉阳区');
insert into web_regions (region_id,city_id,region) values (460204,4602,'天涯区');
insert into web_regions (region_id,city_id,region) values (460205,4602,'崖州区');
insert into web_regions (region_id,city_id,region) values (460321,4603,'西沙群岛');
insert into web_regions (region_id,city_id,region) values (460322,4603,'南沙群岛');
insert into web_regions (region_id,city_id,region) values (460323,4603,'中沙群岛');
insert into web_regions (region_id,city_id,region) values (469001,4690,'五指山市');
insert into web_regions (region_id,city_id,region) values (469002,4690,'琼海市');
insert into web_regions (region_id,city_id,region) values (469003,4690,'儋州市');
insert into web_regions (region_id,city_id,region) values (469005,4690,'文昌市');
insert into web_regions (region_id,city_id,region) values (469006,4690,'万宁市');
insert into web_regions (region_id,city_id,region) values (469007,4690,'东方市');
insert into web_regions (region_id,city_id,region) values (469021,4690,'定安县');
insert into web_regions (region_id,city_id,region) values (469022,4690,'屯昌县');
insert into web_regions (region_id,city_id,region) values (469023,4690,'澄迈县');
insert into web_regions (region_id,city_id,region) values (469024,4690,'临高县');
insert into web_regions (region_id,city_id,region) values (469025,4690,'白沙黎族自治县');
insert into web_regions (region_id,city_id,region) values (469026,4690,'昌江黎族自治县');
insert into web_regions (region_id,city_id,region) values (469027,4690,'乐东黎族自治县');
insert into web_regions (region_id,city_id,region) values (469028,4690,'陵水黎族自治县');
insert into web_regions (region_id,city_id,region) values (469029,4690,'保亭黎族苗族自治县');
insert into web_regions (region_id,city_id,region) values (469030,4690,'琼中黎族苗族自治县');
insert into web_regions (region_id,city_id,region) values (500101,5001,'万州区');
insert into web_regions (region_id,city_id,region) values (500102,5001,'涪陵区');
insert into web_regions (region_id,city_id,region) values (500103,5001,'渝中区');
insert into web_regions (region_id,city_id,region) values (500104,5001,'大渡口区');
insert into web_regions (region_id,city_id,region) values (500105,5001,'江北区');
insert into web_regions (region_id,city_id,region) values (500106,5001,'沙坪坝区');
insert into web_regions (region_id,city_id,region) values (500107,5001,'九龙坡区');
insert into web_regions (region_id,city_id,region) values (500108,5001,'南岸区');
insert into web_regions (region_id,city_id,region) values (500109,5001,'北碚区');
insert into web_regions (region_id,city_id,region) values (500110,5001,'綦江区');
insert into web_regions (region_id,city_id,region) values (500111,5001,'大足区');
insert into web_regions (region_id,city_id,region) values (500112,5001,'渝北区');
insert into web_regions (region_id,city_id,region) values (500113,5001,'巴南区');
insert into web_regions (region_id,city_id,region) values (500114,5001,'黔江区');
insert into web_regions (region_id,city_id,region) values (500115,5001,'长寿区');
insert into web_regions (region_id,city_id,region) values (500116,5001,'江津区');
insert into web_regions (region_id,city_id,region) values (500117,5001,'合川区');
insert into web_regions (region_id,city_id,region) values (500118,5001,'永川区');
insert into web_regions (region_id,city_id,region) values (500119,5001,'南川区');
insert into web_regions (region_id,city_id,region) values (500120,5001,'璧山区');
insert into web_regions (region_id,city_id,region) values (500151,5001,'铜梁区');
insert into web_regions (region_id,city_id,region) values (500223,5001,'潼南县');
insert into web_regions (region_id,city_id,region) values (500226,5001,'荣昌县');
insert into web_regions (region_id,city_id,region) values (500228,5001,'梁平县');
insert into web_regions (region_id,city_id,region) values (500229,5001,'城口县');
insert into web_regions (region_id,city_id,region) values (500230,5001,'丰都县');
insert into web_regions (region_id,city_id,region) values (500231,5001,'垫江县');
insert into web_regions (region_id,city_id,region) values (500232,5001,'武隆县');
insert into web_regions (region_id,city_id,region) values (500233,5001,'忠县');
insert into web_regions (region_id,city_id,region) values (500234,5001,'开县');
insert into web_regions (region_id,city_id,region) values (500235,5001,'云阳县');
insert into web_regions (region_id,city_id,region) values (500236,5001,'奉节县');
insert into web_regions (region_id,city_id,region) values (500237,5001,'巫山县');
insert into web_regions (region_id,city_id,region) values (500238,5001,'巫溪县');
insert into web_regions (region_id,city_id,region) values (500240,5001,'石柱土家族自治县');
insert into web_regions (region_id,city_id,region) values (500241,5001,'秀山土家族苗族自治县');
insert into web_regions (region_id,city_id,region) values (500242,5001,'酉阳土家族苗族自治县');
insert into web_regions (region_id,city_id,region) values (500243,5001,'彭水苗族土家族自治县');
insert into web_regions (region_id,city_id,region) values (500301,5003,'北部新区');
insert into web_regions (region_id,city_id,region) values (500302,5003,'保税港区');
insert into web_regions (region_id,city_id,region) values (500303,5003,'工业园区');
insert into web_regions (region_id,city_id,region) values (510104,5101,'锦江区');
insert into web_regions (region_id,city_id,region) values (510105,5101,'青羊区');
insert into web_regions (region_id,city_id,region) values (510106,5101,'金牛区');
insert into web_regions (region_id,city_id,region) values (510107,5101,'武侯区');
insert into web_regions (region_id,city_id,region) values (510108,5101,'成华区');
insert into web_regions (region_id,city_id,region) values (510112,5101,'龙泉驿区');
insert into web_regions (region_id,city_id,region) values (510113,5101,'青白江区');
insert into web_regions (region_id,city_id,region) values (510114,5101,'新都区');
insert into web_regions (region_id,city_id,region) values (510115,5101,'温江区');
insert into web_regions (region_id,city_id,region) values (510121,5101,'金堂县');
insert into web_regions (region_id,city_id,region) values (510122,5101,'双流县');
insert into web_regions (region_id,city_id,region) values (510124,5101,'郫县');
insert into web_regions (region_id,city_id,region) values (510129,5101,'大邑县');
insert into web_regions (region_id,city_id,region) values (510131,5101,'蒲江县');
insert into web_regions (region_id,city_id,region) values (510132,5101,'新津县');
insert into web_regions (region_id,city_id,region) values (510181,5101,'都江堰市');
insert into web_regions (region_id,city_id,region) values (510182,5101,'彭州市');
insert into web_regions (region_id,city_id,region) values (510183,5101,'邛崃市');
insert into web_regions (region_id,city_id,region) values (510184,5101,'崇州市');
insert into web_regions (region_id,city_id,region) values (510302,5103,'自流井区');
insert into web_regions (region_id,city_id,region) values (510303,5103,'贡井区');
insert into web_regions (region_id,city_id,region) values (510304,5103,'大安区');
insert into web_regions (region_id,city_id,region) values (510311,5103,'沿滩区');
insert into web_regions (region_id,city_id,region) values (510321,5103,'荣县');
insert into web_regions (region_id,city_id,region) values (510322,5103,'富顺县');
insert into web_regions (region_id,city_id,region) values (510402,5104,'东区');
insert into web_regions (region_id,city_id,region) values (510403,5104,'西区');
insert into web_regions (region_id,city_id,region) values (510411,5104,'仁和区');
insert into web_regions (region_id,city_id,region) values (510421,5104,'米易县');
insert into web_regions (region_id,city_id,region) values (510422,5104,'盐边县');
insert into web_regions (region_id,city_id,region) values (510502,5105,'江阳区');
insert into web_regions (region_id,city_id,region) values (510503,5105,'纳溪区');
insert into web_regions (region_id,city_id,region) values (510504,5105,'龙马潭区');
insert into web_regions (region_id,city_id,region) values (510521,5105,'泸县');
insert into web_regions (region_id,city_id,region) values (510522,5105,'合江县');
insert into web_regions (region_id,city_id,region) values (510524,5105,'叙永县');
insert into web_regions (region_id,city_id,region) values (510525,5105,'古蔺县');
insert into web_regions (region_id,city_id,region) values (510603,5106,'旌阳区');
insert into web_regions (region_id,city_id,region) values (510623,5106,'中江县');
insert into web_regions (region_id,city_id,region) values (510626,5106,'罗江县');
insert into web_regions (region_id,city_id,region) values (510681,5106,'广汉市');
insert into web_regions (region_id,city_id,region) values (510682,5106,'什邡市');
insert into web_regions (region_id,city_id,region) values (510683,5106,'绵竹市');
insert into web_regions (region_id,city_id,region) values (510703,5107,'涪城区');
insert into web_regions (region_id,city_id,region) values (510704,5107,'游仙区');
insert into web_regions (region_id,city_id,region) values (510722,5107,'三台县');
insert into web_regions (region_id,city_id,region) values (510723,5107,'盐亭县');
insert into web_regions (region_id,city_id,region) values (510724,5107,'安县');
insert into web_regions (region_id,city_id,region) values (510725,5107,'梓潼县');
insert into web_regions (region_id,city_id,region) values (510726,5107,'北川羌族自治县');
insert into web_regions (region_id,city_id,region) values (510727,5107,'平武县');
insert into web_regions (region_id,city_id,region) values (510781,5107,'江油市');
insert into web_regions (region_id,city_id,region) values (510802,5108,'利州区');
insert into web_regions (region_id,city_id,region) values (510811,5108,'昭化区');
insert into web_regions (region_id,city_id,region) values (510812,5108,'朝天区');
insert into web_regions (region_id,city_id,region) values (510821,5108,'旺苍县');
insert into web_regions (region_id,city_id,region) values (510822,5108,'青川县');
insert into web_regions (region_id,city_id,region) values (510823,5108,'剑阁县');
insert into web_regions (region_id,city_id,region) values (510824,5108,'苍溪县');
insert into web_regions (region_id,city_id,region) values (510903,5109,'船山区');
insert into web_regions (region_id,city_id,region) values (510904,5109,'安居区');
insert into web_regions (region_id,city_id,region) values (510921,5109,'蓬溪县');
insert into web_regions (region_id,city_id,region) values (510922,5109,'射洪县');
insert into web_regions (region_id,city_id,region) values (510923,5109,'大英县');
insert into web_regions (region_id,city_id,region) values (511002,5110,'市中区');
insert into web_regions (region_id,city_id,region) values (511011,5110,'东兴区');
insert into web_regions (region_id,city_id,region) values (511024,5110,'威远县');
insert into web_regions (region_id,city_id,region) values (511025,5110,'资中县');
insert into web_regions (region_id,city_id,region) values (511028,5110,'隆昌县');
insert into web_regions (region_id,city_id,region) values (511102,5111,'市中区');
insert into web_regions (region_id,city_id,region) values (511111,5111,'沙湾区');
insert into web_regions (region_id,city_id,region) values (511112,5111,'五通桥区');
insert into web_regions (region_id,city_id,region) values (511113,5111,'金口河区');
insert into web_regions (region_id,city_id,region) values (511123,5111,'犍为县');
insert into web_regions (region_id,city_id,region) values (511124,5111,'井研县');
insert into web_regions (region_id,city_id,region) values (511126,5111,'夹江县');
insert into web_regions (region_id,city_id,region) values (511129,5111,'沐川县');
insert into web_regions (region_id,city_id,region) values (511132,5111,'峨边彝族自治县');
insert into web_regions (region_id,city_id,region) values (511133,5111,'马边彝族自治县');
insert into web_regions (region_id,city_id,region) values (511181,5111,'峨眉山市');
insert into web_regions (region_id,city_id,region) values (511302,5113,'顺庆区');
insert into web_regions (region_id,city_id,region) values (511303,5113,'高坪区');
insert into web_regions (region_id,city_id,region) values (511304,5113,'嘉陵区');
insert into web_regions (region_id,city_id,region) values (511321,5113,'南部县');
insert into web_regions (region_id,city_id,region) values (511322,5113,'营山县');
insert into web_regions (region_id,city_id,region) values (511323,5113,'蓬安县');
insert into web_regions (region_id,city_id,region) values (511324,5113,'仪陇县');
insert into web_regions (region_id,city_id,region) values (511325,5113,'西充县');
insert into web_regions (region_id,city_id,region) values (511381,5113,'阆中市');
insert into web_regions (region_id,city_id,region) values (511402,5114,'东坡区');
insert into web_regions (region_id,city_id,region) values (511403,5114,'彭山区');
insert into web_regions (region_id,city_id,region) values (511421,5114,'仁寿县');
insert into web_regions (region_id,city_id,region) values (511423,5114,'洪雅县');
insert into web_regions (region_id,city_id,region) values (511424,5114,'丹棱县');
insert into web_regions (region_id,city_id,region) values (511425,5114,'青神县');
insert into web_regions (region_id,city_id,region) values (511502,5115,'翠屏区');
insert into web_regions (region_id,city_id,region) values (511503,5115,'南溪区');
insert into web_regions (region_id,city_id,region) values (511521,5115,'宜宾县');
insert into web_regions (region_id,city_id,region) values (511523,5115,'江安县');
insert into web_regions (region_id,city_id,region) values (511524,5115,'长宁县');
insert into web_regions (region_id,city_id,region) values (511525,5115,'高县');
insert into web_regions (region_id,city_id,region) values (511526,5115,'珙县');
insert into web_regions (region_id,city_id,region) values (511527,5115,'筠连县');
insert into web_regions (region_id,city_id,region) values (511528,5115,'兴文县');
insert into web_regions (region_id,city_id,region) values (511529,5115,'屏山县');
insert into web_regions (region_id,city_id,region) values (511602,5116,'广安区');
insert into web_regions (region_id,city_id,region) values (511603,5116,'前锋区');
insert into web_regions (region_id,city_id,region) values (511621,5116,'岳池县');
insert into web_regions (region_id,city_id,region) values (511622,5116,'武胜县');
insert into web_regions (region_id,city_id,region) values (511623,5116,'邻水县');
insert into web_regions (region_id,city_id,region) values (511681,5116,'华蓥市');
insert into web_regions (region_id,city_id,region) values (511702,5117,'通川区');
insert into web_regions (region_id,city_id,region) values (511703,5117,'达川区');
insert into web_regions (region_id,city_id,region) values (511722,5117,'宣汉县');
insert into web_regions (region_id,city_id,region) values (511723,5117,'开江县');
insert into web_regions (region_id,city_id,region) values (511724,5117,'大竹县');
insert into web_regions (region_id,city_id,region) values (511725,5117,'渠县');
insert into web_regions (region_id,city_id,region) values (511781,5117,'万源市');
insert into web_regions (region_id,city_id,region) values (511802,5118,'雨城区');
insert into web_regions (region_id,city_id,region) values (511803,5118,'名山区');
insert into web_regions (region_id,city_id,region) values (511822,5118,'荥经县');
insert into web_regions (region_id,city_id,region) values (511823,5118,'汉源县');
insert into web_regions (region_id,city_id,region) values (511824,5118,'石棉县');
insert into web_regions (region_id,city_id,region) values (511825,5118,'天全县');
insert into web_regions (region_id,city_id,region) values (511826,5118,'芦山县');
insert into web_regions (region_id,city_id,region) values (511827,5118,'宝兴县');
insert into web_regions (region_id,city_id,region) values (511902,5119,'巴州区');
insert into web_regions (region_id,city_id,region) values (511903,5119,'恩阳区');
insert into web_regions (region_id,city_id,region) values (511921,5119,'通江县');
insert into web_regions (region_id,city_id,region) values (511922,5119,'南江县');
insert into web_regions (region_id,city_id,region) values (511923,5119,'平昌县');
insert into web_regions (region_id,city_id,region) values (512002,5120,'雁江区');
insert into web_regions (region_id,city_id,region) values (512021,5120,'安岳县');
insert into web_regions (region_id,city_id,region) values (512022,5120,'乐至县');
insert into web_regions (region_id,city_id,region) values (512081,5120,'简阳市');
insert into web_regions (region_id,city_id,region) values (513221,5132,'汶川县');
insert into web_regions (region_id,city_id,region) values (513222,5132,'理县');
insert into web_regions (region_id,city_id,region) values (513223,5132,'茂县');
insert into web_regions (region_id,city_id,region) values (513224,5132,'松潘县');
insert into web_regions (region_id,city_id,region) values (513225,5132,'九寨沟县');
insert into web_regions (region_id,city_id,region) values (513226,5132,'金川县');
insert into web_regions (region_id,city_id,region) values (513227,5132,'小金县');
insert into web_regions (region_id,city_id,region) values (513228,5132,'黑水县');
insert into web_regions (region_id,city_id,region) values (513229,5132,'马尔康县');
insert into web_regions (region_id,city_id,region) values (513230,5132,'壤塘县');
insert into web_regions (region_id,city_id,region) values (513231,5132,'阿坝县');
insert into web_regions (region_id,city_id,region) values (513232,5132,'若尔盖县');
insert into web_regions (region_id,city_id,region) values (513233,5132,'红原县');
insert into web_regions (region_id,city_id,region) values (513321,5133,'康定县');
insert into web_regions (region_id,city_id,region) values (513322,5133,'泸定县');
insert into web_regions (region_id,city_id,region) values (513323,5133,'丹巴县');
insert into web_regions (region_id,city_id,region) values (513324,5133,'九龙县');
insert into web_regions (region_id,city_id,region) values (513325,5133,'雅江县');
insert into web_regions (region_id,city_id,region) values (513326,5133,'道孚县');
insert into web_regions (region_id,city_id,region) values (513327,5133,'炉霍县');
insert into web_regions (region_id,city_id,region) values (513328,5133,'甘孜县');
insert into web_regions (region_id,city_id,region) values (513329,5133,'新龙县');
insert into web_regions (region_id,city_id,region) values (513330,5133,'德格县');
insert into web_regions (region_id,city_id,region) values (513331,5133,'白玉县');
insert into web_regions (region_id,city_id,region) values (513332,5133,'石渠县');
insert into web_regions (region_id,city_id,region) values (513333,5133,'色达县');
insert into web_regions (region_id,city_id,region) values (513334,5133,'理塘县');
insert into web_regions (region_id,city_id,region) values (513335,5133,'巴塘县');
insert into web_regions (region_id,city_id,region) values (513336,5133,'乡城县');
insert into web_regions (region_id,city_id,region) values (513337,5133,'稻城县');
insert into web_regions (region_id,city_id,region) values (513338,5133,'得荣县');
insert into web_regions (region_id,city_id,region) values (513401,5134,'西昌市');
insert into web_regions (region_id,city_id,region) values (513422,5134,'木里藏族自治县');
insert into web_regions (region_id,city_id,region) values (513423,5134,'盐源县');
insert into web_regions (region_id,city_id,region) values (513424,5134,'德昌县');
insert into web_regions (region_id,city_id,region) values (513425,5134,'会理县');
insert into web_regions (region_id,city_id,region) values (513426,5134,'会东县');
insert into web_regions (region_id,city_id,region) values (513427,5134,'宁南县');
insert into web_regions (region_id,city_id,region) values (513428,5134,'普格县');
insert into web_regions (region_id,city_id,region) values (513429,5134,'布拖县');
insert into web_regions (region_id,city_id,region) values (513430,5134,'金阳县');
insert into web_regions (region_id,city_id,region) values (513431,5134,'昭觉县');
insert into web_regions (region_id,city_id,region) values (513432,5134,'喜德县');
insert into web_regions (region_id,city_id,region) values (513433,5134,'冕宁县');
insert into web_regions (region_id,city_id,region) values (513434,5134,'越西县');
insert into web_regions (region_id,city_id,region) values (513435,5134,'甘洛县');
insert into web_regions (region_id,city_id,region) values (513436,5134,'美姑县');
insert into web_regions (region_id,city_id,region) values (513437,5134,'雷波县');
insert into web_regions (region_id,city_id,region) values (520102,5201,'南明区');
insert into web_regions (region_id,city_id,region) values (520103,5201,'云岩区');
insert into web_regions (region_id,city_id,region) values (520111,5201,'花溪区');
insert into web_regions (region_id,city_id,region) values (520112,5201,'乌当区');
insert into web_regions (region_id,city_id,region) values (520113,5201,'白云区');
insert into web_regions (region_id,city_id,region) values (520115,5201,'观山湖区');
insert into web_regions (region_id,city_id,region) values (520121,5201,'开阳县');
insert into web_regions (region_id,city_id,region) values (520122,5201,'息烽县');
insert into web_regions (region_id,city_id,region) values (520123,5201,'修文县');
insert into web_regions (region_id,city_id,region) values (520181,5201,'清镇市');
insert into web_regions (region_id,city_id,region) values (520201,5202,'钟山区');
insert into web_regions (region_id,city_id,region) values (520203,5202,'六枝特区');
insert into web_regions (region_id,city_id,region) values (520221,5202,'水城县');
insert into web_regions (region_id,city_id,region) values (520222,5202,'盘县');
insert into web_regions (region_id,city_id,region) values (520302,5203,'红花岗区');
insert into web_regions (region_id,city_id,region) values (520303,5203,'汇川区');
insert into web_regions (region_id,city_id,region) values (520321,5203,'遵义县');
insert into web_regions (region_id,city_id,region) values (520322,5203,'桐梓县');
insert into web_regions (region_id,city_id,region) values (520323,5203,'绥阳县');
insert into web_regions (region_id,city_id,region) values (520324,5203,'正安县');
insert into web_regions (region_id,city_id,region) values (520325,5203,'道真仡佬族苗族自治县');
insert into web_regions (region_id,city_id,region) values (520326,5203,'务川仡佬族苗族自治县');
insert into web_regions (region_id,city_id,region) values (520327,5203,'凤冈县');
insert into web_regions (region_id,city_id,region) values (520328,5203,'湄潭县');
insert into web_regions (region_id,city_id,region) values (520329,5203,'余庆县');
insert into web_regions (region_id,city_id,region) values (520330,5203,'习水县');
insert into web_regions (region_id,city_id,region) values (520381,5203,'赤水市');
insert into web_regions (region_id,city_id,region) values (520382,5203,'仁怀市');
insert into web_regions (region_id,city_id,region) values (520402,5204,'西秀区');
insert into web_regions (region_id,city_id,region) values (520421,5204,'平坝区');
insert into web_regions (region_id,city_id,region) values (520422,5204,'普定县');
insert into web_regions (region_id,city_id,region) values (520423,5204,'镇宁布依族苗族自治县');
insert into web_regions (region_id,city_id,region) values (520424,5204,'关岭布依族苗族自治县');
insert into web_regions (region_id,city_id,region) values (520425,5204,'紫云苗族布依族自治县');
insert into web_regions (region_id,city_id,region) values (520502,5205,'七星关区');
insert into web_regions (region_id,city_id,region) values (520521,5205,'大方县');
insert into web_regions (region_id,city_id,region) values (520522,5205,'黔西县');
insert into web_regions (region_id,city_id,region) values (520523,5205,'金沙县');
insert into web_regions (region_id,city_id,region) values (520524,5205,'织金县');
insert into web_regions (region_id,city_id,region) values (520525,5205,'纳雍县');
insert into web_regions (region_id,city_id,region) values (520526,5205,'威宁彝族回族苗族自治县');
insert into web_regions (region_id,city_id,region) values (520527,5205,'赫章县');
insert into web_regions (region_id,city_id,region) values (520602,5206,'碧江区');
insert into web_regions (region_id,city_id,region) values (520603,5206,'万山区');
insert into web_regions (region_id,city_id,region) values (520621,5206,'江口县');
insert into web_regions (region_id,city_id,region) values (520622,5206,'玉屏侗族自治县');
insert into web_regions (region_id,city_id,region) values (520623,5206,'石阡县');
insert into web_regions (region_id,city_id,region) values (520624,5206,'思南县');
insert into web_regions (region_id,city_id,region) values (520625,5206,'印江土家族苗族自治县');
insert into web_regions (region_id,city_id,region) values (520626,5206,'德江县');
insert into web_regions (region_id,city_id,region) values (520627,5206,'沿河土家族自治县');
insert into web_regions (region_id,city_id,region) values (520628,5206,'松桃苗族自治县');
insert into web_regions (region_id,city_id,region) values (522301,5223,'兴义市 ');
insert into web_regions (region_id,city_id,region) values (522322,5223,'兴仁县');
insert into web_regions (region_id,city_id,region) values (522323,5223,'普安县');
insert into web_regions (region_id,city_id,region) values (522324,5223,'晴隆县');
insert into web_regions (region_id,city_id,region) values (522325,5223,'贞丰县');
insert into web_regions (region_id,city_id,region) values (522326,5223,'望谟县');
insert into web_regions (region_id,city_id,region) values (522327,5223,'册亨县');
insert into web_regions (region_id,city_id,region) values (522328,5223,'安龙县');
insert into web_regions (region_id,city_id,region) values (522601,5226,'凯里市');
insert into web_regions (region_id,city_id,region) values (522622,5226,'黄平县');
insert into web_regions (region_id,city_id,region) values (522623,5226,'施秉县');
insert into web_regions (region_id,city_id,region) values (522624,5226,'三穗县');
insert into web_regions (region_id,city_id,region) values (522625,5226,'镇远县');
insert into web_regions (region_id,city_id,region) values (522626,5226,'岑巩县');
insert into web_regions (region_id,city_id,region) values (522627,5226,'天柱县');
insert into web_regions (region_id,city_id,region) values (522628,5226,'锦屏县');
insert into web_regions (region_id,city_id,region) values (522629,5226,'剑河县');
insert into web_regions (region_id,city_id,region) values (522630,5226,'台江县');
insert into web_regions (region_id,city_id,region) values (522631,5226,'黎平县');
insert into web_regions (region_id,city_id,region) values (522632,5226,'榕江县');
insert into web_regions (region_id,city_id,region) values (522633,5226,'从江县');
insert into web_regions (region_id,city_id,region) values (522634,5226,'雷山县');
insert into web_regions (region_id,city_id,region) values (522635,5226,'麻江县');
insert into web_regions (region_id,city_id,region) values (522636,5226,'丹寨县');
insert into web_regions (region_id,city_id,region) values (522701,5227,'都匀市');
insert into web_regions (region_id,city_id,region) values (522702,5227,'福泉市');
insert into web_regions (region_id,city_id,region) values (522722,5227,'荔波县');
insert into web_regions (region_id,city_id,region) values (522723,5227,'贵定县');
insert into web_regions (region_id,city_id,region) values (522725,5227,'瓮安县');
insert into web_regions (region_id,city_id,region) values (522726,5227,'独山县');
insert into web_regions (region_id,city_id,region) values (522727,5227,'平塘县');
insert into web_regions (region_id,city_id,region) values (522728,5227,'罗甸县');
insert into web_regions (region_id,city_id,region) values (522729,5227,'长顺县');
insert into web_regions (region_id,city_id,region) values (522730,5227,'龙里县');
insert into web_regions (region_id,city_id,region) values (522731,5227,'惠水县');
insert into web_regions (region_id,city_id,region) values (522732,5227,'三都水族自治县');
insert into web_regions (region_id,city_id,region) values (530102,5301,'五华区');
insert into web_regions (region_id,city_id,region) values (530103,5301,'盘龙区');
insert into web_regions (region_id,city_id,region) values (530111,5301,'官渡区');
insert into web_regions (region_id,city_id,region) values (530112,5301,'西山区');
insert into web_regions (region_id,city_id,region) values (530113,5301,'东川区');
insert into web_regions (region_id,city_id,region) values (530114,5301,'呈贡区');
insert into web_regions (region_id,city_id,region) values (530122,5301,'晋宁县');
insert into web_regions (region_id,city_id,region) values (530124,5301,'富民县');
insert into web_regions (region_id,city_id,region) values (530125,5301,'宜良县');
insert into web_regions (region_id,city_id,region) values (530126,5301,'石林彝族自治县');
insert into web_regions (region_id,city_id,region) values (530127,5301,'嵩明县');
insert into web_regions (region_id,city_id,region) values (530128,5301,'禄劝彝族苗族自治县');
insert into web_regions (region_id,city_id,region) values (530129,5301,'寻甸回族彝族自治县 ');
insert into web_regions (region_id,city_id,region) values (530181,5301,'安宁市');
insert into web_regions (region_id,city_id,region) values (530302,5303,'麒麟区');
insert into web_regions (region_id,city_id,region) values (530321,5303,'马龙县');
insert into web_regions (region_id,city_id,region) values (530322,5303,'陆良县');
insert into web_regions (region_id,city_id,region) values (530323,5303,'师宗县');
insert into web_regions (region_id,city_id,region) values (530324,5303,'罗平县');
insert into web_regions (region_id,city_id,region) values (530325,5303,'富源县');
insert into web_regions (region_id,city_id,region) values (530326,5303,'会泽县');
insert into web_regions (region_id,city_id,region) values (530328,5303,'沾益县');
insert into web_regions (region_id,city_id,region) values (530381,5303,'宣威市');
insert into web_regions (region_id,city_id,region) values (530402,5304,'红塔区');
insert into web_regions (region_id,city_id,region) values (530421,5304,'江川县');
insert into web_regions (region_id,city_id,region) values (530422,5304,'澄江县');
insert into web_regions (region_id,city_id,region) values (530423,5304,'通海县');
insert into web_regions (region_id,city_id,region) values (530424,5304,'华宁县');
insert into web_regions (region_id,city_id,region) values (530425,5304,'易门县');
insert into web_regions (region_id,city_id,region) values (530426,5304,'峨山彝族自治县');
insert into web_regions (region_id,city_id,region) values (530427,5304,'新平彝族傣族自治县');
insert into web_regions (region_id,city_id,region) values (530428,5304,'元江哈尼族彝族傣族自治县');
insert into web_regions (region_id,city_id,region) values (530502,5305,'隆阳区');
insert into web_regions (region_id,city_id,region) values (530521,5305,'施甸县');
insert into web_regions (region_id,city_id,region) values (530522,5305,'腾冲县');
insert into web_regions (region_id,city_id,region) values (530523,5305,'龙陵县');
insert into web_regions (region_id,city_id,region) values (530524,5305,'昌宁县');
insert into web_regions (region_id,city_id,region) values (530602,5306,'昭阳区');
insert into web_regions (region_id,city_id,region) values (530621,5306,'鲁甸县');
insert into web_regions (region_id,city_id,region) values (530622,5306,'巧家县');
insert into web_regions (region_id,city_id,region) values (530623,5306,'盐津县');
insert into web_regions (region_id,city_id,region) values (530624,5306,'大关县');
insert into web_regions (region_id,city_id,region) values (530625,5306,'永善县');
insert into web_regions (region_id,city_id,region) values (530626,5306,'绥江县');
insert into web_regions (region_id,city_id,region) values (530627,5306,'镇雄县');
insert into web_regions (region_id,city_id,region) values (530628,5306,'彝良县');
insert into web_regions (region_id,city_id,region) values (530629,5306,'威信县');
insert into web_regions (region_id,city_id,region) values (530630,5306,'水富县');
insert into web_regions (region_id,city_id,region) values (530702,5307,'古城区');
insert into web_regions (region_id,city_id,region) values (530721,5307,'玉龙纳西族自治县');
insert into web_regions (region_id,city_id,region) values (530722,5307,'永胜县');
insert into web_regions (region_id,city_id,region) values (530723,5307,'华坪县');
insert into web_regions (region_id,city_id,region) values (530724,5307,'宁蒗彝族自治县');
insert into web_regions (region_id,city_id,region) values (530802,5308,'思茅区');
insert into web_regions (region_id,city_id,region) values (530821,5308,'宁洱哈尼族彝族自治县');
insert into web_regions (region_id,city_id,region) values (530822,5308,'墨江哈尼族自治县');
insert into web_regions (region_id,city_id,region) values (530823,5308,'景东彝族自治县');
insert into web_regions (region_id,city_id,region) values (530824,5308,'景谷傣族彝族自治县');
insert into web_regions (region_id,city_id,region) values (530825,5308,'镇沅彝族哈尼族拉祜族自治县');
insert into web_regions (region_id,city_id,region) values (530826,5308,'江城哈尼族彝族自治县');
insert into web_regions (region_id,city_id,region) values (530827,5308,'孟连傣族拉祜族佤族自治县');
insert into web_regions (region_id,city_id,region) values (530828,5308,'澜沧拉祜族自治县');
insert into web_regions (region_id,city_id,region) values (530829,5308,'西盟佤族自治县');
insert into web_regions (region_id,city_id,region) values (530902,5309,'临翔区');
insert into web_regions (region_id,city_id,region) values (530921,5309,'凤庆县');
insert into web_regions (region_id,city_id,region) values (530922,5309,'云县');
insert into web_regions (region_id,city_id,region) values (530923,5309,'永德县');
insert into web_regions (region_id,city_id,region) values (530924,5309,'镇康县');
insert into web_regions (region_id,city_id,region) values (530925,5309,'双江拉祜族佤族布朗族傣族自治县');
insert into web_regions (region_id,city_id,region) values (530926,5309,'耿马傣族佤族自治县');
insert into web_regions (region_id,city_id,region) values (530927,5309,'沧源佤族自治县');
insert into web_regions (region_id,city_id,region) values (532301,5323,'楚雄市');
insert into web_regions (region_id,city_id,region) values (532322,5323,'双柏县');
insert into web_regions (region_id,city_id,region) values (532323,5323,'牟定县');
insert into web_regions (region_id,city_id,region) values (532324,5323,'南华县');
insert into web_regions (region_id,city_id,region) values (532325,5323,'姚安县');
insert into web_regions (region_id,city_id,region) values (532326,5323,'大姚县');
insert into web_regions (region_id,city_id,region) values (532327,5323,'永仁县');
insert into web_regions (region_id,city_id,region) values (532328,5323,'元谋县');
insert into web_regions (region_id,city_id,region) values (532329,5323,'武定县');
insert into web_regions (region_id,city_id,region) values (532331,5323,'禄丰县');
insert into web_regions (region_id,city_id,region) values (532501,5325,'个旧市');
insert into web_regions (region_id,city_id,region) values (532502,5325,'开远市');
insert into web_regions (region_id,city_id,region) values (532503,5325,'蒙自市');
insert into web_regions (region_id,city_id,region) values (532504,5325,'弥勒市');
insert into web_regions (region_id,city_id,region) values (532523,5325,'屏边苗族自治县');
insert into web_regions (region_id,city_id,region) values (532524,5325,'建水县');
insert into web_regions (region_id,city_id,region) values (532525,5325,'石屏县');
insert into web_regions (region_id,city_id,region) values (532527,5325,'泸西县');
insert into web_regions (region_id,city_id,region) values (532528,5325,'元阳县');
insert into web_regions (region_id,city_id,region) values (532529,5325,'红河县');
insert into web_regions (region_id,city_id,region) values (532530,5325,'金平苗族瑶族傣族自治县');
insert into web_regions (region_id,city_id,region) values (532531,5325,'绿春县');
insert into web_regions (region_id,city_id,region) values (532532,5325,'河口瑶族自治县');
insert into web_regions (region_id,city_id,region) values (532601,5326,'文山市');
insert into web_regions (region_id,city_id,region) values (532622,5326,'砚山县');
insert into web_regions (region_id,city_id,region) values (532623,5326,'西畴县');
insert into web_regions (region_id,city_id,region) values (532624,5326,'麻栗坡县');
insert into web_regions (region_id,city_id,region) values (532625,5326,'马关县');
insert into web_regions (region_id,city_id,region) values (532626,5326,'丘北县');
insert into web_regions (region_id,city_id,region) values (532627,5326,'广南县');
insert into web_regions (region_id,city_id,region) values (532628,5326,'富宁县');
insert into web_regions (region_id,city_id,region) values (532801,5328,'景洪市');
insert into web_regions (region_id,city_id,region) values (532822,5328,'勐海县');
insert into web_regions (region_id,city_id,region) values (532823,5328,'勐腊县');
insert into web_regions (region_id,city_id,region) values (532901,5329,'大理市');
insert into web_regions (region_id,city_id,region) values (532922,5329,'漾濞彝族自治县');
insert into web_regions (region_id,city_id,region) values (532923,5329,'祥云县');
insert into web_regions (region_id,city_id,region) values (532924,5329,'宾川县');
insert into web_regions (region_id,city_id,region) values (532925,5329,'弥渡县');
insert into web_regions (region_id,city_id,region) values (532926,5329,'南涧彝族自治县');
insert into web_regions (region_id,city_id,region) values (532927,5329,'巍山彝族回族自治县');
insert into web_regions (region_id,city_id,region) values (532928,5329,'永平县');
insert into web_regions (region_id,city_id,region) values (532929,5329,'云龙县');
insert into web_regions (region_id,city_id,region) values (532930,5329,'洱源县');
insert into web_regions (region_id,city_id,region) values (532931,5329,'剑川县');
insert into web_regions (region_id,city_id,region) values (532932,5329,'鹤庆县');
insert into web_regions (region_id,city_id,region) values (533102,5331,'瑞丽市');
insert into web_regions (region_id,city_id,region) values (533103,5331,'芒市');
insert into web_regions (region_id,city_id,region) values (533122,5331,'梁河县');
insert into web_regions (region_id,city_id,region) values (533123,5331,'盈江县');
insert into web_regions (region_id,city_id,region) values (533124,5331,'陇川县');
insert into web_regions (region_id,city_id,region) values (533321,5333,'泸水县');
insert into web_regions (region_id,city_id,region) values (533323,5333,'福贡县');
insert into web_regions (region_id,city_id,region) values (533324,5333,'贡山独龙族怒族自治县');
insert into web_regions (region_id,city_id,region) values (533325,5333,'兰坪白族普米族自治县');
insert into web_regions (region_id,city_id,region) values (533421,5334,'香格里拉市');
insert into web_regions (region_id,city_id,region) values (533422,5334,'德钦县');
insert into web_regions (region_id,city_id,region) values (533423,5334,'维西傈僳族自治县');
insert into web_regions (region_id,city_id,region) values (540102,5401,'城关区');
insert into web_regions (region_id,city_id,region) values (540121,5401,'林周县');
insert into web_regions (region_id,city_id,region) values (540122,5401,'当雄县');
insert into web_regions (region_id,city_id,region) values (540123,5401,'尼木县');
insert into web_regions (region_id,city_id,region) values (540124,5401,'曲水县');
insert into web_regions (region_id,city_id,region) values (540125,5401,'堆龙德庆县');
insert into web_regions (region_id,city_id,region) values (540126,5401,'达孜县');
insert into web_regions (region_id,city_id,region) values (540127,5401,'墨竹工卡县');
insert into web_regions (region_id,city_id,region) values (540202,5402,'桑珠孜区');
insert into web_regions (region_id,city_id,region) values (540221,5402,'南木林县');
insert into web_regions (region_id,city_id,region) values (540222,5402,'江孜县');
insert into web_regions (region_id,city_id,region) values (540223,5402,'定日县');
insert into web_regions (region_id,city_id,region) values (540224,5402,'萨迦县');
insert into web_regions (region_id,city_id,region) values (540225,5402,'拉孜县');
insert into web_regions (region_id,city_id,region) values (540226,5402,'昂仁县');
insert into web_regions (region_id,city_id,region) values (540227,5402,'谢通门县');
insert into web_regions (region_id,city_id,region) values (540228,5402,'白朗县');
insert into web_regions (region_id,city_id,region) values (540229,5402,'仁布县');
insert into web_regions (region_id,city_id,region) values (540230,5402,'康马县');
insert into web_regions (region_id,city_id,region) values (540231,5402,'定结县');
insert into web_regions (region_id,city_id,region) values (540232,5402,'仲巴县');
insert into web_regions (region_id,city_id,region) values (540233,5402,'亚东县');
insert into web_regions (region_id,city_id,region) values (540234,5402,'吉隆县');
insert into web_regions (region_id,city_id,region) values (540235,5402,'聂拉木县');
insert into web_regions (region_id,city_id,region) values (540236,5402,'萨嘎县');
insert into web_regions (region_id,city_id,region) values (540237,5402,'岗巴县');
insert into web_regions (region_id,city_id,region) values (540302,5403,'卡若区');
insert into web_regions (region_id,city_id,region) values (540321,5403,'江达县');
insert into web_regions (region_id,city_id,region) values (540322,5403,'贡觉县');
insert into web_regions (region_id,city_id,region) values (540323,5403,'类乌齐县');
insert into web_regions (region_id,city_id,region) values (540324,5403,'丁青县');
insert into web_regions (region_id,city_id,region) values (540325,5403,'察雅县');
insert into web_regions (region_id,city_id,region) values (540326,5403,'八宿县');
insert into web_regions (region_id,city_id,region) values (540327,5403,'左贡县');
insert into web_regions (region_id,city_id,region) values (540328,5403,'芒康县');
insert into web_regions (region_id,city_id,region) values (540329,5403,'洛隆县');
insert into web_regions (region_id,city_id,region) values (540330,5403,'边坝县');
insert into web_regions (region_id,city_id,region) values (542221,5422,'乃东县');
insert into web_regions (region_id,city_id,region) values (542222,5422,'扎囊县');
insert into web_regions (region_id,city_id,region) values (542223,5422,'贡嘎县');
insert into web_regions (region_id,city_id,region) values (542224,5422,'桑日县');
insert into web_regions (region_id,city_id,region) values (542225,5422,'琼结县');
insert into web_regions (region_id,city_id,region) values (542226,5422,'曲松县');
insert into web_regions (region_id,city_id,region) values (542227,5422,'措美县');
insert into web_regions (region_id,city_id,region) values (542228,5422,'洛扎县');
insert into web_regions (region_id,city_id,region) values (542229,5422,'加查县');
insert into web_regions (region_id,city_id,region) values (542231,5422,'隆子县');
insert into web_regions (region_id,city_id,region) values (542232,5422,'错那县');
insert into web_regions (region_id,city_id,region) values (542233,5422,'浪卡子县');
insert into web_regions (region_id,city_id,region) values (542421,5424,'那曲县');
insert into web_regions (region_id,city_id,region) values (542422,5424,'嘉黎县');
insert into web_regions (region_id,city_id,region) values (542423,5424,'比如县');
insert into web_regions (region_id,city_id,region) values (542424,5424,'聂荣县');
insert into web_regions (region_id,city_id,region) values (542425,5424,'安多县');
insert into web_regions (region_id,city_id,region) values (542426,5424,'申扎县');
insert into web_regions (region_id,city_id,region) values (542427,5424,'索县');
insert into web_regions (region_id,city_id,region) values (542428,5424,'班戈县');
insert into web_regions (region_id,city_id,region) values (542429,5424,'巴青县');
insert into web_regions (region_id,city_id,region) values (542430,5424,'尼玛县');
insert into web_regions (region_id,city_id,region) values (542431,5424,'双湖县');
insert into web_regions (region_id,city_id,region) values (542521,5425,'普兰县');
insert into web_regions (region_id,city_id,region) values (542522,5425,'札达县');
insert into web_regions (region_id,city_id,region) values (542523,5425,'噶尔县');
insert into web_regions (region_id,city_id,region) values (542524,5425,'日土县');
insert into web_regions (region_id,city_id,region) values (542525,5425,'革吉县');
insert into web_regions (region_id,city_id,region) values (542526,5425,'改则县');
insert into web_regions (region_id,city_id,region) values (542527,5425,'措勤县');
insert into web_regions (region_id,city_id,region) values (542621,5426,'林芝县');
insert into web_regions (region_id,city_id,region) values (542622,5426,'工布江达县');
insert into web_regions (region_id,city_id,region) values (542623,5426,'米林县');
insert into web_regions (region_id,city_id,region) values (542624,5426,'墨脱县');
insert into web_regions (region_id,city_id,region) values (542625,5426,'波密县');
insert into web_regions (region_id,city_id,region) values (542626,5426,'察隅县');
insert into web_regions (region_id,city_id,region) values (542627,5426,'朗县');
insert into web_regions (region_id,city_id,region) values (610102,6101,'新城区');
insert into web_regions (region_id,city_id,region) values (610103,6101,'碑林区');
insert into web_regions (region_id,city_id,region) values (610104,6101,'莲湖区');
insert into web_regions (region_id,city_id,region) values (610111,6101,'灞桥区');
insert into web_regions (region_id,city_id,region) values (610112,6101,'未央区');
insert into web_regions (region_id,city_id,region) values (610113,6101,'雁塔区');
insert into web_regions (region_id,city_id,region) values (610114,6101,'阎良区');
insert into web_regions (region_id,city_id,region) values (610115,6101,'临潼区');
insert into web_regions (region_id,city_id,region) values (610116,6101,'长安区');
insert into web_regions (region_id,city_id,region) values (610122,6101,'蓝田县');
insert into web_regions (region_id,city_id,region) values (610124,6101,'周至县');
insert into web_regions (region_id,city_id,region) values (610125,6101,'户县');
insert into web_regions (region_id,city_id,region) values (610126,6101,'高陵区');
insert into web_regions (region_id,city_id,region) values (610202,6102,'王益区');
insert into web_regions (region_id,city_id,region) values (610203,6102,'印台区');
insert into web_regions (region_id,city_id,region) values (610204,6102,'耀州区');
insert into web_regions (region_id,city_id,region) values (610222,6102,'宜君县');
insert into web_regions (region_id,city_id,region) values (610302,6103,'渭滨区');
insert into web_regions (region_id,city_id,region) values (610303,6103,'金台区');
insert into web_regions (region_id,city_id,region) values (610304,6103,'陈仓区');
insert into web_regions (region_id,city_id,region) values (610322,6103,'凤翔县');
insert into web_regions (region_id,city_id,region) values (610323,6103,'岐山县');
insert into web_regions (region_id,city_id,region) values (610324,6103,'扶风县');
insert into web_regions (region_id,city_id,region) values (610326,6103,'眉县');
insert into web_regions (region_id,city_id,region) values (610327,6103,'陇县');
insert into web_regions (region_id,city_id,region) values (610328,6103,'千阳县');
insert into web_regions (region_id,city_id,region) values (610329,6103,'麟游县');
insert into web_regions (region_id,city_id,region) values (610330,6103,'凤县');
insert into web_regions (region_id,city_id,region) values (610331,6103,'太白县');
insert into web_regions (region_id,city_id,region) values (610402,6104,'秦都区');
insert into web_regions (region_id,city_id,region) values (610403,6104,'杨陵区');
insert into web_regions (region_id,city_id,region) values (610404,6104,'渭城区');
insert into web_regions (region_id,city_id,region) values (610422,6104,'三原县');
insert into web_regions (region_id,city_id,region) values (610423,6104,'泾阳县');
insert into web_regions (region_id,city_id,region) values (610424,6104,'乾县');
insert into web_regions (region_id,city_id,region) values (610425,6104,'礼泉县');
insert into web_regions (region_id,city_id,region) values (610426,6104,'永寿县');
insert into web_regions (region_id,city_id,region) values (610427,6104,'彬县');
insert into web_regions (region_id,city_id,region) values (610428,6104,'长武县');
insert into web_regions (region_id,city_id,region) values (610429,6104,'旬邑县');
insert into web_regions (region_id,city_id,region) values (610430,6104,'淳化县');
insert into web_regions (region_id,city_id,region) values (610431,6104,'武功县');
insert into web_regions (region_id,city_id,region) values (610481,6104,'兴平市');
insert into web_regions (region_id,city_id,region) values (610502,6105,'临渭区');
insert into web_regions (region_id,city_id,region) values (610521,6105,'华县');
insert into web_regions (region_id,city_id,region) values (610522,6105,'潼关县');
insert into web_regions (region_id,city_id,region) values (610523,6105,'大荔县');
insert into web_regions (region_id,city_id,region) values (610524,6105,'合阳县');
insert into web_regions (region_id,city_id,region) values (610525,6105,'澄城县');
insert into web_regions (region_id,city_id,region) values (610526,6105,'蒲城县');
insert into web_regions (region_id,city_id,region) values (610527,6105,'白水县');
insert into web_regions (region_id,city_id,region) values (610528,6105,'富平县');
insert into web_regions (region_id,city_id,region) values (610581,6105,'韩城市');
insert into web_regions (region_id,city_id,region) values (610582,6105,'华阴市');
insert into web_regions (region_id,city_id,region) values (610602,6106,'宝塔区');
insert into web_regions (region_id,city_id,region) values (610621,6106,'延长县');
insert into web_regions (region_id,city_id,region) values (610622,6106,'延川县');
insert into web_regions (region_id,city_id,region) values (610623,6106,'子长县');
insert into web_regions (region_id,city_id,region) values (610624,6106,'安塞县');
insert into web_regions (region_id,city_id,region) values (610625,6106,'志丹县');
insert into web_regions (region_id,city_id,region) values (610626,6106,'吴起县');
insert into web_regions (region_id,city_id,region) values (610627,6106,'甘泉县');
insert into web_regions (region_id,city_id,region) values (610628,6106,'富县');
insert into web_regions (region_id,city_id,region) values (610629,6106,'洛川县');
insert into web_regions (region_id,city_id,region) values (610630,6106,'宜川县');
insert into web_regions (region_id,city_id,region) values (610631,6106,'黄龙县');
insert into web_regions (region_id,city_id,region) values (610632,6106,'黄陵县');
insert into web_regions (region_id,city_id,region) values (610702,6107,'汉台区');
insert into web_regions (region_id,city_id,region) values (610721,6107,'南郑县');
insert into web_regions (region_id,city_id,region) values (610722,6107,'城固县');
insert into web_regions (region_id,city_id,region) values (610723,6107,'洋县');
insert into web_regions (region_id,city_id,region) values (610724,6107,'西乡县');
insert into web_regions (region_id,city_id,region) values (610725,6107,'勉县');
insert into web_regions (region_id,city_id,region) values (610726,6107,'宁强县');
insert into web_regions (region_id,city_id,region) values (610727,6107,'略阳县');
insert into web_regions (region_id,city_id,region) values (610728,6107,'镇巴县');
insert into web_regions (region_id,city_id,region) values (610729,6107,'留坝县');
insert into web_regions (region_id,city_id,region) values (610730,6107,'佛坪县');
insert into web_regions (region_id,city_id,region) values (610802,6108,'榆阳区');
insert into web_regions (region_id,city_id,region) values (610821,6108,'神木县');
insert into web_regions (region_id,city_id,region) values (610822,6108,'府谷县');
insert into web_regions (region_id,city_id,region) values (610823,6108,'横山县');
insert into web_regions (region_id,city_id,region) values (610824,6108,'靖边县');
insert into web_regions (region_id,city_id,region) values (610825,6108,'定边县');
insert into web_regions (region_id,city_id,region) values (610826,6108,'绥德县');
insert into web_regions (region_id,city_id,region) values (610827,6108,'米脂县');
insert into web_regions (region_id,city_id,region) values (610828,6108,'佳县');
insert into web_regions (region_id,city_id,region) values (610829,6108,'吴堡县');
insert into web_regions (region_id,city_id,region) values (610830,6108,'清涧县');
insert into web_regions (region_id,city_id,region) values (610831,6108,'子洲县');
insert into web_regions (region_id,city_id,region) values (610902,6109,'汉滨区');
insert into web_regions (region_id,city_id,region) values (610921,6109,'汉阴县');
insert into web_regions (region_id,city_id,region) values (610922,6109,'石泉县');
insert into web_regions (region_id,city_id,region) values (610923,6109,'宁陕县');
insert into web_regions (region_id,city_id,region) values (610924,6109,'紫阳县');
insert into web_regions (region_id,city_id,region) values (610925,6109,'岚皋县');
insert into web_regions (region_id,city_id,region) values (610926,6109,'平利县');
insert into web_regions (region_id,city_id,region) values (610927,6109,'镇坪县');
insert into web_regions (region_id,city_id,region) values (610928,6109,'旬阳县');
insert into web_regions (region_id,city_id,region) values (610929,6109,'白河县');
insert into web_regions (region_id,city_id,region) values (611002,6110,'商州区');
insert into web_regions (region_id,city_id,region) values (611021,6110,'洛南县');
insert into web_regions (region_id,city_id,region) values (611022,6110,'丹凤县');
insert into web_regions (region_id,city_id,region) values (611023,6110,'商南县');
insert into web_regions (region_id,city_id,region) values (611024,6110,'山阳县');
insert into web_regions (region_id,city_id,region) values (611025,6110,'镇安县');
insert into web_regions (region_id,city_id,region) values (611026,6110,'柞水县');
insert into web_regions (region_id,city_id,region) values (611101,6111,'空港新城');
insert into web_regions (region_id,city_id,region) values (611102,6111,'沣东新城');
insert into web_regions (region_id,city_id,region) values (611103,6111,'秦汉新城');
insert into web_regions (region_id,city_id,region) values (611104,6111,'沣西新城');
insert into web_regions (region_id,city_id,region) values (611105,6111,'泾河新城');
insert into web_regions (region_id,city_id,region) values (620102,6201,'城关区');
insert into web_regions (region_id,city_id,region) values (620103,6201,'七里河区');
insert into web_regions (region_id,city_id,region) values (620104,6201,'西固区');
insert into web_regions (region_id,city_id,region) values (620105,6201,'安宁区');
insert into web_regions (region_id,city_id,region) values (620111,6201,'红古区');
insert into web_regions (region_id,city_id,region) values (620121,6201,'永登县');
insert into web_regions (region_id,city_id,region) values (620122,6201,'皋兰县');
insert into web_regions (region_id,city_id,region) values (620123,6201,'榆中县');
insert into web_regions (region_id,city_id,region) values (620201,6202,'雄关区');
insert into web_regions (region_id,city_id,region) values (620202,6202,'长城区');
insert into web_regions (region_id,city_id,region) values (620203,6202,'镜铁区');
insert into web_regions (region_id,city_id,region) values (620302,6203,'金川区');
insert into web_regions (region_id,city_id,region) values (620321,6203,'永昌县');
insert into web_regions (region_id,city_id,region) values (620402,6204,'白银区');
insert into web_regions (region_id,city_id,region) values (620403,6204,'平川区');
insert into web_regions (region_id,city_id,region) values (620421,6204,'靖远县');
insert into web_regions (region_id,city_id,region) values (620422,6204,'会宁县');
insert into web_regions (region_id,city_id,region) values (620423,6204,'景泰县');
insert into web_regions (region_id,city_id,region) values (620502,6205,'秦州区');
insert into web_regions (region_id,city_id,region) values (620503,6205,'麦积区');
insert into web_regions (region_id,city_id,region) values (620521,6205,'清水县');
insert into web_regions (region_id,city_id,region) values (620522,6205,'秦安县');
insert into web_regions (region_id,city_id,region) values (620523,6205,'甘谷县');
insert into web_regions (region_id,city_id,region) values (620524,6205,'武山县');
insert into web_regions (region_id,city_id,region) values (620525,6205,'张家川回族自治县');
insert into web_regions (region_id,city_id,region) values (620602,6206,'凉州区');
insert into web_regions (region_id,city_id,region) values (620621,6206,'民勤县');
insert into web_regions (region_id,city_id,region) values (620622,6206,'古浪县');
insert into web_regions (region_id,city_id,region) values (620623,6206,'天祝藏族自治县');
insert into web_regions (region_id,city_id,region) values (620702,6207,'甘州区');
insert into web_regions (region_id,city_id,region) values (620721,6207,'肃南裕固族自治县');
insert into web_regions (region_id,city_id,region) values (620722,6207,'民乐县');
insert into web_regions (region_id,city_id,region) values (620723,6207,'临泽县');
insert into web_regions (region_id,city_id,region) values (620724,6207,'高台县');
insert into web_regions (region_id,city_id,region) values (620725,6207,'山丹县');
insert into web_regions (region_id,city_id,region) values (620802,6208,'崆峒区');
insert into web_regions (region_id,city_id,region) values (620821,6208,'泾川县');
insert into web_regions (region_id,city_id,region) values (620822,6208,'灵台县');
insert into web_regions (region_id,city_id,region) values (620823,6208,'崇信县');
insert into web_regions (region_id,city_id,region) values (620824,6208,'华亭县');
insert into web_regions (region_id,city_id,region) values (620825,6208,'庄浪县');
insert into web_regions (region_id,city_id,region) values (620826,6208,'静宁县');
insert into web_regions (region_id,city_id,region) values (620902,6209,'肃州区');
insert into web_regions (region_id,city_id,region) values (620921,6209,'金塔县');
insert into web_regions (region_id,city_id,region) values (620922,6209,'瓜州县');
insert into web_regions (region_id,city_id,region) values (620923,6209,'肃北蒙古族自治县');
insert into web_regions (region_id,city_id,region) values (620924,6209,'阿克塞哈萨克族自治县');
insert into web_regions (region_id,city_id,region) values (620981,6209,'玉门市');
insert into web_regions (region_id,city_id,region) values (620982,6209,'敦煌市');
insert into web_regions (region_id,city_id,region) values (621002,6210,'西峰区');
insert into web_regions (region_id,city_id,region) values (621021,6210,'庆城县');
insert into web_regions (region_id,city_id,region) values (621022,6210,'环县');
insert into web_regions (region_id,city_id,region) values (621023,6210,'华池县');
insert into web_regions (region_id,city_id,region) values (621024,6210,'合水县');
insert into web_regions (region_id,city_id,region) values (621025,6210,'正宁县');
insert into web_regions (region_id,city_id,region) values (621026,6210,'宁县');
insert into web_regions (region_id,city_id,region) values (621027,6210,'镇原县');
insert into web_regions (region_id,city_id,region) values (621102,6211,'安定区');
insert into web_regions (region_id,city_id,region) values (621121,6211,'通渭县');
insert into web_regions (region_id,city_id,region) values (621122,6211,'陇西县');
insert into web_regions (region_id,city_id,region) values (621123,6211,'渭源县');
insert into web_regions (region_id,city_id,region) values (621124,6211,'临洮县');
insert into web_regions (region_id,city_id,region) values (621125,6211,'漳县');
insert into web_regions (region_id,city_id,region) values (621126,6211,'岷县');
insert into web_regions (region_id,city_id,region) values (621202,6212,'武都区');
insert into web_regions (region_id,city_id,region) values (621221,6212,'成县');
insert into web_regions (region_id,city_id,region) values (621222,6212,'文县');
insert into web_regions (region_id,city_id,region) values (621223,6212,'宕昌县');
insert into web_regions (region_id,city_id,region) values (621224,6212,'康县');
insert into web_regions (region_id,city_id,region) values (621225,6212,'西和县');
insert into web_regions (region_id,city_id,region) values (621226,6212,'礼县');
insert into web_regions (region_id,city_id,region) values (621227,6212,'徽县');
insert into web_regions (region_id,city_id,region) values (621228,6212,'两当县');
insert into web_regions (region_id,city_id,region) values (622901,6229,'临夏市');
insert into web_regions (region_id,city_id,region) values (622921,6229,'临夏县');
insert into web_regions (region_id,city_id,region) values (622922,6229,'康乐县');
insert into web_regions (region_id,city_id,region) values (622923,6229,'永靖县');
insert into web_regions (region_id,city_id,region) values (622924,6229,'广河县');
insert into web_regions (region_id,city_id,region) values (622925,6229,'和政县');
insert into web_regions (region_id,city_id,region) values (622926,6229,'东乡族自治县');
insert into web_regions (region_id,city_id,region) values (622927,6229,'积石山保安族东乡族撒拉族自治县');
insert into web_regions (region_id,city_id,region) values (623001,6230,'合作市');
insert into web_regions (region_id,city_id,region) values (623021,6230,'临潭县');
insert into web_regions (region_id,city_id,region) values (623022,6230,'卓尼县');
insert into web_regions (region_id,city_id,region) values (623023,6230,'舟曲县');
insert into web_regions (region_id,city_id,region) values (623024,6230,'迭部县');
insert into web_regions (region_id,city_id,region) values (623025,6230,'玛曲县');
insert into web_regions (region_id,city_id,region) values (623026,6230,'碌曲县');
insert into web_regions (region_id,city_id,region) values (623027,6230,'夏河县');
insert into web_regions (region_id,city_id,region) values (630102,6301,'城东区');
insert into web_regions (region_id,city_id,region) values (630103,6301,'城中区');
insert into web_regions (region_id,city_id,region) values (630104,6301,'城西区');
insert into web_regions (region_id,city_id,region) values (630105,6301,'城北区');
insert into web_regions (region_id,city_id,region) values (630121,6301,'大通回族土族自治县');
insert into web_regions (region_id,city_id,region) values (630122,6301,'湟中县');
insert into web_regions (region_id,city_id,region) values (630123,6301,'湟源县');
insert into web_regions (region_id,city_id,region) values (630202,6302,'乐都区');
insert into web_regions (region_id,city_id,region) values (630221,6302,'平安县');
insert into web_regions (region_id,city_id,region) values (630222,6302,'民和回族土族自治县');
insert into web_regions (region_id,city_id,region) values (630223,6302,'互助土族自治县');
insert into web_regions (region_id,city_id,region) values (630224,6302,'化隆回族自治县');
insert into web_regions (region_id,city_id,region) values (630225,6302,'循化撒拉族自治县');
insert into web_regions (region_id,city_id,region) values (632221,6322,'门源回族自治县');
insert into web_regions (region_id,city_id,region) values (632222,6322,'祁连县');
insert into web_regions (region_id,city_id,region) values (632223,6322,'海晏县');
insert into web_regions (region_id,city_id,region) values (632224,6322,'刚察县');
insert into web_regions (region_id,city_id,region) values (632321,6323,'同仁县');
insert into web_regions (region_id,city_id,region) values (632322,6323,'尖扎县');
insert into web_regions (region_id,city_id,region) values (632323,6323,'泽库县');
insert into web_regions (region_id,city_id,region) values (632324,6323,'河南蒙古族自治县');
insert into web_regions (region_id,city_id,region) values (632521,6325,'共和县');
insert into web_regions (region_id,city_id,region) values (632522,6325,'同德县');
insert into web_regions (region_id,city_id,region) values (632523,6325,'贵德县');
insert into web_regions (region_id,city_id,region) values (632524,6325,'兴海县');
insert into web_regions (region_id,city_id,region) values (632525,6325,'贵南县');
insert into web_regions (region_id,city_id,region) values (632621,6326,'玛沁县');
insert into web_regions (region_id,city_id,region) values (632622,6326,'班玛县');
insert into web_regions (region_id,city_id,region) values (632623,6326,'甘德县');
insert into web_regions (region_id,city_id,region) values (632624,6326,'达日县');
insert into web_regions (region_id,city_id,region) values (632625,6326,'久治县');
insert into web_regions (region_id,city_id,region) values (632626,6326,'玛多县');
insert into web_regions (region_id,city_id,region) values (632701,6327,'玉树市');
insert into web_regions (region_id,city_id,region) values (632722,6327,'杂多县');
insert into web_regions (region_id,city_id,region) values (632723,6327,'称多县');
insert into web_regions (region_id,city_id,region) values (632724,6327,'治多县');
insert into web_regions (region_id,city_id,region) values (632725,6327,'囊谦县');
insert into web_regions (region_id,city_id,region) values (632726,6327,'曲麻莱县');
insert into web_regions (region_id,city_id,region) values (632801,6328,'格尔木市');
insert into web_regions (region_id,city_id,region) values (632802,6328,'德令哈市');
insert into web_regions (region_id,city_id,region) values (632821,6328,'乌兰县');
insert into web_regions (region_id,city_id,region) values (632822,6328,'都兰县');
insert into web_regions (region_id,city_id,region) values (632823,6328,'天峻县');
insert into web_regions (region_id,city_id,region) values (640104,6401,'兴庆区');
insert into web_regions (region_id,city_id,region) values (640105,6401,'西夏区');
insert into web_regions (region_id,city_id,region) values (640106,6401,'金凤区');
insert into web_regions (region_id,city_id,region) values (640121,6401,'永宁县');
insert into web_regions (region_id,city_id,region) values (640122,6401,'贺兰县');
insert into web_regions (region_id,city_id,region) values (640181,6401,'灵武市');
insert into web_regions (region_id,city_id,region) values (640202,6402,'大武口区');
insert into web_regions (region_id,city_id,region) values (640205,6402,'惠农区');
insert into web_regions (region_id,city_id,region) values (640221,6402,'平罗县');
insert into web_regions (region_id,city_id,region) values (640302,6403,'利通区');
insert into web_regions (region_id,city_id,region) values (640303,6403,'红寺堡区');
insert into web_regions (region_id,city_id,region) values (640323,6403,'盐池县');
insert into web_regions (region_id,city_id,region) values (640324,6403,'同心县');
insert into web_regions (region_id,city_id,region) values (640381,6403,'青铜峡市');
insert into web_regions (region_id,city_id,region) values (640402,6404,'原州区');
insert into web_regions (region_id,city_id,region) values (640422,6404,'西吉县');
insert into web_regions (region_id,city_id,region) values (640423,6404,'隆德县');
insert into web_regions (region_id,city_id,region) values (640424,6404,'泾源县');
insert into web_regions (region_id,city_id,region) values (640425,6404,'彭阳县');
insert into web_regions (region_id,city_id,region) values (640502,6405,'沙坡头区');
insert into web_regions (region_id,city_id,region) values (640521,6405,'中宁县');
insert into web_regions (region_id,city_id,region) values (640522,6405,'海原县');
insert into web_regions (region_id,city_id,region) values (650102,6501,'天山区');
insert into web_regions (region_id,city_id,region) values (650103,6501,'沙依巴克区');
insert into web_regions (region_id,city_id,region) values (650104,6501,'新市区');
insert into web_regions (region_id,city_id,region) values (650105,6501,'水磨沟区');
insert into web_regions (region_id,city_id,region) values (650106,6501,'头屯河区');
insert into web_regions (region_id,city_id,region) values (650107,6501,'达坂城区');
insert into web_regions (region_id,city_id,region) values (650109,6501,'米东区');
insert into web_regions (region_id,city_id,region) values (650121,6501,'乌鲁木齐县');
insert into web_regions (region_id,city_id,region) values (650202,6502,'独山子区');
insert into web_regions (region_id,city_id,region) values (650203,6502,'克拉玛依区');
insert into web_regions (region_id,city_id,region) values (650204,6502,'白碱滩区');
insert into web_regions (region_id,city_id,region) values (650205,6502,'乌尔禾区');
insert into web_regions (region_id,city_id,region) values (652101,6521,'吐鲁番市');
insert into web_regions (region_id,city_id,region) values (652122,6521,'鄯善县');
insert into web_regions (region_id,city_id,region) values (652123,6521,'托克逊县');
insert into web_regions (region_id,city_id,region) values (652201,6522,'哈密市');
insert into web_regions (region_id,city_id,region) values (652222,6522,'巴里坤哈萨克自治县');
insert into web_regions (region_id,city_id,region) values (652223,6522,'伊吾县');
insert into web_regions (region_id,city_id,region) values (652301,6523,'昌吉市');
insert into web_regions (region_id,city_id,region) values (652302,6523,'阜康市');
insert into web_regions (region_id,city_id,region) values (652323,6523,'呼图壁县');
insert into web_regions (region_id,city_id,region) values (652324,6523,'玛纳斯县');
insert into web_regions (region_id,city_id,region) values (652325,6523,'奇台县');
insert into web_regions (region_id,city_id,region) values (652327,6523,'吉木萨尔县');
insert into web_regions (region_id,city_id,region) values (652328,6523,'木垒哈萨克自治县');
insert into web_regions (region_id,city_id,region) values (652701,6527,'博乐市');
insert into web_regions (region_id,city_id,region) values (652702,6527,'阿拉山口市');
insert into web_regions (region_id,city_id,region) values (652722,6527,'精河县');
insert into web_regions (region_id,city_id,region) values (652723,6527,'温泉县');
insert into web_regions (region_id,city_id,region) values (652801,6528,'库尔勒市');
insert into web_regions (region_id,city_id,region) values (652822,6528,'轮台县');
insert into web_regions (region_id,city_id,region) values (652823,6528,'尉犁县');
insert into web_regions (region_id,city_id,region) values (652824,6528,'若羌县');
insert into web_regions (region_id,city_id,region) values (652825,6528,'且末县');
insert into web_regions (region_id,city_id,region) values (652826,6528,'焉耆回族自治县');
insert into web_regions (region_id,city_id,region) values (652827,6528,'和静县');
insert into web_regions (region_id,city_id,region) values (652828,6528,'和硕县');
insert into web_regions (region_id,city_id,region) values (652829,6528,'博湖县');
insert into web_regions (region_id,city_id,region) values (652901,6529,'阿克苏市');
insert into web_regions (region_id,city_id,region) values (652922,6529,'温宿县');
insert into web_regions (region_id,city_id,region) values (652923,6529,'库车县');
insert into web_regions (region_id,city_id,region) values (652924,6529,'沙雅县');
insert into web_regions (region_id,city_id,region) values (652925,6529,'新和县');
insert into web_regions (region_id,city_id,region) values (652926,6529,'拜城县');
insert into web_regions (region_id,city_id,region) values (652927,6529,'乌什县');
insert into web_regions (region_id,city_id,region) values (652928,6529,'阿瓦提县');
insert into web_regions (region_id,city_id,region) values (652929,6529,'柯坪县');
insert into web_regions (region_id,city_id,region) values (653001,6530,'阿图什市');
insert into web_regions (region_id,city_id,region) values (653022,6530,'阿克陶县');
insert into web_regions (region_id,city_id,region) values (653023,6530,'阿合奇县');
insert into web_regions (region_id,city_id,region) values (653024,6530,'乌恰县');
insert into web_regions (region_id,city_id,region) values (653101,6531,'喀什市');
insert into web_regions (region_id,city_id,region) values (653121,6531,'疏附县');
insert into web_regions (region_id,city_id,region) values (653122,6531,'疏勒县');
insert into web_regions (region_id,city_id,region) values (653123,6531,'英吉沙县');
insert into web_regions (region_id,city_id,region) values (653124,6531,'泽普县');
insert into web_regions (region_id,city_id,region) values (653125,6531,'莎车县');
insert into web_regions (region_id,city_id,region) values (653126,6531,'叶城县');
insert into web_regions (region_id,city_id,region) values (653127,6531,'麦盖提县');
insert into web_regions (region_id,city_id,region) values (653128,6531,'岳普湖县');
insert into web_regions (region_id,city_id,region) values (653129,6531,'伽师县');
insert into web_regions (region_id,city_id,region) values (653130,6531,'巴楚县');
insert into web_regions (region_id,city_id,region) values (653131,6531,'塔什库尔干塔吉克自治县');
insert into web_regions (region_id,city_id,region) values (653201,6532,'和田市');
insert into web_regions (region_id,city_id,region) values (653221,6532,'和田县');
insert into web_regions (region_id,city_id,region) values (653222,6532,'墨玉县');
insert into web_regions (region_id,city_id,region) values (653223,6532,'皮山县');
insert into web_regions (region_id,city_id,region) values (653224,6532,'洛浦县');
insert into web_regions (region_id,city_id,region) values (653225,6532,'策勒县');
insert into web_regions (region_id,city_id,region) values (653226,6532,'于田县');
insert into web_regions (region_id,city_id,region) values (653227,6532,'民丰县');
insert into web_regions (region_id,city_id,region) values (654002,6540,'伊宁市');
insert into web_regions (region_id,city_id,region) values (654003,6540,'奎屯市');
insert into web_regions (region_id,city_id,region) values (654004,6540,'霍尔果斯市');
insert into web_regions (region_id,city_id,region) values (654021,6540,'伊宁县');
insert into web_regions (region_id,city_id,region) values (654022,6540,'察布查尔锡伯自治县');
insert into web_regions (region_id,city_id,region) values (654023,6540,'霍城县');
insert into web_regions (region_id,city_id,region) values (654024,6540,'巩留县');
insert into web_regions (region_id,city_id,region) values (654025,6540,'新源县');
insert into web_regions (region_id,city_id,region) values (654026,6540,'昭苏县');
insert into web_regions (region_id,city_id,region) values (654027,6540,'特克斯县');
insert into web_regions (region_id,city_id,region) values (654028,6540,'尼勒克县');
insert into web_regions (region_id,city_id,region) values (654201,6542,'塔城市');
insert into web_regions (region_id,city_id,region) values (654202,6542,'乌苏市');
insert into web_regions (region_id,city_id,region) values (654221,6542,'额敏县');
insert into web_regions (region_id,city_id,region) values (654223,6542,'沙湾县');
insert into web_regions (region_id,city_id,region) values (654224,6542,'托里县');
insert into web_regions (region_id,city_id,region) values (654225,6542,'裕民县');
insert into web_regions (region_id,city_id,region) values (654226,6542,'和布克赛尔蒙古自治县');
insert into web_regions (region_id,city_id,region) values (654301,6543,'阿勒泰市');
insert into web_regions (region_id,city_id,region) values (654321,6543,'布尔津县');
insert into web_regions (region_id,city_id,region) values (654322,6543,'富蕴县');
insert into web_regions (region_id,city_id,region) values (654323,6543,'福海县');
insert into web_regions (region_id,city_id,region) values (654324,6543,'哈巴河县');
insert into web_regions (region_id,city_id,region) values (654325,6543,'青河县');
insert into web_regions (region_id,city_id,region) values (654326,6543,'吉木乃县');
insert into web_regions (region_id,city_id,region) values (659001,6590,'石河子市');
insert into web_regions (region_id,city_id,region) values (659002,6590,'阿拉尔市');
insert into web_regions (region_id,city_id,region) values (659003,6590,'图木舒克市');
insert into web_regions (region_id,city_id,region) values (659004,6590,'五家渠市');
insert into web_regions (region_id,city_id,region) values (659005,6590,'北屯市');
insert into web_regions (region_id,city_id,region) values (659006,6590,'铁门关市');
insert into web_regions (region_id,city_id,region) values (659007,6590,'双河市');
insert into web_regions (region_id,city_id,region) values (710101,7101,'松山区');
insert into web_regions (region_id,city_id,region) values (710102,7101,'信义区');
insert into web_regions (region_id,city_id,region) values (710103,7101,'大安区');
insert into web_regions (region_id,city_id,region) values (710104,7101,'中山区');
insert into web_regions (region_id,city_id,region) values (710105,7101,'中正区');
insert into web_regions (region_id,city_id,region) values (710106,7101,'大同区');
insert into web_regions (region_id,city_id,region) values (710107,7101,'万华区');
insert into web_regions (region_id,city_id,region) values (710108,7101,'文山区');
insert into web_regions (region_id,city_id,region) values (710109,7101,'南港区');
insert into web_regions (region_id,city_id,region) values (710110,7101,'内湖区');
insert into web_regions (region_id,city_id,region) values (710111,7101,'士林区');
insert into web_regions (region_id,city_id,region) values (710112,7101,'北投区');
insert into web_regions (region_id,city_id,region) values (710201,7102,'盐埕区');
insert into web_regions (region_id,city_id,region) values (710202,7102,'鼓山区');
insert into web_regions (region_id,city_id,region) values (710203,7102,'左营区');
insert into web_regions (region_id,city_id,region) values (710204,7102,'楠梓区');
insert into web_regions (region_id,city_id,region) values (710205,7102,'三民区');
insert into web_regions (region_id,city_id,region) values (710206,7102,'新兴区');
insert into web_regions (region_id,city_id,region) values (710207,7102,'前金区');
insert into web_regions (region_id,city_id,region) values (710208,7102,'苓雅区');
insert into web_regions (region_id,city_id,region) values (710209,7102,'前镇区');
insert into web_regions (region_id,city_id,region) values (710210,7102,'旗津区');
insert into web_regions (region_id,city_id,region) values (710211,7102,'小港区');
insert into web_regions (region_id,city_id,region) values (710212,7102,'凤山区');
insert into web_regions (region_id,city_id,region) values (710213,7102,'林园区');
insert into web_regions (region_id,city_id,region) values (710214,7102,'大寮区');
insert into web_regions (region_id,city_id,region) values (710215,7102,'大树区');
insert into web_regions (region_id,city_id,region) values (710216,7102,'大社区');
insert into web_regions (region_id,city_id,region) values (710217,7102,'仁武区');
insert into web_regions (region_id,city_id,region) values (710218,7102,'鸟松区');
insert into web_regions (region_id,city_id,region) values (710219,7102,'冈山区');
insert into web_regions (region_id,city_id,region) values (710220,7102,'桥头区');
insert into web_regions (region_id,city_id,region) values (710221,7102,'燕巢区');
insert into web_regions (region_id,city_id,region) values (710222,7102,'田寮区');
insert into web_regions (region_id,city_id,region) values (710223,7102,'阿莲区');
insert into web_regions (region_id,city_id,region) values (710224,7102,'路竹区');
insert into web_regions (region_id,city_id,region) values (710225,7102,'湖内区');
insert into web_regions (region_id,city_id,region) values (710226,7102,'茄萣区');
insert into web_regions (region_id,city_id,region) values (710227,7102,'永安区');
insert into web_regions (region_id,city_id,region) values (710228,7102,'弥陀区');
insert into web_regions (region_id,city_id,region) values (710229,7102,'梓官区');
insert into web_regions (region_id,city_id,region) values (710230,7102,'旗山区');
insert into web_regions (region_id,city_id,region) values (710231,7102,'美浓区');
insert into web_regions (region_id,city_id,region) values (710232,7102,'六龟区');
insert into web_regions (region_id,city_id,region) values (710233,7102,'甲仙区');
insert into web_regions (region_id,city_id,region) values (710234,7102,'杉林区');
insert into web_regions (region_id,city_id,region) values (710235,7102,'内门区');
insert into web_regions (region_id,city_id,region) values (710236,7102,'茂林区');
insert into web_regions (region_id,city_id,region) values (710237,7102,'桃源区');
insert into web_regions (region_id,city_id,region) values (710238,7102,'那玛夏区');
insert into web_regions (region_id,city_id,region) values (710301,7103,'中正区');
insert into web_regions (region_id,city_id,region) values (710302,7103,'七堵区');
insert into web_regions (region_id,city_id,region) values (710303,7103,'暖暖区');
insert into web_regions (region_id,city_id,region) values (710304,7103,'仁爱区');
insert into web_regions (region_id,city_id,region) values (710305,7103,'中山区');
insert into web_regions (region_id,city_id,region) values (710306,7103,'安乐区');
insert into web_regions (region_id,city_id,region) values (710307,7103,'信义区');
insert into web_regions (region_id,city_id,region) values (710401,7104,'中区');
insert into web_regions (region_id,city_id,region) values (710402,7104,'东区');
insert into web_regions (region_id,city_id,region) values (710403,7104,'南区');
insert into web_regions (region_id,city_id,region) values (710404,7104,'西区');
insert into web_regions (region_id,city_id,region) values (710405,7104,'北区');
insert into web_regions (region_id,city_id,region) values (710406,7104,'西屯区');
insert into web_regions (region_id,city_id,region) values (710407,7104,'南屯区');
insert into web_regions (region_id,city_id,region) values (710408,7104,'北屯区');
insert into web_regions (region_id,city_id,region) values (710409,7104,'丰原区');
insert into web_regions (region_id,city_id,region) values (710410,7104,'东势区');
insert into web_regions (region_id,city_id,region) values (710411,7104,'大甲区');
insert into web_regions (region_id,city_id,region) values (710412,7104,'清水区');
insert into web_regions (region_id,city_id,region) values (710413,7104,'沙鹿区');
insert into web_regions (region_id,city_id,region) values (710414,7104,'梧栖区');
insert into web_regions (region_id,city_id,region) values (710415,7104,'后里区');
insert into web_regions (region_id,city_id,region) values (710416,7104,'神冈区');
insert into web_regions (region_id,city_id,region) values (710417,7104,'潭子区');
insert into web_regions (region_id,city_id,region) values (710418,7104,'大雅区');
insert into web_regions (region_id,city_id,region) values (710419,7104,'新社区');
insert into web_regions (region_id,city_id,region) values (710420,7104,'石冈区');
insert into web_regions (region_id,city_id,region) values (710421,7104,'外埔区');
insert into web_regions (region_id,city_id,region) values (710422,7104,'大安区');
insert into web_regions (region_id,city_id,region) values (710423,7104,'乌日区');
insert into web_regions (region_id,city_id,region) values (710424,7104,'大肚区');
insert into web_regions (region_id,city_id,region) values (710425,7104,'龙井区');
insert into web_regions (region_id,city_id,region) values (710426,7104,'雾峰区');
insert into web_regions (region_id,city_id,region) values (710427,7104,'太平区');
insert into web_regions (region_id,city_id,region) values (710428,7104,'大里区');
insert into web_regions (region_id,city_id,region) values (710429,7104,'和平区');
insert into web_regions (region_id,city_id,region) values (710501,7105,'东区');
insert into web_regions (region_id,city_id,region) values (710502,7105,'南区');
insert into web_regions (region_id,city_id,region) values (710504,7105,'北区');
insert into web_regions (region_id,city_id,region) values (710506,7105,'安南区');
insert into web_regions (region_id,city_id,region) values (710507,7105,'安平区');
insert into web_regions (region_id,city_id,region) values (710508,7105,'中西区');
insert into web_regions (region_id,city_id,region) values (710509,7105,'新营区');
insert into web_regions (region_id,city_id,region) values (710510,7105,'盐水区');
insert into web_regions (region_id,city_id,region) values (710511,7105,'白河区');
insert into web_regions (region_id,city_id,region) values (710512,7105,'柳营区');
insert into web_regions (region_id,city_id,region) values (710513,7105,'后壁区');
insert into web_regions (region_id,city_id,region) values (710514,7105,'东山区');
insert into web_regions (region_id,city_id,region) values (710515,7105,'麻豆区');
insert into web_regions (region_id,city_id,region) values (710516,7105,'下营区');
insert into web_regions (region_id,city_id,region) values (710517,7105,'六甲区');
insert into web_regions (region_id,city_id,region) values (710518,7105,'官田区');
insert into web_regions (region_id,city_id,region) values (710519,7105,'大内区');
insert into web_regions (region_id,city_id,region) values (710520,7105,'佳里区');
insert into web_regions (region_id,city_id,region) values (710521,7105,'学甲区');
insert into web_regions (region_id,city_id,region) values (710522,7105,'西港区');
insert into web_regions (region_id,city_id,region) values (710523,7105,'七股区');
insert into web_regions (region_id,city_id,region) values (710524,7105,'将军区');
insert into web_regions (region_id,city_id,region) values (710525,7105,'北门区');
insert into web_regions (region_id,city_id,region) values (710526,7105,'新化区');
insert into web_regions (region_id,city_id,region) values (710527,7105,'善化区');
insert into web_regions (region_id,city_id,region) values (710528,7105,'新市区');
insert into web_regions (region_id,city_id,region) values (710529,7105,'安定区');
insert into web_regions (region_id,city_id,region) values (710530,7105,'山上区');
insert into web_regions (region_id,city_id,region) values (710531,7105,'玉井区');
insert into web_regions (region_id,city_id,region) values (710532,7105,'楠西区');
insert into web_regions (region_id,city_id,region) values (710533,7105,'南化区');
insert into web_regions (region_id,city_id,region) values (710534,7105,'左镇区');
insert into web_regions (region_id,city_id,region) values (710535,7105,'仁德区');
insert into web_regions (region_id,city_id,region) values (710536,7105,'归仁区');
insert into web_regions (region_id,city_id,region) values (710537,7105,'关庙区');
insert into web_regions (region_id,city_id,region) values (710538,7105,'龙崎区');
insert into web_regions (region_id,city_id,region) values (710539,7105,'永康区');
insert into web_regions (region_id,city_id,region) values (710601,7106,'东区');
insert into web_regions (region_id,city_id,region) values (710602,7106,'北区');
insert into web_regions (region_id,city_id,region) values (710603,7106,'香山区');
insert into web_regions (region_id,city_id,region) values (710701,7107,'东区');
insert into web_regions (region_id,city_id,region) values (710702,7107,'西区');
insert into web_regions (region_id,city_id,region) values (710801,7108,'板桥区');
insert into web_regions (region_id,city_id,region) values (710802,7108,'三重区');
insert into web_regions (region_id,city_id,region) values (710803,7108,'中和区');
insert into web_regions (region_id,city_id,region) values (710804,7108,'永和区');
insert into web_regions (region_id,city_id,region) values (710805,7108,'新庄区');
insert into web_regions (region_id,city_id,region) values (710806,7108,'新店区');
insert into web_regions (region_id,city_id,region) values (710807,7108,'树林区');
insert into web_regions (region_id,city_id,region) values (710808,7108,'莺歌区');
insert into web_regions (region_id,city_id,region) values (710809,7108,'三峡区');
insert into web_regions (region_id,city_id,region) values (710810,7108,'淡水区');
insert into web_regions (region_id,city_id,region) values (710811,7108,'汐止区');
insert into web_regions (region_id,city_id,region) values (710812,7108,'瑞芳区');
insert into web_regions (region_id,city_id,region) values (710813,7108,'土城区');
insert into web_regions (region_id,city_id,region) values (710814,7108,'芦洲区');
insert into web_regions (region_id,city_id,region) values (710815,7108,'五股区');
insert into web_regions (region_id,city_id,region) values (710816,7108,'泰山区');
insert into web_regions (region_id,city_id,region) values (710817,7108,'林口区');
insert into web_regions (region_id,city_id,region) values (710818,7108,'深坑区');
insert into web_regions (region_id,city_id,region) values (710819,7108,'石碇区');
insert into web_regions (region_id,city_id,region) values (710820,7108,'坪林区');
insert into web_regions (region_id,city_id,region) values (710821,7108,'三芝区');
insert into web_regions (region_id,city_id,region) values (710822,7108,'石门区');
insert into web_regions (region_id,city_id,region) values (710823,7108,'八里区');
insert into web_regions (region_id,city_id,region) values (710824,7108,'平溪区');
insert into web_regions (region_id,city_id,region) values (710825,7108,'双溪区');
insert into web_regions (region_id,city_id,region) values (710826,7108,'贡寮区');
insert into web_regions (region_id,city_id,region) values (710827,7108,'金山区');
insert into web_regions (region_id,city_id,region) values (710828,7108,'万里区');
insert into web_regions (region_id,city_id,region) values (710829,7108,'乌来区');
insert into web_regions (region_id,city_id,region) values (712201,7122,'宜兰市');
insert into web_regions (region_id,city_id,region) values (712221,7122,'罗东镇');
insert into web_regions (region_id,city_id,region) values (712222,7122,'苏澳镇');
insert into web_regions (region_id,city_id,region) values (712223,7122,'头城镇');
insert into web_regions (region_id,city_id,region) values (712224,7122,'礁溪乡');
insert into web_regions (region_id,city_id,region) values (712225,7122,'壮围乡');
insert into web_regions (region_id,city_id,region) values (712226,7122,'员山乡');
insert into web_regions (region_id,city_id,region) values (712227,7122,'冬山乡');
insert into web_regions (region_id,city_id,region) values (712228,7122,'五结乡');
insert into web_regions (region_id,city_id,region) values (712229,7122,'三星乡');
insert into web_regions (region_id,city_id,region) values (712230,7122,'大同乡');
insert into web_regions (region_id,city_id,region) values (712231,7122,'南澳乡');
insert into web_regions (region_id,city_id,region) values (712301,7123,'桃园市');
insert into web_regions (region_id,city_id,region) values (712302,7123,'中坜市');
insert into web_regions (region_id,city_id,region) values (712303,7123,'平镇市');
insert into web_regions (region_id,city_id,region) values (712304,7123,'八德市');
insert into web_regions (region_id,city_id,region) values (712305,7123,'杨梅市');
insert into web_regions (region_id,city_id,region) values (712306,7123,'芦竹市');
insert into web_regions (region_id,city_id,region) values (712321,7123,'大溪镇');
insert into web_regions (region_id,city_id,region) values (712324,7123,'大园乡');
insert into web_regions (region_id,city_id,region) values (712325,7123,'龟山乡');
insert into web_regions (region_id,city_id,region) values (712327,7123,'龙潭乡');
insert into web_regions (region_id,city_id,region) values (712329,7123,'新屋乡');
insert into web_regions (region_id,city_id,region) values (712330,7123,'观音乡');
insert into web_regions (region_id,city_id,region) values (712331,7123,'复兴乡');
insert into web_regions (region_id,city_id,region) values (712401,7124,'竹北市');
insert into web_regions (region_id,city_id,region) values (712421,7124,'竹东镇');
insert into web_regions (region_id,city_id,region) values (712422,7124,'新埔镇');
insert into web_regions (region_id,city_id,region) values (712423,7124,'关西镇');
insert into web_regions (region_id,city_id,region) values (712424,7124,'湖口乡');
insert into web_regions (region_id,city_id,region) values (712425,7124,'新丰乡');
insert into web_regions (region_id,city_id,region) values (712426,7124,'芎林乡');
insert into web_regions (region_id,city_id,region) values (712427,7124,'横山乡');
insert into web_regions (region_id,city_id,region) values (712428,7124,'北埔乡');
insert into web_regions (region_id,city_id,region) values (712429,7124,'宝山乡');
insert into web_regions (region_id,city_id,region) values (712430,7124,'峨眉乡');
insert into web_regions (region_id,city_id,region) values (712431,7124,'尖石乡');
insert into web_regions (region_id,city_id,region) values (712432,7124,'五峰乡');
insert into web_regions (region_id,city_id,region) values (712501,7125,'苗栗市');
insert into web_regions (region_id,city_id,region) values (712521,7125,'苑里镇');
insert into web_regions (region_id,city_id,region) values (712522,7125,'通霄镇');
insert into web_regions (region_id,city_id,region) values (712523,7125,'竹南镇');
insert into web_regions (region_id,city_id,region) values (712524,7125,'头份镇');
insert into web_regions (region_id,city_id,region) values (712525,7125,'后龙镇');
insert into web_regions (region_id,city_id,region) values (712526,7125,'卓兰镇');
insert into web_regions (region_id,city_id,region) values (712527,7125,'大湖乡');
insert into web_regions (region_id,city_id,region) values (712528,7125,'公馆乡');
insert into web_regions (region_id,city_id,region) values (712529,7125,'铜锣乡');
insert into web_regions (region_id,city_id,region) values (712530,7125,'南庄乡');
insert into web_regions (region_id,city_id,region) values (712531,7125,'头屋乡');
insert into web_regions (region_id,city_id,region) values (712532,7125,'三义乡');
insert into web_regions (region_id,city_id,region) values (712533,7125,'西湖乡');
insert into web_regions (region_id,city_id,region) values (712534,7125,'造桥乡');
insert into web_regions (region_id,city_id,region) values (712535,7125,'三湾乡');
insert into web_regions (region_id,city_id,region) values (712536,7125,'狮潭乡');
insert into web_regions (region_id,city_id,region) values (712537,7125,'泰安乡');
insert into web_regions (region_id,city_id,region) values (712701,7127,'彰化市');
insert into web_regions (region_id,city_id,region) values (712721,7127,'鹿港镇');
insert into web_regions (region_id,city_id,region) values (712722,7127,'和美镇');
insert into web_regions (region_id,city_id,region) values (712723,7127,'线西乡');
insert into web_regions (region_id,city_id,region) values (712724,7127,'伸港乡');
insert into web_regions (region_id,city_id,region) values (712725,7127,'福兴乡');
insert into web_regions (region_id,city_id,region) values (712726,7127,'秀水乡');
insert into web_regions (region_id,city_id,region) values (712727,7127,'花坛乡');
insert into web_regions (region_id,city_id,region) values (712728,7127,'芬园乡');
insert into web_regions (region_id,city_id,region) values (712729,7127,'员林镇');
insert into web_regions (region_id,city_id,region) values (712730,7127,'溪湖镇');
insert into web_regions (region_id,city_id,region) values (712731,7127,'田中镇');
insert into web_regions (region_id,city_id,region) values (712732,7127,'大村乡');
insert into web_regions (region_id,city_id,region) values (712733,7127,'埔盐乡');
insert into web_regions (region_id,city_id,region) values (712734,7127,'埔心乡');
insert into web_regions (region_id,city_id,region) values (712735,7127,'永靖乡');
insert into web_regions (region_id,city_id,region) values (712736,7127,'社头乡');
insert into web_regions (region_id,city_id,region) values (712737,7127,'二水乡');
insert into web_regions (region_id,city_id,region) values (712738,7127,'北斗镇');
insert into web_regions (region_id,city_id,region) values (712739,7127,'二林镇');
insert into web_regions (region_id,city_id,region) values (712740,7127,'田尾乡');
insert into web_regions (region_id,city_id,region) values (712741,7127,'埤头乡');
insert into web_regions (region_id,city_id,region) values (712742,7127,'芳苑乡');
insert into web_regions (region_id,city_id,region) values (712743,7127,'大城乡');
insert into web_regions (region_id,city_id,region) values (712744,7127,'竹塘乡');
insert into web_regions (region_id,city_id,region) values (712745,7127,'溪州乡');
insert into web_regions (region_id,city_id,region) values (712801,7128,'南投市');
insert into web_regions (region_id,city_id,region) values (712821,7128,'埔里镇');
insert into web_regions (region_id,city_id,region) values (712822,7128,'草屯镇');
insert into web_regions (region_id,city_id,region) values (712823,7128,'竹山镇');
insert into web_regions (region_id,city_id,region) values (712824,7128,'集集镇');
insert into web_regions (region_id,city_id,region) values (712825,7128,'名间乡');
insert into web_regions (region_id,city_id,region) values (712826,7128,'鹿谷乡');
insert into web_regions (region_id,city_id,region) values (712827,7128,'中寮乡');
insert into web_regions (region_id,city_id,region) values (712828,7128,'鱼池乡');
insert into web_regions (region_id,city_id,region) values (712829,7128,'国姓乡');
insert into web_regions (region_id,city_id,region) values (712830,7128,'水里乡');
insert into web_regions (region_id,city_id,region) values (712831,7128,'信义乡');
insert into web_regions (region_id,city_id,region) values (712832,7128,'仁爱乡');
insert into web_regions (region_id,city_id,region) values (712901,7129,'斗六市');
insert into web_regions (region_id,city_id,region) values (712921,7129,'斗南镇');
insert into web_regions (region_id,city_id,region) values (712922,7129,'虎尾镇');
insert into web_regions (region_id,city_id,region) values (712923,7129,'西螺镇');
insert into web_regions (region_id,city_id,region) values (712924,7129,'土库镇');
insert into web_regions (region_id,city_id,region) values (712925,7129,'北港镇');
insert into web_regions (region_id,city_id,region) values (712926,7129,'古坑乡');
insert into web_regions (region_id,city_id,region) values (712927,7129,'大埤乡');
insert into web_regions (region_id,city_id,region) values (712928,7129,'莿桐乡');
insert into web_regions (region_id,city_id,region) values (712929,7129,'林内乡');
insert into web_regions (region_id,city_id,region) values (712930,7129,'二仑乡');
insert into web_regions (region_id,city_id,region) values (712931,7129,'仑背乡');
insert into web_regions (region_id,city_id,region) values (712932,7129,'麦寮乡');
insert into web_regions (region_id,city_id,region) values (712933,7129,'东势乡');
insert into web_regions (region_id,city_id,region) values (712934,7129,'褒忠乡');
insert into web_regions (region_id,city_id,region) values (712935,7129,'台西乡');
insert into web_regions (region_id,city_id,region) values (712936,7129,'元长乡');
insert into web_regions (region_id,city_id,region) values (712937,7129,'四湖乡');
insert into web_regions (region_id,city_id,region) values (712938,7129,'口湖乡');
insert into web_regions (region_id,city_id,region) values (712939,7129,'水林乡');
insert into web_regions (region_id,city_id,region) values (713001,7130,'太保市');
insert into web_regions (region_id,city_id,region) values (713002,7130,'朴子市');
insert into web_regions (region_id,city_id,region) values (713023,7130,'布袋镇');
insert into web_regions (region_id,city_id,region) values (713024,7130,'大林镇');
insert into web_regions (region_id,city_id,region) values (713025,7130,'民雄乡');
insert into web_regions (region_id,city_id,region) values (713026,7130,'溪口乡');
insert into web_regions (region_id,city_id,region) values (713027,7130,'新港乡');
insert into web_regions (region_id,city_id,region) values (713028,7130,'六脚乡');
insert into web_regions (region_id,city_id,region) values (713029,7130,'东石乡');
insert into web_regions (region_id,city_id,region) values (713030,7130,'义竹乡');
insert into web_regions (region_id,city_id,region) values (713031,7130,'鹿草乡');
insert into web_regions (region_id,city_id,region) values (713032,7130,'水上乡');
insert into web_regions (region_id,city_id,region) values (713033,7130,'中埔乡');
insert into web_regions (region_id,city_id,region) values (713034,7130,'竹崎乡');
insert into web_regions (region_id,city_id,region) values (713035,7130,'梅山乡');
insert into web_regions (region_id,city_id,region) values (713036,7130,'番路乡');
insert into web_regions (region_id,city_id,region) values (713037,7130,'大埔乡');
insert into web_regions (region_id,city_id,region) values (713038,7130,'阿里山乡');
insert into web_regions (region_id,city_id,region) values (713301,7133,'屏东市');
insert into web_regions (region_id,city_id,region) values (713321,7133,'潮州镇');
insert into web_regions (region_id,city_id,region) values (713322,7133,'东港镇');
insert into web_regions (region_id,city_id,region) values (713323,7133,'恒春镇');
insert into web_regions (region_id,city_id,region) values (713324,7133,'万丹乡');
insert into web_regions (region_id,city_id,region) values (713325,7133,'长治乡');
insert into web_regions (region_id,city_id,region) values (713326,7133,'麟洛乡');
insert into web_regions (region_id,city_id,region) values (713327,7133,'九如乡');
insert into web_regions (region_id,city_id,region) values (713328,7133,'里港乡');
insert into web_regions (region_id,city_id,region) values (713329,7133,'盐埔乡');
insert into web_regions (region_id,city_id,region) values (713330,7133,'高树乡');
insert into web_regions (region_id,city_id,region) values (713331,7133,'万峦乡');
insert into web_regions (region_id,city_id,region) values (713332,7133,'内埔乡');
insert into web_regions (region_id,city_id,region) values (713333,7133,'竹田乡');
insert into web_regions (region_id,city_id,region) values (713334,7133,'新埤乡');
insert into web_regions (region_id,city_id,region) values (713335,7133,'枋寮乡');
insert into web_regions (region_id,city_id,region) values (713336,7133,'新园乡');
insert into web_regions (region_id,city_id,region) values (713337,7133,'崁顶乡');
insert into web_regions (region_id,city_id,region) values (713338,7133,'林边乡');
insert into web_regions (region_id,city_id,region) values (713339,7133,'南州乡');
insert into web_regions (region_id,city_id,region) values (713340,7133,'佳冬乡');
insert into web_regions (region_id,city_id,region) values (713341,7133,'琉球乡');
insert into web_regions (region_id,city_id,region) values (713342,7133,'车城乡');
insert into web_regions (region_id,city_id,region) values (713343,7133,'满州乡');
insert into web_regions (region_id,city_id,region) values (713344,7133,'枋山乡');
insert into web_regions (region_id,city_id,region) values (713345,7133,'三地门乡');
insert into web_regions (region_id,city_id,region) values (713346,7133,'雾台乡');
insert into web_regions (region_id,city_id,region) values (713347,7133,'玛家乡');
insert into web_regions (region_id,city_id,region) values (713348,7133,'泰武乡');
insert into web_regions (region_id,city_id,region) values (713349,7133,'来义乡');
insert into web_regions (region_id,city_id,region) values (713350,7133,'春日乡');
insert into web_regions (region_id,city_id,region) values (713351,7133,'狮子乡');
insert into web_regions (region_id,city_id,region) values (713352,7133,'牡丹乡');
insert into web_regions (region_id,city_id,region) values (713401,7134,'台东市');
insert into web_regions (region_id,city_id,region) values (713421,7134,'成功镇');
insert into web_regions (region_id,city_id,region) values (713422,7134,'关山镇');
insert into web_regions (region_id,city_id,region) values (713423,7134,'卑南乡');
insert into web_regions (region_id,city_id,region) values (713424,7134,'鹿野乡');
insert into web_regions (region_id,city_id,region) values (713425,7134,'池上乡');
insert into web_regions (region_id,city_id,region) values (713426,7134,'东河乡');
insert into web_regions (region_id,city_id,region) values (713427,7134,'长滨乡');
insert into web_regions (region_id,city_id,region) values (713428,7134,'太麻里乡');
insert into web_regions (region_id,city_id,region) values (713429,7134,'大武乡');
insert into web_regions (region_id,city_id,region) values (713430,7134,'绿岛乡');
insert into web_regions (region_id,city_id,region) values (713431,7134,'海端乡');
insert into web_regions (region_id,city_id,region) values (713432,7134,'延平乡');
insert into web_regions (region_id,city_id,region) values (713433,7134,'金峰乡');
insert into web_regions (region_id,city_id,region) values (713434,7134,'达仁乡');
insert into web_regions (region_id,city_id,region) values (713435,7134,'兰屿乡');
insert into web_regions (region_id,city_id,region) values (713501,7135,'花莲市');
insert into web_regions (region_id,city_id,region) values (713521,7135,'凤林镇');
insert into web_regions (region_id,city_id,region) values (713522,7135,'玉里镇');
insert into web_regions (region_id,city_id,region) values (713523,7135,'新城乡');
insert into web_regions (region_id,city_id,region) values (713524,7135,'吉安乡');
insert into web_regions (region_id,city_id,region) values (713525,7135,'寿丰乡');
insert into web_regions (region_id,city_id,region) values (713526,7135,'光复乡');
insert into web_regions (region_id,city_id,region) values (713527,7135,'丰滨乡');
insert into web_regions (region_id,city_id,region) values (713528,7135,'瑞穗乡');
insert into web_regions (region_id,city_id,region) values (713529,7135,'富里乡');
insert into web_regions (region_id,city_id,region) values (713530,7135,'秀林乡');
insert into web_regions (region_id,city_id,region) values (713531,7135,'万荣乡');
insert into web_regions (region_id,city_id,region) values (713532,7135,'卓溪乡');
insert into web_regions (region_id,city_id,region) values (713601,7136,'马公市');
insert into web_regions (region_id,city_id,region) values (713621,7136,'湖西乡');
insert into web_regions (region_id,city_id,region) values (713622,7136,'白沙乡');
insert into web_regions (region_id,city_id,region) values (713623,7136,'西屿乡');
insert into web_regions (region_id,city_id,region) values (713624,7136,'望安乡');
insert into web_regions (region_id,city_id,region) values (713625,7136,'七美乡');
insert into web_regions (region_id,city_id,region) values (713701,7137,'金城镇');
insert into web_regions (region_id,city_id,region) values (713702,7137,'金湖镇');
insert into web_regions (region_id,city_id,region) values (713703,7137,'金沙镇');
insert into web_regions (region_id,city_id,region) values (713704,7137,'金宁乡');
insert into web_regions (region_id,city_id,region) values (713705,7137,'烈屿乡');
insert into web_regions (region_id,city_id,region) values (713706,7137,'乌丘乡');
insert into web_regions (region_id,city_id,region) values (713801,7138,'南竿乡');
insert into web_regions (region_id,city_id,region) values (713802,7138,'北竿乡');
insert into web_regions (region_id,city_id,region) values (713803,7138,'莒光乡');
insert into web_regions (region_id,city_id,region) values (713804,7138,'东引乡');
insert into web_regions (region_id,city_id,region) values (810101,8101,'中西区');
insert into web_regions (region_id,city_id,region) values (810102,8101,'湾仔区');
insert into web_regions (region_id,city_id,region) values (810103,8101,'东区');
insert into web_regions (region_id,city_id,region) values (810104,8101,'南区');
insert into web_regions (region_id,city_id,region) values (810201,8102,'油尖旺区');
insert into web_regions (region_id,city_id,region) values (810202,8102,'深水埗区');
insert into web_regions (region_id,city_id,region) values (810203,8102,'九龙城区');
insert into web_regions (region_id,city_id,region) values (810204,8102,'黄大仙区');
insert into web_regions (region_id,city_id,region) values (810205,8102,'观塘区');
insert into web_regions (region_id,city_id,region) values (810301,8103,'荃湾区');
insert into web_regions (region_id,city_id,region) values (810302,8103,'屯门区');
insert into web_regions (region_id,city_id,region) values (810303,8103,'元朗区');
insert into web_regions (region_id,city_id,region) values (810304,8103,'北区');
insert into web_regions (region_id,city_id,region) values (810305,8103,'大埔区');
insert into web_regions (region_id,city_id,region) values (810306,8103,'西贡区');
insert into web_regions (region_id,city_id,region) values (810307,8103,'沙田区');
insert into web_regions (region_id,city_id,region) values (810308,8103,'葵青区');
insert into web_regions (region_id,city_id,region) values (810309,8103,'离岛区');
insert into web_regions (region_id,city_id,region) values (820101,8201,'花地玛堂区');
insert into web_regions (region_id,city_id,region) values (820102,8201,'圣安多尼堂区');
insert into web_regions (region_id,city_id,region) values (820103,8201,'大堂区');
insert into web_regions (region_id,city_id,region) values (820104,8201,'望德堂区');
insert into web_regions (region_id,city_id,region) values (820105,8201,'风顺堂区');
insert into web_regions (region_id,city_id,region) values (820201,8202,'嘉模堂区');
insert into web_regions (region_id,city_id,region) values (820301,8203,'圣方济各堂区');


update web_cities set city='新疆维吾尔直辖县级' where city_id=6590;
  update web_cities set city='河南直辖县级' where city_id=4190;
  update web_cities set city='海南直辖县级' where city_id=4690;
  update web_cities set city='湖北直辖县级' where city_id=4290;