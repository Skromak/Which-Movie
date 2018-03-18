package com.project.skromak.whichmovie;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.project.skromak.whichmovie.http.HttpClient;

public class MainActivity extends Activity {

    private static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 0;
    private static Context ctx;
    private static TextView txtTitle, txtYear, txtRating, txtDirector, txtLanguage, txtVotes;
    private static WebView webView;
    private static Button btnFind;
    private static CheckBox chkEng, chkRecent, chkDecent, chkPop;

    public static Context getContext() {
        return ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ctx = getApplicationContext();

        checkPermissionNotification();

        if (checkPermission()){
            btnFind = findViewById(R.id.btnFind);
            txtTitle = findViewById(R.id.title);
            txtYear = findViewById(R.id.year);
            txtRating = findViewById(R.id.rating);
            txtDirector = findViewById(R.id.director);
            txtLanguage = findViewById(R.id.language);
            txtVotes = findViewById(R.id.votes);
            webView = findViewById(R.id.webView);

            chkEng = findViewById(R.id.chkEng);
            chkRecent = findViewById(R.id.chkRecent);
            chkDecent = findViewById(R.id.chkDecent);
            chkPop = findViewById(R.id.chkPop);

            chkEng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                      boolean checked = (chkEng).isChecked();

                                                      // Check which checkbox was clicked
                                                      if (checked) {
                                                          HttpClient.setLanguage("English");
                                                      } else {
                                                          HttpClient.setLanguage("Any");
                                                      }
                                                  }
                                              }
            );
        }


    }

    public void onClickBtn(View view) {
        btnFind.setText("Searching...");
        go();
    }

    public static void go() {
        HttpClient.getResponse(getRandomImdbId());
    }

    private static String getRandomTop20ImdbId() {
        String[] randomMovieArray = {"011116", "0068646", "0071562", "0468569", "0050083", "0108052",
                "0167260", "0110912", "0060196", "0137523", "0120737", "0109830", "0080684",
                "1375666", "0167261", "0073486", "0099685", "0133093", "0047478", "0076759"};

        String randomMovie = "n/a";

        int randomNumber = (int) Math.floor((Math.random()) * randomMovieArray.length - 1);
        if (randomNumber < randomMovieArray.length) {
            if (randomNumber < 0) {
                randomNumber = 0;
            }

            randomMovie = randomMovieArray[randomNumber];
        }
        return randomMovie;

    }

    private static String getRandomImdbId() {
        int randomNumber = (int) Math.floor((Math.random()) * 5000000);
        String randomNumStr = intToString(randomNumber, 7);
        System.out.println("Random Number: " + randomNumStr);
        return randomNumStr;
    }

    private static String getRandomMovieName() {
        String[] randomMovieArray = {"Star Wars", "Game of Thrones", "Lord of The Rings", "Harry Potter"};

        int randomNumber = (int) Math.floor((Math.random()) * randomMovieArray.length - 1);
        String randomMovie = "n/a";
        if (randomNumber < randomMovieArray.length) {
            if (randomNumber < 0) {
                randomNumber = 0;
            }
            randomMovie = randomMovieArray[randomNumber];
        }
        return randomMovie;
    }

    public static void updateDataSet(String title, int year, double imdbRating, String director, String url, String language, int votes) {
        btnFind.setText("Find");
        txtTitle.setText("Title: " + title); //set text for text view
        txtYear.setText("Year: " + year); //set text for text view
        txtRating.setText("IMDB Rating: " + imdbRating + "/10"); //set text for text view
        txtDirector.setText("Director: " + director); //set text for text view
        txtLanguage.setText("Language: " + language);
        txtVotes.setText("Votes: " + votes);

        if (url.contains("N/A")) {
            String summary = "<html><body>No Image Available</body></html>";
            webView.loadData(summary, "text/html; charset=utf-8", "utf-8");
        } else {
            webView.loadUrl(url);
        }

    }

    public static String intToString(int num, int digits) {
        String output = Integer.toString(num);
        while (output.length() < digits) output = "0" + output;
        return output;
    }

    private void checkPermissionNotification(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
                // TODO:
            } else {

                // Request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
            }
        } else {
            // Permission has already been granted
            // do nothing
        }
    }

    private boolean checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            return false;
        } else {
            return true;
        }
    }
}

