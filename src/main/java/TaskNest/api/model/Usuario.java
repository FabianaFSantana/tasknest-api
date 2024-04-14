package TaskNest.api.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "usuario")

public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUsuario;

    @Column(nullable = false)
    private String nome;

    @Embedded
    private Endereco endereco;

    @NotBlank(message = "O email é obrigatório.")
    @Size(max = 50, message = "O email não pode ter mais de 50 caracteres")
    @Column(nullable = false, unique = true)
    private String email;
    
    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, max = 8, message = "A senha deve conter entre 6 e 8 caracteres.")
    @Column(nullable = false)
    private String senha;
    
    @NotBlank(message = "O telefone é obrigatório")
    @Size(min = 10, max = 20, message = "O telefone precisa ter entre 10 e 20 caracteres")
    @Pattern(regexp = "\\+?[0-9]+", message = "O telefone deve conter apenas números.")
    private String telefone;

    //Método para validar a senha do usuário:
    /*public boolean validarSenha(String senha) {

        boolean temComprimento = senha.length() >= 6 && senha.length() <= 8;
        boolean temCaracterEspecial = senha.matches(".*[!@#$%&*?!].*");
        boolean temNumero = senha.matches(".*[\\d].*");
        boolean temLetraMinuscula = senha.matches(".*[a-z].*");
        boolean temLetraMaiscula = senha.matches(".*[A-Z].*");

        return temComprimento && temCaracterEspecial && temNumero && temLetraMinuscula && temLetraMaiscula;

    }*/
}
