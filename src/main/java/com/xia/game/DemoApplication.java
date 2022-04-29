package com.xia.game;

import com.xia.framework.common.SpringContext;
import com.xia.framework.message.MessageFactory;
import com.xia.framework.net.NettyServer;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.xia.framework"})
public class DemoApplication  implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DemoApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}



	@Override
	public void run(String... args) {
		MessageFactory.getInstance().initMessagePool("com.xia.game.control");
		SpringContext.getBean(NettyServer.class).start();
	}
}
