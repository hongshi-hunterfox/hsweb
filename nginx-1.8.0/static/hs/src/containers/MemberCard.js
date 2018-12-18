import React from 'react'
import DocumentTitle from 'react-document-title'
import './member-card.css'
import req from 'superagent'
import { browserHistory } from 'react-router'
import './member-center.css'
import fto from 'form_to_object'
import LazyLoad from 'react-lazyload'
 
 
const NoItem = () => {
  return (
    <div style={{
      margin: '50px 0',
      textAlign: 'center'
    }}>
      暂无会员卡
    </div>
    )
}

const MemberCardItem = (props) => {
  return (
    <div className="member-card-item clearfix">
      <div className="member-card-item-code">卡号：{props.code}</div>
      <div className="member-card-item-balance">余额：{props.balance} 元<br/>
      	{localStorage.getItem('merchantCode') !== "zxmb" ? <span className="member-card-item-points">积分：{props.bonusPoints} 分</span> : null}
      </div>
      <div className="member-card-item-recharge">
        <div onClick={props._clickHander.bind(this,"/seller/recharge",props.cardStatus)} className="btn btn-success">充值</div>
      </div>
    </div>
  )
}

class MemberCard extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      balance: null,
      cVipCode: null,
      bonusPoints: null,
      image: '',
      cMobileNumber: '',
      phones: '',
      config:{},
      vipImage:'',
      allowRecharge:true,
      vipJbarcode:'',
      cardStatus:'',
      id:0,
      list:[]
    }
  }

  componentDidMount() {
    req
      .get('/uclee-user-web/getUserInfo/')
      .query({
        t: new Date().getTime()
      })
      .end((err, res) => {
 		if (res.text) {
          this.setState(res.body)
        }
      })
      
    req.get('/uclee-backend-web/config').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        config: data.config
      })
    })
      
    req.get('/uclee-user-web/selectAllMarketingEntrance').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        list: data.list
      })
    })
    
    req
      .get('/uclee-user-web/getVipInfo')
      .end((err, res) => {
        if (err) {
          return err
        }

        if (res.text) {
          this.setState(res.body)
        }
        if(this.state.fail === false){
        	alert("状态异常,请退出页面重新进入")
        	return;
        }
      })
      
  	  
  
  }
  _clickHander=(url,cardStatus)=>{
    if(this.state.allowRecharge){
      window.location=url;
    }else{
      alert(cardStatus);
    }
  }
  
  render() {
  	
  	var list = this.state.list.map((item,index)=>{
        return (
            <div
              className="card-nav-item" onClick={() => { window.location=item.url+"?merchantCode="+localStorage.getItem('merchantCode')}}
              key={index}
            >
	            <img src={item.imgUrl} className='card-nav-item-image' width="50" height="50"/>
		        <span className="card-nav-item-text">
		          {item.name}
		        </span>
            </div>
        )
   })
  	
    return (
      <DocumentTitle title="我的会员卡">
        <div className="member-card">
          <div onClick={this._setting} className="member-card-setting">
            <div className="media">
              <div className="media-left">
                <img src={this.state.image} alt="" className="member-card-avatar" width="50" height="50"/>
              </div>
              <div className="media-body">
                <span>{this.state.nickName}</span>
                {
                  !this.state.cVipCode ?
                  <span className="member-card-setting-link">绑定会员卡 ></span>
                  :
                  <span className="member-card-setting-link"><a href="install">设置>></a></span>
                }
              </div>
            </div>
          </div>

          <div className="member-card-list">
            {
              this.state.cVipCode ?
              <MemberCardItem balance={this.state.balance} code={this.state.cVipCode} bonusPoints={this.state.bonusPoints} _clickHander={this._clickHander} cardStatus={this.state.cardStatus} allowRecharge={this.state.allowRecharge}/>
              :
              <NoItem />
            }
            
          </div>
          <div className="member-card-list">
            <div className="member-card-item">
              <div className="member-card-item-code">出示付款码:</div>
              {
                this.state.vipJbarcode&&this.state.vipJbarcode!==''?
                  <div className='member-card-image'><img src={this.state.vipJbarcode} className="member-card-image-barcode" alt=""/></div>:null
              }
              {
                this.state.vipImage&&this.state.vipImage!==''?
                  <div className='member-card-image'><img src={this.state.vipImage} className="member-card-image-item" alt=""/></div>:null
              }
            </div>
          </div>
          <div className="card-nav">
	        {
              !this.state.cVipCode ?
              null
              :
              list
            }	
	      </div>
        </div>
      </DocumentTitle>
      )
  }

  _setting = () => {
    if (!this.state.cVipCode) {
    	var cc = this.state.config.startUp;
    if(cc ==3){
    	alert("未启用此功能")
    }else{
   	 if(cc == 0){
      browserHistory.push({
        pathname: '/member-setting'
      })
     }else if(cc == 1){
     browserHistory.push({
        pathname: '/member-setting1'
     })
     }
     else{
     browserHistory.push({
        pathname: '/member-setting2'
     })	
     }
     }
    }
  }
}

export default MemberCard
