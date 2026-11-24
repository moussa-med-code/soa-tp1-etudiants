package mr.univ.soa.metier;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private Long id;
    private String nom;
    private List<Long> etudiantIds = new ArrayList<>(); // Relation N-N

    public Module() {}
    public Module(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public List<Long> getEtudiantIds() { return etudiantIds; }
    public void setEtudiantIds(List<Long> etudiantIds) { this.etudiantIds = etudiantIds; }
}