package cn.hw.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import cn.hw.pageModel.Menu;
import cn.hw.service.RepairService;

@Namespace("/")
@Action(value = "repairAction")
public class RepairAction extends BaseAction {
	private RepairService repairService;

	public RepairService getRepairService() {
		return repairService;
	}
	@Autowired
	public void setRepairService(RepairService repairService) {
		this.repairService = repairService;
	}

	public void init() {
		this.repairService.repair();
	}
}
