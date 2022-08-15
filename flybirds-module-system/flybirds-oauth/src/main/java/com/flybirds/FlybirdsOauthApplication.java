package com.flybirds;

import com.flybirds.security.annotation.EnableCustomConfig;
import com.flybirds.security.annotation.EnableflybirdsFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
/**
 * 认证授权服务管理
 *
 * @author flybirds
 */
@EnableCustomConfig
@EnableflybirdsFeignClients
@SpringCloudApplication
public class FlybirdsOauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlybirdsOauthApplication.class, args);

		System.out.println("(♥◠‿◠)ﾉﾞ  flybirds-oauth认证服务启动成功   ლ(´ڡ`ლ)ﾞ");
		System.out.println("" +
				" _____   _____   _   _____    _____        _____       ___   _   _   _____   _   _  \n" +
				"|  ___| |  _  \\ | | |  _  \\  |  _  \\      /  _  \\     /   | | | | | |_   _| | | | | \n" +
				"| |__   | |_| | | | | |_| |  | | | |      | | | |    / /| | | | | |   | |   | |_| | \n" +
				"|  __|  |  _  { | | |  _  /  | | | |      | | | |   / / | | | | | |   | |   |  _  | \n" +
				"| |     | |_| | | | | | \\ \\  | |_| |      | |_| |  / /  | | | |_| |   | |   | | | | \n" +
				"|_|     |_____/ |_| |_|  \\_\\ |_____/      \\_____/ /_/   |_| \\_____/   |_|   |_| |_| " +
				"v1.0.0.RELEASE");
	}
}
