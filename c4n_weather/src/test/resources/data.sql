-- Load a few users into user table
INSERT INTO user (username, password, name, code) VALUES ('test1', 'password', 'Test User1', null);
INSERT INTO user (username, password, name, code) VALUES ('test2', 'password', 'Test User2', null);
INSERT INTO user (username, password, name, code) VALUES ('test3', 'password', 'Test User3', null);

-- Load a few locations into location table
INSERT INTO location (lat, lon, user, home) VALUES (1.1111, 1.1111, 'user1', 1);
INSERT INTO location (lat, lon, user, home) VALUES (2.2222, 2.2222, 'user1', 0);
INSERT INTO location (lat, lon, user, home) VALUES (3.3333, 3.3333, 'user1', 0);

-- Load a few locations into all_locations table
INSERT INTO all_locations (city, state_id, state_name, lat, lon) VALUES ('City1', 'CA', 'California', 1.1111, 1.1111);
INSERT INTO all_locations (city, state_id, state_name, lat, lon) VALUES ('City2', 'SD', 'South Dakota', 2.2222, 2.2222);