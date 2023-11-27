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
DROP TABLE entrenados;
drop table inscritos;
drop table articulos;
drop table solicitudesModificacionDatos;


create table socios (id integer PRIMARY KEY AUTOINCREMENT, dni varchar2(255) UNIQUE, name varchar2(255),surname varchar2(255), email varchar2(255), telf int, cuota_type varchar2(255), iban varchar2(255), height real, weight real, birth_date date, gender varchar2(255), directive bool);
create table licencias (owner_id int,tutor_dni varchar2(255), tutor_name varchar2(255),tutor_surname varchar2(255), tutor_email varchar2(255), tutor_telf int, tutor_birth_date date, tutor_gender varchar2(255), state varchar2(255), price int, licence_type varchar2(255), facturation_direction varchar2(255),facturation_info varchar2(255), deporte varchar2(255), facturation_date date, validation_date date, foreign key(owner_id) references socios(id));
create table cuotas (owner_id int, cuota_type varchar2(255), price int, state int, foreign key(owner_id) references socios(id));
create table instalaciones (code varchar2(255), name varchar2(255), min_reserva int, max_reserva int, min_curso int, max_curso int, foreign key(code) references reservas(instalation_code));
create table reservas (id integer PRIMARY KEY AUTOINCREMENT, fecha_inicio date, fecha_fin date, instalation_code varchar2(255), tipo varchar2(255));
CREATE TABLE IF NOT EXISTS participante_reserva (id integer PRIMARY KEY AUTOINCREMENT, reserva_id int, dni varchar2(255) NOT NULL, FOREIGN KEY(reserva_id) REFERENCES reservas(id));
create table loggin (dni_socio varchar2(255) PRIMARY KEY, contrasena varchar2(255) NOT NULL, fin_bloqueo date, foreign key(dni_socio) references socios(dni));
create table test(id int, fecha date, tipo varchar2(255), peso int, edad int, sexo varchar2(255), tiempo int, pulsaciones int, distance int, resultado int);
create table competiciones (id integer PRIMARY KEY AUTOINCREMENT, name varchar2(255), competition_date date, place varchar2(255), categories varchar2(255), deporte varchar2(255), estado varchar2(255));
create table recibos (owner_iban varchar2(255), number int, amount int, value_date date, charge_date date, concept varchar2(255), type_recibo varchar2(255), state varchar2(255));
create table inscripcion_competiciones(competicion_id integer PRIMARY KEY, socio_id integer UNIQUE, foreign key(competicion_id) references competiciones(id), foreign key(socio_id) references socios(id));
create table solicitudesModificacionDatos(id integer PRIMARY KEY AUTOINCREMENT, dni varchar2(255) NOT NULL UNIQUE, name varchar2(255), surname varchar2(255), modificacion varchar2(255));
create table asambleas (type varchar2(255), date varchar2(255), hour_conv1 varchar2(255), hour_conv2 varchar2(255), orderOfDay varchar2(255), acta varchar2(255), state varchar2(255));
create table entrenados(entrenador_id int, entrenado_id);
create table articulos(id int, name varchar2(255), price double);
CREATE TABLE IF NOT EXISTS cursillos (id integer PRIMARY KEY AUTOINCREMENT, nombre varchar2(255), code_instalacion varchar2(255), fecha_inicio date, fecha_fin date, price real, plazas int, foreign key(code_instalacion) REFERENCES instalaciones(code));
CREATE TABLE IF NOT EXISTS entrenadores_cursillos (id integer PRIMARY KEY AUTOINCREMENT, id_curso int, dni varchar2(255), foreign key(id_curso) REFERENCES cursillos(id));
CREATE TABLE inscritos(id_cursante int, id_curso int, fecha_eliminacion date, estado varchar2(255));
