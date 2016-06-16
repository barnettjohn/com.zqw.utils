package com.zqw.common.toolkit;

import java.awt.Font;
import java.math.BigDecimal;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;

import com.zqw.common.model.DefaultCheckItem;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Title: 公共处理函数
 * </p>
 * <p>
 * Description:字符串等处理函数
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * <p>
 * Company: 用友政务
 * </p>
 * 
 * @author chenhh
 * @version 6.1
 */
public class PubFunc {
	
	//private static IUqDBHelper dbHelper = null;    // 通用数据库访问接口

	// 比较字符串,IgnoreCase
	public static boolean isEmptyString(Object str1) {
		if (null == str1)
			return true;

		if (str1.equals(""))
			return true;

		return false;
	}

	// 比较字符串
	public static boolean isEqualsString(String str1, String str2) {
		if (null != str1 && null != str2)
			return str1.equals(str2);

		if (null == str1 && null == str2)
			return true;

		return false;
	}

	// 比较字符串
	public static boolean isEqualsStringIgnoreNull(String str1, String str2) {
		if (null != str1 && null != str2)
			return str1.equals(str2);

		if (isEmptyString(str1) && isEmptyString(str2))
			return true;

		return false;
	}

	// 比较字符串
	public static boolean isEqualsStringIgnoreNull(Object str1, Object str2) {
		if (nullToEmptyString(str1).equals(nullToEmptyString(str2)))
			return true;

		return false;
	}

	// 比较字符串,IgnoreCase
	public static boolean isEqualsStringIgnoreCase(String str1, String str2) {
		if (null != str1 && null != str2)
			return str1.equalsIgnoreCase(str2);

		if (null == str1 && null == str2)
			return true;

		return false;
	}

	//返回填充固定数量连续的字符，如：000000
	public static String pad(String str1, int count) {
		if (count <= 0)
			return "";

		StringBuffer strRet=new StringBuffer();
		for (int i=0;i<count;i++){
			strRet.append(str1);
		}

		return strRet.toString();
	}

	//返回填充固定数量连续的字符，如：000000
	public static String pad(String[] str1) {
		if (null ==str1 || str1.length <= 0)
			return "";

		StringBuffer strRet=new StringBuffer();
		for (int i=0;i<str1.length;i++){
			strRet.append(str1[i]);
		}

		return strRet.toString();
	}

	public static String[] initArray(String strDef,int count) {
		if (count <= 0)
			return null;

		String[] strRet=new String[count];
		for (int i=0;i<count;i++){
			strRet[i]=strDef;
		}

		return strRet;
	}

	public static HashMap cloneHashMap(HashMap inData) {
		if (null == inData) {
			return null;
		}

		HashMap retData = new HashMap();
		for (Iterator iter = inData.keySet().iterator(); iter
				.hasNext();) {
			String strKey = (String) iter.next();
			Object obj = inData.get(strKey);

			if (obj instanceof String) {
					String strValue = (String)obj;
					retData.put(strKey, strValue);
			} else if (obj instanceof BigDecimal) {
				BigDecimal decValue = new BigDecimal(
						((BigDecimal) obj).toString());
				retData.put(strKey, decValue);
			}  else if (obj instanceof Integer) {
				int iValue = ((Integer) obj).intValue();
				retData.put(strKey, new Integer(iValue));
			}else if (obj instanceof String[]) {
				String[] strValue = PubFunc.cloneString((String[]) obj);
				retData.put(strKey, strValue);
			} else {
				retData.put(strKey, obj);
			}
		}
		return retData;
	}

	public static HashSet cloneHashSet(HashSet inData) {
		if (null == inData) {
			return null;
		}

		HashSet retData = new HashSet();
		for (Iterator iter = inData.iterator(); iter.hasNext();) {
			Object obj = (String) iter.next();
			if (obj instanceof String[]) {
				String[] strValue = PubFunc.cloneString((String[]) obj);
				retData.add(strValue);
			} else {
				retData.add(obj);
			}
		}
		
		return retData;
	}

	public static ArrayList cloneArrayList(ArrayList inData) {
		if (null == inData) {
			return null;
		}
		ArrayList retData = new ArrayList();
		for (int i = 0; i < inData.size(); i++) {
			Object obj = inData.get(i);
			if (obj instanceof String) {
				String strValue = new String((String) obj);
				retData.add(strValue);
			} else if (obj instanceof Double) {
				Double decValue = new Double(((Double) obj).doubleValue());
				retData.add(decValue);
			} else if (obj instanceof Integer) {
				int iValue = ((Integer) obj).intValue();
				retData.add(new Integer(iValue));
			} else if (obj instanceof BigDecimal) {
				BigDecimal decValue = new BigDecimal(
						((BigDecimal) obj).toString());
				retData.add(decValue);
			} else if (obj instanceof String[]) {
				String[] strValue = PubFunc.cloneString((String[]) obj);
				retData.add(strValue);
			} else if (obj instanceof ArrayList) {
				ArrayList strValue = PubFunc.cloneArrayList((ArrayList) obj);
				retData.add(strValue);
			} else {
				retData.add(obj);
			}
		}

		return retData;
	}

	public static String[] cloneString(String[] inData) {
		if (null == inData) {
			return null;
		}

		String[] retData = new String[inData.length];
		for (int i = 0; i < inData.length; i++) {
			String strKey = inData[i];
			retData[i] = strKey;
		}
		return retData;
	}

	public static int[] cloneInt(int[] inData) {
		if (null == inData) {
			return null;
		}
		int[] retData = new int[inData.length];
		for (int i = 0; i < inData.length; i++) {
			int strKey = inData[i];
			retData[i] = strKey;
		}
		return retData;
	}

	// 追加int[]
	public static int[] appendIntArray(int[] inData, int iNewData) {
		if (null == inData) {
			return null;
		}

		int[] retData = new int[inData.length + 1];
		for (int i = 0; i < inData.length; i++) {
			retData[i] = inData[i];
		}

		retData[retData.length-1] = iNewData;

		return retData;
	}

	// 追加String[]
	public static String[] appendStringArray(String[] inData, String strNewData) {
		if (null == inData) {
			return null;
		}

		String[] retData = new String[inData.length + 1];
		for (int i = 0; i < inData.length; i++) {
			retData[i] = inData[i];
		}

		retData[retData.length-1] = strNewData;

		return retData;
	}

	public static String nullToEmpty(String inString) {
		return (inString == null) ? "" : inString;
	}

	public static String nullToEmptyString(Object inString) {
		return (inString == null) ? "" : (String) inString;
	}

	public static String nullToZero(String inString) {
		if (isEmptyString(inString)) {
			return "0";
		}
		return inString;
	}

	public static String nullToZero(Object inString) {
		if (null == inString) {
			return "0";
		} else if (PubFunc.isEmptyString((String) inString)) {
			return "0";
		}
		return (String) inString;
	}

	public static String nullToEmptyBracket(Object inString) {
		if (null == inString) {
			return "  ";
		} else if (PubFunc.isEmptyString((String) inString)) {
			return "  ";
		}
		return (String) inString;
	}
	
	/**
	 * 空或空串转成"~",nc中参照默认值 add by zhangpana 20150525
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 空或空串返回"~",否则返回初始值
	 */
	public static String null2Wave(String str) {
		return StringUtils.isEmpty(str) ? "~" : str;
	}
	
	// StringBuffer中的字符串数值加1，如0000002变为0000003
	public static void increteStringBuffer(StringBuffer sTempStart) {
		int iMin = (new Integer(sTempStart.toString())).intValue();
		iMin++;

		int iLength = sTempStart.length();
		sTempStart.delete(0, iLength);
		sTempStart.append("0000000000000000000000000000000000000000").append(
				iMin);
		sTempStart.delete(0, sTempStart.length() - iLength);
	}

	// 替换单引号为两个单引号
	public static String convertSingleQuote(String strValue) {
		if (isEmptyString(strValue)) {
			return "";
		}

		return strValue.replaceAll("'", "''");
	}

	// 去掉单引号
	public static String disSingleQuote(String strValue) {
		if (isEmptyString(strValue)) {
			return "";
		}

		return strValue.replaceAll("'", "");
	}

	// 转换Iterator到ArrayList
	public static ArrayList Iter2List(Iterator iter) {
		ArrayList a = new ArrayList();
		while (iter.hasNext()) {
			a.add(iter.next());
		}
		return a;
	}
	
	public static ArrayList split(String src,String sep){
	        ArrayList a=new ArrayList();
	        if (null == src || null == sep)
	          return a;

	        int iStart=0;int iEnd=0;
	        while(true){
	          iEnd=src.indexOf(sep,iStart);
	          if (iEnd<0){
	            a.add(src.substring(iStart));
	            break;
	          }

	          a.add(src.substring(iStart,iEnd));
	          iStart=iEnd+1;
	        }

	        return a;
	 }

	 public static ArrayList split(String src,String sep,int iStartPoint,int iEndPoint){
	        ArrayList a=new ArrayList();
	        if (null == src || null == sep)
	          return a;

	        int iEnd=0;
	        while(true){
	          iEnd=src.indexOf(sep,iStartPoint);
	          if (iEnd<0 || iEnd >= iEndPoint){
	            a.add(src.substring(iStartPoint,iEndPoint));
	            break;
	          }

	          a.add(src.substring(iStartPoint,iEnd));
	          iStartPoint=iEnd+1;
	        }

	        return a;
	 }

	 public static String[] splitToArray(String src,String sep){
	   ArrayList a=new ArrayList();
	   if (null == src || null == sep)
	     return null;

	   int iStart=0;int iEnd=0;
	   while(true){
	     iEnd=src.indexOf(sep,iStart);
	     if (iEnd<0){
	       a.add(src.substring(iStart));
	       break;
	     }

	     a.add(src.substring(iStart,iEnd));
	     iStart=iEnd+1;
	   }

	   String[] sRet=new String[a.size()];
	   for (int i=0;i<a.size();i++){
	     sRet[i]=(String)a.get(i);
	   }

	   return sRet;
	}
	 
	/**
	 * 将数组按照limit长度进行分割 
	 * 	
	 * @param array
	 * @param limit
	 * @return ArrayList<T[]> 分割后的数组列表
	 */
	public static <T> ArrayList<T[]> splitArray(T[] array ,int limit){
		if(array == null||array.length==0){
			throw new NullPointerException();
		}
		ArrayList<T[]> tl = new ArrayList<T[]>();
		int size = array.length;
		if(limit>size){
			throw new IndexOutOfBoundsException();
		}
		int temp = size/limit;
		if (0 == size%limit) {
			temp--;
		}
		for (int i = 0; i <= temp; i++) {
			int start = i*limit;
			int end = (i+1)*limit;
			if(i==temp){
				end = size+1;
			}
			T[] tt = (T[]) ArrayUtils.subarray(array,start,end);
			tl.add(tt);
		}
		return tl;
	}

	  public static String StringOfDateTimeMills(){
		    long lTime=System.currentTimeMillis();
		    java.util.Date d=new java.util.Date(lTime);

		    int year=d.getYear()+1900;
		    String month="00" +(d.getMonth()+1);
		    String day="00" + d.getDate();
		    String hour="00" + d.getHours();
		    String minus="00" + d.getMinutes();
		    String second="00" + d.getSeconds();

		    return year + "-" + month.substring(month.length()-2)+ "-" + day.substring(day.length()-2)
		            + " " + hour.substring(hour.length()-2) + ":"+minus.substring(minus.length()-2) + ":" + second.substring(second.length()-2) + "." + (lTime % 1000);
	  }

	  //YYYY-MM-DD
	  public static String StringOfDate2(){
	    java.util.Date d=new java.util.Date();
	    int year=d.getYear()+1900;
	    String month="00" +(d.getMonth()+1);
	    String day="00" + d.getDate();

	    return year + "-" + month.substring(month.length()-2) + "-" + day.substring(day.length()-2);
	  }

	  //YYYY-MM-DD
	  public static String StringOfDate2(java.util.Date d){
	    int year=d.getYear()+1900;
	    String month="00" +(d.getMonth()+1);
	    String day="00" + d.getDate();

	    return year + "-" + month.substring(month.length()-2) + "-" + day.substring(day.length()-2);
	  }

	  //YYYY-MM-DD
	  public static String StringOfDate2(Calendar cal){
	    int year=cal.get(Calendar.YEAR);
	    String month="00" +(cal.get(Calendar.MONTH)+1);
	    String day="00" + cal.get(Calendar.DATE);

	    return year + "-" + month.substring(month.length()-2) + "-" + day.substring(day.length()-2);
	  }

	  //YYYY-MM-DD HH:MI
	  public static String StringOfDateMinus2(){
	    java.util.Date d=new java.util.Date();
	    int year=d.getYear()+1900;
	    String month="00" +(d.getMonth()+1);
	    String day="00" + d.getDate();
	    String hour="00" + d.getHours();
	    String minus="00" + d.getMinutes();

	    return year + "-" + month.substring(month.length()-2)+ "-" + day.substring(day.length()-2) + " "
	            + hour.substring(hour.length()-2)  + ":" + minus.substring(minus.length()-2);
	  }

	  //YYYY-MM-DD HH:MI:SS
	  public static String StringOfDateTime2(){
	    java.util.Date d=new java.util.Date();
	    int year=d.getYear()+1900;
	    String month="00" +(d.getMonth()+1);
	    String day="00" + d.getDate();
	    String hour="00" + d.getHours();
	    String minus="00" + d.getMinutes();
	    String second="00" + d.getSeconds();

	    return year + "-" + month.substring(month.length()-2)+ "-" + day.substring(day.length()-2) + " "
	            + hour.substring(hour.length()-2)  + ":" + minus.substring(minus.length()-2)  + ":" + second.substring(second.length()-2);
	  }

	  //YYYY-MM-DD HH:MI:SS
	  public static String StringOfDateTime2(java.util.Date d){
		    int year=d.getYear()+1900;
		    String month="00" +(d.getMonth()+1);
		    String day="00" + d.getDate();
		    String hour="00" + d.getHours();
		    String minus="00" + d.getMinutes();
		    String second="00" + d.getSeconds();

		    return year + "-" + month.substring(month.length()-2)+ "-" + day.substring(day.length()-2) + " "
		            + hour.substring(hour.length()-2)  + ":" + minus.substring(minus.length()-2)  + ":" + second.substring(second.length()-2);
	  }
	  
	  //YYYYMMDDHHMISS
	  public static String StringOfShortDateTime(){
	    java.util.Date d=new java.util.Date();
	    int year=d.getYear()+1900;
	    String month="00" +(d.getMonth()+1);
	    String day="00" + d.getDate();
	    String hour="00" + d.getHours();
	    String minus="00" + d.getMinutes();
	    String second="00" + d.getSeconds();

	    return year + month.substring(month.length()-2)+ day.substring(day.length()-2)
	            + hour.substring(hour.length()-2) + minus.substring(minus.length()-2) + second.substring(second.length()-2);
	  }
	  
	  //系统字符集
	  private static String CHARSET=null;
	  public static String getCharSet(){
	    if (null == CHARSET){
	      java.util.Properties p= System.getProperties();
	      if (p.containsKey("file.encoding")){
	        String sCharSet=p.getProperty("file.encoding");
	        System.out.println("system file.encoding="+sCharSet);
	        if (PubFunc.isEqualsStringIgnoreCase(sCharSet,"GBK")
	          || PubFunc.isEqualsStringIgnoreCase(sCharSet,"GB2312")){
	        	CHARSET="GBK";
	           	System.out.println("set file.encoding=GBK");
	        }else{
	        	CHARSET=sCharSet;
	        }
	      }

	      if (null == CHARSET){
	        CHARSET="GBK";
	        System.out.println("set default file.encoding=GBK");
	      }
	    }

	    return CHARSET;
	  }
	  
	  public static HashMap _hmGBKChar=new HashMap();
	  

	  /**
	   * 拼写SQL语句中的in条件
	   * @param arr
	   * @return
	   */
	  public static StringBuffer getInCondition(String strFldCode,ArrayList arr, boolean bAddQuota) {
		  String strQuota="";
		  if (bAddQuota){
			  strQuota="'";
		  }
		  StringBuffer sbCon = new StringBuffer();
		  int count = 0;
		  for (int i = 0; i < arr.size(); i++) {
			  String strDWID = arr.get(i).toString();
			  if (PubFunc.isEmptyString(strDWID) || PubFunc.isEqualsString(strDWID, "''")){
				  continue;
			  }
			  if (count<1000) {
				  if(count>0)sbCon.append(",");
				  count++;
				  sbCon.append(strQuota+strDWID+strQuota);
			  }else {
				  sbCon.append(") or "+strFldCode+" in (");
				  sbCon.append(strQuota+strDWID+strQuota);
				  count=1;
			  }
		  }
		  
		  if (sbCon.length() > 0){
			  sbCon.insert(0, " ("+strFldCode+ " in  (");
			  sbCon.append(")) ");
		  }else{
			  sbCon.append(" 1=0 ");
		  }
		  
		  return sbCon;
	  }
	  
	  /**
	   * 拼写SQL语句中的in条件
	   * @param arr
	   * @return
	   */
	  public static StringBuffer getInCondition(String strFldCode, ArrayList arr) {
		  return getInCondition(strFldCode, arr, true);
	  }
	  
	  public static StringBuffer getInCondition(String strFieldName,Iterator iter){
		    ArrayList aValue=new ArrayList();
		    while (iter.hasNext()){
		      aValue.add(iter.next());
		    }
		    return getInCondition(strFieldName,aValue);
	  }
	  
	  public static StringBuffer getNotInCondition(String strFldCode,Iterator iter){
		    ArrayList aValue=new ArrayList();
		    while (iter.hasNext()){
		      aValue.add(iter.next());
		    }
		    return getNotInCondition(strFldCode,aValue);
	  }

	  public static StringBuffer getNotInCondition(String strFldCode, ArrayList arr){
		    return getNotInCondition(strFldCode, arr, true);
	 }	  

	  /**
	   * 拼写SQL语句中的in条件
	   * @param arr
	   * @return
	   */
	  public static StringBuffer getNotInCondition(String strFldCode, ArrayList arr, boolean bAddQuota) {
		  String strQuota="";
		  if (bAddQuota){
			  strQuota="'";
		  }
		  StringBuffer sbCon = new StringBuffer();
		  int count = 0;
		  for (int i = 0; i < arr.size(); i++) {
			  String strDWID = (String)arr.get(i);
			  if (PubFunc.isEmptyString(strDWID) || PubFunc.isEqualsString(strDWID, "''")){
				  continue;
			  }
			  
			  if (count<1000) {
				  if(count>0)sbCon.append(",");
				  count++;
				  sbCon.append(strQuota+strDWID+strQuota);
			  }else {
				  sbCon.append(") and "+strFldCode+" not in (");
				  sbCon.append(strQuota+strDWID+strQuota);
				  count=1;
			  }
		  }
		  
		  if (sbCon.length() > 0){
			  sbCon.insert(0, " ("+strFldCode+ " not in  (");
			  sbCon.append(")) ");
		  }else{
			  sbCon.append(" 1=0 ");
		  }

		  return sbCon;
	  }
	  
	  //sql数据库的字段类型java.sql.types转换为java实际类型
	  public static int sql2JavaType(int intJavaSQLType){
			switch (intJavaSQLType) 
			{
				case java.sql.Types.BIGINT: //整形数据
				case java.sql.Types.INTEGER:
				case java.sql.Types.SMALLINT:
				case java.sql.Types.TINYINT:
					return PubConst.DATATYPE_DECIMAL;

				case java.sql.Types.DATE: //日期数据
				case java.sql.Types.TIME:
				case java.sql.Types.TIMESTAMP:
					return PubConst.DATATYPE_CHAR;

				case java.sql.Types.DECIMAL: //小数数据
				case java.sql.Types.DOUBLE:
				case java.sql.Types.FLOAT:
				case java.sql.Types.NUMERIC:
				case java.sql.Types.REAL:
					return PubConst.DATATYPE_DECIMAL;

				default: //字符数据
					return PubConst.DATATYPE_CHAR;
			}
	}	  
	
	  //sql数据库的字段类型java.sql.types转换为UQ数据类型
	  public static int sql2UqType(int intJavaSQLType){
			switch (intJavaSQLType) 
			{
				case java.sql.Types.BIGINT: //整形数据
				case java.sql.Types.INTEGER:
				case java.sql.Types.SMALLINT:
				case java.sql.Types.TINYINT:
					return PubConst.DATATYPE_DECIMAL;

				case java.sql.Types.DATE: //日期数据
				case java.sql.Types.TIME:
				case java.sql.Types.TIMESTAMP:
					return PubConst.DATATYPE_DATE;

				case java.sql.Types.DECIMAL: //小数数据
				case java.sql.Types.DOUBLE:
				case java.sql.Types.FLOAT:
				case java.sql.Types.NUMERIC:
				case java.sql.Types.REAL:
					return PubConst.DATATYPE_DECIMAL;

				default: //字符数据
					return PubConst.DATATYPE_CHAR;
			}
	}	  	  
	  
	  //UQ的字段类型转换为java实际类型
	  public static int uq2JavaType(String strUQType){
		    int intUQType=(new Integer(strUQType)).intValue();
			return uq2JavaType(intUQType);
	}	  

	  //UQ的字段类型转换为java实际类型
	  public static int uq2JavaType(int intUQType){
			switch (intUQType) 
			{
				case PubConst.DATATYPE_INTEGER: //整形
					return PubConst.DATATYPE_DECIMAL;

				case PubConst.DATATYPE_DECIMAL: //小数、金额、比率
				case PubConst.DATATYPE_MONEY:
				case PubConst.DATATYPE_RATE:
					return PubConst.DATATYPE_DECIMAL;

				default: //字符数据
					return PubConst.DATATYPE_CHAR;
			}
	  }
	  
	  //UQ的字段类型转换为java实际类型
	  public static int uq2JavaType_Date(String strUQType){
		    int intUQType=(new Integer(strUQType)).intValue();
			return uq2JavaType_Date(intUQType);
	  }	  

	  //UQ的字段类型转换为java实际类型
	  public static int uq2JavaType_Date(int intUQType){
			switch (intUQType) 
			{
				case PubConst.DATATYPE_INTEGER: //整形
					return PubConst.DATATYPE_DECIMAL;

				case PubConst.DATATYPE_DECIMAL: //小数、金额、比率
				case PubConst.DATATYPE_MONEY:
				case PubConst.DATATYPE_RATE:
					return PubConst.DATATYPE_DECIMAL;

				default:
					return intUQType;
			}
	  }
	  
	  //根据UQ的字段类型，将数据用单引号括起来
	  public static String uqQuotaData(String strUQType,String strData){
		  	int intUQType=(new Integer(strUQType)).intValue();
			switch (intUQType) 
			{
				case PubConst.DATATYPE_INTEGER: //整形
				case PubConst.DATATYPE_DECIMAL: //小数、金额、比率
				case PubConst.DATATYPE_MONEY:
				case PubConst.DATATYPE_RATE:
					if (PubFunc.isEmptyString(strData)){
						return "0";
					}else{
						return strData;
					}
				default: //字符数据
					if (PubFunc.isEmptyString(strData)){
						return "''";
					}else{
						return "'" + strData + "'";
					}
			}
	  }

	  //根据UQ的字段类型，将数据用单引号括起来
	  public static String uqNullData(String strUQType){
		  	int intUQType=(new Integer(strUQType)).intValue();
			switch (intUQType) 
			{
				case PubConst.DATATYPE_INTEGER: //整形
				case PubConst.DATATYPE_DECIMAL: //小数、金额、比率
				case PubConst.DATATYPE_MONEY:
				case PubConst.DATATYPE_RATE:
					return "0";
				default: //字符数据
					return "''";
			}
	  }

	  //UQ的字段类型转换为java实际类型
	  public static boolean isDecimalUqType(String strUQType){
		  if (PubFunc.isEqualsString(strUQType, PubConst.DATATYPE_STR_DECIMAL)
				  || PubFunc.isEqualsString(strUQType, PubConst.DATATYPE_STR_INTEGER)
				  || PubFunc.isEqualsString(strUQType, PubConst.DATATYPE_STR_MONEY)
				  || PubFunc.isEqualsString(strUQType, PubConst.DATATYPE_STR_RATE)){
			  return true;
		  }
		  
		  return false;
	  }
	  
	  public static String disQuota(String strOnevalue){
		  if (PubFunc.isEmptyString(strOnevalue) || strOnevalue.length() <= 1){
			  return strOnevalue;
		  }else if (strOnevalue.startsWith("'") && strOnevalue.endsWith("'")){
			  return strOnevalue.substring(1, strOnevalue.length()-1);
		  }else{
			  return strOnevalue;
		  }
	  }
	  
	  //Excel单元格名称如:A12。row=1,2,3,4   col=1,2,3,4
	  public static String cellName(int row,int col){
		  StringBuffer strRet=new StringBuffer();
		  col--;
		  if (col < 26){
			  strRet.append(PubConst._CHAR[col]);
		  }else if (col < 676){
			  int i2=col/26;
			  int i1=col%26;
			  strRet.append(PubConst._CHAR[i2-1]).append(PubConst._CHAR[i1]);
		  }else{
			  int i3=col/676;
			  int i2=(col-i3*676);
			  int i1=i2%26;
			  i2=i2/26;
			  strRet.append(PubConst._CHAR[i3-1]).append(PubConst._CHAR[i2-1]).append(PubConst._CHAR[i1]);
		  }
		  
		  strRet.append("" + row);
		  return strRet.toString();
	  }
	  
	  public static String filterErrMsg(String strErr){
		  if (PubFunc.isEmptyString(strErr)){
			  return "";
		  }
		  
		  int iPoint=strErr.indexOf("Exception:");
		  if (iPoint >= 0){
			  return strErr.substring(iPoint + 10);
		  }
		  
		  return strErr;
	  }

	  //获得一个字符串的1000以内的码，用于缓存
	  public static Integer getCode1000(String strValue){
		  if (PubFunc.isEmptyString(strValue)){
			  return new Integer(0);
		  }
		  
	      int c = 0;
	      for (int i=0;(i<strValue.length() && i < 20);i++){
	    	  c = c + strValue.charAt(i);
	      }

	      return new Integer(c%999);
	  }
	  
	  //插入值
	  public static void put1000(HashMap hm1000,String ID,Object obj){
			Integer iID1000=PubFunc.getCode1000(ID);
			HashMap hm=null;
			if (hm1000.containsKey(iID1000)){
				hm=(HashMap)hm1000.get(iID1000);
			}else{
				hm=new HashMap();
				hm1000.put(iID1000, hm);
			}
			
			hm.put(ID, obj);
		}
		
	  //是否存在
	  public static boolean exist1000(HashMap hm1000,String ID){
			Integer iID1000=PubFunc.getCode1000(ID);
			HashMap hm=null;
			if (hm1000.containsKey(iID1000)){
				hm=(HashMap)hm1000.get(iID1000);
				if (hm.containsKey(ID)){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}

	  //获得对象
	  public static Object get1000(HashMap hm1000,String ID){
			if (false == exist1000(hm1000, ID)){
				return null;
			}
			Integer iID1000=PubFunc.getCode1000(ID);
			HashMap hm=(HashMap)hm1000.get(iID1000);;
			return hm.get(ID);
	  }
	  /*
	  //Oracle的表全名，如：rmis5.T1@DB
	  public static String getFullTableName(UqTable uqTable , HashMap hmUqDbLink) throws Exception{
		  String strTableCode = uqTable.getTableCode();
		  String DB_ID=uqTable.getDbId();
		  if (false == PubFunc.isEmptyString(DB_ID)){
			  if (false == hmUqDbLink.containsKey(DB_ID)){
				  throw new Exception("信息域所在的外部数据库没有定义，信息域：" + uqTable.getTableName());
			  }

			  DbLink dbLink=(DbLink)hmUqDbLink.get(DB_ID);
		  		String DB_USER_TYPE=dbLink.getDbUserType();
		  		if (PubFunc.isEqualsString(DB_USER_TYPE, "2")){   //外部数据库
		      		if (false == PubFunc.isEqualsString(dbLink.getValid(),"1")){
		      			throw new Exception("信息域所在的外部数据库不能正常连接，信息域：" + strTableCode);
		      		}

		      		if (false == PubFunc.isEmptyString(dbLink.getDbLink())){
		      			strTableCode=strTableCode+ "@"+dbLink.getDbLink();
		      		}
		      		if (false == PubFunc.isEmptyString(dbLink.getDbUser())){
		      			strTableCode=dbLink.getDbUser()+"."+strTableCode;
		      		}
		  		}else
		  		if (PubFunc.isEqualsString(DB_USER_TYPE, "1")){   //本数据库其他用户
		      		if (false == PubFunc.isEqualsString(dbLink.getValid(),"1")){
		      			throw new Exception("信息域所在的外部数据库不能正常连接，信息域：" + strTableCode);
		      		}

		      		if (false == PubFunc.isEmptyString(dbLink.getDbUser())){
		      			strTableCode=dbLink.getDbUser()+"."+strTableCode;
		      		}
		  		}			  
		  }		  
  		
		  return strTableCode;
	  }*/
	  
	  //默认年度
	  public static String getDefaultNd(){
		    java.util.Date d=new java.util.Date();
		    return "" + (d.getYear() + 1900);
	  }	  
	  
	  // 将LIST转换成String 输出
	  public static String arrayListToString(AbstractCollection list, String splitChar) {
		  StringBuffer buffer = new StringBuffer();
		  Iterator it = list.iterator();
		  while(it.hasNext()) {			  
			  buffer.append(it.next()).append(splitChar); 
		  }
		 /* for (int i=0; i<list.size(); i++) {			  
			  buffer.append(list.get(i)).append(splitChar);
		  }*/
		  String tmpStr = buffer.toString();
		  if (tmpStr.equals("")) {
			  return "";
		  } else {
			  return tmpStr.substring(0, tmpStr.length()-1);  
		  }		  
	  }
	  
	// 将LIST转换成String 输出
	public static String[] CollectionToArray(AbstractCollection collection) {
		String[] buffer = new String[collection.size()];
		Iterator it = collection.iterator();
		int i=0;
		while (it.hasNext()) {
			buffer[i] = (String)it.next();
			i++;
		}
		return buffer;
	}
	
	//单位金额转换。iType：0=清册，1=清册页小计，2=清册总计，3=统计区
	public static void transMoneyUnit(Cds cdsPlanField, int iDestMoneyUnit, Cds cdsData, int iType) throws Exception{
		if (null == cdsData || cdsData.size() <= 0){
			return;
		}
		
		for (int iRowField=0;iRowField<cdsPlanField.size();iRowField++){
			boolean bTrans=false;

			String FIELD_DATA_TYPE=cdsPlanField.getStringValue(iRowField, "FIELD_DATA_TYPE");
			String strFieldName="F" + cdsPlanField.getStringValue(iRowField, "SEQ_NO");
			int iSrcMONEY_UNIT=cdsPlanField.getIntValue(iRowField, "MONEY_UNIT");
			int iScale=cdsPlanField.getIntValue(iRowField, "FIELD_DECIMAL");

			boolean bAvg=false;    //求平均值的，需要处理Fn_SUM字段
			String SUM_FUNC_TYPE="";
			if (iType == 1){
				SUM_FUNC_TYPE=cdsPlanField.getStringValue(iRowField, "PAGE_FUNC_TYPE");
			}else if (iType == 2){
				SUM_FUNC_TYPE=cdsPlanField.getStringValue(iRowField, "LIST_FUNC_TYPE");
			}else if (iType == 3){
				SUM_FUNC_TYPE=cdsPlanField.getStringValue(iRowField, "SUM_FUNC_TYPE");
				if (PubFunc.isEqualsString(SUM_FUNC_TYPE, PubConst.FUNC_TYPE_AVG)){
					bAvg=true;
				}
			}
			
			//求计数的，不处理
			if (PubFunc.isEqualsString(SUM_FUNC_TYPE, PubConst.FUNC_TYPE_COUNT)){
				continue;
			}
			
			//数据类型＝金额
			if (PubFunc.isEqualsString(FIELD_DATA_TYPE, PubConst.DATATYPE_STR_MONEY)){
				bTrans=true;
			}else{
				//对比计算类型:0=非计算字段，2=汇总项，10=上年同期数、11=同比增减数、20=基期数、21=基比增减数、30=上期数、31=环比增减数
				String CONTR_TYPE=cdsPlanField.getStringValue(iRowField, "CONTR_TYPE");
				if (PubFunc.isEqualsString(CONTR_TYPE, "2")
						|| PubFunc.isEqualsString(CONTR_TYPE, "10") || PubFunc.isEqualsString(CONTR_TYPE, "11")
						|| PubFunc.isEqualsString(CONTR_TYPE, "20") || PubFunc.isEqualsString(CONTR_TYPE, "21")
						|| PubFunc.isEqualsString(CONTR_TYPE, "30") || PubFunc.isEqualsString(CONTR_TYPE, "31")){
					//检查原字段是否金额类型，取其金额单位、小数位数
					String FIELD_ID=cdsPlanField.getStringValue(iRowField, "FIELD_ID");
					int[] iRows=cdsPlanField.filterAllRowNosByCode("FIELD_ID", FIELD_ID);
					for (int i=0;i<iRows.length;i++){
						String CONTR_TYPE2=cdsPlanField.getStringValue(iRows[i], "CONTR_TYPE");
						if (PubFunc.isEqualsString(CONTR_TYPE2, "0")){
							String FIELD_DATA_TYPE2=cdsPlanField.getStringValue(iRows[i], "FIELD_DATA_TYPE");
							if (PubFunc.isEqualsString(FIELD_DATA_TYPE2, PubConst.DATATYPE_STR_MONEY)){
								bTrans=true;
								iSrcMONEY_UNIT=cdsPlanField.getIntValue(iRows[i], "MONEY_UNIT");
								iScale=cdsPlanField.getIntValue(iRows[i], "FIELD_DECIMAL");
								break;
							}
						}
					}
				}
			}
			
			if (bTrans){
				if (iSrcMONEY_UNIT != iDestMoneyUnit){
					BigDecimal bDiv=new BigDecimal(Math.pow(10,(iDestMoneyUnit-iSrcMONEY_UNIT)));
					if (iSrcMONEY_UNIT > iDestMoneyUnit){
						bDiv=bDiv.setScale(iSrcMONEY_UNIT-iDestMoneyUnit,BigDecimal.ROUND_FLOOR);
					}
					
					BigDecimal bZero=new BigDecimal("0");
					if (cdsData.getFieldMap().containsKey(strFieldName)){
						for (int iRow=0;iRow<cdsData.size();iRow++){
							BigDecimal bData=cdsData.getBigDecimalValue(iRow, strFieldName);
							if (null == bData || bData.compareTo(bZero) == 0){
								continue;
							}
							bData=bData.divide(bDiv, iScale, BigDecimal.ROUND_HALF_UP);
							cdsData.setValue(iRow, strFieldName, bData);
							
						}
						
						//平均值，补充处理
						if (bAvg){
							String strFieldNameSum=strFieldName+"_SUM";
							if (cdsData.getFieldMap().containsKey(strFieldNameSum)){
								for (int iRow=0;iRow<cdsData.size();iRow++){
									BigDecimal bData=cdsData.getBigDecimalValue(iRow, strFieldNameSum);
									if (null == bData || bData.compareTo(bZero) == 0){
										continue;
									}
									bData=bData.divide(bDiv, iScale, BigDecimal.ROUND_HALF_UP);
									cdsData.setValue(iRow, strFieldNameSum, bData);
									
								}
							}
						}
					}
				}
			}
		}		
	}

	// 放大表格字体
	public static void zoomIn(JTable table) {
		int newSize = table.getFont().getSize() + 2;// 放大后的字体大小
		if (newSize < 24) {
			// 放大字体
			Font newFont = new Font("宋体", Font.PLAIN, newSize);
			table.setFont(newFont);
			// 放大行间距
			int newRowHeight = table.getRowHeight() + 5;
			table.setRowHeight(newRowHeight);
		}
		System.out.println(table.getFont());
	}

	// 缩小表格字体
	public static void zoomOut(JTable table) {
		int defaultSize = table.getFont().getSize();
		if (defaultSize > 12) {
			// 缩小字体
			Font newFont = new Font("宋体", Font.PLAIN, defaultSize - 2);
			table.setFont(newFont);
			// 缩小行间距
			int newRowHeight = table.getRowHeight() - 5;
			table.setRowHeight(newRowHeight);
			
		}
	}
	
	public static ComboBoxModel cdsToComboModel(Cds cdsData) {
		DefaultCheckItem item = null;
		ComboBoxModel model = new DefaultComboBoxModel();
		for (int i=0; i<cdsData.size(); i++) {
			item = new DefaultCheckItem(cdsData.getStringValue(i, "CODE"),
					cdsData.getStringValue(i, "NAME"));
			((DefaultComboBoxModel)model).addElement(item);
		}
		return model;
	}
	
	public static ComboBoxModel cdsToComboModel(Cds cdsData, String keyCode, String valueName) {
		DefaultCheckItem item = null;
		ComboBoxModel model = new DefaultComboBoxModel();
		for (int i=0; i<cdsData.size(); i++) {
			item = new DefaultCheckItem(cdsData.getStringValue(i, keyCode),
					cdsData.getStringValue(i, valueName));
			((DefaultComboBoxModel)model).addElement(item);
		}
		return model;
	}

	/**
	 * 横向合并
	 * 
	 * @param cds1
	 * @param cds2
	 * @return cds1与cds2水平合并后的Cds
	 * @throws Exception
	 */
	public static Cds horizontalMerge(Cds cds1, Cds cds2) throws Exception {
		if (null == cds1) {
			return cds2;
		}
		if (null == cds2) {
			return cds1;
		}
		// cds1,cds2交换，行数多的排前面
		Cds tempCds = cds1;
		if (cds1.size() < cds2.size()) {
			cds1 = cds2;
			cds2 = tempCds;
		}
	
		// 行数少的补空行
		for (int i = 0, j = cds1.size() - cds2.size(); i < j; i++) {
			cds2.appendRow();
		}
	
		Cds cds = new Cds();
		cds.appendField(cds1.getCdsColumns());
		cds.appendField(cds2.getCdsColumns());
	
		for (int i = 0, maxSize = cds1.size(); i < maxSize; i++) {
			ArrayList oneRow = new ArrayList();
			ArrayList oneRow1 = cds1.getOneRow(i);
			ArrayList oneRow2 = cds2.getOneRow(i);
	
			oneRow.addAll(oneRow1);
			oneRow.addAll(oneRow2);
			cds.appendRow(oneRow);
		}
		cds.resetFilter();
	
		return cds;
	}
	
//	public static IUqDBHelper getDbHelper(){
//		if (dbHelper == null) {
//			dbHelper = getService(IUqDBHelper.class, "IUqDBHelper");
//			//dbHelper = getService(IUqDBHelper.class, "IUqDBHelper", "http://localhost:7009/UQDB/");
//		}
//		
//		return dbHelper;
//	}
//	
//	public static <T> T getService(Class<T> clazz, String serviceId) {
//
//		String url = WorkEnv.getInstance().getRequestParameter(
//				PubFunc.class.getName(), "webRoot")
//				+ clazz.getSimpleName() + ".hessian";
//		//String url = webUrl + clazz.getSimpleName() + ".hessian";		
//		try {
//			if (url.startsWith("null")) {
//				url = "http://localhost:7001/UP/" + serviceId + ".hessian";
//			}
//			return (T) ServiceFactory.currentServiceFactory().getService(url,
//					clazz);
//		} catch (Throwable e) {
//			throw new IllegalArgumentException(String.format(
//					"service %s not found. url %s", serviceId, url, e));
//		}
//	}
//	
//	public static <T> T getService(Class<T> clazz, String serviceId, String serviceURL) {
//
//		String url = WorkEnv.getInstance().getRequestParameter(
//				PubFunc.class.getName(), "webRoot")
//				+ clazz.getSimpleName() + ".hessian";
//		try {
//			if (url.startsWith("null")) {
//				//url = "http://localhost:7009/FP/" + serviceId + ".hessian";
//				url = serviceURL + serviceId + ".hessian";
//			}
//			return (T) ServiceFactory.currentServiceFactory().getService(url,
//					clazz);
//		} catch (Throwable e) {
//			throw new IllegalArgumentException(String.format(
//					"service %s not found. url %s", serviceId, url, e));
//		}
//	}
	/*
	public static String getSqlWhere(ArrayList conditions){
		StringBuffer strWhere = new StringBuffer(1024);
		if (null != conditions && conditions.size() > 0){
			for(int i=0; i<conditions.size(); i++){
				ValueObject obj = (ValueObject)conditions.get(i);
				if (!PubFunc.isEmptyString(obj.getCode()) && !PubFunc.isEmptyString(obj.getValue()) 
					&& PubFunc.isEmptyString(obj.getTableCode())){
					String value = (String)obj.getValue();
					if (obj.getCompare().equalsIgnoreCase("between") && value.indexOf(",") > 0 ){
						String values[] = value.split(",");
						if (PubFunc.isEmptyString(values[0]) || values[0].equalsIgnoreCase("'null'") 
								|| PubFunc.isEmptyString(values[1]) || values[1].equalsIgnoreCase("'null'") || values[0].equals(values[1])) continue;
						
				        if (strWhere.length() > 0){
					           strWhere.append(" and ");
					    }
				        strWhere.append(obj.getCode()).append(" between ").append("'").append(values[0]).append("' and '").append(values[1]).append("'");
					}else if (obj.getCompare().equalsIgnoreCase("like") ){
				        if (strWhere.length() > 0){
					           strWhere.append(" and ");
					    }
				        strWhere.append(obj.getCode()).append(" like '%").append(obj.getValue()).append("%'");
					}else if (obj.getCompare().equalsIgnoreCase("in") || value.indexOf(",") > 0){
				        if (strWhere.length() > 0){
					           strWhere.append(" and ");
					    }
				        if (value.indexOf(",") == -1 && !value.startsWith("'")) {
				        	 strWhere.append(obj.getCode()).append(" in ('").append(value).append("') ");
				        } else {
				        	 strWhere.append(obj.getCode()).append(" in (").append(value).append(") ");
				        }
				       
					}else{
				        if (strWhere.length() > 0){
				           strWhere.append(" and ");
				        }
	
				        strWhere.append(obj.getCode()).append("='").append(obj.getValue()).append("'");
					}
				}
			}
		}
		
		return strWhere.toString();
	}
	
	public static HashMap getVarSqlWhere(ArrayList conditions){
		StringBuffer strWhere = new StringBuffer(1024);
		ArrayList paramValues = new ArrayList();
		HashMap sqlMap = new HashMap();
		
		if (null != conditions && conditions.size() > 0){
			for(int i=0; i<conditions.size(); i++){
				ValueObject obj = (ValueObject)conditions.get(i);
				if (!PubFunc.isEmptyString(obj.getCode()) && !PubFunc.isEmptyString(obj.getValue())
						&& PubFunc.isEmptyString(obj.getTableCode())){
					String value = (String) obj.getValue();
					if (obj.getCompare().equalsIgnoreCase("between") && value.indexOf(",") > 0 ){
						String values[] = value.split(",");
						if (PubFunc.isEmptyString(values[0]) || values[0].equalsIgnoreCase("'null'") 
								|| PubFunc.isEmptyString(values[1]) || values[1].equalsIgnoreCase("'null'")) continue;
						
				        if (strWhere.length() > 0){
				        	strWhere.append(" and ");
					    }
				        strWhere.append(obj.getCode()).append(" between ? and ?");
				        paramValues.add(values[0]);
				        paramValues.add(values[1]);
					}else if (obj.getCompare().equalsIgnoreCase("like") ){
				        if (strWhere.length() > 0){
					        strWhere.append(" and ");
					    }
				        strWhere.append(obj.getCode()).append(" like ? ");
				        paramValues.add("%"+obj.getValue()+"%");
					}else if (obj.getCompare().equalsIgnoreCase("in") || value.indexOf(",") > 0 ){//增加in的参数处理  modify by zhaoyang 20131114  
				        if (strWhere.length() > 0){
					        strWhere.append(" and ");
					    }
				        String[] paramArray=((String)obj.getValue()).split(",");
				        strWhere.append(obj.getCode()).append(" in ( ? ");
				        paramValues.add(paramArray[0].replaceAll("\\'", ""));
				        for(int h=1;h<paramArray.length;h++){
					        strWhere.append(" ,? ");
					        paramValues.add(paramArray[h].replaceAll("\\'", ""));
				        }
				        strWhere.append(" )");
					}else if (obj.getCompare().equalsIgnoreCase(">=")||obj.getCompare().equalsIgnoreCase("<=")
							||obj.getCompare().equalsIgnoreCase(">")||obj.getCompare().equalsIgnoreCase("<") ){
				        if (strWhere.length() > 0){
					        strWhere.append(" and ");
					    }
				        strWhere.append(obj.getCode()).append(obj.getCompare()).append("?");
				        paramValues.add(obj.getValue());
					}else{
				        if (strWhere.length() > 0){
				           strWhere.append(" and ");
				        } 
				        strWhere.append(obj.getCode()).append("=?");
				        paramValues.add(obj.getValue());
					}
				}
			}
			sqlMap.put("SQL_WHERE", strWhere.toString());
			sqlMap.put("PARAM_VALUES", paramValues);
		}
		
		return sqlMap;
	}
	
	public static HashMap getVarSqlWhereExists(ArrayList conditions){
		StringBuffer strWhere = new StringBuffer(1024);
		HashMap tableMap = new HashMap();
		if (null != conditions && conditions.size() > 0){
			for(int i=0; i<conditions.size(); i++){
				ValueObject obj = (ValueObject)conditions.get(i);
				if (!PubFunc.isEmptyString(obj.getCode()) && !PubFunc.isEmptyString(obj.getValue()) 
						&& !PubFunc.isEmptyString(obj.getTableCode())){
					String value = (String)obj.getValue();
					if (obj.getCompare().equalsIgnoreCase("between") && value.indexOf(",") > 0 ){
						String values[] = value.split(",");
						if (PubFunc.isEmptyString(values[0]) || values[0].equalsIgnoreCase("'null'") 
								|| PubFunc.isEmptyString(values[1]) || values[1].equalsIgnoreCase("'null'")) continue;
						
				        if (strWhere.length() > 0){
					           strWhere.append(" and ");
					    }
				        strWhere.append(obj.getCode()).append(" between ").append(values[0]).append(" and ").append(values[1]);
					}else if (obj.getCompare().equalsIgnoreCase("like") ){
				        if (strWhere.length() > 0){
					           strWhere.append(" and ");
					    }
				        strWhere.append(obj.getCode()).append(" like '%").append(obj.getValue()).append("%'");
					}else if (obj.getCompare().equalsIgnoreCase("in") || value.indexOf(",") > 0){
				        if (strWhere.length() > 0){
					           strWhere.append(" and ");
					    }
				    
				        strWhere.append(obj.getCode()).append(" in (").append(value).append(") ");
					}else{
				        String tableCode = obj.getTableCode();
						if (tableMap.containsKey(tableCode)) {							
							strWhere = (StringBuffer) tableMap.get(tableCode);
							strWhere.append(" and ").append(obj.getTableCode()).append(".")
								.append(obj.getCode()).append("='").append(obj.getValue()).append("'");
						} else {
							strWhere.append(" exists (select * from ").append(tableCode).append(" where ")
								.append(obj.getTableCode()).append(".").append(obj.getLinkFieldCode())
								.append(" = ?.").append(obj.getLinkFieldCode()).append(" and ")
				        		.append(obj.getTableCode()).append(".").append(obj.getCode())
				        		.append("='").append(obj.getValue()).append("'").append(")");						
						}
						
						tableMap.put(tableCode, strWhere);
					}
				}
			}
		}	
		
		return tableMap;//strWhere.toString();
	}
	
	//取得唯一码
	public static String createGUID() {
		UUID id = UUID.randomUUID();
		String tempID = id.toString().replaceAll("-", "");

		return tempID;
	}*/
	
	/**
	 * 获取指定的Cds列对象
	 * 
	 * @param cds
	 * @param name
	 *            列名
	 * @return 指定的Cds列对象
	 */
	public static CdsColumn getCdsColumnByName(Cds cds, String name) {
		CdsColumn[] cdsColumns = cds.getCdsColumns();
		for (int i = 0; i < cdsColumns.length; i++) {
			CdsColumn cdsColumn = cdsColumns[i];
			if (name.equalsIgnoreCase(cdsColumn.getColumnName())) {
				return cdsColumn;
			}
		}
		return null;
	}

}
