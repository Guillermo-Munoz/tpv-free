# 🛒 TPV Free
Proyecto en **Java 21** para la gestión de un **TPV (Terminal Punto de Venta)** con persistencia en **MySQL** usando **JDBC**.

## 🚀 Funcionalidades
- Gestión de usuarios (CRUD, roles, activo/inactivo).
- Persistencia de datos con MySQL.
- Arquitectura en capas: `model`, `dao`, `service`, `util`.

## 🗄️ Requisitos
- Java 21
- MySQL 8+
- Maven 3+

## ⚙️ Configuración
1. Crear base de datos y tabla `usuarios` en MySQL.
2. Configurar `src/main/resources/database.properties`:
   ```properties
   db.url=jdbc:mysql://localhost:3306/tpv_db
   db.user=root
   db.password=tu_password
   
Compilar:

bash
Copiar código
mvn clean install
Ejecutar tests:

bash
Copiar código
mvn test

