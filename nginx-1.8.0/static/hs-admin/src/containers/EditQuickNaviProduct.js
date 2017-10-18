import React from 'react'
import DocumentTitle from 'react-document-title'
import fto from 'form_to_object'
import req from 'superagent'
import ErrorMsg from '../components/ErrorMsg'
import './edit-quick-navi.css'
class EditQuickNaviProduct extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      productId: null,
      productIds:[],
      texts:[],
      selectedItem:{},
      groupId:null,
      text:'',
      products: [],
      preProductId:0,
      preGroupId:0,
      filter: ''
    }
  }

  componentDidMount() {
    req.get('/uclee-product-web/productList').end((err, res) => {
      if (err) {
        return err
      }

      this.setState(res.body)
    })
  }

  render() {
    var selected = this.state.productIds.map((item,index)=>{
        return(
          <div className='selected-item' key={index}>
            <span>{this.state.texts[index]}</span>
            <span className="fa fa-ban icon" onClick={this._del.bind(this,index)}></span>
          </div>
        )
    })
    return (
      <DocumentTitle title={'编辑首页快捷导航'}>
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
              <label className="control-label col-md-3">已选产品：</label>
              <div className='selected'>
                {selected}
                
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

  _pick = (id,title) => {
    /*this.setState({
      productId: id,
      text:title
    })*/
    //this.state.selectedItem[id] = title;
    var ids = this.state.productIds;
    var titles = this.state.texts;
    if(ids.indexOf(id)===-1){
      ids.push(id);
      titles.push(title);
      this.setState({
        productIds:ids,
        texts:titles
      })
    }

  }
   _del = (index) => {
    console.log(this.state.productIds.length);
    var ids = [];
    var titles = [];

    if(!isNaN(index)&&index<this.state.productIds.length){
      for(var i=0;i<this.state.productIds.length;i++){
        if(i!==index){
          ids.push(this.state.productIds[i]);
        }
      }
    }
    if(!isNaN(index)&&index<this.state.texts.length){
      for(var i=0,n=0;i<this.state.texts.length;i++){
        if(i!==index){
          titles.push(this.state.texts[i]);
        }
      }
    }
    this.setState({
      productIds:ids,
      texts:titles
    })
    

  }
  _submit = e => {
    e.preventDefault()
    var data = {}
    if (!this.state.productIds||!this.state.productIds.length>0) {
      return this.setState({
        err: '请选择产品'
      })
    }
    if(!this.props.location.query.naviId){
      return this.setState({
        err: '参数错误，请返回快捷导航列表重新进入添加'
      })
    }
    console.log(this.props.location.query.naviId);
    data.naviId = this.props.location.query.naviId;
    data.productIds = this.state.productIds;
    req.post('/uclee-backend-web/editQuickNaviProduct').send(data).end((err, res) => {
      if (err) {
        return err
      }
      console.log(res.body)
      if (res.body) {
        window.location = '/quick-navi-product?naviId='+this.props.location.query.naviId
      } 
    })
  }
}

export default EditQuickNaviProduct
