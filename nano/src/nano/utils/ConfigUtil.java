package nano.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ConfigUtil {
	private static final Log log = LogFactory.getLog(ConfigUtil.class);

	private ConfigUtil() {
	}

	/***
	 * ��ȡ�����ļ���ָ�������ļ����ַ�����Ϊ֧�������Ը���������ֵ�������ļ�ʹ��UTF-8���뱣�棬��ȡ��ʱ��ָ��charsetNameΪUTF-8��
	 * @param configFile �ļ�����
	 * @param charsetName �ַ�����Ĭ��ΪUTF-8��
	 * @return
	 */
	public static Properties getProperties(String configFile, String charsetName) {
		Properties prop = new Properties();
		String cfg = configFile.trim();
		InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream(
				cfg);
		if (is == null) {
			is = ConfigUtil.class.getResourceAsStream(cfg);
			if (is == null)
				try {
					is = new FileInputStream(cfg);
				} catch (FileNotFoundException e) {
					log.fatal("Config File not found: " + cfg);
					log.error(e);
					log.error(MiscUtil.traceInfo(e));
				}
		}
		if (is != null)
			try {
				prop.load(new InputStreamReader(is,
						charsetName == null ? "UTF-8" : charsetName));
			} catch (IOException e) {
				log.fatal("Config File read error: " + cfg);
				log.error(e);
				log.error(MiscUtil.traceInfo(e));
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					log.error(e);
					log.error(MiscUtil.traceInfo(e));
				}
			}
		return prop;
	}

	public static Properties getProperties(String configFile) {
		Properties prop = new Properties();
		String cfg = configFile.trim();
		InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream(
				cfg);
		if (is == null) {
			is = ConfigUtil.class.getResourceAsStream(cfg);
			if (is == null)
				try {
					is = new FileInputStream(cfg);
				} catch (FileNotFoundException e) {
					log.fatal("Config File not found: " + cfg);
					log.error(e);
					log.error(MiscUtil.traceInfo(e));
				}
		}
		if (is != null)
			try {
				prop.load(is);
			} catch (IOException e) {
				log.fatal("Config File read error: " + cfg);
				log.error(e);
				log.error(MiscUtil.traceInfo(e));
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					log.error(e);
					log.error(MiscUtil.traceInfo(e));
				}
			}
		return prop;
	}

	public static int getIdCount(Properties config) {
		if (config == null) {
			return 0;
		}
		Set set = config.keySet();
		Iterator it = set.iterator();
		int i = 0;
		while (it.hasNext()) {
			String key = (String) (it.next());
			try {
				int num = Integer.parseInt(key);

				if (num > i) {
					i = num;
				}
			} catch (NumberFormatException e) {
			}
		}
		return ++i;
	}

}
