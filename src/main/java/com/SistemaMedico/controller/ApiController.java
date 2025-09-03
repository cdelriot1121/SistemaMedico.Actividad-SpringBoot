package com.SistemaMedico.controller;

import com.SistemaMedico.model.CitaModel;
import com.SistemaMedico.model.ClienteModel;
import com.SistemaMedico.model.UsuarioModel;
import com.SistemaMedico.services.CitaService;
import com.SistemaMedico.services.CitaService.CitaEstadisticas;
import com.SistemaMedico.services.ClienteService;
import com.SistemaMedico.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private CitaService citaService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private UsuarioService usuarioService;

    // ========== ENDPOINTS DE CITAS ==========
    
    @GetMapping("/citas")
    public List<CitaModel> obtenerTodasLasCitas() {
        return citaService.obtenerTodasLasCitas();
    }
    
    @GetMapping("/citas/{id}")
    public ResponseEntity<CitaModel> obtenerCitaPorId(@PathVariable Long id) {
        Optional<CitaModel> cita = citaService.obtenerCitaPorId(id);
        return cita.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/citas/paciente/{identificacion}")
    public List<CitaModel> obtenerCitasPorIdentificacion(@PathVariable Integer identificacion) {
        return citaService.obtenerCitasPorIdentificacion(identificacion);
    }
    
    @GetMapping("/citas/estado/{estado}")
    public List<CitaModel> obtenerCitasPorEstado(@PathVariable String estado) {
        return citaService.obtenerCitasPorEstado(estado);
    }
    
    @GetMapping("/citas/tipo/{tipo}")
    public List<CitaModel> obtenerCitasPorTipo(@PathVariable String tipo) {
        return citaService.obtenerCitasPorTipo(tipo);
    }
    
    @GetMapping("/citas/recientes")
    public List<CitaModel> obtenerCitasRecientes() {
        return citaService.obtenerCitasRecientes();
    }
    
    @PostMapping("/citas")
    public ResponseEntity<?> crearCita(@RequestBody CitaModel cita) {
        try {
            CitaModel citaGuardada = citaService.guardarCita(cita);
            return ResponseEntity.ok(citaGuardada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la cita: " + e.getMessage());
        }
    }
    
    @PutMapping("/citas/{id}")
    public ResponseEntity<?> actualizarCita(@PathVariable Long id, @RequestBody CitaModel cita) {
        try {
            CitaModel citaActualizada = citaService.actualizarCita(id, cita);
            return ResponseEntity.ok(citaActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar la cita: " + e.getMessage());
        }
    }
    
    @PutMapping("/citas/{id}/estado")
    public ResponseEntity<?> cambiarEstadoCita(@PathVariable Long id, @RequestParam String estado) {
        try {
            CitaModel citaActualizada = citaService.cambiarEstadoCita(id, estado);
            return ResponseEntity.ok(citaActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al cambiar el estado: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/citas/{id}")
    public ResponseEntity<?> eliminarCita(@PathVariable Long id) {
        try {
            citaService.eliminarCita(id);
            return ResponseEntity.ok().body("Cita eliminada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar la cita: " + e.getMessage());
        }
    }

    // ========== ENDPOINTS DE CLIENTES ==========
    
    @GetMapping("/clientes")
    public List<ClienteModel> obtenerTodosLosClientes() {
        return clienteService.obtenerTodosLosClientes();
    }
    
    @GetMapping("/clientes/{id}")
    public ResponseEntity<ClienteModel> obtenerClientePorId(@PathVariable Long id) {
        Optional<ClienteModel> cliente = clienteService.obtenerClientePorId(id);
        return cliente.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/clientes/identificacion/{identificacion}")
    public ResponseEntity<ClienteModel> obtenerClientePorIdentificacion(@PathVariable Integer identificacion) {
        Optional<ClienteModel> cliente = clienteService.obtenerClientePorIdentificacion(identificacion);
        return cliente.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/clientes/buscar")
    public List<ClienteModel> buscarClientes(@RequestParam(required = false) String nombre,
                                           @RequestParam(required = false) String genero,
                                           @RequestParam(required = false) Integer edadMin,
                                           @RequestParam(required = false) Integer edadMax) {
        if (nombre != null) {
            return clienteService.buscarClientesPorNombre(nombre);
        } else if (genero != null) {
            return clienteService.buscarClientesPorGenero(genero);
        } else if (edadMin != null && edadMax != null) {
            return clienteService.buscarClientesPorRangoEdad(edadMin, edadMax);
        } else {
            return clienteService.obtenerTodosLosClientes();
        }
    }
    
    @PostMapping("/clientes")
    public ResponseEntity<?> crearCliente(@RequestBody ClienteModel cliente) {
        try {
            ClienteModel clienteGuardado = clienteService.guardarCliente(cliente);
            return ResponseEntity.ok(clienteGuardado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear el cliente: " + e.getMessage());
        }
    }
    
    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody ClienteModel cliente) {
        try {
            ClienteModel clienteActualizado = clienteService.actualizarCliente(id, cliente);
            return ResponseEntity.ok(clienteActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar el cliente: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        try {
            clienteService.eliminarCliente(id);
            return ResponseEntity.ok().body("Cliente eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar el cliente: " + e.getMessage());
        }
    }

    // ========== ENDPOINTS DE USUARIOS ==========
    
    @GetMapping("/usuarios")
    public List<UsuarioModel> obtenerTodosLosUsuarios() {
        return usuarioService.obtenerTodosLosUsuarios();
    }
    
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioModel> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<UsuarioModel> usuario = usuarioService.obtenerUsuarioPorId(id);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/usuarios/activos")
    public List<UsuarioModel> obtenerUsuariosActivos() {
        return usuarioService.obtenerUsuariosActivos();
    }
    
    @GetMapping("/usuarios/rol/{rol}")
    public List<UsuarioModel> obtenerUsuariosPorRol(@PathVariable String rol) {
        return usuarioService.obtenerUsuariosPorRol(rol);
    }
    
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioModel usuario) {
        try {
            UsuarioModel usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar el usuario: " + e.getMessage());
        }
    }
    
    @PutMapping("/usuarios/{id}/activar")
    public ResponseEntity<?> activarUsuario(@PathVariable Long id) {
        try {
            usuarioService.activarUsuario(id);
            return ResponseEntity.ok().body("Usuario activado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al activar el usuario: " + e.getMessage());
        }
    }
    
    @PutMapping("/usuarios/{id}/desactivar")
    public ResponseEntity<?> desactivarUsuario(@PathVariable Long id) {
        try {
            usuarioService.desactivarUsuario(id);
            return ResponseEntity.ok().body("Usuario desactivado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al desactivar el usuario: " + e.getMessage());
        }
    }

    // ========== ENDPOINTS DE ESTADÍSTICAS ==========
    
    @GetMapping("/estadisticas/citas")
    public CitaEstadisticas obtenerEstadisticasCitas() {
        return citaService.obtenerEstadisticas();
    }
    
    @GetMapping("/estadisticas/resumen")
    public ResponseEntity<?> obtenerResumenGeneral() {
        try {
            long totalClientes = clienteService.obtenerTodosLosClientes().size();
            long totalUsuarios = usuarioService.obtenerTodosLosUsuarios().size();
            long usuariosActivos = usuarioService.obtenerUsuariosActivos().size();
            CitaEstadisticas estadisticasCitas = citaService.obtenerEstadisticas();
            
            ResumenGeneral resumen = new ResumenGeneral(totalClientes, totalUsuarios, usuariosActivos, estadisticasCitas);
            return ResponseEntity.ok(resumen);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener estadísticas: " + e.getMessage());
        }
    }
    
    // Clase auxiliar para el resumen general
    public static class ResumenGeneral {
        private long totalClientes;
        private long totalUsuarios;
        private long usuariosActivos;
        private CitaEstadisticas citas;
        
        public ResumenGeneral(long totalClientes, long totalUsuarios, long usuariosActivos, CitaEstadisticas citas) {
            this.totalClientes = totalClientes;
            this.totalUsuarios = totalUsuarios;
            this.usuariosActivos = usuariosActivos;
            this.citas = citas;
        }
        
        // Getters
        public long getTotalClientes() { return totalClientes; }
        public long getTotalUsuarios() { return totalUsuarios; }
        public long getUsuariosActivos() { return usuariosActivos; }
        public CitaEstadisticas getCitas() { return citas; }
    }
}
