﻿use modeldb
go
--select 'drop table '+name from sysobjects where xtype='u' and name like 'web%'
drop table web_balance
drop table web_balance_log
drop table web_banner
drop table web_cart
drop table web_categories
drop table web_cities
--drop table web_config
drop table web_datasource
drop table web_deliver_addrs
drop table web_freight
drop table web_lottery_draw_config
drop table web_lottery_record
drop table web_msg_record
drop table web_napa_stores
drop table web_oauth_login
drop table web_order_items
drop table web_orders
drop table web_payment_orders
drop table web_payments
drop table web_permissions
drop table web_product_group_links
drop table web_product_groups
drop table web_product_images_link
drop table web_product_sale
drop table web_products
drop table web_products_categories_link
drop table web_products_specifications_values_link
drop table web_province
drop table web_recharge_config
drop table web_regions
drop table web_role_permission_link
drop table web_roles
drop table web_shake_record
drop table web_sign_record
drop table web_specification_value_store_link
drop table web_specification_values
drop table web_specifications
drop table web_store_info
drop table web_user_invited_link
drop table web_user_profiles
drop table web_user_role_link
drop table web_users
--drop table web_vars
drop table web_winning_record