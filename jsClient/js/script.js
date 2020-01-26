var ws;
var mtId = 2;
function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
}

function connect() {
	ws = new WebSocket('ws://192.168.0.105:1488/messages');
	ws.onmessage = function(data) {
		helloWorld(data.data);
	}
	setConnected(true);
}

function disconnect() {
	if (ws != null) {
		ws.close();
	}
	setConnected(false);
}

function sendMessage() {
	var message = JSON.stringify({
		'method': 'POST',
		'receiverId': $('#message-receiver').val(),
		'text': $('#message-input').val(),
		'time': 1286705410
	})
	ws.send(message);
}

$(function() {
	$("#connect-btn").click(function() {
		connect();
	});
	$("#disconnect-btn").click(function() {
		disconnect();
	});
	$("#send-message-btn").click(function() {
		sendMessage();
	});
});