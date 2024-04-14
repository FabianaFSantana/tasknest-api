package TaskNest.api.controller;

import java.util.Optional;

import javax.print.DocFlavor.STRING;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TaskNest.api.model.Endereco;
import TaskNest.api.model.Usuario;
import TaskNest.api.repository.UsuarioRepository;
import TaskNest.api.service.ViaCepEnderecoService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuariorRepository;

    @Autowired
    private ViaCepEnderecoService viaCepEnderecoService;
   

    @PostMapping()
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
    
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(usuariorRepository.save(usuario));
    }

    @PostMapping("/cadastrarEndereco/{idUsuario}")
    public ResponseEntity<String> cadastrarEnderecoUsuario(@PathVariable("idUsuario") Long idUsuario,
    @RequestBody Endereco endereco) {
        
        viaCepEnderecoService.buscarEnderecoPorCep(idUsuario, endereco.getCep());
        return ResponseEntity.status(HttpStatus.OK)
        .body("Endereço cadastrado com sucesso!"); 
    }




}
