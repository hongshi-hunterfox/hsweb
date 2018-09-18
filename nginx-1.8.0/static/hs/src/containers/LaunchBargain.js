/* global wx */
import React from 'react'
import DocumentTitle from 'react-document-title'
import Loading from '../components/Loading'
import './launch-bargain.css'
import req from 'superagent'
import CheckBox from '../components/CheckBox'
import Counter from '../components/Counter'
import Big from 'big.js'
var myDate = new Date()
var Date1 = new Date(myDate).getTime()

class LaunchBargain extends React.Component {
	constructor(props) {
		super(props)
		this.state = {
			valueId: this.props.params.valueId,
			values: {},
			isShareShow: false,
			users: [],
			sunMoney: 0,
			status:true,
			size: 0,
			brand:'',
			status1:'',
			record:'',
			isTrue:false,
			config:{},
			result:0
		}
	}
	componentDidMount() {
		var str=this.state.valueId;
		var num = str.indexOf("&")
		console.log("num===="+num)
		var valueid=str.substr(0,num);
		console.log("valueis===="+valueid)
		var codes=str.substr(num+1);
		console.log("codes====="+codes)
		req
			.get('/uclee-user-web/goBargain')
			.query({
				valueId: valueid,
				codes: codes
			})
			.end((err, res) => {
				if(err) {
					return err
				}
				var data = JSON.parse(res.text)
				this.setState({
					values: data.values,
					status: data.status,
					size: data.size,
					status1: data.status1
				})
				if(this.state.status1!==undefined){
					alert(this.state.status1)
					window.location='/bargain';
					return;
				}
			})
		var str=this.state.valueId;
		var num = str.indexOf("&")
		console.log("num===="+num)
		var codes=str.substr(num+1);
		console.log("codes====="+codes)
		req
			.get('/uclee-user-web/getBargainUser')
			.query({
				codes: codes
			})
			.end((err, res) => {
				if(err) {
					return err
				}
				var data = JSON.parse(res.text)
				this.setState({
					users: data.users,
					sunMoney: data.sunMoney
				})
			})
		req.get('/uclee-user-web/storeList').end((err, res) => {
			if (err) {
				return err
			}
			var c = JSON.parse(res.text)
			console.log(c.storeList)
			this.setState({
				brand:c.brand
			})
		})
		
		req
			.get('/uclee-user-web/wxConfig')
			.query({
				url: window.location.href.split('#')[0]
			})
			.end((err, res) => {
				var c = JSON.parse(res.text)
				wx.config({
					debug: false,
					appId: c.appId,
					timestamp: c.timestamp,
					nonceStr: c.noncestr,
					signature: c.signature,
					jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage']
				})
				
				wx.ready(() => {
					wx.onMenuShareAppMessage({
					title: this.state.values.name, // 分享标题
					desc: '我在'+this.state.brand+'发现了一件好东西:砍到'+this.state.values.price+'元可以购买，快来帮我砍价吧',  // 分享描述
					link: window.location.href, // 分享链接
					imgUrl: this.state.values.imageUrl, // 分享图标
					success: function() {},
					cancel: function() {}
				})

				  wx.onMenuShareTimeline({
				  	title: '我在'+this.state.brand+'发现了一件好东西:砍到'+this.state.values.price+'元可以购买，快来帮我砍价吧', // 分享标题
					link: window.location.href, // 分享链接
					imgUrl: this.state.values.imageUrl, // 分享图标
					success: function() {},
					cancel: function() {}
				   })
				})
			})
			
			req.get('/uclee-backend-web/config').end((err, res) => {
      			if (err) {
        			return err
     			}
      			var data = JSON.parse(res.text)
      			this.setState({
        			config: data.config,
     			})
      		console.log(this.state.config)
    		})
	}

	render() {
//		if(this.state.status === true&&this.state.values.price === (this.state.values.hsGoodsPrice - this.state.sunMoney)){
//			req
//			.get('/uclee-user-web/sucessMsg')
//			.end((err, res) => {
//				if(err) {
//					return err
//				}
//			})
//		}
		var userList = this.state.users.map((item, index) => {
			return(
			  	<div className='usersList' key={index}>	
					<img
            			src={item.image}
            			alt=''
            			width="35"
            			height="35"
            			className="img-circle"
          			/>
					<span>{item.nickName}</span>
					<span className='right pull-right'>帮你砍掉{item.randomAmount}元</span>
				</div>
			)
		})
		
		var str=this.state.valueId;
		var num = str.indexOf("&")
		var valueid=str.substr(0,num);
		var codes=str.substr(num+1);
		
		var pri = this.state.values.hsGoodsPrice - this.state.values.price;
		var value = this.state.sunMoney/pri;
		var bfb = value*100;
		return(
			<DocumentTitle title="发起砍价">
				<div className="launch-bargain">
        			<div className="launch-bargain-items">
        			{
            			this.state.isTrue === true 
            			?
            			<div>
            				<label className="control-label col-md-3" style={{marginTop:'45px'}} />
		            		<div className='launch-bargain-item-cardstyle' onClick={() => this.setState({ isTrue:false })}>
			            		<div className='launch-bargain-item-on'>
			            			活动规则
			            			<div style={{padding:'10px',lineHeight:'25px',whiteSpace: 'pre-line',color:'#FF7256'}}>
				            				{this.state.config.bargainText}
				            		</div>
				            		<label className="control-label col-md-3" style={{marginTop:'3px'}} />
				            		<div style={{color:'#DCDCDC'}}>
				            		<i className="fa fa-times-circle fa-2x" aria-hidden="true"></i>
				            		</div>
				            	</div>
		            		</div>
            			</div>
            			:
        				(this.state.isShareShow
							?
							<div className="launch-bargain-items-gx" onClick={() => this.setState({ isShareShow:false })}>
								<img
              						src='/images/gx.png'
              						alt=''
              						width='100%'
              						height='auto'
              					/>
								<span className='launch-bargain-items-fontTitle'>恭喜你!</span>
								<span className='launch-bargain-items-fontMoney'>已砍<font color='red'>{this.state.sunMoney}元.</font></span>
								<span className='launch-bargain-items-fontText'>点右上角邀请朋友帮你砍一刀</span>
							</div>
							:
							<div className="launch-bargain">
								<label className="control-label col-md-3" style={{marginTop:'10px'}} />
								<div className="launch-bargain-item">
          							<div className='launch-bargain-item-cardstyle'>
            							<div className="launch-bargain-item-img">
              								<img
              									src={this.state.values.imageUrl}
              									alt=''
              									width="80"
              									height="80"
              								/>
            							</div>
            							<div className="launch-bargain-item-checkin" onClick={() =>
              								this.setState({
												isTrue:true
											})
            							}>
            								活动规则
            							</div>
            							<div className="launch-bargain-item-info">
                							<div className="launch-bargain-item-title">                              
                  								产品名称:{this.state.values.productName}                     
                							</div>
                							<div  className="launch-bargain-item-spec">                             
                  								规格:{this.state.values.value}	                          
                							</div>
                							<div className="launch-bargain-item-spec">
                								库存:{this.state.values.hsStock}
                							</div>
	                						<div className="launch-bargain-item-spec">
	                  							价格:{this.state.values.hsGoodsPrice}元
	                						</div>
	                						<div className="launch-bargain-item-spec">
	                  							结束日期:{this.state.values.ends}
	                						</div>
            							</div>
          							</div>
          						</div>
        						<label className="control-label col-md-3" style={{marginTop:'20px'}} />
        				
								{this.state.isShareShow == true
								? null
								:
								<div className="launch-bargain-item">
									<div className='launch-bargain-item-cardstyle'>
										<div className='launch-bargain-item-button'>
										{this.state.status !== false ?
            								<button type="button" className="btn btn-danger" onClick={() =>
              									this.setState({
													isShareShow:true
												})
            								}>
												邀请朋友帮忙砍价
            								</button>
            								:
            								<div>
            									<div>
            									{this.state.size > 0 || this.state.values.price === (this.state.values.hsGoodsPrice - this.state.sunMoney) ?
            										<button type="button" className="btn btn-danger" onClick={()=>{                        				
                            							window.location='/bargain';
                            						}}>
														我也要试试
            										</button>
            										:
            										<button type="button" className="btn btn-danger" onClick={()=>{
                            							req
                            							.get('/uclee-user-web/insertBargainLog?codes='+codes+'&productName='+this.state.values.productName+'&valueId='+valueid)
                            							.end((err, res) => {
															if(err) {
																return err
															}
															var data = JSON.parse(res.text)
															this.setState({
																result: data.result
															})
															if(this.state.size > 0){
                            									alert("已经帮忙砍过了，快去邀请朋友帮忙砍价吧");
                            								}else{
                            									alert("恭喜你砍掉"+this.state.result+"元")
                            									window.location.reload();
                            								}
        												})
                            					
                            							
                            				
                            							}}>
															砍一刀
            											</button>
            									}
            									</div>
            									<label className="control-label col-md-3" style={{marginTop:'10px'}}>
          										</label>
            									<div>
            										{this.state.values.price === (this.state.values.hsGoodsPrice - this.state.sunMoney) 
            										?
            										null
            										:
            										<button type="button" className="btn btn-danger" onClick={() =>
              											this.setState({
															isShareShow:true
														})
              										}>
            											邀请朋友帮他砍
            										</button>
            										}
            									</div>
            								</div>
										}
          								</div>
          									<label className="control-label col-md-3" style={{marginTop:'10px'}} />
          									<div className='launch-bargain-item-button'>
          									{
          									this.state.status === true&&this.state.values.price === (this.state.values.hsGoodsPrice - this.state.sunMoney)
          									?
												<button type="button" className="btn btn-success" 
													onClick={this._clickNext.bind(
															this,
															this.state.values.price,
													)}>
														砍价成功,点击购买
            									</button>
            									:
            									<div>
            										<div className="progress progress-striped active">
            											<div className="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style={{width:bfb.toFixed(2)+'%'}}>
            								        		{bfb.toFixed(2)}%
														</div>
            								    	</div>
            											已砍:{this.state.sunMoney}元,邀请朋友砍到{this.state.values.price}元下单.
            										{/*<meter id="meterid" value={value} max="1" low="30" high="60" optimum={value} style={{width:'290px'}}>您的浏览器版本不能显示此控件</meter>*/}
            									</div>
            								
            								}
          								</div>
          							</div>
          							<label className="control-label col-md-3" style={{marginTop:'20px'}} />
          						
          							<div className='launch-bargain-item-cardstyle'>
          								<div className='launch-bargain-item-roll'>
          									<div>
          										砍价记录:
          										<div>
          											{userList}
          										</div>
         									</div>
         								</div>
         							</div>
          						</div>
          					}
							</div>
						)
        			}
        			</div>
        		</div>
      		</DocumentTitle>
		)
	}
	
_clickNext = (bargainPrice) => {
	req
	.get('/uclee-user-web/status')
	.end((err, res) => {
		if(err) {
			return err
		}
		var c = JSON.parse(res.text)
		console.log(c.storeList)
		this.setState({
			record:c.record
		})
		if(this.state.record === undefined){
			window.location = '/cart'
			return;
		}else{
			  // 加入购物车
    		var str=this.state.valueId;
			var num = str.indexOf("&")
			var valueid=str.substr(0,num);
			localStorage.setItem('bargainPrice', this.state.values.price),
    		req
      		.post('/uclee-user-web/cartHandler')
      		.send({
        		amount: 1,
        		productId: parseInt(this.state.values.productId, 10),
        		specificationValueId: this.state.values.valueId,
        		activityMarkers: 1,
      		}) 
      		.end((err, res) => {
        		if (err) {
          			return err
        		}
        		var result = JSON.parse(res.text);
        		if(result.result){
         			 window.location = '/cart'
        		}else{
          			alert(result.reason);
        		}
        
      		})
    
    		return

    // 立即购买
		}
	})
  

  }
}

export default LaunchBargain