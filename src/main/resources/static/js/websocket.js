// websocket
let websocket = new WebSocket("ws://liveget.top:6601");

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