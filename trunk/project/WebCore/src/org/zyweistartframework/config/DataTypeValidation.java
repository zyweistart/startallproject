package org.zyweistartframework.config;


/**
 * 数据类型验证
 */
public interface DataTypeValidation {
	//1
	final String isByte="byte,Byte";
	//1
	final String isBoolean="boolean,Boolean";
	//1
	final String isEnum="Enum,enum";
	//2
	final String isChar="char,Character";
	//2
	final String isShort="short,Short";
	//4
	final String isInteger="int,Integer";
	//4
	final String isFloat="float,Float";
	//8
	final String isLong="long,Long";
	//8
	final String isDouble="double,Double";
	
	final String isBigInteger="BigInteger";
	
	final String isBigDecimal="BigDecimal";

	final String isString="String,StringBuilder,StringBuffer";
	//日期时间
	final String isDate="Date";
	//数字
	final String isNumber=isShort+","+isInteger+","+isLong+","+isBigInteger+","+isBigDecimal+","+isFloat+","+isDouble;
	//字符
	final String isCharAndString=isChar+","+isString;
	//基本数据类型Java语言的原生数据类型
	final String isBaseDataType="byte,boolean,char,short,int,float,long,double";
	
}