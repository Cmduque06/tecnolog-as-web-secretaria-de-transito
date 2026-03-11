package com.transito.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.transito.model.Infraccion;
import com.transito.util.FileManager;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InfraccionRepository {

    private static final String FILE_PATH = "data/infracciones.json";
    private final FileManager fileManager;
    private List<Infraccion> infracciones = new ArrayList<>();

    public InfraccionRepository(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @PostConstruct
    public void init() {
        infracciones = fileManager.loadList(FILE_PATH, new TypeReference<List<Infraccion>>() {});
    }

    public List<Infraccion> findAll() { return infracciones; }

    public Optional<Infraccion> findById(Long id) {
        return infracciones.stream().filter(i -> i.getId().equals(id)).findFirst();
    }

    public Infraccion save(Infraccion infraccion) {
        if (infraccion.getId() == null) {
            infraccion.setId(nextId());
            infracciones.add(infraccion);
        } else {
            deleteById(infraccion.getId());
            infracciones.add(infraccion);
        }
        persist();
        return infraccion;
    }

    public void deleteById(Long id) {
        infracciones.removeIf(i -> i.getId().equals(id));
        persist();
    }

    private Long nextId() {
        return infracciones.stream().mapToLong(Infraccion::getId).max().orElse(0L) + 1;
    }

    private void persist() { fileManager.saveList(FILE_PATH, infracciones); }
}
