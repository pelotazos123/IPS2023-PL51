--Primero se deben borrar todas las tablas (de detalle a maestro) y lugo anyadirlas (de maestro a detalle)
--(en este caso en cada aplicacion se usa solo una tabla, por lo que no hace falta)

--Para giis.demo.tkrun:

drop table socios;
drop table licencias;
drop table cuotas;
drop table reservas;
drop table instalaciones;
drop table asambleas;
drop table recibos;

create table socios (id int, dni varchar2(255), name varchar2(255),surname varchar2(255), email varchar2(255), cuota_type varchar2(255), iban varchar2(255), height varchar2(255), weight int, birth_date date, gender varchar2(255), directive bool);
create table licencias (owner_id int, tutor_name varchar2(255),tutor_surname varchar2(255), tutor_birth_date date, tutor_gender varchar2(255), state varchar2(255), price int, licence_type varchar2(255), facturation_direction varchar2(255),facturation_info varchar2(255), foreign key(owner_id) references socios(id));
create table cuotas (owner_id int, cuota_type varchar2(255), price int, state int, foreign key(owner_id) references socios(id));
create table reservas (owner_id int, fecha varchar, hora varchar, instalation_code varchar2(255), foreign key(owner_id) references socios(id));
create table instalaciones (code varchar2(255), name varchar2(255), foreign key(code) references reservas(instalation_code));
-- create table recibos (number int, foreign key(number) references cuotas(num_recibo));
-- create table asambleas (type varchar2(255), announcement varchar2(255), date_announcement1 date, date_announcement2 date);

-- Elegid qué tablas se quedan o hay que modificar de las 2 que hay
-- create table socios (id int, name varchar2(255), cuota_type varchar2(255), iban varchar2(255), height varchar2(255), weight int, age int, gender varchar2(255), directive bool);
-- create table licencias (owner_id int, tutor_name varchar2(255), tutor_age int, state varchar2(255), price int, facturation_direction varchar2(255),facturation_info varchar2(255), foreign key(owner_id) references socios(id));
-- create table reservas (owner_id int, date date, instalation_code varchar2(255), foreign key(owner_id) references socios(id));
-- create table instalaciones (code varchar2(255), name varchar2(255), foreign key(code) references reservas(instalation_code));
create table recibos (owner_iban varchar2(255), number int, amount int, value_date date, charge_date date);
create table asambleas (type varchar2(255), announcement varchar2(255), date_announcement1 date, date_announcement2 date, orderOfDay varchar2(255));