import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;
import java.util.Iterator;

import java.io.File;

/**
 * This creates and displays the GUI for the game. It also contains the images and sets the image.
 * 
 * @author Jake Hatfield
 * @version 5/19/16
 */
public class GUI
{
    private JFrame frame;
    private JPanel gamePanel, topPanel, bottomPanel;
    private JButton[] squares; 

    private TurnKeeper turnKeeper;
    public Player playerOne, playerTwo;
    private JButton playerOneName, playerOneImage, playerTwoName, playerTwoImage;
    private ImageIcon playerOnesImage, playerTwosImage;

    private ArrayList<Player> players;
    private ArrayList<ImageIcon> imageIcons;

    private ImageIcon xImageIcon, oImageIcon, blankSquareImageIcon, x2ImageIcon, o2ImageIcon;
    public JLabel turnIndicator;
    private Border emptyBorder = BorderFactory.createEmptyBorder();

    private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir")); //used to select a custom image file

    /**
     * Creates the players and builds the GUI. Also creates a TurnKeeper.
     */
    public GUI()
    {
        loadDefaultImages();

        playerOne = new Player(1, 11, "Player 1", xImageIcon);
        playerTwo = new Player(2, 12, "Player 2", oImageIcon);
        players = new ArrayList<Player>();
        players.add(playerOne);
        players.add(playerTwo);

        turnKeeper = new TurnKeeper(this);

        makeFrame();
    }

    /**
     * Builds the frame for the GUI. Does this by setting the background color and then calling other methods that add to the frame.
     */
    public void makeFrame()
    {
        frame = new JFrame("Tic-Tac-Toe");
        frame.setResizable(false);

        Container contentPane = frame.getContentPane(); 
        contentPane.setBackground(Color.LIGHT_GRAY);

        makeTitleBar();
        makeGameGrid();
        makeMenuBar();
        makePlayerOnesPanel();
        makePlayerTwosPanel();
        makeTurnPanel();        

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates the title bar for the game.
     */
    private void makeTitleBar()
    {
        JLabel gameTitle = new JLabel("The Best Tic-Tac-Toe Game Ever!", 0); // Game Title
        gameTitle.setFont(new Font("San Serif", Font.BOLD, 48)); // TITLE FONT
        gameTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); //top,left,bottom,right
        frame.add(gameTitle, BorderLayout.NORTH);
    }

    /**
     * Creates the layout and squares for the game. 
     */
    private void makeGameGrid()
    {
        gamePanel = new JPanel(new GridLayout(3,3, 3,3)); //rows, columns, hgap, vgap (bigger=thicker)
        gamePanel.setBackground(Color.WHITE); //sets background white (creates black spaces)
        gamePanel.setBorder(emptyBorder);

        squares = new JButton[10]; // 0 is null, start with 1 to correspond better with squares

        for(int i=1; i<=9; i++)
        {
            final int j = i;
            squares[i] = new JButton(blankSquareImageIcon);
            squares[i].setBackground(Color.BLACK);
            squares[i].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) { 
                        if(turnKeeper.scoreKeeper.getScore(j) == -2) 
                            turnKeeper.takeTurn(j); 
                        ;}
                });
        }

        for(int i=1; i<=9; i++)
        {
            gamePanel.add(squares[i]);
        }

        frame.add(gamePanel, BorderLayout.CENTER);
    }

    /**
     * Creaates the menubar and menu items. Adds the items to the bar.
     */
    private void makeMenuBar()
    {
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menubar.add(fileMenu);

        JMenuItem newItem = new JMenuItem("New Game");
        newItem.setMnemonic(KeyEvent.VK_N);
        KeyStroke newGameShortcut = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
        newItem.setAccelerator(newGameShortcut);
        newItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { turnKeeper.newGame(); }
            });

        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.setMnemonic(KeyEvent.VK_Q);
        quitItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { System.exit(0); }
            });

        JMenu helpMenu = new JMenu("Help");
        menubar.add(helpMenu);
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { showAbout(); }
            });

        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { showHelp(); }
            });

        fileMenu.add(newItem);
        fileMenu.add(quitItem);
        helpMenu.add(aboutItem);
        helpMenu.add(helpItem);
    }

    /**
     * Creates Player One's panel. This is added to the West side of the border layout. The player panel features
     * the player's name, the player's image, and the functionality to make it work.
     */
    public void makePlayerOnesPanel()
    {
        JPanel playerOnePanel = new JPanel(new GridLayout(3,1));
        playerOnePanel.setBackground(Color.LIGHT_GRAY);
        frame.add(playerOnePanel, BorderLayout.WEST); //puts it on the WEST of the frame
        playerOneName = new JButton(playerOne.getName()); //String name, 0 centers text
        playerOneName.setFont(new Font("San Serif", Font.BOLD, 20)); // Player's Name Font
        playerOneName.setBackground(Color.LIGHT_GRAY);

        playerOneName.setBorder(emptyBorder);
        playerOneName.setFocusPainted(false);

        playerOnePanel.add(playerOneName); //adds player ones name to to grid spot 1
        playerOneImage = new JButton(playerOne.getPlayerImage()); //creates button with player 1's image
        playerOneImage.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { 
                    createImageOptionPanel(playerOne);
                    ;}
            });
        playerOneName.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { 
                    try {
                        playerOne.setName(inputNamePopUp(playerOne));
                        updatePlayerName(playerOneName, playerOne);
                    }
                    catch(NoNameException ex) {
                        JOptionPane.showMessageDialog(frame, (ex)); 
                    }
                    ;}
            });
        playerOneImage.setPreferredSize(new Dimension(180,180)); //sets default size 
        playerOneImage.setBackground(Color.BLACK); //sets background to black
        playerOnePanel.add(playerOneImage); //adds button
        playerOnePanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); //top,left,bottom,right
    }

    /**
     * Creates Player Two's panel. This is added to the East side of the border layout. The player panel features
     * the player's name, the player's image, and the functionality to make it work.
     */
    public void makePlayerTwosPanel()
    {
        JPanel playerTwoPanel = new JPanel(new GridLayout(3,1));
        playerTwoPanel.setBackground(Color.LIGHT_GRAY);
        frame.add(playerTwoPanel, BorderLayout.EAST);
        playerTwoName = new JButton(playerTwo.getName()); //Sets PlayerName to JButton
        playerTwoName.setFont(new Font("San Serif", Font.BOLD, 20)); // Player's Name Font
        playerTwoName.setBorder(emptyBorder);
        playerTwoName.setFocusPainted(false);

        playerTwoName.setBackground(Color.LIGHT_GRAY);
        playerTwoPanel.add(playerTwoName, BorderLayout.NORTH);
        playerTwoImage = new JButton(playerTwo.getPlayerImage()); //Player 2's Image
        playerTwoImage.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { 
                    createImageOptionPanel(playerTwo);
                    ;}
            });
        playerTwoName.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { 
                    try {
                        playerTwo.setName(inputNamePopUp(playerTwo));
                        updatePlayerName(playerTwoName, playerTwo);
                    }
                    catch(NoNameException ex) {
                        JOptionPane.showMessageDialog(frame, (ex));
                    }
                    ;}

            });
        playerTwoImage.setPreferredSize(new Dimension(180, 180));
        playerTwoImage.setBackground(Color.BLACK);
        playerTwoPanel.add(playerTwoImage);
        playerTwoPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); //top,left,bottom,right   
    }

    /**
     * Creates the turn panel that is displayed on the South of the border layout. This indicates
     * whose turn it is and any additional turn information needed.
     */
    public void makeTurnPanel()
    {
        JPanel turnPanel = new JPanel();
        turnPanel.setBackground(Color.LIGHT_GRAY);
        frame.add(turnPanel, BorderLayout.SOUTH);

        turnIndicator = new JLabel(playerOne.getName() + "'s Turn");
        turnIndicator .setFont(new Font("San Serif", Font.BOLD, 36)); // Bottom panel Font
        turnPanel.add(turnIndicator);
        turnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
    }

    /**
     * Changes the image for squares. Checks the sqrNum to determine which square to change. Passes the imgVal to determine
     * what picture to set the square to.
     * 
     * @param sqrNum  this is the square that needs to be changed. 1-9 in the square grid. 11 for Player 1. 12 for Player 2.
     * @param imgVal  this dictates what image to set the square to. 1 for Player 1. 2 for Player 2. -2 for the BlankSquareImage.
     */
    public void changeImage(int sqrNum, int imgVal)  //sqrNum is square#
    {

        if(imgVal == 1)
            if(sqrNum == 11)
                playerOneImage.setIcon(playerOne.getPlayerImage());
            else if(sqrNum == 12)
                playerTwoImage.setIcon(playerTwo.getPlayerImage());
            else squares[sqrNum].setIcon(playerOne.getPlayerImage());//new ImageIcon(getClass().getClassLoader().getResource(playerOne.image.getFilename())));
        else if(imgVal == 2)
            if(sqrNum == 11)
                playerOneImage.setIcon(playerOne.getPlayerImage());
            else if(sqrNum == 12)
                playerTwoImage.setIcon(playerTwo.getPlayerImage());
            else squares[sqrNum].setIcon(playerTwo.getPlayerImage());//new ImageIcon(getClass().getClassLoader().getResource(playerTwo.image.getFilename())));
        else if(imgVal == -2)
            squares[sqrNum].setIcon(blankSquareImageIcon);//new ImageIcon(getClass().getClassLoader().getResource(blankSquareImage.getFilename())));
        else{}

    }

    /**
     * This method is ran after a winner has been found. It displays the winners name on a popup and includes the player's image.
     */
    public void winnerDance()
    {
        if(turnKeeper.getTurnValue() == 1)
        {    
            turnIndicator.setText(playerOne.getName() + " WON!");
            JOptionPane.showMessageDialog(frame, (playerOne.getName() + " WON!"), "Congratulations!", 0, playerOne.getPlayerImage());
        }
        else
        {
            turnIndicator.setText(playerTwo.getName() + " WON!");    
            JOptionPane.showMessageDialog(frame, (playerTwo.getName() + " WON!"), "Congratulations!", 0, playerTwo.getPlayerImage());
        }
    }

    /**
     * This method is ran when there are no more empty squares and there is no winner.
     */
    public void loserDance()
    {
        turnIndicator.setText("Press CTRL + N for a new game.");
        JOptionPane.showMessageDialog(frame, "No winner... GAME OVER!");
    }

    /**
     * This changes the player's name on the Player Panel.
     */
    public void updatePlayerName(JButton button, Player player)
    {
        button.setText(player.getName());
    }

    /**
     * Loads the default images so they can be used immediately. XImage and OImage are set for Player 1 and Player 2
     * by default, and the others are preloaded so if the player wants to change their image.
     */
    public void loadDefaultImages()
    {
        blankSquareImageIcon = new ImageIcon("BlankSquareImage.png");
        xImageIcon = new ImageIcon("XImage.png");
        oImageIcon = new ImageIcon("OImage.png");
        x2ImageIcon = new ImageIcon("XImage2.png");
        o2ImageIcon = new ImageIcon("OImage2.png");

        imageIcons = new ArrayList<ImageIcon>();

        imageIcons.add(xImageIcon);
        imageIcons.add(oImageIcon);
        imageIcons.add(x2ImageIcon);
        imageIcons.add(o2ImageIcon);
    }

    /**
     * After clicking on the button, this method brings a popup menu that allows the player to input their name. 
     * 
     * @param player  the player that wants to change their name.
     * @return  returns the name the selected player wants
     */
    public String inputNamePopUp(Player player)
    throws NoNameException
    {
        JOptionPane nameInput = new JOptionPane("Change your name");
        String newName = nameInput.showInputDialog(frame, "What's your name?", "Changing Name...", JOptionPane.QUESTION_MESSAGE);

        // NullPointerException thrown if JOptionPane is cancelled
        try {
            newName = newName.trim();
        }
        catch(Exception e)
        {
            return player.getName();
        }

        if(newName == null || newName.length() == 0)
            throw new NoNameException(player.getName());
        else
            player.setName(newName);

        return player.getName();
    }

    /**
     * This method creates the popup that allows the player to change their image. This method displayes default images and captures the 
     * player's input by options. The option select sets the player's image to the corresponding image. If the first option is chosen, option 0,
     * the player has the option to load in a custom image. If the custom image is chosen, another method is called to deal with the file input.
     */
    public void createImageOptionPanel(Player player)
    {
        if(turnKeeper.checkNewGame())
        {
            int optionNum;
            String x = "Please click on the desired image " + player.getName();
            String y = player.getName() + ", select your image";
            Object[] options = { "Custom Image", xImageIcon, x2ImageIcon, oImageIcon, o2ImageIcon };
            optionNum = JOptionPane.showOptionDialog(null, x, y, JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, player.getPlayerImage(), options, options[0]);

            if(optionNum == 0) // Custom Image
            {
                openFile(player);
            }
            else if(optionNum == 1) // X Image
            {
                player.setImage(xImageIcon);
                changeImage(player.getSquareValue(), player.getPlayerOrder());
            }
            else if(optionNum == 2) // Funny X Image
            {
                player.setImage(x2ImageIcon);
                changeImage(player.getSquareValue(), player.getPlayerOrder());
            }
            else if(optionNum == 3) // O Image
            {
                player.setImage(oImageIcon);
                changeImage(player.getSquareValue(), player.getPlayerOrder());
            }
            else if(optionNum == 4) // Funny O Image
            {
                player.setImage(o2ImageIcon);
                changeImage(player.getSquareValue(), player.getPlayerOrder());
            }
            else{} // do nothing
        }
        else { // Don't change the image in the middle of the game.
            JOptionPane.showMessageDialog(frame, "You can only change your image before the game starts.");
        }
    }

    /**
     * Called from the menubar. This shows info about the game in a popup.
     */
    private void showAbout()
    {
        JOptionPane.showMessageDialog(frame, "This is the best Tic-Tac-Toe game ever! \nCoded and played by: Jake Hatfield");
    }

    /**
     * Called from the menubar. This shows helpful game information in a popup.
     */
    private void showHelp()
    {
        JOptionPane.showMessageDialog(frame, "Click on your name to change your name" +
            "\nClick on your image before a new game to change your image" +
            "\nCustom Images must be a .png or .jpg file" +
            "\n\nPress CTRL + N to start a new game \n\nHave fun!");
    }

    /**
     * This method is called after the player choses to select a custom image. 
     * A filechooser is created and allows the player to chose a file. After the image is selected,
     * the file extension is checked to confirm it is a jpg or png filetype. If it is, it is set to the player's image. 
     * If it isn't, a message is given that the file needs to be one of the two filetypes.
     * 
     * @param player  the player that is changing their image.
     */
    private void openFile(Player player)
    {
        int returnVal = fileChooser.showOpenDialog(frame);

        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;  // cancelled - if not set, throws a null pointer exception
        }

        File customImageFile = fileChooser.getSelectedFile(); // allows user to select file
        String customImageFilename = customImageFile.getAbsolutePath(); //the filename of the selected image
        int len = customImageFilename.length(); // lenth of the string
        String ext = customImageFilename.substring(len-3); // sets ext to last 3 chars of filename which should be extension
        ext = ext.toLowerCase(); //sets it to lowercase for comparison below

        if(ext.equals("png") || ext.equals("jpg")) // ensures the filename is either a png or jpg
        {
            player.setImage(new ImageIcon(customImageFilename));
            changeImage(player.getSquareValue(), player.getPlayerOrder());
        }
        else{
            JOptionPane.showMessageDialog(frame, "Image file must be a .png or .jpg file.");
        }
    }
}
