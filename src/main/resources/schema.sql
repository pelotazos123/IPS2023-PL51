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
drop table loggin;
drop table competiciones;
drop table inscripcion_competiciones;
drop table test;
drop table entrenados;

create table socios (id int, dni varchar2(255), name varchar2(255),surname varchar2(255), email varchar2(255), telf int, cuota_type varchar2(255), iban varchar2(255), height real, weight real, birth_date date, gender varchar2(255), directive bool);
create table licencias (owner_id int,tutor_dni varchar2(255), tutor_name varchar2(255),tutor_surname varchar2(255), tutor_email varchar2(255), tutor_telf int, tutor_birth_date date, tutor_gender varchar2(255), state varchar2(255), price int, licence_type varchar2(255), facturation_direction varchar2(255),facturation_info varchar2(255), foreign key(owner_id) references socios(id));
create table cuotas (owner_id int, cuota_type varchar2(255), price int, state int, foreign key(owner_id) references socios(id));
create table instalaciones (code varchar2(255), name varchar2(255), min_users int, max_users int, foreign key(code) references reservas(instalation_code));
create table reservas (id integer PRIMARY KEY AUTOINCREMENT, fecha date, instalation_code varchar2(255), extra bool);
CREATE TABLE IF NOT EXISTS participante_reserva (id integer PRIMARY KEY AUTOINCREMENT, reserva_id int, dni varchar2(255) NOT NULL, FOREIGN KEY(reserva_id) REFERENCES reservas(id));
create table loggin (dni_socio varchar2(255), contrasena varchar2(255), fin_bloqueo date, foreign key(dni_socio) references socios(dni));
create table test(id int, fecha date, tipo varchar2(255), peso int, edad int, sexo varchar2(255), tiempo int, pulsaciones int, distance int, resultado int);
-- create table recibos (number int, foreign key(number) references cuotas(num_recibo));
-- create table asambleas (type varchar2(255), announcement varchar2(255), date_announcement1 date, date_announcement2 date);
-- create table licencias (owner_id int, tutor_name varchar2(255), tutor_age int, state varchar2(255), price int, facturation_direction varchar2(255),facturation_info varchar2(255), foreign key(owner_id) references socios(id));
-- create table reservas (owner_id int, date date, instalation_code varchar2(255), foreign key(owner_id) references socios(id));
create table recibos (owner_iban varchar2(255), number int, amount int, value_date date, charge_date date, type_recibo varchar2(255), state varchar2(255));
create table asambleas (type varchar2(255), announcement varchar2(255), date_announcement1 date, date_announcement2 date, orderOfDay varchar2(255));
create table competiciones (id int, name varchar2(255), competition_date date, place varchar2(255), categories varchar2(255));
create table inscripcion_competiciones(competicion_id int, socio_id int, foreign key(competicion_id) references competiciones(id), foreign key(socio_id) references socios(id));
create table entrenados(entrenador_id int, entrenado_id);