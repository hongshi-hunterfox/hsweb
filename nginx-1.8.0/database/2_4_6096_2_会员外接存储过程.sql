--use Modeldb4
/*
与微商城对接的更新
函数：
  GetBirthday: 函数 转换当年生日为指定日期后的生日日期  
  fn_GetPickUpCode:根据网上商城订单号计算相应的提货码
  fn_UnPickUpCode:根据提货码计算相应的网上商城订单号
公开过程:
  GetVipInfo:取会员信息
  AddVip:添加会员(有则不添加),并与指定会员绑定
  VipReCharge:会员充值
  GetVipLog:获取指定会员(外键)的消费/充值记录
  GetVipLog_ID:获取指定会员(ID)的消费/充值记录
  NewVipCode:生成一个新的会员编号
  NewVip:生成会员记录
  GetVouchers:获取礼券信息(指定部门可发放的/指定编号的)
  Saleing:生成销售记录(单个商品销售)
  SaleingVoucher:销售礼券
  CreateOrder:生成一张订单(只是主记录)
  AddOrderItem:添加一条订单明细
  GetOrdersList:获取订单列表
  GetOrdersDetail:获取指定订单的明细
  RecoverVoucher:礼券回收
  VipBonusPoints:对会员积分进行变动(通过充值0实现)
  QueryFormMObject:执行mobile_object中的查询
  Get_BossTotal:老板小助手 统计销售数据非会员消费，今日新增会员，昨日新增会员，今日充值，昨日充值
  GetBirthday:获取指定日期后XX天内过生日的会员信息
  Loger:写日志
  AddWebPay:在线下系统中生成线上支付对应的结算记录
  PushWeiXinMessage:生成一条消费推送信息
  ChangeVipLinks:将一个会员的绑定信息修改为指向另一个会员
   
为优势力提供的调用封装:
  WSC_GetVipInfo:取会员信息
  WSC_AddVip:添加会员
  WSC_VipReCharge:会员充值
  WSC_GetVipLog:获取指定会员外键的消费/充值记录,含积分信息
  WSC_GetVouchers:得到微商城中可以发放的电子券列表/指定编号的礼券
  WSC_SaleVoucher:电子券销售
  WSC_CreateOrder:生成订单主记录,得到单号
  WSC_AddOrderItem:添加订单明细
  WSC_GetOrdersList:获取订单列表
  WSC_GetOrdersDetail:获取指定订单明细
  WSC_RecoverVoucher:订单中回收礼券
  WSC_VipBonusPoints:积分变动
  WSC_GetVipRelevance:确认两个会员是否可以建立分销关系
  Wsc_Get_BossTotal:老板小助手 统计销售数据
  WSC_GetPickUpCode:从web_orders.order_serial_num计算相应的提货码

<修改日志已经移到文件尾部>
2016-12-06 hunter__fox
*/
/******************************************************************************/
/*
订单明细扩展表
*/
If Object_id('orders_detail_extend','U') Is Null
Begin
  Create Table [orders_detail_extend](
               [link_id] int not null,
               [web_price] money null)
End
Go
/******************************************************************************/
/*
公众号对接需要添加相应的部门,操作员,结算方式
部门
为公众号/微商城建立相应的部门与操作员,以区分数据来源
生成的部门编号是从1开始最小的未使用号,最大不超过255
生成的操作员编号为'部门01'
*/
Declare @cDepartCode varchar(10)
Select @cDepartCode=编号 From department Where 简称='公众号'
If @cDepartCode Is Null
Begin
  Select @cDepartCode=Min(number) 
         From master..spt_values 
         Where type='P' And number>0 
           And number Not In(Select 编号 
                             From department)
  Insert Into department(编号,简称,全称,类别编号)
         Values(@cDepartCode,'公众号','公众号','102')
End
--操作员
Declare @cUserCode varchar(10)
Select @cUserCode=编号 From pub_user Where 姓名='公众号'
If @cUserCode Is Null
Begin
  Set @cUserCode = @cDepartCode + '01'
  Insert Into pub_user(编号,姓名,部门编号,密码,权限,Limit)
         Values(@cUserCode,
                '公众号',
                @cDepartCode,
                Replicate(Reverse(@cDepartCode),2),
                0,
                Replicate('A',32))
End
/******************************************************************************/
--结算方式
If Not Exists(Select * From 结算方式表 Where 名称='公众号')
  Insert Into 结算方式表(名称,结算类型,结算折扣率,第三方支付,是否启用)
         Values('公众号','现金',1,1,0)
If Not Exists(Select * From 结算方式表 Where 名称='WSC_微信支付')
  Insert Into 结算方式表(名称,结算类型,结算折扣率,第三方支付,是否启用)
         Values('WSC_微信支付','现金',1,1,0)
If Not Exists(Select * From 结算方式表 Where 名称='WSC_支付宝支付')
  Insert Into 结算方式表(名称,结算类型,结算折扣率,第三方支付,是否启用)
         Values('WSC_支付宝支付','现金',1,1,0)
Go
/******************************************************************************/
/*
礼券表添加字段[产品编号]
  变长10,允许空
为代金券匹配相应的产品编号
*/
If Not Exists(Select * From syscolumns where name='产品编号' And id=Object_id('礼券表','U'))
Begin
  Alter Table 礼券表 Add [产品编号] varchar(10) Null
End
Go
Update 礼券表 
   Set 产品编号=(Select Top 1 编号 
                 From goods 
                 Where 存货属性='现金券' 
                 Order By Abs(销售主价-面额)) 
 Where 礼券类别='代金券'
   And 产品编号 Is Null 
Go
/******************************************************************************/
--商品:运费,编号'SYS_001'
--商品类别:加收费,编号:8(代金券类)下未用的最小序号
Declare @SortCode varchar(10)
If Not Exists(Select * From goods Where 编号='SYS_001')
Begin
  --取得加收费对应的类别编号
  If Not Exists(Select * From goods_sort Where 名称='加收费')
  Begin
    Select @SortCode = '0' + Convert(varchar(2), Min(number))
    From master..spt_values 
    Where type='P' And number>0 
      And number Not In(Select SubString(编号, 2, 10) 
                        From goods_sort 
                        Where 编号 like '8%')
    Set @SortCode = '8' + SubString(@SortCode, Len(@SortCode) - 2, 10)
    Insert Into goods_sort(编号,名称)Values(@SortCode, '加收费')
  End
  Else
    Select @SortCode=编号 From goods_sort Where 名称='加收费'
  --添加商品"运费",类别为"加收费",单价为1
  Insert Into goods(编号,名称,规格,单位,换算单位,换算率
                   ,类别编号,是否销售,销售主价,成本价
                   ,产地编号,叫货控制,停用日期,是否库存)
         Values('SYS_001', '运费', '', '元', '元',1
               ,@SortCode, 1 ,1, 1
               ,'10101',0,convert(varchar(10),getdate()+3650,120),0)
End
Else
  Update goods
  Set 名称     = '运费'
    , 规格     = ''
    , 单位     = ''
    , 换算单位 = ''
    , 换算率   = 1
    , 是否销售 = 1
    , 销售主价 = 1
    , 成本价   = 1
    , 产地编号 = '10101'
    , 叫货控制 = 0
    , 停用日期 = convert(varchar(10),getdate()+3650,120)
    , 是否库存 = 0
  Where 编号='SYS_001'
Go
/******************************************************************************/
--确保[会员身份]表有效可用
If Object_id('会员身份','U') Is Null
Begin
  Create Table [会员身份](
     会员ID int not null
    ,外键 varchar(50) not null
    )
  Create Index IX_会员身份_会员ID On 会员身份(会员ID)
End
Go
If col_length('会员身份','验证方式') Is Null
Begin
  Alter Table [会员身份] Add 验证方式 varchar(20) not null default 'WeiXinOpenid'
  Create Index IX_会员身份_验证方式 On 会员身份(验证方式)
End
Go
If col_length('会员身份','建立时间') Is Null
Begin
  Alter Table [会员身份] Add 建立时间 datetime not null default getdate()
End
Go
If Not Exists(Select * From sysobjects 
              Where parent_obj=Object_ID('会员身份','U') And xtype='UQ' And name='UQ_会员身份_外键')
Begin
  If Exists(Select * From sysindexes Where name='IX_会员身份_外键')
    Drop Index [会员身份].[IX_会员身份_外键]
  Alter Table [会员身份] Add Constraint [UQ_会员身份_外键] Unique (外键)
End
Go
/*******************************************************************************
Procedure GetVipLog_ID
功能:获取指定会员的消费/充值记录
参数:
  @iVipID:会员表ID
  @cDateStart:可选,查询开始日期(含此日)
  @cDateStart:可选,查询截止日期(含此日)
*/
If Object_id('[GetVipLog_ID]','P') Is Not Null
  Drop Procedure [GetVipLog_ID]
Go
Create Procedure [GetVipLog_ID]
       @iVipID     int                     ,--会员表ID
       @cDateStart varchar(10)='1900-01-01',--查询开始日期(含此日)
       @cDateEnd   varchar(10)=''           --查询截止日期(含此日)
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'GetVipLog_ID'
  Set @ParaInfo = dbo.NameValue('N','@iVipID',@iVipID)         Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@cDateStart',@cDateStart) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@cDateEnd',@cDateEnd)     Exec Loger @LogID,@Parameters=@ParaInfo
  Declare @dStart datetime
  Declare @dEnd datetime
  If @cDateStart='' Or @cDateStart Is Null Set @cDateStart='1900-01-01'
  If @cDateEnd  ='' Or @cDateEnd   Is Null Set @cDateEnd=Convert(varchar(10),getdate(),120)
  Set @dStart = Convert(datetime,@cDateStart)
  Set @dEnd = Convert(datetime,@cDateEnd) + 1

  Declare @会员积分比例 money
  Select @会员积分比例=积分比例 From 会员 Where ID=@iVipID
  Set @会员积分比例 = IsNull(@会员积分比例, 1)
  --取会员核心信息
  Declare @cVipCode varchar(20),@Tmp_Balance money,@Tmp_Integral int
  Select @cVipCode=编号,@Tmp_Balance=卡余额,@Tmp_Integral=积分 From 会员 Where id =@iVipID
  --游标:指定会员指定时间之后所有交易记录
  Declare @t_opt table(来源 varchar(10), id int      , 单号 varchar(20), 建立日期 datetime, 充值 money
                      ,刷卡金额 money  , 积分变化 int, 当前余额 money  , 计算余额 money   , 当前积分 int)
  Declare @来源 varchar(10), @id int      , @单号 varchar(20), @建立日期 datetime, @充值 money
         ,@刷卡金额 money  , @积分变化 int, @当前余额 money  , @余额 money       , @当前积分 int
  Declare cur_Tmp Cursor
      For --零售记录
        Select '零售'as 来源,id,单号,建立日期,Null As 充值,刷卡金额,金额合计*@会员积分比例 as 积分变化,当前余额,0 As 余额,当前积分 
        From sales 
        Where 建立日期 >= @dStart And 往来编号=@cVipCode And 刷卡金额<>0
        Union All
        --订单记录
        Select Case 商户订单号 When '' Then '线下订单' Else '线上订单' End
              ,id,单号,建立日期,0,刷卡金额,应收金额*@会员积分比例,当前余额,0 ,当前积分
        From orders
        Where 建立日期 >= @dStart And 是否废止=0 And 往来编号=@cVipCode
        Union All 
        --充值记录
        Select '充值',b.id,b.单号,b.建立日期,a.金额,0,0,Null,0 ,Null
        From 充值明细表 a 
        Left Join 充值表 b On a.pid=b.id 
        Where b.建立日期 >= @dStart And a.编号=@cVipCode
          And a.金额<>0 And IsNull(a.备注,'')=''
        Union All
        --积分充值
        Select '积分充值',b.id,b.单号,b.建立日期,a.金额,0,0-a.备注,Null,0 ,Null
        From 充值明细表 a 
        Left Join 充值表 b On a.pid=b.id 
        Where b.建立日期 >= @dStart And a.编号=@cVipCode
          And a.金额<>0 And a.现金=0
          And IsNull(a.备注,'')<>''
          And a.备注 Like '[0-9][0-9][0-9]%'
          And Len(RTrim(a.备注))<6
        Union All
        --统一结算中会员积分支付(零售与订单)
        Select Case 来源 When 0 Then'零售' Else'订单' End,业务ID
              ,Case 来源 When 0 Then c.单号 Else d.单号 End
              ,建立时间,Null,0,-结算金额 * B.结算折扣率,Null,0,Null
        From 结算明细表 As A
        Inner Join 结算方式表 As B On A.结算ID=B.ID
        Left Join sales As c On A.业务ID=c.id
        Left Join orders As d On A.业务ID=d.id
        Where B.是否启用=1 And B.名称='会员积分' And A.来源 In (0,1,2) 
          And A.备注=@cVipCode And A.建立时间>= @dStart
        Union All
        --旧的零售中积分兑换记录
        Select '零售',A.id,A.单号,A.建立日期,Null,0,0-IsNull(SubString(B.备注,5,10),0),当前余额,0 As 余额,当前积分
        From sales As A
        Inner Join sales_detail As b On A.ID=B.销售ID
        Where A.建立日期 >= @dStart And A.往来编号=@cVipCode And B.备注 Like '减积分:%'
        Union All
        --积分变动
        Select b.备注,b.id,b.单号,b.建立日期,0,0,积分,Null,0 ,Null
        From 充值明细表 a 
        Left Join 充值表 b On a.pid=b.id 
        Where b.建立日期 >= @dStart And a.编号=@cVipCode
          And a.积分<>0 And 金额=0
        Order By 建立日期 Desc,来源
  --遍历:还原余额与积分在每笔交易发生后的即时值,生成输出集
  Declare @tmp varchar(1000)
  Open cur_Tmp
  Fetch Next From cur_Tmp Into @来源,@id,@单号,@建立日期,@充值,@刷卡金额,@积分变化,@当前余额,@余额,@当前积分
  While @@Fetch_status=0
  Begin
    If @当前积分 Is Null Set @当前积分 = @Tmp_Integral
                    --@t_opt ( 来源, id, 单号, 建立日期, 充值, 刷卡金额, 积分变化, 当前余额, 计算余额   , 当前积分)
    Insert Into @t_opt Values(@来源,@id,@单号,@建立日期,@充值,@刷卡金额,@积分变化,@当前余额,@Tmp_Balance,@Tmp_Integral)
    Set @tmp = '@来源='       +cast(@来源         as char(14))
             + '单号='        +cast(@单号         as char(20))
             + '@当前积分='   +cast(@当前积分     as char(20))
             + '@积分变化='   +cast(@积分变化     as char(20))
             +'@Tmp_Integral='+cast(@Tmp_Integral as char(20)) 
    print @tmp
    Set @Tmp_Balance = @Tmp_Balance + IsNull(@刷卡金额, 0) - IsNull(@充值, 0)
    Set @Tmp_Integral = @当前积分 - @积分变化
    Fetch Next From cur_Tmp Into @来源,@id,@单号,@建立日期,@充值,@刷卡金额,@积分变化,@当前余额,@余额,@当前积分
  End
  Close cur_Tmp
  Deallocate cur_Tmp
  --输出集整理
  Select 建立日期 As [DealTim],
         来源     As [Source],
         单号     As [BillCode],
         Case When 来源 In('充值','积分充值') Then 充值 Else 刷卡金额 End As [Amount],/*交易金额*/
         计算余额 As [Balance],    /*余额*/
         积分变化 As [BonusPoints],/*积分变化*/
         当前积分 As [Integral]    /*积分*/
  From @t_opt
  Where 建立日期 < @dEnd
  Order By 建立日期 Desc
End
Go
----测试
--exec GetVipLog_ID 82915

/*******************************************************************************
Procedure NewVipCode
功能:生成一个新的会员编号
     无论是否使用,生成的编号均能再次通过该过程得到
参数:
  @RetCode:用于返回生成的编号,需要一个varchar(20)类型
*/
If Object_id('[NewVipCode]','P') Is Not Null
  Drop Procedure [NewVipCode]
Go
Create Procedure [NewVipCode]
       @RetCode varchar(20) Output
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'NewVipCode'
  --生成会员编号,独立事务
  Declare @iBillMax int
  Begin Tran tran_NewVipCode
    Select @iBillMax=IsNull(最大单号, 0) + 1 From bills Where 表名 = '微商城会员' And 会计期间 = 'WX'
    If @iBillMax Is Null Set @iBillMax = 1
    If @iBillMax=1
      Insert Into bills(表名, 会计期间, 最大单号)Values('微商城会员', 'WX', @iBillMax)
    Else
      Update bills Set 最大单号 = @iBillMax Where 表名 = '微商城会员' And 会计期间 = 'WX'
  Commit Tran tran_NewVipCode
  If @@Error<>0 
  Begin
    Rollback Tran tran_NewVipCode
    Exec Loger @LogID,@Key='',@Result=0
    Return ''
  End
  Set @RetCode= 'WX' + Replace(Str(@iBillMax, 8), ' ', '0')
  Exec Loger @LogID,@Key=@RetCode,@Result=0
End
Go
----测试
--Declare @Newcode varchar(20)
--Exec NewVipCode @Newcode output
--Select @Newcode

/*******************************************************************************
Procedure NewVip()
功能:在[会员]表建立一条记录,并返回相应的ID
参数
  @iVipID:返回新建记录的ID值,如果失败返回-1
*/
If Object_id('[NewVip]','P') Is Not Null
  Drop Procedure [NewVip]
Go
Create Procedure [NewVip]
       @iVipID int Output
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Set @ParaInfo=dbo.NameValue('N','@iVipID',@iVipID)
  Exec @LogID=Loger null,'NewVip',@Parameters=@ParaInfo
  Declare @LastError int
  Declare @cVipCode varchar(20)
  Set @LastError=-1
  While @LastError<>0
  Begin
    Exec NewVipCode @cVipCode Output
    Insert Into 会员(编号,内码,名称,电话,生日,是否农历,
                     积分,状态,截止日期,是否充值,卡金额,卡余额,
                     积分比例,是否积分,会员生日,会员卡类型,会员等级类型)
             Values(@cVipCode,@cVipCode,'','','',0,
                    0,1,GetDate()+3650,0,0,0,
                    1,0,'',3,1)
    Select @LastError=@@Error,@iVipID=Scope_Identity()
  End
  Exec Loger @LogID,@Key=@iVipID,@Result=@LastError
End
Go
----测试
--Declare @NewID int
--Exec NewVip @NewID Output
--Select * From 会员 Where id=@NewID



/*******************************************************************************
Procedure GetVipInfo
功能:取会员信息
参数
  @cWeiXinCode:会员身份外键
  不指定则返回所有未绑定的会员列表
*/
If Object_id('[GetVipInfo]','P') Is Not Null
  Drop Procedure [GetVipInfo]
Go
Create Procedure [GetVipInfo]
       @cWeiXinCode varchar(50) = null --会员身份外键
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Set @ParaInfo = dbo.NameValue('C','@cWeiXinCode',@cWeiXinCode)
  Exec @LogID=Loger null,'GetVipInfo',@Parameters=@ParaInfo
  /*
  通过外键获取会员记录
  参数 @cWeiXinCode 为绑定到会员的外键
       不指定则返回所有没有绑定外键的会员
  */
  If @cWeiXinCode Is Null Or len(@cWeiXinCode)=0
    Select ID,编号,名称,生日,是否农历,电话,积分,折扣,最近时间,最近部门,状态,截止日期
          ,是否充值,卡金额,卡余额,建立时间,内码,身份证号,工作单位,审核人,记帐人,备注
          ,卡现金,卡赠送,换卡余额,换卡积分,换卡日期,换卡编号,是否积分,会员生日
          ,所属编号,会员类别,是否购物券,客户编号,会员卡类型,积分比例,限额
          ,会员等级类型,oID,Ocode,首次充值
    From 会员 
    Where id Not In(Select Distinct 会员ID 
                    From 会员身份)
  Else
    Select ID,编号,名称,生日,是否农历,电话,积分,折扣,最近时间,最近部门,状态,截止日期
          ,是否充值,卡金额,卡余额,建立时间,内码,身份证号,工作单位,审核人,记帐人,备注
          ,卡现金,卡赠送,换卡余额,换卡积分,换卡日期,换卡编号,是否积分,会员生日
          ,所属编号,会员类别,是否购物券,客户编号,会员卡类型,积分比例,限额
          ,会员等级类型,oID,Ocode,首次充值
    From 会员 
    Where id In(Select 会员ID 
                From 会员身份 
                Where 外键=@cWeiXinCode)
End
Go
----测试
--Exec GetVipInfo 'hHzy6qbEQfs_reKJ9ymW'
--Exec GetVipInfo
--Go

/*******************************************************************************
Procedure AddVip
功能:添加会员
参数
  @cWeiXinCode:会员身份外键
  @cMobileNumber:手机号
  @cName:姓名
  @cBirthday:生日,yyyy-mm-dd格式
  @bIsLunar:是农历,0表示生日是公历
  @cVipCode:绑定到会员编号
  --@cVipCode不指定则新建会员
  --指定则绑定到已有会员
  --指定的会员编号不存在则失败返回-1
返回值: 0---成功
       -1---已绑定到其它卡
       -2---无此会员
       -3---会员已停用
       -4---手机号码重复
2016-12-12 hunter__fox                  调用NewVip完成新会员记录建立
*/
If Object_id('[AddVip]','P') Is Not Null
  Drop Procedure [AddVip]
Go
Create Procedure [AddVip]
       @cWeiXinCode varchar(50)
      ,@cMobileNumber varchar(20)= ''--手机号
      ,@cName varchar(20)        = ''--姓名
      ,@cBirthday varchar(10)    = '1900-01-01'--生日,yyyy-mm-dd格式,范围1900-01-01到2079-06-06
      ,@bIsLunar bit             = 0--是农历,0表示生日是公历
      ,@cVipCode varchar(20)     = ''--要绑定的会员编号
                   --不指定则新建一个会员,指定则绑定到已有会员
                   --如果指定的会员不存在,则失败返回-1
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'AddVip'
  Set @ParaInfo = dbo.NameValue('C','@cWeiXinCode',@cWeiXinCode)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@cMobileNumber',@cMobileNumber) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@cName',@cName)                 Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@cBirthday',@cBirthday)         Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('L','@bIsLunar',@bIsLunar)           Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@cVipCode',@cVipCode)           Exec Loger @LogID,@Parameters=@ParaInfo
  Declare @iVipID int    --会员表ID
  Declare @VipState int  --10表示停用,表示有效
  Declare @IsLose bit    --1表示挂失,0表示未挂失
  Declare @IsVoucher bit   --1表示是购物券,0表示是会员卡
  Declare @VipType int   --1表示可折扣,2表示可充值
  Declare @dBirthday datetime
  --验证:外键唯一
  If Exists(Select * From 会员身份 Where 外键 = @cWeiXinCode)
  Begin
    Exec Loger @LogID,@ExtendInfo='此外键(参数:@cWeiXinCode)已经与某会员绑定',@Result=-1
    Return -1
  End
  --取得相应卡的状态信息(@iVipID,@cVipCode,@VipState,@IsLose,@Voucher)
  If @cVipCode='' Or @cVipCode Is Null
    Begin
      --无会员,建立它
      Exec Loger @LogID,@ExtendInfo='无会指定的会员,调用存储过程[NewVip]建立新的会员卡.'
      Exec NewVip @iVipID Output
      Exec Loger @LogID,@ExtendInfo='寻找卡:(id=@iVipID)'
      Select @cVipCode =编号
            ,@VipState =状态
            ,@IsLose   =是否充值
            ,@IsVoucher=是否购物券
            ,@VipType  =会员卡类型 
      From 会员 
      Where id=@iVipID
    End
  Else
    Begin
      --有会员,取ID
      Exec Loger @LogID,@ExtendInfo='寻找卡:(编号=@cVipCode)'
      Select @iVipID   =id
            ,@VipState =状态
            ,@IsLose   =是否充值
            ,@IsVoucher=是否购物券
            ,@VipType  =会员卡类型 
      From 会员 
      Where 编号=@cVipCode
    End
  
  Declare @RetCode int
  Set @RetCode = 0
  
  If IsNull(@iVipID,0)=0 
    Begin
      Exec Loger @LogID,@ExtendInfo='无此会员',@Result=-2
      Return -2
    End
  If @VipState = 0
    Begin
      Exec Loger @LogID,@ExtendInfo='会员卡已停用',@Result=-3
      Return -3
    End
  If @IsLose = 1
    Begin
      Exec Loger @LogID,@ExtendInfo='会员已经挂失',@Result=-4
      Return -4
    End
  If @IsVoucher = 1
    Begin
      Exec Loger @LogID,@ExtendInfo='不能绑定到购物券',@Result=-5
      Return -5
    End
  
  Update 会员
     Set 电话 = Case When IsNull(@cMobileNumber,'')=''
                     Then 电话 
                     Else @cMobileNumber
                End
        ,名称 = Case When IsNull(@cName,'')=''
                     Then 名称 
                     Else @cName
                End
        ,生日 = Case When IsNull(@cBirthday,'')=''
                     Then 生日 
                     Else Convert(datetime,@cBirthday)
                End
        ,是否农历 = Case When @bIsLunar Is Null 
                         Then 是否农历 
                         Else @bIsLunar
                    End
        ,会员生日 = Case When IsNull(@cBirthday,'')=''
                         Then 会员生日 
                         Else Right(Replace(@cBirthday,'-',''),4)
                    End
  Where ID=@iVipID
  --进行会员绑定
  Insert Into 会员身份(会员ID,外键)Values(@iVipID,@cWeiXinCode)
  If @@ERROR<>0
  Begin
    Delete From 会员 where ID=@iVipID
    Return -1
  End
  Exec Loger @LogID,@ExtendInfo='在表[会员身份]添加记录',@Result=0
End
Go
--测试
--下述所述"会成功"是在相应外键没有使用的情况下
--Delete From 会员身份 Where 外键 In('{测试绑定码}','{测试绑定码2}','{测试绑定码3}')
--Exec AddVip '{测试绑定码6}','{手机号}','{微信}'  --新增电子会员并绑定外键,第一次会成功
--Exec GetVipInfo '{测试绑定码6}'
--Exec AddVip '{测试绑定码2}','{手机号}','{微信}','1983-10-23',1,'999999999'  --绑定到已有会员,第一次会成功
--Exec GetVipInfo '{测试绑定码2}'
--Exec AddVip '{测试绑定码6}','{手机号}','{微信}',Null,Null,'000000' --绑定失败:指定的会员不存在
--Exec GetVipInfo '{测试绑定码6}'
--Go

/*******************************************************************************
Procedure VipReCharge
功能:会员充值
参数:
  @cWeiXinCode:会员绑定的外键
  @nAddMoney:充值金额
  @cWeiXinOrderCode:对应的微商城支付订单号
返回值
    0 :成功
    -1:无此会员
    -2:生成订单号失败
    -3:生成充值记录失败
    -4:卡未启用
    -5:是购物券
    -6:卡已挂失
    -7:卡不可充值
*/
If Object_id('[VipReCharge]','P') Is Not Null
  Drop Procedure [VipReCharge]
Go
Create Procedure [VipReCharge]
       @cDepart varchar(10),         --部门编号
       @cUser varchar(10),           --制单人
       @iPayMode int,                --结算方式
       @VipCode varchar(50),         --会员编号
       @nAddMoney money=0,           --充值金额
       @cWeiXinOrderCode varchar(50) --微商城支付订单号
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'VipReCharge'
  Set @ParaInfo = dbo.NameValue('C','@cDepart',@cDepart)                   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@cUser',@cUser)                       Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('N','@iPayMode',@iPayMode)                 Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@VipCode',@VipCode)                   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@nAddMoney',@nAddMoney)               Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@cWeiXinOrderCode',@cWeiXinOrderCode) Exec Loger @LogID,@Parameters=@ParaInfo
  --取会员信息,确认是否是可充值的卡
  Declare @iVipID int, @cVipCode varchar(20),@VipState int,@IsVoucher bit,@IsLose bit,@VipType int,@EndDate datetime
  Select @iVipID   =id
        ,@cVipCode =编号
        ,@VipState =状态
        ,@IsVoucher=是否购物券
        ,@IsLose   =是否充值
        ,@VipType  =会员卡类型
        ,@EndDate = 截止日期
  From 会员 
  Where 编号=@VipCode
  If IsNull(@iVipID,0)=0
    Begin
      Exec Loger @LogID,@ExtendInfo='无此会员',@Result=-1
      Return -1
    End
  If @VipState = 0
    Begin
      Exec Loger @LogID,@ExtendInfo='卡未启用',@Result=-4
      Return -4
    End
  If @IsVoucher = 1
    Begin
      Exec Loger @LogID,@ExtendInfo='是购物券',@Result=-5
      Return -5
    End
  If @IsLose = 1
    Begin
      Exec Loger @LogID,@ExtendInfo='卡已挂失',@Result=-6
      Return -6
    End
  If (@VipType & 2) = 0
    Begin
      Exec Loger @LogID,@ExtendInfo='卡不可充值',@Result=-7
      Return -7
    End
  If @EndDate<getdate()
    Begin
      Exec Loger @LogID,@ExtendInfo='卡已超过使用期限',@Result=-8
      Return -8
    End
  --生成充值单号,独立事务
  --它是充值的必要条件,但生成的单号可以丢弃
  Declare @cOrderNo varchar(20), @cDate varchar(10), @iBillMax int
  Set @cDate=Convert(varchar(10), GetDate(), 120)
  Begin Tran tran_VipReCharge_1
    Select @iBillMax=IsNull(最大单号, 0) + 1 From bills Where 表名 = '充值业务' And 会计期间 = @cDate
    If @iBillMax Is Null Set @iBillMax = 1
    If @iBillMax=1
      Insert Into bills(表名, 会计期间, 最大单号)Values('充值业务', @cDate, @iBillMax)
    Else
      Update bills Set 最大单号 = @iBillMax Where 表名 = '充值业务' And 会计期间 = @cDate
  Commit Tran tran_VipReCharge_1
  If @@Error<>0 
    Begin
      Rollback Tran tran_VipReCharge_1
      Exec Loger @LogID,@ExtendInfo='生成充值业务单号失败',@Result=-2
      Return -2
    End
  Set @cOrderNo = Substring(Replace(@cDate, '-', ''),3,6) + '-' + Replace(Str(@iBillMax, 5), ' ', '0')
  --建立充值记录,在事务中进行
  --要求四表同步,会员表由充值明细表触发器更改,第三方结算记录由AddWebPay在事务外完成
  Declare @PayMoney money
  Select @PayMoney = money From web_payment_orders Where payment_serial_num=@cWeiXinOrderCode
  Set @PayMoney = IsNull(@PayMoney, 0)
  Declare @iBusinessID int,@RetCode int
  Begin Tran tran_VipReCharge_2
    Insert Into 充值表(单号, 日期, 部门编号, 制单人,金额合计, 现金合计, 状态, 备注)
           Values(@cOrderNo, @cDate, @cDepart, @cUser, @nAddMoney, @PayMoney, 1, 'WSC_Serial_num:' + @cWeiXinOrderCode)
    Select @iBusinessID=Scope_Identity()
    Insert Into 充值明细表(PID, 编号, 金额, 现金)
           Values(@iBusinessID, @cVipCode, @nAddMoney, @PayMoney)
  Commit Tran tran_VipReCharge_2
  If @@Error<>0 
    Begin
      Rollback Tran tran_VipReCharge_2
      Exec Loger @LogID,@Key=@iBusinessID,@ExtendInfo='建立充值记录失败.',@Result=-3
      Return -3
    End
  Else
    Exec Loger @LogID,@Key=@iBusinessID,@ExtendInfo='建立充值记录成功.'
  
  Exec Loger @LogID,@ExtendInfo='调用存储过程[AddWebPay]补充第三方结算记录.'
  Exec AddWebPay 3,@iBusinessID,@cWeiXinOrderCode

  Exec Loger @LogID,@Result=0
  Return 0
End
Go
----测试
--Exec GetVipInfo 'hHzy6qbEQfs_reKJ9ymW'
--Exec VipReCharge 'hHzy6qbEQfs_reKJ9ymW',0.01,'1234567890'
--Exec GetVipInfo 'hHzy6qbEQfs_reKJ9ymW'
--declare @LasID int
--Select @LasID=max(ID) From 充值表
--select * from 充值表 where id=@LasID
--select * from 充值明细表 where pid=@LasID
--select * from 结算明细表 where 业务ID=@LasID and 来源=3

/*******************************************************************************
Procedure PushWeiXinMessage
功能:生成一条推送信息
参数:
     @cVipCode:会员编号
     @cSource:线下零售/线下订单/线下充值
     @cSaleNum:单号
     @cPayType:支付方式
     @nAmount:金额
*/
If Object_id('[PushWeiXinMessage]','P') Is Not Null
  Drop Procedure [PushWeiXinMessage]
Go
Create Procedure [PushWeiXinMessage]
       @cVipCode varchar(20), --会员编号
       @cSource  varchar(50), --交易类型,线下零售/线下订单/线下充值
       @cSaleNum varchar(20), --相关单号
       @cPayType varchar(20), --支付方式,现金/会员卡/积分...
       @nAmount  money        --相关金额
As
Begin
  Insert Into web_message(oauth_id,order_num,[money],pay_type)
  Select 外键,@cSource + ':' + @cSaleNum,@nAmount,@cPayType
  From 会员身份
  Where 会员ID In (Select ID From 会员 Where 编号=@cVipCode)
    And 验证方式='WeiXinOpenid'
  Return @@RowCount
End
Go
----测试
--Exec PushWeiXinMessage 'WX00000005','线下零售','170928-09527','没给钱',0.5

/*******************************************************************************
Procedure VipBonusPoints
功能:修改会员积分
参数:
     @cDepart:部门
     @cUser:操作员
     @VipCode:会员卡号
     @Add:t积分增加数
     @Description:积分变动原因
*/
If Object_id('VipBonusPoints','P') Is Not Null
  Drop Procedure [VipBonusPoints]
Go
Create Procedure [VipBonusPoints]
       @cDepart     varchar(10)   ,--部门
       @cUser       varchar(10)   ,--操作员
       @VipCode     varchar(20)   ,--会员卡编号
       @Add         money=0       ,--积分增加值(减少为负数,0为不变)
       @Description varchar(20)='' --积分变动原因
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'VipBonusPoints'
  Set @ParaInfo = dbo.NameValue('C','@cDepart',@cDepart)         Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@cUser',@cUser)             Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@VipCode',@VipCode)         Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@Add',@Add)                 Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@Description',@Description) Exec Loger @LogID,@Parameters=@ParaInfo
  
  Declare @状态 int,@是否充值 bit,@积分 money,@截止日期 datetime,@是否购物券 bit
  Select @状态=状态,@是否充值=是否充值,@积分=积分,@截止日期=截止日期,@是否购物券=是否购物券 From 会员 Where 编号=@VipCode
  Declare @iBusinessID int,@RetCode int

  If @状态 Is Null
    Begin
      Exec Loger @LogID,@VipCode,@ExtendInfo='无此会员',@Result=-1
      Return -1
    End
  If @是否购物券=1
    Begin
      Exec Loger @LogID,@ExtendInfo='是购物券',@Result=-6
      Return -6
    End
  If @状态 = 0
    Begin
      Exec Loger @LogID,@ExtendInfo='卡未启用',@Result=-2
      Return -2
    End
  If @是否充值=1
    Begin
      Exec Loger @LogID,@ExtendInfo='卡已挂失',@Result=-3
      Return -3
    End
  If @截止日期< getdate()
    Begin
      Exec Loger @LogID,@ExtendInfo='卡已过期',@Result=-4
      Return -4
    End
  --如果积分变动值不为0,则生成相应的充值金额为0的充值记录
  If @Add <> 0
    Begin
      --生成充值单号,独立事务
      --它是充值的必要条件,但生成的单号可以丢弃
      Declare @cOrderNo varchar(20), @cDate varchar(10), @iBillMax int
      Set @cDate=Convert(varchar(10), GetDate(), 120)
      Exec getbillmax '充值业务', @cDate, @iBillMax OUTPUT
      Set @cOrderNo = Replace(@cDate, '-', '') + '-' + Replace(Str(@iBillMax, 5), ' ', '0')
      
      Begin Tran tran_VipBonusPoints
        Insert Into 充值表(单号, 日期, 部门编号, 制单人,金额合计, 现金合计, 状态, 备注)
               Values(@cOrderNo, @cDate, @cDepart, @cUser, 0, 0, 1, @Description)
        Set @iBusinessID=Scope_Identity()
        Insert Into 充值明细表(PID, 编号, 金额, 现金,积分,备注)
               Values(@iBusinessID, @VipCode, 0, 0, @Add, @Description)
        Update 会员 Set 积分 = @积分 + @Add Where 编号 = @VipCode
      Commit Tran tran_VipBonusPoints  
      If @@Error<>0 
        Begin
          Rollback Tran tran_VipBonusPoints
          Exec Loger @LogID,@Key=@iBusinessID,@ExtendInfo='积分变动失败',@Result=-5
          Return -5
        End
    End

  Exec Loger @LogID,@Key=@iBusinessID,@Result=0
  Return 0
End
Go
/*******************************************************************************
Procedure GetVipLog
功能:获取指定会员的消费/充值记录
参数:
  @cWeiXinCode:会员绑定的外键
  @cDateStart:查询开始日期(含此日)
  @cDateStart:查询截止日期(含此日)
*/
If Object_id('[GetVipLog]','P') Is Not Null
  Drop Procedure [GetVipLog]
Go
Create Procedure [GetVipLog]
       @cWeiXinCode varchar(50),--会员绑定的外键
       @cDateStart varchar(10)='1900-01-01', --查询开始日期(含此日)
       @cDateEnd varchar(10)=''  --查询截止日期(含此日)
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'GetVipLog'
  Set @ParaInfo = dbo.NameValue('C','@cWeiXinCode',@cWeiXinCode) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@cDateStart',@cDateStart)   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@cDateEnd',@cDateEnd)       Exec Loger @LogID,@Parameters=@ParaInfo

  If @cWeiXinCode='' Or @cWeiXinCode Is Null
  Begin
    Exec Loger @LogID,@ExtendInfo='需要指定一个外键',@Result=-1
    Return -1
  End
  Declare @iVipID int
  Select @iVipID=id From 会员 Where id In(Select 会员ID From 会员身份 Where 外键 = @cWeiXinCode)
  If @iVipID Is Null
  Begin
    Exec Loger @LogID,@ExtendInfo='指定的外键没有与会员绑定',@Result=-2
    Return -2
  End
  Exec GetVipLog_ID @iVipID,@cDateStart,@cDateEnd
  Return 0
End
Go
----测试
----注:测试数据库中数据有手动修改,余额不能自恰
--Delete From 会员身份 Where 外键 = '{测试绑定码2}'
--Exec AddVip '{测试绑定码2}','{手机号}','{微信}','1983-10-23',1,'999999999'
--Exec GetVipLog '{测试绑定码2}'
--Exec GetVipLog '{测试绑定码2}','2016-10-12',''
--Exec GetVipLog '{测试绑定码2}','2016-10-12','2016-10-30'
/*******************************************************************************
Procedure GetVouchers
功能:获取礼券信息
参数:@DepartmentCode:部门编号
     @VipCode:会员编号
     @VoucherCode:礼券编号
     @GoodsCode:券的产品编号
此三个参数互斥:
  指定@DepartmentCode将获取出库到该部门且出销售的礼券列表(此部门可销售的礼券)
  指定@VipCode则获取销售到指定会员并且未使用的礼券列表,不限定是哪个部门销售的
  指定@VoucherCode则获取指定礼券的信息,不限定券状态与销售部门和归属
  指定了@DepartmentCode将忽略@VipCode与@VoucherCode
  指定了@VipCode将忽略@VoucherCode,@GoodsCode
  指定了@VoucherCode将忽略@GoodsCode
*/

If Object_id('[GetVouchers]','P') Is Not Null
  Drop Procedure [GetVouchers]
Go
Create Procedure [GetVouchers]
       @DepartmentCode varchar(20)='',--获取出库到指定部门并且尚未销售的礼券
       @VipCode        varchar(20)='',--获取销售给指定会员的礼券
       @VoucherCode    varchar(20)='',--获取指定编号礼券
       @GoodsCode      varchar(10)='' --获取指定产品编号的券,仅在指定@DepartmentCode时有效
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'GetVouchers'
  Set @ParaInfo = dbo.NameValue('C','@DepartmentCode',@DepartmentCode) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@VipCode',@VipCode)               Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@VoucherCode',@VoucherCode)       Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@GoodsCode',@GoodsCode)           Exec Loger @LogID,@Parameters=@ParaInfo
  If IsNull(@DepartmentCode, '') <> '' And IsNull(@GoodsCode, '') <> ''
    Begin
      Exec Loger @LogID,@ExtendInfo='获取出库到指定部门,并且尚未销售的,指定产品编号的礼券'
      Select A.产品编号,A.编号,A.面额,'已出库' As 状态
            ,Case When IsNull(A.往来编号,'0')='0' Then '' Else A.往来编号 End As 会员编号
            ,A.截止日期,A.条码
      From 礼券表 As A
      Left Join 礼券出库表 As B On A.编号=B.编号
      Where A.状态=1 And B.往来编号=@DepartmentCode
        And A.截止日期>=getdate()
        And A.产品编号=@GoodsCode
      Order By b.出库时间
    End
  Else If IsNull(@DepartmentCode, '') <> ''
    Begin
      Exec Loger @LogID,@ExtendInfo='获取出库到指定部门并且尚未销售的礼券'
      Select A.产品编号,A.编号,A.面额,'已出库' As 状态
            ,Case When IsNull(A.往来编号,'0')='0' Then '' Else A.往来编号 End As 会员编号
            ,A.截止日期,A.条码
      From 礼券表 As A
      Left Join 礼券出库表 As B On A.编号=B.编号
      Where A.状态=1 And B.往来编号=@DepartmentCode
        And A.截止日期>=getdate()
      Order By b.出库时间
    End
  Else If IsNull(@VipCode, '') <> ''
    Begin
      Exec Loger @LogID,@ExtendInfo='获取指定会员持有的未过期礼券'
      Select A.产品编号,A.编号,A.面额,'已销售' As 状态
            ,@VipCode As 会员编号
            ,A.截止日期,A.条码
      From 礼券表 As a
      Inner Join 礼券销售表 As b On a.编号=b.编号
      Where b.往来编号=@VipCode
        And a.状态=2 and a.是否回收=0
        And a.截止日期>=getdate()
      Order By a.截止日期
    End
  Else If IsNull(@VoucherCode, '') <> ''
    Begin
      Exec Loger @LogID,@ExtendInfo='指定产品编号的礼券,无论它们处于什么状态'
      Select A.产品编号,A.编号,A.面额
            ,Case A.状态+A.是否回收
                  When 0 Then '未出库'
                  When 1 Then '已出库'
                  When 2 Then '已销售'
                  When 3 Then '已使用'
                  Else '无效'
             End As 状态
            ,Case When IsNull(A.往来编号,'0')='0' 
                  Then IsNull(C.往来编号,'') 
                  Else A.往来编号 
             End As 会员编号
            ,A.截止日期,A.条码
      From 礼券表 As A
      Left Join 礼券出库表 As B On A.编号=B.编号
      Left Join 礼券销售表 As C On A.编号=C.编号
      Where A.编号=@VoucherCode
    End
  Else
    Begin
      Exec Loger @LogID,@ExtendInfo='获取一个空结果集',@Result=-1
      Select Top 0 产品编号,编号,面额,'' As 状态,往来编号 As 会员编号,截止日期,条码 From 礼券表
      Return -1 --无参数
    End
  Return 0
End
Go
/*******************************************************************************
Procedure Saleing
功能:生成销售记录
参数:
    @DepartmentCode:销售部门
    @UserCode:操作员
    @CDate:做帐日期
    @GoodsCode:商品编号
    @GoodsCount:商品数量
    @Price:销售单价
    @VipCode:往来编号
    @Ret_BillCode:创建的销售单单号,OUTPUT
该存储过程仅适用于单一商品的销售行为
多种商品应循环调用SaleingEx
*/
If Object_id('[Saleing]','P') Is Not Null
  Drop Procedure [Saleing]
Go
Create Procedure [Saleing]
    @DepartmentCode varchar(10)     ,--销售部门
    @UserCode varchar(10)           ,--操作员
    @CDate varchar(10)              ,--做帐日期
    @GoodsCode varchar(10)          ,--商品编号
    @GoodsCount int                 ,--商品数量
    @Price money                    ,--单价
    @PayByCash money=0              ,--现金支付金额
    @PayByCard money=0              ,--会员卡支付金额    
    @VipCode varchar(20)            ,--往来编号
    @Ret_BillCode varchar(20) OutPut --创建的销售单单号
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'Saleing'
  Set @ParaInfo = dbo.NameValue('C','@DepartmentCode',@DepartmentCode) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@UserCode',@UserCode)             Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@CDate',@CDate)                   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@GoodsCode',@GoodsCode)           Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('N','@GoodsCount',@GoodsCount)         Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@Price',@Price)                   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@PayByCash',@PayByCash)           Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@PayByCard',@PayByCard)           Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@VipCode',@VipCode)               Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@Ret_BillCode',@Ret_BillCode)     Exec Loger @LogID,@Parameters=@ParaInfo
  --生成单号
  Declare @BillMax int,@SalesID int
  Exec getbillmax '销售', @CDate, @BillMax Output
  Set @Ret_BillCode = Convert(varchar(6),Convert(datetime, @CDate), 12) 
                    + '-' + Right('0000' + Convert(varchar(10), @BillMax), 5)
  Set @ParaInfo = '生成单号:' + @Ret_BillCode Exec Loger @LogID,@ExtendInfo=@ParaInfo
  
  --在事务中添加记录
  Declare @销售类别 varchar(20),@原价 money,@当前余额 money,@当前积分 money
  Set @销售类别=Case When @VipCode='' Or @VipCode Is Null Then '正常零销' Else '会员销售' End
  Select @原价=销售主价 From goods Where 编号=@GoodsCode And 是否销售=1
  Begin Transaction tran_Saleing
    Insert Into sales(单号,部门编号,制单人,日期,销售类别,结单,往来编号,当前余额,当前积分
                     ,数量合计,金额合计,折扣合计
                     ,现金金额,刷卡金额,礼券金额)
           Values(@Ret_BillCode,@DepartmentCode,@UserCode,@CDate,@销售类别,1,@VipCode,0,0
                 ,@GoodsCount,@GoodsCount*@Price,@GoodsCount*(@原价-@Price)
                 ,@PayByCash,@PayByCard,@GoodsCount*@Price-@PayByCash-@PayByCard)
    If @@Rowcount > 0
      Begin
        Set @SalesID = Scope_Identity()
        Insert Into sales_detail(销售ID,编号,单价,数量,金额,原价,促销标记)
               Values(@SalesID,@GoodsCode,@Price,@GoodsCount,@GoodsCount*@Price,@原价,Case When @原价=@Price Then 0 Else 1 End)
      End
    --更新销售主表中会员余额与积分信息
    If @销售类别='会员销售'
      Begin
        Select @当前余额=卡余额, @当前积分=积分 From 会员 where 编号=@VipCode
        Update sales Set 当前余额=@当前余额,当前积分=@当前积分 Where ID=@SalesID
      End
  --提交事务
  Commit Transaction tran_Saleing
  If @@Error<>0
  Begin
    Rollback Transaction tran_Saleing
    Exec Loger @LogID,@ExtendInfo='提交事务失败',@Result=-1
    Return -1
  End
  Exec Loger @LogID,@Key=@SalesID,@ExtendInfo='成功'
  Return 0
End
Go
/*******************************************************************************
Procedure SaleingVoucher
功能:礼券销售
参数:
    --部门信息
    @DepartmentCode:销售部门
    @UserCode:操作员
    @CDate:做账日期
    --商品信息
    @GoodsCode:商品编号
    @Price:售价
    --支付信息
    @PayByCash:现金支付金额
    @PayByCard:会员卡支付金额
    @VipCode:会员编号
    --礼券信息
    @VoucherCode:礼券编号
此过程调用Saleing建立相应的销售记录(在sales/sales_detail表),在[礼券销售表]生成记录,更改[礼券表][状态][是否回收]字段
返回值:
  0:成功
  -1:生成销售单失败
  -100:券号无效
  -101:券已过期
  -102:券销售失败
  -103:券未出库
  -105:券已经销售
  -106:券已使用
*/
If Object_id('[SaleingVoucher]','P') Is Not Null
  Drop Procedure [SaleingVoucher]
Go
Create Procedure [SaleingVoucher]
       @DepartmentCode varchar(10)       ,--销售部门
       @UserCode       varchar(10)       ,--制单人
       @CDate          varchar(10)=''    ,--做帐日期
       @GoodsCode      varchar(10)       ,--商品编号
       @Price          money      =0     ,--售价
       @PayByCash      money      =0     ,--现金支付金额
       @PayByCard      money      =0     ,--会员卡支付金额
       @VipCode        varchar(20)=''    ,--往来编号
       @VoucherCode    varchar(20)='0000',--券编号,编号为0000的券不做记录
       @Ret_BillCode   varchar(20) Output--相应销售单号,返回值
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'SaleingVoucher'
  Set @ParaInfo = dbo.NameValue('C','@DepartmentCode',@DepartmentCode) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@UserCode',@UserCode)             Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@CDate',@CDate)                   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@GoodsCode',@GoodsCode)           Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@Price',@Price)                   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@PayByCash',@PayByCash)           Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@PayByCard',@PayByCard)           Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@VipCode',@VipCode)               Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@VoucherCode',@VoucherCode)       Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@Ret_BillCode',@Ret_BillCode)     Exec Loger @LogID,@Parameters=@ParaInfo

  If @CDate='' Or @CDate Is Null 
    Set @CDate = Convert(varchar(10), GetDate(), 120) --中午十二点之后做帐到后一天
  Declare @Ret int
  Declare @面额 money,@状态 int,@截止日期 datetime
  Select @面额=面额,@状态=状态+是否回收,@截止日期=截止日期 From 礼券表 Where 编号=@VoucherCode
  --检查:券必需是已销售并且未过截止日期的
  If @@RowCount = 0
    Begin
      Exec Loger @LogID,@ExtendInfo='券号无效',@Result=-100
      Return -100
    End
  If @截止日期<getdate()
    Begin
      Exec Loger @LogID,@ExtendInfo='券已过期',@Result=-101
      Return -101
    End
  If @状态 <> 1
    Begin
      Set @状态=-103-@状态
      Set @ParaInfo = Case @状态
                           When -103 Then '券未出库'
                           When -105 Then '券已销售'
                           When -106 Then '券已使用'
                      End
      Exec Loger @LogID,@ExtendInfo=@ParaInfo,@Result=@状态
      Return @状态
    End
  Begin Transaction tran_SaleingVoucher
    --生成销售记录
    Exec @Ret=Saleing @DepartmentCode,@UserCode,@CDate,@GoodsCode,1,@Price,@PayByCash,@PayByCard,@VipCode,@Ret_BillCode Output
    If @Ret<>0
      Begin
        Rollback Transaction tran_SaleingVoucher
        Exec Loger @LogID,@ExtendInfo='调用存储过程[Saleing]生成交易记录失败失败',@Result=@Ret
        Return @Ret
      End
    --生成礼券销售表记录
    If @VoucherCode<>'0000'
      Begin
        Insert Into 礼券销售表(编号,面额,金额,日期,部门编号,往来编号,销售时间,单号)
               Values(@VoucherCode, @面额, @Price, @CDate, @DepartmentCode, @VipCode, getdate(), @Ret_BillCode)
        --更改礼券表状态
        Update 礼券表 Set 状态=2,是否回收=0,往来编号=@VipCode Where 编号=@VoucherCode
      End
  --提交事务
  Commit Transaction tran_SaleingVoucher
  If @@ERROR<>0
  Begin
    Rollback Transaction tran_SaleingVoucher
    Exec Loger @LogID,@ExtendInfo='提交事务失败',@Result=-102
    Return -102 --事务失败
  End
  Exec Loger @LogID,@ExtendInfo='成功'
  Return 0 --成功
End
Go
/*******************************************************************************
Procedure CreateOrder
功能:生成一张订单
参数:
    @单号:为空则新建单,不为空则修改已有单
    @部门编号:销售部门
    @制单人
    其它均为可选参数
返回值:
  返回对应的订单ID,如果返回值小于1则失败
*/
If Object_id('[CreateOrder]','P') Is Not Null
  Drop Procedure [CreateOrder]
Go
Create Procedure [CreateOrder]
       @单号       varchar(20)=NULL  OUTPUT,--为空则新建单,不为空则修改已有单
       @部门编号   varchar(10)       ,--销售部门
       @制单人     varchar(10)       ,--制单人
       @商户订单号 varchar(32)=''    ,--外部转入的订单,对应的外部订单号
       @日期       varchar(10)=Null  ,--做帐日期
       @生产单位   varchar(10)=Null  ,--生产单位
       @取货部门   varchar(10)=Null  ,--取货部门
       @取货时间   datetime   =Null  ,--预约取货时间
       @往来编号   varchar(20)=''    ,--会员卡编号
       @联系方式   varchar(50)=Null  ,--联系方式
       @备注       varchar(100)=Null ,--
       @送货地址   varchar(100)=Null ,--
       @金额合计   money      =0     ,--未折扣时合计
       @优惠合计   money      =0     ,--折让部分,金额合计-优惠合计=实收金额,实收金额-让利-订金-刷卡金额-礼券=欠款
       @让利       money      =0     ,--整单免除部分(抹零)
       @订金       money      =0     ,--下单时用现金支付的
       @刷卡金额   money      =0     ,--下单时用会员卡支付的
       @礼券       money      =0     ,--下单时用礼券抵扣的
       @结单       bit        =Null  ,--是否结单
       @废止       bit        =Null  ,--如果废止,@结单将变为0
       @销售类别   varchar(50)=''    ,--网上商城生成的订单为'公众号',不可修改
       @礼券列表   varchar(300)=''   --顾客使用的礼券编号列表
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'CreateOrder'
  Set @ParaInfo = dbo.NameValue('C','@单号',@单号)             Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@部门编号',@部门编号)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@制单人',@制单人)         Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@商户订单号',@商户订单号) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@日期',@日期)             Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@生产单位',@生产单位)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@取货部门',@取货部门)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@取货时间',@取货时间)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@往来编号',@往来编号)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@联系方式',@联系方式)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@备注',@备注)             Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@送货地址',@送货地址)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@金额合计',@金额合计)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@优惠合计',@优惠合计)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@让利',@让利)             Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@订金',@订金)             Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@刷卡金额',@刷卡金额)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@礼券',@礼券)             Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('L','@结单',@结单)             Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('L','@废止',@废止)             Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@销售类别',@销售类别)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@礼券列表',@礼券列表)     Exec Loger @LogID,@Parameters=@ParaInfo

  Declare @运费 money --运费从微商城相应订单记录中提取
  If IsNull(@商户订单号,'')<>''
    Select @运费=shipping_cost from web_orders where order_serial_num = @商户订单号
  Set @运费 = IsNull(@运费, 0)
  
  Declare @OrderID int--成功返回订单ID,失败返回0或负值
  Declare @单位名称 varchar(50),@当前余额 money,@当前积分 money,@应收金额 money,@欠款 money,@交货日期 varchar(10)
  Declare @第三方支付 money,@ErrorID int
  Set @废止=IsNull(@废止,0)
  If @废止=1 Set @结单=0 Else Set @结单=IsNull(@结单,0)
  If @生产单位 Is Null Set @生产单位=@部门编号
  If @取货部门 Is Null Set @取货部门=@生产单位
  If @取货时间 Is Null Set @取货时间=GetDate()
  If @往来编号=''
    Set @单位名称=''
  Else
    Select @单位名称=名称 From 会员 Where 编号=@往来编号
  Set @应收金额=@金额合计-@优惠合计
  Set @欠款 = Case When @结单=1 Then 0 Else @应收金额-@让利-@订金-@刷卡金额-@礼券 End
  Set @欠款 = IsNull(@欠款, 0)
  Set @交货日期=Case When @结单=1 Or @废止=1 Then Convert(varchar(10),GetDate(),120) Else Null End
  If @单号 Is Null Or @单号=''
  Begin
    Declare @刷卡日期 varchar(10),@礼券日期 varchar(10)
    If IsNull(@刷卡金额, 0) <> 0 Set @刷卡日期 = Convert(varchar(10), GetDate(), 120)
    If IsNull(@礼券    , 0) <> 0 Set @礼券日期 = Convert(varchar(10), GetDate(), 120)
    Exec Loger @LogID,@ExtendInfo='新建订单'
    If @日期 Is Null
      Set @日期 = Convert(varchar(10), GetDate(), 120)
    Declare @BillMax int,@SalesID int
    Exec getbillmax '订单', @日期, @BillMax Output
    Set @单号 = Convert(varchar(6),Convert(datetime, @日期), 12) 
              + '-' + Right('0000' + Convert(varchar(10), @BillMax), 5)
    Exec Loger @LogID,@Key=@单号,@ExtendInfo='建立订单(orders)记录'
    Insert orders(单号,日期,部门编号,制单人,生产单位,取货部门,取货时间,商户订单号
                 ,往来编号,联系方式,备注,送货地址,单位名称,当前余额,当前积分
                 ,金额合计,优惠合计,应收金额,让利,订金,刷卡金额,刷卡日期,礼券,礼券日期,欠款
                 ,结单,是否废止,交货日期,销售类别)
           Values(@单号,@日期,@部门编号,@制单人,@生产单位,@取货部门,@取货时间,@商户订单号
                 ,@往来编号,@联系方式,@备注,@送货地址,@单位名称,0,0
                 ,@金额合计,@优惠合计,@应收金额,@让利,@订金,@刷卡金额,@刷卡日期,@礼券,@礼券日期,@欠款
                 ,@结单,@废止,@交货日期,@销售类别)
    Set @ErrorID=@@Error
    Set @OrderID=Scope_Identity()
    If @ErrorID <> 0
      Begin
        If @ErrorID > 0 Set @ErrorID = 0 - @ErrorID
        Exec Loger @LogID,@ExtendInfo='失败',@Result=@ErrorID
        Return @ErrorID
      End
    --更新记录中相关会员当前余额与积分信息
    If @往来编号 <> ''
    Begin
      Exec Loger @LogID,@ExtendInfo='更新订单中相关会员当前余额与积分'
      Select @当前余额=卡余额, @当前积分=积分 From 会员 Where 编号=@往来编号
      Update orders Set 当前余额=@当前余额,当前积分=@当前积分 Where ID=@OrderID
    End
    ----添加礼券回收表记录,更新礼券表状态
    --If IsNull(@礼券列表,'')<>''
    --Begin
    --  Declare @SPT_UpdateVochers varchar(3000)
    --  Set @SPT_UpdateVochers = 'Update '
    --  execute 
    --End
    --更新从微商城产生的第三方支付金额
    Exec Loger @LogID,@ExtendInfo='同步从微商城产生的第三方支付金额'
    Exec AddWebPay 1,@OrderID
    --重算并更新欠款
    Exec Loger @LogID,@ExtendInfo='重算并更新欠款'
    Select @第三方支付 = Sum(结算金额) 
    From 结算明细表 
    Where 业务ID=@OrderID 
      and 来源=1 
      And 结算ID In (Select ID 
                     From 结算方式表 
                     Where 第三方支付=1)
    Set @第三方支付 = IsNull(@第三方支付, 0)
    Set @欠款= Case When @结单=1 
                    Then 0 
                    Else @应收金额-@让利-@订金-@刷卡金额-@礼券-@第三方支付 
               End
    Set @欠款 = IsNull(@欠款, 0)
    Update orders Set 订金=@第三方支付,欠款=@欠款 Where ID=@OrderID
  End
  Else
  Begin
    --订单只能修改部分字段
    Exec Loger @LogID,@Key=@单号,@ExtendInfo='新建订单'
    Set @OrderID=Scope_Identity()
    Update orders 
       Set 联系方式=IsNull(@联系方式,联系方式)
          ,取货时间=IsNull(@取货时间,取货时间)
          ,生产单位=IsNull(@生产单位,生产单位)
          ,备注    =IsNull(@备注,备注)
          ,送货地址=IsNull(@送货地址,送货地址)
          ,取货部门=IsNull(@取货部门,取货部门)
          ,交货日期=IsNull(@交货日期,交货日期)
          ,结单    =IsNull(@结单,结单)
          ,是否废止=IsNull(@废止,是否废止)
          ,欠款    =IsNull(@欠款,欠款)
     Where id=@OrderID
  End
  --如果运费不为零,生成明细中的运费项
  If @运费 > 0
  Begin
    Set @ParaInfo='生成明细中的运费项:@运费='+Convert(varchar(20),@运费)
    Exec Loger @LogID,@ExtendInfo=@ParaInfo
    Delete From orders_detail Where 订单ID=@OrderID And 编号='SYS_001'
    Exec AddOrderItem @OrderID,'SYS_001',@运费,1,@运费
    --如果有必要,在此加入相应的入库动作
  End
  
  Exec Loger @LogID,@Result=@OrderID
  Return @OrderID
End
Go
/*******************************************************************************
Procedure AddOrderItem
功能:生成一条订单明细
*/
If Object_id('[AddOrderItem]','P') Is Not Null
  Drop Procedure [AddOrderItem]
Go
Create Procedure [AddOrderItem]
       @PID         int        ,--订单ID
       @GoodsCode   varchar(10),--商品编号
       @GoodsCount  int        ,--商品数量
       @Price       money      ,--单价,未折扣的单价
       @TotalAmount money      ,--金额,折扣后总金额
       @ExtendPrice money=0     --线上单价
As       
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'AddOrderItem'
  Set @ParaInfo = dbo.NameValue('N','@PID',@PID)                 Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@GoodsCode',@GoodsCode)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('N','@GoodsCount',@GoodsCount)   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@Price',@Price)             Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@TotalAmount',@TotalAmount) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@ExtendPrice',@ExtendPrice) Exec Loger @LogID,@Parameters=@ParaInfo
  

  Declare @Return int,@DetailID int
  Declare @名称 varchar(30),@规格 varchar(20),@折扣 money,@优惠金额 money

  Select @名称=名称,@规格=规格 From goods Where 是否销售=1 And 编号=@GoodsCode
  Set @折扣 = @TotalAmount * 100 / @Price / @GoodsCount
  Set @优惠金额 = @Price * @GoodsCount - @TotalAmount

  Insert Into orders_detail(订单ID,编号,名称,规格,单价,折扣,数量,金额,优惠金额,结单)
         Values(@PID,@GoodsCode,@名称,@规格,@Price,@折扣,@GoodsCount,@TotalAmount,@优惠金额,0)
  Set @DetailID = Scope_Identity()
  If IsNull(@ExtendPrice, 0)<>0
    Insert Into orders_detail_extend(link_id,web_price)Values(@DetailID,@ExtendPrice)
  If @@Error<>0 Set @Return = -1 Else Set @Return = 0
  
  Exec Loger @LogID,@Key=@DetailID,@Result=@Return
  Return @Return
End
Go
/*******************************************************************************
Procedure GetOrdersList
功能:得到订单列表
参数:
     @Department:部门编号
     @StartDate:开始日期
     @EndDate:结束日期
     @OrderCode:订单号
     @VipCode:会员编号
返回相应的订单列表
参数中:部门,单号,会员三者互斥,优先级:单号>部门>会员
       日期有默认值
*/
If Object_id('[GetOrdersList]','P') Is Not Null 
  Drop Procedure [GetOrdersList]
Go
Create Procedure [GetOrdersList]
       @Department varchar(10)             ,--部门编号
       @StartDate  varchar(10)='1900-01-01',--日期范围:开始日期
       @EndDate    varchar(10)=''          ,--日期范围:结束日期
       @OrderCode  varchar(20)=''          ,--订单号
       @VipCode    varchar(20)=''          ,--会员编号
       @authcode   varchar(50)=''           --寻找此会员的下级会员相关订单,仅在@VipCode=''时有效
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'GetOrdersList'
  Set @ParaInfo = dbo.NameValue('C','@Department',@Department)   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@StartDate',@StartDate)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@EndDate@EndDate',@EndDate) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@OrderCode',@OrderCode)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@VipCode',@VipCode)         Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@authcode',@authcode)       Exec Loger @LogID,@Parameters=@ParaInfo
  
  If IsNull(@StartDate,'')=''
    Begin
      Set @StartDate='1900-01-01'
      Set @ParaInfo = '设置@StartDate="' + @StartDate + '"'
      Exec Loger @LogID,@ExtendInfo=@ParaInfo
    End
  If IsNull(@EndDate,'')=''
    Begin
      Set @EndDate=convert(varchar(10),getdate(),120)
      Set @ParaInfo = '设置@EndDate="' + @EndDate + '"'
      Exec Loger @LogID,@ExtendInfo=@ParaInfo
    End
  If @OrderCode<>''
    Begin--查询指定单号的订单,不受日期限制
      Exec Loger @LogID,@ExtendInfo='查询指定单号的订单,不受日期限制'
      Select * ,0 As Hierarchy From orders 
      Where 单号=@OrderCode
    End
  Else If @Department Is Null
    Begin--查询公众号产生的订单
      Exec Loger @LogID,@ExtendInfo='查询公众号产生的订单'
      Select * ,0 As Hierarchy From orders 
      Where 销售类别='公众号' And 日期 Between @StartDate And @EndDate
    End
  Else If @Department <> ''
    Begin--查询指定部门制单的订单
      Exec Loger @LogID,@ExtendInfo='查询指定部门制单的订单'
      Select * ,0 As Hierarchy From orders 
      Where 部门编号=@Department And 日期 Between @StartDate And @EndDate
    End
  Else If @VipCode<>''
    Begin--查询指定会员的订单
      Exec Loger @LogID,@ExtendInfo='查询指定会员的订单'
      Select * ,0 As Hierarchy From orders
      Where 日期 Between @StartDate And @EndDate And 往来编号=@VipCode
      Order By 建立日期
    End
  Else If @authcode<>''
    Begin--查询指定会员的下级会员分销订单
      Exec Loger @LogID,@ExtendInfo='查询指定会员的下级会员分销订单'
      --将需要进行过滤的会员生成到表变量@VipCodes中
      Declare @VipCodes table(VipCode varchar(20),[Level] int)
      Declare @tmp Table(id int,[level] int)--这是两层分销的web_user_id
      --第一层分销者
      Insert Into @tmp
             Select invited_id,1 
             From web_user_invited_link 
             Where [user_id] In (Select [user_id] 
                                 From web_oauth_login 
                                 Where oauth_id=@authcode)
      --第二层分销者
      Insert Into @tmp 
             Select invited_id,2 
             From web_user_invited_link 
             Where [user_id] In (Select id From @tmp)
      --得到对应的会员编号
      Insert Into @VipCodes 
             Select d.编号,a.[level]
             From      @tmp                As a
             Left Join dbo.web_oauth_login As b On A.id      =b.[user_id]
             Left Join 会员身份            As c On b.oauth_id=c.外键
             Left Join 会员                As d On c.会员ID  =d.ID
      
      Select a.* ,b.[Level] As Hierarchy From orders As A
      Inner Join @VipCodes As b On a.往来编号=b.VipCode
      Where 日期 Between @StartDate And @EndDate 
        And 单号=@OrderCode 
    End
  Else
    Begin--没有结定限制条件
      Exec Loger @LogID,@ExtendInfo='没有结定限制条件,返回空集'
      Select Top 0 *,0 As Hierarchy From orders
    End
  If @@Error<>0
    Exec Loger @LogID,@ExtendInfo='错误',@Result=-1
    Return -1
  Return 0
End
Go
/*******************************************************************************
Procedure GetOrdersDetail
功能:获取订单明细
参数:
     @OrderID:订单ID
*/
If Object_id('[GetOrdersDetail]','P') Is Not Null
  Drop Procedure [GetOrdersDetail]
Go
Create Procedure [GetOrdersDetail]
       @OrderID int
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'GetOrdersDetail'
  Set @ParaInfo = dbo.NameValue('N','@OrderID',@OrderID) Exec Loger @LogID,@Key=@OrderID,@Parameters=@ParaInfo
  Select * From orders_detail Where 订单ID=@OrderID
  Return 0
End
Go
/*******************************************************************************
Procedure RecoverVoucher
功能:礼券回收
参数:
     @Department:部门
     @Source:来源,0为零售,1为订单
     @RecordID:单据ID:相应零售单/订单的主记录ID
     @VoucherCode:礼券编号
     @Amount:金额:抵扣金额
     @Remark:备注
返回值:
       0:成功
       -1:券编号无效
       -2:券未发放
       -3:券已回收
       -4:券已过期
       -5:写回收记录失败
*/
If Object_id('[RecoverVoucher]','P') Is Not Null
  Drop Procedure [RecoverVoucher]
Go
Create Procedure [RecoverVoucher]
       @Department  varchar(10),--部门编号
       @Source      int        ,--来源,0为零售,1为订单
       @RecordID    int        ,--单据ID:相应零售单/订单的主记录ID
       @VoucherCode varchar(20),--礼券编号
       @Amount      money      =0,--金额:抵扣金额
       @Remark      varchar(50)='' --备注
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'RecoverVoucher'
  Set @ParaInfo = dbo.NameValue('C','@Department',@Department)   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('N','@Source',@Source)           Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('N','@RecordID',@RecordID)       Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@VoucherCode',@VoucherCode) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@Amount',@Amount)           Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@Remark',@Remark)           Exec Loger @LogID,@Parameters=@ParaInfo
  
  --检查;礼券状态=2,是否回收=0,截止日期<getdate(),@Amount<=面额
  Declare @Return int,@msg varchar(100)
  Declare @状态 int,@是否回收 bit,@截止日期 datetime,@面额 money
  Select @状态=状态,@是否回收=是否回收,@截止日期=截止日期,@面额=面额 From 礼券表 Where 编号=@VoucherCode
  If @@RowCount < 1
    Begin
      Exec Loger @LogID,@ExtendInfo='券编号无效',@Result=-1
      Return -1
    End
  If @状态 <> 2 
    Begin
      Exec Loger @LogID,@ExtendInfo='不是已发放的券',@Result=-2
      Return -2
    End
  If @是否回收 = 1
    Begin
      Exec Loger @LogID,@ExtendInfo='券已回收',@Result=-3
      Return -3
    End
  If @截止日期 < GetDate()
    Begin
      Exec Loger @LogID,@ExtendInfo='券已过有效期',@Result=-4
      Return -4
    End
  If @Amount=0 Set @Amount=@面额
  Insert Into 礼券回收表(部门编号,来源,PID,编号,面额,金额,备注)
         Values(@Department,@Source,@RecordID,@VoucherCode,@面额,@Amount,@Remark)
  If @@Error<>0
    Begin
      Exec Loger @LogID,@ExtendInfo='生成[礼券回收表]记录失败',@Result=-5
      Return -5
    End
  Return 0
End
Go
/*******************************************************************************
Procedure QueryFormMObject
功能:执行mobile_object中的查询
参数:@QueryName是查询名,对应name字段
返回值:成功返回0,失败返回-1
*/
If Object_id('[QueryFormMObject]','P') Is Not Null
  Drop Procedure [QueryFormMObject]
Go
Create Procedure [QueryFormMObject]
       @QueryName varchar(50)
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'QueryFormMObject'
  Set @ParaInfo = dbo.NameValue('C','@QueryName',@QueryName) Exec Loger @LogID,@Parameters=@ParaInfo
  Declare @SQL varchar(8000)
  Select @SQL=command From mobile_object Where name=@QueryName
  If IsNull(@SQL,'')=''
    Begin
      Exec Loger @LogID,@Key=@QueryName,@ExtendInfo='无脚本内容',@Result=-1
      Return -1
    End
  Exec (@SQL)
  Return 0
End
Go
/*******************************************************************************
Procedure Get_BossTotal
为老板助手返回统计数据
用法
   exec Get_BossTotal [部门],[老板],[日期]
  部门可为空或null,或部门号，老板 可为空或null,或老板号，日期可为空，或日期
  例如 
  --exec  Get_BossTotal            --全部汇总        不带参数则汇总全部
  --exec  Get_BossTotal  '001'     --单店汇总        第一个参数对应 表 web_napa_stores  中hs_code 字段
  --exec  Get_BossTotal  '',1       --指定老板店汇总  第二个参数对应 表 web_napa_stores  中ID 字段
  --exec  Get_BossTotal  '','',‘2017-06-09’ --第三个参数不指定，当前日期为今天，也可指定某个日期为今天，例如昨天为今天
*/
If Object_id('[Get_BossTotal]','P') Is Not Null 
  Drop Procedure [Get_BossTotal]
Go
Create Procedure [Get_BossTotal]
       @Department varchar(10)=null,--选择店铺 参数对应 表 web_napa_stores  中hs_code 字
       @Boss       int        =null,--选择老板 参数对应 表 web_napa_stores  中ID 字段
       @InDate     datetime   =null--今天所指日期，不指定系统默认当前日期
AS
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'Get_BossTotal'
  Set @ParaInfo = dbo.NameValue('C','@Department',@Department) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('N','@Boss',@Boss)             Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('D','@InDate',@InDate)         Exec Loger @LogID,@Parameters=@ParaInfo

  --日期
  Declare @curDate Table(Date_C varchar(10),Name varchar(10),KeyAdd int)
  Declare  @date datetime,@date0 varchar(10),@date1 varchar(10)
  If @InDate Is Null
    Set @date=Convert(varchar(10),GetDate(),120)
  Else
    Set @date=Convert(varchar(10),@InDate,120)
  Set @date0= Convert(varchar(10),@date,120)   --今日
  Set @date1= Convert(varchar(10),@date-1,120)  --昨日
  Insert Into @curDate Values(@date0, '今日', 10)
  Insert Into @curDate Values(@date1, '昨日', 0)
  --用户
  Declare @WscUser varchar(10)
  Select @WscUser=编号 from pub_user where 姓名 ='公众号'
  --部门范围
  Declare @Depart table(Department varchar(10))
  If IsNull(@Department,'')<>''
    Begin
      Exec Loger @LogID,@ExtendInfo='针对指定店'
      Insert Into @Depart Values(@Department)--指定店
    End
  Else If IsNull(@Boss,0)<>0
    Begin
      Exec Loger @LogID,@ExtendInfo='针对指定老板'
      --Insert Into @Depart Select distinct hs_code from [web_napa_stores] where [user_id]=@Boss --指定老板
      Insert Into @Depart
      Select distinct hs_code
      From [web_napa_stores]
      Where store_id In(Select store_id
                        From web_napa_store_user_link
                        Where [user_id]=@Boss)
    End
  Else
    Begin
      Exec Loger @LogID,@ExtendInfo='针对全部店'
      Insert Into @Depart Select 编号 from department where 类别编号 in ('102','103')--全部店
    End
  --未排序的输出集
  Declare @curTmp table(Caption varchar(20),Value float,OrderKey int,vtype varchar(10))
  --通用临时集
  Declare @curSource Table(Date_Name varchar(10), KeyAdd Int
                          ,Value_Name_1 varchar(20),Value_1 float Null
                          ,Value_Name_2 varchar(20),Value_2 float Null)

  --订单相关:金额与单数
  Insert Into @curSource
         Select A.Name As Date_Name,KeyAdd,'订单金额',B.金额合计,'订单数',B.数量
         From @curDate As A
         Full Join(Select 日期
                         ,Sum(金额合计) As 金额合计
                         ,Count(*) As 数量
                   From orders
                   Where 制单人=@WscUser ----仅取线上数据
                     And 日期 In (Select Date_C From @curDate)
                     And 部门编号 In (Select * From @Depart)
                   Group By 日期
                   ) As B On A.Date_C=B.日期
  Insert Into @curTmp Select Date_Name+Value_Name_1,IsNull(Value_1,0),KeyAdd + 1,'money' From @curSource
  Insert Into @curTmp Select Date_Name+Value_Name_2,IsNull(Value_2,0),KeyAdd + 2,'integer' From @curSource
  Delete From @curSource

  --充值相关:充值金额
  Insert Into @curSource
         Select A.Name As Date_Name,KeyAdd,'充值金额',B.金额合计,Null,Null
         From @curDate As A
         Full Join(Select 日期
                         ,Sum(金额合计) As 金额合计
                   From 充值表
                   Where 制单人=@WscUser ----仅取线上数据
                     And 日期 In (Select Date_C From @curDate)
                     And 部门编号 In (Select * From @Depart)
                   Group By 日期
                   ) As B On A.Date_C=B.日期
  Insert Into @curTmp Select Date_Name+Value_Name_1,IsNull(Value_1,0),KeyAdd + 3,'money' From @curSource
  Delete From @curSource

  --会员相关:新増绑定数
  Insert Into @curSource
         Select A.Name As Date_Name,KeyAdd,'新増会员',B.新増会员,Null,Null
         From @curDate As A
         Full Join(
                   Select Convert(varchar(10),建立时间,120)As 日期,Count(*) As 新増会员
                   From 会员身份
                   Where 验证方式='WeiXinOpenid'
                     And Convert(varchar(10),建立时间,120) In (Select Date_C From @curDate)
                   Group By Convert(varchar(10),建立时间,120)
                   ) As B On A.Date_C=B.日期
  Insert Into @curTmp Select Date_Name+Value_Name_1,IsNull(Value_1,0),KeyAdd + 21,'integer' From @curSource
  Delete From @curSource

  --服务相关:评价星级/差评数
  Declare @Value float
  Select @Value = Sum(deliver+service+quality) From web_comment Where Convert(varchar(10),time,120) = @date0
  Insert Into @curTmp Values('今日评价平均星级数', IsNull(@Value,0)/3, 22,'money')
  Select @Value=Count(*) From web_comment Where Convert(varchar(10),time,120) = @date0 And deliver+service+quality <= 12
  Insert Into @curTmp Values('今日差评数', @Value, 23,'integer')


  --Select * From @curTmp Order By OrderKey
  Select Caption,Value As [Values],vtype as Vype, OrderKey As sort From @curTmp Order By OrderKey
End
Go
/*******************************************************************************
Procedure GetBirthday
取指定日期未来XX天内过生日的会员
引用 生日函数 obo.GetBirthday(生日,指定日期)
用法：
-- exec GetBirthday 20              取当下     未来10天内过生日的会员
-- exec GetBirthday 20,'2017-7-1'   取2017-7-1 未来20天内过生日的会员
*/
If Object_id('[GetBirthday]','P') Is Not Null
  Drop Procedure [GetBirthday]
Go
Create Procedure [GetBirthday]
       @Days    int     =null,
       @NowDate datetime=null
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'GetBirthday'
  Set @ParaInfo = dbo.NameValue('N','@Days',@Days)       Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('D','@NowDate',@NowDate) Exec Loger @LogID,@Parameters=@ParaInfo
  If @NowDate Is Null  Set @NowDate=getdate()
  If @Days Is Null     Set @days=0
  Select *
  From 会员
  Where 状态=1
    And 是否充值=0
    And 是否购物券=0
    And 截止日期>= @NowDate
    And dbo.Fun_Birthday(生日,@NowDate) Between @NowDate And @NowDate + @Days
End
Go
/*******************************************************************************
Procedure AddWebPay
在结算明细表中添加从微商城中进行支付的记录(除会员卡支付外)
  有相应的支付就添加,没有相应的支付则不处理
*/
If Object_id('[AddWebPay]','P') Is Not Null
  Drop Procedure [AddWebPay]
Go
--返回值
--  0:成功添加
--  -1:已有记录,不再添加
--  -2:已有记录,不再添加
--  其它:错误号
Create Procedure [AddWebPay]
       @BusinessType int,---业务类型:0台帐,1订单,3充值
       @BusinessID int,   ---相应业务主表的ID
       @PaySerailNum varchar(50)=''
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'AddWebPay'
  Set @ParaInfo = dbo.NameValue('N','@BusinessType',@BusinessType) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('N','@BusinessID',@BusinessID)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@PaySerailNum',@PaySerailNum) Exec Loger @LogID,@Parameters=@ParaInfo

  Declare @RetCode int
  Set @BusinessType = IsNull(@BusinessType, 0)
  Declare @Department varchar(10),@CDate varchar(10)
  If Exists(Select * 
            From 结算明细表 
            Where 来源=@BusinessType 
              And 业务ID=@BusinessID 
              And 结算ID In (Select ID 
                             From 结算方式表 
                             Where 名称 Like 'WSC_%'))
  
    Set @RetCode = -1
  Else If @BusinessType = 0
  Begin
    --这是零售业务
    Set @RetCode = @@ERROR
  End
  Else If @BusinessType = 1
  Begin
    Exec Loger @LogID,@ExtendInfo='这是订单业务'
    Insert Into 结算明细表(来源,业务ID,结算ID,结算金额,备注,部门编号,日期,建立时间,第三方支付,是否启用)
    Select @BusinessType, A.ID  , E.ID           , C.money, C.transaction_id
          ,A.部门编号   , A.日期, C.complete_time, 1      , 0
    From orders As A
    Inner Join web_orders As B On A.商户订单号=B.order_serial_num
    Inner Join web_payment_orders AS C On B.payment_order_id=C.payment_order_id
    Inner Join web_payments As D On C.payment_id=D.payment_id
    Inner Join 结算方式表 As E On E.名称='WSC_'+D.payment_name
    Where A.ID=@BusinessID
    Set @RetCode = @@ERROR
  End
  Else If @BusinessType = 3
  Begin
    Exec Loger @LogID,@ExtendInfo='这是充值业务'
    If Not Exists(Select * From web_payment_orders Where payment_serial_num = @PaySerailNum)
    Begin
      Exec Loger @LogID,@ExtendInfo='无微商城交易号'
      Set @RetCode = -2
    End
    Else
    Begin
      Select @Department=部门编号, @CDate=日期 From 充值表 Where ID=@BusinessID
      Insert Into 结算明细表(来源    ,业务ID,结算ID  ,结算金额  ,备注
                           ,部门编号,日期  ,建立时间,第三方支付,是否启用)
      Select @BusinessType, @BusinessID, E.ID           , C.money, C.transaction_id
           , @Department  , @CDate     , C.complete_time, 1      , 0
      From web_payment_orders AS C
      Inner Join web_payments As D On C.payment_id=D.payment_id
      Inner Join 结算方式表 As E On E.名称='WSC_'+D.payment_name
      Where C.payment_serial_num = @PaySerailNum
      Set @RetCode = @@ERROR
    End
  End
  Exec Loger @LogID,@Key=@BusinessID,@Result=@RetCode
End
Go
/*******************************************************************************
Function Fun_Birthday
未来生日函数
    小于 指定日期的生日为下一年生日
    大于 指定日期的为当年生日
用法：
   obo.Fun_Birthday(生日,指定日期)    
*/
If Object_id('dbo.Fun_Birthday','FN') Is Not Null
  Drop Function Fun_Birthday
GO
Create Function Fun_Birthday
      (@Birthday varchar(10) ,--生日
       @NowDate  datetime     --指定日期
       )
Returns varchar(10)
As 
Begin
  Declare @Birthday_t datetime
  Set @NowDate =  convert(varchar(10), @NowDate, 120) 

  If  @NowDate>dateadd(year , year(@NowDate) - year(@Birthday), @Birthday)  
    Set @Birthday_t=dateadd(year , year(@NowDate) - year(@Birthday) + 1, @Birthday)
  Else  
    Set @Birthday_t=dateadd(year , year(@NowDate) - year(@Birthday),   @Birthday)
    
  Return convert(varchar,@Birthday_t,120)
End
Go
/*******************************************************************************
Function fn_GetPickUpCode
计算提货码
  根据传入的一串数字计算相应的36进制串
  传入的数字串长度不超过19位(不大于9223372036854775807)
  返回的串是一个长度不超过13位的字符串
示例
  Select dbo.fn_GetPickUpCode('9223372036854775807')
  Select dbo.fn_GetPickUpCode('922337203685477580')
  Select order_serial_num,dbo.fn_GetPickUpCode(web_orders.order_serial_num) From web_orders
*/
If Object_id('[fn_GetPickUpCode]','FN') Is Not Null
  Drop Function [fn_GetPickUpCode]
Go
Create Function [fn_GetPickUpCode]
      (@OrderSerialNum varchar(20))
Returns varchar(20)
As
Begin
  If Len(@OrderSerialNum) > 19 Or @OrderSerialNum > '9223372036854775807'
    Return 'NumberTooLong'
  Declare @Mod bigint, @BaseCode varchar(36)
  Declare @BigVal bigint, @SubVal bigint
  Declare @PickUpCode varchar(50)
  Set @BaseCode   = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
  Set @Mod        = Len(@BaseCode)
  Set @PickUpCode = ''
  Set @BigVal     = Convert(bigint, @OrderSerialNum)
  While @BigVal > 0
  Begin
    Set @SubVal     = @BigVal % @Mod
    Set @PickUpCode = SubString(@BaseCode, @SubVal + 1, 1) + @PickUpCode
    Set @BigVal     = (@BigVal - @SubVal) / @Mod
  End
  Return 'SN' + @PickUpCode
End
Go
/*******************************************************************************
Function fn_UnPickUpCode
从提货码计算相应的网上商城订单号(web_orders.order_serial_num或orders.商户订单号)
  这是函数【fn_GetPickUpCode】的逆运算
  因此:dbo.fn_UnPickUpCode(dbo.fn_GetPickUpCode(a))=a
它用于线下系统扫到提货码后计算相应的商户订单号以定位订单
*/
If Object_id('[fn_UnPickUpCode]','FN') Is Not Null
  Drop Function [fn_UnPickUpCode]
Go
Create Function [fn_UnPickUpCode]
      (@PickUpCode varchar(20))
Returns varchar(20)
As
Begin
  Declare @BaseCode varchar(36)
  Declare @Mut bigint, @BigVal bigint
  Declare @Index int, @At int
  Set @BaseCode = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
  Set @Mut      = Len(@BaseCode)
  Set @BigVal   = 0
  Set @Index = 2
  While @Index < Len(@PickUpCode)
  Begin
    Set @Index = @Index + 1
    Set @At = CharIndex(SubString(@PickUpCode, @Index, 1), @BaseCode)
    If @At > 0
      Set @BigVal = @BigVal * @Mut + @At - 1
  End
  Return Convert(varchar(20), @BigVal)
End
Go
/*******************************************************************************
Procedure ChangeVipLinks
将一个会员的绑定信息修改为指向另一个会员
*如果不指定目标会员ID或目标会员ID无效则为清除源会员绑定
Exec ChangeVipLinks 1,2
Exec ChangeVipLinks 1
*/
If Object_id('[ChangeVipLinks]','P') Is Not Null
  Drop Procedure [ChangeVipLinks]
Go
Create Procedure [ChangeVipLinks]
       @SourceID int,  ---源会员ID
       @DestID   int=0 ---目标会员ID
As
Begin
  Set @DestID = IsNull(@DestID,0)
  If Not Exists(Select * From 会员 Where ID = @DestID) 
    Set @DestID = 0
  If (@DestID = 0)
  Begin
    --删除绑定
    Delete From web_user_profiles 
           Where [user_id] in (Select [user_id] 
                               From web_oauth_login 
                               Where oauth_id In(Select 外键 
                                                 From 会员身份 
                                                 Where 会员ID=@SourceID 
                                                   And 验证方式 = 'WeiXinOpenid'))
    Delete From web_users
           Where [user_id] in (Select [user_id] 
                               From web_oauth_login 
                               Where oauth_id In(Select 外键 
                                                 From 会员身份 
                                                 Where 会员ID=@SourceID 
                                                   And 验证方式 = 'WeiXinOpenid'))
    Delete From web_oauth_login
           Where oauth_id In(Select 外键 
                             From 会员身份 
                             Where 会员ID=@SourceID 
                               And 验证方式 = 'WeiXinOpenid')
    Delete From 会员身份 
           Where 会员ID=@SourceID 
             And 验证方式 = 'WeiXinOpenid'
  End
  Begin
    --重绑定
    Update web_user_profiles
           Set vip_image='',vip_jbarcode=''
           Where [user_id] in (Select [user_id] 
                               From web_oauth_login 
                               Where oauth_id In(Select 外键 
                                                 From 会员身份 
                                                 Where 会员ID=@SourceID 
                                                   And 验证方式 = 'WeiXinOpenid'))
    Update 会员身份
           Set 会员ID=@DestID
           Where 会员ID=@SourceID
  End
End
Go
/*******************************************************************************
为微商城系统提供的接口封装
  主要功能为一些参数的自动生成,以及返回值转化为结果集
*******************************************************************************/
--------------------------------------------------------------------------------
--取会员信息
--  @cWeiXinCode外键
--用法：
--exec WSC_GetVipInfo 'Y6GagnXEeygErTHh9-Rx3'  取外键会员信息
--exec WSC_GetVipInfo '',10                    取当下日期未来10天内过生日的会员（含当日） 
--exec WSC_GetVipInfo '',20,'2017-07-01'       取2017-07-01未来20天内过生日的会员（含当日）
If Object_id('[WSC_GetVipInfo]','P') Is Not Null
  Drop Procedure [WSC_GetVipInfo]
Go
Create Procedure [WSC_GetVipInfo]
       @cWeiXinCode varchar(50) = null,
       @DayCount int=null,
       @NowDate datetime =null
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'WSC_GetVipInfo'
  Set @ParaInfo = dbo.NameValue('C','@cWeiXinCode',@cWeiXinCode) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('N','@DayCount',@DayCount)       Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('D','@NowDate',@NowDate)         Exec Loger @LogID,@Parameters=@ParaInfo

  If Object_id('tempdb..#tmp_WSC_GetVipInfo') Is Not Null Drop Table #tmp_WSC_GetVipInfo
  Create Table #tmp_WSC_GetVipInfo
        (ID int,VipCode varchar(20),Name varchar(20),Birthday datetime,IsLunar bit,CallNumber varchar(20)
        ,BonusPoints money,DiscountRate money
        ,LastTime datetime,LastDepartment varchar(20)
        ,State int,EndTime datetime,Disable bit
        ,AmountOfMoney money,Balance money,CreateTime datetime,CardCode varchar(20)
        ,PersonalID varchar(20),WorkSpace varchar(50)
        ,Assessor varchar(20),Bookkeeper varchar(20),memo varchar(50)
        ,BuyInAmount money ,GiveInAmount money
        ,PrevCard_Balance money,PrevCard_BonusPoints money,PrevCard_Date varchar(10),PrevCard_VipCode varchar(20)
        ,CanBonusPoints bit,Birthday_C varchar(5),RelationID varchar(20)
        ,VipGroup varchar(10),IsVoucher bit,Department varchar(20),VipType int
        ,BonusPointsRatio money,PayQuota money,VipLevelType int
        ,Outer_ID int,Outer_Code varchar(30),FirstTime datetime)
  If @DayCount Is Null
    Insert Into #tmp_WSC_GetVipInfo Exec GetVipInfo @cWeiXinCode
  Else
    Insert Into #tmp_WSC_GetVipInfo Exec GetBirthday @DayCount,@NowDate

  Select * From #tmp_WSC_GetVipInfo  --以此输出,可以在此排除不需要的字段
  Drop Table #tmp_WSC_GetVipInfo
End
Go
--exec WSC_GetVipInfo 'Y6GagnXEeygErTHh9-Rx3'
--exec WSC_GetVipInfo '',10,'2017-07-01'

--------------------------------------------------------------------------------
--添加会员
--  @cWeiXinCode外键
--  @cMobileNumber手机号
--  @cName姓名,可选
--  @cBirthday生日,可选
--  @bIsLuna,是农历,可选
--WSC_AddVip状态集:
-- 0   :成功
-- -1  :已绑定到其它会员
-- -2  :无此会员
-- -3  :会员已停用
-- -201:手机号重复(有多张卡)
If Object_id('[WSC_AddVip]','P') Is Not Null
  Drop Procedure [WSC_AddVip]
Go
Create Procedure [WSC_AddVip]
       @cWeiXinCode varchar(50)
      ,@cMobileNumber varchar(20)--手机号
      ,@cName varchar(20)        = ''--姓名
      ,@cBirthday varchar(10)    = '1900-01-01'--生日,yyyy-mm-dd格式,范围1900-01-01到2079-06-06
      ,@bIsLunar bit             = 0--是农历,0表示生日不是农历(是公历)
      ,@cVipCode varchar(20)     = ''--此参数停用
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'WSC_AddVip'
  Set @ParaInfo = dbo.NameValue('C','@cWeiXinCode',@cWeiXinCode)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@cMobileNumber',@cMobileNumber) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@cName',@cName)                 Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@cBirthday',@cBirthday)         Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('L','@bIsLunar',@bIsLunar)           Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@cVipCode',@cVipCode)           Exec Loger @LogID,@Parameters=@ParaInfo

  Set @cVipCode = null --该参数的值已经丢弃,本函数使用手机号来定位线下身份
  Declare @Return int, @msg varchar(100)
  --定位下线会员身份
  Declare @Tally int
  
  Select @cVipCode=编号 From 会员 Where 电话=@cMobileNumber
  Set @Tally = @@RowCount
  If @Tally < 1 And IsNull(@cName,'')='' And IsNull(@cBirthday,'')=''
  Begin 
    Set @Return = -2
    Set @msg = '无此会员'
  End
  Else If @Tally < 2
  Begin
    If @Tally = 0 Set @cVipCode = ''
    Exec @Return=AddVip @cWeiXinCode,@cMobileNumber,@cName,@cBirthday,@bIsLunar,@cVipCode
    Set @msg = Case @Return 
               When  0 Then '成功'
               When -1 Then '外键''' + @cWeiXinCode + '''已经与其它线下会员卡绑定'
               When -2 Then '无此会员'
               When -3 Then '会员卡已停用'
               When -4 Then '会员卡已挂失'
               Else '未知错误'
               End
  End
  Else
  Begin
    Set @Return = -201
    Set @msg = '手机号''' + @cMobileNumber + '''重复注册到多个会员,不能绑定.'
  End
  
  Exec Loger @LogID,@ExtendInfo=@msg,@Result=@Return
  Select @Return As retcode,@msg As Msg
End
Go
--exec WSC_AddVip 'AKnexiDEEH1vrWSl9-wa','12345678901','asdf'
--------------------------------------------------------------------------------
--会员充值
--  @cWeiXinCode会员外键
--  @nAddMoney充值金额
--  @cWeiXinOrderCode微商城支付订单号
If Object_id('[WSC_VipReCharge]','P') Is Not Null
  Drop Procedure [WSC_VipReCharge]
Go
Create Procedure [WSC_VipReCharge]
       @cWeiXinCode varchar(50),     --会员身份外键
       @nAddMoney money=0,           --充值金额
       @cWeiXinOrderCode varchar(50) --微商城支付订单号
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'WSC_VipReCharge'
  Set @ParaInfo = dbo.NameValue('C','@cWeiXinCode',@cWeiXinCode)           Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@nAddMoney',@nAddMoney)               Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@cWeiXinOrderCode',@cWeiXinOrderCode) Exec Loger @LogID,@Parameters=@ParaInfo

  Declare @cDepart varchar(10) --部门编号(微商城,无可登录人员)
  Declare @cUser varchar(10)   --制单人(微商城操作员,不可登录)
  Declare @iPayMode int        --结算方式(微商城,在结算方式表中为第三方支付,不启用)
  Declare @VipCode varchar(50) --会员编号（可以是更复杂的匹配串)
  Set @cDepart  = ''
  Set @cUser    = ''
  Select @cDepart  = 编号 From department Where 简称='公众号'
  Select @cUser    = 编号 From pub_user Where 姓名='公众号'
  Select @iPayMode = id   From 结算方式表 Where 名称='公众号'
  Select @VipCode  = 编号 From 会员 As a Inner Join 会员身份 As b On A.id=b.会员id Where b.外键=@cWeiXinCode
  Declare @Return int,@msg varchar(100)
  If IsNull(@VipCode,'') <> ''
    Exec @Return = VipReCharge @cDepart,@cUser,@iPayMode,@VipCode,@nAddMoney,@cWeiXinOrderCode
  Else
    Set @Return = -1
  Set @msg = Case @Return
             When  0 Then '成功'
             When -1 Then '无此会员'
             When -2 Then '生成订单号失败'
             When -3 Then '生成充值记录失败'
             When -4 Then '卡未启用'
             When -5 Then '是购物券'
             When -6 Then '卡已挂失'
             When -7 Then '卡不可充值'
             Else '未知错误'
             End
  Exec Loger @LogID,@ExtendInfo=@msg,@Result=@Return
  Select @Return As retcode,@msg As Msg
End
Go
--------------------------------------------------------------------------------
--获取指定会员的消费记录
If Object_id('[WSC_GetVipLog]','P') Is Not Null
  Drop Procedure [WSC_GetVipLog]
Go
Create Procedure [WSC_GetVipLog]
       @cWeiXinCode varchar(50),--会员绑定的外键
       @cDateStart varchar(10)='1900-01-01', --查询开始日期(含此日)
       @cDateEnd varchar(10)=''  --查询截止日期(含此日)
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'WSC_GetVipLog'
  Set @ParaInfo = dbo.NameValue('C','@cWeiXinCode',@cWeiXinCode) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@cDateStart',@cDateStart)   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@cDateEnd',@cDateEnd)       Exec Loger @LogID,@Parameters=@ParaInfo
  Exec GetVipLog @cWeiXinCode,@cDateStart,@cDateEnd
End
Go
--exec WSC_GetVipLog '4YSju0DE3B2jrQTY99Hz'
--------------------------------------------------------------------------------
--得到微商城中可以发放的电子券列表/指定编号的礼券
--参数
--  @WeiXinCode:获取指定可使用的券(已销售,未使用,有效期内)
--  @VoucherCode:获取指定券的信息,不限状态与销售部门
--  @GoodsCode:获取指定产品编号的券,(出库到本部门,未销售)
--  指定了@WeiXinCode则忽略@VoucherCode
--  都不指定则返回网上商城可以销售/赠送的券
If Object_id('[WSC_GetVouchers]','P') Is Not Null
  Drop Procedure [WSC_GetVouchers]
Go
Create Procedure [WSC_GetVouchers]
       @WeiXinCode  varchar(50)='',--会员外键
       @VoucherCode varchar(20)='',--券编号
       @GoodsCode   varchar(10)='',--券的产品编号
       @VouchersCode1 varchar(20)='',--券的起码
       @VouchersCode2 varchar(20)='' --券的止码
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'WSC_GetVouchers'
  Set @ParaInfo = dbo.NameValue('C','@WeiXinCode',@WeiXinCode)   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@VoucherCode',@VoucherCode) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@GoodsCode',@GoodsCode)     Exec Loger @LogID,@Parameters=@ParaInfo
  If Object_id('tempdb..#tmp_WSC_GetVouchers')Is Not Null
    Drop Table #tmp_WSC_GetVouchers
  Create Table #tmp_WSC_GetVouchers
        (GoodsCode varchar(10)
        ,VouchersCode varchar(20),PayQuota money,State varchar(10)
        ,VipCode varchar(20),EndTime datetime
        ,BarCode varchar(30))
  --查找相应的会员编号
  Declare @cDepart varchar(20)
  If IsNull(@WeiXinCode, '') <> ''
    Begin
      Exec Loger @LogID,@ExtendInfo='查找指定会员持有的券'
      Declare @VipCode varchar(20)
      Select @VipCode=编号 
      From 会员 As a
      Inner Join 会员身份 As b On a.id=b.会员id
      Where b.外键=@WeiXinCode
      Insert Into #tmp_WSC_GetVouchers Exec GetVouchers '', @VipCode
    End
  Else If IsNull(@VoucherCode, '') <> ''
    Begin
      Exec Loger @LogID,@ExtendInfo='查找指定编号的券'
      Insert Into #tmp_WSC_GetVouchers Exec GetVouchers '', '', @VoucherCode
    End
  Else If IsNull(@GoodsCode, '') <> ''
    Begin
      Exec Loger @LogID,@ExtendInfo='查找微商城可以发放的，指定商品编号的券'
      Select @cDepart = 编号 From department Where 简称='公众号'
      Insert Into #tmp_WSC_GetVouchers Exec GetVouchers @cDepart,'','',@GoodsCode
    End
  Else
    Begin
      Exec Loger @LogID,@ExtendInfo='查找微商城可以发放的券'
      Select @cDepart = 编号 From department Where 简称='公众号'
      Insert Into #tmp_WSC_GetVouchers Exec GetVouchers @cDepart
    End
  If IsNull(@VouchersCode1, '') <> '' And IsNull(@VouchersCode2, '') <> ''
    Select * From #tmp_WSC_GetVouchers Where VouchersCode Between @VouchersCode1 And @VouchersCode2
  Else
    Select * From #tmp_WSC_GetVouchers
  Drop Table #tmp_WSC_GetVouchers 
End
Go
--exec WSC_GetVouchers --商城所有可以销售的券
--exec WSC_GetVouchers '{测试绑定码2}'--该会员持有但未使用的券
--exec WSC_GetVouchers '','21009'--指定编号的券
--exec WSC_GetVouchers '','','83001'--商城可销售的指定产品编号的券
--exec WSC_GetVouchers '{测试绑定码2}','21009' --等价WSC_GetVouchers '','21009'
--------------------------------------------------------------------------------
--电子券销售
--  @GoodsCode,@VoucherCode,@WeiXinCode,@Price
If Object_id('[WSC_SaleVoucher]','P') Is Not Null
  Drop Procedure [WSC_SaleVoucher]
Go
Create Procedure [WSC_SaleVoucher]
       @GoodsCode varchar(10)  ,--礼券产品编号
       @VoucherCode varchar(20),--礼券编号
       @WeiXinCode varchar(50) ,--会员外键
       @PayByCard money=0      ,--会员卡支付金额,赠送则为0
       @Price money = 0         --实际售价,赠送时为0
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'WSC_SaleVoucher'
  Set @ParaInfo = dbo.NameValue('C','@GoodsCode',@GoodsCode)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@VoucherCode',@VoucherCode) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@WeiXinCode',@WeiXinCode)   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@PayByCard',@PayByCard)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@Price',@Price)             Exec Loger @LogID,@Parameters=@ParaInfo

  Declare @Return int,@msg varchar(100),@SalesCode varchar(20)
  Declare @Department varchar(10),@UserCode varchar(10),@VipCode varchar(20),@CDate varchar(10)
  Select @Department=编号 From department Where 简称='公众号'
  Select @UserCode=编号 From pub_user Where 姓名='公众号'
  Select @VipCode=编号 From 会员 Where id in(Select 会员ID From 会员身份 Where 外键=@WeiXinCode)
  Set @CDate=Convert(varchar(10),getdate(),120)
  If IsNull(@GoodsCode,'')=''
  Begin
    --取礼券面额
    Declare @y_面额 money
    Select @y_面额=面额 From 礼券表 Where 编号=@VoucherCode
    If @y_面额 Is Null 
    Begin
      Select -1 As retcode,'礼券编号无效' As Msg
      Exec Loger @LogID,@ExtendInfo='礼券编号无效',@Result=-1
      Return -1
    End
    --在Goods中寻找销售价格最接近的券
    Select Top 1 @GoodsCode=编号 From goods Where 存货属性='现金券' Order By Abs(11-销售主价)
    If @GoodsCode Is Null
    Begin
      Select -2 As retcode,'没有匹配到可以销售/赠送的礼券产品' As Msg
      Exec Loger @LogID,@ExtendInfo='没有匹配到可以销售/赠送的礼券产品',@Result=-2
      Return -2
    End
  End
  Exec Loger @LogID,@ExtendInfo='调用存储过程[SaleingVoucher]进行电子券销售'
  Exec @Return=SaleingVoucher @Department, @UserCode, @CDate
                            , @GoodsCode, @Price, 0, @PayByCard
                            , @VipCode, @VoucherCode, @SalesCode Output
  
  Set @msg = Case @Return 
             When 0    Then '成功'
             When -1   Then '生成销售单失败'
             When -100 Then '券编号无效'
             When -101 Then '券已过期'
             When -102 Then '券销售失败'
             When -103 Then '券未出库'
             When -105 Then '券已经销售'
             When -106 Then '券已使用'
             Else '未知错误' 
             End
  Exec Loger @LogID,@Key=@SalesCode,@ExtendInfo=@msg,@Result=@Return
  Select 0 As retcode,@msg As Msg,@SalesCode As SalesOrderNo
End
Go
--------------------------------------------------------------------------------
--建立订单
--@OrderCode传入''或null则为新建订单
--@OrderCode有内容则为修改订单,可提交新的@CallNumber/@PickUpTime/@Remarks/@Destination/@EndOrder/@VoidOrder
--返回结果集:
--  结果集:订单ID int,订单号 varchar(20)
If Object_id('[WSC_CreateOrder]','P') Is Not Null 
  Drop Procedure [WSC_CreateOrder]
Go
Create Procedure [WSC_CreateOrder]
       @OrderCode   varchar(20) =NULL OUTPUT,--为空则新建单,不为空则修改已有单
       @WSC_TardNo  varchar(32) =''   ,--外部转入的订单,对应的外部订单号
       @PickUpTime  datetime    =Null ,--预约取货时间
       @WeiXinCode  varchar(50) =''   ,--会员外键
       @CallNumber  varchar(50) =Null ,--联系方式
       @Remarks     varchar(100)=Null ,--顾客注明的内容
       @Destination varchar(100)=Null ,--送货地址非送货订单不填
       @TotalAmount money       =0    ,--未折扣时合计,金额合计
       @Discount    money       =0    ,--商品折扣金额,折后金额,-整单免除-会员卡支付-礼券支付=欠款
       @Deducted    money       =0    ,--整单免除金额
       @Payment     money       =0    ,--下单时用会员卡支付的金额
       @Voucher     money       =0    ,--下单时使用礼券支付的金额
       @EndOrder    bit         =Null ,--是否结单,欠款不为0时不可结单
       @VoidOrder   bit         =Null ,--如果废止,只可对结单=0的单废止
       @Department  varchar(20) =''   ,--订单下到哪个店
       @Vouchers    varchar(300)=''    --客户支付中使用的礼券的编号列表,使用逗号分隔
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'WSC_CreateOrder'
  Set @ParaInfo = dbo.NameValue('C','@OrderCode',@OrderCode)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@WSC_TardNo',@WSC_TardNo)   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('D','@PickUpTime',@PickUpTime)   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@WeiXinCode',@WeiXinCode)   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@CallNumber',@CallNumber)   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@Remarks',@Remarks)         Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@Destination',@Destination) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@TotalAmount',@TotalAmount) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@Discount',@Discount)       Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@Deducted',@Deducted)       Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@Payment',@Payment)         Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@Voucher',@Voucher)         Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('L','@EndOrder',@EndOrder)       Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('L','@VoidOrder',@VoidOrder)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@Department',@Department)   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@Vouchers',@Vouchers)       Exec Loger @LogID,@Parameters=@ParaInfo

  Declare @Return int,@msg varchar(100)
  Declare @OrderID int,@Department_Web varchar(10),@UserCode varchar(10),@VipCode varchar(20),@cDate varchar(10)
  Select @Department_Web='公众号'
  Select @UserCode=编号 From pub_user Where 姓名='公众号'
  Select @VipCode=编号 From 会员 Where id in(Select 会员ID From 会员身份 Where 外键=@WeiXinCode)
  --Set @cDate = Convert(varchar(10),getdate(),120)
  Select @cDate=Max(日期) From stock_end Where 部门编号=@Department and 状态=1
  If Exists(Select * From stock_end Where 部门编号=@Department and 状态=0 And 日期>@cDate)
    Select @cDate=Min(日期) From stock_end Where 部门编号=@Department and 状态=0 And 日期>@cDate
  Else
    Set @cDate = convert(varchar(10)
                        ,convert(datetime,@cDate)+1
                        ,120)
  --调用订单接口生成订单
  Exec @OrderID=CreateOrder @单号=@OrderCode Output
                           ,@部门编号=@Department
                           ,@制单人=@UserCode
                           ,@商户订单号=@WSC_TardNo
                           ,@日期=@cDate
                           ,@取货时间=@PickUpTime
                           ,@往来编号=@VipCode
                           ,@联系方式=@CallNumber
                           ,@备注=@Remarks
                           ,@送货地址=@Destination
                           ,@金额合计=@TotalAmount
                           ,@优惠合计=@Discount
                           ,@让利=@Deducted
                           ,@刷卡金额=@Payment
                           ,@礼券=@Voucher
                           ,@结单=@EndOrder
                           ,@废止=@VoidOrder
                           ,@销售类别=@Department_Web
                           ,@礼券列表=@Vouchers
  Exec Loger @LogID,@Key=@OrderCode,@ExtendInfo='调用存储过程[CreateOrder]建立订单',@Result=0
  Select @OrderID As OrderID,@OrderCode As OrderCode
EnD
Go
--------------------------------------------------------------------------------
--写入订单明细
If Object_id('[WSC_AddOrderItem]','P') Is Not Null
  Drop Procedure [WSC_AddOrderItem]
Go
Create Procedure [WSC_AddOrderItem]
       @PID         int        ,--订单ID
       @GoodsCode   varchar(10),--商品编号
       @GoodsCount  int        ,--商品数量
       @Price       money      ,--单价,未折扣的单价
       @TotalAmount money       --金额,折扣后总金额
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'WSC_AddOrderItem'
  Set @ParaInfo = dbo.NameValue('N','@PID',@PID)                 Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@GoodsCode',@GoodsCode)     Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('N','@GoodsCount',@GoodsCount)   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@Price',@Price)             Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@TotalAmount',@TotalAmount) Exec Loger @LogID,@Parameters=@ParaInfo

  Declare @Return int,@msg varchar(100)
  Exec @Return=AddOrderItem @PID,@GoodsCode,@GoodsCount,@Price,@TotalAmount,@Price
  Set @msg = Case @Return
             When 0 Then '成功'
             When -1 Then '失败'
             Else '未知错误'
             End
  Exec Loger @LogID,@ExtendInfo=@msg,@Result=@Return
  Select @Return As retcode, @msg As Msg
End
Go
--------------------------------------------------------------------------------
--获取订单列表
--  
If Object_id('[WSC_GetOrdersList]','P') Is Not Null 
  Drop Procedure [WSC_GetOrdersList]
Go
Create Procedure [WSC_GetOrdersList]
       @StartDate  varchar(10)='1900-01-01',--日期范围:开始日期
       @EndDate    varchar(10)=''          ,--日期范围:结束日期
       @OrderCode  varchar(20)=''          ,--订单号
       @VipCode    varchar(50)=''          ,--会员外键
       @oauth_code varchar(50)=''          ,--返回该会员下级产生的订单,层数在GetOrdersList中控制
                                            --不含该会员自己产生的订单
       @IsEnd      bit        =null        --指定此参数,则只返回结单状态与之一致的单
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'WSC_GetOrdersList'
  Set @ParaInfo = dbo.NameValue('C','@StartDate',@StartDate)   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@EndDate',@EndDate)       Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@OrderCode',@OrderCode)   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@VipCode',@VipCode)       Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@oauth_code',@oauth_code) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('L','@IsEnd',@IsEnd)           Exec Loger @LogID,@Parameters=@ParaInfo
  
  If Object_id('tempdb..#tmp_WSC_GetOrdersList') Is Not Null Drop Table #tmp_WSC_GetOrdersList
  Create Table #tmp_WSC_GetOrdersList
        (ID int,OrderCode varchar(20),Department varchar(10),Date_C varchar(10)
        ,VipCode varchar(30),VipName varchar(50),VipCallNumber varchar(50)
        ,EndDate varchar(10),AppointedTime datetime,IsEnd bit
        ,Deposit_Cash money,TotalAmount money,Accounts money,Arrears money
        ,ProductionDepartment varchar(10),One_Man varchar(10),Assessor varchar(10),Bookkeeper varchar(20)
        ,CreateTime datetime,IsVoid bit,memo varchar(50)
        ,Deposit_Voucher money,LetOut money,Deposit_Card money
        ,Address varchar(100),DiscountRate money,SumDiscount money
        ,Vip_Balance money,Vip_BonusPoints money
        ,IsPrinted bit,Reserved1 varchar(20),BuildState int,DepositTime varchar(10),DepositTime2 varchar(10)
        ,Outer_OrderCode varchar(32),PickUpDepartment varchar(20),Sender varchar(20)
        ,Reserved2 money,Reserved3 money,Reserved4 money,Reserved5 varchar(50),Reserved6 varchar(50),Reserved7 varchar(50)
        ,Hierarchy int)
  Declare @CardCode varchar(20)
  If IsNull(@OrderCode,'')<>'' 
    Begin--查询指定订单号的订单
      Exec Loger @LogID,@ExtendInfo='查询指定订单号的订单'
      Insert Into #tmp_WSC_GetOrdersList 
      Exec GetOrdersList '',@StartDate,@EndDate,@OrderCode,'',''
    End
  Else If IsNull(@VipCode,'')<>''
    Begin--查询指定会员的订单
      Exec Loger @LogID,@ExtendInfo='查询指定会员的订单'
      Select @CardCode=编号 From 会员 Where id in(Select 会员ID From 会员身份 Where 外键=@VipCode)
      Insert Into #tmp_WSC_GetOrdersList Exec GetOrdersList '',@StartDate,@EndDate,'',@CardCode
    End
  Else If IsNull(@oauth_code,'')<>''
    Begin--查询指定会员的分销订单
      Exec Loger @LogID,@ExtendInfo='查询指定会员的分销订单'
      Select @CardCode=编号 From 会员 Where id in(Select 会员ID From 会员身份 Where 外键=@VipCode)
      Insert Into #tmp_WSC_GetOrdersList Exec GetOrdersList '',@StartDate,@EndDate,'','',@CardCode
    End
  Else
    Begin--查询公众号产生的订单
      Exec Loger @LogID,@ExtendInfo='查询公众号产生的订单'
      Insert Into #tmp_WSC_GetOrdersList Exec GetOrdersList Null,@StartDate,@EndDate
    End
  If @IsEnd Is Null
    Begin
      Exec Loger @LogID,@ExtendInfo='结果集以[CreateTime]倒序排序'
      --Select *,dbo.fn_GetPickUpCode(Outer_OrderCode) As PickUpCode 
      Select *,dbo.fn_GetPickUpCode(ID) As PickUpCode 
      From #tmp_WSC_GetOrdersList Order By CreateTime Desc
    End
  Else 
    Begin
      Exec Loger @LogID,@ExtendInfo='提取相应结单状态的记录作为结果集,以[CreateTime]倒序排序'
      --Select *,dbo.fn_GetPickUpCode(Outer_OrderCode) As PickUpCode
      Select *,dbo.fn_GetPickUpCode(ID) As PickUpCode
      From #tmp_WSC_GetOrdersList Where IsEnd=@IsEnd Order By CreateTime Desc
    End
  Drop Table #tmp_WSC_GetOrdersList
End
Go
----指定单号模式下日期范围无效
----其它模式均可自由组合@StartDate与@EndDate来限定日期范围
----所有模式下,均可使用@IsEnd来限定范围
--Exec WSC_GetOrdersList @Ordercode='170227-00002'  --查指定订单
----指定会员模式
--Exec WSC_GetOrdersList @VipCode='MFfM3UC5WQQ5yRAfUh19hbk'  
--Exec WSC_GetOrdersList @VipCode='MFfM3UC5WQQ5yRAfUh19hbk',@IsEnd=0--查此会员未结的单
--Exec WSC_GetOrdersList @VipCode='MFfM3UC5WQQ5yRAfUh19hbk',@StartDate='2017-01-01'  --查此会员今年(从1月1日至今)
--Exec WSC_GetOrdersList @VipCode='MFfM3UC5WQQ5yRAfUh19hbk',@StartDate='2017-01-01',@EndDate='2017-01-31'  --查此会员一月的订单
--Exec WSC_GetOrdersList @VipCode='MFfM3UC5WQQ5yRAfUh19hbk',@EndDate='2017-04-31'  --查此会员今年五一前的所有订单
----指定分销会员模式
--Exec WSC_GetOrdersList @oauth_code='MFfM3UC5WQQ5yRAfUh19hbk'--查此会员分销下级产生的订单(不含此会员的)
--Exec WSC_GetOrdersList @oauth_code='MFfM3UC5WQQ5yRAfUh19hbk'@EndDate='2017-05-01',@IsEnd=1 --查此会员分销下级产生的订单(不含此会员的),已结的
----取商城生成的订单模式
--Exec WSC_GetOrdersList --查网上商城生成的订单
--Exec WSC_GetOrdersList @StartDate='2017-01-01'--查网上商城生成的订单
--Exec WSC_GetOrdersList @StartDate='2017-01-01',@EndDate='2017-04-19'--查网上商城生成的订单

--------------------------------------------------------------------------------
--获取指定订单信息
If Object_id('[WSC_GetOrdersDetail]','P') Is Not Null 
  Drop Procedure [WSC_GetOrdersDetail]
Go
Create Procedure [WSC_GetOrdersDetail]
       @OrderID int
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'WSC_GetOrdersDetail'
  Set @ParaInfo = dbo.NameValue('N','@OrderID',@OrderID) Exec Loger @LogID,@Key=@OrderID,@Parameters=@ParaInfo

  If Object_id('tempdb..#tmp_WSC_GetOrdersDetail') Is Not Null Drop Table #tmp_WSC_GetOrdersDetail
  Create Table #tmp_WSC_GetOrdersDetail
        (ID int,OrderID int,Code varchar(10),Name varchar(30),Specification varchar(20)
        ,Price money,DiscountRate money,[Count] money,TotalAmount money,SumDiscount money
        ,memo varchar(20),Reserved1 money,IsEnd bit,SumOfDay int)
  Exec Loger @LogID,@ExtendInfo='调用存储过程[GetOrdersDetail]获取计单明细'
  Insert Into #tmp_WSC_GetOrdersDetail Exec GetOrdersDetail @OrderID
  Exec Loger @LogID,@ExtendInfo='从[orders_detail_extend]表同步商城售价'
  Update #tmp_WSC_GetOrdersDetail 
     Set price = web_price 
     From orders_detail_extend 
     Where orders_detail_extend.link_id=#tmp_WSC_GetOrdersDetail.id
  Exec Loger @LogID,@ExtendInfo='排除运费项后作为输出结果集'
  Select * From #tmp_WSC_GetOrdersDetail Where Code<>'SYS_001'
  
  Drop Table #tmp_WSC_GetOrdersDetail
End
Go
--------------------------------------------------------------------------------
--订单中回收礼券
If Object_id('[WSC_RecoverVoucher]','P') Is Not Null
  Drop Procedure [WSC_RecoverVoucher]
Go
Create Procedure [WSC_RecoverVoucher]
       @RecordID    int        ,--单据ID:相应零售单/订单的主记录ID
       @VoucherCode varchar(20),--礼券编号
       @Amount      money      =0,--金额:抵扣金额,为0则按礼券面额抵扣
       @Remark      varchar(50)='' --备注
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'WSC_RecoverVoucher'
  Set @ParaInfo = dbo.NameValue('N','@RecordID',@RecordID)       Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@VoucherCode',@VoucherCode) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@Amount',@Amount)           Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@Remark',@Remark)           Exec Loger @LogID,@Parameters=@ParaInfo
  
  Declare @Return int,@msg varchar(100)
  Declare @Department varchar(10),@Source int
  --Select @Department=编号 From department Where 简称='公众号'
  Set @Source=1 --1为订单,0为零售,网上商城均为订单
  Select @Department=部门编号 From Orders Where ID=@RecordID
  Exec @Return=RecoverVoucher @Department,@Source,@RecordID,@VoucherCode,@Amount,@Remark
  Set @Msg = Case @Return
             When 0  Then '成功'
             When -1 Then '券编号无效'
             When -2 Then '券未发放'
             When -3 Then '券已回收'
             When -4 Then '券已过期'
             When -5 Then '写回收记录失败'
             Else '未知错误'
             End
  Exec Loger @LogID,@ExtendInfo=@msg,@Result=@Return
  Select @Return As retcode, @msg As Msg
End
Go
--------------------------------------------------------------------------------
--积分变动
If Object_id('[WSC_VipBonusPoints]','P') Is Not Null
  Drop Procedure [WSC_VipBonusPoints]
Go
Create Procedure [WSC_VipBonusPoints]
       @WeiXinCode  varchar(50),--会员外键
       @Add         money      ,--积分增加值(减少为负数,0为不变)
       @Description varchar(20) --积分变动原因
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,''
  Set @ParaInfo = dbo.NameValue('C','@WeiXinCode',@WeiXinCode)   Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('Y','@Add',@Add)                 Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@Description',@Description) Exec Loger @LogID,@Parameters=@ParaInfo

  Declare @Return int,@msg varchar(100)
  Declare @cDepart varchar(10),@cUser varchar(10),@VipCode varchar(20)
  Select @cDepart=编号 From department Where 简称='公众号'
  Select @cUser=编号 From pub_user Where 姓名='公众号'
  Select @VipCode=A.编号 From 会员 As A Inner Join 会员身份 As B On A.ID=B.会员ID Where B.外键=@WeiXinCode
  Exec Loger @LogID,@ExtendInfo='调用存储过程[VipBonusPoints]完成积分变动'
  Exec @Return=VipBonusPoints @cDepart,@cUser,@VipCode,@Add,@Description
  Set @msg = Case @Return
             When  0 Then '成功'
             When -1 Then '无此会员'
             When -2 Then '卡未启用'
             When -3 Then '卡已挂失'
             When -4 Then '卡已过期'
             When -5 Then '积分变动失败'
             Else '未知错误'
             End
  Exec Loger @LogID,@ExtendInfo=@msg,@Result=@Return
  Select @Return As retcode, @msg As Msg
End
Go
--------------------------------------------------------------------------------
--检查分销树以确认两个会员可以建立分销父子关系
--返回集0则可以建立分销关系
--如果不能建立分销关系则在返回集msg中包含原因
   ----原始数据测试
  --Exec WSC_GetVipRelevance 'oH7hfuHST2w_VAqTw9dB4dnksHE0','oH7hfuJGShVdPEX7AeR50X3JuYVI'
  ----模拟测试1:正常
  --Begin Tran
  --Update web_user_invited_link Set user_id=140,invited_id=150 where user_id=14
  --Exec WSC_GetVipRelevance 'oH7hfuHST2w_VAqTw9dB4dnksHE0','oH7hfuJGShVdPEX7AeR50X3JuYVI'
  --Rollback
  ----模拟测试1:树交叉
  --Begin Tran
  --Update web_user_invited_link Set user_id=140,invited_id=150 where user_id=14
  --Update web_user_invited_link Set user_id=17 where user_id=13 and invited_id=14
  --Exec WSC_GetVipRelevance 'oH7hfuHST2w_VAqTw9dB4dnksHE0','oH7hfuJGShVdPEX7AeR50X3JuYVI'
  --Rollback
If Object_id('[WSC_GetVipRelevance]','P') Is Not Null
  Drop Procedure [WSC_GetVipRelevance]
Go
Create Procedure [WSC_GetVipRelevance]
       @ParentVipoAuthCode varchar(50),--准备作为父的会员外键
       @ChildVipoAuthCode varchar(50)  --准备作为子的会员外键
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,'WSC_GetVipRelevance'
  Set @ParaInfo = dbo.NameValue('C','@ParentVipoAuthCode',@ParentVipoAuthCode) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('C','@ChildVipoAuthCode',@ChildVipoAuthCode)   Exec Loger @LogID,@Parameters=@ParaInfo

  Declare @Tree Table(id int,level int)
  Declare @iLevel int
  Declare @ID int
  Declare @VipCode varchar(20)
  --限制:子项应当是没有上级的
  Select Top 1 @VipCode = E.编号
         From web_oauth_login As A
         Left Join web_user_invited_link As B On A.user_id=B.invited_id
         Left Join web_oauth_login As C On B.user_id=C.user_id
         Left Join 会员身份 As D On C.oauth_id=D.外键
         Left Join 会员 As E On D.会员ID=E.ID
         Where A.oauth_id=@ChildVipoAuthCode
  If @VipCode Is Not Null
  Begin
    Set @ParaInfo = '子项已经是会员[' + @VipCode + ']的下级'
    Exec Loger @LogID,@ExtendInfo=@ParaInfo,@Result=-1
    Select -1 As retcode,@ParaInfo As Msg
    Return
  End
  --子系树
  --  因此限制最递归层数为3
  --  不检查下级中的循环引用
  Insert Into @Tree
         Select C.user_id,1 --A.会员ID,B.外键,C.user_id
         From 会员身份 As A
         Left Join 会员身份 As B On A.会员ID=B.会员ID
         Left Join web_oauth_login As C On B.外键=C.oauth_id
         Where A.外键=@ChildVipoAuthCode
  Set @iLevel = 1
  While @@RowCount > 0 And @iLevel < 4
  Begin
    Set @iLevel = @iLevel + 1
    Insert Into @Tree 
           Select invited_id,@iLevel
           From @Tree As A 
           Inner Join web_user_invited_link As B On A.id=B.user_id 
           Where A.level=@iLevel-1
  End
  --父级对应的ID
  --  限制递归层数为3
  --  不检查上级中的循环引用
  Set @iLevel = 0
  Insert Into @Tree
         Select C.user_id,@iLevel
         From 会员身份 As A
         Left Join 会员身份 As B On A.会员ID=B.会员ID
         Left Join web_oauth_login As C On B.外键=C.oauth_id
         Where A.外键=@ParentVipoAuthCode
  While @@RowCount>0 And @iLevel > -3
  Begin
    Set @iLevel = @iLevel-1
    Insert Into @Tree 
           Select user_id,@iLevel
           From @Tree As A 
           Inner Join web_user_invited_link As B On A.id=B.invited_id
           Where A.level=@iLevel+1
  End
  --判断是否可以建立相应的分销关系
  --  如果父系树与子系树有交集,则不可组建分销关系
  Select Top 1 @ID=A.id
         From @Tree As A
         Inner Join @Tree As B On A.ID=B.ID
         Where A.level<0 
           And B.level>=0
  If @ID Is Not Null
    Begin
      Select @VipCode=C.编号
             From web_oauth_login As A
             Left Join 会员身份 As B On A.oauth_id = B.外键
             Left Join 会员 As C On B.会员ID=C.ID
             Where A.user_id=@ID
      Set @VipCode = IsNull(@VipCode,'')
      Set @ParaInfo = '分销关系交叉:' + @VipCode
      Exec Loger @LogID,@ExtendInfo=@ParaInfo,@Result=-1
      Select -1 As retcode,@ParaInfo As Msg    
    End
  Else
    Begin
      Exec Loger @LogID,@ExtendInfo='可以建立分销关系'
      Select 0 As retcode,'可以建立分销关系' As Msg
    End
End
Go
--------------------------------------------------------------------------------
/*
老板小助手 统计数据
  老板小助手
调用示例
  exec WSC_Get_BossTotal 部门，老板 ，日期
  部门可为空或null,或部门号，老板 可为空或null,或老板号，日期可为空，或日期
  例如 
  exec WSC_Get_BossTotal         --全部汇总        不带参数则汇总全部
  exec WSC_Get_BossTotal  '001'     --单店汇总        第一个参数对应 表 web_napa_stores  中hs_code 字段
  exec WSC_Get_BossTotal  '',1       --指定老板店汇总  第二个参数对应 表 web_napa_stores  中ID 字段
  exec WSC_Get_BossTotal  '','',‘2017-06-09’ --第三个参数不指定，当前日期为今天，也可指定某个日期为今天，例如昨天为今天
*/
If Object_id('[WSC_Get_BossTotal]','P') Is Not Null 
  Drop Procedure [WSC_Get_BossTotal]
Go
 Create Procedure [WSC_Get_BossTotal]  
       @Department  varchar(10)=null,--选择店铺  对应 表 web_napa_stores  中hs_code 字段
       @Boss        int        =null,--选择老板  对应 表 web_napa_stores  中ID 字段
       @InDate      datetime   =null --今天所指日期，不指定系统默认当前日期
As
Begin
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,''
  Set @ParaInfo = dbo.NameValue('C','@Department',@Department) Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('N','@Boss',@Boss)             Exec Loger @LogID,@Parameters=@ParaInfo
  Set @ParaInfo = dbo.NameValue('D','@InDate',@InDate)         Exec Loger @LogID,@Parameters=@ParaInfo
  Exec Loger @LogID,@ExtendInfo='调用存储过程[Get_BossTotal]获取统计数据'
  If Object_id('tempdb..#tmp_WSC_GetBossTotal') Is Not Null 
    Drop Table #tmp_WSC_GetBossTotal
  Create Table #tmp_WSC_GetBossTotal
        (Coption varchar(40),[Values] money,VType varchar(20),sort int)
  Insert Into #tmp_WSC_GetBossTotal Exec Get_BossTotal @Department,@Boss,@InDate 
  Select Coption,[Values],VType  From #tmp_WSC_GetBossTotal
  Drop Table #tmp_WSC_GetBossTotal
End
Go
--------------------------------------------------------------------------------
--得到指定网上订单对应的提货码
--参数
--  @OrderSerialNum:对应web_orders.order_serial_num
--                  长度限制为19,并且不能以9开始
--返回值 
--  返回一个长度不超过13的字符串,由[0-9A-Z]构成
--  它与传入值是一一对应的
--示例
--Exec WSC_GetPickUpCode '15041491594818464'
If Object_id('[WSC_GetPickUpCode]','P') Is Not Null
  Drop Procedure [WSC_GetPickUpCode]
Go
Create Procedure [WSC_GetPickUpCode]
       @OrderSerialNum varchar(20)--一个不大于922 3372 0368 5477 5807的数
As
Begin
  Select dbo.fn_GetPickUpCode(@OrderSerialNum) As PickUpCode
End
Go
/*******************************************************************************
日志相关函数与过程
正式环境下使用下列脚本替代：
--If Object_id('[EventLog]','U') Is Not Null 
--  Drop Table [EventLog]
--Go
--If Object_id('[NameValue]','FN') Is Not Null
--  Drop Function [NameValue]
--Go
--Create Function [NameValue]
--      (@TypeName varchar(218)='C',
--       @Name     varchar(128),
--       @Value    sql_variant)
--Returns varchar(3000)
--As
--Begin
--  Return ''
--End
--Go
--If Object_id('[Loger]','P') Is Not Null 
--  Drop Procedure [Loger]
--Go
--Create Procedure [Loger]
--       @ID         int         =null Output,
--       @Procedure  varchar(128)=null,
--       @Key        varchar(128)=null,
--       @Parameters varchar(128)=null,
--       @ExtendInfo varchar(999)=null,
--       @Result     int         =null
--As 
--Begin
--End
--Go
常用的写日志方式：
  Declare @LogID int,@ParaInfo varchar(128)
  Exec @LogID=Loger null,''--生成日志项,得到日志ID
  Set @ParaInfo = dbo.NameValue('C','参数',参数) Exec Loger @LogID,@Parameters=@ParaInfo--记录参数值
  Exec Loger @LogID,@Key=0,@ExtendInfo='',@Result=0
*******************************************************************************/
/*
日志表
*/
If Object_id('[EventLog]','U') Is Not Null 
  Drop Table [EventLog]
Go
Create Table [EventLog]
       ([Id]         int          identity(1,1) Primary Key
       ,[Time]       datetime     not null default getdate()
       ,[Procedure]  varchar(128) not null
       ,[Key]        varchar(128) not null default ''
       ,[Parameters] varchar(4000)null
       ,[ExtendInfo] varchar(3000)null
       ,[Result]     int          not null default 0
       )
Go
Create Index IX_EventLog_Procedure On [EventLog]([Procedure])
Create Index IX_EventLog_Result    On [EventLog]([Result])
Go
/*
Function NameValue
生成名值对字符串
*/
If Object_id('[NameValue]','FN') Is Not Null
  Drop Function [NameValue]
Go
Create Function [NameValue]
      (@TypeName varchar(218)='C',
       @Name     varchar(128),
       @Value    sql_variant)
Returns varchar(3000)
As
Begin
  Declare @Value_C varchar(3000)
  If @Value Is Null
    Set @Value_C = '"<NULL>"'
  Else If @TypeName In ('char','nchar','varchar','nvarchar','text','ntext','sysname','C')
    Set @Value_C = '"' + replace(replace(replace(convert(varchar(1000),@Value),'''',''''''), '\', '\\'), '"', '\"') + '"'
  Else If @TypeName In ('money','smallmoney','Y')
    Set @Value_C = Convert(varchar(20),@Value)
  Else If @TypeName In ('int','smallint','tinyint','bigint','numeric','decimal','float','real','N')
    Set @Value_C = convert(varchar(20),@Value)
  Else If @TypeName In ('datetime','smalldatetime','D','T')
    Set @Value_C = '"' + convert(varchar(20),@Value,120) + '"'
  Else If @TypeName In ('bit','L')
    Set @Value_C = Case When @Value = 1 Then '"True"' Else '"False"' End
  Else
    Set @Value_C = '"<' + @TypeName + '>..."'
  
  Return '"' + @Name + '":' + @Value_C
End
Go
--------------------------------------------------------------------------------
/*
Procedure Loger
功能:写一条日志
以下四组语句等效：
Declare @LogID Int
Exec @LogID=Loger 
Exec Loger @LogID,'test'
Exec Loger @LogID,Default,'这是Key'
Exec Loger @LogID,Default,Default,'"参数1":"值1"'
Exec Loger @LogID,Default,Default,'"参数2":"值2"'
Exec Loger @LogID,Default,Default,Default,'已经完成第1步;'
Exec Loger @LogID,Default,Default,Default,'已经完成第2步;'
Exec Loger @LogID,Default,Default,Default,Default,0

Exec @LogID=Loger 
Exec Loger @LogID,'test'
Exec Loger @LogID,Default,'这是Key'
Exec Loger @LogID,Default,Default,'"参数1":"值1"'
Exec Loger @LogID,Default,Default,'"参数2":"值2"'
Exec Loger @LogID,Default,Default,Default,'已经完成第1步;'
Exec Loger @LogID,Default,Default,Default,'已经完成第2步;'
Exec Loger @LogID,Default,Default,Default,Default,0

Exec @LogID=Loger 
Exec Loger @LogID,@Procedure='test'
Exec Loger @LogID,@Key='这是Key'
Exec Loger @LogID,@Parameters='"参数1":"值1"'
Exec Loger @LogID,@Parameters='"参数2":"值2"'
Exec Loger @LogID,@ExtendInfo='已经完成第1步;'
Exec Loger @LogID,@ExtendInfo='已经完成第2步;'
Exec Loger @LogID,@Result=0

Exec Loger Null,'test','这是Key','"参数1":"值1","参数2":"值2"','已经完成第1步;已经完成第2步;',0

Select * from [EventLog]
*/
If Object_id('[Loger]','P') Is Not Null 
  Drop Procedure [Loger]
Go
Create Procedure [Loger]
       @ID         int         =null Output,
       @Procedure  varchar(128)=null,
       @Key        varchar(128)=null,
       @Parameters varchar(128)=null,
       @ExtendInfo varchar(999)=null,
       @Result     int         =null
As 
Begin
  If @ID Is Null
    Begin
      If @Procedure Is Null Set @Procedure= ''
      Insert Into [EventLog]([Procedure])Values(@Procedure)
      Set @ID = Scope_Identity()
    End
  If @Procedure  Is Not Null Update [EventLog] Set [Procedure] =@Procedure  Where ID=@ID
  If @Key        Is Not Null Update [EventLog] Set [Key]       =@Key        Where ID=@ID
  If @Parameters Is Not Null 
    If Exists(Select * From [EventLog] Where ID=@ID And [Parameters] Is Null)
      Update [EventLog] Set [Parameters]='{'+@Parameters+'}' Where ID=@ID
    Else
      Update [EventLog] Set [Parameters]=SubString([Parameters], 1, Len([Parameters]) - 1) + ',' + @Parameters + '}' Where ID=@ID
  If @ExtendInfo Is Not Null 
    If Exists(Select * From [EventLog] Where ID=@ID And [ExtendInfo] Is Null)
      Update [EventLog] Set [ExtendInfo]=@ExtendInfo Where ID=@ID
    Else
      Update [EventLog] Set [ExtendInfo]=[ExtendInfo] + Convert(varchar(1), 0x0d0a) + @ExtendInfo Where ID=@ID
  If @Result Is Not Null Update [EventLog] Set [Result]=@Result Where ID=@ID
  Return @ID
End
Go
--------------------------------------------------------------------------------
/*
关于订单接口的使用说明
***建立一张订单
调用WSC_CreateOrder写主表,得到相应的ID与单号
  使用礼券支付的,如此处理,同时要赋值"@礼券"
  会员消费只需要赋值'@刷卡金额'即可
循环调用WSC_AddOrderItem逐条写入明细
如果支付中使用了礼券,调用逐条WSC_RecoverVoucher做礼券回收处理

***修改订单
调用WSC_CreateOrder,结入@OrderCode(订单号),同时给入@CallNumber/@PickUpTime/@Remarks/@Destination/@EndOrder/@VoidOrder
  仅上述内容可以修改.未给入或给入Null值的不被修改.
  

--门店列表
--select id,编号,简称,全称,地址,联系电话,类别简称,往来属性 from department where 是否停用=0 and 往来属性 in ('直营店','加盟店')
--所有可销售产品
--select ID,编号,名称,规格,单位,销售主价 from goods where 是否销售=1 and 停用日期>convert(varchar(10),getdate(),120)
*/
/*
修改
2016-12-10 hunter__fox
  添加过程GetVipLog_ID:获取指定会员ID的消费/充值记录,简化GetVipLog实现

2016-12-10 hunter__fox
  NewVipCode:生成一个新的会员编号

2016-12-12 hunter__fox
  NewVip:在[会员]表建立一条记录,并返回相应的ID

2016-12-26 hunter__fox
  NewVip:指定[积分比例]默认值为1

2017-04-28
  修正:公众号部门的编号生成时使用的子查询不排除department表中的任何记录,以避免得到一个已经存在的编号

2017-05-08 hunter__fox
  修正:GetVipLog_ID中修正BUG"当会员交易记录中只有充值记录时,余额显示为Null"
  添加了封装性质的存储过程:WSC_GetVipInfo/WSC_AddVip/WSC_VipReCharge/WSC_GetVipLog
  其中WSC_AddVip/WSC_VipReCharge/WSC_GetVipLog均多返回一个结果集,只含一个字段'retcode',只有一条记录,它相当于原始存储过程的返回值
  *调用方法与原始存储过程一致.
  
2017-05-09
  修正GetVipLog:
      当会员消费/充值记录最后一次交易是充值时,以会员当前余额为调平计算的基点
      此修正用于适应初始化卡金额不为0的卡,以及手动修改数据库中会员卡余额的情况
      GetVipLog返回的结果集以时间倒序排列

2017-05-10
  WSC_AddVip:参数@cMobileNumber现在是必需参数
             参数@cVipCode不再使用,该参数未从定义中去除,但它的值已经丢弃
             状态结果集添加一个字段Msg,包含状态的说明性文字
             状态:0/-1/-2/-3/-201
  WSC_VipReCharge:状态结果集添加了字段Msg
  AddVip:添加返回值-2(无卡)/-3(停用)
         绑定到已有会员时,检查会员的状态是否是停用状态
  VipReCharge:生成的充值记录制单人为'公众号'
  GetVipLog_ID:消费记录中包含了积分信息,增加了字段[Integral]
               包含了积分充值记录
               包含了零售中积分兑换礼品记录
               包含了统一结算中使用积分支付的记录
               这一改动影响这些存储过程的输出集结构:GetVipLog,WSC_GetVipLog
  添加存储过程GetVouchers
  添加存储过程WSC_GetVouchers,获取出库到微商城并且未销售的礼券列表

2017-05-11
  WSC_SaleVoucher:电子券销售
  Saleing:生成销售单(单个物品的),并生成相应的销售明细
  SaleingVoucher:礼券销售/赠送
  CreateOrder:生成订单主表记录

2017-05-12
  GetOrdersList:获取订单列表
  GetOrdersDetail:获取指定订单明细
  WSC_CreateOrder:生成订单的封装
  WSC_GetOrdersList:获取订单列表
  WSC_GetOrdersDetail:获取指定订单明细

2017-05-14
  AddOrderItem:写订单明细
  WSC_AddOrderItem:添加订单明细
  RecoverVoucher礼券回收
  WSC_RecoverVoucher:为订单回收礼券

2017-05-15
  修正:在WSC_AddVip中判断指定的电话号码是否已注册为会员
  在脚本最后补充了获取可用门店列表与可销售产品列表的查询语句

2017-05-17
  GetOrdersList添加了一个可选参数@authcode,用于配合多层分销
  WSC_GetOrdersList添加了一个可选参数@oauth_code,用于取多层分销订单数据

2017-05-18
  GetVouchers增加了参数,用于获取指定会员可使用的券
             一些查询语句添加了对截止日期的判断
             添加了一列,指出券是销售到哪个会员,对于未销售的券,它的值为''
  WSC_GetVouchers增加了一个参数,用于获取指定会员可使用的券
  WSC_GetVipLog增加一列[BonusPoints],记录每笔交易中积分的增减数量
  GetVipLog_ID在输出集中添加了积分变动列
              在获取数据源中添加对对积分使用的一些子查询

2017-05-19
  GetVipLog_ID:修正了零售记录中未提取退货记录的错误

2017-05-24
  GetVouchers:在无参数的情况下返回一个空结果集
  WSC_GetVipLog:去除了返回的状态结果集
  WSC_GetVouchers:去除了返回的状态结果集
  WSC_CreateOrder:去除了返回的状态结果集
  WSC_GetOrdersList:去除了返回的状态结果集
  WSC_GetOrdersDetail:去除了返回的状态结果集
  WSC_GetVipInfo结果集字段名改为英文
  WSC_GetVouchers结果集字段名改为英文
  WSC_CreateOrder结果集字段名改为英文
  WSC_GetOrdersList结果集字段名改为英文
  WSC_GetOrdersDetail结果集字段名改为英文
  
2017-05-26
  CreateOrder添加了一个参数"@销售类别",对应字段[销售类别],仅在创建订单时可以指定值
  WSC_CreateOrder添加了一个参数,用于指出订单将下到哪个店
            该接口生成的订单,销售类别为'公众号'
  GetOrdersList:重新定义了分支逻辑,现在,部门/单号/会员编号/分销根会员号是互斥的
  WSC_GetOrdersList:按GetOrdersList的修改重新实现了接口逻辑
  WSC_GetOrdersList:添加了一个参数@IsEnd用于进一步过滤订单的结单状态
     使用法法的改变请看该过程后附的示例

2017-06-06
  GetVouchers:礼券出库表中改为检查往来编号以识别礼券出库的目标部门(原为部门编号)
  SaleingVoucher:调用Saleing时増加了两个参数@PayByCash,@PayByCard,与Saleing保持一致
  WSC_SaleVoucher:添加了自动匹配礼券的产品编号功能
  WSC_SaleVoucher:返回的状态集増加了一列,用于返回相应的零售单号,失败时为Null

2017-06-07
  礼券表添加了字段[产品编号],并为已有数据自动匹配了合适的产品编号
  GetVouchers:添加了输出列[产品编号]
  WSC_GetVouchers:添加了输出列[GoodsCode],它是第一列
  WSC_GetOrdersList:输出集以CreateTime列倒序排列
  WSC_GetVipRelevance:新増加过程,用于判断两个会员外键是否可以建立分销关系,用法参见相关过程头部注释

2017-06-09
  GetVouchers:添加了一个参数@GoodsCode用于通过产品编号过滤券,仅在指定部门时该参数有效
  WSC_GetVouchers:添加了一个参数@GoodsCode用于获取本部门(公众号)可发放的券

2017-06-12
  添加存储过程:QueryFormMObject

2017-06-15 
  添加存储过程Get_BossTotal返回老板小助手统计数据
  添加存储过程Wsc_Get_BossTotal对Get_BossTotal调用的封装

2017-6-19
  修改 存储过程Get_BossTotal @Boss 变量类型为 int
  修改 存储过程WSC_Get_BossTotal @Boss 变量类型为 int
  增加 WSC_Get_BossTotal 调用说明示例
  
2017-6-20
  增加 函数 dbo.Fun_Birthday 计算指定日期后的生日
  增加 过程 GetBirthday 取指定日期内过生日的会员信息
  修改 过程 WSC_GetVipInfo 增加 2个可带参数 天数，指定日期

2017-6-28
  恢复Get_BossTotal 输出 Xtype 内字符为小写
      
2017-08-15
  添加了建表Event_Log的脚本
  添加了产品类别【加收费】与产品【运费】
  添加了存储过程Loger
  修改了存储过程CreateOrder,在其中添加了写日志的动作
  修改了存储过程AddOrderItem,在其中添加了写日志的动作
  修改了存储过程CreateOrder,如果订单有运费,生成/更新运费商品项
  修改了存储过程WSC_GetOrdersDetail,从结果集中去除运费项

2017-08-16
  添加了建表orders_detail_extend的脚本
  修改了存储过程AddOrderItem,增加了一个可选参数@ExtendPrice,用于传递来自于网上商城中的售价
  修改了存储过程WSC_GetOrdersDetail,其结果集中的单价使用商城中相应订单生成时的售价
  存储过程【Loger】添加了一个可选参数用于传递附加于Key字段的信息
  表【Event_Log】的【Key】字段宽度修改为128

2017-08-17
  存储过程【AddVip】修改,加入了对券与挂失卡的检查，增加一个返回码-4,表示相应的卡已经挂失
  存储过程【WSC_AddVip】,加入了对返回码-4的文字化代码
  存储过程【VipReCharge】参数改变,不再固定用于微商城充值
  存储过程【WSC_VipReCharge】修改代码以适应【VipReCharge】新的参数表
  存储过程【VipReCharge】添加了写日志的动作
  存储过程【VipBonusPoints】添加了写日志的动作

2017-08-22
  添加了日志相关的存储过程与函数/表
  为所有存储过程添加了写日志的代码

2017-08-26
  添加存储过程【AddWebPay】用于在线下系统中生成线上支付对应的结算记录

2017-09-04  
  添加函数【fn_GetPickUpCode】根据网上商城订单号计算相应的提货码
  添加函数【fn_UnPickUpCode】:根据提货码计算相应的网上商城订单号
  添加存储过程【WSC_GetPickUpCode】完成线上订单号到提货码的转换

2017-09-07
  存储过程【CreateOrder】中添加了对字段[刷卡日期]与[礼券日期]的赋值动作

2017-09-11
  存储过程【NewVip】中,新添加的会员,不指定默认折扣值,直接依靠【会员】表【折扣】字段默认值【DF__会员__状态__4D7622B8】来定值

2017-09-14
  存储过程【WSC_GetOrdersList】添加了一列【PickUpCode】，其内容是提货码

2017-09-18
  存储过程【CreateOrder】中关于订金的计算方式保持与线下一致:订金=现金+第三支付金额

2017-09-27
  存储过程【VipReCharge】中生成的单号不带世纪值,与线下保持一致
  为表【会员身份】添加唯一约束:外键唯一
  存储过程【AddVip】中如果因为【会员身份】外键唯一约束而添加失败,会删除会员表中添加的记录以保证不重复添加会员

2017-09-28
  添加存储过程【PushWeiXinMessage】用于线下向线上推送消费信息
      调用:Exec PushWeiXinMessage 'c会员编号','c交易类型','c单号','c支付方式',n金额
  存储过程【WSC_CreateOrder】中日期以Stock_end表来计算

2017-10-12
  添加存储过程【ChangeVipLinks】用于会员重绑定

2017-10-20
  存储过程【VipReCharge】对充值表/充值明细表中【现金合计】【现金】写值

2017-11-02
  存储过程【Get_BossTotal】返回集中vType根据需要修改为不同的值:money或integer
*/