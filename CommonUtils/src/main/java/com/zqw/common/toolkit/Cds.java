package com.zqw.common.toolkit;

import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * <p>Title: 数据集对象</p>
 * <p>Description:二维数组数据集对象，插行、删行、修改等数据处理</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 用友政务</p>
 * @author chenhh
 * @version 6.1
 */
public class Cds implements Serializable {
  private static final long serialVersionUID = 9147832685214559999L;
  
  private ArrayList<String> _listColumnName = null;

  //数据
  protected ArrayList _arrData = null; //ArrayList<ArrayList>

  //字段名称、类型
  protected String[] _arrColumnName = null;

  protected int[] _arrColumnType = null; //数据类型

  protected int[] _arrColumnLength = null; //数据长度

  protected int[] _arrColumnScale = null; //小数位数

  //字段名称－序号
  protected HashMap _hmFieldName = null; //HashMap<String,Integer>

  //单字段-数据的HashMap，用于单字段过滤
  private HashMap _hmFilter = null; //HashMap<FieldName,HashMap<fieldValue,TreeSet<Integer>>>

  //分页显示的总记录数
  private int _iTotalRowCount = -1;
  
	private CdsColumn[] cdsColumns = null;// 所有字段对象

	/**
	 * 获取所有字段对象
	 * 
	 * @return CdsColumn[]
	 */
	public CdsColumn[] getCdsColumns() {
		cdsColumns = new CdsColumn[_arrColumnName.length];
		for (int i = 0; i < cdsColumns.length; i++) {
			CdsColumn cdsColumn = new CdsColumn(_arrColumnName[i], _arrColumnType[i], _arrColumnLength[i], _arrColumnScale[i]);
			cdsColumns[i] = cdsColumn;
		}

		return cdsColumns;
	}
	
	/**
	 * 根据多个列名获取多个Cds列对象
	 * 
	 * @param names
	 *            多个列名
	 * @return 多个Cds列对象
	 * @throws Exception
	 *             当{@code names}中某个列名不存在
	 */
	public CdsColumn[] getCdsColumnsObjByNames(String[] names) throws Exception {
		CdsColumn[] cdsColumns = new CdsColumn[names.length];
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			CdsColumn cdsColumn = this.getOneCdsColumnObjByName(name);
			cdsColumns[i] = cdsColumn;
		}

		return cdsColumns;
	}

	/**
	 * 获取单个Cds列对象
	 * 
	 * @param name
	 *            列名
	 * @return 单个Cds列对象
	 * @throws Exception
	 *             If the {@code name} field is not exist
	 */
	public CdsColumn getOneCdsColumnObjByName(String name) throws Exception {
		if (!isExistField(name)) {
			throw new Exception(name + "字段不存在");
		}

		int index = _listColumnName.indexOf(name);
		return new CdsColumn(name, _arrColumnType[index], _arrColumnLength[index], _arrColumnScale[index]);
	}



  public Cds() {
  }

  //构造
  public void initCds(ResultSet rs) throws Exception {
    try {
      if (null == rs) {
        return;
      }

      ResultSetMetaData rsMD = rs.getMetaData();
      _arrColumnName = new String[rsMD.getColumnCount()];
      _arrColumnType = new int[rsMD.getColumnCount()];
      _arrColumnLength = new int[rsMD.getColumnCount()];
      _arrColumnScale = new int[rsMD.getColumnCount()];

      _arrData = new ArrayList();

      //无列名的情况:
      for (int i = 0; i < _arrColumnName.length; i++) {
        if (PubFunc.isEmptyString(_arrColumnName[i])) {
          _arrColumnName[i] = "SYS__FIELD__" + i;
        }
      }

      for (int i = 0; i < rsMD.getColumnCount(); i++) {
        _arrColumnName[i] = rsMD.getColumnName(i + 1).toUpperCase();
        _arrColumnType[i] = rsMD.getColumnType(i + 1);
        if (rsMD.getColumnType(i + 1) == java.sql.Types.BLOB) {
          _arrColumnLength[i] = -1;
          _arrColumnScale[i] = 0;
        } else if (rsMD.getColumnType(i + 1) == java.sql.Types.CLOB) {
          _arrColumnLength[i] = 4000;
          _arrColumnScale[i] = 0;
        } else if (rsMD.getColumnType(i + 1) == java.sql.Types.LONGVARCHAR) {
          _arrColumnLength[i] = 4000;
          _arrColumnScale[i] = 0;
        } else {
          _arrColumnLength[i] = rsMD.getPrecision(i + 1);
          _arrColumnScale[i] = rsMD.getScale(i + 1);
        }
      }
      rsMD = null;

      _hmFieldName = new HashMap(_arrColumnName.length);
      for (int i = 0; i < _arrColumnName.length; i++) {
        _hmFieldName.put(_arrColumnName[i], new Integer(i));
      }

      while (rs.next()) {
        ArrayList aOneRow = new ArrayList(_arrColumnName.length);
        for (int i = 0; i < _arrColumnName.length; i++) {
          Object data = getColumnValue(rs, i + 1, _arrColumnType[i]);
          aOneRow.add(data);
        }

        this._arrData.add(aOneRow);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
  }

  public void initCds(String[] strColumnName, int[] intColumnType, int[] intColumnLength, int[] intColumnScale, HashMap hmFieldName,
    ArrayList arrayData) throws Exception {
    if (null == strColumnName || null == intColumnType || null == intColumnLength || null == intColumnScale || null == hmFieldName) {
      throw new Exception("传入的字段名称、数据类型、长度、小数位数为null");
    }
    if (intColumnType.length != strColumnName.length || intColumnLength.length != strColumnName.length
      || intColumnScale.length != strColumnName.length || intColumnType.length != hmFieldName.size()) {
      throw new Exception("传入的字段名称、数据类型、长度、小数位数的数组长度不相等");
    }

    _arrColumnName = strColumnName;
    _arrColumnType = intColumnType;
    _arrColumnLength = intColumnLength;
    _arrColumnScale = intColumnScale;

    _hmFieldName = hmFieldName;
    _arrData = arrayData;
  }

  //变更字段名称
  public void changeFieldName(HashMap hmFieldName) {
    HashMap hm = new HashMap();
    for (int i = 0; i < this._arrColumnName.length; i++) {
      if (hmFieldName.containsKey(_arrColumnName[i])) {
        String strNewField = (String) hmFieldName.get(_arrColumnName[i]);
        Integer iFieldNo = (Integer) _hmFieldName.get(_arrColumnName[i]);
        hm.put(strNewField, iFieldNo);
        _arrColumnName[i] = strNewField;
      } else {
        hm.put(_arrColumnName[i], _hmFieldName.get(_arrColumnName[i]));
      }
    }

    _hmFieldName = hm;

    this.resetFilter();
  }

  //20160121,返回strFieldName字段＝hsFieldValue值的所有记录序号，保持现Cds中的原记录顺序
  public int[] filterAllRowNosByCode(String strFieldName, HashSet<String> hsFieldValue) throws Exception {
	  if (null == hsFieldValue || hsFieldValue.size() <= 0){
		  return null;
	  }
	  
	  TreeSet<Integer> ts=new TreeSet<Integer>();
	  for (Iterator<String> iter=hsFieldValue.iterator();iter.hasNext();){
		  String strValue=iter.next();
		  int iRow=this.filterOneRowNoByCode(strFieldName, strValue);
		  if (iRow >= 0){
			  ts.add(iRow);
		  }
	  }
	  
	  if (ts.size()<=0){
		  return null;
	  }
	  
	  int[] iRet=new int[ts.size()];
	  int i=0;
	  for (Iterator<Integer> iter=ts.iterator();iter.hasNext();){
		  iRet[i]=iter.next();
		  i++;
	  }
	  
	  return iRet;
  }

  //返回strFieldName字段＝strFieldValue值的所有记录序号，保持现Cds中的原记录顺序
  public int[] filterAllRowNosByCode(String strFieldName, String strFieldValue) throws Exception {
    strFieldName = strFieldName.toUpperCase();
    if (false == _hmFieldName.containsKey(strFieldName)) {
      throw new Exception("字段不存在：" + strFieldName);
    } else if (null == _arrData || _arrData.size() <= 0) {
      return null;
    }

    int[] iRet = null;
    Object objHit = getEqualHitRowNos(strFieldName, strFieldValue);
    if (null == objHit) {
      return null;
    } else if (objHit instanceof TreeSet) {
      TreeSet tr = (TreeSet) objHit;
      iRet = new int[tr.size()];
      int i = 0;
      for (Iterator iter = tr.iterator(); iter.hasNext();) {
        iRet[i] = ((Integer) iter.next()).intValue();
        i++;
      }
    } else {
      iRet = new int[1];
      iRet[0] = ((Integer) objHit).intValue();
    }

    return iRet;
  }

  //返回strCodeField字段＝strCodeValue值的一条记录的strNameField的值
  public String filterNameByCode(String strCodeField, String strCodeValue, String strNameField) throws Exception {
    strCodeField = strCodeField.toUpperCase();
    strNameField = strNameField.toUpperCase();

    int iHit = filterOneRowNoByCode(strCodeField, strCodeValue);
    if (iHit >= 0) {
      return this.getStringValue(iHit, strNameField);
    } else {
      return null;
    }
  }

  //返回strCodeField字段＝strCodeValue值的一条记录的序号
  public int filterOneRowNoByCode(String strCodeField, String strCodeValue) throws Exception {
    strCodeField = strCodeField.toUpperCase();

    if (false == _hmFieldName.containsKey(strCodeField)) {
      throw new Exception("字段不存在：" + strCodeField);
    } else if (null == _arrData || _arrData.size() <= 0) {
      return -1;
    }

    Object objHit = getEqualHitRowNos(strCodeField, strCodeValue);
    if (null == objHit) {
      return -1;
    } else if (objHit instanceof TreeSet) {
      TreeSet tr = (TreeSet) objHit;
      return ((Integer) tr.first()).intValue();
    } else {
      return ((Integer) objHit).intValue();
    }
  }

  //获得字段是否数值类型
  public boolean bDecimal(String strFieldName) {
    Integer iCol = (Integer) _hmFieldName.get(strFieldName);
    return isDecimalType(this._arrColumnType[iCol.intValue()]);
  }

  //插行、删行、修改数据后，必须清除原筛选
  public void resetFilter() {
    this._hmFilter = null;
  }

  //插行、删行、修改数据后，必须清除原筛选
  public void resetFilter(String strFieldName) {
	  if (null !=_hmFilter){
		  if (_hmFilter.containsKey(strFieldName)){
			  _hmFilter.remove(strFieldName);
		  }
	  }
  }

  //简单多条件筛选，多个FilterClause之间为and的关系。返回所有命中的记录序号，按照原排序
  //比较符：支持  =、>、>=、<、<=、<>、*=*、=*、*=、between
  //性能测试：1万条记录，
  //FilterClause[] f=new FilterClause[2];
  //f[0]=new FilterClause("A","=*",new String[]{"2","4"});
  //f[1]=new FilterClause("v_num","<>",new String[]{"22.0","65.45","66"});
  //测试结果：0.001s完成搜索
  public int[] filter(FilterClause[] filterClause) throws Exception {
    if (null == _arrData || _arrData.size() <= 0) {
      return null;
    }
    if (null == filterClause || filterClause.length <= 0) {
      throw new Exception("筛选条件为空");
    }

    //检查筛选条件的合法性
    checkFilterValid(filterClause);

    //先处理相等的筛选条件
    HashSet hsRetRows = procEqualFilter(filterClause);

    //再处理其他的比较符
    if (null == arrOtherFilerDataType || arrOtherFilerDataType.length <= 0) {
      //没有其他比较符需要处理，所以不处理
    } else if (null == hsRetRows) {
      hsRetRows = new HashSet();
      for (int iRowNo = 0; iRowNo < _arrData.size(); iRowNo++) {
        boolean bHit = procOtherFilter(iRowNo);
        if (bHit) {
          hsRetRows.add(new Integer(iRowNo));
        }
      }
    } else {
      HashSet hsNotHit = new HashSet();
      for (Iterator iter = hsRetRows.iterator(); iter.hasNext();) {
        Integer iRowNo = (Integer) iter.next();
        boolean bHit = procOtherFilter(iRowNo.intValue());
        if (false == bHit) {
          hsNotHit.add(iRowNo);
        }
      }
      hsRetRows.removeAll(hsNotHit);
    }

    return sortRows(hsRetRows);
  }

  //记录排序
  protected int[] sortRows(HashSet hsRows) {
    if (null == hsRows || hsRows.size() <= 0) {
      return null;
    }
    TreeSet tr = new TreeSet(hsRows);
    int[] retRows = new int[tr.size()];
    int i = 0;
    for (Iterator iter = tr.iterator(); iter.hasNext();) {
      Object obj = iter.next();
      retRows[i] = ((Integer) obj).intValue();
      i++;
    }

    return retRows;
  }

  //检查筛选条件的合法性,去掉空值筛选条件
  private transient int[] arrOtherFilerDataType = null; //数据类型

  private transient int[] arrOtherFilerCol = null; //字段序号

  private transient int[] arrOtherFilerComp = null; //比较符

  private transient Object[] arrOtherFilerValue = null; //筛选条件值

  private void checkFilterValid(FilterClause[] filterClause) throws Exception {
    ArrayList a = new ArrayList();
    for (int i = 0; i < filterClause.length; i++) {
      String strFieldName = filterClause[i].getFieldName();
      if (false == _hmFieldName.containsKey(strFieldName)) {
        throw new Exception("字段不存在：" + strFieldName);
      }

      int iOperator = compareOperator(filterClause[i].getOperaor());

      if (iOperator != PubConst.COMP_EQUAL) {
        Object objFilterValue = filterClause[i].getFilterValue();
        if (null == objFilterValue) { //空筛选条件值表示不参与筛选
          continue;
        }

        if (objFilterValue instanceof String) {
          if (PubFunc.isEmptyString(objFilterValue)) {
            continue;
          }
        } else {
          String[] strValue = (String[]) objFilterValue;
          if (strValue.length <= 0 || (strValue.length == 1 && PubFunc.isEmptyString(strValue[0]))) {
            continue;
          }
        }

        a.add(filterClause[i]);
      }
    }

    arrOtherFilerDataType = new int[a.size()]; //比较数据类型
    arrOtherFilerCol = new int[a.size()]; //字段序号
    arrOtherFilerComp = new int[a.size()]; //比较符
    arrOtherFilerValue = new Object[a.size()]; //筛选条件值
    for (int i = 0; i < a.size(); i++) {
      FilterClause aFilterClause = (FilterClause) a.get(i);
      String strFieldName = aFilterClause.getFieldName();
      String strOperator = aFilterClause.getOperaor();
      Object objFilterValue = aFilterClause.getFilterValue();

      int iCol = ((Integer) _hmFieldName.get(strFieldName)).intValue();
      arrOtherFilerDataType[i] = PubFunc.sql2JavaType(this._arrColumnType[iCol]);
      arrOtherFilerCol[i] = iCol;
      arrOtherFilerComp[i] = this.compareOperator(strOperator);

      if (objFilterValue instanceof String) {
        arrOtherFilerValue[i] = convertFilterValue(arrOtherFilerDataType[i], (String) objFilterValue);
        if (arrOtherFilerComp[i] == PubConst.COMP_BETWEEN) {
          throw new Exception("between类型筛选需要两个比较值，比较字段：" + strFieldName);
        }
      } else if (objFilterValue instanceof String[]) {
        String[] strValue = (String[]) objFilterValue;
        if (arrOtherFilerComp[i] == PubConst.COMP_BETWEEN && strValue.length != 2) {
          throw new Exception("between类型筛选需要两个比较值，比较字段：" + strFieldName);
        }
        ArrayList aVilidValue = new ArrayList();
        for (int j = 0; j < strValue.length; j++) {
          Object obj = convertFilterValue(arrOtherFilerDataType[i], strValue[j]);
          if (null == obj && arrOtherFilerComp[i] != PubConst.COMP_BETWEEN) {
            continue;
          }

          aVilidValue.add(obj);
        }

        Object[] objValue = new Object[aVilidValue.size()];
        for (int j = 0; j < aVilidValue.size(); j++) {
          objValue[j] = aVilidValue.get(j);
        }
        arrOtherFilerValue[i] = objValue;
      } else {
        throw new Exception("筛选值的类型只能是String或者String[]，比较字段：" + strFieldName);
      }
    }
  }

  //把筛选值字符串转换为相应数据类型
  private Object convertFilterValue(int iCompDataType, String strValue) {
    if (PubFunc.isEmptyString(strValue)) {
      return null;
    } else if (iCompDataType == PubConst.DATATYPE_DECIMAL) {
      return new BigDecimal(strValue);
    } else {
      return strValue;
    }
  }

  //处理相等的筛选条件
  private HashSet procEqualFilter(FilterClause[] filterClause) throws Exception {
    HashSet hsRetRows = null;
    for (int i = 0; i < filterClause.length; i++) {
      String strOperator = filterClause[i].getOperaor();

      //仅处理相等的条件
      if (false == strOperator.equals("=")) {
        continue;
      }

      HashSet hsOneFilterRows = procOneEqualFilter(filterClause[i]);
      if (null == hsOneFilterRows) {
        continue;
      } else if (0 == i) {
        hsRetRows = hsOneFilterRows;
      } else {
        HashSet hsNotHit = new HashSet();
        for (Iterator iter = hsRetRows.iterator(); iter.hasNext();) {
          Object obj = iter.next();
          if (false == hsOneFilterRows.contains(obj)) {
            hsNotHit.add(obj);
          }
        }
        hsRetRows.removeAll(hsNotHit);
      }
    }

    return hsRetRows;
  }

  private boolean procOtherFilter(int iRowNo) throws Exception {
    boolean bHit = true;
    for (int i = 0; i < this.arrOtherFilerDataType.length; i++) {
      int iOperator = this.arrOtherFilerComp[i];
      int iDataType = this.arrOtherFilerDataType[i];
      int iCol = this.arrOtherFilerCol[i];
      Object objFilterValue = this.arrOtherFilerValue[i];

      Object objSrcValue = this.getValue(iRowNo, iCol); //Cds中的值
      if (objFilterValue instanceof Object[]) {
        Object[] strFilterValue = (Object[]) objFilterValue;
        if (strFilterValue.length == 0) {
          continue;
        } else if (iOperator == PubConst.COMP_BETWEEN) {
          if (null == strFilterValue[0]) {
            bHit = compareOtherValue(iDataType, objSrcValue, PubConst.COMP_LESS_EQUAL, strFilterValue[1]);
          } else if (null == strFilterValue[1]) {
            bHit = compareOtherValue(iDataType, objSrcValue, PubConst.COMP_GREAT_EQUAL, strFilterValue[0]);
          } else {
            bHit = compareOtherValue_Between(iDataType, objSrcValue, PubConst.COMP_GREAT_EQUAL, strFilterValue[0], strFilterValue[1]);
          }
        } else {
          for (int iValue = 0; iValue < strFilterValue.length; iValue++) {
            bHit = compareOtherValue(iDataType, objSrcValue, iOperator, strFilterValue[iValue]);
            if (iOperator == PubConst.COMP_NOT_EQUAL) {
              if (!bHit) {
                break;
              }
            } else if (bHit) {
              break;
            }
          }
        }
      } else {
        bHit = compareOtherValue(iDataType, objSrcValue, iOperator, objFilterValue);
      }

      if (false == bHit) {
        break;
      }
    }

    return bHit;
  }

  private static BigDecimal convertToBigDecimal(Object objValue) {
    if (null == objValue) {
      return new BigDecimal(0);
    } else if (objValue instanceof BigDecimal) {
      return (BigDecimal) objValue;
    } else if (objValue instanceof Integer) {
      return new BigDecimal(((Integer) objValue).intValue());
    } else if (objValue instanceof Double) {
      return new BigDecimal(((Double) objValue).doubleValue());
    } else if (objValue instanceof String) {
      if ("".equals((String) objValue)) {
        return new BigDecimal(0);
      } else {
        return new BigDecimal(objValue.toString());
      }
    } else {
      return new BigDecimal(objValue.toString());
    }
  }

  //比较两个值，是否命中
  private static boolean compareOtherValue(int iDataType, Object objSrcValue, int iOperator, Object objFilterValue) {
    boolean bHit = false;
    if (iDataType == PubConst.DATATYPE_CHAR) {
      bHit = compareOtherValue(objSrcValue.toString(), iOperator, objFilterValue.toString());
    } else {
      bHit = compareOtherValue(convertToBigDecimal(objSrcValue), iOperator, convertToBigDecimal(objFilterValue));
    }

    return bHit;
  }

  //比较字符串
  private static boolean compareOtherValue(String objSrcValue, int iOperator, String objFilterValue) {
    boolean bHit = false;
    if (null == objSrcValue) {
      objSrcValue = "";
    }
    switch (iOperator) {
    case PubConst.COMP_EQUAL:
      bHit = (objSrcValue.compareTo(objFilterValue) == 0);
      break;
    case PubConst.COMP_NOT_EQUAL:
      bHit = (objSrcValue.compareTo(objFilterValue) != 0);
      break;
    case PubConst.COMP_GREAT:
      bHit = (objSrcValue.compareTo(objFilterValue) > 0);
      break;
    case PubConst.COMP_GREAT_EQUAL:
      bHit = (objSrcValue.compareTo(objFilterValue) >= 0);
      break;
    case PubConst.COMP_LESS:
      bHit = (objSrcValue.compareTo(objFilterValue) < 0);
      break;
    case PubConst.COMP_LESS_EQUAL:
      bHit = (objSrcValue.compareTo(objFilterValue) <= 0);
      break;
    case PubConst.COMP_ANY_EQUAL:
      bHit = (objSrcValue.indexOf(objFilterValue) >= 0);
      break;
    case PubConst.COMP_FRONT_EQUAL:
      bHit = objSrcValue.startsWith(objFilterValue);
      break;
    case PubConst.COMP_BEHIND_EQUAL:
      bHit = objSrcValue.endsWith(objFilterValue);
      break;
    }

    return bHit;
  }

  //比较BigDecimal
  private static boolean compareOtherValue(BigDecimal objSrcValue, int iOperator, BigDecimal objFilterValue) {
    boolean bHit = false;
    if (null == objSrcValue) {
      objSrcValue = new BigDecimal(0);
    }
    switch (iOperator) {
    case PubConst.COMP_EQUAL:
      bHit = (objSrcValue.compareTo(objFilterValue) == 0);
      break;
    case PubConst.COMP_NOT_EQUAL:
      bHit = (objSrcValue.compareTo(objFilterValue) != 0);
      break;
    case PubConst.COMP_GREAT:
      bHit = (objSrcValue.compareTo(objFilterValue) > 0);
      break;
    case PubConst.COMP_GREAT_EQUAL:
      bHit = (objSrcValue.compareTo(objFilterValue) >= 0);
      break;
    case PubConst.COMP_LESS:
      bHit = (objSrcValue.compareTo(objFilterValue) < 0);
      break;
    case PubConst.COMP_LESS_EQUAL:
      bHit = (objSrcValue.compareTo(objFilterValue) <= 0);
      break;
    }

    return bHit;
  }

  //between 两个值，是否命中
  private static boolean compareOtherValue_Between(int iDataType, Object objSrcValue, int iOperator, Object objFilterValue1, Object objFilterValue2) {
    if (iDataType == PubConst.DATATYPE_CHAR) {
      if (PubFunc.isEmptyString(objSrcValue)) {
        return false;
      } else {
        if (((String) objSrcValue).compareTo((String) objFilterValue1) >= 0 && ((String) objSrcValue).compareTo((String) objFilterValue2) <= 0) {
          return true;
        } else {
          return false;
        }
      }
    } else {
      BigDecimal decSrc = convertToBigDecimal(objSrcValue);
      BigDecimal decFilterValue1 = convertToBigDecimal(objFilterValue1);
      BigDecimal decFilterValue2 = convertToBigDecimal(objFilterValue2);
      if (decSrc.compareTo(decFilterValue1) >= 0 && decSrc.compareTo(decFilterValue2) <= 0) {
        return true;
      } else {
        return false;
      }
    }
  }

  //比较符
  protected int compareOperator(String strOperator) throws Exception {
    //=等于，>大于，>=大于等于，<小于，<=小于等于，<>不等于，*=*模糊相等，=*后部模糊相等，*=前部模糊相等，between起、止范围内(含)
    if ("=".equals(strOperator)) {
      return PubConst.COMP_EQUAL;
    } else if (">".equals(strOperator)) {
      return PubConst.COMP_GREAT;
    } else if (">=".equals(strOperator)) {
      return PubConst.COMP_GREAT_EQUAL;
    } else if ("<".equals(strOperator)) {
      return PubConst.COMP_LESS;
    } else if ("<=".equals(strOperator)) {
      return PubConst.COMP_LESS_EQUAL;
    } else if ("<>".equals(strOperator)) {
      return PubConst.COMP_NOT_EQUAL;
    } else if ("*=*".equals(strOperator)) {
      return PubConst.COMP_ANY_EQUAL;
    } else if ("=*".equals(strOperator)) {
      return PubConst.COMP_FRONT_EQUAL;
    } else if ("*=".equals(strOperator)) {
      return PubConst.COMP_BEHIND_EQUAL;
    } else if ("BETWEEN".equals(strOperator)) {
      return PubConst.COMP_BETWEEN;
    } else {
      throw new Exception("比较符错误：" + strOperator + "。必须是：=、>、>=、<、<=、<>、*=*、=*、*=、between中之一");
    }
  }

  //比较两个英文、中文字符串，性能较好
  protected int chineseCompare(String s1, String s2) {
    if (null == s1 && null == s2) {
      return 0;
    } else if (null != s1 && null == s2) {
      return 1;
    } else if (null == s1 && null != s2) {
      return -1;
    }

    if (s1.compareTo(s2) == 0) {
      return 0;
    }

    try {
      int len1 = s1.length();
      int len2 = s2.length();
      int n = Math.min(len1, len2);
      for (int i = 0; i < n; i++) {
        Character c1 = new Character(s1.charAt(i));
        Character c2 = new Character(s2.charAt(i));
        if (c1.compareTo(c2) == 0) {
          continue;
        }
        int s1_code = getCharCode(c1);
        int s2_code = getCharCode(c2);

        if (s1_code == s2_code)
          continue;

        if (s1_code > 0 && s2_code < 0)
          return -1;
        if (s2_code > 0 && s1_code < 0)
          return 1;
        return s1_code - s2_code;
      }

      return len1 - len2;
    } catch (Exception ignore) {
      return s1.compareTo(s2);
    }
  }

  //GBK编码值
  private int getCharCode(Character c) throws Exception {
    if (PubFunc._hmGBKChar.containsKey(c)) {
      return ((Integer) PubFunc._hmGBKChar.get(c)).intValue();
    }

    byte[] b = (new String(c.toString().getBytes(PubFunc.getCharSet()), "GBK")).getBytes();
    int value = 0;

    //保证取第一个字符（汉字或者英文）
    for (int i = 0; i < b.length && i <= 2; i++) {
      value = value * 100 + b[i];
    }

    PubFunc._hmGBKChar.put(c, new Integer(value));

    return value;
  }

  //处理一个相等筛选条件，返回null表示本条件不筛选或者取全部数据
  private HashSet procOneEqualFilter(FilterClause oneFilterClause) throws Exception {
    HashSet hsOneEqualFilter = new HashSet();

    String strFieldName = oneFilterClause.getFieldName();
    Object objValue = oneFilterClause.getFilterValue();
    if (null == objValue) { //空筛选条件值表示不参与筛选
      return null;
    } else if (objValue instanceof String) {
      if (PubFunc.isEmptyString(objValue)) {
        return null;
      } else {
        Object objHit = getEqualHitRowNos(strFieldName, (String) objValue);
        if (null == objHit) {
          return hsOneEqualFilter;
        } else if (objHit instanceof TreeSet) {
          TreeSet tr = (TreeSet) objHit;
          hsOneEqualFilter.addAll(tr);
        } else {
          hsOneEqualFilter.add(objHit);
        }
      }
    } else if (objValue instanceof BigDecimal || objValue instanceof Integer) {
    	Object objHit = getEqualHitRowNos(strFieldName, objValue.toString());
        if (null == objHit) {
          return hsOneEqualFilter;
        } else if (objHit instanceof TreeSet) {
          TreeSet tr = (TreeSet) objHit;
          hsOneEqualFilter.addAll(tr);
        } else {
          hsOneEqualFilter.add(objHit);
        }
    } else if (objValue instanceof BigDecimal[] || objValue instanceof Integer[]){
      Object[] strValue = (Object[]) objValue;
      if (strValue.length <= 0) {
        return null;
      }
      for (int iValue = 0; iValue < strValue.length; iValue++) {
        Object objHit = getEqualHitRowNos(strFieldName, strValue[iValue].toString());
        if (null == objHit) {
          continue;
        } else if (objHit instanceof TreeSet) {
          TreeSet tr = (TreeSet) objHit;
          hsOneEqualFilter.addAll(tr);
        } else {
          hsOneEqualFilter.add(objHit);
        }
      }
    } else {
        String[] strValue = (String[]) objValue;
        if (strValue.length <= 0 || (strValue.length == 1 && PubFunc.isEmptyString(strValue[0]))) {
          return null;
        }
        for (int iValue = 0; iValue < strValue.length; iValue++) {
          Object objHit = getEqualHitRowNos(strFieldName, strValue[iValue]);
          if (null == objHit) {
            continue;
          } else if (objHit instanceof TreeSet) {
            TreeSet tr = (TreeSet) objHit;
            hsOneEqualFilter.addAll(tr);
          } else {
            hsOneEqualFilter.add(objHit);
          }
        }
      }

    return hsOneEqualFilter;
  }

  //单个值的相等筛选:单个值、数组
  private Object getEqualHitRowNos(String strCodeField, String strCodeValue) {
    HashMap hmValueCode = null;
    if (null == _hmFilter) {
      _hmFilter = new HashMap();
    }
    if (false == _hmFilter.containsKey(strCodeField)) {
      hmValueCode = new HashMap();
      _hmFilter.put(strCodeField, hmValueCode);
    } else {
      hmValueCode = (HashMap) _hmFilter.get(strCodeField);
    }

    if (hmValueCode.size() <= 0) {
      for (int iRow = 0; iRow < this.size(); iRow++) {
        String strValue = this.getStringValue(iRow, strCodeField);
        if (hmValueCode.containsKey(strValue)) {
          Object objData = hmValueCode.get(strValue);
          if (objData instanceof TreeSet) {
            ((TreeSet) objData).add(new Integer(iRow));
          } else {
            TreeSet tr = new TreeSet();
            tr.add(objData);
            tr.add(new Integer(iRow));

            hmValueCode.put(strValue, tr);
          }
        } else {
          hmValueCode.put(strValue, new Integer(iRow));
        }
      }
    }

    return hmValueCode.get(strCodeValue);
  }

  //上移1行
  public int moveUp(int iCurRow) {
    return moveUp(iCurRow, 1);
  }

  //上移n行
  public int moveUp(int iCurRow, int iCount) {
    if (this._arrData.size() <= 0 || iCurRow < 0 || iCurRow >= this._arrData.size()) {
      return -1;
    } else if (iCurRow == 0) {
      return iCurRow;
    } else {
      int iNewRow = iCurRow - iCount;
      if (iNewRow <= 0) {
        iNewRow = 0;
      }

      ArrayList curRow = (ArrayList) this._arrData.get(iCurRow);
      this._arrData.remove(iCurRow);
      this._arrData.add(iNewRow, curRow);

      resetFilter();

      return iNewRow;
    }
  }

  //下移1行
  public int moveDown(int iCurRow) {
    return moveDown(iCurRow, 1);
  }

  //下移n行
  public int moveDown(int iCurRow, int iCount) {
    if (this._arrData.size() <= 0 || iCurRow < 0 || iCurRow >= this._arrData.size()) {
      return -1;
    } else if (iCurRow == (this._arrData.size() - 1)) {
      return iCurRow;
    } else {
      int iNewRow = iCurRow + iCount;
      if (iNewRow >= (this._arrData.size() - 1)) {
        iNewRow = this._arrData.size() - 1;
      }

      ArrayList curRow = (ArrayList) this._arrData.get(iCurRow);
      this._arrData.remove(iCurRow);
      this._arrData.add(iNewRow, curRow);

      resetFilter();

      return iNewRow;
    }
  }

  //克隆一个完整的Cds
  public Cds cloneCds() {
    Cds objRet = cloneCds_NoData();

    for (int i = 0; i < this._arrData.size(); i++) {
      ArrayList aRow = (ArrayList) _arrData.get(i);
      ArrayList aRowNew = PubFunc.cloneArrayList(aRow);
      objRet._arrData.add(aRowNew);
    }

    return objRet;
  }

  //克隆一个完整的Cds，但没有数据
  public Cds cloneCds_NoData() {
    Cds objRet = new Cds();
    objRet._arrColumnName = PubFunc.cloneString(this._arrColumnName);
    objRet._arrColumnType = PubFunc.cloneInt(this._arrColumnType);
    objRet._arrColumnLength = PubFunc.cloneInt(this._arrColumnLength);
    objRet._arrColumnScale = PubFunc.cloneInt(this._arrColumnScale);
    objRet._hmFieldName = PubFunc.cloneHashMap(this._hmFieldName);
    objRet._arrData = new ArrayList();

    return objRet;
  }

  //克隆一个完整的Cds，仅复制一条数据
  public Cds cloneCds_SomeRow(int iRowNo) {
    Cds objRet = cloneCds_NoData();

    if (iRowNo < this._arrData.size()) {
      ArrayList aRow = (ArrayList) _arrData.get(iRowNo);
      ArrayList aRowNew = PubFunc.cloneArrayList(aRow);
      objRet._arrData.add(aRowNew);
    }

    return objRet;
  }

  //克隆一个完整的Cds，仅复制部分数据
  public Cds cloneCds_SomeRow(int[] iRowNos) {
    Cds objRet = cloneCds_NoData();

    if (null != iRowNos) {
      for (int i = 0; i < iRowNos.length; i++) {
        int iRowNo = iRowNos[i];
        if (iRowNo < this._arrData.size()) {
          ArrayList aRow = (ArrayList) _arrData.get(iRowNo);
          ArrayList aRowNew = PubFunc.cloneArrayList(aRow);
          objRet._arrData.add(aRowNew);
        }
      }
    }

    return objRet;
  }

  //取Cds的子集，不复制数据.iRowNos参数在传入前应该已经排序
  public Cds getCds_SomeRow(int[] iRowNos) {
    Cds objRet = cloneCds_NoData();

    if (null != iRowNos) {
      for (int i = 0; i < iRowNos.length; i++) {
        int iRowNo = iRowNos[i];
        if (iRowNo < this._arrData.size()) {
          ArrayList aRow = (ArrayList) _arrData.get(iRowNo);
          objRet._arrData.add(aRow);
        }
      }
    }

    return objRet;
  }


  //追加一个字段
  public void appendField(String strColumnName, int intColumnType, int intColumnLength, int intColumnScale, Object objDefaultValue) throws Exception {
    strColumnName = strColumnName.toUpperCase();
    /*if (null == _arrColumnName || null == _arrColumnType || null == _arrColumnLength || null == _arrColumnScale){
    	throw new Exception("没有初始化");
    }*/
    if (null == _arrColumnName) {
      _arrColumnName = new String[0];
    }
    if (null == _arrColumnType) {
      _arrColumnType = new int[0];
    }
    if (null == _arrColumnLength) {
      _arrColumnLength = new int[0];
    }
    if (null == _arrColumnScale) {
      _arrColumnScale = new int[0];
    }
    if (null == _hmFieldName) {
      _hmFieldName = new HashMap();
    }
    if (null == _arrData) {
      _arrData = new ArrayList();
    }

    if (_hmFieldName.containsKey(strColumnName)) {
      return;
    }

    _arrColumnName = PubFunc.appendStringArray(_arrColumnName, strColumnName);
    _arrColumnType = PubFunc.appendIntArray(_arrColumnType, intColumnType);
    _arrColumnLength = PubFunc.appendIntArray(_arrColumnLength, intColumnLength);
    _arrColumnScale = PubFunc.appendIntArray(_arrColumnScale, intColumnScale);

    if (_hmFieldName.containsKey(strColumnName)) {
      throw new Exception("字段重复定义：" + strColumnName);
    }
    _hmFieldName.put(strColumnName, new Integer(_arrColumnName.length - 1));

    for (int i = 0; i < this._arrData.size(); i++) {
      ArrayList arrRow = this.getOneRow(i);
      arrRow.add(objDefaultValue);
    }
  }

  public HashMap getFieldMap() {
    return this._hmFieldName;
  }

  public String[] getColumnName() {
    return _arrColumnName;
  }

  public int[] getColumnType() {
    return _arrColumnType;
  }

  public int[] getColumnLength() {
    return _arrColumnLength;
  }

  public int[] getColumnScale() {
    return _arrColumnScale;
  }

  public ArrayList getAllData() {
    return _arrData;
  }

  public int size() {
    return _arrData.size();
  }

  public ArrayList getOneRow(int iRow) {
    return _arrData==null || _arrData.size()==0?null: (ArrayList) _arrData.get(iRow);
  }

  public void delOneRow(int iRow) {
	  if (iRow<0){
		  return;
	  }
	  
    _arrData.remove(iRow);

    //清除过滤器
    resetFilter();
  }

  public void delAllRow() {
    _arrData.clear();

    _iTotalRowCount = -1;

    //清除过滤器
    resetFilter();
  }

  //删除部分行
  public void delSomeRow(int[] iRow) {
    if (null == iRow || iRow.length <= 0) {
      return;
    }

    TreeSet tr = new TreeSet();
    for (int i = 0; i < iRow.length; i++) {
      tr.add(new Integer((-1) * iRow[i]));
    }

    for (Iterator iter = tr.iterator(); iter.hasNext();) {
      int i = ((Integer) iter.next()).intValue() * (-1);
      if (_arrData.size() > i) {
        _arrData.remove(i);
      }
    }

    //清除过滤器
    resetFilter();
  }

  //合并一个Cds的数据到本Cds
  public void merge(Cds cds) {
    if (null == cds || cds.size() <= 0) {
      return;
    }

    for (int iRow = 0; iRow < cds.size(); iRow++) {
      ArrayList aRow = (ArrayList) cds.getOneRow(iRow);
      ArrayList aRowNew = PubFunc.cloneArrayList(aRow);
      this._arrData.add(aRowNew);
    }

    //清除过滤器
    resetFilter();
  }
  
  //合并一个Cds的数据到本Cds，不克隆数据
  public void mergeNotClone(Cds cds) {
    if (null == cds || cds.size() <= 0) {
      return;
    }

    for (int iRow = 0; iRow < cds.size(); iRow++) {
      ArrayList aRow = (ArrayList) cds.getOneRow(iRow);
      this._arrData.add(aRow);
    }

    //清除过滤器
    resetFilter();
  }
  
	/**
	 * 增加多个字段
	 * 
	 * @param cdsColumns
	 *            多个字段
	 * @throws Exception
	 */
	public void appendField(CdsColumn[] cdsColumns) throws Exception {
		for (int i = 0; i < cdsColumns.length; i++) {
			CdsColumn cdsColumn = cdsColumns[i];
			this.appendField(cdsColumn);
		}
	}

	/**
	 * 增加一个字段
	 * 
	 * @param cdsColumn
	 *            列（字段）
	 * @throws Exception
	 */
	public void appendField(CdsColumn cdsColumn) throws Exception {
		Object objDefaultValue = null;
		BigDecimal dZero = new BigDecimal(0);
		String strDef = "";
		if (isDecimalType(cdsColumn.getColumnType())) {
			objDefaultValue = dZero;
		} else {
			objDefaultValue = strDef;
		}

		this.appendField(cdsColumn.getColumnName(), cdsColumn.getColumnType(), cdsColumn.getColumnLength(), cdsColumn.getColumnScale(), objDefaultValue);
	}


  public int appendRow() throws Exception {
    BigDecimal dZero = new BigDecimal(0);
    String strDef = "";
    ArrayList oneRow = new ArrayList(_arrColumnName.length);
    for (int i = 0; i < _arrColumnName.length; i++) {
      if (isDecimalType(this._arrColumnType[i])) {
        oneRow.add(dZero);
      } else {
        oneRow.add(strDef);
      }
    }
    this._arrData.add(oneRow);

    //清除过滤器
    resetFilter();

    return _arrData.size() - 1;
  }

  /**
   * 插入一个空行
   * 
   * 柳敬峰增加
   * 
   * @return
   * @throws Exception
   */
  public int insertEmptyRow(int index) throws Exception {
    BigDecimal dZero = new BigDecimal(0);
    String strDef = "";
    ArrayList oneRow = new ArrayList(_arrColumnName.length);
    for (int i = 0; i < _arrColumnName.length; i++) {
      if (isDecimalType(this._arrColumnType[i])) {
        oneRow.add(dZero);
      } else {
        oneRow.add(strDef);
      }
    }
    this._arrData.add(index, oneRow);

    //清除过滤器
    resetFilter();

    return _arrData.size() - 1;
  }

  //将其他Cds中一行数据，引用添加本Cds
  public void appendRow(ArrayList oneRow) throws Exception {
    this._arrData.add(oneRow);

    //清除过滤器
    resetFilter();
  }

  //插入一行数据
  public void insertRow(ArrayList oneRow, int iRow) throws Exception {
    // add by wuzg 2014.9.16  
    if (null == oneRow) {
      BigDecimal dZero = new BigDecimal(0);
      String strDef = "";
      oneRow = new ArrayList(_arrColumnName.length);
      for (int i = 0; i < _arrColumnName.length; i++) {
        if (isDecimalType(this._arrColumnType[i])) {
          oneRow.add(dZero);
        } else {
          oneRow.add(strDef);
        }
      }
    }
    this._arrData.add(iRow, oneRow);

    //清除过滤器
    resetFilter();
  }

  public void setValue(ArrayList oneRow, String strFieldName, Object oneValue) {
    Integer iField = (Integer) this._hmFieldName.get(strFieldName);
    oneRow.set(iField.intValue(), oneValue);
    
    resetFilter(strFieldName); // add by wuzg 2016.3.11
  }

  public void setValue(int iRow, String strFieldName, Object oneValue) {
    Integer iField = (Integer) this._hmFieldName.get(strFieldName);
    // add by zhaoyang 2014.6.25
    if (null == iField || iField < 0) {
      return;
    }
    ArrayList oneRow = getOneRow(iRow);
    setValue(oneRow, strFieldName, oneValue);
  }

  public Object getValue(ArrayList oneRow, String strFieldName) {
    Integer iField = (Integer) this._hmFieldName.get(strFieldName);
    // add by wuzg 2014.623
    if (null == iField || iField < 0) {
      return "";
    }
    return oneRow.get(iField.intValue());
  }

  public Object getValue(int iRow, String strFieldName) {
    ArrayList oneRow = getOneRow(iRow);
    if (null == oneRow) return "";
    return getValue(oneRow, strFieldName);
  }

  public Object getValue(int iRow, int iCol) {
    ArrayList oneRow = getOneRow(iRow);
    return oneRow.get(iCol);
  }

  /**
   * @param strFieldName 列名
   * @return 该列的所有值
   * @throws Exception
   */
  public List getColumnValue(String strFieldName) throws Exception {

    Integer iField = (Integer) this._hmFieldName.get(strFieldName);
    if (null == iField || iField < 0) {
      throw new Exception("数据集中没有此字段：" + strFieldName);
    }

    List allData = this.getAllData();
    List oneColumnValues = new ArrayList();
    for (int i = 0; i < allData.size(); i++) {
      oneColumnValues.add(this.getValue(i, strFieldName));
    }
    return oneColumnValues;
  }

  /**
   * @param strFieldNames 要查询的所有列
   * @return 这些列对应的值，Map<列名, 值> 
   * @throws Exception
   */
  public Map<String, List> getColumnsValue(String strFieldNames[]) throws Exception {
    Map<String, List> map = new HashMap<String, List>();
    for (String field : strFieldNames) {
      map.put(field, this.getColumnValue(field));
    }
    return map;
  }

  public int getIntValue(ArrayList oneRow, String strFieldName) {
    //柳敬峰增加开始。2014.06.12
    if (_hmFieldName.containsKey(strFieldName) == false) {
      //System.out.println("数据集中没有此字段：" + strFieldName);
      return -1;
    }
    //柳敬峰增加结束

    Integer iField = (Integer) this._hmFieldName.get(strFieldName);
    Object objValue = oneRow.get(iField.intValue());
    if (null == objValue) {
      return 0;
    } else if (objValue instanceof BigDecimal) {
      return ((BigDecimal) objValue).intValue();
    } else if (objValue instanceof Integer) {
      return ((Integer) objValue).intValue();
    } else if( objValue instanceof Double){//add by zlj 20150603
    	return ((Double)objValue).intValue();
    }else {
      return Integer.valueOf((String) objValue).intValue();
    }
  }

  public int getIntValue(int iRow, String strFieldName) {
    ArrayList oneRow = getOneRow(iRow);
    return getIntValue(oneRow, strFieldName);
  }

  public String getStringValue(ArrayList oneRow, String strFieldName) {
	  if(oneRow==null || oneRow.size()==0){
		  return null;
	  }
    //柳敬峰增加开始。2014.06.12
    if (_hmFieldName.containsKey(strFieldName) == false) {
      //System.out.println("数据集中没有此字段：" + strFieldName);
      return "";
    }
    //柳敬峰增加结束

    Integer iField = (Integer) this._hmFieldName.get(strFieldName);
    Object objValue = oneRow.get(iField.intValue());
    if (null == objValue) {
      return "";
    } else if (objValue instanceof String) {
      return (String) objValue;
    } else {
      return objValue.toString();
    }
  }

  public String getStringValue(int iRow, String strFieldName) {
    ArrayList oneRow = getOneRow(iRow);
    return getStringValue(oneRow, strFieldName);
  }

  public BigDecimal getBigDecimalValue(ArrayList oneRow, String strFieldName) {
    Integer iField = (Integer) this._hmFieldName.get(strFieldName);
    Object objValue = oneRow.get(iField.intValue());
    if (null == objValue || objValue.toString().length()==0) {//mod by zlj 20150603
      return new BigDecimal("0.00");
    } else if (objValue instanceof BigDecimal) {
      return (BigDecimal) objValue;
    } else if (objValue instanceof Integer) {
      return new BigDecimal(((Integer) objValue).intValue());
    } else if (objValue instanceof Double){//add by zlj 
    	return new BigDecimal((Double)objValue);
    }else {
      return new BigDecimal((String) objValue);
    }
  }

  public BigDecimal getBigDecimalValue(int iRow, String strFieldName) {
    ArrayList oneRow = getOneRow(iRow);
    return getBigDecimalValue(oneRow, strFieldName);
  }

  //返回字段的布尔值
  public boolean getBoolValue(int row, String fieldName) {
    Object obj = this.getValue(row, fieldName);
    if (null == obj) {
      return false;
    } else if (obj instanceof Boolean) {
      return ((Boolean) obj).booleanValue();
    } else {
      String str = this.getValue(row, fieldName).toString();
      return str.equals("1") || str.toLowerCase() == "true";
    }
  }

  private boolean isDecimalType(int intType) {
    switch (intType) {
    case java.sql.Types.DECIMAL: //小数数据
    case java.sql.Types.DOUBLE:
    case java.sql.Types.FLOAT:
    case java.sql.Types.NUMERIC:
    case java.sql.Types.REAL:
    case java.sql.Types.BIGINT: //整形数据
    case java.sql.Types.INTEGER:
    case java.sql.Types.SMALLINT:
    case java.sql.Types.TINYINT:
      return true;
    default:
      return false;
    }
  }

  private boolean isCharType(int intType) {
    switch (intType) {
    case java.sql.Types.CHAR: //字符数据
    case java.sql.Types.LONGVARCHAR:
    case java.sql.Types.VARCHAR:
      return true;
    default:
      return false;
    }
  }

  private Object getColumnValue(ResultSet rs, int intColumn, int intFieldType) throws Exception {
    String s = "";
    switch (intFieldType) {
    case java.sql.Types.BIGINT: //整形数据
    case java.sql.Types.INTEGER:
    case java.sql.Types.SMALLINT:
    case java.sql.Types.TINYINT:
      BigDecimal d1 = rs.getBigDecimal(intColumn);
      if (null == d1)
        return new BigDecimal(0);
      else
        return d1;

    case java.sql.Types.DATE: //日期数据
    case java.sql.Types.TIME:
    case java.sql.Types.TIMESTAMP:
      Timestamp t = rs.getTimestamp(intColumn);
      if (t != null) {
        //年
        StringBuffer strBufTime = new StringBuffer("" + (1900 + t.getYear()));
        //月
        String strMonth = "00" + (1 + t.getMonth());
        strBufTime.append("-").append(strMonth.substring(strMonth.length() - 2));
        //日
        String strDay = "00" + t.getDate();
        strBufTime.append("-").append(strDay.substring(strDay.length() - 2));
        //时
        String strHour = "00" + t.getHours();
        strBufTime.append(" ").append(strHour.substring(strHour.length() - 2)).append(":");
        //分
        String strMinus = "00" + t.getMinutes();
        strBufTime.append(strMinus.substring(strMinus.length() - 2)).append(":");
        //秒
        String strSecond = "00" + t.getSeconds();
        strBufTime.append(strSecond.substring(strSecond.length() - 2));

        s = strBufTime.toString();
      }

      return s;

    case java.sql.Types.DECIMAL: //小数数据
    case java.sql.Types.DOUBLE:
    case java.sql.Types.FLOAT:
    case java.sql.Types.NUMERIC:
    case java.sql.Types.REAL:
      BigDecimal d2 = rs.getBigDecimal(intColumn);
      if (null == d2)
        return new BigDecimal(0);
      else
        return d2;

    case java.sql.Types.CLOB:
      /*java.sql.Clob clob=rs.getClob(intColumn);
      if(clob != null){
      	s = clob.getSubString((long)1,(int)clob.length());
      }
      return s;*/

      Reader r = rs.getCharacterStream(intColumn);
      if (r == null) {
        return "";
      }
      StringBuffer sBuf = new StringBuffer(256);
      char[] c = new char[256];
      while (true) {
        int iCount = r.read(c);
        if (iCount <= 0) {
          break;
        } else if (iCount == 256) {
          sBuf.append(c);
        } else if (iCount < 256) {
          sBuf.append(c, 0, iCount);
        }
      }
      return sBuf.toString();
    case java.sql.Types.LONGVARBINARY:
      return s;

    default: //字符数据
      s = PubFunc.nullToEmpty(rs.getString(intColumn));
      if (s == null)
        return "";
      else
        return s;
    }
  }

  public boolean hasLargeValue() {
    if (null == this._arrData || _arrData.size() <= 0) {
      return false;
    }

    for (int iCol = 0; iCol < this._arrColumnName.length; iCol++) {
      if (this.isCharType(this._arrColumnType[iCol]) && this._arrColumnLength[iCol] >= 1800) {
        for (int iRow = 0; iRow < _arrData.size(); iRow++) {
          ArrayList aRow = (ArrayList) _arrData.get(iRow);
          String value = (String) aRow.get(iCol);
          if (false == PubFunc.isEmptyString(value) && value.length() > 900) {
            return true;
          }
        }
      }
    }

    return false;
  }

  public int getTotalRowCount() {
    return _iTotalRowCount;
  }

  public void setTotalRowCount(int iTotalRowCount) {
    _iTotalRowCount = iTotalRowCount;
  }

  //筛选重名重号的数据，其他数据删除
  //arrDimField 重名重号依据字段ID
  public void filterDupData(String[] arrDupField) throws Exception {
    if (this._arrData.size() <= 0) {
      return;
    }

    //按关键字计数
    int[] iKeyDataType = new int[arrDupField.length];
    for (int i = 0; i < arrDupField.length; i++) {
      String strFieldName = arrDupField[i];
      int fieldNo = ((Integer) _hmFieldName.get(strFieldName)).intValue();
      iKeyDataType[i] = PubFunc.sql2JavaType(this._arrColumnType[fieldNo]);
    }

    HashMap hmKey = new HashMap(); //依据字段-ArrayList<行号>
    for (int iRow = 0; iRow < this._arrData.size(); iRow++) {
      //key值
      Object[] objKey = new Object[arrDupField.length];
      for (int i = 0; i < arrDupField.length; i++) {
        String strFieldName = arrDupField[i];
        objKey[i] = this.getValue(iRow, strFieldName);
      }

      ObjectKey key = new ObjectKey(objKey, iKeyDataType);
      ArrayList arrRows = null;
      if (hmKey.containsKey(key)) {
        arrRows = (ArrayList) hmKey.get(key);
      } else {
        arrRows = new ArrayList();
        hmKey.put(key, arrRows);
      }

      arrRows.add(new Integer(iRow));
    }

    //重名重号的行号
    HashSet hsDupRow = new HashSet();
    for (Iterator iter = hmKey.keySet().iterator(); iter.hasNext();) {
      ObjectKey key = (ObjectKey) iter.next();
      ArrayList arrRows = (ArrayList) hmKey.get(key);
      if (arrRows.size() > 1) {
        for (int i = 0; i < arrRows.size(); i++) {
          hsDupRow.add(arrRows.get(i));
        }
      }
    }

    //删除非重名重号的
    for (int iRow = (_arrData.size() - 1); iRow >= 0; iRow--) {
      if (false == hsDupRow.contains(new Integer(iRow))) {
        _arrData.remove(iRow);
        resetFilter(); // add by wuzg 2016.3.11
      }
    }
  }

  //生成同比、基比、环比数据
  //arrSumGroupFieldSect 维度分段
  //strPerdFieldName期间字段
  //iPerdType 期间类型
  //strValueFieldName数据字段
  //iContrType对比类型：
  //10=上年同期数、11=同比增减数、12=同比增减百分比、
  //20=基期数、    21=基比增减数、22=基比增减百分比、
  //30=上期数、    31=环比增减数、32=环比增减百分比
  public String createContrData(ArrayList arrDimField, String strPerdFieldName, int iPerdType, String strDataFieldName, int iContrType)
    throws Exception {
    if (this._arrData.size() <= 0) {
      return "";
    }

    int fieldNo = ((Integer) _hmFieldName.get(strDataFieldName)).intValue();
    String strContrFieldName = strDataFieldName + "_" + iContrType; //新字段，对比数据

    //补充对比字段
    if (false == this._hmFieldName.containsKey(strContrFieldName)) {
      if (iContrType == 12 || iContrType == 22 || iContrType == 32) {
        this.appendField(strContrFieldName, java.sql.Types.DECIMAL, 20, 4, new BigDecimal(0));
      } else {
        this.appendField(strContrFieldName, java.sql.Types.DECIMAL, this._arrColumnLength[fieldNo], this._arrColumnScale[fieldNo], new BigDecimal(0));
      }
    }

    //生成比较数据
    int[] iKeyDataType = new int[arrDimField.size()];
    for (int i = 0; i < arrDimField.size(); i++) {
      String strFieldName = (String) arrDimField.get(i);
      int dimFieldNo = ((Integer) _hmFieldName.get(strFieldName)).intValue();
      iKeyDataType[i] = PubFunc.sql2JavaType(this._arrColumnType[dimFieldNo]);
    }

    //行号,
    HashMap hmKey = new HashMap(); //维度Key-行号
    String minPerd = null; //最小期间
    for (int iRow = 0; iRow < this._arrData.size(); iRow++) {
      //key值
      Object[] objKey = new Object[arrDimField.size()];
      for (int i = 0; i < arrDimField.size(); i++) {
        String strFieldName = (String) arrDimField.get(i);

        if (strPerdFieldName.equals(strFieldName)) {
          String strPerdValue = getStringValue(iRow, strFieldName);
          if (null == minPerd || minPerd.compareTo(strPerdValue) > 0) {
            minPerd = strPerdValue;
          }
          objKey[i] = strPerdValue;
        } else {
          objKey[i] = this.getValue(iRow, strFieldName);
        }
      }

      ObjectKey key = new ObjectKey(objKey, iKeyDataType);
      hmKey.put(key, new Integer(iRow));
    }

    if (PubFunc.isEmptyString(minPerd)) {
      return "";
    }

    //计算
    BigDecimal dZero = new BigDecimal(0);
    for (int iRow = 0; iRow < this._arrData.size(); iRow++) {
      //空期间不处理
      String strPerd = getStringValue(iRow, strPerdFieldName);
      if (PubFunc.isEmptyString(strPerd)) {
        continue;
      }
      String strLastPerd = getLastPerd(minPerd, strPerd, iPerdType, iContrType);
      if (PubFunc.isEmptyString(strLastPerd)) {
        continue;
      }

      //对应上期间key值
      Object[] objLastPerdKey = new Object[arrDimField.size()];
      for (int i = 0; i < arrDimField.size(); i++) {
        String strFieldName = (String) arrDimField.get(i);
        if (strPerdFieldName.equals(strFieldName)) {
          objLastPerdKey[i] = strLastPerd;
        } else {
          objLastPerdKey[i] = this.getValue(iRow, strFieldName);
        }
      }

      //计算
      ObjectKey lastPerdKey = new ObjectKey(objLastPerdKey, iKeyDataType);
      if (hmKey.containsKey(lastPerdKey)) {
        int iLastPerdRow = ((Integer) hmKey.get(lastPerdKey)).intValue();
        BigDecimal dCur = null;
        BigDecimal dLastPerd = this.getBigDecimalValue(iLastPerdRow, strDataFieldName);
        switch (iContrType) {
        case 10: //上期值
        case 20:
        case 30:
          this.setValue(iRow, strContrFieldName, dLastPerd);
          break;
        case 11: //增减值
        case 21:
        case 31:
          dCur = this.getBigDecimalValue(iRow, strDataFieldName);
          this.setValue(iRow, strContrFieldName, dCur.subtract(dLastPerd));
          break;
        case 12: //增减百分比%
        case 22:
        case 32:
          //上期为0，不处理，比例=0
          if (false == (null == dLastPerd || dLastPerd.compareTo(dZero) == 0)) {
            dCur = this.getBigDecimalValue(iRow, strDataFieldName);
            this.setValue(iRow, strContrFieldName, (dCur.subtract(dLastPerd)).divide(dLastPerd, 4, BigDecimal.ROUND_HALF_UP));
          } else {
            this.setValue(iRow, strContrFieldName, dZero);
          }
          break;
        }
      }
    }

    return minPerd;
  }

  //获得上期期间码
  private String getLastPerd(String minPerd, String strPerd, int iPerdType, int iContrType) {
    String strLastPerd = null;

    switch (iContrType) {
    case 10: //上年同期
    case 11:
    case 12:
      if (strPerd.length() > 4) {
        String strYear = strPerd.substring(0, 4);
        if (strYear.compareTo(minPerd) > 0) {
          strLastPerd = ((new Integer(strYear)).intValue() - 1) + strPerd.substring(4, strPerd.length());
        }
      } else if (strPerd.length() == 4) {
        String strYear = strPerd.substring(0, 4);
        strLastPerd = ((new Integer(strYear)).intValue() - 1) + "";
      }
      break;

    case 20: //基期=最小期间
    case 21:
    case 22:
      strLastPerd = minPerd;
      break;

    case 30: //上期
    case 31:
    case 32:
      switch (iPerdType) {
      case PubConst.iDATE_YEAR:
        if (strPerd.length() >= 4) {
          String strYear = strPerd.substring(0, 4);
          strLastPerd = ((new Integer(strYear)).intValue() - 1) + "";
        }
        break;

      case PubConst.iDATE_HALF_YEAR:
        if (strPerd.length() >= 7) {
          String strYear = strPerd.substring(0, 4);
          String strHalfYear = strPerd.substring(5, 7);
          if (strHalfYear.equals("01")) {
            strLastPerd = ((new Integer(strYear)).intValue() - 1) + "-02";
          } else {
            strLastPerd = strYear + "-01";
          }
        } else if (strPerd.length() >= 4) {
          String strYear = strPerd.substring(0, 4);
          strLastPerd = ((new Integer(strYear)).intValue() - 1) + "";
        }
        break;

      case PubConst.iDATE_SEASON:
        if (strPerd.length() > 4) {
          String strYear = strPerd.substring(0, 4);
          int iSeason = (new Integer(strPerd.substring(5, 7))).intValue();
          if (iSeason <= 1) {
            strLastPerd = ((new Integer(strYear)).intValue() - 1) + "-04";
          } else {
            strLastPerd = strYear + "-0" + (iSeason - 1);
          }
        } else {
          strLastPerd = ((new Integer(strPerd)).intValue() - 1) + "";
        }
        break;

      case PubConst.iDATE_MONTH:
        if (strPerd.length() > 4) {
          String strYear = strPerd.substring(0, 4);
          int iMonth = (new Integer(strPerd.substring(5, 7))).intValue();
          if (iMonth <= 1) {
            strLastPerd = ((new Integer(strYear)).intValue() - 1) + "-12";
          } else if (iMonth > 10) {
            strLastPerd = strYear + "-" + (iMonth - 1);
          } else {
            strLastPerd = strYear + "-0" + (iMonth - 1);
          }
        } else {
          strLastPerd = ((new Integer(strPerd)).intValue() - 1) + "";
        }
        break;

      case PubConst.iDATE_DAY:
        if (strPerd.length() > 4) {

        } else if (strPerd.length() > 4) {
          String strYear = strPerd.substring(0, 4);
          int iMonth = (new Integer(strPerd.substring(5, 7))).intValue();
          if (iMonth <= 1) {
            strLastPerd = ((new Integer(strYear)).intValue() - 1) + "-12";
          } else if (iMonth > 10) {
            strLastPerd = strYear + "-" + (iMonth - 1);
          } else {
            strLastPerd = strYear + "-0" + (iMonth - 1);
          }
        } else {
          strLastPerd = ((new Integer(strPerd)).intValue() - 1) + "";
        }
        break;
      }
      break;
    }

    return strLastPerd;
  }

  //生成统计分组汇总数据
  //arrGroupField = group by的字段
  //arrSumField = 聚合字段
  //arrFuncType = 聚合函数类型。类型＝按公式计算的，没有处理
  //arrDataType = 聚合字段的数据类型
  //strMask = 当前汇总的MASK值
  //bFromDetailData = false，表示从统计数据产生汇总数据，对计数、平均值、占比，需要从_SUM、_COUNT等进行计算
  public Cds createGroupCds(ArrayList arrGroupField, ArrayList arrSumField, ArrayList arrFuncType, ArrayList arrDataType, String strMask,
    boolean bFromDetailData) throws Exception {
    Cds cdsSum = null;

    if (bFromDetailData) {
      cdsSum = createGroupCdsFromDetail(arrGroupField, arrSumField, arrFuncType, arrDataType);
    } else {
      cdsSum = createGroupCdsFromSum(arrGroupField, arrSumField, arrFuncType, arrDataType);
    }

    //掩码
    if (false == PubFunc.isEmptyString(strMask)) {
      if (false == cdsSum._hmFieldName.containsKey(PubConst._CUBE_MASK)) {
        cdsSum.appendField(PubConst._CUBE_MASK, java.sql.Types.CHAR, 60, 0, strMask);
      } else {
        for (int iRow = 0; iRow < cdsSum.size(); iRow++) {
          cdsSum.setValue(iRow, PubConst._CUBE_MASK, strMask);
        }
      }
    }

    return cdsSum;
  }

  //从明细数据中汇总
  private Cds createGroupCdsFromDetail(ArrayList arrGroupField, ArrayList arrSumField, ArrayList arrFuncType, ArrayList arrDataType) throws Exception {
    //汇总字段数据类型
    int[] iValueDataType = new int[arrSumField.size()];
    String[] strFuncType = new String[arrSumField.size()];
    for (int i = 0; i < arrSumField.size(); i++) {
      strFuncType[i] = (String) arrFuncType.get(i);
      String strFieldName = (String) arrSumField.get(i);
      int fieldNo = ((Integer) _hmFieldName.get(strFieldName)).intValue();
      iValueDataType[i] = PubFunc.sql2JavaType(this._arrColumnType[fieldNo]);
    }

    //比较数据库类型与定义类型是否一致
    //平均值：添加字段：***_SUM  、 ***_COUNT
    //占比：添加字段：***_SUM
    int[] iDefDataType = new int[arrSumField.size()];
    for (int i = 0; i < arrSumField.size(); i++) {
      iDefDataType[i] = PubFunc.uq2JavaType((String) arrDataType.get(i));
    }

    Cds cdsSum = this.cloneCds_NoData();
    TreeMap key_Value = new TreeMap();
    BigDecimal dDef = new BigDecimal(0);
    for (int i = 0; i < arrSumField.size(); i++) {
      String strFieldName = (String) arrSumField.get(i);

      int iFieldNo = ((Integer) cdsSum._hmFieldName.get(strFieldName)).intValue();
      //求平均：增加字段_SUM  _COUNT
      if (PubFunc.isEqualsString(strFuncType[i], PubConst.FUNC_TYPE_AVG)) { //平均数
        if (false == cdsSum._hmFieldName.containsKey(strFieldName + "_SUM"))
          cdsSum
            .appendField(strFieldName + "_SUM", java.sql.Types.DECIMAL, cdsSum._arrColumnLength[iFieldNo], cdsSum._arrColumnScale[iFieldNo], dDef);
        if (false == cdsSum._hmFieldName.containsKey(strFieldName + "_COUNT"))
          cdsSum.appendField(strFieldName + "_COUNT", java.sql.Types.DECIMAL, cdsSum._arrColumnLength[iFieldNo], cdsSum._arrColumnScale[iFieldNo],
            dDef);
      } else //计数，数据类型改为数值型
      if (PubFunc.isEqualsString(strFuncType[i], PubConst.FUNC_TYPE_COUNT)) { //计数
        cdsSum._arrColumnType[iFieldNo] = java.sql.Types.DECIMAL;
        cdsSum._arrColumnLength[iFieldNo] = 20;
        cdsSum._arrColumnScale[iFieldNo] = 0;
      }
    }

    //计算、汇总
    calc(key_Value, arrGroupField, arrSumField, strFuncType, iValueDataType);

    //添加记录行，到汇总Cds中
    for (Iterator iter = key_Value.keySet().iterator(); iter.hasNext();) {
      int iRow = cdsSum.appendRow();

      ObjectKey key = (ObjectKey) iter.next();
      Object[] values = (Object[]) key_Value.get(key);

      //Group By字段
      for (int i = 0; i < arrGroupField.size(); i++) {
        String strFieldName = (String) arrGroupField.get(i);
        cdsSum.setValue(iRow, strFieldName, key._objValue[i]);
      }

      //聚合字段数据
      for (int i = 0; i < arrSumField.size(); i++) {
        String strFieldName = (String) arrSumField.get(i);

        if (PubFunc.isEmptyString(strFuncType[i])) {
          //不处理
        } else if (PubConst.FUNC_TYPE_MAX.equals(strFuncType[i]) || PubConst.FUNC_TYPE_MIN.equals(strFuncType[i])
          || PubConst.FUNC_TYPE_COUNT.equals(strFuncType[i]) || PubConst.FUNC_TYPE_SUM.equals(strFuncType[i])) { //最大值、最小值、计数、求和
          cdsSum.setValue(iRow, strFieldName, values[i]);
        } else if (PubConst.FUNC_TYPE_AVG.equals(strFuncType[i])) { //平均值
          AvgObject d = (AvgObject) values[i];
          if (d.iCount > 0) {
            cdsSum.setValue(iRow, strFieldName, d.dData.divide(new BigDecimal(d.iCount), d.dData.scale(), BigDecimal.ROUND_HALF_UP));
          }

          cdsSum.setValue(iRow, strFieldName + "_SUM", d.dData);
          cdsSum.setValue(iRow, strFieldName + "_COUNT", new BigDecimal(d.iCount));
        } else if (PubConst.FUNC_TYPE_MEDIAN.equals(strFuncType[i])) { //中位数
          TreeSet tr = (TreeSet) values[i];
          int iTotalCount = tr.size();
          if (iTotalCount > 0) {
            BigDecimal[] obj = (BigDecimal[]) tr.toArray();
            int iNum = iTotalCount / 2;
            if ((iTotalCount % 2) == 0) { //偶数个，取中间两个的平均值
              BigDecimal obj1 = obj[iNum];
              BigDecimal obj2 = obj[iNum + 1];
              cdsSum.setValue(iRow, strFieldName, (obj1.add(obj2)).divide(new BigDecimal(2), obj1.scale(), BigDecimal.ROUND_HALF_UP));
            } else {
              cdsSum.setValue(iRow, strFieldName, obj[iNum]);
            }
          }
        }
      }
    }

    return cdsSum;
  }

  //从统计数据中再汇总:
  //对计数:改为求和
  //平均值:需要从_SUM、_COUNT等进行计算
  //占比：对_SUM字段求和
  private Cds createGroupCdsFromSum(ArrayList arrGroupField, ArrayList arrSumFieldOri, ArrayList arrFuncTypeOri, ArrayList arrDataTypeOri)
    throws Exception {
    //比较数据库类型与定义类型是否一致
    //计数：改为求sum
    //计算平均值：***_SUM  、 ***_COUNT
    //占比的情况:仅计算***_SUM
    //汇总字段数据类型
    Cds cdsSum = this.cloneCds_NoData();
    TreeMap key_Value = new TreeMap();
    BigDecimal dDef = new BigDecimal(0);
    ArrayList arrSumField = new ArrayList();
    ArrayList arrFuncType = new ArrayList();
    ArrayList arrDataType = new ArrayList();

    for (int iOri = 0; iOri < arrSumFieldOri.size(); iOri++) {
      String strFieldName = (String) arrSumFieldOri.get(iOri);
      String strFuncType = (String) arrFuncTypeOri.get(iOri);

      arrSumField.add(arrSumFieldOri.get(iOri));
      arrFuncType.add(arrFuncTypeOri.get(iOri));
      arrDataType.add(arrDataTypeOri.get(iOri));

      int iNew = arrSumField.size() - 1;

      //求平均：增加字段_SUM  _COUNT
      if (PubFunc.isEqualsString(strFuncType, PubConst.FUNC_TYPE_AVG)) { //平均数
        arrFuncType.set(iNew, PubConst.FUNC_TYPE_AVG_FROM_SUM_DATA); //第一步：计算_SUM、_COUNT字段，第二步：计算平均值

        arrSumField.add(strFieldName + "_SUM"); //添加_SUM汇总字段
        arrFuncType.add(PubConst.FUNC_TYPE_SUM);
        arrDataType.add(PubConst.DATATYPE_STR_DECIMAL);

        arrSumField.add(strFieldName + "_COUNT"); //添加_COUNT汇总字段
        arrFuncType.add(PubConst.FUNC_TYPE_SUM);
        arrDataType.add(PubConst.DATATYPE_STR_DECIMAL);
      } else //计数，改为汇总
      if (PubFunc.isEqualsString(strFuncType, PubConst.FUNC_TYPE_COUNT)) { //计数
        arrFuncType.set(iNew, PubConst.FUNC_TYPE_SUM); //改为汇总
      }
    }

    int[] iValueDataType = new int[arrSumField.size()];
    String[] strFuncType = new String[arrSumField.size()];
    for (int i = 0; i < arrSumField.size(); i++) {
      strFuncType[i] = (String) arrFuncType.get(i);
      String strFieldName = (String) arrSumField.get(i);
      int fieldNo = ((Integer) _hmFieldName.get(strFieldName)).intValue();
      iValueDataType[i] = PubFunc.sql2JavaType(this._arrColumnType[fieldNo]);
    }

    //计算、汇总
    calc(key_Value, arrGroupField, arrSumField, strFuncType, iValueDataType);

    //添加记录行，到汇总Cds中
    for (Iterator iter = key_Value.keySet().iterator(); iter.hasNext();) {
      int iRow = cdsSum.appendRow();

      ObjectKey key = (ObjectKey) iter.next();
      Object[] values = (Object[]) key_Value.get(key);

      //Group By字段
      for (int i = 0; i < arrGroupField.size(); i++) {
        String strFieldName = (String) arrGroupField.get(i);
        cdsSum.setValue(iRow, strFieldName, key._objValue[i]);
      }

      //聚合字段数据
      for (int i = 0; i < arrSumField.size(); i++) {
        String strFieldName = (String) arrSumField.get(i);

        if (PubFunc.isEmptyString(strFuncType[i])) {
          //不处理
        } else if (PubConst.FUNC_TYPE_MAX.equals(strFuncType[i]) || PubConst.FUNC_TYPE_MIN.equals(strFuncType[i])
          || PubConst.FUNC_TYPE_COUNT.equals(strFuncType[i]) || PubConst.FUNC_TYPE_SUM.equals(strFuncType[i])) { //最大值、最小值、计数、求和
          cdsSum.setValue(iRow, strFieldName, values[i]);
        } else if (PubConst.FUNC_TYPE_AVG.equals(strFuncType[i])) { //平均值
          AvgObject d = (AvgObject) values[i];
          if (d.iCount > 0) {
            cdsSum.setValue(iRow, strFieldName, d.dData.divide(new BigDecimal(d.iCount), d.dData.scale(), BigDecimal.ROUND_HALF_UP));
          }

          cdsSum.setValue(iRow, strFieldName + "_SUM", d.dData);
          cdsSum.setValue(iRow, strFieldName + "_COUNT", new BigDecimal(d.iCount));
        } else if (PubConst.FUNC_TYPE_MEDIAN.equals(strFuncType[i])) { //中位数
          TreeSet tr = (TreeSet) values[i];
          int iTotalCount = tr.size();
          if (iTotalCount > 0) {
            BigDecimal[] obj = (BigDecimal[]) tr.toArray();
            int iNum = iTotalCount / 2;
            if ((iTotalCount % 2) == 0) { //偶数个，取中间两个的平均值
              BigDecimal obj1 = obj[iNum];
              BigDecimal obj2 = obj[iNum + 1];
              cdsSum.setValue(iRow, strFieldName, (obj1.add(obj2)).divide(new BigDecimal(2), obj1.scale(), BigDecimal.ROUND_HALF_UP));
            } else {
              cdsSum.setValue(iRow, strFieldName, obj[iNum]);
            }
          }
        }
      }
    }

    //处理平均值
    for (int i = 0; i < strFuncType.length; i++) {
      if (PubFunc.isEqualsString(strFuncType[i], PubConst.FUNC_TYPE_AVG_FROM_SUM_DATA)) { //平均数
        String strFieldName = (String) arrSumField.get(i);
        String strFieldName_SUM = strFieldName + "_SUM";
        String strFieldName_COUNT = strFieldName + "_COUNT";
        for (int iRow = 0; iRow < cdsSum.size(); iRow++) {
          BigDecimal dSum = cdsSum.getBigDecimalValue(iRow, strFieldName_SUM);
          BigDecimal dCount = cdsSum.getBigDecimalValue(iRow, strFieldName_COUNT);
          if (dCount.intValue() != 0) {
            cdsSum.setValue(iRow, strFieldName, dSum.divide(dCount, dSum.scale(), BigDecimal.ROUND_HALF_UP));
          }
        }
      }
    }

    return cdsSum;
  }

  //计算汇总
  private void calc(TreeMap key_Value, ArrayList arrGroupField, ArrayList arrSumField, String[] strFuncType, int[] iValueDataType) throws Exception {
    //group by字段的数据类型
    int[] iKeyDataType = new int[arrGroupField.size()];
    for (int i = 0; i < arrGroupField.size(); i++) {
      String strFieldName = (String) arrGroupField.get(i);
      int fieldNo = ((Integer) _hmFieldName.get(strFieldName)).intValue();
      iKeyDataType[i] = PubFunc.sql2JavaType(this._arrColumnType[fieldNo]);
    }

    //计算数据
    for (int iRow = 0; iRow < this._arrData.size(); iRow++) {
      //key值
      Object[] objKey = new Object[arrGroupField.size()];
      for (int i = 0; i < arrGroupField.size(); i++) {
        String strFieldName = (String) arrGroupField.get(i);
        objKey[i] = this.getValue(iRow, strFieldName);
      }

      ObjectKey key = new ObjectKey(objKey, iKeyDataType);

      //数据值
      Object[] values = null;
      if (key_Value.containsKey(key)) {
        values = (Object[]) key_Value.get(key);
      } else {
        values = new Object[arrSumField.size()];
        key_Value.put(key, values);
      }

      //汇总计算
      for (int i = 0; i < arrSumField.size(); i++) {
        if (PubFunc.isEmptyString(strFuncType[i])) {
          //不处理
        } else if (PubConst.FUNC_TYPE_COUNT.equals(strFuncType[i])) { //计数
          BigDecimal d = (BigDecimal) values[i];
          if (null == d) {
            d = new BigDecimal(1);
          } else {
            d = d.add(new BigDecimal(1));
          }

          values[i] = d;
        } else {
          String strFieldName = (String) arrSumField.get(i);

          //字符类型
          if (iValueDataType[i] == PubConst.DATATYPE_CHAR) {
            String strNewValue = this.getStringValue(iRow, strFieldName);
            String strObj = (String) values[i];
            if (PubConst.FUNC_TYPE_MAX.equals(strFuncType[i])) { //最大值
              if (null == strObj) {
                strObj = strNewValue;
              } else {
                if (chineseCompare(strNewValue, strObj) > 0) {
                  strObj = strNewValue;
                }
              }
            } else if (PubConst.FUNC_TYPE_MIN.equals(strFuncType[i])) { //最小值
              if (null == strObj) {
                strObj = strNewValue;
              } else {
                if (chineseCompare(strNewValue, strObj) < 0) {
                  strObj = strNewValue;
                }
              }
            } else {
              //throw new Exception(strFieldName+"字符类型，只能进行求计数、最大值、最小值类型的汇总");
            }

            values[i] = strObj;
          } else {
            BigDecimal NewValue = null;
            Object obj = this.getValue(iRow, strFieldName);
            if (null == obj) {
              //
            } else if (obj instanceof BigDecimal) {
              NewValue = (BigDecimal) obj;
            } else {
              NewValue = new BigDecimal(obj.toString());
            }

            if (PubConst.FUNC_TYPE_MEDIAN.equals(strFuncType[i])) { //中位数
              TreeSet tr = (TreeSet) values[i];
              if (null == tr) {
                tr = new TreeSet();
              }

              if (null != NewValue) {
                tr.add(NewValue);
              }

              values[i] = tr;
            } else if (PubConst.FUNC_TYPE_AVG.equals(strFuncType[i])) { //平均数
              AvgObject d = (AvgObject) values[i];
              if (null == d) {
                d = new AvgObject();
              }

              if (null != NewValue) {
                if (null == d.dData) {
                  d.dData = NewValue;
                  d.iCount++;
                } else {
                  d.dData = d.dData.add(NewValue);
                  d.iCount++;
                }
              }

              values[i] = d;
            } else {
              BigDecimal d = (BigDecimal) values[i];
              if (PubConst.FUNC_TYPE_MAX.equals(strFuncType[i])) { //最大值
                if (null == d) {
                  d = NewValue;
                } else {
                  if (NewValue.compareTo(d) > 0) {
                    d = NewValue;
                  }
                }
              } else if (PubConst.FUNC_TYPE_MIN.equals(strFuncType[i])) { //最小值
                if (null == d) {
                  d = NewValue;
                } else {
                  if (NewValue.compareTo(d) < 0) {
                    d = NewValue;
                  }
                }
              } else if (PubConst.FUNC_TYPE_SUM.equals(strFuncType[i])) { //求和
                if (null == d) {
                  d = NewValue;
                } else {
                  if (null != NewValue) {
                    d = d.add(NewValue);
                  }
                }
              } else {
                //throw new Exception("只能进行求计数、求和、中位数、平均值、最大值、最小值类型的汇总");
              }

              values[i] = d;
            }
          }
        }
      }
    }
  }

  class AvgObject {
    BigDecimal dData = null;

    int iCount = 0;

    public AvgObject() {
    }
  }

  //分组字段比较
  class ObjectKey implements Comparable {
    private Object[] _objValue = null; //数据

    private int[] _iDataType = null; //数据类型

    private int _iHashCode = 0; //HashCode比较

    public ObjectKey(Object[] objValue, int[] iDataType) {
      _objValue = objValue;
      _iDataType = iDataType;

      if (null != _objValue && _objValue.length > 0) {
        StringBuffer strTemp = new StringBuffer();
        for (int i = 0; i < _objValue.length; i++) {
          strTemp.append(",;,~,^");
          if (null != _objValue[i]) {
            strTemp.append(_objValue[i].toString());
          }
        }

        _iHashCode = strTemp.toString().hashCode();
      }
    }

    public int hashCode() {
      return _iHashCode;
    }

    public boolean equals(Object o) {
      if (o == null)
        return false;

      ObjectKey oKey = (ObjectKey) o;
      if (null == _objValue) {
        if (null == oKey._objValue) {
          return true;
        } else {
          return false;
        }
      }
      if (null == oKey._objValue) {
        if (null == _objValue) {
          return true;
        } else {
          return false;
        }
      }
      if (_objValue.length == 0) {
        if (oKey._objValue.length == 0) {
          return true;
        } else {
          return false;
        }
      }
      if (oKey._objValue.length == 0) {
        if (_objValue.length == 0) {
          return true;
        } else {
          return false;
        }
      }

      if (this.compareTo(o) == 0) {
        return true;
      }

      return false;
    }

    public int compareTo(Object o) {
      ObjectKey oKey = (ObjectKey) o;
      for (int i = 0; i < _objValue.length; i++) {
        if (null == _objValue[i] && null == oKey._objValue[i]) {
          return 0;
        } else if (null != _objValue[i] && null == oKey._objValue[i]) {
          return 1;
        } else if (null == _objValue[i] && null != oKey._objValue[i]) {
          return -1;
        }

        boolean bHit = compareOtherValue(_iDataType[i], _objValue[i], PubConst.COMP_EQUAL, oKey._objValue[i]);
        if (bHit) {
          continue;
        } else {
          bHit = compareOtherValue(_iDataType[i], _objValue[i], PubConst.COMP_GREAT, oKey._objValue[i]);
          if (bHit) {
            return 1;
          } else {
            return -1;
          }
        }
      }

      return 0;
    }
  }

  public static boolean isEmptyString(Object o) {
    if (o != null) {
      int l = o.toString().trim().length();
      return l == 0;
    } else
      return true;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < getAllData().size(); i++) {
      Iterator<String> it = _hmFieldName.keySet().iterator();
      while (it.hasNext()) {
        String s = it.next();
        //只打印非空字段，容易查看错误！
        if (isEmptyString(getStringValue(i, s)))
          continue;
        if (getStringValue(i, s).equals("0"))
          continue;
        sb.append(s).append(" = ").append(getValue(i, s)).append(" ,");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
  
  public boolean isExistField(String fieldName) {
	  if (null == _listColumnName) { 
		  //_listColumnName = new ArrayList<String>();
		  _listColumnName = new ArrayList<String>(Arrays.asList(_arrColumnName));
	  }
	  return (_listColumnName.indexOf(fieldName)>-1);
  }
}
