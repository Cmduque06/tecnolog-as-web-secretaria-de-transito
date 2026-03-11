package com.transito.controller;

import com.transito.model.Infraccion;
import com.transito.model.TipoDeteccion;
import com.transito.model.TipoSeveridad;
import com.transito.service.InfraccionService;
import com.transito.service.VehiculoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/infracciones")
public class InfraccionController {

    private final InfraccionService infraccionService;
    private final VehiculoService vehiculoService;

    public InfraccionController(InfraccionService infraccionService, VehiculoService vehiculoService) {
        this.infraccionService = infraccionService;
        this.vehiculoService = vehiculoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("infracciones", infraccionService.listar());
        model.addAttribute("vehiculos", vehiculoService.listar());
        model.addAttribute("tiposDeteccion", TipoDeteccion.values());
        model.addAttribute("severidades", TipoSeveridad.values());
        model.addAttribute("infraccion", new Infraccion());
        return "infracciones";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Infraccion infraccion, RedirectAttributes redirectAttributes) {
        infraccionService.guardar(infraccion);
        String mensaje = infraccionService.mensajeSuspension(infraccion.getVehiculoId());
        if (!mensaje.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensaje", mensaje);
        }
        return "redirect:/infracciones";
    }

    @PostMapping("/pagar/{id}")
    public String pagar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        infraccionService.marcarComoPagada(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Infracción marcada como pagada y puntos restaurados.");
        return "redirect:/infracciones";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        infraccionService.eliminar(id);
        return "redirect:/infracciones";
    }
}
