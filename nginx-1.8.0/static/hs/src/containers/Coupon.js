import React from 'react'
const QRCode = require('qrcode.react');
import DocumentTitle from 'react-document-title'
import './coupon.css'
import './order.css'
import Navi from './Navi'
import req from 'superagent'
import Icon from '../components/Icon'


class Coupon extends React.Component{
	constructor(props) {
	    super(props)
	    this.state = {
	      coupons: [],
	      voucherCode:'',
	      voucherText:'',
	      fullamount:'',
	      convertibleGoods:'',
	      remarks:'',
	      showBarCode: false,
	      bar:''
	    }
	}

    componentDidMount() {
        	req
              .get('/uclee-user-web/getCoupon')
              .end((err, res) => {
                if (err) {
                  return err
                }
                var resJson = JSON.parse(res.text);
                this.setState({
                coupons:resJson.coupons
                })
              })
              if(sessionStorage.getItem('voucher')!=null){
      	        this.setState({
                  voucherCode: JSON.parse(sessionStorage.getItem('voucher'))
                })
              }
              if(sessionStorage.getItem('fullamount')!=null){
      	        this.setState({
                  fullamount: JSON.parse(sessionStorage.getItem('fullamount'))
                })
              }
              if(sessionStorage.getItem('voucher_text')!=null){
                this.setState({
                voucherText: JSON.parse(sessionStorage.getItem('voucher_text'))
                })
              }
               if(sessionStorage.getItem('convertibleGoods')!=null){
                this.setState({
                convertibleGoods: JSON.parse(sessionStorage.getItem('convertibleGoods'))
                })
              }
              if(sessionStorage.getItem('remarks')!=null){
                this.setState({
                remarks: JSON.parse(sessionStorage.getItem('remarks'))
                })
              }
	}

	  render(){
	  	
	  	
//var BarCode = (props) => {
//if (this._) {
//    return (
//      <div style={{textAlign:'center'}} onClick={props.closeBar}>
//        <div onClick={(e) => {e.stopPropagation()}}>
//        	<span style={{float:'right',paddingRight:'5px'}} onClick={props.closeBar}><Icon name="times-circle"/></span>
//        	<div style={{textAlign:'center'}}>
//        		<QRCode value={this.state.barcode} size={120} /><br/>
//        		<span>优惠券编号：{this.state.barcode}</span>
//        	</div>
//        </div>
//      </div>
//  )
//}
//	return null
//}
	  	
	  	var coupons = this.state.coupons.map((item, index) => {
            return(
            	<div className={
						"coupon-item"
					} key={index} onClick={this._pickvoucher.bind(this,item.vouchersCode,item.payQuota,item.fullamount,item.convertibleGoods,item.remarks)}>
	            	<div className='top-line'>
	            		<div className="title">{item.name}</div>
	            		<div className="condition"><i className="fa fa-qrcode fa-3x" aria-hidden="true"></i></div>
	            	</div> 
	            	<div className='center-line'>
	            		<div className="money">
	            		￥<span className='number'>{item.payQuota}</span>
	            		</div>
	            	</div>
	            	<div className='bottom-line'>
	            		<div className="date">有效期截止至：{item.endTime}</div>	            		
	            		<div className="date">{item.remarks}</div>
	            		{this.state.voucherCode === item.vouchersCode&&this.props.location.query.isFromOrder?<span className="fa fa-check icon-check tag" />:<span className="tag" />}
	            	</div> 
	            	<div>

            </div>
	            	
	            </div>
            );
        });
        return(
        	<DocumentTitle title="我的优惠券">
        		<div className="coupon">
        		{this.state.showBarCode === false ?
        			<div>
	        			<div className="coupon-item">
		            		<div className='bottom-line'>
				             	<span className="date">
				               		<font color="red" size='4' Face="楷体"><marquee loop='1'>已过期优惠券,已使用优惠券此页面会自动屏蔽不显示哦~~~</marquee></font>
				                </span>
		            		</div>
		            	</div>
		        		{coupons}
		        			{this.props.location.query.isFromOrder?<div className="coupon-bottom">
		        				<button
									type="button"
									className="btn btn-default coupon-bottom-button"
									onClick={this._cancelHandler}
								>
									返回
								</button>
								<button
									type="button"
									className="btn btn-default coupon-bottom-button"
									onClick={this._submitHandler}
								>
									确定
								</button>
							</div>:null
							}
		        		<Navi query={this.props.location.query}/>
		        		<div className="order">
	        				<div className="bottom-text"> 
								O(∩_∩)O 啊哦，到底啦~~~
							</div>
						</div>
					</div>
					:
					<div>
						<div style={{float:'right',paddingRight:'10px',paddingTop:'10px'}} onClick={this._closeShowCode}><li className="fa fa-times fa-2x"/></div>
          				<div style={{textAlign:'center',paddingTop:'65px'}}>
          			 		<QRCode value={this.state.bar} size={150} />
          				</div>
          				<div style={{textAlign:'center',paddingTop:'10px'}}>优惠券编号：{this.state.bar}</div>
          			</div>}
            	</div>
        	</DocumentTitle>
        );
	  }
	  
	  _closeShowCode =()=>{
	  	this.setState({
	  		showBarCode: false,
	  	})
	  }
	  _pickvoucher = (code,money,amount,Goods,remarks) =>{
	  	if(this.props.location.query.isFromOrder){
	  		if(this.state.voucherCode!==code){
	  		this.setState({
		  		voucherCode: code,
		  		voucherText:money,
		  		fullamount:amount,
		  		convertibleGoods:Goods,
		  		remarks:remarks,
		  	})	
	  	}else{
	  		this.setState({
		  		voucherCode: '',
		  		voucherText:'',
		  		fullamount:'',
		  		convertibleGoods:'',
		  		remarks:''
		  	})	
		  }
	  	}else{
	  		this.setState({
		  		showBarCode: true,
	  			bar:code
		  	})	
	  	}
	  	
	  	
	  }
	  _submitHandler=()=>{
	  	if(this.props.location.query.isFromOrder){
			sessionStorage.setItem('voucher', JSON.stringify(this.state.voucherCode));
			sessionStorage.setItem('voucher_text', JSON.stringify(this.state.voucherText));
			sessionStorage.setItem('fullamount', JSON.stringify(this.state.fullamount));
			sessionStorage.setItem('convertibleGoods', JSON.stringify(this.state.convertibleGoods));
			sessionStorage.setItem('remarks', JSON.stringify(this.state.remarks));
			
		}
		window.location='/order'
	  }
	   _cancelHandler=()=>{
	  	window.location='/order'
	  }
}

export default Coupon