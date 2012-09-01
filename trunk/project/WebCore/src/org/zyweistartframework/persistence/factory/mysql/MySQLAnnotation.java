package org.zyweistartframework.persistence.factory.mysql;

import java.lang.annotation.Annotation;

import org.zyweistartframework.config.DataTypeValidation;
import org.zyweistartframework.config.Message;
import org.zyweistartframework.context.PropertyMember;
import org.zyweistartframework.exception.GenerateTableError;
import org.zyweistartframework.persistence.ContextAnnotation;
import org.zyweistartframework.persistence.PersistenceConfig;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.Comment;
import org.zyweistartframework.persistence.annotation.Id;
import org.zyweistartframework.persistence.annotation.Lob;
import org.zyweistartframework.persistence.annotation.Temporal;
import org.zyweistartframework.persistence.annotation.dt.GeneratedValue;
import org.zyweistartframework.persistence.annotation.dt.TemporalType;



public final class MySQLAnnotation extends ContextAnnotation {
	
	/**
	 * 类型  大小 范围（有符号） 范围（无符号） 用途
	 * 1.整数
	 * TINYINT 1 字节 (-128，127) (0，255) 小整数值 
	 * SMALLINT 2 字节 (-32 768，32 767) (0，65 535) 大整数值 
	 * MEDIUMINT 3 字节 (-8 388 608，8 388 607) (0，16 777 215) 大整数值
	 * INT或INTEGER 4 字节 (-2 147 483 648，2 147 483 647) (0，4 294 967 295) 大整数值
	 * BIGINT 8 字节 (-9 233 372 036 854 775 808，9 223 372 036 854 775 807) (0，18 446 744 073 709 551 615) 极大整数值
	 * 2.小数
	 * FLOAT 4 字节 (-3.402 823 466 E+38，1.175 494 351 E-38)，0，(1.175 494 351 E-38，3.402 823 466 351 E+38) 0，(1.175 494 351 E-38，3.402 823 466 E+38) 单精度浮点数值
	 * DOUBLE 8 字节 (1.797 693 134 862 315 7 E+308，2.225 073 858 507 201 4 E-308)，0，(2.225 073 858 507 201 4 E-308，1.797 693 134 862 315 7 E+308) 0，(2.225 073 858 507 201 4 E-308，1.797 693 134 862 315 7 E+308) 双精度浮点数值
	 * DECIMAL 对DECIMAL(M,D) ，如果M>D，为M+2否则为D+2 依赖于M和D的值 依赖于M和D的值 小数值 
	 * 3.字符
	 * CHAR 0-255字节 定长字符串 
	 * TINYTEXT 0-255字节 短文本字符串 
	 * VARCHAR 0-255字节 变长字符串 
	 * TEXT 0-65 535字节 长文本数据 
	 * MEDIUMTEXT 0-16 777 215字节 中等长度文本数据 
	 * LONGTEXT 0-4 294 967 295字节 极大文本数据
	 * 4.二进制 
	 * TINYBLOB 0-255字节 不超过 255 个字符的二进制字符串 
	 * BLOB 0-65 535字节 二进制形式的长文本数据 
	 * MEDIUMBLOB 0-16 777 215字节 二进制形式的中等长度文本数据 
	 * LOGNGBLOB 0-4 294 967 295字节 二进制形式的极大文本数据 
	 * 5.日期时间
	 * YEAR 1 1901/2155 YYYY 年份值 
	 * TIME 3 '-838:59:59'/'838:59:59' HH:MM:SS 时间值或持续时间 
	 * DATE 3 1000-01-01/9999-12-31 YYYY-MM-DD 日期值 
	 * DATETIME 8 1000-01-01 00:00:00/9999-12-31 23:59:59 YYYY-MM-DD HH:MM:SS 混合日期和时间值 
	 * TIMESTAMP 8 1970-01-01 00:00:00/2037 年某时 YYYYMMDD HHMMSS 混合日期和时间值，时间戳 
	 * BIT
	 * REAL
	 * NUMERIC
	 * ENUM
	 * SET
	 * BINARY
	 * VARBINARY
	 */
	public void fieldDefinition(StringBuilder definitionSQL, String dataType, int length){
		if(DataTypeValidation.isByte.contains(dataType)){
			definitionSQL.append("TINYBLOB");
        }else if(DataTypeValidation.isBoolean.contains(dataType)){
        	definitionSQL.append("TINYINT");
        }else if(DataTypeValidation.isChar.contains(dataType)){
        	definitionSQL.append("CHAR");
        }else if(DataTypeValidation.isShort.contains(dataType)){
        	definitionSQL.append("SMALLINT");
        }else if (DataTypeValidation.isInteger.contains(dataType)){
        	definitionSQL.append("INT");
        }else if (DataTypeValidation.isBigInteger.contains(dataType)){
        	definitionSQL.append("BIGINT");
        }else if (DataTypeValidation.isBigDecimal.contains(dataType)){
        	definitionSQL.append("BIGINT");
        }else if (DataTypeValidation.isLong.contains(dataType)){
        	definitionSQL.append("BIGINT");
        }else if(DataTypeValidation.isFloat.contains(dataType)){
        	definitionSQL.append("FLOAT");
        }else if (DataTypeValidation.isDouble.contains(dataType)){
        	definitionSQL.append("DOUBLE");
        }else if(DataTypeValidation.isEnum.contains(dataType)){
        	definitionSQL.append("VARCHAR(" + length + ")");
        }else if (DataTypeValidation.isString.contains(dataType)){
            if (length == 0){//长度为零则表示为大文本型
            	definitionSQL.append("LONGTEXT");
            }else{
            	definitionSQL.append("VARCHAR(" + length + ")");
            }
        }else if (DataTypeValidation.isDate.contains(dataType)){
        	if (length == 8){
        		definitionSQL.append("TIME");
        	}else if(length==10){
        		definitionSQL.append("DATE");
        	}else{
        		definitionSQL.append("DATETIME");
        	}
        }else{
        	throw new GenerateTableError(Message.getMessage(Message.PM_5012,dataType));
        }
    }

	@Override
	public void id(PropertyMember propertyMember,Id id, StringBuilder fieldBuilder) {
		fieldBuilder.append(PersistenceConfig.LEFTSEPARATED);
		fieldBuilder.append(propertyMember.getFieldName());
		fieldBuilder.append(PersistenceConfig.RIGHTSEPARATED);
		fieldBuilder.append(" ");
		fieldDefinition(fieldBuilder,propertyMember.getReturnTypeSimpleName(), id.length());
        fieldBuilder.append(" NOT NULL");
        if (id.value() == GeneratedValue.IDENTITY){
            //自动增长
            fieldBuilder.append("  AUTO_INCREMENT");
        }
        fieldBuilder.append(",");
	}

	@Override
	public void column(PropertyMember propertyMember,Column column,StringBuilder fieldBuilder,String tableName) {
		fieldBuilder.append(PersistenceConfig.LEFTSEPARATED);
		fieldBuilder.append(propertyMember.getFieldName());
		fieldBuilder.append(PersistenceConfig.RIGHTSEPARATED);
		fieldBuilder.append(" ");
        if (column.columnDefinition().isEmpty()){
        	fieldDefinition(fieldBuilder,propertyMember.getReturnTypeSimpleName(),column.length());
        }else{
        	fieldBuilder.append(column.columnDefinition());
        }
        if (!column.nullable()){
        	fieldBuilder.append(" NOT");
        }
        fieldBuilder.append(" NULL");
        if (column.unique()){
        	//添加唯一约束
        	fieldBuilder.append(" UNIQUE");
        }
        //初始值
        if(!"".equals(column.initVal())){
        	fieldBuilder.append(" DEFAULT '"+column.initVal()+"'");
        }
        fieldBuilder.append(",");
	}

	@Override
	public void temporal(PropertyMember propertyMember,Temporal temporal,StringBuilder fieldBuilder) {
		fieldBuilder.append(PersistenceConfig.LEFTSEPARATED);
		fieldBuilder.append(propertyMember.getFieldName());
		fieldBuilder.append(PersistenceConfig.RIGHTSEPARATED);
		fieldBuilder.append(" ");
		if (temporal.value() == TemporalType.DATE){
            //默认格式：1989-06-24
            fieldDefinition(fieldBuilder, "Date", 10);
        }else if (temporal.value() == TemporalType.TIME){
            //默认格式: 00:00:00
            fieldDefinition(fieldBuilder, "Date",8);
        }else{
            //默认格式：1989-06-24 00:00:00
            fieldDefinition(fieldBuilder, "Date", 19);
        }
       if (!temporal.nullable()){
       	fieldBuilder.append(" NOT");
       }
       fieldBuilder.append(" NULL,");
	}

	@Override
	public void lob(PropertyMember propertyMember,Lob lob,StringBuilder fieldBuilder) {
		fieldBuilder.append(PersistenceConfig.LEFTSEPARATED);
		fieldBuilder.append(propertyMember.getFieldName());
		fieldBuilder.append(PersistenceConfig.RIGHTSEPARATED);
		fieldBuilder.append(" ");
    	fieldDefinition(fieldBuilder, "String",0);
        if (!lob.nullable()){
        	fieldBuilder.append(" NOT");
        }
        fieldBuilder.append(" NULL,");
	}

	@Override
	public void defaultDefinition(PropertyMember propertyMember,StringBuilder fieldBuilder) {
		fieldBuilder.append(PersistenceConfig.LEFTSEPARATED);
		fieldBuilder.append(propertyMember.getFieldName());
		fieldBuilder.append(PersistenceConfig.RIGHTSEPARATED);
		fieldBuilder.append(" ");
        fieldDefinition(fieldBuilder,propertyMember.getReturnTypeSimpleName(), 255);
        fieldBuilder.append(" NULL,");
	}
	/**
	 * 获取注释
	 */
	public String comment(String tableName,String fieldName,Annotation[] annotations){
		String comment=new String();
		if(annotations!=null){
			for(Annotation annotation:annotations){
				if(annotation.annotationType().equals(Comment.class)){
					comment=((Comment)annotation).value();
				}
			}
		}
		if(comment.isEmpty()){
			return null;
		}
		return String.format("COMMENT ON COLUMN "+PersistenceConfig.LEFTSEPARATED+
				"%s"+PersistenceConfig.RIGHTSEPARATED+"."+PersistenceConfig.LEFTSEPARATED+
				"%s"+PersistenceConfig.RIGHTSEPARATED+" IS '%s'",tableName,fieldName,comment);
	}
}