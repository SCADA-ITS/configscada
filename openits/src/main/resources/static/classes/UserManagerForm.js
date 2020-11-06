import * as revenga from "../lib/revenga.js";
import {RULE} from "../data/GLOBAL_PROPERTIES.js";
 

export class CustomRoleManager extends userManager.views["users/roles"] {
    init() {
        // default logic
        super.init();
        // custom logic below
        this.rolePermissions();
    }
    rolePermissions(){
        // do something on init
        let ui = super.config()
        let modify_button = ui.rows[0].rows[0].rows[0].rows[1].cols[1]

        if (! checkUserPermission(RULE.EDIT_ROLE)){
            this.$$(modify_button.localId).disable()
        }
        return ui
    }
}

export class CustomUserDetails extends userManager.views["users/details"]{
    init() {
        // default logic
        super.init();
        // custom logic below
        this.editPermissions();
    }
    editPermissions(){
        let ui = super.config();
        let button_edit = ui.rows[0].cols[2];
        if (!checkUserPermission(RULE.EDIT_USER)){
            this.$$(button_edit.localId).disable()
        }
        return ui
    }
}

export class CustomAddRoleAndUser extends userManager.views["addmenu"]{
    init() {
        // default logic
        super.init();
        // custom logic below
        this.editPermissions();
    }
    editPermissions(){
        let ui = super.config();
        let items = this.$$('addMenuItems');
        if(!checkUserPermission(RULE.EDIT_USER)){
            items.hideItem("user");
        }
        if(!checkUserPermission(RULE.EDIT_ROLE)){
            items.hideItem("role");
        }
        
        return ui
    }
}

