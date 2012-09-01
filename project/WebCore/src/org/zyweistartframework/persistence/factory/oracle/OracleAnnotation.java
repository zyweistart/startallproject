package org.zyweistartframework.persistence.factory.oracle;

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
import org.zyweistartframework.persistence.annotation.dt.TemporalType;



public final class OracleAnnotation extends ContextAnnotation {
	/**
	在Oracle数据库中，每个关系表都由许多列组成。给每一列指派特定的数据类型来定义将在这个列中存储得数据类型。
	 
	1、CHAR
	最多可以以固定长度的格式存储2000个字符或字节。默认指定为以字符形式进行存储，这个数据类型是固定长度的，并且当位数不够时，需要在其右边添加空格来补满。
	 
	例如：
	CREATE TABLE test
	(name char(20))
	 
	2、VARCHAR和VARCHAR2
	最多可以以可变长度来存储4000B，因此不需要空格来作补充。VARCHAR2 比 VARCHAR 更适合使用，由于兼容性的原因，所以仍然在 Oracle 数据库中保留着 VARCHAR 。
	 
	例如：
	CREATE TABLE test
	(name varchar2(20))
	 
	3、NCHAR 
	NLS（national language support ， 国际语言支持）的数据类型仅可以存储由数据库 NLS 字符集定义的 Unicode 字符集。该数据类型最多可以存储2000B。NCHAR 的列在位数不够时需要在右边填充空格。例如：
	CREATE TABLE test
	(name Nchar(20))
	注意：在 Oracle9i 数据库及其更新的版本中，仅使用 Unicode 数据类型
	 
	4、NVARCHAR2
	NLS 的数据类型与 VARCHAR2 数据类型等价。这个数据类型最多可存储4000B 。例如：
	CREATE TABLE test
	(name Nvarchar2(20))
	注意：在 Oracle9i 数据库及其更新的版本中，仅使用 Unicode 数据类型
	 
	5、NUMBER
	用于存储零、正数、定长负数以及浮点数。NUMBER 数据类型可以以 NUMBER(P,S)的
	 
	形式来定义数字的精度和范围。 这里：p 表示精度 （1-38），它表示存储在列中数
	 
	字的总长度是 p 位： s 表示范围，它表示小数点后的位数。该取值范围可以从-84 到 127 。例如：
	CREATE TABLE test
	(name number(5))
	使用精度 5 来定义一个正数（如 12345）。
	CREATE TABLE test
	(name number(5,2))
	使用精度 5 和范围 2 来定义一个数字。符合这种数据类型的数字值如 123.45 和 12.34
	 
	6、LONG
	LONG 类型的列存储可变长度的字符串，最多可以存储 2GB 的数据。LONG 类型的列有很多在 VARCHAR2 类型列中所具有的特征。可以使用 LONG 类型的列来存储 LONG 类型的文本字符串。LONG 数据类型的使用是为了向前兼容的需要。建议使用 LOB 数据类型来代替 LONG 类型。例如：
	CREATE TABLE test
	(name long)
	 
	7、DATE
	用于在数据库中存储日期和时间。存储时间的精度可以达到 1/100s。不提供时区的相关信息。例如：
	CREATE TABLE test
	(name DATE)
	 
	8、TIMESTAMP
	使用年、月、日、小时、分钟、秒域来对日期/时间提供更详细的支持。最多可以使用 9 位数字的精度来存储秒（受底层操作系统支持的限制）。这个数据类型没有时区的相关信息，它可以在 Oracle9i 数据库及其更新的版本中使用。例如：
	CREATE TABLE test
	(timestamp_column TIMESTAMP);
	 
	9、TIMESTAMP WITH TIME ZONE
	包含 TIMESTAMP 数据类型中的所有域，除此之外，还包含两个额外的域：timezone_hour 和 timezone_minute。这个数据类型包含支持时区的相关信息。这个数据类型可以在 Oracle9i 数据库及其更新的版本中使用。例如：
	CREATE TABLE test
	(timestamp_column
	TIMESTAMP WITH
	TIME ZONE);
	 
	10、TIMESTAMP WITH LOCAL TIME ZONE
	除了在数据库中存储的时区采用标准化以外，所包含的其他域与 TIMESTAMP 数据类型中的域相同。当选择列时，将日期/时间标准为会话的时区。这个数据类型可以在 Oracle9i 数据库及其更新的版本中使用。例如
	CREATE TABLE test
	(timestamp_column
	TIMESTAMP WITH
	LOCAL TIME ZONE);
	 
	11、INTERVAL YEAR TO MONTH
	用于存储一个时间段，由月份和年组成。需要 5B 来存储。这个数据类型可以在 Oracle9i 数据库及其更新的版本中使用。例如：
	SELECT INTERVAL
	'01-05'
	YEAR TO MONTH -
	INTERVAL '01-02' YEAR
	TO MONTH FROM dual;
	 
	12、INTERVAL DAY TO SECOND
	用于存储一个时间段，由日和秒组成。需要 11B 来存储。这个数据类型可以在 Oracle9i 数据库及其更新的版本中使用。例如：
	SELECT INTERVAL
	'100 10:20:42.22'
	DAY(3) TO SECOND(2) - 
	INTERVAL
	'101 10:20:42.22'
	DAY(3) TO SECOND(2)
	FROM dual;
	 
	13、RAW
	用于存储 raw 类型的二进制数据。最多可以存储 2000B。建议使用 BLOB 来代替它。例如：
	CREATE TABLE test
	(raw_column
	RAW(2000));
	 
	14、LONG RAW
	用于存储 raw 类型的二进制数据。最多可以存储 2GB 的数据。建议使用BLOB来代替它。例如：
	CREATE TABLE test
	(raw_column LONG RAW);
	 
	15、ROWID
	表中 ROWID 类型的字符串表示。使用这个数据类型来存储由 ROWID 类型伪列的返回值。例如：
	CREATE TABLE test
	(rowid_column ROWID);
	 
	16、UROWID
	在索引组织表中表示逻辑行地址。例如：
	CREATE TABLE test
	(urowid_column UROWID);
	 
	17、CLOB
	用于存储基于字符的大对象。在 Oracle9i 数据库中最多可以存储 4GB 的数据，这比 Oracle10g 数据库中可存储的最大数据还要大，这也是现在数据库规定块大小的一个因素（~4GB* 数据块大小）。例如：
	CREATE TABLE test
	(clob_column CLOB);
	 
	18、NCLOB
	可以使用由数据库国际字符集所定义的字符集来存储仅为 Unicode 类型的基于字符的数据。最多可以存储 4GB 的数据，这比 Oracle10g 数据库中可存储的最大数据还要大，这也是现在数据库规定块大小的一个因素（~4GB* 数据块大小）。例如：
	CREATE TABLE test
	(nclob_column NCLOB);
	 
	19、BLOB
	最多可以存储 4GB 数据的二进制大对象，这比 Oracle10g数据库中可存储的最大数据还要大，这也是现在数据库规定块大小的一个因素（~4GB* 数据库块大小）。例如：
	CREATE TABLE test
	(blob_column BLOB);
	 
	20、BFILE
	存储指向数据库外部文件的定位符。外部文件最大为 4GB。例如：
	CREATE TABLE test
	(bfile_column BFILE);
	 
	21、BINARY_FLOAT
	该数据类型是一个基于 ANSI_IEEE745 标准的浮点数据类型。它定义了一个 32 位的双精度浮点数。这个数据类型需要 5B 的存储空间。例如：
	CREATE TABLE test
	(b_float BINARY_FLOAT);
	 
	22、BINARY_DOUBLE
	该数据类型是一个基于 ANSI_IEEE745 标准的双精度浮点数据类型，它定义了一个32位的双精度浮点数。这个数据类型需要 9B 节的存储空间。例如：
	CREATE TABLE test
	(b_float BINARY_DOUBLE);
	 
	
	关于各种数据类型的注释
	 
	1、关于 CHAR 和 VARCHAR2 数据类型的注释
	由数据库字符集来确定特定的 CHAR、VARCHAR 或者 VARCHAR2 字符类型所占的字节数。多字节字符集中的字符可以存储 1~4B。CHAR 或 VARCHAR2 数据类型的大小由该数据类型可以存储的字节数或字符数来决定（这成为字符的语义）。所有定义的存储大小都是默认以字节为单位的。如果使用多字节字符集（大部分常见的西方字符集都是以单字节为单位的，而值得注意的是，有一个例外就是 UTF 字符集不是以单字节为单位的），则可能需要定义以字符为单位的存储，下面的例子说明了这个问题：
	 
	CREATE TABLE test
	(name VARCHAR2(20 char));
	 
	建议在表的末尾处创建一些值为 NULL 的列。通过使用这种方法，可以用 VARCHAR2 数据类型来存储一些数据，但不能使用 CHAR 数据类型来存储数据，因为 Oracle 数据库是以在一行中连续存储多个 NULL 值的方式来进行存储的。
	 
	Oracle9i 数据库及其更新的版本提供数据压缩功能，该功能仅在包含于只读表空间中的表中使用（也就是说，表中的数据将不会改变）。仅当通过下面的批量装载（bulk-load）操作中的任意一种操作将数据装载进表中时，才会执行数据压缩操作
	 
	：
	使用 create table as select(CTAS)操作来创建表。
	在直接模式下的 insert 操作或者并行的 insert 操作。
	SQL*Loader 装载程序在直接模式下的操作。
	 
	注意：
	如果使用 update 语句对数据进行了修改，则已经压缩过的数据块将不会再次压缩！因此，一张很小的表可以快速地增长成一张很大的表。通过使用 alter table move 命令可以在已经存在的表中压缩数据。下面是创建压缩表的例子和对已经存在的表进行压缩的例子：
	 
	CREATE TABLE my_tab (id NUMBER,current_value VARCHAR2(2000)) COMPRESS;
	ALTER TABLE my_comp_tab MOVE COMPRESS;
	 
	关于 CHAR 和 VARCHAR2 数据类型，常常会有这样一个问题：到底是用哪一个数据类型最好？下面是对这个问题的一些指导性建议：
	通常 VARCHAR2 数据类型比 CHAR 数据类型优先使用。
	如果数据大小是变化的则在数据库中使用 VARCHAR2 数据类型可以节省空间。
	如果 VARCHAR2 数据类型列中的数据需要频繁更新，则 VARCHAR2 数据类型列的扩展可能会导致行连接或者行迁移的发生。当最终需要使用 VARCHAR2 数据类型列总大小的时候，可以考虑使用 CHAR 数据类型来代替 VARCHAR2 数据类型。
	 
	注意：
	通常来说，使用 VARCHAR2(1)数据类型比使用 CHAR(1)数据类型开销要大，但上面所讲的则是例外。
	 
	2、关于 NUMBER 数据类型的注释
	NUMBER 数据类型内部使用科学计数法以可变长度格式来存储数据。使用 1B 存储指数，而另外 20B（这个字节数可以变化）用于存储该数字剩下的部分。这种存储模式使得 NUMBER 数据类型可以表示的精度为 38 位。
	 
	如果想要确定给定的数字所占字节数的大小，可以使用 vsize 函数，如下所示：
	 
	SQL>SELECT VSIZE(100) FROM dual;
	VSIZE(100)
	----------
	 2
	 
	在上面的例子中，该 vsize 函数用于将数字的大小指定为100.使用 2B 存储。1B 存储数字，另 1B存储指数。
	也可以使用 dump 函数来确定任意列的具体大小，如下所示：
	SQL>SELECT id,dump(id) did FROM test;
	 ID DID
	-----------------------------
	 123 Typ=2 Len=3: 194,2,24
	 141 Typ=2 Len=3: 194,2,42
	     123456 Typ=2 Len=4: 195,13,35,57
	 
	NUMBER 数据类型可以有若干种形式来定义。可以根据需要使用或禁用精度和范围的方式来定义 NUMBER 数据类型。如果使用特定的精度来定义 NUMBER 列，则当超过所定义的精度时，Oracle 数据库将会产生错误。例如，NUMBER(6,2) 在存储数字 1234.56 时，不会对该数字的值产生任何影响，而在存储数字 123.456 时将会对该数字进行四舍五入，最终存储的数字将变成 123.46；如果以相同的 NUMBER 数据类型来存储数字 12345.67 时，数据库将产生错误，因为该数字的精度是 7 而不是 6.
	 
	最后需要注意的是，也可以将列定义为没有精度的 NUMBER 数据类型，这表明将把 
	
	NUMBER 数据类型的列作为没有范围属性的整数来看待。
	 
	3、关于 LONG RAW 数据类型的注释
	 
	LONG RAW 数据类型用于存储数据库无法解释的二进制数据。这个数据类型最多能够存储 2GB 的数据并且它的存储容量是可变的。Oracle 数据库不鼓励使用 LONG RAW 数据类型，因为该数据类型已经由 BLOB 数据类型所代替了。Oracle 数据库
	 
	（Oracle9i数据库及其更新的版本）已经提供了通过 alter table 命令将 LONG RAW 数据类型列转换到相应的 CLOB 数据类型的能力。也可以使用 to_lob 函数将 LONG RAW 数据格式转换为 BLOB 数据格式。
	 
	4、关于 LONG 数据类型的注释
	 
	LONG 数据类型用于存储大量的字符文本。LONG 数据类型会受到某些存储的限制，最多可以存储 2GB 的数据。Oracle 数据库不鼓励使用 LONG 数据类型，因为这个数据类型已经由 CLOB 数据类型所代替了。Oracle数据库（Oracle9i 数据库及其更新的版本）已经提供了通过 alter table 命令将 LONG 数据类型列转换到相应的 LOB 数据类型的能力。也可以使用 to_lob 函数将 LONG 数据格式转换成 CLOB 数据格式。
	在下面的 SQL 语句类型中可以引用 LONG 数据类型列：
	* select 列表
	* 在 update 语句的 set 子句中
	* 在 insert 语句的 values 子句中使用 LONG 数据类型将会受到许多的限制；
	* 在每个表中只允许使用一个 LONG 数据类型的列。
	* 不能使用 LONG 数据类型的属性创建对象类型。
	* Oracle 数据库的 where 子句或者完整性约束不能引用 LONG 数据类型，仅有一种例外的情况，就是 LONG 数据类型可以出现在 NULL 和 NOT NULL 约束中，或者可以作为 NULL 或者 NOT NULL 的 where 子句谓语的一部分。
	* 不能索引 LONG 数据类型的列。
	* 不能对包含 LONG 数据类型的列进行分布操作。所有包含 LONG 数据类型列的事务
	 
	必须在同一个数据库中协同工作。
	* 复制不支持 LONG 数据类型。
	* 如果所创建的表有 LONG 和 CLOB 两种数据类型的列，则在同一 SQL 语句中，同时绑定到 LONG 和 CLOB 数据类型列上的数据不能超过 4000B。但是可以将超过 4000B 的数据单独绑定到 LONG 数据类型或者 CLOB 数据类型列上。
	除了前面所讲的约束外，当 LONG 数据类型出现在 SQL 语句中时，还有另一些关于 LONG 数据类型列的约束。首先需要注意的是，下面的操作不支持 LONG 数据类型列：
	 
	* group by
	* order by
	* connect by
	* distinct
	* unique
	* 任何 SQL 的内建函数、表达式或者条件
	* 任何 select 语句（在该语句中，union、intersect 或者 minus 操作将查询或者子查询结合起来）
	也会存在关于 LONG 数据类型的 DDL 约束，如下所示：
	* 在 create cluster 语句的表列中不能包含 LONG 数据类型列。
	* 在 alter table ...move 语句中不能包含 LONG 数据类型列。
	* 在 create table as select 语句的选择列表中不能包含 LONG 数据类型列。
	 
	在PL/SQL 程序单元和触发器中使用 LONG 数据类型列时，同样也存在一些约束，如下所示：
	* 不能使用 LONG 数据类型表示触发器中的变量。
	* 触发器变量 :new 和 :old 的数据类型不能是 LONG 数据类型列。
	* PL/SQL 存储函数不能返回 LONG 数据类型，但是 PL/SQL 程序中的变量或参数可以使用 LONG 数据类型，不过不能从 SQL 语句中调用这种 PL/SQL 程序单元。
	 
	5、关于 DATE 数据类型的注释
	 
	DATE 数据类型是 Oracle 数据库中自带的一种用于存储日期和时间的方法。当 DATE 数据类型存储在数据库中的时候，这个数据类型占据 7B 的内部存储空间。这些字节分别用来存储世纪、年、月、日、小时、分和秒的详细信息。
	默认的显示格式为 dd-mon-yy,它表示日、月以及两位数的年，由破折号（-）将其分离。例如默认格式为：01-FEB-01。如果要重新定义日期格式，可以在数据库参数文件中设置 nls_date_format 变量。如果要改变日期的格式，也可以为特定的会话使
	 
	用 alter session 命令设置 nls_date_format 变量的值。如下所示：
	SQL>SELECT sysdate FROM dual;
	SYSDATE
	---------
	24-MAY-03
	SQL>ALTER SESSION SET nls_date_format='mm/dd/yyyy hh24:mi:ss';
	Session altered.
	SQL>SELECT sysdate FROM dual;
	SYSDATE
	--------------------
	06/25/2009 16:49:09
	 
	在上面的例子中，将日期格式的时间标志改变为 24 小时制格式的时间，并且在年的前面加上了世纪值。
	如果想要在特定系统的所有会话中使用不同的日期格式，可以设置 NLS_LANG 操作系统的环境变量，并且同时将 NLS_DATE_FORMAT 作为操作系统环境变量进行设置。这将使得每一次登录系统时，会发出 alter session 命令。需要注意的是，只有当同时设置了环境变量 NLS_LANG 时，环境变量 NLS_DATE_FORMATE 才会生效。
	 
	注意：
	如果使用 RMAN（Oracle Recovery Manager，Oracle 恢复管理），则显示的左右日期格式都将是默认的日期格式。应该在启动 RMAN 之前设置 NLS_LANG 和 NLS_DATE_FORMAT 环境变量以设定所需要的日期格式。
	 
	在 SQL 语句中为了改变输出的格式，需要使用 Oracle 数据库的内建函数 to_char。如果有基于字符的日期要插入到 DATE 数据类型列中，则需要使用 to_date 函数。关于 to_char 函数转换日期格式的例子如下所示：
	SQL>SELECT to_char(sysdate,'mm/dd/yyyy hh24:mi:ss') the_date FROM dual;
	 THE_DATE
	-----------------
	06/25/2009 17:05:36
	 
	在上面的例子中，将日期格式转化为由四位数表示的年以及 24 小时制、分钟、秒表示的时间格式。
	 
	6、关于 TIMESTAMP 和 INTERVAL 数据类型的注释
	 
	某些新的 TIMESTAMP 数据类型的值依赖于数据库中适当的时区设置。数据库的时区默认为当前操作系统的时区。在数据库创建的时候，可以通过在 create database 命令中使用 set time_zone 参数为数据库设置不同的时区。也可以通过使用 alter database set time_zone 命令改变已经存在的数据库时区。使用 alter session 命令可以在会话级上修改时区设置。可以定义基于UTC(Universal Time Coordinated，协调世界时)小时偏移的时区，或者使用一个指定的时区，如 CST 或 EST。下面是设置数据库时区的例子：
	ALTER DATABASE SET time_zone='CST';
	ALTER DATABASE SET time_zone='-05:00';
	 
	一些转换函数可以用于 TIMESTAMP 和 INTERVAL 数据类型。这些函数包括 to_data、to_char、to_timestamp_tz、to_yminterval 和 to_dsinterval，同时 nls_timestamp_format 参数和 nls_timestamp_tz_format 参数也与 TIMESTAMP 和 INTERVAL 数据类型有一些特定的关联。
	当从 TIMESTAMP 数据类型列中获取数据时，可以使用其他的内建函数。extract 函数可以从 TIMESTAMP 数据类型列中获取特定的信息，如小时或者分钟的信息。如下所示（注意，从 sysdate 函数到 TIMESTAMP 数据类型格式的隐式转换）：
	CREATE TABLE my_tab(test_col TIMESTAMP);
	INSERT INTO my_tab VALUES (sysdate);
	SELECT test_col,EXTRACT(HOUR FROM test_col) FROM my_tab;
	TEST_COL           HOUR
	-------------------------------------
	25-JUNE -09 05.18.50.000000 PM     9
	 */
	public void fieldDefinition(StringBuilder definitionSQL, String dataType, int length){
		if(DataTypeValidation.isBoolean.contains(dataType)){
        	definitionSQL.append("INTEGER");
        }else if(DataTypeValidation.isByte.contains(dataType)){
			definitionSQL.append("BLOB");
        }else if(DataTypeValidation.isChar.contains(dataType)){
        	definitionSQL.append("CHAR(1)");
        }else if(DataTypeValidation.isShort.contains(dataType)){
        	definitionSQL.append("INTEGER");
        }else if (DataTypeValidation.isInteger.contains(dataType)){
        	definitionSQL.append("INTEGER");
        }else if (DataTypeValidation.isBigInteger.contains(dataType)){
        	definitionSQL.append("NUMBER(5,5)");
        }else if (DataTypeValidation.isBigDecimal.contains(dataType)){
        	definitionSQL.append("NUMBER(5,5)");
        }else if (DataTypeValidation.isLong.contains(dataType)){
        	definitionSQL.append("NUMBER");
        }else if(DataTypeValidation.isFloat.contains(dataType)){
        	definitionSQL.append("NUMBER(5,2)");
        }else if (DataTypeValidation.isDouble.contains(dataType)){
        	definitionSQL.append("NUMBER(5,5)");
        }else if(DataTypeValidation.isEnum.contains(dataType)){
        	definitionSQL.append("VARCHAR2(" + length + ")");
        }else if (DataTypeValidation.isString.contains(dataType)){
            if (length == 0){
                //长度为零则表示为大文本型
            	definitionSQL.append("CLOB");
            }else{
            	definitionSQL.append("VARCHAR2(" + length + ")");
            }
        }else if (DataTypeValidation.isDate.contains(dataType)){
        	if (length == 8){
        		definitionSQL.append("TIMESTAMP");
        	}else if(length==10){
        		definitionSQL.append("DATE");
        	}else if(length==19){
        		definitionSQL.append("DATE");
        	}else if(length==255){
        		definitionSQL.append("DATE");
        	}else{
        		definitionSQL.append("VARCHAR2(32)");
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
        fieldBuilder.append(" NOT NULL,");
	}

	@Override
	public void column(PropertyMember propertyMember,Column column,StringBuilder fieldBuilder,String tableName) {
		fieldBuilder.append(PersistenceConfig.LEFTSEPARATED);
		fieldBuilder.append(propertyMember.getFieldName());
		fieldBuilder.append(PersistenceConfig.RIGHTSEPARATED);
		fieldBuilder.append(" ");
		if(column.columnDefinition().isEmpty()){
			fieldDefinition(fieldBuilder,propertyMember.getReturnTypeSimpleName(),column.length());
        }else{
        	fieldBuilder.append(column.columnDefinition());
        }
        if (!column.nullable()){
        	fieldBuilder.append(" NOT NULL");
        }
        //初始值
        if(!"".equals(column.initVal())){
        	fieldBuilder.append(" DEFAULT ");
        	fieldBuilder.append(PersistenceConfig.LEFTSEPARATED);
        	fieldBuilder.append(column.initVal());
        	fieldBuilder.append(PersistenceConfig.RIGHTSEPARATED);
        }
        fieldBuilder.append(",");
	}

	@Override
	public void temporal(PropertyMember propertyMember,Temporal temporal,StringBuilder fieldBuilder) {
		fieldBuilder.append(PersistenceConfig.LEFTSEPARATED);
		fieldBuilder.append(propertyMember.getFieldName());
		fieldBuilder.append(PersistenceConfig.RIGHTSEPARATED);
		fieldBuilder.append(" ");
		if(temporal.format().isEmpty()){
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
		}else{
			fieldDefinition(fieldBuilder, "Date", 255);
		}
        if (!temporal.nullable()){
        	fieldBuilder.append(" NOT NULL");
        }
        fieldBuilder.append(",");
	}

	@Override
	public void lob(PropertyMember propertyMember,Lob lob,StringBuilder fieldBuilder) {
		fieldBuilder.append(PersistenceConfig.LEFTSEPARATED);
		fieldBuilder.append(propertyMember.getFieldName());
		fieldBuilder.append(PersistenceConfig.RIGHTSEPARATED);
		fieldBuilder.append(" ");
    	fieldDefinition(fieldBuilder, "String",0);
        if (!lob.nullable()){
        	fieldBuilder.append(" NOT NULL");
        }
        fieldBuilder.append(",");
	}

	@Override
	public void defaultDefinition(PropertyMember propertyMember,StringBuilder fieldBuilder) {
		fieldBuilder.append(PersistenceConfig.LEFTSEPARATED);
		fieldBuilder.append(propertyMember.getFieldName());
		fieldBuilder.append(PersistenceConfig.RIGHTSEPARATED);
		fieldBuilder.append(" ");
        fieldDefinition(fieldBuilder,propertyMember.getReturnTypeSimpleName(), 255);
        fieldBuilder.append(",");
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
		StringBuilder query=new StringBuilder();
		query.append("COMMENT ON COLUMN ");
		query.append(PersistenceConfig.LEFTSEPARATED);
		query.append("%s");
		query.append(PersistenceConfig.RIGHTSEPARATED);
		query.append(".");
		query.append(PersistenceConfig.LEFTSEPARATED);
		query.append("%s");
		query.append(PersistenceConfig.RIGHTSEPARATED);
		query.append(" IS '%s'");
		return String.format(query.toString(),tableName,fieldName,comment);
	}
	
}