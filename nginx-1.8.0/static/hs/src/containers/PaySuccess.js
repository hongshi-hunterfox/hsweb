import React from 'react'
import DocumentTitle from 'react-document-title'
import moment from 'moment'
import req from 'superagent'
class PaySuccess extends React.Component {
	constructor(props) {
	    super(props)
	    this.state = {
			wxdcCode:this.props.location.query.wxdcCode,
			total:this.props.location.query.total,
			storeId:this.props.location.query.storeId,
			station:this.props.location.query.station,
	    }
	}
	componentDidMount() {}
	
	_goGoodsList =()=>{
		window.location="/all-goods?storeId="+this.state.storeId+"&station="+this.state.station
	}
	
	render(){
        return(
        	<DocumentTitle title="支付成功">
        	<div style={{textAlign:'center'}}>
        		<div style={{paddingTop:'20px'}}>
	        		<img 
	        			src='/images/check-circle.png' 
	        			alt=""
	        			width="100"
	        			height="100"
	        			/>
        		</div>
        		<div style={{paddingTop:'15px',fontSize:'20px',fontWeight:'bold'}}>支付成功</div>
        		<div style={{paddingTop:'15px',fontSize:'15px',color:'#C2C2C2'}}>点餐单号：{this.state.wxdcCode}</div>
        		<div style={{paddingTop:'15px',fontSize:'15px',color:'#C2C2C2'}}>支付金额：{this.state.total}</div>
        		<div style={{paddingTop:'45px',paddingLeft:'10px',paddingRight:'10px'}}>
        			<button className="btn btn-info btn-lg btn-block" onClick={this._goGoodsList}>确定</button>
        		</div>
        	</div>
        	</DocumentTitle>
        );
	}
}
export default PaySuccess