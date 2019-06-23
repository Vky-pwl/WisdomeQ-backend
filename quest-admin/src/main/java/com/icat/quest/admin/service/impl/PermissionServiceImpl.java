/**
 * 
 */
package com.icat.quest.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icat.quest.admin.service.PermissionService;
import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.vo.PermissionVo;
import com.icat.quest.dao.PermissionDao;
import com.icat.quest.model.Permission;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionDao permissionDao;

	@Override
	public Integer createPermission(PermissionVo permissionVo, Integer userId) {

		Permission permission = new Permission(permissionVo.getPermissionName(), true, new Date(), new Long(userId),
				new Date(), new Long(userId));
		Integer id = permissionDao.create(permission);
		return id;
	}

	@Override
	public Integer updatePermission(PermissionVo permissionVo, Integer userId) throws Exception {
		if (permissionVo.getPermissionId() == null)
			throw new Exception("PermissionId should not be null");

		Permission permission = permissionDao.read(permissionVo.getPermissionId());
		if (permission == null)
			throw new Exception("Record not found");

		if (permissionVo.getPermissionName() != null) {
			permission.setPermissionName(permissionVo.getPermissionName().trim());
		}
		if (permissionVo.getActive() != null) {
			permission.setActive(permissionVo.getActive());
		}
		permission.setLastModified(new Date());
		permission.setLastModifiedBy(new Long(userId));
		permissionDao.update(permission);
		return permissionVo.getPermissionId();
	}

	@Override
	public PermissionVo readPermission(Integer permissionId, Integer userId) throws Exception {
		if (permissionId == null) {
			throw new Exception("PermissionId should not be null");
		}
		Permission permission = permissionDao.read(permissionId);
		if (permission == null) {
			throw new Exception("Record not found");
		}
		return Transformer.PERMISSION_TRANSFORMER.transform(permission);
	}

	@Override
	public Map<String, Object> listPermission(Integer pageNo, Integer pageSize, String searchKey, Boolean active,
			Integer userId) throws Exception {

		List<Permission> permissionList = new ArrayList<>();
		if (pageNo == null)
			pageNo = 50;
		if (pageSize == null)
			pageSize = 1;
		Map<String, Object> parameters = new HashMap<>();
		Integer startIndex = null;
		if (pageNo != 1)
			startIndex = (pageNo * pageSize) - pageSize;
		if (searchKey != null && active != null) {
			searchKey = "%" + searchKey + "%";
			parameters.put("_2_permissionName", searchKey);
			parameters.put("_1_active", active);
			permissionList = permissionDao.listEntityByParameter(PermissionDao.findAllBySearchKeyWithFilter, parameters,
					startIndex, pageSize);
		} else if (searchKey == null && active != null) {
			parameters.put("_1_active", active);
			permissionList = permissionDao.listEntityByParameter(PermissionDao.findAllByFilter, parameters, startIndex,
					pageSize);
		} else if (searchKey != null && active == null) {
			searchKey = "%" + searchKey + "%";
			parameters.put("_2_permissionName", searchKey);
			permissionList = permissionDao.listEntityByParameter(PermissionDao.findAllBySearchKey, parameters,
					startIndex, pageSize);
		} else {
			permissionList = permissionDao.listEntityByParameter(PermissionDao.findAll, parameters, startIndex,
					pageSize);
		}
		Map<String, Object> permissionResultSet = new HashMap<>();
		if (pageNo == 1) {
			startIndex = (pageNo * pageSize) - pageSize;
			permissionResultSet.put("count", permissionList.size());
			List<PermissionVo> permissionVos = new ArrayList<>();
			permissionList.stream().skip(startIndex).limit(pageSize).forEach(permission -> {
				permissionVos.add(Transformer.PERMISSION_TRANSFORMER.transform(permission));
			});
			permissionResultSet.put("permissionVoList", permissionVos);
		} else {
			List<PermissionVo> permissionVos = new ArrayList<>();
			permissionList.forEach(permission -> {
				permissionVos.add(Transformer.PERMISSION_TRANSFORMER.transform(permission));
			});
			permissionResultSet.put("permissionVoList", permissionVos);
		}

		return permissionResultSet;
	}

}
