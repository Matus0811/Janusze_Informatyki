## Backend documentation

This document represents basic backend documentation.

## Project structure

Project was divided by functionalities as a monolith. We can list packages:
- **[api](src/main/java/org/project/projectmanagementsystem/api)** - contains controllers, dto and annotations
- **[configuration](src/main/java/org/project/projectmanagementsystem/configuration)** - this is package contains configuration classes like security and filters
- **[database](src/main/java/org/project/projectmanagementsystem/database)** - contains jpa and entities
- **[domain](src/main/java/org/project/projectmanagementsystem/domain)** - contains domain objects
- **[services](src/main/java/org/project/projectmanagementsystem/services)** - contains business logic


### `API` package

#### Custom validators
To valid email and phone number there was implemented custom validators annotations