package nano.db;

import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public abstract class AbstractDBMgr {
	
	protected static final Log log = LogFactory.getLog(AbstractDBMgr.class);
	
	protected static SqlSessionFactory sessionFactory;
	
	private static Reader reader;
	   
	public static boolean isInited(){
		if(sessionFactory!=null)
			return true;
		return false;
	}
	   
	public static void setConfig(String cfgFile) {
		try {
			reader = Resources.getResourceAsReader(cfgFile);
			sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}