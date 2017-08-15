import React from 'react'
import DocumentTitle from 'react-document-title'

class Login extends React.Component {
  render() {
    return (
      <DocumentTitle title="登录">
        <h1>{'<Login /> without menu'}</h1>
      </DocumentTitle>
      )
  }
}

export default Login