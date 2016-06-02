/**
 * 产生用户友好的异常提示信息
 * @author caijz
 */
package nano.web.framework;

import java.util.Properties;

import nano.utils.ConfigUtil;

public class ExMsgBuilder {
	private static Properties exceptions;

	private ExMsgBuilder() {
	}

	/**
	 * 返回异常提示信息
	 * 
	 * @param cmd
	 *            ：执行那个命令时返回的异常，可以取commandObj.cmd或发往服务器的cmd的id
	 *            如果没有该命令专用的提示信息，就返回exceptionId的公用提示信息
	 * @param exceptionId
	 *            ：服务器返回的exceptionId 如果没有exceptionId专用的提示信息，就返回cmd的公用提示信息，优先于cmd
	 * @return 由cmd和exceptionId指向的提示信息， 如果找不到，就返回默认的提示信息。
	 */
	public static String getExMesage(String cmd, int exceptionId) {
		String s = "系统内部错误，初始化失败。";
		if (null == exceptions)
			return s;
		s = exceptions.getProperty(exceptionId + "_" + cmd);
		if (null == s)
			s = exceptions.getProperty("_" + cmd);
		if (null == s)
			s = exceptions.getProperty(Integer.toString(exceptionId));
		if (null == s)
			s = exceptions.getProperty("default");
		return s;
	}

	public static void setConfig(String cfgFile) {
		if (null == exceptions)
			exceptions = ConfigUtil.getProperties(cfgFile, "utf-8");
	}

	public static boolean isInited() {
		return null != exceptions;
	}
}
