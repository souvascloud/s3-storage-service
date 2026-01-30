# S3 Storage Service
**Hybrid S3 Integration Platform (Direct API + Presigned Architecture)**

Base API Version: `/api/v1/files`

---

## Overview

`s3-storage-service` is a production-grade Spring Boot application that provides a **hybrid architecture** for Amazon S3 file operations:

1. **Direct API Mode** – Backend uploads/downloads files to S3  
2. **Presigned Mode** – Clients upload/download directly to S3 using presigned URLs  

This design enables both:
- **control-first architecture** (API-managed data flow)
- **scale-first architecture** (S3-managed data flow)

The project is designed for **any production systems**, with:
- IAM Role–based authentication (no access keys)
- Private S3 buckets
- Secure architecture
- Config-driven runtime behavior
- Dockerized deployment
- Cloud-native patterns

---

## Core Design Philosophy

### Security
- No AWS access keys in code
- No secrets in configuration
- IAM Role–based authentication
- Private S3 bucket access
- Principle of least privilege

### Architecture
- Control plane in API
- Data plane in S3
- Hybrid model (API proxy + direct S3 access)
- Separation of concerns
- Cloud-native design

### Scalability
- Direct upload/download for controlled workflows
- Presigned URLs for large files and high traffic
- Horizontal scalability via stateless services
- S3 handles bandwidth and storage scaling

---

## Architecture Overview

### Direct Mode (API Proxy)
```
Client → API → S3
```

Use case:
- Secure internal systems
- Regulated data
- Controlled access
- Audit logging
- Compliance systems

### Presigned Mode (Direct S3 Access)
```
Client → API (get URL)
Client → S3 (upload/download)
```

Use case:
- Large file uploads
- High traffic downloads
- Mobile/web clients
- Performance-critical flows
- Cost-optimized architectures

---

## Features

- Private S3 integration
- IAM Role authentication
- Direct upload/download APIs
- Presigned upload/download APIs
- Hybrid architecture support
- Dockerized deployment
- Runtime configuration
- Validation layer
- Global error handling
- Swagger/OpenAPI documentation
- Clean layered architecture
- Production-grade logging
- Configurable limits
- Secure by default

---

## API Design (Versioned)

Base Path: `/api/v1/files`

### Direct APIs (API → S3)

| Method | Endpoint | Purpose |
|------|--------|--------|
| POST | `/api/v1/files/upload` | Upload file via backend |
| GET | `/api/v1/files/download` | Download file via backend |

### Presigned APIs (Client → S3)

| Method | Endpoint | Purpose |
|------|--------|--------|
| POST | `/api/v1/files/presign/upload` | Generate presigned upload URL |
| GET | `/api/v1/files/presign/download` | Generate presigned download URL |

---

## S3 Design Model

- Bucket is **private**
- Public access is blocked
- Access is granted only to IAM Role
- Application identity is infrastructure-based
- No credential distribution
- No secret management in application layer

---

## Authentication Model

```
Application → EC2 IAM Role → STS → Temporary Credentials → S3
```

---

## Configuration Model

All configuration is injected at runtime via environment variables.

Example:

```yaml
storage:
  s3:
    bucket-name: ${S3_BUCKET_NAME}
    region: ${AWS_REGION}
    base-path: ${S3_BASE_PATH}
    presign-expiry-minutes: ${PRESIGN_EXPIRY_MINUTES}
```

---

## Running the Project

### Prerequisites

- Java 17
- Docker
- Maven
- AWS Account
- EC2 with IAM Role
- Private S3 bucket
- IAM Role with S3 permissions

---

## IAM Role Policy (Example)

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": ["s3:PutObject", "s3:GetObject"],
      "Resource": "arn:aws:s3:::souva-prod-file-storage/*"
    }
  ]
}
```

---

## Build Application

```bash
mvn clean package -DskipTests
```

---

## Docker Build

```bash
docker build -t s3-storage-service .
```

---

## Docker Run

```bash
docker run -d -p 8080:8080 \
  -e S3_BUCKET_NAME=your-bucket \
  -e AWS_REGION=your-region \
  -e S3_BASE_PATH=yourpath \
  -e PRESIGN_EXPIRY_MINUTES=any_number \
  -e MAX_FILE_SIZE=200MB \
  -e MAX_REQUEST_SIZE=200MB \
  -e SERVER_PORT=8080 \
  s3-storage-service
```

---

## Swagger Documentation

```
http://localhost:8080/swagger-ui/index.html
```

OpenAPI spec:

```
http://localhost:8080/v3/api-docs
```

---

## Example Flows

### Direct Upload Flow
```
Client → API → S3
```

### Presigned Upload Flow
```
Client → API (get URL)
Client → S3 (upload directly)
```

### Direct Download Flow
```
Client → API → S3 → API → Client
```

### Presigned Download Flow
```
Client → API (get URL)
Client → S3 (download directly)
```

---

## Architecture Diagrams (PlantUML)

All diagrams are provided in PlantUML format for downloadable image generation.

### System Architecture



### Direct Upload Flow


### Presigned Upload Flow


### Presigned Download Flow


### Sequence Diagram

---

## Production Readiness

- Stateless service design
- Horizontal scaling
- IAM role based security
- Private S3 bucket
- Config-driven runtime
- Dockerized deployment
- Cloud-native architecture
- Clean dependency model
- Validation layer
- Error handling
- Observability ready
- API documentation
- Security-first design

---

## Design Principles

- Infrastructure defines identity
- Application defines behavior
- API defines control
- S3 defines storage
- IAM defines trust
- Config defines environment
- Docker defines runtime
- Architecture defines scalability

---

## Architecture Summary

This project demonstrates a **real enterprise cloud architecture pattern**:

- Hybrid storage access model  
- Secure identity-based authentication  
- Separation of control plane and data plane  
- Cloud-native runtime design  
- Infrastructure-driven security  
- Scalable data handling  
- Production-grade system design  

---

## Intended Use Cases

- Enterprise file platforms  
- SaaS storage systems  
- Internal document systems  
- Media platforms  
- LMS platforms  
- Fintech document storage  
- Healthcare file systems  
- Secure enterprise portals  
- Cloud-native microservices  
- API-driven platforms  

---

## License

Internal / Educational / Enterprise Architecture Reference

---

## Author

Senior Software Engineer
Passionate about backend systems, design, and clean code.
LinkedIn: https://www.linkedin.com/in/souvanik-saha

