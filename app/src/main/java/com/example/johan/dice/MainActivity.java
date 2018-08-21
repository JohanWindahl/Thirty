    package com.example.johan.dice;

    import android.content.Intent;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.ListView;
    import android.widget.TextView;
    import java.util.ArrayList;
    import java.util.Arrays;


    //This class is the Main class which starts the main activity.
    public class MainActivity extends AppCompatActivity {
        static final String STATE_OF_GAME = "GameState"; //for saving state
        static final String ROLL_BTN_NAME = "RollBtn"; //for saving state

        //Create all game rounds
        GameRound g3 = new GameRound(false," Low");
        GameRound g4 = new GameRound(false," 4-Multipler");
        GameRound g5 = new GameRound(false," 5-Multipler");
        GameRound g6 = new GameRound(false," 6-Multipler");
        GameRound g7 = new GameRound(false," 7-Multipler");
        GameRound g8 = new GameRound(false," 8-Multipler");
        GameRound g9 = new GameRound(false," 9-Multipler");
        GameRound g10 = new GameRound(false,"10-Multipler");
        GameRound g11 = new GameRound(false,"11-Multipler");
        GameRound g12 = new GameRound(false,"12-Multipler");

        ArrayList<GameRound> gameRoundsArrayList = new ArrayList(Arrays.asList(g3, g4, g5, g6, g7, g8, g9, g10, g11, g12)); //create a list of game rounds
        Game game = new Game(gameRoundsArrayList);    //init a game with game rounds


        //Saves states for game
        @Override
        public void onSaveInstanceState(Bundle savedInstanceState) {
            savedInstanceState.putSerializable(STATE_OF_GAME, game);
            savedInstanceState.putSerializable(ROLL_BTN_NAME, getRollBtnText());
            super.onSaveInstanceState(savedInstanceState);
        }

        //Restores game and update ui elements
        @Override
        public void onRestoreInstanceState(Bundle savedInstanceState){
            super.onRestoreInstanceState(savedInstanceState);
            game = (Game) savedInstanceState.getSerializable(STATE_OF_GAME);
            refreshRollBtn((String) savedInstanceState.getSerializable(ROLL_BTN_NAME));

            if (game.getCurrentRoundChoice()>0) {
                game.getGameRounds().get(game.getCurrentRoundChoice()).setSelected(true);
            }
            refreshUI();
            }



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //Init ui-objects that will be used
            final Button endRoundBtn = (Button) findViewById(R.id.endRoundBtn);
            final Button rollBtn = (Button) findViewById(R.id.rollBtn);
            final ListView gameRoundsList = findViewById(R.id.gameRoundsList);
            final CustomAdapter adapter = new CustomAdapter(MainActivity.this, game.getGameRounds());

            gameRoundsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    gameRoundsListLogic(view,i,endRoundBtn);
                }
            });

            //Loads values from saved instance state if contains any.
            if(savedInstanceState != null) {
                game = (Game) savedInstanceState.getSerializable(STATE_OF_GAME);
                refreshRollBtn((String) savedInstanceState.getSerializable(ROLL_BTN_NAME));
                if (game.getCurrentRoundChoice()>0) {
                    game.getGameRounds().get(game.getCurrentRoundChoice()).setSelected(true);
                }
                refreshUI();
            }

            //refresh ui
            refreshListOfRounds();
            refreshDiceImages();
            game.refreshPreScore();

            initOnEndButtonClick(endRoundBtn,rollBtn);
            initRollBtnOnClickListeners(rollBtn);
            initDiceOnClickListeners();
            initGameRoundsListOnClickListeners(gameRoundsList,endRoundBtn,rollBtn);
        }




        //This is the logic for the six dice-buttons.
        private void initDiceOnClickListeners() {
            final ImageButton diceButton0 = findViewById(R.id.imageDice0);
            final ImageButton diceButton1 = findViewById(R.id.imageDice1);
            final ImageButton diceButton2 = findViewById(R.id.imageDice2);
            final ImageButton diceButton3 = findViewById(R.id.imageDice3);
            final ImageButton diceButton4 = findViewById(R.id.imageDice4);
            final ImageButton diceButton5 = findViewById(R.id.imageDice5);

            diceButton0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (game.dicesAreClickable()) {
                        game.getSixDices().flipColors(0);
                        refreshDiceImages();
                    }
                }
            });

            diceButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (game.dicesAreClickable()) {
                        game.getSixDices().flipColors(1);
                        refreshDiceImages();
                    }
                }
            });

            diceButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (game.dicesAreClickable()) {
                        game.getSixDices().flipColors(2);
                        refreshDiceImages();
                    }
                }
            });

            diceButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (game.dicesAreClickable()) {
                        game.getSixDices().flipColors(3);
                        refreshDiceImages();
                    }
                }
            });

            diceButton4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (game.dicesAreClickable()) {
                        game.getSixDices().flipColors(4);
                        refreshDiceImages();
                    }
                }
            });


                diceButton5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (game.dicesAreClickable()) {
                        game.getSixDices().flipColors(5);
                        refreshDiceImages();
                    }
                }
            });
        }

        //The clickable ListView of all different game rounds. This is the logic what happens when a item is clicked.
        private void initGameRoundsListOnClickListeners(final ListView gameRoundsList, final Button endRoundBtn, Button rollBtn) {
            gameRoundsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    gameRoundsListLogic(view,position,endRoundBtn);
                }
            });
        }

        private void gameRoundsListLogic(View view, int position, Button endRoundBtn) {
            final CustomAdapter adapter = new CustomAdapter(MainActivity.this, game.getGameRounds());
            final ListView gameRoundsList = (ListView) findViewById(R.id.gameRoundsList);
            gameRoundsList.setAdapter(adapter);
            GameRound model = game.getGameRounds().get(position);

            if (game.isClickableGameRounds() & !game.getGameRounds().get(position).hasBeenPlayed()) { //if an item has not been played
                game.resetAllSelections();

                model.setSelected(true);
                game.getGameRounds().set(position, model);
                game.setCurrentRoundChoice(position);
                endRoundBtn.setEnabled(true);
                game.setEndClickable(true);
                game.getSixDices().resetAllColors();
                if (game.getCurrentRoundChoice().equals(0) ) { //if it is the first item the round "Low"
                    game.getSixDices().showRedDicesLow();
                    refreshDiceImages();
                }
                else {
                    game.getSixDices().showRedDicesMult(game.getCurrentRoundChoice() + 3); //if it is any other item than the round "Low"
                    refreshDiceImages();
                }
                refreshListOfRounds();
            }
            else {
                model.setSelected(false);
                game.getGameRounds().set(position, model);
                endRoundBtn.setEnabled(false);
                game.setEndClickable(false);
                view.setSelected(false);
                refreshListOfRounds();
            }
        }

        //The logic for the clickable "Roll Dice"-button
        private void initRollBtnOnClickListeners(final Button rollBtn) {

            rollBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rollBtn.getText().equals("Start Round") | rollBtn.getText().equals("Start Game") ) {
                        game.incRounds();
                        refreshRound();
                    }

                    refreshRollBtn("Roll dice");

                    if (game.getThrowNumber() > 3) { //if the round is over, no more throws
                        game.setThrowNumber(0);
                       }

                    game.setClickableGameRounds(true);
                    game.getSixDices().reRollDices();
                    refreshDiceImages();
                    game.incThrowNumber();
                    refreshThrow();

                    game.refreshPreScore();

                    if (game.getThrowNumber().equals(3)) { //if the round is over, no more throws
                        rollBtn.setEnabled(false);
                        game.setClickableDices(false);
                    }
                    game.setClickableDices(true);
                    refreshListOfRounds();
                }
            });
        }

        //The logic for the clickable "End Round"-button
        private void initOnEndButtonClick(final Button endRoundBtn, final Button rollBtn) {
            endRoundBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refreshRollBtn("Start Round");
                    game.getGameRounds().get(game.getCurrentRoundChoice()).setBeenPlayed(true);
                    endRoundBtn.setEnabled(false);
                    game.setEndClickable(false);
                    Integer currentBestScore = 0;

                    if (game.getCurrentRoundChoice().equals(0)) { //If round "Low" is chosen, calc score differently
                        currentBestScore = Score.getBestScoreLow(game.getSixDices().getDicesValuesAsArray());
                    } else {
                        currentBestScore = Score.getBestScore(game.getSixDices().getDicesValuesAsArray(), game.getCurrentRoundChoice() + 3);
                    }

                    game.setGameRoundPostScore(game.getCurrentRoundChoice(), currentBestScore);
                    game.setCurrentScore(currentBestScore + game.getCurrentScore());
                    refreshCurrentScore();

                    if (game.getRounds().equals(10)) { //If we have played all ten rounds, end game and start new activity
                        rollBtn.setEnabled(false);
                        openEndOfGameActivity();
                        return;
                    }
                    rollBtn.setEnabled(true);
                    game.setThrowNumber(0);
                    game.setClickableDices(false);
                    game.setClickableGameRounds(false);
                    refreshThrow();
                    refreshRound();
                    game.getSixDices().resetAllColors();
                    refreshDiceImages();
                    game.refreshPreScore();
                    refreshListOfRounds();
                    }
            });
        }

        //Opens a new activity
        private void openEndOfGameActivity() {
            Intent intent = new Intent(this, EndOfGameActivity.class);
            Integer test =  game.getCurrentScore();
            intent.putExtra("totalScore", test);
            startActivity(intent);
        }

        //refresh ui - throw number
        private void refreshThrow() {
            final TextView throwTxt = (TextView) findViewById(R.id.throwTxt);
            throwTxt.setText("Throw #" + game.getThrowNumber());
        }

        //refresh ui - round number
        private void refreshRound() {
            final TextView rndTxt = (TextView) findViewById(R.id.rndTxt);
            rndTxt.setText("Round #" + game.getRounds());
        }

        //Refresh ui - current score
        private void refreshCurrentScore() {
            final TextView totalScore = (TextView) findViewById(R.id.totalScoreTxt);
            totalScore.setText("Score: " + game.getCurrentScore().toString());
        }

        //Refresh ui - The list of game rounds
        private void refreshListOfRounds() {
            final CustomAdapter adapter = new CustomAdapter(MainActivity.this, game.getGameRounds());
            final ListView gameRoundsList = (ListView) findViewById(R.id.gameRoundsList);
            gameRoundsList.setAdapter(adapter);
            game.updateGameRoundNames();
        }

        //Refreshes ui - All dice images
        public void refreshDiceImages() {
            final ImageView imageDice0 = (ImageView) findViewById(R.id.imageDice0);
            final ImageView imageDice1 = (ImageView) findViewById(R.id.imageDice1);
            final ImageView imageDice2 = (ImageView) findViewById(R.id.imageDice2);
            final ImageView imageDice3 = (ImageView) findViewById(R.id.imageDice3);
            final ImageView imageDice4 = (ImageView) findViewById(R.id.imageDice4);
            final ImageView imageDice5 = (ImageView) findViewById(R.id.imageDice5);

            imageDice0.setImageResource(getResources().getIdentifier(game.getSixDices().asText(0), "drawable", getPackageName()));
            imageDice1.setImageResource(getResources().getIdentifier(game.getSixDices().asText(1), "drawable", getPackageName()));
            imageDice2.setImageResource(getResources().getIdentifier(game.getSixDices().asText(2), "drawable", getPackageName()));
            imageDice3.setImageResource(getResources().getIdentifier(game.getSixDices().asText(3), "drawable", getPackageName()));
            imageDice4.setImageResource(getResources().getIdentifier(game.getSixDices().asText(4), "drawable", getPackageName()));
            imageDice5.setImageResource(getResources().getIdentifier(game.getSixDices().asText(5), "drawable", getPackageName()));
        }



        //refresh ui - roll button enabled
        private void refreshRollBtnEnabled() {
            final Button rollBtn = (Button) findViewById(R.id.rollBtn);
            if (game.getThrowNumber()<3) {
                rollBtn.setEnabled(true);
            }
            else {
                rollBtn.setEnabled(false);
            }
        }

        //refresh ui - roll button name/enabled
        private void refreshRollBtn(String text) {
            final Button rollBtn = (Button) findViewById(R.id.rollBtn);
            rollBtn.setText(text);
        }


        //refresh ui - end button enabled
        private void refreshEndBtnEnabled() {
            final Button endBtn = (Button) findViewById(R.id.endRoundBtn);
            endBtn.setEnabled(game.isEndClickable());
        }

        //refresh ui - get roll button name
        private String getRollBtnText() {
            final Button rollBtn = (Button) findViewById(R.id.rollBtn);
            return rollBtn.getText().toString();
        }

        //refresh ui - black checkmarks
        private void refreshBlackChecks() {
            if (game.getCurrentRoundChoice()>-1) {
                GameRound model = game.getGameRounds().get(game.getCurrentRoundChoice());
                model.setSelected(true);
                game.getGameRounds().set(game.getCurrentRoundChoice(), model);
            }
        }

        //refresh ui - green checkmarks
        private void refreshGreenChecks() {
            for (int i = 0;i<this.game.getGameRounds().size();i++) {
                if (game.getGameRounds().get(i).hasBeenPlayed()) {
                    GameRound model2 = game.getGameRounds().get(i);
                    model2.setBeenPlayed(true);
                    game.getGameRounds().set(i, model2);

                }
            }
        }

        //Calls all refresh ui methods
        private void refreshUI() {
            refreshThrow();
            refreshRound();
            refreshCurrentScore();
            refreshDiceImages();
            refreshRollBtnEnabled();
            refreshEndBtnEnabled();
            refreshBlackChecks();
            refreshGreenChecks();
            refreshListOfRounds();
        }
    }
