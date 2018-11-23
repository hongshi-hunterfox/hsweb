import React from 'react'

class ErrorMessage extends React.Component{
	render() {

		if (!this.props.error || this.props.error.trim() === '') {
			return null;
		}
		return (
			<div className="alert alert-danger">
				{this.props.error}
			</div>
			);
	}
};

export default ErrorMessage