plugins {
    idea
}

repositories {
    maven("https://dl.bintray.com/arrow-kt/arrow-kt") {
        content {
            includeGroup("io.arrow-kt")
        }
    }
    maven("https://oss.jfrog.org/artifactory/oss-snapshot-local") {
        content {
            includeGroup("io.arrow-kt")
        }
    }
}

idea.module {
    val variants = arrayOf("/main"/*, "/debug", "/release"*/)
    val paths =
            variants.map { "$buildDir/generated/source/kaptKotlin$it" } +
            variants.map { "$buildDir/generated/source/kapt$it" } +
            variants.map { "$projectDir/src/generated/kotlin$it" } +
            variants.map { "$projectDir/src/generated/java$it" } +
            "$buildDir/tmp/kapt/main/kotlinGenerated" +
            "$projectDir/src/generated/kotlin" +
            "$projectDir/src/generated/java" +
            "$projectDir/generated"

    files(paths) {
        files.also(::setSourceDirs)
             .also(::setGeneratedSourceDirs)
    }
}
