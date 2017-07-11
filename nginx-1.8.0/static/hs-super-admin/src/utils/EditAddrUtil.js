var request = require('superagent');

module.exports = {
	getData: function(query, cb) {
		request
			.get('/uclee-user-web/editAddr')
			.query(query)
			.end(function(err, res) {
				if (err) {
					return err;
				}
				cb(JSON.parse(res.text));
			});
	},
	getCity :function(query,cb){
		request
			.get('/uclee-user-web/getCitiesByStr')
			.query(query)
			.end(function(err, res) {
				if (err) {
					return err;
				}
				cb(JSON.parse(res.text));
				console.log(res.text);
			});
	},
	getRegion :function(query,cb){
		request
			.get('/uclee-user-web/getRegionsByStr')
			.query(query)
			.end(function(err, res) {
				if (err) {
					return err;
				}
				cb(JSON.parse(res.text));
				console.log(res.text);
			});
	},
	addAddr : function(data, cb) {
		request
			.post('/uclee-user-web/editAddrHandler')
			.send(data)
			.end(function(err, res) {
				if (err) {
					return err;
				}
				cb(JSON.parse(res.text));
			});
	},
};