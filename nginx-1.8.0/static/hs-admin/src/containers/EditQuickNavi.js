import React from 'react'
import DocumentTitle from 'react-document-title'
import fto from 'form_to_object'
// import validator from 'validator'
import './product.css'
import req from 'superagent'
import ErrorMsg from '../components/ErrorMsg'

class EditQuickNavi extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      image: null,
      uploading: false,
      title:''
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/quickNavi?naviId='+(this.props.location.query.naviId?this.props.location.query.naviId:0)).end((err, res) => {
      if (err) {
        return err
      }

      console.log(res.body)
      if (res.body) {
        this.setState({
          image:res.body.imageUrl,
          title:res.body.title
        })
      } 
    })
  }

  render() {
    return (
      <DocumentTitle title={'编辑快捷导航'}>
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
                            <input type="hidden" name="imageUrl" value={this.state.image}/>
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
            <div className="form-group">
              <label className="control-label col-md-3">标题：</label>
              <input type='text' name='title' value={this.state.title} onChange={this._titleChange}/>
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
  _titleChange=(e)=>{
    this.setState({
      title:e.target.value
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
    if(this.props.location.query.naviId){
      data.naviId=this.props.location.query.naviId
    }
    console.log(data)

    if (!data.imageUrl) {
      return this.setState({
        err: '至少上传一张图片'
      })
    }

    req.post('/uclee-backend-web/editQiuckNavi').send(data).end((err, res) => {
      if (err) {
        return err
      }

      console.log(res.body)
      if (res.body) {
        window.location = '/quick-navi-list'
      } else {
        alert('网络繁忙，请稍后重试')
      }
    })
  }
}

export default EditQuickNavi
