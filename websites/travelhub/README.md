# TravelHub — Multi-Category Travel Booking Platform (Spring Boot + Maven + Tomcat 9)

A full-stack, colourful travel booking web app in the spirit of MakeMyTrip/Bookmytrip: **hotels, flights, trains, buses, movie tickets, and metro tickets** — each with search, listing pages with images, a generic booking flow, and a simulated payment checkout — built with Java, Spring Boot, Thymeleaf, and Maven, packaged as a WAR for deployment on Apache Tomcat 9.

## Tech stack

- **Java 21** (openjdk-21-jdk)
- **Spring Boot 2.7.18** (Web MVC, Spring Data JPA, Validation, Thymeleaf) — the last Spring Boot line built on the `javax.*` servlet/persistence namespace, which is what Tomcat 9 (Servlet 4.0) understands. Spring Boot 3.x moved to `jakarta.*` and requires Tomcat 10+, so it is intentionally not used here.
- **Apache Tomcat 9** as the target servlet container (packaging is `war`, not a runnable jar)
- **H2** in-memory database for local development (auto-seeded with sample data)
- **PostgreSQL** for production (swap in with three environment variables — no code changes)
- Plain HTML/CSS rendered server-side via Thymeleaf (no separate JS build step, no frontend framework)
- Maven build

## Categories covered

| Category | List/search page | Detail / inline booking | Sample data seeded |
|---|---|---|---|
| 🏨 Hotels | `/hotels?city=` | `/hotels/{id}` | 8 hotels across Hyderabad, Mumbai, Delhi, Bengaluru, Goa |
| ✈️ Flights | `/flights?source=&destination=&date=` | inline on list | 6 domestic routes |
| 🚆 Trains | `/trains?source=&destination=&date=` | inline on list | 6 named trains (Rajdhani, Duronto, Shatabdi...) |
| 🚌 Buses | `/buses?source=&destination=&date=` | inline on list | 6 operators/routes |
| 🎬 Movies | `/movies?city=` | `/movies/{id}` (pick showtime) | 6 movies across cities/languages |
| 🚇 Metro | `/metro?city=&source=&destination=` | inline on list | 8 routes (Hyderabad, Delhi, Mumbai, Bengaluru, Chennai) |

Every category feeds into the **same generic booking + payment flow**:

```
POST /booking/create           → creates a Booking (status PENDING_PAYMENT), redirects to payment
GET  /booking/{ref}/pay        → payment summary + method selection (UPI / Card / Net Banking / Wallet)
POST /booking/{ref}/pay        → simulates payment, marks Booking CONFIRMED, creates a Payment record
GET  /booking/{ref}/confirmation → e-ticket / confirmation page with reference & transaction ID
```

This means adding a 7th category (say, cabs or ferries) only requires a new entity + repository + controller — the booking/payment/confirmation pipeline is reused as-is.

## Project structure

```
travelhub/
├── pom.xml
├── Dockerfile
├── docker-entrypoint.sh         # rewrites Tomcat's connector port from $PORT at container start
├── Procfile                     # runs the WAR on Tomcat 9 via Webapp Runner (Heroku/Railway-style)
├── render.yaml                  # Render.com blueprint (Docker + Tomcat 9)
└── src/main/
    ├── java/com/travelhub/
    │   ├── TravelHubApplication.java
    │   ├── config/DataLoader.java              # seeds hotels/flights/trains/buses/movies/metro on startup
    │   ├── model/                              # Hotel, Flight, Train, Bus, Movie, MetroRoute, Booking, Payment...
    │   ├── repository/                         # Spring Data JPA repositories, one per entity
    │   ├── service/BookingService.java         # booking reference generation + simulated payment processing
    │   └── controller/                         # Home, Hotel, Flight, Train, Bus, Movie, Metro, Booking controllers
    └── resources/
        ├── application.properties              # dev profile (H2)
        ├── application-prod.properties         # prod profile (PostgreSQL, via env vars)
        ├── templates/                           # Thymeleaf pages + fragments/layout.html (navbar/footer)
        └── static/css/style.css                 # colourful booking-site theme
```

## Prerequisites

```bash
sudo apt install -y openjdk-21-jdk
sudo apt install -y maven
```

Tomcat 9 itself is only required if you choose to deploy the WAR to a manually-installed Tomcat 9 (see "Run locally" below) rather than using the Docker image or Webapp Runner path.

```bash
sudo apt install -y tomcat9
```

## Run locally

### Option 1 — Build the WAR and drop it into a local Tomcat 9

```bash
mvn clean package
sudo cp target/travelhub-ecommerce.war /var/lib/tomcat9/webapps/ROOT.war
sudo systemctl restart tomcat9
```

Visit `http://localhost:8080`.

### Option 2 — Build and run with Webapp Runner (no Tomcat install needed)

```bash
mvn clean package
java -jar target/dependency/webapp-runner.jar --port 8080 target/travelhub-ecommerce.war
```

Visit `http://localhost:8080`. The H2 database is in-memory and reseeds every restart — no setup needed. (H2 console, if you want to inspect data: `http://localhost:8080/h2-console`, JDBC URL `jdbc:h2:mem:travelhub`, user `sa`, blank password.)

### Option 3 — plain Spring Boot run (quickest for local dev)

```bash
mvn spring-boot:run
```

## Deploy to the cloud

The app is 12-factor style: it reads its port from `PORT` and its database from `SPRING_DATASOURCE_URL` / `SPRING_DATASOURCE_USERNAME` / `SPRING_DATASOURCE_PASSWORD`, activated via the `prod` Spring profile. Pick whichever platform you prefer:

### Option A — Render.com (matches the included `render.yaml`)

1. Push this project to a GitHub repo.
2. On Render: **New → Blueprint**, point it at the repo (`render.yaml` is already included). It builds the included `Dockerfile`, which runs Tomcat 9 on Java 21 and deploys the WAR as `ROOT.war`. `docker-entrypoint.sh` rewrites Tomcat's connector port to Render's `$PORT` automatically.
3. Create a free PostgreSQL instance on Render, then in the web service's Environment tab set `SPRING_DATASOURCE_URL` (convert Render's `postgres://user:pass@host:5432/db` to `jdbc:postgresql://host:5432/db`), `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`.

### Option B — Docker (works on any cloud: Railway, Fly.io, AWS App Runner, Azure, GCP Cloud Run)

```bash
docker build -t travelhub-ecommerce .
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/<db> \
  -e SPRING_DATASOURCE_USERNAME=<user> \
  -e SPRING_DATASOURCE_PASSWORD=<password> \
  travelhub-ecommerce
```

The image is built from `tomcat:9.0-jdk21-temurin`; the WAR is deployed as `ROOT.war` so the app serves from `/`. Push the same image to any container registry (Docker Hub, ECR, GCR, ACR) and point your cloud platform's container service at it. This is a server-rendered Java app on Tomcat, not a static site, so S3 static hosting alone cannot run it.

### Option C — Railway / Heroku (buildpack, no Docker needed)

Both detect the `pom.xml` and `Procfile` automatically:

1. Push to GitHub, create a new app, connect the repo.
2. Add a PostgreSQL plugin/add-on.
3. Set `SPRING_PROFILES_ACTIVE=prod` and the three `SPRING_DATASOURCE_*` variables from the database credentials the platform gives you.
4. Deploy — the Procfile runs the WAR on Tomcat 9 via Webapp Runner (`target/dependency/webapp-runner.jar`, fetched automatically during `mvn package`).

### Option D — AWS Elastic Beanstalk / EC2 / a traditional VM with Tomcat 9

```bash
mvn clean package
scp target/travelhub-ecommerce.war user@your-server:/tmp/
ssh user@your-server
sudo apt install -y openjdk-21-jdk tomcat9
sudo cp /tmp/travelhub-ecommerce.war /var/lib/tomcat9/webapps/ROOT.war
sudo systemctl restart tomcat9
```

Set `SPRING_PROFILES_ACTIVE=prod` and the `SPRING_DATASOURCE_*` variables as environment variables for the `tomcat9` service (e.g. in `/etc/default/tomcat9` or a systemd override), then restart. Put it behind Nginx / an ALB for TLS termination.

## How booking + payment works (important: this is a simulation)

Payments are **not** wired to a real payment gateway. `BookingService.processPayment()` marks the booking `CONFIRMED` and generates a fake transaction ID immediately — there is no actual money movement, card validation, or fraud checking. This mirrors how the reference Bakers.in project captured payment method as order metadata only. Before using this for anything real, you'd need to:

- Integrate an actual gateway (Razorpay is the natural choice for INR; Stripe for international) using their server-side SDKs and webhook confirmation, rather than trusting the client's POST directly.
- Never mark a booking CONFIRMED from a client-submitted form post alone — confirm status from the gateway's webhook/callback.

## Before going to production

- [ ] Wire the payment method selector to a real gateway (Razorpay/Stripe) with webhook-based confirmation — currently a simulated success.
- [ ] Replace placeholder Unsplash images with licensed/product photography.
- [ ] Point `SPRING_DATASOURCE_URL` at a managed PostgreSQL instance (Render, RDS, Supabase, Neon, etc.).
- [ ] Set `spring.jpa.hibernate.ddl-auto=validate` once you've reviewed the generated schema, and manage schema changes with Flyway/Liquibase instead of `update`.
- [ ] Add real seat maps / room inventory / showtime capacity instead of a flat `seatsAvailable` counter (no concurrency control yet — two people can "book" the last seat).
- [ ] Add user accounts/authentication (Spring Security) so bookings are tied to a logged-in user instead of a free-text email.
- [ ] Add an admin view for managing hotels/flights/trains/buses/movies/metro routes instead of editing `DataLoader.java`.
- [ ] Add HTTPS (most platforms above provide this automatically) and set secure cookie flags.
- [ ] Add input validation (`@Valid` + Bean Validation annotations) on the booking form fields.

## A note on this build

This project could not be compiled inside the sandbox that generated it, because that environment has no outbound network access to reach Maven Central. Every file was written and manually reviewed for correctness (imports, JPA/Thymeleaf expressions, brace balance), but please run `mvn clean package` as your first step locally or in CI to confirm a clean build before deploying, and open an issue/ask if anything doesn't compile.
