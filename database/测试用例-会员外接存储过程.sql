Use modeldb
Set NoCount On
/*
��������
*/
----Test Description
--Set @TestName = 'test0'
----ToDo:
----  TESTCODE
--Print '[' + @TestName + ']AddVip����ֵ:' + Cast(@Reurn As varchar(20))
--If @Reurn <> -1
--Begin
--  Print 'ִ�н���.'
--  Return
--End



Declare @VipName     varchar(20) Set @VipName     = 'test'
Declare @WeiXinCode  varchar(50) Set @WeiXinCode  = 'tes_weixincode'
Declare @WeiXinCode2 varchar(50) Set @WeiXinCode2 = 'tes_weixincode_2'
Declare @CallNumber  varchar(20) Set @CallNumber  = '12345678901'
Declare @Reurn int
Declare @TestName    varchar(99) Set @TestName    = ''
--------------------------------------------------------------------------------
--����»�Ա
Set @TestName = 'test1'
Delete From ��Ա��� Where ��� = @WeiXinCode
Exec @Reurn=AddVip @WeiXinCode, @CallNumber, @VipName
Print '[' + @TestName + ']AddVip����ֵ:' + Cast(@Reurn As varchar(20))
If @Reurn <> 0 
Begin
  Print 'ִ�н���.'
  Return
End
--------------------------------------------------------------------------------
--�ظ�����»�Ա
Set @TestName = 'test2'
Exec @Reurn=AddVip @WeiXinCode, @CallNumber, @VipName
Print '[' + @TestName + ']AddVip����ֵ:' + Cast(@Reurn As varchar(20))
If @Reurn <> -1
Begin
  Print 'ִ�н���.'
  Return
End
--------------------------------------------------------------------------------
--�󶨵����п�
Set @TestName = 'test3'
Exec @Reurn=AddVip @WeiXinCode2, @CallNumber, @VipName
Print '[' + @TestName + ']AddVip����ֵ:' + Cast(@Reurn As varchar(20))
If @Reurn <> 0
Begin
  Print 'ִ�н���.'
  Return
End
--------------------------------------------------------------------------------
--ȡ��Ա��Ϣ
Set @TestName = 'test4'
Exec @Reurn=GetVipInfo @WeiXinCode2
Print '[' + @TestName + ']GetVipInfo����ֵ:' + Cast(@Reurn As varchar(20))


--------------------------------------------------------------------------------
Select * From EventLog
Delete From EventLog
Delete ��Ա��� Where ��� in(@WeiXinCode, @WeiXinCode2)
