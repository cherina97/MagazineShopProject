drop database if exists magazine_shop;
create database magazine_shop;
use magazine_shop;

drop database if exists users;
create table users(
id int not null primary key auto_increment,
first_name varchar(50) not null,
last_name varchar(50) not null,
email varchar(50) not null unique,
role varchar(50) not null,
password varchar(50) not null
);

INSERT INTO users(first_name, last_name, email, role, password) 
values("Aaaaaaaa", "Aaaaaaaaaaaa", "kkk@mail", 'USER', "kkkkkkkkk"),
("Bbbbbbbbbbbb", "Bbbbbbbb", "mmm@mail", 'ADMIN', "mmmmmmmmm");

create table products(
id int not null primary key auto_increment,
name varchar(50) not null,
description varchar(50) not null,
price decimal (5, 2) not null
);

insert into products(name, description, price) 
values ("Book", "Lorem lorem lorem lorem lorem", 20),
("Book2", "Lorem lorem lorem lorem lorem", 40),
("Book3", "Lorem lorem lorem lorem lorem", 30),
("Book4", "Lorem lorem lorem lorem lorem", 60),
("Book5", "Lorem lorem lorem lorem lorem", 10);

create table buckets(
id int not null primary key auto_increment,
user_id int not null, 
foreign key (user_id) references users(id),
product_id int not null,
foreign key (product_id) references products(id),
purchase_date date not null
);

SET GLOBAL time_zone = '+2:00';

