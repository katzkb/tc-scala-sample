# testcontainers-scala sample project

This repository provides a sample project demonstrating how to speed up database testing in Scala 3 using [testcontainers-scala](https://github.com/testcontainers/testcontainers-scala).

## How to run the tests

### Prerequisites

- Installed JDK 21 or later
- Installed sbt

### Running the tests

1. Launch the Docker Desktop
1. Run the following command:

```bash
sbt test
```

if you installed [sdkman](https://sdkman.io/), you can run the following command:

```bash
sdk use java 21.0.2-amzn && sbt test
```
