package com.vmware.retail.analytics;

import nyla.solutions.core.util.Cryption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RetailAnalyticsAppTests {

	@BeforeAll
	static void beforeAll() {
		System.setProperty(Cryption.CRYPTION_KEY_PROP,"JUNIT_UNIT_KEY");
	}

	@Test
	void contextLoads() {
	}

}
