USE master;
IF DB_ID('FFSDB') IS NOT NULL
  DROP DATABASE FFSDB;

CREATE DATABASE  FFSDB;
GO
USE FFSDB;

CREATE TABLE customers(
phone int  NOT NULL,
customerName nvarchar(30),
customerAddress nvarchar(30),
postalCode int not null,
CPR nvarchar(10) not null,
hasActiveLoan bit,
PRIMARY KEY(phone)
);

insert into customers VALUES(54456754,'sara rig','kongevej 22', 7400,'0101992331', 0);
insert into customers VALUES(34565432,'neda megetrig','gammelvej2',7400,'2003453510', 0);
insert into customers VALUES(11111111,'Testy McTesterson','gammelvej3', 7400 ,'0000000004', 0);

CREATE TABLE city(
postalCode int not null,
cityName nvarchar(30),
PRIMARY KEY(postalcode)); 

CREATE TABLE cars(
id int IDENTITY NOT NULL,
price int NOT NULL,
model nvarchar(30),
PRIMARY KEY(id));

insert into cars VALUES (20000000,'Ferrari 6754');
insert into cars VALUES (15000000,'Ferrari 5674');
insert into cars VALUES (10000000,'Ferrari 490');


CREATE TABLE salesmen(
id int IDENTITY NOT NULL,
salesmanName nvarchar(30),
boss bit  not null,
loanLimit int not null,
PRIMARY KEY(id));

insert into Salesmen VALUES ('sara',0, 12000000);
insert into Salesmen VALUES ('martin',1, 1000000000);
insert into Salesmen VALUES ('bloms',0, 18000000 );
insert into Salesmen VALUES ('Shahnaz Yahyavi',0, 18000000 );


CREATE TABLE loanOffers(
id int IDENTITY NOT NULL,
annualCost float not null,
downPayment int not null,
repayments float not null,
noOfMonths int not null,
customerPhone int not null,
CarId int not null,
SalesmanId int not null,
isApproved bit not null,
PRIMARY KEY(id));
insert into loanOffers VALUES(5, 1000000, 50000, 120, 11111111, 1, 1, 0);
insert into loanOffers VALUES(6, 500000, 70000, 108, 34565432, 2, 3, 0);
--insert into loanOffers VALUES(5.5 , 750000, 80000, 96, 33333333, 2, 1, 0);
--insert into loanOffers VALUES(6, 1200000, 45000, 100, 44444444, 3, 3, 0);
--insert into loanOffers VALUES(4.5, 1700000, 350000, 84, 55555555, 2, 4, 0);
--insert into loanOffers VALUES(6, 500000, 70000, 108, 66666666, 1, 4, 0);
--insert into loanOffers VALUES(3.5, 2500000, 170000, 132, 77777777, 2, 3, 0);
--insert into loanOffers VALUES(9, 500000, 70000, 144, 88888888, 3, 1, 0);
--insert into loanOffers VALUES(7.5, 110000, 80000, 108, 99999999, 3, 3, 0);
--insert into loanOffers VALUES(8, 2000000, 350000, 60, 12345678, 1, 4, 0);


select * from loanOffers;

