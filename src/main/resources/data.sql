--DROP USER C##user1234 CASCADE;
--
--create user C##user1234 identified by user1234 quota unlimited on users;
--grant connect, resource to C##user1234;
--
--ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;
--
--create user user1234 identified by user1234 quota unlimited on users;
--grant connect, resource to user1234;

-- alter user user1234 default tablespace users quota unlimited on users;



INSERT INTO DIR (id, PARENT_ID, NAME) VALUES(1, null, '음식--');
INSERT INTO DIR (id, PARENT_ID, NAME) VALUES(2, 1, '한식');
INSERT INTO DIR (id, PARENT_ID, NAME) VALUES(3, 2, '콩나물국밥');
INSERT INTO DIR (id, PARENT_ID, NAME) VALUES(4, 2, '비빔밥');
INSERT INTO DIR (id, PARENT_ID, NAME) VALUES(5, 1, '양식');
INSERT INTO DIR (id, PARENT_ID, NAME) VALUES(6, 5, '치킨');
INSERT INTO DIR (id, PARENT_ID, NAME) VALUES(7, 5, '햄버거');
INSERT INTO DIR (id, PARENT_ID, NAME) VALUES(8, 1, '일식');
INSERT INTO DIR (id, PARENT_ID, NAME) VALUES(9, 8, '라멘');
INSERT INTO DIR (id, PARENT_ID, NAME) VALUES(10, 8, '돈까스');


--select SYS_CONNECT_BY_PATH( PARENT_ID, ' -> ' ) || ' -> ' || id || NAME AS path
--from DIR
--connect by prior id = PARENT_ID
----START WITH PARENT_ID IS null
--START WITH PARENT_ID = 1







--{{base_url}}/member/signup
    insert
    into member (email, password, role, username, id)
    values
        ('test4@test.com'
        , '$2a$10$kA.unYIgq85.KEZSqDP.w.Rn8pV6BZvmNS.vRkI.tkry./kG5zr16'
        , 'MEMBER'
        , 'test4'
        , default);


INSERT INTO ROLES( NAME) VALUES('ROLE_USER');
INSERT INTO ROLES( NAME) VALUES('ROLE_MODERATOR');
INSERT INTO ROLES( NAME) VALUES('ROLE_ADMIN');



INSERT INTO USERS
( USERNAME, EMAIL, PASSWORD)
VALUES( 'tester', 'tester@tester.com', '$2a$10$cGwPjroUZri3zCx1F8M5x.Vdl8WYry50Y7TulNeeDFopiykx50BT2');


INSERT INTO USER1234.USER_ROLES
(ROLE_ID, USER_ID)
VALUES(1, 1);