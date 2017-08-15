import React from 'react'
import DocumentTitle from 'react-document-title'
import fto from 'form_to_object'
// import validator from 'validator'
import './product.css'
import req from 'superagent'
import ErrorMsg from '../components/ErrorMsg'

import ReactQuill from 'react-quill'
import 'react-quill/dist/quill.snow.css'

class StoreIntro extends React.Component {
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

      text: '',
      title: '',
      categoryId: '',
      images: []
    }

    this.hongShiProductById = {}
    this.quill = null
    this.imgFile = null
  }

  componentDidMount() {
    var q = {};
    req.get('/uclee-backend-web/storeInfo').query(q).end((err, res) => {
      if (err) {
        return err
      }
      this.setState({
        text:res.body.text
      })
    })
  }

  render() {
    return (
      <DocumentTitle title='编辑公司介绍'>
        <div className="product">
          <form onSubmit={this._submit} className="form-horizontal">
            
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

  
  _quillChange = v => {
    this.setState({
      text: v
    })
  }

  _submit = e => {
    e.preventDefault()
    var data = {}
    console.log(document.querySelector('.ql-editor').innerHTML)
    data.description = document.querySelector('.ql-editor').innerHTML
    console.log(data)
    req.post('/uclee-backend-web/updateStoreInfo').send(data).end((err, res) => {
      if (err) {
        return err
      }
      window.location='/store-intro';
    })
  }
}

export default StoreIntro
