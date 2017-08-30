import React from 'react'
import DocumentTitle from 'react-document-title'
import './member-card.css'
import req from 'superagent'
import { browserHistory } from 'react-router'

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
    <div className="member-card-item">
      <div className="member-card-item-code">卡号：{props.code}</div>
      <div className="member-card-item-balance">余额：{props.balance} 元</div>
      {
        props.allowRecharge?
        <div className="member-card-item-recharge">
        <a href="/seller/recharge" className="btn btn-success">充值</a>
      </div>
      :null
      }
      
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
      vipJbarcode:''
    }
  }

  componentDidMount() {
    req
      .get('/uclee-user-web/getUserInfo')
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
              <MemberCardItem balance={this.state.balance} code={this.state.cVipCode} allowRecharge={this.state.allowRecharge}/>
              :
              <NoItem />
            }
            
          </div>
          <div className="member-card-list">
            <div className="member-card-item">
              <div className="member-card-item-code">电子会员卡</div>
              {
                this.state.vipImage&&this.state.vipImage!==''?
                  <div className='member-card-image'><img src={this.state.vipImage} className="member-card-image-item" alt=""/></div>:null
              }{
                this.state.vipJbarcode&&this.state.vipJbarcode!==''?
                  <div className='member-card-image'><img src={this.state.vipJbarcode} className="member-card-image-barcode" alt=""/></div>:null
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