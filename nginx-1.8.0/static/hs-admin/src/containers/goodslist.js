import React from 'react'
import DocumentTitle from 'react-document-title'
import './product-list.css'
import req from 'superagent'
var fto = require('form_to_object')
import { Link } from 'react-router'

class goodslist extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      goods:[],
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/getGoods').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        goods: data.goods,
      })
    })
  }

  _del=(id)=>{
    var conf = confirm('确认要删除该产品吗？');
    if(conf){
      req.get('/uclee-backend-web/delGoods?id='+id).end((err, res) => {
        if (err) {
          return err
        }
        window.location='/goodslist'
      })
    }
  }

  render(){
    var list = this.state.goods.map((item, index) => {
      return (
        <tr key={index}>
          <td><img src={item.goodsimg} alt="" width="50"/></td>
          <td>{item.goodsname}</td>
          <td>
            <Link to={'/OperationGood/' + item.id} className="btn btn-primary">
            编辑
            </Link>
            <button className="btn btn-danger button-right" onClick={this._del.bind(this,item.id)}>
            删除
            </button>
          </td>
        </tr>
      )
    })
    return (
      <DocumentTitle title="产品列表">
        <div className="product-list">
            <div className="product-list-add">
              <Link to={'/OperationGood/'} className="btn btn-primary">
               添加产品
              </Link>
            </div>
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>产品图片</th>
                  <th>产品标题</th>
                  <th>
                    <a href=""></a>编辑</th>
                </tr>
              </thead>
              <tbody>
                {list}
              </tbody>
            </table>
        </div>
      </DocumentTitle>
    )
  }
}

export default goodslist
