# Monarch API - Age of STEMpires 🏰

Una API REST completa desarrollada con Spring Boot para gestionar información sobre reinos medievales y sus monarcas. Este proyecto simula un sistema de gestión de reinos históricos con funcionalidades CRUD completas, validaciones de negocio y lógica avanzada.

## 📋 Descripción

**Age of STEMpires** es una aplicación backend que permite gestionar:
- **Monarcas** con dinastías, ordinales romanos y niveles de habilidad
- **Reinos** con recursos, población y validaciones geográficas
- **Dinastías** con nombres históricos y sucesión automática

### 👨‍💻 Autor
**Luis García Díaz** - Proyecto académico de desarrollo de software

## 🏗️ Arquitectura del Proyecto

```
monarch-api/
├── Api/                    # Aplicación Spring Boot
│   ├── src/
│   │   ├── main/java/      # Código fuente principal
│   │   └── test/java/      # Tests unitarios
│   ├── pom.xml            # Dependencias Maven
│   └── README.md          # Documentación específica de la API
├── Diagramas/             # Documentación visual
│   ├── Diagrama_casos_uso.drawio.png
│   ├── Diagrama_clases.drawio.png
│   └── README.md          # Documentación de diagramas
├── BBDD.sql              # Script de base de datos
├── LICENSE               # Licencia del proyecto
└── README.md            # Este archivo
```

## 🚀 Inicio Rápido

### Prerrequisitos

Antes de comenzar, asegúrate de tener instalado:

- ☕ **Java 21** o superior
- 🗄️ **MySQL 8.0+** o superior
- 🔧 **VS Code** con Extension Pack for Java
- 📮 **Postman** (para probar la API)

### 📥 Instalación

1. **Clona el repositorio**
   ```bash
   git clone https://github.com/garcialuis23/monarch-api.git
   cd monarch-api
   ```

2. **Configura la base de datos**
   
   Ejecuta el script SQL en tu servidor MySQL:
   ```bash
   mysql -u root -p < BBDD.sql
   ```
   
   Esto creará:
   - Base de datos `AGE_OF_STEMPIRES`
   - Todas las tablas necesarias
   - Triggers y validaciones
   - Datos de ejemplo

3. **Configura las credenciales de BD**
   
   Edita `Api/src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=tu_usuario_mysql
   spring.datasource.password=tu_contraseña_mysql
   ```

4. **Ejecuta la aplicación**
   
   **Opción A - Desde VS Code (Recomendado):**
   - Abre la carpeta `Api/` en VS Code
   - Presiona `F5` o ve a Run and Debug
   - Selecciona "Run Monarch API"
   
   **Opción B - Desde línea de comandos:**
   ```bash
   cd Api/
   ./mvnw spring-boot:run
   ```

5. **Verifica que funciona**
   
   La aplicación estará disponible en: `http://localhost:8080`
   
   Prueba con Postman:
   ```
   GET http://localhost:8080/monarch
   GET http://localhost:8080/kingdom
   ```

## 🎯 Funcionalidades Principales

### 👑 Gestión de Monarcas
- ✅ CRUD completo (Create, Read, Update, Delete)
- ✅ Validación de nombres por dinastía
- ✅ Generación automática de ordinales romanos
- ✅ Sistema de niveles balanceados (Estrategia + Diplomacia = 5)
- ✅ Generación automática de edad y características
- ✅ Sucesión dinástica automática

### 🏰 Gestión de Reinos
- ✅ CRUD completo con validaciones
- ✅ Cálculo automático de recursos (oro, madera, piedra)
- ✅ Validaciones geográficas y históricas
- ✅ Relación con monarcas activos
- ✅ Control de población basado en superficie

### 📊 Validaciones de Negocio
- ✅ Años de fundación entre 400-1400 DC
- ✅ Nombres de dinastías históricamente correctos
- ✅ Población proporcional a la superficie
- ✅ Integridad referencial entre entidades

## 📚 Documentación de la API

### Endpoints Principales

| Recurso | Método | Endpoint | Descripción |
|---------|--------|----------|-------------|
| Monarcas | GET | `/monarch` | Listar todos los monarcas |
| | GET | `/monarch/{id}` | Obtener monarca específico |
| | POST | `/monarch` | Crear nuevo monarca |
| | PUT | `/monarch` | Actualizar monarca |
| | DELETE | `/monarch` | Eliminar monarca |
| Reinos | GET | `/kingdom` | Listar todos los reinos |
| | GET | `/kingdom/{id}` | Obtener reino específico |
| | POST | `/kingdom` | Crear nuevo reino |
| | PUT | `/kingdom` | Actualizar reino |
| | DELETE | `/kingdom` | Eliminar reino |

### Ejemplos de Uso con Postman

**Crear un nuevo monarca:**
```json
POST http://localhost:8080/monarch
{
    "name": "FERNANDO",
    "dynasty": "TRASTAMARA",
    "shield": "escudo_trastamara.png",
    "description": "Rey de Aragón",
    "strategyLevel": 3,
    "diplomacyLevel": 2
}
```

**Crear un nuevo reino:**
```json
POST http://localhost:8080/kingdom
{
    "kingdomName": "Reino de León",
    "description": "Reino del noroeste peninsular",
    "foundationYear": 910,
    "capitalKingdom": "León",
    "surface": 38000.5,
    "populationKingdom": 150000,
    "flagKingdom": "bandera_leon.png",
    "monarchKingdom": {
        "id": 1
    }
}
```

## 🗄️ Base de Datos

### Esquema Principal
- **DINASTIA** - Familias reales
- **DINASTIA_TIENE_NOMBRE** - Nombres por dinastía
- **MONARCA** - Información de gobernantes
- **REINO** - Datos de territorios
- **MONARCA_REINA_REINO** - Historial de gobierno

### Características Técnicas
- 🔧 Triggers automáticos para validaciones
- 🔒 Restricciones de integridad referencial
- 📊 Validaciones de rangos y formatos
- 🔄 Cascada de eliminaciones controlada

## 🎨 Diagramas y Documentación

En la carpeta `Diagramas/` encontrarás:
- **Diagrama de Casos de Uso** - Funcionalidades del sistema
- **Diagrama de Clases** - Arquitectura y relaciones
- **Documentación detallada** de cada diagrama

## 🛠️ Tecnologías Utilizadas

| Categoría | Tecnología | Versión |
|-----------|------------|---------|
| **Backend** | Java | 21+ |
| | Spring Boot | 3.2.5 |
| | Spring Web | 3.2.5 |
| **Base de Datos** | MySQL | 8.0+ |
| | MySQL Connector/J | Runtime |
| **Build Tool** | Maven | 3.6+ |
| **Testing** | JUnit Jupiter | 5.10.2 |
| | Spring Boot Test | 3.2.5 |

## 🧪 Testing

Para ejecutar las pruebas:

```bash
cd Api/
./mvnw test
```

## 🔧 Configuración Avanzada

### Perfiles de Entorno

Puedes configurar diferentes perfiles editando `application.properties`:

```properties
# Desarrollo
spring.profiles.active=dev
logging.level.com.stem=DEBUG

# Producción
spring.profiles.active=prod
logging.level.com.stem=INFO
```

### Configuración de CORS

La API tiene CORS habilitado para desarrollo. Para producción, ajusta las configuraciones en los controladores.

## 🚧 Próximas Mejoras

- [ ] 🔐 Autenticación y autorización con JWT
- [ ] 📖 Documentación automática con Swagger/OpenAPI
- [ ] 📄 Paginación en endpoints de listado
- [ ] 🔍 Búsqueda y filtrado avanzado
- [ ] 📊 Métricas y monitoreo con Actuator
- [ ] 🐳 Dockerización del proyecto
- [ ] ☁️ Despliegue en la nube

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📞 Soporte

Si encuentras algún problema:

1. Revisa la [documentación de la API](Api/README.md)
2. Verifica que MySQL esté ejecutándose
3. Comprueba las credenciales en `application.properties`
4. Revisa los logs de la aplicación

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

---

### 🎯 Estado del Proyecto

✅ **Funcional** - La API está completamente operativa y lista para uso con Postman

**Desarrollado con ❤️ por Luis García Díaz**