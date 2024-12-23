# Prueba Técnica

Este repositorio contiene la implementación de la prueba técnica solicitada. A continuación, se detallan las instrucciones y los requisitos necesarios para comprender y ejecutar el proyecto.

> **Nota**:La parte del BACKEND esta completa pero la parte del Frontend para mostrar lo que hice esta incompleta. Por eso te deje el POSTMAN para que puedas probar todo

## Consigna

La consigna de esta prueba técnica está detallada en el siguiente archivo PDF:

[Consigna de la prueba técnica](consigna.pdf)

**Nota**: Asegúrate de revisar la consigna para comprender los detalles y requisitos del proyecto.

## Diagrama de Relaciones

A continuación, se muestra un diagrama de relaciones que representa las entidades y sus interacciones dentro del proyecto.

![Diagrama de Relaciones](ruta/a/tu/diagrama_relaciones.png)

> **Explicación del diagrama**: Este diagrama muestra cómo las diferentes entidades se relacionan entre sí, permitiendo la estructura adecuada de la base de datos y las interacciones entre los diferentes servicios.

## Postman

Se incluye un archivo de Postman con las colecciones de las API que puedes utilizar para interactuar con el sistema.

### Importar el archivo de Postman

1. Abre Postman.
2. Haz clic en el botón **Importar** en la esquina superior izquierda.
3. Selecciona el archivo `.json` que se encuentra en el directorio raíz de este proyecto, con el nombre `postman_collection.json`.
4. El archivo será cargado en Postman y podrás empezar a hacer solicitudes a las API definidas.

> **Nota**: Asegúrate de que los servicios (por ejemplo, PostgreSQL y pgAdmin) estén ejecutándose antes de hacer las solicitudes. Si usas Docker, puedes iniciar los servicios con el comando `docker-compose up`.

## Obtener la Contraseña de la Aplicación de Gmail

Para poder utilizar servicios que requieren autenticación a través de una cuenta de Gmail (por ejemplo, enviar correos electrónicos desde la aplicación), necesitarás una **contraseña de aplicación**. Sigue los pasos a continuación para obtenerla:

1. Accede a tu cuenta de Gmail.
2. Entra en tu [Google Account](https://myaccount.google.com/).
3. Ve a **Seguridad** en el menú lateral.
4. En la sección **Acceso a Google**, asegúrate de que la **Verificación en dos pasos** esté activada.
5. Después de activar la verificación en dos pasos, ve a **Contraseñas de aplicaciones**.
6. Genera una nueva contraseña de aplicación seleccionando la aplicación y el dispositivo para el que la necesitas (por ejemplo, "Correo" y "Windows").
7. Una vez generada, copia la contraseña y utilízala en tu proyecto.
8. En la raiz del proyecto, tenes que generar un archivo **.env** que contenga lo siguente:

 ```bash
MAIL_USERNAME=tucorreo@gmail.com
MAIL_PASSWORD=contraseña que te dio gmail
MAIL_FROM=tucorreo@gmail.com
```

> **Nota**: La contraseña de aplicación es diferente de tu contraseña normal de Gmail y solo se usa para autenticar aplicaciones externas.


## Configuración del Proyecto

> **Nota**:Si no tenes pgAdmin y PostgreSQL te dejo un docker compose para que puedas desplegar la base de datos 

Este proyecto utiliza **Docker** para gestionar los servicios de PostgreSQL y pgAdmin. Para configurar el entorno de desarrollo, sigue los siguientes pasos:

 Ejecutar Docker Compose
Una vez que tienes el archivo docker-compose.yml, puedes ejecutar los siguientes comandos:

Construir y lanzar los servicios:

bash
Copy code
docker-compose up
Este comando descargará las imágenes necesarias, creará los contenedores y los levantará.

Si necesitas correrlo en segundo plano (modo detached):

bash
Copy code
docker-compose up -d
Para detener los servicios:

bash
Copy code
docker-compose down


## Configurar un Proyecto Angular

 Instalar dependencias con npm install
Dentro del directorio del proyecto, ejecuta el siguiente comando para instalar todas las dependencias necesarias que se encuentran en el archivo package.json:

bash
Copy code
npm install
Este comando descarga todas las dependencias del proyecto, como las bibliotecas de Angular y otras necesarias para el funcionamiento de la aplicación.

Paso 3: Ejecutar el servidor de desarrollo con ng serve
Una vez que las dependencias se hayan instalado correctamente, puedes iniciar el servidor de desarrollo con el siguiente comando:

bash
Copy code
ng serve
Por defecto, el servidor de Angular se ejecuta en el puerto 4200, por lo que podrás acceder a la aplicación a través de http://localhost:4200 en tu navegador.
