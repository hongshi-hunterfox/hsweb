import React from 'react'
import DocumentTitle from 'react-document-title'
import './user-list.css'
import req from 'superagent'
// import { Link } from 'react-router'

class UserList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      users:[],
      size:10
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/userList').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        users: data.users,
        size:data.users.length
      })
    })
  }
  
  render() {
    var list = this.state.users.map((item, index) => {
      return (
        <tr key={index}>
          <td width='20%'><img src={item.image} alt="" width="50"/></td>
          <td width='15%'>{item.cardNum}</td>
          <td width='15%'>{item.name}</td>
          <td width='15%'>{item.phone}</td>
          <td width='15%'>{item.birthday}</td>
          <td width='5%'>{item.age&&item.age>0?item.age:null}</td>
          <td width='15%'>{item.registTimeStr}</td>
        </tr>
      )
    })
    return (
      <DocumentTitle title="用户列表">
        <div className="user-list">
          <div className="user-list-add">
              <div className="btn btn-primary">
               用户总数:{this.state.size}
              </div>
            </div>
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th width='10%'>头像</th>
                  <th width='15%'>卡号</th>
                  <th width='15%'>昵称</th>
                  <th width='15%'>手机</th>
                  <th width='15%'>生日</th>
                  <th width='10%'>年龄</th>
                  <th width='20%'>成为会员时间</th>
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

export default UserList
