import React from 'react'
import DocumentTitle from 'react-document-title'
import { browserHistory } from 'react-router'

// fto 可以将表单装换成 json
import fto from 'form_to_object'

// validator 用户表单验证
// import validator from 'validator'

// 创建 less 文件，但是引用 css 文件
import './add-store.css'

// req 用于发送 AJAX 请求
import req from 'superagent'

// ErrorMsg 显示表单错误
import ErrorMsg from '../components/ErrorMsg'

class AddUser extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
       userId: 0,
      name:"",
      hongShiProduct: [],
      phone:"",
      store:[],
      sSearch: '',
      storeIds:[],
      ids:{}
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/getStore')
      .query(this.props.location.query)
      .end((err, res) => {
        if (err) {
          return err
        }

        this.setState({
          store:res.body.stores
        })
      })
  }

  render() {
    return (
      <DocumentTitle title="加盟商管理">
        <div className="add-user">
          {/* 类名加上页面前缀防止冲突 */}
          <form onSubmit={this._submit} className="form-horizontal" ref="f">
            <input type="hidden" name="userId" value="0" />
            <div className="form-group">
              <label className="control-label col-md-3">名字：</label>
              <div className="col-md-9">
                <input type="text" name="name" className="form-control" />
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">手机号码：</label>
              <div className="col-md-9">
                <input type="text" name="phone" className="form-control" />
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">选择店铺：</label>
              <div className="control-label col-md-9">
                  <div className="panel panel-default">
                    <div className="panel-heading">
                      <div className="input-group input-group-sm add-store-search">
                        <span className="input-group-addon">店铺</span>
                        <input
                          type="text"
                          className="form-control input"
                          placeholder="搜索"
                          value={this.state.sSearch}
                          onChange={e => {
                            this.setState({ sSearch: e.target.value })
                          }}
                        />
                      </div>
                    </div>
                    <div className="panel-body add-store-specs">

                      {this.state.store
                          .filter(item => {
                            return item.storeName.indexOf(this.state.sSearch) !== -1
                          })
                          .map((item, index) => {
                          return (
                            <div className="checkbox" key={index}>
                              <label className="add-store-specs-lable">
                                <input
                                  type="checkbox"
                                  onChange={this._changeStore.bind(
                                    this,
                                    item.storeId
                                  )}
                                  checked={
                                    this.state.storeIds &&
                                      this.state.storeIds.indexOf(
                                        item.storeId
                                      ) !== -1
                                  }
                                />
                                {item.storeName}
                              </label>
                            </div>
                            )}
                      )}

                    </div>
                  </div>
                </div>
            </div>
            <ErrorMsg msg={this.state.err} />
            <div className="form-group">
              <div className="col-md-9 col-md-offset-3">
                <button type="submit" className="btn btn-primary">提交</button>
              </div>
            </div>
          </form>
        </div>
      </DocumentTitle>
    )
  }
_changeStore = (storeId, e) => {
    
    var list = this.state.ids;//有重复的数组
    var storeIdsTmp = [];
    if(list[storeId]){
      delete list[storeId]
    }else{
      list[storeId] = storeId;//加入标记对象中
    }
    console.log(list)
    for(var item in list){
      storeIdsTmp.push(Number(item))
    }
    
    console.log(storeIdsTmp)

    this.setState({
      storeIds:storeIdsTmp
    })
  }
  _submit = e => {
    e.preventDefault()
    var data = fto(e.target)
    console.log(data)
data.storeIds = this.state.storeIds;
    if (!data.name) {
      return this.setState({
        err: '请填写 供应商名称'
      })
    }

    if (!data.phone) {
      return this.setState({
        err: '请填写 手机号码'
      })
    }
   if(!this.state.store.length>0){
      var conf = confirm('无可用门店，是否立即前去添加？');
      if(conf){
        window.location="/napaStoreList"
      }
    }

    if(!data.storeIds>0){
      return this.setState({
        err: '请选择门店'
      })
    }

    this.setState({
      err: null
    })
    req
      .post('/uclee-backend-web/doAddPhoneUser')
      .send(data)
      .end((err, res) => {
        if (err) {
          return err
        }

        if(res.body.result==="success"){
          alert("添加成功！");
          browserHistory.push({
            pathname: '/phoneUserList'
          })
        }else{
          alert(res.body.reason);
        }

        
      })
  }
}

export default AddUser
