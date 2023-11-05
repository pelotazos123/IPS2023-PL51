--Datos para carga inicial de la base de datos

--Para giis.demo.tkrun:
delete from socios;

insert into socios (id,dni,name,surname,email,telf,cuota_type,iban,height,weight,birth_date,gender,directive) values 
	(100,'123456789A','Lucia','Suarez','lucia@gmail.com',684295304,'SENIOR','ES6000138500051234567523','1.72',70,'2000-10-24','MUJER',true),
	(101,'123456789B','Juan','Garcia','juan@gmail.com',650369327,'VETERANO','ES6000491506251234567713','1.95',105,'1950-05-14','HOMBRE',true),
	(102,'123456789C','Jose','Alonso','jose@gmail.com',789453618,'SENIOR','ES6340491500088834566464','1.81',75,'1999-06-29','HOMBRE',false),
	(103,'123456789D','Paula','Perez','paula@gmail.com',693528769,'SUB18','ES6000491515051234567692','1.83',78,'2007-01-16','MUJER',false),
	(104,'123456789E','Cristian','Gonzalez','cristian@gmail.com',634895248,'VETERANO','ES6000491500063234560069','1.78',77,'1963-09-10','HOMBRE',false),
	(105,'123456789J','Pedro','Fernandez','pedro@gmail.com',796231447,'VETERANO','ES6000436501851414507715','1.75',80,'1995-01-01','HOMBRE',false),
	(106,'123456789K','Adrian','Garcia','adrian@gmail.com',649874539,'SENIOR','ES6340491500088834566478','1.54',57,'2008-06-06','HOMBRE',false);

insert into cuotas (owner_id, cuota_type, price, state) values
	(100, 'Joven', 100, 'Pendiente'),
	(101, 'Joven', 100, 'Pendiente'),
	(102, 'Adulto', 200, 'Emitida'),
	(103, 'Jubilado', 150, 'Pendiente');

insert into licencias (owner_id, tutor_dni, tutor_name, tutor_surname, tutor_email, tutor_telf, tutor_birth_date, tutor_gender, state, price, licence_type, facturation_direction, facturation_info) values 
	(100,'noTutor','noTutor','noTutor','noTutor',null,null,null, 'PENDIENTE', 30, 'DEPORTISTA', "ESXX-XXXX-XXXX-XXXX-XXXX-XXXX", null),
	(101,'noTutor','noTutor','noTutor','noTutor',null,null,null,'pagado',30,'DEPORTISTA','Aviles','Segundo A'),
	(101,'noTutor','noTutor','noTutor','noTutor',null,null,null,'pagado',30,'JUEZ','Aviles','Tercero B'),
	(101,'noTutor','noTutor','noTutor','noTutor',null,null,null,'pagado',30,'MONITOR','Aviles','Tercero B'),
	(104,'noTutor','noTutor','noTutor','noTutor',null,null,null,'pagado',30,'JUEZ','Vega','Bajo A'),
	(105,'noTutor','noTutor','noTutor','noTutor',null,null,null,'pagado',30,'DEPORTISTA','Gijon','Segundo A'),
	(105,'noTutor','noTutor','noTutor','noTutor',null,null,null,'pagado',30,'JUEZ','Gijon','Segundo A'),
	(106,'123456789L','Pedro','Garcia','pedro@gmail.com',654873691,'1983-6-8','HOMBRE','pagado',30,'DEPORTISTA','Oviedo','Segundo A');
	
INSERT INTO reservas (owner_id, fecha, instalation_code, extra) VALUES 
	(100, "2023-11-20 20:00", "13410", true),
	(100, "2023-11-22 21:00", "13411", false),
	(100, "2023-11-23 22:00", "13412", false),
	(100, "2023-11-24 23:00", "13413", false);

INSERT INTO participante_reserva (reserva_id, dni) VALUES
	(1, "123456789A"),
	(2, "123456789B"),
	(3, "123456789A"),
	(3, "123456789B"),
	(3, "123456789C"),
	(3, "123456789D"),
	(4, "123456789A"),
	(4, "123456789B");

INSERT INTO instalaciones (code, name, min_users, max_users) VALUES
	("13410", "Pista Atletismo", 1, 1),
	("13411", "Piscina", 1, 1),
	("13412", "Cancha fútbol", 1, 28),
	("13413", "Pista de tenis",1, 4);