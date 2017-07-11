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
          <td><img src={item.image} alt="" width="50"/></td>
          <td>{item.cardNum}</td>
          <td>{item.name}</td>
          <td>{item.phone}</td>
          <td>{item.birthday}</td>
          <td>{item.age&&item.age>0?item.age:null}</td>
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
                  <th>头像</th>
                  <th>卡号</th>
                  <th>昵称</th>
                  <th>手机</th>
                  <th>生日</th>
                  <th>年龄</th>
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
