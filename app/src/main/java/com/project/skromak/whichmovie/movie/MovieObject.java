package com.project.skromak.whichmovie.movie;

/**
 * Created by SamMcK on 17/03/2018.
 */

public class MovieObject {

    private String Title;
    private int YearInt;
    private String Year;
    private double imdbRating;
    private String Type;
    private String Poster;
    private boolean Response;
    private String Director;
    private String Language;
    private String imdbVotes;

    public int getYearInt() {
        return Integer.valueOf(getYear());
    }

    public String getYear(){
        if (Year.length()>4){
//            Year.replace(Year.charAt(Year.length()-1), Character.valueOf(' '));
            System.out.println(Year.substring(0,3));
            return Year.substring(0,3);
        }
        return Year;
    }

    public void setYear(String year) {
        this.Year = year;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public boolean isResponse() {
        return Response;
    }

    public void setResponse(boolean response) {
        Response = response;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public int getImdbVotes() {
        String imdbVotesStr = String.valueOf(imdbVotes);
        String newStr = imdbVotesStr.replaceAll(",", "");
        return Integer.valueOf(newStr);
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }
}
