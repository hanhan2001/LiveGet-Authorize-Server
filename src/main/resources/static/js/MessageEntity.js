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
	}
}