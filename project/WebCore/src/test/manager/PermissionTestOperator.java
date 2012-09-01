package test.manager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpPost;
import org.platform.manager.entity.Permission;
import org.platform.manager.service.PermissionService;
import org.platform.manager.service.impl.PermissionServiceImpl;
import org.zyweistartframework.config.ConfigConstant;
import org.zyweistartframework.context.GlobalEntityContext;
import org.zyweistartframework.context.annotation.Controller;
import org.zyweistartframework.utils.ClassUtils;

import test.core.Base;

public class PermissionTestOperator extends Base {
	
	public void createPermission() throws Exception{
		httpPost = new HttpPost(SERVERURL+"/permission/create.do");
		params.put("accessid", accessid);
		params.put("name","/manager/remove.do");
		sendRequest(accesskey);
	}
	
	public void createPermissionbuilder() throws Exception{
		httpPost = new HttpPost(SERVERURL+"permission/builder.do");
		sendRequest();
	}
	/**
	 * 生成权限列表
	 */
	public void builderPermissionTree() throws Exception{
		new GlobalEntityContext().initLoadClass();
		PermissionService permissionService=(PermissionService)GlobalEntityContext.getGlobalcontextinjection().createInstance(PermissionServiceImpl.class);
		List<Class<?>> classes=new ArrayList<Class<?>>();
		ClassUtils.findAndAddClassesInPackageByFile("org.platform.manager.action", ConfigConstant.RESOURCEPATH+"org/platform/manager/action", true, classes);
		for(Class<?> prototype:classes){
			Controller controller=prototype.getAnnotation(Controller.class);
			if(controller!=null){
				Method[] methods=prototype.getDeclaredMethods();
				for(Method m:methods){
					Permission per=new Permission();
					per.setAction("/"+controller.value()+"/"+m.getName()+".do");
					if(!permissionService.isPresenceByAction(per.getAction())){
						permissionService.save(per);
					}
				}
			}
		}
	}
	
}
