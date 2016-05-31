CREATE TABLE IF NOT EXISTS oauth_access_token (
  token_id varchar(256) DEFAULT NULL,
  token blob,
  authentication_id varchar(256) DEFAULT NULL,
  user_name varchar(256) DEFAULT NULL,
  client_id varchar(256) DEFAULT NULL,
  authentication blob,
  refresh_token varchar(256) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS oauth_refresh_token (
  token_id varchar(256) DEFAULT NULL,
  token blob,
  authentication blob
);

CREATE TABLE IF NOT EXISTS oauth_client_details (
  client_name varchar(256) NOT NULL,
  client_id varchar(256) NOT NULL,
  resource_ids varchar(256) DEFAULT NULL,
  client_secret varchar(256) DEFAULT NULL,
  scope varchar(256) DEFAULT NULL,
  authorized_grant_types varchar(256) DEFAULT NULL,
  web_server_redirect_uri varchar(256) DEFAULT NULL,
  authorities varchar(256) DEFAULT NULL,
  access_token_validity int(11) DEFAULT NULL,
  refresh_token_validity int(11) DEFAULT NULL,
  additional_information varchar(4096) DEFAULT NULL,
  autoapprove bit(1) DEFAULT NULL,
  PRIMARY KEY (client_id)
);

CREATE TABLE IF NOT EXISTS oauth_approvals (
	userId VARCHAR(256),
	clientId VARCHAR(256),
	scope VARCHAR(256),
	status VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_status (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  is_selectable char(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY ix_name (name),
  KEY ix_is_selectable (is_selectable)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS user (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  user_status_id int(11) NOT NULL,
  created_ts TIMESTAMP NOT NULL DEFAULT now(),
  updated_ts TIMESTAMP NOT NULL,
  updated_by varchar(14) NULL,
  version bigint(20) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY ix_username (username),
  CONSTRAINT fk_user_user_status FOREIGN KEY (user_status_id) REFERENCES user_status (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS role (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  presedence_order tinyint(3) NOT NULL,
  is_selectable bit(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY ix_name (name),
  UNIQUE KEY ix_presedence_order (presedence_order)
);

CREATE TABLE IF NOT EXISTS user_roles (
  id int(11) NOT NULL AUTO_INCREMENT,
  role_id int(11) NOT NULL,
  user_id bigint(20) NOT NULL,
  created_ts TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (id),
  KEY ix_user_id (user_id),
  KEY fk_user_roles_role (role_id),
  CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES role (id),
  CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS committee_role (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  display_name varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  visible bit(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY ix_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS elected_officers (
  id int(11) NOT NULL AUTO_INCREMENT,
  committee_role_id int(11) NOT NULL,
  user_id bigint(20) NOT NULL,
  year YEAR(4) NOT NULL,
  created_ts TIMESTAMP NOT NULL DEFAULT now(),
  updated_ts TIMESTAMP NULL,
  PRIMARY KEY (id),
  UNIQUE KEY ix_user_id (user_id, committee_role_id, year),
  CONSTRAINT fk_elected_officers_committee_role FOREIGN KEY (committee_role_id) REFERENCES committee_role (id),
  CONSTRAINT fk_elected_officers_user FOREIGN KEY (user_id) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;