package com.transito.controller;

import com.transito.model.TipoVehiculo;
import com.transito.model.Vehiculo;
import com.transito.service.PropietarioService;
import com.transito.service.VehiculoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;
    private final PropietarioService propietarioService;

    public VehiculoController(VehiculoService vehiculoService, PropietarioService propietarioService) {
        this.vehiculoService = vehiculoService;
        this.propietarioService = propietarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("vehiculos", vehiculoService.listar());
        model.addAttribute("propietarios", propietarioService.listar());
        model.addAttribute("tipos", TipoVehiculo.values());
        model.addAttribute("vehiculo", new Vehiculo());
        return "vehiculos";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Vehiculo vehiculo) {
        vehiculoService.guardar(vehiculo);
        return "redirect:/vehiculos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("vehiculo", vehiculoService.buscarPorId(id).orElse(new Vehiculo()));
        model.addAttribute("vehiculos", vehiculoService.listar());
        model.addAttribute("propietarios", propietarioService.listar());
        model.addAttribute("tipos", TipoVehiculo.values());
        return "vehiculos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        vehiculoService.eliminar(id);
        return "redirect:/vehiculos";
    }
}
