import {timezones,countries} from "../data/datacountry.js";


export default class UserForm {
	constructor(id,data,mode) {
        this.id = id;
        this.mode = mode; // 0 details, 1 edition, 2 new, 3 delete
        if (this.id === null && data !== null) {
            this.name = data.name;
            this.fullName = data.fullName;
            this.email = data.email;
            this.password = data.password;
            this.allowMultipleSessions = data.allowMultipleSessions;
            this.isAdmin = data.isAdmin;
            this.language = data.language;
            this.country = data.country;
            this.timezone = data.timezone;
            this.role = data.role;
            this.userPermissions = data.userPermissions;
        }
        ( this.id === null && data === null ) ? this.id = "new" : null;
		var graphUser = `query { 
			getUser(userId: "` + this.id + `"){
                id
                name
                fullName
                email
                password
                allowMultipleSessions
                isAdmin
                language
                country
                timezone
                role{
                  id
                  alias
                  rolePermissions{id alias}
                }
                userPermissions{id alias}
			}
        }`;
        
		this.graphql = webix.proxy("GraphQL", graphUser);
		this.header = {
			view: "template",
			type: "header",
			id: "userFormHeader_"+this.id,
			data: {
				value: ""
			},
			template: function (obj) {
				return obj.value;
			},
			css: { "text-align": "left !important" }
        };
        
        function saveEditUser(id) {
            let itemId = id.toString().split("_")[2];
            let createUserForm = $$("userForm_" + itemId).getValues();
            $$("userForm_" + itemId).validate();
            let createUserForm3 = $$("userFormUserPermissions_" + itemId).getValue();

            let graphCreateUser = webix.proxy("GraphQL",`
                mutation  updateUser($user: UserVO!){
                    updateUser(user: $user)   {
                    id
                    name
                    fullName
                    email
                    language
                    timezone
                    isAdmin
                    role{
                        id
                        alias
                        rolePermissions{id alias}
                    }
                    userPermissions{id alias}
                    }
                }`
            );
            graphCreateUser.load({
                user: {
                    "id": itemId,
                    "name": createUserForm.name,
                    "password": createUserForm.password,
                    "preset": false,
                    "allowMultipleSessions": createUserForm.allowMultipleSessions === 1 ? true : false,
                    "isAdmin": createUserForm.isAdmin === 1 ? true : false,
                    "email": createUserForm.email,
                    "fullName": createUserForm.fullName,
                    "language": createUserForm.language,
                    "country": createUserForm.country.toString().split("_")[2],
                    "timezone": createUserForm.timezone.toString().split("_")[2],
                    "roleId": createUserForm.role.toString().split("_")[2],
                    "userPermissionIds": createUserForm3.split(",")
                }
            }).then(data => {
                let item = $$("usersTable").getItem(data.id);
                item["id"] = data.id;
                item["name"] = data.name;
                item["fullName"] = data.fullName;
                item["email"] = data.email;
                item["language"] = data.language;
                item["timezone"] = data.timezone;
                item["isAdmin"] = data.isAdmin;
                item["role"] = data.role;
                $$("usersTable").updateItem(data.id, item);
                $$("user_win").destructor();
            });            
        }

        function createNewUser(id) {
            let itemId = id.toString().split("_")[2];
            let createUserForm = $$("userForm_" + itemId).getValues();
            $$("userForm_" + itemId).validate();
            let createUserForm3 = $$("userFormUserPermissions_" + itemId).getValue();

            let graphCreateUser = webix.proxy("GraphQL",`
                mutation  createUser($user: UserVO!){
                    createUser(user: $user)   {
                    id
                    name
                    fullName
                    email
                    language
                    allowMultipleSessions
                    country
                    timezone
                    isAdmin
                    role{
                        id
                        alias
                      }
                    userPermissions{id alias}
                    }
                }`
            );

            graphCreateUser.load({
                user: {
                    "name": createUserForm.name,
                    "password": createUserForm.password,
                    "preset": false,
                    "allowMultipleSessions": createUserForm.allowMultipleSessions === 1 ? true : false,
                    "isAdmin": createUserForm.isAdmin === 1 ? true : false,
                    "email": createUserForm.email,
                    "fullName": createUserForm.fullName,
                    "language": createUserForm.language,
                    "country": createUserForm.country.toString().split("_")[3],
                    "timezone": createUserForm.timezone.toString().split("_")[2],
                    "roleId": createUserForm.role.toString().split("_")[2],
                    "userPermissionIds": createUserForm3.split(",")
                }
            }).then(data => {
                $$("usersTable").add({
                    id:data.id,
                    name:data.name,
                    fullName: data.fullName,
                    email:data.email,
                    language:data.language,
                    timezone: data.timezone,
                    isAdmin: data.isAdmin,
                    role: data.role
                  });
                $$("user_win").destructor();
            });
        }

        function setTimezone(id) {
            //console.log(id);
            if (this.mode !== 0) {
                let countryId = id.toString().split("_")[2];
                let iduser = id.toString().split("_")[1];
                $$("userForm_" + iduser + "_language").setValue(countryId);
            }
        };

        function setPermissions(id) {
            let itemId = id.toString().split("_")[1];
            let roleId = id.toString().split("_")[2];

            let graphRolUser = webix.proxy("GraphQL",`
                query($roleId: String!)  {
                    getRole(roleId: $roleId){
                    id
                    preset
                    alias
                    description
                    rolePermissions{id alias}
                    }
                }`
            );

            graphRolUser.load({
                roleId: roleId
            }).then(data => {

                let graphPermissions = webix.proxy("GraphQL",`
                query  {
                    getSystemPermissions{
                    id
                    alias
                    description
                    }
                }`);
                
                let rolePermissionsList = [];
                let rolePermissionsIdList = [];
                
                if (data.rolePermissions !== null)
                    data.rolePermissions.forEach(item => {
                        rolePermissionsList.push({id: item.id, value: item.alias});
                        rolePermissionsIdList.push(item.id); //= rolePermissionsIdList + item.id + ",";
                    });

                graphPermissions.load()
                .then(data2 => {
                    let permissionsList = [];
                    let permissionsListTotal = [];
                    data2.forEach(item => {
                        permissionsListTotal.push({"id": item.id, "value": item.alias});
                        if (!rolePermissionsIdList.includes(item.id))
                            permissionsList.push({"id": item.id, "value": item.alias});
                    });

                    $$("userFormRolePermissions_" + itemId ).data_setter(permissionsListTotal);
                    $$("userFormRolePermissions_" + itemId ).setValue(rolePermissionsIdList);
                    $$("userFormUserPermissions_" + itemId ).data_setter(permissionsList);
                });
            });
        };

        this.form = {   
            id: "tabview_" + this.id,
            view:"tabview", 
            tabbar : { 
                options:[ 
                    { id:"userForm_" + this.id, value:$.i18n("user.popup.tab.details") , width:100 },
                    //{ id:"userFormPermissions_" + this.id, value:$.i18n("user.popup.tab.permissions"), width:100 }
                ]
            },
            cells:[
                { 
                    view: "form",
                    id: "userForm_" + this.id,
                    width: 0,
                    margin: 0,
                    elements: [
                        { view: "text", id: "userForm_" + this.id + "_id", name: "id", label: $.i18n("user.popup.id") , labelWidth: 200, disabled: true } ,
                        { view: "text", id: "userForm_" + this.id + "_name",name: "name", label: $.i18n("user.popup.name") , labelWidth: 200, disabled: true },
                        { view: "text", id: "userForm_" + this.id + "_fullName",name: "fullName", label: $.i18n("user.popup.fullName") ,labelWidth: 200, disabled: true },
                        { view: "text", type: "email", id: "userForm_" + this.id + "_email",name: "email", label: $.i18n("user.popup.email") ,labelWidth: 200, disabled: true },
                        { view: "text", type: "password", id: "userForm_" + this.id + "_password",name: "password", label: $.i18n("user.popup.password") ,labelWidth: 200, disabled: true },
                        { view: "text", type: "password", id: "userForm_" + this.id + "_password_conf",name: "password_conf", label: $.i18n("user.popup.password_conf") ,labelWidth: 200, disabled: true },
                        { view: "checkbox", id: "userForm_" + this.id + "_allowMultipleSessions",name: "allowMultipleSessions", label: $.i18n("user.popup.allowMultipleSessions") ,labelWidth: 200, disabled: true },
                        { view: "checkbox", id: "userForm_" + this.id + "_isAdmin",name: "isAdmin", label: $.i18n("user.popup.isAdmin") ,labelWidth: 200, disabled: true },
                        { view: "text", id: "userForm_" + this.id + "_language",name: "language", label: $.i18n("user.popup.language") ,labelWidth: 200, disabled: true },
                        { view: "combo", id: "userForm_" + this.id + "_country",name: "country", label: $.i18n("user.popup.country") ,labelWidth: 200, disabled: true, options: [],
                            on: {
                                onChange: setTimezone
                            } 
                        },
                        { view: "combo", id: "userForm_" + this.id + "_timezone",name: "timezone", label: $.i18n("user.popup.timezone") ,labelWidth: 200, disabled: true, options: [] },
                        { view: "combo", id: "userForm_" + this.id + "_role",name: "role", require: true, label: $.i18n("user.popup.role") ,labelWidth: 200, disabled: true, options: [],
                            on: {
                                onChange: setPermissions
                            }                     
                        }
                    ],
                    rules:{
                        "email":webix.rules.isEmail,
                        "name":webix.rules.isNotEmpty,
                        "password":webix.rules.isNotEmpty,
                        "password_conf":webix.rules.isNotEmpty,
                        "language":webix.rules.isNotEmpty,
                        "country":webix.rules.isNotEmpty,
                        "timezone":webix.rules.isNotEmpty,
                        "role":webix.rules.isNotEmpty,
                        $obj:function(data){
                            //password must not be empty
                            if (!data.password){
                              webix.message("You need to specify password");
                              return false;
                            }
                            //passwords must be equal
                            if (data.password != data.password_conf){
                              webix.message("Passwords are not the same"); 
                              return false;
                            }
                            return true;
                          }
                    }               
                 }
            ]
        };
		this.buttons = {
            id: "userFormButtons_" + this.id,
			view: "form",
			elements: [
                { view: "button", hidden: true, id: "usersFormButtons_create_" + this.id, label: $.i18n("user.popup.button.create"), value: "",
                    click: createNewUser
                },
                { view: "button", hidden: true, id: "usersFormButtons_save_" + this.id,  label: $.i18n("user.popup.button.save"), value: "",
                    click: saveEditUser
                }
			]
		};
	}

	getComponent() {
        let header = "";

		let genericForm = {
			rows: [
                this.header,
                this.form,
                this.buttons,
				{}
			]
		};

        switch (this.mode) {
            case 0:  //Details Mode
                this.graphql.load().then(data => {
                    if (data) {
                        header = data.id + " " + data.name;
                        try {
                            $$("userForm_" + this.id).setValues({
                                id: data.id,
                                name: data.name,
                                fullName: data.fullName,
                                email: data.email,
                                password: data.password,
                                password_conf: data.password,
                                allowMultipleSessions: data.allowMultipleSessions === true ? 1 : 0,
                                isAdmin: data.isAdmin === true ? 1 : 0,
                                language: data.language,
                            });

                            $$("userForm_" + this.id + "_timezone").setValue({ id: "userForm_" + this.id + "_" + data.timezone, value: data.timezone });
                            $$("userForm_" + this.id + "_role").setValue({ id: "userForm_" + this.id + "_" + data.role.id, value: data.role.alias });
                            $$("userForm_" + this.id + "_country").setValue({ id: "userForm_" + this.id + "_" + data.country, value: data.country });
                        } catch {}
                        $$("userFormHeader_" + this.id).setValues({ value: header });
                        let graphPermissions = webix.proxy("GraphQL",`
                            query  {
                                getSystemPermissions{
                                id
                                alias
                                description
                                }
                            }`);

                        let rolePermissionsList = [];
                        let rolePermissionsIdList = [];
                        
                        if (data.role.rolePermissions !== null)
                            data.role.rolePermissions.forEach(item => {
                                rolePermissionsList.push({id: item.id, value: item.alias});
                                rolePermissionsIdList.push(item.id); //= rolePermissionsIdList + item.id + ",";
                            });

                        let permissionsListUser = [];
                        if (data.userPermissions !== null)
                            data.userPermissions.forEach(item => {
                                permissionsListUser.push(item.id);
                            });
                        
                        graphPermissions.load()
                        .then(data2 => {
                            let permissionsList = [];
                            data2.forEach(item => {
                                if (!rolePermissionsIdList.includes(item.id))
                                    permissionsList.push({"id": item.id, "value": item.alias});
                            });
                            $$("tabview_" + this.id).addView({  
                                id:"userFormPermissions_" + this.id, value:$.i18n("user.popup.tab.permissions"), width:100,
                                header: $.i18n("user.popup.tab.permissions"),
                                body: {   
                                    rows:[
                                        { template:"Role Permissions", type:"section" },
                                        { 
                                            id:"userFormRolePermissions_" + this.id,
                                            view:"dbllist", 
                                            value:rolePermissionsIdList.toString(),
                                            list:{ height:150, scroll:true },
                                            labelLeft:"Available screens",
                                            labelRight:"Selected",
                                            disabled: true,
                                            data:rolePermissionsList
                                        },
                                        { template:"User Permissions", type:"section" },
                                        { 
                                            id:"userFormUserPermissions_" + this.id,
                                            view:"dbllist", 
                                            list:{ height:150, scroll:true },
                                            value: permissionsListUser.toString(),
                                            labelLeft:"Available screens",
                                            labelRight:"Selected",
                                            disabled: true,
                                            data:permissionsList
                                        }
                                    ]
                                }
                            });
                        });
                    } 
                });
                break;
            case 1: //Edit Mode
                this.graphql.load().then(data => {
                    if (data) {

                        let graphRoles = webix.proxy("GraphQL",`
                            query  {
                                getRoles{
                                id
                                preset
                                alias
                                description
                                rolePermissions{id alias}
                                }
                            }              
                        `);

                        $$("userForm_" + this.id + "_name").enable();
                        $$("userForm_" + this.id + "_fullName").enable();
                        $$("userForm_" + this.id + "_email").enable();
                        $$("userForm_" + this.id + "_password").enable();
                        $$("userForm_" + this.id + "_password_conf").enable();
                        $$("userForm_" + this.id + "_allowMultipleSessions").enable();
                        $$("userForm_" + this.id + "_isAdmin").enable();
                        //$$("userForm_new_language").enable();
                        $$("userForm_" + this.id + "_country").enable();
                        $$("userForm_" + this.id + "_timezone").enable();
                        $$("userForm_" + this.id + "_role").enable();
                        
                        //$$("userFormButtons_create").show();
                        $$("usersFormButtons_save_" + this.id).show();
                        header = data.id + " " + data.name;
                        try {
                            $$("userForm_" + this.id).setValues({
                                id: data.id,
                                name: data.name,
                                fullName: data.fullName,
                                email: data.email,
                                password: data.password,
                                password_conf: data.password,
                                allowMultipleSessions: data.allowMultipleSessions === true ? 1 : 0,
                                isAdmin: data.isAdmin === true ? 1 : 0,
                                language: data.language,
                            });


                            let timezonesList = [];
                            timezones.forEach(item => {
                                if (data.timezone !== item) {
                                    timezonesList.push({ value: item });
                                    $$("userForm_" + this.id + "_timezone").getList().add({ id: "newtimezone_" + this.id + "_" + item, value: item });
                                }
                            });
        
                            let countriesList = [];
                            countries.forEach(item => {
                                if (data.country !== item.name) {
                                    countriesList.push({ value: item.name, code: item.code });
                                    $$("userForm_" + this.id + "_country").getList().add({ id: "newCountry_" + this.id + "_" + item.code.toLowerCase() + "_" + item.name, value: item.name, code: item.code });
                                }
                            });
        
                            graphRoles.load()
                            .then(data2 => {
                                data2.forEach(item => {
                                    //rolesList.push({"id": item.id, "value": item.alias});
                                    if (data.role.id !== item.id) 
                                        $$("userForm_" + this.id + "_role").getList().add({ id: "newRol_" + this.id + "_" + item.id, value: item.alias });
                                });
                            });

                            $$("userForm_" + this.id + "_timezone").setValue({ id: "userForm_" + this.id + "_" + data.timezone, value: data.timezone });
                            $$("userForm_" + this.id + "_role").setValue({ id: "userForm_" + this.id + "_" + data.role.id, value: data.role.alias });
                            $$("userForm_" + this.id + "_country").setValue({ id: "userForm_" + this.id + "_" + data.country, value: data.country });
                            
                        } catch {}
                        $$("userFormHeader_" + this.id).setValues({ value: header });
                        let graphPermissions = webix.proxy("GraphQL",`
                            query  {
                                getSystemPermissions{
                                id
                                alias
                                description
                                }
                            }`);

                        let rolePermissionsList = [];
                        let rolePermissionsIdList = [];
                        
                        if (data.role.rolePermissions !== null)
                            data.role.rolePermissions.forEach(item => {
                                rolePermissionsList.push({id: item.id, value: item.alias});
                                rolePermissionsIdList.push(item.id); //= rolePermissionsIdList + item.id + ",";
                            });

                        let permissionsListUser = [];
                        if (data.userPermissions !== null)
                            data.userPermissions.forEach(item => {
                                permissionsListUser.push(item.id);
                            });
                        
                        graphPermissions.load()
                        .then(data2 => {
                            let permissionsList = [];
                            data2.forEach(item => {
                                if (!rolePermissionsIdList.includes(item.id))
                                    permissionsList.push({"id": item.id, "value": item.alias});
                            });
                            $$("tabview_" + this.id).addView({  
                                id:"userFormPermissions_" + this.id, value:$.i18n("user.popup.tab.permissions"), width:100,
                                header: $.i18n("user.popup.tab.permissions"),
                                body: {   
                                    rows:[
                                        { template:"Role Permissions", type:"section" },
                                        { 
                                            id:"userFormRolePermissions_" + this.id,
                                            view:"dbllist", 
                                            value:rolePermissionsIdList.toString(),
                                            list:{ height:150, scroll:true },
                                            labelLeft:"Available screens",
                                            labelRight:"Selected",
                                            disabled: true,
                                            data:rolePermissionsList
                                        },
                                        { template:"User Permissions", type:"section" },
                                        { 
                                            id:"userFormUserPermissions_" + this.id,
                                            view:"dbllist", 
                                            list:{ height:150, scroll:true },
                                            value: permissionsListUser.toString(),
                                            labelLeft:"Available screens",
                                            labelRight:"Selected",
                                            //disabled: true,
                                            data:permissionsList
                                        }
                                    ]
                                }
                            });
                        });
                    } 
                });
                break;
            case 2: //new Mode
                //$$("userFormButtons_create").show();
                let graphRoles = webix.proxy("GraphQL",`
                    query  {
                        getRoles{
                        id
                        preset
                        alias
                        description
                        rolePermissions{id alias}
                        }
                    }              
                `);
                graphRoles.load()
                .then(data => {
                    $$("userForm_new_id").hide();
                    $$("userForm_new_name").enable();
                    $$("userForm_new_fullName").enable();
                    $$("userForm_new_email").enable();
                    $$("userForm_new_password").enable();
                    $$("userForm_new_password_conf").enable();
                    $$("userForm_new_allowMultipleSessions").enable();
                    $$("userForm_new_isAdmin").enable();
                    //$$("userForm_new_language").enable();
                    $$("userForm_new_country").enable();
                    $$("userForm_new_timezone").enable();
                    $$("userForm_new_role").enable();
                    $$("usersFormButtons_create_" + this.id).show();
                    let timezonesList = [];
                    timezones.forEach(item => {
                        timezonesList.push({ value: item });
                        $$("userForm_" + this.id + "_timezone").getList().add({ id: "newtimezone_" + this.id + "_" + item, value: item });
                    });

                    let countriesList = [];
                    countries.forEach(item => {
                        countriesList.push({ value: item.name, code: item.code });
                        $$("userForm_" + this.id + "_country").getList().add({ id: "newCountry_" + this.id + "_" + item.code.toLowerCase() + "_" + item.name, value: item.name, code: item.code });
                    });

                    header = $.i18n("user.popup.new.header");
                    $$("userFormHeader_" + this.id).setValues({ value: header });
                    
                    data.forEach(item => {
                        //rolesList.push({"id": item.id, "value": item.alias});
                        $$("userForm_" + this.id + "_role").getList().add({ id: "newRol_" + this.id + "_" + item.id, value: item.alias });
                    });
                    
                    let graphPermissions = webix.proxy("GraphQL",`
                    query  {
                        getSystemPermissions{
                        id
                        alias
                        description
                        }
                    }`);
                    let permissionsList = [];
                    graphPermissions.load()
                    .then(data2 => {
                        data2.forEach(item => {
                            permissionsList.push({"id": item.id, "value": item.alias});
                        });

                        $$("tabview_" + this.id).addView({  
                            id:"userFormPermissions_" + this.id, value:$.i18n("user.popup.tab.permissions"), width:100,
                            header: $.i18n("user.popup.tab.permissions"),
                            body: {   
                                rows:[
                                    { template:"Role Permissions", type:"section" },
                                    { 
                                        id:"userFormRolePermissions_" + this.id,
                                        view:"dbllist", 
                                        //value:rolePermissionsIdList.toString(),
                                        list:{ height:150, scroll:true },
                                        labelLeft:"Available screens",
                                        labelRight:"Selected",
                                        disabled: true,
                                        //data:rolePermissionsList
                                    },
                                    { template:"User Permissions", type:"section" },
                                    { 
                                        id:"userFormUserPermissions_" + this.id,
                                        view:"dbllist", 
                                        list:{ height:150, scroll:true },
                                        //value: permissionsListUser.toString(),
                                        labelLeft:"Available screens",
                                        labelRight:"Selected",
                                        //disabled: true,
                                        //data:permissionsList
                                    }
                                ]
                            }
                        });
                    });

                });
                break;
            case 3: // delete Mode
                genericForm = {
                    rows: [
                        { margin:5, cols:[
                            { view:"button", value:"Confirmar", css:"webix_primary", id: this.id,
                                click: function deleteUser(obj){
                                    console.log(obj);
                                    let graphRolUser = webix.proxy("GraphQL",`
                                        mutation($userId: String!) {
                                            deleteUser(userId: $userId){
                                              id
                                            }
                                        }`
                                    );
                        
                                    graphRolUser.load({
                                        userId: obj
                                    }).then(data => {
                                        try {
                                            $$("usersTable").remove(data.id);
                                            $$("user_win").destructor();
                                            $$("b_details_user").hide();
                                            $$("b_edit_user" ).hide();
                                            $$("b_delete_user").hide();
                                        } catch  {}
                                    });
                                }
                            },
                            { view:"button", value:"Cancel",
                                click: function cancelUser(obj){
                                    try {
                                        $$("user_win").destructor();
                                    } catch  {}
                                }                        
                            }
                        ]},
                        //{}
                    ]
                };

                break;
        }

		return genericForm;
	}
}
