dependencies {
	// spring boot
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
//	implementation("org.springframework.boot:spring-boot-starter-data-redis")

	// aws
	implementation("io.awspring.cloud:spring-cloud-aws-starter-parameter-store:3.3.0")
	implementation("software.amazon.awssdk:s3:2.31.11")

	// swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")

	implementation(project(":core"))
}
