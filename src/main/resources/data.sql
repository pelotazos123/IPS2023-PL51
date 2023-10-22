--Datos para carga inicial de la base de datos

--Para giis.demo.tkrun:
delete from socios;

insert into socios (id,name,surname,cuota_type,iban,height,weight,age,gender,directive) values 
	(100,'Lucia','Suarez','CUOTA_JOVEN','ES6000138500051234567523','1.72',70,24,'MUJER',true),
	(101,'Juan','Garcia','CUOTA_ADULTO','ES6000491506251234567713','1.95',105,54,'HOMBRE',true),
	(102,'Jose','Alonso','CUOTA_JOVEN','ES6340491500088834566464','1.81',75,24,'HOMBRE',false),
	(103,'Paula','Perez','CUOTA_JOVEN','ES6000491515051234567692','1.83',78,16,'MUJER',false),
	(104,'Cristian','Gonzalez','CUOTA_JUBILADO','ES6000491500063234560069','1.78',77,71,'HOMBRE',false),
	(105,'Pedro','Fernandez','CUOTA_JOVEN','ES6000436501851414507715','1.75',80,23,'HOMBRE',false),
	(106,'Adrian','Garcia','CUOTA_JOVEN','ES6340491500088834566478','1.54',57,14,'HOMBRE',false);

insert into cuotas (owner_id, cuota_type, price, state) values
	(100, 'Joven', 100, 'Pendiente'),
	(101, 'Joven', 100, 'Pendiente'),
	(102, 'Adulto', 200, 'Emitida'),
	(103, 'Jubilado', 150, 'Pendiente');

insert into licencias (owner_id, tutor_name, tutor_surname, tutor_age, tutor_gender, state, price, licence_type, facturation_direction, facturation_info) values 
	(100,'noTutor','noTutor',-1,null, 'PENDIENTE', 30, 'DEPORTISTA', "ESXX-XXXX-XXXX-XXXX-XXXX-XXXX", null),
	(101,'noTutor','noTutor',-1,null,'pagado',30,'DEPORTISTA','Aviles','Segundo A'),
	(101,'noTutor','noTutor',-1,null,'pagado',30,'JUEZ','Aviles','Tercero B'),
	(101,'noTutor','noTutor',-1,null,'pagado',30,'MONITOR','Aviles','Tercero B'),
	(104,'noTutor','noTutor',-1,null,'pagado',30,'JUEZ','Vega','Bajo A'),
	(105,'noTutor','noTutor',-1,null,'pagado',30,'DEPORTISTA','Gijon','Segundo A'),
	(105,'noTutor','noTutor',-1,null,'pagado',30,'JUEZ','Gijon','Segundo A'),
	(106,'Pedro','Garcia',40,'HOMBRE','pagado',30,'DEPORTISTA','Oviedo','Segundo A');
	
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