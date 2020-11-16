import UserManagerInt from "../classes/UserManagerInterface.js";
import {CustomRoleManager, CustomUserDetails, CustomAddRoleAndUser, CustomUsersForm, CustomCredentialsView} from "../classes/UserManagerForm.js";

export let usersmanager= {
	id:"usersmanagerContainer",
    body: {
        view:"usermanager",
        id: "userManagerDelete",
        override: new Map([[userManager.services.Backend, UserManagerInt],
            [userManager.views["users/roles"], CustomRoleManager],
            [userManager.views["users/details"], CustomUserDetails],
            [userManager.views["addmenu"], CustomAddRoleAndUser],
            [userManager.views["users/form"], CustomUsersForm],
            [userManager.views["users/credentials"], CustomCredentialsView]
        ]),

    }
};
