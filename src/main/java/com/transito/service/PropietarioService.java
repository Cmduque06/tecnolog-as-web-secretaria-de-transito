package com.transito.service;

import com.transito.model.Propietario;
import com.transito.repository.PropietarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropietarioService {

    private final PropietarioRepository propietarioRepository;

    public PropietarioService(PropietarioRepository propietarioRepository) {
        this.propietarioRepository = propietarioRepository;
    }

    public List<Propietario> listar() { return propietarioRepository.findAll(); }

    public Optional<Propietario> buscarPorId(Long id) { return propietarioRepository.findById(id); }

    public Propietario guardar(Propietario propietario) {
        if (propietario.getId() == null) {
            propietario.setPuntosLicencia(20);
        } else {
            int puntosActuales = propietarioRepository.findById(propietario.getId())
                    .map(Propietario::getPuntosLicencia)
                    .orElse(20);
            propietario.setPuntosLicencia(puntosActuales);
        }
        return propietarioRepository.save(propietario);
    }

    public void guardarPuntos(Propietario propietario) {
        propietarioRepository.save(propietario);
    }

    public void eliminar(Long id) { propietarioRepository.deleteById(id); }
}
