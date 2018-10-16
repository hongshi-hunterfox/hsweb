var request = require('superagent');

module.exports = {
    FillTable: function(data,colsNames, cb){
    var heahHtml = ['<tr>'];

    for( var i = 0; i < colsNames.length; i++ ){
        heahHtml.push('<th>' +colsNames[i].cnName + '</th>')
    }
    heahHtml.push('</tr>');

	for(var i = 0; i < data.length; i++){
        heahHtml.push('<tr>');
        for(var j = 0; j < colsNames.length; j++ ){
            heahHtml.push('<td>' +data[i][colsNames[j].cnName] + '</td>')
        }
        heahHtml.push('</tr>');
    };

        var table = document.getElementById('myview');
        table.innerHTML = heahHtml.join('');
	}
}