package TaskNest.api.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TaskNest.api.model.Comentario;
import TaskNest.api.model.Tarefa;
import TaskNest.api.repository.ComentarioRepository;
import TaskNest.api.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ComentarioService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    public void adicionarComentarioListaDeComentariosDaTarefa(Long id, Long idComent) {

        Optional<Tarefa> tarefOptional = tarefaRepository.findById(id);
        if (tarefOptional.isPresent()) {
            Tarefa tarefa = tarefOptional.get();

            Optional<Comentario> comeOptional = comentarioRepository.findById(idComent);
            if (comeOptional.isPresent()) {
                Comentario comentario = comeOptional.get();

                List<Comentario> comentarios = tarefa.getComentarios();
                comentarios.add(comentario);
                tarefaRepository.save(tarefa);
                
            } else {
                throw new EntityNotFoundException("Comentário não foi enconrado.");
            }
            
        } else {
            throw new EntityNotFoundException("Tarefa não foi encontrada.");
        }
    }

    public List<Comentario> exibirCometariosDaTarefa(Long id) {

        Optional<Tarefa> tarefOptional = tarefaRepository.findById(id);

        if (tarefOptional.isPresent()) {
            Tarefa tarefa = tarefOptional.get();

            List<Comentario> comentarios = tarefa.getComentarios();
            return comentarios;
            
        } else {
            return Collections.emptyList();
        }
    }

    public void removerComentarioDaTarefa(Long id, Long idComent) {

        Optional<Tarefa> tarefOptional = tarefaRepository.findById(id);
        if (tarefOptional.isPresent()) {
            Tarefa tarefa = tarefOptional.get();

            Optional<Comentario> comentOptional = comentarioRepository.findById(idComent);
            if (comentOptional.isPresent()) {
                Comentario comentario = comentOptional.get();

                List<Comentario> comentarios = tarefa.getComentarios();
                comentarios.remove(comentario);
                tarefaRepository.save(tarefa);
                
            } else {
                throw new EntityNotFoundException("Comentário não encontrado.");
            }
            
        } else {
            throw new EntityNotFoundException("Tarefa não encontrada.");
        }
    }


    
}
