import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

/**
 * Keeps track of the turns. 
 * 
 * @author Jake Hatfield
 * @version 5/19/16
 */
public class TurnKeeper
{
    public ScoreKeeper scoreKeeper;
    private GUI gui;
    public int turn;
    private Random random;
    private boolean newGame;

    /**
     * Sets the game to turn 1, and creates a new ScoreKeeper. Also sets the newGame boolean to true
     * so the player can change their image.
     * 
     * @param gui  takes a gui and connects it to the turnKeeper
     */
    public TurnKeeper(GUI gui)
    {
        scoreKeeper = new ScoreKeeper();
        this.gui = gui;
        turn = 1;
        newGame = true;
    }

    /**
     * Returns a random int to determine who goes first after the first game. 
     * If it is a 1, player 1 goes first, if it is a 0, it is set to 2 and player 2 goes first.
     * 
     * @return  0 or 1 to determine who goes first
     */
    private int randomTurn()
    {
        random = new Random();
        int newTurn = random.nextInt(2);
        if(newTurn == 0)
            newTurn = 2;
        else{}
        return newTurn;
    }

    /**
     * Passes the turn. This is run after the player clicks on a square. 
     * First it increases the turn, then mods it by two. If it is 1, it is player 1's turn next.
     * If it is a 0, it is set to 2 and it is player 2's turn next. Then it changes the name on the
     * turn panel if their isn't a winner. If there is a winner, it chacks for a loser.
     */
    public void passTurn()
    {
        turn++;
        turn = turn % 2;
        if(turn == 0)
            turn = 2;

        //Changes the name on the playerTurns bar    
        if(!scoreKeeper.checkForWinner())
            if(getTurnValue() == 1)
                gui.turnIndicator.setText(gui.playerOne.getName() + "'s Turn");
            else if(getTurnValue() == 2)
                gui.turnIndicator.setText(gui.playerTwo.getName() + "'s Turn");
            else{}

        if(!scoreKeeper.checkForWinner())
            if(checkForLoser())
                gui.loserDance();

    }

    /**
     * Gets the turn value.
     * 
     * @return  the turn value
     */
    public int getTurnValue()
    {
        return turn;
    }

    /**
     * This method is called when the player clicks on a square. The square passes its
     * value through the paramater. The square score is then changed and the image is changed.
     * The game is checked for a winner, and then the turn is passed.
     * 
     * @param sqrNum  the square number that was clicked on
     */
    public void takeTurn(int sqrNum)
    {
        newGame = false;
        scoreKeeper.setScore(sqrNum, getTurnValue()); 
        gui.changeImage(sqrNum, getTurnValue());

        if(scoreKeeper.checkForWinner())
        {
            for(int i=1; i<=9; i++) // set values to non default so they cant continue playing
                if(scoreKeeper.getScore(i) == -2)
                    scoreKeeper.setScore(i, 7);  // 7 > 6 so no combos make this a winner
            gui.winnerDance();
        }

        passTurn();
    }

    /**
     * This method is called from the menubar. It resets the game. It first sets the newGame
     * to true so player images can be changed. It then sets all squares to -2 and changes the 
     * images accordingly. Then it get a random number and sets the turn equal to that number.
     */

    public void newGame()
    {
        newGame = true;
        for(int i=1; i<=9; i++)
        {
            scoreKeeper.setScore(i, -2);
            gui.changeImage(i, -2);
        }
        turn = randomTurn();
        if(turn == 1)
            gui.turnIndicator.setText(gui.playerOne.getName() + "'s Turn");
        else
            gui.turnIndicator.setText(gui.playerTwo.getName() + "'s Turn");
    }

    /**
     * Checks if there is a loser by checking if all square values are set to -2.
     * 
     * @return  true if the game is over and false otherwise
     */
    public boolean checkForLoser()
    {
        int count = 0;
        for(int i=1; i<=9; i++)
            if(scoreKeeper.getScore(i) != -2)
                count++;
            else{}

        if(count == 9)
            return true;
        else return false;
    }

    /**
     * Checks if the newGame variable is true.
     * 
     * @return  true if it is a new game, false if the first turn has been taken
     */
    public boolean checkNewGame()
    {
        return newGame;
    }
}
