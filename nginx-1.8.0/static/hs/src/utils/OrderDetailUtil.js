/**
 * Created by jiangpan on 2018/3/7.
 * never used
 */
var request = require('superagent');
module.exports = {
    getData: function(query, cb) {
        request
            .get('/uclee-user-web/getMyOrderDetail')
            .query(query)
            .end(function(err, res) {
                if (err) {
                    return err;
                }

                cb(JSON.parse(res.text));
            });
    }
};