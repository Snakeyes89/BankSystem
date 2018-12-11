package pl.po.core.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.po.core.domain.Account;
import pl.po.core.domain.Client;
import pl.po.core.domain.Transfer;
import pl.po.core.repositories.TransferRepository;

import java.util.Currency;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransferServiceUnitTest {

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransferService transferService = new TransferServiceImpl(transferRepository, accountService);

    private Account source;
    private Account destination;
    private Transfer transfer;

    @Before
    public void setUp() throws Exception {
        final Client clientSource = new Client(1L, "John", "Doe");
        final Client clientDestination = new Client(2L, "Jane", "Doe");

        source = new Account(1L, clientSource, 1000.00, Currency.getInstance("PLN"));
        destination = new Account(2L, clientDestination, 2000.00, Currency.getInstance("PLN"));

        transfer = new Transfer(100.00, source, destination, new Date());

        Mockito.when(accountService.get(source.getId())).thenReturn(source);
        Mockito.when(accountService.get(destination.getId())).thenReturn(destination);
    }

    @Test
    public void executeTransferWithValidData_shouldReturnTrue() {
        assertThat(transferService.execute(source.getId(), destination.getId(), transfer.getAmount())).isTrue();
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeTransferWithSameSourceAndDestiantionAccounts_shouldThrowException() {
        transferService.isExecutable(1L, 1L, 100.00);
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeTransferWithDifferentCurrency_shouldThrowException() {
        destination.setCurrency(Currency.getInstance("USD"));
        transferService.execute(source.getId(), destination.getId(), transfer.getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeTransferWithNotEnoughFundsOnSourceSide_shouldThrowException() {
        transfer.setAmount(1100.00);

        Mockito.when(accountService.changeFundsAmount(source, -transfer.getAmount()))
                .thenThrow(IllegalArgumentException.class);

        transferService.execute(source.getId(), destination.getId(), transfer.getAmount());
    }

    @Test
    public void isTransferExecutableWithValidData_shouldReturnTrue() {
        assertThat(transferService.isExecutable(source.getId(), destination.getId(), 100.00)).isTrue();
    }

    @Test(expected = IllegalArgumentException.class)
    public void isTransferExecutableWithSameSourceAndDestinationAccounts_shouldThrowException() {
        transferService.isExecutable(1L, 1L, 100.00);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isTransferExecutableWithDifferentCurrency_shouldThrowException() {
        destination.setCurrency(Currency.getInstance("USD"));
        transferService.isExecutable(source.getId(), destination.getId(), 200.00);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isTransferExecutableWithNotEnoughFundsOnSourceSide_shouldThrowException() {
        transfer.setAmount(1100.00);

        Mockito.when(accountService.changeFundsAmount(source, -transfer.getAmount()))
                .thenThrow(IllegalArgumentException.class);

        transferService.execute(source.getId(), destination.getId(), transfer.getAmount());
    }
}
