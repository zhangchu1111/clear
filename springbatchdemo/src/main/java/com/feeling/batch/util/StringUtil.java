package com.feeling.batch.util;

public class StringUtil {
	public static boolean isNumeric(String str){
        try{
            for(int i=0;i<str.length();i++){
                if(!Character.isDigit(str.charAt(i))){
                    return false;
                }
            }
        }catch(Exception e){
            System.out.println("StringUtil.isNumeric :"+e);
        }
        return true;
    }


    public static boolean isNotNullOrNotEmptyString(String str){
        try{

            if(str !=null &&str.length()!=0){
                return true;
            }
        }catch(Exception e){
          System.out.println("StringUtil.isNotNullOrEmptyString :"+e);  
        }
        return false;
    }

}
