import React from 'react'
import DocumentTitle from 'react-document-title'
import fto from 'form_to_object'
import req from 'superagent'
import ErrorMsg from '../components/ErrorMsg'

class EditProductGroup extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      productId: null,
      all:[],
      groupId:null,
      groupName:'',
      text:'',
      products: [],
      preProductId:0,
      preGroupId:0,
      filter: ''
    }
  }

  componentDidMount() {
    this.setState({
      preGroupId:this.props.location.query.groupId,
      preProductId:this.props.location.query.productId,
      productId:this.props.location.query.productId,
      groupId:this.props.location.query.groupId
    })
     
     req.get('/uclee-backend-web/getAll').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        all:data.all
      })
    })
    if(this.props.location.query.productId){
      req.get('/uclee-product-web/productList?productId='+this.props.location.query.productId).end((err, res) => {
        if (err) {
          return err
        }

        this.setState(res.body)
      })
    }else{
      req.get('/uclee-product-web/productList').end((err, res) => {
        if (err) {
          return err
        }

        this.setState(res.body)
      })
    }
  }

  render() {
    return (
      <DocumentTitle title={'编辑首页产品'}>
        <div className="product">
          <form onSubmit={this._submit} className="form-horizontal">
            <h1>{'已选产品: ' + this.state.text}</h1>
            <div className="form-group">
              <label className="control-label col-md-3">选择产品：</label>
              <div className="col-md-9">
                <div className="panel panel-default">
                  <div className="panel-heading">
                    <input
                      type="text"
                      className="form-control"
                      placeholder="筛选"
                      value={this.state.filter}
                      onChange={(e) => {
                        this.setState({
                          filter: e.target.value
                        })
                      }}
                      style={{
                        width: 200,
                        display: 'inline-block',
                        marginLeft: 15
                      }}
                    />
                  </div>
                  <div className="panel-body">
                    {this.state.products.filter((item) => {
                      return item.title.indexOf(this.state.filter) !== -1
                    }).map((item, index) => {
                      console.log(this.state.productId)
                      console.log(item.productId)
                      console.log(this.state.productId===item.productId)
                      return (
                        <div
                          key={index}
                          style={{
                            cursor: 'pointer',
                            color: parseInt(this.state.productId)===item.productId ? 'red' : 'initial'
                          }}
                          onClick={this._pick.bind(this, item.productId,item.title)}
                        >
                          {item.title}
                        </div>
                      )
                    })}
                  </div>
                </div>
              </div>
            </div>
            <div className="form-group">
              <input type='hidden' name='groupId' value={this.state.groupId} onChange={this._change}/>
             
              <label className="control-label col-md-3">选择模块：</label>
               <select name="groupName">
                {
                  this.state.all.map((item,index)=>{
                    return(
                      <option value={item.groupName} key={index}>{item.groupName}</option>
                    )
                  })
                }
              </select>
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

  _pick = (id,title) => {
    this.setState({
      productId: id,
      text:title
    })
  }
  _submit = e => {
    e.preventDefault()
    var data = fto(e.target)
    data.preProductId=this.state.preProductId
    data.preGroupId = this.state.preGroupId
    if(this.state.productId!==0){
      data.productId = this.state.productId 
    }

    if (!data.productId) {
      return this.setState({
        err: '请选择产品'
      })
    }
    
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

export default EditProductGroup
