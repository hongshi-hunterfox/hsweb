var request = require('superagent');

module.exports = {
    FillTable: function(data, cb){
        var chtml = '<tr>';
        var title = data[0];
    for (var key in title){
        chtml = chtml + '<th>' +key + '</th>';
    };
        var	chtml = chtml + '</tr>';

	for(var i = 0; i < data.length; i++){
        chtml += '<tr>';
        for (var value in data[i]){
            chtml += '<td>' + data[i][value] + '</td>';
        };
        chtml += '</tr>';
    };

        var table = document.getElementById('myview');
        table.innerHTML = chtml;
	}
}