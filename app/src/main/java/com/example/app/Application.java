package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
public class Application {

  @GetMapping("/get")
  public String get() {
    return "Dostęp do GET";
  }

  @PostMapping("/post")
  public String post(@RequestBody String param) {
    return "Dostęp do POST" + param;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
