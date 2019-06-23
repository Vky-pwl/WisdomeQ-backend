/**
 * 
 */
package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.Permission;


public interface PermissionDao extends GenericDao<Permission,Integer>{

	
	public String findAllBySearchKeyWithFilter="from com.icat.quest.model.Permission permission where "
			+ "permission.active =:_1_active and permission.permissionName like :_2_permissionName order by permission.permissionId desc";
	public String findAllByFilter="from com.icat.quest.model.Permission permission where permission.active =:_1_active order by permission.permissionId desc";
	public String findAllBySearchKey="from com.icat.quest.model.Permission permission where permission.permissionName like :_2_permissionName order by permission.permissionId desc";
	public String findAll = "from com.icat.quest.model.Permission permission order by permission.permissionId desc";
	public String findAllPermissionsByTcId = "select permissionName from Permission Per inner join TestConductorHasPermission TCHP on TCHP.permissionId=Per.permissionId where TCHP.testConductorId=:userName";
}
