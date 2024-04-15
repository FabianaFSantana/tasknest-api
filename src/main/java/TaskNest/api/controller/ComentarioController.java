package TaskNest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TaskNest.api.model.Comentario;
import TaskNest.api.repository.ComentarioRepository;

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

    
    
}
