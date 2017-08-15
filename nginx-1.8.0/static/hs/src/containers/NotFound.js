import React from 'react'
import DocumentTitle from 'react-document-title'

class NotFound extends React.Component {
  render() {
    return (
      <DocumentTitle title="404">
        <div style={{
          marginTop: '50px',
          textAlign: 'center',
          background: '#F1C40F',
          border: '5px solid #2C3E50',
          padding: '15px',
          width: '80%',
          marginLeft: 'auto',
          marginRight: 'auto',
          marginBottom: '50px',
          fontSize: '24px',
          borderRadius: '40px',
          position: 'relative'
        }}>
          <span style={{
            position: 'absolute',
            display: 'block',
            width: '10px',
            height: '30px',
            background: '#2C3E50',
            top: '100%',
            left: '50px'
          }} />
          <span style={{
            position: 'absolute',
            display: 'block',
            width: '10px',
            height: '30px',
            background: '#2C3E50',
            top: '100%',
            right: '50px'
          }} />
          <span>正在建设中...</span>
        </div>
      </DocumentTitle>
      )
  }
}

export default NotFound