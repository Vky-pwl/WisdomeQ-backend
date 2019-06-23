package com.icat.quest.common.transformer;

import com.icat.quest.common.vo.PermissionVo;
import com.icat.quest.model.Permission;

public class PermissionTransformer implements Transformer<Permission, PermissionVo> {

	@Override
	public PermissionVo transform(Permission permission) {
		if (permission == null) {
			return null;
		}
		PermissionVo permissionVo = new PermissionVo();
		permissionVo.setPermissionId(permission.getPermissionId());
		permissionVo.setPermissionName(permission.getPermissionName());
		permissionVo.setActive(permission.getActive());
		return permissionVo;
	}
}
