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
import pl.po.core.repositories.AccountRepository;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceUnitTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService = new AccountServiceImpl(accountRepository);

    private Account account;

    @Before
    public void setUp() throws Exception {
        final Client owner = new Client(1L, "John", "Doe");
        account = new Account(1L, owner, 1000.00, Currency.getInstance("PLN"));

        Mockito.when(accountRepository.getOne(account.getId())).thenReturn(account);
        Mockito.when(accountRepository.save(account)).thenReturn(account);
    }

    @Test
    public void changeFundsAmountByAddingMoreFunds_shouldReturnTrue() {
        final double amount = 100.00;
        assertThat(accountService.changeFundsAmount(account, amount)).isTrue();
    }

    @Test
    public void changeFundsAmountBySubtractingFundsWithEnoughFunds_shouldReturnTrue() {
        final double amount = -160.00;
        assertThat(accountService.changeFundsAmount(account, amount)).isTrue();
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeFundsAmountBySubtractingFundsWithNotEnoughFunds_shouldThrowException() {
        final double amount = -1100.00;
        accountService.changeFundsAmount(account, amount);
    }

    @Test
    public void isChangeFundsAmountPossibleWithValidData_shouldReturnTrue() {
        assertThat(accountService.isChangeFundsAmountPossible(account, 50.00)).isTrue();
    }

    @Test(expected = IllegalArgumentException.class)
    public void isChangeFundsAmountPossibleWithZeroAmount_shouldThrowException() {
        accountService.isChangeFundsAmountPossible(account, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isChangeFundsAmountPossibleWithNotEnoughFunds_shouldThrowException() {
        accountService.isChangeFundsAmountPossible(account, -1200.00);
    }
}
