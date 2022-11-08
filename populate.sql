REM   Script: P1-2
REM   TABLE WITH INSERT


INSERT INTO SERVICE_CENTER 
VALUES(30001, '3921 Western Blvd, Raleigh, NC 27606', 3392601234, 1);

INSERT INTO SERVICE_CENTER 
VALUES(30002, '4500 Preslyn Dr Suite 103, Raleigh, NC 27616', 8576890280, 1);

INSERT INTO SERVICE_CENTER 
VALUES(30003, '9515 Chapel Hill Rd, Morrisville, NC 27560', 7798182200, 0);

INSERT INTO EMPLOYEES(SCID, EMPID, FNAME, LNAME, ADDRESS, EMAIL, PHONENO, EROLE, USERNAME) 
VALUES(30001, 123456789, 'John', 'Doe', '1378 University Woods, Raleigh, NC 27612', 'jdoe@gmail.com', 8636368778, 'MANAGER', 'JohnDoe');

INSERT INTO EMPLOYEES 
VALUES(30002, 987654321, 'Rachel', 'Brooks', '2201 Gorman Parkwood, Raleigh, NC 27618', 'rbrooks@ymail.com', 8972468552, 'MANAGER', 'RachelBrooks');

INSERT INTO EMPLOYEES 
VALUES(30003, 987612345, 'Caleb', 'Smith', '1538 Red Bud Lane, Morrisville, NC 27560', 'csmith@yahoo.com', 8547963210, 'MANAGER', 'CalebSmith');

INSERT INTO EMPLOYEES 
VALUES(30001, 132457689, 'James', 'Williams', '1400 Gorman St, Raleigh, NC 27606-2972', 'jwilliams@gmail.com', 4576312882, 'MECHANIC', 'JamesWilliams');

INSERT INTO EMPLOYEES 
VALUES(30001, 314275869, 'David', 'Johnson', '686 Stratford Court, Raleigh, NC 27606', 'djohnson@ymail.com', 9892321100, 'MECHANIC', 'DavidJohnson');

INSERT INTO EMPLOYEES 
VALUES(30001, 241368579, 'Maria', 'Garcia', '1521 Graduate Lane, Raleigh, NC 27606', 'mgarcia@yahoo.com', 4573459090, 'MECHANIC', 'MariaGarcia');

INSERT INTO EMPLOYEES 
VALUES(30002, 123789456, 'Robert', 'Martinez', '1232 Tartan Circle, Raleigh, NC 27607', 'rmartinez@ymail.com', 7652304282, 'MECHANIC', 'RobertMartinez');

INSERT INTO EMPLOYEES 
VALUES(30002, 789123456, 'Charles', 'Rodriguez', '218 Patton Lane, Raleigh, NC 27603', 'crodriguez@yahoo.com', 9092234281, 'MECHANIC', 'CharlesRodriguez');

INSERT INTO EMPLOYEES 
VALUES(30002, 125689347, 'Jose', 'Hernandez', '4747 Dola Mine Road, Raleigh, NC 27609', 'jhernandez@gmail.com', 7653241714, 'MECHANIC', 'JoseHernandez');

INSERT INTO EMPLOYEES 
VALUES(30002, 423186759, 'Ellie', 'Clark', '3125 Avent Ferry Road, Raleigh, NC 27605', 'eclark@gmail.com', 9892180921, 'MECHANIC', 'EllieClark');

INSERT INTO EMPLOYEES 
VALUES(30003, 347812569, 'Charlie', 'Brown', '1 Rockford Mountain Lane, Morrisville, NC 27560', 'cbrown@ymail.com', 9091237568, 'MECHANIC', 'CharlieBrown');

INSERT INTO EMPLOYEES 
VALUES(30003, 123456780, 'Jeff', 'Gibson', '900 Development Drive, Morrisville, NC 27560', 'jgibson@yahoo.com', 3390108899, 'MECHANIC', 'JeffGibson');

INSERT INTO EMPLOYEES 
VALUES(30003, 123456708, 'Isabelle', 'Wilder', '6601 Koppers Road, Morrisville, NC 27560', 'iwilder@gmail.com', 3394561231, 'MECHANIC', 'IsabelleWilder');

INSERT INTO EMPLOYEES 
VALUES(30003, 123456078, 'Peter', 'Titus', '2860 Slater Road, Morrisville, NC 27560', 'ptitus@ymail.com', 3396723418, 'MECHANIC', 'PeterTitus');

INSERT INTO EMPLOYEES 
VALUES(30003, 123450678, 'Mark', 'Mendez', '140 Southport Drive, Morrisville, NC 27560', 'mmendez@yahoo.com', 3395792080, 'MECHANIC', 'MarkMendez');

INSERT INTO EMPLOYEES 
VALUES(30003, 123405678, 'Lisa', 'Alberti', '100 Valley Glen Drive, Morrisville, NC 27560', 'lalberti@yahoo.com', 3391126787, 'MECHANIC', 'LisaAlberti');

INSERT INTO WORKSIN(SCID, EMPID, PAY) 
VALUES(30001, 123456789, 44);

INSERT INTO WORKSIN 
VALUES(30002, 987654321, 35);

INSERT INTO WORKSIN 
VALUES(30003, 987612345, 25);

INSERT INTO WORKSIN 
VALUES(30001, 132457689, 35);

INSERT INTO WORKSIN 
VALUES(30001, 314275869, 35);

INSERT INTO WORKSIN 
VALUES(30001, 241368579, 35);

INSERT INTO WORKSIN 
VALUES(30002, 423186759, 25);

INSERT INTO WORKSIN 
VALUES(30002, 123789456, 25);

INSERT INTO WORKSIN 
VALUES(30002, 789123456, 25);

INSERT INTO WORKSIN 
VALUES(30002, 125689347, 25);

INSERT INTO WORKSIN 
VALUES(30003, 347812569, 22);

INSERT INTO WORKSIN 
VALUES(30003, 123456780, 22);

INSERT INTO WORKSIN 
VALUES(30003, 123456708, 22);

INSERT INTO WORKSIN 
VALUES(30003, 123456078, 22);

INSERT INTO WORKSIN 
VALUES(30003, 123450678, 22);

INSERT INTO WORKSIN 
VALUES(30003, 123405678, 22);

INSERT INTO CUSTOMER(SCID, CID, FNAME, LNAME, USERNAME, ACC_STATUS, PROFILE_STATUS) 
VALUES(30001, 10001, 'Peter', 'Parker', 'PeterParker', 0, 1);

INSERT INTO CUSTOMER 
VALUES(30001, 10002, 'Diana', 'Prince', 'DianaPrince', 0, 1);

INSERT INTO CUSTOMER 
VALUES(30002, 10053, 'Billy', 'Batson', 'BillyBatson', 0, 1);

INSERT INTO CUSTOMER 
VALUES(30002, 10010, 'Bruce', 'Wayne', 'BruceWayne', 0, 1);

INSERT INTO CUSTOMER 
VALUES(30002, 10001, 'Steve', 'Rogers', 'SteveRogers', 0, 1);

INSERT INTO CUSTOMER 
VALUES(30002, 10035, 'Happy', 'Hogan', 'HappyHogan', 0, 1);

INSERT INTO CUSTOMER 
VALUES(30003, 10002, 'Tony', 'Stark', 'TonyStark',0, 1);

INSERT INTO CUSTOMER 
VALUES(30003, 10003, 'Natasha', 'Romanoff', 'NatashaRomanoff', 0, 1);

INSERT INTO CUSTOMER 
VALUES(30003, 10011, 'Harvey', 'Bullock', 'HarveyBullock', 0, 1);

INSERT INTO CUSTOMER 
VALUES(30003, 10062, 'Sam', 'Wilson', 'SamWilson', 0, 1);

INSERT INTO CUSTOMER 
VALUES(30003, 10501, 'Wanda', 'Maximoff', 'WandaMaximoff', 0, 1);

INSERT INTO CUSTOMER 
VALUES(30003, 10010, 'Virginia', 'Potts', 'VirginiaPotts', 0, 1);

INSERT INTO VEHICLE(VIN_NO, YEAR, MFG, MILEAGE, SCHEDULE) 
VALUES('4Y1BL658', 2007, 'Toyota', 53000, 'B');

INSERT INTO VEHICLE 
VALUES('7A1ST264', 1999, 'Honda', 117000, 'A');

INSERT INTO VEHICLE 
VALUES('5TR3K914', 2015, 'Nissan', 111000, 'C');

INSERT INTO VEHICLE 
VALUES('15DC9A87', 2020, 'Toyota', 21000, 'A');

INSERT INTO VEHICLE 
VALUES('18S5R2D8', 2019, 'Nissan', 195500, 'C');

INSERT INTO VEHICLE 
VALUES('9R2UHP54', 2013, 'Honda', 67900, 'B');

INSERT INTO VEHICLE 
VALUES('88TSM888', 2000, 'Honda', 10000, 'A');

INSERT INTO VEHICLE 
VALUES('71HK2D89', 2004, 'Toyota', 195550, 'B');

INSERT INTO VEHICLE 
VALUES('34KLE19D', 2010, 'Toyota', 123800, 'C');

INSERT INTO VEHICLE 
VALUES('29T56WC3', 2011, 'Nissan', 51300, 'A');

INSERT INTO VEHICLE 
VALUES('P39VN198', 2013, 'Nissan', 39500, 'B');

INSERT INTO VEHICLE 
VALUES('39YVS415', 2021, 'Honda', 49900, 'A');

INSERT INTO OWNS(SCID, CID, VIN_NO) 
VALUES(30001, 10001,'4Y1BL658');

INSERT INTO OWNS 
VALUES(30001, 10002,'7A1ST264');

INSERT INTO OWNS 
VALUES(30002, 10053, '5TR3K914');

INSERT INTO OWNS 
VALUES(30002, 10010, '15DC9A87');

INSERT INTO OWNS 
VALUES(30002, 10001, '18S5R2D8');

INSERT INTO OWNS 
VALUES(30002, 10035, '9R2UHP54');

INSERT INTO OWNS 
VALUES(30003, 10002, '88TSM888');

INSERT INTO OWNS 
VALUES(30003, 10003, '71HK2D89');

INSERT INTO OWNS 
VALUES(30003, 10011, '34KLE19D');

INSERT INTO OWNS 
VALUES(30003, 10062, '29T56WC3');

INSERT INTO OWNS 
VALUES(30003, 10501, 'P39VN198');

INSERT INTO OWNS 
VALUES(30003, 10010, '39YVS415');

INSERT INTO SERVICE(SID, SNAME, TYPE) 
VALUES(101, 'Belt Replacement', 'Repair Service');

INSERT INTO SERVICE 
VALUES(102, 'Engine Repair', 'Repair Service');

INSERT INTO SERVICE 
VALUES(103, 'Exhaust Repair', 'Repair Service');

INSERT INTO SERVICE 
VALUES(104, 'Muffler Repair', 'Repair Service');

INSERT INTO SERVICE 
VALUES(105, 'Alternator Repair', 'Repair Service');

INSERT INTO SERVICE 
VALUES(106, 'Power Lock Repair', 'Repair Service');

INSERT INTO SERVICE 
VALUES(107, 'Axle Repair', 'Repair Service');

INSERT INTO SERVICE 
VALUES(108, 'Brake Repair', 'Repair Service');

INSERT INTO SERVICE 
VALUES(109, 'Tire Balancing', 'Repair Service');

INSERT INTO SERVICE 
VALUES(110, 'Wheel Alignment', 'Repair Service');

INSERT INTO SERVICE 
VALUES(111, 'Compressor Repair', 'Repair Service');

INSERT INTO SERVICE 
VALUES(112, 'Evaporator Repair', 'Repair Service');

INSERT INTO SERVICE 
VALUES(113, 'A', 'Maintainance Service');

INSERT INTO SERVICE 
VALUES(114, 'B', 'Maintainance Service');

INSERT INTO SERVICE 
VALUES(115, 'C', 'Maintainance Service');

INSERT INTO REPAIR_SERVICE(SID, CATEGORY) 
VALUES(101, 'Engine Services');

INSERT INTO REPAIR_SERVICE 
VALUES(102, 'Engine Services');

INSERT INTO REPAIR_SERVICE 
VALUES(103, 'Exhaust Services');

INSERT INTO REPAIR_SERVICE 
VALUES(104, 'Exhaust Services');

INSERT INTO REPAIR_SERVICE 
VALUES(105, 'Electrical Services');

INSERT INTO REPAIR_SERVICE 
VALUES(106, 'Electrical Services');

INSERT INTO REPAIR_SERVICE 
VALUES(107, 'Transmission Services');

INSERT INTO REPAIR_SERVICE 
VALUES(108, 'Transmission Services');

INSERT INTO REPAIR_SERVICE 
VALUES(109, 'Tire Services');

INSERT INTO REPAIR_SERVICE 
VALUES(110, 'Tire Services');

INSERT INTO REPAIR_SERVICE 
VALUES(111, 'Heating and A/C Services');

INSERT INTO REPAIR_SERVICE 
VALUES(112, 'Heating and A/C Services');

INSERT INTO MAINTAINANCE_SERVICE(SID, MSNAME) 
VALUES(113, 'Oil Changes');

INSERT INTO MAINTAINANCE_SERVICE 
VALUES(113, 'Filter Replacements');

INSERT INTO MAINTAINANCE_SERVICE 
VALUES(114, 'Oil Changes');

INSERT INTO MAINTAINANCE_SERVICE 
VALUES(114, 'Filter Replacements');

INSERT INTO MAINTAINANCE_SERVICE 
VALUES(114, 'Brake Repair');

INSERT INTO MAINTAINANCE_SERVICE 
VALUES(114, 'Check Engine Light Diagnostics');

INSERT INTO MAINTAINANCE_SERVICE 
VALUES(115, 'Oil Changes');

INSERT INTO MAINTAINANCE_SERVICE 
VALUES(115, 'Filter Replacements');

INSERT INTO MAINTAINANCE_SERVICE 
VALUES(115, 'Brake Repair');

INSERT INTO MAINTAINANCE_SERVICE 
VALUES(115, 'Check Engine Light Diagnostics');

INSERT INTO MAINTAINANCE_SERVICE 
VALUES(115, 'Suspension Repair');

INSERT INTO MAINTAINANCE_SERVICE 
VALUES(115, 'Evaporator Repair');

INSERT INTO OFFERS(SID, SCID, MFG, DURATION, PRICE) 
VALUES(101, 30001, 'Honda', 1, 140);

INSERT INTO OFFERS 
VALUES(101, 30001, 'Nissan', 1, 175);

INSERT INTO OFFERS 
VALUES(101, 30001, 'Toyota', 1, 160);

INSERT INTO OFFERS 
VALUES(101, 30002, 'Honda', 1, 160);

INSERT INTO OFFERS 
VALUES(101, 30002, 'Nissan', 1, 195);

INSERT INTO OFFERS 
VALUES(101, 30002, 'Toyota', 1, 180);

INSERT INTO OFFERS 
VALUES(101, 30003, 'Honda', 1, 175);

INSERT INTO OFFERS 
VALUES(101, 30003, 'Nissan', 1, 210);

INSERT INTO OFFERS 
VALUES(101, 30003, 'Toyota', 1, 195);

INSERT INTO OFFERS 
VALUES(102, 30001, 'Honda', 5, 400);

INSERT INTO OFFERS 
VALUES(102, 30001, 'Nissan', 5, 500);

INSERT INTO OFFERS 
VALUES(102, 30001, 'Toyota', 5, 450);

INSERT INTO OFFERS 
VALUES(102, 30002, 'Honda', 5, 420);

INSERT INTO OFFERS 
VALUES(102, 30002, 'Nissan', 5, 520);

INSERT INTO OFFERS 
VALUES(102, 30002, 'Toyota', 5, 470);

INSERT INTO OFFERS 
VALUES(102, 30003, 'Honda', 5, 435);

INSERT INTO OFFERS 
VALUES(102, 30003, 'Nissan', 5, 535);

INSERT INTO OFFERS 
VALUES(102, 30003, 'Toyota', 5, 485);

INSERT INTO OFFERS 
VALUES(103, 30001, 'Honda', 4, 590);

INSERT INTO OFFERS 
VALUES(103, 30001, 'Nissan', 4, 700);

INSERT INTO OFFERS 
VALUES(103, 30001, 'Toyota', 4, 680);

INSERT INTO OFFERS 
VALUES(103, 30002, 'Honda', 4, 610);

INSERT INTO OFFERS 
VALUES(103, 30002, 'Nissan', 4, 720);

INSERT INTO OFFERS 
VALUES(103, 30002, 'Toyota', 4, 700);

INSERT INTO OFFERS 
VALUES(103, 30003, 'Honda', 4, 625);

INSERT INTO OFFERS 
VALUES(103, 30003, 'Nissan', 4, 735);

INSERT INTO OFFERS 
VALUES(103, 30003, 'Toyota', 4, 715);

INSERT INTO OFFERS 
VALUES(104, 30001, 'Honda', 2, 140);

INSERT INTO OFFERS 
VALUES(104, 30001, 'Nissan', 2, 175);

INSERT INTO OFFERS 
VALUES(104, 30001, 'Toyota', 2, 160);

INSERT INTO OFFERS 
VALUES(104, 30002, 'Honda', 2, 160);

INSERT INTO OFFERS 
VALUES(104, 30002, 'Nissan', 2, 195);

INSERT INTO OFFERS 
VALUES(104, 30002, 'Toyota', 2, 180);

INSERT INTO OFFERS 
VALUES(104, 30003, 'Honda', 2, 175);

INSERT INTO OFFERS 
VALUES(104, 30003, 'Nissan', 2, 210);

INSERT INTO OFFERS 
VALUES(104, 30003, 'Toyota', 2, 195);

INSERT INTO OFFERS 
VALUES(105, 30001, 'Honda', 4, 400);

INSERT INTO OFFERS 
VALUES(105, 30001, 'Nissan', 4, 500);

INSERT INTO OFFERS 
VALUES(105, 30001, 'Toyota', 4, 450);

INSERT INTO OFFERS 
VALUES(105, 30002, 'Honda', 4, 420);

INSERT INTO OFFERS 
VALUES(105, 30002, 'Nissan', 4, 520);

INSERT INTO OFFERS 
VALUES(105, 30002, 'Toyota', 4, 470);

INSERT INTO OFFERS 
VALUES(105, 30003, 'Honda', 4, 435);

INSERT INTO OFFERS 
VALUES(105, 30003, 'Nissan', 4, 535);

INSERT INTO OFFERS 
VALUES(105, 30003, 'Toyota', 4, 485);

INSERT INTO OFFERS 
VALUES(106, 30001, 'Honda', 5, 400);

INSERT INTO OFFERS 
VALUES(106, 30001, 'Nissan', 5, 500);

INSERT INTO OFFERS 
VALUES(106, 30001, 'Toyota', 5, 450);

INSERT INTO OFFERS 
VALUES(106, 30002, 'Honda', 5, 420);

INSERT INTO OFFERS 
VALUES(106, 30002, 'Nissan', 5, 520);

INSERT INTO OFFERS 
VALUES(106, 30002, 'Toyota', 5, 470);

INSERT INTO OFFERS 
VALUES(106, 30003, 'Honda', 5, 435);

INSERT INTO OFFERS 
VALUES(106, 30003, 'Nissan', 5, 535);

INSERT INTO OFFERS 
VALUES(106, 30003, 'Toyota', 5, 485);

INSERT INTO OFFERS 
VALUES(107, 30001, 'Honda', 7, 1000);

INSERT INTO OFFERS 
VALUES(107, 30001, 'Nissan', 7, 1200);

INSERT INTO OFFERS 
VALUES(107, 30001, 'Toyota', 7, 1100);

INSERT INTO OFFERS 
VALUES(107, 30002, 'Honda', 7, 1020);

INSERT INTO OFFERS 
VALUES(107, 30002, 'Nissan', 7, 1220);

INSERT INTO OFFERS 
VALUES(107, 30002, 'Toyota', 7, 1120);

INSERT INTO OFFERS 
VALUES(107, 30003, 'Honda', 7, 1035);

INSERT INTO OFFERS 
VALUES(107, 30003, 'Nissan', 7, 1235);

INSERT INTO OFFERS 
VALUES(107, 30003, 'Toyota', 7, 1135);

INSERT INTO OFFERS 
VALUES(108, 30001, 'Honda', 3, 400);

INSERT INTO OFFERS 
VALUES(108, 30001, 'Nissan', 3, 500);

INSERT INTO OFFERS 
VALUES(108, 30001, 'Toyota', 3, 450);

INSERT INTO OFFERS 
VALUES(108, 30002, 'Honda', 3, 420);

INSERT INTO OFFERS 
VALUES(108, 30002, 'Nissan', 3, 520);

INSERT INTO OFFERS 
VALUES(108, 30002, 'Toyota', 3, 470);

INSERT INTO OFFERS 
VALUES(108, 30003, 'Honda', 3, 435);

INSERT INTO OFFERS 
VALUES(108, 30003, 'Nissan', 3, 535);

INSERT INTO OFFERS 
VALUES(108, 30003, 'Toyota', 3, 485);

INSERT INTO OFFERS 
VALUES(109, 30001, 'Honda', 2, 50);

INSERT INTO OFFERS 
VALUES(109, 30001, 'Nissan', 2, 70);

INSERT INTO OFFERS 
VALUES(109, 30001, 'Toyota', 2, 60);

INSERT INTO OFFERS 
VALUES(109, 30002, 'Honda', 2, 70);

INSERT INTO OFFERS 
VALUES(109, 30002, 'Nissan', 2, 90);

INSERT INTO OFFERS 
VALUES(109, 30002, 'Toyota', 2, 80);

INSERT INTO OFFERS 
VALUES(109, 30003, 'Honda', 2, 85);

INSERT INTO OFFERS 
VALUES(109, 30003, 'Nissan', 2, 105);

INSERT INTO OFFERS 
VALUES(109, 30003, 'Toyota', 2, 95);

INSERT INTO OFFERS 
VALUES(110, 30001, 'Honda', 1, 140);

INSERT INTO OFFERS 
VALUES(110, 30001, 'Nissan', 1, 175);

INSERT INTO OFFERS 
VALUES(110, 30001, 'Toyota', 1, 160);

INSERT INTO OFFERS 
VALUES(110, 30002, 'Honda', 1, 160);

INSERT INTO OFFERS 
VALUES(110, 30002, 'Nissan', 1, 195);

INSERT INTO OFFERS 
VALUES(110, 30002, 'Toyota', 1, 180);

INSERT INTO OFFERS 
VALUES(110, 30003, 'Honda', 1, 175);

INSERT INTO OFFERS 
VALUES(110, 30003, 'Nissan', 1, 210);

INSERT INTO OFFERS 
VALUES(110, 30003, 'Toyota', 1, 195);

INSERT INTO OFFERS 
VALUES(111, 30001, 'Honda', 3, 590);

INSERT INTO OFFERS 
VALUES(111, 30001, 'Nissan', 3, 700);

INSERT INTO OFFERS 
VALUES(111, 30001, 'Toyota', 3, 680);

INSERT INTO OFFERS 
VALUES(111, 30002, 'Honda', 3, 610);

INSERT INTO OFFERS 
VALUES(111, 30002, 'Nissan', 3, 720);

INSERT INTO OFFERS 
VALUES(111, 30002, 'Toyota', 3, 700);

INSERT INTO OFFERS 
VALUES(111, 30003, 'Honda', 3, 625);

INSERT INTO OFFERS 
VALUES(111, 30003, 'Nissan', 3, 735);

INSERT INTO OFFERS 
VALUES(111, 30003, 'Toyota', 3, 715);

INSERT INTO OFFERS 
VALUES(112, 30001, 'Honda', 4, 400);

INSERT INTO OFFERS 
VALUES(112, 30001, 'Nissan', 4, 500);

INSERT INTO OFFERS 
VALUES(112, 30001, 'Toyota', 4, 450);

INSERT INTO OFFERS 
VALUES(112, 30002, 'Honda', 4, 420);

INSERT INTO OFFERS 
VALUES(112, 30002, 'Nissan', 4, 520);

INSERT INTO OFFERS 
VALUES(112, 30002, 'Toyota', 4, 470);

INSERT INTO OFFERS 
VALUES(112, 30003, 'Honda', 4, 435);

INSERT INTO OFFERS 
VALUES(112, 30003, 'Nissan', 4, 535);

INSERT INTO OFFERS 
VALUES(112, 30003, 'Toyota', 4, 485);

INSERT INTO OFFERS 
VALUES(113, 30001, 'Honda', 3, 120);

INSERT INTO OFFERS 
VALUES(113, 30001, 'Nissan', 3, 190);

INSERT INTO OFFERS 
VALUES(113, 30001, 'Toyota', 3, 200);

INSERT INTO OFFERS 
VALUES(113, 30002, 'Honda', 3, 125);

INSERT INTO OFFERS 
VALUES(113, 30002, 'Nissan', 3, 195);

INSERT INTO OFFERS 
VALUES(113, 30002, 'Toyota', 3, 205);

INSERT INTO OFFERS 
VALUES(113, 30003, 'Honda', 3, 140);

INSERT INTO OFFERS 
VALUES(113, 30003, 'Nissan', 3, 180);

INSERT INTO OFFERS 
VALUES(113, 30003, 'Toyota', 3, 195);

INSERT INTO OFFERS 
VALUES(114, 30001, 'Honda', 6, 195);

INSERT INTO OFFERS 
VALUES(114, 30001, 'Nissan', 6, 210);

INSERT INTO OFFERS 
VALUES(114, 30001, 'Toyota', 6, 215);

INSERT INTO OFFERS 
VALUES(114, 30002, 'Honda', 6, 200);

INSERT INTO OFFERS 
VALUES(114, 30002, 'Nissan', 6, 215);

INSERT INTO OFFERS 
VALUES(114, 30002, 'Toyota', 6, 220);

INSERT INTO OFFERS 
VALUES(114, 30003, 'Honda', 6, 210);

INSERT INTO OFFERS 
VALUES(114, 30003, 'Nissan', 6, 220);

INSERT INTO OFFERS 
VALUES(114, 30003, 'Toyota', 6, 215);

INSERT INTO OFFERS 
VALUES(115, 30001, 'Honda', 9, 300);

INSERT INTO OFFERS 
VALUES(115, 30001, 'Nissan', 9, 310);

INSERT INTO OFFERS 
VALUES(115, 30001, 'Toyota', 9, 305);

INSERT INTO OFFERS 
VALUES(115, 30002, 'Honda', 9, 305);

INSERT INTO OFFERS 
VALUES(115, 30002, 'Nissan', 9, 315);

INSERT INTO OFFERS 
VALUES(115, 30002, 'Toyota', 9, 310);

INSERT INTO OFFERS 
VALUES(115, 30003, 'Honda', 9, 310);

INSERT INTO OFFERS 
VALUES(115, 30003, 'Nissan', 9, 305);

INSERT INTO OFFERS 
VALUES(115, 30003, 'Toyota', 9, 310);
