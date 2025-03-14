# 🎥 VideoStream  

VideoStream is a powerful web application designed for seamless video streaming over the internet. This project demonstrates expertise in full-stack development, encompassing video processing, streaming, and web technologies.  

The application features a **Spring Boot** backend with **Spring MVC** and **Thymeleaf** for the frontend. Users can upload videos to the backend server, and these videos can then be downloaded or streamed directly. The system also supports **chunked video streaming**, ensuring efficient and smooth playback without buffering delays.  

---

## 🚀 Features  

- 🎬 **High-Quality Video Streaming**: Stream video content smoothly in real-time.  
- 🗂️ **Video Upload & Management**: Upload videos directly to the backend server.  
- 📥 **Download Videos**: Users can download videos from the backend.  
- 🧩 **Chunked Video Streaming**: Efficient video streaming by serving videos in chunks.  
- 🔍 **Search & Filter**: Quickly find your favorite videos.  
- 🎥 **Video Playback Controls**: Full control with play, pause, volume, and fullscreen.  
- 🌐 **REST API Integration**: Seamless communication between frontend and backend.  

---

## 🛠️ Tech Stack  

**Backend:**  
- ☕ **Java 17+**  
- 🌱 **Spring Boot** (Spring MVC)  
- 🌿 **Thymeleaf** (For server-side rendering)  
- 🗃️ **MySQL** (Database)  

---

## ⚙️ Installation  

### Prerequisites  

- 🧑‍💻 **JDK 17** or above  
- 🌐 **Node.js** (Optional, if you add any JS-based interactivity)  
- 🗃️ **MySQL**  

---

### Backend Setup  

```bash
# Clone the repository
git clone https://github.com/DhruvGupta130/VideoStream.git
cd VideoStream

# Navigate to the backend folder
cd backend

# Build and run the backend
./mvnw clean install
./mvnw spring-boot:run
```

---

### Frontend Setup (Spring MVC + Thymeleaf)  

The project uses **Spring MVC** and **Thymeleaf**, so the frontend is integrated with the backend.  
Once the backend server is running, the frontend can be accessed directly.  

---

### Docker Setup (Optional)  

```bash
docker-compose up --build
```

---

## 🌐 Usage  

1. Open `http://localhost:8080` — the backend and Thymeleaf frontend run on the same port.  
2. Register or log in to start streaming videos.  
3. Upload videos directly to the backend server.  
4. Download or stream videos with chunked playback for smooth performance.  

---

## 📝 Contributing  

Contributions are welcome! Please fork the repo, create a feature branch, and submit a pull request.  

---

## 📜 License  

Licensed under the **MIT License**.  

---

## 🙏 Acknowledgments  

- **Spring Boot** for simplifying backend and server-side rendering.  
- **Thymeleaf** for seamless and elegant HTML integration.  
- **MySQL** for robust and reliable data management.  
