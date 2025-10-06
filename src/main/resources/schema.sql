CREATE TABLE IF NOT EXISTS weather_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    city VARCHAR(255) NOT NULL,
    temperature DOUBLE NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    description VARCHAR(500)
);
