REM   Script: P1
REM   P1 part 1

drop table OFFERS;
drop table OWNS;
drop table WORKSIN;
drop table CUSTOMER;
drop table VEHICLE;
drop table HOURLY_EMPLOYEE;
drop table HOURLY_EMPLOYEE_SCHEDULE;
drop table EMPLOYEES;
drop table MAINTAINANCE_SERVICE;
drop table REPAIR_SERVICE;
drop table SERVICE_CENTER;
drop table SERVICE;
drop table INVOICE;
drop table SERVICE_EVENT;


create table SERVICE_CENTER(  
    SCID number(9) primary key,  
    ADDRESS varchar(40),  
    PHONENO number(10), 
    OPEN_SATURDAY number(1) 
);

create table EMPLOYEES(  
    SCID number(9),  
    EMPID number(9),  
    ENAME varchar(20),  
    ADDRESS varchar(40),  
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
    CID number(9), 
    FNAME varchar(20), 
    LNAME varchar(20),
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
    SNAME varchar(40) 
);

create table REPAIR_SERVICE( 
    SID number(9), 
    CATEGORY varchar(30), 
    PRIMARY KEY(SID), 
    FOREIGN KEY(SID) REFERENCES SERVICE ON DELETE CASCADE 
);

create table MAINTAINANCE_SERVICE( 
    SID number(9), 
    SCHEDULE varchar(1), 
    PRIMARY KEY(SID), 
    FOREIGN KEY(SID) REFERENCES SERVICE ON DELETE CASCADE 
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
    BILL number(7),
    STATUS number(1),
    ORDER_ID number(9),

    PRIMARY KEY(ORDER_ID)
);

create table SERVICE_EVENT (
    ORDER_ID number(9),
    SCID number(9),
    CID number(9),
    SID number(9),

    PRIMARY KEY(SCID, CID, SID),
    FOREIGN KEY(SCID) REFERENCES SERVICE_CENTER ON DELETE CASCADE,
    FOREIGN KEY(SCID, CID) REFERENCES CUSTOMER ON DELETE CASCADE,
    FOREIGN KEY(SID) REFERENCES SERVICE ON DELETE CASCADE,
    FOREIGN KEY(ORDER_ID) REFERENCES INVOICE ON DELETE CASCADE
);

create table HOURLY_EMPLOYEE_SCHEDULE(
    SCID number(9),
    ORDER_ID number(9),
    EMPID number(9),
    WEEK number(1),
    DAY number(1),
    SLOTS_FILLED number(2),
    PRIMARY KEY(EMPID, SCID),
    FOREIGN KEY(EMPID, SCID) REFERENCES EMPLOYEES ON DELETE CASCADE,
    FOREIGN KEY(ORDER_ID) REFERENCES INVOICE ON DELETE CASCADE
);

create table TIMEOFF_REQUEST (
    SCID number(9),
    EMPID number(9),
    DAY number(4);
    WEEK number(4),
    START_SLOT number(4),
    END_SLOT number(4)
    STATUS number(1),

    PRIMARY KEY(SCID, EMPID),
    FOREIGN KEY(EMPID, SCID) REFERENCES EMPLOYEES ON DELETE CASCADE
);

create table SWAP_REQUEST (
    REQUEST_ID number(10) auto increment,
    SCID number(9),
    EMPID number(9),
    REQUESTED_EMP_ID number(9),
    DAY number(4);
    WEEK number(4),
    START_SLOT number(4),
    END_SLOT number(4)
    STATUS number(1),

    PRIMARY KEY(SCID, EMPID),
    FOREIGN KEY(EMPID, SCID) REFERENCES EMPLOYEES ON DELETE CASCADE
);

CREATE TRIGGER addingHourlyEmployee 
AFTER UPDATE ON HOURLY_EMPLOYEE
FOR EACH ROW 
DECLARE 
    EMPLOYEE_ROLE varchar(12); 
BEGIN 
    SELECT E.EROLE INTO EMPLOYEE_ROLE 
    FROM HOURLY_EMPLOYEE HE, EMPLOYEES E 
    WHERE HE.EMPID = E.EMPID; 
    IF EMPLOYEE_ROLE <> 'MECHANIC' THEN 
        RAISE_APPLICATION_ERROR (-20000, 'Not a mechanic!'); 
    END IF; 
END; 
/

CREATE TRIGGER changeProfileStatus 
AFTER UPDATE ON OWNS 
FOR EACH ROW 
BEGIN 
    UPDATE CUSTOMER C 
    SET C.PROFILE_STATUS = 0 
    WHERE C.CID NOT IN ( SELECT O.CID  
                    FROM OWNS O); 
END;
/

