package nano.spring;

import nano.utils.MiscUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class SpringContainer {
	protected static Log log = LogFactory.getLog(SpringContainer.class);
	private XmlBeanFactory factory;

	public SpringContainer(String beanCfgFile) {
		factory = new XmlBeanFactory(new ClassPathResource(beanCfgFile));
	}

	public SpringContainer(String beanCfgFile, String[] properties) {
		factory = new XmlBeanFactory(new ClassPathResource(beanCfgFile));
		PropertyPlaceholderConfigurer cfg = new PropertyPlaceholderConfigurer();
		int len = properties.length;
		//cfg.setLocation(new ClassPathResource(properties));
		ClassPathResource[] resources = new ClassPathResource[len];
		for(int i=0; i<len; i++)
			resources[i] = new ClassPathResource(properties[i]);
		cfg.setLocations(resources);
		cfg.postProcessBeanFactory(factory);
	}

	public Object getBean(String beanName) {
		try {
			return factory.getBean(beanName);
		} catch (NoSuchBeanDefinitionException e) {			
			return null;
		} catch (Exception e) {
			log.error(e);
			log.error(MiscUtil.traceInfo(e));
			return null;
		}
	}
}