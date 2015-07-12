package cn.hw.pageModel;

import java.io.Serializable;

public class HwJson implements Serializable{
	private boolean success = false; ///返回标识：返回的数据是否正确
	
	private String msg = "";//返回的数据信息
	
	private Object obj = null;//★★在后台增加、删除、修改以后可能还要返回相应对象给前台

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	

}
