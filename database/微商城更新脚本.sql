--20180315 update script for fxc
If Not Exists(Select * From syscolumns where name='is_show' And id=Object_id('web_products','U'))
Begin
  alter table web_products add is_show bit not null default 1;
End

If Not Exists(Select * From syscolumns where name='Explain' And id=Object_id('web_products','U'))
Begin
  alter table web_products add Explain varchar(255);
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


If Not Exists(Select * From syscolumns where name='check_count' And id=Object_id('web_payment_orders','U'))
Begin
 alter table web_payment_orders add check_count int not null default 0;
End


If Not Exists(Select * From syscolumns where name='version' And id=Object_id('web_payment_orders','U'))
Begin
 alter table web_payment_orders add version int not null default 0;
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






