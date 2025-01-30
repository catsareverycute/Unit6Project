import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        // step 1: create a file object
        File f = new File("src/input");

        String fileData = "";
        String bidData = "";
        HashMap<String, Integer> cardInformation = new HashMap<>();
        try {

            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String currentLine = s.nextLine();
                int verticalLine = currentLine.indexOf("|");
                String bidLine = currentLine.substring(verticalLine+1);
                bidData += bidLine + "\n";
                int bidLineInt = Integer.parseInt(bidLine);
                currentLine = currentLine.substring(0, verticalLine);
                fileData += currentLine + "\n";
                cardInformation.put(currentLine, bidLineInt);
            }

            System.out.println(cardInformation);


            String[] bidArray = bidData.split("\n"); // bid values array
            System.out.println(Arrays.toString(bidArray));
            // a String array where every item in the array is a line from the file
            String[] fileArray = fileData.split("\n");
            System.out.println(Arrays.toString(fileArray));
            int onePair = 0;
            int twoPair = 0;
            int threeKind = 0;
            int fullHouse = 0;
            int fourOfKind = 0;
            int fiveKind = 0;
            int highCard = 0;
            int totalBid = 0;
            int rank = 0;

            ArrayList<Hand> handsList = new ArrayList<>();

            for (String line : fileArray) {
                System.out.println(line);
                String[] lineList = line.split(",");
                int count = 0;
                for(int i = 0; i < lineList.length; i++){
                    for(int j = i+1; j < lineList.length; j++){
                        if(lineList[i].equals(lineList[j])){

                            count ++;
                        }
                    }
                }
                int currentBid = cardInformation.get(line);
                System.out.println(currentBid);
                System.out.println(count);


                if (count == 1) {
                    onePair += 1;

                    System.out.println("One Pair");
                    rank = 2;

                }
                if (count == 2) {
                    twoPair += 1;

                    System.out.println("Two Pair");
                    rank = 3;

                }
                if(count == 3){
                    threeKind += 1;

                    System.out.println("Three Kind");
                    rank = 4;
                }

                if(count == 0){
                    highCard += 1;
                    System.out.println("High card");
                    rank = 1;
                }

                if(count == 4){
                    fullHouse += 1;

                    System.out.println("Full House");
                    rank = 5;
                }

                if(count == 6){
                    fourOfKind += 1;

                    System.out.println("Four Kind ");
                    rank = 6;
                }

                if(count == 10){
                    fiveKind += 1;

                    System.out.println("Five Kind ");
                    rank = 7;
                }

                handsList.add(new Hand(line, currentBid, rank, lineList, cardInformation));
            }
            
            sortHands(handsList);

            int sortRanks = 1;
            for (Hand hand : handsList) {
                hand.setRank(sortRanks);
                sortRanks++;
            }

            for (Hand hand : handsList) {
                int currentRank = hand.getRank();
                int currentBid = hand.getBid();
                totalBid += currentRank * currentBid;

                System.out.println("Hand Rank: " + currentRank + ", Bid: " + currentBid + ", Total Bid: " + totalBid );
            }


            System.out.println(
                    "Number of five of a kind hands: "+ fiveKind + "\n"  +
                            "Number of full house hands: "+ fullHouse + "\n" +
                            "Number of four of a kind hands: "+ fourOfKind + "\n" +
                            "Number of three of a kind hands: "+ threeKind + "\n" +
                            "Number of two pair hands: "+ twoPair + "\n" +
                            "Number of one pair hands: "+ onePair + "\n" +
                            "Number of high card hands: "+ highCard + "\n" +
                            "Total Bid Value: " + totalBid
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

    public static class Hand {
        String hand;
        int bid;
        int rank;
        String[] cards;
        HashMap<String, Integer> cardCount;

        public Hand (String hand, int bid, int rank, String[] cards, HashMap<String, Integer> cardCount) {
            this.hand = hand;
            this.bid = bid;
            this.rank = rank;
            this.cards = cards;
            this.cardCount = cardCount;
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
    }

    public static void sortHands(List<Hand> hands) {
        for (int i = 0; i < hands.size() - 1; i++) {
            for (int j = 0; j < hands.size() - i - 1; j++) {
                Hand one = hands.get(j);
                Hand two = hands.get(j + 1);

                int rankTest = Integer.compare(one.getRank(), two.getRank());

                if (rankTest == 0) {
                    rankTest = compareHands(one, two);
                }

                if (rankTest > 0) {
                    hands.set(j, two);
                    hands.set(j + 1, one);
                }
            }
        }
    }

    public static int compareHands(Hand one, Hand two) {
        for (int i = 0; i < 5; i++) {
            int cardComparison = rankCard(one.cards[i]).compareTo(rankCard(two.cards[i]));
            if (cardComparison != 0) {
                return cardComparison;
            }
        }
        return 0;
    }


}
