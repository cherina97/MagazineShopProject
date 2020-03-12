drop database if exists magazine_shop;
create database magazine_shop;
use magazine_shop;

drop database if exists users;
create table users(
id int not null primary key auto_increment,
first_name varchar(50) not null,
last_name varchar(50) not null,
email varchar(50) not null unique,
role varchar(50) not null
);

create table products(
id int not null primary key auto_increment,
name varchar(50) not null,
description varchar(50) not null,
price decimal (5, 2) not null
);

create table buckets(
id int not null primary key auto_increment,
user_id int not null, 
foreign key (user_id) references users(id),
product_id int not null,
foreign key (product_id) references products(id),
purchase_date date not null
);



