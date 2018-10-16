import React, { Component } from 'react';
import Menu from './components/Menu'

class App extends Component {
  render() {
    return (
      <div className="app container">
        <div className="col-md-12">
          <a href="/"><img src="./images/top.jpg"/></a>
        </div>
        <div className="col-md-2">
          <Menu />
        </div>
        <div className="col-md-10">
          <div style={{
            padding: 15
          }}>
          {
            this.props.children
          }
          </div>
        </div>
      </div>
    );
  }
}

export default App;
