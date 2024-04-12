package TaskNest.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @Column(unique = true)
    private String email;

    @Column(name = "senha")
    @NotNull
    @NotEmpty
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*([a-zA-Z])(?=.*([@#$%^&+=]).*$", message = "A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial")
    @JsonIgnore
    private String senha;

    @NotBlank(message = "O telefone é obrigatório")
    @Size(min = 10, max = 20, message = "O telefone precisa ter entre 10 e 20 caracteres")
    @Pattern(regexp = "\\+?[0-9]+", message = "O telefone deve conter apenas números.")
    private String telefone;
}
