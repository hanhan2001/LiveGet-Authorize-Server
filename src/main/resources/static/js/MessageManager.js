const knownJsonMessage = new Map();

function interpreterJsonMessage(message) {
	let json;
	try {
		json = JSON.parse(message);
	} catch {
		return;
	}

	let code = json.code;
	let jsonExecutor;
	if ((jsonExecutor = knownJsonMessage.get(code)) == null)
		return;

	jsonExecutor.execute(message);
}

function registerJSONMessage(code, jsonExecutor) {
	knownJsonMessage.set(code, jsonExecutor);
}