package com.transito.controller;

import com.transito.model.TipoDeteccion;
import com.transito.service.InfraccionService;
import com.transito.service.ReporteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashSet;
import java.util.Set;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService reporteService;
    private final InfraccionService infraccionService;

    public ReporteController(ReporteService reporteService, InfraccionService infraccionService) {
        this.reporteService = reporteService;
        this.infraccionService = infraccionService;
    }

    @GetMapping
    public String verReportes(@RequestParam(required = false) Set<String> mostrar, Model model) {
        Set<String> reportesActivos = (mostrar == null || mostrar.isEmpty())
                ? new LinkedHashSet<>(Set.of("porVehiculo", "porPropietario", "vehiculosTop", "resueltas"))
                : mostrar;

        model.addAttribute("mostrar", reportesActivos);
        model.addAttribute("infraccionesPorVehiculo", reporteService.infraccionesPorVehiculo());
        model.addAttribute("propietariosConMasInfracciones", reporteService.propietariosConMasInfracciones());
        model.addAttribute("vehiculosConMasMultas", reporteService.vehiculosConMasMultas());
        model.addAttribute("totalMultas", reporteService.totalDineroMultas());
        model.addAttribute("totalResueltas", infraccionService.totalInfraccionesResueltas());
        model.addAttribute("totalPendientes", infraccionService.totalInfraccionesPendientes());
        model.addAttribute("resueltasAgente", infraccionService.infraccionesResueltasPorTipoDeteccion(TipoDeteccion.AGENTE));
        model.addAttribute("resueltasCamara", infraccionService.infraccionesResueltasPorTipoDeteccion(TipoDeteccion.CAMARA));
        return "reportes";
    }
}
