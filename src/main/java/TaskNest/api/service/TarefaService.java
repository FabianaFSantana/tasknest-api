package TaskNest.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TaskNest.api.model.Tarefa;
import TaskNest.api.model.Usuario;
import TaskNest.api.repository.TarefaRepository;
import TaskNest.api.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TarefaService {
    
    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private MensagemSmsService mensagemSmsService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void enviarNotificacaoDeTarefaParaUsuario(Long idUsuario, Long id) {

        Optional<Usuario> usuarOptional = usuarioRepository.findById(idUsuario);
        Optional<Tarefa> tarefOptional = tarefaRepository.findById(id);

        if (usuarOptional.isPresent() && tarefOptional.isPresent()) {
            Usuario usuario = usuarOptional.get();
            Tarefa tarefa = tarefOptional.get();

            mensagemSmsService.enviarNotificacaoDeTarefaPorSms(usuario, tarefa);
            
        } else {
            throw new EntityNotFoundException("Usuário ou tarefa não encontrada.");
        }
    }
}
