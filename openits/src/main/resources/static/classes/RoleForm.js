import {timezones,countries} from "../data/datacountry.js";


export default class RoleForm {
	constructor(id,data,mode, parentId) {
        this.id = id;
        this.parentId=parentId;
        this.btnDetailId= "btn_detail"+this.parentId;
	    this.btnEditId= "btn_edit"+this.parentId;
	    this.btnDeleteId= "btn_delete"+this.parentId;

        this.mode = mode; // 0 details, 1 edition, 2 new, 3 delete
        if (this.id === null && data !== null) {
            this.alias = data.alias;
            this.description = data.description;
            this.rolePermissions = data.userPermissions;
        }
        ( this.id === null && data === null ) ? this.id = "new" : null;
		var graphRole = `query { 
			getRole(roleId: "` + this.id + `"){
                id
                preset
                alias
                description
                rolePermissions{id alias}
			}
        }`;
        
		this.graphql = webix.proxy("GraphQL", graphRole);
		this.header = {
			view: "template",
			type: "header",
			id: "roleFormHeader_"+this.id,
			data: {
				value: ""
			},
			template: function (obj) {
				return obj.value;
			},
			css: { "text-align": "left !important" }
        };
        
        function saveEditRole(id) {
            let itemId = id.toString().split("_")[2];
            let createRoleForm = $$("roleForm_" + itemId).getValues();
            $$("roleForm_" + itemId).validate();
            let createRoleForm3 = $$("roleFormRolePermissions_" + itemId).getValue();

            let graphCreateRole = webix.proxy("GraphQL",`
                mutation  updateRole($role: RoleVO!){
                    updateRole(role: $role)   {
                        id
                        alias
                        description
                        preset
                        rolePermissions{id alias}
                    }
                }`
                );
            graphCreateRole.load({
                role: {
                    "id": itemId,
                    "alias": createRoleForm.alias,
                    "description": createRoleForm.description,
                    "preset": false,
                    "rolePermissionIds": createRoleForm3.split(",")
                }
            }).then(data => {
                let item = $$(this.parentId).getItem(data.id);
                item["id"] = data.id;
                item["alias"] = data.alias;
                item["description"] = data.description;
                item["preset"] = data.preset;
                $$(this.parentId).updateItem(data.id, item);
                $$("role_win").destructor();
            });            
        }

        function createNewRole(id) {
            let itemId = id.toString().split("_")[2];
            let createRoleForm = $$("roleForm_" + itemId).getValues();
            $$("roleForm_" + itemId).validate();
            let createRoleForm3 = $$("roleFormRolePermissions_" + itemId).getValue();

            let graphCreateRole = webix.proxy("GraphQL",`
                mutation createRole($role: RoleVO!){
                    createRole(role: $role)   {
                        id
                        alias
                        description
                        preset
                        rolePermissions{id alias}
                    }
                }`
            );

            graphCreateRole.load({
                role: {
                    "alias": createRoleForm.alias,
                    "description": createRoleForm.description,
                    "preset": false,
                    "rolePermissionIds": createRoleForm3.split(",")
                }
            }).then(data => {
                $$(this.parentId).add({
                    id:data.id,
                    alias:data.alias,
                    description: data.description,
                    preset:data.preset
                  });
                $$("role_win").destructor();
            });
        }

        this.form = {   
            id:"roleViewForm",
            body: {
                rows:[
                        {
                            view: "form",
                            id: "roleForm_" + this.id,
                            width: 0,
                            margin: 0,
                            elements: [
                                { view: "text", id: "roleForm_" + this.id + "_id", name: "id", label: $.i18n("role.popup.id") , labelWidth: 200, disabled: true } ,
                                { view: "text", id: "roleForm_" + this.id + "_alias",name: "alias", label: $.i18n("role.popup.alias") , labelWidth: 200, disabled: true },
                                { view: "text", id: "roleForm_" + this.id + "_description",name: "description", label: $.i18n("role.popup.description") ,labelWidth: 200, disabled: true },
                            ],
                            rules:{
                                "alias":webix.rules.isNotEmpty,
                            }               
                        },
                        { template: $.i18n("role.popup.rolePermissions.header"), type:"section" },
                        { 
                            id:"roleFormRolePermissions_" + this.id,
                            view:"dbllist", 
                            //value:rolePermissionsIdList.toString(),
                            list:{ height:150, scroll:true },
                            labelLeft:$.i18n("role.popuo.available"),
                            labelRight:$.i18n("role.popup.selected"),
                            disabled: true,
                            //data:rolePermissionsList
                        }
                    ]
                }
        };
		this.buttons = {
            id: "roleFormButtons_" + this.id,
			view: "form",
			elements: [
                { view: "button", hidden: true, id: "rolesFormButtons_create_" + this.id, label: $.i18n("role.popup.button.create"), value: "",
                    click: createNewRole
                },
                { view: "button", hidden: true, id: "rolesFormButtons_save_" + this.id,  label: $.i18n("role.popup.button.save"), value: "",
                    click: saveEditRole
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
                        header = data.id + " " + data.alias;
                        try {
                            $$("roleForm_" + this.id).setValues({
                                id: data.id,
                                alias: data.alias,
                                description: data.description,
                                preset: data.preset,
                            });

                        } catch {}
                        $$("roleFormHeader_" + this.id).setValues({ value: header });

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
                            data2.forEach(item => {
                                permissionsList.push({"id": item.id, "value": item.alias});
                            });
                            $$("roleFormRolePermissions_" + this.id ).data_setter(permissionsList);
                            $$("roleFormRolePermissions_" + this.id ).setValue(rolePermissionsIdList);
                        });
                    } 
                });
                break;
            case 1: //Edit Mode
                this.graphql.load().then(data => {
                    if (data) {
                        header = data.id + " " + data.alias;
                        try {
                            $$("roleForm_" + this.id).setValues({
                                id: data.id,
                                alias: data.alias,
                                description: data.description,
                                preset: data.preset,
                            });

                        } catch {}
                        $$("roleFormHeader_" + this.id).setValues({ value: header });

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
                            data2.forEach(item => {
                                permissionsList.push({"id": item.id, "value": item.alias});
                            });
                            $$("roleFormRolePermissions_" + this.id ).data_setter(permissionsList);
                            $$("roleFormRolePermissions_" + this.id ).setValue(rolePermissionsIdList);
                            $$("roleForm_" + this.id + "_alias").enable();
                            $$("roleForm_" + this.id + "_description").enable();
                            $$("roleFormRolePermissions_" + this.id).enable();
                            $$("rolesFormButtons_save_" + this.id).show();
                        });
                    } 
                });
                break;
            case 2: //new Mode

                header = $.i18n("role.popup.newRoleheader");
                let graphPermissions = webix.proxy("GraphQL",`
                    query  {
                        getSystemPermissions{
                        id
                        alias
                        description
                        }
                    }`);
                    console.log(this.id);
                graphPermissions.load()
                .then(data2 => {
                    $$("roleFormHeader_" + this.id).setValues({ value: header });
                    let permissionsList = [];
                    data2.forEach(item => {
                        permissionsList.push({"id": item.id, "value": item.alias});
                    });
                    
                    $$("roleForm_" + this.id + "_alias").enable();
                    $$("roleForm_" + this.id + "_description").enable();
                    $$("roleFormRolePermissions_" + this.id).enable();
                    $$("roleFormRolePermissions_" + this.id ).data_setter(permissionsList);
                    $$("rolesFormButtons_create_" + this.id).show();
                });
                break;
            case 3: // delete Mode
                genericForm = {
                    rows: [
                        { margin:5, cols:[
                            { view:"button", value:"Confirmar", css:"webix_primary", id: this.id,
                                click: function deleteUser(obj){
                                   
                                    let graphRolUser = webix.proxy("GraphQL",`
                                        mutation($roleId: String!) {
                                            deleteRole(roleId: $roleId){
                                              id
                                            }
                                        }`
                                    );
                        
                                    graphRolUser.load({
                                        roleId: obj
                                    }).then(data => {
                                        try {
                                            $$(this.parentId).remove(data.id);
                                            $$("role_win").destructor();
                                            $$(btnDetailId).hide();
                                            $$(btnEditId).hide();
                                            $$(btnDeleteId).hide();
                                        } catch  {}
                                    });
                                }
                            },
                            { view:"button", value:"Cancel",
                                click: function cancelUser(obj){
                                    try {
                                        $$("role_win").destructor();
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
