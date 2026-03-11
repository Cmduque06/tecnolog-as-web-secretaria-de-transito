package com.transito.service;

import com.transito.model.Vehiculo;
import com.transito.repository.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    public List<Vehiculo> listar() { return vehiculoRepository.findAll(); }
    public Optional<Vehiculo> buscarPorId(Long id) { return vehiculoRepository.findById(id); }
    public Vehiculo guardar(Vehiculo v) { return vehiculoRepository.save(v); }
    public void eliminar(Long id) { vehiculoRepository.deleteById(id); }
}
