-- PlatypusApplication 3.14.15 Flyway script 
--
-- This SQL script creates the required tables

-- SEQUENCE revinfo_seq -----

CREATE SEQUENCE revinfo_seq MINVALUE 1;

----- TABLE revinfo -----

CREATE TABLE revinfo (
    rev BIGINT DEFAULT nextval('revinfo_seq') PRIMARY KEY,
    revtstmp BIGINT
);

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
  
