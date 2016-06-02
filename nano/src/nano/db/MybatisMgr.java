package nano.db;

import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
public class MybatisMgr {
   private static SqlSessionFactory sqlSessionFactory;
   private static Reader reader;

   protected static Log log = LogFactory.getLog(MybatisMgr.class);
   
   public static boolean isInited(){
	   if(sqlSessionFactory!=null)
		   return true;
	   return false;
   }
   
   public static void setConfig(String cfgFile) {
	   try {
		   reader = Resources.getResourceAsReader(cfgFile);
		   sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
   }

   public static SqlSessionFactory getInstance() {
      return sqlSessionFactory;
   }
}

