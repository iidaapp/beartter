<%@page language="java" contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery.js"></script>
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
	<a href="page/Top.jsp">test</a>
	<input type="button" onclick="addWs()" />
${sessionScope.beartterId}
</body>
<c:if test="${empty sessionScope.beartterId}"><c:redirect url="/" />
</c:if>
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
		if(evt.data == "null"){
			onError(evt);
		}
		
		try{
			var wsJson = $.parseJSON(evt.data);
		}catch (e){
			alert(e);
			return;
		}
		writeToScreen(wsJson);
	}

	function onError(evt) {
		writeToScreen('<span style="color: red;">ERROR</span>' + evt.data)
	}

	function writeToScreen(wsJson) {
		var table = document.getElementById("TBL");
		var row = table.insertRow(0);
		var cell1 = row.insertCell(0);

		var textNode = document.createTextNode(wsJson.text);

		var z = numberOfMessage % 2;

		if (z == 1) {
			cell1.style.backgroundColor = "#ADD8E6";
		}

		cell1.appendChild(textNode);
	}

	window.addEventListener("load", init, false);
	
	addWs(){
		
	}
</script>
</html>