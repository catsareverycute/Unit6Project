import java.util.HashMap;
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

    /**
     * Constructor for the Person class. This creates a new instance of a Person given
     * the below parameters.
     *
     * @param n represents the name of the Person
     * @param a represents the age of the Person
     * @param h represents the hobby of the person
     */


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

    public String getHand() {
        return hand;
    }
}