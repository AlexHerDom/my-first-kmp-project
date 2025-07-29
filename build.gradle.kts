plugins {
    kotlin("multiplatform") version "1.9.22"
    id("com.android.library") version "8.6.1"  // Updated Android Gradle Plugin
    id("com.android.application") version "8.6.1" apply false  // Updated AGP
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
}

kotlin {
    // Target configuration - define platforms to compile for
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"  // Android compatibility
            }
        }
    }
    
    // iOS targets - supports different architectures
    listOf(
        iosX64(),        // iOS Simulator (Intel Macs)
        iosArm64(),      // iOS Devices  
        iosSimulatorArm64()  // iOS Simulator (Apple Silicon)
    ).forEach {
        it.binaries.framework {
            baseName = "shared"  // Framework name for iOS consumption
            isStatic = true      // Static linking (better performance)
        }
    }

    sourceSets {
        // commonMain - code shared across ALL platforms
        val commonMain by getting {
            dependencies {
                // Coroutines - multiplataform reactive programming
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
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
    compileSdk = 35  // Updated to support latest Compose libraries
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
