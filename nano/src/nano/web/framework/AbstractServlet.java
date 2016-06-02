package nano.web.framework;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nano.NanoConstant;
import nano.NanoException;
import nano.db.AbstractDBMgr;
import nano.spring.SpringContainer;
import nano.utils.ConfigUtil;
import nano.utils.MiscUtil;
import nano.web.framework.processor.handler.HandlerBuilder;
import nano.web.framework.processor.handler.HttpServletHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;

public abstract class AbstractServlet extends HttpServlet {

	protected static Log log = LogFactory.getLog(AbstractServlet.class);

	private IProcBuilder procBuilder;
	private HandlerBuilder handlerBuilder;
	private int warnTimeOut = 500;

	protected abstract void action(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain; charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "must-revalidate");
		response.addHeader("Cache-Control", "no-cache");
		response.addHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);

		long startTime = System.currentTimeMillis();
		action(request, response);
		long endTime = System.currentTimeMillis();
		long useTime = endTime - startTime;
		if (useTime > warnTimeOut) {
			String strWarn = getParametersString(request);
			strWarn = "The request use too much time:" + useTime + "."
					+ strWarn;
			log.warn(strWarn);
		} else {
			if (log.isDebugEnabled()) {
				String strlog = getParametersString(request);
				strlog = "Use time:" + useTime + "." + strlog;
				log.debug(strlog);
			}
		}
	}

	private String getParametersString(HttpServletRequest request) {
		String name, value, strRet = "";
		Enumeration parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			name = (String) parameterNames.nextElement();
			value = request.getParameter(name);
			strRet = strRet + name + ":" + value + ";";
		}
		return strRet;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	protected void setup(SpringContainer container) {
		procBuilder = (IProcBuilder) container.getBean("ProcBuilder");
		handlerBuilder = (HandlerBuilder) container.getBean("HandlerBuilder");

		String config = "setting.nano.properties";
		log.info("init servlet using config:" + config);
		Properties prop = ConfigUtil.getProperties(config);
		if (!ExMsgBuilder.isInited()) {
			ExMsgBuilder.setConfig(prop.getProperty("exception.cfgfile"));
		}
//		if (!TimerTaskMgr.isInited()) {
//			TimerTaskMgr.setConfig(prop.getProperty("timer.cfgfile"));
//		}
		if (!AbstractDBMgr.isInited()) {
			AbstractDBMgr.setConfig(prop.getProperty("mybatis.cfgfile"));
		}
	}

	protected void processCmd(SessionObj sessionObj,HttpServletRequest httpReq,
			HttpServletResponse httpRes) throws IOException {

		String characterEncode = httpReq.getHeader("Content-Encoding");
		httpReq.setCharacterEncoding(null == characterEncode ? "utf-8"
				: characterEncode);

		CommandObj cmdObj = null;
		String cmdStr = "";
		try {
			String cmd = httpReq.getParameter(NanoConstant.CMD_ATTR);
			if (null != cmd) {
				if (log.isDebugEnabled())
					log.debug("cmd=" + cmd);
				cmdObj = new CommandObj(cmd, httpReq.getParameterMap());
			} 
			
			if (checkParam(cmdObj)) {
				ICmdProcessor processor = procBuilder.getProcessor(cmdObj.getCmd());

				//request特殊处理
				if(null!=handlerBuilder){
					HttpServletHandler httpServletReqHandler = handlerBuilder.getHandler(cmdObj.getCmd() + "ReqHandler");
					if (httpServletReqHandler != null) {
						cmdObj=	(CommandObj) httpServletReqHandler.httpHandler(httpReq, httpRes,cmdObj);
					}
				}
				
				//processor的处理过程
				String result = processor.process(sessionObj, cmdObj);
				
				if (null == result) {
					result = "";
				}
				if (log.isDebugEnabled())
					log.debug("result sent:" + result);
				//Response特殊处理
				if(null!=handlerBuilder){
					HttpServletHandler httpServletRespHandler = handlerBuilder
							.getHandler(cmdObj
									.getCmd()+ "RespHandler");
					if (httpServletRespHandler != null) {
						httpServletRespHandler.httpHandler(httpReq, httpRes,result);
						return;
					}
				}
				httpRes.getWriter().write(result);
				
			} else {
				log.error("action cmdstr=" + cmdStr);
				// 非预期的异常
				httpRes.getWriter().write(createErrorJSON("default", 0));
			}
		} catch (NanoException e) {
			log.error("action cmdstr111=" + cmdStr);
			// 上层应用逻辑抛出的异常
			log.error(MiscUtil.traceInfo(e));
			if (null == cmdObj) {
				httpRes.getWriter().write(createErrorJSON(null, e.getExceptionId()));
				log.error("null==cmdObj,ex：" + e);
			} else {
				httpRes.getWriter().write(createErrorJSON(cmdObj.getCmd(), e.getExceptionId()));
				log.error("ex processing " + cmdObj.getCmd() + ":" + e);
			}
		} catch (Exception e) {
			log.error("action cmdstr=" + cmdStr);
			// 非预期的异常
			httpRes.getWriter().write(createErrorJSON("default", 0));
			log.error("unexcepted e:" + MiscUtil.traceInfo(e));
		}
	}
	
	protected boolean checkParam(CommandObj cmdObj) {
		if (cmdObj == null)
			return false;
		String cmd = cmdObj.getCmd();
		if (cmd == null || "".equals(cmd))
			return false;
		return true;
	}
	
	protected String createErrorJSON(String cmd, int exId) {
		JSONObject ret = new JSONObject();
		ret.put("exId", exId);
		ret.put("exDesc", ExMsgBuilder.getExMesage(cmd, exId));

		return ret.toString();
	}
}
