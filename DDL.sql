REM   Script: P1
REM   P1 part 1

drop table SERVICE_EVENT;
drop table SWAP_REQUEST;
drop table TIMEOFF_REQUEST;
drop table OFFERS;
drop table OWNS;
drop table WORKSIN;
drop table HOURLY_EMPLOYEE_SCHEDULE;
drop table INVOICE;
drop table CUSTOMER;
drop table VEHICLE;
drop table EMPLOYEES;
drop table MAINTAINANCE_SERVICE;
drop table REPAIR_SERVICE;
drop table SERVICE_CENTER;
drop table SERVICE;


create table SERVICE_CENTER(   
    SCID number(9) primary key,   
    ADDRESS varchar(100),   
    PHONENO number(10),  
    OPEN_SATURDAY number(1),
    MIN_WAGE number(5),
    MAX_WAGE number(5)
);

create table EMPLOYEES(   
    SCID number(9),   
    EMPID number(9),   
    FNAME varchar(20),   
    LNAME varchar(20),   
    ADDRESS varchar(100),   
    EMAIL varchar(30),   
    PHONENO number(10),   
    EROLE varchar(12),  
    USERNAME varchar(20), 
    PRIMARY KEY(EMPID, SCID),   
    FOREIGN KEY(SCID) REFERENCES SERVICE_CENTER ON DELETE CASCADE   
);

create table WORKSIN(   
    SCID number(9) NOT NULL,   
    EMPID number(9) NOT NULL,   
    PAY number(9),   
    PRIMARY KEY(EMPID, SCID),   
    FOREIGN KEY(EMPID, SCID) REFERENCES EMPLOYEES ON DELETE CASCADE,   
    FOREIGN KEY(SCID) REFERENCES SERVICE_CENTER ON DELETE CASCADE   
);

create table CUSTOMER(  
    SCID number(9),  
    CID number(9) GENERATED BY DEFAULT ON NULL AS IDENTITY,
    FNAME varchar(20),  
    LNAME varchar(20),
    ADDRESS varchar(500),
    EMAIL varchar(50),
    PHONE number(10),
    USERNAME varchar(20),
    ACC_STATUS number(1), /* 0 means no outstanding balance */  
    PROFILE_STATUS number(1), /* 1 means active so car exists */  
    PRIMARY KEY(SCID, CID),  
    FOREIGN KEY(SCID) REFERENCES SERVICE_CENTER ON DELETE CASCADE  
);

create table VEHICLE(  
    VIN_NO varchar(8) primary key,  
    YEAR number(4),  
    MFG varchar(20),  
    MILEAGE number(9),  
    SCHEDULE varchar(1)  
);

create table OWNS(  
    SCID number(9),  
    CID number(9),  
    VIN_NO varchar(8),
    PRIMARY KEY(SCID, CID, VIN_NO),  
    FOREIGN KEY(VIN_NO) REFERENCES VEHICLE ON DELETE CASCADE,  
    FOREIGN KEY(SCID, CID) REFERENCES CUSTOMER ON DELETE CASCADE  
);

create table SERVICE(  
    SID number(9) primary key,  
    SNAME varchar(100)
);

create table REPAIR_SERVICE(  
    SID number(9),  
    CATEGORY varchar(100),  
    PRIMARY KEY(SID),  
    FOREIGN KEY(SID) REFERENCES SERVICE ON DELETE CASCADE  
);

create table MAINTAINANCE_SERVICE(  
    SCHEDULE_ID number(9),  
    SERVICE_ID number(9), 
    PRIMARY KEY(SCHEDULE_ID, SERVICE_ID),  
    FOREIGN KEY(SCHEDULE_ID) REFERENCES SERVICE(SID) ON DELETE CASCADE,
    FOREIGN KEY(SERVICE_ID) REFERENCES SERVICE(SID) ON DELETE CASCADE  
);

create table OFFERS(  
    SID number(9),  
    SCID number(9),  
    MFG varchar(20),  
    DURATION number(4),  
    PRICE number(5),  
    PRIMARY KEY(SCID, SID, MFG),  
    FOREIGN KEY(SCID) REFERENCES SERVICE_CENTER ON DELETE CASCADE,  
    FOREIGN KEY(SID) REFERENCES SERVICE ON DELETE CASCADE  
);

create table INVOICE (
    ORDER_ID number(9),
    SCID number(9), 
    CID number(9),
    BILL number(7), 
    STATUS number(1),
    PRIMARY KEY(ORDER_ID),
    FOREIGN KEY(SCID, CID) REFERENCES CUSTOMER ON DELETE CASCADE
);

create table SERVICE_EVENT ( 
    ORDER_ID number(9), 
    SCID number(9), 
    CID number(9), 
    SID number(9),
    VIN_NO varchar(8),
    PRIMARY KEY(ORDER_ID, SCID, CID, SID), 
    FOREIGN KEY(SCID) REFERENCES SERVICE_CENTER ON DELETE CASCADE, 
    FOREIGN KEY(SCID, CID) REFERENCES CUSTOMER ON DELETE CASCADE, 
    FOREIGN KEY(SID) REFERENCES SERVICE ON DELETE CASCADE, 
    FOREIGN KEY(ORDER_ID) REFERENCES INVOICE ON DELETE CASCADE,
    FOREIGN KEY(VIN_NO) REFERENCES VEHICLE
);

create table HOURLY_EMPLOYEE_SCHEDULE( 
    SCID number(9), 
    ORDER_ID number(9), 
    EMPID number(9), 
    WEEK number(1), 
    DAY number(1), 
    START_SLOT number(2), 
    END_SLOT number(2), 
    PRIMARY KEY(EMPID, SCID, ORDER_ID, WEEK, DAY, START_SLOT),
    FOREIGN KEY(EMPID, SCID) REFERENCES EMPLOYEES ON DELETE CASCADE, 
    FOREIGN KEY(ORDER_ID) REFERENCES INVOICE ON DELETE CASCADE 
);

create table TIMEOFF_REQUEST (
    SCID number(9),
    EMPID number(9),
    DAY number(4),
    WEEK number(4),
    START_SLOT number(4),
    END_SLOT number(4),
    STATUS number(1),
    PRIMARY KEY(SCID, EMPID, DAY, WEEK, START_SLOT, END_SLOT),
    FOREIGN KEY(EMPID, SCID) REFERENCES EMPLOYEES ON DELETE CASCADE
);

create table SWAP_REQUEST (
    REQUEST_ID number(10) GENERATED BY DEFAULT ON NULL AS IDENTITY,
    SCID number(9),
    EMPID number(9),
    REQ_EMP_ID number(9),
    DAY number(4),
    WEEK number(4),
    START_SLOT number(4),
    END_SLOT number(4),
    REQ_DAY number(4),
    REQ_WEEK number(4),
    REQ_START_SLOT number(4),
    REQ_END_SLOT number(4),
    STATUS number(1),
    PRIMARY KEY(REQUEST_ID),
    FOREIGN KEY(EMPID, SCID) REFERENCES EMPLOYEES ON DELETE CASCADE
);

-- CREATE TRIGGER addingHourlyEmployee  
-- AFTER UPDATE ON HOURLY_EMPLOYEE_SCHEDULE 
-- FOR EACH ROW  
-- DECLARE  
--     DAY_SELECTED integer(1);  
--     CHECK_SATURDAY integer(1); 
-- BEGIN  
--     SELECT HE.DAY, SC.OPEN_SATURDAY INTO DAY_SELECTED, CHECK_SATURDAY 
--     FROM HOURLY_EMPLOYEE_SCHEDULE HE 
--     INNER JOIN SERVICE_CENTER SC 
--     ON SC.SCID = HE.SCID; 
--     IF CHECK_SATURDAY <> 1 AND DAY_SELECTED = 6 THEN  
--         RAISE_APPLICATION_ERROR (-20000, 'Not open on saturday!');  
--     END IF;  
-- END; 
 
-- /

-- CREATE TRIGGER addingOnSat  
-- AFTER UPDATE ON HOURLY_EMPLOYEE_SCHEDULE  
-- FOR EACH ROW  
-- DECLARE  
--     EMPLOYEE_ROLE varchar(12);  
-- BEGIN  
--     SELECT E.EROLE INTO EMPLOYEE_ROLE  
--     FROM HOURLY_EMPLOYEE_SCHEDULE HE, EMPLOYEES E  
--     WHERE HE.EMPID = E.EMPID;  
--     IF EMPLOYEE_ROLE <> 'MECHANIC' THEN  
--         RAISE_APPLICATION_ERROR (-20000, 'Not a mechanic!');  
--     END IF;  
-- END; 
 
-- /

-- CREATE TRIGGER changeProfileStatus  
-- AFTER UPDATE ON OWNS  
-- FOR EACH ROW  
-- BEGIN  
--     UPDATE CUSTOMER C  
--     SET C.PROFILE_STATUS = 0  
--     WHERE C.CID NOT IN ( SELECT O.CID   
--                     FROM OWNS O);  
-- END; 
 
-- /


create or replace trigger avoidIncorrrectRepairCategory
before insert or update on REPAIR_SERVICE
for each row
begin
    if :new.CATEGORY not in ('Engine Services', 'Exhaust Services', 'Electrical Services', 'Transmission Services', 'Tire Services', 'Heating and A/C Services') then
        raise_application_error(-20000, 'Category should be one of Engine Services, Exhaust Services, Electrical Services, Transmission Services, Tire Services, Heating and A/C Services');
    end if;
end avoidIncorrrectRepairCategory;
/


create or replace trigger maintenanceHierarchy
before insert or update on MAINTAINANCE_SERVICE
for each row
declare
    pragma autonomous_transaction;
    b_count number;
    c_count number;
begin
    if :new.SCHEDULE_ID = 113 THEN
        select count(*) into b_count from MAINTAINANCE_SERVICE where SCHEDULE_ID=114 and SERVICE_ID=:new.SERVICE_ID;
        select count(*) into c_count from MAINTAINANCE_SERVICE where SCHEDULE_ID=115 and SERVICE_ID=:new.SERVICE_ID;
        if b_count <= 0 or c_count <= 0 THEN
            raise_application_error(-20000, 'Service should be present in both Schedule B and Schedule C');
        end if;
    end if;
    if :new.SCHEDULE_ID = 114 THEN
        select count(*) into c_count from MAINTAINANCE_SERVICE where SCHEDULE_ID=115 and SERVICE_ID=:new.SERVICE_ID;
        if c_count <= 0 THEN
            raise_application_error(-20000, 'Service should be present in Schedule C');
        end if;
    end if;
end maintenanceHierarchy;
/


create or replace trigger avoidNonHourlyEmployees
before insert or update on HOURLY_EMPLOYEE_SCHEDULE
for each row
declare
    emp_role EMPLOYEES.EROLE%TYPE;
begin
    SELECT EROLE INTO emp_role FROM EMPLOYEES WHERE EMPID=:new.EMPID and SCID=:new.SCID;
    if (emp_role != 'MECHANIC') then
        raise_application_error(-20000, 'Only Mechanics can be added into HOURLY_EMPLOYEE_SCHEDULE table');
    end if;
end avoidNonHourlyEmployees;
/

create or replace trigger checkMaxAndMinSalaries
before insert or update on WORKSIN
for each row
declare
    emp_role EMPLOYEES.EROLE%TYPE;
    min_salary number;
    max_salary number;
    pay number;
begin
    SELECT MIN_WAGE INTO min_salary, EROLE INTO emp_role FROM SERVICE_CENTER S, EMPLOYEES E WHERE S.SCID=:new.SCID AND E.EMPID=:new.EMPID AND E.SCID=:new.SCID AND E.EROLE = 'MECHANIC';
    SELECT MAX_WAGE INTO max_salary FROM SERVICE_CENTER S, EMPLOYEES E WHERE S.SCID=:new.SCID AND E.EMPID=:new.EMPID AND E.SCID=:new.SCID AND E.EROLE = 'MECHANIC';

    if emp_role='MECHANIC' AND :new.PAY > max_salary then
        raise_application_error(-20000, 'Salary not in correct range');
    if emp_role='MECHANIC' AND :new.PAY < min_salary then
        raise_application_error(-20000, 'Salary not in correct range');
    end if;
end checkMaxAndMinSalaries;
/

-- create or replace trigger checkOneReceptionistPerStore
-- before insert or update on EMPLOYEES
-- for each row
-- declare
--     pragma autonomous_transaction;
--     count_ number;
-- begin
--     SELECT COUNT(*) INTO count_ FROM EMPLOYEES WHERE SCID=:new.SCID and EROLE = 'RECEPTIONIST' GROUP BY SCID;
--     if count_ > 0 then
--         raise_application_error(-20000, 'Only one receptionist per store');
--     end if;
-- end checkMaxAndMinSalaries;
-- /

create or replace trigger deactivateUserProfile
after delete on OWNS
for each row
declare
    pragma autonomous_transaction;
    count_ number;
begin
    SELECT COUNT(*) INTO count_ FROM OWNS WHERE SCID=:old.SCID and CID=:old.CID GROUP BY CID, SCID;
    if count_ = 0 then
        UPDATE CUSTOMER SET PROFILE_STATUS = 0 WHERE SCID = :old.SCID AND CID = :old.CID;
    end if;
end deactivateUserProfile;
/

/*
create or replace trigger invoiceAccountStatus
after insert or update on INVOICE
for each row
declare
    pragma autonomous_transaction;
    count_ number;
begin
    SELECT COUNT(*) INTO count_ FROM INVOICE I, CUSTOMER C WHERE C.CID=:new.CID AND I.CID = :new.CID and I.STATUS = 0;
    if count_ > 0 then
        UPDATE CUSTOMER SET ACC_STATUS = 0 WHERE SCID = :new.SCID AND CID = :new.CID;
    end if;
end invoiceAccountStatus;
/
*/