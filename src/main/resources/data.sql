--Datos para carga inicial de la base de datos

--Para giis.demo.tkrun:
delete from socios;
insert into socios (id,name,cuota_type,iban,height,weight,age,gender,directive) values 
	(100,'Lucia','CUOTA_JOVEN','ES6000138500051234567523','1.72',70,24,'female',true),
	(101,'Juan','CUOTA_ADULTO','ES6000491506251234567713','1.95',105,54,'male',true),
	(102,'Jose','CUOTA_JOVEN','ES6340491500088834566464','1.81',75,24,'male',false),
	(103,'Marcos','CUOTA_JOVEN','ES6000491515051234567692','1.83',78,29,'female',false),
	(104,'Cristian','CUOTA_JUBILADO','ES6000491500063234560069','1.78',77,71,'male',false);
