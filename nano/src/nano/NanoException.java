package nano;

import com.alibaba.fastjson.JSONObject;

public class NanoException  extends Exception {
	
	private int exceptionId;
	private String info;
	
	public String toString(){
		JSONObject obj = new JSONObject();
		obj.put("exId", exceptionId);
		obj.put("info", info);
		return obj.toJSONString();
	}
	
	public NanoException(byte exceptionId,String info) {
		super(info);
		this.info = info;
		this.exceptionId = exceptionId;
	}
		
	public int getExceptionId() {
		return exceptionId;
	}
	public void setExceptionId(int exceptionId) {
		this.exceptionId = exceptionId;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
}
