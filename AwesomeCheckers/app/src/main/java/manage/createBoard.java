package manage;

import java.util.Random;

/**
 * Created by lu on 5/12/15.
 * Create New TitTacToe Game
 */
public class createBoard {

    public static final char emptySpot = ' ';
    public static final char playerOne = 'X';
    public static final char playerTwo = 'O';

    private Random randomNum;
    //
    private char makeBoard[];
    private final static int board = 9;

    // return board size
    // use this instead of an activity
    public static int getBoard(){
        return board;
    }

    public createBoard(){

        makeBoard = new char[board];

        for (int i = 0; i< board; i++){
            makeBoard[i] = emptySpot;
            randomNum = new Random();
        }// end board
    }


    //clear board for java

    public void clearBoard() {

        for (int i = 0; i < board; i++) {
            makeBoard[i] = emptySpot;

        }
    }

    public void executeMove(int spotOnBoard, char player){

        makeBoard[spotOnBoard] = player;
        // see where they movec, set that to who moved.
    }


    // Indicate AI/Player2 move
    public int getAiMove() {

        int move;

        // return the move but return
        // the actual location by the AI move
        // less than boared size.

        // return a move, so that the AI can win
        // score the move

        for (int i = 0; i < getBoard(); i++){
            // check the board to see if ethier player one or
            // player two (can be AI) has played a spot on the board

            if(makeBoard[i] != playerOne && makeBoard[i] != playerTwo){

                // store empty space
                char holdBoardSpot = makeBoard[i];

                // can be AI
                makeBoard[i] = playerTwo;

                if(checkWinner() == 3){

                    executeMove(i,playerTwo);
                    return i;

                } // end this if

                else {
                    makeBoard[i] = holdBoardSpot;
                }
            } // end if
        } //end for

        for (int i = 0; i < getBoard(); i++){

            // check the board to see if ethier player one or
            // player two (can be AI) has played a spot on the board

            // loop through to check for empty space
            if(makeBoard[i] != playerOne && makeBoard[i] != playerTwo){
                // if there is empty space
                // store empty space

                char holdBoardSpot = makeBoard[i];

                // can be AI
                makeBoard[i] = playerOne;

                // check if playerOne has won
                if(checkWinner() == 2){
                    executeMove(i,playerTwo);
                    return i;
                } // end this if

                else {
                    makeBoard[i] = holdBoardSpot;
                }
            } // end if
        } //end for

        do {move = randomNum.nextInt(getBoard());
        } while(makeBoard[move] == playerOne || makeBoard[move] == playerTwo);
                executeMove(move, playerTwo);
            return move;

    } // end get AI move

    public int checkWinner(){

        // 2 returns player1 victory
        // 3 returns player2 victory
        // 1 returns a tie
        // 0 returns an empty stot and forces auser to make amove

        // check for winner horizontally

        for (int i = 0; i<=6; i+=3){

            if(     makeBoard[i]   == playerOne &&
                    makeBoard[i+1] == playerOne &&
                    makeBoard[i+2] == playerOne){

                            return 2;
            }// end if

            if(     makeBoard[i]   == playerTwo &&
                    makeBoard[i+1] == playerTwo &&
                    makeBoard[i+2] == playerTwo){

                            return 3;
            } //end IF
        } // END FOR

        // Now check for vertical winners
        for (int i = 0; i<=2; i++){

            if(     makeBoard[i]   == playerOne &&
                    makeBoard[i+3] == playerOne &&
                    makeBoard[i+6] == playerOne){

                            return 2;
            }

            if(     makeBoard[i]   == playerTwo &&
                    makeBoard[i+3] == playerTwo &&
                    makeBoard[i+6] == playerTwo){

                            return 3;
            }

        } // end for

        // Checck for diagnoal winners

        if((    makeBoard[0] == playerOne &&
                makeBoard[4] == playerOne &&
                makeBoard[8] == playerOne) ||
                        makeBoard[2] == playerOne &&
                        makeBoard[4] == playerOne &&
                        makeBoard[6] == playerOne){return 2;}

        if((    makeBoard[0] == playerTwo &&
                makeBoard[4] == playerTwo &&
                makeBoard[8] == playerTwo) ||
                        makeBoard[2] == playerTwo &&
                        makeBoard[4] == playerTwo &&
                        makeBoard[6] == playerTwo){return 3;}

        //Check for tie
        //Now check if there is an open spot and
        // if there is not, return a tie

        for(int i = 0; i<getBoard(); i++){

            if (makeBoard[i] != playerOne && makeBoard[i] != playerTwo){

                // there is still a move open, so return 0
                // and continue

                            return 0;
            }
        }

        // if false
        // return a tie.

                            return 1;
    } // end CheckWinner
} // end class file
