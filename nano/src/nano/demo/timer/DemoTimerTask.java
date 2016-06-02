package nano.demo.timer;

import nano.timer.AbstractTimer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DemoTimerTask extends AbstractTimer{
	
	protected static final Log log = LogFactory.getLog(DemoTimerTask.class);
	
	@Override
	public void run() {
		log.info("timer start");
	}

}
