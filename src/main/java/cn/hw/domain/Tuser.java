package cn.hw.domain;

import java.util.Date;

public class Tuser implements java.io.Serializable {
	private static final long serialVersionUID = -1266930502943041919L;
	private String id;
	private String name;
	private String pwd;
	private Date creatTime;
	private Date endTime;

	// Constructors

	/** default constructor */
	public Tuser() {
	}

	/** minimal constructor */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}