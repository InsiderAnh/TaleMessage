# Guía de Instalación con JitPack

## ¿Qué es JitPack?

JitPack es un repositorio de paquetes que construye tus proyectos de GitHub y los hace disponibles como dependencias Maven/Gradle. Es perfecto para proyectos open source y no requiere configuración compleja como Maven Central.

## 🚀 Instalación Rápida

### Para Gradle

1. Agrega el repositorio de JitPack en tu `build.gradle`:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
```

2. Agrega la dependencia:

```gradle
dependencies {
    compileOnly 'com.github.InsiderAnh:TaleMessage:1.0.0'
}
```

3. Sincroniza tu proyecto y ¡listo!

### Para Maven

1. Agrega el repositorio en tu `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

2. Agrega la dependencia:

```xml
<dependencies>
    <dependency>
        <groupId>com.github.InsiderAnh</groupId>
        <artifactId>TaleMessage</artifactId>
        <version>1.0.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

## 📌 Versiones Disponibles

Puedes usar diferentes versiones del proyecto:

- **Release específico**: `1.0.0`
- **Commit específico**: `de050de` (primeros 7 caracteres del commit hash)
- **Rama específica**: `main-SNAPSHOT`
- **Tag específico**: `v1.0.0`

### Ejemplos:

```gradle
// Release específico
compileOnly 'com.github.InsiderAnh:TaleMessage:1.0.0'

// Último commit de la rama main
compileOnly 'com.github.InsiderAnh:TaleMessage:main-SNAPSHOT'

// Commit específico
compileOnly 'com.github.InsiderAnh:TaleMessage:de050de'
```

## 🔍 Verificar el Estado del Build

Puedes ver el estado del build en JitPack:
- URL: https://jitpack.io/#InsiderAnh/TaleMessage
- Badge: [![](https://jitpack.io/v/InsiderAnh/TaleMessage.svg)](https://jitpack.io/#InsiderAnh/TaleMessage)

## ❓ Solución de Problemas

### La dependencia no se descarga

1. Verifica que el repositorio de JitPack esté agregado correctamente
2. Limpia la caché de Gradle: `./gradlew clean --refresh-dependencies`
3. Verifica que la versión existe en: https://jitpack.io/#InsiderAnh/TaleMessage

### Error de compilación

Si obtienes errores de compilación, asegúrate de:
- Usar `compileOnly` (Gradle) o `provided` (Maven) como scope
- Tener el JAR de HytaleServer en tu classpath para compilación

### Actualizar a una nueva versión

Simplemente cambia el número de versión en tu configuración:

```gradle
// De esto:
compileOnly 'com.github.InsiderAnh:TaleMessage:1.0.0'

// A esto:
compileOnly 'com.github.InsiderAnh:TaleMessage:1.1.0'
```

Y sincroniza tu proyecto.

## 📚 Más Información

- [Documentación de JitPack](https://jitpack.io/docs/)
- [Repositorio del Proyecto](https://github.com/InsiderAnh/TaleMessage)
- [Ejemplos de Uso](EXAMPLES.md)

---

**Nota:** JitPack construye el proyecto automáticamente la primera vez que se solicita una versión. Puede tardar unos minutos en estar disponible después de crear un nuevo release.

