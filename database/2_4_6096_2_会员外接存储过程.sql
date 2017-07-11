/*
与微商城对接的更新
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
  
  Get_BossTotal:老板小助手 统计数据 今日销售，昨日销售，今日订单数，昨日订单数，今日会员消费，昨日会员消费，
  --              今日非会员消费，昨日非会员消费，今日新增会员，昨日新增会员，今日充值，昨日充值
  
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
      关于订单的使用,请参考最后的说明
  获取可用门店列表的查询语句
  获取所有可销售产品的查询语句
  
  Wsc_Get_BossTotal:老板小助手 统计数据 今日销售，昨日销售，今日订单数，昨日订单数，今日会员消费，昨日会员消费，
  --              今日非会员消费，昨日非会员消费，今日新增会员，昨日新增会员，今日充值，昨日充值
  
2016-12-06 hunter__fox

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
*/

/******************************************************************************/
--公众号对接需要添加相应的部门,操作员,结算方式
--部门
--为公众号/微商城建立相应的部门与操作员,以区分数据来源
--生成的部门编号是从1开始最小的未使用号,最大不超过255
--生成的操作员编号为'部门01'



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
--结算方式
If Not Exists(Select * From 结算方式表 Where 名称='公众号')
  Insert Into 结算方式表(名称,结算类型,结算折扣率,第三方支付,是否启用)
         Values('公众号','现金',1,1,0)
Go
--礼券表添加字段[产品编号]
--  变长10,允许空
--为代金券匹配相应的产品编号
If Not Exists(Select * From syscolumns where name='产品编号' And id=object_id('礼券表','U'))
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
--确保[会员身份]表有效可用
If object_id('会员身份','U') Is Null
Begin
  Create Table [会员身份](
     会员ID int not null
    ,外键 varchar(50) not null
    )
  Create Index IX_会员身份_会员ID On 会员身份(会员ID)
  Create Index IX_会员身份_外键 On 会员身份(外键)
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

/*******************************************************************************
Procedure GetVipLog_ID
功能:获取指定会员的消费/充值记录
参数:
  @iVipID:会员表ID
  @cDateStart:可选,查询开始日期(含此日)
  @cDateStart:可选,查询截止日期(含此日)
*/
If Object_ID('[GetVipLog_ID]','P') Is Not Null
  Drop Procedure [GetVipLog_ID]
Go
Create Procedure [GetVipLog_ID]
       @iVipID     int                     ,--会员表ID
       @cDateStart varchar(10)='1900-01-01',--查询开始日期(含此日)
       @cDateEnd   varchar(10)=''           --查询截止日期(含此日)
As
Begin
  Declare @dStart datetime
  Declare @dEnd datetime
  If @cDateStart='' Or @cDateStart Is Null Set @cDateStart='1900-01-01'
  If @cDateEnd  ='' Or @cDateEnd   Is Null Set @cDateEnd=Convert(varchar(10),getdate(),120)
  Set @dStart = Convert(datetime,@cDateStart)
  Set @dEnd = Convert(datetime,@cDateEnd) + 1
 
  --取会员核心信息
  Declare @cVipCode varchar(20),@Tmp_Balance money,@Tmp_Integral int
  Select @cVipCode=编号,@Tmp_Balance=卡余额,@Tmp_Integral=积分 From 会员 Where id =@iVipID

  --游标:指定会员指定时间之后所有交易记录
  Declare @t_opt table(来源 varchar(10),id int,单号 varchar(20),建立日期 datetime,充值 money,刷卡金额 money,积分变化 int,当前余额 money ,计算余额 money,当前积分 int)
  Declare @来源 varchar(10),@id int,@单号 varchar(20),@建立日期 datetime,@充值 money,@刷卡金额 money,@积分变化 int,@当前余额 money ,@余额 money,@当前积分 int
  Declare cur_Tmp Cursor
    For --零售记录
        Select '零售'as 来源,id,单号,建立日期,Null As 充值,刷卡金额,0 as 积分变化,当前余额,0 As 余额,当前积分 
        From sales 
        Where 建立日期 >= @dStart And 往来编号=@cVipCode And 刷卡金额<>0
        Union All
        --订单记录
        Select '订单',id,单号,建立日期,Null,刷卡金额,0,当前余额,0 ,当前积分
        From orders 
        Where 建立日期 >= @dStart And 是否废止=0 And 往来编号=@cVipCode
        Union All 
        --充值记录(含积分充值)
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
          And a.金额<>0 And Isnull(a.备注,'')<>''
          And a.备注 Like '[0-9][0-9][0-9]%'
          And DataLength(rtrim(a.备注))<6
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
        Select '零售',A.id,A.单号,A.建立日期,Null,0,0-substring(B.备注,5,10),当前余额,0 As 余额,当前积分
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
  Open cur_Tmp
  Fetch Next From cur_Tmp Into @来源,@id,@单号,@建立日期,@充值,@刷卡金额,@积分变化,@当前余额,@余额,@当前积分
  While @@Fetch_status=0
  Begin
    If @当前积分 Is Null Set @当前积分 = @Tmp_Integral
    Insert Into @t_opt Values(@来源,@id,@单号,@建立日期,@充值,@刷卡金额,@积分变化,@当前余额,@Tmp_Balance,@Tmp_Integral)
    Set @Tmp_Balance = @Tmp_Balance + isnull(@刷卡金额,0) - isnull(@充值,0)
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
If Object_ID('[NewVipCode]','P') Is Not Null
  Drop Procedure [NewVipCode]
Go
Create Procedure [NewVipCode]
       @RetCode varchar(20) Output
As
Begin
  --生成会员编号,独立事务
  Declare @iBillMax int
  Begin Tran
    Select @iBillMax=IsNull(最大单号, 0) + 1 From bills Where 表名 = '微商城会员' And 会计期间 = 'WX'
    If @iBillMax Is Null Set @iBillMax = 1
    If @iBillMax=1
      Insert Into bills(表名, 会计期间, 最大单号)Values('微商城会员', 'WX', @iBillMax)
    Else
      Update bills Set 最大单号 = @iBillMax Where 表名 = '微商城会员' And 会计期间 = 'WX'
  Commit Tran
  If @@Error<>0 
  Begin
    Rollback Tran
    Return ''
  End
  Set @RetCode= 'WX' + Replace(Str(@iBillMax, 8), ' ', '0')
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
If Object_ID('[NewVip]','P') Is Not Null
  Drop Procedure [NewVip]
Go
Create Procedure [NewVip]
       @iVipID int Output
As
Begin
  Declare @LastError int
  Declare @cVipCode varchar(20)
  Set @LastError=-1
  While @LastError<>0
  Begin
    Exec NewVipCode @cVipCode Output
    Insert Into 会员(编号,内码,名称,电话,生日,是否农历,
                     积分,折扣,状态,截止日期,是否充值,卡金额,卡余额,
                     积分比例,是否积分,会员生日,会员卡类型,会员等级类型)
             Values(@cVipCode,@cVipCode,'','','',0,
                    0,1,1,GetDate()+3650,0,0,0,
                    1,0,'',1,1)
    Select @LastError=@@Error,@iVipID = @@Identity
  End
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
If Object_ID('[GetVipInfo]','P') Is Not Null
  Drop Procedure [GetVipInfo]
Go
Create Procedure [GetVipInfo]
       @cWeiXinCode varchar(50) = null --会员身份外键
As
Begin
  /*
  通过外键获取会员记录
  参数 @cWeiXinCode 为绑定到会员的外键
       不指定则返回所有没有绑定外键的会员
  */
  If @cWeiXinCode Is Null Or len(@cWeiXinCode)=0
    Select * 
    From 会员 
    Where id Not In(Select Distinct 会员ID 
                    From 会员身份)
  Else
    Select * 
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
2016-12-12 hunter__fox                  调用NewVip完成新会员记录建立
*/
If Object_ID('[AddVip]','P') Is Not Null
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
  Declare @iVipID int,@VipState int
  Declare @dBirthday datetime
  --验证:外键唯一
  If Exists(Select * From 会员身份 Where 外键 = @cWeiXinCode)
  Begin
    Raiserror(N'外键"%s"已经与某会员绑定',16,1,@cWeiXinCode)
    Return -1
  End
  
  If @cVipCode='' Or @cVipCode Is Null
  Begin
    --无会员,建立它
    Exec NewVip @iVipID Output
    Set @VipState = 1
  End
  Else
    --有会员,取ID
    Select @iVipID=id,@VipState=状态 From 会员 Where 编号=@cVipCode
  If @iVipID Is Null Return -2 --无此会员
  If @VipState = 0 Return -3 --会员已停用
  
  Update 会员
     Set 电话 = Case When IsNull(@cMobileNumber,'')=''
                     Then 电话 Else @cMobileNumber End
        ,名称 = Case When IsNull(@cName,'')=''
                     Then 名称 Else @cName End
        ,生日 = Case When IsNull(@cBirthday,'')=''
                     Then 生日 Else Convert(datetime,@cBirthday) End
        ,是否农历 = Case When @bIsLunar Is Null 
                         Then 是否农历 Else @bIsLunar End
        ,会员生日 = Case When IsNull(@cBirthday,'')=''
                         Then 会员生日 Else Right(Replace(@cBirthday,'-',''),4) End
  Where ID=@iVipID
  --进行会员绑定
  Insert Into 会员身份(会员ID,外键)Values(@iVipID,@cWeiXinCode)
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
*/
If Object_ID('[VipReCharge]','P') Is Not Null
  Drop Procedure [VipReCharge]
Go
Create Procedure [VipReCharge]
       @cWeiXinCode varchar(50),     --会员身份外键
       @nAddMoney money=0,           --充值金额
       @cWeiXinOrderCode varchar(50) --微商城支付订单号
As
Begin
  Declare @cDepart varchar(10) --部门编号(微商城,无可登录人员)
  Declare @cUser varchar(10)   --制单人(微商城操作员,不可登录)
  Declare @iPayMode int        --结算方式(微商城,在结算方式表中为第三方支付,不启用)
  Set @cDepart  = ''
  Set @cUser    = ''
  Select @cDepart = 编号 From department Where 简称='公众号'
  Select @cUser=编号 From pub_user Where 姓名='公众号'
  Select @iPayMode = id From 结算方式表 Where 名称='公众号'
  If @iPayMode Is Null Set @iPayMode = -1 
  --取会员ID与编号
  Declare @iVipID int, @cVipCode varchar(20)
  Select @iVipID=id,@cVipCode=编号 From 会员 Where id In(Select 会员ID From 会员身份 Where 外键 = @cWeiXinCode)
  If @iVipID Is Null
  Begin
    Raiserror(N'外键"%s"没有与会员绑定',16,1,@cWeiXinCode)
    Return -1
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
    Return -2
  End
  Set @cOrderNo = Replace(@cDate, '-', '') + '-' + Replace(Str(@iBillMax, 5), ' ', '0')
  --建立充值记录,在事务中进行
  --要求四表同步
  Declare @iBusinessID int
  Begin Tran tran_VipReCharge_2
    Insert Into 充值表(单号, 日期, 部门编号, 制单人,金额合计, 现金合计, 状态, 备注)
           Values(@cOrderNo, @cDate, @cDepart, @cUser, @nAddMoney, 0, 1, '微信支付订单号:' + @cWeiXinOrderCode)
    Set @iBusinessID = @@Identity
    Insert Into 充值明细表(PID, 编号, 金额, 现金)
           Values(@iBusinessID, @cVipCode, @nAddMoney, 0)
    Insert Into 结算明细表(业务ID, 来源, 结算ID, 结算金额,备注)
           Values(@iBusinessID, 3, @iPayMode, @nAddMoney, @cWeiXinOrderCode)
  Commit Tran tran_VipReCharge_2
  If @@Error<>0 
  Begin
    Rollback Tran tran_VipReCharge_2
    Return -3
  End
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
Procedure VipBonusPoints
功能:修改会员积分
参数:
     @cDepart:部门
     @cUser:操作员
     @VipCode:会员卡号
     @Add:t积分增加数
     @Description:积分变动原因
*/
If Object_ID('VipBonusPoints','P') Is Not Null
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
  Declare @状态 int,@是否充值 bit,@积分 money,@截止日期 datetime
  Select @状态=状态,@是否充值=是否充值,@积分=积分,@截止日期=截止日期 From 会员 Where 编号=@VipCode
  If @状态 Is Null
    Return -1 --无此会员
  Else If @状态 = 0
    Return -2 --卡未启用
  Else If @是否充值=1
    Return -3 --卡已挂失
  Else If @截止日期< getdate()
    Return -4 --卡已过期
  
  If @Add <> 0
  Begin
    --生成充值单号,独立事务
    --它是充值的必要条件,但生成的单号可以丢弃
    Declare @cOrderNo varchar(20), @cDate varchar(10), @iBillMax int
    Set @cDate=Convert(varchar(10), GetDate(), 120)
    Exec getbillmax '充值业务', @cDate, @iBillMax OUTPUT
    Set @cOrderNo = Replace(@cDate, '-', '') + '-' + Replace(Str(@iBillMax, 5), ' ', '0')
    
    Declare @iBusinessID int
    Begin Tran
      Insert Into 充值表(单号, 日期, 部门编号, 制单人,金额合计, 现金合计, 状态, 备注)
             Values(@cOrderNo, @cDate, @cDepart, @cUser, 0, 0, 1, @Description)
      Set @iBusinessID = @@Identity
      Insert Into 充值明细表(PID, 编号, 金额, 现金,积分,备注)
             Values(@iBusinessID, @VipCode, 0, 0, @Add, @Description)
      Update 会员 Set 积分 = @积分 + @Add Where 编号 = @VipCode
    Commit Tran  
    If @@Error<>0 
    Begin
      Rollback Tran
      Return -5 --积分变动失败
    End
  End
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
If Object_ID('[GetVipLog]','P') Is Not Null
  Drop Procedure [GetVipLog]
Go
Create Procedure [GetVipLog]
       @cWeiXinCode varchar(50),--会员绑定的外键
       @cDateStart varchar(10)='1900-01-01', --查询开始日期(含此日)
       @cDateEnd varchar(10)=''  --查询截止日期(含此日)
As
Begin
  If @cWeiXinCode='' Or @cWeiXinCode Is Null
  Begin
    Raiserror(N'需要指定一个外键',16,1)
    Return -1
  End
  Declare @iVipID int
  Select @iVipID=id From 会员 Where id In(Select 会员ID From 会员身份 Where 外键 = @cWeiXinCode)
  If @iVipID Is Null
  Begin
    Raiserror(N'外键"%s"没有与会员绑定',16,2,@cWeiXinCode)
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

If Object_ID('[GetVouchers]','P') Is Not Null
  Drop Procedure [GetVouchers]
Go
Create Procedure [GetVouchers]
       @DepartmentCode varchar(20)='',--获取出库到指定部门并且尚未销售的礼券
       @VipCode        varchar(20)='',--获取销售给指定会员的礼券
       @VoucherCode    varchar(20)='',--获取指定编号礼券
       @GoodsCode      varchar(10)='' --获取指定产品编号的券,仅在指定@DepartmentCode时有效
As
Begin
  If IsNull(@DepartmentCode, '') <> '' And IsNull(@GoodsCode, '') <> ''
    Select A.产品编号,A.编号,A.面额,'已出库' As 状态
          ,Case When IsNull(A.往来编号,'0')='0' Then '' Else A.往来编号 End As 会员编号
          ,A.截止日期,A.条码
    From 礼券表 As A
    Left Join 礼券出库表 As B On A.编号=B.编号
    Where A.状态=1 And B.往来编号=@DepartmentCode
      And A.截止日期>=getdate()
      And A.产品编号=@GoodsCode
  Else If IsNull(@DepartmentCode, '') <> '' 
    Select A.产品编号,A.编号,A.面额,'已出库' As 状态
          ,Case When IsNull(A.往来编号,'0')='0' Then '' Else A.往来编号 End As 会员编号
          ,A.截止日期,A.条码
    From 礼券表 As A
    Left Join 礼券出库表 As B On A.编号=B.编号
    Where A.状态=1 And B.往来编号=@DepartmentCode
      And A.截止日期>=getdate()
  Else If IsNull(@VipCode, '') <> ''
    Select A.产品编号,A.编号,A.面额,'已销售' As 状态
          ,@VipCode As 会员编号
          ,A.截止日期,A.条码
    From 礼券表 As a
    Inner Join 礼券销售表 As b On a.编号=b.编号
    Where b.往来编号=@VipCode
      And a.状态=2 and a.是否回收=0
      And a.截止日期>=getdate()
  Else If IsNull(@VoucherCode, '') <> ''
    Select A.产品编号,A.编号,A.面额
          ,Case A.状态+A.是否回收
           When 0 Then '未出库'
           When 1 Then '已出库'
           When 2 Then '已销售'
           When 3 Then '已使用'
           Else '无效'
           End As 状态
          ,Case When IsNull(A.往来编号,'0')='0' Then IsNull(C.往来编号,'') Else A.往来编号 End As 会员编号
          ,A.截止日期,A.条码
    From 礼券表 As A
    Left Join 礼券出库表 As B On A.编号=B.编号
    Left Join 礼券销售表 As C On A.编号=C.编号
    Where A.编号=@VoucherCode
  Else
  Begin
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
If Object_ID('[Saleing]','P') Is Not Null
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
  --生成单号
  Declare @BillMax int,@SalesID int
  Exec getbillmax '销售', @CDate, @BillMax Output
  Set @Ret_BillCode = Convert(varchar(6),Convert(datetime, @CDate), 12) 
                    + '-' + Right('0000' + Convert(varchar(10), @BillMax), 5)
  Declare @销售类别 varchar(20),@原价 money,@当前余额 money,@当前积分 money
  Set @销售类别=Case When @VipCode='' Or @VipCode Is Null Then '正常零销' Else '会员销售' End
  Select @原价=销售主价 From goods Where 编号=@GoodsCode And 是否销售=1
  --在事务中添加记录
  Begin Transaction tran_Saleing
  Insert Into sales(单号,部门编号,制单人,日期,销售类别,结单,往来编号,当前余额,当前积分
                   ,数量合计,金额合计,折扣合计
                   ,现金金额,刷卡金额,礼券金额)
         Values(@Ret_BillCode,@DepartmentCode,@UserCode,@CDate,@销售类别,1,@VipCode,0,0
               ,@GoodsCount,@GoodsCount*@Price,@GoodsCount*(@原价-@Price)
               ,@PayByCash,@PayByCard,@GoodsCount*@Price-@PayByCash-@PayByCard)
  If @@Rowcount > 0
  Begin
    Set @SalesID = SCOPE_IDENTITY()
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
    Return -1
  End
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
If Object_ID('[SaleingVoucher]','P') Is Not Null
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
  If @CDate='' Or @CDate Is Null 
    Set @CDate = Convert(varchar(10), GetDate(), 120) --中午十二点之后做帐到后一天
  
  Declare @Ret int--,@Ret_BillCode varchar(20)
  Declare @面额 money,@状态 int,@截止日期 datetime
  Select @面额=面额,@状态=状态+是否回收,@截止日期=截止日期 From 礼券表 Where 编号=@VoucherCode
  --检查:券必需是已销售并且未过截止日期的
  If @@RowCount = 0           Set @Ret = -100
  Else If @截止日期<getdate() Set @Ret = -101
  Else If @状态 <> 1          Set @Ret = -103 - @状态 --(-103未出库/-105已销售/-106已使用)
  Else                        Set @Ret = 0
  If @Ret > 0 Return @Ret
  --在事务中修改数据
  Begin Transaction tran_SaleingVoucher
  --生成销售记录
  Exec @Ret=Saleing @DepartmentCode,@UserCode,@CDate,@GoodsCode,1,@Price,@PayByCash,@PayByCard,@VipCode,@Ret_BillCode Output
  If @Ret<>0
  Begin
    Rollback Transaction tran_SaleingVoucher
    Return @Ret --生成交易失败
  End
  If @VoucherCode<>'0000'
  Begin
    --生成礼券销售表记录
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
    Return -102 --事务失败
  End
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
If Object_ID('[CreateOrder]','P') Is Not Null
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
       @销售类别   varchar(50)=''     --网上商城生成的订单为'公众号',不可修改
As
Begin
  Declare @OrderID int--成功返回订单ID,失败返回0或负值
  Declare @单位名称 varchar(50),@当前余额 money,@当前积分 money,@应收金额 money,@欠款 money,@交货日期 varchar(10)
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
  Set @欠款= Case When @结单=1 Then 0 Else @应收金额-@让利-@订金-@刷卡金额-@礼券 End
  Set @交货日期=Case When @结单=1 Or @废止=1 Then Convert(varchar(10),GetDate(),120) Else Null End
  If @单号 Is Null Or @单号=''
  Begin
    If @日期 Is Null
      Set @日期 = Convert(varchar(10), GetDate(), 120)
    Declare @BillMax int,@SalesID int
    Exec getbillmax '订单', @日期, @BillMax Output
    Set @单号 = Convert(varchar(6),Convert(datetime, @日期), 12) 
              + '-' + Right('0000' + Convert(varchar(10), @BillMax), 5)
    Insert orders(单号,日期,部门编号,制单人,生产单位,取货部门,取货时间,商户订单号
                 ,往来编号,联系方式,备注,送货地址,单位名称,当前余额,当前积分
                 ,金额合计,优惠合计,应收金额,让利,订金,刷卡金额,礼券,欠款
                 ,结单,是否废止,交货日期,销售类别)
           Values(@单号,@日期,@部门编号,@制单人,@生产单位,@取货部门,@取货时间,@商户订单号
                 ,@往来编号,@联系方式,@备注,@送货地址,@单位名称,0,0
                 ,@金额合计,@优惠合计,@应收金额,@让利,@订金,@刷卡金额,@礼券,@欠款
                 ,@结单,@废止,@交货日期,@销售类别)
    Select @OrderID=SCOPE_IDENTITY()
    If @往来编号 <> ''
    Begin
      Select @当前余额=卡余额, @当前积分=积分 From 会员 where 编号=@往来编号
      Update orders Set 当前余额=@当前余额,当前积分=@当前积分 Where ID=@OrderID
    End
  End
  Else
  Begin
    --订单只能修改部分字段
    Select @OrderID=id From orders Where 单号=@单号
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
  Return @OrderID
End
Go
/*******************************************************************************
Procedure AddOrderItem
功能:生成一条订单明细
*/
If Object_ID('[AddOrderItem]','P') Is Not Null
  Drop Procedure [AddOrderItem]
Go
Create Procedure [AddOrderItem]
       @PID         int        ,--订单ID
       @GoodsCode   varchar(10),--商品编号
       @GoodsCount  int        ,--商品数量
       @Price       money      ,--单价,未折扣的单价
       @TotalAmount money       --金额,折扣后总金额
As       
Begin
  Declare @名称 varchar(30),@规格 varchar(20),@折扣 money,@优惠金额 money
  Select @名称=名称,@规格=规格 From goods Where 是否销售=1 And 编号=@GoodsCode
  Set @折扣 = @TotalAmount * 100 / @Price / @GoodsCount
  Set @优惠金额 = @Price * @GoodsCount - @TotalAmount
  Insert Into orders_detail(订单ID,编号,名称,规格,单价,折扣,数量,金额,优惠金额,结单)
         Values(@PID,@GoodsCode,@名称,@规格,@Price,@折扣,@GoodsCount,@TotalAmount,@优惠金额,0)
  If @@Error<>0 Return -1
  Return 0
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
If Object_ID('[GetOrdersList]','P') Is Not Null 
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
  If IsNull(@StartDate,'')='' Set @StartDate='1900-01-01'
  If IsNull(@EndDate,'')='' Set @EndDate=convert(varchar(10),getdate(),120)
  If @OrderCode<>''
    Begin--查询指定单号的订单,不受日期限制
      Select * ,0 As Hierarchy From orders 
      Where 单号=@OrderCode
    End
  Else If @Department Is Null
    Begin--查询公众号产生的订单
      Select * ,0 As Hierarchy From orders 
      Where 销售类别='公众号' And 日期 Between @StartDate And @EndDate
    End
  Else If @Department <> ''
    Begin--查询指定部门制单的订单
      Select * ,0 As Hierarchy From orders 
      Where 部门编号=@Department And 日期 Between @StartDate And @EndDate
    End
  Else If @VipCode<>''
    Begin--查询指定会员的订单
      Select * ,0 As Hierarchy From orders
      Where 日期 Between @StartDate And @EndDate And 往来编号=@VipCode
    End
  Else If @authcode<>''
    Begin--查询指定会员的下级会员分销订单
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
      Select Top 0 *,0 As Hierarchy From orders
    End
  If @@Error<>0
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
If Object_ID('[GetOrdersDetail]','P') Is Not Null
  Drop Procedure [GetOrdersDetail]
Go
Create Procedure [GetOrdersDetail]
       @OrderID int
As
Begin
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
If Object_ID('[RecoverVoucher]','P') Is Not Null
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
  --检查;礼券状态=2,是否回收=0,截止日期<getdate(),@Amount<=面额
  Declare @Return int,@msg varchar(100)
  Declare @状态 int,@是否回收 bit,@截止日期 datetime,@面额 money
  Select @状态=状态,@是否回收=是否回收,@截止日期=截止日期,@面额=面额 From 礼券表 Where 编号=@VoucherCode
  If @@RowCount < 1 Return -1 --券编号无效
  Else If @状态 <> 2 Return -2  --不是已发放的券
  Else If @是否回收 = 1 Return -3 --券已回收
  Else If @截止日期 < GetDate() Return -4 --券已过有效期
  If @Amount=0 Set @Amount=@面额
  Insert Into 礼券回收表(部门编号,来源,PID,编号,面额,金额,备注)
         Values(@Department,@Source,@RecordID,@VoucherCode,@面额,@Amount,@Remark)
  If @@Error<>0 Return -5
  Return 0
End
Go
/*******************************************************************************
Procedure QueryFormMObject
功能:执行mobile_object中的查询
参数:@QueryName是查询名,对应name字段
返回值:成功返回0,失败返回-1
*/
If Object_ID('[QueryFormMObject]','P') Is Not Null
  Drop Procedure [QueryFormMObject]
Go
Create Procedure [QueryFormMObject]
       @QueryName varchar(50)
As
Begin
  Declare @SQL varchar(8000)
  Select @SQL=command From mobile_object Where name=@QueryName
  If IsNull(@SQL,'')=''
    Return -1
  Exec (@SQL)
  Return 0
End
Go
/*******************************************************************************
Author:   <Author,,Name>
Create date: 2017-06-15
Description:为老板助手返回统计数据
用法
   exec Get_BossTotal [部门],[老板],[日期]
  部门可为空或null,或部门号，老板 可为空或null,或老板号，日期可为空，或日期
  例如 
  --exec  Get_BossTotal        --全部汇总        不带参数则汇总全部
  --exec  Get_BossTotal  '001'     --单店汇总        第一个参数对应 表 web_napa_stores  中hs_code 字段
  --exec  Get_BossTotal  '',1       --指定老板店汇总  第二个参数对应 表 web_napa_stores  中ID 字段
  --exec  Get_BossTotal  '','',‘2017-06-09’ --第三个参数不指定，当前日期为今天，也可指定某个日期为今天，例如昨天为今天

*/
If Object_ID('[Get_BossTotal]','P') Is Not Null 
  Drop Procedure [Get_BossTotal]
Go
Create Procedure [Get_BossTotal]
       @Department varchar(10)=null,--选择店铺 参数对应 表 web_napa_stores  中hs_code 字
       @Boss       int        =null,--选择老板 参数对应 表 web_napa_stores  中ID 字段
       @InDate     datetime   =null--今天所指日期，不指定系统默认当前日期
AS
-- =============================================
-- =============================================
Begin
  If @InDate Is null Set @InDate=getdate()
  Declare @Wsc varchar(10),@date0 varchar(10),@date1 varchar(10)
  Declare @Dates    Table(cName varchar(10),Date varchar(10),iIndex int)
  Declare @Depart   Table(Department varchar(10))
  Declare @TmpTable Table(日期 varchar(10),往来编号 varchar(20),应收金额 money)
  Declare @TmpOut   Table(Caption varchar(20),Value money,ValueType varchar(10),Short int)

  Select @Wsc=编号 From pub_user where 姓名 ='公众号'
  Select @date0=Convert(varchar(10),@InDate  ,120),@date1=Convert(varchar(10),@InDate-1,120)
  Insert into @Dates Values('今日',@date0,1)
  Insert into @Dates Values('昨日',@date1,2)
  If IsNull(@Department,'')<>''
    Insert Into @Depart Values(@Department)
  Else If IsNull(@Boss,0)<>0
    Insert Into @Depart Select Distinct hs_code From [web_napa_stores] Where [user_id]=@Boss
  Else
    Insert Into @Depart Select 编号 From department Where 类别编号 in ('102','103')
  Insert Into @TmpTable
  Select 日期,往来编号,应收金额
  From orders
  Where 日期 BetWeen @date1 And @date0
    And 部门编号 In (Select * From @Depart)
    And 制单人=@Wsc
  Insert Into @TmpOut 
         Select A.cName+'销售',IsNull(Sum(应收金额),0),'money',A.iindex
         From @Dates As A
         Left Join @TmpTable As B On A.[Date]=B.日期
         Group by A.cName+'销售',A.iindex
  Insert Into @TmpOut
         Select A.cName+'订单数',Count(*),'int',A.iindex+10
         From @Dates As A
         Left Join @TmpTable As B On A.[Date]=B.日期
         Group by A.cName + '订单数',A.iindex+10
  Insert Into @TmpOut 
         Select A.cName+'会员',Count(*),'int',A.iindex+20
         From @Dates As A
         Left Join 会员 As B On A.[Date]=convert(varchar(10),B.建立时间,120)
         Group By A.cName + '会员',A.iindex+20
  Select Caption,Value,ValueType,Short from @TmpOut Order By Short
End
Go 
/*******************************************************************************
为微商城系统提供的接口封装
  主要功能为一些参数的自动生成,以及返回值转化为结果集
*/
--------------------------------------------------------------------------------
--取会员信息
--  @cWeiXinCode外键
If Object_ID('[WSC_GetVipInfo]','P') Is Not Null
  Drop Procedure [WSC_GetVipInfo]
Go
Create Procedure [WSC_GetVipInfo]
       @cWeiXinCode varchar(50) = null,
       @DayCount int=null
As
Begin
  If Object_ID('tempdb..#tmp_WSC_GetVipInfo') Is Not Null Drop Table #tmp_WSC_GetVipInfo
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
    Insert Into #tmp_WSC_GetVipInfo Exec GetBirthday @DayCount
  Select * From #tmp_WSC_GetVipInfo  --以此输出,可以在此排除不需要的字段
  Drop Table #tmp_WSC_GetVipInfo
End
Go
--exec WSC_GetVipInfo 'Y6GagnXEeygErTHh9-Rx3'
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
If Object_ID('[WSC_AddVip]','P') Is Not Null
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
               Else '未知错误'
               End
  End
  Else
  Begin
    Set @Return = -201
    Set @msg = '手机号''' + @cMobileNumber + '''重复注册到多个会员,不能绑定.'
  End
  Select @Return As retcode,@msg As Msg
End
Go
--exec WSC_AddVip 'AKnexiDEEH1vrWSl9-wa','12345678901','asdf'
--------------------------------------------------------------------------------
--会员充值
--  @cWeiXinCode会员外键
--  @nAddMoney充值金额
--  @cWeiXinOrderCode微商城支付订单号
If Object_ID('[WSC_VipReCharge]','P') Is Not Null
  Drop Procedure [WSC_VipReCharge]
Go
Create Procedure [WSC_VipReCharge]
       @cWeiXinCode varchar(50),     --会员身份外键
       @nAddMoney money=0,           --充值金额
       @cWeiXinOrderCode varchar(50) --微商城支付订单号
As
Begin
  Declare @Return int,@msg varchar(100)
  Exec @Return = VipReCharge @cWeiXinCode,@nAddMoney,@cWeiXinOrderCode
  Set @msg = Case @Return
             When  0 Then '成功'
             When -1 Then '无此会员'
             When -2 Then '生成订单号失败'
             When -3 Then '生成充值记录失败'
             Else '未知错误'
             End
  Select @Return As retcode,@msg As Msg
End
Go
--------------------------------------------------------------------------------
--获取指定会员的消费记录
If Object_ID('[WSC_GetVipLog]','P') Is Not Null
  Drop Procedure [WSC_GetVipLog]
Go
Create Procedure [WSC_GetVipLog]
       @cWeiXinCode varchar(50),--会员绑定的外键
       @cDateStart varchar(10)='1900-01-01', --查询开始日期(含此日)
       @cDateEnd varchar(10)=''  --查询截止日期(含此日)
As
Begin
  --Declare @Return int,@msg varchar(100)
  --Exec @Return=GetVipLog @cWeiXinCode,@cDateStart,@cDateEnd
  --Set @msg = Case @Return
  --           When 0 Then '成功'
  --           When -1 Then '需要外键'
  --           When -2 Then '外键未与线下会员绑定'
  --           Else '未知错误'
  --           End
  --Select @Return As retcode,@msg As Msg
  --不需要返回状态集
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
If Object_ID('[WSC_GetVouchers]','P') Is Not Null
  Drop Procedure [WSC_GetVouchers]
Go
Create Procedure [WSC_GetVouchers]
       @WeiXinCode  varchar(50)='',--会员外键
       @VoucherCode varchar(20)='',--券编号
       @GoodsCode   varchar(10)='' --券的产品编号
As
Begin
  If Object_ID('tempdb..#tmp_WSC_GetVouchers')Is Not Null
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
      Declare @VipCode varchar(20)
      Select @VipCode=编号 
      From 会员 As a
      Inner Join 会员身份 As b On a.id=b.会员id
      Where b.外键=@WeiXinCode
      Insert Into #tmp_WSC_GetVouchers Exec GetVouchers '', @VipCode
    End
  Else If IsNull(@VoucherCode, '') <> ''
    Begin
      Insert Into #tmp_WSC_GetVouchers Exec GetVouchers '', '', @VoucherCode
    End
  Else If IsNull(@GoodsCode, '') <> ''
    Begin
      Select @cDepart = 编号 From department Where 简称='公众号'
      Insert Into #tmp_WSC_GetVouchers Exec GetVouchers @cDepart,'','',@GoodsCode
    End
  Else
    Begin
      Select @cDepart = 编号 From department Where 简称='公众号'
      Insert Into #tmp_WSC_GetVouchers Exec GetVouchers @cDepart
    End
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
If Object_ID('[WSC_SaleVoucher]','P') Is Not Null
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
      Return -1
    End
    --在Goods中寻找销售价格最接近的券
    Select Top 1 @GoodsCode=编号 From goods Where 存货属性='现金券' Order By Abs(11-销售主价)
    If @GoodsCode Is Null
    Begin
      Select -2 As retcode,'没有匹配到可以销售/赠送的礼券产品' As Msg
      Return -2
    End
  End
  
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
  Select 0 As retcode,'成功' As Msg,@SalesCode As SalesOrderNo
End
Go
--------------------------------------------------------------------------------
--建立订单
--@OrderCode传入''或null则为新建订单
--@OrderCode有内容则为修改订单,可提交新的@CallNumber/@PickUpTime/@Remarks/@Destination/@EndOrder/@VoidOrder
--返回结果集:
--  结果集:订单ID int,订单号 varchar(20)
If Object_ID('[WSC_CreateOrder]','P') Is Not Null 
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
       @Department varchar(20)  =''    --订单下到哪个店
As
Begin
  Declare @Return int,@msg varchar(100)
  Declare @OrderID int,@Department_Web varchar(10),@UserCode varchar(10),@VipCode varchar(20),@cDate varchar(10)
  Select @Department_Web='公众号'
  Select @UserCode=编号 From pub_user Where 姓名='公众号'
  Select @VipCode=编号 From 会员 Where id in(Select 会员ID From 会员身份 Where 外键=@WeiXinCode)
  Set @cDate = Convert(varchar(10),getdate(),120)
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
  Select @OrderID As OrderID,@OrderCode As OrderCode
  --If @OrderID>0
  --  Select  0 As retcode, '成功' As Msg
  --Else
  --  Select -1 As retcode, '失败' As Msg
EnD
Go
--------------------------------------------------------------------------------
--写入订单明细
If Object_ID('[WSC_AddOrderItem]','P') Is Not Null
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
  Declare @Return int,@msg varchar(100)
  Exec @Return=AddOrderItem @PID,@GoodsCode,@GoodsCount,@Price,@TotalAmount
  Set @msg = Case @Return
             When 0 Then '成功'
             When -1 Then '失败'
             Else '未知错误'
             End
  Select @Return As retcode, @msg As Msg
End
Go
--------------------------------------------------------------------------------
--获取订单列表
--  
If Object_ID('[WSC_GetOrdersList]','P') Is Not Null 
  Drop Procedure [WSC_GetOrdersList]
Go
Create Procedure [WSC_GetOrdersList]
       @StartDate  varchar(10)='1900-01-01',--日期范围:开始日期
       @EndDate    varchar(10)=''          ,--日期范围:结束日期
       @OrderCode  varchar(20)=''          ,--订单号
       @VipCode    varchar(50)=''          ,--会员外键
       @oauth_code varchar(50)=''          ,--返回该会员下级产生的订单,层数在GetOrdersList中控制
                                            --不含该会员自己产生的订单
       @IsEnd      bit        =null         --指定此参数,则只返回结单状态与之一致的单
As
Begin
  --Declare @Return int,@msg varchar(100)
  Declare @CardCode varchar(20)
  If Object_ID('tempdb..#tmp_WSC_GetOrdersList') Is Not Null Drop Table #tmp_WSC_GetOrdersList
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
  If IsNull(@OrderCode,'')<>'' 
    Begin--查询指定订单号的订单
      Insert Into #tmp_WSC_GetOrdersList 
      Exec GetOrdersList '',@StartDate,@EndDate,@OrderCode,'',''
    End
  Else If IsNull(@VipCode,'')<>''
    Begin--查询指定会员的订单
      Select @CardCode=编号 From 会员 Where id in(Select 会员ID From 会员身份 Where 外键=@VipCode)
      Insert Into #tmp_WSC_GetOrdersList Exec GetOrdersList '',@StartDate,@EndDate,'',@CardCode
    End
  Else If IsNull(@oauth_code,'')<>''
    Begin--查询指定会员的分销订单
      Select @CardCode=编号 From 会员 Where id in(Select 会员ID From 会员身份 Where 外键=@VipCode)
      Insert Into #tmp_WSC_GetOrdersList Exec GetOrdersList '',@StartDate,@EndDate,'','',@CardCode
    End
  Else
    Begin--查询公众号产生的订单
      Insert Into #tmp_WSC_GetOrdersList Exec GetOrdersList Null,@StartDate,@EndDate
    End
  If @IsEnd Is Null
    Select * From #tmp_WSC_GetOrdersList Order By CreateTime Desc
  Else 
    Select * From #tmp_WSC_GetOrdersList Where IsEnd=@IsEnd Order By CreateTime Desc
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
If Object_ID('[WSC_GetOrdersDetail]','P') Is Not Null 
  Drop Procedure [WSC_GetOrdersDetail]
Go
Create Procedure [WSC_GetOrdersDetail]
       @OrderID int
As
Begin
  --Declare @Return int,@msg varchar(100)
  --Exec @Return=GetOrdersDetail @OrderID
  --Set @msg=Case @Return When 0 Then '成功' Else '失败' End
  --Select @Return As retcode, @msg As Msg
  --Exec GetOrdersDetail @OrderID
  If Object_ID('tempdb..#tmp_WSC_GetOrdersDetail') Is Not Null Drop Table #tmp_WSC_GetOrdersDetail
  Create Table #tmp_WSC_GetOrdersDetail
        (ID int,OrderID int,Code varchar(10),Name varchar(30),Specification varchar(20)
        ,Price money,DiscountRate money,[Count] money,TotalAmount money,SumDiscount money
        ,memo varchar(20),Reserved1 money,IsEnd bit,SumOfDay int)
  Insert Into #tmp_WSC_GetOrdersDetail Exec GetOrdersDetail @OrderID
  Select * From #tmp_WSC_GetOrdersDetail
  Drop Table #tmp_WSC_GetOrdersDetail
End
Go
--------------------------------------------------------------------------------
--订单中回收礼券
If Object_ID('[WSC_RecoverVoucher]','P') Is Not Null
  Drop Procedure [WSC_RecoverVoucher]
Go
Create Procedure [WSC_RecoverVoucher]
       @RecordID    int        ,--单据ID:相应零售单/订单的主记录ID
       @VoucherCode varchar(20),--礼券编号
       @Amount      money      =0,--金额:抵扣金额,为0则按礼券面额抵扣
       @Remark      varchar(50)='' --备注
As
Begin
  Declare @Return int,@msg varchar(100)
  Declare @Department varchar(10),@Source int
  Select @Department=编号 From department Where 简称='公众号'
  Set @Source=1 --1为订单,0为零售,网上商城均为订单
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
  Select @Return As retcode, @msg As Msg
End
Go
--------------------------------------------------------------------------------
--积分变动
If Object_ID('[WSC_VipBonusPoints]','P') Is Not Null
  Drop Procedure [WSC_VipBonusPoints]
Go
Create Procedure [WSC_VipBonusPoints]
       @WeiXinCode  varchar(50),--会员外键
       @Add         money      ,--积分增加值(减少为负数,0为不变)
       @Description varchar(20) --积分变动原因
As
Begin
  Declare @Return int,@msg varchar(100)
  Declare @cDepart varchar(10),@cUser varchar(10),@VipCode varchar(20)
  Select @cDepart=编号 From department Where 简称='公众号'
  Select @cUser=编号 From pub_user Where 姓名='公众号'
  Select @VipCode=A.编号 From 会员 As A Inner Join 会员身份 As B On A.ID=B.会员ID Where B.外键=@WeiXinCode
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
If Object_ID('[WSC_GetVipRelevance]','P') Is Not Null
  Drop Procedure [WSC_GetVipRelevance]
Go
Create Procedure [WSC_GetVipRelevance]
       @ParentVipoAuthCode varchar(50),--准备作为父的会员外键
       @ChildVipoAuthCode varchar(50)  --准备作为子的会员外键
As
Begin
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
    Select -1 As retcode,'子项已经是会员[' + @VipCode + ']的下级' As Msg
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
    Select -1 As retcode,'分销关系交叉:' + @VipCode As Msg
  End
  Else
    Select 0 As retcode,'可以建立分销关系' As Msg
End
Go
--------------------------------------------------------------------------------
--老板小助手 统计数据

-- Author:    <Author,,Name>
-- Create date: 2017-06-15
-- Description: 老板小助手
--调用示例
  --exec WSC_Get_BossTotal 部门，老板 ，日期
  --部门可为空或null,或部门号，老板 可为空或null,或老板号，日期可为空，或日期
  --例如 
  --exec WSC_Get_BossTotal         --全部汇总        不带参数则汇总全部
  --exec WSC_Get_BossTotal  '001'    --单店汇总        第一个参数对应 表 web_napa_stores  中hs_code 字段
  --exec WSC_Get_BossTotal  '',1       --指定老板店汇总  第二个参数对应 表 web_napa_stores  中ID 字段
  --exec WSC_Get_BossTotal  '','',‘2017-06-09’ --第三个参数不指定，当前日期为今天，也可指定某个日期为今天，例如昨天为今天
If Object_ID('[WSC_Get_BossTotal]','P') Is Not Null 
  Drop Procedure [WSC_Get_BossTotal]
Go
 Create Procedure [WSC_Get_BossTotal]  
       @Department  varchar(10)=null,--选择店铺  对应 表 web_napa_stores  中hs_code 字段
       @Boss        int        =null,--选择老板  对应 表 web_napa_stores  中ID 字段
       @InDate      datetime   =null --今天所指日期，不指定系统默认当前日期
     As  
Begin  
 If Object_ID('tempdb..#tmp_WSC_GetBossTotal') Is Not Null Drop Table #tmp_WSC_GetBossTotal
  Create Table #tmp_WSC_GetBossTotal
        (Coption varchar(40),[Values] money,VType varchar(20),sort int)
  Insert Into #tmp_WSC_GetBossTotal Exec Get_BossTotal @Department,@Boss,@InDate 
  Select Coption,[Values],VType  From #tmp_WSC_GetBossTotal  
  Drop Table #tmp_WSC_GetBossTotal
End  

 Go

 
--   --------------------------------------------------------------------------------
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
exec Get_BossTotal



