
/**
 * Keeps track of the score for each square. Used to set the images of the squares and to check
 * for a winner or if the game is over.
 * 
 * @author Jake Hatfield
 * @version 5/19/16
 */
public class ScoreKeeper
{
    private int[] score;

    /**
     * Creates an Array that holds the score for each square. Square 0 is null.
     * The default value is set at -2 as a BlankSquare.
     */
    public ScoreKeeper()
    {
        score = new int[10];
        for(int i=1; i<=9; i++)
            score[i] = -2;
    }

    /**
     * Checks if their is winner by adding the value of the winning combinations of squares
     * to see if the value is 3 for player 1 or 6 for player 2. Returns true if there is a winner.
     * 
     * @return  true if there is a winner, false if not
     */
    public boolean checkForWinner()
    {
        if(getScore(1) + getScore(2) + getScore(3) == 3 || getScore(1)+ getScore(2) + getScore(3) == 6)
            return true;
        else if(getScore(1) + getScore(5) + getScore(9) == 3 || getScore(1) + getScore(5) + getScore(9) == 6) 
            return true;
        else if(getScore(1) + getScore(4) + getScore(7) == 3 || getScore(1)+ getScore(4) + getScore(7) == 6) 
            return true;
        else if(getScore(2) + getScore(5) + getScore(8) == 3 || getScore(2)+ getScore(5) + getScore(8) == 6) 
            return true;
        else if(getScore(3) + getScore(6) + getScore(9) == 3 || getScore(3)+ getScore(6) + getScore(9) == 6) 
            return true;
        else if(getScore(4) + getScore(5) + getScore(6) == 3 || getScore(4)+ getScore(5) + getScore(6) == 6) 
            return true;
        else if(getScore(7) + getScore(8) + getScore(9) == 3 || getScore(7)+ getScore(8) + getScore(9) == 6) 
            return true;
        else if(getScore(7) + getScore(5) + getScore(3) == 3 || getScore(7)+ getScore(5) + getScore(3) == 6) 
            return true;
        else return false;
    }

    /**
     * Gets the score for a selected square.
     * 
     * @param x  the square the score needs to be checked
     * @return  the score for the square in question
     */
    public int getScore(int x)
    {
        return score[x];
    }

    /**
     * Sets the score for a square. 
     * 
     * @param x  the square in question
     * @param y  the score of the corresponding player's score or the BlankScore
     */
    public void setScore(int x, int y)
    {
        score[x] = y;
    }
}
