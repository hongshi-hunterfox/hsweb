import React from 'react'
import DocumentTitle from 'react-document-title'
import fto from 'form_to_object'
// import validator from 'validator'
import './product.css'
import req from 'superagent'
import ErrorMsg from '../components/ErrorMsg'

import ReactQuill from 'react-quill'
import 'react-quill/dist/quill.snow.css'
import './input-moment/input-moment.less'
import InputMoment from 'input-moment'
import moment from 'moment';
class Product extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,

      cat: [],
      hongShiProduct: [],
      pSearch: '',
      store: [],
      sSearch: '',

      currentSpec: {
        storeIds: []
      },
      isShow:false,
      shippingFree:false,
      text: '',
      title: '',
      explain: '',
      appointedTime: 0,
      pickUpTime:'',
      pickEndTime:'',
      parameter: '',
      categoryId: '',
      images: [],
      sale:0,
      attribute1:'',
      attribute2:'',
      attribute3:'',
      attribute4:'',
      attribute5:'',
      attribute6:''
      
    }

    this.hongShiProductById = {}
    this.quill = null
    this.imgFile = null
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
    var begin = this.state.currentSpec.startTimeStr
    var end = this.state.currentSpec.endTimeStr
    var date1 = new Date(begin).getTime()
    var date2 = new Date(end).getTime()
    return (
      <DocumentTitle title={id ? '编辑产品' : '新增产品'}>
        <div className="product">
          <form onSubmit={this._submit} className="form-horizontal">
            <div className="form-group">
            {this.props.params?<input type='hidden' name='productId' value={this.props.params.id}/>:null}
              <label className="control-label col-md-3">名称：</label>
              <div className="col-md-9">
                <input
                  type="text"
                  name="title"
                  className="form-control"
                  value={this.state.title}
                  onChange={this._simpleInputChange}
                />
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">分类：</label>
              <div className="col-md-9">
                <select
                  name="categoryId"
                  className="form-control"
                  value={this.state.categoryId}
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
              <label className="control-label col-md-3">销量：</label>
              <div className="col-md-9">
                <input
                  type="text"
                  name="sale"
                  className="form-control"
                  value={this.state.sale}
                  onChange={this._simpleInputChange}
                />
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">提前/小时下单：</label>
              <div className="col-md-9">
                <input
                  type="text"
                  name="appointedTime"
                  className="form-control"
                  value={this.state.appointedTime}
                  onChange={this._simpleInputChange}
                />
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">取货起始时间：</label>
              <div className="col-md-3">
                <input
                  type="date"
                  name="pickUpTimes"
                  className="form-control"
                  value={this.state.pickUpTimes}
                  onChange={this._simpleInputChange}
                />
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">取货结束时间：</label>
              <div className="col-md-3">
                <input
                  type="date"
                  name="pickEndTimes"
                  className="form-control"
                  value={this.state.pickEndTimes}
                  onChange={this._simpleInputChange}
                />
              </div>
            </div>
            <div className="form-group">
                <label className="control-label col-md-3">是否显示：</label>
                <div className="col-md-9">
                <select
                    name="isShow"
                    className="form-control"
                     value={this.state.isShow}
                    onChange={this._simpleInputChange}
                    >
                  <option value={false}>否</option>
                  <option value={true}>是</option>
                </select>
                </div>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">是否免运费：</label>
              <div className="col-md-9">
                <select
                  name="shippingFree"
                  className="form-control"
                  value={this.state.shippingFree}
                  onChange={this._simpleInputChange}
                >
                  <option value={false}>否</option>
                  <option value={true}>是</option>
                </select>
              </div>
            </div>
            <div className="form-group">
            {this.props.params?<input type='hidden' name='productId' value={this.props.params.id}/>:null}
              <label className="control-label col-md-3">分享描述：</label>
              <div className="col-md-9">
                <textarea rows="2" cols="20" value={this.state.explain} name="explain" className="form-control" onChange={this._simpleInputChange}>
                </textarea>
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
                {this.state.images.length < 3
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
            <div className="product-hint">
              <span className=''>图片分辨率建议450*450，长宽比1:1，图片大小不超过500kb</span>
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
                                          库存：
                                        </span>
                                        <input
                                          type="text"
                                          className="form-control"
                                          value={item.hsStock}
                                          onChange={this._changeStock.bind(
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
                                          原价：
                                        </span>
                                        <input
                                          type="text"
                                          className="form-control"
                                          value={item.prePrice}
                                          onChange={this._changePrePrice.bind(
                                            this,
                                            item
                                          )}
                                        />
                                      </div>
                                       <div className="input-group input-group-sm">
                                        <span className="input-group-addon">
                                          促销价：
                                        </span>
                                        <input
                                          type="text"
                                          className="form-control"
                                          value={item.promotionPrice}
                                          onChange={this._changePromotionPrice.bind(
                                            this,
                                            item
                                          )}
                                        />
                                      </div>
                                      <div className="input-group input-group-sm">
                                        <span className="input-group-addon">
                                          促销开始：
                                        </span>
                                        <input type='text' className="form-control" placeholder="格式1997-01-16 00:00:00" value={item.startTimeStr==null ? '1997-01-16 00:00:00' : item.startTimeStr} onChange={this._changeStartTime.bind(this,item)}/>
                                      </div>
                                        <div className="input-group input-group-sm">
                                        <span className="input-group-addon">
                                          促销结束：
                                        </span>
                                        <input type='text' className="form-control"  placeholder="格式1997-01-16 00:00:00"value={item.endTimeStr==null ? '1997-01-16 00:00:00' : item.endTimeStr} onChange={this._changeEndTime.bind(this,item)}/>
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
                <label className="control-label col-md-3">规格参数名称：</label>
						  		<div className="col-md-9">
						  		 <div className="input-group input-group-sm">
                		<input 
                  		type="text"
                  		name="parameter"
                  		className="form-control"
                  		value={this.state.parameter}
                  		onChange={this._simpleInputChange}
                		/>
                	 </div>
              		</div>
            </div>
            <div className="form-group">
            	<label className="control-label col-md-3">规格参数属性：</label>
						  	<div className="col-md-9">
						  		<table>
						  		 <tr>
                		<td cellspacing="2px"><span className="input-group input-group-sm">
                		<input type="text" name="attribute1" className="form-control"
                			value={this.state.attribute1}
                  		onChange={this._simpleInputChange}
                		/>
                		</span></td>
                		&nbsp;&nbsp;
                		<td cellspacing="2px"><span className="input-group input-group-sm">
                		 <input type="text" name="attribute2" className="form-control" 
                			value={this.state.attribute2}
                  		onChange={this._simpleInputChange}
                		 />
                	  </span></td>
                	  &nbsp;&nbsp;
                	  <td><span className="input-group input-group-sm">
                		 <input type="text" name="attribute3" className="form-control" 
                			value={this.state.attribute3}
                  		onChange={this._simpleInputChange}
                		 />
                	  </span></td>
                	 </tr>
                	 <tr>
                		<td cellspacing="2px"><span className="input-group input-group-sm">
                		<input type="text" name="attribute4" className="form-control"
                			value={this.state.attribute4}
                  		onChange={this._simpleInputChange}
                		/>
                		</span></td>
                		&nbsp;&nbsp;
                		<td cellspacing="2px"><span className="input-group input-group-sm">
                		 <input type="text" name="attribute5" className="form-control" 
                			value={this.state.attribute5}
                  		onChange={this._simpleInputChange}
                		 />
                	  </span></td>
										&nbsp;&nbsp;
                	  <td><span className="input-group input-group-sm">
                		 <input type="text" name="attribute6" className="form-control" 
                			value={this.state.attribute6}
                  		onChange={this._simpleInputChange}
                		 />
                	  </span></td>
                	 </tr>
                	</table> 
              	</div>
            </div>
            
            <div className="form-group">
              <label className="control-label col-md-3">描述：</label>
              <div className="col-md-9">
                <ReactQuill
                  ref={el => {
                    this.quill = el
                  }}
                  modules={{
                    toolbar: [
                      ['bold', 'italic', 'underline', 'strike'],
                      [{ header: [1, 2, 3, 4, 5, 6, false] }],
                      [{ color: [] }, { background: [] }],
                      [{ list: 'ordered' }, { list: 'bullet' }],
                      ['link', 'image']
                    ]
                  }}
                  value={this.state.text}
                  onChange={this._quillChange}
                  theme="snow"
                />
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

  _getUpdateData = id => {
    req
      .get('/uclee-product-web/updateProduct')
      .query({
        productId: id
      })
      .end((err, res) => {
        if (err) {
          return err
        }

        // merge productFrom.valuePost to hongShiProduct
        var hongShiProduct = res.body.hongShiProduct.slice()
        hongShiProduct = hongShiProduct.map((item) => {
          return {
            ...item,
            storeIds: [],
            hsStock: '' //???
          }
        })
        res.body.productForm.valuePost.forEach((vp) => {
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
          isShow : res.body.productForm.isShow,
          shippingFree:res.body.productForm.shippingFree,
          title: res.body.productForm.title,
          explain: res.body.productForm.explain,
          appointedTime: res.body.productForm.appointedTime,
          pickUpTimes: res.body.productForm.pickUpTimes,
          pickEndTimes: res.body.productForm.pickEndTimes,
          parameter: res.body.productForm.parameter,
          categoryId: res.body.productForm.categoryId,
          //images: res.body.productForm.images,
          images: res.body.productForm.images.filter((item) => (item !== null)),
          cat: res.body.cat,
          hongShiProduct: hongShiProduct,
          store: res.body.store,
          text: res.body.productForm.description,
          sale:res.body.sale,
          attribute1:res.body.attribute1,
          attribute2:res.body.attribute2,
          attribute3:res.body.attribute3,
          attribute4:res.body.attribute4,
          attribute5:res.body.attribute5,
          attribute6:res.body.attribute6,
          
        })       
        hongShiProduct.forEach(item => {
          this.hongShiProductById[item.id] = item
        })
      })
  }

  _getAddData = () => {
    req.get('/uclee-product-web/addProduct').end((err, res) => {
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

  _changeStock = (item, e) => {
    var nhongShiProduct = this.state.hongShiProduct.slice()
    var changedItem = null

    nhongShiProduct.forEach((a, index) => {
      if (a.id === item.id) {
        nhongShiProduct[index].hsStock = e.target.value
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

  _changePrePrice = (item, e) => {
    var nhongShiProduct = this.state.hongShiProduct.slice()
    var changedItem = null

    nhongShiProduct.forEach((a, index) => {
      if (a.id === item.id) {
        nhongShiProduct[index].prePrice = e.target.value
        changedItem = nhongShiProduct[index]
      }
    })
    this.setState({
      currentSpec: changedItem,
      hongShiProduct: nhongShiProduct
    })
  }

 _changePromotionPrice = (item, e) => {
    var nhongShiProduct = this.state.hongShiProduct.slice()
    var changedItem = null

    nhongShiProduct.forEach((a, index) => {
      if (a.id === item.id) {
        nhongShiProduct[index].promotionPrice = e.target.value
        changedItem = nhongShiProduct[index]
      }
    })
    this.setState({
      currentSpec: changedItem,
      hongShiProduct: nhongShiProduct
    })
  }
 
  _changeStartTime = (item, e) => {
    var nhongShiProduct = this.state.hongShiProduct.slice()
    var changedItem = null

    nhongShiProduct.forEach((a, index) => {
      if (a.id === item.id) {
        nhongShiProduct[index].startTimeStr = e.target.value
        changedItem = nhongShiProduct[index]
      }
    })
    this.setState({
      currentSpec: changedItem,
      hongShiProduct: nhongShiProduct
    })
  }
  
   _changeEndTime = (item, e) => {
    var nhongShiProduct = this.state.hongShiProduct.slice()
    var changedItem = null
    nhongShiProduct.forEach((a, index) => {
      if (a.id === item.id) {
        nhongShiProduct[index].endTimeStr = e.target.value
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

  _deleteImg = index => {
    this.setState(prevState => {
      var n = prevState.images.slice()
      n.splice(index, 1)
      return {
        images: n
      }
    })
  }

  _submit = e => {
    e.preventDefault()
    var data = fto(e.target)
    data.valuePost = this.state.hongShiProduct.filter(item => {
      return item.storeIds.length > 0
    })
    data.images = this.state.images
    data.description = document.querySelector('.ql-editor').innerHTML
    console.log(data)

    if (!data.title) {
      return this.setState({
        err: '请填写产品名称'
      })
    }    
    if (Number(data.sale)<0) {
      return this.setState({
        err: '销量不可以为负数'
      })
    }
    var params=data.title+'';
    if(this.props.params.id){
        params=params+'&productId='+this.props.params.id;
    }
    req.get('/uclee-product-web/isTitleExisted?title='+params).end((err, res) => {
      if (err) {
        return err
      }
      var resJson = JSON.parse(res.text)
      if(!resJson.result){
        return this.setState({
          err: '名称已存在'
        });
      }

    if (!data.images || !data.images.length) {
      return this.setState({
        err: '至少上传一张图片'
      })
    }

    var foundWrongName = false
    data.valuePost.every(item => {
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

    var foundWrongStock = false
    data.valuePost.every(item => {
      if (!item.hsStock) {
        foundWrongStock = true
        return false
      }
      return true
    })
    if (foundWrongStock) {
      return this.setState({
        err: '库存不能为空'
      })
    }
    var foundWrongPrice = false
    data.valuePost.every(item => {
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

    this.setState({
      err: ''
    })

    var url = '/uclee-product-web/doAddProductHandler'
    if (this.props.params.id) {
      url = '/uclee-product-web/doUpdateProductHandler'
    }

    req.post(url).send(data).end((err, res) => {
      if (err) {
        return err
      }

      console.log(res.body)
      if(res.body){
        window.location='/product-list';
      }else{
        alert("网络繁忙，请稍后重试");
      }
    })
    })

  }
}

export default Product
