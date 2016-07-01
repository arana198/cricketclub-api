SET MODE MySQL;

INSERT INTO role(id, description, presedence_order, is_selectable, name) VALUES (1, 'Indicates the role is for site admin', 1, 1, 'ROLE_ADMIN');
INSERT INTO role(id, description, presedence_order, is_selectable, name) VALUES (2, 'Indicates the role is for club admin', 2, 1, 'ROLE_CLUB_ADMIN');
INSERT INTO role(id, description, presedence_order, is_selectable, name) VALUES (3, 'Indicates the role is of type captain', 3, 1, 'ROLE_CAPTAIN');
INSERT INTO role(id, description, presedence_order, is_selectable, name) VALUES (4, 'Indicates the role is of type player', 4, 1, 'ROLE_PLAYER');

INSERT INTO user_status(id, name, description, is_selectable) VALUES (1, 'ACTIVE', 'Indicates the userId is active', 1);
INSERT INTO user_status(id, name, description, is_selectable) VALUES (2, 'PENDING', 'Indicates the userId is pending', 1);
INSERT INTO user_status(id, name, description, is_selectable) VALUES (3, 'SUSPENDED', 'Indicates the userId is suspended', 1);
INSERT INTO user_status(id, name, description, is_selectable) VALUES (4, 'BLACKLISTED', 'Indicates the userId is blacklisted', 1);

--Functional Test admin
INSERT INTO user(id, username, password, user_status_id, updated_by, updated_ts, version)
VALUES (1, 'admin', '5f4dcc3b5aa765d61d8327deb882cf99', 1, 3, '2014-09-12 16:46:45', 0);
INSERT INTO user_roles(id, role_id, user_id) VALUES (1, 1, 1);

INSERT INTO user(id, username, password, user_status_id, updated_by, updated_ts, version)
VALUES (2, 'test2', '5f4dcc3b5aa765d61d8327deb882cf99', 1, 3, '2014-09-12 16:46:45', 0);
INSERT INTO user_roles(id, role_id, user_id) VALUES (2, 2, 2);