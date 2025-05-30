package br.com.acme.cervejariaacme.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data @AllArgsConstructor@NoArgsConstructor@Builder
@Entity
public class Cerveja implements Serializable {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @ManyToOne@JoinColumn(name = "estilo_id")
    private Estilo estilo;
    @ManyToOne
    private Marca marca;
    @ManyToMany(fetch = FetchType.EAGER) //Lazy - Eagger
    List<Lupulo> lupulos;
    @ManyToMany
    private Set<Usuario> curtidas;

}
