package TaskNest.api.controller;

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

import TaskNest.api.model.Comentario;
import TaskNest.api.repository.ComentarioRepository;
import lombok.experimental.PackagePrivate;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @PostMapping
    public ResponseEntity<Comentario> cadastrarComentario(@RequestBody Comentario comentario) {
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(comentarioRepository.save(comentario));
    }

    @GetMapping
    public ResponseEntity<List<Comentario>> exibirListaDeComentarios() {
        return ResponseEntity.status(HttpStatus.OK)
        .body(comentarioRepository.findAll());
    }

    @GetMapping("/{idComent}")
    public ResponseEntity<Optional<Comentario>> buscarComentarioPeloId(@PathVariable("idComent") Long idComent) {
        return ResponseEntity.status(HttpStatus.OK)
        .body(comentarioRepository.findById(idComent));
    }

    


    
}
