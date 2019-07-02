package com.hapoalimtest.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movies")
public class Movie /* extends RealmObject implements Parcelable */ {

    @SerializedName("vote_count")
    private int vote_count;
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "movieid")
    @SerializedName("id")
    private int id;
    @SerializedName("video")
    private boolean video;
    @ColumnInfo(name = "rating")
    @SerializedName("vote_average")
    private float vote_average;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;
    @SerializedName("popularity")
    private float popularity;
    @SerializedName("poster_path")
    private String poster_path;
    @SerializedName("original_language")
    private String original_language;
    @SerializedName("original_title")
    private String original_title;
    @SerializedName("backdrop_path")
    private String backdrop_path;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String release_date;


    public void setVote_count(int vote_count){
        this.vote_count = vote_count;
    }
    public int getVote_count(){
        return this.vote_count;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setVideo(boolean video){
        this.video = video;
    }
    public boolean getVideo(){
        return this.video;
    }
    public void setVote_average(float vote_average){
        this.vote_average = vote_average;
    }
    public float getVote_average(){
        return this.vote_average;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setPopularity(float popularity){
        this.popularity = popularity;
    }
    public float getPopularity(){
        return this.popularity;
    }
    public void setPoster_path(String poster_path){
        this.poster_path = poster_path;
    }
    public String getPoster_path(){
        return this.poster_path;
    }
    public void setOriginal_language(String original_language){
        this.original_language = original_language;
    }
    public String getOriginal_language(){
        return this.original_language;
    }
    public void setOriginal_title(String original_title){
        this.original_title = original_title;
    }
    public String getOriginal_title(){
        return this.original_title;
    }
    public void setBackdrop_path(String backdrop_path){
        this.backdrop_path = backdrop_path;
    }
    public String getBackdrop_path(){
        return this.backdrop_path;
    }
    public void setAdult(boolean adult){
        this.adult = adult;
    }
    public boolean getAdult(){
        return this.adult;
    }
    public void setOverview(String overview){
        this.overview = overview;
    }
    public String getOverview(){
        return this.overview;
    }
    public void setRelease_date(String release_date){
        this.release_date = release_date;
    }
    public String getRelease_date(){
        return this.release_date;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        Movie article = (Movie) obj;
        return article.id == this.id;
    }

    public static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.equals(newItem);
        }
    };
}
