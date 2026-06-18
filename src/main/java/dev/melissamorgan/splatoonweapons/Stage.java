package dev.melissamorgan.splatoonweapons;

import jakarta.persistence.*;

@Entity
@Table(name = "stages")
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stage_id", nullable = false)
    private Integer id;

    @Column(name = "stage_name", nullable = false, length = 45)
    private String stageName;

    @Column(name = "secret_name", nullable = false, length = 45)
    private String secretName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getSecretName() {
        return secretName;
    }

    public void setSecretName(String secretName) {
        this.secretName = secretName;
    }

}