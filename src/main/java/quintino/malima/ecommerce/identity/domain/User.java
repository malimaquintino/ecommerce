package quintino.malima.ecommerce.identity.domain;

import jakarta.persistence.*;

@Entity
@Table(schema = "identity", name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String document;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    protected User() {}

    public User(String name, String document, String email, String password) {
        this.name     = name;
        this.document = document;
        this.email    = email;
        this.password = password;
    }

    public Long getId()       { return id; }
    public String getName()   { return name; }
    public String getDocument() { return document; }
    public String getEmail()  { return email; }
    public String getPassword() { return password; }
}
