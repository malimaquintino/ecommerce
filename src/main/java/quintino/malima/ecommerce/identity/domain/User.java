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
    private String name;

    @Column(nullable = false, unique = true)
    private String document;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected User() {}

    public User(String name, String document, String email, String password) {
        this.userCode  = UUID.randomUUID();
        this.name      = name;
        this.document  = document;
        this.email     = email;
        this.password  = password;
        this.status    = "ACTIVE";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId()                  { return id; }
    public UUID getUserCode()            { return userCode; }
    public String getName()              { return name; }
    public String getDocument()          { return document; }
    public String getEmail()             { return email; }
    public String getPassword()          { return password; }
    public String getStatus()            { return status; }
    public LocalDateTime getCreatedAt()  { return createdAt; }
    public LocalDateTime getUpdatedAt()  { return updatedAt; }
}
