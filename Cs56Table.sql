drop database locals;
create database locals;
use locals;

CREATE TABLE locals_person(
	person_id       int(6)      UNSIGNED AUTO_INCREMENT ,      
    firstname       varchar(30) not null,
    lastname        varchar(30) not null,
    email	        varchar(50) not null,
    pwd		        varchar(30) not null,
    gender	        char(1)     not null,
    zipcode         int(5)      not null,
    dateBirth       date        not null,
    regPerson_date  TIMESTAMP,
    PRIMARY KEY (person_id)   
);

CREATE TABLE locals_act(
	act_code     int(6)       UNSIGNED AUTO_INCREMENT,
	act_type     varchar(30)  not null,
	act_location varchar(50)  not null,
	act_descri   varchar(250) not null,
    act_date     date         not null,
	act_time     time         not null,
	act_creator  int(6)       UNSIGNED not null,
	regAct_date  TIMESTAMP,
	PRIMARY KEY (act_code),
    FOREIGN KEY (act_creator) REFERENCES locals_person(person_id)
	
);

CREATE TABLE locals_enroll(
	enroll_id       int(6)       UNSIGNED AUTO_INCREMENT ,
	enroll_user     int(6)       UNSIGNED,
	act_code        int(6)	     UNSIGNED not null,
	review          varchar(150), 
	regEnrrol_date  TIMESTAMP,
	PRIMARY KEY (enroll_id),
    FOREIGN KEY (enroll_user) REFERENCES locals_person(person_id),
    FOREIGN KEY (act_code)    REFERENCES locals_act(act_code)
);
CREATE TABLE locals_message(
	msg_id       int(6)        UNSIGNED AUTO_INCREMENT,
	enroll_id    int(6)	       UNSIGNED not null,
    sending      int(6)        UNSIGNED not null,
    msg          varchar(1000) not null,
	regMsg_date  TIMESTAMP,
	PRIMARY KEY (msg_id),
    FOREIGN KEY (enroll_id) REFERENCES locals_enroll(enroll_id),
    FOREIGN KEY (sending) REFERENCES locals_person(person_id)
);

INSERT INTO locals_person(firstname,lastname,email,pwd,gender,zipcode,dateBirth) VALUES ('admin'       ,'admin', 'a@a.com', 'abc', 'M', 90405 ,'1988-11-07');
INSERT INTO locals_person(firstname,lastname,email,pwd,gender,zipcode,dateBirth) VALUES ('Fernando'    ,'Brambilla', 'bramba@outlook.com', 'abc', 'M',90405,'1988-11-07');
INSERT INTO locals_person(firstname,lastname,email,pwd,gender,zipcode,dateBirth) VALUES ('Jack'        ,'daniels', 'jd@a.com', 'abc', 'M',90025,'1990-11-07');
INSERT INTO locals_person(firstname,lastname,email,pwd,gender,zipcode,dateBirth) VALUES ('Maria Helena','Brambilla de Mello', 'maria@b.com', 'abc', 'F',900000 ,'1997-03-20 ');
INSERT INTO locals_person(firstname,lastname,email,pwd,gender,zipcode,dateBirth) VALUES ('Xiaofei','Wang', 'samuel-wang@foxmail.com', 'abcabc', 'M', 90066,'1995-02-22');
INSERT INTO locals_person(firstname,lastname,email,pwd,gender,zipcode,dateBirth) VALUES ('Zack','Davis', 'zd@smc.edu', 'team3java', 'M',90293 ,'1986-12-06');
INSERT INTO locals_person(firstname,lastname,email,pwd,gender,zipcode,dateBirth) VALUES ('Karina','Nunes', 'k@nunes.com', 'abc', 'M', 90405,'1994-10-30');
INSERT INTO locals_person(firstname,lastname,email,pwd,gender,zipcode,dateBirth) VALUES ('Anna','Smith', 'a@smc.com', 'abc', 'F', 90405 ,'2000-01-01');
INSERT INTO locals_person(firstname,lastname,email,pwd,gender,zipcode,dateBirth) VALUES ('Gabriela','Watarai', 'ga@smc.com', 'abc', 'F',90054 ,'1986-06-10');
INSERT INTO locals_person(firstname,lastname,email,pwd,gender,zipcode,dateBirth) VALUES ('Lisa','Lee', 'Lee@smc.com', 'abc', 'F', 90076 ,'1999-02-12');

INSERT INTO locals_act(act_type,act_location,act_descri,act_date,act_time,act_creator) VALUES ('Hiking','Santa Monica ','none', '2018-12-8', '10:35:00', 7);
INSERT INTO locals_act(act_type,act_location,act_descri,act_date,act_time,act_creator) VALUES ('Nightlife','Santa Monica Pier','none', '2018-12-10' ,'19:35:00', 7);
INSERT INTO locals_act(act_type,act_location,act_descri,act_date,act_time,act_creator) VALUES ('Surfing','Santa Monica ','none', '2018-12-7' ,'07:35:00', 7);
INSERT INTO locals_act(act_type,act_location,act_descri,act_date,act_time,act_creator) VALUES ('Cycling','Santa Monica ','none', '2018-12-8' ,'10:35:00', 1);
INSERT INTO locals_act(act_type,act_location,act_descri,act_date,act_time,act_creator) VALUES ('Nightlife','SM Bangalo','none', '2018-12-10', '19:35:00', 2);
INSERT INTO locals_act(act_type,act_location,act_descri,act_date,act_time,act_creator) VALUES ('Rock Climbing','Santa Monica College','none', '2018-12-7','07:35:00', 8);

INSERT INTO locals_enroll(act_code,enroll_user) VALUES (1,6);
INSERT INTO locals_enroll(act_code,enroll_user) VALUES (5,6);
INSERT INTO locals_enroll(act_code,enroll_user) VALUES (1,3);
INSERT INTO locals_enroll(act_code,enroll_user) VALUES (2,3);
INSERT INTO locals_enroll(act_code,enroll_user) VALUES (3,3);
INSERT INTO locals_enroll(act_code,enroll_user) VALUES (1,5);
INSERT INTO locals_enroll(act_code,enroll_user) VALUES (2,5);
INSERT INTO locals_enroll(act_code,enroll_user) VALUES (3,5);
INSERT INTO locals_enroll(act_code,enroll_user) VALUES (4,5);
INSERT INTO locals_enroll(act_code,enroll_user) VALUES (5,5);
-- All activities offered 
select A.act_code,A.act_type, P.firstname, P.zipcode
from locals_person P
Join locals_act A 
ON P.person_id = A.act_creator
order by P.firstname  ;
-- Activities Created by a Specific User(LOCAL)
select A.act_code,A.act_type, P.firstname,P.zipcode
from locals_person P
Join locals_act A 
on P.person_id = A.act_creator and P.person_id= 7;
-- Activities that the user selected
select *
from locals_person P
Join locals_act A ON P.person_id = A.act_creator 
join locals_enroll E on E.act_code = A.act_code and E.enroll_user = 6 and A.act_code = 1;
-- Activities Created by a Specific User(LOCAL) test creator (Delete tool)
select A.act_code,A.act_type, P.firstname,P.zipcode
from locals_person P
Join locals_act A 
on P.person_id = A.act_creator and P.person_id= 7 and A.act_code = 3;