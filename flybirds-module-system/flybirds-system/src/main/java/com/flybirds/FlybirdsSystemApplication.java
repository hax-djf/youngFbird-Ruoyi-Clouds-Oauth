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
public class FlybirdsSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlybirdsSystemApplication.class, args);

		System.out.println("(♥◠‿◠)ﾉﾞ  system服务模块启动成功   ლ(´ڡ`ლ)ﾞ");
		System.out.println("" +
				" _____   _____   _   _____    _____        _____  __    __  _____   _____   _____       ___  ___  \n" +
				"|  ___| |  _  \\ | | |  _  \\  |  _  \\      /  ___/ \\ \\  / / /  ___/ |_   _| | ____|     /   |/   | \n" +
				"| |__   | |_| | | | | |_| |  | | | |      | |___   \\ \\/ /  | |___    | |   | |__      / /|   /| | \n" +
				"|  __|  |  _  { | | |  _  /  | | | |      \\___  \\   \\  /   \\___  \\   | |   |  __|    / / |__/ | | \n" +
				"| |     | |_| | | | | | \\ \\  | |_| |       ___| |   / /     ___| |   | |   | |___   / /       | | \n" +
				"|_|     |_____/ |_| |_|  \\_\\ |_____/      /_____/  /_/     /_____/   |_|   |_____| /_/        |_| " +
				"v1.0.0.RELEASE");
	}

}
