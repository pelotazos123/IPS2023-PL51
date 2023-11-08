[![Build Status](https://github.com/javiertuya/samples-test-dev/actions/workflows/test.yml/badge.svg)](https://github.com/javiertuya/samples-test-dev/actions/workflows/test.yml)
[![Javadoc](https://img.shields.io/badge/%20-javadoc-blue)](https://javiertuya.github.io/samples-test-dev/)

# IPS2023-PL5-01

Bienvenido al repositorio del proyecto IPS2023-PL5-01. Este README proporciona una visión general del proyecto, su estructura y las instrucciones de implementación.

## Tabla de Contenidos
- [Descripción del Proyecto](#descripción-del-proyecto)
- [Miembros](#miembros)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Despliegue](#despliegue)
- [Contribuciones](#contribuciones)


## Descripción del Proyecto
Proporcione una breve introducción al proyecto IPS2023-PL03-ING-01, describiendo sus objetivos, propósito y cualquier otra información relevante.

## Miembros
Este proyecto es mantenido por los siguientes miembros del equipo:
- [Pablo Calvo Gamonal - UO276220](https://github.com/pelotazos123)
- [Luis Miguel Gómez del Cueto - UO277310](https://github.com/uo277310)
- [Yago Fernandez Lopez - UO289549](https://github.com/uo289549)
- [Gabriel García Martínez - UO289097](https://github.com/uo289097)

No dude en ponerse en contacto con cualquiera de los miembros del equipo si tiene preguntas o desea contribuir al proyecto.

## Estructura del Proyecto
El proyecto está estructurado de la siguiente manera:

1. `/algundirectorio`: Descripción del directorio y su contenido.
2. `/algundirectorio`: Descripción del directorio y su contenido.
3. `/algundirectorio`: Descripción del directorio y su contenido.

Puede explorar cada directorio para obtener más información sobre los componentes y características del proyecto.

## Despliegue
Para implementar el proyecto, siga los siguientes pasos:

1. Paso 1: Describa el primer paso de implementación, incluyendo cualquier requisito previo o configuración necesaria.
2. Paso 2: Proporcione instrucciones para el segundo paso de implementación.
3. Paso 3: Continúe con pasos adicionales de implementación si es necesario.

Asegúrese de tener las dependencias y configuraciones necesarias en su lugar antes de implementar el proyecto.

## Contribuciones
Agradecemos las contribuciones de la comunidad. Si desea contribuir a este proyecto, siga estos pasos:
1. Haga un "fork" del repositorio.
2. Cree una nueva rama para su nueva característica o corrección de errores.
3. Realice sus cambios y haga "commit" de los mismos.
4. Suba sus cambios a su rama.
5. Cree una solicitud de extracción para fusionar sus cambios en el proyecto principal.

Apreciamos sus contribuciones y revisaremos sus solicitudes de extracción de manera oportuna.

#README Original



# IPS2023-PL51

Este proyecto es utilizado como proyecto base o plantilla para el desarrollo y a modo de ejemplo para ilustrar algunos aspectos del desarrollo y automatización de pruebas para las asignaturas relacionadas con ingenieria del software, sistemas de información y pruebas de software.

[Descargar la última versión](https://github.com/javiertuya/samples-test-dev/releases) - 
[Ver más detalles en el javadoc](https://javiertuya.github.io/samples-test-dev/)

## Contenido

Permite ilustrar, entre otros:
- Repaso del uso de JDBC para acceder a bases de datos
- Un conjunto de utilidades para simplificar el acceso a base de datos y el uso de tablas en Swing
- Implementación de MVC con Swing
- Automatización de pruebas unitarias con varias versiones de JUnit
- Estructura y configuración de un proyecto Maven y diferentes reports

Contiene los siguientes paquetes principales:
- `giis.demo.jdbc`: Repaso de acceso a base de datos con jdbc
- `giis.demo.tkrun`: Ilustra estructura de proyecto MVC con Swing (TicketRun)
- `giis.demo.tkrun.ut`: Ilustra pruebas con JUnit para TicketRun
- `giis.demo.util`: Diferentes utilidades de uso por parte de los anteriores

La estructura es la estándar de maven:
- `src/main/java`: Codigo fuente de aplicación
- `src/test/java`: Pruebas unitarias
- `target`: Generado con el codigo objeto y reports

## Requisitos e Instalación

Este proyecto requiere un mínimo de Java 8 JDK.

Preparación del proyecto:
- Si se va a utilizar solamente para pruebas y experimentación, clonar/descargar el zip
  (opción `<> Code` en esta página) o desde [Releases](https://github.com/javiertuya/samples-test-dev/releases)
- Si se va a utilizar como plantilla para un proyecto propio en GitHub,
  Crear el repositorio directamente en Git usando este proyecto como plantilla
  (opción `Use this template` en esta página)
- En este segundo caso es imprescindible cambiar `samples-test-dev` por el nombre del nuevo proyecto
  (se puede hacer desde el propio repositorio creado) en los ficheros:
  - `.project`: cambiar `<name>samples-test-dev</name>` para incluir el nombre del proyecto
  - `pom.xml`: cambiar `<artifactId>samples-test-dev</artifactId>` para incluir el nombre del proyecto

## Ejecución del proyecto:

- Desde línea de comandos con [Apache Maven](https://maven.apache.org/download.cgi):
  - Asegurarse de que JAVA_HOME apunta a un JDK y no JRE
  - Ejecución completa: `mvn install`, incluye generación del Javadoc
  - Solo pruebas unitarias: `mvn test`, todas las pruebas: `mvn verify`
  - Ejecución sin tests: `mvn install -DskipTests=true`, genera todos los jar incluyendo javadoc

- Desde Eclipse con M2Eclipse instalado (las distribuciones recientes ya lo incluyen).
  Desde la raiz del proyecto:
  - Asegurarse de que esta configurado JDK: Desde build path, editar JRE System Library y en Environment
	comprobar que JavaSE-1.8 apunta a un JDK en vez de un JRE
  - *Maven->Update Project*: Actualiza todas las dependencias y permite usar el proyecto como 
    si hubiera sido creado desde el propio Eclipse
  - *Run As->Maven install*: Ejecuta este (o otros) comandos maven desde Eclipse
  - Ejecutar los tests en `src/main/test` o el programa principal (aplicación swing)
    en la clase `giis.demo.util.SwingMain`

## Reports

La instalacion anterior compilará, ejecutará pruebas y dispondrá de los reports en carpetas dentro de `target`:
- `site/testapidocs/index.html`: javadoc del proyecto
- `site/surefire-report.html`: report de las pruebas unitarias (ut)
- `site/junit*`: report de todas las pruebas con el formato que genera junit
- `site/jacoco-ut`: reports de cobertura de código

## Personalización de GitHub Actions y Dependabot

Este proyecto está configurado con los correspondientes scripts de Dependabot (para actualización de versiones de dependencias)
y GitHub actions (para realizar acciones automáticas cuando se realiza un pull request hacia main o un push de una rama).
A continuación se describen y se indican las posibles personalizaciones a realizar:

- `.github/workflows/test.yml`: Ejecuta automáticamente un build y todas las pruebas unitarias.
  Aunque se puede eliminar o desactivar. Si se mantiene,
  en el caso de que no se tengan pruebas unitarias, modificarlo para que compile la aplicación de la siguiente forma:
  - cambiar `verify` por `compile` en la acción `run: mvn verify ...`
  - eliminar el código a partir de `- name: Publish surefire test report`
- `.github/workflows/pages.yml`: Exporta el javadoc de la aplicación a GitHub pages, e indiará fallo
  si no se ha configurado el repositorio para ello, por lo que se puede eliminar.
- `.github/dependabot.yml`: Permite que Dependabot cree una pull request cuando hay alguna dependencia
  que precisa actualización. Se recomienda mantenerlo.
