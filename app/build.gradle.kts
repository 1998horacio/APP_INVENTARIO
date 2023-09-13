plugins {
    id("com.android.application")
}

android {
    namespace = "com.achtosoftware.inventario_achto"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.achtosoftware.inventario_achto"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation ("org.projectlombok:lombok:1.18.22")
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar", "*.jar"))))
    annotationProcessor ("org.projectlombok:lombok:1.18.22")
    implementation ("androidx.recyclerview:recyclerview:1.3.1")
    implementation ("androidx.cardview:cardview:1.0.0")

    implementation ("androidx.sqlite:sqlite:2.3.1")
    //noinspection GradleDependency
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.android.car.ui:car-ui-lib:2.5.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}