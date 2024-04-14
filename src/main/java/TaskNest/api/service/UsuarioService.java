package TaskNest.api.service;

import org.springframework.stereotype.Service;

import TaskNest.api.model.Usuario;
import TaskNest.api.repository.UsuarioRepository;

@Service
public class UsuarioService {

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

    
}
