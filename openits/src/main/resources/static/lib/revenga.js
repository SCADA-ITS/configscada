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