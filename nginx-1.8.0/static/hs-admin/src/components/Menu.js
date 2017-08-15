import React from 'react'
import { Link } from 'react-router'

class Menu extends React.Component {
  render() {
    return (
      <div style={{
        paddingTop: 30
      }}>
        <div className="list-group">
          <Link to={'/user-list?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            用户列表
          </Link> <Link to={'/product-list?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            产品管理
          </Link>
          <Link to={'/freight?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            运费配置
          </Link>
          <Link to={'/lottery?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            积分抽奖配置
          </Link>
          <Link to={'/phoneUserList?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            加盟商管理
          </Link>
          <Link to={'/recharge-config?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            充值赠送配置
          </Link>
           <Link to={'/user-birth-list?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            生日信息推送
          </Link>
          <Link to={'/user-unbuy-list?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            消费信息推送
          </Link>
          <Link to={'/banner-list?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            首页banner设置
          </Link>
          <Link to={'/product-group-list?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            首页产品设置
          </Link>
          <Link to={'/store-intro?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            公司介绍设置
          </Link>
           <Link to={'/shake-monitor?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            现场抽奖活动
          </Link>
           <Link to={'/global-config?merchantCode='+localStorage.getItem('merchantCode')} className="list-group-item" activeClassName="active">
            系统配置
          </Link>
        </div>
      </div>
      )
  }
}

export default Menu