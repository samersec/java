
create database skillhub;
use skillhub;
 
create table etudiants(
id int primary key auto_increment,
Prenom varchar(20) not null,
Nom varchar(20) not null,
Daten date(10) not null,
Mail varchar(20) not null,
Cours varchar(20) not null
)


insert into etudiants(Prenom,nom,daten,mail,cours)values("dali","said","2023","dali@gmail.com","java");