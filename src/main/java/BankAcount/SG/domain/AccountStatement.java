package BankAcount.SG.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter @AllArgsConstructor
public class AccountStatement {
	
	    private Type type;
	    private BigDecimal amount;
	    private LocalDate localDate;
	    private BigDecimal balance ;

	    public enum Type {
	        DEPOSIT,
	        WITHDRAWAL
	    }

}
