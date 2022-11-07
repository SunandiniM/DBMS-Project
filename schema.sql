REM   Script: P1
REM   P1 part 1

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
    PRIMARY KEY(EMPID, SCID),  
    FOREIGN KEY(SCID) REFERENCES SERVICE_CENTER ON DELETE CASCADE  
);

create table HOURLY_EMPLOYEE(  
    SCID number(9),  
    EMPID number(9),  
    HOURS_WORKED number(2), 
    PRIMARY KEY(EMPID, SCID),  
    FOREIGN KEY(EMPID, SCID) REFERENCES EMPLOYEES ON DELETE CASCADE 
    /*CONSTRAINT checkIfHourlyEmployee*/ 
    /*CHECK( (SELECT COUNT (*) 
    FROM HOURLY_EMPLOYEE HE, EMPLOYEES E 
    WHERE HE.SCID = E.SCID  
    AND HE.EMPID = E.EMPID 
    AND EROLE <> 'MECHANIC') = 0 )*/ 
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
