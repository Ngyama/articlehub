# ArticleHub

A full-stack web application for managing articles and categories with user authentication.

## Features

### User Management
- User registration and login
- JWT token authentication
- Update user profile (nickname, email)
- Update avatar (upload to Alibaba Cloud OSS)
- Change password

### Article Management
- Create article with rich text editor
- View article list with pagination
- Edit article
- Delete article
- Upload cover image to Alibaba Cloud OSS
- Article status management (published/draft)
- Filter articles by category and status

### Category Management
- Create category
- View category list
- Edit category
- Delete category

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

Update `backend/src/main/resources/application.yml` with your database, Redis, and OSS settings:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/article_hub
    username: your_username
    password: your_password
  data:
    redis:
      host: localhost
      port: 6379

aliyun:
  oss:
    endpoint: https://oss-ap-northeast-1.aliyuncs.com
    access-key-id: your_access_key_id
    access-key-secret: your_access_key_secret
    bucket-name: your_bucket_name
```

**Note:** 
- Create the MySQL database `article_hub` before running the application
- Update the OSS endpoint according to your bucket region
- Redis is configured but currently not used in the code

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
