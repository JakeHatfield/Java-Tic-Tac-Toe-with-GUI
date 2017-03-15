import javax.swing.*;

/**
 * Creates a player. Holds their turn value (player order), their square value, their name, and their image.
 * 
 * @author Jake Hatfield
 * @version 5/19/16
 */
public class Player implements Name
{
    private int playerOrder;
    private int squareValue;
    private String name;
    private ImageIcon playerImage;

    /**
     * Creates the player and holds their turn value (player order), their square value,
     * their name, and their image.
     * 
     * @param playerOrder  the player order or turn value. ie: player 1 = 1
     * @param squareValue  the square value for the players image ie: player 1 = 11
     * @param name  the name of the player
     * @param image  the image of the player, png or jpg image file as an ImageIcon
     */
    public Player(int playerOrder, int squareValue, String name, ImageIcon image)
    {
        this.playerOrder = playerOrder;
        this.squareValue = squareValue;
        this.name = name;
        playerImage = image;
    }

    /**
     * Changes the name of the player.
     * 
     * @param name  the new name for the player
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the name of the player.
     * 
     * @retrun  the name of the player
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the image of the player.
     * 
     * @param image  the image of the player in jpg or png fileformat
     */
    public void setImage(ImageIcon image)
    {
        playerImage = image;
    }

    /**
     * Gets the players image.
     * 
     * @return  the image of the player
     */
    public ImageIcon getPlayerImage()
    {
        return playerImage;
    }

    /**
     * Gets the player order or turn order for the player.
     * 
     * @return  gives the player order or turn order for the player
     */
    public int getPlayerOrder()
    {
        return playerOrder;
    }

    /**
     * Gets the square value for the player. This value is where the image is stored.
     * 
     * @return the square value for the player
     */
    public int getSquareValue()
    {
        return squareValue;
    }
}
