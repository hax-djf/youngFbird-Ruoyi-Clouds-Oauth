package com.flybirds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//排除数据库的加载
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class FlybirdsGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlybirdsGatewayApplication.class, args);

		System.out.println("(♥◠‿◠)ﾉﾞ  flybirds-gateway网关服务启动成功   ლ(´ڡ`ლ)ﾞ");
		System.out.println("" +
				" _____   _____   _   _____    _____        _____       ___   _____   _____   _          __ __    __ \n" +
				"|  ___| |  _  \\ | | |  _  \\  |  _  \\      /  ___|     /   | |_   _| | ____| | |        / / \\ \\  / / \n" +
				"| |__   | |_| | | | | |_| |  | | | |      | |        / /| |   | |   | |__   | |  __   / /   \\ \\/ /  \n" +
				"|  __|  |  _  { | | |  _  /  | | | |      | |  _    / / | |   | |   |  __|  | | /  | / /     \\  /   \n" +
				"| |     | |_| | | | | | \\ \\  | |_| |      | |_| |  / /  | |   | |   | |___  | |/   |/ /      / /    \n" +
				"|_|     |_____/ |_| |_|  \\_\\ |_____/      \\_____/ /_/   |_|   |_|   |_____| |___/|___/      /_/     \n" +
				"v1.0.0.RELEASE");
	}

}
