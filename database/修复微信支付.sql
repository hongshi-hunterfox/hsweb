CREATE TABLE web_payment_callback_data (
  id int identity (1,1) primary key,
  data varchar(4096) NOT NULL DEFAULT '',
  payment_serial_num varchar(255) NOT NULL,
  create_time DATETIME DEFAULT(GETDATE())
) ;

alter table web_payment_orders add check_count int not null default 0;
alter table web_payment_orders add version int not null default 0;