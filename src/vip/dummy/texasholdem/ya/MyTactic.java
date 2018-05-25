package vip.dummy.texasholdem.ya;

import java.util.Arrays;

/**
 * @program: java
 * @description: 判断分析局势，制定策略- -
 * @author: Mr.Wang
 * @create: 2018-05-24 13:03
 **/
public class MyTactic {

    /**
     * 起手牌
     */
    public static String firstTactic(String[] mycards){
        //先判断对子
        if(isPair(mycards)){
            if(mycards[0].charAt(0)=='A'||
                    mycards[0].charAt(0)=='K'||
                    mycards[0].charAt(0)=='Q'||
                    mycards[0].charAt(0)=='J'||
                    mycards[0].charAt(0)=='T'){
                return "干";  // TT +
            }
            return "跟";   //对子 99-
        }
        //再判断 单牌
        if(isA_KQJ(mycards)){
            return "干"; //AK AQ AJ
        }
        if(isKQJ(mycards)){  //KQJ 单牌组合
            return "跟";
        }
        if(hasA(mycards)){
            return "跟";
        }
        if(isSameColor(mycards)){
            return "观望";
        }
        return "溜了";
    }

    /**
     *
     *
     */
    //TODO 没有判断 组牌能形成单对，
    public static String nextTactic(String[] mycards, String[] table_cards){
        if(isThreeOrFour(mycards,table_cards).equals("三条")){
            return "干";
        }
        if(isThreeOrFour(mycards,table_cards).equals("葫芦")){
            return "干";
        }
        if(isThreeOrFour(mycards,table_cards).equals("四条")){
            return "干";
        }
        if(isFlush(mycards,table_cards) == 0){   //顺子
            return "干";
        }

        if(isThreeOrFour(mycards,table_cards).equals("两对")){
            if(!tableIsPair(table_cards)){
                return "干";
            }else{
                return "跟";
            }
        }
        if(isThreeOrFour(mycards,table_cards).equals("对子") && !tableIsPair(table_cards)){
            //if() TODO 如果组牌包含 AA KK JJ（非桌牌）直接allIn
            if(isAAKKQQ(mycards,table_cards)){
                return "干";
            }
        }
        if(isSameColor(mycards,table_cards) == 0){  //同花
            return "干";
        }
        if(isFlush(mycards,table_cards) == 2){     //差一张顺子
            return "跟";
        }
        if(table_cards.length == 3 && isSameColor(mycards,table_cards)<=1){
            return "跟";
        }
        if(isFlush(mycards,table_cards) == 1){
            return "观望";
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
        if(mycards[0].charAt(0) == 'A' ||
                mycards[1].charAt(0) == 'A'){
            return true;
        }
        return false;
    }

    //判断手牌是否 A_KQJ 单牌  (非对)
    public static boolean isA_KQJ(String[] mycards){
        if(hasA(mycards)){
            if((mycards[0].charAt(0) == 'K'||
                    mycards[0].charAt(0) == 'Q'||
                    mycards[0].charAt(0) == 'J') ||
                    (mycards[1].charAt(0) == 'K'||
                            mycards[1].charAt(0) == 'Q'||
                            mycards[1].charAt(0) == 'J')){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
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


    //判断组牌 TODO 四条，葫芦，三条，两对，对子,单牌
    public static String isThreeOrFour(String[] mycards, String[] table_cards){
        int len1 = mycards.length;
        int len2 = table_cards.length;
        char[] a = new char[len1+len2];
        a[0] = mycards[0].charAt(0);
        a[1] = mycards[1].charAt(0);
        for(int i = 0; i < table_cards.length; i++){
            a[i+2] = table_cards[i].charAt(0);
        }
        Arrays.sort(a);

        int max1 = 1;
        int max2 = 1;
        int temp = 1;
        for(int i = 1; i < a.length; i++){
            if(a[i] == a[i-1]){
                temp++;
            }else{
                if(max1 > max2 && temp > 1){
                    max2 = temp;
                    temp = 1;
                }else if(temp > 1){
                    max1 = temp;
                    temp = 1;
                }
            }
        }
        if(max1 > max2 && temp > 1){
            max2 = temp;
            temp = 1;
        }else if(temp > 1){
            max1 = temp;
            temp = 1;
        }
        if(max1 == 2 && max2 == 1){
            return "对子";
        }
        if(max1 == 2 && max2 == 2){
            return "两对";
        }
        if(max1 == 1){
            return "单牌";
        }
        if(max1 == 3 && max2 == 1){
            return "三条";
        }
        if((max1 == 3 && max2 ==2) || (max1==2 && max2 ==3)){
            return "葫芦";
        }
        if(max1 == 4 || max2 == 4){
            return "四条";
        }
        return "单牌";
    }

    public static boolean tableIsPair(String[] s){
        char[] a = new char[s.length];
        for(int i = 0; i < a.length; i++){
            a[i] = s[i].charAt(0);
        }
        Arrays.sort(a);

        int max = 1;
        for(int i = 1; i < a.length; i++){
            if(a[i] == a[i-1]){
                max++;
            }
        }
        if(max > 1){
            return true;
        }
        return false;
    }


    //TODO 判断差几张构成同花
    public static int isSameColor(String[] mycards, String[] table_cards){
        if(mycards[0].charAt(1) != mycards[1].charAt(1)){
            return 4;
        }
        int len1 = mycards.length;
        int len2 = table_cards.length;
        char[] a = new char[len1+len2];
        a[0] = mycards[0].charAt(0);
        a[1] = mycards[1].charAt(0);
        for(int i = 0; i < table_cards.length; i++){
            a[i+2] = table_cards[i].charAt(0);
        }
        char color = mycards[0].charAt(1);
        int num = 0;
        for(int i = 0; i < table_cards.length; i++){
            if(color == table_cards[i].charAt(1)){
                num++;
            }
        }
        if(num >= 5) return 0;
        if(num == 4) return 1;
        if(num == 3) return 2;
        return 4;

    }

    // TODO 判断差几张构成顺子
    public static int isFlush(String[] mycards, String[] table_cards){
        int[] poke = new int [14];
        for(int i = 0; i < poke.length; i++){
            poke[i] = 0;
        }
        for(int i = 0; i < mycards.length; i++){
            poke[pokeNum(mycards[i].charAt(0))]++;
        }
        for(int i = 0; i < table_cards.length; i++){
            poke[pokeNum(table_cards[i].charAt(0))]++;
        }

        //先单独判断AKQJT
        if(poke[10] != 0 &&
                poke[11] != 0 &&
                poke[12] != 0 &&
                poke[13] != 0 &&
                poke[11] != 0 ){
            return 0;
        }

        //判断是不是五连顺子  TJQKA另外单独判断,
        int step = 1;
        for(int i = 1; i < poke.length; i++){
            if(poke[i] == 0) continue;
            for(int j = i+1; j < poke.length; j++){
                if(poke[j] == 0) {
                    step = 1;
                    break;
                }
                step++;
                if(step == 5)return 0;
            }
        }

        //判断是不是四缺一
        step = 1;
        for(int i = 1; i < poke.length; i++){
            if(poke[i] == 0) continue;
            for(int j = i+1; j < poke.length; j++){
                if(poke[j] == 0)continue;;
                step++;
                if(step == 4){
                    if(j - i == 3)return 2;
                    if(j - i == 4)return 1;
                }
            }
        }

        return 5;
    }

    public static int pokeNum(char c){
        switch (c){
            case 'A':return 1;
            case '2':return 2;
            case '3':return 3;
            case '4':return 4;
            case '5':return 5;
            case '6':return 6;
            case '7':return 7;
            case '8':return 8;
            case '9':return 9;
            case 'T':return 10;
            case 'J':return 11;
            case 'Q':return 12;
            case 'K':return 13;
            default: return 0;
        }
    }

    public static boolean isAAKKQQ(String[] mycards, String[] table_cards){
        if(mycards[0].charAt(0) == 'A'){
            for(int i = 0; i < table_cards.length; i++){
                if(table_cards[i].charAt(0) == 'A'){
                    return true;
                }
            }
        }
        if(mycards[0].charAt(0) == 'K'){
            for(int i = 0; i < table_cards.length; i++){
                if(table_cards[i].charAt(0) == 'K'){
                    return true;
                }
            }
        }
        if(mycards[0].charAt(0) == 'Q'){
            for(int i = 0; i < table_cards.length; i++){
                if(table_cards[i].charAt(0) == 'Q'){
                    return true;
                }
            }
        }
        return false;
    }



}
