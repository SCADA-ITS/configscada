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
        if(items){
            if(!checkUserPermission(RULE.EDIT_USER)){
                items.hideItem("user");
            }
            if(!checkUserPermission(RULE.EDIT_ROLE)){
                items.hideItem("role");
            }
        }        
        return ui
    }
}

export class CustomUsersForm extends userManager.views["users/form"]{
    Delete() {
        let getUser = webix.proxy("GraphQL",
        `query  getUser($userId: String!){
            getUser(userId: $userId) {
              id
              name
              preset
            }
        }`);
        
        getUser.load({
            "userId": "User:"+this.ID.toString()
            
        }).then(dataReturn => {
           
            if (dataReturn.preset){
                webix.message({type:"error", text:$.i18n("usermanager.user.edit.delete.message")});
            }else{
                var _this = this;
                var _ = this.app.getService("locale")._;
                webix
                    .confirm({
                    title: _("Delete"),
                    ok: _("Delete"),
                    cancel: _("Cancel"),
                    text: _("Are you sure to delete this user?"),
                })
                    .then(function () {
                    _this.app.getService("operations").deleteUser(_this.ID);
                    _this.app.show("/top/users/_hidden");
                });

            }
         
        })
        
    }
}
