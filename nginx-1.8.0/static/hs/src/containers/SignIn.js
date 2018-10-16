import React from 'react'
import DocumentTitle from 'react-document-title'
import './sign-in.css'
import Navi from './Navi'
import moment from 'moment'
import req from 'superagent'
class SignIn extends React.Component {
	constructor(props) {
	    super(props)
	    this.state = {
	      voucherCode:'',
	      voucherText:'',
	      nickName: '',
	      pDate: '',
	      cBirthday: '',
	      point:0,
	      signText:'',
	      cVipCode: null,
	      limits:0,
		  rest:0,
		  accumulation:0,
		  accumulations:0
	    }
	}
	componentDidMount() {
	req
      .get('/uclee-user-web/getUserInfo')
      .query({
        t: new Date().getTime()
      })
      .end((err, res) => {
        if(res&&res.body){
          this.setState(res.body)
        }
      })
    req
		.get('/uclee-user-web/getLotteryConfig')
		.end((err, res) => {
			if (err) {
				return err
			}
			this.setState({
				limits:res.body.limits,
				rest:res.body.rest
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
      	
      req.get('/uclee-user-web/getSignText').end((err, res) => {
      if (err) {
        return err
      }
      var d = res.body

      this.setState({
        signText: d.signText
      })
    })
	}
	
	render(){
		var myDate = new Date()
		var date=myDate.toLocaleDateString();
        return(
        	<DocumentTitle title="签到">
        	<div className="check-table">
        		<div className="check-table-co">
                 	今日日期:{date}
            	</div>
        		<div className="check-table-upper">
        			<span>
        				尊贵的 {this.state.nickName},
        			</span>
        		</div>
        		<div className="check-table-vipcode">
        			目前你的积分为: {this.state.point}
        		</div>
           		<div className="check-table-vipcode" onClick={() =>{
            		if(this.state.cVipCode===null){
                		alert("请先绑定会员。");
                		window.location="/member-card"
                		return ;
              		}
            		req.get('/uclee-user-web/SigningGift')
					   .end((err, res) => {
							if (err) {
								return err
							}
						})
              		req.get('/uclee-user-web/signInHandler')
					    .end((err, res) => {
                  			var data = JSON.parse(res.text);
                  			if(data.existed){
                    			alert("今天已经签到过了哦~")
                    			return;
                  			}
              				if(data.result){
                    			this.setState({
                      				point : Number(this.state.point) + Number(data.point),
                      				isSigned:true
                    			})
                    			alert("签到成功，积分+" + data.point+"，连续签到次数:+"+1+"!")
                    			window.location="/SignIn"
                    			return;
                           	}
                  			if(!data.result){
                    			alert("网络繁忙请稍后重试")
                    			return;
                  			}
                		})
            		}}>  	
            			<div className="check-wrap">
            				<button type="button" className="check-btn-wrap" >
            					<span>{!this.state.isSigned? "点击签到 " : "今日已签"}</span>
							</button>
            			</div> 
          			</div>
          			<div className="check-table-vipcode">
          				<div className="check-limits">
            				<span>连续签到第{(this.state.accumulation==0 ? this.state.accumulations : this.state.accumulation)}天</span>
            			</div>
            		</div>
        			<div className="check-table-on">
        				<span>签到规则说明</span>
        					<div className="check-table-dui">	
								<span style={{padding:'10px',lineHeight:'25px',whiteSpace: 'pre-line'}}>
									{this.state.signText}
								</span>
							</div>
        			</div>
        	</div>
        	</DocumentTitle>
        );
	}
}
export default SignIn