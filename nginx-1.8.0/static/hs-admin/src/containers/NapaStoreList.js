import React from 'react'
import DocumentTitle from 'react-document-title'
import { Link } from 'react-router'
import './add-store.css'
import req from 'superagent'

class NapaStoreList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      list:[]
    }

  }

  componentDidMount() {
    req
      .get('/uclee-backend-web/napaStoreList')
      .query(this.props.location.query)
      .end((err, res) => {
        if (err) {
          return err
        }

        this.setState({
          list: res.body
        })
      })
  }

  render() {
    return (
      <DocumentTitle title="门店列表">
        <div className="store-list">
          {/* 类名加上页面前缀防止冲突 */}
          <Link to={"addStore?userId="+this.props.location.query.userId} className="btn btn-success">添加店铺</Link>
          <table className="table table-bordered store-list">
            <thead>
              <tr>
                <th> 微商城名称</th>
                <th> 加盟店名称</th>
                <th> 地址</th>
                <th> 操作</th>
              </tr>
            </thead>
            <tbody>
              {
                this.state.list.map((item, index) => {
                  return (
                    <tr key={index}>
                        <td> {item.storeName}</td>
                        <td> {item.hsName}</td>
                        <td>{item.addrDetail}</td>
                        <td>
                          <Link to={`/editStore?storeId=${item.storeId}`} className="btn btn-primary">编辑店铺</Link>
                          <button onClick={this._del.bind(this,item.storeId)} className="btn btn-danger">删除店铺</button>
                        </td>
                    </tr>
                    )
                })
              }
              
            </tbody>
          </table>

        </div>
      </DocumentTitle>
      )
  }
  _del=(id)=>{
    var conf = confirm('确认要删除该店铺吗？');
    if(conf){
      req.get('/uclee-backend-web/delStore?storeId='+id).end((err, res) => {
        if (err) {
          return err
        }
        window.location='/napaStoreList?userId=' + this.props.location.query.userId
      })
    }
  }

}

export default NapaStoreList