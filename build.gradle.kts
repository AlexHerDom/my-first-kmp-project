plugins {
    kotlin("multiplatform") version "1.9.22"
    id("com.android.library") version "8.2.2"
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
}

kotlin {
    // Configuración para Android
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    // Configuración para iOS
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        // Código común - funciona en todas las plataformas
        val commonMain by getting {
            dependencies {
                // Aquí van las dependencias comunes
            }
        }
        
        // Tests para código común
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        
        // Código específico de Android
        val androidMain by getting {
            dependencies {
                // Dependencias específicas de Android
            }
        }
        
        // Código específico de iOS
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    namespace = "com.example.myfirstkmp"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
