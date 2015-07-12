package cn.hw.pageModel;

import java.util.Map;

import cn.hw.domain.Tmenu;

public class Menu {
	private String state;//应对EasyUI所需要的json数据，建立的属性
	
	private String pid; //对应父类
	private Tmenu ptext;//对应父类的名字
	private String id;
	private String text;
	private String iconcls;
	private String url;
	
	private Map<String,Object> attributes;
	
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public Tmenu getPtext() {
		return ptext;
	}
	public void setPtext(Tmenu ptext) {
		this.ptext = ptext;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconcls() {
		return iconcls;
	}
	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
}
