package pl.po.bslibs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ClientDTO {

    private Long id;

    private String firstName;

    private String surname;

    @JsonIgnoreProperties("owner")
    private List<AccountDTO> accounts;
}
