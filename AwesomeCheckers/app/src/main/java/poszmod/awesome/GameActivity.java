package poszmod.awesome;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.lang.Integer;
import java.lang.String;

// import from manage package



import manage.createBoard;
// ^^ logic for the TTT game

public class GameActivity extends NavigationGeneralActivity {

    //Here i will declare all the variables
    // for the board and game to commence
    // here i go.
    // .

    private createBoard cBoard;
    private Button cBoardButtons[];

    // Vars for text output

    private TextView playerTurn;
    private TextView playerCount;
    private TextView tieCount;
    private TextView playerTwoCount;

    private TextView pOne;
    private TextView pTwo;

    // Keep track of wins and tie intergers
    // S

    private int playerCounts = 0;
    private int tieCounts = 0;
    private int playerTwoCounts = 0;

    // set player one to start

    private boolean playerOneBegin = true;
    private boolean singlePlayer = false;
    private boolean isPLayerOneTurn = true;

    //mhumanfirst
    // mgameover
    private boolean gameOver = false;

    // Create ToolBar Var


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        View v = setInnerLayout(R.layout.activity_game);




        cBoardButtons = new Button[cBoard.getBoard()];

        // Now assign the buttons to each array element

        cBoardButtons[0] = (Button) findViewById(R.id.one);
        cBoardButtons[1] = (Button) findViewById(R.id.two);
        cBoardButtons[2] = (Button) findViewById(R.id.three);
        cBoardButtons[3] = (Button) findViewById(R.id.four);
        cBoardButtons[4] = (Button) findViewById(R.id.five);
        cBoardButtons[5] = (Button) findViewById(R.id.six);
        cBoardButtons[6] = (Button) findViewById(R.id.seven);
        cBoardButtons[7] = (Button) findViewById(R.id.eight);
        cBoardButtons[8] = (Button) findViewById(R.id.nine);

        // assing text widgets to xml id
        playerTurn = (TextView) findViewById(R.id.info);
        playerCount = (TextView) findViewById(R.id.player);
        playerTwoCount = (TextView) findViewById(R.id.playertwo);
        tieCount = (TextView) findViewById(R.id.ties);

        // convert count widgets to text views
        // text views are declared above

        playerCount.setText(Integer.toString(playerCounts));
        playerTwoCount.setText(Integer.toString(playerTwoCounts));
        tieCount.setText(Integer.toString(tieCounts));

        // Object to create new board
        // create new game

        cBoard = new createBoard();

        // Create new Game
        createNewBoard();
    }



    private void createNewBoard(){
            // clear board
        cBoard.clearBoard();

        for (int i = 0; i < cBoardButtons.length; i++){

            // Loop through array
            // and for each element inside array
            // set button text to a null string
            // at the start of a new game
            // enable each button
            // set each button to an onclicklistener

            cBoardButtons[i].setText(" ");
            cBoardButtons[i].setEnabled(true);
            cBoardButtons[i].setOnClickListener(new createGameListener(i));
        }
        //Change Player turn text depending on player

        if(playerOneBegin){

            playerTurn.setText(R.string.move_your);
            //set to false after first game
            playerOneBegin = false;

        }else{

            playerTurn.setText(R.string.move_playertwo);
            // Gets AI move and sets it to i
            // getAiMove check whole board to see if there is a spot
            // randomly picks one and choses the emptyspot
            // then sets that to the variable I
            // getAiMove returns i
            // set i to move
            int move = cBoard.getAiMove();
            exeMove(move, cBoard.playerTwo);

            playerOneBegin = true;

        } // end If else

        gameOver = false;
    } // end createNewBoard


    private void exeMove(int location, char player){

        cBoard.executeMove(location, player);

        // which ever location is on the array
        // if the button is pressed on that array location
        // disable it because the move has already been made.

        cBoardButtons[location].setEnabled(false);

        // Now if button is pressed set the value of the button
        // to the player
        // X or O

        cBoardButtons[location].setText(String.valueOf(player));

        // Set the color of player Char


        if(player == cBoard.playerOne){

                cBoardButtons[location].setTextColor(Color.CYAN);
        }else{  cBoardButtons[location].setTextColor(Color.DKGRAY);
        }
    } //end exeMOVe




    private class createGameListener implements View.OnClickListener{

        int location;

        public createGameListener(int location){

            this.location = location;

        }

        public void onClick(View view){

            if(!gameOver){

                if(cBoardButtons[location].isEnabled()){
                    exeMove(location, cBoard.playerOne);
                    int winner = cBoard.checkWinner();

                    if(winner == 0){
                        playerTurn.setText(R.string.move_playertwo);
                        int move = cBoard.getAiMove();
                        exeMove(move, cBoard.playerTwo);
                        winner = cBoard.checkWinner();

                    }

                    if(winner== 0){playerTurn.setText(R.string.move_your); }
                    else if(winner == 1){

                        playerTurn.setText(R.string.move_tie);
                        tieCounts++;
                        tieCount.setText(Integer.toString(tieCounts));
                        gameOver = true;


                    }

                    else if (winner == 2){
                        playerTurn.setText(R.string.move_player1victory);
                        playerCounts++;
                        playerCount.setText(Integer.toString(playerCounts));
                        gameOver = true;

                    }

                    else{
                        playerTurn.setText(R.string.move_player2victory);
                        playerTwoCounts++;
                        playerTwoCount.setText(Integer.toString(playerTwoCounts));
                        gameOver = true;

                    } // end else
                } //end Second f
            } //end gameover if
        } // end onCLick
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.navigate){

            createNewBoard();
            //startActivity(new Intent(this, SettingsActivity.class));


        }
        return super.onOptionsItemSelected(item);
    }



}
