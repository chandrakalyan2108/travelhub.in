package com.travelhub.model;

import javax.persistence.*;

@Entity
@Table(name = "metro_routes")
public class MetroRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String line;
    private String source;
    private String destination;
    private Integer stops;
    private Double fare;
    private String estimatedDuration;

    public MetroRoute() {}

    public MetroRoute(String city, String line, String source, String destination, Integer stops, Double fare, String estimatedDuration) {
        this.city = city;
        this.line = line;
        this.source = source;
        this.destination = destination;
        this.stops = stops;
        this.fare = fare;
        this.estimatedDuration = estimatedDuration;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getLine() { return line; }
    public void setLine(String line) { this.line = line; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public Integer getStops() { return stops; }
    public void setStops(Integer stops) { this.stops = stops; }
    public Double getFare() { return fare; }
    public void setFare(Double fare) { this.fare = fare; }
    public String getEstimatedDuration() { return estimatedDuration; }
    public void setEstimatedDuration(String estimatedDuration) { this.estimatedDuration = estimatedDuration; }
}
