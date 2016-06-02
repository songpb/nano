package nano.web.framework;

import nano.NanoException;

public interface ICmdProcessor {
	public String process(SessionObj sessionObj,CommandObj cmdObj) throws NanoException;
}

