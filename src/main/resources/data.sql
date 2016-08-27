-- role
INSERT INTO role VALUES ('SYS_ADMIN', 'System Administrator', 'A');
INSERT INTO role VALUES ('ADMIN', 'Administrator', 'A');
INSERT INTO role VALUES ('OFFICER', 'Approving Officer', 'A');
INSERT INTO role VALUES ('EMPLOYEE', 'Employee', 'A');

-- authority
INSERT INTO authority VALUES ('FULL_CONTROL', 'Access & Execute all screen', 'A');
INSERT INTO authority VALUES ('FULL_ACCESS', 'Access & View all screen', 'A');
INSERT INTO authority VALUES ('EXE_EMP', 'Execute Employee', 'A');
INSERT INTO authority VALUES ('VIEW_EMP', 'View All Employee', 'A');
INSERT INTO authority VALUES ('EXE_USER', 'Execute User', 'A');
INSERT INTO authority VALUES ('VIEW_USER', 'View All User', 'A');

-- role_authority
INSERT INTO role_authority VALUES ('SYS_ADMIN', 'FULL_CONTROL', 'A');
INSERT INTO role_authority VALUES ('ADMIN', 'EXE_EMP', 'A');
INSERT INTO role_authority VALUES ('ADMIN', 'VIEW_EMP', 'A');

-- user
INSERT INTO user VALUES ('U000000000', 'root', '$2a$10$PaMU6xG2CxfGj1jX60Ag.eqqNBvG7oKngtV6AWZYWSyuMmZcVSqTu', false, false, false, false);
INSERT INTO user VALUES ('U000000001', 'admin', '$2a$10$PaMU6xG2CxfGj1jX60Ag.eqqNBvG7oKngtV6AWZYWSyuMmZcVSqTu', true, true, true, true);
INSERT INTO user VALUES ('U000000002', 'officer', '$2a$10$PaMU6xG2CxfGj1jX60Ag.eqqNBvG7oKngtV6AWZYWSyuMmZcVSqTu', true, true, true, true);
INSERT INTO user VALUES ('U000000003', 'employee', '$2a$10$PaMU6xG2CxfGj1jX60Ag.eqqNBvG7oKngtV6AWZYWSyuMmZcVSqTu', true, true, true, true);

-- user_role
INSERT INTO user_role VALUES ('U000000000', 'SYS_ADMIN', 'A');
INSERT INTO user_role VALUES ('U000000001', 'ADMIN', 'A');
INSERT INTO user_role VALUES ('U000000002', 'OFFICER', 'A');
INSERT INTO user_role VALUES ('U000000003', 'EMPLOYEE', 'A');