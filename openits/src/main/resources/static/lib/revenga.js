

const REGEX_PATTERN_INIT=":{";
const REGEX_PATTERN_END="}";

const UI_ELEMENT_TYPE_DEFAULT="default";
const UI_ELEMENT_TYPE_GENERIC="generic";
const UI_ELEMENT_TYPE_MATRIX="matrix";
const UI_ELEMENT_TYPE_STATIC="static";

const ELEMENT_FORM_TAB_FORM="form";
const ELEMENT_FORM_TAB_VIDEO="video";
const ELEMENT_FORM_TAB_SYN="synoptic";

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


function checkUserPermission(alias) {
  try{
    let userPermissions = webix.storage.session.get("app_user_permissions");
    let permissionList = []  
    for (i = 0 ; i < userPermissions.length; i++){
      permissionList.push(userPermissions[i].alias)
      
    }
    return permissionList.includes(alias);

  } catch (error) {
    console.error(error);
    return false;
  }
  
}

