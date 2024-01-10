package lvov.finalwork.repository;

import lvov.finalwork.entity.Logs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggingRepo  extends JpaRepository<Logs, Long> {
}
