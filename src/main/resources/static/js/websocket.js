// websocket
let websocket = new WebSocket("ws://127.0.0.1:22333");

websocket.onopen = function(e) {
}

websocket.onmessage = function(e) {
	let json;
   	try {
   		json = JSON.parse(e.data);
  	} catch (e) {
    	return;
   	}

   	interpreterJsonMessage(e.data);
}

websocket.onclose = function(e) {
}

websocket.onerror = function(e) {
}