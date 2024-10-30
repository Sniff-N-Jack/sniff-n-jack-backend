package com.soen342.sniffnjack;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {
	static final MySQLContainer MY_SQL_CONTAINER;

	static {
		MY_SQL_CONTAINER = new MySQLContainer<>("mysql:latest");
		MY_SQL_CONTAINER.start();
	}

	@DynamicPropertySource
	static void setDatasourceProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
		registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
		registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
	}

	//region Activities
	public static final String yoga = "Yoga";
	public static final String swimming = "Swimming";
	public static final String league = "League of Legends";
	public static final String soccer = "Soccer";
	public static final String writing = "Writing";
	public static final String cooking = "Cooking";
	public static final String[] activities = {yoga, swimming, league, soccer, writing, cooking};
	//endregion

	//region Cities
	public static final String montreal = "Montreal";
	public static final String brossard = "Brossard";
	public static final String laval = "Laval";
	public static final String troisRivieres = "Trois-Rivières";
	public static final String louiseville = "Louiseville";
	public static final String shawinigan = "Shawinigan";
	public static final String quebec = "Quebec";
	public static final String levis = "Levis";
	public static final String donnacona = "Donnacona";
	public static final String[] cities = {montreal, brossard, laval, troisRivieres, louiseville, shawinigan, quebec, levis, donnacona};
	//endregion

	//region Users
	public static final String adminFirstName = "Admin-First";
	public static final String adminLastName = "Admin-Last";
	public static final String adminEmail = "admin@test.com";
	public static final String adminPassword = "admin123";
	
	public static final String instructorFirstName = "Instructor-First";
	public static final String instructorLastName = "Instructor-Last";
	public static final String instructorEmail = "instructor@test.com";
	public static final String instructorPassword = "instructor123";
	public static final String instructorPhone = "111-111-1111";
	
	public static final String childFirstName = "Child-First";
	public static final String childLastName = "Child-Last";
	public static final String childEmail = "child@test.com";
	public static final String childPassword = "child123";
	public static final String childPhone = "222-222-2222";
	
	public static final String parentFirstName = "Parent-First";
	public static final String parentLastName = "Parent-Last";
	public static final String parentEmail = "parent@test.com";
	public static final String parentPassword = "parent123";
	public static final String parentPhone = "333-333-3333";
	//endregion
}
