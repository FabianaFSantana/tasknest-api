package TaskNest.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import TaskNest.api.constants.Status;
import TaskNest.api.model.Tarefa;
import TaskNest.api.model.Usuario;
import TaskNest.api.repository.TarefaRepository;


@Service
public class EmailTarefaService {
    
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TarefaRepository tarefaRepository;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String usuario;

    @Value("${spring.mail.password}")
    private String senha;


    public void enviarEmail(Usuario usuario, Tarefa tarefa) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(usuario.getEmail());
        mailMessage.setSubject(tarefa.getTitulo());
        mailMessage.setText(tarefa.getDescricao());
        
        javaMailSender.send(mailMessage);
    }

    public void enviarNotificacaoTarefaPorEmail(Usuario usuario, Tarefa tarefa) throws MailException {

        if (!tarefa.getStatus().equals(Status.PENDENTE)) {
            throw new IllegalStateException("Tarefa aguardando ser enviada.");
        }

        try {
            String destinatario = usuario.getEmail();
            String assunto = "Nova tarefa: " + tarefa.getTitulo();
            String corpo = "Olá, " + usuario.getNome() + "! Você tem uma nova tarefa! \n\n" +
                           "Título: " + tarefa.getTitulo() + "\n" +
                           "Tarefa: " + tarefa.getDescricao() + "\n" +
                           "Prioridade: " + tarefa.getPrioridade() + "\n" +
                           "Data de Criação: " + tarefa.getDataDeCriacao() + "\n" +
                           "Data de Vencimento: " + tarefa.getDataDeVencimento() + "\n";
            
            try {
                enviarEmail(usuario, tarefa);
                tarefaRepository.save(tarefa);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException("Falha ao enviar tarefa por email.");
        }
    }
}
