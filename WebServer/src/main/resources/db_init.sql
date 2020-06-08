INSERT INTO users_roles VALUES (0, 'OWNER');
INSERT INTO users_roles VALUES (1, 'DEVELOPER');
INSERT INTO users_roles VALUES (2, 'TESTER');
INSERT INTO users_roles VALUES (3, 'DEV_OPS');

INSERT INTO bug_priorities VALUES (0, 'LOW');
INSERT INTO bug_priorities VALUES (1, 'MEDIUM');
INSERT INTO bug_priorities VALUES (2, 'HIGH');
INSERT INTO bug_priorities VALUES (3, 'CRITICAL');

INSERT INTO bug_statuses VALUES (0, 'IN_PROGRESS');
INSERT INTO bug_statuses VALUES (1, 'NOT_A_BUG');
INSERT INTO bug_statuses VALUES (2, 'NOT_REPRODUCIBLE');
INSERT INTO bug_statuses VALUES (3, 'MISSING_INFORMATION');
INSERT INTO bug_statuses VALUES (4, 'READY_FOR_NEXT_RELEASE');
INSERT INTO bug_statuses VALUES (5, 'READY_FOR_RETEST');
INSERT INTO bug_statuses VALUES (6, 'CLOSED');
INSERT INTO bug_statuses VALUES (7, 'ON_HOLD');
INSERT INTO bug_statuses VALUES (8, 'DUPLICATE_BUG');
INSERT INTO bug_statuses VALUES (9, 'OPEN');

INSERT INTO bug_user_roles VALUES (0, 'OWNER');
INSERT INTO bug_user_roles VALUES (1, 'DEVELOPER');
INSERT INTO bug_user_roles VALUES (2, 'TESTER');
INSERT INTO bug_user_roles VALUES (3, 'DEV_OPS');
INSERT INTO bug_user_roles VALUES (4, 'ALL');
