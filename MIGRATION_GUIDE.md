# Migración de MongoDB a MySQL - Sistema Médico

Este documento describe los cambios realizados para migrar el sistema médico de MongoDB a MySQL con JPA Data.

## Cambios Realizados

### 1. Configuración (application.properties)
- ✅ Removida configuración de MongoDB
- ✅ Agregada configuración de MySQL con JPA/Hibernate
- ✅ Configurada creación automática de base de datos

### 2. Dependencias (pom.xml)
- ✅ spring-boot-starter-data-jpa (ya estaba presente)
- ✅ mysql-connector-j (ya estaba presente)
- ℹ️ Puedes remover dependencias de MongoDB si no las usas más

### 3. Modelos (Entidades JPA)

#### ClienteModel
- ✅ Convertido de `@Document` a `@Entity`
- ✅ ID cambiado de String a Long con auto-generación
- ✅ Agregadas anotaciones JPA (@Column, @Table)
- ✅ Agregada relación OneToMany con CitaModel
- ✅ Mantenidos métodos de compatibilidad con nombres antiguos

#### UsuarioModel
- ✅ Convertido de `@Document` a `@Entity`
- ✅ ID cambiado de String a Long con auto-generación
- ✅ Agregadas validaciones y constraints
- ✅ Agregada relación OneToOne con ClienteModel
- ✅ Agregados campos de auditoría (fechaRegistro, activo)

#### CitaModel
- ✅ Convertido de `@Document` a `@Entity`
- ✅ ID cambiado de String a Long con auto-generación
- ✅ Agregada relación ManyToOne con ClienteModel
- ✅ Agregados campos de control (estadoCita, fechaCreacion)
- ✅ Mantenidos métodos de compatibilidad

### 4. Repositorios

#### ClienteRepository
- ✅ Cambiado de MongoRepository a JpaRepository
- ✅ Agregadas consultas personalizadas con @Query
- ✅ Agregados métodos de búsqueda avanzada

#### UsuarioRepository
- ✅ Cambiado de MongoRepository a JpaRepository
- ✅ Agregadas consultas para usuarios activos
- ✅ Agregadas validaciones de existencia

#### CitaRepository
- ✅ Cambiado de MongoRepository a JpaRepository
- ✅ Agregadas consultas por estado, fecha, tipo
- ✅ Agregadas consultas estadísticas

### 5. Servicios

#### ClienteService
- ✅ Agregadas transacciones con @Transactional
- ✅ Agregado manejo de excepciones
- ✅ Agregados métodos CRUD completos
- ✅ Agregadas validaciones de negocio

#### UsuarioService
- ✅ Agregada lógica de registro mejorada
- ✅ Integración automática con ClienteModel
- ✅ Agregados métodos de administración de usuarios

#### CitaService
- ✅ Agregada lógica de vinculación con clientes
- ✅ Agregado control de estados de citas
- ✅ Agregadas estadísticas y reportes

### 6. Controladores
- ✅ Actualizado ClienteController para nueva lógica
- ✅ Agregado manejo de errores mejorado

## Instrucciones de Instalación

### 1. Base de Datos
```sql
CREATE DATABASE sistema_medico_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Configuración
Actualiza `src/main/resources/application.properties` con tus credenciales de MySQL:
```properties
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### 3. Ejecución
```bash
./mvnw spring-boot:run
```

La aplicación creará automáticamente las tablas en la primera ejecución.

## Funcionalidades Mejoradas

### 1. Relaciones entre Entidades
- Los usuarios están vinculados a clientes automáticamente
- Las citas se vinculan a clientes existentes
- Integridad referencial garantizada por la base de datos

### 2. Validaciones Mejoradas
- Validación de datos únicos (identificación, username, email)
- Validaciones de negocio en los servicios
- Manejo de errores más robusto

### 3. Consultas Optimizadas
- Índices en campos importantes
- Consultas personalizadas con JPA
- Paginación disponible (heredada de JpaRepository)

### 4. Transacciones
- Control transaccional en operaciones críticas
- Rollback automático en caso de errores
- Consistencia de datos garantizada

## Compatibilidad

### Métodos Mantenidos
Para evitar romper el código existente, se mantuvieron métodos con los nombres antiguos:
- `getNombre_cliente()` → llama a `getNombreCliente()`
- `getEdad_cliente()` → llama a `getEdadCliente()`
- etc.

### Gradual Migration
Puedes migrar gradualmente:
1. Ejecuta la aplicación con MySQL
2. Migra los datos desde MongoDB (si es necesario)
3. Actualiza el código front-end gradualmente para usar los nuevos nombres

## Próximos Pasos Recomendados

### 1. Seguridad
- Implementar encriptación de contraseñas (BCrypt)
- Agregar Spring Security
- Implementar JWT para autenticación

### 2. Validaciones
- Agregar Bean Validation (@Valid, @NotNull, etc.)
- Validaciones personalizadas
- Mensajes de error internacionalizados

### 3. API REST
- Crear controladores REST (@RestController)
- Documentación con Swagger/OpenAPI
- Versionado de API

### 4. Testing
- Tests unitarios con JUnit 5
- Tests de integración con @DataJpaTest
- Tests de controladores con MockMvc

## Scripts Útiles

### Verificar Datos
```sql
SELECT COUNT(*) as total_clientes FROM clientes;
SELECT COUNT(*) as total_usuarios FROM usuarios;
SELECT COUNT(*) as total_citas FROM citas;
```

### Resetear Base de Datos
```sql
DROP DATABASE IF EXISTS sistema_medico_db;
CREATE DATABASE sistema_medico_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

## Soporte
Si encuentras algún problema durante la migración, revisa:
1. Los logs de la aplicación
2. Las credenciales de base de datos
3. La conectividad a MySQL
4. Las versiones de las dependencias
