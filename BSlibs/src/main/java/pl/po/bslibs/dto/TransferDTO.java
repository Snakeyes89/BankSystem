package pl.po.bslibs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class TransferDTO {

    private Long id;

    private double amount;

    private AccountDTO source;

    private AccountDTO destination;

    private Date date;
}
