plugins {
	id("java")
	id("com.diffplug.spotless") version "6.6.1"
}

group = "com.gastonfournier"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(20))
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("com.google.code.gson:gson:2.10.1")
	implementation("io.vavr:vavr:1.0.0-alpha-4")

	testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.assertj:assertj-core:3.24.2")

}

tasks.test {
	useJUnitPlatform()
}

spotless {
	java {
		target("src/**/*.java")
		googleJavaFormat()
		removeUnusedImports()
		importOrder()
		trimTrailingWhitespace()
		indentWithSpaces()
		endWithNewline()
	}
}