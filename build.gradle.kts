import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.detekt

import com.github.benmanes.gradle.versions.VersionsPlugin
import com.getkeepsafe.dexcount.DexMethodCountPlugin
import com.getkeepsafe.dexcount.DexMethodCountExtension
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project

/* EXTENSION FUNCTIONS */

fun Project.dexcount(configure: DexMethodCountExtension.() -> Unit) =
    extensions.configure(DexMethodCountExtension::class.java, configure)

/* BUILD SCRIPT */

buildscript {
    repositories {
        google()
        jcenter()
        maven { url = uri(Reps.Url.GRADLE) }
    }
    dependencies {
        classpath(Deps.Plugin.ANDROID)
        classpath(Deps.Plugin.KOTLIN)
        classpath(Deps.Plugin.VERSIONS)
        classpath(Deps.Plugin.DEXCOUNT)
        classpath(Deps.Plugin.DETEKT)
    }
}

/* PROJECTS CONFIGURATION */

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri(Reps.Url.GRADLE) }
    }
}

subprojects {
    plugins.withType(DexMethodCountPlugin::class) {
        dexcount {
            format = Config.Dexcount.FORMAT
            includeClasses = true
            includeClassCount = true
            includeFieldCount = true
            includeTotalMethodCount = true
            orderByMethodCount = false
            verbose = false
            maxTreeDepth = Integer.MAX_VALUE
            teamCityIntegration = false
            teamCitySlug = null
            runOnEachPackage = true
            maxMethodCount = Config.Dexcount.MAX_METHOD_COUNT
        }
    }
    plugins.withType(DetektPlugin::class) {
        detekt {
            toolVersion = Versions.Plugin.DETEKT
            config = files(Config.Detekt.CONFIG_FILE_PATH)
            filters = Config.Detekt.FILTERS
            disableDefaultRuleSets = false
            parallel = true
        }
    }
    plugins.withType(VersionsPlugin::class) {
        apply(Config.Gradle.DEPENDENCY_UPDATES)
    }
}
