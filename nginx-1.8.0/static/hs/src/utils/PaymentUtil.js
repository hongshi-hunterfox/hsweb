var request = require('superagent');

module.exports = {
	getData: function(query, cb) {
		request
			.get('/uclee-user-web/payment')
			.query(query)
			.end(function(err, res) {
				if (err) {
					return err;
				}

				cb(JSON.parse(res.text));
			});
	},
	submitHandler: function(data, cb) {
		request
			.post('/uclee-user-web/seller/paymentHandler')
			.send(data)
			.end(function(err, res) {
				if (err) {
					return err;
				}
				cb(JSON.parse(res.text));
			});
	},
};