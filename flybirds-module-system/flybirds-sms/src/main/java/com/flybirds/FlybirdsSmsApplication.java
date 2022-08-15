package com.flybirds;

import com.flybirds.security.annotation.EnableCustomConfig;
import com.flybirds.security.annotation.EnableflybirdsFeignClients;
import com.flybirds.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@EnableCustomConfig
@EnableCustomSwagger2
@EnableflybirdsFeignClients
@SpringCloudApplication
public class FlybirdsSmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlybirdsSmsApplication.class, args);
		System.out.println("(♥◠‿◠)ﾉﾞ  sms服务模块启动成功   ლ(´ڡ`ლ)ﾞ");
		System.out.println(" _____   _____   _   _____    _____        _____       ___  ___   _____  \n" +
							"|  ___| |  _  \\ | | |  _  \\  |  _  \\      /  ___/     /   |/   | /  ___/ \n" +
							"| |__   | |_| | | | | |_| |  | | | |      | |___     / /|   /| | | |___  \n" +
							"|  __|  |  _  { | | |  _  /  | | | |      \\___  \\   / / |__/ | | \\___  \\ \n" +
							"| |     | |_| | | | | | \\ \\  | |_| |       ___| |  / /       | |  ___| | \n" +
							"|_|     |_____/ |_| |_|  \\_\\ |_____/      /_____/ /_/        |_| /_____/ (V1.0.0.RELEASE)");
	}

}
