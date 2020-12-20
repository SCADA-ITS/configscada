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

        if (!checkUserPermission(RULE.EDIT_ROLE)){
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
            if(this.$$(button_edit.localId)){
                this.$$(button_edit.localId).disable();
            }
        }
        return ui
    }

    GetDetailsHTML(user, roles, rules) {
        var userDetails = "";
        userDetails += this.InfoTemplate(user.email, this._("Email"), "umi-email");
        userDetails += this.GetListsHTML(user, roles, rules);
        return userDetails;
    };
}

export class CustomAddRoleAndUser extends userManager.views["addmenu"]{
    config() {
        let ui = super.config();
        ui.body.data.pop();
        return ui;
    }
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
    init() {
        // default logic
        super.init();
        // custom logic below
        this.addMultisessionButton();
        this.removeLastLoginDate()
    }
    config(){
        function userCss(name) {
            return " webix_um_avatar_" + ((name.charCodeAt(1) + (name.length % 10)) % 10);
        }
        function userAvatar(user, cssClass) {
            var css = typeof cssClass == "string" ? cssClass : "webix_um_member_avatar";
            if (user.avatar)
                return "<img class=\"" + css + "\" src=\"" + user.avatar + "\"/>";
            return "<div class='" + css + " " + (user.name ? userCss(user.name) : "webix_um_no_name") + "'>" + (user.name ? user.name.charAt(0).toUpperCase() : "") + "</div>";
        }
        var _this = this;
            var _ = this.app.getService("locale")._;
            var buttons = {
                view: "form",
                margin: 10,
                cols: [
                    {},
                    {
                        width: 120,
                        value: _("Delete"),
                        view: "button",
                        click: function () { return _this.Delete(); },
                    },
                    {
                        width: 120,
                        value: _("Save"),
                        view: "button",
                        css: "webix_primary",
                        click: function () { return _this.Save(); },
                    },
                ],
            };
            var info = {
                type: "clean",
                rows: [
                    buttons,
                    {
                        view: "form",
                        localId: "form",
                        margin: 20,
                        css: "webix_um_edit_form",
                        scroll: "auto",
                        elementsConfig: {
                            labelAlign: "right",
                            labelWidth: 130,
                        },
                        rows: [
                            {
                                cols: [
                                    {
                                        view: "text",
                                        label: _("Full Name"),
                                        localId: "focus",
                                        name: "name",
                                        gravity: 2,
                                        maxWidth: 550,
                                        minWidth: 420,
                                    },
                                    {},
                                ],
                            },
                            {
                                cols: [
                                    {
                                        view: "text",
                                        label: _("Email"),
                                        name: "email",
                                        gravity: 2,
                                        maxWidth: 550,
                                        minWidth: 420,
                                    },
                                    {},
                                ],
                            },
                            {
                                cols: [
                                    {
                                        view: "forminput",
                                        label: _("Avatar"),
                                        body: {
                                            localId: "avatar",
                                            css: "webix_um_form_avatar",
                                            width: 95,
                                            height: 95,
                                            borderless: true,
                                            template: function (user) {
                                                return "" + userAvatar(user, "webix_um_avatarbox");
                                            },
                                        },
                                    },
                                    {
                                        rows: [
                                            {},
                                            {
                                                view: "uploader",
                                                localId: "avatarUpl",
                                                accept: "image/*",
                                                autowidth: true,
                                                autosend: true,
                                                label: _("Upload new photo"),
                                                type: "icon",
                                                icon: "umi-upload",
                                                css: "webix_transparent webix_um_uploader",
                                                on: {
                                                    onAfterFileAdd: function (fileData) { return _this.UpdateAvatar(fileData); },
                                                    onUploadComplete: function (response) {
                                                        return _this.UpdateAvatar(response);
                                                    },
                                                },
                                            },
                                        ],
                                    },
                                    {},
                                ],
                            },
                            {
                                view: "switch",
                                name: "status",
                                localId: "status",
                                label: _("Status"),
                                css: "webix_um_status_switch",
                                on: {
                                    onChange: function (value) { return _this.UpdateBadge(value); },
                                },
                            },
                            
                            {
                                visibleBatch: "def",
                                localId: "credentials",
                                css: "webix_um_credentials",
                                cols: [
                                    {
                                        view: "label",
                                        label: _("Credentials"),
                                        width: 130,
                                        css: "webix_um_form_label",
                                    },
                                    {
                                        batch: "def",
                                    },
                                    {
                                        batch: "no-credentials",
                                        cols: [
                                            {
                                                view: "button",
                                                label: _("Add credentials"),
                                                width: 160,
                                                click: function () { return _this.AddCredentials(); },
                                            },
                                            {},
                                        ],
                                    },
                                    {
                                        batch: "credentials",
                                        margin: 5,
                                        cols: [
                                            {
                                                view: "button",
                                                css: "webix_transparent",
                                                label: _("Reset password"),
                                                width: 140,
                                                click: function () { return _this.ResetPassword(); },
                                            },
                                            {
                                                view: "button",
                                                css: "webix_transparent",
                                                label: _("Delete credentials"),
                                                width: 150,
                                                click: function () { return _this.DeleteCredentials(); },
                                            },
                                            {},
                                        ],
                                    },
                                ],
                            },
                            {
                                cols: [
                                    {
                                        view: "textarea",
                                        name: "details",
                                        label: _("Details"),
                                        height: 128,
                                        gravity: 2,
                                        maxWidth: 550,
                                        minWidth: 420,
                                    },
                                    {},
                                ],
                            },
                            {},
                        ],
                    },
                ],
            };
            return info;
        
    }
   
    statusBadge(value, _) {
        var status = value ? "Active" : "Not active";
        var cssStatusName = value ? "active" : "not_active";
        var html = "\n\t\t<div class=\"webix_um_badge_container\">\n\t\t\t<span class=\"webix_um_status_badge webix_um_" + cssStatusName + "\">" + _(status) + "</span>\n\t\t</div>";
        return html;
    }
    
    updateMultisession(value){
        let multisesion = this.$$('multisession');
        var _ = this.app.getService("locale")._;
        var html = this.statusBadge(value, _);
        multisesion.config.labelRight = html;
        multisesion.refresh();

    }
    addMultisessionButton(){
        let ui = super.config();
        let form = this.$$('form');
        const multisesion = {
            view: "switch",
            name: "multisession",
            localId: "multisession",
            label: "Multisession",
            labelAlign: "right",
            labelWidth: 130,
            css: "webix_um_status_switch",
            on: {
                onChange:  (value) => { this.updateMultisession(value);},   
            
            },
        }
        form.addView(multisesion,4);
        this.$$('multisession').adjust()
        return ui;
    
    }
    removeLastLoginDate(){
        let ui = super.config();
        let form = this.$$('form');
        console.log("ui", ui)
        let reg = ui.rows[1].rows[4]
        //this.$$(reg.body.localId).hide()

        
        return ui
        

    }



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

    AddCredentials() {
        var _this = this;
        var _ = this.app.getService("locale")._;
        /*
        if (!this.CheckEmail())
            return webix.alert({
                type: "alert-error",
                title: _("Error: no email"),
                text: _("Please submit an email first!"),
            });
        */    
        var data = { user_id: this.ID, source: 1, record: "test" };
        var p = this.app.getService("backend").addCredentials(this.ID, data);
        p.then(function (response) {
            if (response.id) {
                _this.credID = response.id;
                _this.$$("credentials").showBatch("credentials");
            }
        });
    };

}

export class CustomCredentialsView extends userManager.views["users/credentials"]{
    config() {
        var _this = this;
        var _ = this.app.getService("locale")._;
        return {
            view: "window",
            modal: true,
            move: true,
            width: 300,
            height: 300,
            head: {
                view: "toolbar",
                padding: { left: 20, right: 9 },
                borderless: true,
                elements: [
                    { view: "label", label: _("New password") },
                    {
                        view: "icon",
                        icon: "wxi-close",
                        hotkey: "esc",
                        click: function () { return _this.getRoot().close(); },
                    },
                ],
            },
            position: "center",
            body: {
                view: "form",
                localId: "form",
                padding: { top: 20, left: 20, right: 20, bottom: 20 },
                margin: 2,
                elements: [
                    /*
                    {
                        view: "button",
                        label: _("Generate password"),
                        click: function () { return _this.GeneratePassword(); },
                    },
                    
                    {
                        borderless: true,
                        height: 20,
                        cols: [
                            {},
                            {
                                template: "<div class='webix_um_divider_line'/>",
                                css: "webix_um_divider",
                                borderless: true,
                                width: 40,
                            },
                            {},
                        ],
                    },
                    */
                    {
                        margin: 10,
                        rows: [
                            {
                                labelPosition: "top",
                                css: "webix_um_passw_label",
                                label: _("Enter password"),
                                view: "text",
                                name: "password",
                                validate: "isNotEmpty",
                                type: "password",
                            },
                            {
                                view: "button",
                                css: "webix_primary",
                                label: _("Reset password"),
                                click: function () { return _this.ResetPassword(); },
                            },
                        ],
                    },
                ],
            },
            on: {
                onShow: function () {
                    var input = this.getBody().elements.password.getInputNode();
                    input.focus();
                },
            },
        };
    };
}

