package org.zyweistartframework.config;


/**
 * 框架配置的常量
 * @author Start
 */
public final class ConfigConstant {
	/**
	 * 获取当前类的路径
	 */
	public final static String RESOURCEPATH = new Object() {
		public String getResourcePath() {
			/**
			 * 当前方法需要放在该类的最后，原因该类都是静态成员加载顺序由上而下FILESEPARATOR需先加载后方可使用
			 */
			return this.getClass().getClassLoader().getResource("").getPath();
		}
	}.getResourcePath();
	/**
	 * 框架的类路径
	 */
	public final static String FRAMEWORKCLASSPATH="org.zyweistartframework";
	/**
	 * 框架的文件路径
	 */
	public final static String FRAMEWORKFILEPATH="org/zyweistartframework";
	/**
	 * 开发模式
	 * <pre>
	 * 可选值：
	 * 	true: 会打印一些帮助信息
	 * 	false:关闭打印信息
	 * </pre>
	 */
	public final static Boolean DEVMODE=Boolean.parseBoolean(GlobalConfig.Constants.get("DEVMODE"));
	/**
	 * 默认编码格式 
	 *	<pre>
	 *	GBK、UTF-8
	 *	</pre>
	 */
	public final static String ENCODING=GlobalConfig.Constants.get("ENCODING");
	/**
	 * 需要自动打扫的类路径
	 * <pre>
	 * 	类的路径示例：org.zyweistartframework则打扫该包下的所有类包概所有子包
	 * </pre>
	 */
	public final static String CLASSSCANPATH=GlobalConfig.Constants.get("CLASSSCANPATH");
	/**
	 * 数据保存主路径
	 */
	public final static String ROOTPATH=GlobalConfig.Constants.get("ROOTPATH");
	/**
	 * 临时文件目录
	 */
	public final static String TMPPATH=ROOTPATH+GlobalConfig.Constants.get("TMPPATH");
	/**
	 * 文件上传大小限制BYTE为单位
	 */
	public final static Long MAXUPLOADSIZE=Long.parseLong(GlobalConfig.Constants.get("MAXUPLOADSIZE"));
	/**
	 * 允许上传的文件类型"*"代表允许所有
	 * <pre>
	 * 可选值：*或其他文件类型
	 * </pre>
	 */
	public final static String[] ALLOWUPLOADTYPES=GlobalConfig.Constants.get("ALLOWUPLOADTYPES").trim().split(",");
	/**
	 * 是否自动生成表
	 */
	public final static Boolean GENERATETABLE=Boolean.parseBoolean(GlobalConfig.Persistents.get("GENERATETABLE"));
	/**
	 * 是否为延迟加载模式
	 */
	public final static Boolean LAZYLOADMODE=Boolean.parseBoolean(GlobalConfig.Persistents.get("LAZYLOADMODE"));
	/**
	 * 添加过滤字符，防止生成表或字段是应该字段为关键字而出错
	 * <pre>
	 * 可选值：true,false
	 * </pre>
	 */
	public final static Boolean ADDFILTER=Boolean.parseBoolean(GlobalConfig.Persistents.get("ADDFILTER"));
	/**
	 * 添加过滤字符，一般在每个数据表字段默认字段中末尾添加下划线
	 */
	public final static String ADDFILTERCHAR=Boolean.parseBoolean(GlobalConfig.Persistents.get("ADDFILTERCHAR"))?"_":"";
	/**
	 * 数据库方向
	 * <pre>
	 * 可选值：OracleDialect,MySQLDialect,MSSQLServerDialect
	 * </pre>
	 */
	public final static String DIALECT=GlobalConfig.Persistents.get("DIALECT");
	
}
