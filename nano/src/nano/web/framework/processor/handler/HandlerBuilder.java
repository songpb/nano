package nano.web.framework.processor.handler;

import java.util.Map;


public class HandlerBuilder implements IHandlerBuilder {
private Map<String, HttpServletHandler> cmds;
	

	@SuppressWarnings("unchecked")
	public void setCmds(Map cmds) {
		this.cmds = cmds;
	}
	//@Override
	public HttpServletHandler getHandler(String cmd) {
		// TODO Auto-generated method stub
		 return cmds.get(cmd);
	}

}
