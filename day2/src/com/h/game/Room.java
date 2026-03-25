package com.h.game;

import java.util.*;

public class Room {


    public void start() {
//        1.创建牌
        List<Card> allCards = new ArrayList<>();
        String[] size = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K","A", "2",};
        String[] color = {"♥", "♠", "♣", "♦"};

        int num =0;
        for (String s : size) {
            num++;
            for (String c : color) {
                allCards.add(new Card(s, c,num));
            }
        }
        Collections.addAll(allCards, new Card("", "🃏",++num), new Card("", "👲",++num));
        System.out.println("牌："+allCards);
        //2 洗牌
        Collections.shuffle(allCards);
        System.out.println("洗牌后："+allCards);
//    初始化玩家
        Map<String, List<Card>> players = new HashMap<>();
        List<Card> player1 = new ArrayList<>();
        List<Card> player2 = new ArrayList<>();
        List<Card> player3 = new ArrayList<>();
//        发牌
        for(int i = 0 ;i<allCards.size()-3 ;){
            player1.add(allCards.get(i++));
            player2.add(allCards.get(i++));
            player3.add(allCards.get(i++));
        }
        players.put("玩家1", player1);
        players.put("玩家2", player2);
        players.put("玩家3", player3);

        System.out.println("底牌："+allCards.subList(allCards.size()-3,allCards.size()));

// 抢地主
        int sum1 = getSum(players.get("玩家1"));
        int sum2 = getSum(players.get("玩家2"));
        int sum3 = getSum(players.get("玩家3"));

        if(sum1>sum2&&sum1>sum3){
            player1.addAll(allCards.subList(allCards.size()-3,allCards.size()));
        }else if(sum2>sum1&&sum2>sum3){
            player2.addAll(allCards.subList(allCards.size()-3,allCards.size()));
        }else{
            player3.addAll(allCards.subList(allCards.size()-3,allCards.size()));
        }

//        对牌排序
        for(Map.Entry<String, List<Card>> entry:players.entrySet()){
            entry.getValue().sort((c1,c2)->c2.getNum()-c1.getNum());
        }

//    看牌
        for(Map.Entry<String, List<Card>> entry:players.entrySet()){
            System.out.println(entry.getKey()+"的牌是："+entry.getValue());
        }

    }

    public int getSum(List<Card> cards){
        int sum =0;
        for(Card card:cards){
            sum+=card.getNum();
        }
        return sum;
    }
}
