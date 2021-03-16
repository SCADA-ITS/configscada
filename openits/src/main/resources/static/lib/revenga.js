

const REGEX_PATTERN_INIT=":{";
const REGEX_PATTERN_END="}";

const UI_ELEMENT_TYPE_DEFAULT="default";
const UI_ELEMENT_TYPE_GENERIC="generic";
const UI_ELEMENT_TYPE_MATRIX="matrix";
const UI_ELEMENT_TYPE_MTX="matrixPanel";
const UI_ELEMENT_TYPE_STATIC="static";
const UI_ELEMENT_TYPE_TABLE="table";

const ELEMENT_FORM_TAB_FORM="form";
const ELEMENT_FORM_TAB_VIDEO="video";
const ELEMENT_FORM_TAB_SYN="synoptic";

const DATA_TYPE_4="DataType:4";

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

function parseUIParam(value, dataTypeId) {
  let result = value;
  try {
    if(typeof dataTypeId != 'undefined' && dataTypeId && typeof value != 'undefined' && value){
      if(dataTypeId == DATA_TYPE_4){
        result = webix.i18n.fullDateFormatStr(new Date(parseInt(value)));
      }
    }	
  } catch (error) {
    console.warn(error);
    result=value;
  }
  return result;
}

function elementReady(selector) {
  return new Promise((resolve, reject) => {
    let el = document.querySelector(selector);
    if (el) {
      resolve(el);
    }
    new MutationObserver((mutationRecords, observer) => {
      // Query for elements matching the specified selector
      Array.from(document.querySelectorAll(selector)).forEach((element) => {
        resolve(element);
        //Once we have resolved we don't need the observer anymore.
        observer.disconnect();
      });
    }).observe(document.documentElement, {
      childList: true,
      subtree: true,
    });
  });
}

function getTrimId(complexId) {
  let result;
  try {
    result = parseInt(complexId.substring(complexId.lastIndexOf(":")+1));
  } catch (error) {
    console.warn(error);
  }
  return result;
}

function findModuleByIdAction(moduleActionId) {
  let moduleFound = undefined;
		let modules = window.em.getAll(EntityType.MODULE);
		if (modules && modules.length > 0) {
			modules.forEach(module => {
				if (!moduleFound && module.moduleActions.find(moduleAction => moduleAction.id === moduleActionId)) {
					moduleFound = module;
				}
			});
		}
		return moduleFound;
}

//Deprecated
function auditMutation(auditLog) {
  let auditMutation = webix.proxy("GraphQL",
    `mutation  sendAuditLogs ($auditLogs: [AuditLogVO!]!){
      sendAuditLogs(auditLogs :  $auditLogs)
     }`
  );
  if(auditLog==undefined){
    auditLog={};
  }
  auditLog.userId=webix.storage.session.get("userId");
  // auditLog.time=new Date().getTime();
  auditLog.level="INFO";
  
  auditMutation.load({
    auditLogs: [auditLog]
  })
  // .then(auditResult => {
  //   console.warn(auditResult);
  //   console.warn(auditLog);
  //   console.warn(JSON.stringify(auditLog));
  // });
}
