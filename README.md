***********************************************************************************
Proyecto Fullstack para 3era Evaluación de la asignatura Desarrollo Fullstack II
***********************************************************************************
DUOC UC - 2025
Fernando Zárate - Carla Prado
Hazel Lab

Prerrequisitos
***********************************
-XAMPP instalado
-Java JDK 17+ instalado
-Node.js 16+ instalado
-Visual Studio Code

Instrucciones para ejecutar:
***********************************
Backend
***********************************
Configuración Inicial:

-Abrir proyecto backend en vscode.
backend/hazellabev2

-Iniciar Servicios XAMPP
  *Abrir XAMPP Control Panel
  *Iniciar los servicios: Apache Web Server y MySQL Database

-Verificar Servicios
PHPMyAdmin: http://localhost:8080

-Ejecutar Backend Spring Boot usando Spring Boot Dashboard (Recomendada) en VS Code:
  *Abrir extensión "Spring Boot Dashboard"
  *Localizar proyecto "hazellab-backend"
  *Clic en botón "Start"

-Verificar Backend
  *Swagger UI: http://localhost:8080/swagger-ui/index.html
  *Endpoints API: http://localhost:8080/api/productos

-Poblar base de datos con el archivo import.sql

-Credenciales de prueba
******************************************

***Administrador***

Email: admin@hazellab.cl

Usuario: admin

Contraseña: Admin123*

***Usuario Cliente***

Email: cliente@hazellab.cl

Usuario: cliente

Contraseña: Cliente123*

Estas credenciales permiten realizar pruebas completas en Postman, Swagger y el frontend (login y rutas protegidas).

-Endpoints Principales para pruebas en Postman
GET    http://localhost:8080/api/productos
GET    http://localhost:8080/api/usuarios
GET    http://localhost:8080/api/categorias
GET    http://localhost:8080/api/blogs
POST   http://localhost:8080/api/auth/login


Rutas Principales frontend
http://localhost:5173/                 # Página principal
http://localhost:5173/login           # Login de usuarios
http://localhost:5173/admin           # Panel administrativo
http://localhost:5173/admin/productos # Gestión de productos
http://localhost:5173/admin/clientes  # Gestión de usuarios
