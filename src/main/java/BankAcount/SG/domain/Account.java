package BankAcount.SG.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Account {
	   private String accountId; 
	   private BigDecimal balance =BigDecimal.ZERO;
	    private List<AccountStatement> statements = new ArrayList<>();

}
