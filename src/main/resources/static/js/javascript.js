let openedClassification = null;
let openedDisplayBox = null;
let classifications = new Map();

// 音乐播放器
let music;
let musicID;

// 页面初始化
window.onload = function(event) {
	// 使用循环遍历检测websocket是否建立连接
	let interval = setInterval(() => {
		if (websocket.readyState != 1)
			return;

		websocket.send(new WindowLoad().toString());
		clearInterval(interval);
	}, 100);

    // 页面加载事件
	music = document.getElementById("music");
	music.onended = function (event) {
		websocket.send(new MusicEndedMessage(musicID, music.src).toString());
	}

	// 监听登录
	const form = document.querySelector(".content .display .display_box_login_login .login .box .form");
    form.onsubmit = function (e) {
        e.preventDefault();
        fetch(e.target.action, { method: e.target.method, body: new FormData(form) })
            .then(res => res.text())
            .then(res => {
                console.log('后端返回内容', res);
                alert('后端返回内容: ' + res)
            })
            .catch(() => {
                console.error('请求出错');
                alert('请求出错')
            });
    };

	// 分类 登录
	let box_login = new Map();
	let box_login_login = new DisplayBox(".content .display .display_box_login_login");
	box_login_login.setLine(".content .display .display_hrefs .display_hrefs_login .line");
	box_login_login.setButton(".content .display .display_hrefs .display_hrefs_login .display_box_login_login_button");
	let box_login_register = new DisplayBox(".content .display .display_box_login_register");
	box_login_register.setLine(".content .display .display_hrefs .display_hrefs_login .line");
	box_login_register.setButton(".content .display .display_hrefs .display_hrefs_login .display_box_login_register_button");
	box_login.set("login", box_login_login);
	box_login.set("register", box_login_register);
	let classification_login = new Classification(".content .display .display_hrefs .display_hrefs_login", box_login, "login");
	classification_login.setLine(".content .display .display_hrefs .display_hrefs_login .line");

	// 分类 概览
	// 界面
	let box_preview = new Map();
	let box_preview_default = new DisplayBox(".content .display .display_box_preview");
	box_preview_default.setButton(".content .display .display_hrefs .display_hrefs_preview .display_box_preview_button");
	box_preview_default.setLine(".content .display .display_hrefs .display_hrefs_preview .line");
	box_preview.set("preview", box_preview_default);
	// 分类
	let classification_preview = new Classification(".content .display .display_hrefs .display_hrefs_preview", box_preview, "preview");
	classification_preview.setLine(".content .display .display_hrefs .display_hrefs_preview .line");


	// 分类 授权码
	// 界面
	let box_code = new Map();
	let box_code_default = new DisplayBox(".content .display .display_box_code");
	box_code_default.setLine(".content .display .display_hrefs .display_hrefs_code .line");
	box_code_default.setButton(".content .display .display_hrefs .display_hrefs_code .display_box_code_button");
	box_code.set("code", box_code_default);
	// 分类
	let classification_code = new Classification(".content .display .display_hrefs .display_hrefs_code", box_code, "code");
	classification_code.setLine(".content .display .display_hrefs .display_hrefs_code .line");


	// 分类 服务
	// 界面
	let box_shop = new Map();
	let box_shop_code = new DisplayBox(".content .display .display_box_shop_code");
	box_shop_code.setLine(".content .display .display_hrefs .display_hrefs_shop .line");
	box_shop_code.setButton(".content .display .display_hrefs .display_hrefs_shop .display_box_shop_code_button");
	let box_shop_subsidiary = new DisplayBox(".content .display .display_box_shop_subsidiary");
	box_shop_subsidiary.setLine(".content .display .display_hrefs .display_hrefs_shop .line");
	box_shop_subsidiary.setButton(".content .display .display_hrefs .display_hrefs_shop .display_box_shop_subsidiary_button");
	box_shop.set("code", box_shop_code)
	box_shop.set("subsidiary", box_shop_subsidiary);
	// 分类
	let classification_shop = new Classification(".content .display .display_hrefs .display_hrefs_shop", box_shop, "code");
	classification_shop.setLine(".content .display .display_hrefs .display_hrefs_shop .line");


	// 分类 设置
	// 界面
	let box_set = new Map();
	let box_set_default = new DisplayBox(".content .display .display_box_set");
	box_set_default.setLine(".content .display .display_hrefs .display_hrefs_set .line");
	box_set_default.setButton(".content .display .display_hrefs .display_hrefs_set .display_box_set_button");
	box_set.set("set", box_set_default)
	// 分类
	let classification_set = new Classification(".content .display .display_hrefs .display_hrefs_set", box_set, "set");
	classification_set.setLine(".content .display .display_hrefs .display_hrefs_set .line");

	// 存入分类
	classifications.set("login", classification_login);
	classifications.set("preview", classification_preview);
	classifications.set("code", classification_code);
	classifications.set("shop", classification_shop);
	classifications.set("set", classification_set);

	// 默认打开
	// openClassification("preview");
	// openDisplayBox("preview", "preview");
	// openClassification("shop");
	// openDisplayBox("shop", "subsidiary");
	openClassification("login")
}

/**
 * 打开 界面
 * 
 * @param classification 分类
 * @param name 界面名称
 * */
function openDisplayBox(classification, name) {
	let classificationEntity = classifications.get(classification);
	let line = classificationEntity.getLine();
	let display = classificationEntity.getDisplayBox(name);

	display.open(display.getLine());
	openedDisplayBox = display;
}

function openClassification(classification) {
	if (openedClassification == classifications.get(classification))
		return;

	if (openedClassification != null)
		openedClassification.close();

	let classificationEntity = classifications.get(classification);
	classificationEntity.open();

	openedClassification = classificationEntity;
}

class DisplayBox {
	/**
	 * 构造 DisplayBox
	 * 
	 * @param displayBox dom实体
	 * */
	constructor(displayBox) {
		this.displayBox = document.querySelector(displayBox);
	}

	/**
	 * 打开 display
	 * */
	open() {
		if (this.displayBox.classList.contains("open"))
			return;

		if (openedDisplayBox != null)
			openedDisplayBox.close();

		let button = this.getButton();

		this.line.style.left = button.offsetLeft + "px";
		this.line.style.width = button.offsetWidth + "px";

		openedDisplayBox = this;
		this.displayBox.classList.add("open");
	}

	/**
	 * 关闭 displayBox
	 * */
	close() {
		if (!this.displayBox.classList.contains("open"))
			return;

		this.displayBox.classList.remove("open");
	}

	setButton(button) {
		this.button = document.querySelector(button);
	}

	getButton() {
		return this.button;
	}

	setLine(line) {
		this.line = document.querySelector(line);
	}

	getLine() {
		return this.line;
	}
}

class Classification {
	/**
	 * 构造 Classification
	 * 
	 * @param hrefs buttons
	 * @param displayBoxMap display Map
	 * @param defaultDisplayBoxName 默认displaybox
	 * */
	constructor(hrefs, displayBoxMap, defaultDisplayBoxName) {
		this.hrefs = document.querySelector(hrefs);
		this.displayBoxMap = displayBoxMap;
		this.defaultDisplayBoxName = defaultDisplayBoxName;
		this.defaultDisplayBox = displayBoxMap.get(defaultDisplayBoxName);
	}

	/**
	 * 打开 Classification
	 * */
	open() {
		if (openedClassification != null) {
			openedClassification.getHref().classList.remove("open");
			openedDisplayBox.close();
		}

		this.getHref().classList.add("open");
		this.defaultDisplayBox.open();
	}

	close() {
		this.getHref().classList.remove("open");

		openedDisplayBox.close();
	}

	getDisplayBox(displayBox) {
		return this.displayBoxMap.get(displayBox);
	}

	getDefaultDisplayBoxName() {
		return this.defaultDisplayBoxName;
	}

	setLine(line) {
		this.line = document.querySelector(line);
	}

	getLine() {
		return this.line;
	}

	getHref() {
		return this.hrefs;
	}
}

/**
 * 根据值移除 ArrayList 中的元素
 * 
 * @param list 列表
 * @param value 值
 * */
function removeArrayListValue(list, value) {
	if (!users.includes(id))
		return;

	for (let i = 0; i < list.length; i++) {
		if (list[i] != value)
			continue;

		list.splice(i, 1);
	}
}

function playMusic() {
	let object = document.querySelector(".music .sound");
	object.setAttribute("onclick", "stopMusic()");
	object.setAttribute("title", "静音");
	object.style.backgroundImage = "url(./images/mute.svg)";
	music.play();
}

function stopMusic() {
	let object = document.querySelector(".music .sound");
	object.setAttribute("onclick", "playMusic()");
	object.setAttribute("title", "播放");
	object.style.backgroundImage = "url(./images/sound.svg)";
	music.pause();
}

function changeMusic(id, url) {
    musicID = id;
	music.src = url;
}