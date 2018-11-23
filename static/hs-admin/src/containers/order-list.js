import React from 'react'
import DocumentTitle from 'react-document-title'
import './order-list.css'
import req from 'superagent'
import { Link } from 'react-router'

class OrderList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      orders:[{"id":189480,"orderCode":"170531-00002","date":"2017-05-31","totalAmount":100.0,"accounts":100.0,"isEnd":false,"outerOrderCode":"123456789","orderItems":[{"id":231701,"orderId":189480,"code":"11001","price":10.0,"discountRate":100.0,"count":1,"totalAmount":10.0,"sumDiscount":0.0,"hongShiGoods":{"id":5322,"code":"11001","title":"蔓越莓乳酪球","unit":"个","image":"http://m.uclee.com/images/hongshi/11-2.jpg","spec":"90g"}},{"id":231703,"orderId":189480,"code":"11001","price":10.0,"discountRate":100.0,"count":1,"totalAmount":10.0,"sumDiscount":0.0,"hongShiGoods":{"id":5322,"code":"11001","title":"蔓越莓乳酪球","unit":"个","image":"http://m.uclee.com/images/hongshi/11-2.jpg","spec":"90g"}}]},{"id":189495,"orderCode":"170604-00001","date":"2017-06-04","totalAmount":1000.0,"accounts":1000.0,"isEnd":false,"outerOrderCode":"1231561","orderItems":[]},{"id":189496,"orderCode":"170605-00001","date":"2017-06-05","totalAmount":41.0,"accounts":41.0,"isEnd":false,"outerOrderCode":"14966410295598925","orderItems":[{"id":231706,"orderId":189496,"code":"11002","price":29.0,"discountRate":100.0,"count":1,"totalAmount":29.0,"sumDiscount":0.0,"hongShiGoods":{"id":5323,"code":"11002","title":"冥想法国","unit":"个","image":"http://120.25.193.220/group1/M00/31/AC/eBnB3Fk0G4yAXY2lAAAgJ6wLrXc73.file","spec":"220g"}}]}]
    }
  }

  componentDidMount() {
    req.get('/uclee-backend-web/productList').end((err, res) => {
      if (err) {
        return err
      }
      var data = JSON.parse(res.text)
      this.setState({
        products: data.products
      })
    })
  }
  
  render() {
    var list = this.state.products.map((item, index) => {
      return (
        <tr key={index}>
          <td><img src={item.image} alt="" width="100"/></td>
          <td>{item.title}</td>
          <td>
            <Link to={'/product/' + item.id} className="btn btn-primary">
            编辑
            </Link>
          </td>
        </tr>
      )
    })
    return (
      <DocumentTitle title="订单列表">
        <div className="order-list">
            <table className="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>产品图片</th>
                  <th>产品标题</th>
                  <th>
                    <a href=""></a>编辑</th>
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

export default OrderList
