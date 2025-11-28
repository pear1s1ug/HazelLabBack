package com.carrie.hazellabev2.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carrie.hazellabev2.entities.Boleta;
import com.carrie.hazellabev2.entities.DetalleBoleta;
import com.carrie.hazellabev2.entities.ItemCarrito;
import com.carrie.hazellabev2.entities.Producto;
import java.util.Date;
import com.carrie.hazellabev2.entities.Usuario;
import com.carrie.hazellabev2.repositories.BoletaRepository;
import com.carrie.hazellabev2.repositories.DetalleBoletaRepository;
import com.carrie.hazellabev2.repositories.ItemCarritoRepository;
import com.carrie.hazellabev2.repositories.ProductoRepository;
import com.carrie.hazellabev2.repositories.UsuarioRepository;

@Service
public class BoletaServiceImpl implements BoletaService {
    
    @Autowired
    private BoletaRepository boletaRepository;
    
    @Autowired
    private DetalleBoletaRepository detalleBoletaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private ItemCarritoRepository itemsCarritoRepository;

    /* ================= OPERACIONES CRUD BÁSICAS ================= */
    
    @Override
    public Boleta crear(Boleta boleta) {
        return boletaRepository.save(boleta);
    }

    @Override
    public Boleta obtenerPorID(Long id) {
        return boletaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Boleta no encontrada."));
    }

    @Override
    public List<Boleta> listarTodo() {
        return boletaRepository.findAll();
    }

    @Override
    public void eliminar(Long id) {
        if (!boletaRepository.existsById(id)) {
            throw new RuntimeException("Boleta no encontrada.");
        } 
        boletaRepository.deleteById(id);
    }

    /* ================= OPERACIONES ESPECÍFICAS DE NEGOCIO ================= */
    
    @Override
    public Boleta generarBoleta(Long usuarioId, String metodoPago, String metodoEnvio) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
        // Obtener items del carrito
        List<ItemCarrito> itemsCarrito = itemsCarritoRepository.findByUsuarioId(usuarioId);
        
        if (itemsCarrito.isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }
        
        // Calcular totales
        Integer subtotal = calcularSubtotal(itemsCarrito);
        Integer iva = (int) (subtotal * 0.19); // 19% IVA Chile
        Integer total = subtotal + iva;
        
        // Generar número de boleta correlativo
        String numeroBoleta = generarNumeroBoleta();
        
        // Crear boleta
        Boleta boleta = new Boleta();
        boleta.setNumeroBoleta(numeroBoleta);
        boleta.setFechaEmision(new Date());
        boleta.setUsuario(usuario);
        boleta.setSubtotal(subtotal);
        boleta.setIva(iva);
        boleta.setTotal(total);
        boleta.setMetodoPago(metodoPago);
        boleta.setMetodoEnvio(metodoEnvio);
        boleta.setEstado("PAGADA");
        
        Boleta boletaGuardada = boletaRepository.save(boleta);
        
        // Crear detalles
        List<DetalleBoleta> detalles = new ArrayList<>();
        for (ItemCarrito item : itemsCarrito) {
            DetalleBoleta detalle = new DetalleBoleta();
            detalle.setBoleta(boletaGuardada);
            detalle.setProducto(item.getProducto());
            detalle.setCantidad(item.getQuantity());
            detalle.setPrecioUnitario(item.getProducto().getCost());
            detalle.setTotal(item.getProducto().getCost() * item.getQuantity());
            
            detalles.add(detalle);
        }
        
        detalleBoletaRepository.saveAll(detalles);
        
        // Limpiar carrito
        itemsCarritoRepository.deleteAll(itemsCarrito);
        
        return boletaGuardada;
    }
    
    @Override
    public List<Boleta> obtenerHistorialPorUsuario(Long usuarioId) {
        return boletaRepository.findByUsuarioIdOrderByFechaEmisionDesc(usuarioId);
    }
    
    @Override
    public Boleta obtenerPorNumero(String numeroBoleta) {
        return boletaRepository.findByNumeroBoleta(numeroBoleta)
            .orElseThrow(() -> new RuntimeException("Boleta no encontrada."));
    }
    
    @Override
    public Boleta anularBoleta(Long id) {
        Boleta boleta = obtenerPorID(id);
        boleta.setEstado("ANULADA");
        return boletaRepository.save(boleta);
    }

    /* ================= SISTEMA DE REPORTES ================= */
    
    @Override
    public Integer obtenerTotalVentasPorPeriodo(Date fechaInicio, Date fechaFin) {
        List<Boleta> boletas = boletaRepository.findAll();
        return boletas.stream()
            .filter(b -> !b.getFechaEmision().before(fechaInicio) && !b.getFechaEmision().after(fechaFin))
            .filter(b -> "PAGADA".equals(b.getEstado()))
            .mapToInt(Boleta::getTotal)
            .sum();
    }
    
    @Override
    public List<Boleta> obtenerBoletasPorPeriodo(Date fechaInicio, Date fechaFin) {
        List<Boleta> boletas = boletaRepository.findAll();
        return boletas.stream()
            .filter(b -> !b.getFechaEmision().before(fechaInicio) && !b.getFechaEmision().after(fechaFin))
            .toList();
    }

    /* ================= MÉTODOS PRIVADOS AUXILIARES ================= */
    
    private Integer calcularSubtotal(List<ItemCarrito> items) {
        return items.stream()
            .mapToInt(item -> item.getProducto().getCost() * item.getQuantity())
            .sum();
    }
    
    private String generarNumeroBoleta() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Optional<Integer> maxNumero = boletaRepository.findMaxNumeroByYear(year);
        
        int siguienteNumero = maxNumero.orElse(0) + 1;
        return String.format("B%03d-%d", siguienteNumero, year);
    }
}