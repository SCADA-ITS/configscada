// Create the logger
var logger = log4javascript.getLogger();

// Create a PopUpAppender with default options
var browserConsoleAppender = new log4javascript.BrowserConsoleAppender();

var layout = new log4javascript.PatternLayout("[%d{dd/MM/yyyy HH:mm:ss,SSS}][%-5p]%m%n");

browserConsoleAppender.setLayout(layout);

// Add the appender to the logger
logger.addAppender(browserConsoleAppender);

var log = {

	getLine() {

		var info = stackinfo();

		if (Array.isArray(info) && info.length > 2) {

			return "[" + info[2].file.substring(info[2].file.lastIndexOf('/') + 1) + ":" + info[2].line + "] - ";
		}
		else {

			return " - ";
		}
	},

	trace(message) { logger.trace(log.getLine() + message); },
	debug(message) { logger.debug(log.getLine() + message); },
	info(message) { logger.info(log.getLine() + message); },
	warn(message) { logger.warn(log.getLine() + message); },
	error(message) { logger.error(log.getLine() + message); },
	fatal(message) { logger.fatal(log.getLine() + message); }
};
