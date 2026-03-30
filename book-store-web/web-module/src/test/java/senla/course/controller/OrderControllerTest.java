package senla.course.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import senla.course.dto.OrderDto;
import senla.course.dto.RequestDto;
import senla.course.exception.BookNotFoundException;
import senla.course.exception.ListOrderEmptyException;
import senla.course.exception.ListRequestEmptyException;
import senla.course.exception.OrderNotFoundException;
import senla.course.service.ServiceManager;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test OrderController")
public class OrderControllerTest {

    @Mock
    ServiceManager serviceManager;

    @InjectMocks
    OrderController orderController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).setControllerAdvice(GlobalExceptionHandler.class).build();
    }

    @Test
    @DisplayName("Given create order When correct book id Then get status ok")
    public void createOrder_correctBookId_getStatusOk() throws Exception {

        int id = 2;
        String email = "emailTest";

        doNothing().when(serviceManager).createOrder(2, "emailTest");
        mockMvc.perform(post("/order/create/{bookId}/{emailUser}", id, "emailTest")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given create order When book with specified not found Then get status not found")
    public void createOrder_bookWithSpecifiedNotFound_getStatusNotFound() throws Exception {

        int id = 999;
        String emailForTest = "emailTest";

        doThrow(BookNotFoundException.class).when(serviceManager).createOrder(999, "emailTest");
        mockMvc.perform(post("/order/create/{bookId}/{emailUser}", id, emailForTest)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Given closed order When correct orderId Then get status ok")
    public void closedOrder_correctOrderId_getStatusOk() throws Exception {

        int id = 2;
        doNothing().when(serviceManager).closedOrder(2);

        mockMvc.perform(post("/order/closed/{id}", id)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given closed order When order with specify id not found Then get status not found")
    public void closedOrder_orderWithSpecifyIdNotFound_getStatusNotFound() throws Exception {

        int id = 999;
        doThrow(OrderNotFoundException.class).when(serviceManager).closedOrder(999);

        mockMvc.perform(post("/order/closed/{id}", id)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Given complected order When correct orderId Then get status ok")
    public void complectedOrder_correctOrderId_getStatusOk() throws Exception {

        int id = 2;
        doNothing().when(serviceManager).complectedOrder(2);

        mockMvc.perform(post("/order/complected/{id}", id)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given complected order When order with specify id not found Then get status not found")
    public void complectedOrder_orderWithSpecifyIdNotFound_getStatusNotFound() throws Exception {

        int id = 999;
        doThrow(OrderNotFoundException.class).when(serviceManager).complectedOrder(999);

        mockMvc.perform(post("/order/complected/{id}", id)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Given get info order When correct order id Then get status ok and orderDto")
    public void getInfoOrder_correctOrderId_getStatusOkAndOrderDto() throws Exception {

        int id = 1;

        OrderDto order = new OrderDto(1, "emailTest");
        order.setId(id);
        when(serviceManager.getInfoOrder(1)).thenReturn(order);

        mockMvc.perform(get("/order/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.bookId").value(1))
                .andExpect(jsonPath("$.emailUser").value("emailTest"));
    }

    @Test
    @DisplayName("Given get info order When order with specify id Then get status not found")
    public void getInfoOrder_orderWithSpecifyIdNotFound_getStatusNotFound() throws Exception {

        int id = 999;

        doThrow(OrderNotFoundException.class).when(serviceManager).getInfoOrder(999);
        mockMvc.perform(get("/order/{id}", id)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Given get all orders When list order not empty Then get status ok and list order")
    public void getInfoAllOrder_listOrderNotEmpty_getStatusOkAndListOrder() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        List<OrderDto> expectedOrders = new ArrayList<>();
        expectedOrders.add(new OrderDto());

        when(serviceManager.getAllOrder("Test")).thenReturn(expectedOrders);

        MvcResult mvcResult = mockMvc.perform(get("/order/all/{criteria}", "Test"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        List<OrderDto> ordersResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<OrderDto>>() {});

        Assertions.assertEquals(expectedOrders.size(), ordersResponse.size());

        for (int i = 0; i < ordersResponse.size(); i++) {
            Assertions.assertEquals(expectedOrders.get(i).getId(), ordersResponse.get(i).getId());
        }
    }

    @Test
    @DisplayName("Given get all orders When list order empty Then get status not found")
    public void getInfoAllOrder_listOrderEmpty_getStatusNotFound() throws Exception {

        List<RequestDto> requests = new ArrayList<>();

        doThrow(new ListRequestEmptyException()).when(serviceManager).getAllOrder("Test");

        mockMvc.perform(get("/order/all/{criteria}", "Test"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Given get complected orders When list complected order not empty Then get status ok and list complected order")
    public void getComplectedOrder_listComplectedOrderNotEmpty_getStatusOkAndListComplectedOrder() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        List<OrderDto> complectedOrder = new ArrayList<>();
        complectedOrder.add(new OrderDto());

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);

        when(serviceManager.getComplectedOrder(startDate, endDate, "Test")).thenReturn(complectedOrder);

        MvcResult mvcResult = mockMvc.perform(get("/order/complected-all/{startDate}/{endDate}/{criteria}", startDate, endDate, "Test"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        List<OrderDto> ordersResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<OrderDto>>() {});

        Assertions.assertEquals(complectedOrder.size(), ordersResponse.size());

        for (int i = 0; i < ordersResponse.size(); i++) {
            Assertions.assertEquals(complectedOrder.get(i).getId(), ordersResponse.get(i).getId());
        }
    }

    @Test
    @DisplayName("Given get complected orders When list complected order empty Then get status not found")
    public void getComplectedOrder_listComplectedOrderEmpty_getStatusNotFound() throws Exception {

        List<RequestDto> requests = new ArrayList<>();

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);

        when(serviceManager.getComplectedOrder(startDate, endDate, "Test")).thenThrow(ListOrderEmptyException.class);

        mockMvc.perform(get("/order/complected-all/{startDate}/{endDate}/{criteria}", startDate, endDate, "Test"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Given get count complected orders When list complected order not empty Then get status ok and count complected order")
    public void getCountComplectedOrder_listComplectedOrderNotEmpty_getStatusOkAndCountComplectedOrder() throws Exception {

        long countComplectedOrder = 4L;

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);

        when(serviceManager.getCountComplectedOrder(startDate, endDate)).thenReturn(4L);

        mockMvc.perform(get("/order/complected-count/{startDate}/{endDate}", startDate, endDate))
                .andExpect(jsonPath("$").value(countComplectedOrder));
    }

    @Test
    @DisplayName("Given get count complected orders When list complected order not empty Then get status ok and zero")
    public void getCountComplectedOrder_listComplectedOrderNotEmpty_getStatusOkAndZero() throws Exception {

        long countComplectedOrder = 0L;

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);

        when(serviceManager.getCountComplectedOrder(startDate, endDate)).thenReturn(0L);

        mockMvc.perform(get("/order/complected-count/{startDate}/{endDate}", startDate, endDate))
                .andExpect(jsonPath("$").value(countComplectedOrder));
    }

    @Test
    @DisplayName("Given get profit When list complected order not empty Then get status ok and profit")
    public void getProfit_listComplectedOrderNotEmpty_getStatusOkAndProfit() throws Exception {

        BigDecimal profit = BigDecimal.valueOf(1000);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);

        when(serviceManager.getProfit(startDate, endDate)).thenReturn(BigDecimal.valueOf(1000));

        mockMvc.perform(get("/order/profit/{startDate}/{endDate}", startDate, endDate))
                .andExpect(jsonPath("$").value(profit));
    }

    @Test
    @DisplayName("Given get profit When list complected order empty Then get status ok and zero")
    public void getProfit_listComplectedOrderEmpty_getStatusOkAndZero() throws Exception {

        BigDecimal profit = BigDecimal.ZERO;

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);

        when(serviceManager.getProfit(startDate, endDate)).thenReturn(BigDecimal.ZERO);

        mockMvc.perform(get("/order/profit/{startDate}/{endDate}", startDate, endDate))
                .andExpect(jsonPath("$").value(profit));
    }
}
