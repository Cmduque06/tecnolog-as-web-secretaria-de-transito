package com.transito.service;

import com.transito.model.Infraccion;
import com.transito.model.Propietario;
import com.transito.model.Vehiculo;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    private final InfraccionService infraccionService;
    private final VehiculoService vehiculoService;
    private final PropietarioService propietarioService;

    public ReporteService(InfraccionService infraccionService,
                          VehiculoService vehiculoService,
                          PropietarioService propietarioService) {
        this.infraccionService = infraccionService;
        this.vehiculoService = vehiculoService;
        this.propietarioService = propietarioService;
    }

    public Map<String, Long> infraccionesPorVehiculo() {
        Map<Long, String> placaPorId = vehiculoService.listar().stream()
                .collect(Collectors.toMap(Vehiculo::getId, Vehiculo::getPlaca));

        return infraccionService.listar().stream()
                .collect(Collectors.groupingBy(i -> placaPorId.getOrDefault(i.getVehiculoId(), "SIN-PLACA"), Collectors.counting()));
    }

    public Map<String, Long> propietariosConMasInfracciones() {
        Map<Long, Long> propietarioByVehiculo = vehiculoService.listar().stream()
                .collect(Collectors.toMap(Vehiculo::getId, Vehiculo::getPropietarioId));
        Map<Long, String> nombreByPropietario = propietarioService.listar().stream()
                .collect(Collectors.toMap(Propietario::getId, Propietario::getNombre));

        Map<String, Long> base = infraccionService.listar().stream()
                .collect(Collectors.groupingBy(i -> {
                    Long propietarioId = propietarioByVehiculo.get(i.getVehiculoId());
                    return nombreByPropietario.getOrDefault(propietarioId, "SIN-PROPIETARIO");
                }, Collectors.counting()));

        return ordenarDesc(base);
    }

    public Map<String, Long> vehiculosConMasMultas() {
        return ordenarDesc(infraccionesPorVehiculo());
    }

    public double totalDineroMultas() {
        return infraccionService.listar().stream().mapToDouble(Infraccion::getValor).sum();
    }

    private Map<String, Long> ordenarDesc(Map<String, Long> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }
}
