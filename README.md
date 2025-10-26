# ArticleHub

A full-stack web application for managing articles and categories with user authentication.

## Features

- User registration and login
- Article management (create, edit, delete, view)
- Category management
- File upload to cloud storage
- Responsive web interface

## Tech Stack

**Backend:**
- Spring Boot 3.1.3
- MyBatis
- MySQL
- Redis
- JWT Authentication
- Alibaba Cloud OSS

**Frontend:**
- Vue.js 3
- Element Plus
- Pinia
- Axios
- Vite

## Prerequisites

- Java 17 or higher
- Node.js 16 or higher
- MySQL 8.0
- Redis

## Installation

1. Clone the repository
```bash
git clone https://github.com/Ngyama/articlehub.git
cd articlehub
```

2. Backend setup
```bash
cd backend
mvn clean install
```

3. Frontend setup
```bash
cd frontend
npm install
```

## Configuration

Update `backend/src/main/resources/application.yml` with your database and Redis settings:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database
    username: your_username
    password: your_password
  data:
    redis:
      host: localhost
      port: 6379
```

Set environment variables for Alibaba Cloud OSS:
```bash
export ALIYUN_ACCESS_KEY_ID=your_access_key_id
export ALIYUN_ACCESS_KEY_SECRET=your_access_key_secret
export ALIYUN_BUCKET_NAME=your_bucket_name
```

## Running the Application

1. Start the backend
```bash
cd backend
mvn spring-boot:run
```

2. Start the frontend
```bash
cd frontend
npm run dev
```

The application will be available at `http://localhost:5173`

## License

This project is licensed under the MIT License.
