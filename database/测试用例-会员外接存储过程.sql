Use modeldb
Set NoCount On
/*
测试用例
*/
----Test Description
--Set @TestName = 'test0'
----ToDo:
----  TESTCODE
--Print '[' + @TestName + ']AddVip返回值:' + Cast(@Reurn As varchar(20))
--If @Reurn <> -1
--Begin
--  Print '执行结束.'
--  Return
--End



Declare @VipName     varchar(20) Set @VipName     = 'test'
Declare @WeiXinCode  varchar(50) Set @WeiXinCode  = 'tes_weixincode'
Declare @WeiXinCode2 varchar(50) Set @WeiXinCode2 = 'tes_weixincode_2'
Declare @CallNumber  varchar(20) Set @CallNumber  = '12345678901'
Declare @Reurn int
Declare @TestName    varchar(99) Set @TestName    = ''
--------------------------------------------------------------------------------
--添加新会员
Set @TestName = 'test1'
Delete From 会员身份 Where 外键 = @WeiXinCode
Exec @Reurn=AddVip @WeiXinCode, @CallNumber, @VipName
Print '[' + @TestName + ']AddVip返回值:' + Cast(@Reurn As varchar(20))
If @Reurn <> 0 
Begin
  Print '执行结束.'
  Return
End
--------------------------------------------------------------------------------
--重复添加新会员
Set @TestName = 'test2'
Exec @Reurn=AddVip @WeiXinCode, @CallNumber, @VipName
Print '[' + @TestName + ']AddVip返回值:' + Cast(@Reurn As varchar(20))
If @Reurn <> -1
Begin
  Print '执行结束.'
  Return
End
--------------------------------------------------------------------------------
--绑定到已有卡
Set @TestName = 'test3'
Exec @Reurn=AddVip @WeiXinCode2, @CallNumber, @VipName
Print '[' + @TestName + ']AddVip返回值:' + Cast(@Reurn As varchar(20))
If @Reurn <> 0
Begin
  Print '执行结束.'
  Return
End
--------------------------------------------------------------------------------
--取会员信息
Set @TestName = 'test4'
Exec @Reurn=GetVipInfo @WeiXinCode2
Print '[' + @TestName + ']GetVipInfo返回值:' + Cast(@Reurn As varchar(20))


--------------------------------------------------------------------------------
Select * From EventLog
Delete From EventLog
Delete 会员身份 Where 外键 in(@WeiXinCode, @WeiXinCode2)
