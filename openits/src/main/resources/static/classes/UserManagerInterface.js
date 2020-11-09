

export default class UserManagerInterface extends userManager.services.Backend {
    users() {
        let graphElemType = `query {
            getUsers{
              id
              name
              password
              passwordExpiredAt
              sessionExpireTimeS
              passwordExpiredAt
              allowMultipleSessions
              isAdmin
              lockedUntil
              preset
              fullName
              email
              language
              timezone
              role{
                id
                alias
                rolePermissions
                {
                id
                alias
                }
              }
              userPermissions{
                id
                alias
              }
            }
           }`;
          let graphqlEType = webix.proxy("GraphQL", graphElemType);
          return Promise.resolve(graphqlEType.load().then(a=>{ let vdata = []; a.forEach(element => {
            vdata.push({
              "id": element.id.split(":")[1],
              "name": element.name,
              "email": element.email,
              "details": "",
              "visited": "2020-05-21T10:02:06Z",
              "registered": "2020-05-21T10:02:06Z",
              "avatar": "",
              "status": 1,
              "preset": element.preset
            });
          }); return vdata;}));
    }

    roles() {
        let graphType = `query {
                getRoles{
                  id
                  preset
                  alias
                  description
                }
               }`;
          let graphqlRole = webix.proxy("GraphQL", graphType);
          return Promise.resolve(graphqlRole.load().then(a=>{ let vdata = []; a.forEach(element => {
            vdata.push({
              "id": element.id.split(":")[1],
              "name": element.alias,
              "color": "",
              "details": element.description,
              "preset": element.preset
            });
          }); return vdata;}));
    }

    rules() {
        let graphType = `query {
                getSystemPermissions{
                  id
                  alias
                  description
                }
               }
              `;
      let graphqlRule = webix.proxy("GraphQL", graphType);
      return Promise.resolve(graphqlRule.load().then(a=>{ let vdata = []; a.forEach(element => {
        vdata.push({
          "id": element.id.split(":")[1],
          "short": element.alias,
          "long": element.description
        });
      }); return vdata;}));
    }

    meta() {
        let graphElemType = `query {
            getUsers{
              id
              role{
                id
              }
              userPermissions{
                id
              }
            }
           }`;
           let graphType = `query {
            getRoles{
                id
                rolePermissions{id}
            }
           }`;           
          let graphqlEType = webix.proxy("GraphQL", graphElemType);
          let graphqlRType = webix.proxy("GraphQL", graphType);
          return Promise.resolve(graphqlEType.load().then(a=>{ 
                let vUserRole = []; 
                let vUserRule = [];
                let vRoleRule = [];
                a.forEach(element => {
                    vUserRole.push([(element.id.split(":")[1]),(element.role.id.split(":")[1])]);

                    if(element.userPermissions !== null)
                        element.userPermissions.forEach(ru =>{
                            vUserRule.push([(element.id.split(":")[1]),(ru.id.split(":")[1])]);
                        });
                });
                graphqlRType.load().then(b => {
                    b.forEach(element => {
                        element.rolePermissions.forEach(ru => {
                            vRoleRule.push([(element.id.split(":")[1]),(ru.id.split(":")[1])]);
                        });
                    });
                });
                let vdata = {};
                vdata["RoleRule"] = vRoleRule;
                vdata["UserRole"] = vUserRole;
                vdata["UserRule"] = vUserRule;
            return vdata;
            }));
    }

    addUser(data) {
        let graphCreateUser = webix.proxy("GraphQL",
            `mutation  createUser($user: UserVO!){
                createUser(user: $user)   {
                  id
                }
              }`);
        return Promise.resolve(graphCreateUser.load({
            user: {

                "name": data.name,
                "password": "1234",
                "preset": false,
                "allowMultipleSessions": false,
                "isAdmin": false,
                "language": "es",
                "country": "Spain",
                "timezone": "Asia/Nepal",
                "roleId": "Role:2",
            }
        }).then(dataReturn => {
            return { 'id': dataReturn.id.split(":")[1] }
        }));

    }

    updateUser(id, data) {
        let graphUpdateUser = webix.proxy("GraphQL",
            `mutation  updateUser($user: UserVO!){
                updateUser(user: $user)   {
                  id
                }
              }`);
              
        if(data.roles[0] !== undefined && data.roles[0] !==null)
            return Promise.resolve(graphUpdateUser.load({

                user: {
                    "id": "User:"+ data.id,
                    "name": data.name,
                    "password": "1234",
                    "preset": data.preset,
                    "allowMultipleSessions": false,
                    "isAdmin": false,
                    "language": "es",
                    "country": "Spain",
                    "timezone": "Asia/Nepal",
                    "roleId": "Role:"+data.roles[0].toString(),
                }
            }).then(dataReturn => {
                return { 'id': data.id}
            }));
    }

    deleteUser(data) {
        let getUser = webix.proxy("GraphQL",
        `query  getUser($userId: String!){
            getUser(userId: $userId) {
              id
              name
              preset
            }
        }`);
        
        getUser.load({
            "userId": "User:"+data.toString()
            
        }).then(dataReturn => {
            if (dataReturn.preset){
                //return Promise.resolve([]);
            }else{
                let graphDeleteUser = webix.proxy("GraphQL",
                `mutation  deleteUser($userId: String!){
                    deleteUser(userId: $userId){
                        id
                    }
                }`);
                return Promise.resolve(graphDeleteUser.load({
                    "userId": "User:"+data.toString()
                
                }).then(dataReturn => {
                    return { 'id': data }
                }));

            }
         
        })

        
    }

    addRole(data){
        let graph = `mutation  createRole($role: RoleVO!){
                createRole(role: $role){
                    id
                }
            }`;
        let graphql = webix.proxy("GraphQL", graph);
        return Promise.resolve(graphql.load({
            role: {
                "alias": data.name,
                "description": data.name,
                "preset": false
            }
        }).then(b => {
            let vdata = {};
            vdata["id"] = b.id.split(":")[1];
            return vdata;
        }));
    }

    deleteRole(id){
        let graph = `mutation  deleteRole($roleId: String!){
            deleteRole(roleId: $roleId){
                id
            }
        }`;
        let graphql = webix.proxy("GraphQL", graph);
        return Promise.resolve(graphql.load({
            roleId: "Role:" + id
        }).then(b => {
            let vdata = {};
            vdata["id"] = b.id.split(":")[1];
            return vdata;
        }));
    }

    updateRole(id, data){
        let graph = `mutation  updateRole($role: RoleVO!){
            updateRole(role: $role){
                id
                alias
                description
                preset
                rolePermissions{id alias}
            }
        }`;
        let graphql = webix.proxy("GraphQL", graph);
        let vPermissions = [];
        if (data.rules !== undefined && data.rules !== null){
            data.rules.forEach(e => {
                vPermissions.push("SystemPermission:" + e);
            });

            return Promise.resolve(graphql.load({
                "role": {
                    "id": "Role:" +id,
                    "alias": data.name,
                    "preset": data.preset,
                    "rolePermissionIds": vPermissions
                }
            }).then(b => {
                let vdata = {};
                vdata["id"] = b.id.split(":")[1];
                return vdata;
            }));

        }else{
           
            return Promise.resolve(graphql.load({
                "role": {
                    "id": "Role:" +id,
                    "alias": data.name,
                    "preset": false
                }
            }).then(b => {
                let vdata = {};
                vdata["id"] = b.id.split(":")[1];
                return vdata;
            }));

        }
    }

    avatar(id){}

    credentials(id){
        return Promise.resolve([]);
    }

    addCredentials(id, data){
        return Promise.resolve({ "id": id});
    }

    resetPassword(id, data){
        let graphquery = webix.proxy("GraphQL",
            `query getuser($userId: String!) {
                getUser(userId: $userId){
                    id
                    name
                    password
                    preset
                    allowMultipleSessions
                    language
                    isAdmin
                    country
                    timezone
                    role{
                        id
                    }
                }
            }`);
        let vUser = [];
        graphquery.load({
            userId: "User:" + id
        }).then(a=>{
            if(data.record !== undefined && data.recoed !== null){
                let graphUpdateUser = webix.proxy("GraphQL",
                `mutation  updateUser($user: UserVO!){
                    updateUser(user: $user)   {
                    id
                    }
                }`);
                return Promise.resolve(graphUpdateUser.load({
                    user: {
                        "id": "User:"+ data.id,
                        "name": a.name,
                        "password": data.record,
                        "preset": a.preset,
                        "allowMultipleSessions": a.allowMultipleSessions,
                        "isAdmin": a.isAdmin,
                        "language": a.language,
                        "country": a.country,
                        "timezone": a.timezone,
                        "roleId": a.role.id,
                    }
                }).then(dataReturn => {
                    return {'id': data.id}
                }));
            } else {
                let graphUpdateUser = webix.proxy("GraphQL",
                `mutation  updateUser($user: UserVO!){
                    updateUser(user: $user)   {
                    id
                    }
                }`);
                return Promise.resolve(graphUpdateUser.load({
                    user: {
                        "id": "User:"+ data.id,
                        "name": a.name,
                        "password": "qwerty123",
                        "preset": a.preset,
                        "allowMultipleSessions": a.allowMultipleSessions,
                        "isAdmin": a.isAdmin,
                        "language": a.language,
                        "country": a.country,
                        "timezone": a.timezone,
                        "roleId": a.role.id,
                    }
                }).then(dataReturn => {
                    return {'id': data.id}
                }));
            }
        });
    }

    deleteCredentials(id, credId){
        return Promise.resolve({ "id": id});
    }

    logs(type, id){
        return Promise.resolve([]);
    }

    logsMeta(){
        return Promise.resolve([]);
    }
}