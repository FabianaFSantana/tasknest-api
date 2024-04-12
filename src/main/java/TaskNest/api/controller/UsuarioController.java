package TaskNest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TaskNest.api.model.Usuario;
import TaskNest.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuariorRepository;

    @PostMapping()
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {

        return ResponseEntity.status(HttpStatus.CREATED)
        .body(usuariorRepository.save(usuario));
    }
}
