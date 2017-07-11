import React from 'react'
import DocumentTitle from 'react-document-title'
// import hero from './member-center.jpg'
import './boss-center.css'
// import Icon from '../components/Icon'
// import LinkGroup from '../components/LinkGroup'
// import LinkGroupItem from '../components/LinkGroupItem'
 import req from 'superagent'
// import Navi from './Navi'
// import Footer from '../components/Footer'

class BossCenter extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      nickName: '',
      point:0,
      phone:localStorage.getItem('napaStorePhone'),
      hsCode:localStorage.getItem('hsCode'),
      napaStores:[],
      items:[]
    }
  }

  componentDidMount() {
    if(!localStorage.getItem('napaStorePhone')){
      window.location='/phone-login'
    }
    req
      .get('/uclee-user-web/bossCenter')
      .query({
        phone: this.state.phone,
        hsCode:this.state.hsCode
      })
      .end((err, res) => {
        if(!res.body.result){
          if(res.body.reason==='no_department'){
            alert("当前老板尚未关联加盟店");
            window.location="phone-login";
          }else{
            alert("网络繁忙，请稍后重试")
          }
          return ;
        }
        this.setState({
          napaStores:res.body.napaStores,
          items:res.body.items
        })
      })
  }

  _setHsCode=(e)=>{
    localStorage.setItem('hsCode', e.target.value);
    window.location='/boss-center';
  }
  render() {
    var items = this.state.items.map((item,index)=>{
        return (
            <div className='item' key={index}>
                <div className='top'>
                {item.valueType==='money'?item.value.toFixed(2):item.value}
                </div>
                <div className='bottom'>
                {item.coption}
                </div>
            </div>
        );
    })
    var option = this.state.napaStores.map((item,index)=>{
        return(
          <option key={index} value={item.hsCode} selected={this.state.hsCode===item.hsCode?'selected':null}>{item.storeName}</option>
        );  
    })
    return (
      <DocumentTitle title="老板助手">
        <div className="boss-center">
            <img src='/images/data.png' alt=""/>
            <div className='boss-center-select'>
              请选择加盟店：
              <select name='hsCode' onChange={this._setHsCode}>
                <option value="">全部数据</option>
                {option}
              </select>
            </div>
            <div className='boss-center-top'>
              {items}
            </div>
            <div className='boss-center-bottom'>
              <div className='item'>
                <div className='top '>
                <span className='fa fa-line-chart'/>
                </div> 
                <div className='bottom'>
                10000
                </div>
              </div>
              <div className='item'>
                <div className='top '>
                <span className='fa fa-line-chart'/>
                </div> 
                <div className='bottom'>
                10000
                </div>
              </div>
              <div className='item'>
                <div className='top '>
                <span className='fa fa-line-chart'/>
                </div> 
                <div className='bottom'>
                10000
                </div>
              </div>
              <div className='item'>
                <div className='top '>
                <span className='fa fa-line-chart'/>
                </div> 
                <div className='bottom'>
                10000
                </div>
              </div>
              <div className='item'>
                <div className='top '>
                <span className='fa fa-line-chart'/>
                </div> 
                <div className='bottom'>
                10000
                </div>
              </div>
              <div className='item'>
                <div className='top '>
                <span className='fa fa-line-chart'/>
                </div> 
                <div className='bottom'>
                10000
                </div>
              </div>
            </div>
        </div>
      </DocumentTitle>
      )
  }
}

export default BossCenter
