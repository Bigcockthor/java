package vip.dummy.texasholdem.ya;

import java.util.Arrays;

/**
 * @program: java
 * @description: 判断分析局势，制定策略- -
 * @author: Mr.Wang
 * @create: 2018-05-24 13:03
 **/
public class MyTactic {

    public static String firstTactic(String[] mycards){
        if(isPair(mycards)){
            if(mycards[0].charAt(0)=='A'||
                    mycards[0].charAt(0)=='K'||
                    mycards[0].charAt(0)=='Q'||
                    mycards[0].charAt(0)=='J'||
                    mycards[0].charAt(0)=='T'){
                return "干";  //99+
            }
            return "跟";   //对子 88-
        }
        if(isA_KQJ(mycards)){
            return "干"; //AK AQ AJ
        }
        if(isKQJ(mycards)){  //KQJ 单牌组合
            return "跟";
        }
        if(isSameColor(mycards)){
            return "观望一下";
        }
        return "溜了";
    }

    public static String nextTactic(String[] mycards, String[] table_cards){
      if(isThreeOrFour(mycards,table_cards).equals("三条")){
          return "干";
      }
      if(isThreeOrFour(mycards,table_cards).equals("四条")){
          return "干";
      }
      if(isThreeOrFour(mycards,table_cards).equals("对子")){
          return "跟";
      }
      return "溜了";
       //TODO 还得判断是不是同花 或者顺子  或者葫芦啥的
    }



    /**
     * 一些方法
     */
    //判断手牌是否对子  22+ 15% |
    public static boolean isPair(String[] mycards){
        if(mycards[0].charAt(0) == mycards[1].charAt(0)){
            return true;
        }
        return false;
    }
    //判断手牌是否同花     45s+ 15%
    public static boolean isSameColor(String[] mycards){
        if(mycards[0].charAt(1) == mycards[1].charAt(1)){
            return true;
        }
        return false;
    }
    //判断是否有A
    public static boolean hasA(String[] mycards){
        if(mycards[0].charAt(0) == 'A'){
            return true;
        }
        return false;
    }

    //判断手牌是否 A_KQJ   (非对)
    public static boolean isA_KQJ(String[] mycards){
        if(mycards[0].charAt(0) == 'A'){
            if(mycards[1].charAt(0) == 'K'||
                    mycards[1].charAt(0) == 'Q'||
                    mycards[1].charAt(0) == 'J'){
                return true;
            }
            return false;
        }
        return false;
    }

    //判断手牌是否 KQJ 任意组合    (非对)
    public static boolean isKQJ(String[] mycards){
        if(mycards[0].charAt(0) == 'K'||
                mycards[0].charAt(0) == 'Q'||
                mycards[0].charAt(0) == 'J'){
            if(mycards[1].charAt(0) == 'K'||
                    mycards[1].charAt(0) == 'Q'||
                    mycards[1].charAt(0) == 'J'){
                return true;
            }
            return false;
        }
        return false;
    }


    //判断组牌 是不是三条或四条
    public static String isThreeOrFour(String[] mycards, String[] table_cards){
        int len1 = mycards.length;
        int len2 = table_cards.length;
        char[] a = new char[len1+len2];
        a[0] = mycards[0].charAt(0);
        a[1] = mycards[1].charAt(0);
        for(int i = 2; i < a.length; i++){
            a[i] = table_cards[i].charAt(0);
        }
        Arrays.sort(a);
        int temp = 1;
        int max = 1;
        for(int i = 1; i < a.length; i++){
            if(a[i] == a[i-1]){
                temp++;
                if(temp > max){
                    max = temp;
                }
            }else{
                temp = 1;
            }
        }
        if(max == 3){return "三条";}
        if(max == 4){return "四条";}
        if(max == 2){return "对子";}
        return "单牌";
    }


    //TODO 判断差几张构成同花


    // TODO 判断差几张构成顺子
    public static int isFlush(char[] a){

        return 5;
    }

}
