import { synoptic } from "../data/synoptic.js";
import { staticsIcons } from "../icons/synopticJson.js";
import { synopticTunnel, synopticMetro, synopticGlobal } from "../data/synoptics.js";
import ElementForm from "../classes/ElementForm.js";

export default class Synoptic {
  constructor(data, metadata, synopticDiv) {
    this.elements = [];
    this.elementsTypes = [];
    this.elementsToDraw = {};
    this.baseLayer = "";
    this.ratioWidth = 1.0;
    this.ratioHeight = 1.0;
    this.baseWidth = 0;
    this.baseHeight = 0;
    this.data = data;
    
    this.synopticDiv = synopticDiv;
    this.synopticConf = "";
    this.synopticType = "";

    // for using the object inside callbacks
    let self = this;

    // creates the stomp connection
    this.client = new StompJs.Client({
      brokerURL: webix.storage.session.get("url.ws.stomp"),
      connectHeaders: {
        login: "user",
        passcode: "user"
      },
      debug: function(str) {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000
    });

    this.client.onConnect = function(frame) {
      // Do something, all subscribes must be done is this callback
      // This is needed because this will be executed after a (re)connect
      this.suscription = self.client.subscribe(
        "/topic/element.>",
        self.processStompMessageWithFallback
      );
    };

    this.client.onStompError = function(frame) {
      // Will be invoked in case of error encountered at Broker
      // Bad login/passcode typically will cause an error
      // Complaint brokers will set `message` header with a brief message. Body may contain details.
      // Compliant brokers will terminate the connection after any error
      console.log("Broker reported error: " + frame.headers["message"]);
      console.log("Additional details: " + frame.body);
      alert(`[error] ${error.message}`);
    };

    this.client.activate();

    /*
		data.load().then(d =>
			d.forEach(element => {
				console.log(element.positionX + " --> " + element.positionY);
				this.elements.push(element);
			})
		);
		*/

    if (metadata == null) {
      this.metadata = synoptic;
    } else {
      this.metadata = metadata;
    }
  }

  // Get element icon
  getElementIcon(element) {
    let elem_icon = "";
    let width = 0;
    let height = 0;

    // icon based on status
    if (typeof staticsIcons[element.elementType.id] != "undefined") {
      elem_icon = staticsIcons[element.elementType.id]["state"].find(
        ic => ic.state === element.state
      );
    } else {
      elem_icon = staticsIcons["ElementType:33"]["state"].find(
        ic => ic.state === element.state
      );
    }

    if (typeof elem_icon != "undefined") {
      width = elem_icon.width;
      height = elem_icon.height;
      elem_icon = elem_icon.icon;
    } else {
      elem_icon = "";
    }

    // icon based on element value
    if (element.elementType.alias === "FAN" && element.state === 1) {
      let fan_cmd = element.elementValues.find(
        ic => ic.elementTypeParam.description === "comand for fan"
      );
      elem_icon = staticsIcons[element.elementType["id"]]["state"].find(
        ic => ic.fan_cmd === parseInt(fan_cmd.value)
      );

      if (typeof elem_icon != "undefined") {
        elem_icon = elem_icon.icon;
      } else {
        elem_icon = "";
      }
    } else if (element.elementType.alias === "BARRIER" && element.state === 1) {
      let bar_cmd = element.elementValues.find(
        ic => ic.elementTypeParam.description === "comand for barrier"
      );

      elem_icon = staticsIcons[element.elementType.id]["state"].find(
        ic => ic.bar_cmd === parseInt(bar_cmd.value)
      );

      if (typeof elem_icon != "undefined") {
        elem_icon = elem_icon.icon;
      } else {
        elem_icon = "";
      }
    }

    return {"icon": elem_icon, "width": width, "height": height};
  }

  /*
   *	Stomp without GraphQL
   */
  processStompMessage(message) {
    try {
      let obj = JSON.parse(JSON.parse(message.body));

      obj.basicElementsVO.forEach(element => {
        console.log(element);

        let elem_icon = this.getElementIcon(element).icon;
        //let elem_icon_width = this.getElementIcon(element).width;
        //let elem_icon_height = this.getElementIcon(element).height;
        let elem_icon_width = this.synopticConf[element.id].width;
        let elem_icon_height = this.synopticConf[element.id].height;

        // icon based on element value
        if (element.elementType.alias === "FAN" && element.state === 1) {
          let fan_cmd = element.elementValues.find(
            ic => ic.elementTypeParam.description === "comand for fan"
          );
          elem_icon = staticsIcons[element.elementType["id"]]["state"].find(
            ic => ic.fan_cmd === parseInt(fan_cmd.value)
          );

          if (typeof elem_icon != "undefined") {
            elem_icon = elem_icon.icon;
          } else {
            elem_icon = "";
          }
        } else if (element.elementType.alias === "BARRIER" && element.state === 1) {
          let bar_cmd = element.elementValues.find(
            ic => ic.elementTypeParam.description === "comand for barrier"
          );

          elem_icon = staticsIcons[element.elementType.id]["state"].find(
            ic => ic.bar_cmd === parseInt(bar_cmd.value)
          );
          
          if (typeof elem_icon != "undefined") {
            elem_icon = elem_icon.icon;
          } else {
            elem_icon = "";
          }
        }

        // replace the icon
        document
          .getElementById(element.id)
          .setAttribute("href", "./img/" + elem_icon)
          .setAttribute("width", elem_icon_width)
          .setAttribute("height", elem_icon_height);
      });
    } catch (err) {
      console.log("Error: " + err.message);
    }
  }

  /*
   *	queries GraphQL based on the element ID received by Stomp
   */
  processStompMessageGraphQL(message) {
    try {
      let obj = JSON.parse(JSON.parse(message.body));

      obj.basicElementsVO.forEach(element => {
        //initialize the proxy
        let elementQuery = webix.proxy(
          "GraphQL",
          `
					query ($elem: String!) {
						getElement(elementId: $elem)
						{
							id
							state
							elementValues
							{
								id
								value
								alerted
								elementTypeParam {
									description
								}
							}
						}
					}
					`
        );

        // loads the data
        elementQuery
          .load({
            elem: element.id
          })
          .then(data => {
            // process the received data
            console.log(data);
            let element = data;
            let elem_icon = this.getElementIcon(element).icon;
            //let elem_icon_width = this.getElementIcon(element).width;
            //let elem_icon_height = this.getElementIcon(element).height;
            let elem_icon_width = this.synopticConf[element.id].width;
            let elem_icon_height = this.synopticConf[element.id].height;

            // replace the icon
            document
              .getElementById(element.id)
              .setAttribute("href", "./img/" + elem_icon)
              .setAttribute("width", elem_icon_width)
              .setAttribute("height", elem_icon_height);
          });
      });
    } catch (err) {
      console.log("Error: " + err.message);
    }
  }

  /*
   *	Tries to get the data from Stomp, but if it doesn't find the needed data,
   * gets it by GraphQL
   */
  processStompMessageWithFallback(message) {
    try {
      let obj = JSON.parse(JSON.parse(message.body));

      obj.basicElementsVO.forEach(element => {
        console.log(element);

        let elem_icon = this.getElementIcon(element).icon;
        //let elem_icon_width = this.getElementIcon(element).width;
        //let elem_icon_height = this.getElementIcon(element).height;
        let elem_icon_width = this.synopticConf[element.id].width;
        let elem_icon_height = this.synopticConf[element.id].height;

        // replace the icon
        document
          .getElementById(element.id)
          .setAttribute("href", "./img/" + elem_icon)
          .setAttribute("width", elem_icon_width)
          .setAttribute("height", elem_icon_height);
      });
    } catch (err) {
      console.log("Error: " + err.message);
      console.log("RETRY WITH GRAPHQL");
      //console.log(message.body);

      let obj = JSON.parse(JSON.parse(message.body));

      console.log("OBJ");
      console.log(obj);

      let self = this;

      obj.basicElementsVO.forEach(element => {
        //initialize the proxy
        let elementQuery = webix.proxy(
          "GraphQL",
          `
					query ($elem: String!) {
						getElement(elementId: $elem)
						{
							id
              state
              elementType {
                id
                alias
              }
							elementValues
							{
								id
								value
								alerted
								elementTypeParam {
									description
								}
							}
						}
					}
					`
        );

        // loads the data
        elementQuery
          .load({
            elem: element.id
          })
          .then(data => {
            // process the received data
            let element = data;            
            // TODO: THIS DOESN'T WORK!!! F*CKING CALLBACK HELL!!!
            /*
            let elem_icon = self.getElementIcon(element);
            if(typeof elem_icon != 'undefined') {
              elem_icon = elem_icon.icon;
            }
            */

            let elem_icon = "";

            //let elem_icon_width = this.getElementIcon(element).width;
            //let elem_icon_height = this.getElementIcon(element).height;

            // icon based on element value
            if (element.elementType.id === "ElementType:9") {   // Fan
              if(element.state === 1) {
                let fan_cmd = element.elementValues.find(
                  ic => ic.elementTypeParam.description === "comand for fan"
                );
                elem_icon = staticsIcons[element.elementType["id"]]["state"].find(
                  ic => ic.fan_cmd === parseInt(fan_cmd.value)
                );

              } else {  // states 0, 2 and 3
                if (typeof staticsIcons[element.elementType.id] != "undefined") {
                  elem_icon = staticsIcons[element.elementType.id]["state"].find(
                    ic => ic.state === element.state
                  );
                } else {
                  elem_icon = staticsIcons["ElementType:33"]["state"].find(
                    ic => ic.state === element.state
                  );
                }
              }
              
              if (typeof elem_icon != "undefined") {
                elem_icon = elem_icon.icon;
              }

            } else if (element.elementType.id === "ElementType:8") {    // Barrier
              if(element.state === 1) {

                let bar_cmd = element.elementValues.find(
                  ic => ic.elementTypeParam.description === "comand for barrier"
                );

                elem_icon = staticsIcons[element.elementType.id]["state"].find(
                  ic => ic.bar_cmd === parseInt(bar_cmd.value)
                );

              } else {  // states 0, 2 and 3
                if (typeof staticsIcons[element.elementType.id] != "undefined") {
                  elem_icon = staticsIcons[element.elementType.id]["state"].find(
                    ic => ic.state === element.state
                  );
                } else {
                  elem_icon = staticsIcons["ElementType:33"]["state"].find(
                    ic => ic.state === element.state
                  );
                }
              }

              if (typeof elem_icon != "undefined") {
                elem_icon = elem_icon.icon;
              } else {
                elem_icon = "";
              }

            } else if (element.elementType.id === "ElementType:7") {    // Tunnel
              // icon based on status
              if (typeof staticsIcons[element.elementType.id] != "undefined") {
                elem_icon = staticsIcons[element.elementType.id]["state"].find(
                  ic => ic.state === element.state
                );
              } else {
                elem_icon = staticsIcons["ElementType:33"]["state"].find(
                  ic => ic.state === element.state
                );
              }

              if (typeof elem_icon != "undefined") {
                //width = elem_icon.width;
                //height = elem_icon.height;
                elem_icon = elem_icon.icon;
              } else {
                elem_icon = "";
              }

              // Change title bar color if it's in alarm state
              // FIXME: it's set to Rio Verde tunnel just for the demo, it should be general to apply for other tunnels
              if(document.getElementById("Element:7:1:Bar") != null) {
                if(element.state == 3) {
                  document.getElementById("Element:7:1:Bar").setAttribute("fill", "#ff0000");
                } else {
                  document.getElementById("Element:7:1:Bar").setAttribute("fill", "#ebb700");
                }
              }
            
            } else if (element.elementType.id === "ElementType:10") {   // DMS
              // icon based on status
              if(element.state != "1") {
                // Reset text lines
                document.getElementById(element.id + ':linea1').innerHTML = "";
                document.getElementById(element.id + ':linea2').innerHTML = "";
                document.getElementById(element.id + ':linea3').innerHTML = "";

                if(document.getElementById(element.id + ':rect2250-2-0') != null && document.getElementById(element.id + ':rect2085-9') != null) {
                  if(element.state == "0") {
                    document.getElementById(element.id + ':rect2250-2-0').style["stroke"] = "#ffcc00";
                    document.getElementById(element.id + ':rect2085-9').style["stroke"] = "#ffcc00";
                  } else if(element.state == "2") {
                    document.getElementById(element.id + ':rect2250-2-0').style["stroke"] = "#800080";
                    document.getElementById(element.id + ':rect2085-9').style["stroke"] = "#800080";
                  } else if(element.state == "3") {
                    document.getElementById(element.id + ':rect2250-2-0').style["stroke"] = "#ff0000";
                    document.getElementById(element.id + ':rect2085-9').style["stroke"] = "#ff0000";
                  }
                }
                
              } else {
                // check for the status message we should use
                // FIXME: this is just for the demo, it is not a real DMS
                let messageToShow = element.elementValues.find(
                  ic => ic.elementTypeParam.description === "comand for rotapanel"
                );

                let message1 = "~~~######~~~";

                document.getElementById(element.id + ':rect2250-2-0').style["stroke"] = "#c0c0c0";
                document.getElementById(element.id + ':rect2085-9').style["stroke"] = "#c0c0c0";
                
                if(messageToShow.value == "1") {
                  let tmp_message1 =
                    element.elementValues.find(
                      ic => ic.elementTypeParam.description === "text for status 1"
                    );
  
                  if (typeof tmp_message1 != 'undefined') {
                    message1 =
                      "~~~###" +
                      element.elementValues.find(
                        ic => ic.elementTypeParam.description === "text for status 1"
                      ).value +
                      "###~~~";
                  }
                } else if(messageToShow.value == "2") {
                  let tmp_message1 =
                    element.elementValues.find(
                      ic => ic.elementTypeParam.description === "text for status 2"
                    );
  
                  if (typeof tmp_message1 != 'undefined') {
                    message1 =
                      "~~~###" +
                      element.elementValues.find(
                        ic => ic.elementTypeParam.description === "text for status 2"
                      ).value +
                      "###~~~";
                  }
                }
  
                if (typeof message1 != "undefined") {
                  let linea1 = message1.split("~~~")[1].split("###")[0];
                  let linea2 = message1.split("~~~")[1].split("###")[1];
                  let linea3 = message1.split("~~~")[1].split("###")[2];
  
                  document.getElementById(element.id + ':linea1').innerHTML = linea1;
                  document.getElementById(element.id + ':linea2').innerHTML = linea2;
                  document.getElementById(element.id + ':linea3').innerHTML = linea3;
                }
              }

            } else {    // FIX FOR CALLBACK HELL
              // icon based on status
              if (typeof staticsIcons[element.elementType.id] != "undefined") {
                elem_icon = staticsIcons[element.elementType.id]["state"].find(
                  ic => ic.state === element.state
                );
              } else {
                elem_icon = staticsIcons["ElementType:33"]["state"].find(
                  ic => ic.state === element.state
                );
              }

              if (typeof elem_icon != "undefined") {
                //width = elem_icon.width;
                //height = elem_icon.height;
                elem_icon = elem_icon.icon;
              } else {
                elem_icon = "";
              }
            }

            // replace the icon
            document
              .getElementById(element.id)
              .setAttribute("href", "./img/" + elem_icon);
              //.setAttribute("width", elem_icon_width)
              //.setAttribute("height", elem_icon_height);
          });
      });
    }
  }

  getComponent() {
    if (this.data.includes("http") || this.data.includes("GraphQL")) {
      this.metadata.url = this.data;
    } else {
      this.metadata.data = this.data;
    }
    return this.metadata;
  }

  handleLayers(e) {
    let els = this.s.selectAll('[component="' + e + '"]');

    // Hides device
    if (this.elementsToDraw[e] == "visible") {
      this.elementsToDraw[e] = "hidden";
    } else {
      this.elementsToDraw[e] = "visible";
    }

    els.forEach(el => {
      console.log(el);
      el.attr("visibility", this.elementsToDraw[e]);
    });
  }

  // got from Github
  elementReady(selector) {
    return new Promise((resolve, reject) => {
      let el = document.querySelector(selector);
      if (el) {
        resolve(el);
      }
      new MutationObserver((mutationRecords, observer) => {
        // Query for elements matching the specified selector
        Array.from(document.querySelectorAll(selector)).forEach(element => {
          resolve(element);
          //Once we have resolved we don't need the observer anymore.
          observer.disconnect();
        });
      }).observe(document.documentElement, {
        childList: true,
        subtree: true
      });
    });
  }

  loadData() {
    this.data
      .load()
      .then(d => {
        // creates the base layer
        this.baseLayer = d.icon;

        // get the container size
        let c = document.querySelector('[view_id="' + this.synopticDiv + '"]');
        this.s = Snap("#" + this.synopticDiv + "SVG");
        let total_width = c.offsetWidth;
        let total_height = c.offsetHeight;

        // set the aspect ratios
        this.ratioWidth = total_width / 1920;
        this.ratioHeight = total_height / 1080;

        // set width and height
        this.s.attr({ width: total_width, height: total_height });

        // add basemap and base road to elements_to_draw
        this.elementsTypes.push("BASE_LAYER");
        this.elementsToDraw["BASE_LAYER"] = "visible";

        // if we are in a tunnel synoptic, put the title bar
        if(this.synopticType == "tunnel") {
          
          let titleBackground = this.s.rect(36 * this.ratioWidth, total_height - (48 * this.ratioHeight) - (1004 * this.ratioHeight), 1722 * this.ratioWidth, 58 * this.ratioHeight);

          // It's set just for the demo
          if(d.state != 3) {
            titleBackground.attr({
              fill: '#ebb700'
            });
          } else {
            titleBackground.attr({
              fill: '#ff0000'
            });
          }
          
          // FIXME: The id should be: 'id: d.id + ":Bar"' in a generic way
          titleBackground.attr({
            id: d.id + ":Bar",
            class: "capa_mapa"
          });

          // FIXME: there is no "Rio Verde" tunnel in the DB, just "Tunnel 1" so we set it manually
          // We should bring modify the GraphQL query (add "description" field) and bring it from the DB
          let titleText = this.s.text(806 * this.ratioWidth, total_height - (15 * this.ratioHeight) - 1011 * this.ratioHeight, "RIO VERDE");
          let subTitleText = this.s.text(855 * this.ratioWidth, total_height - 1009 * this.ratioHeight, "290 m");
          titleText.attr({
            'font-size': 28 * this.ratioHeight,
            'fill': "#ffffff",
            'class': 'capa_mapa'
          });
          subTitleText.attr({
            'font-size': 18 * this.ratioHeight,
            'fill': "#ffffff",
            'class': 'capa_mapa'
          });
        }

        // draws the base layer
        this.s
          .image("./img/" + this.baseLayer, 0, 0, total_width, total_height)
          .attr({
            id: "capa_base",
            visibility: this.elementsToDraw["BASE_LAYER"],
            component: "BASE_LAYER"
          });

        return d;
      })
      .then(d => {
        d.childs.forEach(element => {
          //console.log(element.positionX + " --> " + element.positionY);

          if(element.id in this.synopticConf) {
            let synopticElement = this.synopticConf[element.id];
          
            this.elements.push(element);
            // draw elements
            let elem_icon = this.getElementIcon(element).icon;
            let elem_icon_width = this.synopticConf[element.id].width;
            let elem_icon_height = this.synopticConf[element.id].height;

            // add the elementType to the array of elements to be drawn and the elementTypes array
            if (!this.elementsTypes.includes(element.elementType.alias)) {
              this.elementsTypes.push(element.elementType.alias);
              this.elementsToDraw[element.elementType.alias] = "visible";
            }

            // check for special icons: DMS and ETD
            if (element.elementType.id === "ElementType:10") {
              let scale_x = (this.synopticConf[element.id].width / 55) * this.ratioWidth;
              let scale_y = (this.synopticConf[element.id].height / 16) * this.ratioHeight;

              // check for the status message we should use
              // FIXME: this is just for the demo, it is not a real DMS
              let messageToShow = element.elementValues.find(
                ic => ic.elementTypeParam.description === "comand for rotapanel"
              );

              let message1 = "~~~######~~~";
              
              if(messageToShow.value == "1") {
                let tmp_message1 =
                  element.elementValues.find(
                    ic => ic.elementTypeParam.description === "text for status 1"
                  );

                if (typeof tmp_message1 != 'undefined') {
                  message1 =
                    "~~~###" +
                    element.elementValues.find(
                      ic => ic.elementTypeParam.description === "text for status 1"
                    ).value +
                    "###~~~";
                }
              } else if(messageToShow.value == "2") {
                let tmp_message1 =
                  element.elementValues.find(
                    ic => ic.elementTypeParam.description === "text for status 2"
                  );

                if (typeof tmp_message1 != 'undefined') {
                  message1 =
                    "~~~###" +
                    element.elementValues.find(
                      ic => ic.elementTypeParam.description === "text for status 2"
                    ).value +
                    "###~~~";
                }
              }

              if (typeof message1 != "undefined") {
                let linea1 = message1.split("~~~")[1].split("###")[0];
                let linea2 = message1.split("~~~")[1].split("###")[1];
                let linea3 = message1.split("~~~")[1].split("###")[2];

                let pmv_str =
                  '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" > <rect class="capa_mapa" style="fill:#000000;fill-opacity:1;stroke:#c0c0c0;stroke-width:0.79649729;stroke-miterlimit:4.32999992;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="' + element.id + ':rect2250-2-0" width="13.686104" height="13.755985" x="0.39483935" y="282.84232" /> <rect class="capa_mapa" style="fill:#000000;fill-opacity:1;stroke:#c0c0c0;stroke-width:0.79649729;stroke-miterlimit:4.32999992;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="' + element.id + ':rect2085-9" width="38.440899" height="13.75597" x="14.080923" y="282.84232" /> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:2.50368834px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.06259221" x="15.905216" y="286.23163" id="text2462" transform="scale(0.99745676,1.0025497)"><tspan sodipodi:role="line" x="15.905216" y="286.23163" id="tspan2460" style="stroke-width:0.06259221"><tspan x="15.905216" y="286.23163" style="font-style:normal;font-variant:normal;font-weight:normal;font-stretch:normal;font-family:\'Erbos Draco Open NBP\';-inkscape-font-specification:\'Erbos Draco Open NBP\';fill:#ffff00;fill-opacity:1;stroke-width:0.06259221" id="' + element.id + ':linea1">' +
                  linea1 +
                  ' </tspan></tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:2.50368834px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.06259221" x="15.960894" y="289.95462" id="text2468" transform="scale(0.99745676,1.0025497)"><tspan sodipodi:role="line" x="15.960894" y="289.95462" id="tspan2466" style="stroke-width:0.06259221"><tspan x="15.960894" y="289.95462" style="font-style:normal;font-variant:normal;font-weight:normal;font-stretch:normal;font-family:\'Erbos Draco Open NBP\';-inkscape-font-specification:\'Erbos Draco Open NBP\';fill:#ffff00;fill-opacity:1;stroke-width:0.06259221" id="' + element.id + ':linea2">' +
                  linea2 +
                  '</tspan></tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:2.50368834px;line-height:0;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.06259221" x="15.943024" y="293.65649" id="text2474" transform="scale(0.99745676,1.0025497)"><tspan sodipodi:role="line" x="15.943024" y="293.65649" id="tspan2472" style="stroke-width:0.06259221"><tspan x="15.943024" y="293.65649" style="font-style:normal;font-variant:normal;font-weight:normal;font-stretch:normal;font-family:\'Erbos Draco Open NBP\';-inkscape-font-specification:\'Erbos Draco Open NBP\';fill:#ffff00;fill-opacity:1;stroke-width:0.06259221" id="' + element.id + ':linea3">' +
                  linea3 +
                  '</tspan></tspan></text></g>';
                let pmv_icon = Snap.parse(pmv_str);

                let el = this.s.group().append(pmv_icon);

                if (el != null) {

                  el.transform(
                    "T" +
                      (80  * this.ratioWidth + synopticElement.x * this.ratioWidth) +
                      "," +
                      (-282 +
                        this.baseHeight -
                        this.synopticConf[element.id].height * this.ratioHeight -
                        synopticElement.y * this.ratioHeight) +
                      ",s" +
                      scale_x +
                      "," +
                      scale_y
                  );
                  

                  el.attr({ id: element.id });
                  el.attr({ class: "capa_mapa" });
                  el.attr({ name: element.id });
                  el.attr({ type: element.elementType["id"] });
                  el.attr({ component: element.elementType.alias });

//                  el.click(function(e) {
//                    webix.message("HELLO1: " + element.id);
//                  });
                }

                // adds the pictograph
                // currently its fixed to pic1.jpg
                let pmv_svg = document.getElementById(element.id);
                let pmv_pic = document.getElementById(element.id + ":rect2250-2-0"); // selects the pictograph rectangle
                let r = Snap(pmv_pic);
                let rpg = r.parent().group();
                let rpg_image = rpg.image(
                  "./img/pic1.jpg",
                  r.node.getAttribute("x"),
                  r.node.getAttribute("y"),
                  r.node.getAttribute("width"),
                  r.node.getAttribute("height")
                  );
                // adds a class to the image so it can be resized in a responsive way
                rpg_image.addClass("capa_mapa");
              }
            } else if (element.elementType.alias === "ETD") {
              let etd_str =
                '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-268.68957)"> <rect class="capa_mapa" style="fill:#c3e6a1;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93" width="28.228191" height="5.6456451" x="0.041112646" y="268.73068" ry="0" />  class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-0" width="28.228191" height="5.6456451" x="0.041112646" y="280.02197" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-6" width="28.228191" height="5.6456451" x="0.041112646" y="274.37631" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-62" width="28.228191" height="5.6456451" x="0.041112646" y="285.6676" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-61" width="28.228191" height="5.6456451" x="0.041112646" y="291.31323" ry="0" /> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="2.465615" y="277.72061" id="text4070-1-2-2" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="2.465615" y="277.72061" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan4072-0-0-8">I</tspan><tspan sodipodi:role="line" x="2.465615" y="283.56769" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1689">V</tspan><tspan sodipodi:role="line" x="2.465615" y="289.41479" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1691">O</tspan><tspan sodipodi:role="line" x="2.465615" y="295.26187" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693">C</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.904327" y="272.36899" id="titulo" transform="scale(0.99833799,1.0016648)" inkscape:label="#titulo"><tspan sodipodi:role="line" x="13.904327" y="272.36899" style="font-size:3.15464282px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-0">' +
                element.id +
                '</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.086432" y="277.66467" id="galleta_texto1" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="13.086432" y="277.66467" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-9">' +
                Math.floor(Math.random() * 10001) +
                '</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="23.677027" y="277.66467" id="text4070-1-2-2-22" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="23.677027" y="277.66467" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan4072-0-0-8-8">V/h</tspan><tspan sodipodi:role="line" x="23.677027" y="283.51175" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1689-9">K/h</tspan><tspan sodipodi:role="line" x="23.677027" y="289.35886" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1691-7">%</tspan><tspan sodipodi:role="line" x="23.677027" y="295.20593" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-3">%L</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.15081" y="283.57147" id="galleta_texto2" transform="scale(0.99833797,1.0016648)"><tspan sodipodi:role="line" x="13.15081" y="283.57147" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-9-3">' +
                Math.floor(Math.random() * 10001) +
                '</tspan></text> <text transform="scale(0.99833797,1.0016648)" id="galleta_texto3" y="289.38263" x="13.15081" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" xml:space="preserve"><tspan id="tspan2140" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" y="289.38263" x="13.15081" sodipodi:role="line">' +
                Math.floor(Math.random() * 10001) +
                '</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.15081" y="295.19379" id="galleta_texto4" transform="scale(0.99833797,1.0016648)"><tspan sodipodi:role="line" x="13.15081" y="295.19379" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan2150">' +
                Math.floor(Math.random() * 10001) +
                "</tspan></text> </g>";
              
              let etd_icon = Snap.parse(etd_str);
              let el = this.s.group().append(etd_icon);

              let scale_x =
                staticsIcons[element.elementType["id"]]["size"]["width"] / 150;
              let scale_y =
                staticsIcons[element.elementType["id"]]["size"]["height"] / 150;

              if (el != null) {
                el.transform(
                  "T" +
                    synopticElement.x +
                    "," +
                    (this.baseHeight - synopticElement.y * this.ratioHeight) +
                    ",s" +
                    scale_x +
                    "," +
                    scale_y
                );

                el.attr({ id: element.id });
                el.attr({ name: element.id });
                el.attr({ type: element.elementType["id"] });
                el.attr({ component: element.elementType.alias });

//                el.click(function(e) {
//                  webix.message("HELLO2: " + element.id);
//                });
              }

            } else if (element.elementType.alias === "METEO") {
              let meteo_str =
                '<g inkscape:label="Capa 1" inkscape:groupmode="layer" id="layer1" transform="translate(0,-268.68957)"> <rect class="capa_mapa" style="fill:#c3e6a1;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93" width="28.228191" height="5.6456451" x="0.041112646" y="268.73068" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-0" width="28.228191" height="5.6456451" x="0.041112646" y="280.02197" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-6" width="28.228191" height="5.6456451" x="0.041112646" y="274.37631" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-62" width="28.228191" height="5.6456451" x="0.041112646" y="285.6676" ry="0" /> <rect class="capa_mapa" style="fill:#ececec;fill-opacity:1;stroke:#434041;stroke-width:0.08222529;stroke-miterlimit:5;stroke-dasharray:none;stroke-dashoffset:6;stroke-opacity:1;paint-order:fill markers stroke" id="rect4066-6-5-93-61" width="28.228191" height="5.6456451" x="0.041112646" y="291.31323" ry="0" /> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="2.465615" y="277.72061" id="text4070-1-2-2" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="2.465615" y="277.72061" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan4072-0-0-8">I</tspan><tspan sodipodi:role="line" x="2.465615" y="283.56769" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1689">V</tspan><tspan sodipodi:role="line" x="2.465615" y="289.41479" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1691">O</tspan><tspan sodipodi:role="line" x="2.465615" y="295.26187" style="font-size:3.85567474px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693">C</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.904327" y="272.36899" id="titulo" transform="scale(0.99833799,1.0016648)" inkscape:label="#titulo"><tspan sodipodi:role="line" x="13.904327" y="272.36899" style="font-size:3.15464282px;line-height:2.62886882px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-0">' +
                element.id +
                '</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.086432" y="277.66467" id="galleta_texto1" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="13.086432" y="277.66467" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-9">' +
                Math.floor(Math.random() * 10001) +
                '</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="23.677027" y="277.66467" id="text4070-1-2-2-22" transform="scale(0.99833799,1.0016648)"><tspan sodipodi:role="line" x="23.677027" y="277.66467" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan4072-0-0-8-8">V/h</tspan><tspan sodipodi:role="line" x="23.677027" y="283.51175" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1689-9">K/h</tspan><tspan sodipodi:role="line" x="23.677027" y="289.35886" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1691-7">%</tspan><tspan sodipodi:role="line" x="23.677027" y="295.20593" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-3">%L</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.15081" y="283.57147" id="galleta_texto2" transform="scale(0.99833797,1.0016648)"><tspan sodipodi:role="line" x="13.15081" y="283.57147" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan1693-9-3">' +
                Math.floor(Math.random() * 10001) +
                '</tspan></text> <text transform="scale(0.99833797,1.0016648)" id="galleta_texto3" y="289.38263" x="13.15081" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" xml:space="preserve"><tspan id="tspan2140" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" y="289.38263" x="13.15081" sodipodi:role="line">' +
                Math.floor(Math.random() * 10001) +
                '</tspan></text> <text xml:space="preserve" style="font-style:normal;font-weight:normal;font-size:10.51547241px;line-height:5.27088261px;font-family:sans-serif;letter-spacing:0px;word-spacing:0px;fill:#000000;fill-opacity:1;stroke:none;stroke-width:0.26288685" x="13.15081" y="295.19379" id="galleta_texto4" transform="scale(0.99833797,1.0016648)"><tspan sodipodi:role="line" x="13.15081" y="295.19379" style="font-size:3.85567474px;line-height:2.62886858px;text-align:center;text-anchor:middle;fill:#000000;stroke-width:0.26288685" id="tspan2150">' +
                Math.floor(Math.random() * 10001) +
                "</tspan></text> </g>";
              
              let etd_icon = Snap.parse(etd_str);
              let el = this.s.group().append(etd_icon);

              //let scale_x = 3.0;
              //let scale_y = 3.0;
              let scale_x =
                staticsIcons[element.elementType["id"]]["size"]["width"] / 150;
              let scale_y =
                staticsIcons[element.elementType["id"]]["size"]["height"] / 150;

              if (el != null) {
                el.transform(
                  "T" +
                    synopticElement.x +
                    "," +
                    (this.baseHeight - staticsIcons[element.elementType["id"]]["size"]["height"] - synopticElement.y * this.ratioHeight) +
                    ",s" +
                    scale_x +
                    "," +
                    scale_y
                );

                el.attr({ id: element.id });
                el.attr({ name: element.id });
                el.attr({ type: element.elementType["id"] });
                el.attr({ component: element.elementType.alias });

//                el.click(function(e) {
//                  webix.message("HELLO3: " + element.id);
//                });
              }

            } else {
              let im = this.s
                .image(
                  "./img/" + elem_icon,
                  synopticElement.x * this.ratioWidth,
                  (1080 - elem_icon_height - synopticElement.y) * this.ratioHeight,
                  elem_icon_width * this.ratioWidth,
                  elem_icon_height * this.ratioHeight
                )
                .attr({
                  id: element.id,
                  component: element.elementType.alias,
                  class: "capa_mapa",
                  visibility: "visible"
                });

              // check if the item is a tunnel
              // FIXME: ONLY FOR THE DEMO
              if (element.elementType.id === "ElementType:7") {
                im.click(function(e) {
                  $$('synoptic1').show();
                });

                // FIXME: this is for the global synoptic, adds the title to the tunnel
                if(this.synopticType == "global") {
                  let c = document.querySelector('[view_id="' + this.synopticDiv + '"]');
                  let total_width = c.offsetWidth;
                  let total_height = c.offsetHeight;
                  let titleBackground = this.s.rect(synopticElement.x * this.ratioWidth, total_height - (30 * this.ratioHeight) - (synopticElement.y * this.ratioHeight), synopticElement.width * this.ratioWidth, synopticElement.height * this.ratioHeight);

                  im.remove();

                  // It's set just for the demo
                  if(element.state != 3) {
                    titleBackground.attr({
                      fill: '#ebb700'
                    });
                  } else {
                    titleBackground.attr({
                      fill: '#ff0000'
                    });
                  }
                  
                  // FIXME: The id should be: 'id: d.id + ":Bar"' in a generic way
                  titleBackground.attr({
                    id: element.id + ":Bar",
                    class: "capa_mapa"
                  });

                  // FIXME: there is no "Rio Verde" tunnel in the DB, just "Tunnel 1" so we set it manually
                  // We should bring modify the GraphQL query (add "description" field) and bring it from the DB
                  //let titleText = this.s.text((synopticElement.x + 105) * this.ratioWidth, total_height - 781 * this.ratioHeight, element.description);
                  let titleText = this.s.text((synopticElement.x + 105) * this.ratioWidth, total_height - 776 * this.ratioHeight, "RIO VERDE");
                  titleText.attr({
                    'font-size': 21 * this.ratioHeight,
                    'fill': "#ffffff",
                    'class': 'capa_mapa'
                  });
              
                  titleBackground.click(function(e) {
                    $$('synoptic1').show();
                  });
                }
              } else {
                im.click(function(e) {
            	try {
            		if($$("win"+element.id)!=null){
            			if("Element:2:1"===element.id){
                			$$("win"+element.id).show();
                		}else{
                			 $$("win"+element.id).destructor();
                			 let elementForm = new ElementForm(element.id);
 	                        webix.ui({
 	                      	  id:"win"+element.id,
 	                        	  view:"window", 
 	                        	  move:true,
 	                        	  close: true,
 	                        	  position:"right",
 	                        	  body:elementForm.getComponent()
 	                        	}).show();
                		}	
            		}else{
            			let elementForm = new ElementForm(element.id);
                        webix.ui({
                      	  id:"win"+element.id,
                        	  view:"window", 
                        	  move:true,
                        	  close: true,
                        	  position:"right",
                        	  body:elementForm.getComponent()
                        	}).show();
            		}
            	} catch (e) {
					// TODO: handle exception
				}
	            });		
            		
				
              }
            }
          }
        });

        return d;
      })
      .then(d => {
        // draw the buttons for filtering the element type
        let self = this;
        
        // adds the dropdown button
        let buttonToAdd = document.createElement("button");
        //let buttonText = document.createTextNode("Layers");
        //buttonText.innerHTML = "<b>TEST</b>";
        buttonToAdd.innerHTML = "<span class='webix_icon wxi-drag'></span>";
        buttonToAdd.setAttribute("class", "dropbtn");
        //buttonToAdd.appendChild(buttonText);
        document.getElementById(this.synopticDiv + "dropdownDiv").appendChild(buttonToAdd);

        // adds the dropdown div
        let divToAdd = document.createElement("div");
        divToAdd.setAttribute("id", this.synopticDiv + "dropdownDivContent");
        divToAdd.setAttribute("class", "dropdown-content");
        document.getElementById(this.synopticDiv + "dropdownDiv").appendChild(divToAdd);
        
        this.elementsTypes.map(n => {

          let linkToAdd = document.createElement("a");
          let linkText = document.createTextNode(n);
          linkToAdd.onclick = function() {
            self.handleLayers(n);
          };
          linkToAdd.appendChild(linkText);
          document.getElementById(this.synopticDiv + "dropdownDivContent").appendChild(linkToAdd);

        });

        document.getElementById(this.synopticDiv + "spinner").style.display = "none";

      });
  }

  /*
   *	Initializes everything
   */
  init(synopticConf) {
    if(synopticConf == "tunnel") {
      this.synopticConf = synopticTunnel;
    } else if(synopticConf == "metro") {
      this.synopticConf = synopticMetro;
    } else if(synopticConf == "global") {
      this.synopticConf = synopticGlobal;
    }

    this.synopticType = synopticConf;

    let self = this;
    // waits for the element and starts drawing
    this.elementReady('[view_id="' + this.synopticDiv + '"]').then(c => {
      let spinnerDivToAdd = document.createElement("div");
      spinnerDivToAdd.setAttribute("id", this.synopticDiv + "spinner");
      spinnerDivToAdd.setAttribute("class", "spinner");
      document.querySelector('[view_id="' + this.synopticDiv + '"]').prepend(spinnerDivToAdd);

      var svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
      document.querySelector('[view_id="' + this.synopticDiv + '"]').prepend(svg);
      svg.setAttribute("id", this.synopticDiv + "SVG");
      svg.setAttribute("width", c.offsetWidth);
      svg.setAttribute("height", c.offsetHeight);

      self.baseWidth = c.offsetWidth;
      self.baseHeight = c.offsetHeight;

      let divToAdd = document.createElement("div");
      divToAdd.setAttribute("id", this.synopticDiv + "dropdownDiv");
      divToAdd.setAttribute("class", "dropdown");
      document.querySelector('[view_id="' + this.synopticDiv + '"]').prepend(divToAdd);

      this.elementReady("#" + this.synopticDiv + "SVG").then(c1 => {
        // loads data and draws for first time
        this.loadData();
      });


      this.initResponsive();
    });
  }

  /*
   * Resize the synoptic if the parent element changes its size
   */
  initResponsive() {
    let e = document.getElementsByClassName("webix_sidebar")[0];

    let self = this;

    let observer = new MutationObserver(function(event) {
      let c = document.querySelector('[view_id="' + self.synopticDiv + '"]');
      let svg = document.getElementById(self.synopticDiv + "SVG");
      svg.setAttribute("width", c.offsetWidth);
      svg.setAttribute("height", c.offsetHeight);

      let ratioWidth = c.offsetWidth / self.baseWidth;
      let ratioHeight = c.offsetHeight / self.baseHeight;

      // scales the base layer
      let base = document.querySelector('[component="BASE_LAYER"]');
      base.setAttribute("width", self.baseWidth * ratioWidth);
      base.setAttribute("height", self.baseHeight * ratioHeight);

      // scales all the images of the svg (class == "capa_mapa")
      for (
        let i = 0;
        i < document.getElementsByClassName("capa_mapa").length;
        i++
      ) {
        let item = document.getElementsByClassName("capa_mapa")[i];

        // update size
        item.setAttribute("width", item.getAttribute("width") * ratioWidth);
        item.setAttribute("height", item.getAttribute("height") * ratioHeight);

        // update position
        item.setAttribute("x", item.getAttribute("x") * ratioWidth);
        item.setAttribute("y", item.getAttribute("y") * ratioHeight);

        // check if the element has a transform attribute (i.e.: for DMS)
        if(item.getAttribute("transform") != null) {
          item.transform.baseVal[0].matrix["e"] = item.transform.baseVal[0].matrix["e"] * ratioWidth;
          item.transform.baseVal[0].matrix["f"] = item.transform.baseVal[0].matrix["f"] * ratioHeight;
        }
      }

      // update the base size
      self.baseWidth = self.baseWidth * ratioWidth;
      self.baseHeight = self.baseHeight * ratioHeight;
    });

    observer.observe(e, {
      attributes: true,
      attributeFilter: ["class"],
      childList: false,
      characterData: false
    });
  }
}