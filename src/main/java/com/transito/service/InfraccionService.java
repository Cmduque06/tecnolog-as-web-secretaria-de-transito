package com.transito.service;

import com.transito.model.Infraccion;
import com.transito.model.Propietario;
import com.transito.model.TipoDeteccion;
import com.transito.model.TipoSeveridad;
import com.transito.model.Vehiculo;
import com.transito.repository.InfraccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InfraccionService {

    private final InfraccionRepository infraccionRepository;
    private final VehiculoService vehiculoService;
    private final PropietarioService propietarioService;

    public InfraccionService(InfraccionRepository infraccionRepository,
                             VehiculoService vehiculoService,
                             PropietarioService propietarioService) {
        this.infraccionRepository = infraccionRepository;
        this.vehiculoService = vehiculoService;
        this.propietarioService = propietarioService;
    }

    public List<Infraccion> listar() { return infraccionRepository.findAll(); }

    public Infraccion guardar(Infraccion infraccion) {
        infraccion.setPagada(false);
        Infraccion guardada = infraccionRepository.save(infraccion);
        descontarPuntosLicencia(infraccion);
        return guardada;
    }

    public void eliminar(Long id) { infraccionRepository.deleteById(id); }

    public void marcarComoPagada(Long id) {
        Optional<Infraccion> infraccionOpt = infraccionRepository.findById(id);
        if (infraccionOpt.isEmpty()) return;

        Infraccion infraccion = infraccionOpt.get();
        if (infraccion.isPagada()) return;

        infraccion.setPagada(true);
        infraccionRepository.save(infraccion);
        restaurarPuntosLicencia(infraccion);
    }

    public long totalInfraccionesResueltas() {
        return infraccionRepository.findAll().stream().filter(Infraccion::isPagada).count();
    }

    public long totalInfraccionesPendientes() {
        return infraccionRepository.findAll().stream().filter(i -> !i.isPagada()).count();
    }

    public long infraccionesResueltasPorTipoDeteccion(TipoDeteccion tipoDeteccion) {
        return infraccionRepository.findAll().stream()
                .filter(Infraccion::isPagada)
                .filter(i -> i.getTipoDeteccion() == tipoDeteccion)
                .count();
    }

    public double totalMultasPagadas() {
        return infraccionRepository.findAll().stream().filter(Infraccion::isPagada).mapToDouble(Infraccion::getValor).sum();
    }

    public double totalMultasPendientes() {
        return infraccionRepository.findAll().stream().filter(i -> !i.isPagada()).mapToDouble(Infraccion::getValor).sum();
    }

    private void descontarPuntosLicencia(Infraccion infraccion) {
        Optional<Vehiculo> vehiculoOpt = vehiculoService.buscarPorId(infraccion.getVehiculoId());
        if (vehiculoOpt.isEmpty()) return;

        Optional<Propietario> propietarioOpt = propietarioService.buscarPorId(vehiculoOpt.get().getPropietarioId());
        if (propietarioOpt.isEmpty()) return;

        Propietario propietario = propietarioOpt.get();
        int descuento = puntosPorSeveridad(infraccion.getSeveridad());
        propietario.setPuntosLicencia(Math.max(0, propietario.getPuntosLicencia() - descuento));
        propietarioService.guardarPuntos(propietario);
    }

    private void restaurarPuntosLicencia(Infraccion infraccion) {
        Optional<Vehiculo> vehiculoOpt = vehiculoService.buscarPorId(infraccion.getVehiculoId());
        if (vehiculoOpt.isEmpty()) return;

        Optional<Propietario> propietarioOpt = propietarioService.buscarPorId(vehiculoOpt.get().getPropietarioId());
        if (propietarioOpt.isEmpty()) return;

        Propietario propietario = propietarioOpt.get();
        int puntosRecuperados = puntosPorSeveridad(infraccion.getSeveridad());
        propietario.setPuntosLicencia(Math.min(20, propietario.getPuntosLicencia() + puntosRecuperados));
        propietarioService.guardarPuntos(propietario);
    }

    private int puntosPorSeveridad(TipoSeveridad severidad) {
        return switch (severidad) {
            case LEVE -> 2;
            case MEDIA -> 4;
            case GRAVE -> 6;
        };
    }

    public String mensajeSuspension(Long vehiculoId) {
        Optional<Vehiculo> vehiculoOpt = vehiculoService.buscarPorId(vehiculoId);
        if (vehiculoOpt.isEmpty()) return "";

        Optional<Propietario> propietarioOpt = propietarioService.buscarPorId(vehiculoOpt.get().getPropietarioId());
        if (propietarioOpt.isPresent() && propietarioOpt.get().getPuntosLicencia() == 0) {
            return "Licencia suspendida por exceso de infracciones";
        }
        return "";
    }
}
