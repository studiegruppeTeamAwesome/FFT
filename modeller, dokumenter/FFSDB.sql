USE master;
IF DB_ID('FFSDB') IS NOT NULL
  DROP DATABASE FFSDB;

CREATE DATABASE  FFSDB;
GO
USE FFSDB;

CREATE TABLE costumer(
phone int  NOT NULL,
name nvarchar(30),
adress nvarchar(30),
CPR nvarchar(30) not null,

PRIMARY KEY(phone)
);
insert into costumer VALUES(544567543,'sara rig','kongevej 22 6000 skjrn','0101992331');
insert into costumer VALUES(34565432,'neda megetrig','gammelvej2','20034535');


CREATE TABLE car(
id int IDENTITY NOT NULL,
price int NOT NULL,
name nvarchar(30),
PRIMARY KEY(id));

insert into car VALUES (23889000,'Ferrari 6754');
insert into car VALUES (65000000,'Ferrari 5674');
insert into car VALUES (56740000,'Ferrari 490');

CREATE TABLE loanOffers(
id int IDENTITY NOT NULL,
date nvarchar(30) NOT NULL,
downPayment int not null,
repayments int not null,
costumerPhone int not null,
CarId int not null,
SalesmanId int not null,
PRIMARY KEY(id));



CREATE TABLE Salesmen(
id int IDENTITY NOT NULL,
name nvarchar(30),
chaf bit  not null,
PRIMARY KEY(id));

insert into Salesmen VALUES ('sara',0);
insert into Salesmen VALUES ('martin',1);
insert into Salesmen VALUES ('sofia',0);


