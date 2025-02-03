import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * The Person class represents a Person. A person is a human with a name, age
 * and a hobby
 */

public class Hand {
    String hand;
    int bid;
    int rank;
    String[] cards;
    HashMap<String, Integer> cardCount;


    public Hand(String hand, int bid, int rank, String[] cards, HashMap<String, Integer> cardCount) {
        this.hand = hand;
        this.bid = bid;
        this.rank = rank;
        this.cards = cards;
        this.cardCount = cardCount;
    }

    public Hand() {

    }

    public int getRank() {
        return rank;
    }

    public int getBid() {
        return bid;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getHand() {
        return hand;
    }


    public int getRank(int count) {
        int rank = 0;

        if (count == 0) {
            rank = 1;
        }

        if (count == 1) {
            rank = 2;
        }

        if (count == 2) {
            rank = 3;
        }

        if (count == 3) {
            rank = 4;
        }

        if (count == 4) {
            rank = 5;
        }

        if (count == 6) {
            rank = 6;
        }

        if (count == 10) {
            rank = 7;
        }
        return rank;
    }

    public Integer rankCard(String card, boolean jackWild) {
        HashMap<String, Integer> cardRank = new HashMap<>();
        cardRank.put("Ace", 14);
        cardRank.put("King", 13);
        cardRank.put("Queen", 12);
        if (!jackWild) {
            cardRank.put("Jack", 11);
        } else {
            cardRank.put("Jack", 1);
        }
        cardRank.put("10", 10);
        cardRank.put("9", 9);
        cardRank.put("8", 8);
        cardRank.put("7", 7);
        cardRank.put("6", 6);
        cardRank.put("5", 5);
        cardRank.put("4", 4);
        cardRank.put("3", 3);
        cardRank.put("2", 2);
        return cardRank.get(card);
    }


    /**
     * The sortHands method will sort the list of hands in the array list
     *
     * @param hands an object array list representing the hand object
     * @return a String representing a formatted sentence showing the future age
     */

    public void sortHands(List<Hand> hands, boolean jack) {
        for (int i = 0; i < hands.size() - 1; i++) {
            for (int j = 0; j < hands.size() - i - 1; j++) {
                Hand one = hands.get(j);
                Hand two = hands.get(j + 1);

                int rankTest = Integer.compare(one.getRank(), two.getRank());

                if (rankTest == 0) {
                    if (jack) {
                        rankTest = compareHands(one, two, true);
                    } else {
                        rankTest = compareHands(one, two, false);
                    }

                }

                if (rankTest > 0) {
                    hands.set(j, two);
                    hands.set(j + 1, one);
                }
            }
        }
    }

    public int compareHands(Hand one, Hand two, boolean jack) {
        for (int i = 0; i < 5; i++) {
            int cardComparison = 0;

            if (!jack) {
                cardComparison = rankCard(one.cards[i], false).compareTo(rankCard(two.cards[i], false));
            } else {
                cardComparison = rankCard(one.cards[i], true).compareTo(rankCard(two.cards[i], true));
            }

            if (cardComparison != 0) {
                return cardComparison;
            }
        }
        return 0;
    }

    public String highestFrequency(String[] cardList) {
        int maxcount = 0;
        String maxFreq = "";
        for (int i = 0; i < cardList.length; i++) {
            int count = 0;
            for (int j = 0; j < cardList.length; j++) {
                if (cardList[i].equals(cardList[j])) {
                    count++;
                }
            }

            if (count == maxcount) {
                if (rankCard(maxFreq, true) < rankCard(cardList[i], true)) {
                    maxFreq = cardList[i];
                }
            }

            if (count > maxcount) {
                maxcount = count;
                maxFreq = cardList[i];
            }
        }
        int maxRank = 0;
        if (maxFreq.equals("Jack") || maxcount == 1) {
            for (int l = 0; l < cardList.length; l++) {
                if (!cardList[l].equals("Jack")) {
                    int valueRank = rankCard(cardList[l], true);
                    if (valueRank > maxRank) {
                        maxRank = valueRank;
                        maxFreq = cardList[l];

                    }
                }
            }
        }

        return maxFreq;
    }

    public String[] replaceAllJacks(String line){
        // Part 3: check the hands that have a jack
        String[] jackLineList = line.split(",");
        if(line.contains("Jack")){
            // get the highest frequency or the biggest card by calling the highestFrequency method
            String replace = highestFrequency(jackLineList);
            // replace every jack with the highest frequency
            for(int i = 0; i < jackLineList.length; i++){
                if(jackLineList[i].equals("Jack")){
                    jackLineList[i] = replace;
                }
            }
        }
        else {
            return jackLineList;
        }
        return jackLineList;
    }

    public int countSimilarities(String[] cards){
        int count  = 0;
        // Nested for loop to count how many similarities
        for(int i = 0; i < cards.length; i++){
            for(int j = i+1; j < cards.length; j++){
                if(cards[i].equals(cards[j])){
                    count ++;
                }
            }
        }
        return count;
    }

    public void sortRanks(ArrayList<Hand> handsList){
        int sortRanks = 1;
        for (Hand hand : handsList) {
            hand.setRank(sortRanks);
            sortRanks++;
        }
    }

    public int totalBid(ArrayList<Hand> handsList) {
        int totalBid = 0;
        for (Hand hand : handsList) {
            int currentRank = hand.getRank();
            int currentBid = hand.getBid();
            totalBid += currentRank * currentBid;
        }
        return totalBid;

    }




}

