import React from 'react'
import DocumentTitle from 'react-document-title'
import { Link } from 'react-router'
import './add-store.css'
import req from 'superagent'

class UrlVoucherCollectionList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      list:[]
    }

  }

  componentDidMount() {
    req
      .get('/uclee-backend-web/selectAllUrlVoucherCollection')
      .end((err, res) => {
        if (err) {
          return err
        }
        this.setState({
          list: res.body.usv
        })
      })
  }

  render() {
    return (
      <DocumentTitle title="领券活动列表">
        <div className="store-list">
         {/* 类名加上页面前缀防止冲突 */}
          <table className="table table-bordered store-list">
            <thead>
              <tr>
                <th> 活动名称</th>
                <th> 用户参与次数</th>
                <th> 是否限制每天/1次</th>
                <th> 操作</th>
              </tr>
            </thead>
            <tbody>
              {
                this.state.list.map((item, index) => {
                  return (
                    <tr key={index}>
                        <td> {item.activityname}</td>
                        <td> {item.frequencylimit}</td>
                        <td>{item.dailylimit == 1 ? '启用' : '停止'}</td>
                        <td>
                          <Link to={`/UrlVoucherCollection?id=${item.id}`} className="btn btn-primary">编辑活动</Link>
                          <button onClick={this._del.bind(this,item.id)} className="btn btn-danger">删除活动</button>
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
    var conf = confirm('确认要删除该活动吗？');
    if(conf){
      req.get('/uclee-backend-web/removeUrlVoucherCollection?id='+id).end((err, res) => {
        if (err) {
          return err
        }
        window.location='/UrlVoucherCollectionList'
      })
    }
  }

}

export default UrlVoucherCollectionList