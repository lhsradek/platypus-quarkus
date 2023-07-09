#!/bin/bash

/usr/local/bin/psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --password "$POSTGRES_PASSWORD" --dbname "$POSTGRES_DB" <<-EOSQL
        CREATE USER platypus;
        CREATE DATABASE platypus;
        GRANT ALL PRIVILEGES ON DATABASE platypus TO platypus;
        ALTER USER platypus PASSWORD 'platypus';
EOSQL
