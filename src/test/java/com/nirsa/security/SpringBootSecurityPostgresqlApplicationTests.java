package com.nirsa.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootSecurityPostgresqlApplicationTests {

  private static final Logger logger = LoggerFactory.getLogger(SpringBootSecurityPostgresqlApplicationTests.class);

  @Autowired
  PasswordEncoder encoder;

  @Test
  public void contextLoads() {
    logger.info("\n\n>>> Clave cifrada: " + encoder.encode("1234"));
  }

}