package pl.po.core.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountUnitTest {

    private Account account;
    final private double startAmount = 1000.00;

    @Before
    public void setUp() throws Exception {
        final Client owner = new Client(1L, "John", "Doe");
        account = new Account(1L, owner, startAmount, Currency.getInstance("PLN"));
    }

    @Test
    public void changeAmountByPositiveValueTest_shouldReturnChangedAmount() {
        final double amount = 116.20;
        assertThat(account.changeAmount(amount)).isEqualTo(startAmount + amount);
    }

    @Test
    public void changeAmountByNegativeValueTest_shouldReturnChangedAmount() {
        final double amount = -115.30;
        assertThat(account.changeAmount(amount)).isEqualTo(startAmount + amount);
    }
}
