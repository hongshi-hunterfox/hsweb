require('./product-group.css');
var React = require('react');
var Link = require('react-router').Link;
import ProductItem from './ProductItem'

class ProductGroup extends React.Component{
	constructor(props) {
	    super(props)
	    this.state = {

	    }
	  }
	render() {
		var items = this.props.products.map(function(item, index) {
			return <ProductItem key={index} {...item} _buyClick={this.props._buyClick}/>
		});

		return (
			<div className={'product-group' + (this.props.displayType === 'horizontal' ? ' product-group-hor': '')}>
				<div className="product-group-header">
					{this.props.groupName}
					<Link to='/all-product' className="product-group-header-link">
						更多
					</Link>

					
				</div>
				<div className="product-group-body">
					{items}
				</div>
			</div>
		);
	}
};

export default ProductGroup