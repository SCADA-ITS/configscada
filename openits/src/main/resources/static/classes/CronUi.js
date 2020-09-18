var v_month = {
    "1":"JAN", 
    "2":"FEB", 
    "3":"MAR",
    "4":"APR",
    "5":"MAY",
    "6":"JUN",
    "7":"JUL",
    "8":"AUG",
    "9":"SEP",
    "10":"OCT",
    "11":"NOV",
    "12":"DEC"
};

var v_month2 = {
    "JAN":{id:  "1",value: $.i18n("cronUi.popup.month.1" )}, 
    "FEB":{id:  "2",value: $.i18n("cronUi.popup.month.2" )}, 
    "MAR":{id:  "3",value: $.i18n("cronUi.popup.month.3" )},
    "APR":{id:  "4",value: $.i18n("cronUi.popup.month.4" )},
    "MAY":{id:  "5",value: $.i18n("cronUi.popup.month.5" )},
    "JUN":{id:  "6",value: $.i18n("cronUi.popup.month.6" )},
    "JUL":{id:  "7",value: $.i18n("cronUi.popup.month.7" )},
    "AUG":{id:  "8",value: $.i18n("cronUi.popup.month.8" )},
    "SEP":{id:  "9",value: $.i18n("cronUi.popup.month.9" )},
    "OCT":{id: "10",value: $.i18n("cronUi.popup.month.10")},
    "NOV":{id: "11",value: $.i18n("cronUi.popup.month.11")},
    "DEC":{id: "12",value: $.i18n("cronUi.popup.month.12")}
};

var v_month3 = {
    "JAN":"1", 
    "FEB":"2", 
    "MAR":"3",
    "APR":"4",
    "MAY":"5",
    "JUN":"6",
    "JUL":"7",
    "AUG":"8",
    "SEP":"9",
    "OCT":"10",
    "NOV":"11",
    "DEC":"12"
};

export default class CronUi {
	constructor(input_id,popupId) {
        var input = input_id;
        this.input = input;
        var popupId = popupId;

        this.inputsHeader = {
            view: "form",
            id: "inputsHeader",
            width: 0,
            margin: 0,
            elements: [ 
                {
                    cols:[
                        { view: "text", id: "inputsHeaderSeconds", name: "seconds", value: "*", disabled:true},
                        { view: "text", id: "inputsHeaderMinutes", name: "minutes", value: "*", disabled:true},
                        { view: "text", id: "inputsHeaderHours",   name: "hours", value: "*", disabled:true},
                        { view: "text", id: "inputsHeaderDays",    name: "days", value: "*", disabled:true},
                        { view: "text", id: "inputsHeaderMonths",  name: "months", value: "*", disabled:true},
                        //{ view: "text", id: "inputsHeaderWeeks",   name: "weeks"},
                        { view: "text", id: "inputsHeaderYears",   name: "years", value: "*", disabled:true},
                    ],
                }
            ]
        };

        this.form = {
            id: "tabview_CronUi",
            view:"tabview", 
            tabbar : { 
                options:[ 
                    { id:"cronUi_seconds", value:$.i18n("cronUi.popup.tab.seconds"), width:100 },
                    { id:"cronUi_minutes", value:$.i18n("cronUi.popup.tab.minutes"), width:100 },
                    { id:"cronUi_hours",   value:$.i18n("cronUi.popup.tab.hours"),   width:100 },
                    { id:"cronUi_days",    value:$.i18n("cronUi.popup.tab.days"),    width:100 },
                    { id:"cronUi_months",  value:$.i18n("cronUi.popup.tab.months"),  width:100 },
                    //{ id:"cronUi_weeks",   value:$.i18n("cronUi.popup.tab.weeks"),   width:100 },
                    { id:"cronUi_years",   value:$.i18n("cronUi.popup.tab.years"),   width:100 },
                    //{ id:"cronUi_help",    value:$.i18n("cronUi.popup.tab.help"),    width:100 },
                ]
            },
            cells:[
                { 
                    view: "form",
                    id: "cronUi_seconds",
                    width: 0,
                    margin: 0,
                    elements: [
                        {
                            rows: [
                                {
                                    view:"checkbox", 
                                    id:"secondsOpt01", 
                                    labelRight:$.i18n("cronUi.popup.secondsOpt01"), 
                                    value:1,
                                    click: function(id,event){
                                        if($$("secondsOpt01").data.value === 1){
                                            $$("inputsHeaderSeconds").setValue("*");
                                            //$$("secondsOpt01").setValue(0);
                                            $$("secondsOpt02").setValue(0);
                                            $$("secondsOpt03").setValue(0);
                                            $$("secondsOpt04").setValue(0);
                                        }
                                    }
                                },
                                {
                                    cols:[
                                        {
                                            view:"checkbox", 
                                            id:"secondsOpt02", 
                                            labelRight:$.i18n("cronUi.popup.secondsOpt02.01"), 
                                            //labelWidth: 80,
                                            value:0,
                                            click: function(id,event){
                                                let counter01 = $$("secondsOpt02_counter01").getValue();
                                                let counter02 = $$("secondsOpt02_counter02").getValue();

                                                if($$("secondsOpt02").data.value === 1){
                                                    $$("inputsHeaderSeconds").setValue(counter02 + "/" + counter01);
                                                    //$$("secondsOpt01").setValue(0);
                                                    $$("secondsOpt01").setValue(0);
                                                    $$("secondsOpt03").setValue(0);
                                                    $$("secondsOpt04").setValue(0);
                                                }
                                            }
                                        },
                                        { 
                                            view: "counter", 
                                            id: "secondsOpt02_counter01" ,
                                            name: "", 
                                            value: 1, 
                                            min: 1, 
                                            max: 60,
                                            click: function(id,event){
                                                let counter01 = $$("secondsOpt02_counter01").getValue();
                                                let counter02 = $$("secondsOpt02_counter02").getValue();
                                                $$("inputsHeaderSeconds").setValue(counter02 + "/" + counter01);
                                            }
                                        },
                                        { 
                                            view:"label", 
                                            label: $.i18n("cronUi.popup.secondsOpt02.02"), 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { 
                                            view: "counter", id: "secondsOpt02_counter02" ,name: "", 
                                            click: function(id,event){
                                                let counter01 = $$("secondsOpt02_counter01").getValue();
                                                let counter02 = $$("secondsOpt02_counter02").getValue();
                                                $$("inputsHeaderSeconds").setValue(counter02 + "/" + counter01);
                                            },
                                            value: 0, min: 0, max: 59},
                                        { gravity:1}
                                    ]
                                },
                                {
                                    view:"checkbox", 
                                    id:"secondsOpt03", 
                                    labelRight:$.i18n("cronUi.popup.secondsOpt03"), 
                                    value:0,
                                    click: function(id,event){
                                        if($$("secondsOpt03").data.value === 1){
                                            let multi = $$("secondsOpt03_combo").getValue();
                                            $$("inputsHeaderSeconds").setValue(multi);
                                            
                                            //$$("secondsOpt01").setValue(0);
                                            $$("secondsOpt01").setValue(0);
                                            $$("secondsOpt02").setValue(0);
                                            $$("secondsOpt04").setValue(0);
                                        }
                                    }
                                },
                                {
                                    cols:[
                                        {},
                                        { view:"multicombo", id: "secondsOpt03_combo", name: "secondsOpt03_combo", value: "0", //placeholder:"Tags",
                                            options:["0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"], //newValues: true
                                            on: {
                                                'onChange': function(obj){ 
                                                    if($$("secondsOpt03").data.value === 1){
                                                        let multi = $$("secondsOpt03_combo").getValue();
                                                        $$("inputsHeaderSeconds").setValue(multi);
                                                    }
                                                }
                                            }
                                        },
                                        {gravity:2}
                                    ]
                                },
                                {
                                    cols:[
                                        {
                                            view:"checkbox", 
                                            id:"secondsOpt04", 
                                            //labelwidth: 200,
                                            labelRight: $.i18n("cronUi.popup.secondsOpt04.01"),
                                            //labelWidth: 0, 
                                            value:0,
                                            click: function(id,event){
                                                if($$("secondsOpt04").data.value === 1){
                                                    let counter01 = $$("secondsOpt04_counter01").getValue();
                                                    let counter02 = $$("secondsOpt04_counter02").getValue();                                            
                                                    $$("inputsHeaderSeconds").setValue(counter01 + "-" + counter02);
                                                    //$$("secondsOpt01").setValue(0);
                                                    $$("secondsOpt01").setValue(0);
                                                    $$("secondsOpt02").setValue(0);
                                                    $$("secondsOpt04").setValue(0);
                                                }
                                            }
                                        },
                                        
                                        { 
                                            view:"label", 
                                            label: $.i18n("cronUi.popup.secondsOpt04.02"),
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        
                                        { view: "counter", name: "", id: "secondsOpt04_counter01", 
                                            value: 0, 
                                            click: function(id,event){
                                                let counter01 = $$("secondsOpt04_counter01").getValue();
                                                let counter02 = $$("secondsOpt04_counter02").getValue();
                                                $$("inputsHeaderSeconds").setValue(counter01 + "-" + counter02);
                                            },
                                            min: 0, max: 59
                                        },
                                        /*
                                        { 
                                            view:"label", 
                                            label: $.i18n("cronUi.popup.secondsOpt04.03"), 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        */
                                        { view: "counter", name: "", id: "secondsOpt04_counter02", 
                                            value: 0, 
                                            click: function(id,event){
                                                let counter01 = $$("secondsOpt04_counter01").getValue();
                                                let counter02 = $$("secondsOpt04_counter02").getValue();
                                                $$("inputsHeaderSeconds").setValue(counter01 + "-" + counter02);
                                            },
                                            min: 0, max: 59
                                        },
                                        //{ gravity:1}
                                    ]
                                }
                            ]
                        }
                    ],              
                },
                { 
                    view: "form",
                    id: "cronUi_minutes",
                    width: 0,
                    margin: 0,
                    elements: [
                        {
                            rows: [
                                {
                                    view:"checkbox", 
                                    id:"minsOpt01", 
                                    labelRight: $.i18n("cronUi.popup.minsOpt01"), 
                                    value:1,
                                    click: function(id,event){
                                        if($$("minsOpt01").data.value === 1){
                                            $$("inputsHeaderMinutes").setValue("*");
                                            //$$("secondsOpt01").setValue(0);
                                            $$("minsOpt02").setValue(0);
                                            $$("minsOpt03").setValue(0);
                                            $$("minsOpt04").setValue(0);
                                        }
                                    }
                                },
                                {
                                    cols:[
                                        {
                                            view:"checkbox", 
                                            id:"minsOpt02", 
                                            labelRight: $.i18n("cronUi.popup.minsOpt02.01"), 
                                            //labelWidth: 80,
                                            value:0,
                                            click: function(id,event){
                                                let counter01 = $$("minsOpt02_counter01").getValue();
                                                let counter02 = $$("minsOpt02_counter02").getValue();

                                                if($$("minsOpt02").data.value === 1){
                                                    $$("inputsHeaderMinutes").setValue(counter02 + "/" + counter01);
                                                    //$$("secondsOpt01").setValue(0);
                                                    $$("minsOpt01").setValue(0);
                                                    $$("minsOpt03").setValue(0);
                                                    $$("minsOpt04").setValue(0);
                                                }
                                            }
                                        },
                                        { view: "counter", name: "",id: "minsOpt02_counter01", value: 1, min: 1, max: 60,
                                            click: function(id,event){
                                                let counter01 = $$("minsOpt02_counter01").getValue();
                                                let counter02 = $$("minsOpt02_counter02").getValue();
                                                $$("inputsHeaderMinutes").setValue(counter02 + "/" + counter01);
                                            }
                                        },
                                        { 
                                            view:"label", 
                                            label: $.i18n("cronUi.popup.minsOpt02.02"), 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { view: "counter", name: "", id: "minsOpt02_counter02", value: 0, min: 0, max: 59,
                                            click: function(id,event){
                                                let counter01 = $$("minsOpt02_counter01").getValue();
                                                let counter02 = $$("minsOpt02_counter02").getValue();
                                                $$("inputsHeaderMinutes").setValue(counter02 + "/" + counter01);
                                            }
                                        },
                                        { gravity:1}
                                    ]
                                },
                                {
                                    view:"checkbox", 
                                    id:"minsOpt03", 
                                    labelRight: $.i18n("cronUi.popup.minsOpt03"), 
                                    value:0,
                                    click: function(id,event){
                                        if($$("minsOpt03").data.value === 1){
                                            let multi = $$("minsOpt03_combo").getValue();
                                            $$("inputsHeaderMinutes").setValue(multi);
                                            
                                            //$$("secondsOpt01").setValue(0);
                                            $$("minsOpt01").setValue(0);
                                            $$("minsOpt02").setValue(0);
                                            $$("minsOpt04").setValue(0);
                                        }
                                    }
                                },
                                {
                                    cols:[
                                        {},
                                        { view:"multicombo", id: "minsOpt03_combo", name: "minsOpt03_combo", value: "0", //placeholder:"Tags",
                                            options:["0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"], //newValues: true
                                            on: {
                                                'onChange': function(obj){ 
                                                    if($$("minsOpt03").data.value === 1){
                                                        let multi = $$("minsOpt03_combo").getValue();
                                                        $$("inputsHeaderMinutes").setValue(multi);
                                                    }
                                                }
                                            }
                                        },
                                        {gravity:2}
                                    ]
                                },
                                {
                                    cols:[
                                        
                                        {
                                            view:"checkbox", 
                                            id:"minsOpt04", 
                                            labelRight: $.i18n("cronUi.popup.minsOpt04.01"),
                                            //labelWidth: 0, 
                                            value:0,
                                            click: function(id,event){
                                                if($$("minsOpt04").data.value === 1){
                                                    let counter01 = $$("minsOpt04_counter01").getValue();
                                                    let counter02 = $$("minsOpt04_counter02").getValue();                                            
                                                    $$("inputsHeaderMinutes").setValue(counter01 + "-" + counter02);
                                                    //$$("secondsOpt01").setValue(0);
                                                    $$("minsOpt01").setValue(0);
                                                    $$("minsOpt02").setValue(0);
                                                    $$("minsOpt03").setValue(0);
                                                }
                                            }
                                        },
                                        { 
                                            view:"label", 
                                            label: $.i18n("cronUi.popup.minsOpt04.02"), 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { view: "counter", id: "minsOpt04_counter01", name: "", value: 0, 
                                            click: function(id,event){
                                                let counter01 = $$("minsOpt04_counter01").getValue();
                                                let counter02 = $$("minsOpt04_counter02").getValue();
                                                $$("inputsHeaderMinutes").setValue(counter01 + "-" + counter02);
                                            },
                                            min: 0, max: 59},
                                        { 
                                            view:"label", 
                                            label: $.i18n("cronUi.popup.minsOpt04.03"), 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { view: "counter", name: "", id: "minsOpt04_counter02",
                                            click: function(id,event){
                                                let counter01 = $$("minsOpt04_counter01").getValue();
                                                let counter02 = $$("minsOpt04_counter02").getValue();
                                                $$("inputsHeaderMinutes").setValue(counter01 + "-" + counter02);
                                            },
                                            value: 0, min: 0, max: 59},
                                        //{ gravity:1}
                                    ]
                                }
                            ]
                        }
                    ],              
                },
                { 
                    view: "form",
                    id: "cronUi_hours",
                    width: 0,
                    margin: 0,
                    elements: [
                        {
                            rows: [
                                {
                                    view:"checkbox", 
                                    id:"hoursOpt01", 
                                    labelRight: $.i18n("cronUi.popup.hoursOpt01"), 
                                    value:1,
                                    click: function(id,event){
                                        if($$("hoursOpt01").data.value === 1){
                                            $$("inputsHeaderHours").setValue("*");
                                            //$$("secondsOpt01").setValue(0);
                                            $$("hoursOpt02").setValue(0);
                                            $$("hoursOpt03").setValue(0);
                                            $$("hoursOpt04").setValue(0);
                                        }
                                    }
                                },
                                {
                                    cols:[
                                        {
                                            view:"checkbox", 
                                            id:"hoursOpt02", 
                                            labelRight: $.i18n("cronUi.popup.hoursOpt02.01"), 
                                            //labelWidth: 80,
                                            value:0,
                                            click: function(id,event){
                                                let counter01 = $$("hoursOpt02_counter01").getValue();
                                                let counter02 = $$("hoursOpt02_counter02").getValue();

                                                if($$("hoursOpt02").data.value === 1){
                                                    $$("inputsHeaderHours").setValue(counter02 + "/" + counter01);
                                                    $$("hoursOpt01").setValue(0);
                                                    $$("hoursOpt03").setValue(0);
                                                    $$("hoursOpt04").setValue(0);
                                                }
                                            }
                                        },
                                        { view: "counter", name: "", id: "hoursOpt02_counter01",
                                            click: function(id,event){
                                                let counter01 = $$("hoursOpt02_counter01").getValue();
                                                let counter02 = $$("hoursOpt02_counter02").getValue();
                                                $$("inputsHeaderHours").setValue(counter02 + "/" + counter01);
                                            },
                                            value: 1, min: 1, max: 24},
                                        { 
                                            view:"label", 
                                            label: $.i18n("cronUi.popup.hoursOpt02.02"), 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { view: "counter", name: "", id: "hoursOpt02_counter02",
                                            click: function(id,event){
                                                let counter01 = $$("hoursOpt02_counter01").getValue();
                                                let counter02 = $$("hoursOpt02_counter02").getValue();
                                                $$("inputsHeaderHours").setValue(counter02 + "/" + counter01);
                                            },
                                            value: 1, min: 0, max: 23},
                                        { gravity:1}
                                    ]
                                },
                                {
                                    view:"checkbox", 
                                    id:"hoursOpt03", 
                                    labelRight: $.i18n("cronUi.popup.hoursOpt03"), 
                                    value:0,
                                    click: function(id,event){
                                        if($$("hoursOpt03").data.value === 1){
                                            let multi = $$("hoursOpt03_combo").getValue();
                                            $$("inputsHeaderHours").setValue(multi);
                                            
                                            //$$("secondsOpt01").setValue(0);
                                            $$("hoursOpt01").setValue(0);
                                            $$("hoursOpt02").setValue(0);
                                            $$("hoursOpt04").setValue(0);
                                        }
                                    }
                                },
                                {
                                    cols:[
                                        {},
                                        { view:"multicombo", id: "hoursOpt03_combo", name: "hoursOpt03_combo", value: "0", placeholder:"Tags",
                                            options:["0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"], //newValues: true
                                            on: {
                                                'onChange': function(obj){ 
                                                    if($$("hoursOpt03").data.value === 1){
                                                        let multi = $$("hoursOpt03_combo").getValue();
                                                        $$("inputsHeaderHours").setValue(multi);
                                                    }
                                                }
                                            }
                                        },
                                        {gravity:2}
                                    ]
                                },
                                {
                                    cols:[
                                        
                                        {
                                            view:"checkbox", 
                                            id:"hoursOpt04", 
                                            labelRight: $.i18n("cronUi.popup.hoursOpt04.01"),
                                            //labelWidth: 0, 
                                            value:0,
                                            click: function(id,event){
                                                if($$("hoursOpt04").data.value === 1){
                                                    let counter01 = $$("hoursOpt04_counter01").getValue();
                                                    let counter02 = $$("hoursOpt04_counter02").getValue();                                            
                                                    $$("inputsHeaderHours").setValue(counter01 + "-" + counter02);
                                                    //$$("secondsOpt01").setValue(0);
                                                    $$("hoursOpt01").setValue(0);
                                                    $$("hoursOpt02").setValue(0);
                                                    $$("hoursOpt03").setValue(0);
                                                }
                                            }
                                        },
                                        { 
                                            view:"label", 
                                            label: $.i18n("cronUi.popup.hoursOpt04.02"), 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { view: "counter", name: "", id: "hoursOpt04_counter01",
                                            click: function(id,event){
                                                let counter01 = $$("hoursOpt04_counter01").getValue();
                                                let counter02 = $$("hoursOpt04_counter02").getValue();
                                                $$("inputsHeaderHours").setValue(counter01 + "-" + counter02);
                                            },
                                            value: 0, min: 0, max: 23},
                                        { 
                                            view:"label", 
                                            label: $.i18n("cronUi.popup.hoursOpt04.03"), 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { view: "counter", name: "", id: "hoursOpt04_counter02",
                                            click: function(id,event){
                                                let counter01 = $$("hoursOpt04_counter01").getValue();
                                                let counter02 = $$("hoursOpt04_counter02").getValue();
                                                $$("inputsHeaderHours").setValue(counter01 + "-" + counter02);
                                            },
                                            value: 0, min: 0, max: 23},
                                        //{ gravity:1}
                                    ]
                                }
                            ]
                        }
                    ],              
                },
                { 
                    view: "form",
                    id: "cronUi_days",
                    width: 0,
                    margin: 0,
                    elements: [
                        {
                            rows: [
                                {
                                    view:"checkbox", 
                                    id:"daysOpt01", 
                                    labelRight: $.i18n("cronUi.popup.daysOpt01"), 
                                    value:1,
                                    click: function(id,event){
                                        if($$("daysOpt01").data.value === 1){
                                            $$("inputsHeaderDays").setValue("*");
                                            $$("daysOpt02").setValue(0);
                                            $$("daysOpt03").setValue(0);
                                            //$$("daysOpt04").setValue(0);
                                            $$("daysOpt05").setValue(0);
                                        }
                                    }
                                },
                                {
                                    cols:[
                                        {
                                            view:"checkbox", 
                                            id:"daysOpt02", 
                                            labelRight: $.i18n("cronUi.popup.daysOpt02.01"), 
                                            //labelWidth: 80,
                                            value:0,
                                            click: function(id,event){
                                                let counter01 = $$("daysOpt02_counter01").getValue();
                                                let counter02 = $$("daysOpt02_counter02").getValue();

                                                if($$("daysOpt02").data.value === 1){
                                                    $$("inputsHeaderDays").setValue(counter02 + "/" + counter01);
                                                    //$$("secondsOpt01").setValue(0);
                                                    $$("daysOpt01").setValue(0);
                                                    $$("daysOpt03").setValue(0);
                                                    //$$("daysOpt04").setValue(0);
                                                    $$("daysOpt05").setValue(0);
                                                }
                                            }
                                        },
                                        { view: "counter", name: "", id: "daysOpt02_counter01", 
                                            click: function(id,event){
                                                let counter01 = $$("daysOpt02_counter01").getValue();
                                                let counter02 = $$("daysOpt02_counter02").getValue();
                                                $$("inputsHeaderDays").setValue(counter02 + "/" + counter01);
                                            },
                                            value: 1, min: 1, max: 31},
                                        { 
                                            view:"label", 
                                            label: $.i18n("cronUi.popup.daysOpt02.02"), 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { view: "counter", name: "", id: "daysOpt02_counter02", 
                                            click: function(id,event){
                                                let counter01 = $$("daysOpt02_counter01").getValue();
                                                let counter02 = $$("daysOpt02_counter02").getValue();
                                                $$("inputsHeaderDays").setValue(counter02 + "/" + counter01);
                                            },
                                            value: 1, min: 1, max: 31},
                                        { 
                                            view:"label", 
                                            label: $.i18n("cronUi.popup.daysOpt02.03"), 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        //{ gravity:1}
                                    ]
                                },
                                {
                                    view:"checkbox", 
                                    id:"daysOpt03", 
                                    labelRight: $.i18n("cronUi.popup.daysOpt03"), 
                                    value:0,
                                    click: function(id,event){
                                        if($$("daysOpt03").data.value === 1){
                                            let multi = $$("daysOpt03_combo").getValue();
                                            $$("inputsHeaderDays").setValue(multi);
                                            
                                            //$$("secondsOpt01").setValue(0);
                                            $$("daysOpt01").setValue(0);
                                            $$("daysOpt02").setValue(0);
                                            //$$("daysOpt04").setValue(0);
                                            $$("daysOpt05").setValue(0);
                                        }
                                    }
                                },
                                {
                                    cols:[
                                        {},
                                        { view:"multicombo", id: "daysOpt03_combo", name: "daysOpt03_combo", value: "1", //placeholder:"Tags",
                                            options:["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"], //newValues: true
                                            on: {
                                                'onChange': function(obj){ 
                                                    if($$("daysOpt03").data.value === 1){
                                                        let multi = $$("daysOpt03_combo").getValue();
                                                        $$("inputsHeaderDays").setValue(multi);
                                                    }
                                                }
                                            }
                                        },
                                        {gravity:2}
                                    ]
                                },

                                /*
                                {
                                    cols:[
                                        {
                                            view:"checkbox", 
                                            id:"daysOpt04", 
                                            labelRight:"Every",
                                            //labelWidth: 0, 
                                            value:0,
                                            click: function(id,event){
                                                if($$("daysOpt04").data.value === 1){
                                                    let counter01 = $$("daysOpt04_counter01").getValue();
                                                    let counter02 = $$("daysOpt04_counter02").getValue();                                            
                                                    $$("inputsHeaderDays").setValue(counter01 + "-" + counter02);
                                                    //$$("secondsOpt01").setValue(0);
                                                    $$("daysOpt01").setValue(0);
                                                    $$("daysOpt02").setValue(0);
                                                    $$("daysOpt03").setValue(0);
                                                    $$("daysOpt05").setValue(0);
                                                }
                                            }
                                        },
                                        { view: "counter", name: "", id: "daysOpt04_counter01", 
                                            click: function(id,event){
                                                let counter01 = $$("daysOpt04_counter01").getValue();
                                                let counter02 = $$("daysOpt04_counter02").getValue();
                                                $$("inputsHeaderDays").setValue(counter01 + "-" + counter02);
                                            },
                                            value: 1, min: 1, max: 59},
                                        { 
                                            view:"label", 
                                            label: "day(s) starting on", 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        {
                                            view:"combo", 
                                            label: 'Combo',
                                            value:"Sunday", 
                                            id: "daysOpt04_counter02",
                                            options:["Sunday", "Monday", "Tuesday","Wednesday","Thursday","Friday","Saturday"],
                                            click: function(id,event){
                                                let counter01 = $$("daysOpt04_counter01").getValue();
                                                let counter02 = $$("daysOpt04_counter02").getValue();
                                                $$("inputsHeaderDays").setValue(counter01 + "-" + counter02);
                                            },
                                        },
                                        //{ view: "counter", name: "", value: 1, min: 1, max: 59},
                                        //{ gravity:1}
                                    ]
                                },
*/

                                {
                                    view:"checkbox", 
                                    id:"daysOpt05", 
                                    labelRight:$.i18n("cronUi.popup.daysOpt05"), 
                                    value:0,
                                    click: function(id,event){
                                        if($$("daysOpt05").data.value === 1){                                          
                                            $$("inputsHeaderDays").setValue("?");
                                            //$$("secondsOpt01").setValue(0);
                                            $$("daysOpt01").setValue(0);
                                            $$("daysOpt02").setValue(0);
                                            $$("daysOpt03").setValue(0);
                                            //$$("daysOpt04").setValue(0);
                                        }
                                    }
                                }
                            ]
                        }
                    ],              
                },
                { 
                    view: "form",
                    id: "cronUi_months",
                    width: 0,
                    margin: 0,
                    elements: [
                        {
                            rows: [
                                {
                                    view:"checkbox", 
                                    id:"monthsOpt01", 
                                    labelRight:$.i18n("cronUi.popup.monthsOpt01"), 
                                    value:1,
                                    click: function(id,event){
                                        if($$("monthsOpt01").data.value === 1){
                                            $$("inputsHeaderMonths").setValue("*");
                                            //$$("secondsOpt01").setValue(0);
                                            $$("monthsOpt02").setValue(0);
                                            $$("monthsOpt03").setValue(0);
                                            $$("monthsOpt04").setValue(0);
                                        }
                                    }
                                },
                                {
                                    cols:[
                                        {
                                            view:"checkbox", 
                                            id:"monthsOpt02", 
                                            labelRight:$.i18n("cronUi.popup.monthsOpt02.01"), 
                                            //labelWidth: 80,
                                            value:0,
                                            click: function(id,event){
                                                let counter01 = $$("monthsOpt02_counter01").getValue();
                                                let counter02 = $$("monthsOpt02_counter02").getValue();

                                                if($$("monthsOpt02").data.value === 1){
                                                    $$("inputsHeaderMonths").setValue(counter02 + "/" + counter01);
                                                    //$$("secondsOpt01").setValue(0);
                                                    $$("monthsOpt01").setValue(0);
                                                    $$("monthsOpt03").setValue(0);
                                                    $$("monthsOpt04").setValue(0);
                                                }
                                            }
                                        },
                                        { view: "counter", name: "", id: "monthsOpt02_counter01", 
                                            click: function(id,event){
                                                let counter01 = $$("monthsOpt02_counter01").getValue();
                                                let counter02 = $$("monthsOpt02_counter02").getValue();
                                                $$("inputsHeaderMonths").setValue(counter02 + "/" + counter01);
                                            },
                                            value: 1, min: 1, max: 12},
                                        { 
                                            view:"label", 
                                            label: $.i18n("cronUi.popup.monthsOpt02.02"), 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        {
                                            view:"combo", 
                                            value:"1",//{id: "1",value: $.i18n("cronUi.popup.month.1")}, 
                                            id: "monthsOpt02_counter02", 
                                            options:[
                                                {id: "1",value: $.i18n("cronUi.popup.month.1")}, 
                                                {id: "2",value: $.i18n("cronUi.popup.month.2")},
                                                {id: "3",value: $.i18n("cronUi.popup.month.3")},
                                                {id: "4",value: $.i18n("cronUi.popup.month.4")},
                                                {id: "5",value: $.i18n("cronUi.popup.month.5")},
                                                {id: "6",value: $.i18n("cronUi.popup.month.6")},
                                                {id: "7",value: $.i18n("cronUi.popup.month.7")},
                                                {id: "8",value: $.i18n("cronUi.popup.month.8")},
                                                {id: "9",value: $.i18n("cronUi.popup.month.9")},
                                                {id: "10",value: $.i18n("cronUi.popup.month.10")},
                                                {id: "11",value: $.i18n("cronUi.popup.month.11")},
                                                {id: "12",value: $.i18n("cronUi.popup.month.12")},
                                            ],
                                            on: {
                                                onChange: function(newv, oldv){
                                                    let counter01 = $$("monthsOpt02_counter01").getValue();
                                                    let counter02 = newv;//$$("monthsOpt02_counter02").getValue();
                                                    $$("inputsHeaderMonths").setValue(counter02 + "/" + counter01);
                                                }
                                            }
                                        },
                                        { gravity:1}
                                    ]
                                },
                                {
                                    view:"checkbox", 
                                    id:"monthsOpt03", 
                                    labelRight:$.i18n("cronUi.popup.monthsOpt03"), 
                                    value:0,
                                    click: function(id,event){
                                        if($$("monthsOpt03").data.value === 1){
                                            let multi = $$("monthsOpt03_combo").getValue().split(",");
                                            let v_multi = "";
                                            let contador = 1;
                                            multi.forEach(ele => {
                                                if (contador ===multi.length){
                                                    v_multi = v_multi + v_month[ele];
                                                } else {
                                                    v_multi = v_multi + v_month[ele] + ",";
                                                }
                                                contador += 1;
                                            });
                                                                                        
                                            $$("inputsHeaderMonths").setValue(v_multi);
                                            
                                            //$$("secondsOpt01").setValue(0);
                                            $$("monthsOpt01").setValue(0);
                                            $$("monthsOpt02").setValue(0);
                                            $$("monthsOpt04").setValue(0);
                                        }
                                    }
                                },
                                {
                                    cols:[
                                        {},
                                        { view:"multicombo", id: "monthsOpt03_combo", name: "monthsOpt03_combo", value: "1", //placeholder:"Tags",
                                            options:[
                                                {id: "1",value: $.i18n("cronUi.popup.month.1")}, 
                                                {id: "2",value: $.i18n("cronUi.popup.month.2")},
                                                {id: "3",value: $.i18n("cronUi.popup.month.3")},
                                                {id: "4",value: $.i18n("cronUi.popup.month.4")},
                                                {id: "5",value: $.i18n("cronUi.popup.month.5")},
                                                {id: "6",value: $.i18n("cronUi.popup.month.6")},
                                                {id: "7",value: $.i18n("cronUi.popup.month.7")},
                                                {id: "8",value: $.i18n("cronUi.popup.month.8")},
                                                {id: "9",value: $.i18n("cronUi.popup.month.9")},
                                                {id: "10",value: $.i18n("cronUi.popup.month.10")},
                                                {id: "11",value: $.i18n("cronUi.popup.month.11")},
                                                {id: "12",value: $.i18n("cronUi.popup.month.12")},
                                            ],
                                            on: {
                                                'onChange': function(obj){ 
                                                    if($$("monthsOpt03").data.value === 1){
                                                        let multi = $$("monthsOpt03_combo").getValue().split(",");
                                                        let v_multi = "";
                                                        let contador = 1;
                                                        multi.forEach(ele => {
                                                            if (contador === multi.length){
                                                                v_multi = v_multi + v_month[ele];
                                                            } else {
                                                                v_multi = v_multi + v_month[ele] + ",";
                                                            }
                                                            contador += 1;
                                                        });
                                                                                                    
                                                        $$("inputsHeaderMonths").setValue(v_multi);         
                                                    }
                                                }
                                            }
                                        },
                                        {gravity:2}
                                    ]
                                },
                                {
                                    cols:[
                                        {
                                            view:"checkbox", 
                                            id:"monthsOpt04", 
                                            labelRight:$.i18n("cronUi.popup.monthsOpt04.01"),
                                            //labelWidth: 0, 
                                            value:0,
                                            click: function(id,event){
                                                if($$("monthsOpt04").data.value === 1){
                                                    let counter01 = $$("monthsOpt04_counter01").getValue();
                                                    let counter02 = $$("monthsOpt04_counter02").getValue();                                            
                                                    $$("inputsHeaderMonths").setValue(counter01 + "-" + counter02);
                                                    $$("monthsOpt01").setValue(0);
                                                    $$("monthsOpt02").setValue(0);
                                                    $$("monthsOpt03").setValue(0);
                                                }
                                            }
                                        },
                                        {
                                            view:"combo", 
                                            id: "monthsOpt04_counter01",
                                            value:"1", 
                                            on: {
                                                'onChange': function(obj){
                                                    let counter01 = $$("monthsOpt04_counter01").getValue();
                                                    let counter02 = $$("monthsOpt04_counter02").getValue();
                                                    $$("inputsHeaderMonths").setValue(counter01 + "-" + counter02);
                                                }
                                            },
                                            options:[
                                                {id: "1",value: $.i18n("cronUi.popup.month.1")}, 
                                                {id: "2",value: $.i18n("cronUi.popup.month.2")},
                                                {id: "3",value: $.i18n("cronUi.popup.month.3")},
                                                {id: "4",value: $.i18n("cronUi.popup.month.4")},
                                                {id: "5",value: $.i18n("cronUi.popup.month.5")},
                                                {id: "6",value: $.i18n("cronUi.popup.month.6")},
                                                {id: "7",value: $.i18n("cronUi.popup.month.7")},
                                                {id: "8",value: $.i18n("cronUi.popup.month.8")},
                                                {id: "9",value: $.i18n("cronUi.popup.month.9")},
                                                {id: "10",value: $.i18n("cronUi.popup.month.10")},
                                                {id: "11",value: $.i18n("cronUi.popup.month.11")},
                                                {id: "12",value: $.i18n("cronUi.popup.month.12")},
                                            ],
                                        },
                                        /*
                                        { 
                                            view:"label", 
                                            label: $.i18n("cronUi.popup.monthsOpt04.02"), 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        */
                                        {
                                            view:"combo", 
                                            id: "monthsOpt04_counter02",
                                            labelRight: $.i18n("cronUi.popup.monthsOpt04.02"),
                                            value:"1", 
                                            on: {
                                                'onChange': function(obj){
                                                    let counter01 = $$("monthsOpt04_counter01").getValue();
                                                    let counter02 = $$("monthsOpt04_counter02").getValue();
                                                    $$("inputsHeaderMonths").setValue(counter01 + "-" + counter02);
                                                }
                                            },
                                            options:[
                                                {id: "1",value: $.i18n("cronUi.popup.month.1")}, 
                                                {id: "2",value: $.i18n("cronUi.popup.month.2")},
                                                {id: "3",value: $.i18n("cronUi.popup.month.3")},
                                                {id: "4",value: $.i18n("cronUi.popup.month.4")},
                                                {id: "5",value: $.i18n("cronUi.popup.month.5")},
                                                {id: "6",value: $.i18n("cronUi.popup.month.6")},
                                                {id: "7",value: $.i18n("cronUi.popup.month.7")},
                                                {id: "8",value: $.i18n("cronUi.popup.month.8")},
                                                {id: "9",value: $.i18n("cronUi.popup.month.9")},
                                                {id: "10",value: $.i18n("cronUi.popup.month.10")},
                                                {id: "11",value: $.i18n("cronUi.popup.month.11")},
                                                {id: "12",value: $.i18n("cronUi.popup.month.12")},
                                            ],
                                        },
                                        //{ view: "counter", name: "", value: 1, min: 1, max: 59},
                                        //{ gravity:1}
                                    ]
                                }
                            ]
                        }
                    ],              
                },
                { 
                    view: "form",
                    id: "cronUi_years",
                    width: 0,
                    margin: 0,
                    elements: [
                        {
                            rows: [
                                {
                                    view:"checkbox", 
                                    id:"yearsOpt01", 
                                    labelRight:$.i18n("cronUi.popup.yearsOpt01"), 
                                    value:1,
                                    click: function(id,event){
                                        if($$("yearsOpt01").data.value === 1){
                                            $$("inputsHeaderYears").setValue("*");
                                            //$$("secondsOpt01").setValue(0);
                                            $$("yearsOpt02").setValue(0);
                                            $$("yearsOpt03").setValue(0);
                                            $$("yearsOpt04").setValue(0);
                                        }
                                    }
                                },
                                {
                                    cols:[
                                        {
                                            view:"checkbox", 
                                            id:"yearsOpt02", 
                                            labelRight:$.i18n("cronUi.popup.yearsOpt02.01"), 
                                            //labelWidth: 80,
                                            value:0,
                                            click: function(id,event){
                                                let counter01 = $$("yearsOpt02_counter01").getValue();
                                                let counter02 = $$("yearsOpt02_counter02").getValue();

                                                if($$("yearsOpt02").data.value === 1){
                                                    $$("inputsHeaderYears").setValue(counter02 + "/" + counter01);
                                                    //$$("secondsOpt01").setValue(0);
                                                    $$("yearsOpt01").setValue(0);
                                                    $$("yearsOpt03").setValue(0);
                                                    $$("yearsOpt04").setValue(0);
                                                }
                                            }
                                        },
                                        { view: "counter", name: "", id: "yearsOpt02_counter01", 
                                            click: function(id,event){
                                                let counter01 = $$("yearsOpt02_counter01").getValue();
                                                let counter02 = $$("yearsOpt02_counter02").getValue();
                                                $$("inputsHeaderYears").setValue(counter02 + "/" + counter01);
                                            },
                                            value: 1, min: 1, max: 100},
                                        { 
                                            view:"label", 
                                            label: $.i18n("cronUi.popup.yearsOpt02.02"), 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { view: "counter", name: "", id: "yearsOpt02_counter02", 
                                            click: function(id,event){
                                                let counter01 = $$("yearsOpt02_counter01").getValue();
                                                let counter02 = $$("yearsOpt02_counter02").getValue();
                                                $$("inputsHeaderYears").setValue(counter02 + "/" + counter01);
                                            },
                                            value: 2020, min: 2020, max: 2100},
                                        { gravity:1}
                                    ]
                                },
                                {
                                    view:"checkbox", 
                                    id:"yearsOpt03", 
                                    labelRight:$.i18n("cronUi.popup.yearsOpt03"), 
                                    value:0,
                                    click: function(id,event){
                                        if($$("yearsOpt03").data.value === 1){
                                            let multi = $$("yearsOpt03_combo").getValue();
                                            $$("inputsHeaderYears").setValue(multi);
                                            $$("yearsOpt01").setValue(0);
                                            $$("yearsOpt02").setValue(0);
                                            $$("yearsOpt04").setValue(0);
                                        }
                                    }
                                },
                                {
                                    cols:[
                                        {},
                                        { view:"multicombo", id: "yearsOpt03_combo", name: "yearsOpt03_combo", value: "2020", //placeholder:"Tags",
                                            options:["2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035","2036","2037","2038","2039","2040","2041","2042","2043","2044","2045","2046","2047","2048","2049","2050","2051","2052","2053","2054","2055","2056","2057","2058","2059","2060","2061","2062","2063","2064","2065","2066","2067","2068","2069","2070"],
                                            on: {
                                                'onChange': function(obj){ 
                                                    if($$("yearsOpt03").data.value === 1){
                                                        let multi = $$("yearsOpt03_combo").getValue();
                                                        $$("inputsHeaderYears").setValue(multi);
                                                    }
                                                }
                                            }
                                        },
                                        {gravity:2}
                                    ]
                                },
                                {
                                    cols:[
                                        {
                                            view:"checkbox", 
                                            id:"yearsOpt04", 
                                            labelRight:$.i18n("cronUi.popup.yearsOpt04.01"),
                                            //labelWidth: 0, 
                                            value:0,
                                            click: function(id,event){
                                                if($$("yearsOpt04").data.value === 1){
                                                    let counter01 = $$("yearsOpt04_counter01").getValue();
                                                    let counter02 = $$("yearsOpt04_counter02").getValue();                                            
                                                    $$("inputsHeaderYears").setValue(counter01 + "-" + counter02);
                                                    //$$("secondsOpt01").setValue(0);
                                                    $$("yearsOpt01").setValue(0);
                                                    $$("yearsOpt02").setValue(0);
                                                    $$("yearsOpt03").setValue(0);
                                                }
                                            }
                                        },
                                        { view: "counter", name: "", id: "yearsOpt04_counter01",
                                            click: function(id,event){
                                                let counter01 = $$("yearsOpt04_counter01").getValue();
                                                let counter02 = $$("yearsOpt04_counter02").getValue();
                                                $$("inputsHeaderYears").setValue(counter01 + "-" + counter02);
                                            },
                                            value: 2020, min: 2020, max: 2100
                                        },
                                        /*
                                        { 
                                            view:"label", 
                                            label: $.i18n("cronUi.popup.yearsOpt04.02"), 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        */
                                        { view: "counter", name: "", id: "yearsOpt04_counter02",
                                            click: function(id,event){
                                                let counter01 = $$("yearsOpt04_counter01").getValue();
                                                let counter02 = $$("yearsOpt04_counter02").getValue();
                                                $$("inputsHeaderYears").setValue(counter01 + "-" + counter02);
                                            },
                                            value: 2020, min: 2020, max: 2100},
                                        //{ view: "counter", name: "", value: 1, min: 1, max: 59},
                                        //{ gravity:1}
                                    ]
                                }
                            ]
                        }
                    ],              
                }
            ]
        };
        this.buttons = {
            id: "CronUiFormButtons",
			view: "form",
			elements: [
                { view: "button", id: "CronUiFormButtons_apply", label: $.i18n("planSchedule.popup.button.create"), value: "",
                    click: function(){
                        let sec = $$("inputsHeaderSeconds").getValue();
                        let min = $$("inputsHeaderMinutes").getValue();
                        let hour = $$("inputsHeaderHours").getValue();
                        let day = $$("inputsHeaderDays").getValue();
                        let month = $$("inputsHeaderMonths").getValue();
                        //{ view: "text", id: "inputsHeaderWeeks",   name: "weeks"},
                        let year = $$("inputsHeaderYears").getValue();
                        $$(input).setValue(sec + " " + min + " " + hour + " " + day + " " + month + " ? " + year);
                        $$(popupId).hide();
                    }
                }
			]
        };
    }

    getComponent() {

        let genericForm = {
            rows: [
                this.inputsHeader,
                this.form,
                this.buttons,
                {}
            ]
        };
        return genericForm;
    }

    loadData(){
        let valor = $$(this.input).getValue();
        let valorList = valor.split(" ");
        let controlSecond = "";
        let controlMinute = "";
        let controlHour = "";
        let controlDay = "";
        let controlMonth = "";
        let controlYear = "";

        let controlTotal = true;

        if(valorList.length!==7){
            //ERROR
            console.log("ERROR DE TAMAÑO DE FORMATO");
        } else {
            if(valorList[0] === "*"){
                //EVERY YEAR
                controlSecond = "secondsOpt01";
            }else{
                if (valorList[0].search("/")>0) {
                    let control = true;
                    let valores = valorList[0].split("/");
                    let valor1 = null;
                    let valor2 = null;
                    try {
                        valor1 = parseInt(valores[0]);
                        valor2 = parseInt(valores[1]);
                    } catch (e) {
                        control = false;
                    }

                    if(control){
                        controlSecond = "secondsOpt02";
                        $$("secondsOpt02_counter01").setValue(valor2);
                        $$("secondsOpt02_counter02").setValue(valor1);
                    } else {
                        //ERROR 
                        controlTotal = false;
                        //console.log("ERROR FALLO / SEC 02");
                        webix.message({type:"error", text:$.i18n("cronUi.popup.error.second")});
                    }
                } else {
                    if (valorList[0].search("-")>0) {
                        let control = true;
                        let valores = valorList[0].split("-");
                        let valor1 = null;
                        let valor2 = null;
                        try {
                            valor1 = parseInt(valores[0]);
                            valor2 = parseInt(valores[1]);
                        } catch (e) {
                            control = false;
                        }

                        if(control){
                            controlSecond = "secondsOpt04";
                            $$("secondsOpt04_counter01").setValue(valor2);
                            $$("secondsOpt04_counter02").setValue(valor1);
                        } else {
                            //ERROR 
                            controlTotal = false;
                            webix.message({type:"error", text:$.i18n("cronUi.popup.error.second")});
                            //console.log("ERROR FALLO - SEC 04");
                        }
                    } else {
                        if (valorList[0].search(",")>0) {
                            let control = true;
                            let valores = valorList[0].split(",");
                            let valor1 = null;
                            let valor2 = null;
                            try {
                                valores.forEach(item =>{
                                    valor1 = parseInt(item);
                                });
                            } catch (e) {
                                control = false;
                            }
    
                            if(control){
                                controlSecond = "secondsOpt03";
                                $$("secondsOpt03_combo").setValue(valorList[0]);
                            } else {
                                //ERROR 
                                controlTotal = false;
                                webix.message({type:"error", text:$.i18n("cronUi.popup.error.second")});
                                //console.log("ERROR FALLO , SEC 03");
                            }
                        } else {
                            //un año individual
                            let control = true;
                            //let valores = valorList[6].split(",");
                            let valor1 = null;
                            let valor2 = null;
                            try {
                                valor1 = parseInt(valorList[0]);
                            } catch (e) {
                                console.log(e);
                                control = false;
                            }
    
                            if(control){
                                controlSecond = "secondsOpt03";
                                $$("secondsOpt03_combo").setValue(valorList[0]);
                            } else {
                                //ERROR 
                                controlTotal = false;
                                webix.message({type:"error", text:$.i18n("cronUi.popup.error.second")});
                                //console.log("ERROR FALLO , SEC 03_2");
                            }
                        }    
                    }
                }
            }

            if(valorList[1] === "*"){
                //EVERY minute
                controlMinute = "minsOpt01";
            }else{
                if (valorList[1].search("/")>0) {
                    let control = true;
                    let valores = valorList[1].split("/");
                    let valor1 = null;
                    let valor2 = null;
                    try {
                        valor1 = parseInt(valores[0]);
                        valor2 = parseInt(valores[1]);
                    } catch (e) {
                        control = false
                    }

                    if(control){
                        controlMinute = "minsOpt02";
                        $$("minsOpt02_counter01").setValue(valor2);
                        $$("minsOpt02_counter02").setValue(valor1);
                    } else {
                        //ERROR 
                        controlTotal = false;
                        webix.message({type:"error", text:$.i18n("cronUi.popup.error.minute")});
                        //console.log("ERROR FALLO / MIN 02");
                    }
                } else {
                    if (valorList[1].search("-")>0) {
                        let control = true;
                        let valores = valorList[1].split("-");
                        let valor1 = null;
                        let valor2 = null;
                        try {
                            valor1 = parseInt(valores[0]);
                            valor2 = parseInt(valores[1]);
                        } catch (e) {
                            control = false
                        }

                        if(control){
                            controlMinute = "minsOpt04";
                            $$("minsOpt04_counter01").setValue(valor2);
                            $$("minsOpt04_counter02").setValue(valor1);
                        } else {
                            //ERROR 
                            controlTotal = false;
                            webix.message({type:"error", text:$.i18n("cronUi.popup.error.minute")});
                            //console.log("ERROR FALLO - MIN 03");
                        }
                    } else {
                        if (valorList[1].search(",")>0) {
                            let control = true;
                            let valores = valorList[1].split(",");
                            let valor1 = null;
                            let valor2 = null;
                            try {
                                valores.forEach(item =>{
                                    valor1 = parseInt(item);
                                });
                            } catch (e) {
                                control = false
                            }
    
                            if(control){
                                controlMinute = "minsOpt03";
                                $$("minsOpt03_combo").setValue(valorList[1]);
                            } else {
                                //ERROR 
                                controlTotal = false;
                                webix.message({type:"error", text:$.i18n("cronUi.popup.error.minute")});
                                //console.log("ERROR FALLO , MIN 03");
                            }
                        } else {
                            //un año individual
                            let control = true;
                            //let valores = valorList[6].split(",");
                            let valor1 = null;
                            let valor2 = null;
                            try {
                                valor1 = parseInt(valorList[1]);
                            } catch (e) {
                                control = false
                            }
    
                            if(control){
                                controlMinute = "minsOpt03";
                                $$("minsOpt03_combo").setValue(valorList[1]);
                            } else {
                                //ERROR 
                                controlTotal = false;
                                webix.message({type:"error", text:$.i18n("cronUi.popup.error.minute")});
                                //console.log("ERROR FALLO , MIN 03_2");
                            }
                        }    
                    }
                }
            }

            if(valorList[2] === "*"){
                //EVERY minute
                controlHour = "hoursOpt01";
            }else{
                if (valorList[2].search("/")>0) {
                    let control = true;
                    let valores = valorList[2].split("/");
                    let valor1 = null;
                    let valor2 = null;
                    try {
                        valor1 = parseInt(valores[0]);
                        valor2 = parseInt(valores[1]);
                    } catch (e) {
                        control = false
                    }

                    if(control){
                        controlHour = "hoursOpt02";
                        $$("hoursOpt02_counter01").setValue(valor2);
                        $$("hoursOpt02_counter02").setValue(valor1);
                    } else {
                        //ERROR 
                        controlTotal = false;
                        webix.message({type:"error", text:$.i18n("cronUi.popup.error.hour")});
                        //console.log("ERROR FALLO / HOUR 02");
                    }
                } else {
                    if (valorList[2].search("-")>0) {
                        let control = true;
                        let valores = valorList[2].split("-");
                        let valor1 = null;
                        let valor2 = null;
                        try {
                            valor1 = parseInt(valores[0]);
                            valor2 = parseInt(valores[1]);
                        } catch (e) {
                            control = false
                        }

                        if(control){
                            controlHour = "hoursOpt04";
                            $$("hoursOpt04_counter01").setValue(valor2);
                            $$("hoursOpt04_counter02").setValue(valor1);
                        } else {
                            //ERROR 
                            controlTotal = false;
                            webix.message({type:"error", text:$.i18n("cronUi.popup.error.hour")});
                            //console.log("ERROR FALLO - HOUR 04");
                        }
                    } else {
                        if (valorList[2].search(",")>0) {
                            let control = true;
                            let valores = valorList[2].split(",");
                            let valor1 = null;
                            let valor2 = null;
                            try {
                                valores.forEach(item =>{
                                    valor1 = parseInt(item);
                                });
                            } catch (e) {
                                control = false
                            }
    
                            if(control){
                                controlHour = "hoursOpt03";
                                $$("hoursOpt03_combo").setValue(valorList[2]);
                            } else {
                                //ERROR 
                                controlTotal = false;
                                webix.message({type:"error", text:$.i18n("cronUi.popup.error.hour")});
                                //console.log("ERROR FALLO , HOUR 03");
                            }
                        } else {
                            //un año individual
                            let control = true;
                            //let valores = valorList[6].split(",");
                            let valor1 = null;
                            let valor2 = null;
                            try {
                                valor1 = parseInt(valorList[2]);
                            } catch (e) {
                                control = false
                            }
    
                            if(control){
                                controlHour = "hoursOpt03";
                                $$("hoursOpt03_combo").setValue(valorList[2]);
                            } else {
                                //ERROR 
                                controlTotal = false;
                                webix.message({type:"error", text:$.i18n("cronUi.popup.error.hour")});
                                //console.log("ERROR FALLO , HOUR 03");
                            }
                        }    
                    }
                }
            }            

            if(valorList[3] === "*"){
                //EVERY minute
                controlDay = "daysOpt01";
            }else{
                if (valorList[3].search("/")>0) {
                    let control = true;
                    let valores = valorList[3].split("/");
                    let valor1 = null;
                    let valor2 = null;
                    try {
                        valor1 = parseInt(valores[0]);
                        valor2 = parseInt(valores[1]);
                    } catch (e) {
                        control = false
                    }

                    if(control){
                        controlDay = "daysOpt02";
                        $$("daysOpt02_counter01").setValue(valor2);
                        $$("daysOpt02_counter02").setValue(valor1);
                    } else {
                        //ERROR 
                        controlTotal = false;
                        //console.log("ERROR FALLO / DAY 02");
                        webix.message({type:"error", text:$.i18n("cronUi.popup.error.day")});
                    }
                } else {
                    if (valorList[3].search("?")>0) {
                        let control = true;
                        //let valores = valorList[3];

                        /*
                        try {
                            valor1 = parseInt(valores[0]);
                            valor2 = parseInt(valores[1]);
                        } catch (e) {
                            control = false
                        }
                        */

                        if(control){
                            controlDay = "daysOpt05";
                            //$$("daysOpt04_counter01").setValue(valor2);
                            //$$("daysOpt04_counter02").setValue(valor1);
                        } else {
                            //ERROR 
                            controlTotal = false;
                            webix.message({type:"error", text:$.i18n("cronUi.popup.error.day")});
                            //console.log("ERROR FALLO ? DAY 02");
                        }
                    } else {
                        if (valorList[3].search(",")>0) {
                            let control = true;
                            let valores = valorList[3].split(",");
                            let valor1 = null;
                            let valor2 = null;
                            try {
                                valores.forEach(item =>{
                                    valor1 = parseInt(item);
                                });
                            } catch (e) {
                                control = false
                            }
    
                            if(control){
                                controlDay = "daysOpt03";
                                $$("daysOpt03_combo").setValue(valorList[3]);
                            } else {
                                //ERROR 
                                controlTotal = false;
                                webix.message({type:"error", text:$.i18n("cronUi.popup.error.day")});
                                //console.log("ERROR FALLO , DAY 03");
                            }
                        } else {
                            //un año individual
                            let control = true;
                            //let valores = valorList[6].split(",");
                            let valor1 = null;
                            let valor2 = null;
                            try {
                                valor1 = parseInt(valorList[3]);
                            } catch (e) {
                                control = false
                            }
    
                            if(control){
                                controlDay = "daysOpt03";
                                $$("daysOpt03_combo").setValue(valorList[3]);
                            } else {
                                //ERROR 
                                controlTotal = false;
                                webix.message({type:"error", text:$.i18n("cronUi.popup.error.day")});
                                //console.log("ERROR FALLO , DAY 03_01");
                            }
                        }    
                    }
                }
            }  

            if(valorList[4] === "*"){
                //EVERY minute
                controlMonth = "monthsOpt01";
            }else{
                if (valorList[4].search("/")>0) {
                    let control = true;
                    let valores = valorList[4].split("/");
                    let valor1 = null;
                    let valor2 = null;
                    try {
                        valor1 = parseInt(valores[0]);
                        valor2 = parseInt(valores[1]);
                    } catch (e) {
                        control = false
                    }

                    if(control){
                        controlMonth = "monthsOpt02";
                        $$("monthsOpt02_counter01").setValue(valor2);
                        $$("monthsOpt02_counter02").setValue(valor1);
                    } else {
                        //ERROR 
                        controlTotal = false;
                        webix.message({type:"error", text:$.i18n("cronUi.popup.error.month")});
                        //console.log("ERROR FALLO / MON 02");
                    }
                } else {
                    if (valorList[4].search("-")>0) {
                        let control = true;
                        let valores = valorList[4].split("-");
                        let valor1 = null;
                        let valor2 = null;
                        try {
                            valor1 = parseInt(valores[0]);
                            valor2 = parseInt(valores[1]);
                        } catch (e) {
                            control = false
                        }

                        if(control){
                            controlMonth = "monthsOpt04";
                            $$("monthsOpt04_counter01").setValue(valor2);
                            $$("monthsOpt04_counter02").setValue(valor1);
                        } else {
                            //ERROR 
                            controlTotal = false;
                            webix.message({type:"error", text:$.i18n("cronUi.popup.error.month")});
                            //console.log("ERROR FALLO - MON 04");
                        }
                    } else {
                        if (valorList[4].search(",")>0) {
                            let control = true;
                            let valores = valorList[4].split(",");
                            let valor1 = [];
                            let valor2 = null;
                            try {
                                valores.forEach(item =>{
                                    valor1.push(parseInt(v_month3[item]).toString());
                                });
                            } catch (e) {
                                control = false
                            }
    
                            if(control){
                                controlMonth = "monthsOpt03";
                                $$("monthsOpt03_combo").setValue(valor1.toString());
                            } else {
                                //ERROR 
                                controlTotal = false;
                                webix.message({type:"error", text:$.i18n("cronUi.popup.error.month")});
                                //console.log("ERROR FALLO , MON 03");
                            }
                        } else {
                            //un año individual
                            let control = true;
                            //let valores = valorList[6].split(",");
                            let valor1 = null;
                            let valor2 = null;
                            try {
                                valor1 = parseInt(v_month3[valorList[4]]);
                            } catch (e) {
                                control = false
                            }
    
                            if(control){
                                controlMonth = "monthsOpt03";
                                $$("monthsOpt03_combo").setValue(valor1.toString());
                            } else {
                                //ERROR 
                                controlTotal = false;
                                webix.message({type:"error", text:$.i18n("cronUi.popup.error.month")});
                                //console.log("ERROR FALLO , MON 03_02");
                            }
                        }    
                    }
                }
            }  

            if(valorList[6] === "*"){
                //EVERY YEAR
                controlYear = "yearsOpt01";
            }else{
                if (valorList[6].search("/")>0) {
                    let control = true;
                    let valores = valorList[6].split("/");
                    let valor1 = null;
                    let valor2 = null;
                    try {
                        valor1 = parseInt(valores[0]);
                        valor2 = parseInt(valores[1]);
                    } catch (e) {
                        control = false
                    }

                    if(control){
                        controlYear = "yearsOpt02";
                        $$("yearsOpt02_counter01").setValue(valor2);
                        $$("yearsOpt02_counter02").setValue(valor1);
                    } else {
                        //ERROR 
                        controlTotal = false;
                        webix.message({type:"error", text:$.i18n("cronUi.popup.error.year")});
                        //console.log("ERROR FALLO / YEA 02");
                    }
                } else {
                    if (valorList[6].search("-")>0) {
                        let control = true;
                        let valores = valorList[6].split("-");
                        let valor1 = null;
                        let valor2 = null;
                        try {
                            valor1 = parseInt(valores[0]);
                            valor2 = parseInt(valores[1]);
                        } catch (e) {
                            control = false
                        }

                        if(control){
                            controlYear = "yearsOpt04";
                            $$("yearsOpt04_counter01").setValue(valor2);
                            $$("yearsOpt04_counter02").setValue(valor1);
                        } else {
                            //ERROR 
                            controlTotal = false;
                            webix.message({type:"error", text:$.i18n("cronUi.popup.error.year")});
                            //console.log("ERROR FALLO - YEA 04");
                        }
                    } else {
                        if (valorList[6].search(",")>0) {
                            let control = true;
                            let valores = valorList[6].split(",");
                            let valor1 = null;
                            let valor2 = null;
                            try {
                                valores.forEach(item =>{
                                    valor1 = parseInt(item);
                                });
                            } catch (e) {
                                control = false
                            }
    
                            if(control){
                                controlYear = "yearsOpt03";
                                $$("yearsOpt03_combo").setValue(valorList[6]);
                            } else {
                                //ERROR 
                                controlTotal = false;
                                webix.message({type:"error", text:$.i18n("cronUi.popup.error.year")});
                                //console.log("ERROR FALLO , YEA 03");
                            }
                        } else {
                            //un año individual
                            let control = true;
                            //let valores = valorList[6].split(",");
                            let valor1 = null;
                            let valor2 = null;
                            try {
                                valor1 = parseInt(valorList[6]);
                            } catch (e) {
                                control = false
                            }
    
                            if(control){
                                controlYear = "yearsOpt03";
                                $$("yearsOpt03_combo").setValue(valorList[6]);
                            } else {
                                //ERROR 
                                controlTotal = false;
                                webix.message({type:"error", text:$.i18n("cronUi.popup.error.year")});
                                //console.log("ERROR FALLO , YEA 03_01");
                            }
                        }    
                    }
                }
            }

            if(controlTotal){
                $$("secondsOpt01").setValue(0);
                $$("secondsOpt02").setValue(0);
                $$("secondsOpt03").setValue(0);
                $$("secondsOpt04").setValue(0);
                
                $$("minsOpt01").setValue(0);
                $$("minsOpt02").setValue(0);
                $$("minsOpt03").setValue(0);
                $$("minsOpt04").setValue(0);
                
                $$("hoursOpt01").setValue(0);
                $$("hoursOpt02").setValue(0);
                $$("hoursOpt03").setValue(0);
                $$("hoursOpt04").setValue(0);

                $$("daysOpt01").setValue(0);
                $$("daysOpt02").setValue(0);
                $$("daysOpt03").setValue(0);
                //$$("daysOpt04").setValue(0);
                $$("daysOpt05").setValue(0);

                $$("monthsOpt01").setValue(0);
                $$("monthsOpt02").setValue(0);
                $$("monthsOpt03").setValue(0);
                $$("monthsOpt04").setValue(0);

                $$("yearsOpt01").setValue(0);
                $$("yearsOpt02").setValue(0);
                $$("yearsOpt03").setValue(0);
                $$("yearsOpt04").setValue(0);

                $$(controlSecond).setValue(1);
                $$(controlMinute).setValue(1);
                $$(controlHour).setValue(1);
                $$(controlDay).setValue(1);
                $$(controlMonth).setValue(1);
                $$(controlYear).setValue(1);

                $$("inputsHeaderSeconds").setValue(valorList[0]);
                $$("inputsHeaderMinutes").setValue(valorList[1]);
                $$("inputsHeaderHours").setValue(valorList[2]);
                $$("inputsHeaderDays").setValue(valorList[3]);
                $$("inputsHeaderMonths").setValue(valorList[4]);
                $$("inputsHeaderYears").setValue(valorList[6]);
            } else {
                webix.message({type:"error", text:$.i18n("cronUi.popup.error.total")});
            }
        }
    }
}