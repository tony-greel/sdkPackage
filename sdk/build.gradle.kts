plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("stringfog")
    id("maven-publish")
    id("com.google.gms.google-services")

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
                version = "1.0.3"

            }
        }
    }
}

// 添加 afterEvaluate 块来确保所有配置都已加载
//afterEvaluate {
//    // 配置发布任务
//    publishing {
//        // 配置发布物
//        publications {
//            create<MavenPublication>("release") {
//                // 指定从哪个组件发布
//                from(components.getByName("release"))
//
//                // 设置 groupId, artifactId 和 version
//                groupId = "com.fs.core"
//                artifactId = "sdk"
//                version = "1.0.7"
//
//                // 添加生成的源码包作为构件
//                artifact(tasks.named("generateSourcesJar"))
//            }
//        }
//
//        // 配置存储库，这里指定本地 repo 目录
//        repositories {
//            maven {
//                // 指定本地仓库的路径
//                url = uri("$buildDir/repo")
//            }
//        }
//    }
//}

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    java.toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}


dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.appsflyer:af-android-sdk:6.12.4")
    implementation("com.github.megatronking.stringfog:xor:5.0.0")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-config")
}


