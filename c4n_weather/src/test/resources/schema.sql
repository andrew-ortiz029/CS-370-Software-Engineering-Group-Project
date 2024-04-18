-- Create user table in H2 Database
CREATE TABLE user (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(64),
    name VARCHAR(255)
);

-- Create location table in H2 Database
CREATE TABLE location (
    name VARCHAR(255),
    lat DECIMAL(6, 4),
    lon DECIMAL(7, 4),
    user VARCHAR(255),
    home BOOLEAN,
    PRIMARY KEY (lat, lon)
);

CREATE TABLE all_locations (
    city VARCHAR(255),
    state_id VARCHAR(255),
    state_name VARCHAR(255),
    lat DECIMAL(6, 4),
    lon DECIMAL(7, 4),
    PRIMARY KEY (lat, lon)
);