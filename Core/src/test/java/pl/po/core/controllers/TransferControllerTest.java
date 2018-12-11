package pl.po.core.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.po.core.services.TransferService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TransferController.class)
public class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransferService transferService;

    final private Long sourceAccountId = 1L;
    final private Long destinationAccountId = 2L;
    final private double amount = 120.10;

    @Test
    public void execTransferWithValidData() throws Exception {
        Mockito.when(transferService.isExecutable(sourceAccountId, destinationAccountId, amount))
                .thenReturn(true);

        mockMvc.perform(post("/transfer/exec")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("sourceAccountId", sourceAccountId.toString())
                .param("destinationAccountId", destinationAccountId.toString())
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk());
    }

    @Test
    public void execTransferWithInvalidData() throws Exception {
        Mockito.when(transferService.isExecutable(sourceAccountId, destinationAccountId, amount))
                .thenReturn(false);

        mockMvc.perform(post("/transfer/exec")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("sourceAccountId", sourceAccountId.toString())
                .param("destinationAccountId", destinationAccountId.toString())
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isNotAcceptable());
    }
}
