import React from 'react'
import DocumentTitle from 'react-document-title'
import './store-info.css'
import req from 'superagent'
import Navi from "./Navi"
const DetailRich = (props) => {
  return (
    <div className="detail-rich">
      <div className="detail-rich-content" dangerouslySetInnerHTML={{
        __html: props.description
      }}/>
    </div>
    )
}

class StoreInfo extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      images: [],
      description: null,
      specifications: [],
      title: null,

      loading: true,
      showPick: false,
      currentSpecValudId: null,
      currentAmount: 1,
      pickType: 'add_to_cart' // 'add_to_cart' || 'buy_now'
    }

    this.specPriceMap = {}
    this.specValueMap = {}
    this.specPrePriceMap = {}
    this.specPreValueMap = {}
    this.minPrice = 0
    this.maxPrice = 0
    this.preMinPrice = 0
    this.preMaxPrice = 0
  }

  componentDidMount() {
    req
      .get('/uclee-backend-web/storeInfo')
      .end((err, res) => {
        if (err) {
          return err
        }

        this.setState({
          description:res.body.text
        })
      })
  }

  render() {

    return (
      <DocumentTitle title="公司介绍">
          <div className="store">
            <DetailRich description={this.state.description}/>
            <Navi query={this.props.location.query}/>
          </div>
      </DocumentTitle>
      )
  }
}

export default StoreInfo