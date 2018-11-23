var request = require('superagent');

module.exports = {
	home: function(cb) {
		request
			.get('/uclee-user-web/home')
			.end(function(err, res) {
				if (err) {
					return err;
				}

				cb(JSON.parse(res.text));
			});
	}	
};