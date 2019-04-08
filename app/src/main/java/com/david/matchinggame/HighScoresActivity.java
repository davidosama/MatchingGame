package com.david.matchinggame;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class HighScoresActivity extends AppCompatActivity {

    DBAdapter myDb;
    TextView textViewName;
    TextView textViewScore;

    ArrayList<String> allScores;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        getSupportActionBar().hide();

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewScore = (TextView) findViewById(R.id.textViewScore);

        textViewName.append("Name:\n\n");
        textViewScore.append("Score:\n\n");


        allScores = new ArrayList<String>();

        myDb = new DBAdapter(this);
        myDb.open();

        Cursor cursor = myDb.getAllRows();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(DBAdapter.COL_ROWID);
                String name = cursor.getString(DBAdapter.COL_NAME);
                String score = cursor.getString(DBAdapter.COL_SCORE);

                //add the data to text views
                textViewName.append(name+"\n");
                textViewScore.append(score+"\n");

            } while (cursor.moveToNext());
        }
    }
}

