# **DE CASE Solution**

### **1- Master branch - Question 1/a**

**Overview**

This is a Spring Boot application that processes delivered orders from postgres db, transforms them into a  DeliveredOrder data transfer object, and publishes them to a Kafka topic named order_delivery_statistics.

**Setup & Installation**

The following technologies must be installed:
- Java 17+
- Maven
- Postgresql
- Kafka (via Docker)

Clone the repo : 

    https://github.com/aycaaksoy/de_case.git

Start kafka and zookeeper : 

    docker-compose up -d

Create kafka topic:

    docker exec -it kafka kafka-topics.sh --create --topic order_delivery_statistics --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1


Create a database in postgresql and run the create script:

    CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    last_updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    collection_started_at TIMESTAMP,
    collected_at TIMESTAMP,
    delivery_started_at TIMESTAMP,
    delivered_at TIMESTAMP,
    eta INT NOT NULL,
    customer_id BIGINT NOT NULL
    );

Insert some example data:

    INSERT INTO orders (created_at, last_updated_at, collection_started_at, collected_at, delivery_started_at, delivered_at, eta, customer_id)
    VALUES
    ('2025-02-12 10:00:00', '2025-02-12 10:30:00', '2025-02-12 10:10:00', '2025-02-12 10:15:00', '2025-02-12 10:20:00', '2025-02-12 10:28:00', 30, 101),
    ('2025-02-12 11:00:00', '2025-02-12 11:45:00', NULL, NULL, '2025-02-12 11:30:00', '2025-02-12 11:50:00', 45, 102),
    ('2025-02-12 12:00:00', '2025-02-12 12:10:00', '2025-02-12 12:05:00', NULL, NULL, NULL, 20, 103);

Change database username, password information in application.properties accordingly.

Build and run the application:

    mvn clean install
    mvn spring-boot:run

Make a request:

    GET http://localhost:8080/orders/process/2025-02-13

After making the request, consume messages from the topic:

    kafka-console-consumer.sh --topic order_delivery_statistics --from-beginning --bootstrap-server localhost:9092


### **1-b**


### **2-a**

### **2-b**

### **2-c**