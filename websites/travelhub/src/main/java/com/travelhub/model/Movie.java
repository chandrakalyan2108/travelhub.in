package com.travelhub.model;

import javax.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private String language;
    private String duration;
    private Double rating;
    private String imageUrl;
    private String theater;
    private String city;
    private String showtimes; // comma separated e.g. "10:00 AM,1:30 PM,6:00 PM,9:30 PM"
    private Double price;

    public Movie() {}

    public Movie(String title, String genre, String language, String duration, Double rating,
                 String imageUrl, String theater, String city, String showtimes, Double price) {
        this.title = title;
        this.genre = genre;
        this.language = language;
        this.duration = duration;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.theater = theater;
        this.city = city;
        this.showtimes = showtimes;
        this.price = price;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getTheater() { return theater; }
    public void setTheater(String theater) { this.theater = theater; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getShowtimes() { return showtimes; }
    public void setShowtimes(String showtimes) { this.showtimes = showtimes; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String[] getShowtimeList() {
        return showtimes == null ? new String[0] : showtimes.split(",");
    }
}
