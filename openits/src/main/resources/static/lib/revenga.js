function changeConnectedIcon() {
  try {
    if(webix.storage.session.get("system.conected")){
      $$("connectedFlag").config.image="http://localhost:8080/img/icons/checkbox-blank-circle_green.png";
      $$("connectedFlag").refresh();
    }else{
      $$("connectedFlag").config.image="http://localhost:8080/img/icons/checkbox-blank-circle_red.png";
      $$("connectedFlag").refresh();
    }
  } catch (error) {
    
  }
}