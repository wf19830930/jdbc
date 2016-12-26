package com.Spring;

import org.springframework.context.annotation.*;
@Configuration
public class HelloWorldConfig {

   @Bean(name="wangfeng") 
   public HelloWorld helloWorld(){
      return new HelloWorld();
   }
}