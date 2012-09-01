package org.zyweistartframework.config;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zyweistartframework.utils.StackTraceInfo;


/**
 * 消息资源
 * @author Start
 */
public final class Message {
	
	private final static Log log=LogFactory.getLog(Message.class);
	
	private final static String BUNDLE_NAME_MESSAGE=ConfigConstant.FRAMEWORKCLASSPATH +".META-INF.Message";

	private final static ResourceBundle RESOURCE_BUNDLE_MESSAGE;
	
	static{
		RESOURCE_BUNDLE_MESSAGE = ResourceBundle.getBundle(BUNDLE_NAME_MESSAGE);
	}
	
	public static String getMessage(Integer key,Object...params){
		if(key == null) {
			return Constants.UNKNOWN;
		}
		try{
			return String.format(RESOURCE_BUNDLE_MESSAGE.getString(String.valueOf(key)), params);
		}catch(MissingResourceException e){
			log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
			return Constants.UNKNOWN;
		}
	}
	
	//Comm
	
	//Context
	/**
	 * Initializing Root WebApplicationContext.Wait....
	 */
	public final static Integer PM_1000=1000;
	/**
	 * 正在为你生成数据表，请稍候........
	 */
	public final static Integer PM_1001=1001;
	/**
	 * WebApplicationContext Close!
	 */
	public final static Integer PM_1002=1002;
	/**
	 * 容器中不存在该%s对象!
	 */
	public final static Integer PM_1003=1003;
	/**
	 * %s：定义的值%s的长度大于%s
	 */
	public final static Integer PM_1004=1004;
	/**
	 * 当前%s字段上未标注%s注解
	 */
	public final static Integer PM_1005=1005;
	//Config
	
	//Annotation
	/**
	 * %s--已经注册，重复注册！
	 */
	public final static Integer PM_3000=3000;
	/**
	 * %s实体的主键未定义！
	 */
	public final static Integer PM_3001=3001;
	/**
	 * %s实体的主键重复定义，根据约定一个表主键只能有一个，可用唯一约束加非空来代替！
	 */
	public final static Integer PM_3002=3002;
	/**
	 * %s实体%s成员，为多对多关系方，双方均未注解@JoinTable！
	 */
	public final static Integer PM_3003=3003;
	/**
	 * %s实体%s成员，为多对多关系方，只能有一方可以注解@JoinTable,重复定义！
	 */
	public final static Integer PM_3004=3004;
	/**
	 * %s实体%s成员，未定义一多对或多对一注解!
	 */
	public final static Integer PM_3005=3005;
	/**
	 * %s实体%s成员，未定义多对多注解!
	 */
	public final static Integer PM_3006=3006;
	/**
	 * %s实体%s成员，未定义一对一注解!
	 */
	public final static Integer PM_3007=3007;
	/**
	 * %s实体%s成员与%s实体%s成员，一对一都定义为关系映射方，只需在一方定义!
	 */
	public final static Integer PM_3008=3008;
	/**
	 * %s实体%s成员与%s实体%s成员，一对一关系映射方未定义!
	 */
	public final static Integer PM_3009=3009;
	/**
	 * %s实体类中%s字段由于安全限制无法访问，请检查其修饰符!
	 */
	public final static Integer PM_3010=3010;
	/**
	 * %s实体类中%s字段未定义!
	 */
	public final static Integer PM_3011=3011;
	/**
	 * %s实体类中%s字段不是泛型类型
	 */
	public final static Integer PM_3012=3012;
	/**
	 * %s实体类中%s字段,@Temporal注解只能注解在字符串型的字段
	 */
	public final static Integer PM_3013=3013;
	/**
	 * %s实体类中%s字段,@Lob注解只能注解在类型为字符串的字段
	 */
	public final static Integer PM_3014=3014;
	/**
	 * %s实体%s字段中,自动生成主键策略类型只能为数字型
	 */
	public final static Integer PM_3015=3015;
	/**
	 * %s实体%s字段中,UUID生成策略类型只能为字符串型
	 */
	public final static Integer PM_3016=3016;
	/**
	 * %s实体%s字段中,Oracle暂时不支持主键自动增长
	 */
	public final static Integer PM_3017=3017;
	/**
	 * %s实体%s成员，是一对一关系被映射方，无需定义@JoinColumn注解!
	 */
	public final static Integer PM_3018=3018;
	//Controller
	/**
	 * %s控制器类找不到%s成员方法!
	 */
	public final static Integer PM_4001=4001;
	/**
	 * %s控制类%s方法返回的类型不是IActionResult接口!
	 */
	public final static Integer PM_4002=4002;
	/**
	 * %s控制类中不存在%s执行方法
	 */
	public final static Integer PM_4003=4003;
	/**
	 * 返回值已设置无须重复设置
	 */
	public final static Integer PM_4004=4004;
	/**
	 * 当前Action类实例不是ActionSupport的子类，数据注入失败！
	 */
	public final static Integer PM_4005=4005;
	//Persistence
	/**
	 * %s无法执行持久化操作，未对该实体做数据表映射
	 */
	public final static Integer PM_5000=5000;
	/**
	 * %s实体的主键值为空！
	 */
	public final static Integer PM_5001=5001;
	/**
	 * %s表，%s字段,当前插入的一对多对象的主键值为空值!
	 */
	public final static Integer PM_5002=5002;
	/**
	 * 找不到该%s实体类！
	 */
	public final static Integer PM_5003=5003;
	/**
	 * %s实体,%s方法,%s类型,注入%s值时出错！
	 */
	public final static Integer PM_5004=5004;
	/**
	 * %s实体,%s方法,无法将%s值注入到%s类型中！
	 */
	public final static Integer PM_5005=5005;
	/**
	 * 没有该%s数据库实例的实现!
	 */
	public final static Integer PM_5006=5006;
	/**
	 * 数据表生成完毕，用时：%s 毫秒！
	 */
	public final static Integer PM_5007=5007;
	/**
	 * %s表，%s字段,当前插入的一对多对象的主键值为空值!
	 */
	public final static Integer PM_5008=5008;
	/**
	 * %s语句执行失败，错误信息：%s
	 */
	public final static Integer PM_5009=5009;
	/**
	 * %s为多表查询语句，请设置返回对象:addPrototype(Class<?> prototype)
	 */
	public final static Integer PM_5010=5010;
	/**
	 * 实体%s中找不到%s该字段！
	 */
	public final static Integer PM_5011=5011;
	/**
	 * 数据库不支持%s类型的数据类型！
	 */
	public final static Integer PM_5012=5012;
	/**
	 * 实体%s类中的字段%s值长度不能大于数据库定义的长度:%s
	 */
	public final static Integer PM_5013=5013;
	/**
	 * 实体%s类中的字段%s值不能为NULL
	 */
	public final static Integer PM_5014=5014;
	/**
	 * 容器析构出现异常，信息：%s
	 */
	public final static Integer PM_5015=5015;
	/**
	 * 开启事务失败！
	 */
	public final static Integer PM_5016=5016;
	/**
	 * 事务提交时发生异常，无法完成提交！
	 */
	public final static Integer PM_5017=5017;
	/**
	 * 事务调用发现异常,无法完成回滚！
	 */
	public final static Integer PM_5018=5018;
	
}
