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

   	let code = json.code;
   	if (code == 101) interpreterFunction(json.function);
   	if (code == 202) changeMusic(json.id, json.url);
}

websocket.onclose = function(e) {
}

websocket.onerror = function(e) {
}

function interpreterFunction(f) {
	// if (f == "playMusic") playMusic();
//	if (f == "changeMusic") changeMusic(f.id, f.url);
}