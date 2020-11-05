if(window.app)
	window.app.module = {
		
		getParamValue(module, param) {
			
			for (var i = 0; i < module.moduleValues.length; i++) {
				var moduleValue = module.moduleValues[i];
				if (moduleValue.moduleParam.id == param) {
					return moduleValue.value;
				}
			}
		},

		getWindowParamValue(module, param) {
			
			for (var i = 0; i < module.windowValues.length; i++) {
				var windowValue = module.windowValues[i];
				if (windowValue.windowParam.id == param) {
					return windowValue.value;
				}
			}
		},
		
		loadScript(path, callback) {

		    var done = false;
		    var scr = document.createElement('script');
		
		    scr.onload = handleLoad;
		    scr.onreadystatechange = handleReadyStateChange;
		    scr.onerror = handleError;
		    scr.src = path;
		
		    function handleLoad() {
		        if (!done) {
		            done = true;
		            callback(path, "ok");
		        }
		    }
		
		    function handleReadyStateChange() {
		        var state;
		
		        if (!done) {
		            state = scr.readyState;
		            if (state === "complete") {
		                handleLoad();
		            }
		        }
		    }
		    function handleError() {
		        if (!done) {
		            done = true;
		            callback(path, "error");
		        }
		    }

			document.body.appendChild(scr);
		}
	};
