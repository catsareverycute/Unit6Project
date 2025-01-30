
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        // step 1: create a file object
        File f = new File("/Users/andyfang/IdeaProjects/unit 6/src/input");

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

            ArrayList<String> highCardList = new ArrayList();
            ArrayList<String> onePairList = new ArrayList();
            ArrayList<String> twoPairList = new ArrayList();
            ArrayList<String> threeKindList = new ArrayList();
            ArrayList<String> fullHouseList = new ArrayList();
            ArrayList<String> fourKindList = new ArrayList();
            ArrayList<String> fiveKindList = new ArrayList();

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
                System.out.println(count);


                if (count == 1) {
                    onePair += 1;
                    onePairList.add(line);
                    System.out.println("One Pair");

                }
                if (count == 2) {
                    twoPair += 1;
                    twoPairList.add(line);
                    System.out.println("Two Pair");

                }
                if(count == 3){
                    threeKind += 1;
                    threeKindList.add(line);
                    System.out.println("Three Kind");
                }

                if(count == 0){
                    highCard += 1;
                    highCardList.add(line);
                    System.out.println("High card");
                }

                if(count == 4){
                    fullHouse += 1;
                    fullHouseList.add(line);
                    System.out.println("Full House");
                }

                if(count == 6){
                    fourOfKind += 1;
                    fourKindList.add(line);
                    System.out.println("Four Kind ");
                }

                if(count == 10){
                    fiveKind += 1;
                    fiveKindList.add(line);
                    System.out.println("Five Kind ");
                }
            }
            System.out.println(onePairList);
            System.out.println(twoPairList);
            System.out.println(threeKindList);
            onePairList = order(onePairList);
            twoPairList = order(twoPairList);

            // creating new empty list
            // List Concatenation;
            List<String> concatenated_list = new ArrayList<>();
            concatenated_list.addAll(onePairList);
            concatenated_list.addAll(twoPairList);
            System.out.println(concatenated_list);

            // get the bid value
            System.out.println(cardInformation.get(concatenated_list.get(0)));

            System.out.println(
                    "Number of five of a kind hands: "+ fiveKind + "\n"  +
                            "Number of full house hands: "+ fullHouse + "\n" +
                            "Number of four of a kind hands: "+ fourOfKind + "\n" +
                            "Number of three of a kind hands: "+ threeKind + "\n" +
                            "Number of two pair hands: "+ twoPair + "\n" +
                            "Number of one pair hands: "+ onePair + "\n" +
                            "Number of high card hands "+ highCard
            );

        }
        catch (FileNotFoundException fe) {
            System.out.println("File was not found");
            System.exit(1);
        }

    }

    public static ArrayList<String> order(ArrayList<String> line){
        ArrayList<String> ordered = new ArrayList<>();
        if(line.size() <= 1){
            return line;
        }

        else {
            ordered.add("King,Ace,Ace,Ace,Ace");
        }
        return ordered;
    }


}
