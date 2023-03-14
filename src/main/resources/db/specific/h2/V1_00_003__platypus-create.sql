-- PlatypusApplication 3.14.15 Flyway script 
--
-- This SQL script creates the required tables

-- SEQUENCE revinfo_seq -----

CREATE SEQUENCE revinfo_seq MINVALUE 1;

-- TABLE revinfo -----

CREATE TABLE revinfo (
    rev BIGINT DEFAULT nextval('revinfo_seq') PRIMARY KEY,
    revtstmp BIGINT
);

-- TABLE platypus_counter -----

CREATE TABLE platypus_counter (
    id BIGINT DEFAULT nextval('platypus_counter_seq') PRIMARY KEY,
    counter_name VARCHAR(255) NOT NULL,
    cnt BIGINT, 
    timestmp BIGINT, 
    status VARCHAR(16) NOT NULL,
    CONSTRAINT counter_name_uk UNIQUE (counter_name)
);

-- TABLE platypus_counter_aud -----

CREATE TABLE platypus_counter_aud (
    id BIGINT NOT NULL,
    rev BIGINT REFERENCES revinfo (rev),
    revtype TINYINT,
    counter_name VARCHAR(255) NULL,
    counter_name_mod BOOLEAN,
    cnt BIGINT NULL,
    cnt_mod BOOLEAN,
    timestmp BIGINT NULL,
    timestmp_mod BOOLEAN,
    status VARCHAR(16) NULL,
    status_mod BOOLEAN,
    CONSTRAINT primary_platypus_counter_aud PRIMARY KEY (id, rev)
);
CREATE INDEX key_platypus_counter_aud_rev ON platypus_counter_aud (rev);


-- TABLE platypus_role -----

CREATE TABLE platypus_role (
  id BIGINT NOT NULL,
  role_name CHARACTER VARYING(255) NOT NULL,
  enabled BOOLEAN,
  CONSTRAINT primary_key_role PRIMARY KEY (id),
  CONSTRAINT role_name UNIQUE (role_name)
);

-- TABLE platypus_user -----

CREATE TABLE platypus_user (
  id BIGINT NOT NULL,
  user_name CHARACTER VARYING(255) NOT NULL,
  password CHARACTER VARYING(255) NOT NULL,
  account_non_expired BOOLEAN,
  account_non_locked BOOLEAN,
  credentials_non_expired BOOLEAN,
  enabled BOOLEAN,
  CONSTRAINT primary_key_user PRIMARY KEY (id),
  CONSTRAINT user_name UNIQUE (user_name)
);


-- TABLE platypus_user_role -----

CREATE TABLE platypus_user_role (
  user_id BIGINT REFERENCES platypus_user (id),
  role_id BIGINT REFERENCES platypus_role (id),
  CONSTRAINT primary_key_user_role PRIMARY KEY (user_id, role_id)
);
CREATE INDEX key_user_role_user_id ON platypus_user_role (user_id);
CREATE INDEX key_user_role_role_id ON platypus_user_role (role_id);


-- VIEW platypus_view -----

CREATE VIEW platypus_view AS SELECT ROWNUM() id, a.id user_id, a.user_name, a.enabled user_enabled, c.id role_id,
  c.role_name, c.enabled role_enabled FROM platypus_user a JOIN platypus_user_role b ON a.id = b.user_id
  JOIN platypus_role c ON b.role_id = c.id ORDER BY a.id, c.id;
  
  
-- VIEW platypus_counter_view -----

CREATE VIEW platypus_counter_view AS SELECT
    id, counter_name,
    CASE WHEN cnt > 0 THEN
        TO_CHAR (cnt) ELSE '' END cnt,
    CASE WHEN timestmp > 0 THEN
        TO_CHAR(DATEADD('SECOND', (3600 * RIGHT(CAST(CURRENT_TIMESTAMP AS TIMESTAMP WITH TIME ZONE), 2) + timestmp / 1000),
        DATE '1970-01-01'), 'YYYY-MM-DD HH24:MI:SS') ELSE '' END date,
    status
FROM platypus_counter ORDER BY id;
  

-- VIEW platypus_counter_a_view -----

CREATE VIEW platypus_counter_aud_view AS SELECT
    TO_CHAR(DATEADD('SECOND', (3600 * RIGHT(CAST(CURRENT_TIMESTAMP AS TIMESTAMP WITH TIME ZONE), 2) + revtstmp / 1000),
        DATE '1970-01-01'), 'YYYY-MM-DD HH24:MI:SS') revdate,
    a.id, a.rev, a.revtype,
    a.counter_name,
    CASE WHEN a.cnt > 0 THEN
        TO_CHAR (a.cnt) ELSE '' END cnt,
    CASE WHEN a.timestmp > 0 THEN
        TO_CHAR(DATEADD('SECOND', (3600 * RIGHT(CAST(CURRENT_TIMESTAMP AS TIMESTAMP WITH TIME ZONE), 2) + a.timestmp / 1000),
        DATE '1970-01-01'), 'YYYY-MM-DD HH24:MI:SS') ELSE '' END date
FROM platypus_counter_aud a, revinfo r WHERE a.rev = r.rev ORDER BY revtstmp, id, rev;


