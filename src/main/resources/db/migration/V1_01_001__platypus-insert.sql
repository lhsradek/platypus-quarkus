--
-- PlatypusApplication 3.14.15 Flyway script 
--
  
-- TABLE platypus_role -----
  
INSERT INTO platypus_role (id, role_name, enabled) VALUES (1, 'adminRole', true);
INSERT INTO platypus_role (id, role_name, enabled) VALUES (2, 'managerRole', true);
INSERT INTO platypus_role (id, role_name, enabled) VALUES (3, 'userRole', true);


-- TABLE platypus_user -----
  
INSERT INTO platypus_user
  (id, user_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
    VALUES (1, 'admin',
    '$2a$04$jwaKSRwNuvnD3Yg4BhIqK.XVrKZYL7IEC1jWe7NtuxgECiUJ5rlNe',
    true, true, true, true);
INSERT INTO platypus_user
  (id, user_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
    VALUES (2, 'lhs', 
    '$2a$04$eUiuSRGo.0sxscoJ82QXxuhuemW64CjpTgOxMVVeK3yXlOXOl2hDG',
    true, true, true, true);
INSERT INTO platypus_user
  (id, user_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
    VALUES (3, 'user',
    '$2a$04$CXVR7TLO8SuJoiUR8M8oW.RYi6rHlkkK6rAcT.bLKDqhx6ZsZ0jmu',
    true, true, true, true);

    
-- TABLE platypus_user_role -----
  
-- old
-- 
-- Simple INSERT for H2, Postgresql, MariaDB (MySQL) and others databases
-- 
-- INSERT INTO platypus_user_role (user_id, role_id) VALUES (1, 1);
-- INSERT INTO platypus_user_role (user_id, role_id) VALUES (1, 2);
-- INSERT INTO platypus_user_role (user_id, role_id) VALUES (1, 3);
-- INSERT INTO platypus_user_role (user_id, role_id) VALUES (2, 2);
-- INSERT INTO platypus_user_role (user_id, role_id) VALUES (2, 3);
-- INSERT INTO platypus_user_role (user_id, role_id) VALUES (3, 3);

    
-- For H2, Postgresql, MariaDB (MySQL)

INSERT INTO platypus_user_role (user_id, role_id) (
    SELECT id user_id, (SELECT id FROM platypus_role
    WHERE role_name='adminRole') role_id
    FROM platypus_user WHERE user_name='admin');
INSERT INTO platypus_user_role (user_id, role_id) (
    SELECT id user_id, (SELECT id FROM platypus_role
    WHERE role_name='managerRole') role_id
    FROM platypus_user WHERE user_name='admin');
INSERT INTO platypus_user_role (user_id, role_id) (
    SELECT id user_id, (SELECT id FROM platypus_role
    WHERE role_name='userRole') role_id
    FROM platypus_user WHERE user_name='admin');
INSERT INTO platypus_user_role (user_id, role_id) (
    SELECT id user_id, (SELECT id FROM platypus_role
    WHERE role_name='managerRole') role_id
    FROM platypus_user WHERE user_name='lhs');
INSERT INTO platypus_user_role (user_id, role_id) (
    SELECT id user_id, (SELECT id FROM platypus_role
    WHERE role_name='userRole') role_id
    FROM platypus_user WHERE user_name='lhs');
INSERT INTO platypus_user_role (user_id, role_id) (
    SELECT id user_id, (SELECT id FROM platypus_role
    WHERE role_name='userRole') role_id
    FROM platypus_user WHERE user_name='user');

