var request = require('superagent');

module.exports = {
	getData: function(query, cb) {
		request
			.get('/uclee-user-web/prePaymentForAlipay')
			.query(query)
			.end(function(err, res) {
				if (err) {
					return err;
				}

				cb(JSON.parse(res.text));
			});
	},
	getResult: function(q, cb) {
		request
			.get('/uclee-user-web/getPaymentResult')
			.query(q)
			.end(function(err, res) {
				if (err) {
					return err;
				}

				cb(JSON.parse(res.text));
			});
	},
	submitHandler: function(data, cb) {
		request
			.post('/uclee-user-web/seller/paymentAlipayHandler')
			.send(data)
			.end(function(err, res) {
				if (err) {
					return err;
				}
				cb(JSON.parse(res.text));
			});
	},
};