// 主方法体
class JSONExecutor {
	execute(message) {
		throw new Error("Method 'execute' must be implemented.");
	}
}

// 成功登录
class LoginSuccessMessage extends JSONExecutor {
	execute(message) {
		localStorage.setItem("token", JSON.parse(message).token);
		sendPopup("info", "<img src='./images/success.svg' style='width: 30px; margin-top: 2px;'>登录成功", 1800);
		openClassification("preview");
	}
}