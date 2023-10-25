--Datos para carga inicial de la base de datos

--Para giis.demo.tkrun:
delete from socios;

insert into socios (id,dni,name,surname,email,cuota_type,iban,height,weight,birth_date,gender,directive) values 
	(100,'123456789A','Lucia','Suarez','lucia@gmail.com','CUOTA_JOVEN','ES6000138500051234567523','1.72',70,'2000-24-10','MUJER',true),
	(101,'123456789B','Juan','Garcia','juan@gmail.com','CUOTA_ADULTO','ES6000491506251234567713','1.95',105,'1950-05-14','HOMBRE',true),
	(102,'123456789C','Jose','Alonso','jose@gmail.com','CUOTA_JOVEN','ES6340491500088834566464','1.81',75,'1999-06-29','HOMBRE',false),
	(103,'123456789D','Paula','Perez','paula@gmail.com','CUOTA_JOVEN','ES6000491515051234567692','1.83',78,'2007-01-16','MUJER',false),
	(104,'123456789E','Cristian','Gonzalez','cristian@gmail.com','CUOTA_JUBILADO','ES6000491500063234560069','1.78',77,'1963-09-10','HOMBRE',false),
	(105,'123456789J','Pedro','Fernandez','pedro@gmail.com','CUOTA_JOVEN','ES6000436501851414507715','1.75',80,'1995-01-01','HOMBRE',false),
	(106,'123456789K','Adrian','Garcia','adrian@gmail.com','CUOTA_JOVEN','ES6340491500088834566478','1.54',57,'2008-06-06','HOMBRE',false);

insert into cuotas (owner_id, cuota_type, price, state) values
	(100, 'Joven', 100, 'Pendiente'),
	(101, 'Joven', 100, 'Pendiente'),
	(102, 'Adulto', 200, 'Emitida'),
	(103, 'Jubilado', 150, 'Pendiente');

insert into licencias (owner_id, tutor_name, tutor_surname, tutor_birth_date, tutor_gender, state, price, licence_type, facturation_direction, facturation_info) values 
	(100,'noTutor','noTutor',null,null, 'PENDIENTE', 30, 'DEPORTISTA', "ESXX-XXXX-XXXX-XXXX-XXXX-XXXX", null),
	(101,'noTutor','noTutor',null,null,'pagado',30,'DEPORTISTA','Aviles','Segundo A'),
	(101,'noTutor','noTutor',null,null,'pagado',30,'JUEZ','Aviles','Tercero B'),
	(101,'noTutor','noTutor',null,null,'pagado',30,'MONITOR','Aviles','Tercero B'),
	(104,'noTutor','noTutor',null,null,'pagado',30,'JUEZ','Vega','Bajo A'),
	(105,'noTutor','noTutor',null,null,'pagado',30,'DEPORTISTA','Gijon','Segundo A'),
	(105,'noTutor','noTutor',null,null,'pagado',30,'JUEZ','Gijon','Segundo A'),
	(106,'Pedro','Garcia','8-6-1983-6-8','HOMBRE','pagado',30,'DEPORTISTA','Oviedo','Segundo A');
	
INSERT INTO reservas (owner_id, fecha, hora, instalation_code) VALUES 
	(100, "20/10/2023", "20:00", "13410"),
	(100, "22/10/2023", "20:00", "13412"),
	(100, "24/10/2023", "20:00", "13411"),
	(100, "21/10/2023", "20:00", "13413");

INSERT INTO instalaciones (code, name) VALUES
	("13410", "Pista Atletismo"),
	("13411", "Piscina"),
	("13412", "Cancha fútbol"),
	("13413", "Sauna");