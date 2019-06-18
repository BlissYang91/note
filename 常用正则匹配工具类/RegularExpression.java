package com.util.test;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用正则表达式匹配工具
 *
 * @author ytf
 */
public class RegularExpression {
	
    private RegularExpression() {

    }

    private static class LazyHolder {
        private static final RegularExpression INSTANCE = new RegularExpression();
    }

    public static final RegularExpression getInstance() {
        return LazyHolder.INSTANCE;
    }

    private Pattern p_PhoneNum = Pattern.compile("^1[3|4|5|6|7|8|9][0-9]\\d{8}$");//匹配手机号
    private Pattern p_str = Pattern.compile("\\s*|\t|\r|\n");//删除换行和空格
    private Pattern p_letter = Pattern.compile("^[a-zA-Z]+$");//匹配字母
    private Pattern p_letterNum = Pattern.compile("^[a-zA-Z0-9]+$");//匹配数字和字母
    private Pattern p_letterNumLine = Pattern.compile("^[a-zA-Z0-9_]+$");//匹配数字字母下划线
    private Pattern p_chinese = Pattern.compile("^[\\u4E00-\\u9FA5]+$");//匹配中文（简繁）
    private Pattern p_chineses = Pattern.compile("^[\\u4E00-\\u9FFF]+$");//匹配中文（简繁）
    private Pattern p_emailAddress = Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+");//匹配邮箱
    @SuppressWarnings("unused")
	//匹配年月日
	//private String regx="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
    private String regxStr="^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
    private Pattern p_data = Pattern.compile(regxStr);
    /**
     * 字符串是否为空
     */
    public boolean isEmpty(String value) {
        return value == null || value.length() == 0 || "null".equals(value);
    }


    /**
     * String 中是否包含空格和换行符
     * 如果包含，去除String中所有空格和换行符
     */
    public String getStringNoBlank(String str) {
        if (str != null && !"".equals(str)) {
            Matcher m = p_str.matcher(str);
            String strNoBlank = m.replaceAll("");
            return strNoBlank;
        } else {
            return str;
        }
    }

    /**
     * 匹配手机号
     * 第1位：1；
     * 第2位：{3、4、5、6、7、8、9}任意数字；
     * 第3—11位：0—9任意数字
     */
    @SuppressWarnings("unused")
	private boolean isTelPhoneNumber(String value) {
        if (value != null && value.length() == 11) {
            Matcher matcher = p_PhoneNum.matcher(value);
            return matcher.matches();
        }
        return false;
    }

    /**
     * 匹配邮箱
     *
     * @return 返回true表示合法
     */
    public boolean isEmailAddress(String address) {
        if (null != address) {
            Matcher m = p_emailAddress.matcher(address);
            return m.matches();
        }
        return false;
    }


    /***
     *  是否为int型数字
     */
    public boolean isInterger(String value) {
        try {
            int num = Integer.parseInt(value);
            return num < (Math.pow(2, 31) - 1) && num > Math.pow(-2, 31);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /***
     *  是否为LONG
     */
    public boolean isLong(String value) {
        try {
            long num = Long.parseLong(value);
            return num < (Math.pow(2, 63) - 1) && num > Math.pow(-2, 63);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 是否为字母
     *
     * @param value
     * @return
     */
    public boolean isLetter(String value) {
        if (value != null) {
            Matcher m = p_letter.matcher(value);
            return m.matches();
        }
        return false;
    }

    /**
     * 匹配数字和字母
     *
     * @param value
     * @return 返回true匹配成功
     */
    public boolean isLetterAndNum(String value) {
        if (value != null) {
            Matcher m = p_letterNum.matcher(value);
            return m.matches();
        }
        return false;
    }

    /**
     * 匹配数字字母下划线
     *
     * @param value
     * @return
     */
    public boolean isLetterNumLine(String value) {
        if (value != null) {
            Matcher m = p_letterNumLine.matcher(value);
            return m.matches();
        }
        return false;
    }

    /**
     * 匹配简体中文
     *
     * @param value
     * @return
     */
    public boolean isChinese(String value) {
        if (value != null) {
            Matcher m = p_chinese.matcher(value);
            return m.matches();
        }
        return false;
    }

    /**
     * 匹配简体中文和繁体中文
     *
     * @param value
     * @return
     */
    public boolean isChineses(String value) {
        if (value != null) {
            Matcher m = p_chineses.matcher(value);
            return m.matches();
        }
        return false;
    }

    /**
     * 年月日匹配yyyy-mm-dd
     * @param idCard
     * @return
     */
    public  boolean isDateFormated(String idCard) {
        Matcher isNo=p_data.matcher(idCard);
        if (!isNo.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 验证身份证号码简单匹配
     * 详细匹配调用聚合数据接口在线匹配
     * @param idCard
     * @return
     */
    public  boolean isIdCardNum(String idCard) {
        String reg = "^\\d{15}$|^\\d{17}[0-9Xx]$";
        @SuppressWarnings("unused")
		String strYear = idCard.substring(6, 14);// 年月日
        if (!idCard.matches(reg)) {
            System.out.println("身份证格式不正确");
            return false;
        }
        @SuppressWarnings("rawtypes")
		Hashtable h = GetAreaCode();
        if (h.get(idCard.substring(0, 2)) == null) {
            System.out.println("身份证地区编码错误。");
              return false;
        }

        return true;
    }


    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

}
