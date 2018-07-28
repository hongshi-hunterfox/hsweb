import React from 'react'
import DocumentTitle from 'react-document-title'
import './show-coupon.css'
import Navi from './Navi'
import req from 'superagent'
class ShowCoupon extends React.Component {
	constructor(props) {
	    super(props)
	    this.state = {
	      coupons: [],
	      voucherCode:'',
	      voucherText:'',
	      cVipCode: null
	    }
	}
	componentDidMount() {
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
        	req
              .get('/uclee-user-web/getShowCoupon')
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
              if(sessionStorage.getItem('voucher_text')!=null){
                this.setState({
                voucherText: JSON.parse(sessionStorage.getItem('voucher_text'))
                })
              }
	}
	
	render(){
	  	var coupons = this.state.coupons.map((item, index) => {
            return(
            	<div className='show-coupon-item' key={index} onClick={this._pickvoucher.bind(this,item.vouchersCode,item.payQuota)}>
	            	<div className='top-line'>
	            		<div className="title">{item.payQuota}元现金礼券</div>
	            		<div className="condition">优惠券号:{item.barCode}</div>
	            	</div> 
	            	<div className='center-line'>
	            		<div className="money">￥<span className='number'>{item.payQuota}</span></div>
	            	</div> 
	            	<div className='bottom-line'>
	            		<div className="date">有效期截止至：{item.endTime}</div>
	            		{this.state.voucherCode === item.vouchersCode&&this.props.location.query.isFromOrder?<span className="fa fa-check icon-check tag" />:<span className="tag" />}
	            	</div> 	
	            </div>
            );
        });
        return(
        	<DocumentTitle title="绑定成功">
        	<div>        	
        			<div className="show-coupon">
        			<div className="show-coupon-up">新人礼包</div>
        			<div className="show-coupon-vipcode"><i className="fa fa-volume-up">已放入你的账户  {this.state.cVipCode}</i></div>
        	</div>	
        	<div className="show-btn">
        		<div className="show-coupon">
        			<span className="show-coupon-foll">
						{coupons}
        			</span>
        		</div>
        	</div> 
        	<span className="show-btn-items" onClick={this._cancelHandler}>知道了!</span>
			</div>
        	</DocumentTitle>
        );
	}
	_pickvoucher = (code,money) =>{
	  	if(this.state.voucherCode!==code){
	  		this.setState({
		  		voucherCode: code,
		  		voucherText:money
		  	})	
	  	}else{
	  		this.setState({
		  		voucherCode: '',
		  		voucherText:''
		  	})	
	  	}
	}
	_cancelHandler=()=>{
	  	window.location='/member-card'
	}


}
export default ShowCoupon