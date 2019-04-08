package com.david.matchinggame;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class GameActivity extends AppCompatActivity {

    ImageView im_11, im_12, im_13, im_14, im_21, im_22, im_23, im_24;

    boolean clicked_11 = false;
    boolean clicked_12 = false;
    boolean clicked_13 = false;
    boolean clicked_14 = false;
    boolean clicked_21 = false;
    boolean clicked_22 = false;
    boolean clicked_23 = false;
    boolean clicked_24 = false;

    ArrayList<String> ImagesNames = new ArrayList<String>();

    int points = 0;
    int numOfWrongTries = 0;
    Date dateStart;
    Date dateEnd;

    int click_Number = 1; //1 -> first click or 2 -> second click

    String first_image;
    String second_image;

    ImageView first_im;
    ImageView second_im;

    int flippedSets = 0;

    MediaPlayer sound;

    DBAdapter myDb;

    String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();

        myDb = new DBAdapter(this);
        myDb.open();

        dateStart = Calendar.getInstance().getTime();

        Intent intent = getIntent();
        playerName = intent.getStringExtra("playerName");


        im_11 = (ImageView) findViewById(R.id.im_11);
        im_12 = (ImageView) findViewById(R.id.im_12);
        im_13 = (ImageView) findViewById(R.id.im_13);
        im_14 = (ImageView) findViewById(R.id.im_14);
        im_21 = (ImageView) findViewById(R.id.im_21);
        im_22 = (ImageView) findViewById(R.id.im_22);
        im_23 = (ImageView) findViewById(R.id.im_23);
        im_24 = (ImageView) findViewById(R.id.im_24);

        im_11.setTag("q");
        im_12.setTag("q");
        im_13.setTag("q");
        im_14.setTag("q");
        im_21.setTag("q");
        im_22.setTag("q");
        im_23.setTag("q");
        im_24.setTag("q");

        //adding the 4 images twice in the ImagesNames arraylist
        for(int i = 1; i < 5; i++){
            String s = "im_"+i;
            ImagesNames.add(s);
            ImagesNames.add(s);
        }

        Collections.shuffle(ImagesNames);


        im_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage(im_11, ImagesNames.get(0));
            }
        });

        im_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage(im_12, ImagesNames.get(1));
            }
        });

        im_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage(im_13, ImagesNames.get(2));
            }
        });

        im_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage(im_14, ImagesNames.get(3));
            }
        });

        im_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage(im_21, ImagesNames.get(4));
            }
        });

        im_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage(im_22, ImagesNames.get(5));
            }
        });

        im_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage(im_23, ImagesNames.get(6));
            }
        });

        im_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage(im_24, ImagesNames.get(7));
            }
        });

    }

    private void showImage(final ImageView im, final String ImageName) {
        boolean sameImage = false;

        if(!String.valueOf(im.getTag()).equals("q")){
            sameImage = true;
        }

        switch (ImageName){
            case "im_1":
                im.setImageResource(R.drawable.im_1);
                im.setTag("im_1");
                sound = MediaPlayer.create(GameActivity.this, R.raw.sound_1);
                sound.setVolume(1.0f, 1.0f);
                sound.start();
                break;
            case "im_2":
                im.setImageResource(R.drawable.im_2);
                im.setTag("im_2");
                sound = MediaPlayer.create(GameActivity.this, R.raw.sound_2);
                sound.setVolume(1.0f, 1.0f);
                sound.start();
                break;
            case "im_3":
                im.setImageResource(R.drawable.im_3);
                im.setTag("im_3");
                sound = MediaPlayer.create(GameActivity.this, R.raw.sound_3);
                sound.setVolume(1.0f, 1.0f);
                sound.start();
                break;
            case "im_4":
                im.setImageResource(R.drawable.im_4);
                im.setTag("im_4");
                sound = MediaPlayer.create(GameActivity.this, R.raw.sound_4);
                sound.setVolume(1.0f, 1.0f);
                sound.start();
                break;
        }

        //delay
        final boolean finalSameImage = sameImage;
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkImages(im, ImageName, finalSameImage);
            }
        }, 500);

    }

    private void checkImages(ImageView im, String ImageName, boolean sameImage){
        sound.release();
        if(click_Number == 1 && !sameImage){
            first_image = ImageName;
            first_im = im;
            click_Number = 2;
        }
        else if(click_Number == 2 && !sameImage){
            second_image = ImageName;
            second_im = im;
            if(first_image.equals(second_image)){
                first_im.setVisibility(View.INVISIBLE);
                second_im.setVisibility(View.INVISIBLE);

                points++;
                flippedSets++;

                //check if all images are flipped(shown)
                if(flippedSets == 4){
                    //end game and calculate the score, add it to database and display it
                    dateEnd = Calendar.getInstance().getTime();

                    long timeTaken = dateEnd.getTime() - dateStart.getTime();
                    timeTaken = timeTaken/1000;
                    double score = 1000 - ((timeTaken*10) + numOfWrongTries);

                    myDb.insertRow(playerName, String.valueOf(score));

                    final Activity thisGameActivity = this;

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setTitle("Your Score");
                    builder.setMessage(""+score);

                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            //destroy current activity to return to MainActivity
                            thisGameActivity.finish();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();


                }
            }
            else {
                first_im.setImageResource(R.drawable.q);
                first_im.setTag("q");
                second_im.setImageResource(R.drawable.q);
                second_im.setTag("q");

                numOfWrongTries++;
            }
            click_Number = 1;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDb.close();
    }
}
