package com.xia.game;

import com.xia.game.common.SpringContext;
import com.xia.game.message.MessageFactory;
import com.xia.game.net.NettyServer;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.xia.game"})
public class DemoApplication  implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DemoApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}



	@Override
	public void run(String... args) {
		MessageFactory.getInstance().initMessagePool("com.xia.game.message");
		SpringContext.getBean(NettyServer.class).start();
	}
}
