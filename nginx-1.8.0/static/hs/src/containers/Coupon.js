import React from 'react'
import DocumentTitle from 'react-document-title'
import './coupon.css'
import Navi from './Navi'
import req from 'superagent'
class Coupon extends React.Component{
	 constructor(props) {
	    super(props)
	    this.state = {
	      coupons: [],
	      voucherCode:'',
	      voucherText:''
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
		      if(sessionStorage.getItem('voucher_text')!=null){
		      	this.setState({
		          voucherText: JSON.parse(sessionStorage.getItem('voucher_text'))
		        })
		      }
	  }

	  render(){
	  	var coupons = this.state.coupons.map((item, index) => {
            return(
            	<div className={
						"coupon-item"
					} key={index} onClick={this._pickvoucher.bind(this,item.vouchersCode,item.payQuota)}>
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
        	<DocumentTitle title="我的优惠券">
        		<div className="coupon">
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
	  _submitHandler=()=>{
	  	if(this.props.location.query.isFromOrder){
			sessionStorage.setItem('voucher', JSON.stringify(this.state.voucherCode));
			sessionStorage.setItem('voucher_text', JSON.stringify(this.state.voucherText));
		}
		window.location='/order'
	  }
	   _cancelHandler=()=>{
	  	window.location='/order'
	  }
}

export default Coupon