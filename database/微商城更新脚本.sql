--20180315 update script for fxc
If Not Exists(Select * From syscolumns where name='is_show' And id=Object_id('web_products','U'))
Begin
  alter table web_products add is_show bit not null default 1;
End

If Not Exists(Select * From syscolumns where name='Explain' And id=Object_id('web_products','U'))
Begin
  alter table web_products add Explain varchar(255);
End

If Not Exists(Select * From 会员where name='性别' And id=Object_id('会员','U'))
Begin
  alter table 会员 add 性别varchar(20);
End

If Not Exists(Select * From syscolumns where name='parameter' And id=Object_id('web_products','U'))
Begin
  alter table web_products add parameter varchar(50);
End

If Not Exists(Select * From web_config where tag='restrictedDistance')
Begin
  insert into web_config (tag,value) values ('restrictedDistance','50'); --配送范围
End

If Not Exists(Select * From web_config where tag='commentText')
Begin
  insert into web_config(tag,value) values('commentText','评论有惊喜哦');--评论赠送提示内容
End

If Not Exists(Select * From web_config where tag='startUp')
Begin
  insert into web_config (tag,value) values('startUp',0);--是否开启会员绑定
End

If Not Exists(Select * From web_config where tag='ACardMoreMembers')
Begin
  insert into web_config (tag,value) values('ACardMoreMembers',1);--是否开启多个微信可绑定一张卡(默认开启)
End

If Not Exists(Select * From web_config where tag='full')
Begin
  insert into web_config (tag,value) values('full',0);
End
If Not Exists(Select * From web_config where tag='unbundling')
Begin
  insert into web_config (tag,value) values('unbundling',0);
End
 


If Object_id('web_user_list','U') Is Null
Begin
	CREATE TABLE web_user_list(
	id int identity(1,1) primary key,
	vip_id int,
	oauth_id varchar(255),
	create_time datetime,
	phone varchar(25)
	)
End

If Object_id('web_product_parameters','U') Is Null
Begin
	CREATE TABLE web_product_parameters(
	id int IDENTITY(1,1) primary key NOT NULL,
	product_id int NULL,
	sname varchar(50) NULL,
	)
End

--必需要插入用户密码
If Object_id('web_account_information','U') Is Null
Begin
CREATE TABLE [dbo].[web_account_information](
	[account] [varchar](25) NULL,
	[password] [varchar](30) NULL
)
End


If Object_id('web_viplog','U') Is Null
Begin
CREATE TABLE [dbo].[web_viplog](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[vcode] [varchar](255) NULL,
	[foreign_key] [varchar](255) NULL,
	[recording_time] [datetime] NULL,
	[timestamp] [timestamp] NULL
)

ALTER TABLE [dbo].[web_viplog] ADD  CONSTRAINT [DF_web_viplog_recording_time]  DEFAULT (getdate()) FOR [recording_time]
End
If Object_id('web_evaluation_config','U') Is Null
Begin
	CREATE TABLE web_evaluation_config(
		[id] int IDENTITY(1,1) NOT NULL,
		[point] int NOT NULL,
		[money] decimal(20, 2) NULL,
		[voucher_code] varchar(255) NOT NULL,
		[amount] int NOT NULL,
		[time] datetime NULL)
End


If Object_id('web_payment_callback_data','U') Is Null
Begin
CREATE TABLE web_payment_callback_data (
  id int identity (1,1) primary key,
  data varchar(4096) NOT NULL DEFAULT '',
  payment_serial_num varchar(255) NOT NULL,
  create_time DATETIME DEFAULT(GETDATE())
) ;
End


If Not Exists(Select * From syscolumns where name='promotion_price' And id=Object_id('web_specification_values','U'))
Begin
  alter table web_specification_values add promotion_price decimal(20, 2);
  update web_specification_values set promotion_price=0;
End

If Not Exists(Select * From syscolumns where name='start_time' And id=Object_id('web_specification_values','U'))
Begin
 alter table web_specification_values add start_time datetime;
 update web_specification_values set start_time='1997-01-16 00:00:00';
End

If Not Exists(Select * From syscolumns where name='end_time' And id=Object_id('web_specification_values','U'))
Begin
 alter table web_specification_values add end_time datetime;
 update web_specification_values set end_time='1997-01-16 00:00:00';
End


If Not Exists(Select * From syscolumns where name='check_count' And id=Object_id('web_payment_orders','U'))
Begin
 alter table web_payment_orders add check_count int not null default 0;
End


If Not Exists(Select * From syscolumns where name='version' And id=Object_id('web_payment_orders','U'))
Begin
 alter table web_payment_orders add version int not null default 0;
End

If Object_id('web_orders_setting_picktime','U') Is Null
BEGIN
create table web_orders_setting_picktime(
id int identity (1,1) primary key,
pickStartTime datetime,
pickEndTime datetime
)
END

If Object_id('web_integralin_config','U') Is Null
BEGIN
CREATE TABLE [dbo].[web_integralin_config](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[day] [int] NOT NULL,
	[money] [decimal](20, 2) NULL,
	[voucher_code] [varchar](255) NULL,
	[amount] [int] NULL
)
END


If Object_id('web_vip_voucher','U') Is Null
BEGIN
CREATE TABLE [dbo].[web_vip_voucher](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[voucher] [varchar](255) NOT NULL,
	[amount] [int] NOT NULL,
)

END

If Not Exists(Select * From syscolumns where name='accumulation' And id=Object_id('web_sign_record','U'))
Begin
 alter table web_sign_record add accumulation int;
End

If Not Exists(Select * From syscolumns where name='sort_value' And id=Object_id('web_products','U'))
	Begin
		alter table web_products add  sort_value  int not null ;
	End
go

declare @def varchar(100),@SQL Nvarchar(100)
if exists(select [name]
	from sysobjects t
	where id = (select cdefault from syscolumns where id = object_id(N'web_binding_rewards')
	and name='amount')  ) 
begin
	select @def=[name] 	from sysobjects t
	where id = (select cdefault from syscolumns where id = object_id(N'web_binding_rewards') and name='amount') 
	set @SQL ='alter table web_binding_rewards drop constraint '+@def
	exec  sp_executesql  @SQL
	alter table web_binding_rewards add default(1) for amount
end
else
begin
  alter table web_binding_rewards add default(1) for amount
end



go
If Object_id('web_refund_orders','U') Is Null
	BEGIN
		CREATE TABLE web_refund_orders(
			refund_order_id int IDENTITY(1,1) NOT NULL,
			user_id int NOT NULL,
			payment_id int NOT NULL,
			payment_order_id int NOT NULL,
			payment_serial_num varchar(255) NOT NULL,
			refund_serial_num varchar(255) NOT NULL,
			transaction_id varchar(255) NOT NULL DEFAULT ('') ,
			transaction_type smallint NOT NULL,
			totalFree decimal(20, 2) NOT NULL DEFAULT ('0.00') ,
			refundFree decimal(20, 2) NOT NULL DEFAULT ('0.00') ,
			create_time datetime NULL DEFAULT (getdate()),
			complete_time datetime NULL  DEFAULT (null),
			is_completed BIT not NULL DEFAULT (0),
			refundDesc varchar(500),
			flag int not null DEFAULT 0
		)
	END


If Not Exists(Select * From web_config where tag='zhengshu')
	Begin
		insert into web_config (tag,value) values ('zhengshu','D:\zhengshu\hs\apiclient_cert.p12'); --证书路径
	End



If Not Exists(Select * From web_config where tag='zhengshuPassword')
	Begin
		insert into web_config (tag,value) values ('zhengshuPassword','to119,0002'); --证书的密码(一般是web_config表中的merchantcode值,如果不是的话也需要写入到数据库中)
	End

if not EXISTS (select * from sysObjects where  id=object_id (N'[dbo].[WSC_insertOrderTrace]'))
	BEGIN
						CREATE PROCEDURE  [dbo].[WSC_insertOrderTrace]
							@paymentSerialNum varchar(50),
							@openId varchar(100),
							@flag int

						AS
						BEGIN
							SET NOCOUNT ON;
							Declare @orderSeialNum varchar(30),@orderId int,@num varchar(10)

							select @orderSeialNum=A.order_serial_num from web_orders A left join web_payment_orders B
							on A.payment_order_id=B.payment_order_id where B.payment_serial_num=@paymentSerialNum


							select @orderId=ID FROM orders where 商户订单号=@orderSeialNum
							select @num=单号 from orders where 商户订单号=@orderSeialNum

							if @flag=1
							begin
								insert into orders_trace(订单id,订单单号,状态,建立日期,操作人)
								values(@orderId,@num,'申请退款中',getDate(),@openId)
							end
							else if @flag=3
							begin
								insert into orders_trace(订单id,订单单号,状态,建立日期,操作人)
								values(@orderId,@num,'退款成功',getDate(),@openId)
							end
						END
	END


go








