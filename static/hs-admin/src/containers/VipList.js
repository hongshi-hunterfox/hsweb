import React from 'react'
import DocumentTitle from 'react-document-title'
import './vip-list.css'
import req from 'superagent'

class VipList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      vips:[],
      size:10,
      start:'',
      end:'',
      cartphone: this.props.location.query.cartphone,
      checked: [],
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/vipList?start=' + this.props.location.query.start+'&end='+this.props.location.query.end)
    	.end((err, res) => {
      		if (err) {
        		return err
      		}
      		var data = JSON.parse(res.text)
      		this.setState({
        			vips: data.vips,
        			size: data.size,
       		    start:this.props.location.query.start?this.props.location.query.start:data.start,
          		end:this.props.location.query.end?this.props.location.query.end:data.end
      		})
    	})	
    req
      .get(
        '/uclee-backend-web/vipPhone?cartphone=' + this.props.location.query.cartphone
      )
      .end((err, res) => {
        if (err) {
          return err
        }
        var data = JSON.parse(res.text)
        this.setState({
          vips: data.vips,
          size: data.size,
        })
      })
  }
  
_search = () => {
    	if(!this.state.start||!this.state.end){
      		alert('请填写完整搜索时间');
      		return;
    	}
    	window.location = window.location = '/vip-list?start=' + this.state.start +'&end='+ this.state.end
  }
 _search1 = () => {
    window.location = window.location = '/vip-list?cartphone=' + this.state.cartphone
  }
 _CouponSetting = () => {
    window.location = window.location = '/voucher-delivery'
  }

 _change = e => {
    this.setState({
      cartphone: e.target.value
    })
 }

_selectCat=(e)=>{
 		var sort = e.target.value;
 		req
		.get('/uclee-backend-web/getAllvip')
		.query({sort})
		.end((err, res) => {
			if (err) {
			return err
			}

		var data = JSON.parse(res.text)
			this.setState({
				vips: data.vips,
				size: data.size,
			})
		})

}

  render() {
    var vips = this.state.vips.map((item, index) => {
      return (
        <tr key={index}>
          <td width="7%">
            <input
              type="checkbox"
              checked={this.state.checked.indexOf(item.userId) !== -1}
              onChange={this._checkChange.bind(this, item.userId)}
            />
          </td>
          <td width="13%">{item.cardNum}</td>
          <td width="15%">{item.name}</td>
          <td width="13%">{item.phone}</td>
          <td width="20%">{item.lastBuyStr}</td>
          <td width="20%">{item.registTimeStr}</td>
          <td width="12%">  
          	<button className="btn btn-warning" onClick={this._send.bind(this, item.userId)}>
              派送礼券
          	</button>
          </td>
        </tr>
      )
    })
    return (
      <DocumentTitle title="会员列表">
        <div className="user-list">
        
        	<div className="clearfix" style={{margin:'30px 0'}}>
          		<div className='searchTime'>
           		 <label className="control-label">起始时间：</label>
           		 <input
              		className="form-control"
              		type="date"
              		name="start"
              		value={this.state.start}
              		onChange={e => {
                		this.setState({ start: e.target.value })
              		}}
            	 />
            	</div>
            	<div className='searchTime'>
            	 <label className="control-label">截止时间：</label>
            	 <input
              		className="form-control"
              		type="date"
              		name="end"
              		value={this.state.end}
              		onChange={e => {
                		this.setState({ end: e.target.value })
              		}}
            	 />
            	</div>
            	<div className="btn btn-success searchBtn" style={{marginTop:'25px',float:'left'}} onClick={this._search} >
              		搜索
            	</div>
            	<div style={{float:'right'}}>
            		<button className="btn btn-danger" onClick={this._CouponSetting}>礼券派送设置</button>
            	</div>
        	</div> 
					<div className="clearfix">
          	<div className='searchTime'>
            		<input
            			className="form-control"
              		type="text"
              		name="cartphone"
              		placeholder="请输入卡号或手机号查询"
              		value={this.state.cartphone}
              		onChange={this._change}
            		/>
            </div>
            	<div className="btn btn-success" onClick={this._search1}>
              	搜索
          		</div>
          </div>
          <div style={{float:'right',marginTop:'25px'}}>
          	<select name="sort" onChange={this._selectCat}>
            		<option value=''>选择排序方式</option>
            		<option value='0'>按会员注册时间排序</option>
            		<option value='1'>按最后消费时间排序</option>
            </select>&nbsp;&nbsp;
          	<button className="btn btn-success" onClick={this._sendAll}>批量发送</button>
          </div>
          <div style={{float:'left',marginTop:'25px'}}>
        			<span className="btn btn-success" 
        			onClick={() => { 
        			req
        			.get('/uclee-backend-web/getAllvip')
        			.end((err, res) => {
        				if (err) {
          				return err
        				}
        
								var data = JSON.parse(res.text)
        				this.setState({
          				vips: data.vips,
          				size: data.size,
        				})
      				})}}>
      				查询全部
          		</span>
          		<span>
          		 	{"------符合条件的会员有:"+this.state.size+"位------"}
            </span>
          </div>
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th width="7%">
                  	<input type="checkbox" 
                  	onChange={this._checkChangeAll}
                  	checked={this.state.checked.length === this.state.vips.length&&this.state.checked.length>0}
                  	/>全选
               	  </th>
                  <th width="13%">卡号</th>
                  <th width="15%">昵称</th>
                  <th width="13%">手机</th>
                  <th width="20%">最近消费时间</th>
                  <th width="20%">会员注册时间</th>
                  <th width="12%">操作</th>
                </tr>
              </thead>
              <tbody>
                {vips}
              </tbody>
            </table>
        </div>
      </DocumentTitle>
    )
  }
  
  _checkChangeAll = e => {
    if (e.target.checked) {
      this.setState(prevState => {
        return {
          checked: prevState.vips.map(item => item.userId)
        }
      })
      return
    }

    this.setState(prevState => {
      return {
        checked: []
      }
    })
  }
  _checkChange = (id, e) => {
    if (e.target.checked) {
      // push it
      this.setState(prevState => {
        return {
          checked: prevState.checked.concat(id)
        }
      })
      return
    }

    // remove it
    this.setState(prevState => {
      return {
        checked: prevState.checked.filter(item => {
          return item !== id
        })
      }
    })
  }
  _send = userId => {
    var conf = confirm('确定要联动派送礼券吗？');
    var url='';
    if(conf){
      url='/uclee-backend-web/sendVipMsg?userId=' + userId+'&sendVoucher=1';
      req
      .get('/uclee-backend-web/isCouponAmount?amount=1')
      .end((err, res) => {
        if (err) {
          return err
        }
        if (!res.body.result) {
          if(res.body.text){
            alert(res.body.text);
          }else{
            alert('礼券数量不符，数量不足');
          }
        }  else {
          req
            .get(url)
            .end((err, res) => {
              if (err) {
                return err
              }
              if (res.body) {
                alert('发送成功')
                window.location =
                  '/vip-list'
              } else {
                alert('网络繁忙，请稍后重试')
              }
            })
        }
      })
    }else{ window.location = '/vip-list'}
  }
  
  _sendAll=()=>{
    if(this.state.checked.length===0){
      alert("请选择要批量发送的用户");
      return;
    }
    var conf = confirm('确定要派送礼券吗？');
    
    if(conf){
      req
      .get('/uclee-backend-web/isCouponAmount?amount='+this.state.checked.length)
      .end((err, res) => {
        if (err) {
          return err
        }
        if (!res.body.result) {
          if(res.body.text){
            alert(res.body.text);
          }else{
            alert('礼券数量不符，数量不足');
          }
        } else {
          var ret = true;
          for (var i in this.state.checked)
          {
            console.log(this.state.checked[i]);
            var url='';
            if(conf){
              url='/uclee-backend-web/sendVipMsg?userId=' + this.state.checked[i]+'&sendVoucher=1';
            }else{
              url='/vip-list';
            }
            req
            .get(url)
            .end((err, res) => {
              if (err) {
                return err
              }
              ret = ret && res.body;
            })
          }
          if (ret) {
                alert('发送成功')
                window.location =
                  '/vip-list'
              } else {
                alert('网络繁忙，请稍后重试')
              }
        }
      })
    }else{
          window.location='/vip-list';
       }   
  }

}

export default VipList
