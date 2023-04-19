INSERT INTO airline.airlines(id, name, active)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb45', 'airline_1', TRUE);
INSERT INTO airline.airlines(id, name, active)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb46', 'airline_2', FALSE);

INSERT INTO airline.flights(id, name, price, available)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb47', 'flight_1', 25.00, FALSE);
INSERT INTO airline.flights(id, name, price, available)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb48', 'flight_2', 50.00, TRUE);
INSERT INTO airline.flights(id, name, price, available)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb49', 'flight_3', 20.00, FALSE);
INSERT INTO airline.flights(id, name, price, available)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb50', 'flight_4', 40.00, TRUE);

INSERT INTO airline.airline_flights(id, airline_id, flight_id)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb51', 'd215b5f8-0249-4dc5-89a3-51fd148cfb45',
        'd215b5f8-0249-4dc5-89a3-51fd148cfb47');
INSERT INTO airline.airline_flights(id, airline_id, flight_id)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb52', 'd215b5f8-0249-4dc5-89a3-51fd148cfb45',
        'd215b5f8-0249-4dc5-89a3-51fd148cfb48');
INSERT INTO airline.airline_flights(id, airline_id, flight_id)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb53', 'd215b5f8-0249-4dc5-89a3-51fd148cfb46',
        'd215b5f8-0249-4dc5-89a3-51fd148cfb49');
INSERT INTO airline.airline_flights(id, airline_id, flight_id)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb54', 'd215b5f8-0249-4dc5-89a3-51fd148cfb46',
        'd215b5f8-0249-4dc5-89a3-51fd148cfb50');
