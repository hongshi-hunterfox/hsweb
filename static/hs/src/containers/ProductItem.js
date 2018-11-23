require("./product-item.css")
var React = require("react")

class ProductItem extends React.Component {
	render() {
		return (
			<div className="product-item">
				<div className='product-item-img'
					onClick={this._location.bind(
						this,
						"/detail/" + this.props.productId
					)}
				>
					<img
						src={this.props.image}
						className="image"
						alt=""
					/>
				</div>

				{this.props.tag
					? <span className="product-item-tag">
							{this.props.tag}
						</span>
					: null}

				<div className="product-item-info">
					<div className="product-item-title">
						{this.props.title}
					</div>
					<div className="product-item-price">
						<div className='left'>¥{this.props.price}</div>
						<div className='right' onClick={this.props._buyClick.bind(this,this.props.productId)}>buy</div>
					</div>
				</div>
			</div>
		)
	}
	_location = url => {
		window.location = url
	}
}

export default ProductItem
