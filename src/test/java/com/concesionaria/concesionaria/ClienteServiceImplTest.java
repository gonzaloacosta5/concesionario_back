package com.concesionaria.concesionaria;

import com.concesionaria.controller.*;
import com.concesionaria.model.*;
import com.concesionaria.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FullAppFlowTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    @MockBean UsuarioService    usuarioService;
    @MockBean ClienteService    clienteService;
    @MockBean VehiculoService   vehiculoService;
    @MockBean PedidoService     pedidoService;
    @MockBean PagoService       pagoService;
    @MockBean ReporteService    reporteService;

    private Usuario mockUser;
    private Cliente mockCliente;
    private Vehiculo mockVehiculo;
    private Pedido mockPedido;

    @BeforeEach
    void setupMocks() {
        // — UsuarioService
        mockUser = new Usuario();
        mockUser.setId(99L);
        mockUser.setUsername("user1");
        when(usuarioService.register(any())).thenReturn(mockUser);
        when(usuarioService.authenticate(any())).thenReturn(mockUser);

        // — ClienteService
        mockCliente = new Cliente();
        mockCliente.setId(11L);
        mockCliente.setNombre("Test");
        mockCliente.setApellido("Client");
        when(clienteService.crearCliente(any())).thenReturn(mockCliente);
        when(clienteService.listarTodos()).thenReturn(List.of(mockCliente));
        when(clienteService.buscarPorId(11L)).thenReturn(mockCliente);

        // — VehiculoService
        mockVehiculo = new Vehiculo();
        mockVehiculo.setId(22L);
        mockVehiculo.setMarca("VW");
        mockVehiculo.setModelo("Gol");
        mockVehiculo.setColor("Rojo");
        mockVehiculo.setChasis("CHX");
        mockVehiculo.setMotor("MX1");
        mockVehiculo.setPrecioBase(20000.0);
        mockVehiculo.setTipo(TipoVehiculo.CAMIONETA);
        when(vehiculoService.crearVehiculo(any())).thenReturn(mockVehiculo);
        when(vehiculoService.listarTodos()).thenReturn(List.of(mockVehiculo));
        when(vehiculoService.buscarPorId(22L)).thenReturn(mockVehiculo);

        // — PedidoService
        mockPedido = new Pedido();
        mockPedido.setId(33L);
        mockPedido.setNumeroPedido("ORD-1");
        mockPedido.setCliente(mockCliente);
        mockPedido.setVehiculo(mockVehiculo);
        mockPedido.setFormaPago(FormaPago.CONTADO);
        mockPedido.setTotal(20000.0 * 1.17); // 17%
        mockPedido.setFechaCreacion(LocalDateTime.now());

        when(pedidoService.crearPedido(any())).thenReturn(mockPedido);
        when(pedidoService.buscarPorId(33L)).thenReturn(mockPedido);
        when(pedidoService.listarTodos()).thenReturn(List.of(mockPedido));

        // avanzarEstado muta el historial interno
        when(pedidoService.avanzarEstado(eq(33L), eq("VENTAS")))
            .thenAnswer(inv -> {
                mockPedido.getHistorial().add(new HistorialEstado(null, EstadoPedido.VENTAS, mockPedido));
                return mockPedido;
            });

        // — PagoService
        PagoContado pc = new PagoContado();   pc.setId(44L);
        PagoTransferencia pt = new PagoTransferencia(); pt.setId(55L);
        PagoTarjeta pta = new PagoTarjeta();  pta.setId(66L);
        when(pagoService.registrarPagoContado(eq(33L), any())).thenReturn(pc);
        when(pagoService.registrarPagoTransferencia(eq(33L), any())).thenReturn(pt);
        when(pagoService.registrarPagoTarjeta(eq(33L), any())).thenReturn(pta);
        when(pagoService.listarPagosPorPedido(33L)).thenReturn(List.of(pc, pt, pta));

        // — ReporteService (usamos any() para el String, admite null)
        when(reporteService.generarReportePedidos(any(LocalDate.class), any()))
            .thenReturn(List.of(mockPedido));
        when(reporteService.calcularTotales(any(LocalDate.class), any(LocalDate.class), anyBoolean()))
            .thenReturn(Map.of(FormaPago.CONTADO, mockPedido.getTotal() * 3));
        when(reporteService.exportPedidosCsv(any(LocalDate.class), any(LocalDate.class), any()))
            .thenReturn("numeroPedido\nORD-1".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void fullEndToEndHappyPath() throws Exception {
        // 1) AUTH REGISTER
        mvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"user1\",\"password\":\"pass1\"}"))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.id").value(99))
           .andExpect(jsonPath("$.username").value("user1"))
           .andExpect(jsonPath("$.password").doesNotExist());

        // 2) AUTH LOGIN
        mvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"user1\",\"password\":\"pass1\"}"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(99));

        // 3) CLIENTES
        mvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Test\",\"apellido\":\"Client\",\"documento\":\"DNI100\",\"email\":\"cli@mail\",\"telefono\":\"000\"}"))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.id").value(11));
        mvc.perform(get("/api/clientes"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").value(11));

        // 4) VEHICULOS
        mvc.perform(post("/api/vehiculos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"marca\":\"VW\",\"modelo\":\"Gol\",\"color\":\"Rojo\",\"chasis\":\"CHX\",\"motor\":\"MX1\",\"precioBase\":20000.0,\"tipo\":\"CAMIONETA\"}"))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.id").value(22));
        mvc.perform(get("/api/vehiculos"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").value(22));

        // 5) PEDIDOS
        mvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numeroPedido\":\"ORD-1\",\"cliente\":{\"id\":11},\"vehiculo\":{\"id\":22},\"formaPago\":\"CONTADO\"}"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(33))
           .andExpect(jsonPath("$.total").value(20000.0 * 1.17));
        mvc.perform(get("/api/pedidos/33/historial"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.length()").value(0));
        mvc.perform(put("/api/pedidos/33/estado").param("nuevoEstado","VENTAS"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.historial[0].estado").value("VENTAS"));

        // 6) PAGOS
        mvc.perform(post("/api/pedidos/33/pagos/contado")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
           .andExpect(status().isCreated());
        mvc.perform(post("/api/pedidos/33/pagos/transferencia")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
           .andExpect(status().isCreated());
        mvc.perform(post("/api/pedidos/33/pagos/tarjeta")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
           .andExpect(status().isCreated());
        mvc.perform(get("/api/pedidos/33/pagos"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.length()").value(3));

        // 7) REPORTES
        mvc.perform(get("/api/reportes/pedidos")
                .param("desde", LocalDate.now().toString()))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].numeroPedido").value("ORD-1"));
        mvc.perform(get("/api/reportes/totales")
                .param("desde", LocalDate.now().toString())
                .param("hasta", LocalDate.now().toString()))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.CONTADO").value(20000.0 * 1.17 * 3));

        byte[] csv = mvc.perform(get("/api/reportes/pedidos/csv")
                .param("desde", LocalDate.now().toString())
                .param("hasta", LocalDate.now().toString()))
           .andExpect(status().isOk())
           .andExpect(header().string("Content-Disposition","attachment; filename=\"reporte_pedidos.csv\""))
           .andReturn().getResponse().getContentAsByteArray();
        assertThat(new String(csv, StandardCharsets.UTF_8))
            .contains("ORD-1","numeroPedido");
    }
}
