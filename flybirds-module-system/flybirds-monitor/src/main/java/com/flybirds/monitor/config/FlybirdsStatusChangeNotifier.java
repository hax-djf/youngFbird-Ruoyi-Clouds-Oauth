package com.flybirds.monitor.config;

import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author :ruoyi
 * @create :2021-05-12 16:43:00
 * @description :自定义通知程序服务
 */
@Component
public class FlybirdsStatusChangeNotifier extends AbstractStatusChangeNotifier {

    public FlybirdsStatusChangeNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event,
                                  de.codecentric.boot.admin.server.domain.entities.Instance instance)
    {
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent)
            {
                String status = ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus();
                switch (status)
                {
                    // 健康检查没通过
                    case "DOWN":
                        System.out.println("发送 健康检查没通过 的通知！");
                        break;
                    // 服务离线
                    case "OFFLINE":
                        System.out.println("发送 服务离线 的通知！");
                        break;
                    // 服务上线
                    case "UP":
                        System.out.println("发送 服务上线 的通知！");
                        break;
                    // 服务未知异常
                    case "UNKNOWN":
                        System.out.println("发送 服务未知异常 的通知！");
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
