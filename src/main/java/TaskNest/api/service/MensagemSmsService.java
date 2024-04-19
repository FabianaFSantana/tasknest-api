package TaskNest.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import TaskNest.api.constants.Status;
import TaskNest.api.model.Tarefa;
import TaskNest.api.model.Usuario;
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

    public void enviarNotificacaoDeTarefaPorSms(Usuario usuario, Tarefa tarefa) {

        if (!tarefa.getStatus().equals(Status.PENDENTE)) {
            throw new IllegalStateException("Tarefa esprando ser enviada."); 
        }

        try {
            String mensagemSms = "Olá, " + usuario.getNome() +"! Você tem uma notificação pendente!\n\n" +
                                 "Título: " + tarefa.getTitulo() + "\n" +
                                 "Descrição: " + tarefa.getDescricao() + "\n" +
                                 "Status: " + tarefa.getStatus() + "\n" +
                                 "Data de Criação: " + tarefa.getDataDeCriacao() + "\n" + 
                                 "Prioridade: " + tarefa.getPrioridade() + "\n" +
                                 "Data de Vencimento: " + tarefa.getDataDeVencimento() + "\n";

            enviarMensagem(usuario.getTelefone(), mensagemSms);
            tarefaRepository.save(tarefa);

        } catch (Exception e) {

            throw new RuntimeException("Falha ao enviar a notificação.", e);

        }
    }
    
}
