package nano.utils;

import nano.spring.SpringContainer;


public class ContainerUtil {	
	protected static SpringContainer container;
	
	private ContainerUtil() {}
	
	public static boolean isInited() {
		return (null != container);
	}
	
	public static void startContainer(String beans, String[] properties){
		container = new SpringContainer(beans, properties);
	}
	
	public static void startContainer(String beans){
		container = new SpringContainer(beans);
	}
	
	public static SpringContainer getContainer() {
		return container;
	}
	
	public static SpringContainer createContainer(String beans) {
		return new SpringContainer(beans);
	}

	public static SpringContainer createContainer(String beans,String[] properties) {
		return new SpringContainer(beans, properties);
	}

	public static Object getBean(String beanName){
		return container.getBean(beanName);
	}
}
