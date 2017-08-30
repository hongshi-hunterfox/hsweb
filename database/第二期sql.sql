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