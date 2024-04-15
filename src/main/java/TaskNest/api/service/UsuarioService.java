package TaskNest.api.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TaskNest.api.model.Tarefa;
import TaskNest.api.model.Usuario;
import TaskNest.api.repository.TarefaRepository;
import TaskNest.api.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {

    @Autowired
    private TarefaRepository tarefaRepository;

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    
    public Usuario cadastrarUsuario(Usuario usuario) {
        
        if(!usuario.validarSenha(usuario.getSenha())) {
            throw new IllegalArgumentException("A senha não é válida.");
        }

        return usuarioRepository.save(usuario);
    }

    public void adicionarTarefaNaListaDoUsuario(Long idUsuario, Long id) {
        
        Optional<Usuario> usuarOptional = usuarioRepository.findById(idUsuario);
        if (usuarOptional.isPresent()) {
            Usuario usuario = usuarOptional.get();

            Optional<Tarefa> tarefOptional = tarefaRepository.findById(id);
            if (tarefOptional.isPresent()) {
                Tarefa tarefa = tarefOptional.get();

                List<Tarefa> tarefas =  usuario.getTarefas();
                tarefas.add(tarefa);
                usuarioRepository.save(usuario);

            } else {
                throw new EntityNotFoundException("Tarefa não foi encontrada.");
            }
            
        } else {
            throw new EntityNotFoundException("Usuário não foi encontrado.");
        }
    }

    public List<Tarefa> exibirListaDeTarefasDoUsuario(Long idUsuario) {
        Optional<Usuario> usuarOptional = usuarioRepository.findById(idUsuario);

        if (usuarOptional.isPresent()) {
            Usuario usuario = usuarOptional.get();
            return usuario.getTarefas();
            
        } else {
            return Collections.emptyList();
        }
    }

    public void removerTarefaDaListaDeTarefas(Long idUsuario, Long id) {

        Optional<Usuario> usuarOptional = usuarioRepository.findById(idUsuario);
        if (usuarOptional.isPresent()) {
            Usuario usuario = usuarOptional.get();

            Optional<Tarefa> tarefOptional = tarefaRepository.findById(id);
            if (tarefOptional.isPresent()) {
                Tarefa tarefa = tarefOptional.get();

                List<Tarefa> tarefas = usuario.getTarefas();
                tarefas.remove(tarefa);
                usuarioRepository.save(usuario);
                
            } else {
                throw new EntityNotFoundException("Tarefa não foi encontrada.");
            }
            
        } else {
            throw new EntityNotFoundException("Usuário não foi encontrado.");
        }
    }

    
}
