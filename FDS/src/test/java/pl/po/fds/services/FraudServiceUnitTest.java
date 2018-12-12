package pl.po.fds.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.po.bslibs.dto.AccountDTO;
import pl.po.bslibs.dto.ClientDTO;
import pl.po.bslibs.dto.TransferDTO;

import java.util.Currency;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FraudServiceUnitTest {

    @Autowired
    private FraudService fraudService;

    private AccountDTO source;
    private AccountDTO destination;
    private TransferDTO transfer;

    @Before
    public void setUp() throws Exception {
        final ClientDTO clientSource = new ClientDTO(1L, "John", "Doe", null);
        final ClientDTO clientDestination = new ClientDTO(2L, "Jane", "Doe", null);
        source = new AccountDTO(1L, clientSource, 1000.00, Currency.getInstance("PLN"));
        destination = new AccountDTO(2L, clientDestination, 2000.00, Currency.getInstance("PLN"));
        transfer = new TransferDTO(1L, 190.56, source, destination, new Date());
    }

    @Test
    public void isTransferSuspiciousWithAllNormalAndValidData_shoutReturnFalse() {
        assertThat(fraudService.isSuspicious(transfer)).isFalse();
    }

    @Test
    public void isTransferSuspiciousWithVeryBigAmount_shouldReturnTrue() {
        transfer.setAmount(15000.00);
        assertThat(fraudService.isSuspicious(transfer)).isTrue();
    }

    @Test
    public void isTransferSuspiciousWithExoticCurrency_shouldReturnTrue() {
        source.setCurrency(Currency.getInstance("NGN"));
        destination.setCurrency(Currency.getInstance("NGN"));
        assertThat(fraudService.isSuspicious(transfer)).isTrue();
    }
}
