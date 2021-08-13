package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.easytoolsoft.easyreport.web.WebApplication;

class TestApplication {

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @BeforeEach
  void setUp() throws Exception {}

  @Test
  void test() throws Exception {
    WebApplication.main(new String[] {"--spring.profiles.active=dev"});
    for (;; Thread.sleep(10000));
  }

}
