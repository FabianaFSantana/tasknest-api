package TaskNest.api.controller;

import java.util.List;
import java.util.Optional;

import javax.print.DocFlavor.STRING;

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

import TaskNest.api.model.Endereco;
import TaskNest.api.model.Tarefa;
import TaskNest.api.model.Usuario;
import TaskNest.api.repository.UsuarioRepository;
import TaskNest.api.service.UsuarioService;
import TaskNest.api.service.ViaCepEnderecoService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuariorRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ViaCepEnderecoService viaCepEnderecoService;
   

    @PostMapping()
    public ResponseEntity<String> cadastrarUsuario(@RequestBody Usuario usuario) {
    
        usuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Usuário cadastrado com sucesso.");
    }

    @PostMapping("/cadastrarEndereco/{idUsuario}")
    public ResponseEntity<String> cadastrarEnderecoUsuario(@PathVariable("idUsuario") Long idUsuario,
    @RequestBody Endereco endereco) {
        
        viaCepEnderecoService.buscarEnderecoPorCep(idUsuario, endereco.getCep());
        return ResponseEntity.status(HttpStatus.OK)
        .body("Endereço cadastrado com sucesso!"); 
    }

    @PostMapping("/{idUsuario}/adicionarTarefaNaLista/{id}")
    public ResponseEntity<String> adicionarTarefaListaUsuario(@PathVariable("idUsuario") Long idUsuario,
    @PathVariable("id") Long id) {

        usuarioService.adicionarTarefaNaListaDoUsuario(idUsuario, id);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Tarefa adicionada à lista de tarefas do Usuario.");
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> exibirListaDeUsuarios() {
        return ResponseEntity.status(HttpStatus.OK)
        .body(usuariorRepository.findAll());
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Optional<Usuario>> buscarUsuarioPeloId(@PathVariable("idUsuario") Long idUsuario) {
        return ResponseEntity.status(HttpStatus.OK)
        .body(usuariorRepository.findById(idUsuario));
    }

    @GetMapping("/exibirListaDeTarefas/{idUsuario}")
    public ResponseEntity<List<Tarefa>> exibirListaDeTarefas(@PathVariable("idUsuario") Long idUsuario) {

        List<Tarefa> tarefas = usuarioService.exibirListaDeTarefasDoUsuario(idUsuario);
        return ResponseEntity.status(HttpStatus.OK).body(tarefas);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<Usuario> atulizarDadosUsuario(@PathVariable("idUsuario") Long idUsuario,
    @RequestBody Usuario usuario) {

        Optional<Usuario> usuarOptional = usuariorRepository.findById(idUsuario);
        if (usuarOptional.isPresent()) {
            Usuario usuarioEncont = usuarOptional.get();

            usuarioEncont.setNome(usuario.getNome());
            usuarioEncont.getEndereco().setCep(usuario.getEndereco().getCep());
            usuarioEncont.getEndereco().setEndereco(usuario.getEndereco().getEndereco());
            usuarioEncont.getEndereco().setNumero(usuario.getEndereco().getNumero());
            usuarioEncont.getEndereco().setComplemento(usuario.getEndereco().getComplemento());
            usuarioEncont.getEndereco().setBairro(usuario.getEndereco().getBairro());
            usuarioEncont.getEndereco().setCidade(usuario.getEndereco().getCidade());
            usuarioEncont.getEndereco().setEstado(usuario.getEndereco().getEstado());
            usuarioEncont.getEndereco().setPais(usuario.getEndereco().getPais());
            usuarioEncont.setEmail(usuario.getEmail());
            usuarioEncont.setSenha(usuario.getSenha());
            usuarioEncont.setTelefone(usuario.getTelefone());

            return ResponseEntity.status(HttpStatus.OK)
            .body(usuariorRepository.save(usuarioEncont));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<String> excluirUsuario(@PathVariable("idUsuario") Long idUsuario) {
        usuariorRepository.deleteById(idUsuario);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Usuário excluído com sucesso.");
    }

    @DeleteMapping("/{idUsuario}/removerTarefaDaListaDeUsuario/{id}")
    public ResponseEntity<String> removerUsuarioDaLista(@PathVariable("idUsuario") Long idUsuario,
    @PathVariable("id") Long id) {

        usuarioService.removerTarefaDaListaDeTarefas(idUsuario, id);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Tarefa excluída da lista de tarefas do Usuário.");
    }






}
