DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS role_authority;
DROP TABLE IF EXISTS authority;
DROP TABLE IF EXISTS role;

CREATE TABLE role (
  role_code VARCHAR(255) PRIMARY KEY,
  role_desc VARCHAR(4096),
  status    VARCHAR(10)
);

CREATE TABLE authority (
  authority_code VARCHAR(255) PRIMARY KEY,
  authority_desc VARCHAR(4096),
  status         VARCHAR(10)
);

CREATE TABLE role_authority (
  role_code       VARCHAR(255) NOT NULL,
  authority_code  VARCHAR(255) NOT NULL,
  status          VARCHAR(10),

  PRIMARY KEY (role_code, authority_code),

  CONSTRAINT FOREIGN KEY (role_code)
  REFERENCES role (role_code)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,

  CONSTRAINT FOREIGN KEY (authority_code)
  REFERENCES authority (authority_code)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
);

CREATE TABLE user (
  user_id                 VARCHAR(255) PRIMARY KEY,
  username                VARCHAR(255) NOT NULL,
  password                VARCHAR(255) NOT NULL,
  account_non_expired     BOOL DEFAULT TRUE,
  account_non_locked      BOOL DEFAULT TRUE,
  credentials_non_expired BOOL DEFAULT TRUE,
  enabled                 BOOL DEFAULT TRUE
);

CREATE TABLE user_role (
  user_id   VARCHAR(255) NOT NULL,
  role_code VARCHAR(255) NOT NULL,
  status    VARCHAR(10),

  PRIMARY KEY (user_id, role_code),

  CONSTRAINT FOREIGN KEY (user_id)
  REFERENCES user(user_id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,

  CONSTRAINT FOREIGN KEY (role_code)
  REFERENCES role (role_code)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
);
