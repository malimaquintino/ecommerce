package quintino.malima.ecommerce.identity.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(schema = "identity", name = "login_attempts")
public class LoginAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Boolean success;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "attempted_at", nullable = false, updatable = false)
    private LocalDateTime attemptedAt;

    protected LoginAttempt() {}

    public LoginAttempt(Long userId, Boolean success, String ipAddress) {
        this.userId      = userId;
        this.success     = success;
        this.ipAddress   = ipAddress;
        this.attemptedAt = LocalDateTime.now();
    }

    public Long getId()                    { return id; }
    public Long getUserId()                { return userId; }
    public Boolean getSuccess()            { return success; }
    public String getIpAddress()           { return ipAddress; }
    public LocalDateTime getAttemptedAt()  { return attemptedAt; }
}
