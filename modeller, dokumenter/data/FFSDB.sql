USE master;
IF DB_ID('FFSDB') IS NOT NULL
  DROP DATABASE FFSDB;

CREATE DATABASE  FFSDB;
GO
USE FFSDB;

CREATE TABLE customer(
phone int  NOT NULL,
customerName nvarchar(30),
custumerAddress nvarchar(30),
postalCode int not null,
CPR nvarchar(10) not null,
creditRating nvarchar(1),
hasActiveLoan bit
PRIMARY KEY(phone)
);

insert into customer VALUES(54456754,'sara rig','kongevej 22', 7400,'0101992331', 'a', 0);
insert into customer VALUES(34565432,'neda megetrig','gammelvej2',7400,'2003453510','a', 0);
insert into customer VALUES(11111111,'Testy McTesterson','gammelvej3', 7400 ,'0000000003','a', 0);

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

CREATE TABLE loanOffers(
id int IDENTITY NOT NULL,
downPayment int not null,
repayments int not null,
noOfMonths int not null,
costumerPhone int not null,
CarId int not null,
SalesmanId int not null,
PRIMARY KEY(id));



CREATE TABLE Salesmen(
id int IDENTITY NOT NULL,
salesmanName nvarchar(30),
boss bit  not null,
loanLimit int not null,
PRIMARY KEY(id));

insert into Salesmen VALUES ('sara',0, 12000000);
insert into Salesmen VALUES ('martin',1, 1000000000);
insert into Salesmen VALUES ('bloms',0, 18000000 );

select * from Salesmen where salesmanName = 'bloms';
select * from customer where phone = 54456754;
