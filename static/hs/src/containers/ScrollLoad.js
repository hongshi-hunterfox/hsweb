import React from 'react'

class ScrollLoad extends React.Component{
	propTypes: {
		containerHeight: React.PropTypes.number,
		loading: React.PropTypes.bool,
		onReachBottom: React.PropTypes.func
	}

	getDefaultProps() {
		return {
			containerHeight: 100,
			loading: false
		};
	}
	
	render() {
		return (
			<div id="scroll-load" className="scroll-load" ref="wrap" onScroll={this._handleScroll} style={{
				height: this.props.containerHeight + 'px',
				overflowY: 'scroll',
				overflowX: 'hidden',
				WebkitOverflowScrolling: 'touch'
			}}>
				<div className="scroll-load-inner clearfix" ref="inner">
					{this.props.children}
				</div>
			</div>
			);
	}

	_handleScroll = (e) => {
		if (e.target.scrollTop >= (this.refs.inner.clientHeight - this.props.containerHeight) && !this.props.loading) {
			if (this.props.onReachBottom && typeof this.props.onReachBottom === 'function') {	
				this.props.onReachBottom();
			}
		}
	}
};

export default ScrollLoad