package com.example.diceroll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int player;
    int score1;
    int score2;

    boolean gameRunning;

    ImageView diceImg;
    TextView scoreView1;
    TextView scoreView2;
    TextView playerInfo;

    Resources res;
    String packageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (this.getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        player = 1;
        score1 = 0;
        score2 = 0;

        gameRunning = true;

        diceImg = (ImageView) findViewById(R.id.dice);
        scoreView1 = (TextView) findViewById(R.id.score1);
        scoreView2 = (TextView) findViewById(R.id.score2);
        playerInfo = (TextView) findViewById(R.id.currentPlayer);

        res = this.getResources();
        packageName = this.getPackageName();

        diceImg.setBackgroundResource(R.drawable.dice_animation);
    }

    private void animateRoll(int result) {
        AnimationDrawable frameAnimation = (AnimationDrawable) diceImg.getBackground();
        frameAnimation.start();
        diceImg.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        frameAnimation.stop();
                        frameAnimation.selectDrawable(result-1);
                        updateScore(result);
                    }
                }
        , 1500);
    }

    private boolean checkWin() {
        if(score1 >= 25 || score2 >= 25)
            return true;
        else return false;
    }

    private void updateScore(int result) {
        if(player == 1) {
            score1 += result;
            scoreView1.setText(Integer.toString(score1));
        } else {
            score2 += result;
            scoreView2.setText(Integer.toString(score2));
        }

        if(checkWin()){
            gameRunning = false;
            Toast.makeText(this, "Player " + player + " wins!", Toast.LENGTH_LONG).show();
        } else {
            if(player == 1)
                player = 2;
            else player = 1;
            playerInfo.setText("PLAYER " + player);
        }
    }

    public void resetScore(View v) {
        score1 = 0;
        score2 = 0;

        player = 1;
        playerInfo.setText("PLAYER 1");

        scoreView1.setText("0");
        scoreView2.setText("0");

        gameRunning = true;
    }

    public void diceRoll(View v) {
        if(gameRunning) {
            Random rn = new Random();
            int result = rn.nextInt(6) + 1;
            animateRoll(result);
        } else {
            Toast.makeText(this, "Player " + player + " has won!", Toast.LENGTH_LONG).show();
        }
    }
}