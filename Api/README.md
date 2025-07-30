# Monarch API - Age of STEMpires

Una API REST desarrollada con Spring Boot para gestionar información sobre reinos medievales y sus monarcas. Este proyecto simula un sistema de gestión de reinos históricos con funcionalidades completas de CRUD.

## 🏰 Descripción del Proyecto

**Age of STEMpires** es una aplicación que permite gestionar:

- **Monarcas**: Información detallada sobre los gobernantes
- **Reinos**: Datos sobre territorios, recursos y población
- **Dinastías**: Linajes y nombres asociados a cada familia real

### Autor

Luis García Díaz

## 🛠️ Tecnologías Utilizadas

- **Java 21** - Lenguaje de programación principal
- **Spring Boot 3.2.5** - Framework web
- **Spring Web** - Para crear endpoints REST
- **MySQL Connector/J** - Conexión a base de datos MySQL
- **Maven** - Gestión de dependencias y construcción del proyecto

## 📁 Estructura del Proyecto

```text
src/
├── main/
│   ├── java/com/stem/MonarcaAPl/
│   │   ├── MonarcaAPlApplication.java          # Clase principal
│   │   ├── controller/
│   │   │   ├── KingdomController.java          # Controlador de reinos
│   │   │   └── MonarchController.java          # Controlador de monarcas
│   │   └── model/
│   │       ├── Kingdom.java                    # Entidad Reino
│   │       ├── KingdomModel.java              # Modelo de datos Reino
│   │       ├── Monarch.java                   # Entidad Monarca
│   │       ├── MonarchModel.java              # Modelo de datos Monarca
│   │       └── MyConnection.java              # Conexión a BD
│   └── resources/
│       └── application.properties             # Configuración
└── test/
    └── java/com/stem/MonarcaAPl/
        └── MonarcaAPlApplicationTests.java    # Tests
```

## 🗄️ Base de Datos

La aplicación utiliza MySQL con las siguientes entidades principales:

### Tablas

- **DINASTIA**: Información sobre las familias reales
- **DINASTIA_TIENE_NOMBRE**: Nombres asociados a cada dinastía
- **MONARCA**: Datos detallados de los gobernantes
- **REINO**: Información sobre los territorios
- **MONARCA_REINA_REINO**: Relación histórica entre monarcas y reinos

### Características de la BD

- Triggers para validaciones automáticas
- Restricciones de integridad referencial
- Validaciones de rangos (años 400-1400, niveles 1-10, etc.)

## 🚀 Instalación y Configuración

### Prerrequisitos

- Java 21 o superior
- Maven 3.6+
- MySQL 8.0+

### Pasos de instalación

1. **Clonar el repositorio**

   ```bash
   git clone <repository-url>
   cd monarch-api/Api
   ```

2. **Configurar la base de datos**
   - Ejecutar el script `BBDD.sql` en MySQL
   - Configurar la conexión en `application.properties`

3. **Compilar el proyecto**

   ```bash
   mvnw clean compile
   ```

4. **Ejecutar la aplicación**

   ```bash
   mvnw spring-boot:run
   ```

La aplicación estará disponible en `http://localhost:8080`

## 📚 API Endpoints

### Monarcas (`/monarch`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/monarch` | Obtener todos los monarcas |
| GET | `/monarch/{id}` | Obtener monarca por ID |
| POST | `/monarch` | Crear nuevo monarca |
| PUT | `/monarch` | Actualizar monarca existente |
| DELETE | `/monarch` | Eliminar monarca |

### Reinos (`/kingdom`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/kingdom` | Obtener todos los reinos |
| GET | `/kingdom/{id}` | Obtener reino por ID |
| POST | `/kingdom` | Crear nuevo reino |
| PUT | `/kingdom` | Actualizar reino existente |
| DELETE | `/kingdom` | Eliminar reino |

## 🎯 Características Principales

### Gestión de Monarcas

- **Validación de nombres**: Solo nombres válidos para cada dinastía
- **Generación automática de ordinales**: Convierte números a romanos (I, II, III...)
- **Niveles balanceados**: Estrategia + Diplomacia = 5 puntos máximo
- **Generación automática de edad**: Entre valores realistas
- **Sucesión dinástica**: Creación automática de herederos

### Gestión de Reinos

- **Validaciones geográficas**: Superficie, población, año de fundación
- **Cálculo automático de recursos**: Madera, piedra y oro según área
- **Validación de capitales**: Nombres apropiados para la época
- **Relación con monarcas**: Cada reino tiene un gobernante activo

### Lógica de Negocio

- **Recursos automáticos**: Cálculo basado en superficie del territorio
- **Validaciones históricas**: Años entre 400-1400 DC
- **Integridad referencial**: Consistencia entre monarcas y reinos
- **Sucesión automática**: Generación de nuevos monarcas cuando es necesario

## 🔧 Configuración

### Base de Datos

Configurar en `application.properties`:

```properties
spring.application.name=MonarcaAPl
spring.datasource.url=jdbc:mysql://localhost:3306/AGE_OF_STEMPIRES
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### CORS

Todos los endpoints tienen habilitado CORS para desarrollo frontend.

## 🧪 Testing

Ejecutar las pruebas:

```bash
mvnw test
```

## 📝 Validaciones Implementadas

### Monarcas

- Nombres deben existir en la dinastía correspondiente
- Niveles de estrategia y diplomacia entre 1-4
- Suma de niveles no debe exceder 5
- Edad mínima de 14 años

### Reinos

- Año de fundación entre 400-1400 DC
- Superficie mayor a 0
- Población entre 1 y 10 veces la superficie
- Nombres de capital con formato válido
- Monarca debe existir en la base de datos

## 🔄 Funcionalidades Avanzadas

- **Generación automática de ordinales**: Convierte números a romanos
- **Cálculo de recursos**: Algoritmos para determinar riqueza del reino
- **Validación de dinastías**: Verifica nombres históricos apropiados
- **Gestión de sucesión**: Creación automática de herederos dinásticos

## 🐛 Manejo de Errores

La API maneja:

- Validaciones de entrada con mensajes descriptivos
- Errores de base de datos con rollback automático
- Verificación de integridad referencial
- Respuestas HTTP apropiadas (200, 400, 404, 500)

## 📋 Próximas Mejoras

- [ ] Autenticación y autorización
- [ ] Documentación con Swagger/OpenAPI
- [ ] Paginación en listados
- [ ] Búsqueda y filtrado avanzado
- [ ] Logging estructurado
- [ ] Métricas y monitoreo
- [ ] Dockerización del proyecto

## 🤝 Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear un Pull Request

## 📄 Licencia

Este proyecto es parte de un trabajo académico desarrollado por Luis García Díaz.

---

**Nota**: Para más información sobre los diagramas UML y arquitectura del sistema, consulta la carpeta `Diagramas/` en el directorio raíz del proyecto.