plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.emre.filedownloadmanagerconverter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.emre.filedownloadmanagerconverter"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled=true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }


    buildFeatures{
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")




    //word to pdf lib aspose
    implementation("com.google.android.material:material:1.1.0")
    implementation("com.android.support:multidex:2.0.1")
    implementation("com.github.barteksc:android-pdf-viewer:2.8.2")
    implementation(group="com.aspose", name="aspose-words", version="20.6", classifier="android.via.java")
    implementation(group="com.aspose", name="aspose-pdf", version="20.11", classifier="android.via.java")

    //itexpdf lib
    implementation("com.itextpdf:itextpdf:5.5.13.3")
    //apache poi lib
    //implementation("org.apache.poi:poi:5.2.5")
    //implementation("org.apache.poi:poi-ooxml:3.17")
    implementation("org.apache.poi:poi-ooxml:4.0.1")
    implementation("xerces:xercesImpl:2.12.0")


}