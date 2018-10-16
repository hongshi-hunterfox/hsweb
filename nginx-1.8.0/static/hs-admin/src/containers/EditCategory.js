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
      category:'',
      batchDiscount:'1',
      startTimeStrs:'',
      endTimeStrs:''
      
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
          category:res.body.category,
          batchDiscount:res.body.batchDiscount,
          startTimeStrs:res.body.startTimeStrs,
          endTimeStrs:res.body.endTimeStrs,
          startTimeStr:res.body.startTimeStr,
          endTimeStr:res.body.endTimeStr
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
              <input type='text' name='category' value={this.state.category} onChange={this._change}/>
            </div>
            <div className="form-group">
              <label className="control-label col-md-3">批量设置折扣：</label>
              <input type='text' name='batchDiscount' value={this.state.batchDiscount} onChange={this._change}/>
            </div>
             <div className="form-group">
                                        <label className="control-label col-md-3">
                                          批量设置开始时间：
                                        </label>
                                        <input type='text' name="startTimeStrs" value={this.state.startTimeStrs} onChange={this._change}/>
                                      </div>
                                        <div className="form-group">
                                        <label className="control-label col-md-3">
                                          批量设置结束时间：
                                        </label>
                                        <input type='text' name="endTimeStrs"  value={this.state.endTimeStrs} onChange={this._change}/>
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
 
   _change = e => {
    this.setState({
      [e.target.name]: e.target.value
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
    if(data.batchDiscount<=1&&data.batchDiscount>0){
    req.post('/uclee-backend-web/editCategory').send(data).end((err, res) => {
      if (err) {
        return err
      }

      var data = JSON.parse(res.text)
      if(data.result){
        window.location='/category-list'
      }else{
        alert(data.reason);
      }
    })
    }else{
    	alert("设置折扣必须为小数");
    	return false;
    }
  }
}

export default EditCategory
