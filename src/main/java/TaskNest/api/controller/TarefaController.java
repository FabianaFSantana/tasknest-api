package TaskNest.api.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TaskNest.api.constants.Prioridade;
import TaskNest.api.constants.Status;
import TaskNest.api.model.Tarefa;
import TaskNest.api.repository.TarefaRepository;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {
    
    @Autowired
    private TarefaRepository tarefaRepository;

    @PostMapping
    public ResponseEntity<Tarefa> cadastrarNovaTarefa(@RequestBody Tarefa tarefa) {
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(tarefaRepository.save(tarefa));
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> exibirListaDeTodasAsTarefas() {
        return ResponseEntity.status(HttpStatus.OK)
        .body(tarefaRepository.findAll());
    }

    @GetMapping("/bucarPeloId/{id}")
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






}
