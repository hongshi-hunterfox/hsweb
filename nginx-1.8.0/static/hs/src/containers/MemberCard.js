import React from 'react'
import DocumentTitle from 'react-document-title'
import './member-card.css'
import req from 'superagent'
import { browserHistory } from 'react-router'
import './member-center.css'
import fto from 'form_to_object'
 
 
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
      <div className="member-card-item-code">卡号：{props.code}
      </div>
      <div className="member-card-item-balance">余额：{props.balance} 元</div>
      
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
      image: '',
      nickName: '',
      
      vipImage:'',
      allowRecharge:true,
      vipJbarcode:'',
      cardStatus:''
    }
  }

  componentDidMount() {
    req
      .get('/uclee-user-web/getUserInfo/')
      .end((err, res) => {
        this.setState({
          image: res.body.image,
          nickName: res.body.nickName
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
                  <span className="member-card-setting-link">设置 ></span>
                  : null
                }
              </div>
            </div>
          </div>

          <div className="member-card-list">
            {
              this.state.cVipCode ?
              <MemberCardItem balance={this.state.balance} code={this.state.cVipCode} _clickHander={this._clickHander} cardStatus={this.state.cardStatus} allowRecharge={this.state.allowRecharge}/>
              :
              <NoItem />
            }
            
          </div>
          <div className="member-card-list">
            <div className="member-card-item">
              <div className="member-card-item-code">电子会员卡:
              <span onClick={() => { 
              	var conf = confirm('确定解绑吗？解绑后会员功能将无法使用!');
          	if(!conf){
          		return;
          	}else{
              req
		  					.get('/uclee-user-web/changeVip')
		  					.end((err, res) => {
		  						          
                 						alert("解绑成功,请返回页面刷新!")
                 						window.location.reload();
                    				return;
                    				window.location.reload();
                  })}
		  					}}className="member-card-item-Unbundling"><button type="submit" className="btn btn-warning btn-sm" >解除绑定</button>
		  				</span>    
              </div>
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
        </div>
      </DocumentTitle>
      )
  }

  _setting = () => {
    if (!this.state.cVipCode) {
      browserHistory.push({
        pathname: '/member-setting'
      })
    }
  }
}

export default MemberCard