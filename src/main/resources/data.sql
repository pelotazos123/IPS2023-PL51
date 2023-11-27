--Datos para carga inicial de la base de datos

--Para giis.demo.tkrun:
delete from socios;
DELETE FROM participante_reserva;
DELETE FROM reservas;
DELETE FROM entrenadores_cursillos;
DELETE FROM cursillos;

insert into socios (id,dni,name,surname,email,telf,cuota_type,iban,height,weight,birth_date,gender,directive) values 
	(100,'123456789A','Lucia','Suarez','UO276220@uniovi.es',684295304,'SENIOR','ES6000138500051234567523','1.72',70,'2000-10-24','MUJER',true),
	(101,'123456789B','Juan','Garcia','UO277310@uniovi.es',650369327,'VETERANO','ES6000491506251234567713','1.95',105,'1950-05-14','HOMBRE',true),
	(102,'123456789C','Jose','Alonso','UO289549@uniovi.es',789453618,'SENIOR','ES6340491500088834566464','1.81',75,'1999-06-29','HOMBRE',false),
	(103,'123456789D','Paula','Perez','pau@gmail.com',693528769,'SUB18','ES6000491515051234567692','1.83',78,'2007-01-16','MUJER',false),
	(104,'123456789E','Cristian','Gonzalez','cristian@gmail.com',634895248,'VETERANO','ES6000491500063234560069','1.78',77,'1963-09-10','HOMBRE',false),
	(105,'123456789J','Pedro','Fernandez','UO276220@uniovi.es',796231447,'VETERANO','ES6000436501851414507715','1.75',80,'1995-01-01','HOMBRE',false),
	(106,'123456789K','Adrian','Garcia','adrian@gmail.com',649874539,'SUB18','ES6340491500088834566478','1.54',57,'2008-06-06','HOMBRE',false);

insert into cuotas (owner_id, cuota_type, price, state) values
	(100, 'Joven', 100, 'Pendiente'),
	(101, 'Joven', 100, 'Pendiente'),
	(102, 'Adulto', 200, 'Pendiente'),
	(103, 'Jubilado', 150, 'Pendiente');

insert into licencias (owner_id, tutor_dni, tutor_name, tutor_surname, tutor_email, tutor_telf, tutor_birth_date, tutor_gender, state, price, licence_type, facturation_direction, facturation_info, deporte) values 
	(100,'noTutor','noTutor','noTutor','noTutor',null,null,null, 'PENDIENTE', 30, 'DEPORTISTA', "ESXX-XXXX-XXXX-XXXX-XXXX-XXXX", null, 'FUTBOL'),
	(100,'noTutor','noTutor','noTutor','noTutor',null,null,null, 'PENDIENTE', 30, 'MONITOR', "ESXX-XXXX-XXXX-XXXX-XXXX-XXXX", null, 'TENIS'),
	(101,'noTutor','noTutor','noTutor','noTutor',null,null,null,'pagado',30,'DEPORTISTA','Aviles','Segundo A', 'TENIS'),
	(101,'noTutor','noTutor','noTutor','noTutor',null,null,null,'pagado',30,'JUEZ','Aviles','Tercero B', 'NATACION'),
	(101,'noTutor','noTutor','noTutor','noTutor',null,null,null,'pagado',30,'MONITOR','Aviles','Tercero B', 'FUTBOL'),
	(104,'noTutor','noTutor','noTutor','noTutor',null,null,null,'pagado',30,'JUEZ','Vega','Bajo A', 'NATACION'),
	(105,'noTutor','noTutor','noTutor','noTutor',null,null,null,'pagado',30,'DEPORTISTA','Gijon','Segundo A', 'TIRO_CON_ARCO'),
	(105,'noTutor','noTutor','noTutor','noTutor',null,null,null,'pagado',30,'JUEZ','Gijon','Segundo A', 'TIRO_CON_ARCO'),
	(106,'123456789L','Pedro','Garcia','pedro@gmail.com',654873691,'1983-6-8','HOMBRE','pagado',30,'DEPORTISTA','Oviedo','Segundo A', 'FUTBOL');

INSERT INTO reservas (id, fecha_inicio, fecha_fin, instalation_code, tipo) VALUES 
	(0, "2023-11-20 20:00", "2023-11-20 22:00", "13410", "RESERVA"),
	(1, "2023-11-21 11:00", "2023-11-21 12:00", "13413", "ANULADA");

INSERT INTO participante_reserva (reserva_id, dni) VALUES
	(0, "123456789A");

INSERT INTO instalaciones (code, name, min_reserva, max_reserva, min_curso, max_curso) VALUES
	("13410", "Tiro con arco", 1, 1, 5, 12),
	("13411", "Piscina", 1, 1, 5, 15),
	("13412", "Campo de fútbol", 1, 28, 10, 22),
	("13413", "Pista de tenis",1, 4, 6, 10);

insert into loggin (dni_socio, contrasena) values
	('123456789A','c455eb6a355fd48b6ece6dee6fbd6b53'),
	('123456789B','a5a5cd2a943b76e5d8d837ed2e236849'), 
	('123456789C','48bd4ae36c967008e0ef546d6d4dcd16'), 
	('123456789D','26fa039217f63fbc3cb73242c7a8acd9'), 
	('123456789E','7143807319c982cdcb00af7d92ba1ea8'), 
	('123456789J','99eeb6fb14afe15448b972fad2bb6337'), 
	('123456789K','4d1000ad38485563b550c7dc6f2221f1'); 
	--Usuario: 123456789A contraseña:a5896
	--Usuario: 123456789B contraseña:b8763
	--Usuario: 123456789C contraseña:c9863
	--Usuario: 123456789D contraseña:d4789
	--Usuario: 123456789E contraseña:e4852
	--Usuario: 123456789J contraseña:j1236
	--Usuario: 123456789K contraseña:k2143

insert into test(id, fecha, tipo, peso, edad, sexo, tiempo, pulsaciones, distance, resultado) values
	(100, '2021-05-10','ROCKPORT', 55, 20, 'MUJER', '5.5', 170, null, '77.36'),
	(100, '2023-05-10','ROCKPORT', 65, 22, 'MUJER', '5.8', 156, null, '76.07'),
	(100, '2023-10-10','ROCKPORT', 67, 23, 'MUJER', '5.0', 160, null, '77.34'),
	(100, '2023-10-10','ROCKPORT', 67, 23, 'MUJER', '5.2', 155, null, '87.00'),
	(100, '2021-05-10','COOPER', null, null, 'MUJER', null, null, 3.2, '82.81'),
	(100, '2023-05-10','COOPER', null, null, 'MUJER', null, null, 3.8, '87.89'),
	(103, '2023-05-10','ROCKPORT', 65, 22, 'MUJER', '5.8', 156, null, '72.07'),
	(103, '2023-10-10','ROCKPORT', 67, 23, 'MUJER', '5.0', 160, null, '79.34'),
	(104, '2021-05-10','COOPER', null, null, 'HOMBRE', null, null, 3.2, '82.81'),
	(104, '2023-05-10','COOPER', null, null, 'HOMBRE', null, null, 2.5, '67.17'),
	(104, '2023-10-10','COOPER', null, null, 'HOMBRE', null, null, 2.2, '60.46');

insert into entrenados(entrenador_id, entrenado_id) values 
	(100, 104),
	(100, 103),
	(101, 105);
	
insert into asambleas(type, date, hour_conv1, hour_conv2, orderOfDay, acta) values 
	('Ordinaria', '2023-12-01', '8:00', '8:30', 'Eleccion de presidente', 'Sin acta'),
	('Ordinaria', '2023-11-01', '8:00', '8:30', 'Gestion de gastos', 'Sin acta'),
	('Extraordinaria', '2023-10-10', '10:00', '11:00', 'Problemas de horarios', 'Sin acta'),
	('Ordinaria', '2023-12-10', '8:00', '8:30', 'Gestion de gastos', 'Sin acta'),
	('Extraordinaria', '2023-12-10', '14:00', '16:00', 'Nuevos entrenadores', 'Sin acta'),
	('Extraordinaria', '2022-06-14', '10:00', '11:00', 'Problemas de horarios', 'Sin acta');