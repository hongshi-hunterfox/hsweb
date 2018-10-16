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
            推送活动url:{this.state.config.domain}/bargain?merchantCode={localStorage.getItem('merchantCode')}
        </div>
      </DocumentTitle>
    )
  }
}

export default BargainList
