package cn.mldn.travel.service.back.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.travel.dao.IActionDAO;
import cn.mldn.travel.dao.IDeptDAO;
import cn.mldn.travel.dao.IEmpDAO;
import cn.mldn.travel.dao.ILevelDAO;
import cn.mldn.travel.dao.IRoleDAO;
import cn.mldn.travel.service.back.IEmpServiceBack;
import cn.mldn.travel.service.back.abs.AbstractService;
import cn.mldn.travel.vo.Emp;

@Service
public class EmpServiceBackImpl extends AbstractService implements IEmpServiceBack {
	@Resource
	private IEmpDAO empDAO;
	@Resource
	private IRoleDAO roleDAO;
	@Resource
	private IActionDAO actionDAO;
	@Resource
	private ILevelDAO levelDAO;
	@Resource
	private IDeptDAO deptDAO ;
	
	@Override
	public Map<String, Object> getAddPre() {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("allDepts", this.deptDAO.findAll()) ;
		map.put("allLevels", this.levelDAO.findAll()) ;
		return map ;
	}

	@Override
	public Map<String, Object> getDetails(String eid) {
		Map<String,Object> map = new HashMap<String,Object>() ;
		Emp emp = this.empDAO.findById(eid) ;
		if (emp != null) {
			map.put("dept", this.deptDAO.findById(emp.getDid())) ;
			map.put("level", this.levelDAO.findById(emp.getLid())) ;
		}
		map.put("emp", emp) ;
		return map;
	}
	
	@Override
	public Map<String, Object> get(String eid, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		Emp emp = this.empDAO.findById(eid);
		if (emp != null) {
			if (password.equals(emp.getPassword())) {
				map.put("level", this.levelDAO.findById(emp.getLid()));
			}
		}
		map.put("emp", emp);
		return map;
	}

	@Override
	public Map<String, Set<String>> listRoleAndAction(String eid) {
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		map.put("allRoles", this.roleDAO.findAllIdByEmp(eid));
		map.put("allActions", this.actionDAO.findAllIdByEmp(eid));
		return map;
	}

}
