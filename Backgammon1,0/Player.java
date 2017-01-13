import java.util.Random;

/**
 * The Player class stores fields that personalise the player (name), give them a score and determine
 * which is the active one in the game; apart from accessor and mutator methods it also allows
 * a player to roll the die and mark a chosen dice as used.
 * 
 * @author Mikolaj Gackowski
 * @version 1.0
 */
public class Player
{
    
    private boolean activePlayer;
    private int score;
    private String name;
    
    /*normally there are two dice in play, but if a user rolls a double, it's as if they got two
     * extra dice - thus four vaslues in the array.
     */
    private int [] dice = new int[4];
    

    /**
     * Constructor for objects of class Player, setting default values.
     */
    public Player()
    {
        activePlayer = false;
        dice = new int[] {0, 0, 0, 0};
        score = 15;
        name = "";
    }

    /**
     * Rolls two dice and fills in the dice[] array, storing available moves in a turn.
     * 
     * @param  None
     * @return     An array storing values of rolled dice.
     */
    public int [] rollDice()
    {
       Random randomGenerator = new Random();
       dice[0] = randomGenerator.nextInt(6) + 1;
       dice[1] = randomGenerator.nextInt(6) + 1;
       dice[2] = 0; //only useable in-game if a double was rolled
       dice[3] = 0; //as above
       System.out.println("You rolled: " + dice[0] + " and " + dice[1]);
       if (dice[0] == dice[1])
       {
           /*In backgammon, rolling a double is theoretically like giving
            * the player two additional moves.
            */ 
           System.out.println("Nice! You rolled a double!");
           dice[2] = dice[0];
           dice[3] = dice[1];
       }
       
       return dice;
    }
    
    /**
     * Accessor for determining if it's the players turn.
     * 
     * @param  None
     * @return     True if the player is in charge of the turn
     */
    public boolean isActivePlayer()
    {
        return activePlayer;
    }
    
    /**
     * Mutator to set if the player's active.
     * 
     * @param  toWhat is the player's active status
     * @return     None
     */
    public void setActivePlayer(boolean toWhat)
    {
        activePlayer = toWhat;
    }
    
    /**
     * Mutator that switches the player's active status without knowing what it was before.
     * 
     * @param  None
     * @return     None
     */
    public void toggleActivePlayer()
    {
        if (activePlayer == true)
        {
            activePlayer = false;
        }
        else
        {
            activePlayer = true;
        }
    }
    
    /**
     * Accessor for dice values.
     * 
     * @param  None
     * @return     inetger array of values of dice
     */
    public int [] getDice()
    {
        return dice;
    }
    
    /**
     * Accessor for the player's name.
     * 
     * @param  None
     * @return     the player's name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Mutator setting the player's name.
     * 
     * @param  newName - a new name for the player
     * @return     None
     */
    public void setName(String newName)
    {
        name = newName;
    }
    
    /**
     * Accessor for the player's score.
     * 
     * @param  None
     * @return     the player's score
     */
    public int getScore()
    {
        return score;
    }
    
    /**
     * Mutator for the player's score.
     * 
     * @param  newScore - the new score for the player
     * @return     None
     */
    public void setScore(int newScore)
    {
        score = newScore;
    }
    
    /**
     * Mutator for the player's score, decrementing it by one.
     * 
     * @param  None
     * @return     None
     */
    public void decrementScore()
    {
        score -= 1;
    }
    
    /**
     * Resets a chosen die to the value of zero, rendering it as unplayable for the rest of the turn.
     * 
     * @param  which - which die should be set as used
     * @return     None
     */
    public void resetDie(int which)
    {
        dice[which] = 0; //zero means it's unavailable to make a move with
    }
}
