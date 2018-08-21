package com.example.johan.dice;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


//This class is the secondary class which starts the end-of-game activity.
//Which is a end message with the score achieved.
public class EndOfGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_game);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();

        Button playAgainBtn = (Button) findViewById(R.id.playAgainBtn);
        TextView EOGtotalScore = (TextView) findViewById(R.id.EOGtotalScoreTxt);

        Integer score = intent.getIntExtra("totalScore", 0);
        EOGtotalScore.setText(score.toString());

        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainGameActivity();
            }
        });
        System.out.println("sdf");
    }


    //Opens the main activity, for a continuous loop
    private void openMainGameActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}