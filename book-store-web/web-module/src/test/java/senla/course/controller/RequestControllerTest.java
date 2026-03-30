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
import senla.course.dto.RequestDto;
import senla.course.exception.ListRequestEmptyException;
import senla.course.service.ServiceManager;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test RequestController")
@ExtendWith(MockitoExtension.class)
public class RequestControllerTest {

    @Mock
    ServiceManager serviceManager;

    @InjectMocks
    RequestController requestController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(requestController).setControllerAdvice(GlobalExceptionHandler.class).build();
    }

    @Test
    @DisplayName("Given get info all request When list request not empty Then get status ok and list request")
    public void getInfoAllRequest_listRequestNotEmpty_getStatusOkAndListRequest() throws Exception {

        List<RequestDto> requestsDto = new ArrayList<RequestDto>();
        requestsDto.add(new RequestDto());

        when(serviceManager.getAllRequest("Test")).thenReturn(requestsDto);

        MvcResult mvcResult = mockMvc.perform(get("/request/all/{criteria}", "Test"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        List<RequestDto> requestsActual = objectMapper.readValue(jsonResponse, new TypeReference<List<RequestDto>>() {});

        Assertions.assertEquals(requestsDto.size(), requestsActual.size());

        for (int i = 0; i < requestsDto.size(); i++) {
            Assertions.assertEquals(requestsDto.get(i).getId(), requestsActual.get(i).getId());
        }
    }

    @Test
    @DisplayName("Given get info all request When list request not empty Then get status not found")
    public void getInfoAllRequest_listRequestEmpty_getStatusNotFound() throws Exception {

        List<RequestDto> requests = new ArrayList<>();

        doThrow(new ListRequestEmptyException()).when(serviceManager).getAllRequest("Test");

        mockMvc.perform(get("/request/all/{criteria}", "Test"))
                .andExpect(status().isNotFound());
    }
}
