--
-- PlatypusApplication 3.14.15 Flyway script 
--
-- This SQL script creates the required tables

-- SEQUENCE hibernate_sequence -----

CREATE SEQUENCE hibernate_sequence MINVALUE 1;

CREATE TABLE revinfo (
  rev INTEGER AUTO_INCREMENT,
  revtstmp BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (rev)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE platypus_counter (
    id BIGINT(20) AUTO_INCREMENT,
    counter_name VARCHAR(255) DEFAULT NULL,
    cnt BIGINT(20) DEFAULT NULL,
    timestmp BIGINT(20) DEFAULT NULL,
    status VARCHAR(16) DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY platypus_platypus_name_uk (counter_name)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE platypus_counter_aud (
    id BIGINT(20) NOT NULL,
    rev INTEGER REFERENCES revinfo (rev),
    revtype TINYINT(4) DEFAULT NULL,
    counter_name VARCHAR(255) DEFAULT NULL,
    countername_mod BIT(1) DEFAULT NULL,
    cnt BIGINT(20) DEFAULT NULL,
    cnt_mod BIT(1) DEFAULT NULL,
    timestmp BIGINT(20) DEFAULT NULL,
    timestmp_mod BIT(1) DEFAULT NULL,
    status VARCHAR(16) DEFAULT NULL,
    status_mod BIT(1) DEFAULT NULL,
    PRIMARY KEY (id,rev),
    CONSTRAINT platypus_counter_aud_rev FOREIGN KEY (rev) REFERENCES revinfo (rev)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE platypus_role (
  id BIGINT(20) AUTO_INCREMENT,
  role_name VARCHAR(255) DEFAULT NULL,
  enabled BIT(1) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY platypus_role_name_uk (role_name)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE platypus_user (
  id BIGINT(20) AUTO_INCREMENT,
  user_name VARCHAR(255) DEFAULT NULL,
  password VARCHAR(255) DEFAULT NULL,
  account_non_expired BIT(1) DEFAULT NULL,
  account_non_locked BIT(1) DEFAULT NULL,
  credentials_non_expired BIT(1) DEFAULT NULL,
  enabled BIT(1) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY platypus_user_name_uk (user_name)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE platypus_user_role (
  user_id BIGINT(20) NOT NULL,
  role_id BIGINT(20) NOT NULL,
  PRIMARY KEY (user_id,role_id),
  KEY platypus_user_role_user_id (user_id),
  KEY platypus_user_role_role_id (role_id),
  CONSTRAINT platypus_user_role_user_id FOREIGN KEY (user_id) REFERENCES platypus_user (id),
  CONSTRAINT platypus_user_role_role_id FOREIGN KEY (role_id) REFERENCES platypus_role (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE VIEW platypus_view AS SELECT ROW_NUMBER() OVER (ORDER BY a.id, c.id) as id, a.id user_id, a.user_name,
  a.enabled user_enabled, c.id role_id, c.role_name, c.enabled role_enabled FROM platypus_user a
  JOIN platypus_user_role b ON a.id = b.user_id JOIN platypus_role c ON b.role_id = c.id ORDER BY a.id, c.id;
  
CREATE VIEW platypus_counter_view AS SELECT
    id,
    counter_name,
    IF (cnt = 0, "", cnt) cnt,
    IF (timestmp = 0, "",
        FROM_UNIXTIME(timestmp / 1000, '%Y-%m-%d %H:%i:%s')) date,
    status
FROM platypus_counter ORDER BY id;

CREATE VIEW platypus_counter_aud_view AS SELECT
    FROM_UNIXTIME(revtstmp / 1000, '%Y-%m-%d %H:%i:%s') revdate,
    a.id, a.rev, revtype,
    a.counter_name,
    IF (cnt = 0, "", cnt) cnt,
    IF (timestmp = 0, "",
        FROM_UNIXTIME(timestmp / 1000, '%Y-%m-%d %H:%i:%s')) date
FROM platypus_counter_aud a, revinfo r WHERE a.rev = r.rev ORDER BY revtstmp, id, rev;
