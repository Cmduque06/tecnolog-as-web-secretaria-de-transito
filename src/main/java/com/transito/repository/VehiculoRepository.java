package com.transito.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.transito.model.Vehiculo;
import com.transito.util.FileManager;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class VehiculoRepository {

    private static final String FILE_PATH = "data/vehiculos.json";
    private final FileManager fileManager;
    private List<Vehiculo> vehiculos = new ArrayList<>();

    public VehiculoRepository(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @PostConstruct
    public void init() {
        vehiculos = fileManager.loadList(FILE_PATH, new TypeReference<List<Vehiculo>>() {});
    }

    public List<Vehiculo> findAll() { return vehiculos; }

    public Optional<Vehiculo> findById(Long id) {
        return vehiculos.stream().filter(v -> v.getId().equals(id)).findFirst();
    }

    public Vehiculo save(Vehiculo vehiculo) {
        if (vehiculo.getId() == null) {
            vehiculo.setId(nextId());
            vehiculos.add(vehiculo);
        } else {
            deleteById(vehiculo.getId());
            vehiculos.add(vehiculo);
        }
        persist();
        return vehiculo;
    }

    public void deleteById(Long id) {
        vehiculos.removeIf(v -> v.getId().equals(id));
        persist();
    }

    private Long nextId() {
        return vehiculos.stream().mapToLong(Vehiculo::getId).max().orElse(0L) + 1;
    }

    private void persist() { fileManager.saveList(FILE_PATH, vehiculos); }
}
