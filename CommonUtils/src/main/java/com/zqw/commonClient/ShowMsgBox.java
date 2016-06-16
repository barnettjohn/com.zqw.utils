package com.zqw.commonClient
        ;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * <p>Description: 封装了常用的信息对话框</p>
 * <p>Copyright: Copyright 2012 ufgov, Inc.</p>
 * <p>Company: ufgov</p>
 * <p>创建时间: 2012-10</p>
 * @author LiWD
 * @version 1.0
 */
public final class ShowMsgBox {
  public static MsgBoxType Error       = MsgBoxType.error;
  public static MsgBoxType Information = MsgBoxType.information;
  public static MsgBoxType Warning     = MsgBoxType.warning;
  public static MsgBoxType Confirm     = MsgBoxType.confirm;
  public static MsgBoxType Question    = MsgBoxType.question;
  public static MsgBoxType Plain       = MsgBoxType.plain;
  public static MsgBoxType WarnAndQues = MsgBoxType.warnAndQues;

  //信息框类型
  public enum MsgBoxType {
    error,        //错误对话框
    information,  //提示信息对话框
    warning,      //警告对话框
    confirm,      //确认对话框(确定,取消)
    question,     //确认对话框(是,否)
    plain,        //无图标对话框
    warnAndQues,  //警告并确认对话框

  }

  public static Boolean show(MsgBoxType msgBoxType, String msg) {
    return show(null, msgBoxType, msg);
  }

  public static Boolean show(Component parentComponent, MsgBoxType msgBoxType, String msg) {
    switch (msgBoxType) {
      case error:
        JOptionPane.showMessageDialog(parentComponent, msg, "错误信息", JOptionPane.ERROR_MESSAGE);
        break;

      case information:
        JOptionPane.showMessageDialog(parentComponent, msg, "提示信息", JOptionPane.INFORMATION_MESSAGE);
        break;

      case warning:
        JOptionPane.showMessageDialog(parentComponent, msg, "警告", JOptionPane.WARNING_MESSAGE);
        break;

      case confirm:
        return (JOptionPane.showConfirmDialog(parentComponent, msg, "确认", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION);

      case question:
        return (JOptionPane.showConfirmDialog(parentComponent, msg, "确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);

      case plain:
        //无图标
        JOptionPane.showMessageDialog(parentComponent, msg, "信息", JOptionPane.PLAIN_MESSAGE);
        break;

      case warnAndQues:
        return (JOptionPane.showConfirmDialog(parentComponent, msg, "确认警告", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION);

      default:
        JOptionPane.showMessageDialog(parentComponent, msg, "信息", JOptionPane.PLAIN_MESSAGE);
        break;
    }
    return true;

  }

}
