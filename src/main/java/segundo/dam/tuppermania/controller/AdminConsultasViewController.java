package segundo.dam.tuppermania.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/consultas")
public class AdminConsultasViewController {

    @GetMapping
    public String mostrarDashboard() {
        return "admin/consultas-dashboard";
    }
}