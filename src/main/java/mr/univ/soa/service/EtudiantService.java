package mr.univ.soa.service;

import jakarta.enterprise.context.ApplicationScoped;
import mr.univ.soa.metier.Etudiant;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class EtudiantService {
    private final Map<Long, Etudiant> donnees = new HashMap<>();
    private final AtomicLong compteurId = new AtomicLong(1);

    public Collection<Etudiant> tous() { return donnees.values(); }
    public Etudiant trouver(Long id) { return donnees.get(id); }

    public Etudiant creer(String nom, String email, Long departementId) {
        Etudiant e = new Etudiant(compteurId.getAndIncrement(), nom, email, departementId);
        donnees.put(e.getId(), e);
        return e;
    }

    public Etudiant mettreAJour(Long id, String nom, String email, Long departementId, List<Long> moduleIds) {
        if (!donnees.containsKey(id)) return null;
        Etudiant e = new Etudiant(id, nom, email, departementId);
        if (moduleIds != null) e.setModuleIds(moduleIds);
        donnees.put(id, e);
        return e;
    }

    public Etudiant modifierPartiellement(Long id, String nom, String email, Long departementId, List<Long> moduleIds) {
        Etudiant existant = donnees.get(id);
        if (existant == null) return null;

        if (nom != null && !nom.isBlank()) existant.setNom(nom);
        if (email != null && !email.isBlank()) existant.setEmail(email);
        if (departementId != null) existant.setDepartementId(departementId);
        if (moduleIds != null) existant.setModuleIds(moduleIds);

        return existant;
    }

    public boolean supprimer(Long id) { return donnees.remove(id) != null; }
}