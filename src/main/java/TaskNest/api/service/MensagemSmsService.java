package TaskNest.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.accounts.v1.credential.PublicKey;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import TaskNest.api.repository.TarefaRepository;

@Service
public class MensagemSmsService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    private final String TWILIO_PHONE_NUMBER = "+13344497765";

    public void enviarMensagem(String numeroDestino, String mensagem) {
        Twilio.init(accountSid, authToken);

        try {
            Message message = Message.creator(
                new PhoneNumber(numeroDestino), 
                new PhoneNumber(TWILIO_PHONE_NUMBER), 
                mensagem).create();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
