import React from 'react'
import DocumentTitle from 'react-document-title'

class NotFound extends React.Component {
  render() {
    return (
      <DocumentTitle title="404">
        <div>404 Not Found</div>
      </DocumentTitle>
      )
  }
}

export default NotFound