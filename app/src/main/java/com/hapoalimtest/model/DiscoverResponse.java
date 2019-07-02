package com.hapoalimtest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DiscoverResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int total_results;
    @SerializedName("total_pages")
    private int total_pages;
    @SerializedName("results")
    private List<Movie> movies;

    public void setPage(int page){
        this.page = page;
    }

    public int getPage(){
        return this.page;
    }

    public void setTotal_results(int total_results){
        this.total_results = total_results;
    }

    public int getTotal_results(){
        return this.total_results;
    }

    public void setTotal_pages(int total_pages){
        this.total_pages = total_pages;
    }

    public int getTotal_pages(){
        return this.total_pages;
    }
    public void setResults(List<Movie> results){
        this.movies = results;
    }
    public List<Movie> getResults(){
        return this.movies ;
    }

}
