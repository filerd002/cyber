package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application {

  @RequestMapping(value = "/get" , method = RequestMethod.GET)
  @ResponseBody
  public String available() {
    return "Dostęp do GET";
  }

  @RequestMapping(value = "/post", method = RequestMethod.POST)
  @ResponseBody
  public String checkedOut() {
    return "Dostęp do POST";
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
