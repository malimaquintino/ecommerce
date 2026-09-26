package quintino.malima.ecommerce.identity.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "identity", name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_code", nullable = false, unique = true, updatable = false)
    private UUID userCode;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String documento;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected User() {}

    public User(String nome, String documento, String email, String senha) {
        this.userCode  = UUID.randomUUID();
        this.nome      = nome;
        this.documento = documento;
        this.email     = email;
        this.senha     = senha;
        this.status    = "ACTIVE";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // getters

    public Long getId()              { return id; }
    public UUID getUserCode()        { return userCode; }
    public String getNome()          { return nome; }
    public String getDocumento()     { return documento; }
    public String getEmail()         { return email; }
    public String getSenha()         { return senha; }
    public String getStatus()        { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
