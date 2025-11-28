package com.carrie.hazellabev2.services;

import java.util.List;
import java.util.Date;
import com.carrie.hazellabev2.entities.Boleta;

public interface BoletaService {
    
    /* ---------------------------------- CRUD básico ---------------------------------- */
    Boleta crear(Boleta boleta);
    Boleta obtenerPorID(Long id);
    List<Boleta> listarTodo();
    void eliminar(Long id);
    
    /* ---------------------------------- Negocio ---------------------------------- */
    Boleta generarBoleta(Long usuarioId, String metodoPago, String metodoEnvio);
    List<Boleta> obtenerHistorialPorUsuario(Long usuarioId);
    Boleta obtenerPorNumero(String numeroBoleta);
    Boleta anularBoleta(Long id);
    
    /* ---------------------------------- Reportes ---------------------------------- */
    Integer obtenerTotalVentasPorPeriodo(Date fechaInicio, Date fechaFin);
    List<Boleta> obtenerBoletasPorPeriodo(Date fechaInicio, Date fechaFin);
}