package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean currentPlayer;

    int roundCount;
    int player1;
    int player2;

    Button[][] grids;
    TextView score1;
    TextView score2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (this.getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        score1 = (TextView) findViewById(R.id.score1);
        score2 = (TextView) findViewById(R.id.score2);

        currentPlayer = true;
        roundCount = 0;

        grids = new Button[4][4];
        for(int i=1; i<=3; i++) {
            for(int j=1; j<=3; j++) {
                grids[i][j] = (Button) findViewById(getResources().getIdentifier("grid"+i+j, "id", this.getPackageName()));
            }
        }
    }

    public void buttonClicked(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (currentPlayer) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        roundCount++;
        if (checkForWin()) {
            if (currentPlayer) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            currentPlayer = !currentPlayer;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i=1; i<=3; i++) {
            for (int j=1; j<=3; j++) {
                field[i-1][j-1] = grids[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void player1Wins() {
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_LONG).show();
        player1++;
        score1.setText(Integer.toString(player1));
        resetBoard();
    }

    private void player2Wins() {
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        player2++;
        score2.setText(Integer.toString(player2));
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_LONG).show();
        resetBoard();
    }

    private void resetBoard() {
        for (int i=1; i<=3; i++) {
            for (int j=1; j<=3; j++) {
                grids[i][j].setText("");
            }
        }
        roundCount = 0;
        currentPlayer = true;
    }

    public void resetGame(View v) {
        roundCount = 0;
        currentPlayer = true;
        player1 = 0;
        player2 = 0;

        score1.setText("0");
        score2.setText("0");
    }
}