import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        // step 1: create a file object
        File f = new File("src/input_file");

        String fileData = "";
        String bidData = "";
        HashMap<String, Integer> cardInformation = new HashMap<>();
        try {

            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String currentLine = s.nextLine();
                // index of the vertical line
                int verticalLine = currentLine.indexOf("|");
                // separation of the bid and cards
                String bidLine = currentLine.substring(verticalLine+1);
                bidData += bidLine + "\n";
                int bidLineInt = Integer.parseInt(bidLine);
                // cards
                currentLine = currentLine.substring(0, verticalLine);
                fileData += currentLine + "\n";
                // hashmap to get the bid amount specific to the cards
                cardInformation.put(currentLine, bidLineInt);

            }

            // a String array where every item in the array is a line from the file
            // cards in a array
            String[] fileArray = fileData.split("\n");

            // the types of hands
            int onePair = 0;
            int twoPair = 0;
            int threeKind = 0;
            int fullHouse = 0;
            int fourOfKind = 0;
            int fiveKind = 0;
            int highCard = 0;
            // total bid amounts
            int totalBid = 0;
            int totalJackBid = 0;
            // hands ranking
            int rank = 0;
            int rankJack = 0;

            // object arraylist to store specific cards, hand type, rank and bid at certain indexes
            ArrayList<Hand> handsList = new ArrayList<>();
            ArrayList<Hand> jackHandsList = new ArrayList<>();

            // Go through each of the hands
            for (String line : fileArray) {
                // Make the hand into a array
                String[] lineList = line.split(",");
                String[] jackLineList = line.split(",");

                // Part 3: check the hands that have a jack
                if(line.contains("Jack")){
                    // get the highest frequency or the biggest card by calling the highestFrequency method
                    String replace = highestFrequency(jackLineList);
                    // replace every jack with the highest frequency
                    for(int i = 0; i < lineList.length; i++){
                        if(jackLineList[i].equals("Jack")){
                            jackLineList[i] = replace;
                        }
                    }
                }

                // count will help us determine the hand type
                int count = 0;
                int jackCount = 0;

                // Nested for loop to count how many similarities
                for(int i = 0; i < lineList.length; i++){
                    for(int j = i+1; j < lineList.length; j++){
                        if(lineList[i].equals(lineList[j])){
                            count ++;
                        }
                    }
                }

                // Part 3: Nested for loop to count how many similarities in the hands that got replaced of Jacks
                for(int i = 0; i < jackLineList.length; i++){
                    for(int j = i+1; j < jackLineList.length; j++){
                        if(jackLineList[i].equals(jackLineList[j])){
                            jackCount ++;
                        }
                    }
                }

                // get the hands bid value
                int currentBid = cardInformation.get(line);

                // All distinct
                if(count == 0 ){
                    highCard += 1;
                }

                // One pair if two cards are the same
                if (count == 1 ) {
                    onePair += 1;

                }
                // Two pair if two different cards are the same with the other two different cards
                if (count == 2) {
                    twoPair += 1;
                }
                // Two pair if two different cards are the same with the other two different cards
                if(count == 3 ){
                    threeKind += 1;
                }

                if(count == 4){
                    fullHouse += 1;
                }

                if(count == 6 ){
                    fourOfKind += 1;
                }

                if(count == 10 ){
                    fiveKind += 1;
                }

                // get the rank specific to the hand type count
                rank = getRank(count);
                rankJack = getRank(jackCount);

                // Add each hands information into a hand object then into a array list
                handsList.add(new Hand(line, currentBid, rank, lineList, cardInformation));
                jackHandsList.add(new Hand(line, currentBid, rankJack, lineList, cardInformation));
            }

            sortHands(handsList, false);
            sortHands(jackHandsList, true);

            int sortRanks = 1;
            for (Hand hand : handsList) {
                hand.setRank(sortRanks);
                sortRanks++;
            }
            // sort the jack hand list
            sortRanks = 1;
            for(Hand jackHand : jackHandsList){
                jackHand.setRank(sortRanks);
                sortRanks++;
            }

            for (Hand hand : handsList) {
                int currentRank = hand.getRank();
                int currentBid = hand.getBid();
                totalBid += currentRank * currentBid;

//                System.out.println("Hand Rank: " + currentRank + ", Bid: " + currentBid + ", Total Bid: " + totalBid );
            }

            // jack

            for (Hand jackHand : jackHandsList) {
                int currentRank = jackHand.getRank();
                int currentBid = jackHand.getBid();
                totalJackBid += currentRank * currentBid;
                System.out.println("Hand Rank: " + currentRank + ", Bid: " + currentBid + ", Total Jack Bid: " + totalJackBid + ", Hand Type: " + jackHand.getHand() );
            }

            System.out.println(
                    "Number of five of a kind hands: "+ fiveKind + "\n"  +
                            "Number of full house hands: "+ fullHouse + "\n" +
                            "Number of four of a kind hands: "+ fourOfKind + "\n" +
                            "Number of three of a kind hands: "+ threeKind + "\n" +
                            "Number of two pair hands: "+ twoPair + "\n" +
                            "Number of one pair hands: "+ onePair + "\n" +
                            "Number of high card hands: "+ highCard + "\n" +
                            "Total Bid Value: " + totalBid + "\n" +
                            "Total Bid Value With Jacks Wild: " + totalJackBid
            );

        }
        catch (FileNotFoundException fe) {
            System.out.println("File was not found");
            System.exit(1);
        }

    }

    public static Integer rankCard(String card) {
        HashMap<String, Integer> cardRank = new HashMap<>();
        cardRank.put("Ace", 14);
        cardRank.put("King", 13);
        cardRank.put("Queen", 12);
        cardRank.put("Jack", 11);
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

    public static Integer rankCardJack(String card) {
        HashMap<String, Integer> cardRank = new HashMap<>();
        cardRank.put("Ace", 14);
        cardRank.put("King", 13);
        cardRank.put("Queen", 12);
        cardRank.put("Jack", 1);
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
     *
     * @return a String representing a formatted sentence showing the future age
     */

    public static void sortHands(List<Hand> hands, boolean jack) {
        for (int i = 0; i < hands.size() - 1; i++) {
            for (int j = 0; j < hands.size() - i - 1; j++) {
                Hand one = hands.get(j);
                Hand two = hands.get(j + 1);

                int rankTest = Integer.compare(one.getRank(), two.getRank());

                if (rankTest == 0) {
                    if (jack) {
                        rankTest = compareHands(one, two, true);
                    }
                    else{
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

    public static int compareHands(Hand one, Hand two, boolean jack) {
        for (int i = 0; i < 5; i++) {
            int cardComparison = 0;

            if(!jack) {
                cardComparison = rankCard(one.cards[i]).compareTo(rankCard(two.cards[i]));
            }
            else{
                cardComparison = rankCardJack(one.cards[i]).compareTo(rankCardJack(two.cards[i]));
            }

            if (cardComparison != 0) {
                return cardComparison;
            }
        }
        return 0;
    }

    public static String highestFrequency(String[] cardList){
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
                if (rankCardJack(maxFreq) < rankCardJack(cardList[i])) {
                    maxFreq = cardList[i];
                }
            }

            if (count > maxcount) {
                maxcount = count;
                maxFreq = cardList[i];
            }
        }
            int maxRank = 0;
            if(maxFreq.equals("Jack") || maxcount == 1){
                for(int l = 0; l <cardList.length; l++){
                    if(!cardList[l].equals("Jack")) {
                        int valueRank = rankCard(cardList[l]);
                        if(valueRank > maxRank){
                            maxRank = valueRank;
                            maxFreq = cardList[l];

                        }
                    }
                }
            }

        return maxFreq;
    }

    public static int getRank(int count){
        int rank = 0;

        if(count == 0 ){
            rank = 1;
        }

        if (count == 1 ) {
            rank = 2;
        }

        if (count == 2) {
            rank = 3;
        }

        if(count == 3 ){
            rank = 4;
        }

        if(count == 4){
            rank = 5;
        }

        if(count == 6 ){
            rank = 6;
        }

        if(count == 10 ){
            rank = 7;
        }
        return rank;
    }


}
