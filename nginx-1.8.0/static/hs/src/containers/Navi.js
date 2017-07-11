import './navi.css'
var React = require('react')
var Link = require('react-router').Link
var IndexLink = require('react-router').IndexLink

class Navi extends React.Component {
	_allProductClass = () => {
		var { filterType } = this.props.query
		if (!filterType && window.location.pathname === '/all-product') {
			return ' active'
		}
		return ''
	}

	_cakeClass = () => {
		var { filterType } = this.props.query
		if (
			filterType &&
			window.location.pathname === '/all-product' &&
			filterType === 'process'
		) {
			return ' active'
		}
		return ''
	}

	render() {
		return (
			<div className="navi" id="hs-navi">
				<IndexLink to="/" className="navi-item" activeClassName="active">
					<i className="fa fa-home" />
					主页
				</IndexLink>
				<Link
					to={{
						pathname: '/all-product',
					}}
					className={'navi-item' + this._allProductClass()}
				>
					<i className="fa fa-th-large" />
					全部商品
				</Link>
				<Link
					to={{
						pathname: '/store-info',
					}}
					className={'navi-item' + this._cakeClass()}
				>
					<i className="fa fa-building" />
					公司介绍
				</Link>
				<Link
					to={{
						pathname: '/member-center'
					}}
					className="navi-item"
					activeClassName="active"
				>
					<i className="fa fa-user" />
					个人中心
				</Link>
			</div>
		)
	}
}

export default Navi
