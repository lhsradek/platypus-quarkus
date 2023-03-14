--
-- PlatypusApplication 3.14.15 Flyway script 
--
-- This SQL script creates the required tables

-- SEQUENCE hibernate_sequence -----

CREATE SEQUENCE hibernate_sequence INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

/* -- CREATE SEQUENCE hibernate_sequence MINVALUE 1 START 1; */

-- TABLE revinfo -----

CREATE TABLE revinfo (
    rev BIGINT NOT NULL,
    revtstmp BIGINT NULL,
    CONSTRAINT revinfo_rev_pkey PRIMARY KEY (rev)
);

-- TABLE platypus_counter -----

CREATE TABLE platypus_counter (
    id BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    counter_name VARCHAR(255) NOT NULL,
    cnt BIGINT, 
    timestmp BIGINT,
    status VARCHAR(16) NOT NULL,
    CONSTRAINT platypus_counter_id_pkey PRIMARY KEY (id),
    CONSTRAINT platypus_counter_name_uk UNIQUE (counter_name)
);


-- TABLE platypus_counter_aud -----

CREATE TABLE platypus_counter_aud (
    id BIGINT NOT NULL,
    rev BIGINT REFERENCES revinfo (rev) NOT NULL,
    revtype SMALLINT NULL,
    counter_name VARCHAR(255) NULL,
    counter_name_mod BOOLEAN NULL,
    cnt BIGINT NULL,
    cnt_mod BOOLEAN,
    timestmp BIGINT NULL,
    timestmp_mod BOOLEAN,
    status VARCHAR(16) NULL,
    status_mod BOOLEAN,
    CONSTRAINT platypus_counter_aud_pkey PRIMARY KEY (id, rev)
);
CREATE INDEX platypus_counter_aud_rev_key ON platypus_counter_aud USING btree (rev);

-- TABLE platypus_role -----

CREATE TABLE platypus_role (
  id BIGINT NOT NULL,
  role_name VARCHAR(255) NOT NULL,
  enabled BOOLEAN,
  CONSTRAINT platypus_role_id_pkey PRIMARY KEY (id),
  CONSTRAINT platypus_role_name_uk UNIQUE (role_name)
);


-- TABLE platypus_user -----

CREATE TABLE platypus_user (
  id BIGINT NOT NULL,
  user_name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  account_non_expired BOOLEAN,
  account_non_locked BOOLEAN,
  credentials_non_expired BOOLEAN,
  enabled BOOLEAN,
  CONSTRAINT platypus_user_id_pkey PRIMARY KEY (id),
  CONSTRAINT platypus_user_name_uk UNIQUE (user_name)
);


-- TABLE platypus_user_role -----

CREATE TABLE platypus_user_role (
  user_id BIGINT REFERENCES platypus_user (id) NOT NULL,
  role_id BIGINT REFERENCES platypus_role (id) NOT NULL,
  CONSTRAINT platypus_user_role_pkey PRIMARY KEY (user_id, role_id)
);


-- VIEW platypus_view -----

CREATE VIEW platypus_view AS SELECT ROW_NUMBER() OVER (ORDER BY a.id, c.id) as id, a.id user_id, a.user_name,
  a.enabled user_enabled, c.id role_id, c.role_name, c.enabled role_enabled FROM platypus_user a
  JOIN platypus_user_role b ON a.id = b.user_id JOIN platypus_role c ON b.role_id = c.id ORDER BY a.id, c.id;
  
-- VIEW platypus_counter_view -----

CREATE VIEW platypus_counter_view AS SELECT
    id, counter_name,
    CASE WHEN cnt = 0 THEN '' ELSE cnt::TEXT END cnt,
    CASE WHEN timestmp = 0 THEN '' ELSE
        TO_CHAR(TO_TIMESTAMP(timestmp / 1000),
        'YYYY-MM-DD HH24:MI:SS') END date,
    status
FROM platypus_counter ORDER BY id;


-- VIEW platypus_counter_a_view -----

CREATE VIEW platypus_counter_aud_view AS SELECT
    TO_CHAR(TO_TIMESTAMP(revtstmp / 1000),
        'YYYY-MM-DD HH24:MI:SS') revdate,
    a.id, a.rev, a.revtype,
    a.counter_name,
    CASE WHEN cnt = 0 THEN '' ELSE cnt::TEXT END cnt,
    CASE WHEN timestmp = 0 THEN '' ELSE
        TO_CHAR(TO_TIMESTAMP(timestmp / 1000),
        'YYYY-MM-DD HH24:MI:SS') END date
FROM platypus_counter_aud a, revinfo r WHERE a.rev = r.rev ORDER BY revtstmp, id, rev;



