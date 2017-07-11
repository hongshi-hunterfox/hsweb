var React = require('react');
var request = require('superagent');

import React from 'react'
import req from 'superagent'

class WXSignIn extends React.Component {
	componentDidMount() {
		req
			.get('/duobao-user-web/weiXinCallback')
			.query({
				code: this.props.location.query.code
			})
			.end((err, res) => {
				if (err) {
					return err
				}

				alert(res.text)
			})
	}

	render() {
		return (
			<h1 className="text-center">处理中，请稍候...</h1>
			)
	}
}


export default WXSignIn