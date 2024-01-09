plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("stringfog")
    id("maven-publish")
}

tasks.register("generateSourcesJar", Jar::class) {
    from(android.sourceSets.getByName("main").java.srcDirs) {
        include("**/*.java")
    }
    archiveClassifier.set("sources")
}


afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components.getByName("release"))

                // 设置 groupId, artifactId 和 version
                groupId = "com.fs.core"
                artifactId = "sdk"
                version = "1.0.1"

            }
        }
    }
}

apply(plugin = "stringfog")
configure<com.github.megatronking.stringfog.plugin.StringFogExtension> {
    // 必要：加解密库的实现类路径，需和上面配置的加解密算法库一致。
    implementation = "com.github.megatronking.stringfog.xor.StringFogImpl"
    enable = true
    kg = com.github.megatronking.stringfog.plugin.kg.RandomKeyGenerator()
    mode = com.github.megatronking.stringfog.plugin.StringFogMode.bytes
}


android {
    namespace = "com.fs.sdk"
    compileSdk = 33

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.appsflyer:af-android-sdk:6.12.4")
    implementation("com.github.megatronking.stringfog:xor:5.0.0")
}


