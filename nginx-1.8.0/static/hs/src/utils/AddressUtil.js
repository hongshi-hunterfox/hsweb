var request = require('superagent');

module.exports = {
	getData: function(query, cb){
		query.pageSize = 10;
		request
			.get('/uclee-user-web/addrList')
			.query(query)
			.end(function(err, res) {
				if (err) {
					return err;
				}
				cb(JSON.parse(res.text));
			});
	},
	delAddr : function(data,cb){
		request
			.post('/uclee-user-web/delAddrHandler')
			.send(data)
			.end(function(err, res) {
				if (err) {
					return err;
				}
				cb(JSON.parse(res.text));
			});
	},
	setDefault : function(data,cb){
		request
			.post('/uclee-user-web/setDefaultAddr')
			.send(data)
			.end(function(err, res) {
				if (err) {
					return err;
				}
				cb(JSON.parse(res.text));
			});
	}

};