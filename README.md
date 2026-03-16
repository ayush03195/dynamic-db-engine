# Dynamic Database Maintenance Engine

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14+-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

A metadata-driven backend engine built using **Java 17, Spring Boot, and PostgreSQL** that allows retrieving data from multiple business tables using a **single dynamic API**.

The system uses **Java Reflection and database configuration tables** to dynamically determine which entity, repository, and dropdown relationships to load — eliminating the need for table-specific backend code.

This architecture is commonly used in **enterprise data maintenance modules** where new tables must be onboarded without backend changes.

---

## Problem Statement

Traditional backend implementations create **separate APIs, services, and repositories for each table**, leading to:

- Repetitive CRUD code across every table
- Tightly coupled backend layers
- Difficult maintainability and slow onboarding
- Poor scalability when new entities are added

This engine solves all of the above using a **single API driven entirely by database configuration**.

---

## Features

- Single API endpoint for multiple database tables
- Metadata-driven architecture — add new tables without code changes
- Dynamic entity resolution using Java Reflection
- Dynamic repository resolution via RepositoryRegistry
- Pagination support for large datasets (100k+ records)
- View-based dropdown resolution for nested relationships
- Metadata caching to optimize Reflection performance
- Clean layered Spring Boot architecture

---

## Technology Stack

| Technology | Usage |
|---|---|
| Java 17 | Core language |
| Spring Boot | Application framework |
| Spring Data JPA | ORM and repository layer |
| JDBC Template | View-based dropdown queries |
| PostgreSQL | Primary database |
| Maven | Build tool |
| Lombok | Boilerplate reduction |
| Swagger / OpenAPI | API documentation |

---

## Architecture
```
Controller
   ↓
Service (Interface)
   ↓
DynamicTableServiceImpl
   ↓
RepositoryRegistry  ←→  MetadataCache
   ↓
JpaRepository (dynamically resolved)
   ↓
Reflection Mapping (Entity → Map)
   ↓
ViewQueryRepository (dropdown resolution)
```

---

## Business Data Hierarchy
```
Country
   ↓
Region
   ↓
Plant
   ↓
Location
   ↓
Supplier
   ↓
Part
   ↓
ManufacturingDetail
```

---

## Metadata Tables

### table_config

| Column | Description |
|---|---|
| id | Primary key |
| table_name | Logical table name used in API request |
| entity_name | Fully qualified Java entity class name |

Example:

| table_name | entity_name |
|---|---|
| supplier | Supplier |
| part | Part |
| country | Country |

### field_config

| Column | Description |
|---|---|
| id | Primary key |
| table_config_id | Foreign key reference to table_config |
| column_key | UI column identifier |
| entity_field_name | Java entity field name used in Reflection |
| content_type | TEXT or DROPDOWN |
| table_view | Database view used to resolve dropdown label |

---

## Project Structure
```
src/main/java/com/example/dynamicdb/
│
├── controller/
├── service/
├── serviceImpl/
├── repository/
├── registry/
├── entity/
├── dto/
├── util/
└── exception/

database/
├── schema.sql
├── sample-data.sql
├── config-data.sql
└── views.sql

postman/
docs/
```

---

## Prerequisites

- Java 17+
- PostgreSQL 14+
- Maven 3.8+

---

## Database Setup

**Step 1 — Create the database**
```sql
CREATE DATABASE dynamicdb;
```

**Step 2 — Run SQL scripts in this exact order**
```
database/schema.sql
database/sample-data.sql
database/config-data.sql
database/views.sql
```

---

## Configuration
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/dynamicdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=false

springdoc.swagger-ui.path=/swagger-ui.html
```

---

## Running the Application
```bash
mvn spring-boot:run
```

Application starts at:
```
http://localhost:8080
```

Swagger UI:
```
http://localhost:8080/swagger-ui.html
```

---

## API Reference

### Fetch table data dynamically
```
GET /api/tables/{tableName}?page=0&size=20
```

| Parameter | Type | Description |
|---|---|---|
| tableName | Path | Logical table name from table_config |
| page | Query | Page number (default 0) |
| size | Query | Page size (default 20) |

**Example — Fetch supplier data**
```bash
curl -X GET "http://localhost:8080/api/tables/supplier?page=0&size=20"
```

**Response**
```json
{
  "page": 0,
  "size": 20,
  "totalRecords": 4,
  "data": [
    {
      "id": 1,
      "name": "ABC Supplier",
      "location": [
        {
          "id": 1,
          "label": "ABC Supplier - Warehouse A - Plant A - North - India"
        }
      ]
    }
  ]
}
```

**Example — Fetch country data**
```bash
curl -X GET "http://localhost:8080/api/tables/country?page=0&size=10"
```

---

## Supported Tables

| Table Name | Description |
|---|---|
| country | Top-level geography |
| region | Region under country |
| plant | Plant under region |
| location | Location under plant |
| supplier | Supplier under location |
| part | Part under supplier |
| manufacturing_detail | Manufacturing detail under part |

---

## Key Design Decisions

**Why Java Reflection?**
Reflection allows the engine to resolve entity classes and field mappings at runtime from database configuration, making the system completely table-agnostic.

**Why Metadata Caching?**
Reflection scanning is expensive. The MetadataCache ensures each entity is scanned only once per application lifecycle, not on every request.

**Why Database Views for Dropdowns?**
Nested relationships like `Supplier → Location → Plant → Region → Country` would cause recursive queries and N+1 problems if resolved lazily. Views flatten this hierarchy into a single efficient query.

---

## Future Improvements

- Dynamic filtering by any field
- Dynamic sorting
- Dynamic save and update API
- Dynamic delete API
- Role-based access control per table
- UI integration

---

## Author

**Ayush Rana**
Senior Software Engineer | Java 17 | Spring Boot | GenAI
Infosys Certified Generative AI Professional (valid till 2028)

- LinkedIn: [linkedin.com/in/ayush03195](https://www.linkedin.com/in/ayush03195)
- GitHub: [github.com/ayush03195](https://github.com/ayush03195)

---

## License

MIT License