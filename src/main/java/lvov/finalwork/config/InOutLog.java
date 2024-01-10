package lvov.finalwork.config;

import lvov.finalwork.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class InOutLog {
    @Autowired
    private LoggingService loggingService;

    @EventListener
    public void onInOk(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        loggingService.logAction(username, "Вход");
    }

    @EventListener
    public void onOutk(LogoutSuccessEvent event) {
        String username = event.getAuthentication().getName();
        loggingService.logAction(username, "Выход");
    }
}
