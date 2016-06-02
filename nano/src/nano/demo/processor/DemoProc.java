package nano.demo.processor;

import nano.NanoException;
import nano.web.framework.BaseProcWithLog;
import nano.web.framework.CommandObj;
import nano.web.framework.SessionObj;

public class DemoProc extends BaseProcWithLog {

	
	@Override
	protected String processCmd(SessionObj sessionObj, CommandObj cmdObj)
			throws NanoException {
		
		return "{\"result\":0}";
	}

}
