/**
 * �����û��Ѻõ��쳣��ʾ��Ϣ
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
	 * �����쳣��ʾ��Ϣ
	 * 
	 * @param cmd
	 *            ��ִ���Ǹ�����ʱ���ص��쳣������ȡcommandObj.cmd������������cmd��id
	 *            ���û�и�����ר�õ���ʾ��Ϣ���ͷ���exceptionId�Ĺ�����ʾ��Ϣ
	 * @param exceptionId
	 *            �����������ص�exceptionId ���û��exceptionIdר�õ���ʾ��Ϣ���ͷ���cmd�Ĺ�����ʾ��Ϣ��������cmd
	 * @return ��cmd��exceptionIdָ�����ʾ��Ϣ�� ����Ҳ������ͷ���Ĭ�ϵ���ʾ��Ϣ��
	 */
	public static String getExMesage(String cmd, int exceptionId) {
		String s = "ϵͳ�ڲ����󣬳�ʼ��ʧ�ܡ�";
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
