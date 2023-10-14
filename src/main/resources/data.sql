--Datos para carga inicial de la base de datos

--Para giis.demo.tkrun:
delete from socios;
insert into socios (id,name,surname,cuota_type,iban,height,weight,age,gender,directive) values 
	(100,'Lucia','Suarez','CUOTA_JOVEN','ES6000138500051234567523','1.72',70,24,'MUJER',true),
	(101,'Juan','Garcia','CUOTA_ADULTO','ES6000491506251234567713','1.95',105,54,'HOMBRE',true),
	(102,'Jose','Alonso','CUOTA_JOVEN','ES6340491500088834566464','1.81',75,24,'HOMBRE',false),
	(103,'Paula','Perez','CUOTA_JOVEN','ES6000491515051234567692','1.83',78,16,'MUJER',false),
	(104,'Cristian','Gonzalez','CUOTA_JUBILADO','ES6000491500063234560069','1.78',77,71,'HOMBRE',false),
	(105,'Pedro','Fernandez','CUOTA_JOVEN','ES6000436501851414507715','1.75',80,23,'HOMBRE',false);
	
