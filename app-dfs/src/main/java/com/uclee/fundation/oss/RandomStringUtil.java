package com.uclee.fundation.oss;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

public class RandomStringUtil {


    public static String getUserCodeUUID() {
        Date d = new Date();
        DateFormat df = DateFormat.getDateInstance();
        String date = df.format(d);
        String str[] = date.split("-");
        String s = "";
        for (String i : str) {
            s += i;
        }

        return s + d.getTime();

    }

    public static String getUserCode() {
        Date d = new Date();
        DateFormat df = DateFormat.getDateInstance();
        String date = df.format(d);
        String str[] = date.split("-");
        String s = "";
        for (String i : str) {
            s += i;
        }

        return s;

    }


    public static String getLogTime() {
        Date d = new Date();

        DateFormat df = DateFormat.getDateTimeInstance();
        String date = df.format(d);
        return date;
    }

    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        String s = java.util.UUID.randomUUID().toString();
        //去掉“-”符号 
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    public static String getCode() {
        // 取随机产生的认证码(4位数字)

        String codeList = "1234567890";
        String sRand = "";
        //生成随机类
        Random random = new Random();


        for (int i = 0; i < 6; i++) {
            int a = random.nextInt(codeList.length() - 1);
            String rand = codeList.substring(a, a + 1);
            sRand += rand;

        }
        //System.out.println(sRand.length());
        return sRand;
    }

    /**
     * 产生一定位数的随机字符串，大小写敏感
     *
     * @param count 需要产生的位数
     * @return
     */
    public static String generateLockCode(int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }


    public static String getPaoId(int num) {
        // 取随机产生的认证码(4位数字)

        String codeList = "1234567890";
        String sRand = "";
        //生成随机类
        Random random = new Random();


        boolean flag = true;
        do {
            int a = random.nextInt(codeList.length() - 1);

            String rand = codeList.substring(a, a + 1);
            if (sRand.length() == 5) {


                sRand += rand;


            } else {
                sRand += rand;
            }

            if (sRand.length() == num) {

                flag = false;
            }


        } while (flag);

        return sRand;
    }

    public static void main(String[] args) {
        /*Date d =new Date();
        DateFormat df=DateFormat.getDateTimeInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dd=sdf.format(d);
		
		System.out.println(dd);*/

        System.out.println(getPaoId(6));
    }


}
