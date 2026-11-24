package mr.univ.soa.service;

import jakarta.enterprise.context.ApplicationScoped;
import mr.univ.soa.metier.Module;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class ModuleService {
    private final Map<Long, Module> donnees = new HashMap<>();
    private final AtomicLong compteurId = new AtomicLong(1);

    public Collection<Module> tous() { return donnees.values(); }

    public Module trouver(Long id) { return donnees.get(id); }

    public Module creer(String nom, Long departementId) {
        Module m = new Module(compteurId.getAndIncrement(), nom, departementId);
        donnees.put(m.getId(), m);
        return m;
    }

    public Module mettreAJour(Long id, String nom, Long departementId) {
        if (!donnees.containsKey(id)) return null;
        Module m = new Module(id, nom, departementId);
        donnees.put(id, m);
        return m;
    }

    public boolean supprimer(Long id) { return donnees.remove(id) != null; }
}