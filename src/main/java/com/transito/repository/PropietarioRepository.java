package com.transito.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.transito.model.Propietario;
import com.transito.util.FileManager;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PropietarioRepository {

    private static final String FILE_PATH = "data/propietarios.json";
    private final FileManager fileManager;
    private List<Propietario> propietarios = new ArrayList<>();

    public PropietarioRepository(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @PostConstruct
    public void init() {
        propietarios = fileManager.loadList(FILE_PATH, new TypeReference<List<Propietario>>() {});
    }

    public List<Propietario> findAll() { return propietarios; }

    public Optional<Propietario> findById(Long id) {
        return propietarios.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public Propietario save(Propietario propietario) {
        if (propietario.getId() == null) {
            propietario.setId(nextId());
            propietarios.add(propietario);
        } else {
            deleteById(propietario.getId());
            propietarios.add(propietario);
        }
        persist();
        return propietario;
    }

    public void deleteById(Long id) {
        propietarios.removeIf(p -> p.getId().equals(id));
        persist();
    }

    private Long nextId() {
        return propietarios.stream().mapToLong(Propietario::getId).max().orElse(0L) + 1;
    }

    private void persist() { fileManager.saveList(FILE_PATH, propietarios); }
}
