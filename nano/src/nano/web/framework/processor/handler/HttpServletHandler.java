package nano.web.framework.processor.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface HttpServletHandler {
	public Object httpHandler(HttpServletRequest httpReq,HttpServletResponse httpResp,Object obj) throws IOException;

}
