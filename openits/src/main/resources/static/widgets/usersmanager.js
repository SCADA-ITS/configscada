import UserManagerInt from "../classes/UserManagerInterface.js";

export let usersmanager= {
	id:"usersmanagerContainer",
    body: {
        view:"usermanager",
        override: new Map([[userManager.services.Backend, UserManagerInt]]),
    }
};