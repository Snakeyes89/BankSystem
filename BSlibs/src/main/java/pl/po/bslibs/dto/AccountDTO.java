package pl.po.bslibs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Currency;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AccountDTO {

    private Long id;

    @JsonIgnoreProperties("accounts")
    private ClientDTO owner;

    private double amount;

    private Currency currency;
}
