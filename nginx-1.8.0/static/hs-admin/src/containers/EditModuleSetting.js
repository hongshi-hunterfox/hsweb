import React from 'react'
import DocumentTitle from 'react-document-title'
import './product-list.css'
import fto from 'form_to_object'
import req from 'superagent'
import { Link } from 'react-router'

class ModuleSetting extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      groupName:'',
      all:[]
    }
  }
 

  componentDidMount() {
    req.get('/uclee-backend-web/getAll').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        all:data.all
      })
    })
  }
  
   _del=(id)=>{
    var conf = confirm('确认要删除该分类吗？');
    if(conf){
      req.get('/uclee-backend-web/deleteGroupName?groupId='+id).end((err, res) => {
        if (err) {
          return err
        }
        var data = JSON.parse(res.text)
        if(data.result){
          window.location='/editModuleSetting'
        }else{
          alert(data.reason);
        }
      })
    }
  }
   _edit=(url)=>{
    window.location=url;
  }
  
  
  render() {
    var list = this.state.all.map((item, index) => {
      return (
        <tr key={index}>
          <td><img src={item.image} alt="" width="50"/></td>
          <td>{item.groupName}</td>
          <td>{item.displayType}</td>
          <td>
            <Link to={'/moduleSetting?groupId=' + item.groupId} className='btn btn-primary' style={{marginRight:'7px',marginBottom:'5px'}}>
            编辑
            </Link>
            <button className='btn btn-danger'  onClick={this._del.bind(this,item.groupId)} style={{marginRight:'7px',marginBottom:'5px'}}>
            删除
            </button>
           <button type="submit" className='btn btn-primary' style={{marginRight:'5px',marginBottom:'7px'}} onClick={this._edit.bind(this,'/product-group-list?groupName='+item.groupName)}>  查看关联产品</button>
          </td>
        </tr>
      )
    })
    return (
      <DocumentTitle title="首页模块管理">
        <div className="product-list">
            <div className="product-list-add">
              <Link to={'/moduleSetting'} className="btn btn-primary">
               添加模块
              </Link>
            </div>
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th width="20%">模块图片</th>
                  <th width="20%">模块分类</th>             
                  <th width="20%">滚动方式</th>
                  <th width="40%">
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
   _submit = e => {
     e.preventDefault()
    var data = fto(e.target)
    req.post('/uclee-backend-web/editProductGroup')
    .send(data)
    .end((err, res) => {
      if (err) {
        return err
      }

      console.log(res.body)
      if (res.body) {
        window.location = '/product-group-list'
      } else {
        alert('该区域已存在此商品')
      }
    })
  }
}

export default ModuleSetting
