package com.Spring;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.*;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) throws IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"bean.xml");
		HelloWorld obj = (HelloWorld) context.getBean("helloBean");
		obj.printHello();

		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				HelloWorldConfig.class);
		HelloWorld helloWorld = ctx.getBean(HelloWorld.class);
		helloWorld.setName("wangfeng");
		helloWorld.printHello();
		
		StringBuffer a = new StringBuffer("A");     
		StringBuffer b = new StringBuffer("B"); 
		App app=new App();
		app.operator(a, b);      
		System.out.println(a + "," + b);
		
		String aa="1";
		String bb="1";
		String cc=new String("1");
		System.out.println(aa.hashCode() + "," + bb.hashCode());
		System.out.println(aa.equals(bb));
		System.out.println(aa==bb);
		System.out.println("----------------------------");
		System.out.println(aa.hashCode() + "," + cc.hashCode());
		System.out.println(aa.equals(cc));
		System.out.println(aa==cc);
		
	}
	public  void operator(StringBuffer x,StringBuffer y){
		x.append(y);
		y=x;
		
	}
}