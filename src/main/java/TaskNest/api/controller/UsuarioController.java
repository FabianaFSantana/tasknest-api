package TaskNest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TaskNest.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuariorRepository;
    
}
