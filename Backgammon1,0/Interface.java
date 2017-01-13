
/**
 * The Interface class contains all the methods used to display the current situation in the game,
 * such as the board, moves and scores; it also displays all the menus and executes the user's
 * commands (the main menu can display rules as well).
 * 
 * @author Mikolaj Gackowski 
 * @version 1.0
 */
public class Interface
{
    private Board testBoard = new Board();
    private Player player = new Player();

    private boolean triangleDown = true; //little aesthetic detail

    /**
     * Constructor for objects of class Interface, but it contains nothing.
     */
    public Interface()
    {

    }

    /**
     * Displays a start menu, allowing the user to choose whether to create a new game,
     * load an existing game, display the rules or quit.
     * 
     * @param  None
     * @return     An integer (1 - New game, 2 - Load game, 3 - Rules, any other - Invalid)
     */
    public int displayStartMenu()
    {
        int choice; //for storing the choice

        System.out.println(" ___          _                                  ");
        System.out.println("| _ ) __ _ __| |____ _ __ _ _ __  _ __  ___ _ _  ");
        System.out.println("| _ \\/ _` / _| / / _` / _` | '  \\| '  \\/ _ \\ ' \\ ");
        System.out.println("|___/\\__,_\\__|_\\_\\__, \\__,_|_|_|_|_|_|_\\___/_||_|");
        System.out.println("                 |___/   by Mikolaj Gackowski    ");
        System.out.println("");
        System.out.println("+-----------------------------------------------+");
        System.out.println("|                   MAIN MENU                   |");
        System.out.println("+-----------------------------------------------+");
        System.out.println("| 1. Create new game                            |");
        System.out.println("| 2. Load game from file                        |");
        System.out.println("| 3. Rules                                      |");
        System.out.println("| 4. Quit                                       |");
        System.out.println("+-----------------------------------------------+");
        System.out.println("| Enter desired number below and press [enter]. |");
        System.out.println("+-----------------------------------------------+");
        System.out.println("");

        choice = Genio.getInteger();

        switch (choice)
        {
            case 1: return 1;
            case 2: return 2;
            case 3: System.out.print('\f');
                    System.out.println("[=======================  RULES OF BACKGAMMON  ====================]");
                    System.out.println("");
                    System.out.println("Players take turns ro roll two die. Each number rolled represents");
                    System.out.println("a move that can be played. You get two moves per turn, but if you");
                    System.out.println("Uroll a double, you can play each die twice!");
                    System.out.println("The player's goal is to move all their checkers to their home board.");
                    System.out.println("The red player moved one checker at a time in the direction of");
                    System.out.println("ascending numbers (e.g a die of 5 will allow them to move one");
                    System.out.println("checker from field 3 to 8), and their home board is fields 19-24.");
                    System.out.println("The black player moves in the opposite direction and their home");
                    System.out.println("board is fields 1-6.");
                    System.out.println("When advancing your checkers, you can't land on fields which have");
                    System.out.println("more than one of your enemy's checkers. If you land on a single one");
                    System.out.println("though, that's a 'blot' hit - you can occupy that space and the");
                    System.out.println("enemy's checker gets centered on the bar. Next time they start");
                    System.out.println("their turn they have to use one die to restore them to play.");
                    System.out.println("Once you have all your checkers in your home board, you can bear");
                    System.out.println("them off the board one at a time - but you need to start from the");
                    System.out.println("ones that are the farthest to the finish. Then you lose a point.");
                    System.out.println("Lose all fifteen points to win a game!");
                    System.out.println("");
                    System.out.println("[==========================  [press enter]  ======================]");
                    Genio.getString();
                    return 3;
            case 4: return 4;
            default: System.out.print("Sorry, that option doesn't exist! (press enter to choose again)");
            Genio.getString();
            System.out.print('\f');
            displayStartMenu();            
        }
        
        return 0;
    }

    /**
     * Displays a mid-game menu, designed to allow the user to either start a new turn
     * or save inbetween turns
     * 
     * @param  None
     * @return     An integer from 1 to 4 (1 - Start turn, 2 - Save, 3 - Quit, 4 - Invalid)
     */
    public int displayMidMenu()
    {
        int choice;
        boolean confirm = false;

        System.out.println("+-----------------------------------------------+");
        System.out.println("   " + player.getName() + "'s turn:");
        System.out.println("+-----------------------------------------------+");
        System.out.println("| 1. Roll dice and begin your turn              |");
        System.out.println("| 2. Save game                                  |");
        System.out.println("| 3. Quit to menu                               |");
        System.out.println("+-----------------------------------------------+");
        System.out.println("| Hint: You can enter '-1' instead of choosing  |");
        System.out.println("|    a checker to skip a turn if you're stuck.  |");
        System.out.println("+-----------------------------------------------+");
        System.out.println("| Enter desired number below and press [enter]. |");
        System.out.println("+-----------------------------------------------+");
        System.out.println("");

        while (true)
        {
            choice = Genio.getInteger();

            switch (choice)  //confirmation code
            {
                case 1:  return 1;
                case 2:  return 2;
                case 3: System.out.print("Enter 3 again to confirm.");
                if (confirm = true)
                {
                    return 3;
                }
                confirm = true;
                default: return 4;            
            }
        }
    }

    /**
     * The main method which encompasses all of the program's functions.
     * 
     * @param  None
     * @return    None
     */
    public static void main(String[] args)
    {
        Interface game = new Interface(); //this encompasses everything in the program, basically.
        
        int [] dice;
        int midchoice;  //stores choice in menus at the beginning of each turn
        boolean success; //stores if individual move was successful
        boolean setupSuccess = false; //stores if setting up game was successful

        while(true)
        {
            System.out.print('\f');
            int choice = game.displayStartMenu(); //stores choice in main menu
            
            switch (choice)
            {
                case 1: System.out.print('\f'); 
                        game.testBoard.newGame();
                        System.out.print('\f');
                        game.testBoard.determineFirstPlayer();
                        Genio.getString(); //wait
                        setupSuccess = true;
                        break;
                case 2: System.out.print('\f'); 
                        setupSuccess = game.testBoard.loadGame();
                        break;
                case 3: break;
                case 4: System.exit(0);
                        break;
                default: break;
            }
    
            while(setupSuccess) //perform a full turn
            {
                
                /* Only make the current player relevant */
                if(game.testBoard.playerRed.isActivePlayer())
                {
                    game.player = game.testBoard.playerRed;
                }
                else if(game.testBoard.playerBlack.isActivePlayer())
                {
                    game.player = game.testBoard.playerBlack;
                }
    
                System.out.print('\f');
                game.printScores();
                System.out.println("");
                game.printBoard();
                midchoice = game.displayMidMenu();
    
                switch (midchoice)
                {
                    case 1: dice = game.player.rollDice();
                    
                            do //perform a single move
                            {
                                System.out.print('\f');
                                game.printScores();
                                System.out.println("");
                                game.printBoard();
                                game.printDice(dice);
            
                                do
                                {
                                    success = game.testBoard.makeMove();
                                }
                                while(!success);
            
                                boolean win = game.testBoard.checkForWin();
                                if (win)
                                {
                                    setupSuccess = false;
                                    game.player.resetDie(0);
                                    game.player.resetDie(1);
                                }
                                
                                Genio.getString(); //wait
            
                            }
                            /*repeat until all moves are exhausted*/
                            while(game.player.getDice()[0] +game.player.getDice()[1] + game.player.getDice()[2] + game.player.getDice()[3] != 0);
            
                            /* Switch active player */
                            game.testBoard.playerRed.toggleActivePlayer();
                            game.testBoard.playerBlack.toggleActivePlayer();
                            break;
                            
                    case 2: game.testBoard.saveGame();
                            break;
                            
                    case 3: setupSuccess = false;
                            break;
                            
                    default: System.out.print("You need to choose 1, 2 or 3. (press enter)");
                            Genio.getString();
                            break;
                            
                }
                
            }
        }
    }
    
    /**
     * Used to test the board during early development and manually change array values;
     * left in the code for maintenance.
     * 
     * @param  None
     * @return     None
     */
    public void manualCheckers()
    {
        int a;
        int b;
        System.out.print("Which field do you want to update? - ");
        a = Genio.getInteger();
        System.out.print("Enter new number of checkers: ");
        b = Genio.getInteger();
        testBoard.setCheckers(a,b);

    }

    /**
     * Determines the visual representation of a value in the array, such as a checker, empty space
     * or decoration in two-dimensional space.
     * 
     * @param  triangle - The relevant index of the array
     * @param  vertical - Vertical position on board, measured as distance from the edges
     * @return     A three-character string representing what should be displayed on the board.
     */
    public String printChecker(int triangle, int vertical)
    {
        int [] field = new int[26];

        field = testBoard.getCheckers();
        if (triangle == 0)  //the bar for red checkers
        {
            if (field[triangle] >= vertical)
            {
                return "(R)"; //print a red checker on the bar
            }

            else
            {
                return " | "; //print the decoration for the empty bar
            } 
        }

        else if (triangle == 25) //same as above, for black checkers
        {
            if (field[triangle] <= (vertical*(-1)))
            {
                return "[B]";
            }
            else
            {
                return " | ";
            }
        }

        else if (field[triangle] == 0 && vertical != 1) { //empty spaces on the board
            return " . ";
        }

        else if (field[triangle] == 0 && vertical == 1 && triangleDown) { //empty spaces near the upper edge
            return "\\ /";
        }

        else if (field[triangle] == 0 && vertical == 1 && !triangleDown) { //empty spaces near the lower edge
            return "/ \\";
        }

        else if (field[triangle] > 0) {  //if there are red checkers on the triangle
            if (field[triangle] >= vertical)
            {
                return "(R)";
            }

            else
            {
                return " . ";
            }
        }

        else if (field[triangle] < 0) {  //if there are black checkers on the triangle
            if (field[triangle] <= (vertical*(-1)))
            {
                return "[B]";
            }
            else
            {
                return " . ";
            }
        }

        else {
            return "???"; //so that I know the program is not running correctly
        }
    }

    /**
     * Prints the board in response to the current checker positions stored in the field[]
     * of Board.
     * 
     * @param  None
     * @return    None
     */
    public void printBoard()
    {
        System.out.println("   +-----------------------------------------+  ");
        System.out.println("  || 13 14 15 16 17 18 BAR 19 20 21 22 23 24 || ");
        System.out.println("  ||                    |                    || ");
        triangleDown = true; //aesthetical detail which simulates the triangular decorations

        for(int j=1; j<6; j++) //prints upper half
        {
            System.out.print("  ||"); //left edge
            for(int i=13; i<19; i++)
            {
                System.out.print(printChecker(i,j)); //checks what to display
            }

            System.out.print(" " + printChecker(25,j) + " "); //bar for black checkers

            for(int i=19; i<25; i++)
            {
                System.out.print(printChecker(i,j));
            }
            System.out.println("|| ");//right edge
        }

        System.out.println("  ||____________________|____________________|| ");
        System.out.println("  ||                    |                    || ");
        triangleDown = false;

        for(int j=5; j>0; j--)
        {
            System.out.print("  ||");
            for(int i=12; i>6; i--)
            {
                System.out.print(printChecker(i,j));
            }

            System.out.print(" " + printChecker(0,j) + " ");

            for(int i=6; i>0; i--)
            {
                System.out.print(printChecker(i,j));
            }
            System.out.println("|| ");
        }

        System.out.println("  ||                    |                    || ");
        System.out.println("  || 12 11 10 9  8  7  BAR  6  5  4  3  2  1 || ");
        System.out.println("  |/-----------------------------------------\\| ");
        System.out.println("  /__________________________________ bkgmn___\\ ");
        System.out.println("");
    }

    /**
     * Displays the values of dice which are still available as possible moves (that is, nto equal zero)
     * 
     * @param  dice - An integer array of four dice values
     * @return     None
     */
    public void printDice(int [] dice)
    {
        System.out.println("Available moves:");

        for (int i=0; i<4; i++)
        {
            if (dice[i] != 0) //dice value zero indicates that move is used/unavailable
            {
                System.out.print("[" + dice[i] + "] ");
            }
        }

        System.out.println("");
    }

    /**
     * Displays the scores of players, as well as an indication which one is the active player.
     * 
     * @param  None
     * @return     None
     */
    public void printScores()
    {
        System.out.print("RED " + testBoard.playerRed.getName() + " - " + testBoard.playerRed.getScore());
        if (testBoard.playerRed.isActivePlayer() == true)
        {
            System.out.println(" [active]");
        }
        else
        {
            System.out.println("");
        }
        System.out.print("BLACK " + testBoard.playerBlack.getName() + " - " + testBoard.playerBlack.getScore());
        if (testBoard.playerBlack.isActivePlayer() == true)
        {
            System.out.println(" [active]");
        }
        else
        {
            System.out.println("");
        }

    }
}
