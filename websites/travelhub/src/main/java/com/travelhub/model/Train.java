package com.travelhub.model;

import javax.persistence.*;

@Entity
@Table(name = "trains")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trainName;
    private String trainNumber;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private String classType;
    private Double price;
    private Integer seatsAvailable;

    public Train() {}

    public Train(String trainName, String trainNumber, String source, String destination,
                 String departureTime, String arrivalTime, String classType, Double price, Integer seatsAvailable) {
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.classType = classType;
        this.price = price;
        this.seatsAvailable = seatsAvailable;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }
    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }
    public String getClassType() { return classType; }
    public void setClassType(String classType) { this.classType = classType; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getSeatsAvailable() { return seatsAvailable; }
    public void setSeatsAvailable(Integer seatsAvailable) { this.seatsAvailable = seatsAvailable; }
}
