package mr.univ.soa.metier;

public class Module {
    private Long id;
    private String nom;
    private Long departementId;

    public Module() {}

    public Module(Long id, String nom, Long departementId) {
        this.id = id;
        this.nom = nom;
        this.departementId = departementId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Long getDepartementId() { return departementId; }
    public void setDepartementId(Long departementId) { this.departementId = departementId; }
}