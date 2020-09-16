
export default class CronUi {
	constructor(input_id,popupId) {
        var input = input_id;
        var popupId = popupId;
        this.inputsHeader = {
            view: "form",
            id: "inputsHeader",
            width: 0,
            margin: 0,
            elements: [ 
                {
                    cols:[
                        { view: "text", id: "inputsHeaderSeconds", name: "seconds", value: "*"},
                        { view: "text", id: "inputsHeaderMinutes", name: "minutes", value: "*"},
                        { view: "text", id: "inputsHeaderHours",   name: "hours", value: "*"},
                        { view: "text", id: "inputsHeaderDays",    name: "days", value: "*"},
                        { view: "text", id: "inputsHeaderMonths",  name: "months", value: "*"},
                        //{ view: "text", id: "inputsHeaderWeeks",   name: "weeks"},
                        { view: "text", id: "inputsHeaderYears",   name: "years", value: "*"},
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
                                    labelRight:"Every second", 
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
                                            labelRight:"Every", 
                                            //labelWidth: 80,
                                            value:0,
                                            click: function(id,event){
                                                let counter01 = $$("secondsOpt02_counter01").getValue();
                                                let counter02 = $$("secondsOpt02_counter02").getValue();

                                                if($$("secondsOpt02").data.value === 1){
                                                    $$("inputsHeaderSeconds").setValue(counter01 + "/" + counter02);
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
                                            max: 59,
                                            click: function(id,event){
                                                let counter01 = $$("secondsOpt02_counter01").getValue();
                                                let counter02 = $$("secondsOpt02_counter02").getValue();
                                                $$("inputsHeaderSeconds").setValue(counter01 + "/" + counter02);
                                            }
                                        },
                                        { 
                                            view:"label", 
                                            label: "second(s) starting at second", 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { 
                                            view: "counter", id: "secondsOpt02_counter02" ,name: "", 
                                            click: function(id,event){
                                                let counter01 = $$("secondsOpt02_counter01").getValue();
                                                let counter02 = $$("secondsOpt02_counter02").getValue();
                                                $$("inputsHeaderSeconds").setValue(counter01 + "/" + counter02);
                                            },
                                            value: 1, min: 1, max: 59},
                                        { gravity:1}
                                    ]
                                },
                                {
                                    view:"checkbox", 
                                    id:"secondsOpt03", 
                                    labelRight:"Specific second (choose one or many)", 
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
                                        { view:"multicombo", id: "secondsOpt03_combo", name: "secondsOpt03_combo", value: "00", placeholder:"Tags",
                                            options:["00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"], //newValues: true
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
                                            labelRight:"Every second",
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
                                            label: " between second", 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { view: "counter", name: "", id: "secondsOpt04_counter01", 
                                            value: 1, 
                                            click: function(id,event){
                                                let counter01 = $$("secondsOpt04_counter01").getValue();
                                                let counter02 = $$("secondsOpt04_counter02").getValue();
                                                $$("inputsHeaderSeconds").setValue(counter01 + "-" + counter02);
                                            },
                                            min: 1, max: 59},
                                        { 
                                            view:"label", 
                                            label: "and second", 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { view: "counter", name: "", id: "secondsOpt04_counter02", 
                                            value: 1, 
                                            click: function(id,event){
                                                let counter01 = $$("secondsOpt04_counter01").getValue();
                                                let counter02 = $$("secondsOpt04_counter02").getValue();
                                                $$("inputsHeaderSeconds").setValue(counter01 + "-" + counter02);
                                            },
                                            min: 1, max: 59},
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
                                    labelRight:"Every minute", 
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
                                            labelRight:"Every", 
                                            //labelWidth: 80,
                                            value:0,
                                            click: function(id,event){
                                                let counter01 = $$("minsOpt02_counter01").getValue();
                                                let counter02 = $$("minsOpt02_counter02").getValue();

                                                if($$("minsOpt02").data.value === 1){
                                                    $$("inputsHeaderMinutes").setValue(counter01 + "/" + counter02);
                                                    //$$("secondsOpt01").setValue(0);
                                                    $$("minsOpt01").setValue(0);
                                                    $$("minsOpt03").setValue(0);
                                                    $$("minsOpt04").setValue(0);
                                                }
                                            }
                                        },
                                        { view: "counter", name: "",id: "minsOpt02_counter01", value: 1, min: 1, max: 59,
                                            click: function(id,event){
                                                let counter01 = $$("minsOpt02_counter01").getValue();
                                                let counter02 = $$("minsOpt02_counter02").getValue();
                                                $$("inputsHeaderMinutes").setValue(counter01 + "/" + counter02);
                                            }
                                        },
                                        { 
                                            view:"label", 
                                            label: "minute(s) starting at minute", 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { view: "counter", name: "", id: "minsOpt02_counter02", value: 1, min: 1, max: 59,
                                            click: function(id,event){
                                                let counter01 = $$("minsOpt02_counter01").getValue();
                                                let counter02 = $$("minsOpt02_counter02").getValue();
                                                $$("inputsHeaderMinutes").setValue(counter01 + "/" + counter02);
                                            }
                                        },
                                        { gravity:1}
                                    ]
                                },
                                {
                                    view:"checkbox", 
                                    id:"minsOpt03", 
                                    labelRight:"Specific minute (choose one or many)", 
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
                                        { view:"multicombo", id: "minsOpt03_combo", name: "minsOpt03_combo", value: "00", placeholder:"Tags",
                                            options:["00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"], //newValues: true
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
                                            labelRight:"Every minute",
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
                                            label: " between minute", 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { view: "counter", id: "minsOpt04_counter01", name: "", value: 1, 
                                            click: function(id,event){
                                                let counter01 = $$("minsOpt04_counter01").getValue();
                                                let counter02 = $$("minsOpt04_counter02").getValue();
                                                $$("inputsHeaderMinutes").setValue(counter01 + "-" + counter02);
                                            },
                                            min: 1, max: 59},
                                        { 
                                            view:"label", 
                                            label: "and minute", 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { view: "counter", name: "", id: "minsOpt04_counter02",
                                            click: function(id,event){
                                                let counter01 = $$("minsOpt04_counter01").getValue();
                                                let counter02 = $$("minsOpt04_counter02").getValue();
                                                $$("inputsHeaderMinutes").setValue(counter01 + "-" + counter02);
                                            },
                                            value: 1, min: 1, max: 59},
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
                                    labelRight:"Every hour", 
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
                                            labelRight:"Every", 
                                            //labelWidth: 80,
                                            value:0,
                                            click: function(id,event){
                                                let counter01 = $$("hoursOpt02_counter01").getValue();
                                                let counter02 = $$("hoursOpt02_counter02").getValue();

                                                if($$("hoursOpt02").data.value === 1){
                                                    $$("inputsHeaderHours").setValue(counter01 + "/" + counter02);
                                                    //$$("secondsOpt01").setValue(0);
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
                                                $$("inputsHeaderHours").setValue(counter01 + "/" + counter02);
                                            },
                                            value: 1, min: 1, max: 59},
                                        { 
                                            view:"label", 
                                            label: "hour(s) starting at hour", 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { view: "counter", name: "", id: "hoursOpt02_counter02",
                                            click: function(id,event){
                                                let counter01 = $$("hoursOpt02_counter01").getValue();
                                                let counter02 = $$("hoursOpt02_counter02").getValue();
                                                $$("inputsHeaderHours").setValue(counter01 + "/" + counter02);
                                            },
                                            value: 1, min: 1, max: 59},
                                        { gravity:1}
                                    ]
                                },
                                {
                                    view:"checkbox", 
                                    id:"hoursOpt03", 
                                    labelRight:"Specific hour (choose one or many)", 
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
                                        { view:"multicombo", id: "hoursOpt03_combo", name: "hoursOpt03_combo", value: "00", placeholder:"Tags",
                                            options:["00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"], //newValues: true
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
                                            labelRight:"Every hour",
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
                                            label: " between hour", 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { view: "counter", name: "", id: "hoursOpt04_counter01",
                                            click: function(id,event){
                                                let counter01 = $$("hoursOpt04_counter01").getValue();
                                                let counter02 = $$("hoursOpt04_counter02").getValue();
                                                $$("inputsHeaderHours").setValue(counter01 + "-" + counter02);
                                            },
                                            value: 1, min: 1, max: 59},
                                        { 
                                            view:"label", 
                                            label: "and hour", 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { view: "counter", name: "", id: "hoursOpt04_counter02",
                                            click: function(id,event){
                                                let counter01 = $$("hoursOpt04_counter01").getValue();
                                                let counter02 = $$("hoursOpt04_counter02").getValue();
                                                $$("inputsHeaderHours").setValue(counter01 + "-" + counter02);
                                            },
                                            value: 1, min: 1, max: 59},
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
                                    labelRight:"Every day", 
                                    value:1,
                                    click: function(id,event){
                                        if($$("daysOpt01").data.value === 1){
                                            $$("inputsHeaderDays").setValue("*");
                                            //$$("secondsOpt01").setValue(0);
                                            $$("daysOpt02").setValue(0);
                                            $$("daysOpt03").setValue(0);
                                            $$("daysOpt04").setValue(0);
                                            $$("daysOpt05").setValue(0);
                                        }
                                    }
                                },
                                {
                                    cols:[
                                        {
                                            view:"checkbox", 
                                            id:"daysOpt02", 
                                            labelRight:"Every", 
                                            //labelWidth: 80,
                                            value:0,
                                            click: function(id,event){
                                                let counter01 = $$("daysOpt02_counter01").getValue();
                                                let counter02 = $$("daysOpt02_counter02").getValue();

                                                if($$("daysOpt02").data.value === 1){
                                                    $$("inputsHeaderDays").setValue(counter01 + "/" + counter02);
                                                    //$$("secondsOpt01").setValue(0);
                                                    $$("daysOpt01").setValue(0);
                                                    $$("daysOpt03").setValue(0);
                                                    $$("daysOpt04").setValue(0);
                                                    $$("daysOpt05").setValue(0);
                                                }
                                            }
                                        },
                                        { view: "counter", name: "", id: "daysOpt02_counter01", 
                                            click: function(id,event){
                                                let counter01 = $$("daysOpt02_counter01").getValue();
                                                let counter02 = $$("daysOpt02_counter02").getValue();
                                                $$("inputsHeaderDays").setValue(counter01 + "/" + counter02);
                                            },
                                            value: 1, min: 1, max: 59},
                                        { 
                                            view:"label", 
                                            label: "day(s) starting on", 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { view: "counter", name: "", id: "daysOpt02_counter02", 
                                            click: function(id,event){
                                                let counter01 = $$("daysOpt02_counter01").getValue();
                                                let counter02 = $$("daysOpt02_counter02").getValue();
                                                $$("inputsHeaderDays").setValue(counter01 + "/" + counter02);
                                            },
                                            value: 1, min: 1, max: 59},
                                        { gravity:1}
                                    ]
                                },
                                {
                                    view:"checkbox", 
                                    id:"daysOpt03", 
                                    labelRight:"Specific day of month (choose one or many)", 
                                    value:0,
                                    click: function(id,event){
                                        if($$("daysOpt03").data.value === 1){
                                            let multi = $$("daysOpt03_combo").getValue();
                                            $$("inputsHeaderDays").setValue(multi);
                                            
                                            //$$("secondsOpt01").setValue(0);
                                            $$("daysOpt01").setValue(0);
                                            $$("daysOpt02").setValue(0);
                                            $$("daysOpt04").setValue(0);
                                            $$("daysOpt05").setValue(0);
                                        }
                                    }
                                },
                                {
                                    cols:[
                                        {},
                                        { view:"multicombo", id: "daysOpt03_combo", name: "daysOpt03_combo", value: "00", placeholder:"Tags",
                                            options:["01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"], //newValues: true
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
                                {
                                    view:"checkbox", 
                                    id:"daysOpt05", 
                                    labelRight:"Unspecified", 
                                    value:1,
                                    click: function(id,event){
                                        if($$("daysOpt05").data.value === 1){                                          
                                            $$("inputsHeaderDays").setValue("?");
                                            //$$("secondsOpt01").setValue(0);
                                            $$("daysOpt01").setValue(0);
                                            $$("daysOpt02").setValue(0);
                                            $$("daysOpt03").setValue(0);
                                            $$("daysOpt04").setValue(0);
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
                                    labelRight:"Every month", 
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
                                            labelRight:"Every", 
                                            //labelWidth: 80,
                                            value:0,
                                            click: function(id,event){
                                                let counter01 = $$("monthsOpt02_counter01").getValue();
                                                let counter02 = $$("monthsOpt02_counter02").getValue();

                                                if($$("monthsOpt02").data.value === 1){
                                                    $$("inputsHeaderMonths").setValue(counter01 + "/" + counter02);
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
                                                $$("inputsHeaderMonths").setValue(counter01 + "/" + counter02);
                                            },
                                            value: 1, min: 1, max: 12},
                                        { 
                                            view:"label", 
                                            label: "month(s) starting in", 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        {
                                            view:"combo", 
                                            label: 'Combo',
                                            value:"January", 
                                            id: "monthsOpt02_counter02", 
                                            options:["January", "February", "March","April","May","June","July","August","September","October","November","December"],
                                            click: function(id,event){
                                                let counter01 = $$("monthsOpt02_counter01").getValue();
                                                let counter02 = $$("monthsOpt02_counter02").getValue();
                                                $$("inputsHeaderMonths").setValue(counter01 + "/" + counter02);
                                            },
                                        },
                                        { gravity:1}
                                    ]
                                },
                                {
                                    view:"checkbox", 
                                    id:"monthsOpt03", 
                                    labelRight:"Specific month (choose one or many)", 
                                    value:0,
                                    click: function(id,event){
                                        if($$("monthsOpt03").data.value === 1){
                                            let multi = $$("monthsOpt03_combo").getValue();
                                            $$("inputsHeaderMonths").setValue(multi);
                                            
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
                                        { view:"multicombo", id: "monthsOpt03_combo", name: "monthsOpt03_combo", value: "00", placeholder:"Tags",
                                            options:["January", "February", "March","April","May","June","July","August","September","October","November","December"],
                                            on: {
                                                'onChange': function(obj){ 
                                                    if($$("monthsOpt03").data.value === 1){
                                                        let multi = $$("monthsOpt03_combo").getValue();
                                                        $$("inputsHeaderMonths").setValue(multi);
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
                                            labelRight:"Every month between ",
                                            //labelWidth: 0, 
                                            value:0,
                                            click: function(id,event){
                                                if($$("monthsOpt04").data.value === 1){
                                                    let counter01 = $$("monthsOpt04_counter01").getValue();
                                                    let counter02 = $$("monthsOpt04_counter02").getValue();                                            
                                                    $$("inputsHeaderMonths").setValue(counter01 + "-" + counter02);
                                                    //$$("secondsOpt01").setValue(0);
                                                    $$("monthsOpt01").setValue(0);
                                                    $$("monthsOpt02").setValue(0);
                                                    $$("monthsOpt03").setValue(0);
                                                }
                                            }
                                        },
                                        {
                                            view:"combo", 
                                            label: 'Combo',
                                            id: "monthsOpt04_counter01",
                                            value:"January", 
                                            click: function(id,event){
                                                let counter01 = $$("monthsOpt04_counter01").getValue();
                                                let counter02 = $$("monthsOpt04_counter02").getValue();
                                                $$("inputsHeaderMonths").setValue(counter01 + "-" + counter02);
                                            },
                                            options:["January", "February", "March","April","May","June","July","August","September","October","November","December"]
                                        },
                                        { 
                                            view:"label", 
                                            label: " and ", 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        {
                                            view:"combo", 
                                            label: 'Combo',
                                            id: "monthsOpt04_counter02",
                                            value:"January", 
                                            click: function(id,event){
                                                let counter01 = $$("monthsOpt04_counter01").getValue();
                                                let counter02 = $$("monthsOpt04_counter02").getValue();
                                                $$("inputsHeaderMonths").setValue(counter01 + "-" + counter02);
                                            },
                                            options:["January", "February", "March","April","May","June","July","August","September","October","November","December"]
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
                                    labelRight:"Any year", 
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
                                            labelRight:"Every", 
                                            //labelWidth: 80,
                                            value:0,
                                            click: function(id,event){
                                                let counter01 = $$("yearsOpt02_counter01").getValue();
                                                let counter02 = $$("yearsOpt02_counter02").getValue();

                                                if($$("yearsOpt02").data.value === 1){
                                                    $$("inputsHeaderYears").setValue(counter01 + "/" + counter02);
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
                                                $$("inputsHeaderYears").setValue(counter01 + "/" + counter02);
                                            },
                                            value: 1, min: 1, max: 100},
                                        { 
                                            view:"label", 
                                            label: "years(s) starting in ", 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
                                        { view: "counter", name: "", id: "yearsOpt02_counter02", 
                                            click: function(id,event){
                                                let counter01 = $$("yearsOpt02_counter01").getValue();
                                                let counter02 = $$("yearsOpt02_counter02").getValue();
                                                $$("inputsHeaderYears").setValue(counter01 + "/" + counter02);
                                            },
                                            value: 2020, min: 2020, max: 2100},
                                        { gravity:1}
                                    ]
                                },
                                {
                                    view:"checkbox", 
                                    id:"yearsOpt03", 
                                    labelRight:"Specific year (choose one or many)", 
                                    value:0,
                                    click: function(id,event){
                                        if($$("yearsOpt03").data.value === 1){
                                            let multi = $$("yearsOpt03_combo").getValue();
                                            $$("inputsHeaderYears").setValue(multi);
                                            
                                            //$$("secondsOpt01").setValue(0);
                                            $$("yearsOpt01").setValue(0);
                                            $$("yearsOpt02").setValue(0);
                                            $$("yearsOpt04").setValue(0);
                                        }
                                    }
                                },
                                {
                                    cols:[
                                        {},
                                        { view:"multicombo", id: "yearsOpt03_combo", name: "yearsOpt03_combo", value: "00", placeholder:"Tags",
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
                                            labelRight:"Every year between ",
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
                                            value: 2020, min: 2020, max: 2100},
                                        { 
                                            view:"label", 
                                            label: " and ", 
                                            //inputWidth:100, 
                                            align:"left"
                                        },
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
                        $$(popupId).destructor();
                    }
                }
			]
        };
    }

    getComponent() {
        let header = "";

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



}
                                /*


/*
var v_week = { 
    "Sunday":1, 
    "Monday":2, 
    "Tuesday":3, 
    "Wednesday":4, 
    "Thursday":5, 
    "Friday":6, 
    "Saturday":7 
};

//var v_month = {};
//"Sunday", "Monday", "Tuesday","Wednesday","Thursday","Friday","Saturday"


                               // {
                                   // cols: [
                                        { view:"radio", name:"persecond", options:[
                                            { value:"Branch2", id:1 },
                                            { value:"Branch1", id:2 },
                                            //{ value:"Branch3", id:3 },
                                            //{ value:"Branch4", id:4 },

/* 
, elements: [
                                                { view: "counter", name: "", value: 1, min: 1, max: 59},
                                                { view: "counter", name: "", value: 1, min: 1, max: 59},  
                                                { view: "counter", name: "", value: 1, min: 1, max: 59},
                                                { view: "counter", name: "", value: 1, min: 1, max: 59}
                                            ],
*/

/*
                                            
                                        ],  align:"center", width:180 },
                                        { view:"radio", name:"gr2x", options:[
                                            { value:"Master", id:1 },
                                            { value:"Branch", id:2 },
                                            { value:"Branc3", id:3 },
                                           // { value:"Branc4", id:4 },
                                          ], width:180, align:"center"},
                                        //{ gravity:8}
                                        
                                    ] 
                                },

                                /*
                                {
                                    cols: [
                                        { view:"radio", name:"from", options:[""], align:"center", width:180 },
                                        { view: "counter", name: "", value: 1, min: 1, max: 59},
                                        { view: "counter", name: "", value: 1, min: 1, max: 59},
                                    ] 
                                },
                                {
                                    cols: [
                                        { view:"radio", name:"from", options:[""], align:"center", width:180 },
                                        { view: "counter", id: "cronUi_seconds_counter01", name: "", value: 1, min: 1, max: 59},
                                        { view: "counter", id: "cronUi_seconds_counter02", name: "", value: 1, min: 1, max: 59, 
                                            on:{
                                                click: function(){
                                                    let counter01 = $$();
                                                }
                                            }
                                        },
                                    ] 
                                }
                                */
                        //    ]
                       // }

                        /*
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


                                        /*
                                        {
                                            view:"checkbox", 
                                            id:"seconds00", 
                                            labelRight:"0", 
                                            //width: 50,
                                            value:0
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds01", 
                                            labelRight:"1", 
                                            //labelPosition:"left",
                                            width: 0,
                                            value:0
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds02", 
                                            labelRight:"2",
                                            //labelPosition:"left", 
                                            width: 60,
                                            value:0
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds03", 
                                            labelRight:"03", 
                                            //labelPosition:"left",
                                            width: 80,
                                            value:0
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds04", 
                                            label:"04", 
                                            labelPosition:"left",
                                            value:0
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds05", 
                                            label:"05", 
                                            labelPosition:"left",
                                            value:0
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds06", 
                                            label:"06", 
                                            labelPosition:"left",
                                            value:0
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds07", 
                                            label:"07", 
                                            labelPosition:"left",
                                            value:0
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds08", 
                                            label:"08", 
                                            labelPosition:"left",
                                            value:0
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds09", 
                                            label:"09", 
                                            labelPosition:"left",
                                            value:0
                                        },
                                        {}
                                    ]
                                },
                                {
                                    cols:[
                                        {},
                                        {
                                            view:"checkbox", 
                                            id:"seconds10", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds11", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds12", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds13", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds14", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds15", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds16", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds17", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds18", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds19", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {}
                                    ]
                                },
                                {
                                    cols:[
                                        {},
                                        {
                                            view:"checkbox", 
                                            id:"seconds20", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds21", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds22", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds23", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds24", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds25", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds26", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds27", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds28", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds29", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {}
                                    ]
                                },
                                {
                                    cols:[
                                        {},
                                        {
                                            view:"checkbox", 
                                            id:"seconds30", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds31", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds32", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds33", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds34", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds35", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds36", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds37", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds38", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds39", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {}
                                    ]
                                },
                                {
                                    cols:[
                                        {},
                                        {
                                            view:"checkbox", 
                                            id:"seconds40", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds41", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds42", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds43", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds44", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds45", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds46", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds47", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds48", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds49", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {}
                                    ]
                                },
                                {
                                    cols:[
                                        {},
                                        {
                                            view:"checkbox", 
                                            id:"seconds50", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds51", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds52", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds53", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds54", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds55", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds56", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds57", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds58", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        {
                                            view:"checkbox", 
                                            id:"seconds59", 
                                            label:"Seconds", 
                                            value:1
                                        },
                                        */





