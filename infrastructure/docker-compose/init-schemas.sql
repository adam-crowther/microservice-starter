CREATE SCHEMA IF NOT EXISTS airline_approval;
CREATE SCHEMA IF NOT EXISTS airline_master;
CREATE SCHEMA IF NOT EXISTS customer_master;
CREATE SCHEMA IF NOT EXISTS orders;
CREATE SCHEMA IF NOT EXISTS payment;

ALTER USER POSTGRES PASSWORD NULL;

CREATE SCHEMA IF NOT EXISTS keycloak;
CREATE USER keycloak WITH PASSWORD 'keycloak';

ALTER USER keycloak PASSWORD 'keycloak';

GRANT CONNECT ON DATABASE postgres TO keycloak;
GRANT ALL ON SCHEMA keycloak TO keycloak;

GRANT ALL ON ALL TABLES IN SCHEMA keycloak TO keycloak;
GRANT ALL ON ALL SEQUENCES IN SCHEMA keycloak TO keycloak;
GRANT ALL ON ALL FUNCTIONS IN SCHEMA keycloak TO keycloak;
GRANT ALL ON ALL PROCEDURES IN SCHEMA keycloak TO keycloak;
GRANT ALL ON ALL ROUTINES IN SCHEMA keycloak TO keycloak;
