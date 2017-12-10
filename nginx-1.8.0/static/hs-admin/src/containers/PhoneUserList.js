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
      <DocumentTitle title="加盟商列表">
        <div className="user-list">
          {/* 类名加上页面前缀防止冲突 */}
        <Link to="addUser" style={{marginRight:'5px',marginBottom:'5px'}} className="btn btn-success">添加用户</Link>
        <Link to="napaStoreList" style={{marginRight:'5px',marginBottom:'5px'}} className="btn btn-success">门店列表</Link>
          <table className="table table-bordered user-list">
            <thead>
              <tr>
                <th width="20%"> 名字</th>
                <th width="20%"> 手机</th>
                <th width="60%"> 操作</th>
              </tr>
            </thead>
            <tbody>
              {
                this.state.list.map((item, index) => {
                  return (
                    <tr key={index}>
                        <td width="20%"> {item.name}</td>
                        <td width="20%"> {item.phone}</td>
                        <td width="60%">
                          <Link style={{marginRight:'5px'}} to={`/editPhoneUser?userId=${item.userId}`} className="btn btn-danger">编辑用户</Link>
                          <button style={{marginRight:'5px'}} onClick={this._delete_user.bind(this,item.userId)} className="btn btn-warning delete-user">删除用户</button>
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