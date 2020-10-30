const REGEX_PATTERN_INIT=":{";
const REGEX_PATTERN_END="}";

function changeConnectedIcon() {
  try {
    if(webix.storage.session.get("system.conected")){
      $$("connectedFlag").config.image="./img/icons/checkbox-blank-circle_green.png";
      $$("connectedFlag").refresh();
    }else{
      $$("connectedFlag").config.image="./img/icons/checkbox-blank-circle_red.png";
      $$("connectedFlag").refresh();
    }
  } catch (error) {
    
  }
}

function prepareStr(str, data) {
  var regex = new RegExp(REGEX_PATTERN_INIT+'(' + Object.keys(data).join('|') + ')'+REGEX_PATTERN_END, 'g');
  return str.replace(regex, (m, $1) => data[$1] || m);
}

let permissions = {
  "1":  "command_action",
  "2":  "plan_admin",
  "3":  "plan_action",
  "4":  "alarm_recognize",
}


function checkUserPermission(alias) {
  let userPermissions = webix.storage.session.get("app_user_permissions");
  //let ret = permissionString.split(":")[1];
  let permissionList = userPermissions.split(",");
  let outputList = [];
  var i;
  for (i = 0 ; i < permissionList.length; i++){
      outputList.push(
          permissions[permissionList[i].split(":")[1]]
      )
  }

  return outputList.includes(alias);
}

