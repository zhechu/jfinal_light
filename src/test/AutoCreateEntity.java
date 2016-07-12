package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.ugiant.common.utils.FileUtils;

/**
 * 此工具类，可以根据数据库表自动生成实体类
 * @author lingyuwang
 *
 */
public class AutoCreateEntity {
	
    /**
     * 数据库名
     */
    private static final String DATABASE = "light";
    
    /**
     * 数据连接地址
     */
    private static final String  URL = "jdbc:mysql://127.0.0.1/"+DATABASE+"?useUnicode=true&characterEncoding=UTF-8";
     
    /**
     * 数据库账号
     */
    private static final String  USERNAME = "root"; 
     
    /**
     * 数据库密码
     */
    private static final String  PASSWORD = "root"; 
     
    /**
     * 数据库驱动
     */
    private static final String DRIVER = "com.mysql.jdbc.Driver";
     
    /**
     * 数据库类型（如mysql，oracle）
     */
    private static final String DATABASE_TYPE = "mysql";

	protected static DruidPlugin dp;
    protected static ActiveRecordPlugin activeRecord;
    
    /**
     * 数据库表名
     */
    private static String[] TABLE_NAMES;

    static {
    	// 数据库表名
    	TABLE_NAMES = new String[]{
    		"sys_area",
    		"sys_dict",
    		"sys_menu",
    		"sys_office",
    		"sys_role",
    		"sys_role_menu",
    		"sys_role_office",
    		"sys_user",
    		"sys_user_role"
    	};
    	
    	/**注册jfinal数据库插件*/
    	dp = new DruidPlugin(URL, USERNAME, PASSWORD, DRIVER);
        
        dp.addFilter(new StatFilter());
         
        dp.setInitialSize(3);
        dp.setMinIdle(2);
        dp.setMaxActive(5);
        dp.setMaxWait(60000);
        dp.setTimeBetweenEvictionRunsMillis(120000);
        dp.setMinEvictableIdleTimeMillis(120000);
         
        WallFilter wall = new WallFilter();
        wall.setDbType(DATABASE_TYPE);
        dp.addFilter(wall);
         
        dp.getDataSource();
        dp.start();
         
        activeRecord = new ActiveRecordPlugin(dp);
        activeRecord.setDialect(new MysqlDialect());
        activeRecord.start();
    }

    /**
     * 根据数据库表，自动生成实体文件
     *
     * @param path 文件存放路径
     * @param packageName 包名
     * @param withField 是否生成属性字段
     */
    public static void process(String path, String packageName, boolean withField) {
    	BufferedWriter bw = null;
    	try {
    		List<String> objs = Arrays.asList(TABLE_NAMES);
           /* List<Object> objs = Db.query("select table_name from information_schema.tables where table_schema=? and table_name in ("+StrKit.join(TABLE_NAMES, ",")+")",
            		DATABASE);//查询所有表名*/
            for (int i = 0; i < objs.size(); i++) {
                String tableName = (String) objs.get(i);
                String className = StrKit.toCamelCase(tableName);
                // 去掉 sys_ 前缀，加上 Base
                className = "Base" + className.substring(3);
                
                StringBuffer result = new StringBuffer();
                result.append("package ").append(packageName).append(";\n\n");
                // public abstract class BaseUser<M extends BaseUser<M>> extends BaseModel<M> implements IBean
                result.append("\nimport com.jfinal.plugin.activerecord.IBean;");
                result.append("\nimport com.ugiant.common.model.BaseModel;\n");
                result.append("\n@SuppressWarnings(\"serial\")");
                result.append("\npublic abstract class ").append(className).append("<M extends ").append(className).append("<M>>").append(" extends BaseModel<M> implements IBean {\n\n");
                if (withField) {
                	result.append("    public final static String TABLE_NAME = \"").append(tableName).append("\";\n");
                    List<Object> records = Db.query("select column_name, data_type from information_schema.columns where table_schema=? and table_name=?",
                    		DATABASE, tableName);//查询表中所有列名
                    for (int j = 0; j < records.size(); j++) {
                        String fieldName = (String) ((Object[]) records.get(j))[0];//数组的第1个元素为列名
                        // 字段名称下划线转驼峰
                        String CamelCaseFieldName = StrKit.toCamelCase(fieldName);
                        String fieldType = (String) ((Object[]) records.get(j))[1];//数组的第2个元素为列类型
                        fieldType = convert(fieldType);
                        //result.append("    public static ").append(fieldType).append(" ").append(StrKit.toCamelCase(fieldName)).append(";\n");
                        // setter
                        result.append("\n    public void set").append(StrKit.firstCharToUpperCase(CamelCaseFieldName))
                        	.append("(String ").append(CamelCaseFieldName).append(") {\n        set(\"").append(fieldName)
                        	.append("\", ").append(CamelCaseFieldName).append(");\n    }\n");
                        // getter
                        result.append("\n    public ").append(fieldType).append(" get").append(StrKit.firstCharToUpperCase(CamelCaseFieldName))
                        .append("(String ").append(CamelCaseFieldName).append(") {\n        return get(\"").append(fieldName).append("\");\n    }\n");
                    }
                }
                result.append("\n}");

                //写文件
                File f = new File(path, className + ".java");
                FileUtils.safeMkdir(f);
                if (!f.exists()) {
                    f.createNewFile();
                }
                bw = new BufferedWriter(new FileWriter(f));
                bw.write(result.toString());
                bw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (bw != null) {
            	try {
					bw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
        }
    }

    private static String convert(String fieldType) {
        if (fieldType.equalsIgnoreCase("varchar") || fieldType.equalsIgnoreCase("char")
                || fieldType.equalsIgnoreCase("blob") || fieldType.equalsIgnoreCase("text")) {
            return "String";
        } else if (fieldType.equalsIgnoreCase("int") || fieldType.equalsIgnoreCase("smallint")) {
            return "Integer";
        } else if (fieldType.equalsIgnoreCase("bit")) {
            return "Boolean";
        } else if (fieldType.equalsIgnoreCase("float") || fieldType.equalsIgnoreCase("double")) {
            return "Double";
        } else if (fieldType.equalsIgnoreCase("bigint")) {
            return "Long";
        } else if (fieldType.equalsIgnoreCase("datetime")) {
            return "java.util.Date";
        } else if (fieldType.equalsIgnoreCase("decimal")) {
            return "java.math.BigDecimal";
        } else {
            return "String";
        }
    }

    public static void main(String[] args) {
        AutoCreateEntity.process("d:/baseModel", "com.ugiant.modules.sys.baseModel", true);
        activeRecord.stop();
        dp.stop();
    }
}
