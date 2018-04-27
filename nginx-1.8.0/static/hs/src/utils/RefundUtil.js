/**
 * Created by jiang on 2018/4/3.
 */
var request = require('superagent');

module.exports = {
    getData: function(query, cb) {
        request
            .get('/uclee-user-web/refund')
            .query(query)
            .end(function(err, res) {
                if (err) {
                    return err;
                }

                cb(JSON.parse(res.text));
            });
    },


    //这个是以前直接退款写得，目前因为流程变化，暂时注释掉
    /*
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
    */
    //客户点击申请退款请求
    submitApplyHandler: function(data, cb) {
        request
            .post('/uclee-user-web/seller/refundApplyHandler')
            .send(data)
            .end(function(err, res) {
                if (err) {
                    return err;
                }
                cb(JSON.parse(res.text));
            });
    },


};