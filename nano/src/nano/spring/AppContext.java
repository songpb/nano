package nano.spring;

import java.util.*;

public class AppContext {
	private Map<String, Object> property = new HashMap<String, Object>();
	
	private static AppContext context = new AppContext();
	
	private AppContext() {
		
	}

	public static AppContext getInstance(){
		return context;
	}
	
	public Object getProperty(String name) {
		return property.get(name);
	}
	
	public void setProperty(String name, Object value){
		property.put(name, value);
	}
}