plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "br.edu.ifsp.ddm6banco"
    compileSdk = 37

    defaultConfig {
        applicationId = "br.edu.ifsp.ddm6banco"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    /*
    SUBSTITUI O FINDVIEWBYID COM SEGURANÇA
    QUANDO O VIEW BINDING ESTÁ ATIVADO.
    VOCÊ ACESSA OS COMPONTES DE TELA DIRETAMENTE
    ATRAVÉS DO OBJETO BINDING EM VEZ DE USAR
    findViewById(R.id.meu_botao), POR EXEMPLO
    */
    buildFeatures{
        viewBinding = true
    }
}



dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}