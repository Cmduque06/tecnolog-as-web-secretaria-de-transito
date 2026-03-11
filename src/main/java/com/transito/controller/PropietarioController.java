package com.transito.controller;

import com.transito.model.Propietario;
import com.transito.service.PropietarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/propietarios")
public class PropietarioController {

    private final PropietarioService propietarioService;

    public PropietarioController(PropietarioService propietarioService) {
        this.propietarioService = propietarioService;
    }

    @GetMapping
    public String listar(Model model) {
        List<Propietario> propietarios = propietarioService.listar();
        model.addAttribute("propietarios", propietarios);
        model.addAttribute("propietario", new Propietario());
        model.addAttribute("propietariosSuspendidos", propietarios.stream().filter(p -> p.getPuntosLicencia() == 0).toList());
        return "propietarios";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Propietario propietario) {
        propietarioService.guardar(propietario);
        return "redirect:/propietarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        List<Propietario> propietarios = propietarioService.listar();
        model.addAttribute("propietario", propietarioService.buscarPorId(id).orElse(new Propietario()));
        model.addAttribute("propietarios", propietarios);
        model.addAttribute("propietariosSuspendidos", propietarios.stream().filter(p -> p.getPuntosLicencia() == 0).toList());
        return "propietarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        propietarioService.eliminar(id);
        return "redirect:/propietarios";
    }
}
