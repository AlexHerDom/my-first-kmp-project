<!-- Use this file to provide workspace-specific custom instructions to Copilot. For more details, visit https://code.visualstudio.com/docs/copilot/copilot-customization#_use-a-githubcopilotinstructionsmd-file -->

# Instrucciones para Copilot - Proyecto Kotlin Multiplatform

Este es un proyecto educativo de Kotlin Multiplatform (KMP) para aprender los conceptos básicos.

## Estructura del Proyecto
- `src/commonMain/kotlin/` - Código compartido entre todas las plataformas
- `src/androidMain/kotlin/` - Código específico de Android
- `src/iosMain/kotlin/` - Código específico de iOS
- `src/commonTest/kotlin/` - Tests que se ejecutan en todas las plataformas

## Conceptos Clave de KMP
- **expect/actual**: Patrón para declarar funcionalidad común pero con implementación específica por plataforma
- **sourceSets**: Organización del código por plataforma
- **Framework iOS**: Se genera automáticamente para usar en Xcode

## Reglas de Codificación
- Mantener la lógica de negocio en `commonMain`
- Usar `expect/actual` solo cuando sea necesario acceder a APIs específicas de plataforma
- Crear tests en `commonTest` para asegurar compatibilidad cross-platform
- Usar nombres de paquete consistentes: `com.example.myfirstkmp`

## Para el Aprendizaje
- Explicar cada concepto de KMP cuando se hagan cambios
- Mostrar cómo el código común se comparte entre plataformas
- Demostrar la diferencia entre código común y específico de plataforma
