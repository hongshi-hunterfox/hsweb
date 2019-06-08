import React from 'react'
import DocumentTitle from 'react-document-title'
import { values } from 'lodash'

// fto 可以将表单装换成 json
import fto from 'form_to_object'

// validator 用户表单验证
import validator from 'validator'

// 创建 less 文件，但是引用 css 文件
import './demo.css'

// req 用于发送 AJAX 请求
import req from 'superagent'

// ErrorMsg 显示表单错误
import ErrorMsg from '../components/ErrorMsg'

//import ValueGroup from '../components/ValueGroup'

class DisparitysList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      disparitys: [],
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/getalldisparity').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        disparitys: data.disparitys
      })
    })
  }

  render() {
  	var list = this.state.disparitys.map((item, index) => {
      return (
        <tr key={index}>
          <td>{item.paramname}</td>
          <td>{item.disparity}</td>
          <td>
            <button className="btn btn-danger" onClick={this._delHandler.bind(this,item.id)}>删除</button>
          </td>
        </tr>
      )
    })
    return (
      <DocumentTitle title="差额设置">
        <div className="demo">
          {/* 类名加上页面前缀防止冲突 */}
          <h1 className="demo-title">设置打包差额</h1>
					<div className="form-group">
            <div className="controls controls-row col-md-9 col-md-offset-2">
            	<label className="control-label">列表：</label>
              <table className="table table-bordered">
	              <thead>
	                <tr>
	                  <th>名称</th>
	                  <th>差额</th>
	                  <th>操作</th>
	                </tr>
	              </thead>
	              <tbody>
	                {list}
	              </tbody>
	            </table>
            </div>
          </div>
          <form onSubmit={this._submit} className="form-horizontal">
	       		<div className="form-group">
							<div className="controls controls-row col-md-9 col-md-offset-2">
								<label className="control-label">名称：</label>
							    <input className="span2" type="text" placeholder="请输入名称" name="paramname"/>
								<label className="control-label">  差额：</label>
							    <input className="span2" type="number" placeholder="请输入差额" name="disparity"/>&nbsp;&nbsp;
							 	<button type="submit" className="btn btn-default"> 新增</button>
							</div>
							<ErrorMsg msg={this.state.err} />
	          </div>
          </form>
        </div>
      </DocumentTitle>
      )
  }
  
  _delHandler=(id)=>{
    req.get('/uclee-backend-web/removedisparity?id='+id).end((err, res) => {
      if (err) {
        return err
      }
      if(res.body > 0){
      	alert("操作成功!")
      }
      return window.location.reload();
    })
  }

  _submit = (e) => {
    e.preventDefault()
    var data = fto(e.target)
    console.log(data)


    if (!data.paramname) {
      return this.setState({
        err: '请输入 名称'
      })
    }

    if (!data.disparity) {
      return this.setState({
        err: '请输入 差额'
      })
    }

    this.setState({
      err: null
    })

    req
      .post('/uclee-backend-web/adddisparity')
      .send(data)
      .end((err, res) => {
        if (err) {
          return err
        }
				if(res.body > 0){
      		alert("操作成功!")
     		}	
      	return window.location.reload();
      })



  }
}

export default DisparitysList