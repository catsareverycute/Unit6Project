
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        // step 1: create a file object
        File f = new File("C:\\Users\\BT_2S14_02\\IdeaProjects\\Unit 6 Project\\src\\Input");

        String fileData = "";
        try {

            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String currentLine = s.nextLine();

                int verticalLine = currentLine.indexOf("|");
                currentLine = currentLine.substring(0, verticalLine);
                fileData += currentLine + "\n";


            }

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

            for (String line : fileArray) {
                System.out.println(line);
                String[] lineList = line.split(",");
                System.out.println(Arrays.toString(lineList));
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

                }
                if (count == 2) {
                    twoPair += 1;

                }
                if(count == 3){
                    threeKind += 1;
                }


            }

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

    public static int checkCard(String value){
        if(value.equals("Ace")){
            return 0;
        }
        if(value.equals("King")){
            return 1;
        }
        if(value.equals("Queen")){
            return 2;
        }
        if(value.equals("Jack")){
            return 3;
        }
        if(value.equals("10")){
            return 4;
        }
        if(value.equals("9")){
            return 5;
        }
        if(value.equals("8")){
            return 6;
        }
        if(value.equals("7")){
            return 7;
        }
        if(value.equals("6")){
            return 8;
        }
        if(value.equals("5")){
            return 9;
        }
        if(value.equals("4")){
            return 10;
        }
        if(value.equals("3")){
            return 11;
        }
        if(value.equals("2")){
            return 12;
        }
        else{
            return 13;
        }

    }

}
