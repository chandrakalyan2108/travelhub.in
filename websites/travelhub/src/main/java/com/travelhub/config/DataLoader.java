package com.travelhub.config;

import com.travelhub.model.*;
import com.travelhub.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final HotelRepository hotelRepository;
    private final FlightRepository flightRepository;
    private final TrainRepository trainRepository;
    private final BusRepository busRepository;
    private final MovieRepository movieRepository;
    private final MetroRouteRepository metroRouteRepository;

    public DataLoader(HotelRepository hotelRepository, FlightRepository flightRepository,
                       TrainRepository trainRepository, BusRepository busRepository,
                       MovieRepository movieRepository, MetroRouteRepository metroRouteRepository) {
        this.hotelRepository = hotelRepository;
        this.flightRepository = flightRepository;
        this.trainRepository = trainRepository;
        this.busRepository = busRepository;
        this.movieRepository = movieRepository;
        this.metroRouteRepository = metroRouteRepository;
    }

    @Override
    public void run(String... args) {
        seedHotels();
        seedFlights();
        seedTrains();
        seedBuses();
        seedMovies();
        seedMetro();
    }

    private void seedHotels() {
        if (hotelRepository.count() > 0) return;
        hotelRepository.save(new Hotel("Taj Krishna", "Hyderabad", "Road No. 1, Banjara Hills", 8500.0, 4.7,
                "/images/hotels/taj-krishna.svg", "Pool, Spa, Free WiFi, Breakfast"));
        hotelRepository.save(new Hotel("Novotel HICC", "Hyderabad", "HITEC City", 5200.0, 4.4,
                "/images/hotels/novotel-hicc.svg", "Gym, Free WiFi, Airport Shuttle"));
        hotelRepository.save(new Hotel("The Taj Mahal Palace", "Mumbai", "Apollo Bunder, Colaba", 15500.0, 4.9,
                "/images/hotels/taj-mahal-palace.svg", "Sea View, Spa, Pool, Fine Dining"));
        hotelRepository.save(new Hotel("Trident Nariman Point", "Mumbai", "Nariman Point", 9800.0, 4.6,
                "/images/hotels/trident-nariman.svg", "Sea View, Gym, Breakfast"));
        hotelRepository.save(new Hotel("The Leela Palace", "New Delhi", "Chanakyapuri", 12500.0, 4.8,
                "/images/hotels/leela-palace.svg", "Pool, Spa, Butler Service"));
        hotelRepository.save(new Hotel("ITC Gardenia", "Bengaluru", "Residency Road", 7600.0, 4.5,
                "/images/hotels/itc-gardenia.svg", "Rooftop Pool, Free WiFi"));
        hotelRepository.save(new Hotel("Taj Exotica", "Goa", "Benaulim Beach", 18500.0, 4.9,
                "/images/hotels/taj-exotica-goa.svg", "Beachfront, Pool, Spa, Water Sports"));
        hotelRepository.save(new Hotel("Zostel Goa", "Goa", "Anjuna", 1200.0, 4.2,
                "/images/hotels/zostel-goa.svg", "Backpacker Hostel, Free WiFi, Cafe"));
    }

    private void seedFlights() {
        if (flightRepository.count() > 0) return;
        flightRepository.save(new Flight("IndiGo", "6E-204", "Hyderabad", "Mumbai", "06:15 AM", "07:45 AM", 4299.0,
                "https://picsum.photos/id/1060/700/450", 42));
        flightRepository.save(new Flight("Air India", "AI-502", "Hyderabad", "Delhi", "09:30 AM", "12:00 PM", 5899.0,
                "https://picsum.photos/id/1062/700/450", 18));
        flightRepository.save(new Flight("Vistara", "UK-816", "Mumbai", "Bengaluru", "02:10 PM", "03:35 PM", 3999.0,
                "https://picsum.photos/id/1067/700/450", 27));
        flightRepository.save(new Flight("SpiceJet", "SG-118", "Delhi", "Goa", "05:45 PM", "08:20 PM", 6499.0,
                "https://picsum.photos/id/1069/700/450", 9));
        flightRepository.save(new Flight("IndiGo", "6E-711", "Bengaluru", "Hyderabad", "07:50 AM", "09:00 AM", 3299.0,
                "https://picsum.photos/id/1070/700/450", 55));
        flightRepository.save(new Flight("Air India", "AI-889", "Chennai", "Hyderabad", "11:20 AM", "12:35 PM", 3599.0,
                "https://picsum.photos/id/1074/700/450", 33));
    }

    private void seedTrains() {
        if (trainRepository.count() > 0) return;
        trainRepository.save(new Train("Rajdhani Express", "12723", "Hyderabad", "New Delhi", "06:20 PM", "10:40 AM (+1)",
                "3A", 2450.0, 60));
        trainRepository.save(new Train("Duronto Express", "12285", "Hyderabad", "Mumbai", "08:00 PM", "11:15 AM (+1)",
                "2A", 3100.0, 40));
        trainRepository.save(new Train("Shatabdi Express", "12007", "Chennai", "Bengaluru", "06:00 AM", "10:30 AM",
                "CC", 950.0, 80));
        trainRepository.save(new Train("Gatimaan Express", "12050", "New Delhi", "Agra", "08:10 AM", "09:50 AM",
                "CC", 750.0, 100));
        trainRepository.save(new Train("Konkan Kanya Express", "10111", "Mumbai", "Goa", "11:05 PM", "12:20 PM (+1)",
                "SL", 620.0, 120));
        trainRepository.save(new Train("Godavari Express", "12727", "Hyderabad", "Visakhapatnam", "06:00 PM", "07:15 AM (+1)",
                "SL", 480.0, 150));
    }

    private void seedBuses() {
        if (busRepository.count() > 0) return;
        busRepository.save(new Bus("Orange Travels", "AC Sleeper", "Hyderabad", "Bengaluru", "09:00 PM", "07:00 AM (+1)", 1450.0, 22));
        busRepository.save(new Bus("VRL Travels", "AC Seater", "Hyderabad", "Vijayawada", "07:30 AM", "01:30 PM", 650.0, 18));
        busRepository.save(new Bus("SRS Travels", "Non-AC Sleeper", "Bengaluru", "Chennai", "10:30 PM", "05:30 AM (+1)", 900.0, 30));
        busRepository.save(new Bus("Kaveri Travels", "AC Sleeper", "Mumbai", "Pune", "06:00 AM", "09:30 AM", 550.0, 25));
        busRepository.save(new Bus("Parveen Travels", "AC Seater", "Chennai", "Madurai", "11:00 PM", "06:00 AM (+1)", 1100.0, 15));
        busRepository.save(new Bus("IntrCity SmartBus", "AC Sleeper", "Delhi", "Jaipur", "11:45 PM", "05:15 AM (+1)", 999.0, 20));
    }

    private void seedMovies() {
        if (movieRepository.count() > 0) return;
        movieRepository.save(new Movie("Pathaan Returns", "Action/Thriller", "Hindi", "2h 30m", 4.3,
                "/images/movies/pathaan-returns.svg", "PVR Nexus Mall", "Hyderabad",
                "10:00 AM,1:30 PM,6:00 PM,9:30 PM", 280.0));
        movieRepository.save(new Movie("RRR: The Final Chapter", "Drama/Action", "Telugu", "2h 55m", 4.7,
                "/images/movies/rrr-final-chapter.svg", "AMB Cinemas", "Hyderabad",
                "9:30 AM,1:00 PM,5:30 PM,9:00 PM", 320.0));
        movieRepository.save(new Movie("Mumbai Nights", "Romance/Drama", "Hindi", "2h 10m", 4.0,
                "/images/movies/mumbai-nights.svg", "PVR Phoenix", "Mumbai",
                "11:00 AM,2:30 PM,7:00 PM,10:00 PM", 300.0));
        movieRepository.save(new Movie("The Last Wicket", "Sports/Drama", "English", "2h 05m", 4.2,
                "/images/movies/last-wicket.svg", "INOX Forum Mall", "Bengaluru",
                "10:15 AM,2:00 PM,6:30 PM,9:45 PM", 260.0));
        movieRepository.save(new Movie("Chennai Express 2", "Comedy/Action", "Tamil", "2h 20m", 3.9,
                "/images/movies/chennai-express-2.svg", "Sathyam Cinemas", "Chennai",
                "9:45 AM,1:15 PM,5:00 PM,8:45 PM", 240.0));
        movieRepository.save(new Movie("Delhi Detectives", "Mystery/Thriller", "Hindi", "2h 15m", 4.1,
                "/images/movies/delhi-detectives.svg", "PVR Select Citywalk", "New Delhi",
                "10:30 AM,2:15 PM,6:15 PM,9:15 PM", 310.0));
    }

    private void seedMetro() {
        if (metroRouteRepository.count() > 0) return;
        metroRouteRepository.save(new MetroRoute("Hyderabad", "Red Line", "Miyapur", "LB Nagar", 27, 55.0, "62 min"));
        metroRouteRepository.save(new MetroRoute("Hyderabad", "Blue Line", "Nagole", "Raidurg", 23, 50.0, "52 min"));
        metroRouteRepository.save(new MetroRoute("Hyderabad", "Green Line", "JBS Parade Ground", "MGBS", 10, 25.0, "22 min"));
        metroRouteRepository.save(new MetroRoute("New Delhi", "Yellow Line", "Samaypur Badli", "HUDA City Centre", 37, 60.0, "90 min"));
        metroRouteRepository.save(new MetroRoute("New Delhi", "Blue Line", "Dwarka Sector 21", "Noida Electronic City", 50, 70.0, "105 min"));
        metroRouteRepository.save(new MetroRoute("Mumbai", "Line 1", "Versova", "Ghatkopar", 12, 40.0, "35 min"));
        metroRouteRepository.save(new MetroRoute("Bengaluru", "Purple Line", "Whitefield", "Challaghatta", 37, 65.0, "95 min"));
        metroRouteRepository.save(new MetroRoute("Chennai", "Blue Line", "Wimco Nagar", "Airport", 26, 50.0, "60 min"));
    }
}
