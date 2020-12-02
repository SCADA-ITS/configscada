import UserManagerInt from "../classes/UserManagerInterface.js";
import {CustomRoleManager, CustomUserDetails, CustomAddRoleAndUser, CustomUsersForm, CustomCredentialsView} from "../classes/UserManagerForm.js";

import { es } from "../i18nUserManager/es.js"
import { sk } from "../i18nUserManager/sk.js"
import { en } from "../i18nUserManager/en.js"


userManager.locales.es = es;
userManager.locales.sk = sk;
userManager.locales.en = en;

export let usersmanager= {
	id:"usersmanagerContainer",
    body: {
        view:"usermanager",
        id: "userManagerModule",
        override: new Map([[userManager.services.Backend, UserManagerInt],
            [userManager.views["users/roles"], CustomRoleManager],
            [userManager.views["users/details"], CustomUserDetails],
            [userManager.views["addmenu"], CustomAddRoleAndUser],
            [userManager.views["users/form"], CustomUsersForm],
            [userManager.views["users/credentials"], CustomCredentialsView]
        ]),
        locale: {
            lang: j_language_usermanager,
            webix:{
                // switch all webix widgets to the selected locale
                es:"es-ES",
                en: "en-EN",
                sk: "sk-SK"
            }
        }

    }
};



