# Product Service

A microservice for managing products in an e-commerce platform, built with **Spring Boot 3** and **Java 21** following **Clean Architecture** principles.

## Architecture

The service is structured in three layers:

```
src/main/java/com/eduardocastro/product_service/
├── domain/                         # Core business rules (no framework dependencies)
│   ├── entity/Product.java         # Aggregate root with factory methods and domain logic
│   ├── event/                      # Domain events (ProductCreatedEvent, ProductUpdatedEvent)
│   ├── exception/                  # Domain exceptions
│   └── repository/ProductRepository.java  # Port (interface)
│
├── application/                    # Use cases / interactors
│   ├── usecase/                    # Input port interfaces
│   └── interactor/                 # Use case implementations
│
└── infrastructure/                 # Adapters (framework-specific)
    ├── persistence/                # JPA adapter, entity, mapper
    └── web/                        # REST controller, DTOs, mapper
```

### Key design decisions

- **Domain entity** (`Product`) exposes only factory methods (`create`, `reconstitute`) and domain methods (`update`). The constructor is private.
- **Validation** is enforced at the domain level via `InvalidProductDataException`.
- **Repository** is a domain interface implemented by `ProductRepositoryAdapter`, keeping JPA details out of the domain.

## Tech Stack

| Layer | Technology |
|---|---|
| Runtime | Java 21 |
| Framework | Spring Boot 3.5 |
| Persistence | Spring Data JPA + PostgreSQL |
| Validation | Jakarta Validation |
| Tests | JUnit 5, Mockito, AssertJ, Testcontainers |

## API

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/products` | List all products |

## Running Locally

**Prerequisites:** Java 21, Docker (for the database)

```bash
# Start the application (requires a PostgreSQL instance)
./mvnw spring-boot:run

# Or run with Testcontainers (spins up PostgreSQL automatically)
./mvnw spring-boot:test-run
```

## Tests

```bash
# Run all tests (unit + integration via Testcontainers)
./mvnw test
```

The test suite is organized in three groups:

| Group | Classes | What is tested |
|---|---|---|
| Domain unit | `ProductTest` | Entity creation, validation, update, domain rules |
| Application unit | `*InteractorTest` | Use case orchestration (mocked repository) |
| Infrastructure unit | `ProductJpaMapperTest`, `ProductWebMapperTest` | Mapping logic |
| Integration | `ProductRepositoryAdapterTest`, `ProductControllerTest` | Real PostgreSQL via Testcontainers, HTTP layer |

## Domain Rules

A `Product` is valid when:

- `name` is not null or empty
- `price` is greater than zero
- `stockQuantity` is greater than zero
- `category` is not null or empty
- `description` and `imageUrl` are optional

Calling `update()` with the same values as the current state is a no-op (timestamps are not touched).
