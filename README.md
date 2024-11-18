<h1>Trabajo Práctico Especial - 1ra Entrega</h1>

<h2>3. Implementar los siguientes servicios/reportes:</h2>

### a. Como encargado de mantenimiento quiero poder generar un reporte de uso de monopatines por kilómetros para establecer si un monopatín requiere de mantenimiento. Este reporte debe poder configurarse para incluir (o no) los tiempos de pausa.
--> Implementado en microservicio_monopatin

### b. Como administrador quiero poder anular cuentas para inhabilitar el uso momentáneo de la misma.
--> Implementado en microservicio_administrador CuentaController

### c. Como administrador quiero consultar los monopatines con más de X viajes en un cierto año.
--> Implementado en microservicio_administrador ViajeController y microservicio_viaje ViajeService

### d. Como administrador quiero consultar el total facturado en un rango de meses de cierto año.
--> Implementado en microservicio_administrador y en microservicio_viaje

### e. Como administrador quiero consultar la cantidad de monopatines actualmente en operación, versus la cantidad de monopatines actualmente en mantenimiento.
--> Implementado en microservicio_administrador MonopatinController

### f. Como administrador quiero hacer un ajuste de precios, y que a partir de cierta fecha el sistema habilite los nuevos precios.
--> Implementado en microservicio_administrador TarifaService

### g. Como usuario quiero un listado de los monopatines cercanos a mi zona, para poder encontrar un monopatín cerca de mi ubicación.
--> Implementado en microservicio_parada ParadaController

<h1>Trabajo Práctico Especial - 2da Entrega</h1>

<h2>6. Incorporar tests de unidad e integración (Junit o Mockito). Documentar los endpoints REST con
Swagger (OpenAPI).</h2>

### **Paso 1**: Levantar las bases de datos
Correr Docker/docker-compose.yml

### **Paso 2**: Correr la aplicación del microservicio
Navegar al directorio del microservicio y correr la aplicación principal:
src/main/java/com.example.microservicio_name/MicroservicioNameApplication.java

### **Paso 3**: Acceder a Swagger UI en el navegador
Abrir el navegador y pegar la siguiente URL:
http://localhost:<puerto>/swagger-ui/index.html

#### _Posibles puertos_:
- **microservicio-administrador**: 8088
- **microservicio-cuenta**: 8082
- **microservicio-mantenimiento**: 8083
- **microservicio-monopatin**: 8084
- **microservicio-parada**: 8085
- **microservicio-usuario**: 8086
- **microservicio-viaje**: 8087
