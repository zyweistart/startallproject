package test;

import test.file.FileTestOperator;
import test.manager.ManagerTestOperator;
import test.manager.PermissionTestOperator;
import test.user.UserTestOperator;



public class TestProject {
	
	public final static String accessid="429ff437114ed60cd69cfcfef46b3e12";
	public final static String accesskey="953cd4de4f6f8bc354cf718dc5c76c57";
	
	static FileTestOperator file=new FileTestOperator();
	static UserTestOperator user=new UserTestOperator();
	static ManagerTestOperator manager=new ManagerTestOperator();
	static PermissionTestOperator permission=new PermissionTestOperator();
	
	public static void main(String[] args) throws Exception {
//		user.register();
//		user.login();
//		user.resendActivationEmail();
//		user.activation();
//		user.changePassword();
//		user.forgetPassword();
//		user.resetPassword();
//		file.upload();
//		file.download();
//		user.register();
//		file.uploadapply();
		manager.login();
//		manager.create();
//		permission.createPermission();
//		permission.createPermissionbuilder();
//		permission.builderPermissionTree();
	}
	
}