// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    dependencies {

        classpath("com.github.CodingGay:BlackObfuscator-ASPlugin:3.9")
        classpath("com.github.oliver-jonas.unmeta:unmeta:1.0.2")
        classpath("com.github.megatronking.stringfog:xor:5.0.0")
        classpath("com.github.megatronking.stringfog:gradle-plugin:5.1.0")
        classpath("com.google.gms:google-services:4.4.0")

    }
}

plugins {
    id("com.android.application") version "8.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.android.library") version "8.1.3" apply false

}
