import React from 'react'
import DocumentTitle from 'react-document-title'
import fto from 'form_to_object'
import req from 'superagent'
import ErrorMsg from '../components/ErrorMsg'

class ModuleSetting extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      groupId:null,
      groupName:'',
      image:'',
      uploading: false,
      text:'',
      all:[],
      products: [],
      filter: ''
    }
  }

  componentDidMount() {
  	req.get('/uclee-backend-web/getGroupName?groupId='+ (this.props.location.query.groupId?this.props.location.query.groupId:'')).end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        groupName: data.groupName,
        image: data.image
      })
    })
   
      req.get('/uclee-product-web/productList').end((err, res) => {
        if (err) {
          return err
        }

        this.setState(res.body)
      })
    
  }

  render() {
    return (
      <DocumentTitle title={'编辑首页产品'}>
        <div className="product">
          <form onSubmit={this._submit} className="form-horizontal">
            <div className="form-group">
               <label className="control-label col-md-3">图片：</label>
              <div className="col-md-9">
                <div className="row">
                  {this.state.uploading
                    ? <div className="product-uploading">
                        <span>上传中...</span>
                      </div>
                    : null}
                  <div className="col-md-4">
                    <div className="panel panel-default">
                      <div className="panel-body">
                        <div style={{ marginBottom: 10 }}>
                          {
                            this.state.image ?
                            <div>
                            <img src={this.state.image} alt="" className="img-responsive" onClick={this._click} width="100%" style={{
                              cursor: 'pointer'
                            }}/>
                            <input type="hidden" name="image" value={this.state.image}/>
                            <div className="text-center">点击重新选择</div>
                            </div>
                            :
                            <button className="btn btn-default" type="button" onClick={this._click}>
                              <span className="glyphicon glyphicon-plus"></span>
                              添加图片
                            </button>
                          }
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

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
              <label className="control-label col-md-3">设置栏目名称：</label>
              <div className="col-md-9">
              <input type='hidden' name='groupId' value={this.props.location.query.groupId} onChange={this._change}/>
              <input type='text' name='groupName' value={this.state.groupName} onChange={this._change}/>
              </div>
              <div className="form-group">
              </div>
              <label className="control-label col-md-3">显示方式：</label>
              <div div className="col-md-9">
               <select name='displayType'>
                <option value='1' selected={this.props.location.query.displayType===1?'selected':null}>横向显示</option>
                <option value='2' selected={this.props.location.query.displayType===2?'selected':null}>竖向显示</option>
              </select>
            </div>
            <div className="form-group">
              <div className="col-md-9 col-md-offset-3">
                <button type="submit" className="btn btn-primary">提交</button>
              </div>
            </div>
          </form>
          <ErrorMsg msg={this.state.err} />
        </div>
      </DocumentTitle>
    )
  }


   _change = e => {
    this.setState({
      [e.target.name]: e.target.value
    })
  }
   _click = () => {
    this.imgFile.click()
  }
    _onChooseImage = fe => {
     if (fe.target.files && fe.target.files[0]) {
      var f = fe.target.files[0]

      this.setState({
        uploading: true
      })

      var fd = new FormData()
      fd.append('file', f)
      req.post('/uclee-product-web/doUploadImage').send(fd).end((err, res) => {
        if (err) {
          return err
        }

        this.setState({
          uploading: false,
          image: res.text
        })
      })
    }
    }
  _submit = e => {
    e.preventDefault()
    var data = fto(e.target)
    console.log("data===="+data.groupId);

    if (!data.groupName&&!data.image) {
      return this.setState({
        err: '名称或图片请至少设置一种才能提交'
      })
    }


    var url = '/uclee-backend-web/insertGroupName'
    if(this.props.location.query.groupId){
    	url = '/uclee-backend-web/updateGroupName'
    }
    req.post(url).send(data).end((err, res) => {
      if (err) {
        return err
      }
       console.log(res.body)
     if (res.body) {
        window.location = '/editModuleSetting'
      } else {
        alert('该栏目已存在')
      }
        
    })
  }
}

export default ModuleSetting
