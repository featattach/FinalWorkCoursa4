package lvov.finalwork.config;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Auth {
    public org.springframework.security.core.Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}