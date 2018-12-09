var ws;

// connects web socket client
function connect(endPoint, tableName) {
	ws = new WebSocket("ws://" + window.location.host + "/" + endPoint);
	ws.onmessage = function(data) {
		addEntry(tableName, data.data);
	}
	setConnected(true);
}

// callback to add received palindrome query to table
function addEntry(tableName, entry) {
  $(tableName).append("<tr><td>" + entry + "</td></tr>");
}

