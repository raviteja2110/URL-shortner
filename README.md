# URL Shortener Pro

A modern, full-featured URL shortening service built with Spring Boot and Thymeleaf. This application allows users to transform long, cumbersome URLs into concise, shareable links and track their performance with basic analytics.

 <!-- It's a good idea to add a screenshot of your application here -->

## ✨ Features

- **Shorten URLs**: Quickly convert any long URL into a short, easy-to-share link.
- **Redirect**: Automatically redirects short links to their original destination.
- **Click Tracking**: Monitors the number of times a link is clicked.
- **Unique Visitor Count**: Tracks the number of unique visitors based on their IP address.
- **Geolocation Analytics**: Identifies the country of origin for each click.
- **QR Code Generation**: Instantly generates a downloadable QR code for every shortened link.
- **Responsive UI**: A clean, modern, and dark-themed user interface that works on all devices.

## 🛠️ Technologies Used

### Backend
- **Java 21**: The core programming language.
- **Spring Boot 3.3.0**: Framework for building the application.
- **Spring Web**: For creating web endpoints.
- **Spring Data JPA**: For database interaction.
- **Lombok**: To reduce boilerplate code.
- **Maven**: For dependency management and building the project.

### Frontend
- **Thymeleaf**: Server-side Java template engine for creating dynamic web pages.
- **HTML5 & CSS3**: For structuring and styling the user interface.
- **JavaScript**: For client-side interactivity like copying to clipboard and downloading QR codes.
- **Font Awesome**: For icons.

### Testing
- **JUnit 5**: For unit and integration testing.
- **Mockito**: For creating mock objects in tests.
- **Spring Boot Test**: For testing Spring Boot applications.

## 🚀 Getting Started

Follow these instructions to get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- **JDK 21** or later.
- **Maven 3.x**.
- An IDE of your choice (e.g., IntelliJ IDEA, VS Code, Eclipse).

### Installation & Running

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/raviteja2110/url-shortner.git
    cd url-shortner
    ```

2.  **Build the project using Maven:**
    This command will compile the code, run tests, and package the application into a JAR file.
    ```sh
    mvn clean install
    ```

3.  **Run the application:**
    You can run the application using the Maven Spring Boot plugin or by executing the JAR file directly.

    *Using Maven:*
    ```sh
    mvn spring-boot:run
    ```

    *Or by running the JAR file:*
    ```sh
    java -jar target/url-shortner-0.0.1-SNAPSHOT.jar
    ```

4.  **Access the application:**
    Once the application is running, open your web browser and navigate to:
    [http://localhost:8080](http://localhost:8080)

## 📝 How to Use

1.  **Shorten a URL**:
    -   Go to the homepage.
    -   Paste your long URL into the input field.
    -   Click the "Shorten URL" button.

2.  **View Results**:
    -   You will be redirected to a result page showing your original URL, the new short URL, and a QR code.
    -   You can copy the short URL or download the QR code.

3.  **Use the Short Link**:
    -   Share your new short link (e.g., `http://localhost:8080/xxxxxx`).
    -   When someone accesses the link, they will be redirected to the original URL, and the click will be recorded.

## 🧪 Running Tests

To run the test suite, execute the following Maven command:
```sh
mvn test
```