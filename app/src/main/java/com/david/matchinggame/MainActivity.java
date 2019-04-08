package com.david.matchinggame;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button buttonStart, buttonScores, buttonAbout, buttonExit;
    String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonScores = (Button) findViewById(R.id.buttonScores);
        buttonAbout = (Button) findViewById(R.id.buttonAbout);
        buttonExit = (Button) findViewById(R.id.buttonExit);

        final Activity thisMainActivity = this;

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get user's playerName
                final EditText editText = new EditText(thisMainActivity);

                new AlertDialog.Builder(thisMainActivity)
                        .setTitle("Enter your name")
                        .setView(editText)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                playerName = editText.getText().toString();
                                if(playerName.equals("") || playerName == null){
                                    playerName = "Player";
                                }
                                if(playerName.length() > 15){
                                    playerName = playerName.subSequence(0, 15).toString();
                                }
                                //launch game activity and send the user's playerName
                                Intent intent = new Intent(thisMainActivity, GameActivity.class);
                                intent.putExtra("playerName", playerName);
                                startActivity(intent);
                            }
                        })
                        .show();

            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisMainActivity.finish();
            }
        });

        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(thisMainActivity);

                builder.setTitle("About");
                builder.setMessage("Matching Game was developed by David Osama as Lab 2 Assignment for Advanced Mobile Applications Development course.");

                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        buttonScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisMainActivity, HighScoresActivity.class);
                startActivity(intent);
            }
        });
    }
}
