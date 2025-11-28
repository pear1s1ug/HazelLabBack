package com.carrie.hazellabev2.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.carrie.hazellabev2.dto.GenerarBoletaRequest;
import com.carrie.hazellabev2.entities.Boleta;
import com.carrie.hazellabev2.services.BoletaService;

@RestController
@RequestMapping("/api/boletas")
@CrossOrigin(origins = "*")
public class BoletaController {
    
    @Autowired
    private BoletaService boletaService;
    
    @PostMapping("/generar")
    public ResponseEntity<?> generarBoleta(@RequestBody GenerarBoletaRequest request) {
        try {
            Boleta boleta = boletaService.generarBoleta(
                request.getUsuarioId(), 
                request.getMetodoPago(), 
                request.getMetodoEnvio()
            );
            return ResponseEntity.ok(boleta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // ✅ NUEVO ENDPOINT - OBTENER TODAS LAS BOLETAS
    @GetMapping("/listar")
    public ResponseEntity<List<Boleta>> obtenerTodasLasBoletas() {
        try {
            List<Boleta> boletas = boletaService.listarTodo();
            return ResponseEntity.ok(boletas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Boleta>> obtenerHistorial(@PathVariable Long usuarioId) {
        List<Boleta> boletas = boletaService.obtenerHistorialPorUsuario(usuarioId);
        return ResponseEntity.ok(boletas);
    }
    
    @GetMapping("/{numeroBoleta}")
    public ResponseEntity<Boleta> obtenerBoleta(@PathVariable String numeroBoleta) {
        try {
            Boleta boleta = boletaService.obtenerPorNumero(numeroBoleta);
            return ResponseEntity.ok(boleta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/anular")
    public ResponseEntity<Boleta> anularBoleta(@PathVariable Long id) {
        try {
            Boleta boleta = boletaService.anularBoleta(id);
            return ResponseEntity.ok(boleta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/reportes/ventas")
    public ResponseEntity<Integer> obtenerTotalVentas(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
        Integer total = boletaService.obtenerTotalVentasPorPeriodo(fechaInicio, fechaFin);
        return ResponseEntity.ok(total);
    }
}