package com.flybirds;

import com.flybirds.security.annotation.EnableflybirdsFeignClients;
import com.flybirds.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableCustomSwagger2
@EnableDiscoveryClient
@EnableflybirdsFeignClients
@SpringCloudApplication
public class FlybirdsOssApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(FlybirdsOssApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  oss服务模块启动成功   ლ(´ڡ`ლ)ﾞ");
        System.out.println(" _____   _____   _   _____    _____        _____   _____   _____  \n" +
                "|  ___| |  _  \\ | | |  _  \\  |  _  \\      /  _  \\ /  ___/ /  ___/ \n" +
                "| |__   | |_| | | | | |_| |  | | | |      | | | | | |___  | |___  \n" +
                "|  __|  |  _  { | | |  _  /  | | | |      | | | | \\___  \\ \\___  \\ \n" +
                "| |     | |_| | | | | | \\ \\  | |_| |      | |_| |  ___| |  ___| | \n" +
                "|_|     |_____/ |_| |_|  \\_\\ |_____/      \\_____/ /_____/ /_____/ (V1.0.0.RELEASE)");
    }
}
