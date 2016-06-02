/**
 * 
 */
package nano.web.framework;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nano.NanoException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyPreFilter;

/**
 * @author yhj
 * 
 */
public abstract class BaseProcWithLog implements ICmdProcessor {

	protected static final Log log = LogFactory.getLog(BaseProcWithLog.class);

	private static SimpleDateFormat sdfDayOnly = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private String resExCodeName="exId";

	private String resExDescName="exDesc";

	abstract protected String processCmd(SessionObj sessionObj,
			CommandObj cmdObj) throws NanoException;

	public String process(SessionObj sessionObj, CommandObj cmdObj)
			throws NanoException {

		String result = processCmd((SessionObj) sessionObj, cmdObj);
		String callback = cmdObj.getParam("Callback");
		if(null==callback||callback.trim().equals("")){
			callback = cmdObj.getParam("callback");
		}
		if (callback != null)
			result = callback + "(" + result + ")";
		return result;
	}
	
	protected Map<String, String> cloneParams(CommandObj cmdObj) {
		HashMap<String, String> cParams = new HashMap<String, String>();
		Map params = cmdObj.getParams();
		Set keys = params.keySet();
		Iterator it = keys.iterator();
		while (it.hasNext()) {
			String str = (String) it.next();
			cParams.put(str, cmdObj.getParam(str));
		}
		return cParams;
	}

	protected String getTimestampStr(Timestamp ts) {
		if (null == ts)
			return "";
		String s = sdf.format(ts);
		// if(s.startsWith(nullDay))
		// return "";
		return s;
	}

	protected Timestamp getDayOnlyTimestamp(String strDay) {
		try {
			if (null != strDay)
				return new Timestamp(sdfDayOnly.parse(strDay).getTime());
		} catch (ParseException e) {
			log.warn("invalid day" + strDay);
		}
		return null;
	}
	
	protected String jsonList(int total,List lists,PropertyPreFilter filter){
		if(lists == null)
			return null;
		JSONObject obj = new JSONObject();
		obj.put("total", total<lists.size()?lists.size():total);
		obj.put("size", lists.size());
		//SimplePropertyPreFilter filter = new SimplePropertyPreFilter(User.class, "userId","nickName","areaId");
		String data = "";
		if(filter != null)
			data = JSON.toJSONString(lists, filter);
		else
			data = JSON.toJSONString(lists);
		obj.put("list", JSONArray.parseArray(data));
		return obj.toString();
		
	}

	protected String getDayOnlyStr(Timestamp ts) {
		if (null == ts)
			return "";
		String s = sdfDayOnly.format(ts);
		// if(nullDay.equals(s))
		// return "";
		return s;
	}

	protected String buildOKResult() {
		JSONObject ret = new JSONObject();
		ret.put("result", 0);
		ret.put("desc", "ok");
		return ret.toString();
	}
	
	protected String buildAddOKResult(double id) {
		JSONObject ret = new JSONObject();
		ret.put("result", 0);
		ret.put("desc", "ok");
		ret.put("id", id);
		return ret.toString();
	}

	protected String createExceptionJSON(int result, String cmd ,String desc) {
		JSONObject jObj = new JSONObject();
		jObj.put(resExCodeName, result);
		if(desc != null)
			jObj.put(resExDescName, desc);
		else
			jObj.put(resExDescName, ExMsgBuilder.getExMesage(cmd, result));
		return jObj.toString();
	}
	
	protected String createExceptionJSON(int result, String cmd) {
		JSONObject jObj = new JSONObject();
		jObj.put(resExCodeName, String.valueOf(result));
		jObj.put(resExDescName, ExMsgBuilder.getExMesage(cmd, result));
		return jObj.toString();
	}

}
