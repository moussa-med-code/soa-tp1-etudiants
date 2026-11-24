package mr.univ.soa.service;

import jakarta.enterprise.context.ApplicationScoped;
import mr.univ.soa.metier.Etudiant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class EtudiantService {
    private final Map<Long, Etudiant> donnees = new HashMap<>();
    private final AtomicLong compteurId = new AtomicLong(1);

    public Collection<Etudiant> tous() {
        return donnees.values();
    }

    public Etudiant trouver(Long id) {
        return donnees.get(id);
    }

    public Etudiant creer(String nom, String email) {
        Long nouvelId = compteurId.getAndIncrement();
        Etudiant e = new Etudiant(nouvelId, nom, email);
        donnees.put(nouvelId, e);
        return e;
    }

    public Etudiant mettreAJour(Long id, String nom, String email) {
        // Si l'étudiant n'existe pas, on renvoie null
        if (!donnees.containsKey(id)) {
            return null;
        }
        Etudiant e = new Etudiant(id, nom, email);
        donnees.put(id, e);
        return e;
    }

    public boolean supprimer(Long id) {
        // remove() renvoie l'élément supprimé, ou null s'il n'existait pas
        return donnees.remove(id) != null;
    }
}