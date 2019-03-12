import React from 'react'
import DocumentTitle from 'react-document-title'
import './boss-center.css'
import req from 'superagent'
var Link = require("react-router").Link


class DeliveryCenter extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      nickName: '',
      point:0,
      phone:localStorage.getItem('napaStorePhone'),
      hsCode:localStorage.getItem('hsCode'),
      type:this.props.location.query.type,
      napaStores:[],
      items:[],
      itemo:[]
    }
  }

  componentDidMount() {
    if(!localStorage.getItem('napaStorePhone')){
      window.location='/driver-login'
    }
    req
      .get('/uclee-user-web/DeliveryCenter')
      .query({
        phone: this.state.phone,
        hsCode:this.state.hsCode,
        type:this.state.type
      })
      .end((err, res) => {
        if(!res.body.result){
          if(res.body.reason==='no_department'){
            alert("当前司机尚未关联门店");
            window.location="phone-login";
          }
        }
        this.setState({
          napaStores:res.body.napaStores,
          items:res.body.items,
        })
      })
     
  }

  
  _setHsCode=(e)=>{
    localStorage.setItem('hsCode', e.target.value);
    localStorage.setItem('type', this.props.location.query.type);
    window.location='/delivery-center';
  }
  
  render() {
  	//加盟店列表
    var option = this.state.napaStores.map((item,index)=>{
        return(
          <option key={index} value={item.hsCode} selected={this.state.hsCode===item.hsCode?'selected':null}>{item.storeName}</option>
        );  
    })
	var items = this.state.items.map((item,index)=>{
        return (
            <div className="order-list" key={index}> 			
							<div className="order-list-top">
								<div className="number">
									<span>订单编号：{item.code}
										{item.outerOrderCode !== null && item.outerOrderCode !== ""
											? <span>(微商城单号：{item.outerOrderCode})</span>
											: null}
									</span><span className="pull-right" style={{color:'red'}}>{item.type}</span><br/>
									<span>取货时间：{item.time}</span><br/>
									<span>取货部门：{item.department}</span><br/>
									<span>联系电话：{item.phone}</span><br/>
									<span>配送地址：{item.address}</span><br/>
								</div>
							</div>
							<div>
								{item.type == "需配送"?
									<button className='btn btn-default' onClick={()=>{
											req
			                 .get('/uclee-user-web/updateDetaileStart?orderID='+item.id)
			                 .end((err, res) => {				          
			                 	if(res.body > 0) {
		                 			alert("开始配送成功")
		                 			window.location.reload();
		                 		}else{
			                 		alert("开始配送失败")
			                 		window.location.reload();
			                 	}
			                 })
										}} 
										style={{float:'right',padding:'5px 12px',margin:'6px 20px',backgroundColor:'#f15f40',color:'white'}} >
										开始配送
									</button>:null}
								{item.type == "配送中"?
									<button className='btn btn-default' onClick={()=>{
										req
		                 .get('/uclee-user-web/updateDetaileEnd?orderID='+item.id)
		                 .end((err, res) => {				          
		                 	if(res.body > 0) {
		                 		alert("配送完成成功")
		                 		window.location.reload();
		                 	}else{
		                 		alert("配送完成失败")
		                 		window.location.reload();
		                 	}
		                 })
										}} 
										style={{float:'right',padding:'5px 12px',margin:'6px 20px',backgroundColor:'#f15f40',color:'white'}}>
										配送完成
									</button>:null}
							</div>
						
	          	<div>
								<span onClick={()=>{window.location="/myOrderDetail/" + item.outerOrderCode}} className='btn btn-default' style={{float:'right',padding:'5px 12px',margin:'6px 20px',backgroundColor:'#09F7C7',color:'white'}} >订单详情</span>
							</div>
            </div>
        );
	})
    return (
      <DocumentTitle title="配送中心">
        <div className="boss-center">
            <img src='/images/data.png' alt=""/>
            <div className='boss-center-select'>
              请选择门店：
              <select name='hsCode' onChange={this._setHsCode}>
                <option value="">全部数据</option>
                {option}
              </select>
            </div>
	            <ul className="nav nav-tabs">
							  <li role="presentation" className="active"><button onClick={()=>{window.location='/delivery-center?phone='+this.state.phone+"&type=0"}} className="btn btn-default">已下单</button></li>
							  <li role="presentation" className="active"><button onClick={()=>{window.location='/delivery-center?phone='+this.state.phone+"&type=1"}} className="btn btn-default">需配送</button></li>
							  <li role="presentation" className="active"><button onClick={()=>{window.location='/delivery-center?phone='+this.state.phone+"&type=2"}} className="btn btn-default">配送中</button></li>
							  <li role="presentation" className="active"><button onClick={()=>{window.location='/delivery-center?phone='+this.state.phone+"&type=3"}} className="btn btn-default">配送完成</button></li>
							</ul>
						<div>
							{items}
						</div>
        </div>
      </DocumentTitle>
    )
  }
}

export default DeliveryCenter
