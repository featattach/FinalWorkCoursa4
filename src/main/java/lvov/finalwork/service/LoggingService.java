package lvov.finalwork.service;

import lvov.finalwork.entity.Logs;
import lvov.finalwork.repository.LoggingRepo;
import org.hibernate.validator.constraints.LuhnCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoggingService {
    private final LoggingRepo loggingRepo;

    @Autowired
    public LoggingService(LoggingRepo loggingRepo){
        this.loggingRepo = loggingRepo;
    }

    public List<Logs> getAllLogs(){
        return loggingRepo.findAll();
    }
    public void logAction(String username, String action){
        Logs logs = new Logs();
        logs.setUsername(username);
        logs.setAction(action);
        logs.setTime(LocalDateTime.now());
        loggingRepo.save(logs);
    }
}
