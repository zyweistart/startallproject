package org.zyweistartframework.config;

/**
 * SQL常量
 * @author Start
 */
public interface Constants {
	
	/**
	 * 文件路径分隔符
	 */
	final String FILESEPARATOR = System.getProperty("file.separator");
	
	final Integer FALSE=0;
	
	final Integer TRUE=1;
	
	final String VALUEOF="valueOf";
	
	final String COLON = ":";
	/**
	 * 双引号
	 */
	final String DQUOT="\"";
	
    final String SIGN_SPLITSTR = "\n";
    /**
	 * 空格
	 */
	final String SPACE=" ";
	/**
	 * 默认执行的Action方法
	 */
	final String EXECUTE="execute";
	
	final String UNKNOWN = "unknown";
	
	final String IS="is";
	
	final String GET="get";
	
	final String SETl="set";
	/**
	 * 数据库字符长度
	 */
	final Integer MAXLENGTH=25;
	
	final String SELECT="SELECT";
	
	final String FROM="FROM";
	
	final String WHERE="WHERE";
	
	final String AND="AND";
	
	final String LIKE="LIKE";
	
	final String NULL="NULL";
	
	final String IN="IN";
	
	final String INSERT="INSERT";
	
	final String INTO="INTO";

	final String VALUES="VALUES";
	
	final String UPDATE="UPDATE";
	
	final String SET="SET";
	
	final String DELETE="DELETE";
	/**
	 * 等于
	 */
	final String EQ="=";
	/**
	 * SQL占位符？
	 */
	final String KEY="?";
	/**
	 * 逗号
	 */
	final String COMMA=",";
	/**
	 * 左括号
	 */
	final String LEFTBRACKETS="(";
	/**
	 * 右括号
	 */
	final String RIGHTSBRACKETS=")";
	/**
	 * 单引号
	 */
	final String QUOT="'";
	
	final String AS="AS";
	/**
	 * Oracle TO_CHAR函数
	 */
	final String TO_CHAR="TO_CHAR";
	/**
	 * Oracle TO_DATA函数
	 */
	final String TO_DATE="TO_DATE";
	
	final String TIME_FORMAT="TIME_FORMAT";
	
	final String DATE_FORMAT="DATE_FORMAT";
	
	final String TABLENAME="TableName";
	
	final String FIELDNAME="fieldName";
	
	final String JOINTABLENAME="joinTableName";
	
	final String FOREIGNKEYSFIELDNAME="foreignKeysFieldName";
	/**
	 * 文件上传提交方式
	 */
	final String MULTIPARTFORMDATA="multipart/form-data";
	/**
	 * 文本
	 */
	final String PARAMETER_TEXT="text";
	/**
	 * 复选框
	 */
	final String PARAMETER_CHECKBOX="checkbox";
	/**
	 * 文件
	 */
	final String PARAMETER_FILE="file";
	
}
