package lvov.finalwork.Controller;

import lvov.finalwork.entity.Logs;
import lvov.finalwork.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class LogsController {
    private LoggingService loggingService;

    @Autowired
    public LogsController(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @GetMapping("/user-logs")
    public String getUserActionLogs(Model model) {
        List<Logs> logs = loggingService.getAllLogs();
        model.addAttribute("logs", logs);
        return "user-logs";
    }

}
