import React from 'react'
import DocumentTitle from 'react-document-title'
import './user-list.css'
import req from 'superagent'
import fto from 'form_to_object'
// import { Link } from 'react-router'

class UserBirthList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      users: [],
      size: 10,
      day: this.props.location.query.day,
      checked: [],
      start:'',
      end:'',
    }
  }

  componentDidMount() {
    req
      .get(
        '/uclee-backend-web/userBirthList?start=' + this.props.location.query.start+'&end='+this.props.location.query.end
      )
      .end((err, res) => {
        if (err) {
          return err
        }
        var data = JSON.parse(res.text)
        this.setState({
          users: data.users,
          size: data.users.length,
          start:this.props.location.query.start?this.props.location.query.start:data.start,
          end:this.props.location.query.end?this.props.location.query.end:data.end
        })
      })
  }


  
  _send = userId => {
    var conf = confirm('是否要联动送礼券？');
    var url='';
    if(conf){
      url='/uclee-backend-web/sendBirthMsg?userId=' + userId+'&sendVoucher=1';
      req
      .get('/uclee-backend-web/isVoucherLimit?amount=1')
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
                  '/user-birth-list?start=' + this.props.location.query.start + '&end='+this.props.location.query.end
              } else {
                alert('网络繁忙，请稍后重试')
              }
            })
        }
      })
    }else{
      url='/uclee-backend-web/sendBirthMsg?userId=' + userId;
      req
      .get(url)
      .end((err, res) => {
        if (err) {
          return err
        }
        if (res.body) {
          alert('发送成功')
          window.location =
            '/user-birth-list?start=' + this.props.location.query.start + '&end='+this.props.location.query.end
        } else {
          alert('网络繁忙，请稍后重试')
        }
      })
    }
  }
  _change = e => {
    this.setState({
      day: e.target.value
    })
  }

  _search = () => {
    if(!this.state.start||!this.state.end){
      alert('请填写完整搜索时间');
      return;
    }
    window.location = window.location = '/user-birth-list?start=' + this.state.start+'&end='+this.state.end
  }
  _sendAll=()=>{
    if(this.state.checked.length===0){
      alert("请选择要批量发送的用户");
      return;
    }
    var conf = confirm('是否要联动送礼券？');
    
    if(conf){
      req
      .get('/uclee-backend-web/isVoucherLimit?amount='+this.state.checked.length)
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
              url='/uclee-backend-web/sendBirthMsg?userId=' + this.state.checked[i]+'&sendVoucher=1';
            }else{
              url='/uclee-backend-web/sendBirthMsg?userId=' + this.state.checked[i];
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
                  '/user-birth-list?start=' + this.props.location.query.start + '&end='+this.props.location.query.end
              } else {
                alert('网络繁忙，请稍后重试')
              }
        }
      })
    }else{
      var ret = true;
      for (var i in this.state.checked)
      {
        console.log(this.state.checked[i]);
        var url='';
        if(conf){
          url='/uclee-backend-web/sendBirthMsg?userId=' + this.state.checked[i]+'&sendVoucher=1';
        }else{
          url='/uclee-backend-web/sendBirthMsg?userId=' + this.state.checked[i];
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
              '/user-birth-list?start=' + this.props.location.query.start + '&end='+this.props.location.query.end
          } else {
            alert('网络繁忙，请稍后重试')
          }
    }

    
  }

  render() {
    var list = this.state.users.map((item, index) => {
      return (
        <tr key={index}>
          <td>
            <input
              type="checkbox"
              checked={this.state.checked.indexOf(item.userId) !== -1}
              onChange={this._checkChange.bind(this, item.userId)}
            />
          </td>
          <td>{item.nickName}</td>
          <td>{item.birthStr}</td>
          <td>
            <button
              className="btn btn-primary"
              onClick={this._send.bind(this, item.userId)}
            >
              <span className="glyphicon glyphicon-send" /> 发送信息
            </button>
          </td>
        </tr>
      )
    })
    return (
      <DocumentTitle title="生日信息推送">
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
            <button className="btn btn-primary searchBtn" style={{marginTop:'25px',float:'left'}} onClick={this._search}>
              <span className="glyphicon glyphicon-search" /> 搜索
            </button>
          </div>
            <button className="btn btn-primary pull-left" onClick={this._sendAll}>
            	<span className="glyphicon glyphicon-bookmark"></span> 批量发送
            </button>
            <button className="btn btn-danger pull-right" onClick={()=>{window.location='/birth-voucher?merchantCode='+localStorage.getItem('merchantCode')}}>
              	<span className="glyphicon glyphicon-cog" /> 礼券赠送设置
            </button>
          <table className="table table-bordered table-striped">
            <thead>
              <tr>
                <th>
                  <input type="checkbox" onChange={this._checkChangeAll} checked={this.state.checked.length === this.state.users.length&&this.state.checked.length>0} /> 全选
                </th>
                <th>昵称</th>
                <th>生日</th>
                <th>操作</th>
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

  _checkChangeAll = e => {
    if (e.target.checked) {
      this.setState(prevState => {
        return {
          checked: prevState.users.map(item => item.userId)
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
}

export default UserBirthList
