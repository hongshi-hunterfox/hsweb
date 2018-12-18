import React from 'react'
import DocumentTitle from 'react-document-title'
import Loading from '../components/Loading'
import './bargain.css'
import req from 'superagent'
import CheckBox from '../components/CheckBox'
import Counter from '../components/Counter'
import Big from 'big.js'
var myDate = new Date()
var Date1 = new Date(myDate).getTime()

const BargainEmpty = props => {
  return (
    <div className="bargain-empty">
      <h4>暂时没有砍价活动,去商城逛逛吧</h4>
      <a href="/">去逛逛</a>
    </div>
  )
}

class bargain extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      loading: true,
      list: [],
      editMode: false,
      code:'',
      record:'',
      vipFail:'',
      valueId:0,
      invitationcode:'',
      limit: 'true'

    }
  }

  componentDidMount() {
    req
      .get('/uclee-user-web/getBargain')
      .end((err, res) => {
        if (err) {
          return err
        }
        this.setState({
          loading: false,
          list: res.body.list,
          codes: res.body.code,
          record: res.body.record,
          vipFail: res.body.vipFail,
          valueId: res.body.valueId,
          invitationcode: res.body.invitationcode
        })
      })
  }

  render() {
    var codes = this.state.codes;
    return (
      <DocumentTitle title="砍价活动">
        <div className="bargain-empty">
          {this.state.list.length
                ? 
                <div className="bargain">
                	<div className="bargain-bargainlist">
                    <div className="bargain-items clearfix">
                      {this.state.list.map((item, index) => {
                        return (
                        	<div>
                          	<div className="bargain-item clearfix" key={index}>
                            	<div className="bargain-item-img">
                                	<img
                                  	src={item.imageUrl}
                                  	alt=''
                                  	width="80"
                                  	height="80"
                                	/>
                            	</div>
                            	<div className="bargain-item-info">
                            		<div className="bargain-item-title">                              
                              		活动名称:{item.name}                           
                            		</div>
                            		<div  className="bargain-item-spec">                             
                              		产品名称:{item.productName}                           
                            		</div>
                            		<div  className="bargain-item-spec">                             
                              		剩余库存:{item.hsStock}                           
                            		</div>
                            		<div className="bargain-item-spec">
                            			开始时间:{item.starts}
                            		</div>
                            		<div className="bargain-item-spec">
                            			结束时间:{item.ends}
                            		</div>
                            	</div>
                          	</div>
                          	<div className="bargain-item-button">
                          		{item.hsStock <= 0 
                          			? 
                          			<button type="button" className="btn btn-danger btn-lg btn-block" disabled="disabled">
                          				已抢完
                          			</button>
                          			:
                            		<button type="button" className="btn btn-danger btn-lg btn-block" onClick={()=>{
	                            				if(this.state.vipFail==null){
	                            					
	                            					req
		                            				.get('/uclee-user-web/LaunchBargain?name='+item.name+'&hsGoodsPrice='+item.hsGoodsPrice+'&codes='+codes+'&pid='+item.id+'&productName='+item.productName)
		                            				.end((err, res) => {
		        															if (err) {
		          															return err
		        															}
		        															this.setState({
																	          stock: res.body.stock,
																	          limit: res.body.limit
																	        })
		        															if(this.state.limit != 'true'&& this.state.limit != undefined){
			                         							alert(this.state.limit)
			                         							window.location.reload()
			                         							return
																					}
		        															if(this.state.stock != undefined){
	        																	alert(this.state.stock)
			                         							window.location.reload()
			                         							return
	    																		}
		        															if(this.state.record==null){
		                            						req
		                            						.get('/uclee-user-web/bargainMsg')
		                            						.end((err, res) => {
		        																	if (err) {
		          																	return err
		        																	}
		        																})
		                            							window.location="/launch-bargain/"+item.valueId+"&"+codes+"?merchantCode="+localStorage.getItem('merchantCode')
		                            					}else{
		                            						alert(this.state.record);
		                            						window.location="/launch-bargain/"+this.state.valueId+"&"+this.state.invitationcode+"?merchantCode="+localStorage.getItem('merchantCode')
		                            					}
		        														})
	                            				
	        															if(this.state.stock == undefined){
	        																console.log(this.state.stock)
	        																return
	    																	}
		                            				if(this.state.limit == 'true'){
		                         							console.log(this.state.limit)
		                         							return
																				}
	                            					
	                            				}else{
	                            					alert(this.state.vipFail);
	                            					window.location='/member-card'
	                            				}
                            		}}>
                            			<font color="white">邀请朋友帮忙砍价{item.price}元拿</font>
                            		</button>}
                            	</div>
                          	</div>
                        	)
                      	})}
                      {this.state.record !== undefined
                				?
                				<div className="bargain-item-btn">
	                      	<button className="btn btn-info" onClick={()=>{window.location="/launch-bargain/"+this.state.valueId+"&"+this.state.invitationcode+"?merchantCode="+localStorage.getItem('merchantCode')}}>
	                   				<font color="white">进入我的砍价</font>
	                   			</button>
                   			</div>
	                		:null
	                		}
                    </div>
                  </div>
                </div>
                : <BargainEmpty />
          }
        </div>
      </DocumentTitle>
    )
  }
}

export default bargain
