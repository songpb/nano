/**
 * 命令参数
 * 产生自HttpRequest.getParamterMap()或ReqParser
 */
package nano.web.framework;

import java.util.Map;

public class CommandObj {
	private Map params;

	private String cmd;

	public CommandObj() {
	}

	public CommandObj(String cmd, Map param) {
		this.params = param;
		this.cmd = cmd;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map param) {
		this.params = param;
	}

	public String getParam(String paramName) {
		Object obj = params.get(paramName);
		if(null == obj)
			return null;
		if (obj instanceof String)
			return (String) obj;
		else
			return ((String[]) obj)[0];
	}
	public Object getValue(String keyName){
		Object obj = params.get(keyName);
		return obj;
	}
}
