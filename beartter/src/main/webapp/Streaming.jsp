<%@page language="java" contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.0/jquery-ui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<h2>
		Twitter タイムライン<BR />
		WebSocket サンプル・アプリケーション!!
	</h2>
	<TABLE BORDER="1" ID="TBL">
	</TABLE>
</body>
<script type="text/javascript">
	var wsUri = "ws://127.0.0.1:8082/beartter/streamdemo";
	var websocket = new WebSocket(wsUri);
	websocket.onopen = function(evt) {
		onOpen(evt)
	};
	websocket.onmessage = function(evt) {
		onMessage(evt)
	};
	websocket.onerror = function(evt) {
		onError(evt)
	};

	var numberOfMessage;

	function init() {
		numberOfMessage = 0;
	}

	function onOpen(evt) {
		;
	}

	function onMessage(evt) {
		writeToScreen(evt.data);
		numberOfMessage++;
	}

	function onError(evt) {
		writeToScreen('<span style="color: red;">ERROR</span>' + evt.data)
	}

	function writeToScreen(message) {
		var table = document.getElementById("TBL");
		var row = table.insertRow(0);
		var cell1 = row.insertCell(0);

		var textNode = document.createTextNode(message);

		var z = numberOfMessage % 2;

		if (z == 1) {
			cell1.style.backgroundColor = "#ADD8E6";
		}

		cell1.appendChild(textNode);
	}

	window.addEventListener("load", init, false);
</script>
</html>