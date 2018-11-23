import React from 'react'
import DocumentTitle from 'react-document-title'
import './home.css'
class Home extends React.Component {
  render() {
    return (
      <DocumentTitle title="首页">
        <div className="home">欢迎使用洪石商城后台管理系统</div>
      </DocumentTitle>
      )
  }
}

export default Home