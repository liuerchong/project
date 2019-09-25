package com.liu.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @Comments :
 * @Author : Chen Yanji
 * @Date : 2011-7-6 上午10:05:07
 * @Project : reporting
 * @Company : Vstsoft
 */
public class StringUtil extends StringUtils {

	public static String controlStr(String oldStr, int maxLenght) {
		String tempStr = oldStr;
		if (tempStr.length() > 0) {
			int len = tempStr.length();
			if (len > maxLenght) {
				tempStr = tempStr.substring(0, maxLenght);
				tempStr = tempStr + "...";
			}
		}
		return tempStr;
	}

	public static String getStringTrim(String obj) {
		if (obj == null || obj.trim().length() <= 0) {
			return "";
		} else {
			return obj.trim();
		}
	}

	public static boolean compareString(String p, String begin, String end) {
		if (p.compareTo(begin) >= 0) {
			if (p.compareTo(end) < 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	public static String getString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return String.valueOf(obj);
		}
	}

	public static char[] getFlag(String obj) {
		if (obj == null || obj.trim().length() <= 0) {
			return null;
		} else {
			String s = obj.substring(2, 6);
			char[] a = s.toCharArray();
			return a;
		}
	}

	public static java.sql.Date covDate(Object obj) {
		java.sql.Date d = null;
		if (obj == null) {
			return null;
		}
		try {
			d = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(
					StringUtil.getString(obj).replace('.', '-')).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return d;
	}

	public static java.sql.Date covDateToM(Object obj) {
		java.sql.Date d = null;
		if (obj == null) {
			return null;
		}
		try {
			d = new java.sql.Date(new SimpleDateFormat("yyyy-MM").parse(
					StringUtil.getString(obj).replace('.', '-')).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return d;
	}

	// 获取当前日期字符串
	public static String getNowDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		return format.format(new java.util.Date());
	}

	private NumberFormat currencyFormat = NumberFormat.getNumberInstance();

	/**
	 * 把BigDecimal 保留两位小数，如果传入值为null 或者为0 返回"0.00"
	 * 
	 * @param value
	 *            传入参数
	 * @return 格式化后的值
	 */
	public String formatBigDecimal(BigDecimal value) {
		String result = "0.00";
		if (value != null && value.doubleValue() != 0) {
			result = currencyFormat.format(value);
		}
		return result;
	}

	public static String formatBigDecimal2(BigDecimal value) {
		String result = "0.00";
		if (value != null && value.doubleValue() != 0) {
			result = NumberFormat.getNumberInstance().format(value);
		}
		return result;
	}

	// 格式金额 cuihj 2008.6.4
	public static String formatMoney(String value) {
		if (value == null || "".equals(value.trim()))
			return "0.00";
		DecimalFormat fmt = new DecimalFormat("###########0.00");
		return fmt.format(Double.parseDouble(value));
	}

	// 格式金额 cuihj 2008.6.4
	public static String formatMoneyBySplit(String value) {
		if (value == null || "".equals(value.trim()))
			return "0.00";
		DecimalFormat fmt = new DecimalFormat("##,###,###,###,##0.00");
		return fmt.format(Double.parseDouble(value));
	}

	/**
	 * 去除右边多余的空格。cuihj 2008.6.18
	 * 
	 * @param value
	 *            待去右边空格的字符串
	 * @return 去掉右边空格后的字符串
	 * @since 0.6
	 */
	public static String trimRight(String value) {
		String result = value;
		if (result == null)
			return result;
		char ch[] = result.toCharArray();
		int endIndex = -1;
		for (int i = ch.length - 1; i > -1; i--) {
			if (Character.isWhitespace(ch[i])) {
				endIndex = i;
			} else {
				break;
			}
		}
		if (endIndex != -1) {
			result = result.substring(0, endIndex);
		}
		return result;
	}

	// 判断是否为空或者null
	public static boolean isNullOrEmpty(Object str) {
		if (str == null || "".equals(str.toString())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 从左向右按字节截取字符串。<br>
	 * 如cutForByteLengthToRight(10, "1234567阿瑟肯德基", 'a')，返回“1234567阿a”
	 * 
	 * @param length
	 *            返回字符串的字节长度
	 * @param str
	 *            原始字符串
	 * @param pad
	 *            补位字符
	 * @return 截取后的字符串
	 */
	public static String cutForByteLengthToRight(int length, String str,
			char pad) {
		String result = "";
		if (isEmpty(str)) {
			for (int i = 0; i < length; i++) {
				result += pad;
			}
		} else {
			result = str.trim().replace(" ", "");
			int orienLength = result.getBytes().length;
			if (orienLength > length) {
				byte[] a = result.getBytes();
				byte[] b = new byte[length];
				for (int i = 0; i < length; i++) {
					b[i] = a[i];
				}
				int flag = 0;
				for (int i = 0; i < length; i++) {
					if (b[i] < (byte) 32)
						flag++;
				}
				if (flag % 2 == 1) {
					byte[] temp = new byte[length - 1];
					for (int i = 0; i < length - 1; i++)
						temp[i] = b[i];
					result = new String(temp) + pad;
				} else
					result = new String(b);
			} else if (orienLength < length) {
				for (int i = 0; i < length - orienLength; i++) {
					result += pad;
				}
			}
		}
		return result;
	}

	/**
	 * 根据传入的正则表达式校验字符串
	 * 
	 * @param contenct
	 *            校验内容
	 * @param regEx
	 *            正则表达式『例如: [^0-9] 非数字 』
	 * @return
	 */
	public static boolean validByRegEx(String contenct, String regEx) {
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(contenct);
		return m.find();
	}

	/**
	 * 去掉方法名称前的get
	 * 
	 * @param str
	 * @return
	 */
	public static String discardGetProperty(String str) {
		if (!str.startsWith("get"))
			return str;
		String noGet = str.substring(3, str.length());
		return noGet;
	}

	/**
	 * @Comments ：向交换平台2传输数据特殊字符转换
	 * @param str
	 * @return
	 * @Author ：安俊华
	 * @Group : 技术组
	 * @Worker: 1634
	 * @Date ：2014-12-10 下午03:25:57
	 */
	public static String convert(String str) {
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("'", "&apos;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}

	/**
	 * @Comments ：获取异常信息字符串
	 * @param e
	 * @return
	 * @Author ：安俊华
	 * @Group : 技术组
	 * @Worker: 1634
	 * @Date ：2016-3-11 下午02:55:11
	 */
	public static String getStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	/**
	 * @Comments ：将对象转换成字符串
	 * @param obj
	 * @return
	 * @Author ：郭磊
	 * @Group : K组
	 * @Worker: 1491
	 * @Date ：Nov 14, 2018 3:20:47 PM
	 */
	public static String valueOf(Object obj) {
		return (obj == null) ? "" : obj.toString();
	}
}
