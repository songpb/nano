package nano.demo.servlet;
import java.io.IOException;
import java.util.Timer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nano.spring.SpringContainer;
import nano.utils.ContainerUtil;
import nano.web.framework.AbstractServlet;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoServlet extends AbstractServlet {

	private static final long serialVersionUID = 6739834762736372881L;
	
	private SpringContainer container;
	private ClassPathXmlApplicationContext timerContext;

	protected void action(HttpServletRequest httpReq,
			HttpServletResponse httpRes) throws ServletException, IOException {
//		SessionObj session = super.getAuthorizedSession(httpReq, httpRes);		
//		if(session!=null){
//			super.processCmd(session, httpReq, httpRes);
//		}
		super.processCmd(null, httpReq, httpRes);
	}
	
	@Override
	public void init(ServletConfig arg0) throws ServletException {
		super.init(arg0);	
		initService("beans." + arg0.getServletName() + ".servlet.xml");
	}

	private void initService(String cfg) throws ServletException {
		log.info("init authorizedServlet using " + cfg);
		
		container = ContainerUtil.createContainer(cfg);	
		
		timerContext = new ClassPathXmlApplicationContext("beans.demo.timer.xml");
		
		super.setup(container);
	}
	
	@Override
	public void destroy(){
		super.destroy();
	}
}
