
/**
 * This exception is thrown when a name is not entered. This is not the same as a NullPointerException.
 * 
 * @author Jake Hatfield
 * @version 5/19/16
 */
public class NoNameException extends Exception
{
    private String name;

    /**
     * Creates the expception and sets the name for the exception.
     * 
     * @param str  the name passed in by the exception
     */
    public NoNameException(String str)
    {
        name = str;
    }

    /**
     * Gets the name from the name field.
     * 
     * @return  the name from this exception
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Changes the toString so valuable information is printed for this exception.
     * 
     * @return  gives detailed information about the exception.
     */
    public String toString()
    {
        return "Player's name can't be blank, sticking with " + getName();
    }
}
