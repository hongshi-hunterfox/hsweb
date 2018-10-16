var request = require('superagent');
module.exports = {
    submitHandler: function(data, cb) {
        request
            .post('/uclee-user-web/seller/refundHandler')
            .send(data)
            .end(function(err, res) {
                if (err) {
                    return err;
                }
                cb(JSON.parse(res.text));
            });
    },
};