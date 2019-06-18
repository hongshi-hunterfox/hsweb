import React from 'react'
import DocumentTitle from 'react-document-title'
import { values } from 'lodash'

// fto 可以将表单装换成 json
import fto from 'form_to_object'

// validator 用户表单验证
import validator from 'validator'

// 创建 less 文件，但是引用 css 文件
//import './demo.css'

// req 用于发送 AJAX 请求
import req from 'superagent'

// ErrorMsg 显示表单错误
import ErrorMsg from '../components/ErrorMsg'

class OperationGood extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      cat: [],
      disparity: [],
      hongShiProduct: [],
      pSearch: '',
      store: [],
      sSearch: '',
      currentSpec: {
        storeIds: []
      },
      images: [],
      goodsname: '',
      goodscategory:'',
      flavors:'',
      flavorone:'',
      flavortwo:'',
      flavorthree:'',
      flavorfour:'',
    }
  }
  
  componentDidMount() {
  	if (this.props.params.id) {
      this._getUpdateData(this.props.params.id)
      return
    }
    this._getAddData()
  }

  render() {
  	var { id } = this.props.params
    return (
      <DocumentTitle title={id ? '编辑产品' : '新增产品'}>
        <div>
         {/* 类名加上页面前缀防止冲突 */}

          <form onSubmit={this._submit} className="form-horizontal">
            <div className="form-group">
              <label className="control-label col-md-3">名称：</label>
              <div className="col-md-9">
               	<input type="text" name="goodsname" value={this.state.goodsname} className="form-control" onChange={this._simpleInputChange}/>
              </div>
            </div>

            <div className="form-group">
              <label className="control-label col-md-3">分类：</label>
              <div className="col-md-9">
                <select
                  name="goodscategory"
                  className="form-control"
                  value={this.state.goodscategory}
                  onChange={this._simpleInputChange}
                >
                  {this.state.cat.map((item, index) => {
                    return (
                      <option key={index} value={item.categoryId}>
                        {item.category}
                      </option>
                    )
                  })}
                </select>
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">图片：</label>
              <div className="col-md-9">
                <div className="row">
                  {
                    this.state.uploading ?
                    <div className="product-uploading">
                      <span>上传中...</span>
                    </div>
                    :
                    null
                  }
                  {this.state.images.map((item, index) => {
                    if (!item) {
                      return null
                    }
                    return (
                      <div className="col-md-4" key={index}>
                        <div className="panel panel-default">
                          <div className="panel-body">
                            <div style={{ marginBottom: 10 }}>
                              <img
                                src={item}
                                alt=""
                                className="img-responsive"
                              />
                            </div>
                            <button
                              type="button"
                              className="btn btn-danger btn-block"
                              onClick={this._deleteImg.bind(this, index)}
                            >
                              删除
                            </button>
                          </div>
                        </div>
                      </div>
                    )
                  })}
                </div>
                {this.state.images.length == 0
                  ? <button
                      className="btn btn-default"
                      type="button"
                      onClick={() => {
                        this.imgFile.click()
                      }}
                    >
                      <span className="glyphicon glyphicon-plus" />
                      添加图片
                    </button>
                  : null}
                <input
                  type="file"
                  onChange={this._onChooseImage}
                  className="hidden"
                  ref={c => {
                    this.imgFile = c
                  }}
                />
              </div>
            </div>
            <div className="form-group">
            	<label className="control-label col-md-3">规格组合：</label>
              <div className="col-md-9">
                <div className="row">
                  <div className="col-md-6">
                    <div className="panel panel-default">
                    	<div className="panel-heading">
                        <div className="input-group input-group-sm product-search">
                          <span className="input-group-addon">规格</span>
                          <input
                            type="text"
                            className="form-control input"
                            placeholder="搜索"
                            value={this.state.pSearch}
                            onChange={e => {
                              this.setState({ pSearch: e.target.value })
                            }}
                          />
                        </div>
                      </div>
                      <div className="panel-body product-specs">
                        {this.state.hongShiProduct
                          .filter(item => {
                            return item.name.indexOf(this.state.pSearch) !== -1
                          })
                          .map((item, index) => {
                            var ing = item.id === this.state.currentSpec.id

                            return (
                              <div
                                className={
                                  'checkbox product-spec' + (ing ? ' ing' : '')
                                }
                                key={index}
                                onClick={this._clickSpec.bind(this, item)}
                              >
                                <label>
                                  <input
                                    type="checkbox"
                                    onChange={this._changeSpec.bind(this, item)}
                                    checked={
                                      item.storeIds.length ===
                                        this.state.store.length
                                    }
                                    ref={c => {
                                      if (c) {
                                        c.indeterminate =
                                          item.storeIds.length > 0 &&
                                          item.storeIds.length <
                                            this.state.store.length
                                      }
                                    }}
                                  />

                                </label>
                                <span className="product-spec-name">
                                  {item.name}
                                </span>
                                {ing
                                  ? <div
                                      style={{
                                        padding: 5
                                      }}
                                    >
                                      <div className="input-group input-group-sm">
                                        <span className="input-group-addon">
                                          名称：
                                        </span>
                                        <input
                                          type="text"
                                          className="form-control"
                                          value={item.name}
                                          onChange={this._changeName.bind(
                                            this,
                                            item
                                          )}
                                        />
                                      </div>
                                      <div className="input-group input-group-sm">
                                        <span className="input-group-addon">
                                          价格：
                                        </span>
                                        <input
                                          type="text"
                                          className="form-control"
                                          value={item.hsPrice}
                                          onChange={this._changePrice.bind(
                                            this,
                                            item
                                          )}
                                        />
                                      </div>
                                      <div className="input-group input-group-sm">
                                        <span className="input-group-addon">
                                          会员价：
                                        </span>
                                        <input
                                          type="text"
                                          className="form-control"
                                          value={item.vipPrice}
                                          onChange={this._changeVipPrice.bind(
                                            this,
                                            item
                                          )}
                                        />
                                      </div>
                                      <div className="input-group input-group-sm">
                                        <span className="input-group-addon">
																					餐盒费设置：
                                        </span>
                                        <select
												                  className="form-control"
												                  value={item.specPack}
												                  onChange={this._changeSpecPack.bind(
                                            this,
                                            item
                                          )}
												                >	
												                	<option value="0">请选择</option>
												                  {this.state.disparity.map((item, index) => {
												                    return (
												                      <option key={index} value={item.id}>
												                        {item.paramname}
												                      </option>
												                    )
												                  })}
												                </select>
                                      </div>
                                    </div>
                                  : null}
                              </div>
                            )
                          })}
                      </div>
                    </div>
                  </div>
                  <div className="col-md-6">
                    <div className="panel panel-default">
                      <div className="panel-heading">
                        <div className="input-group input-group-sm product-search">
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
                      <div className="panel-body product-specs">                       
                        <span>当前规格：{this.state.currentSpec.name}</span> 
                        {this.state.store.map((item, index) => {
                          return (
                            <div className="checkbox" key={index}>
                              <label>
                                <input
                                  type="checkbox"
                                  onChange={this._changeStore.bind(
                                    this,
                                    item.storeId
                                  )}
                                  checked={
                                    this.state.currentSpec.storeIds &&
                                      this.state.currentSpec.storeIds.indexOf(
                                        item.storeId
                                      ) !== -1
                                  }
                                />
                                {item.storeName}
                              </label>
                            </div>
                          )
                        })}
                      </div>
                    </div>
                  </div>
                </div>
              </div>     
            </div>
            <div className="form-group">
            	<label className="control-label col-md-3">偏好名称设置：</label>
              <div className="col-md-9">
              	<span style={{padding:'10px'}}>
               		<input type="text" name="flavors" value={this.state.flavors} onChange={this._simpleInputChange}/>
              	</span>
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">偏好属性设置：</label>
              <div className="col-md-9">
              	<span style={{padding:'10px'}}>
               		<input type="text" name="flavorone" value={this.state.flavorone} onChange={this._simpleInputChange}/><br/>
               	</span>
               	<span style={{padding:'10px'}}>
               		<input type="text" name="flavortwo" value={this.state.flavortwo} onChange={this._simpleInputChange}/><br/>
               	</span>
               	<span style={{padding:'10px'}}>
               		<input type="text" name="flavorthree" value={this.state.flavorthree} onChange={this._simpleInputChange}/><br/>
               	</span>
               	<span style={{padding:'10px'}}>
               		<input type="text" name="flavorfour" value={this.state.flavorfour} onChange={this._simpleInputChange}/>
              	</span>
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
  
  _simpleInputChange = e => {
    this.setState({
      [e.target.name]: e.target.value
    })
  }
  
  _deleteImg = index => {
    this.setState(prevState => {
      var n = prevState.images.slice()
      n.splice(index, 1)
      return {
        images: n
      }
    })
  }
  
  _onChooseImage = fe => {
    if (fe.target.files && fe.target.files[0]) {
      var f = fe.target.files[0]

      this.setState({
        uploading: true
      })

      var fd = new FormData()
      fd.append('file', f)
      req
        .post('/uclee-product-web/doUploadImage')
        .send(fd)
        .end((err, res) => {
          if (err) {
            return err
          }
          
          this.setState(prevState => {
            var n = prevState.images.slice()
            n.push(res.text)
            return {
              uploading: false,
              images: n
            }
          })
        })
    }
  }
  
  _getUpdateData = id => {
    req
      .get('/uclee-backend-web/updateProduct')
      .query({
        productId: id
      })
      .end((err, res) => {
        if (err) {
          return err
        }

        // merge goods.valuePost to hongShiProduct
        var hongShiProduct = res.body.hongShiProduct.slice()
        hongShiProduct = hongShiProduct.map((item) => {
          return {
            ...item,
            storeIds: [],
            hsStock: '' //???
          }
        })
        res.body.goods.valuePost.forEach((vp) => {
          hongShiProduct.every((hsp, index) => {
            if (hsp.code === vp.code) {
              hongShiProduct[index] = {
                ...hsp,
                ...vp
              }
              return false // to break
            }
            return true
          })
        })

        this.setState({
          goodsname: res.body.goods.goodsname,
          images: res.body.goods.images,
          cat: res.body.cat,
          hongShiProduct: hongShiProduct,
          store: res.body.store,
          goodscategory: res.body.goods.goodscategory,
          disparity: res.body.disparity,
          flavors:res.body.goods.flavors,
          flavorone:res.body.goods.flavorone,
      		flavortwo:res.body.goods.flavortwo,
      		flavorthree:res.body.goods.flavorthree,
      		flavorfour:res.body.goods.flavorfour,

        }) 
        hongShiProduct.forEach(item => {
          this.hongShiProductById[item.id] = item
        })
      })
  }
  
  _getAddData = () => {
    req.get('/uclee-backend-web/getAddData').end((err, res) => {
      if (err) {
        return err
      }

      var hongShiProduct = res.body.hongShiProduct.map(item => {
        return {
          ...item,
          storeIds: [],
          hsStock: '',
          checked: false
        }
      })

      this.setState({
        cat: res.body.cat,
        disparity: res.body.disparity,
        hongShiProduct: hongShiProduct,
        store: res.body.store
      })

      hongShiProduct.forEach(item => {
        this.hongShiProductById[item.id] = item
      })
    })
  }
  
    _simpleInputChange = e => {
    this.setState({
      [e.target.name]: e.target.value
    })
  }

  _quillChange = v => {
    this.setState({
      text: v
    })
  }

  _clickSpec = item => {
    this.setState({
      currentSpec: item
    })
  }

  _changeName = (item, e) => {
    var nhongShiProduct = this.state.hongShiProduct.slice()
    var changedItem = null

    nhongShiProduct.forEach((a, index) => {
      if (a.id === item.id) {
        nhongShiProduct[index].name = e.target.value
        changedItem = nhongShiProduct[index]
      }
    })

    this.setState({
      currentSpec: changedItem,
      hongShiProduct: nhongShiProduct
    })
  }

  _changePrice = (item, e) => {
    var nhongShiProduct = this.state.hongShiProduct.slice()
    var changedItem = null

    nhongShiProduct.forEach((a, index) => {
      if (a.id === item.id) {
        nhongShiProduct[index].hsPrice = e.target.value
        changedItem = nhongShiProduct[index]
      }
    })

    this.setState({
      currentSpec: changedItem,
      hongShiProduct: nhongShiProduct
    })
  }

  _changeVipPrice = (item, e) => {
    var nhongShiProduct = this.state.hongShiProduct.slice()
    var changedItem = null

    nhongShiProduct.forEach((a, index) => {
      if (a.id === item.id) {
        nhongShiProduct[index].vipPrice = e.target.value
        changedItem = nhongShiProduct[index]
      }
    })
    this.setState({
      currentSpec: changedItem,
      hongShiProduct: nhongShiProduct
    })
  }
  
  _changeSpecPack = (item, e) => {
    var nhongShiProduct = this.state.hongShiProduct.slice()
    var changedItem = null

    nhongShiProduct.forEach((a, index) => {
      if (a.id === item.id) {
        nhongShiProduct[index].specPack = e.target.value
        changedItem = nhongShiProduct[index]
      }
    })
    this.setState({
      currentSpec: changedItem,
      hongShiProduct: nhongShiProduct
    })
  }


  _changeSpec = (item, e) => {
    var nhongShiProduct = this.state.hongShiProduct.slice()
    var changedItem = null

    nhongShiProduct.forEach((a, index) => {
      if (a.id === item.id) {
        if (e.target.checked) {
          // check all store
          var allStoreId = this.state.store.map(item => item.storeId)
          nhongShiProduct[index].storeIds = allStoreId
        } else {
          nhongShiProduct[index].storeIds = []
        }

        changedItem = nhongShiProduct[index]
      }
    })

    this.setState({
      currentSpec: changedItem,
      hongShiProduct: nhongShiProduct
    })
  }

  _changeStore = (storeId, e) => {
    var nhongShiProduct = this.state.hongShiProduct.slice()
    nhongShiProduct.forEach((a, index) => {
      if (a.id === this.state.currentSpec.id) {
        if (e.target.checked) {
          nhongShiProduct[index].storeIds.push(storeId)
        } else {
          nhongShiProduct[index].storeIds.splice(a.storeIds.indexOf(storeId), 1)
        }
      }
    })

    this.setState({
      hongShiProduct: nhongShiProduct
    })
  }

  _submit = (e) => {
    e.preventDefault()
    var data = fto(e.target)
    data.goodsimg = this.state.images[0]
    data.id = this.props.params.id
    data.goodsSpecifications = this.state.hongShiProduct.filter(item => {
      return item.storeIds.length > 0
    })
    console.log(data)


    if (!data.goodsname) {
      return this.setState({
        err: '请填写名称'
      })
    }

    if (!data.goodsimg) {
      return this.setState({
        err: '请上传图片'
      })
    }
    
    var foundWrongName = false
    data.goodsSpecifications.every(item => {
      if (!item.name) {
        foundWrongName = true
        return false
      }
      return true
    })
    if (foundWrongName) {
      return this.setState({
        err: '规格名称不能为空'
      })
    }

    var foundWrongPrice = false
    data.goodsSpecifications.every(item => {
      if (!/^\d+(\.\d{1,2})?$/.test(item.hsPrice)) {
        foundWrongPrice = true
        return false
      }
      return true
    })
    if (foundWrongPrice) {
      return this.setState({
        err: '价格填写有误'
      })
    }
    
    var foundWrongSpecPack = false
    data.goodsSpecifications.every(item => {
      if (item.specPack == 0) {
        foundWrongSpecPack = true
        return false
      }
      return true
    })
    if (foundWrongSpecPack) {
      return this.setState({
        err: '请选择餐盒费设置'
      })
    }
    
    this.setState({
      err: null
    })
    
    var url = '/uclee-backend-web/addGoods'
    if (this.props.params.id) {
      url = '/uclee-backend-web/upGoods'
    }

    req
      .post(url)
      .send(data)
      .end((err, res) => {
        if (err) {
          return err
        }
				if(res.body > 0){
      		alert("操作成功!")
     		}	
      	return window.location="/goodslist";
      })




  }
}

export default OperationGood