package dev.melissamorgan.splatoonweapons;

import jakarta.persistence.*;

@Entity
@Table(name = "modes")
public class Mode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mode_id", nullable = false)
    private Integer id;

    @Column(name = "mode_name", nullable = false, length = 45)
    private String modeName;

    @Column(name = "secret_name", nullable = false, length = 45)
    private String secretName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public String getSecretName() {
        return secretName;
    }

    public void setSecretName(String secretName) {
        this.secretName = secretName;
    }

}