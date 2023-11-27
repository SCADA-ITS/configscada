/**
 *
 * AutomatismTest
 *
 */
class AutomatismTest {

	GroovyShell shell;
	org.apache.logging.log4j.Logger log;
		
	AutomatismTest(org.apache.logging.log4j.Logger log) {

		shell = new GroovyShell();
		this.log = log;
	}
	
	boolean process(String message) {

		log.info(message);
		
		return true;
	}
}