# Mi Primer Proyecto Kotlin Multiplatform 🚀

¡Bienvenido a tu primer proyecto KMP! Este es un proyecto de aprendizaje que demuestra los conceptos básicos de Kotlin Multiplatform.

## ¿Qué es Kotlin Multiplatform?

Kotlin Multiplatform permite escribir código una vez y usarlo en múltiples plataformas:
- 📱 **Android** - Apps nativas de Android
- 🍎 **iOS** - Apps nativas de iOS  
- 🌐 **Web** - Aplicaciones web
- 💻 **Desktop** - Aplicaciones de escritorio

## Estructura del Proyecto

```
src/
├── commonMain/kotlin/          # 🎯 Código compartido
│   ├── Greeting.kt            # Lógica de negocio
│   └── Platform.kt            # Interfaz común
├── androidMain/kotlin/         # 🤖 Código específico Android
│   └── Platform.android.kt    # Implementación Android
├── iosMain/kotlin/            # 🍎 Código específico iOS
│   └── Platform.ios.kt        # Implementación iOS
└── commonTest/kotlin/         # ✅ Tests multiplataforma
    └── GreetingTest.kt        # Tests del código compartido
```

## Conceptos Clave Aprendidos

### 1. Código Compartido (commonMain)
- ✨ La lógica de negocio va aquí
- 🔄 Se usa en todas las plataformas
- 📝 Ejemplo: `Greeting.kt`

### 2. expect/actual
- 🤔 `expect`: "Espero que cada plataforma implemente esto"
- ✅ `actual`: "Esta es la implementación real para esta plataforma"
- 📱 Ejemplo: `getPlatform()` tiene diferentes implementaciones

### 3. Source Sets
- 📁 Organización del código por plataforma
- 🎯 `commonMain`: Código compartido
- 🤖 `androidMain`: Solo Android
- 🍎 `iosMain`: Solo iOS

## Comandos Útiles

### Compilar el proyecto
\`\`\`bash
./gradlew build
\`\`\`

### Ejecutar tests
\`\`\`bash
./gradlew test
\`\`\`

### Para Android
\`\`\`bash
./gradlew :androidUnitTest
\`\`\`

### Para iOS (requiere macOS)
\`\`\`bash
./gradlew iosTest
\`\`\`

## Cómo Funciona

1. **Escribes código común** en `commonMain`
2. **Defines interfaces** con `expect` para funcionalidad específica
3. **Implementas** con `actual` en cada plataforma
4. **Gradle se encarga** de compilar para cada plataforma

## Próximos Pasos para Aprender

1. 🔧 **Modifica** `Greeting.kt` y ve cómo afecta ambas plataformas
2. 🆕 **Agrega** nuevas funciones al código común
3. 📱 **Crea** una app Android que use este código
4. 🍎 **Crea** una app iOS que use este código
5. 🧪 **Escribe** más tests para practicar

## Archivos Importantes

- `build.gradle.kts` - Configuración del proyecto
- `settings.gradle.kts` - Configuración de módulos
- `gradle.properties` - Propiedades de Gradle

¡Felicidades! 🎉 Has creado tu primer proyecto Kotlin Multiplatform. 

**Recuerda**: La belleza de KMP es que escribes la lógica una vez y funciona en todas partes.
