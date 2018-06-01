package vip.dummy.texasholdem.ya;

import java.util.Arrays;

/**
 * @program: java
 * @description: 判断分析局势，制定策略- -
 * @author: Ya
 * @create: 2018-05-24 13:03
 *
 *      flush 在本文意思是 顺子。。。   same_color是同花
 *      顺子 的实际英文应该是 straight  —。—
 *
 *       H红桃 S黑桃 D方块 C草花  HSDC
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
                    mycards[0].charAt(0)=='J'){
                return "干";  // JJ +
            }
            return "观望";   //对子 TT-
        }
        //再判断 单牌
        if(isA_KQJ(mycards)){
            return "干"; //AK AQ AJ
        }
        if(isKQJ(mycards)){  //KQJ 单牌组合
            return "跟";
        }
        if(hasA(mycards)){
            return "观望";
        }
        if(isSameColor(mycards)){
            return "观望";
        }
        // 加上如果手牌点数和大于多少就跟, 貌似多余
        if(pokeNumRank_hand(mycards) >= 20){
            return "观望";
        }
        return "溜了";
    }

    /**
     *
     *
     */
    //TODO 没有判断 组牌能形成单对，(貌似完成了)  flop阶段弃牌的问题 比如 AQ+25J A2+26J CH+CCCD花色弃牌
    public static String nextTactic(String[] mycards, String[] table_cards){

        if(isThreeOrFour(mycards,table_cards).equals("四条")){
            if(table_isThreeOrFour(table_cards) == 4){ //如果桌牌是四条
                if(isA_KQJ(mycards)){                  //手上有AKQJ 就干 否则观望
                    return "干";
                }else{
                    return "观望";
                }
            }
            return "干";
        }

        if(isThreeOrFour(mycards,table_cards).equals("葫芦")){
            if(table_isThreeOrFour(table_cards) == 3){   //如果桌牌是三条
                return "跟";
            }
            return "干";
        }

        if( isSameColor(mycards,table_cards) == 0){ //同花  isSameColor是差几张同花, 没有判断对方组成四条 葫芦
            return "干";
        }
        if(table_max_same_color(mycards,table_cards)){//桌上有四同花 且不和自己一样
            return "溜了";
        }
        if(isFlush(mycards,table_cards) == 0){   //顺子
            return "干";
        }

        if(isThreeOrFour(mycards,table_cards).equals("三条")){
            if(countStraight(table_cards) == 1){
                return "跟";
            }
            if(table_isThreeOrFour(table_cards) == 3){
                if(isA_KQJ(mycards)){
                    return "跟";
                }
                return "溜了";
            }

            return "干";
        }

        //手牌与桌牌组成两对 且桌牌全为单牌   AB + AB CDE
        if(isThreeOrFour(mycards,table_cards).equals("两对") && isSingle(table_cards)){
            if(countStraight(table_cards) == 1){
                return "跟";
            }
            if(table_cards.length < 4){
                return "干";
            }
            return "跟";
        }

        if(isThreeOrFour(mycards,table_cards).equals("两对") && !isSingle(table_cards)){
            if(isPair(mycards))return "跟";
            if(!isPair(mycards) && isHandAndTable_pair(mycards,table_cards)){
               return "观望";
            }
            return "溜了";
        }
        if(isThreeOrFour(mycards,table_cards).equals("对子") && isSingle(table_cards)){
            if(countStraight(table_cards) == 1){
                return "观望";
            }
            if(isPair(mycards) && pokeNumRank(mycards[0].charAt(0))>= 11){
                return "跟";
            }
        }

        if(isThreeOrFour(mycards,table_cards).equals("对子")){
            if(countStraight(table_cards) == 1){
                return "观望";
            }
            if(handAndTable_pair(mycards,table_cards)){     //判断的是 手牌与桌牌组成的对子,对子至少 JJ+
                return "跟";
            }
        }
        //判断差一张同花  跟
        if(isSameColor(mycards,table_cards) == 1 && table_cards.length <= 4){
            if(table_cards.length == 3){
                return "观望";
            }
            if(table_cards.length == 4){
                return "观望";
            }
        }

        //差一张顺子 0为顺子 1为有一个位置(13 22连)，2为有两个位置(4连) 构成顺子
        if(isFlush(mycards,table_cards) == 2 && table_cards.length <= 4){
            return "观望";
        }

        if(isFlush(mycards,table_cards) == 1 && table_cards.length <= 3){
            return "观望";
        }
        //判断nextTactic 是否能组成个小对子， 观望
        if(isHandAndTable_pairTT(mycards,table_cards)){
            return "溜了";
        }

        //又增加 判断带A 小对  单 TODO 这个需要重新考虑以下要不要加上!!!!!
        /*if(hasA(mycards)){
            return "观望";
        }*/
        return "溜了";
    }



    /**
     * 一些方法
     */
    //判断手牌是否对子
    public static boolean isPair(String[] mycards){
        if(mycards[0].charAt(0) == mycards[1].charAt(0)){
            return true;
        }
        return false;
    }
    //判断手牌是否同花
    public static boolean isSameColor(String[] mycards){
        if(mycards[0].charAt(1) == mycards[1].charAt(1)){
            return true;
        }
        return false;
    }
    //判断手牌是否有A
    public static boolean hasA(String[] mycards){
        if(mycards[0].charAt(0) == 'A' ||
                mycards[1].charAt(0) == 'A'){
            return true;
        }
        return false;
    }

    //判断手牌是否 A_KQJ 单牌  (没判断对子)
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

    //判断手牌是否 KQJ_KQJ 任意组合
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
        if((max1 == 3 && max2 ==2) || (max1==2 && max2 ==3) ){ //要不要加上(max1 == 3 && max2 == 3)
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
    //手牌 与 桌牌 是否能组成至少一个对子 且对子为 JJ+
    public static boolean handAndTable_pair(String[] mycard, String[] table_cards){
        char c1 = mycard[0].charAt(0);
        char c2 = mycard[1].charAt(0);
        if(c1 != 'A' && c1 != 'K' && c1 != 'Q' && c1 != 'J' ){
            c1 = '0';
        }
        if(c2 != 'A' && c2 != 'K' && c2 != 'Q' && c2 != 'J' ){
            c2 = '0';
        }
        if(c1 == '0' && c2 == '0'){
            return false;
        }
        for(int i = 0; i < table_cards.length; i ++){
            if(c1 != '0' && (c1 == table_cards[i].charAt(0))){
                return true;
            }
            if(c2 != '0' && (c2 == table_cards[i].charAt(0))){
                return true;
            }
        }
        return false;
    }


    //TODO 判断差几张构成同花 (前提是手牌同花)
    public static int isSameColor(String[] mycards, String[] table_cards){
        if(mycards[0].charAt(1) != mycards[1].charAt(1)){
            return 4;
        }

        char color = mycards[0].charAt(1);
        int num = 0;
        for(int i = 0; i < table_cards.length; i++){
            if(color == table_cards[i].charAt(1)){
                num++;
            }
        }
        if(num >= 3)return 0;
        if(num == 2)return 1;
        if(num == 1)return 2;
        if(num == 0)return 3;
        return 4;
    }

    // TODO 判断差几张构成顺子 0是顺子，1是差特定1张，2是差特定两张中的任意一张
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
                poke[1] != 0 ){
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

        //判断是不是四缺一 TODO 有错误啊！！(改完了)
        step = 1;
        for(int i = 1; i < poke.length; i++){
            if(poke[i] == 0) continue;
            for(int j = i+1; j < poke.length; j++){
                if(poke[j] == 0)continue;
                step++;
                if(step == 4){
                    if(j - i == 3)return 2;
                    if(j - i == 4)return 1;

                    break;
                }
            }
            step = 1;
        }
        return 5;
    }

    public static int countStraight(String[] s){
        if(s.length < 4){   //只判断 turn river阶段
            return 5;
        }
        int[] poke = new int [14];
        for(int i = 0; i < poke.length; i++){
            poke[i] = 0;
        }

        for(int i = 0; i < s.length; i++){
            poke[pokeNum(s[i].charAt(0))]++;
        }
        //判断是不是四缺一
        int step = 1;
        for(int i = 1; i < poke.length; i++){
            if(poke[i] == 0) continue;
            for(int j = i+1; j < poke.length; j++){
                if(poke[j] == 0)continue;
                step++;
                if(step == 4){
                    if(j - i == 3)return 2;
                    if(j - i == 4)return 1;
                    break;
                }
            }
            step = 1;
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
    public static int pokeNumRank_hand(String[] mycards){
        return pokeNumRank(mycards[0].charAt(0))+pokeNumRank(mycards[1].charAt(0));
    }
    public static int pokeNumRank(char c){
        switch (c){
            case 'A':return 14;
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

    //判断能否存在至少 AA KK QQ  一个对子(手牌与桌牌组合)
    public static boolean isAAKKQQ(String[] mycards, String[] table_cards){

        for(int k = 0; k <= 1; k++){
            if(mycards[k].charAt(0) == 'A'){
                for(int i = 0; i < table_cards.length; i++){
                    if(table_cards[i].charAt(0) == 'A'){
                        return true;
                    }
                }
            }
            if(mycards[k].charAt(0) == 'K'){
                for(int i = 0; i < table_cards.length; i++){
                    if(table_cards[i].charAt(0) == 'K'){
                        return true;
                    }
                }
            }
            if(mycards[k].charAt(0) == 'Q'){
                for(int i = 0; i < table_cards.length; i++){
                    if(table_cards[i].charAt(0) == 'Q'){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    //TODO 判断桌牌最大同花(>= 4) 且不和自己同色     H红桃 S黑桃 D方块 C草花  HSDC
    public static boolean table_max_same_color(String[] mycards, String[] table_cards){
        char[] a = new char[table_cards.length];
        for(int i = 0; i < a.length; i++){
            a[i] = table_cards[i].charAt(1);
        }

        char max_color;
        int h = 0, s = 0, c = 0, d = 0;
        for(int i = 0; i < a.length; i++){
            if(a[i] == 'H') {
                h++;
            }else if(a[i] == 'S'){
                s++;
            }else if(a[i] == 'C'){
                c++;
            }else{
                d++;
            }
        }
        //判断hsdc谁最大 等于4 说明桌牌有四个同花
        if(h >= 4){
            max_color = 'H';
        }else if(s >= 4){
            max_color = 'S';
        }else if(d >= 4){
            max_color = 'D';
        }else if(c >= 4){
            max_color = 'C';
        }else{
            return false;
        }
        if(mycards[0].charAt(1) != max_color && mycards[1].charAt(1)!= max_color){
            System.out.println("执行了 桌上四同花， 和自己不一样，赶快逃跑！！！");
            return true;
        }
        return false;
    }
    public static boolean isHandAndTable_pair(String[] mycards, String[] table_cards){
        char c1 = mycards[0].charAt(0);
        char c2 = mycards[1].charAt(0);
        for(int i = 0; i < table_cards.length; i++){
            if(c1 == table_cards[i].charAt(0)){
                return true;
            }
        }
        for(int i = 0; i < table_cards.length; i++){
            if(c2 == table_cards[i].charAt(0)){
                return true;
            }
        }
        return false;
    }

    //组成小对子
    public static boolean isHandAndTable_pairTT(String[] mycards, String[] table_cards){ // 77~TT
        char c1 = mycards[0].charAt(0);
        char c2 = mycards[1].charAt(0);
        if(c1 == 'T' ||
                c1 == '9' ||
                c1 == '8' ||
                c1 == '7' ){
            for(int i = 0; i < table_cards.length; i++){
                if(c1 == table_cards[i].charAt(0)){
                    return true;
                }
            }
        }
        if(c2 == 'T' ||
                c2 == '9' ||
                c2 == '8' ||
                c2 == '7' ){
            for(int i = 0; i < table_cards.length; i++){
                if(c2 == table_cards[i].charAt(0)){
                    return true;
                }
            }
        }
        return false;
    }
/*
    public static boolean isHandPair_Little_TableSingleMax(String[] mycards,String[] table_cards){

        char c1 = mycards[0].charAt(0);
        for(int i = 0; i < table_cards.length; i++){
            if(c1 == table_cards[i].charAt(0)){
                c1 = table_cards[i].charAt(0);
                return c1;
            }
        }

        for(int i = 0; i < table_cards.length; i++){
            if(c == table_cards[i].charAt(0)){
                c = table_cards[i].charAt(0);
                break;
            }
        }
    }
    public static int findMaxTableSingle(String[] table_cards){

    }
    */

    //  判断牌是不是单张
    public static boolean isSingle(String[] s){
        for(int i = 0; i < s.length; i++){
            for(int j = i+1; j < s.length; j++){
                if(s[i].charAt(0) == s[j].charAt(0)){
                    return false;
                }
            }
        }
        return true;
    }

    public static int max_single_rank(String[] s){
        if(s.length == 0) return 0;
        int max = pokeNumRank(s[0].charAt(0));
        for(int i = 1; i < s.length; i++){
            if(pokeNumRank(s[i].charAt(0)) > max){
                max = pokeNumRank(s[i].charAt(0));
            }
        }
        return max;
    }
    public static int table_isThreeOrFour(String[] s){
        if(s.length < 3)return 0;
        //判断最大相同数量
        int max = 1;
        int temp = 0;
        for(int i = 0; i < s.length; i++){
            for(int j = 0; j < s.length; j++){
                if(s[i].charAt(0) == s[j].charAt(0)){
                    temp++;
                }
            }
            if(temp > max){
                max = temp;
            }
            temp = 0;
        }
        return max;
    }

}
