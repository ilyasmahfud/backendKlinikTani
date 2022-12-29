# Development Guidelines (In Progress)

## 1. Project Structure
---
```bash
└── klinikTani
    ├── configuration                   => Contains configuration files for Spring Boot
    ├── exception                       => Contains custom exception files
    ├── module                          => Contains modules/features files
    │   ├── authentication
    │   │   ├── entity                  => Contains entity files that used for persistence
    │   │   ├── presenter               => Contains web adapter files
    │   │   │   ├── controller
    │   │   │   ├── request
    │   │   │   └── response
    │   │   ├── repository              => Contains JPA repository files
    │   │   └── service                 => Contains bussiness logic files
    │   ├── qna
    │   └── komunitas
    ├── utility                         => Contains utility class files
    └── KlinikTaniBackendApplication.java => Application main class
```
