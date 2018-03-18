package com.project.skromak.whichmovie.http;

import android.support.annotation.NonNull;
import android.util.Log;

import com.project.skromak.whichmovie.ClientPlayer;
import com.project.skromak.whichmovie.MainActivity;
import com.project.skromak.whichmovie.movie.MovieObject;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by SamMcK on 17/03/2018.
 */

public class HttpClient {

    private static String lang = "Any";

    private static String apiKey = "[yourKey]";

    public static void getResponse(String searchTerm) {


        final Request request = new Request.Builder()
                .url("http://www.omdbapi.com/?apikey=" + apiKey + "&i=tt" + encodeValue(searchTerm))
                .get()
                .build();

        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("Failure: " + request);
                e.printStackTrace();
                MainActivity.go();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull okhttp3.Response response) throws IOException {

                try {
                    final MovieObject movieObject = new Gson().fromJson(response.body().string(), MovieObject.class);

                    try {
                        if (movieObject.isResponse() && movieObject.getType().equals("movie")) {
                            if (getLanguage().contains("English")) {
                                if (movieObject.getLanguage().contains("English")) {
                                    printAndUpdate(movieObject);
                                } else {
                                    MainActivity.go();
                                }
                            } else {
                                // any
                                printAndUpdate(movieObject);
                            }

                        } else {
                            MainActivity.go();
                        }
                    } catch (NumberFormatException nfe) {
                        MainActivity.go();
                    }

                } catch (NumberFormatException nfe) {
                    MainActivity.go();
                }
            }
        });
    }

    public static void getResponse(int searchId) {

        Request request = new Request.Builder()
                .url("http://www.omdbapi.com/?apikey=" + apiKey + "&i=tt" + searchId)
                .get()
                .build();

        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("Failure");
                e.printStackTrace();
                MainActivity.go();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull okhttp3.Response response) throws IOException {

                try {
                    final MovieObject movieObject = new Gson().fromJson(response.body().string(), MovieObject.class);

                    // not picky
                    if (movieObject.isResponse() && movieObject.getType().equals("movie")) {
                        if (getLanguage().contains("English")) {
                            if (movieObject.getLanguage().contains("English")) {
                                printAndUpdate(movieObject);
                            }
                        } else {
                            printAndUpdate(movieObject);
                        }

                    } else {
                        MainActivity.go();
                    }


                } catch (NumberFormatException nfe) {
                    MainActivity.go();
                }

            }
        });
    }

    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "error";
        }
    }

    private static void writeFile() {
        try {
            // catches IOException below
            final String TESTSTRING = new String("Hello Android");

            FileOutputStream fOut = MainActivity.getContext().openFileOutput("samplefile.txt",
                    MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            // Write the string to the file
            osw.write(TESTSTRING);

            osw.flush();
            osw.close();

//Reading the file back...

            FileInputStream fIn = MainActivity.getContext().openFileInput("samplefile.txt");
            InputStreamReader isr = new InputStreamReader(fIn);

        /* Prepare a char-Array that will
         * hold the chars we read back in. */
            char[] inputBuffer = new char[TESTSTRING.length()];

            // Fill the Buffer with data from the file
            isr.read(inputBuffer);

            // Transform the chars to a String
            String readString = new String(inputBuffer);

            // Check if we read back the same chars that we had written out
            boolean isTheSame = TESTSTRING.equals(readString);

            Log.i("File Reading stuff", "success = " + isTheSame);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void setLanguage(String language) {
        lang = language;
    }

    private static String getLanguage() {
        return lang;
    }

    private static void printAndUpdate(final MovieObject movieObject) {
        System.out.println("Title: " + movieObject.getTitle());
        System.out.println("Year: " + movieObject.getYearInt());
        System.out.println("imdbRating: " + movieObject.getImdbRating());
        System.out.println("Type: " + movieObject.getType());
        System.out.println("Poster: " + movieObject.getPoster());

        ClientPlayer.runOnUI(new Runnable() {
            public void run() {
                MainActivity.updateDataSet(movieObject.getTitle(), movieObject.getYearInt(),
                        movieObject.getImdbRating(), movieObject.getDirector(),
                        movieObject.getPoster(), movieObject.getLanguage(), movieObject.getImdbVotes());
            }
        });
    }

}
