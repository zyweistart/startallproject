package org.platform.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.framework.service.impl.RootServiceImpl;
import org.framework.utils.StringUtils;
import org.platform.manager.dao.PermissionDao;
import org.platform.manager.entity.Permission;
import org.platform.manager.service.PermissionService;
import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;

@Service("permissionService")
public final class PermissionServiceImpl extends RootServiceImpl<Permission,String> 
implements PermissionService {

	private PermissionDao permissionDao;
	
	public PermissionServiceImpl(@Qualifier("permissionDao")PermissionDao permissionDao) {
		super(permissionDao);
		this.permissionDao=permissionDao;
	}
	
	private final static String SQL_permission_save="INSERT INTO MAN_PERMISSION(ID,NAME,ACTION,VALUE) SELECT ?,?,?,NVL(MAX(ACL),0)+1 FROM MAN_PERMISSION";
	@Override
	public void save(Permission entity) {
		permissionDao.executeUpdate(SQL_permission_save,
				StringUtils.random(),
				entity.getName(),
				entity.getAction());
	}

	private final static String SQL_isPresenceByAction="SELECT COUNT(1) FROM MAN_PERMISSION WHERE ACTION=?";
	@Override
	public Boolean isPresenceByAction(String action) {
		return permissionDao.polymerizationQuery(SQL_isPresenceByAction,action)!=0;
	}
	
	private final static String SQL_loadPermissionByAction="SELECT * FROM MAN_PERMISSION WHERE ACTION=?";
	@Override
	public Permission loadPermissionByAction(String action) {
		return createNativeSQLOnlySingle(SQL_loadPermissionByAction, action);
	}
	@Override
	public List<Permission> loadPermissionsByActions(String...actions) {
		List<Permission> permissions=new ArrayList<Permission>();
		for(String action:actions){
			Permission permission=loadPermissionByAction(action);
			if(permission!=null){
				permissions.add(permission);
				permission=null;
			}
		}
		return permissions;
	}

}