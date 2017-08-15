import React from 'react'
import DocumentTitle from 'react-document-title'
import { Link } from 'react-router'
import './add-store.css'
import req from 'superagent'

class PhoneUserList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      list:[]
    }

    this.lat=23.12463
    this.lng=113.36199

  }

  componentDidMount() {
    req
      .get('/uclee-backend-web/phoneUserList')
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
      <DocumentTitle title="user-list Page">
        <div className="user-list">
          {/* 类名加上页面前缀防止冲突 */}
        <Link to="addUser" className="btn btn-success">添加用户</Link>
          <table className="table table-bordered user-list">
            <thead>
              <tr>
                <th> 名字</th>
                <th> 手机</th>
                <th> 操作</th>
              </tr>
            </thead>
            <tbody>
              {
                this.state.list.map((item, index) => {
                  return (
                    <tr key={index}>
                        <td> {item.name}</td>
                        <td> {item.phone}</td>
                        <td>
                          <Link to={`/editPhoneUser?userId=${item.userId}`} className="btn btn-danger">编辑用户</Link>
                          <Link to={`/addStore?userId=${item.userId}`} className="btn btn-info">添加店铺</Link>
                          <Link to={`/napaStoreList?userId=${item.userId}`} className="btn btn-warning">店铺列表</Link>
                          <button onClick={this._delete_user.bind(this,item.userId)} className="btn btn-danger delete-user">删除用户</button>
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
  _delete_user = (id) => {
    if(confirm("确认要删除该用户吗？")){
        req
          .post('/uclee-backend-web/doDeletePhoneUser?userId='+id)
          .end((err, res) => {
            if (err) {
              return err
            }
            console.log(res.body)
            if(res.body.result==="success"){
              alert("删除成功！");
              window.location.reload();
            }else{
              alert("删除失败！");
            }
          })

    }

  }

}

export default PhoneUserList