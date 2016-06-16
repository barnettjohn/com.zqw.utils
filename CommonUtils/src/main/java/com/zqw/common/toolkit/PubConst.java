package com.zqw.common.toolkit;

import java.util.HashMap;
import java.util.HashSet;

/**
 * <p>Title: 公共常量</p>
 * <p>Description:公共常量</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 用友政务</p>
 * @author chenhh
 * @version 6.1
 */
public class PubConst {
	//系统数据类型:0整数，1字符，2小数，3日期，4=金额，5=字符日期，6=比率   ，查询方案专用:7=ID
    public final static int DATATYPE_INTEGER=0;
    public final static int DATATYPE_CHAR=1;
    public final static int DATATYPE_DECIMAL=2;
    public final static int DATATYPE_DATE=3;
    public final static int DATATYPE_MONEY=4;
    public final static int DATATYPE_CHAR_DATE=5;
    public final static int DATATYPE_RATE=6;
    public final static int DATATYPE_ID=7;
    
    public final static String DATATYPE_STR_INTEGER="0";
    public final static String DATATYPE_STR_CHAR="1";
    public final static String DATATYPE_STR_DECIMAL="2";
    public final static String DATATYPE_STR_DATE="3";
    public final static String DATATYPE_STR_MONEY="4";
    public final static String DATATYPE_STR_CHAR_DATE="5";
    public final static String DATATYPE_STR_RATE="6";
    public final static String DATATYPE_STR_ID="7";
    
    //日期类型的子维度:1=年 、2=半年、3=季度、4=月度、5=日
    public final static String DATE_YEAR="1";
    public final static String DATE_HALF_YEAR="2";
    public final static String DATE_SEASON="3";
    public final static String DATE_MONTH="4";

    public final static String DATE_DAY="5";

    public final static int iDATE_YEAR=1;
    public final static int iDATE_HALF_YEAR=2;
    public final static int iDATE_SEASON=3;
    public final static int iDATE_MONTH=4;

    public final static int iDATE_DAY=5;

    //比较符
    //=等于，>大于，>=大于等于，<小于，<=小于等于，<>不等于，*=*模糊相等，=*后部模糊相等，*=前部模糊相等，between起、止范围内(含)
    public final static int COMP_EQUAL=0;               //等于
    public final static int COMP_GREAT=1;               //大于
    public final static int COMP_GREAT_EQUAL=2;          //对于等于
    public final static int COMP_LESS=3;               //小于
    public final static int COMP_LESS_EQUAL=4;          //小于等于
    public final static int COMP_NOT_EQUAL=5;          //不等于
    public final static int COMP_ANY_EQUAL=6;          //模糊相等
    public final static int COMP_FRONT_EQUAL=7;          //后模糊
    public final static int COMP_BEHIND_EQUAL=8;          //前模糊
    public final static int COMP_BETWEEN=9;             //范围
    
    //http传输
    public static final String CONTENT_TYPE = "text/html; charset=utf-8";
    public static final int MAX_FILE_SIZE = 20480000;      //最大上传文件20M
    
    //Excel文件过渡表后缀
    public static final String EXCEL_TEMP_POSTFIX = "__T";
    
    //Excel报送调用存储过程名称
    public static final String EXCEL_SP_NAME = "SP_UE_UPLOAD";

    //Excel上报数据审核方式，0=默认(自动+人工)，1=自动，2=人工
    public static final String EXCEL_DIST_TYPE_DEFAULT = "0";
    public static final String EXCEL_DIST_TYPE_AUTO = "1";
    public static final String EXCEL_DIST_TYPE_MANUAL = "2";

    //Excel上报状态，0=未报，1=有错退回，2=已报待审，3＝已报已审
    public static final String EXCEL_RPT_NOT = "0";
    public static final String EXCEL_RPT_ERROR = "1";
    public static final String EXCEL_RPT_REPORT = "2";
    public static final String EXCEL_RPT_AUDIT = "3";
    
    //英文字符集
    public static final char[] _CHAR=new char[]{'A','B','C','D','E','F','G','H','I','J'
            							 ,'K','L','M','N','O','P','Q','R','S','T'
            							 ,'U','V','W','X','Y','Z','a','b','c','d'
            							 ,'e','f','g','h','i','j','k','l','m','n'
            							 ,'o','p','q','r','s','t','u','v','w','x'
            							 ,'y','z'};    

    //查询方案类型，类型QUERY_PLAN_TYPE，1=查询方案，2=多维分析，3=查询方案组合
    public static final String QUERY_PLAN_TYPE = "1";
    public static final String QUERY_PLAN_TYPE_CUBE = "2";
    public static final String QUERY_PLAN_TYPE_GROUP = "3";

    //加工信息域，类型QUERY_TYPE，1=普通查询，2=加工信息域再加工，3=查询叠加，4=存储过程，5=手工SQL
    public static final String QUERY_TYPE_JOIN = "1";
    public static final String QUERY_TYPE_JOIN_JOIN = "2";
    public static final String QUERY_TYPE_UNION = "3";
    public static final String QUERY_TYPE_SP = "4";
    public static final String QUERY_TYPE_SQL = "5";
    public static final String QUERY_TYPE_JAVA = "6";

    //加工信息域，筛选条件类型FILTER_TYPE，0=普通设置，1=手写SQL过滤条件
    public static final String QUERY_FILTER_TYPE_SET = "0";
    public static final String QUERY_FILTER_TYPE_SQL = "1";
    
    //加工信息域，表关联类型LINK_TYPE：0=左关联，1=内关联,2=全关联，3=右关联,4=全关联(逐次合并)
    public static final String QUERY_LINK_TYPE_LEFT_JOIN = "0";
    public static final String QUERY_LINK_TYPE_INNER_JOIN = "1";
    public static final String QUERY_LINK_TYPE_FULL_JOIN = "2";
    public static final String QUERY_LINK_TYPE_RIGHT_JOIN = "3";
    public static final String QUERY_LINK_TYPE_STEP_JOIN = "4";

    //加工信息域，数据来源SRC_TYPE：0=物理表,1=查询
    public static final String QUERY_SRC_TYPE_TABLE = "0";
    public static final String QUERY_SRC_TYPE_QUERY = "1";

    //加工信息域，筛选值类型VALUE_TYPE：0=固定值，1=固定值(引用代码)，2=全局参数，3=系统常量
    public static final String QUERY_VALUE_TYPE_FIX = "0";
    public static final String QUERY_VALUE_TYPE_FIX_REF = "1";
    public static final String QUERY_VALUE_TYPE_VAR = "2";
    public static final String QUERY_VALUE_TYPE_SESSION = "3";
    
    //SQL表达式：类型EXP_TYPE：00=表达式(显示)，01=表达式(编译)
    public static final String SQL_EXP_TYPE_DISP = "00";
    public static final String SQL_EXP_TYPE_COMPILE = "01";   
    
    //SQL表达式类型：0=自写SQL的加工信息域、1=自写SQL筛选条件的加工信息域、2=查询计算字段、3=自写SQL的纳税评估指标、4=纳税指标计算字段
    //5=指标的提示信息模板、6=风险筛子的风险提示模板、7=查询方案的计算字段、8=平均值指标计算表达式、9=查询方案的示警条件
    public static final int SQL_EXP_TYPE_QUERY_SQL = 0;
    public static final int SQL_EXP_TYPE_QUERY_FILTER = 1;
    public static final int SQL_EXP_TYPE_QUERY_FIELD = 2;
    public static final int SQL_EXP_TYPE_EVAL_IDX_SQL = 3;
    public static final int SQL_EXP_TYPE_EVAL_IDX_FIELD = 4;
    public static final int SQL_EXP_TYPE_EVAL_IDX_PROMPT = 5;
    public static final int SQL_EXP_TYPE_EVAL_PLAN_PROMPT = 6;
    public static final int SQL_EXP_TYPE_QUERY_PLAN_FIELD = 7;
    public static final int SQL_EXP_TYPE_EVAL_IDX_AVG = 8;
    public static final int SQL_EXP_TYPE_QUERY_PLAN_ALARM = 9;
    
    //SQL：数据库类型:DB_TYPE:ORACLE
    public static final String SQL_DB_TYPE_ORACLE = "ORACLE";
    public static final String SQL_DB_TYPE_SQLSERVER = "SQLSERVER";

    //数据库连接类型：DB_USER_TYPE:0=默认数据库，1=系统内其他数据库用户，2=外部数据库
    public static final String DB_USER_TYPE_DEFAULT = "0";
    public static final String DB_USER_TYPE_OTHER_USER = "1";
    public static final String DB_USER_TYPE_DBLINK = "2";
    
    //表：表类型TABLE_TYPE:0=基础代码表(树型)、1=基础代码表(非树型)、2=业务数据表、3=单位表
    public static final String TABLE_TYPE_BASE_TREE = "0";
    public static final String TABLE_TYPE_BASE_NOT_TREE = "1";
    public static final String TABLE_TYPE_BUSINESS = "2";
    public static final String TABLE_TYPE_DW = "3";
    
    //字段类型FIELD_TYPE：0=基础数据代码,1=基础数据名称,2=版本,3=自动序号,4=基础数据上级代码,5=单位代码,7=作废标志字段，8=是否末级标志，
    //11=年度，13=账套ID
    //20=期间，21=区划代码，22=基础数据ID，23=基础数据上级ID，24=基础数据级次码(4-4-4)，
    //30=发生数，31=余额
    public static final String FIELD_TYPE_CODE = "0";
    public static final String FIELD_TYPE_NAME = "1";
    public static final String FIELD_TYPE_VER = "2";
    public static final String FIELD_TYPE_SEQ_NO = "3";
    public static final String FIELD_TYPE_P_CODE = "4";
    public static final String FIELD_TYPE_DW = "5";
    public static final String FIELD_TYPE_DEL_FLAG = "7";
    public static final String FIELD_TYPE_LOWEST = "8";
    public static final String FIELD_TYPE_ND = "11";
    public static final String FIELD_TYPE_ACCOUNT = "13";
    public static final String FIELD_TYPE_PERD = "20";
    public static final String FIELD_TYPE_REGION = "21";
    public static final String FIELD_TYPE_ID = "22";
    public static final String FIELD_TYPE_P_ID = "23";
    public static final String FIELD_TYPE_LEVEL_CODE = "24";
    public static final String FIELD_TYPE_VALUE = "30";
    public static final String FIELD_TYPE_BALANCE = "31";
    
    //叠加类型UNION_TYPE：0=全叠加，1＝去除重叠记录(并集)，2＝取重叠记录(交集)，3＝减集
    public static final String UNION_TYPE_UNION_ALL = "0";
    public static final String UNION_TYPE_UNION = "1";
    public static final String UNION_TYPE_SECT = "2";
    public static final String UNION_TYPE_MINUS = "3";
    
    //期间类型：PERD_TYPE：Y=年、H=半年、S=季、M=月、D=日
    public static final String PERD_TYPE_YEAR = "Y";
    public static final String PERD_TYPE_HALF_YEAR = "H";
    public static final String PERD_TYPE_SEASON = "S";
    public static final String PERD_TYPE_MONTH = "M";
    public static final String PERD_TYPE_DAY = "D";

    //与or并：0=and  1=or
    public static final String LOGIC_AND = "0";
    public static final String LOGIC_OR = "1";
    
    //参数类型：PARM_TYPE：0=固定值，1=固定引用值，2=全局参数，3=系统常量，4=字段，5=示警变量
    public static final String PARM_TYPE_FIX = "0";
    public static final String PARM_TYPE_FIX_REF = "1";
    public static final String PARM_TYPE_GLOBAL_PARM = "2";
    public static final String PARM_TYPE_SESSION = "3";
    public static final String PARM_TYPE_FIELD = "4";
    public static final String PARM_TYPE_ALARM_VAR = "5";
    
    //表达式特殊标记符号
    public final static char[][] QUERY_SIGN={
        {'[', ']'},     //方括号，[表别名!字段别名]    [字段名称]   [表别名!字段别名.期初数]   [表别名!字段别名.期末数]
        {'{', '}'}      //花括号，{?全局参数}、{#系统常量}、{!权限}
    };

    public final static char QUERY_SIGN_TAB_FIELD_SEPARATOR='!';   //表、字段之间分割符号
    public final static char QUERY_SIGN_PARAM='?';                 //参数引导符号
    public final static char QUERY_SIGN_SESSION_CONST='#';                 //相同常量引导符号
    public final static char QUERY_SIGN_PERD='.';                  //字段、期初数(期末数)之间的分隔符号
    public final static char QUERY_SIGN_VAR='@';                 //变量引导符号

    public final static char QUERY_SIGN_VALUE_SEPARATOR=',';       //参数值的分割符号

    //session变量
    public final static String[] SESSION_ND={		"svNd",          	"业务年度"};
    public final static String[] SESSION_USER_ID={	"svUserID", 		"用户ID"};
    public final static String[] SESSION_USER_NAME={"svUserName", 		"用户名称"};
    public final static String[] SESSION_DW_ID={	"svDwId",     		"用户单位ID"};
    public final static String[] SESSION_ACCOUNT_ID={"svAccountId",		"用户账套ID"};
    public final static String[] SESSION_CO_CODE={	"svCoCode", 		"用户单位代码"};
    public final static String[] SESSION_CO_NAME={	"svCoName", 		"用户单位名称"};
    public final static String[] SESSION_P_CO_CODE={"svPCoCode",     	"用户上级单位代码"};
    public final static String[] SESSION_ALL_P_DW_ID={"svAllPDwIds",    "用户所有上级单位ID"};
    
	//返回部分数据的排序字段
    public final static String _ROWNUM="ROWNUM___X";
    
    //元信息：单位ID、单位Code
    public final static String _DW_ID_INFOUNIT="DW_ID";
    public final static String _DW_CODE_INFOUNIT="CO_CODE";
    
    //固定信息域ID：
    public final static String TABLE_DW="DW";                          //单位基础资料
    public final static String TABLE_SW_DJ_NSRXX="SW_DJ_NSRXX";        //纳税人登记信息主表
    public final static String TABLE_SW_DJ_NSRXX_ZB="SW_DJ_NSRXX_ZB";  //纳税人登记信息附表
    public final static String TABLE_SW_DJ_SZRD="SW_DJ_SZRD";          //登记税种
    public final static String TABLE_USER="USER";                      //用户基础资料

    public final static String TABLE_SW_DM_ZCLX="SW_DM_ZCLX";          //注册类型
    public final static String TABLE_DJBLX="DJBLX";                    //登记表类型
    public final static String TABLE_HY="HY";                          //行业
    public final static String TABLE_SW_DM_NSRZT="SW_DM_NSRZT";        //纳税人状态
    
    public final static String TABLE_SW_DM_SZ="SW_DM_SZ";              //税种
    public final static String TABLE_SW_DM_SM="SW_DM_SM";              //税目

    public final static String TABLE_SW_SK_SBZSXX="SW_SK_SBZSXX";      //税款征收信息表
    public final static String TABLE_SW_SK_SBZSXX_SUM_YEAR_NSR="SW_SK_SBZSXX_SUM_YEAR_NSR";      //税款征收信息统计表(纳税人年)
    public final static String TABLE_SW_SK_SBZSXX_SUM_MONTH_NSR="SW_SK_SBZSXX_SUM_MONTH_NSR";      //税款征收信息统计表(纳税人月)

    //SQL保留关键字:用于判断多选全局参数是否前置
	public static HashSet _HM_SQL_KEY=new HashSet();
	static {
		_HM_SQL_KEY.add("when");
		_HM_SQL_KEY.add("and");
		_HM_SQL_KEY.add("or");
		_HM_SQL_KEY.add("on");
		_HM_SQL_KEY.add("where");
		_HM_SQL_KEY.add("select");
		_HM_SQL_KEY.add(",");
		_HM_SQL_KEY.add("(");
	}

	//加工信息域：FROM关键字
	public final static String _FROM_KEY="--/*FROM/";
	
	public final static String _CUBE_MASK="CUBE__MASK";   //统计区、立方体数据Cds中：掩码字段名称
	public final static String _ALARM_FIELD="SYS__ALARM"; //数据Cds中的示警字段名称
	
	//用于查询控制的参数编码：
	//清册总计行
	public final static String _QUERY_CTL_GET_LIST_SUM_DATA="GET_LIST_SUM_DATA";     //1=返回清册总计行数据Cds
	//清册区
	public final static String _QUERY_CTL_GET_LIST_DATA    ="GET_LIST_DATA";         //1=返回清册数据Cds
	public final static String _QUERY_CTL_S_ASK_TOTAL_ROW  ="S_ASK_TOTAL_ROW";       //1=返回总记录数
	public final static String _QUERY_CTL_S_START_ROW      ="S_START_ROW";           //清册起始行号,>0，则表示清册分页显示，仅返回当前页的记录，<=0则返回清册全部数据
	public final static String _QUERY_CTL_S_END_ROW        ="S_END_ROW";             //清册截止行号
	//统计区
	public final static String _QUERY_CTL_GET_SUM_DATA     ="GET_SUM_DATA";          //1=返回统计区数据Cds
	//重名重号
	public final static String _QUERY_CTL_DUP_NAME     ="DUP_NAME";                 //重名重号参数:字段ID,字段ID

	//返回查询数据的参数编码
	public final static String _QUERY_DATA_LIST      ="LIST";          //清册数据
	public final static String _QUERY_DATA_SUM       ="SUM";           //统计数据
	public final static String _QUERY_DATA_LIST_SUM  ="LIST_SUM";      //清册总计数据
	
	public final static String _QUERY_DATA_LIST_PAGE_SUM  ="LIST_PAGE_SUM"; //清册分页小计数据

	public final static String _QUERY_DATA_IS_SP     ="IS_SP";          //是否存储过程
	
	//用于指标计算的全局参数代码
	public final static String[] UQ_PARM_S_SSRQQ = {"S_SSRQQ","所属期起"};
	public final static String[] UQ_PARM_S_SSRQZ = {"S_SSRQZ","所属期止"};

	//系统选项
	public final static String OPT_IS_RUN = "IS_RUN";                     //1=启用全局缓存
	public final static String OPT_INFOUNIT_VER = "INFOUNIT_VER";         //1=元信息、全局参数、权限定义版本号
	public final static String OPT_TABLE_VER = "TABLE_VER";               //1=表字段版本号
	public final static String OPT_OTHER_VER = "OTHER_VER";               //1=单位、枚举版本号

	//统计函数:sum=求和,max=最大值,min=最小值,count=计数,avg=平均数,median=中位数,formula=按原公式计算
	public final static String FUNC_TYPE_SUM = "sum";
	public final static String FUNC_TYPE_MAX = "max";
	public final static String FUNC_TYPE_MIN = "min";
	public final static String FUNC_TYPE_COUNT = "count";
	public final static String FUNC_TYPE_AVG = "avg";
	public final static String FUNC_TYPE_MEDIAN = "median";
	public final static String FUNC_TYPE_FORMULA = "formula";
	
	public final static String FUNC_TYPE_AVG_FROM_SUM_DATA = "avg(fromSumCount)";

	//风险筛子中的特殊字段
	public final static String[] RISK_FILTER_IDX_SCORE={	"IDX_SCORE",        "分值"};
    public final static String[] RISK_FILTER_TOTAL_SCORE={	"TOTAL_SCORE", 		"总分"};
    public final static String[] RISK_FILTER_DOUBT_COUNT={"DOUBT_COUNT", 		"疑点总数"};
    public final static String[] RISK_FILTER_RANK_SCORE={	"RANK_SCORE",     	"相对排名分(0-100)"};
    
    //分组聚合判断
    public final static int SUM_FIELD=0;          //是统计区使用的
    public final static int SUM_GROUP_FIELD=1;    //是统计区使用的、分组字段
    public final static int SUM_FUNC_FIELD=2;     //是统计区使用的、聚合字段
    
    //自动编号类型：税务文书类型编码、评估编号
    public final static String AUTO_NO_PGBH="PGBH";   //评估编号
    
    // 界面颜色 #829AE3
 	public final static String TOOLBAR_COLOR = "91ACDF"; // #7996DE
 	public final static String HEADER_COLOR = "7996DE";
 	public final static String BUTTON_COLOR = "668ed1";
 	public final static String EDITOR_AREA_COLOR = "F3F6FF"; //   E5EBFD 深  #F3F6FF 淡
 	public final static String DIALOG_BACK_COLOR = "E5EBFD";
 	public final static String SELECTED_COLOR = "FBA94F";
 	public final static String BORDER_COLOR = "7996DE";

 	public final static int BUTTON_WIDTH = 75;  // 按钮宽度
 	public final static int BUTTON_HEIGHT = 23; // 按钮高度
 	public final static int BUTTON_GAP = 5;     // 按钮间隔
 	public final static int MARGIN_GAP = 10;    // 边距 

 	public final static String EDIT_MODEL_CHANGED_EVENT = "EditModelChanged";
 	public final static String REFRESH_DATA_EVENT = "RefreshData";
 	public final static String TOTAL_COUNT_EVENT = "TOTAL_COUNT";
 	public final static String ITEM_VALUE_CHANGED_EVENT = "ItemValueChanged"; // 输入项值发生变化
 	
 	// 
  	public final static String WAIT = "WAIT";    // 未审
  	public final static String AUDIT = "PASSED";    // 已审
  	public final static String SEND_BACK = "SEND_BACK"; // 退回
  	public final static String CALL_BACK = "CALL_BACK"; // 追回
  	public final static String BREAK = "BREAK";     // 作废
  	public final static String APPROVE = "APPROVE"; // 已批复
  	
  	public final static String PAY_SUCCESS = "SUCCESS"; // 支付成功
  	public final static String PAY_FAILED = "FAILED";   // 支付失败
  	public final static String PAY_PROCESS = "PROCESS"; // 正在处理
  	
  	public final static String QUERY_ALL = "[ALL]"; // 查询全部
  	public final static String QUERY_ALL_QUOTE = "'[ALL]'"; // 查询全部
  	public final static String SELECT_NONE = "[NONE]"; // 选择空
  	
    //A++财务基础资料枚举表ID	:系统级、单位级、引用其他枚举表
	public static HashMap _HM_CW_DICT=new HashMap();
	static {
		_HM_CW_DICT.put("__SYS_CW_ORG_CODE" , new String[]{""
                                                     ,"select t.co_code, '' as account_id, t.org_code as code,t.org_name as name,t.parent_org_code as p_code from as_org t where nd=?  order by co_code,org_code"
                                                     ,"DW"});     //内部机构
		_HM_CW_DICT.put("__SYS_CW_BZ" , new String[]{"select CUR_CODE AS CODE,CUR_NAME AS NAME from ma_currency order by CUR_CODE"
				                                     ,""
				                                     ,"DW"});     //币种	
		_HM_CW_DICT.put("__SYS_CW_GNKM", new String[]{"select '' as co_code,'' as account_id, b_acc_code as code, b_acc_name as name, parent_acc_code as p_code from ma_bacc where nd=? order by b_acc_code"
				                                     ,"select co_code,account_id, b_acc_code as code, b_acc_name as name, parent_acc_code as p_code from gl_bacc g where fiscal=? and not exists (select * from ma_bacc m where m.nd=g.fiscal and m.b_acc_code=g.b_acc_code) order by co_code,account_id,b_acc_code"
				                                     ,"DW,__SYS_CW_ZT"});
		_HM_CW_DICT.put("__SYS_CW_JJFL", new String[]{"select '' as co_code,'' as account_id, outlay_code as code, outlay_name as name, par_outlay_code as p_code from gl_ma_outlaytype g where fiscal=? order by outlay_code"
				                                     ,"select co_code,account_id, outlay_code as code, outlay_name as name, par_outlay_code as p_code from gl_outlaytype g where fiscal=? and not exists (select * from gl_ma_outlaytype m where m.fiscal=g.fiscal and m.outlay_code=g.outlay_code) order by co_code, account_id, outlay_code"
				                                     ,"DW,__SYS_CW_ZT"});
		_HM_CW_DICT.put("__SYS_CW_KJKM", new String[]{"select '' as co_code,'' as account_id, coa_code, acc_code as code, acc_name as name, par_acc_code as p_code from ma_coa_acc g where fiscal=? order by coa_code, acc_code"
				                                     ,"select co_code,account_id,coa_code,acc_code as code, acc_name as name, par_acc_code as p_code from gl_coa_acc g where fiscal=? and not exists (select * from ma_coa_acc m where m.fiscal=g.fiscal and m.acc_code=g.acc_code) order by co_code, account_id, acc_code"
				                                     ,"DW,__SYS_CW_ZT,__SYS_CW_KMTX"});
		_HM_CW_DICT.put("__SYS_CW_KMTX", new String[]{"select COA_CODE AS CODE, COA_NAME AS NAME from ma_coa where fiscal=? order by COA_CODE"
				                                     ,""
				                                     ,""});
		_HM_CW_DICT.put("__SYS_CW_SZDW", new String[]{""
				                                      ,""
				                                      ,""});   //收支单位，特殊处理
		_HM_CW_DICT.put("__SYS_CW_XJLL", new String[]{"select '' as co_code,'' as account_id, cashflow_code as code, cashflow_name as name, p_cashflow_code as p_code from gl_ma_cashflow g where fiscal=? order by cashflow_code"
				                                     ,"select co_code, account_id, cashflow_code as code, cashflow_name as name, p_cashflow_code as p_code from gl_cashflow g where fiscal=? and not exists (select * from gl_ma_cashflow m where m.fiscal=g.fiscal and m.cashflow_code=g.cashflow_code) order by co_code, account_id, cashflow_code"
				                                     ,"DW,__SYS_CW_ZT"});   //现金流量
		_HM_CW_DICT.put("__SYS_CW_XM", new String[]{"select '' as co_code,'' as account_id, item_code as code, item_name as name, par_item_code as p_code from gl_ma_item where fiscal=? order by item_code"
				                                   ,"select co_code, account_id, item_code as code, item_name as name, par_item_code as p_code from gl_item g where fiscal=? and not exists (select * from gl_ma_item m where m.fiscal=g.fiscal and m.item_code=g.item_code) order by co_code, account_id, item_code"
				                                   ,"DW,__SYS_CW_ZT"});
		_HM_CW_DICT.put("__SYS_CW_YSLY", new String[]{"select base_code as code, base_name as name,par_code as p_code from gl_ma_common_base g where fiscal=? and type_code='BUDGET_ORIGIN' order by base_code"
				                                     ,""
				                                     ,""});   //预算来源
		_HM_CW_DICT.put("__SYS_CW_ZCLX", new String[]{"select base_code as code, base_name as name,par_code as p_code from gl_ma_common_base g where fiscal=? and type_code='PAYOUT_KIND' order by base_code"
				                                     ,""
				                                     ,""});   //支出类型 
		_HM_CW_DICT.put("__SYS_CW_ZJXZ", new String[]{"select base_code as code, base_name as name,par_code as p_code from gl_ma_common_base g where fiscal=? and type_code='FUND_KIND' order by base_code"
				                                     ,""
				                                     ,""});   //资金性质
		_HM_CW_DICT.put("__SYS_CW_ZT", new String[]{""
				                                   ,"select COA_CODE,CO_CODE,ACCOUNT_ID AS CODE,ACCOUNT_NAME AS NAME from ma_co_acc where nd=? order by CO_CODE,ACCOUNT_ID"
				                                   ,"DW,__SYS_CW_KMTX"});     //账套
		_HM_CW_DICT.put("__SYS_CW_ITEM1", new String[]{"select '' as co_code,'' as account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item_p g where fiscal=? and g.acc_item_code='ACC_ITEM1' order by gl_item_code"
				,"select co_code, account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item g where fiscal=?  and g.acc_item_code='ACC_ITEM1' and not exists (select * from gl_acc_item_p m where m.fiscal=g.fiscal and m.gl_item_code = g.gl_item_code and m.acc_item_code = g.acc_item_code) order by co_code, account_id, gl_item_code"
				,"DW,__SYS_CW_ZT"});
		_HM_CW_DICT.put("__SYS_CW_ITEM2", new String[]{"select '' as co_code,'' as account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item_p g where fiscal=? and g.acc_item_code='ACC_ITEM2' order by gl_item_code"
                ,"select co_code, account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item g where fiscal=?  and g.acc_item_code='ACC_ITEM2' and not exists (select * from gl_acc_item_p m where m.fiscal=g.fiscal and m.gl_item_code = g.gl_item_code and m.acc_item_code = g.acc_item_code) order by co_code, account_id, gl_item_code"
                ,"DW,__SYS_CW_ZT"});
		_HM_CW_DICT.put("__SYS_CW_ITEM3", new String[]{"select '' as co_code,'' as account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item_p g where fiscal=? and g.acc_item_code='ACC_ITEM3' order by gl_item_code"
                ,"select co_code, account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item g where fiscal=?  and g.acc_item_code='ACC_ITEM3' and not exists (select * from gl_acc_item_p m where m.fiscal=g.fiscal and m.gl_item_code = g.gl_item_code and m.acc_item_code = g.acc_item_code) order by co_code, account_id, gl_item_code"
                ,"DW,__SYS_CW_ZT"});
		_HM_CW_DICT.put("__SYS_CW_ITEM4", new String[]{"select '' as co_code,'' as account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item_p g where fiscal=? and g.acc_item_code='ACC_ITEM4' order by gl_item_code"
                ,"select co_code, account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item g where fiscal=?  and g.acc_item_code='ACC_ITEM4' and not exists (select * from gl_acc_item_p m where m.fiscal=g.fiscal and m.gl_item_code = g.gl_item_code and m.acc_item_code = g.acc_item_code) order by co_code, account_id, gl_item_code"
                ,"DW,__SYS_CW_ZT"});
		_HM_CW_DICT.put("__SYS_CW_ITEM5", new String[]{"select '' as co_code,'' as account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item_p g where fiscal=? and g.acc_item_code='ACC_ITEM5' order by gl_item_code"
                ,"select co_code, account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item g where fiscal=?  and g.acc_item_code='ACC_ITEM5' and not exists (select * from gl_acc_item_p m where m.fiscal=g.fiscal and m.gl_item_code = g.gl_item_code and m.acc_item_code = g.acc_item_code) order by co_code, account_id, gl_item_code"
                ,"DW,__SYS_CW_ZT"});
		
		_HM_CW_DICT.put("__SYS_CW_ITEM6", new String[]{"select '' as co_code,'' as account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item_p g where fiscal=? and g.acc_item_code='ACC_ITEM6' order by gl_item_code"
                ,"select co_code, account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item g where fiscal=?  and g.acc_item_code='ACC_ITEM6' and not exists (select * from gl_acc_item_p m where m.fiscal=g.fiscal and m.gl_item_code = g.gl_item_code and m.acc_item_code = g.acc_item_code) order by co_code, account_id, gl_item_code"
                ,"DW,__SYS_CW_ZT"});
		_HM_CW_DICT.put("__SYS_CW_ITEM7", new String[]{"select '' as co_code,'' as account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item_p g where fiscal=? and g.acc_item_code='ACC_ITEM7' order by gl_item_code"
                ,"select co_code, account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item g where fiscal=?  and g.acc_item_code='ACC_ITEM7' and not exists (select * from gl_acc_item_p m where m.fiscal=g.fiscal and m.gl_item_code = g.gl_item_code and m.acc_item_code = g.acc_item_code) order by co_code, account_id, gl_item_code"
                ,"DW,__SYS_CW_ZT"});
		_HM_CW_DICT.put("__SYS_CW_ITEM8", new String[]{"select '' as co_code,'' as account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item_p g where fiscal=? and g.acc_item_code='ACC_ITEM8' order by gl_item_code"
                ,"select co_code, account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item g where fiscal=?  and g.acc_item_code='ACC_ITEM8' and not exists (select * from gl_acc_item_p m where m.fiscal=g.fiscal and m.gl_item_code = g.gl_item_code and m.acc_item_code = g.acc_item_code) order by co_code, account_id, gl_item_code"
                ,"DW,__SYS_CW_ZT"});
		_HM_CW_DICT.put("__SYS_CW_ITEM9", new String[]{"select '' as co_code,'' as account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item_p g where fiscal=? and g.acc_item_code='ACC_ITEM9' order by gl_item_code"
                ,"select co_code, account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item g where fiscal=?  and g.acc_item_code='ACC_ITEM9' and not exists (select * from gl_acc_item_p m where m.fiscal=g.fiscal and m.gl_item_code = g.gl_item_code and m.acc_item_code = g.acc_item_code) order by co_code, account_id, gl_item_code"
                ,"DW,__SYS_CW_ZT"});
		_HM_CW_DICT.put("__SYS_CW_ITEM10", new String[]{"select '' as co_code,'' as account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item_p g where fiscal=? and g.acc_item_code='ACC_ITEM10' order by gl_item_code"
                ,"select co_code, account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item g where fiscal=?  and g.acc_item_code='ACC_ITEM10' and not exists (select * from gl_acc_item_p m where m.fiscal=g.fiscal and m.gl_item_code = g.gl_item_code and m.acc_item_code = g.acc_item_code) order by co_code, account_id, gl_item_code"
                ,"DW,__SYS_CW_ZT"});
		
		_HM_CW_DICT.put("__SYS_CW_ITEM11", new String[]{"select '' as co_code,'' as account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item_p g where fiscal=? and g.acc_item_code='ACC_ITEM11' order by gl_item_code"
                ,"select co_code, account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item g where fiscal=?  and g.acc_item_code='ACC_ITEM11' and not exists (select * from gl_acc_item_p m where m.fiscal=g.fiscal and m.gl_item_code = g.gl_item_code and m.acc_item_code = g.acc_item_code) order by co_code, account_id, gl_item_code"
                ,"DW,__SYS_CW_ZT"});
		_HM_CW_DICT.put("__SYS_CW_ITEM12", new String[]{"select '' as co_code,'' as account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item_p g where fiscal=? and g.acc_item_code='ACC_ITEM12' order by gl_item_code"
                ,"select co_code, account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item g where fiscal=?  and g.acc_item_code='ACC_ITEM12' and not exists (select * from gl_acc_item_p m where m.fiscal=g.fiscal and m.gl_item_code = g.gl_item_code and m.acc_item_code = g.acc_item_code) order by co_code, account_id, gl_item_code"
                ,"DW,__SYS_CW_ZT"});
		_HM_CW_DICT.put("__SYS_CW_ITEM13", new String[]{"select '' as co_code,'' as account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item_p g where fiscal=? and g.acc_item_code='ACC_ITEM13' order by gl_item_code"
                ,"select co_code, account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item g where fiscal=?  and g.acc_item_code='ACC_ITEM13' and not exists (select * from gl_acc_item_p m where m.fiscal=g.fiscal and m.gl_item_code = g.gl_item_code and m.acc_item_code = g.acc_item_code) order by co_code, account_id, gl_item_code"
                ,"DW,__SYS_CW_ZT"});
		_HM_CW_DICT.put("__SYS_CW_ITEM14", new String[]{"select '' as co_code,'' as account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item_p g where fiscal=? and g.acc_item_code='ACC_ITEM14' order by gl_item_code"
                ,"select co_code, account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item g where fiscal=?  and g.acc_item_code='ACC_ITEM14' and not exists (select * from gl_acc_item_p m where m.fiscal=g.fiscal and m.gl_item_code = g.gl_item_code and m.acc_item_code = g.acc_item_code) order by co_code, account_id, gl_item_code"
                ,"DW,__SYS_CW_ZT"});
		_HM_CW_DICT.put("__SYS_CW_ITEM15", new String[]{"select '' as co_code,'' as account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item_p g where fiscal=? and g.acc_item_code='ACC_ITEM15' order by gl_item_code"
                ,"select co_code, account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item g where fiscal=?  and g.acc_item_code='ACC_ITEM15' and not exists (select * from gl_acc_item_p m where m.fiscal=g.fiscal and m.gl_item_code = g.gl_item_code and m.acc_item_code = g.acc_item_code) order by co_code, account_id, gl_item_code"
                ,"DW,__SYS_CW_ZT"});
		_HM_CW_DICT.put("__SYS_CW_ITEM16", new String[]{"select '' as co_code,'' as account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item_p g where fiscal=? and g.acc_item_code='ACC_ITEM16' order by gl_item_code"
                ,"select co_code, account_id, gl_item_code as code, gl_item_name as name, parent_item_code as p_code from gl_acc_item g where fiscal=?  and g.acc_item_code='ACC_ITEM16' and not exists (select * from gl_acc_item_p m where m.fiscal=g.fiscal and m.gl_item_code = g.gl_item_code and m.acc_item_code = g.acc_item_code) order by co_code, account_id, gl_item_code"
                ,"DW,__SYS_CW_ZT"});
	}
}