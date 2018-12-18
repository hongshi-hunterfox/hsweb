import React from 'react'
import DocumentTitle from 'react-document-title'
import fto from 'form_to_object'
// import validator from 'validator'
import './product.css'
import req from 'superagent'
import ErrorMsg from '../components/ErrorMsg'

class MarketingEntranceDetail extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      image: null,
      uploading: false,
      detail:[],
    }
    this.imgFile = null
    this.imgFile1 = null
  }

  componentDidMount() {
    req.get('/uclee-backend-web/getMarketingEntrance?id='+this.props.location.query.id).end((err, res) => {
      		if (err) {
        		return err
      		}
      		var data = JSON.parse(res.text)
      		this.setState({
        		detail: data.detail,
        		imgUrl: data.detail.imgUrl
      		})
    })
  }

  render() {
    return (
       <DocumentTitle title={this.props.location.query.id ? '编辑设置' : '添加设置'}>
        <div className="product">
          <form onSubmit={this._submit} className="form-horizontal">
              <div className="form-group">
              <label className="control-label col-md-3">图标设置：</label>
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
                  <div className="col-md-4" >
                    <div className="panel panel-default">
                      <div className="panel-body">
                        <div style={{ marginBottom: 10 }}>
                          <img
                            src={this.state.imgUrl}
                            alt=""
                            className="img-responsive"
                          />
                        </div>

                      </div>
                    </div>
                  </div>
                </div>
                <button
                  className="btn btn-default"
                  type="button"
                  onClick={() => {
                    this.imgFile.click()
                  }}
                >
                  <span className="glyphicon glyphicon-plus" />
                  更换图片
                  {this.state.imgUrl}
                </button>
                <input
                  type="file"
                  onChange={this._onChooseLogoImage}
                  className="hidden"
                  ref={c => {
                    this.imgFile = c
                  }}
                />
              </div>
            </div>
            {this.props.location.query.id?<input type='hidden' name='id' value={this.props.location.query.id}/>:null}
            <label className="control-label col-md-3" style={{marginTop:'10px'}}>营销入口名称：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.detail.name} name="name" className="form-control" onChange={this._titleChange}/>
              </div>
            <label className="control-label col-md-3" style={{marginTop:'10px'}}>营销地址url：</label>
              <div className="col-md-9" style={{marginTop:'10px'}}>
                <input type="text" value={this.state.detail.url} name="url" className="form-control" onChange={this._titleChange}/>
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
    var c = Object.assign([], this.state.detail)   	
      	c[e.target.name] = e.target.value
      	this.setState({
      		detail:c
    	})
  }
  _click = () => {
    this.imgFile.click()
  }

  _onChooseLogoImage = fe => {
    console.log("进入_onChooseLogoImage")
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
          console.log(this.state);
          this.setState({
              imgUrl: res.text,
              uploading:false
          })
          console.log(this.state);
        })
    }
  }

  _submit = (e) => {
    e.preventDefault()
    var data = fto(e.target)
    console.log(data)
    if(this.props.location.query.id){
      data.id=this.props.location.query.id
    }
    console.log(this.state.imgUrl)
	data.imgUrl = this.state.imgUrl;
    var url = '/uclee-backend-web/insertMarketingEntrance'
    if (this.props.location.query.id) {
      url = '/uclee-backend-web/updateMarketingEntrance'
    }
    req
      .post(url)
      .send(data)
      .end((err, res) => {
        if (err) {
          return err
        }
        if(res.body){
          window.location='/marketing-entranceList';
        }else{
          alert('网络繁忙，请稍后重试');
        }
        console.log(res.body)
      })
  }
}

export default MarketingEntranceDetail