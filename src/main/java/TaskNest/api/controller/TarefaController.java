package TaskNest.api.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TaskNest.api.constants.Prioridade;
import TaskNest.api.constants.Status;
import TaskNest.api.model.Comentario;
import TaskNest.api.model.Tarefa;
import TaskNest.api.repository.TarefaRepository;
import TaskNest.api.service.ComentarioService;
import TaskNest.api.service.TarefaService;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {
    
    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<Tarefa> cadastrarNovaTarefa(@RequestBody Tarefa tarefa) {
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(tarefaRepository.save(tarefa));
    }

    @PostMapping("/{id}/adicionarComentario/{idComent}")
    public ResponseEntity<String> adicionarComentario(@PathVariable("id") Long id,
    @PathVariable("idComent") Long idComent) {
        comentarioService.adicionarComentarioListaDeComentariosDaTarefa(id, idComent);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Comentário adicionado à tarefa.");
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> exibirListaDeTodasAsTarefas() {
        return ResponseEntity.status(HttpStatus.OK)
        .body(tarefaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Tarefa>> buscarTarefaPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
        .body(tarefaRepository.findById(id));
    }

    @GetMapping("/buscarPelaDataCriacao/{dataDeCriacao}")
    public ResponseEntity<List<Tarefa>> buscarTarefaPelaDataDeCriacao(@PathVariable("dataDeCriacao") LocalDate dataDeCriacao) {
        List<Tarefa> tarefas = tarefaRepository.findAllByDataCriacao(dataDeCriacao);

        if (!tarefas.isEmpty()) {         
            return ResponseEntity.status(HttpStatus.OK).body(tarefas);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarPelaDataVencimento/{dataDeVencimento}")
    public ResponseEntity<List<Tarefa>> buscarTarefaPelaDataDeVencimento(@PathVariable("dataDeVencimento") LocalDate dataDeVencimento) {
        List<Tarefa> tarefas = tarefaRepository.findAllByDataVencimento(dataDeVencimento);

        if (!tarefas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(tarefas);
            
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarPelaPrioridade/{prioridade}")
    public ResponseEntity<List<Tarefa>> buscarTarefaPelaPrioridade(@PathVariable("prioridade") Prioridade prioridade) {
        List<Tarefa> tarefas = tarefaRepository.findAllByPrioridade(prioridade);

        if (!tarefas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(tarefas);
            
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarPeloStatus/{status}")
    public ResponseEntity<List<Tarefa>> buscarTarefaPeloStatus(@PathVariable("status") Status status) {
        List<Tarefa> tarefas = tarefaRepository.findAllByStatus(status);

        if (!tarefas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(tarefas);
            
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarPeloTitulo/{titulo}")
    public ResponseEntity<Tarefa> buscarTarefaPeloTitulo(@PathVariable("titulo") String titulo) {
        Optional<Tarefa> tarefOptional = tarefaRepository.findByTitulo(titulo);

        if (tarefOptional.isPresent()) {
            Tarefa tarefa = tarefOptional.get();
            return ResponseEntity.status(HttpStatus.OK).body(tarefa);
            
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exibirComentarios/{id}")
    public ResponseEntity<List<Comentario>> exibirListaDeComentarios(@PathVariable("id") Long id) {

        List<Comentario> comentarios = comentarioService.exibirCometariosDaTarefa(id);
        return ResponseEntity.status(HttpStatus.OK).body(comentarios);
    }

    @GetMapping("{idUsuario}/enviarSmsTarefa/{id}")
    public ResponseEntity<String> enviarNotificacaoTarefaPorSms(@PathVariable("idUsuario") Long idUsuario,
    @PathVariable("id") Long id) {

        tarefaService.enviarNotificacaoDeTarefaParaUsuario(idUsuario, id);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Tarefa enviada por SMS.");

    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarDadosDaTarefa(@PathVariable("id") Long id,
    @RequestBody Tarefa tarefa) {
        Optional<Tarefa> tarefOptional = tarefaRepository.findById(id);

        if (tarefOptional.isPresent()) {
            Tarefa tarefEncont = tarefOptional.get();

            tarefEncont.setTitulo(tarefa.getTitulo());
            tarefEncont.setDescricao(tarefa.getDescricao());
            tarefEncont.setDataDeCriacao(tarefa.getDataDeCriacao());
            tarefEncont.setDataDeVencimento(tarefa.getDataDeVencimento());
            tarefEncont.setPrioridade(tarefa.getPrioridade());
            tarefEncont.setStatus(tarefa.getStatus());

            return ResponseEntity.status(HttpStatus.OK)
            .body(tarefaRepository.save(tarefEncont));

        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> exluirTarefa(@PathVariable("id") Long id) {

        tarefaRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Tarefa excluída com sucesso!");
    }

    @DeleteMapping("/{id}/removerComentarioDaTarefa/{idComent}")
    public ResponseEntity<String> excluirComentarioDaTarefa(@PathVariable("id") Long id,
    @PathVariable("idComent") Long idComent) {

        comentarioService.removerComentarioDaTarefa(id, idComent);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Comentário removido da tarefa.");
    }



}
