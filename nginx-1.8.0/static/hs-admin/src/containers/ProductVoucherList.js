import React from 'react'
import DocumentTitle from 'react-document-title'
import './user-list.css'
import req from 'superagent'
// import { Link } from 'react-router'

class ProductVoucherList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      all:[],
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/getAllProductVoucher').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        all: data.all
      })
    })
  }

  _delHandler=(id)=>{
    req.get('/uclee-backend-web/delProductVoucher?id='+id).end((err, res) => {
      if (err) {
        return err
      }
      window.location='/product-voucher-list'
    })
  }
  _newHandler=()=>{
    window.location="/product-voucher-setting";
  }
  _edit=(url)=>{
    window.location=url;
  }
  
  render() {
    var list = this.state.all.map((item, index) => {
      return (
        <tr key={index}>
          <td>{item.name}</td>
          <td>{item.voucher}</td>
          <td>{item.amount}</td>
          <td>
            <button className='btn btn-primary' style={{margin: '0 5px'}} onClick={this._edit.bind(this,'/product-voucher-setting?id='+ item.id)}>编辑</button>
            <button className='btn btn-danger' style={{margin: '0 5px'}} onClick={this._delHandler.bind(this,item.id)}>   删除</button>
            <button className='btn btn-primary' style={{margin: '0 5px'}} onClick={this._edit.bind(this,'/edit-product-voucher-links?vid='+item.id)}>   查看关联产品</button>
          </td>
        </tr>
      )
    })
    return (
      <DocumentTitle title="指定产品赠送列表">
        <div className="user-list">
            <button className="btn btn-primary pull-right" onClick={this._newHandler}>新增</button>
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th width='30%'>标题</th>
                  <th width='20%'>优惠券对应商品号</th>
                  <th width='20%'>数量</th>
                  <th width='30%'>操作</th>
                </tr>
              </thead>
              <tbody>
                {list}
              </tbody>
            </table>
        </div>
      </DocumentTitle>
    )
  }
}

export default ProductVoucherList
