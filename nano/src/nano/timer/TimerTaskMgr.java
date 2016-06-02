package nano.timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TimerTaskMgr {

	private static ClassPathXmlApplicationContext timerContext;

	public static void setConfig(String cfgFile) {
		timerContext = new ClassPathXmlApplicationContext(cfgFile);
	}
	
	public static boolean isInited(){
		if(timerContext!=null)
			return true;
		return false;
	}
}
