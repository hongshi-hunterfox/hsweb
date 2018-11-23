import React from 'react'
import DocumentTitle from 'react-document-title'
import fto from 'form_to_object'
// import validator from 'validator'
import './product.css'
import req from 'superagent'
import ErrorMsg from '../components/ErrorMsg'

class EditBanner extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      image: null,
      uploading: false,
      link:''
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/banner?id='+this.props.location.query.id).end((err, res) => {
      if (err) {
        return err
      }

      console.log(res.body)
      if (res.body) {
        this.setState({
          image:res.body.imageUrl,
          link:res.body.link
        })
      } else {
        alert('网络繁忙，请稍后重试')
      }
    })
  }

  render() {
    return (
      <DocumentTitle title={'编辑banner'}>
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
            <div className="form-group">
              <label className="control-label col-md-3">链接：</label>
              <input type='text' name='link' value={this.state.link} onChange={this._linkChange}/>
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
  _linkChange=(e)=>{
    this.setState({
      link:e.target.value
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
    if(this.props.location.query.id){
      data.id=this.props.location.query.id
    }
    console.log(data)

    if (!data.image) {
      return this.setState({
        err: '至少上传一张图片'
      })
    }

    req.post('/uclee-backend-web/editBanner').send(data).end((err, res) => {
      if (err) {
        return err
      }

      console.log(res.body)
      if (res.body) {
        window.location = '/banner-list'
      } else {
        alert('网络繁忙，请稍后重试')
      }
    })
  }
}

export default EditBanner
