import React from 'react'
import DocumentTitle from 'react-document-title'
import './home.css'
class Home extends React.Component {
  render() {
    return (
      <DocumentTitle title="首页">
        <div className="home">欢迎来到后台管理页面</div>
      </DocumentTitle>
      )
  }
}

export default Home