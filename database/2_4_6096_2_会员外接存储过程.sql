/*
��΢�̳ǶԽӵĸ���
������
  Fun_Birthday: ���� ת����������Ϊָ�����ں����������  
  
��������:
  GetVipInfo:ȡ��Ա��Ϣ
  AddVip:��ӻ�Ա(�������),����ָ����Ա��
  VipReCharge:��Ա��ֵ
  GetVipLog:��ȡָ����Ա(���)������/��ֵ��¼
  GetVipLog_ID:��ȡָ����Ա(ID)������/��ֵ��¼
  NewVipCode:����һ���µĻ�Ա���
  NewVip:���ɻ�Ա��¼
  GetVouchers:��ȡ��ȯ��Ϣ(ָ�����ſɷ��ŵ�/ָ����ŵ�)
  Saleing:�������ۼ�¼(������Ʒ����)
  SaleingVoucher:������ȯ
  CreateOrder:����һ�Ŷ���(ֻ������¼)
  AddOrderItem:���һ��������ϸ
  GetOrdersList:��ȡ�����б�
  GetOrdersDetail:��ȡָ����������ϸ
  RecoverVoucher:��ȯ����
  VipBonusPoints:�Ի�Ա���ֽ��б䶯(ͨ����ֵ0ʵ��)
  QueryFormMObject:ִ��mobile_object�еĲ�ѯ
  
  Get_BossTotal:�ϰ�С���� ͳ���������ݷǻ�Ա���ѣ�����������Ա������������Ա�����ճ�ֵ�����ճ�ֵ
  GetBirthday:��ȡָ�����ں�XX���ڹ����յĻ�Ա��Ϣ
   
Ϊ�������ṩ�ĵ��÷�װ:
  WSC_GetVipInfo:ȡ��Ա��Ϣ
  WSC_AddVip:��ӻ�Ա
  WSC_VipReCharge:��Ա��ֵ
  WSC_GetVipLog:��ȡָ����Ա���������/��ֵ��¼,��������Ϣ
  WSC_GetVouchers:�õ�΢�̳��п��Է��ŵĵ���ȯ�б�/ָ����ŵ���ȯ
  WSC_SaleVoucher:����ȯ����
  WSC_CreateOrder:���ɶ�������¼,�õ�����
  WSC_AddOrderItem:��Ӷ�����ϸ
  WSC_GetOrdersList:��ȡ�����б�
  WSC_GetOrdersDetail:��ȡָ��������ϸ
  WSC_RecoverVoucher:�����л�����ȯ
  WSC_VipBonusPoints:���ֱ䶯
  WSC_GetVipRelevance:ȷ��������Ա�Ƿ���Խ���������ϵ
      ���ڶ�����ʹ��,��ο�����˵��
  ��ȡ�����ŵ��б�Ĳ�ѯ���
  ��ȡ���п����۲�Ʒ�Ĳ�ѯ���
  
  Wsc_Get_BossTotal:�ϰ�С���� ͳ���������� 

  
2016-12-06 hunter__fox

�޸�
2016-12-10 hunter__fox
  ��ӹ���GetVipLog_ID:��ȡָ����ԱID������/��ֵ��¼,��GetVipLogʵ��

2016-12-10 hunter__fox
  NewVipCode:����һ���µĻ�Ա���

2016-12-12 hunter__fox
  NewVip:��[��Ա]����һ����¼,��������Ӧ��ID

2016-12-26 hunter__fox
  NewVip:ָ��[���ֱ���]Ĭ��ֵΪ1

2017-04-28
  ����:���ںŲ��ŵı������ʱʹ�õ��Ӳ�ѯ���ų�department���е��κμ�¼,�Ա���õ�һ���Ѿ����ڵı��

2017-05-08 hunter__fox
  ����:GetVipLog_ID������BUG"����Ա���׼�¼��ֻ�г�ֵ��¼ʱ,�����ʾΪNull"
  ����˷�װ���ʵĴ洢����:WSC_GetVipInfo/WSC_AddVip/WSC_VipReCharge/WSC_GetVipLog
  ����WSC_AddVip/WSC_VipReCharge/WSC_GetVipLog���෵��һ�������,ֻ��һ���ֶ�'retcode',ֻ��һ����¼,���൱��ԭʼ�洢���̵ķ���ֵ
  *���÷�����ԭʼ�洢����һ��.
  
2017-05-09
  ����GetVipLog:
      ����Ա����/��ֵ��¼���һ�ν����ǳ�ֵʱ,�Ի�Ա��ǰ���Ϊ��ƽ����Ļ���
      ������������Ӧ��ʼ������Ϊ0�Ŀ�,�Լ��ֶ��޸����ݿ��л�Ա���������
      GetVipLog���صĽ������ʱ�䵹������

2017-05-10
  WSC_AddVip:����@cMobileNumber�����Ǳ������
             ����@cVipCode����ʹ��,�ò���δ�Ӷ�����ȥ��,������ֵ�Ѿ�����
             ״̬��������һ���ֶ�Msg,����״̬��˵��������
             ״̬:0/-1/-2/-3/-201
  WSC_VipReCharge:״̬�����������ֶ�Msg
  AddVip:��ӷ���ֵ-2(�޿�)/-3(ͣ��)
         �󶨵����л�Աʱ,����Ա��״̬�Ƿ���ͣ��״̬
  VipReCharge:���ɵĳ�ֵ��¼�Ƶ���Ϊ'���ں�'
  GetVipLog_ID:���Ѽ�¼�а����˻�����Ϣ,�������ֶ�[Integral]
               �����˻��ֳ�ֵ��¼
               �����������л��ֶһ���Ʒ��¼
               ������ͳһ������ʹ�û���֧���ļ�¼
               ��һ�Ķ�Ӱ����Щ�洢���̵�������ṹ:GetVipLog,WSC_GetVipLog
  ��Ӵ洢����GetVouchers
  ��Ӵ洢����WSC_GetVouchers,��ȡ���⵽΢�̳ǲ���δ���۵���ȯ�б�

2017-05-11
  WSC_SaleVoucher:����ȯ����
  Saleing:�������۵�(������Ʒ��),��������Ӧ��������ϸ
  SaleingVoucher:��ȯ����/����
  CreateOrder:���ɶ��������¼

2017-05-12
  GetOrdersList:��ȡ�����б�
  GetOrdersDetail:��ȡָ��������ϸ
  WSC_CreateOrder:���ɶ����ķ�װ
  WSC_GetOrdersList:��ȡ�����б�
  WSC_GetOrdersDetail:��ȡָ��������ϸ

2017-05-14
  AddOrderItem:д������ϸ
  WSC_AddOrderItem:��Ӷ�����ϸ
  RecoverVoucher��ȯ����
  WSC_RecoverVoucher:Ϊ����������ȯ

2017-05-15
  ����:��WSC_AddVip���ж�ָ���ĵ绰�����Ƿ���ע��Ϊ��Ա
  �ڽű���󲹳��˻�ȡ�����ŵ��б�������۲�Ʒ�б�Ĳ�ѯ���

2017-05-17
  GetOrdersList�����һ����ѡ����@authcode,������϶�����
  WSC_GetOrdersList�����һ����ѡ����@oauth_code,����ȡ��������������

2017-05-18
  GetVouchers�����˲���,���ڻ�ȡָ����Ա��ʹ�õ�ȯ
             һЩ��ѯ�������˶Խ�ֹ���ڵ��ж�
             �����һ��,ָ��ȯ�����۵��ĸ���Ա,����δ���۵�ȯ,����ֵΪ''
  WSC_GetVouchers������һ������,���ڻ�ȡָ����Ա��ʹ�õ�ȯ
  WSC_GetVipLog����һ��[BonusPoints],��¼ÿ�ʽ����л��ֵ���������
  GetVipLog_ID�������������˻��ֱ䶯��
              �ڻ�ȡ����Դ����ӶԶԻ���ʹ�õ�һЩ�Ӳ�ѯ

2017-05-19
  GetVipLog_ID:���������ۼ�¼��δ��ȡ�˻���¼�Ĵ���

2017-05-24
  GetVouchers:���޲���������·���һ���ս����
  WSC_GetVipLog:ȥ���˷��ص�״̬�����
  WSC_GetVouchers:ȥ���˷��ص�״̬�����
  WSC_CreateOrder:ȥ���˷��ص�״̬�����
  WSC_GetOrdersList:ȥ���˷��ص�״̬�����
  WSC_GetOrdersDetail:ȥ���˷��ص�״̬�����
  WSC_GetVipInfo������ֶ�����ΪӢ��
  WSC_GetVouchers������ֶ�����ΪӢ��
  WSC_CreateOrder������ֶ�����ΪӢ��
  WSC_GetOrdersList������ֶ�����ΪӢ��
  WSC_GetOrdersDetail������ֶ�����ΪӢ��
  
2017-05-26
  CreateOrder�����һ������"@�������",��Ӧ�ֶ�[�������],���ڴ�������ʱ����ָ��ֵ
  WSC_CreateOrder�����һ������,����ָ���������µ��ĸ���
            �ýӿ����ɵĶ���,�������Ϊ'���ں�'
  GetOrdersList:���¶����˷�֧�߼�,����,����/����/��Ա���/��������Ա���ǻ����
  WSC_GetOrdersList:��GetOrdersList���޸�����ʵ���˽ӿ��߼�
  WSC_GetOrdersList:�����һ������@IsEnd���ڽ�һ�����˶����Ľᵥ״̬
     ʹ�÷����ĸı��뿴�ù��̺󸽵�ʾ��

2017-06-06
  GetVouchers:��ȯ������и�Ϊ������������ʶ����ȯ�����Ŀ�겿��(ԭΪ���ű��)
  SaleingVoucher:����Saleingʱ��������������@PayByCash,@PayByCard,��Saleing����һ��
  WSC_SaleVoucher:������Զ�ƥ����ȯ�Ĳ�Ʒ��Ź���
  WSC_SaleVoucher:���ص�״̬��������һ��,���ڷ�����Ӧ�����۵���,ʧ��ʱΪNull

2017-06-07
  ��ȯ��������ֶ�[��Ʒ���],��Ϊ���������Զ�ƥ���˺��ʵĲ�Ʒ���
  GetVouchers:����������[��Ʒ���]
  WSC_GetVouchers:����������[GoodsCode],���ǵ�һ��
  WSC_GetOrdersList:�������CreateTime�е�������
  WSC_GetVipRelevance:���ӹ���,�����ж�������Ա����Ƿ���Խ���������ϵ,�÷��μ���ع���ͷ��ע��

2017-06-09
  GetVouchers:�����һ������@GoodsCode����ͨ����Ʒ��Ź���ȯ,����ָ������ʱ�ò�����Ч
  WSC_GetVouchers:�����һ������@GoodsCode���ڻ�ȡ������(���ں�)�ɷ��ŵ�ȯ

2017-06-12
  ��Ӵ洢����:QueryFormMObject

2017-06-15 
  ��Ӵ洢����Get_BossTotal�����ϰ�С����ͳ������
  ��Ӵ洢����Wsc_Get_BossTotal��Get_BossTotal���õķ�װ

2017-6-19
  �޸� �洢����Get_BossTotal @Boss ��������Ϊ int
  �޸� �洢����WSC_Get_BossTotal @Boss ��������Ϊ int
  ���� WSC_Get_BossTotal ����˵��ʾ��
  
2017-6-20
  ���� ���� dbo.Fun_Birthday ����ָ�����ں������
  ���� ���� GetBirthday ȡָ�������ڹ����յĻ�Ա��Ϣ
  �޸� ���� WSC_GetVipInfo ���� 2���ɴ����� ������ָ������

2017-6-28
  �ָ�Get_BossTotal ��� Xtype ���ַ�ΪСд
      
*/

/******************************************************************************/
--���ںŶԽ���Ҫ�����Ӧ�Ĳ���,����Ա,���㷽ʽ
--����
--Ϊ���ں�/΢�̳ǽ�����Ӧ�Ĳ��������Ա,������������Դ
--���ɵĲ��ű���Ǵ�1��ʼ��С��δʹ�ú�,��󲻳���255
--���ɵĲ���Ա���Ϊ'����01'



Declare @cDepartCode varchar(10)
Select @cDepartCode=��� From department Where ���='���ں�'
If @cDepartCode Is Null
Begin
  Select @cDepartCode=Min(number) 
         From master..spt_values 
         Where type='P' And number>0 
           And number Not In(Select ��� 
                             From department)
  Insert Into department(���,���,ȫ��,�����)
         Values(@cDepartCode,'���ں�','���ں�','102')
End
--����Ա
Declare @cUserCode varchar(10)
Select @cUserCode=��� From pub_user Where ����='���ں�'
If @cUserCode Is Null
Begin
  Set @cUserCode = @cDepartCode + '01'
  Insert Into pub_user(���,����,���ű��,����,Ȩ��,Limit)
         Values(@cUserCode,
                '���ں�',
                @cDepartCode,
                Replicate(Reverse(@cDepartCode),2),
                0,
                Replicate('A',32))
End
--���㷽ʽ
If Not Exists(Select * From ���㷽ʽ�� Where ����='���ں�')
  Insert Into ���㷽ʽ��(����,��������,�����ۿ���,������֧��,�Ƿ�����)
         Values('���ں�','�ֽ�',1,1,0)
Go
--��ȯ������ֶ�[��Ʒ���]
--  �䳤10,�����
--Ϊ����ȯƥ����Ӧ�Ĳ�Ʒ���
If Not Exists(Select * From syscolumns where name='��Ʒ���' And id=object_id('��ȯ��','U'))
Begin
  Alter Table ��ȯ�� Add [��Ʒ���] varchar(10) Null
End
Go
Update ��ȯ�� 
   Set ��Ʒ���=(Select Top 1 ��� 
                 From goods 
                 Where �������='�ֽ�ȯ' 
                 Order By Abs(��������-���)) 
 Where ��ȯ���='����ȯ'
   And ��Ʒ��� Is Null 
Go
/******************************************************************************/
--ȷ��[��Ա���]����Ч����
If object_id('��Ա���','U') Is Null
Begin
  Create Table [��Ա���](
     ��ԱID int not null
    ,��� varchar(50) not null
    )
  Create Index IX_��Ա���_��ԱID On ��Ա���(��ԱID)
  Create Index IX_��Ա���_��� On ��Ա���(���)
End
Go
If col_length('��Ա���','��֤��ʽ') Is Null
Begin
  Alter Table [��Ա���] Add ��֤��ʽ varchar(20) not null default 'WeiXinOpenid'
  Create Index IX_��Ա���_��֤��ʽ On ��Ա���(��֤��ʽ)
End
Go
If col_length('��Ա���','����ʱ��') Is Null
Begin
  Alter Table [��Ա���] Add ����ʱ�� datetime not null default getdate()
End
Go

/*******************************************************************************
Procedure GetVipLog_ID
����:��ȡָ����Ա������/��ֵ��¼
����:
  @iVipID:��Ա��ID
  @cDateStart:��ѡ,��ѯ��ʼ����(������)
  @cDateStart:��ѡ,��ѯ��ֹ����(������)
*/
If Object_ID('[GetVipLog_ID]','P') Is Not Null
  Drop Procedure [GetVipLog_ID]
Go
Create Procedure [GetVipLog_ID]
       @iVipID     int                     ,--��Ա��ID
       @cDateStart varchar(10)='1900-01-01',--��ѯ��ʼ����(������)
       @cDateEnd   varchar(10)=''           --��ѯ��ֹ����(������)
As
Begin
  Declare @dStart datetime
  Declare @dEnd datetime
  If @cDateStart='' Or @cDateStart Is Null Set @cDateStart='1900-01-01'
  If @cDateEnd  ='' Or @cDateEnd   Is Null Set @cDateEnd=Convert(varchar(10),getdate(),120)
  Set @dStart = Convert(datetime,@cDateStart)
  Set @dEnd = Convert(datetime,@cDateEnd) + 1
 
  --ȡ��Ա������Ϣ
  Declare @cVipCode varchar(20),@Tmp_Balance money,@Tmp_Integral int
  Select @cVipCode=���,@Tmp_Balance=�����,@Tmp_Integral=���� From ��Ա Where id =@iVipID

  --�α�:ָ����Աָ��ʱ��֮�����н��׼�¼
  Declare @t_opt table(��Դ varchar(10),id int,���� varchar(20),�������� datetime,��ֵ money,ˢ����� money,���ֱ仯 int,��ǰ��� money ,������� money,��ǰ���� int)
  Declare @��Դ varchar(10),@id int,@���� varchar(20),@�������� datetime,@��ֵ money,@ˢ����� money,@���ֱ仯 int,@��ǰ��� money ,@��� money,@��ǰ���� int
  Declare cur_Tmp Cursor
    For --���ۼ�¼
        Select '����'as ��Դ,id,����,��������,Null As ��ֵ,ˢ�����,0 as ���ֱ仯,��ǰ���,0 As ���,��ǰ���� 
        From sales 
        Where �������� >= @dStart And �������=@cVipCode And ˢ�����<>0
        Union All
        --������¼
        Select '����',id,����,��������,Null,ˢ�����,0,��ǰ���,0 ,��ǰ����
        From orders 
        Where �������� >= @dStart And �Ƿ��ֹ=0 And �������=@cVipCode
        Union All 
        --��ֵ��¼(�����ֳ�ֵ)
        Select '��ֵ',b.id,b.����,b.��������,a.���,0,0,Null,0 ,Null
        From ��ֵ��ϸ�� a 
        Left Join ��ֵ�� b On a.pid=b.id 
        Where b.�������� >= @dStart And a.���=@cVipCode
          And a.���<>0 And IsNull(a.��ע,'')=''
        Union All
        --���ֳ�ֵ
        Select '���ֳ�ֵ',b.id,b.����,b.��������,a.���,0,0-a.��ע,Null,0 ,Null
        From ��ֵ��ϸ�� a 
        Left Join ��ֵ�� b On a.pid=b.id 
        Where b.�������� >= @dStart And a.���=@cVipCode
          And a.���<>0 And Isnull(a.��ע,'')<>''
          And a.��ע Like '[0-9][0-9][0-9]%'
          And DataLength(rtrim(a.��ע))<6
        Union All
        --ͳһ�����л�Ա����֧��(�����붩��)
        Select Case ��Դ When 0 Then'����' Else'����' End,ҵ��ID
              ,Case ��Դ When 0 Then c.���� Else d.���� End
              ,����ʱ��,Null,0,-������ * B.�����ۿ���,Null,0,Null
        From ������ϸ�� As A
        Inner Join ���㷽ʽ�� As B On A.����ID=B.ID
        Left Join sales As c On A.ҵ��ID=c.id
        Left Join orders As d On A.ҵ��ID=d.id
        Where B.�Ƿ�����=1 And B.����='��Ա����' And A.��Դ In (0,1,2) 
          And A.��ע=@cVipCode And A.����ʱ��>= @dStart
        Union All
        --�ɵ������л��ֶһ���¼
        Select '����',A.id,A.����,A.��������,Null,0,0-substring(B.��ע,5,10),��ǰ���,0 As ���,��ǰ����
        From sales As A
        Inner Join sales_detail As b On A.ID=B.����ID
        Where A.�������� >= @dStart And A.�������=@cVipCode And B.��ע Like '������:%'
        Union All
        --���ֱ䶯
        Select b.��ע,b.id,b.����,b.��������,0,0,����,Null,0 ,Null
        From ��ֵ��ϸ�� a 
        Left Join ��ֵ�� b On a.pid=b.id 
        Where b.�������� >= @dStart And a.���=@cVipCode
          And a.����<>0 And ���=0
        Order By �������� Desc,��Դ
  --����:��ԭ����������ÿ�ʽ��׷�����ļ�ʱֵ,���������
  Open cur_Tmp
  Fetch Next From cur_Tmp Into @��Դ,@id,@����,@��������,@��ֵ,@ˢ�����,@���ֱ仯,@��ǰ���,@���,@��ǰ����
  While @@Fetch_status=0
  Begin
    If @��ǰ���� Is Null Set @��ǰ���� = @Tmp_Integral
    Insert Into @t_opt Values(@��Դ,@id,@����,@��������,@��ֵ,@ˢ�����,@���ֱ仯,@��ǰ���,@Tmp_Balance,@Tmp_Integral)
    Set @Tmp_Balance = @Tmp_Balance + isnull(@ˢ�����,0) - isnull(@��ֵ,0)
    Set @Tmp_Integral = @��ǰ���� - @���ֱ仯
    Fetch Next From cur_Tmp Into @��Դ,@id,@����,@��������,@��ֵ,@ˢ�����,@���ֱ仯,@��ǰ���,@���,@��ǰ����
  End
  Close cur_Tmp
  Deallocate cur_Tmp
  --���������
  Select �������� As [DealTim],
         ��Դ     As [Source],
         ����     As [BillCode],
         Case When ��Դ In('��ֵ','���ֳ�ֵ') Then ��ֵ Else ˢ����� End As [Amount],/*���׽��*/
         ������� As [Balance],    /*���*/
         ���ֱ仯 As [BonusPoints],/*���ֱ仯*/
         ��ǰ���� As [Integral]    /*����*/
  From @t_opt
  Where �������� < @dEnd
  Order By �������� Desc
End
Go
----����
--exec GetVipLog_ID 82915

/*******************************************************************************
Procedure NewVipCode
����:����һ���µĻ�Ա���
     �����Ƿ�ʹ��,���ɵı�ž����ٴ�ͨ���ù��̵õ�
����:
  @RetCode:���ڷ������ɵı��,��Ҫһ��varchar(20)����
*/
If Object_ID('[NewVipCode]','P') Is Not Null
  Drop Procedure [NewVipCode]
Go
Create Procedure [NewVipCode]
       @RetCode varchar(20) Output
As
Begin
  --���ɻ�Ա���,��������
  Declare @iBillMax int
  Begin Tran
    Select @iBillMax=IsNull(��󵥺�, 0) + 1 From bills Where ���� = '΢�̳ǻ�Ա' And ����ڼ� = 'WX'
    If @iBillMax Is Null Set @iBillMax = 1
    If @iBillMax=1
      Insert Into bills(����, ����ڼ�, ��󵥺�)Values('΢�̳ǻ�Ա', 'WX', @iBillMax)
    Else
      Update bills Set ��󵥺� = @iBillMax Where ���� = '΢�̳ǻ�Ա' And ����ڼ� = 'WX'
  Commit Tran
  If @@Error<>0 
  Begin
    Rollback Tran
    Return ''
  End
  Set @RetCode= 'WX' + Replace(Str(@iBillMax, 8), ' ', '0')
End
Go
----����
--Declare @Newcode varchar(20)
--Exec NewVipCode @Newcode output
--Select @Newcode

/*******************************************************************************
Procedure NewVip()
����:��[��Ա]����һ����¼,��������Ӧ��ID
����
  @iVipID:�����½���¼��IDֵ,���ʧ�ܷ���-1
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
    Insert Into ��Ա(���,����,����,�绰,����,�Ƿ�ũ��,
                     ����,�ۿ�,״̬,��ֹ����,�Ƿ��ֵ,�����,�����,
                     ���ֱ���,�Ƿ����,��Ա����,��Ա������,��Ա�ȼ�����)
             Values(@cVipCode,@cVipCode,'','','',0,
                    0,1,1,GetDate()+3650,0,0,0,
                    1,0,'',1,1)
    Select @LastError=@@Error,@iVipID = @@Identity
  End
End
Go
----����
--Declare @NewID int
--Exec NewVip @NewID Output
--Select * From ��Ա Where id=@NewID



/*******************************************************************************
Procedure GetVipInfo
����:ȡ��Ա��Ϣ
����
  @cWeiXinCode:��Ա������
  ��ָ���򷵻�����δ�󶨵Ļ�Ա�б�
*/
If Object_ID('[GetVipInfo]','P') Is Not Null
  Drop Procedure [GetVipInfo]
Go
Create Procedure [GetVipInfo]
       @cWeiXinCode varchar(50) = null --��Ա������
As
Begin
  /*
  ͨ�������ȡ��Ա��¼
  ���� @cWeiXinCode Ϊ�󶨵���Ա�����
       ��ָ���򷵻�����û�а�����Ļ�Ա
  */
  If @cWeiXinCode Is Null Or len(@cWeiXinCode)=0
    Select * 
    From ��Ա 
    Where id Not In(Select Distinct ��ԱID 
                    From ��Ա���)
  Else
    Select * 
    From ��Ա 
    Where id In(Select ��ԱID 
                From ��Ա��� 
                Where ���=@cWeiXinCode)
End
Go
----����
--Exec GetVipInfo 'hHzy6qbEQfs_reKJ9ymW'
--Exec GetVipInfo
--Go

/*******************************************************************************
Procedure AddVip
����:��ӻ�Ա
����
  @cWeiXinCode:��Ա������
  @cMobileNumber:�ֻ���
  @cName:����
  @cBirthday:����,yyyy-mm-dd��ʽ
  @bIsLunar:��ũ��,0��ʾ�����ǹ���
  @cVipCode:�󶨵���Ա���
  --@cVipCode��ָ�����½���Ա
  --ָ����󶨵����л�Ա
  --ָ���Ļ�Ա��Ų�������ʧ�ܷ���-1
����ֵ: 0---�ɹ�
       -1---�Ѱ󶨵�������
       -2---�޴˻�Ա
       -3---��Ա��ͣ��
2016-12-12 hunter__fox                  ����NewVip����»�Ա��¼����
*/
If Object_ID('[AddVip]','P') Is Not Null
  Drop Procedure [AddVip]
Go
Create Procedure [AddVip]
       @cWeiXinCode varchar(50)
      ,@cMobileNumber varchar(20)= ''--�ֻ���
      ,@cName varchar(20)        = ''--����
      ,@cBirthday varchar(10)    = '1900-01-01'--����,yyyy-mm-dd��ʽ,��Χ1900-01-01��2079-06-06
      ,@bIsLunar bit             = 0--��ũ��,0��ʾ�����ǹ���
      ,@cVipCode varchar(20)     = ''--Ҫ�󶨵Ļ�Ա���
                   --��ָ�����½�һ����Ա,ָ����󶨵����л�Ա
                   --���ָ���Ļ�Ա������,��ʧ�ܷ���-1
As
Begin
  Declare @iVipID int,@VipState int
  Declare @dBirthday datetime
  --��֤:���Ψһ
  If Exists(Select * From ��Ա��� Where ��� = @cWeiXinCode)
  Begin
    Raiserror(N'���"%s"�Ѿ���ĳ��Ա��',16,1,@cWeiXinCode)
    Return -1
  End
  
  If @cVipCode='' Or @cVipCode Is Null
  Begin
    --�޻�Ա,������
    Exec NewVip @iVipID Output
    Set @VipState = 1
  End
  Else
    --�л�Ա,ȡID
    Select @iVipID=id,@VipState=״̬ From ��Ա Where ���=@cVipCode
  If @iVipID Is Null Return -2 --�޴˻�Ա
  If @VipState = 0 Return -3 --��Ա��ͣ��
  
  Update ��Ա
     Set �绰 = Case When IsNull(@cMobileNumber,'')=''
                     Then �绰 Else @cMobileNumber End
        ,���� = Case When IsNull(@cName,'')=''
                     Then ���� Else @cName End
        ,���� = Case When IsNull(@cBirthday,'')=''
                     Then ���� Else Convert(datetime,@cBirthday) End
        ,�Ƿ�ũ�� = Case When @bIsLunar Is Null 
                         Then �Ƿ�ũ�� Else @bIsLunar End
        ,��Ա���� = Case When IsNull(@cBirthday,'')=''
                         Then ��Ա���� Else Right(Replace(@cBirthday,'-',''),4) End
  Where ID=@iVipID
  --���л�Ա��
  Insert Into ��Ա���(��ԱID,���)Values(@iVipID,@cWeiXinCode)
End
Go
--����
--��������"��ɹ�"������Ӧ���û��ʹ�õ������
--Delete From ��Ա��� Where ��� In('{���԰���}','{���԰���2}','{���԰���3}')
--Exec AddVip '{���԰���6}','{�ֻ���}','{΢��}'  --�������ӻ�Ա�������,��һ�λ�ɹ�
--Exec GetVipInfo '{���԰���6}'
--Exec AddVip '{���԰���2}','{�ֻ���}','{΢��}','1983-10-23',1,'999999999'  --�󶨵����л�Ա,��һ�λ�ɹ�
--Exec GetVipInfo '{���԰���2}'
--Exec AddVip '{���԰���6}','{�ֻ���}','{΢��}',Null,Null,'000000' --��ʧ��:ָ���Ļ�Ա������
--Exec GetVipInfo '{���԰���6}'
--Go

/*******************************************************************************
Procedure VipReCharge
����:��Ա��ֵ
����:
  @cWeiXinCode:��Ա�󶨵����
  @nAddMoney:��ֵ���
  @cWeiXinOrderCode:��Ӧ��΢�̳�֧��������
����ֵ
    0 :�ɹ�
    -1:�޴˻�Ա
    -2:���ɶ�����ʧ��
    -3:���ɳ�ֵ��¼ʧ��
*/
If Object_ID('[VipReCharge]','P') Is Not Null
  Drop Procedure [VipReCharge]
Go
Create Procedure [VipReCharge]
       @cWeiXinCode varchar(50),     --��Ա������
       @nAddMoney money=0,           --��ֵ���
       @cWeiXinOrderCode varchar(50) --΢�̳�֧��������
As
Begin
  Declare @cDepart varchar(10) --���ű��(΢�̳�,�޿ɵ�¼��Ա)
  Declare @cUser varchar(10)   --�Ƶ���(΢�̳ǲ���Ա,���ɵ�¼)
  Declare @iPayMode int        --���㷽ʽ(΢�̳�,�ڽ��㷽ʽ����Ϊ������֧��,������)
  Set @cDepart  = ''
  Set @cUser    = ''
  Select @cDepart = ��� From department Where ���='���ں�'
  Select @cUser=��� From pub_user Where ����='���ں�'
  Select @iPayMode = id From ���㷽ʽ�� Where ����='���ں�'
  If @iPayMode Is Null Set @iPayMode = -1 
  --ȡ��ԱID����
  Declare @iVipID int, @cVipCode varchar(20)
  Select @iVipID=id,@cVipCode=��� From ��Ա Where id In(Select ��ԱID From ��Ա��� Where ��� = @cWeiXinCode)
  If @iVipID Is Null
  Begin
    Raiserror(N'���"%s"û�����Ա��',16,1,@cWeiXinCode)
    Return -1
  End
  --���ɳ�ֵ����,��������
  --���ǳ�ֵ�ı�Ҫ����,�����ɵĵ��ſ��Զ���
  Declare @cOrderNo varchar(20), @cDate varchar(10), @iBillMax int
  Set @cDate=Convert(varchar(10), GetDate(), 120)
  Begin Tran tran_VipReCharge_1
    Select @iBillMax=IsNull(��󵥺�, 0) + 1 From bills Where ���� = '��ֵҵ��' And ����ڼ� = @cDate
    If @iBillMax Is Null Set @iBillMax = 1
    If @iBillMax=1
      Insert Into bills(����, ����ڼ�, ��󵥺�)Values('��ֵҵ��', @cDate, @iBillMax)
    Else
      Update bills Set ��󵥺� = @iBillMax Where ���� = '��ֵҵ��' And ����ڼ� = @cDate
  Commit Tran tran_VipReCharge_1
  If @@Error<>0 
  Begin
    Rollback Tran tran_VipReCharge_1
    Return -2
  End
  Set @cOrderNo = Replace(@cDate, '-', '') + '-' + Replace(Str(@iBillMax, 5), ' ', '0')
  --������ֵ��¼,�������н���
  --Ҫ���ı�ͬ��
  Declare @iBusinessID int
  Begin Tran tran_VipReCharge_2
    Insert Into ��ֵ��(����, ����, ���ű��, �Ƶ���,���ϼ�, �ֽ�ϼ�, ״̬, ��ע)
           Values(@cOrderNo, @cDate, @cDepart, @cUser, @nAddMoney, 0, 1, '΢��֧��������:' + @cWeiXinOrderCode)
    Set @iBusinessID = @@Identity
    Insert Into ��ֵ��ϸ��(PID, ���, ���, �ֽ�)
           Values(@iBusinessID, @cVipCode, @nAddMoney, 0)
    Insert Into ������ϸ��(ҵ��ID, ��Դ, ����ID, ������,��ע)
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
----����
--Exec GetVipInfo 'hHzy6qbEQfs_reKJ9ymW'
--Exec VipReCharge 'hHzy6qbEQfs_reKJ9ymW',0.01,'1234567890'
--Exec GetVipInfo 'hHzy6qbEQfs_reKJ9ymW'
--declare @LasID int
--Select @LasID=max(ID) From ��ֵ��
--select * from ��ֵ�� where id=@LasID
--select * from ��ֵ��ϸ�� where pid=@LasID
--select * from ������ϸ�� where ҵ��ID=@LasID and ��Դ=3
/*******************************************************************************
Procedure VipBonusPoints
����:�޸Ļ�Ա����
����:
     @cDepart:����
     @cUser:����Ա
     @VipCode:��Ա����
     @Add:t����������
     @Description:���ֱ䶯ԭ��
*/
If Object_ID('VipBonusPoints','P') Is Not Null
  Drop Procedure [VipBonusPoints]
Go
Create Procedure [VipBonusPoints]
       @cDepart     varchar(10)   ,--����
       @cUser       varchar(10)   ,--����Ա
       @VipCode     varchar(20)   ,--��Ա�����
       @Add         money=0       ,--��������ֵ(����Ϊ����,0Ϊ����)
       @Description varchar(20)='' --���ֱ䶯ԭ��
As
Begin
  Declare @״̬ int,@�Ƿ��ֵ bit,@���� money,@��ֹ���� datetime
  Select @״̬=״̬,@�Ƿ��ֵ=�Ƿ��ֵ,@����=����,@��ֹ����=��ֹ���� From ��Ա Where ���=@VipCode
  If @״̬ Is Null
    Return -1 --�޴˻�Ա
  Else If @״̬ = 0
    Return -2 --��δ����
  Else If @�Ƿ��ֵ=1
    Return -3 --���ѹ�ʧ
  Else If @��ֹ����< getdate()
    Return -4 --���ѹ���
  
  If @Add <> 0
  Begin
    --���ɳ�ֵ����,��������
    --���ǳ�ֵ�ı�Ҫ����,�����ɵĵ��ſ��Զ���
    Declare @cOrderNo varchar(20), @cDate varchar(10), @iBillMax int
    Set @cDate=Convert(varchar(10), GetDate(), 120)
    Exec getbillmax '��ֵҵ��', @cDate, @iBillMax OUTPUT
    Set @cOrderNo = Replace(@cDate, '-', '') + '-' + Replace(Str(@iBillMax, 5), ' ', '0')
    
    Declare @iBusinessID int
    Begin Tran
      Insert Into ��ֵ��(����, ����, ���ű��, �Ƶ���,���ϼ�, �ֽ�ϼ�, ״̬, ��ע)
             Values(@cOrderNo, @cDate, @cDepart, @cUser, 0, 0, 1, @Description)
      Set @iBusinessID = @@Identity
      Insert Into ��ֵ��ϸ��(PID, ���, ���, �ֽ�,����,��ע)
             Values(@iBusinessID, @VipCode, 0, 0, @Add, @Description)
      Update ��Ա Set ���� = @���� + @Add Where ��� = @VipCode
    Commit Tran  
    If @@Error<>0 
    Begin
      Rollback Tran
      Return -5 --���ֱ䶯ʧ��
    End
  End
  Return 0
End
Go
/*******************************************************************************
Procedure GetVipLog
����:��ȡָ����Ա������/��ֵ��¼
����:
  @cWeiXinCode:��Ա�󶨵����
  @cDateStart:��ѯ��ʼ����(������)
  @cDateStart:��ѯ��ֹ����(������)
*/
If Object_ID('[GetVipLog]','P') Is Not Null
  Drop Procedure [GetVipLog]
Go
Create Procedure [GetVipLog]
       @cWeiXinCode varchar(50),--��Ա�󶨵����
       @cDateStart varchar(10)='1900-01-01', --��ѯ��ʼ����(������)
       @cDateEnd varchar(10)=''  --��ѯ��ֹ����(������)
As
Begin
  If @cWeiXinCode='' Or @cWeiXinCode Is Null
  Begin
    Raiserror(N'��Ҫָ��һ�����',16,1)
    Return -1
  End
  Declare @iVipID int
  Select @iVipID=id From ��Ա Where id In(Select ��ԱID From ��Ա��� Where ��� = @cWeiXinCode)
  If @iVipID Is Null
  Begin
    Raiserror(N'���"%s"û�����Ա��',16,2,@cWeiXinCode)
    Return -2
  End
  Exec GetVipLog_ID @iVipID,@cDateStart,@cDateEnd
  Return 0
End
Go
----����
----ע:�������ݿ����������ֶ��޸�,������ǡ
--Delete From ��Ա��� Where ��� = '{���԰���2}'
--Exec AddVip '{���԰���2}','{�ֻ���}','{΢��}','1983-10-23',1,'999999999'
--Exec GetVipLog '{���԰���2}'
--Exec GetVipLog '{���԰���2}','2016-10-12',''
--Exec GetVipLog '{���԰���2}','2016-10-12','2016-10-30'
/*******************************************************************************
Procedure GetVouchers
����:��ȡ��ȯ��Ϣ
����:@DepartmentCode:���ű��
     @VipCode:��Ա���
     @VoucherCode:��ȯ���
     @GoodsCode:ȯ�Ĳ�Ʒ���
��������������:
  ָ��@DepartmentCode����ȡ���⵽�ò����ҳ����۵���ȯ�б�(�˲��ſ����۵���ȯ)
  ָ��@VipCode���ȡ���۵�ָ����Ա����δʹ�õ���ȯ�б�,���޶����ĸ��������۵�
  ָ��@VoucherCode���ȡָ����ȯ����Ϣ,���޶�ȯ״̬�����۲��ź͹���
  ָ����@DepartmentCode������@VipCode��@VoucherCode
  ָ����@VipCode������@VoucherCode,@GoodsCode
  ָ����@VoucherCode������@GoodsCode
*/

If Object_ID('[GetVouchers]','P') Is Not Null
  Drop Procedure [GetVouchers]
Go
Create Procedure [GetVouchers]
       @DepartmentCode varchar(20)='',--��ȡ���⵽ָ�����Ų�����δ���۵���ȯ
       @VipCode        varchar(20)='',--��ȡ���۸�ָ����Ա����ȯ
       @VoucherCode    varchar(20)='',--��ȡָ�������ȯ
       @GoodsCode      varchar(10)='' --��ȡָ����Ʒ��ŵ�ȯ,����ָ��@DepartmentCodeʱ��Ч
As
Begin
  If IsNull(@DepartmentCode, '') <> '' And IsNull(@GoodsCode, '') <> ''
    Select A.��Ʒ���,A.���,A.���,'�ѳ���' As ״̬
          ,Case When IsNull(A.�������,'0')='0' Then '' Else A.������� End As ��Ա���
          ,A.��ֹ����,A.����
    From ��ȯ�� As A
    Left Join ��ȯ����� As B On A.���=B.���
    Where A.״̬=1 And B.�������=@DepartmentCode
      And A.��ֹ����>=getdate()
      And A.��Ʒ���=@GoodsCode
  Else If IsNull(@DepartmentCode, '') <> '' 
    Select A.��Ʒ���,A.���,A.���,'�ѳ���' As ״̬
          ,Case When IsNull(A.�������,'0')='0' Then '' Else A.������� End As ��Ա���
          ,A.��ֹ����,A.����
    From ��ȯ�� As A
    Left Join ��ȯ����� As B On A.���=B.���
    Where A.״̬=1 And B.�������=@DepartmentCode
      And A.��ֹ����>=getdate()
  Else If IsNull(@VipCode, '') <> ''
    Select A.��Ʒ���,A.���,A.���,'������' As ״̬
          ,@VipCode As ��Ա���
          ,A.��ֹ����,A.����
    From ��ȯ�� As a
    Inner Join ��ȯ���۱� As b On a.���=b.���
    Where b.�������=@VipCode
      And a.״̬=2 and a.�Ƿ����=0
      And a.��ֹ����>=getdate()
  Else If IsNull(@VoucherCode, '') <> ''
    Select A.��Ʒ���,A.���,A.���
          ,Case A.״̬+A.�Ƿ����
           When 0 Then 'δ����'
           When 1 Then '�ѳ���'
           When 2 Then '������'
           When 3 Then '��ʹ��'
           Else '��Ч'
           End As ״̬
          ,Case When IsNull(A.�������,'0')='0' Then IsNull(C.�������,'') Else A.������� End As ��Ա���
          ,A.��ֹ����,A.����
    From ��ȯ�� As A
    Left Join ��ȯ����� As B On A.���=B.���
    Left Join ��ȯ���۱� As C On A.���=C.���
    Where A.���=@VoucherCode
  Else
  Begin
    Select Top 0 ��Ʒ���,���,���,'' As ״̬,������� As ��Ա���,��ֹ����,���� From ��ȯ��
    Return -1 --�޲���
  End
  Return 0
End
Go
/*******************************************************************************
Procedure Saleing
����:�������ۼ�¼
����:
    @DepartmentCode:���۲���
    @UserCode:����Ա
    @CDate:��������
    @GoodsCode:��Ʒ���
    @GoodsCount:��Ʒ����
    @Price:���۵���
    @VipCode:�������
    @Ret_BillCode:���������۵�����,OUTPUT
�ô洢���̽������ڵ�һ��Ʒ��������Ϊ
������ƷӦѭ������SaleingEx
*/
If Object_ID('[Saleing]','P') Is Not Null
  Drop Procedure [Saleing]
Go
Create Procedure [Saleing]
    @DepartmentCode varchar(10)     ,--���۲���
    @UserCode varchar(10)           ,--����Ա
    @CDate varchar(10)              ,--��������
    @GoodsCode varchar(10)          ,--��Ʒ���
    @GoodsCount int                 ,--��Ʒ����
    @Price money                    ,--����
    @PayByCash money=0              ,--�ֽ�֧�����
    @PayByCard money=0              ,--��Ա��֧�����    
    @VipCode varchar(20)            ,--�������
    @Ret_BillCode varchar(20) OutPut --���������۵�����
As
Begin
  --���ɵ���
  Declare @BillMax int,@SalesID int
  Exec getbillmax '����', @CDate, @BillMax Output
  Set @Ret_BillCode = Convert(varchar(6),Convert(datetime, @CDate), 12) 
                    + '-' + Right('0000' + Convert(varchar(10), @BillMax), 5)
  Declare @������� varchar(20),@ԭ�� money,@��ǰ��� money,@��ǰ���� money
  Set @�������=Case When @VipCode='' Or @VipCode Is Null Then '��������' Else '��Ա����' End
  Select @ԭ��=�������� From goods Where ���=@GoodsCode And �Ƿ�����=1
  --����������Ӽ�¼
  Begin Transaction tran_Saleing
  Insert Into sales(����,���ű��,�Ƶ���,����,�������,�ᵥ,�������,��ǰ���,��ǰ����
                   ,�����ϼ�,���ϼ�,�ۿۺϼ�
                   ,�ֽ���,ˢ�����,��ȯ���)
         Values(@Ret_BillCode,@DepartmentCode,@UserCode,@CDate,@�������,1,@VipCode,0,0
               ,@GoodsCount,@GoodsCount*@Price,@GoodsCount*(@ԭ��-@Price)
               ,@PayByCash,@PayByCard,@GoodsCount*@Price-@PayByCash-@PayByCard)
  If @@Rowcount > 0
  Begin
    Set @SalesID = SCOPE_IDENTITY()
    Insert Into sales_detail(����ID,���,����,����,���,ԭ��,�������)
           Values(@SalesID,@GoodsCode,@Price,@GoodsCount,@GoodsCount*@Price,@ԭ��,Case When @ԭ��=@Price Then 0 Else 1 End)
  End
  --�������������л�Ա����������Ϣ
  If @�������='��Ա����'
  Begin
    Select @��ǰ���=�����, @��ǰ����=���� From ��Ա where ���=@VipCode
    Update sales Set ��ǰ���=@��ǰ���,��ǰ����=@��ǰ���� Where ID=@SalesID
  End
  --�ύ����
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
����:��ȯ����
����:
    --������Ϣ
    @DepartmentCode:���۲���
    @UserCode:����Ա
    @CDate:��������
    --��Ʒ��Ϣ
    @GoodsCode:��Ʒ���
    @Price:�ۼ�
    --֧����Ϣ
    @PayByCash:�ֽ�֧�����
    @PayByCard:��Ա��֧�����
    @VipCode:��Ա���
    --��ȯ��Ϣ
    @VoucherCode:��ȯ���
�˹��̵���Saleing������Ӧ�����ۼ�¼(��sales/sales_detail��),��[��ȯ���۱�]���ɼ�¼,����[��ȯ��][״̬][�Ƿ����]�ֶ�
����ֵ:
  0:�ɹ�
  -1:�������۵�ʧ��
  -100:ȯ����Ч
  -101:ȯ�ѹ���
  -102:ȯ����ʧ��
  -103:ȯδ����
  -105:ȯ�Ѿ�����
  -106:ȯ��ʹ��
*/
If Object_ID('[SaleingVoucher]','P') Is Not Null
  Drop Procedure [SaleingVoucher]
Go
Create Procedure [SaleingVoucher]
       @DepartmentCode varchar(10)       ,--���۲���
       @UserCode       varchar(10)       ,--�Ƶ���
       @CDate          varchar(10)=''    ,--��������
       @GoodsCode      varchar(10)       ,--��Ʒ���
       @Price          money      =0     ,--�ۼ�
       @PayByCash      money      =0     ,--�ֽ�֧�����
       @PayByCard      money      =0     ,--��Ա��֧�����
       @VipCode        varchar(20)=''    ,--�������
       @VoucherCode    varchar(20)='0000',--ȯ���,���Ϊ0000��ȯ������¼
       @Ret_BillCode   varchar(20) Output--��Ӧ���۵���,����ֵ
As
Begin
  If @CDate='' Or @CDate Is Null 
    Set @CDate = Convert(varchar(10), GetDate(), 120) --����ʮ����֮�����ʵ���һ��
  
  Declare @Ret int--,@Ret_BillCode varchar(20)
  Declare @��� money,@״̬ int,@��ֹ���� datetime
  Select @���=���,@״̬=״̬+�Ƿ����,@��ֹ����=��ֹ���� From ��ȯ�� Where ���=@VoucherCode
  --���:ȯ�����������۲���δ����ֹ���ڵ�
  If @@RowCount = 0           Set @Ret = -100
  Else If @��ֹ����<getdate() Set @Ret = -101
  Else If @״̬ <> 1          Set @Ret = -103 - @״̬ --(-103δ����/-105������/-106��ʹ��)
  Else                        Set @Ret = 0
  If @Ret > 0 Return @Ret
  --���������޸�����
  Begin Transaction tran_SaleingVoucher
  --�������ۼ�¼
  Exec @Ret=Saleing @DepartmentCode,@UserCode,@CDate,@GoodsCode,1,@Price,@PayByCash,@PayByCard,@VipCode,@Ret_BillCode Output
  If @Ret<>0
  Begin
    Rollback Transaction tran_SaleingVoucher
    Return @Ret --���ɽ���ʧ��
  End
  If @VoucherCode<>'0000'
  Begin
    --������ȯ���۱��¼
    Insert Into ��ȯ���۱�(���,���,���,����,���ű��,�������,����ʱ��,����)
           Values(@VoucherCode, @���, @Price, @CDate, @DepartmentCode, @VipCode, getdate(), @Ret_BillCode)
    --������ȯ��״̬
    Update ��ȯ�� Set ״̬=2,�Ƿ����=0,�������=@VipCode Where ���=@VoucherCode
  End
  --�ύ����
  Commit Transaction tran_SaleingVoucher
  If @@ERROR<>0
  Begin
    Rollback Transaction tran_SaleingVoucher
    Return -102 --����ʧ��
  End
  Return 0 --�ɹ�
End
Go
/*******************************************************************************
Procedure CreateOrder
����:����һ�Ŷ���
����:
    @����:Ϊ�����½���,��Ϊ�����޸����е�
    @���ű��:���۲���
    @�Ƶ���
    ������Ϊ��ѡ����
����ֵ:
  ���ض�Ӧ�Ķ���ID,�������ֵС��1��ʧ��
*/
If Object_ID('[CreateOrder]','P') Is Not Null
  Drop Procedure [CreateOrder]
Go
Create Procedure [CreateOrder]
       @����       varchar(20)=NULL  OUTPUT,--Ϊ�����½���,��Ϊ�����޸����е�
       @���ű��   varchar(10)       ,--���۲���
       @�Ƶ���     varchar(10)       ,--�Ƶ���
       @�̻������� varchar(32)=''    ,--�ⲿת��Ķ���,��Ӧ���ⲿ������
       @����       varchar(10)=Null  ,--��������
       @������λ   varchar(10)=Null  ,--������λ
       @ȡ������   varchar(10)=Null  ,--ȡ������
       @ȡ��ʱ��   datetime   =Null  ,--ԤԼȡ��ʱ��
       @�������   varchar(20)=''    ,--��Ա�����
       @��ϵ��ʽ   varchar(50)=Null  ,--��ϵ��ʽ
       @��ע       varchar(100)=Null ,--
       @�ͻ���ַ   varchar(100)=Null ,--
       @���ϼ�   money      =0     ,--δ�ۿ�ʱ�ϼ�
       @�Żݺϼ�   money      =0     ,--���ò���,���ϼ�-�Żݺϼ�=ʵ�ս��,ʵ�ս��-����-����-ˢ�����-��ȯ=Ƿ��
       @����       money      =0     ,--�����������(Ĩ��)
       @����       money      =0     ,--�µ�ʱ���ֽ�֧����
       @ˢ�����   money      =0     ,--�µ�ʱ�û�Ա��֧����
       @��ȯ       money      =0     ,--�µ�ʱ����ȯ�ֿ۵�
       @�ᵥ       bit        =Null  ,--�Ƿ�ᵥ
       @��ֹ       bit        =Null  ,--�����ֹ,@�ᵥ����Ϊ0
       @�������   varchar(50)=''     --�����̳����ɵĶ���Ϊ'���ں�',�����޸�
As
Begin
  Declare @OrderID int--�ɹ����ض���ID,ʧ�ܷ���0��ֵ
  Declare @��λ���� varchar(50),@��ǰ��� money,@��ǰ���� money,@Ӧ�ս�� money,@Ƿ�� money,@�������� varchar(10)
  Set @��ֹ=IsNull(@��ֹ,0)
  If @��ֹ=1 Set @�ᵥ=0 Else Set @�ᵥ=IsNull(@�ᵥ,0)
  If @������λ Is Null Set @������λ=@���ű��
  If @ȡ������ Is Null Set @ȡ������=@������λ
  If @ȡ��ʱ�� Is Null Set @ȡ��ʱ��=GetDate()
  If @�������=''
    Set @��λ����=''
  Else
    Select @��λ����=���� From ��Ա Where ���=@�������
  Set @Ӧ�ս��=@���ϼ�-@�Żݺϼ�
  Set @Ƿ��= Case When @�ᵥ=1 Then 0 Else @Ӧ�ս��-@����-@����-@ˢ�����-@��ȯ End
  Set @��������=Case When @�ᵥ=1 Or @��ֹ=1 Then Convert(varchar(10),GetDate(),120) Else Null End
  If @���� Is Null Or @����=''
  Begin
    If @���� Is Null
      Set @���� = Convert(varchar(10), GetDate(), 120)
    Declare @BillMax int,@SalesID int
    Exec getbillmax '����', @����, @BillMax Output
    Set @���� = Convert(varchar(6),Convert(datetime, @����), 12) 
              + '-' + Right('0000' + Convert(varchar(10), @BillMax), 5)
    Insert orders(����,����,���ű��,�Ƶ���,������λ,ȡ������,ȡ��ʱ��,�̻�������
                 ,�������,��ϵ��ʽ,��ע,�ͻ���ַ,��λ����,��ǰ���,��ǰ����
                 ,���ϼ�,�Żݺϼ�,Ӧ�ս��,����,����,ˢ�����,��ȯ,Ƿ��
                 ,�ᵥ,�Ƿ��ֹ,��������,�������)
           Values(@����,@����,@���ű��,@�Ƶ���,@������λ,@ȡ������,@ȡ��ʱ��,@�̻�������
                 ,@�������,@��ϵ��ʽ,@��ע,@�ͻ���ַ,@��λ����,0,0
                 ,@���ϼ�,@�Żݺϼ�,@Ӧ�ս��,@����,@����,@ˢ�����,@��ȯ,@Ƿ��
                 ,@�ᵥ,@��ֹ,@��������,@�������)
    Select @OrderID=SCOPE_IDENTITY()
    If @������� <> ''
    Begin
      Select @��ǰ���=�����, @��ǰ����=���� From ��Ա where ���=@�������
      Update orders Set ��ǰ���=@��ǰ���,��ǰ����=@��ǰ���� Where ID=@OrderID
    End
  End
  Else
  Begin
    --����ֻ���޸Ĳ����ֶ�
    Select @OrderID=id From orders Where ����=@����
    Update orders 
       Set ��ϵ��ʽ=IsNull(@��ϵ��ʽ,��ϵ��ʽ)
          ,ȡ��ʱ��=IsNull(@ȡ��ʱ��,ȡ��ʱ��)
          ,������λ=IsNull(@������λ,������λ)
          ,��ע    =IsNull(@��ע,��ע)
          ,�ͻ���ַ=IsNull(@�ͻ���ַ,�ͻ���ַ)
          ,ȡ������=IsNull(@ȡ������,ȡ������)
          ,��������=IsNull(@��������,��������)
          ,�ᵥ    =IsNull(@�ᵥ,�ᵥ)
          ,�Ƿ��ֹ=IsNull(@��ֹ,�Ƿ��ֹ)
          ,Ƿ��    =IsNull(@Ƿ��,Ƿ��)
     Where id=@OrderID
  End
  Return @OrderID
End
Go
/*******************************************************************************
Procedure AddOrderItem
����:����һ��������ϸ
*/
If Object_ID('[AddOrderItem]','P') Is Not Null
  Drop Procedure [AddOrderItem]
Go
Create Procedure [AddOrderItem]
       @PID         int        ,--����ID
       @GoodsCode   varchar(10),--��Ʒ���
       @GoodsCount  int        ,--��Ʒ����
       @Price       money      ,--����,δ�ۿ۵ĵ���
       @TotalAmount money       --���,�ۿۺ��ܽ��
As       
Begin
  Declare @���� varchar(30),@��� varchar(20),@�ۿ� money,@�Żݽ�� money
  Select @����=����,@���=��� From goods Where �Ƿ�����=1 And ���=@GoodsCode
  Set @�ۿ� = @TotalAmount * 100 / @Price / @GoodsCount
  Set @�Żݽ�� = @Price * @GoodsCount - @TotalAmount
  Insert Into orders_detail(����ID,���,����,���,����,�ۿ�,����,���,�Żݽ��,�ᵥ)
         Values(@PID,@GoodsCode,@����,@���,@Price,@�ۿ�,@GoodsCount,@TotalAmount,@�Żݽ��,0)
  If @@Error<>0 Return -1
  Return 0
End
Go
/*******************************************************************************
Procedure GetOrdersList
����:�õ������б�
����:
     @Department:���ű��
     @StartDate:��ʼ����
     @EndDate:��������
     @OrderCode:������
     @VipCode:��Ա���
������Ӧ�Ķ����б�
������:����,����,��Ա���߻���,���ȼ�:����>����>��Ա
       ������Ĭ��ֵ
*/
If Object_ID('[GetOrdersList]','P') Is Not Null 
  Drop Procedure [GetOrdersList]
Go
Create Procedure [GetOrdersList]
       @Department varchar(10)             ,--���ű��
       @StartDate  varchar(10)='1900-01-01',--���ڷ�Χ:��ʼ����
       @EndDate    varchar(10)=''          ,--���ڷ�Χ:��������
       @OrderCode  varchar(20)=''          ,--������
       @VipCode    varchar(20)=''          ,--��Ա���
       @authcode   varchar(50)=''           --Ѱ�Ҵ˻�Ա���¼���Ա��ض���,����@VipCode=''ʱ��Ч
As
Begin
  If IsNull(@StartDate,'')='' Set @StartDate='1900-01-01'
  If IsNull(@EndDate,'')='' Set @EndDate=convert(varchar(10),getdate(),120)
  If @OrderCode<>''
    Begin--��ѯָ�����ŵĶ���,������������
      Select * ,0 As Hierarchy From orders 
      Where ����=@OrderCode
    End
  Else If @Department Is Null
    Begin--��ѯ���ںŲ����Ķ���
      Select * ,0 As Hierarchy From orders 
      Where �������='���ں�' And ���� Between @StartDate And @EndDate
    End
  Else If @Department <> ''
    Begin--��ѯָ�������Ƶ��Ķ���
      Select * ,0 As Hierarchy From orders 
      Where ���ű��=@Department And ���� Between @StartDate And @EndDate
    End
  Else If @VipCode<>''
    Begin--��ѯָ����Ա�Ķ���
      Select * ,0 As Hierarchy From orders
      Where ���� Between @StartDate And @EndDate And �������=@VipCode
    End
  Else If @authcode<>''
    Begin--��ѯָ����Ա���¼���Ա��������
      --����Ҫ���й��˵Ļ�Ա���ɵ������@VipCodes��
      Declare @VipCodes table(VipCode varchar(20),[Level] int)
      Declare @tmp Table(id int,[level] int)--�������������web_user_id
      --��һ�������
      Insert Into @tmp
             Select invited_id,1 
             From web_user_invited_link 
             Where [user_id] In (Select [user_id] 
                                 From web_oauth_login 
                                 Where oauth_id=@authcode)
      --�ڶ��������
      Insert Into @tmp 
             Select invited_id,2 
             From web_user_invited_link 
             Where [user_id] In (Select id From @tmp)
      --�õ���Ӧ�Ļ�Ա���
      Insert Into @VipCodes 
             Select d.���,a.[level]
             From      @tmp                As a
             Left Join dbo.web_oauth_login As b On A.id      =b.[user_id]
             Left Join ��Ա���            As c On b.oauth_id=c.���
             Left Join ��Ա                As d On c.��ԱID  =d.ID

      Select a.* ,b.[Level] As Hierarchy From orders As A
      Inner Join @VipCodes As b On a.�������=b.VipCode
      Where ���� Between @StartDate And @EndDate 
        And ����=@OrderCode 
    End
  Else
    Begin--û�нᶨ��������
      Select Top 0 *,0 As Hierarchy From orders
    End
  If @@Error<>0
    Return -1
  Return 0
End
Go
/*******************************************************************************
Procedure GetOrdersDetail
����:��ȡ������ϸ
����:
     @OrderID:����ID
*/
If Object_ID('[GetOrdersDetail]','P') Is Not Null
  Drop Procedure [GetOrdersDetail]
Go
Create Procedure [GetOrdersDetail]
       @OrderID int
As
Begin
  Select * From orders_detail Where ����ID=@OrderID
  Return 0
End
Go
/*******************************************************************************
Procedure RecoverVoucher
����:��ȯ����
����:
     @Department:����
     @Source:��Դ,0Ϊ����,1Ϊ����
     @RecordID:����ID:��Ӧ���۵�/����������¼ID
     @VoucherCode:��ȯ���
     @Amount:���:�ֿ۽��
     @Remark:��ע
����ֵ:
       0:�ɹ�
       -1:ȯ�����Ч
       -2:ȯδ����
       -3:ȯ�ѻ���
       -4:ȯ�ѹ���
       -5:д���ռ�¼ʧ��
*/
If Object_ID('[RecoverVoucher]','P') Is Not Null
  Drop Procedure [RecoverVoucher]
Go
Create Procedure [RecoverVoucher]
       @Department  varchar(10),--���ű��
       @Source      int        ,--��Դ,0Ϊ����,1Ϊ����
       @RecordID    int        ,--����ID:��Ӧ���۵�/����������¼ID
       @VoucherCode varchar(20),--��ȯ���
       @Amount      money      =0,--���:�ֿ۽��
       @Remark      varchar(50)='' --��ע
As
Begin
  --���;��ȯ״̬=2,�Ƿ����=0,��ֹ����<getdate(),@Amount<=���
  Declare @Return int,@msg varchar(100)
  Declare @״̬ int,@�Ƿ���� bit,@��ֹ���� datetime,@��� money
  Select @״̬=״̬,@�Ƿ����=�Ƿ����,@��ֹ����=��ֹ����,@���=��� From ��ȯ�� Where ���=@VoucherCode
  If @@RowCount < 1 Return -1 --ȯ�����Ч
  Else If @״̬ <> 2 Return -2  --�����ѷ��ŵ�ȯ
  Else If @�Ƿ���� = 1 Return -3 --ȯ�ѻ���
  Else If @��ֹ���� < GetDate() Return -4 --ȯ�ѹ���Ч��
  If @Amount=0 Set @Amount=@���
  Insert Into ��ȯ���ձ�(���ű��,��Դ,PID,���,���,���,��ע)
         Values(@Department,@Source,@RecordID,@VoucherCode,@���,@Amount,@Remark)
  If @@Error<>0 Return -5
  Return 0
End
Go
/*******************************************************************************
Procedure QueryFormMObject
����:ִ��mobile_object�еĲ�ѯ
����:@QueryName�ǲ�ѯ��,��Ӧname�ֶ�
����ֵ:�ɹ�����0,ʧ�ܷ���-1
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
Author:		<Author,,Name>
Create date: 2017-06-15
Description:Ϊ�ϰ����ַ���ͳ������
�÷�
   exec Get_BossTotal [����],[�ϰ�],[����]
  ���ſ�Ϊ�ջ�null,���źţ��ϰ� ��Ϊ�ջ�null,���ϰ�ţ����ڿ�Ϊ�գ�������
  ���� 
  --exec  Get_BossTotal			   --ȫ������        �������������ȫ��
  --exec  Get_BossTotal  '001'	   --�������        ��һ��������Ӧ �� web_napa_stores  ��hs_code �ֶ�
  --exec  Get_BossTotal  '',1       --ָ���ϰ�����  �ڶ���������Ӧ �� web_napa_stores  ��ID �ֶ�
  --exec  Get_BossTotal  '','',��2017-06-09�� --������������ָ������ǰ����Ϊ���죬Ҳ��ָ��ĳ������Ϊ���죬��������Ϊ����

*/
If Object_ID('[Get_BossTotal]','P') Is Not Null 
  Drop Procedure [Get_BossTotal]
Go
Create Procedure [Get_BossTotal]
       @Department varchar(10)=null,--ѡ����� ������Ӧ �� web_napa_stores  ��hs_code ��
       @Boss       int        =null,--ѡ���ϰ� ������Ӧ �� web_napa_stores  ��ID �ֶ�
       @InDate     datetime		=null--������ָ���ڣ���ָ��ϵͳĬ�ϵ�ǰ����
AS
-- =============================================
-- =============================================
Begin
 
    if object_id('tempdb..#tmp_Get_BossTotal') is not null  
    drop table #tmp_Get_BossTotal
	--declare @Department varchar(10),@Boss varchar(10)
	Declare  @date datetime
	--select  @date = convert(datetime,convert(varchar(10),getdate(),120),120) 
		
	Declare @date0 varchar(10),@date1 varchar(10)
	Declare @Depart table(Department varchar(10))
	Declare @Wsc table(Wsc varchar(10))
	if @InDate is null 
	   select @date=convert(varchar(10),getdate(),120) 
	  else
	   select @date=convert(varchar(10),@InDate,120) 	
	select  @date0= convert(varchar(10),@date,120)   --����
	select  @date1= convert(varchar(10),@date-1,120)  --����
	  

	Insert Into @Wsc select ��� from pub_user where ���� ='���ں�'
   
   If  isnull(@Department,'')<>''
	    Insert Into @Depart Values(@Department)--ָ����
   else If isnull(@Boss,0)<>0
    	Insert Into @Depart Select distinct hs_code from [web_napa_stores] where [user_id]=@Boss --ָ���ϰ�
   else
		Insert Into @Depart Select ��� from department where ����� in ('102','103')--ȫ����
		
	
  select *  into #tmp_Get_BossTotal from (
		select  sum(case ���� when @date0 then Ӧ�ս�� else 0 end) as ND_Saleing                           --��������
			   ,sum(case ���� when @date1 then Ӧ�ս�� else 0 end) as LD_Saleing							--��������
			   ,sum(case ���� when @date0 then 1 else 0 end) as ND_Order									--���ն�����
			   ,sum(case ���� when @date1 then 1 else 0 end) as LD_Order									--���ն�����
			   ,sum(case when ����= @date0 and isnull(�������,'')<>'' then Ӧ�ս�� else 0 end) as ND_Vip   --���ջ�Ա����
			   ,sum(case when ����= @date1 and isnull(�������,'')<>'' then Ӧ�ս�� else 0 end) as LD_Vip   --���ջ�Ա���� 
			   ,sum(case when ����= @date0 and isnull(�������,'') ='' then Ӧ�ս�� else 0 end) as ND_NoVip --���շǻ�Ա����
			   ,sum(case when ����= @date1 and isnull(�������,'') ='' then Ӧ�ս�� else 0 end) as LD_NoVip --���շǻ�Ա����
			 
	 from orders where �Ƶ��� in (select * from @Wsc )--(select ��� from pub_user where ���� ='���ں�')
		And ����     between @date1 and @date0
		And ���ű�� in (Select * from @Depart)--��Щ�����϶���
		 ) a
		left join 
			( select  sum(case convert(datetime,convert(varchar(10),����ʱ��,120),120)  when @date0 then 1 else 0 end) as ND_count --����������Ա
					, sum(case convert(datetime,convert(varchar(10),����ʱ��,120),120)  when @date1 then 1 else 0 end) as LD_count --����������Ա
			  from ��Ա where  convert(datetime,convert(varchar(10),����ʱ��,120),120)  between @date1 and @date0 
			) b on 1=1
		left join
			( select  sum(case ���� when @date0 then ���ϼ� else 0 end) as ND_ReCharge  --���ճ�ֵ
					, sum(case ���� when @date1 then ���ϼ� else 0 end) as FD_ReCharge  --���ճ�ֵ
			  from ��ֵ�� where �Ƶ��� in (select  ��� from pub_user where ���� ='���ں�')
					And ����  between @date1 and @date0
					And ���ű�� in (Select * from @Depart)
			) c on 1=1
		


	select Caption= '��������',		[Values]=ND_Saleing ,VType='money' ,sort='11' from #tmp_Get_BossTotal union
	select Caption= '��������',		[Values]=LD_Saleing ,VType='money' ,sort='12' from #tmp_Get_BossTotal union
	select Caption= '���ն�����',	[Values]=ND_Order	,VType='int'	,sort='21' from #tmp_Get_BossTotal union
	select Caption= '���ն�����',	[Values]=LD_Order	,VType='int'	,sort='22' from #tmp_Get_BossTotal union
	select Caption= '����������Ա',	[Values]=ND_count	,VType='int'	,sort='31' from #tmp_Get_BossTotal union
	select Caption= '����������Ա',	[Values]=LD_count	,VType='int'	,sort='32' from #tmp_Get_BossTotal union
	select Caption= '���շǻ�Ա����',	[Values]=ND_NoVip ,VType='money' ,sort='41' from #tmp_Get_BossTotal union
	select Caption= '���շǻ�Ա����',	[Values]=LD_NoVip ,VType='money' ,sort='42' from #tmp_Get_BossTotal union	 
	select Caption= '���ջ�Ա����',	[Values]=ND_Vip ,VType='money' ,sort='51' from #tmp_Get_BossTotal union
	select Caption= '���ջ�Ա����',	[Values]=LD_Vip ,VType='money' ,sort='52' from #tmp_Get_BossTotal union
	select Caption= '���ճ�ֵ',[Values]=ND_count ,VType='money' ,sort='61' from #tmp_Get_BossTotal union
	select Caption= '���ճ�ֵ',[Values]=LD_count ,VType='money' ,sort='62' from #tmp_Get_BossTotal
	order by sort

	drop table #tmp_Get_BossTotal

  --If @InDate Is null Set @InDate=getdate()
  --Declare @Wsc varchar(10),@date0 varchar(10),@date1 varchar(10)
  --Declare @Dates    Table(cName varchar(10),Date varchar(10),iIndex int)
  --Declare @Depart   Table(Department varchar(10))
  --Declare @TmpTable Table(���� varchar(10),������� varchar(20),Ӧ�ս�� money)
  --Declare @TmpOut   Table(Caption varchar(20),Value money,ValueType varchar(10),Short int)

  --Select @Wsc=��� From pub_user where ���� ='���ں�'
  --Select @date0=Convert(varchar(10),@InDate  ,120),@date1=Convert(varchar(10),@InDate-1,120)
  --Insert into @Dates Values('����',@date0,1)
  --Insert into @Dates Values('����',@date1,2)
  --If IsNull(@Department,'')<>''
  --  Insert Into @Depart Values(@Department)
  --Else If IsNull(@Boss,0)<>0
  --  Insert Into @Depart Select Distinct hs_code From [web_napa_stores] Where [user_id]=@Boss
  --Else
  --  Insert Into @Depart Select ��� From department Where ����� in ('102','103')
  --Insert Into @TmpTable
  --Select ����,�������,Ӧ�ս��
  --From orders
  --Where ���� BetWeen @date1 And @date0
  --  And ���ű�� In (Select * From @Depart)
  --  And �Ƶ���=@Wsc
  --Insert Into @TmpOut	
  --       Select A.cName+'����',IsNull(Sum(Ӧ�ս��),0),'money',A.iindex
  --       From @Dates As A
  --       Left Join @TmpTable As B On A.[Date]=B.����
  --       Group by A.cName+'����',A.iindex
  --Insert Into @TmpOut
  --       Select A.cName+'������',Count(*),'int',A.iindex+10
  --       From @Dates As A
  --       Left Join @TmpTable As B On A.[Date]=B.����
  --       Group by A.cName + '������',A.iindex+10
  --Insert Into @TmpOut 
  --       Select A.cName+'��Ա',Count(*),'int',A.iindex+20
  --       From @Dates As A
  --       Left Join ��Ա As B On A.[Date]=convert(varchar(10),B.����ʱ��,120)
  --       Group By A.cName + '��Ա',A.iindex+20
  --Select Caption,Value,ValueType,Short from @TmpOut Order By Short
End
Go 
/*******************************************************************************
Ϊ΢�̳�ϵͳ�ṩ�Ľӿڷ�װ
  ��Ҫ����ΪһЩ�������Զ�����,�Լ�����ֵת��Ϊ�����
  
  �÷���
  exec WSC_GetVipInfo 'Y6GagnXEeygErTHh9-Rx3'  ȡ�����Ա��Ϣ
  exec WSC_GetVipInfo '',10                    ȡ��������δ��10���ڹ����յĻ�Ա�������գ� 
  exec WSC_GetVipInfo '',20,'2017-07-01'       ȡ2017-07-01δ��20���ڹ����յĻ�Ա�������գ�
*/
--------------------------------------------------------------------------------
--ȡ��Ա��Ϣ
--  @cWeiXinCode���
If Object_ID('[WSC_GetVipInfo]','P') Is Not Null
  Drop Procedure [WSC_GetVipInfo]
Go
Create Procedure [WSC_GetVipInfo]
       @cWeiXinCode varchar(50) = null,
       @DayCount int=null,
       @NowDate datetime =null
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
    Insert Into #tmp_WSC_GetVipInfo Exec GetBirthday @DayCount,@NowDate
  Select * From #tmp_WSC_GetVipInfo  --�Դ����,�����ڴ��ų�����Ҫ���ֶ�
  Drop Table #tmp_WSC_GetVipInfo
End
Go
--exec WSC_GetVipInfo 'Y6GagnXEeygErTHh9-Rx3'
--exec WSC_GetVipInfo '',10,'2017-07-01'

--------------------------------------------------------------------------------
--��ӻ�Ա
--  @cWeiXinCode���
--  @cMobileNumber�ֻ���
--  @cName����,��ѡ
--  @cBirthday����,��ѡ
--  @bIsLuna,��ũ��,��ѡ
--WSC_AddVip״̬��:
-- 0   :�ɹ�
-- -1  :�Ѱ󶨵�������Ա
-- -2  :�޴˻�Ա
-- -3  :��Ա��ͣ��
-- -201:�ֻ����ظ�(�ж��ſ�)
If Object_ID('[WSC_AddVip]','P') Is Not Null
  Drop Procedure [WSC_AddVip]
Go
Create Procedure [WSC_AddVip]
       @cWeiXinCode varchar(50)
      ,@cMobileNumber varchar(20)--�ֻ���
      ,@cName varchar(20)        = ''--����
      ,@cBirthday varchar(10)    = '1900-01-01'--����,yyyy-mm-dd��ʽ,��Χ1900-01-01��2079-06-06
      ,@bIsLunar bit             = 0--��ũ��,0��ʾ���ղ���ũ��(�ǹ���)
      ,@cVipCode varchar(20)     = ''--�˲���ͣ��
As
Begin
  Set @cVipCode = null --�ò�����ֵ�Ѿ�����,������ʹ���ֻ�������λ�������
  Declare @Return int, @msg varchar(100)
  --��λ���߻�Ա���
  Declare @Tally int
  Select @cVipCode=��� From ��Ա Where �绰=@cMobileNumber
  Set @Tally = @@RowCount
  If @Tally < 1 And IsNull(@cName,'')='' And IsNull(@cBirthday,'')=''
  Begin 
    Set @Return = -2
    Set @msg = '�޴˻�Ա'
  End
  Else If @Tally < 2
  Begin
    If @Tally = 0 Set @cVipCode = ''
    Exec @Return=AddVip @cWeiXinCode,@cMobileNumber,@cName,@cBirthday,@bIsLunar,@cVipCode
    Set @msg = Case @Return 
               When  0 Then '�ɹ�'
               When -1 Then '���''' + @cWeiXinCode + '''�Ѿ����������»�Ա����'
               When -2 Then '�޴˻�Ա'
               When -3 Then '��Ա����ͣ��'
               Else 'δ֪����'
               End
  End
  Else
  Begin
    Set @Return = -201
    Set @msg = '�ֻ���''' + @cMobileNumber + '''�ظ�ע�ᵽ�����Ա,���ܰ�.'
  End
  Select @Return As retcode,@msg As Msg
End
Go
--exec WSC_AddVip 'AKnexiDEEH1vrWSl9-wa','12345678901','asdf'
--------------------------------------------------------------------------------
--��Ա��ֵ
--  @cWeiXinCode��Ա���
--  @nAddMoney��ֵ���
--  @cWeiXinOrderCode΢�̳�֧��������
If Object_ID('[WSC_VipReCharge]','P') Is Not Null
  Drop Procedure [WSC_VipReCharge]
Go
Create Procedure [WSC_VipReCharge]
       @cWeiXinCode varchar(50),     --��Ա������
       @nAddMoney money=0,           --��ֵ���
       @cWeiXinOrderCode varchar(50) --΢�̳�֧��������
As
Begin
  Declare @Return int,@msg varchar(100)
  Exec @Return = VipReCharge @cWeiXinCode,@nAddMoney,@cWeiXinOrderCode
  Set @msg = Case @Return
             When  0 Then '�ɹ�'
             When -1 Then '�޴˻�Ա'
             When -2 Then '���ɶ�����ʧ��'
             When -3 Then '���ɳ�ֵ��¼ʧ��'
             Else 'δ֪����'
             End
  Select @Return As retcode,@msg As Msg
End
Go
--------------------------------------------------------------------------------
--��ȡָ����Ա�����Ѽ�¼
If Object_ID('[WSC_GetVipLog]','P') Is Not Null
  Drop Procedure [WSC_GetVipLog]
Go
Create Procedure [WSC_GetVipLog]
       @cWeiXinCode varchar(50),--��Ա�󶨵����
       @cDateStart varchar(10)='1900-01-01', --��ѯ��ʼ����(������)
       @cDateEnd varchar(10)=''  --��ѯ��ֹ����(������)
As
Begin
  --Declare @Return int,@msg varchar(100)
  --Exec @Return=GetVipLog @cWeiXinCode,@cDateStart,@cDateEnd
  --Set @msg = Case @Return
  --           When 0 Then '�ɹ�'
  --           When -1 Then '��Ҫ���'
  --           When -2 Then '���δ�����»�Ա��'
  --           Else 'δ֪����'
  --           End
  --Select @Return As retcode,@msg As Msg
  --����Ҫ����״̬��
  Exec GetVipLog @cWeiXinCode,@cDateStart,@cDateEnd
End
Go
--exec WSC_GetVipLog '4YSju0DE3B2jrQTY99Hz'
--------------------------------------------------------------------------------
--�õ�΢�̳��п��Է��ŵĵ���ȯ�б�/ָ����ŵ���ȯ
--����
--  @WeiXinCode:��ȡָ����ʹ�õ�ȯ(������,δʹ��,��Ч����)
--  @VoucherCode:��ȡָ��ȯ����Ϣ,����״̬�����۲���
--  @GoodsCode:��ȡָ����Ʒ��ŵ�ȯ,(���⵽������,δ����)
--  ָ����@WeiXinCode�����@VoucherCode
--  ����ָ���򷵻������̳ǿ�������/���͵�ȯ
If Object_ID('[WSC_GetVouchers]','P') Is Not Null
  Drop Procedure [WSC_GetVouchers]
Go
Create Procedure [WSC_GetVouchers]
       @WeiXinCode  varchar(50)='',--��Ա���
       @VoucherCode varchar(20)='',--ȯ���
       @GoodsCode   varchar(10)='' --ȯ�Ĳ�Ʒ���
As
Begin
  If Object_ID('tempdb..#tmp_WSC_GetVouchers')Is Not Null
    Drop Table #tmp_WSC_GetVouchers
  Create Table #tmp_WSC_GetVouchers
        (GoodsCode varchar(10)
        ,VouchersCode varchar(20),PayQuota money,State varchar(10)
        ,VipCode varchar(20),EndTime datetime
        ,BarCode varchar(30))
  --������Ӧ�Ļ�Ա���
  Declare @cDepart varchar(20)
  If IsNull(@WeiXinCode, '') <> ''
    Begin
      Declare @VipCode varchar(20)
      Select @VipCode=��� 
      From ��Ա As a
      Inner Join ��Ա��� As b On a.id=b.��Աid
      Where b.���=@WeiXinCode
      Insert Into #tmp_WSC_GetVouchers Exec GetVouchers '', @VipCode
    End
  Else If IsNull(@VoucherCode, '') <> ''
    Begin
      Insert Into #tmp_WSC_GetVouchers Exec GetVouchers '', '', @VoucherCode
    End
  Else If IsNull(@GoodsCode, '') <> ''
    Begin
      Select @cDepart = ��� From department Where ���='���ں�'
      Insert Into #tmp_WSC_GetVouchers Exec GetVouchers @cDepart,'','',@GoodsCode
    End
  Else
    Begin
      Select @cDepart = ��� From department Where ���='���ں�'
      Insert Into #tmp_WSC_GetVouchers Exec GetVouchers @cDepart
    End
  Select * From #tmp_WSC_GetVouchers
  Drop Table #tmp_WSC_GetVouchers 
End
Go
--exec WSC_GetVouchers --�̳����п������۵�ȯ
--exec WSC_GetVouchers '{���԰���2}'--�û�Ա���е�δʹ�õ�ȯ
--exec WSC_GetVouchers '','21009'--ָ����ŵ�ȯ
--exec WSC_GetVouchers '','','83001'--�̳ǿ����۵�ָ����Ʒ��ŵ�ȯ
--exec WSC_GetVouchers '{���԰���2}','21009' --�ȼ�WSC_GetVouchers '','21009'
--------------------------------------------------------------------------------
--����ȯ����
--  @GoodsCode,@VoucherCode,@WeiXinCode,@Price
If Object_ID('[WSC_SaleVoucher]','P') Is Not Null
  Drop Procedure [WSC_SaleVoucher]
Go
Create Procedure [WSC_SaleVoucher]
       @GoodsCode varchar(10)  ,--��ȯ��Ʒ���
       @VoucherCode varchar(20),--��ȯ���
       @WeiXinCode varchar(50) ,--��Ա���
       @PayByCard money=0      ,--��Ա��֧�����,������Ϊ0
       @Price money = 0         --ʵ���ۼ�,����ʱΪ0
As
Begin
  Declare @Return int,@msg varchar(100),@SalesCode varchar(20)
  Declare @Department varchar(10),@UserCode varchar(10),@VipCode varchar(20),@CDate varchar(10)
  Select @Department=��� From department Where ���='���ں�'
  Select @UserCode=��� From pub_user Where ����='���ں�'
  Select @VipCode=��� From ��Ա Where id in(Select ��ԱID From ��Ա��� Where ���=@WeiXinCode)
  Set @CDate=Convert(varchar(10),getdate(),120)
  If IsNull(@GoodsCode,'')=''
  Begin
    --ȡ��ȯ���
    Declare @y_��� money
    Select @y_���=��� From ��ȯ�� Where ���=@VoucherCode
    If @y_��� Is Null 
    Begin
      Select -1 As retcode,'��ȯ�����Ч' As Msg
      Return -1
    End
    --��Goods��Ѱ�����ۼ۸���ӽ���ȯ
    Select Top 1 @GoodsCode=��� From goods Where �������='�ֽ�ȯ' Order By Abs(11-��������)
    If @GoodsCode Is Null
    Begin
      Select -2 As retcode,'û��ƥ�䵽��������/���͵���ȯ��Ʒ' As Msg
      Return -2
    End
  End
  
  Exec @Return=SaleingVoucher @Department, @UserCode, @CDate
                            , @GoodsCode, @Price, 0, @PayByCard
                            , @VipCode, @VoucherCode, @SalesCode Output
  
  Set @msg = Case @Return 
             When 0    Then '�ɹ�'
             When -1   Then '�������۵�ʧ��'
             When -100 Then 'ȯ�����Ч'
             When -101 Then 'ȯ�ѹ���'
             When -102 Then 'ȯ����ʧ��'
             When -103 Then 'ȯδ����'
             When -105 Then 'ȯ�Ѿ�����'
             When -106 Then 'ȯ��ʹ��'
             Else 'δ֪����' 
             End
  Select 0 As retcode,'�ɹ�' As Msg,@SalesCode As SalesOrderNo
End
Go
--------------------------------------------------------------------------------
--��������
--@OrderCode����''��null��Ϊ�½�����
--@OrderCode��������Ϊ�޸Ķ���,���ύ�µ�@CallNumber/@PickUpTime/@Remarks/@Destination/@EndOrder/@VoidOrder
--���ؽ����:
--  �����:����ID int,������ varchar(20)
If Object_ID('[WSC_CreateOrder]','P') Is Not Null 
  Drop Procedure [WSC_CreateOrder]
Go
Create Procedure [WSC_CreateOrder]
       @OrderCode   varchar(20) =NULL OUTPUT,--Ϊ�����½���,��Ϊ�����޸����е�
       @WSC_TardNo  varchar(32) =''   ,--�ⲿת��Ķ���,��Ӧ���ⲿ������
       @PickUpTime  datetime    =Null ,--ԤԼȡ��ʱ��
       @WeiXinCode  varchar(50) =''   ,--��Ա���
       @CallNumber  varchar(50) =Null ,--��ϵ��ʽ
       @Remarks     varchar(100)=Null ,--�˿�ע��������
       @Destination varchar(100)=Null ,--�ͻ���ַ���ͻ���������
       @TotalAmount money       =0    ,--δ�ۿ�ʱ�ϼ�,���ϼ�
       @Discount    money       =0    ,--��Ʒ�ۿ۽��,�ۺ���,-�������-��Ա��֧��-��ȯ֧��=Ƿ��
       @Deducted    money       =0    ,--����������
       @Payment     money       =0    ,--�µ�ʱ�û�Ա��֧���Ľ��
       @Voucher     money       =0    ,--�µ�ʱʹ����ȯ֧���Ľ��
       @EndOrder    bit         =Null ,--�Ƿ�ᵥ,Ƿ�Ϊ0ʱ���ɽᵥ
       @VoidOrder   bit         =Null ,--�����ֹ,ֻ�ɶԽᵥ=0�ĵ���ֹ
       @Department varchar(20)  =''    --�����µ��ĸ���
As
Begin
  Declare @Return int,@msg varchar(100)
  Declare @OrderID int,@Department_Web varchar(10),@UserCode varchar(10),@VipCode varchar(20),@cDate varchar(10)
  Select @Department_Web='���ں�'
  Select @UserCode=��� From pub_user Where ����='���ں�'
  Select @VipCode=��� From ��Ա Where id in(Select ��ԱID From ��Ա��� Where ���=@WeiXinCode)
  Set @cDate = Convert(varchar(10),getdate(),120)
  Exec @OrderID=CreateOrder @����=@OrderCode Output
                           ,@���ű��=@Department
                           ,@�Ƶ���=@UserCode
                           ,@�̻�������=@WSC_TardNo
                           ,@����=@cDate
                           ,@ȡ��ʱ��=@PickUpTime
                           ,@�������=@VipCode
                           ,@��ϵ��ʽ=@CallNumber
                           ,@��ע=@Remarks
                           ,@�ͻ���ַ=@Destination
                           ,@���ϼ�=@TotalAmount
                           ,@�Żݺϼ�=@Discount
                           ,@����=@Deducted
                           ,@ˢ�����=@Payment
                           ,@��ȯ=@Voucher
                           ,@�ᵥ=@EndOrder
                           ,@��ֹ=@VoidOrder
                           ,@�������=@Department_Web
  Select @OrderID As OrderID,@OrderCode As OrderCode
  --If @OrderID>0
  --  Select  0 As retcode, '�ɹ�' As Msg
  --Else
  --  Select -1 As retcode, 'ʧ��' As Msg
EnD
Go
--------------------------------------------------------------------------------
--д�붩����ϸ
If Object_ID('[WSC_AddOrderItem]','P') Is Not Null
  Drop Procedure [WSC_AddOrderItem]
Go
Create Procedure [WSC_AddOrderItem]
       @PID         int        ,--����ID
       @GoodsCode   varchar(10),--��Ʒ���
       @GoodsCount  int        ,--��Ʒ����
       @Price       money      ,--����,δ�ۿ۵ĵ���
       @TotalAmount money       --���,�ۿۺ��ܽ��
As
Begin
  Declare @Return int,@msg varchar(100)
  Exec @Return=AddOrderItem @PID,@GoodsCode,@GoodsCount,@Price,@TotalAmount
  Set @msg = Case @Return
             When 0 Then '�ɹ�'
             When -1 Then 'ʧ��'
             Else 'δ֪����'
             End
  Select @Return As retcode, @msg As Msg
End
Go
--------------------------------------------------------------------------------
--��ȡ�����б�
--  
If Object_ID('[WSC_GetOrdersList]','P') Is Not Null 
  Drop Procedure [WSC_GetOrdersList]
Go
Create Procedure [WSC_GetOrdersList]
       @StartDate  varchar(10)='1900-01-01',--���ڷ�Χ:��ʼ����
       @EndDate    varchar(10)=''          ,--���ڷ�Χ:��������
       @OrderCode  varchar(20)=''          ,--������
       @VipCode    varchar(50)=''          ,--��Ա���
       @oauth_code varchar(50)=''          ,--���ظû�Ա�¼������Ķ���,������GetOrdersList�п���
                                            --�����û�Ա�Լ������Ķ���
       @IsEnd      bit        =null         --ָ���˲���,��ֻ���ؽᵥ״̬��֮һ�µĵ�
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
    Begin--��ѯָ�������ŵĶ���
      Insert Into #tmp_WSC_GetOrdersList 
      Exec GetOrdersList '',@StartDate,@EndDate,@OrderCode,'',''
    End
  Else If IsNull(@VipCode,'')<>''
    Begin--��ѯָ����Ա�Ķ���
      Select @CardCode=��� From ��Ա Where id in(Select ��ԱID From ��Ա��� Where ���=@VipCode)
      Insert Into #tmp_WSC_GetOrdersList Exec GetOrdersList '',@StartDate,@EndDate,'',@CardCode
    End
  Else If IsNull(@oauth_code,'')<>''
    Begin--��ѯָ����Ա�ķ�������
      Select @CardCode=��� From ��Ա Where id in(Select ��ԱID From ��Ա��� Where ���=@VipCode)
      Insert Into #tmp_WSC_GetOrdersList Exec GetOrdersList '',@StartDate,@EndDate,'','',@CardCode
    End
  Else
    Begin--��ѯ���ںŲ����Ķ���
      Insert Into #tmp_WSC_GetOrdersList Exec GetOrdersList Null,@StartDate,@EndDate
    End
  If @IsEnd Is Null
    Select * From #tmp_WSC_GetOrdersList Order By CreateTime Desc
  Else 
    Select * From #tmp_WSC_GetOrdersList Where IsEnd=@IsEnd Order By CreateTime Desc
  Drop Table #tmp_WSC_GetOrdersList
End
Go
----ָ������ģʽ�����ڷ�Χ��Ч
----����ģʽ�����������@StartDate��@EndDate���޶����ڷ�Χ
----����ģʽ��,����ʹ��@IsEnd���޶���Χ
--Exec WSC_GetOrdersList @Ordercode='170227-00002'  --��ָ������
----ָ����Աģʽ
--Exec WSC_GetOrdersList @VipCode='MFfM3UC5WQQ5yRAfUh19hbk'  
--Exec WSC_GetOrdersList @VipCode='MFfM3UC5WQQ5yRAfUh19hbk',@IsEnd=0--��˻�Աδ��ĵ�
--Exec WSC_GetOrdersList @VipCode='MFfM3UC5WQQ5yRAfUh19hbk',@StartDate='2017-01-01'  --��˻�Ա����(��1��1������)
--Exec WSC_GetOrdersList @VipCode='MFfM3UC5WQQ5yRAfUh19hbk',@StartDate='2017-01-01',@EndDate='2017-01-31'  --��˻�Աһ�µĶ���
--Exec WSC_GetOrdersList @VipCode='MFfM3UC5WQQ5yRAfUh19hbk',@EndDate='2017-04-31'  --��˻�Ա������һǰ�����ж���
----ָ��������Աģʽ
--Exec WSC_GetOrdersList @oauth_code='MFfM3UC5WQQ5yRAfUh19hbk'--��˻�Ա�����¼������Ķ���(�����˻�Ա��)
--Exec WSC_GetOrdersList @oauth_code='MFfM3UC5WQQ5yRAfUh19hbk'@EndDate='2017-05-01',@IsEnd=1 --��˻�Ա�����¼������Ķ���(�����˻�Ա��),�ѽ��
----ȡ�̳����ɵĶ���ģʽ
--Exec WSC_GetOrdersList --�������̳����ɵĶ���
--Exec WSC_GetOrdersList @StartDate='2017-01-01'--�������̳����ɵĶ���
--Exec WSC_GetOrdersList @StartDate='2017-01-01',@EndDate='2017-04-19'--�������̳����ɵĶ���

--------------------------------------------------------------------------------
--��ȡָ��������Ϣ
If Object_ID('[WSC_GetOrdersDetail]','P') Is Not Null 
  Drop Procedure [WSC_GetOrdersDetail]
Go
Create Procedure [WSC_GetOrdersDetail]
       @OrderID int
As
Begin
  --Declare @Return int,@msg varchar(100)
  --Exec @Return=GetOrdersDetail @OrderID
  --Set @msg=Case @Return When 0 Then '�ɹ�' Else 'ʧ��' End
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
--�����л�����ȯ
If Object_ID('[WSC_RecoverVoucher]','P') Is Not Null
  Drop Procedure [WSC_RecoverVoucher]
Go
Create Procedure [WSC_RecoverVoucher]
       @RecordID    int        ,--����ID:��Ӧ���۵�/����������¼ID
       @VoucherCode varchar(20),--��ȯ���
       @Amount      money      =0,--���:�ֿ۽��,Ϊ0����ȯ���ֿ�
       @Remark      varchar(50)='' --��ע
As
Begin
  Declare @Return int,@msg varchar(100)
  Declare @Department varchar(10),@Source int
  Select @Department=��� From department Where ���='���ں�'
  Set @Source=1 --1Ϊ����,0Ϊ����,�����̳Ǿ�Ϊ����
  Exec @Return=RecoverVoucher @Department,@Source,@RecordID,@VoucherCode,@Amount,@Remark
  Set @Msg = Case @Return
             When 0  Then '�ɹ�'
             When -1 Then 'ȯ�����Ч'
             When -2 Then 'ȯδ����'
             When -3 Then 'ȯ�ѻ���'
             When -4 Then 'ȯ�ѹ���'
             When -5 Then 'д���ռ�¼ʧ��'
             Else 'δ֪����'
             End
  Select @Return As retcode, @msg As Msg
End
Go
--------------------------------------------------------------------------------
--���ֱ䶯
If Object_ID('[WSC_VipBonusPoints]','P') Is Not Null
  Drop Procedure [WSC_VipBonusPoints]
Go
Create Procedure [WSC_VipBonusPoints]
       @WeiXinCode  varchar(50),--��Ա���
       @Add         money      ,--��������ֵ(����Ϊ����,0Ϊ����)
       @Description varchar(20) --���ֱ䶯ԭ��
As
Begin
  Declare @Return int,@msg varchar(100)
  Declare @cDepart varchar(10),@cUser varchar(10),@VipCode varchar(20)
  Select @cDepart=��� From department Where ���='���ں�'
  Select @cUser=��� From pub_user Where ����='���ں�'
  Select @VipCode=A.��� From ��Ա As A Inner Join ��Ա��� As B On A.ID=B.��ԱID Where B.���=@WeiXinCode
  Exec @Return=VipBonusPoints @cDepart,@cUser,@VipCode,@Add,@Description
  Set @msg = Case @Return
             When  0 Then '�ɹ�'
             When -1 Then '�޴˻�Ա'
             When -2 Then '��δ����'
             When -3 Then '���ѹ�ʧ'
             When -4 Then '���ѹ���'
             When -5 Then '���ֱ䶯ʧ��'
             Else 'δ֪����'
             End
  Select @Return As retcode, @msg As Msg
End
Go
--------------------------------------------------------------------------------
--����������ȷ��������Ա���Խ����������ӹ�ϵ
--���ؼ�0����Խ���������ϵ
--������ܽ���������ϵ���ڷ��ؼ�msg�а���ԭ��
   ----ԭʼ���ݲ���
  --Exec WSC_GetVipRelevance 'oH7hfuHST2w_VAqTw9dB4dnksHE0','oH7hfuJGShVdPEX7AeR50X3JuYVI'
  ----ģ�����1:����
  --Begin Tran
  --Update web_user_invited_link Set user_id=140,invited_id=150 where user_id=14
  --Exec WSC_GetVipRelevance 'oH7hfuHST2w_VAqTw9dB4dnksHE0','oH7hfuJGShVdPEX7AeR50X3JuYVI'
  --Rollback
  ----ģ�����1:������
  --Begin Tran
  --Update web_user_invited_link Set user_id=140,invited_id=150 where user_id=14
  --Update web_user_invited_link Set user_id=17 where user_id=13 and invited_id=14
  --Exec WSC_GetVipRelevance 'oH7hfuHST2w_VAqTw9dB4dnksHE0','oH7hfuJGShVdPEX7AeR50X3JuYVI'
  --Rollback
If Object_ID('[WSC_GetVipRelevance]','P') Is Not Null
  Drop Procedure [WSC_GetVipRelevance]
Go
Create Procedure [WSC_GetVipRelevance]
       @ParentVipoAuthCode varchar(50),--׼����Ϊ���Ļ�Ա���
       @ChildVipoAuthCode varchar(50)  --׼����Ϊ�ӵĻ�Ա���
As
Begin
  Declare @Tree Table(id int,level int)
  Declare @iLevel int
  Declare @ID int
  Declare @VipCode varchar(20)
  --����:����Ӧ����û���ϼ���
  Select Top 1 @VipCode = E.���
         From web_oauth_login As A
         Left Join web_user_invited_link As B On A.user_id=B.invited_id
         Left Join web_oauth_login As C On B.user_id=C.user_id
         Left Join ��Ա��� As D On C.oauth_id=D.���
         Left Join ��Ա As E On D.��ԱID=E.ID
         Where A.oauth_id=@ChildVipoAuthCode
  If @VipCode Is Not Null
  Begin
    Select -1 As retcode,'�����Ѿ��ǻ�Ա[' + @VipCode + ']���¼�' As Msg
    Return
  End
  --��ϵ��
  --  ���������ݹ����Ϊ3
  --  ������¼��е�ѭ������
  Insert Into @Tree
         Select C.user_id,1 --A.��ԱID,B.���,C.user_id
         From ��Ա��� As A
         Left Join ��Ա��� As B On A.��ԱID=B.��ԱID
         Left Join web_oauth_login As C On B.���=C.oauth_id
         Where A.���=@ChildVipoAuthCode
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
  --������Ӧ��ID
  --  ���Ƶݹ����Ϊ3
  --  ������ϼ��е�ѭ������
  Set @iLevel = 0
  Insert Into @Tree
         Select C.user_id,@iLevel
         From ��Ա��� As A
         Left Join ��Ա��� As B On A.��ԱID=B.��ԱID
         Left Join web_oauth_login As C On B.���=C.oauth_id
         Where A.���=@ParentVipoAuthCode
  While @@RowCount>0 And @iLevel > -3
  Begin
    Set @iLevel = @iLevel-1
    Insert Into @Tree 
           Select user_id,@iLevel
           From @Tree As A 
           Inner Join web_user_invited_link As B On A.id=B.invited_id
           Where A.level=@iLevel+1
  End
  --�ж��Ƿ���Խ�����Ӧ�ķ�����ϵ
  --  �����ϵ������ϵ���н���,�򲻿��齨������ϵ
  Select Top 1 @ID=A.id
         From @Tree As A
         Inner Join @Tree As B On A.ID=B.ID
         Where A.level<0 
           And B.level>=0
  If @ID Is Not Null
  Begin
    Select @VipCode=C.���
           From web_oauth_login As A
           Left Join ��Ա��� As B On A.oauth_id = B.���
           Left Join ��Ա As C On B.��ԱID=C.ID
           Where A.user_id=@ID
    Set @VipCode = IsNull(@VipCode,'')
    Select -1 As retcode,'������ϵ����:' + @VipCode As Msg
  End
  Else
    Select 0 As retcode,'���Խ���������ϵ' As Msg
End
Go
--------------------------------------------------------------------------------
--�ϰ�С���� ͳ������

-- Author:		<Author,,Name>
-- Create date: 2017-06-15
-- Description:	�ϰ�С����
--����ʾ��
  --exec WSC_Get_BossTotal ���ţ��ϰ� ������
  --���ſ�Ϊ�ջ�null,���źţ��ϰ� ��Ϊ�ջ�null,���ϰ�ţ����ڿ�Ϊ�գ�������
  --���� 
  --exec WSC_Get_BossTotal			   --ȫ������        �������������ȫ��
  --exec WSC_Get_BossTotal  '001'	   --�������        ��һ��������Ӧ �� web_napa_stores  ��hs_code �ֶ�
  --exec WSC_Get_BossTotal  '',1       --ָ���ϰ�����  �ڶ���������Ӧ �� web_napa_stores  ��ID �ֶ�
  --exec WSC_Get_BossTotal  '','',��2017-06-09�� --������������ָ������ǰ����Ϊ���죬Ҳ��ָ��ĳ������Ϊ���죬��������Ϊ����
If Object_ID('[WSC_Get_BossTotal]','P') Is Not Null 
  Drop Procedure [WSC_Get_BossTotal]
Go
 Create Procedure [WSC_Get_BossTotal]  
       @Department  varchar(10)=null,--ѡ�����  ��Ӧ �� web_napa_stores  ��hs_code �ֶ�
       @Boss        int        =null,--ѡ���ϰ�  ��Ӧ �� web_napa_stores  ��ID �ֶ�
       @InDate      datetime   =null --������ָ���ڣ���ָ��ϵͳĬ�ϵ�ǰ����
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



/*
 ȡָ������δ��XX���ڹ����յĻ�Ա 
  ���� ���պ��� obo.Fun_Birthday(����,ָ������)  

 �÷���
-- exec GetBirthday 20              ȡ����     δ��10���ڹ����յĻ�Ա
-- exec GetBirthday 20,'2017-7-1'   ȡ2017-7-1 δ��20���ڹ����յĻ�Ա
*/

If Object_ID('[GetBirthday]','P') Is Not Null 
  Drop Procedure GetBirthday
Go
 Create Procedure GetBirthday  
       @Days int =null ,
       @NowDate datetime =null
     As  
begin
    --Declare @NowDate datetime
    if @NowDate is null
    set @NowDate=getdate()
      
    if @Days is null 
       set @days=0
       
	select * 
	 from ��Ա 
		 where ״̬=1 and �Ƿ��ֵ=0 and ��ֹ����>= @NowDate 
		       and  dbo.Fun_Birthday( ����,@NowDate) 
		   between @NowDate and @NowDate+@Days
  
end
Go

-----------------------------Function------------------------------------------------ 

If Object_Id('dbo.Fun_Birthday','FN') is not null Drop Function Fun_Birthday
GO
/*
  δ�����պ���
      С�� ָ�����ڵ�����Ϊ��һ������
      ���� ָ�����ڵ�Ϊ��������
  �÷���
     obo.Fun_Birthday(����,ָ������)    
*/
Create FUNCTION Fun_Birthday
(@Birthday varchar(10) ,--����
 @NowDate  datetime     --ָ������
)
RETURNS varchar(10)
AS
BEGIN
Declare @Birthday_t datetime
  set @NowDate =  convert(varchar(10),@NowDate,120) 
 
 If  @NowDate>dateadd(year , year(@NowDate)-year(@Birthday), @Birthday)  
	set @Birthday_t=dateadd(year , year(@NowDate) -year(@Birthday)+1, @Birthday)
  else  
    set @Birthday_t=dateadd(year , year(@NowDate) -year(@Birthday),   @Birthday)
    
    Return convert(varchar,@Birthday_t,120)
 END

GO

--   --------------------------------------------------------------------------------
/*
���ڶ����ӿڵ�ʹ��˵��
***����һ�Ŷ���
����WSC_CreateOrderд����,�õ���Ӧ��ID�뵥��
  ʹ����ȯ֧����,��˴���,ͬʱҪ��ֵ"@��ȯ"
  ��Ա����ֻ��Ҫ��ֵ'@ˢ�����'����
ѭ������WSC_AddOrderItem����д����ϸ
���֧����ʹ������ȯ,��������WSC_RecoverVoucher����ȯ���մ���

***�޸Ķ���
����WSC_CreateOrder,����@OrderCode(������),ͬʱ����@CallNumber/@PickUpTime/@Remarks/@Destination/@EndOrder/@VoidOrder
  ���������ݿ����޸�.δ��������Nullֵ�Ĳ����޸�.
  

--�ŵ��б�
--select id,���,���,ȫ��,��ַ,��ϵ�绰,�����,�������� from department where �Ƿ�ͣ��=0 and �������� in ('ֱӪ��','���˵�')
--���п����۲�Ʒ
--select ID,���,����,���,��λ,�������� from goods where �Ƿ�����=1 and ͣ������>convert(varchar(10),getdate(),120)
*/




