package root.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendEmail(String receiver, String subject, String text);
}
