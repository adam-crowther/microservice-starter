DROP SCHEMA IF EXISTS airline_approval CASCADE;
CREATE SCHEMA IF NOT EXISTS airline_approval;

DROP SCHEMA IF EXISTS airline_master CASCADE;
CREATE SCHEMA IF NOT EXISTS airline_master;

DROP SCHEMA IF EXISTS customer_master CASCADE;
CREATE SCHEMA IF NOT EXISTS customer_master;

DROP SCHEMA IF EXISTS orders CASCADE;
CREATE SCHEMA IF NOT EXISTS orders;

DROP SCHEMA IF EXISTS payment CASCADE;
CREATE SCHEMA IF NOT EXISTS payment;

ALTER USER POSTGRES PASSWORD NULL;

CREATE SCHEMA IF NOT EXISTS keycloak;
DO
$do$
    BEGIN
        IF EXISTS (SELECT
                   FROM pg_catalog.pg_roles
                   WHERE rolname = 'keycloak') THEN

            RAISE NOTICE 'Role "keycloak" already exists. Skipping.';
        ELSE
            CREATE ROLE keycloak LOGIN PASSWORD 'keycloak';
        END IF;
    END
$do$;

ALTER USER keycloak PASSWORD 'keycloak';

GRANT CONNECT ON DATABASE postgres TO keycloak;
GRANT ALL ON SCHEMA keycloak TO keycloak;

GRANT ALL ON ALL TABLES IN SCHEMA keycloak TO keycloak;
GRANT ALL ON ALL SEQUENCES IN SCHEMA keycloak TO keycloak;
GRANT ALL ON ALL FUNCTIONS IN SCHEMA keycloak TO keycloak;
GRANT ALL ON ALL PROCEDURES IN SCHEMA keycloak TO keycloak;
GRANT ALL ON ALL ROUTINES IN SCHEMA keycloak TO keycloak;
