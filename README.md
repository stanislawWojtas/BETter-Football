# ⚽ BETter Football

**A Full-Stack Sports Betting Simulation Platform**

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-green?style=flat-square&logo=springboot)
![React](https://img.shields.io/badge/React-19-blue?style=flat-square&logo=react)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Enabled-336791?style=flat-square&logo=postgresql)

**BETter Football** is a comprehensive web application designed to simulate the real-world experience of sports betting. It allows users to engage with real-time odds from the top 5 European football leagues, build betting slips, and compete within a social network of friends.

Built to demonstrate proficiency in modern **Microservice-ready Architecture**, and **Secure Data Processing**, this project serves as a showcase of Full-Stack development capabilities using the latest industry standards (Java 21, React 19).

---

## Technical Architecture

The application is engineered with a focus on separation of concerns, data integrity, and scalability.

### Backend System (Spring Boot & Java 21)
The server-side logic is built on **Spring Boot 3.5**, utilizing a layered architecture to ensure modularity.
* **Security & Authentication:** Implements a robust, stateless security model using **Spring Security** and **JWT (JSON Web Tokens)**. Custom filters (`JwtFilter`) intercept requests to validate sessions, ensuring secure API access.
* **Data Synchronization Engine:** To mimic a live betting environment, the system employs **Scheduled Tasks** (`@Scheduled`). A dedicated `OddsImportScheduler` periodically fetches and synchronizes match odds and results from external Sports APIs for leagues including the Premier League, La Liga, and Serie A.
* **RESTful API:** Exposes a well-documented API for the frontend, handling complex operations like bet settlement, friend graph management, and statistical aggregation.

### Frontend Application (React 19 & TypeScript)
The client-side is a high-performance Single Page Application (SPA) built with **Vite**.
* **Modern React Patterns:** Leverages **React 19** features and **TypeScript** for strong typing and reduced runtime errors.
* **State Management:** Utilizes **React Context API** to manage global application states, such as the active `BetSlip`, providing a seamless "shopping cart" experience for bets.
* **Network Layer:** Features a centralized **Axios** configuration with interceptors to handle JWT injection and global error responses (e.g., automatic session expiration handling).
* **UI/UX:** Designed with **Chakra UI** to be fully responsive and accessible.

---

## Key Features

### 1. Advanced Betting Engine
* **Real-Time Data:** Users interact with actual market odds, updated dynamically via background schedulers.
* **Bet Slip Management:** A dynamic slip system allows users to combine multiple picks, calculate potential returns, and place bets instantly.
* **Automated Settlement:** The system automatically validates placed bets against match results once concluded, updating user balances and statistics without manual intervention.

### 2. Social Ecosystem
* **Friend Network:** Users can build a social graph by sending and accepting friend requests.
* **Social Activity Feed:** The "Friend Bets" module allows users to track the betting activity of their connections, fostering a competitive social environment.

---

## Technology Stack

| Category | Technology |
|----------|------------|
| **Core Backend** | Java 21, Spring Boot 3.5.4 |
| **Database** | PostgreSQL |
| **Frontend** | React 19, TypeScript, Vite 7 |
| **Security** | Spring Security, JJWT (JWT), BCrypt |
| **UI Library** | Chakra UI |
| **Testing** | JUnit 5, Mockito |
| **Build Tools** | Maven, NPM |

---

## Author

**Stanisław Wojtas**

*Project created for educational purposes and portfolio demonstration.*
