import java.util.Random;
import java.io.*;

/**
 * The Board class, as oppsoed to the Interface class, handles functionality - storing the checkers on the board, allowing users
 * to make moves, setting up the board as either a new or loaded game, checking for valid moves and winning conditions;
 * While it doesn't store any information about Players themselves, it is the class that spawns them and can determine
 * which one should go first.
 * 
 * @author Mikolaj Gackowski
 * @version 1.0
 */
public class Board
{
    /*INDEX: fields 1-25 represent triangles on the board, 0 is red bar, 25 is black bar
    VALUE: 0 for empty field, positive for number of red checkers, negative for number of black*/
    private int [] field = new int[26];
    Player thisPlayer;  //will store the active player
    Player playerRed;
    Player playerBlack;

    /**
     * Constructor for objects of class Board, which also initialises the default positions of checkers.
     */
    public Board()
    {
        field = new int[] {0, 2, 0, 0, 0, 0, -5, 0, -3, 0, 0, 0, 5, -5, 0, 0, 0, 3, 0, 5, 0, 0, 0, 0, -2, 0}; 
        
        /*SCENARIOS USED FOR EARLY TESTING - LEFT FOR MAINTENANCE*/
        //field = new int[] {3, 2, 3, 4, 5, 6, 3, 1, 0, -1, -2, -3, -4, -5, -6, 3, 5, 3, 1, -3, 4, 3, 1, 1, 1, -2};
        //field = new int[] {0, 0, 0, 0, -4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0, 4, -3, 1, 0, 0};

    }

    /**
     * Accessor method that returns the current position and colour of all checkers on the board.
     * 
     * @param  None
     * @return     array of checkers: field[number], 0 and 25 represent bar
     */
    public int[] getCheckers()
    {
        return field;
    }

    /**
     * Mutator method that amends the data on current checker positions.
     * 
     * @param  None
     * @return     array of checkers: field[number], 0 and 25 represent bar
     */
    public void setCheckers(int triangle, int number)
    {
        field[triangle] = number;
    }

    /**
     * Prepares the board for a new game, creating two new players, prompting for names and filling the field[] array
     * with default checker values.
     * 
     * @param  None
     * @return     None
     */
    public void newGame()
    {
        System.out.print('\f');
        playerRed = new Player();
        playerBlack = new Player();

        System.out.println("What's your name, player RED?");
        playerRed.setName(Genio.getString());
        System.out.println("What's your name, player BLACK?");
        playerBlack.setName(Genio.getString());

        field = new int [] {0, 2, 0, 0, 0, 0, -5, 0, -3, 0, 0, 0, 5, -5, 0, 0, 0, 3, 0, 5, 0, 0, 0, 0, -2, 0};
    }

    /**
     * Saves the information that can be used to restore an ongoing game to a text file; that includes
     * checker positioning, player names, scores, and an information which one is active at the moment.
     * 
     * @param  None
     * @return     None
     */
    public void saveGame()
    {
        String newData;
        FileOutputStream outputStream;
        PrintWriter writer;
        
        System.out.print('\f');
        System.out.println("This option will save your game to a file called SAVE.txt\nstored in your game directory. Continue? y/n");
        String save = Genio.getString();
        
        switch (save)
        {
            case "y":   try
                        {
                            outputStream = new FileOutputStream("SAVE.txt");
                            writer = new PrintWriter(outputStream);
                
                            writer.println(playerRed.getName());    //names
                            writer.println(playerBlack.getName());
                            writer.println(playerRed.isActivePlayer()); //is active?
                            writer.println(playerBlack.isActivePlayer());
                            writer.println(playerRed.getScore());   //scores
                            writer.println(playerBlack.getScore());
                            
                            for(int i=0; i<26; i++) //iterate through the array to save all the values
                            {
                                writer.println(field[i]);
                            }
                            
                            writer.flush();
                            writer.close();
                            System.out.println("Save completed.");
                            
                        }
                        catch (IOException e)
                        {
                            System.out.println("Oops, looks like there's been an error.");
                        }    
                        Genio.getString();
                        break;
            case "n":   break;
            default:    System.out.println("Invalid selection. Game will not be saved");
                        Genio.getString();
                        break;
        }

    }

    /**
     * Reads from a text file in order to restore a previously saved game; updates the variables in the program
     * to match the ones in the text file.
     * 
     * @param  None
     * @return     None
     */
    public boolean loadGame()
    {
        playerRed = new Player();
        playerBlack = new Player();

        FileReader fileReader;
        BufferedReader reader;
        String data;
        
        System.out.print('\f');
        System.out.println("This option loads a game from the SAVE.txt file in the game directory.");
        
        try
        {
            fileReader = new FileReader("SAVE.txt");
            reader = new BufferedReader(fileReader); 
            
            /*displays who was playing to help the player decide if this game should be loaded*/
            data = reader.readLine();
            playerRed.setName(data);
            System.out.print(data + " versus ");
            data = reader.readLine();
            playerBlack.setName(data);
            System.out.println(data);
            System.out.println("Load this game? y/n");
            String load = Genio.getString();
            
            switch (load) //update all remaining data to restore the game
            {
                case "y":   data = reader.readLine();
                            playerRed.setActivePlayer(Boolean.parseBoolean(data));  //are the player's active?
                            data = reader.readLine();
                            playerBlack.setActivePlayer(Boolean.parseBoolean(data));
                            data = reader.readLine();
                            playerRed.setScore(Integer.parseInt(data)); //set their scores...
                            data = reader.readLine();
                            playerBlack.setScore(Integer.parseInt(data));
                            for (int i=0; i<26; i++)    //go through the position of checkers on board...
                            {
                                data = reader.readLine();
                                field[i] = Integer.parseInt(data);
                            } 
                            System.out.println("Load successful.");
                            Genio.getString();
                            return true;
                case "n":   break; //cancel load
                default:    System.out.println("Invalid selection. You will return to the main menu.");
                            Genio.getString();
                            break;
            }
            
            reader.close();
        }
        catch (IOException e)
        {
            System.out.println("Oops, looks like the SAVE.txt file is missing or corrupted.");
        }
        
        return false;
    }

    /**
     * Allows the user to make a single, valid move with a checker in response to what colour the user is playing as and what dice
     * have been rolled; it will not allow to make a move that is illegal.
     * 
     * @param  None
     * @return     An indication if a move was made
     */
    public boolean makeMove()
    {
        /*the modifier is a mathematical trick - values will be multiplied by it. A -1 modifier indicates that
         * black is the active player, a +1 modifier indicates that red is the active player. Different values
         * in this method will be multiplied by the modifier to change the outcome (reverse direction of checker
         * movement, etc.)
         */
        int modifier;
        int from = 0;
        int to = 0;
        int dieUsed = 10;   //will be used to indicate which die to reset to zero (meaning it cannot be played again)
        boolean turnSuccessful = false;
        boolean onBar = false;  //any checkers on the bar?
        
        /*checks which player is active and loads their fields and methods
         * also assigns the modifier that will be used in mathematical calculations
         */

        if (playerRed.isActivePlayer())
        {
            modifier = 1;
            thisPlayer = playerRed;
        }
        else
        {
            modifier = -1;
            thisPlayer = playerBlack;
        }

        /*check if the player needs to "unbar" their checkers first*/

        if((thisPlayer == playerRed && field[0] > 0) || (thisPlayer == playerBlack && field[25] < 0))
        {
            System.out.println("You have a checker centered on the bar.");
            onBar = true;
        }

        /*check if there are no checkers outside home*/

        boolean bearingOff = true;
        if(thisPlayer == playerRed)
        {
            for(int i=0; i<19; i++)
            {
                if(field[i] > 0)
                {
                    bearingOff = false;
                }
            }
        }
        else
        {
            for(int i=7; i<26; i++)
            {
                if(field[i] < 0)
                {
                    bearingOff = false;
                }
            }
        }

        /*selecting where to move checkers from */

        if(!onBar)  //players only get to choose if none of their checkers are centered
        {
            System.out.println(thisPlayer.getName() + ", which field do you want to move checkers from?");

            do
            {

                from = Genio.getInteger();

                if(from == -1) //giving up the turn in case no moves are possible
                {
                    System.out.println("You're giving the turn to the opponent.");
                    for(int z=0; z<4; z++)
                    {
                        thisPlayer.resetDie(z);
                    }

                    return true; //treat the move as successful to move onto the next player
                }

                while (from < 1 || from > 24) //to avoid out of bounds exceptions
                {
                    System.out.println("This has to be a number from 1 to 24.");
                    from = Genio.getInteger();
                }

                if((field[from] * modifier) <= 0)   //so happy I paid attention during Math classes
                {
                    System.out.println("You don't have checkers on that field. Choose a different one.");
                    return false;
                }
                else if( ( (from + thisPlayer.getDice()[0] > 24) && bearingOff == false) && ( (from + thisPlayer.getDice()[1] > 24) && bearingOff == false) && modifier == 1 )
                {
                    System.out.println("You won't be able to make a move with this checker, RED!");
                    return false;   // somewhat prevents to get stuck in some situations
                }
                else if( ( (from - thisPlayer.getDice()[0] < 1) && bearingOff == false) && ( (from - thisPlayer.getDice()[1] < 1) && bearingOff == false) && modifier == -1 )
                {
                    System.out.println("You won't be able to make a move with this checker, BLACK!");
                    return false;
                }

            }
            while((field[from] * modifier) <= 0);   //repeat while the player's checker was chosen

        }

        if (bearingOff) {   //special rules apply for selecting checkers when a player's bearing off

            if(thisPlayer == playerRed)
            {
                for(int i = (from - 1); i > 18; i--)
                {
                    if(field[i] > 0)
                    {
                        System.out.println("You are bearing off, " + playerRed.getName() + ". - you must select a checker on the highest point.");
                        return false;
                    }
                } 
            }
            if(thisPlayer == playerBlack)
            {
                for(int i = (from + 1); i < 7; i++)
                {
                    if(field[i] < 0)
                    {
                        System.out.println("You are bearing off, " + playerBlack.getName() + ". - you must select a checker on the highest point.");
                        return false;
                    }
                } 
            }

        }

        if(!onBar)  //player could choose a checker normally
        {
            System.out.println("Move checker on field " + from + " to which field?");
        }
        else if (thisPlayer == playerRed)   //player had to restore a checker from the bar first (red)
        {
            System.out.println("Move checker from bar to which field?");
            from = 0;
        }
        else if (thisPlayer == playerBlack) //player had to restore a checker from the bar first (black)
        {
            System.out.println("Move checker from bar to which field?");
            from = 25;
        }

        if (!bearingOff) {
            do
            {
                System.out.print("(Hint) Your dice might get you here: ");  //saves the user some calculations
                for (int i=0; i<4; i++)  //display hint where to land
                {
                    if (thisPlayer.getDice()[i] != 0) //dice value zero indicates that move is used/unavailable
                    {
                        System.out.print("=" + (from + (thisPlayer.getDice()[i]*modifier)) + "= ");
                    }
                }
                System.out.println("");
                to = Genio.getInteger();
                
                if(to <= 0 || to >= 25)
                {
                    System.out.print("That's out of the field! Try again.");    //again, prevent exceptions
                }
            }
            while(to <= 0 || to >= 25);
        }
        else if (thisPlayer == playerRed)   //if bearing off, that is
        {
            System.out.println("(Enter 25 to bear off)");
            do
            {
                to = Genio.getInteger();
            }
            while(to < 20);
        }
        else if (thisPlayer == playerBlack) //also if bearing off
        {
            System.out.println("(Enter 0 to bear off)");
            do
            {
                to = Genio.getInteger();
            }
            while(to > 5);
        }

        /*now check if there's a match between dice and the move you want to achieve*/
        boolean movePossible = false;   //or simply: does the move match the dice?
        for(int i=0; i<4 && !movePossible; i++) //go through all the dice...
        {
            if( ((to - from) * modifier) == thisPlayer.getDice()[i] )
            {
                movePossible = true;
                dieUsed = i;
            }
            
            /*when bearing off, different players have different sets of conditions*/
            
            if( bearingOff && thisPlayer == playerRed && (from + thisPlayer.getDice()[i] > 24) )
            {
                movePossible = true;
                dieUsed = i;
                System.out.print("Bearing off red checker... ");
            }
            if( bearingOff && thisPlayer == playerBlack && (from - thisPlayer.getDice()[i] < 1) )
            {
                movePossible = true;
                dieUsed = i;
                System.out.print("Bearing off black checker... ");
            }
        }

        if (!movePossible)
        {
            System.out.println("The move doesn't match your dice! Try a different move.");
        }
        else //if the move DOES match the dice...
        {
            if((to >= 25 && thisPlayer == playerRed) || (to <= 0 && thisPlayer == playerBlack))
            {
                field[from]-=(1*modifier);
                System.out.print("successful.");
                thisPlayer.decrementScore();    //player has borne off...
                turnSuccessful = true;
            }
            else if( ( field[to] * modifier ) >= 0)
            {
                field[to]+=(1*modifier);
                field[from]-=(1*modifier);
                System.out.print("Move successful.");   //or moved as normal...
                turnSuccessful = true;
            }
            else if(field[to] == (-1 * modifier))   //or hit a single enemy checker!
            {
                field[to] = (1 * modifier); //replace destination with current checker
                if (thisPlayer == playerRed)
                {
                    field[from] -= 1;
                    field[25] -= 1; //remember 25 represents the bar for black checkers
                    System.out.print("Blot hit! Black checker moved to bar.");
                }
                else if (thisPlayer == playerBlack)
                {
                    field[from] += 1;
                    field[0] += 1;  //remember 0 represents the bar for white checkers
                    System.out.print("Blot hit! Red checker moved to bar.");
                }

                turnSuccessful = true;
            }
            else if(field[to] < -1 && thisPlayer == playerRed)
            {
                System.out.println("You can't make that move - too many black checkers here.");
            }
            else if(field[to] > 1 && thisPlayer == playerBlack)
            {
                System.out.println("You can't make that move - too many red checkers here.");
            }
            else //this saved me once, so i'm keeping it
            {
                System.out.println("Whoops, something went wrong. This program is not running well.");
            }

            if(dieUsed > -1 && dieUsed < 4) //outofbounds prevention
            {
                thisPlayer.resetDie(dieUsed); //change die value to zero (mark as used)
            }
        }

        return turnSuccessful;
    }
    
    /**
     * Prompts the players to roll the dice and assign priority to one over the other.
     * 
     * @param  None
     * @return     None
     */
    public void determineFirstPlayer()
    {
        Random randomGenerator = new Random();
        int dieR;   //red player's die
        int dieB;   //black player's die

        while(true)
        {
            System.out.print("We need to determine the first player. " + playerRed.getName() + ", roll the die!");
            Genio.getString(); //pressing enter as rolling dice
            dieR = randomGenerator.nextInt(6) + 1;
            System.out.println("You rolled: " + dieR);
            System.out.print("OK, " + playerBlack.getName() + ", roll your die!");
            Genio.getString(); //again, dice roll interaction
            dieB = randomGenerator.nextInt(6) + 1;
            System.out.println("You rolled: " + dieB);
            if (dieR != dieB)
            {
                break; //they differ, so we can end this. A double can't be accepted
            }
        }

        if (dieR > dieB)
        {
            System.out.println(playerRed.getName() + " rolled a higher number and will go first.");
            playerRed.toggleActivePlayer(); //default is false, so this will set to true
        }

        else if (dieR < dieB)
        {
            System.out.println(playerBlack.getName() + " rolled a higher number and will go first.");
            playerBlack.toggleActivePlayer();
        }
    }
    
    /**
     * Checks if the current player's score has reached zero and displays the result screen if it has.
     * 
     * @param  None
     * @return     True if one of the players won.
     */
    public boolean checkForWin()
    {
        /*winning sequence for player red*/
        
        if (thisPlayer.getScore() == 0 && thisPlayer == playerRed)
        {
            System.out.print("\f");
            System.out.println("   ___         __         _          __");
            System.out.println("  / _ \\___ ___/ / _    __(_)__  ___ / /");
            System.out.println(" / , _/ -_) _  / | |/|/ / / _ \\(_-</_/ ");
            System.out.println("/_/|_|\\__/\\_,_/  |__,__/_/_//_/___(_)  \n");
            System.out.println("Congratulations, " + playerRed.getName() + "! You have won!\n [enter]");
            Genio.getString();
            
            if (playerBlack.getScore() == 15)
            {
                System.out.println(playerBlack.getName() + " has just lost a GAMMON for not bearing off any checkers.");
                System.out.println("That means double the loss! [enter]");
                Genio.getString();
                if (field[25] < 0)
                {
                    System.out.println("...but wait, there are still red checkers on the bar!");
                    System.out.println("That's a BACKGAMMON and actually means TRIPLE the loss! [enter]");
                    Genio.getString();
                }    
            }
            
            return true;
        }
        
        /*winning sequence for player black*/
        
        if (thisPlayer.getScore() == 0 && thisPlayer == playerBlack)
        {
            System.out.print("\f");
            System.out.println("   ___  __         __            _          __");
            System.out.println("  / _ )/ /__ _____/ /__  _    __(_)__  ___ / /");
            System.out.println(" / _  / / _ `/ __/  '_/ | |/|/ / / _ \\(_-</_/ ");
            System.out.println("/____/_/\\_,_/\\__/\\_/\\_\\  |__,__/_/_//_/___(_)  \n");
            System.out.println("Congratulations, " + playerBlack.getName() + "! You have won!\n [enter]");
            Genio.getString();
            
            if (playerRed.getScore() == 15)
            {
                System.out.println(playerRed.getName() + " has just lost a GAMMON for not bearing off any checkers.");
                System.out.println("That means double the loss! [enter]");
                Genio.getString();
                if (field[0] > 0)
                {
                    System.out.println("...but wait, there are still red checkers on the bar!");
                    System.out.println("That's a BACKGAMMON and actually means TRIPLE the loss! [enter]");
                }    
            }
            
            return true;
        }
        
        return false;
    }
}
