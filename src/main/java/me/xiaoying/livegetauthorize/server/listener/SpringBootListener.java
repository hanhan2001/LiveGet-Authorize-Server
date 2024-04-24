package me.xiaoying.livegetauthorize.server.listener;

import me.xiaoying.logger.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Listener SpringBoot
 */
@Component
public class SpringBootListener implements CommandLineRunner, DisposableBean {
    @Override
    public void run(String... args) throws Exception {
    }

    @Override
    public void destroy() throws Exception {
    }
}