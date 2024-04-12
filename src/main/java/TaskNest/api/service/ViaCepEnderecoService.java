package TaskNest.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import TaskNest.api.model.Endereco;
import TaskNest.api.model.Usuario;
import TaskNest.api.model.ViaCepEndereco;
import TaskNest.api.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ViaCepEnderecoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void buscarEnderecoPorCep(Long idUsuario, String cep) {

        Optional<Usuario> usuarOptional = usuarioRepository.findById(idUsuario);

        if (usuarOptional.isPresent()) {
            Usuario usuarioEncont = usuarOptional.get();

            try {
                String url = "https://viacep.com.br/ws/" + cep + "/json/";
                RestTemplate restTemplate = new RestTemplate();
                ViaCepEndereco viaCepEndereco = restTemplate.getForObject(url, ViaCepEndereco.class);

                if (viaCepEndereco != null) {
                    Endereco endereco = usuarioEncont.getEndereco();

                    endereco.setCep(viaCepEndereco.getCep());
                    endereco.setEndereco(viaCepEndereco.getLogradouro());
                    endereco.setBairro(viaCepEndereco.getBairro());
                    endereco.setCidade(viaCepEndereco.getLocalidade());
                    endereco.setEstado(viaCepEndereco.getUf());

                    usuarioRepository.save(usuarioEncont);
                }

            } catch (Exception e) {
                throw new RuntimeException("Erro ao procurar o cep." + e.getMessage());
            
            } 

        } else {
            throw new EntityNotFoundException("Usuário não foi encontrado.");
        }
    }    
    
}
