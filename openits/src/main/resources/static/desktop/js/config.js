const appConfig = {

	restUrl: '../api/v1/',
	stompUrl: 'ws://192.168.244.12:61614',
}

const SESSION_STORAGE_USER_DATA = "user.data";
const STOMP_DATA_SOURCE_TYPE = 2;

const MODULE_TYPE_SERVICE = 6;

const admin_usr = {
	user: "admin",
	password: "admin",
	userSession: "UserSession:69103CE63359D3B1BB16C1DB1B794C1B"
}

const moduleType = {
	window: {
		id: 1,
		params: {
			hasMinButton: 1,
			hasMaxButton: 2,
			isMaximized: 3,
			height: 4,
			width: 5,
			x: 6,
			y: 7,
			allowMultipleInstances: 8,
			image: 9,
			icon: 10,
			color: 11,
			label: 12
		}
	}
}

const window_params_default = {
	hasMinButton: true,
	hasMaxButton: true,
	isMaximized: false,
	height: 400,
	width: 800,
	allowMultipleInstances: false,
	image: 'img/module/module_default.png',
	icon: 'img/module/module_efault.png',
	color: '#e81123',
	label: 'LBL_MODULE_DEFAULT'
}