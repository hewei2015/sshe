package cn.hw.pageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 依据EasyUI、Demo后台返回的数据格式来定义页面model
 */
public class DataGrid {
	private Long total=0L;
	private List rows = new ArrayList();
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long long1) {
		this.total = long1;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
}
