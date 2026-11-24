package mr.univ.soa.service;

import jakarta.enterprise.context.ApplicationScoped;
import mr.univ.soa.metier.Departement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class DepartementService {
    private final Map<Long, Departement> donnees = new HashMap<>();
    private final AtomicLong compteurId = new AtomicLong(1);

    public Collection<Departement> tous() { return donnees.values(); }

    public Departement trouver(Long id) { return donnees.get(id); }

    public Departement creer(String nom) {
        Departement d = new Departement(compteurId.getAndIncrement(), nom);
        donnees.put(d.getId(), d);
        return d;
    }

    public Departement mettreAJour(Long id, String nom) {
        if (!donnees.containsKey(id)) return null;
        Departement d = new Departement(id, nom);
        donnees.put(id, d);
        return d;
    }

    public boolean supprimer(Long id) { return donnees.remove(id) != null; }
}