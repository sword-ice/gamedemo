package com.xia.game;

import com.xia.framework.common.SpringContext;
import com.xia.framework.message.MessageFactory;
import com.xia.framework.net.NettyServer;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan(value = "com.xia.game.dao",annotationClass = Mapper.class)
@SpringBootApplication(scanBasePackages = {"com.xia.framework","com.xia.game","com.xia.test"})
@EnableCaching
public class DemoApplication  implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DemoApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}



	@Override
	public void run(String... args) {
		MessageFactory.getInstance().initMessagePool("com.xia.game");
		SpringContext.getBean(NettyServer.class).start();
	}
}