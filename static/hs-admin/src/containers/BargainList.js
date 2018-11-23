import React from 'react'
import DocumentTitle from 'react-document-title'
import './product-list.css'
import req from 'superagent'
var fto = require('form_to_object')
import { Link } from 'react-router'

class BargainList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      bargainList:[],
      productId:0,
      config:{}
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/getBargainList')
    .end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        bargainList: data.bargainList
      })
    })
    
    req.get('/uclee-backend-web/config').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        config: data.config
      })
    })
  }

  _del=(id)=>{
    var conf = confirm('确认要删除该活动吗？');
    if(conf){
    	console.log("id=="+id)
      req.get('/uclee-backend-web/delBargainId?id='+id).end((err, res) => {
        if (err) {
          return err
        }
        window.location='/bargain-list'
      })
    }
  }

  render(){
    var list = this.state.bargainList.map((item, index) => {
      return (
        <tr key={index}>
          <td width="18%">{item.name}</td>
          <td width="15%">{item.starts}</td>
          <td width="15%">{item.ends}</td>
          <td width="6%">{item.price}</td>
          <td width="6%">{item.minprice}</td>
          <td width="6%">{item.maxprice}</td>
          <td width="18%">{item.productName}</td>
          <td width="8%">
            <Link to={'/bargain-setting/' + item.id} className="btn btn-primary">
            编辑
            </Link>
          </td>
          <td width="8%">
          	<button className="btn btn-danger button-right" onClick={this._del.bind(this,item.id)}>
            删除
            </button>
          </td>  
        </tr>
      )
    })
    return (
      <DocumentTitle title="活动列表">
        <div className="product-list">
            <div className="product-list-add">
              <Link to={'/bargain-setting/'} className="btn btn-primary">
               添加活动
              </Link>
            </div>
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>活动名称</th>
                  <th>开始时间</th>
                  <th>结束时间</th>
                  <th>最低购买金额</th>
                  <th>单次最小砍价金额</th>
                  <th>单次最大砍价金额</th>
                  <th>关联产品</th>
                  <th>
                    <a href=""></a>编辑</th>
                  <th>删除</th>
                </tr>
              </thead>
              <tbody>
                {list}
              </tbody>
            </table>
            <font color="#FF0000">推送活动url:  {this.state.config.domain}/bargain?merchantCode={localStorage.getItem('merchantCode')}</font>
            <form onSubmit={this._submit} className="form-horizontal">
            	<label style={{marginTop:'10px'}}>活动规则设置：</label>
					      <div style={{marginTop:'10px'}}>
					        <textarea rows="3" cols="20" value={this.state.config.bargainText} name="bargainText" className="form-control" onChange={this._change}>
					        </textarea>
					    </div>
	            <div className="form-group" style={{marginTop:'10px'}}>
	              <div className="col-md-9 col-md-offset-5">
	                <button type="submit" className="btn btn-primary">提交</button>
	              </div>
	            </div>
					  </form>
        </div>
      </DocumentTitle>
    )
  }
  
  _change = (e) => {
    var c = Object.assign({}, this.state.config)
    c[e.target.name] = e.target.value
    this.setState({
      config: c
    })
  }
  
  _submit = (e) => {
    e.preventDefault()
    var data = fto(e.target)
    console.log(data)

    this.setState({
      err: null
    })
    data.logoUrl = this.state.logoUrl;
    data.ucenterImg = this.state.ucenterImg;
    req
      .post('/uclee-backend-web/activityConfigHandler')
      .send(data)
      .end((err, res) => {
        if (err) {
          return err
        }
        if(res.body){
          window.location='bargain-list';
        }else{
          alert('网络繁忙，请稍后重试');
        }
        console.log(res.body)
      })
    }
}

export default BargainList
