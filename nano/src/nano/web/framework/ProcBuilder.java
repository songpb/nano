package nano.web.framework;

import java.util.Map;

public class ProcBuilder implements IProcBuilder {
	private Map<String, ICmdProcessor> cmds;
	
	public ICmdProcessor getProcessor(String cmd) {
		return cmds.get(cmd);
	}

	@SuppressWarnings("unchecked")
	public void setCmds(Map cmds) {
		this.cmds = cmds;
	}
}
