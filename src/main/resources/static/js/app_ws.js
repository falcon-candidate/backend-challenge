var ws;

function connect(endPoint, tableName) {
	ws = new WebSocket(endPoint);
	ws.onmessage = function(data) {
		addEntry(tableName, data.data);
	}
	setConnected(true);
}

function addEntry(tableName, entry) {
  $(tableName).append("<tr><td>" + entry + "</td></tr>");
}

