package com.example.GameVault.controloes;

import com.example.GameVault.model.Juego;
import com.example.GameVault.model.Usuario;
import com.example.GameVault.service.JuegoService;
import com.example.GameVault.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Controller
public class GameController {

    @Autowired
    private JuegoService juegoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/fragments-demo")
    public String fragmentDemo() {
        return "fragments-demo";
    }

    @GetMapping({"/", "/juegos"})
    public String listarJuegos(
            @RequestParam(name = "buscar", required = false) String buscar,
            Model model) {

        if (buscar != null && !buscar.isEmpty()) {
            model.addAttribute("juegos", juegoService.buscarPorTitulo(buscar));
            model.addAttribute("busquedaActual", buscar); // Para mantener el texto en la barra
        } else {
            model.addAttribute("juegos", juegoService.listarTodos());
        }

        return "juegos";
    }

    @GetMapping("/juegos/nuevo")
    public String mostrarFormulario() {

        return "formulario";
    }

    @GetMapping("/juegos/editar/{id}")
    public String editarJuego(@PathVariable Long id, Model model) {
        Juego juego = juegoService.obtenerJuegoPorId(id);
        Usuario logueado = usuarioService.obtenerUsuarioLogeado();

        // Si el juego existe y el dueño coincide, mostramos el formulario
        if (juego != null && juego.getUsuario() != null &&
                Objects.equals(juego.getUsuario().getId(), logueado.getId())) {

            model.addAttribute("juego", juego);
            return "formulario"; // Reutilizamos nuestra vista de formulario
        }

        // Si intenta editar un juego ajeno, lo devolvemos a su panel
        return "redirect:/mis-juegos";
    }

    @PostMapping("/juegos/editar/{id}")
    public String actualizarJuego(@PathVariable Long id,
                                  @RequestParam("titulo") String titulo,
                                  @RequestParam("descripcion") String descripcion,
                                  @RequestParam("precio") Double precio,
                                  @RequestParam("categoria") String categoria,
                                  @RequestParam(value = "portada", required = false) MultipartFile portada) {

        Juego juegoExistente = juegoService.obtenerJuegoPorId(id);
        Usuario logueado = usuarioService.obtenerUsuarioLogeado();

        // Verificamos propiedad nuevamente antes de modificar
        if (juegoExistente != null &&
                juegoExistente.getUsuario() != null &&
                Objects.equals(juegoExistente.getUsuario().getId(), logueado.getId())){
            juegoExistente.setTitulo(titulo);
            juegoExistente.setDescripcion(descripcion);
            juegoExistente.setPrecio(precio);
            juegoExistente.setCategoria(categoria);

            juegoService.guardarJuego(juegoExistente, portada);
        }

        return "redirect:/mis-juegos";
    }

    @PostMapping("/juegos/eliminar/{id}")
    public String eliminarJuego(@PathVariable Long id) {
        Juego juego = juegoService.obtenerJuegoPorId(id);
        Usuario logueado = usuarioService.obtenerUsuarioLogeado();

        // Solo procedemos a borrar si el usuario logueado es el dueño
        if (juego != null &&
                juego.getUsuario() != null &&
                Objects.equals(juego.getUsuario().getId(), logueado.getId())){
            juegoService.eliminarJuego(id);
        }

        return "redirect:/mis-juegos";
    }


    @GetMapping("/mis-juegos")
    public String dashboard(Model model){
        Usuario logeado = usuarioService.obtenerUsuarioLogeado();

        model.addAttribute("juegos",juegoService.obtenerJuegosPorUsuario(logeado.getId()));

        model.addAttribute("usuario",logeado);

        return "mis-juegos";
    }

    @PostMapping("/juegos")
    public String guardarJuego(@RequestParam("titulo") String titulo,
                               @RequestParam("descripcion") String descripcion,
                               @RequestParam("precio") double precio,
                               @RequestParam("categoria") String categoria,
                               @RequestParam("portada") MultipartFile portada) {

        Juego nuevoJuego = new Juego();
        nuevoJuego.setTitulo(titulo);
        nuevoJuego.setDescripcion(descripcion);
        nuevoJuego.setPrecio(precio);
        nuevoJuego.setCategoria(categoria);

        nuevoJuego.setUsuario(usuarioService.obtenerUsuarioLogeado());

        juegoService.guardarJuego(nuevoJuego, portada);

        return "redirect:/mis-juegos";
    }
}
