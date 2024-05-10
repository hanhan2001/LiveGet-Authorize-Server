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
		websocket.send("{\"type\": \"user_info\", \"user\": {\"token\": \"" + localStorage.token + "\"}}");
	}
}

class NeedReLoginMessage extends JSONExecutor {
	execute(message) {
		openClassification("login");
		return false;
	}
}

/**
 * 验证通过
 * */
class VerifySuccessMessage extends JSONExecutor {
	execute(message) {
		return true;
	}
}

/**
 * 验证出错
 * */
class VerifyErrorMessage extends JSONExecutor {
	execute(message) {
		return false;
	}
}

class SelfInfoMessage extends JSONExecutor {
    execute(message) {
        document.querySelector(".content .display .display_box_preview .user_info .user .name").innerText = JSON.parse(message).name;
    }
}