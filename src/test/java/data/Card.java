package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Card {
    private String number;
    private String month;
    private String year;
    private String holderName;
    private String cvc;
}



