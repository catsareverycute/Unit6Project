import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        // step 1: create a file object
        Hand h = new Hand();
        String fileData = getFileData("src/input_file");
        HashMap<String, Integer> cardInformation = new HashMap<>();
        cardInformation = getFileDataMap("src/input_file");

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
            String[] jackLineList = new String[lineList.length];

            jackLineList = h.replaceAllJacks(line);

            // count will help us determine the hand type
            int count = h.countSimilarities(lineList);
            int jackCount = h.countSimilarities(jackLineList);

            // get the hands bid value
            int currentBid = cardInformation.get(line);

            // All distinct
            if (count == 0) {
                highCard += 1;
            }

            // One pair if two cards are the same
            if (count == 1) {
                onePair += 1;
            }
            // Two pair if two different cards are the same with the other two different cards
            if (count == 2) {
                twoPair += 1;
            }
            // Two pair if two different cards are the same with the other two different cards
            if (count == 3) {
                threeKind += 1;
            }

            if (count == 4) {
                fullHouse += 1;
            }

            if (count == 6) {
                fourOfKind += 1;
            }

            if (count == 10) {
                fiveKind += 1;
            }

            // get the rank specific to the hand type count
            rank = h.getRank(count);
            rankJack = h.getRank(jackCount);

            // Add each hands information into a hand object then into a array list
            handsList.add(new Hand(line, currentBid, rank, lineList, cardInformation));
            jackHandsList.add(new Hand(line, currentBid, rankJack, lineList, cardInformation));
        }
        System.out.println(
                "Number of five of a kind hands: " + fiveKind + "\n" +
                        "Number of full house hands: " + fullHouse + "\n" +
                        "Number of four of a kind hands: " + fourOfKind + "\n" +
                        "Number of three of a kind hands: " + threeKind + "\n" +
                        "Number of two pair hands: " + twoPair + "\n" +
                        "Number of one pair hands: " + onePair + "\n" +
                        "Number of high card hands: " + highCard
        );

        h.sortHands(handsList, false);

        int sortRanks = 1;
        for (Hand hand : handsList) {
            hand.setRank(sortRanks);
            sortRanks++;
        }

        for (Hand hand : handsList) {
            int currentRank = hand.getRank();
            int currentBid = hand.getBid();
            totalBid += currentRank * currentBid;

        }

        System.out.println("Total Bid Value: " + totalBid);

        h.sortHands(jackHandsList, true);

        // sort the jack hand list
        sortRanks = 1;
        for (Hand jackHand : jackHandsList) {
            jackHand.setRank(sortRanks);
            sortRanks++;
        }

        // jack

        for (Hand jackHand : jackHandsList) {
            int currentRank = jackHand.getRank();
            int currentBid = jackHand.getBid();
            totalJackBid += currentRank * currentBid;
        }

        System.out.println("Total Bid Value With Jacks Wild: " + totalJackBid);

    }

    public static String getFileData(String file) {
        File f = new File(file);
        String fileData = "";
        try {
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String currentLine = s.nextLine();
                // index of the vertical line
                int verticalLine = currentLine.indexOf("|");
                // cards
                currentLine = currentLine.substring(0, verticalLine);
                fileData += currentLine + "\n";
                // hashmap to get the bid amount specific to the cards
            }
        } catch (FileNotFoundException fe) {
            System.out.println("File was not found");
            System.exit(1);
        }
        return fileData;
    }

    public static HashMap<String, Integer> getFileDataMap(String file) {
        File f = new File(file);
        HashMap<String, Integer> cardInformation = new HashMap<>();
        try {
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String currentLine = s.nextLine();
                // index of the vertical line
                int verticalLine = currentLine.indexOf("|");
                int bid = Integer.parseInt(currentLine.substring(verticalLine+1));
                // cards
                currentLine = currentLine.substring(0, verticalLine);
                // hashmap to get the bid amount specific to the cards
                cardInformation.put(currentLine, bid );
            }
        } catch (FileNotFoundException fe) {
            System.out.println("File was not found");
            System.exit(1);
        }
        return cardInformation;
    }

}
