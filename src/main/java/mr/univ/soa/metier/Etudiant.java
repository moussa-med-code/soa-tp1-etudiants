package mr.univ.soa.metier;

import java.util.ArrayList;
import java.util.List;

public class Etudiant {
    private Long id;
    private String nom;
    private String email;
    private Long departementId; // Relation 1-N
    private List<Long> moduleIds = new ArrayList<>(); // Relation N-N

    public Etudiant() {}

    public Etudiant(Long id, String nom, String email, Long departementId) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.departementId = departementId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Long getDepartementId() { return departementId; }
    public void setDepartementId(Long departementId) { this.departementId = departementId; }
    public List<Long> getModuleIds() { return moduleIds; }
    public void setModuleIds(List<Long> moduleIds) { this.moduleIds = moduleIds; }
}