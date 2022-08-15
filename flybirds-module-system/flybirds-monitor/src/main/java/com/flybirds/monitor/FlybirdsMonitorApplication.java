package com.flybirds.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 监控中心
 * 
 * @author flybirds
 */
@EnableAdminServer
@SpringCloudApplication
public class FlybirdsMonitorApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(FlybirdsMonitorApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  flybirds-monitor监控服务启动成功   ლ(´ڡ`ლ)ﾞ");
        System.out.println(
            "  _____   _____   _   _____    _____            ___  ___   _____   __   _   _   _____   _____   _____   \n" +
            " |  ___| |  _  \\ | | |  _  \\  |  _  \\          /   |/   | /  _  \\ |  \\ | | | | |_   _| /  _  \\ |  _  \\  \n" +
            " | |__   | |_| | | | | |_| |  | | | |         / /|   /| | | | | | |   \\| | | |   | |   | | | | | |_| |  \n" +
            " |  __|  |  _  { | | |  _  /  | | | |        / / |__/ | | | | | | | |\\   | | |   | |   | | | | |  _  /  \n" +
            " | |     | |_| | | | | | \\ \\  | |_| |       / /       | | | |_| | | | \\  | | |   | |   | |_| | | | \\ \\  \n" +
            " |_|     |_____/ |_| |_|  \\_\\ |_____/      /_/        |_| \\_____/ |_|  \\_| |_|   |_|   \\_____/ |_|  \\_\\  ");
    }
}
