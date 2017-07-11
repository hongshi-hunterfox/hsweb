import React from 'react'
import './store-bar.css'

class StoreBar extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      storeName: '请选择店铺'
    }
  }
  componentDidMount() {
    var storeName = localStorage.getItem('storeName')
    if (storeName) {
      this.setState({
        storeName: storeName
      })
    }

    // storeId detect

    var p = window.location.pathname.split('/')[1]
    var storeId = localStorage.getItem('storeId')
    if (p === '' || p === 'detail' || p === 'cart') {
      if (!storeId) {
        localStorage.setItem('store_id_prev_href', window.location.href)
        window.location = '/storeList'
      }
    }
  }
  render() {
    return (
      <div className="store-bar">
        <img
          className="store-bar-icon"
          src={'/images/brand.jpg'}
          alt=""
          width="20"
          height="20"
        />
        <span className="store-bar-name">{this.state.storeName}</span>
        <a
          href="/storeList"
          onClick={e => {
            e.preventDefault()
            localStorage.setItem('store_id_prev_href', window.location.href)
            window.location = '/storeList'
          }}
        >
          <span className="store-bar-change">[切换]</span>
        </a>
      </div>
    )
  }
}

export default StoreBar
