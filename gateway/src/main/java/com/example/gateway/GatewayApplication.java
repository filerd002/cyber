package com.example.gateway;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import com.example.gateway.filters.pre.HTTPFilter;

import java.sql.SQLException;

@EnableZuulProxy
@SpringBootApplication
public class GatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run( GatewayApplication.class, args);
  }

  @Bean
  public HTTPFilter simpleFilter() {
    return new HTTPFilter ();
  }

  @Bean(initMethod = "start", destroyMethod = "stop")
  public Server inMemoryH2DatabaseaServer() throws SQLException {
    return Server.createTcpServer(
            "-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
  }


}
