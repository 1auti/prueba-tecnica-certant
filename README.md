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

> **Nota**: Si no tienes pgAdmin y PostgreSQL, te dejo un archivo `docker-compose.yml` para que puedas desplegar la base de datos de manera sencilla.

Este proyecto utiliza **Docker** para gestionar los servicios de PostgreSQL y pgAdmin. Para configurar el entorno de desarrollo, sigue estos pasos:

### 1. Ejecutar Docker Compose

Si tienes el archivo `docker-compose.yml`, sigue estos pasos para levantar los servicios de PostgreSQL y pgAdmin:

#### Construir y lanzar los servicios

Ejecuta el siguiente comando para descargar las imágenes necesarias, crear los contenedores y levantarlos:

```bash
docker-compose up
```

#### Ejecutar en segundo plano (modo **detached**)

Si prefieres ejecutar los servicios en segundo plano, usa el siguiente comando:

```bash
docker-compose up -d
```

#### Detener los servicios

Para detener los servicios y eliminar los contenedores, usa el siguiente comando:

```bash
docker-compose down
```

---

## Configurar un Proyecto Angular

Sigue estos pasos para configurar tu proyecto Angular:

### 1. Instalar dependencias con `npm install`

Dentro del directorio del proyecto, ejecuta el siguiente comando para instalar todas las dependencias que están definidas en el archivo `package.json`:

```bash
npm install
```

Este comando descargará las bibliotecas y paquetes necesarios para que tu aplicación Angular funcione correctamente.

### 2. Ejecutar el servidor de desarrollo con `ng serve`

Una vez que las dependencias estén instaladas correctamente, puedes iniciar el servidor de desarrollo con el siguiente comando:

```bash
ng serve
```

Por defecto, el servidor de Angular se ejecuta en el puerto `4200`, por lo que podrás acceder a la aplicación desde tu navegador en la siguiente URL:

```
http://localhost:4200
```

Si deseas cambiar el puerto en el que se ejecuta el servidor (por ejemplo, a `8080`), puedes usar el siguiente comando:

```bash
ng serve --port 8080
```

---

Con estos pasos podrás tener tu entorno de desarrollo listo para trabajar tanto con la base de datos PostgreSQL y pgAdmin usando Docker, como con tu proyecto Angular ejecutándose en el servidor de desarrollo.
```

