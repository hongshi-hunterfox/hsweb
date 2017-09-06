import React from 'react'
import DocumentTitle from 'react-document-title'
import fto from 'form_to_object'
// import validator from 'validator'
import './product.css'
import req from 'superagent'
import ErrorMsg from '../components/ErrorMsg'

class EditCategory extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      err: null,
      category:''
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/category?categoryId='+(this.props.location.query.categoryId?this.props.location.query.categoryId:0)).end((err, res) => {
      if (err) {
        return err
      }

      console.log(res.body)
      if (res.body) {
        this.setState({
          category:res.body.category
        })
      }
    })
  }

  render() {
    return (
      <DocumentTitle title={'编辑分类'}>
        <div className="product">
          <form onSubmit={this._submit} className="form-horizontal">

            
            <div className="form-group">
              <label className="control-label col-md-3">分类：</label>
              <input type='text' name='category' value={this.state.category} onChange={this._categoryChange}/>
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
  _categoryChange=(e)=>{
    this.setState({
      category:e.target.value
    })
  }
  _click = () => {
    this.imgFile.click()
  }

  


  _submit = e => {
    e.preventDefault()
    var data = fto(e.target)
    if(this.props.location.query.categoryId){
      data.categoryId=this.props.location.query.categoryId
    }
    console.log(data)
    req.post('/uclee-backend-web/editCategory').send(data).end((err, res) => {
      if (err) {
        return err
      }

      console.log(res.body)
      if (res.body) {
        window.location = '/category-list'
      } else {
        alert('网络繁忙，请稍后重试')
      }
    })
  }
}

export default EditCategory
