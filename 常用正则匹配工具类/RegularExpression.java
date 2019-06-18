package com.util.test;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ����������ʽƥ�乤��
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

    private Pattern p_PhoneNum = Pattern.compile("^1[3|4|5|6|7|8|9][0-9]\\d{8}$");//ƥ���ֻ���
    private Pattern p_str = Pattern.compile("\\s*|\t|\r|\n");//ɾ�����кͿո�
    private Pattern p_letter = Pattern.compile("^[a-zA-Z]+$");//ƥ����ĸ
    private Pattern p_letterNum = Pattern.compile("^[a-zA-Z0-9]+$");//ƥ�����ֺ���ĸ
    private Pattern p_letterNumLine = Pattern.compile("^[a-zA-Z0-9_]+$");//ƥ��������ĸ�»���
    private Pattern p_chinese = Pattern.compile("^[\\u4E00-\\u9FA5]+$");//ƥ�����ģ��򷱣�
    private Pattern p_chineses = Pattern.compile("^[\\u4E00-\\u9FFF]+$");//ƥ�����ģ��򷱣�
    private Pattern p_emailAddress = Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+");//ƥ������
    @SuppressWarnings("unused")
	//ƥ��������
	//private String regx="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
    private String regxStr="^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
    private Pattern p_data = Pattern.compile(regxStr);
    /**
     * �ַ����Ƿ�Ϊ��
     */
    public boolean isEmpty(String value) {
        return value == null || value.length() == 0 || "null".equals(value);
    }


    /**
     * String ���Ƿ�����ո�ͻ��з�
     * ���������ȥ��String�����пո�ͻ��з�
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
     * ƥ���ֻ���
     * ��1λ��1��
     * ��2λ��{3��4��5��6��7��8��9}�������֣�
     * ��3��11λ��0��9��������
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
     * ƥ������
     *
     * @return ����true��ʾ�Ϸ�
     */
    public boolean isEmailAddress(String address) {
        if (null != address) {
            Matcher m = p_emailAddress.matcher(address);
            return m.matches();
        }
        return false;
    }


    /***
     *  �Ƿ�Ϊint������
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
     *  �Ƿ�ΪLONG
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
     * �Ƿ�Ϊ��ĸ
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
     * ƥ�����ֺ���ĸ
     *
     * @param value
     * @return ����trueƥ��ɹ�
     */
    public boolean isLetterAndNum(String value) {
        if (value != null) {
            Matcher m = p_letterNum.matcher(value);
            return m.matches();
        }
        return false;
    }

    /**
     * ƥ��������ĸ�»���
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
     * ƥ���������
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
     * ƥ��������ĺͷ�������
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
     * ������ƥ��yyyy-mm-dd
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
     * ��֤���֤�����ƥ��
     * ��ϸƥ����þۺ����ݽӿ�����ƥ��
     * @param idCard
     * @return
     */
    public  boolean isIdCardNum(String idCard) {
        String reg = "^\\d{15}$|^\\d{17}[0-9Xx]$";
        @SuppressWarnings("unused")
		String strYear = idCard.substring(6, 14);// ������
        if (!idCard.matches(reg)) {
            System.out.println("���֤��ʽ����ȷ");
            return false;
        }
        @SuppressWarnings("rawtypes")
		Hashtable h = GetAreaCode();
        if (h.get(idCard.substring(0, 2)) == null) {
            System.out.println("���֤�����������");
              return false;
        }

        return true;
    }


    /**
     * ���ܣ����õ�������
     *
     * @return Hashtable ����
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "����");
        hashtable.put("12", "���");
        hashtable.put("13", "�ӱ�");
        hashtable.put("14", "ɽ��");
        hashtable.put("15", "���ɹ�");
        hashtable.put("21", "����");
        hashtable.put("22", "����");
        hashtable.put("23", "������");
        hashtable.put("31", "�Ϻ�");
        hashtable.put("32", "����");
        hashtable.put("33", "�㽭");
        hashtable.put("34", "����");
        hashtable.put("35", "����");
        hashtable.put("36", "����");
        hashtable.put("37", "ɽ��");
        hashtable.put("41", "����");
        hashtable.put("42", "����");
        hashtable.put("43", "����");
        hashtable.put("44", "�㶫");
        hashtable.put("45", "����");
        hashtable.put("46", "����");
        hashtable.put("50", "����");
        hashtable.put("51", "�Ĵ�");
        hashtable.put("52", "����");
        hashtable.put("53", "����");
        hashtable.put("54", "����");
        hashtable.put("61", "����");
        hashtable.put("62", "����");
        hashtable.put("63", "�ຣ");
        hashtable.put("64", "����");
        hashtable.put("65", "�½�");
        hashtable.put("71", "̨��");
        hashtable.put("81", "���");
        hashtable.put("82", "����");
        hashtable.put("91", "����");
        return hashtable;
    }

}
