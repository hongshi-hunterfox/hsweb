
	function PullListToTable(data,tablaname) {
		var chtml = '<tr>';
  		var title = data[0];
  	for (var key in title)
  		{
    		chtml = chtml + '<th>' +key + '</th>';
  		};
  			chtml = chtml + '</tr>';
  
  	for (var index in data){
    		chtml = chtml + '<tr>';
    	for (var value in data[index]){
      		chtml = chtml + '<td>' + value + '</td>';
    	};
    		chtml = chtml + '</tr>';
  	};

  		var table = document.getElementById(tablaname);
  		table.innerHTML = chtml;
	}
exports.Table = PullListToTable;