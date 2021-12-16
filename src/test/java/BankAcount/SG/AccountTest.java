package BankAcount.SG;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import BankAcount.SG.domain.Account;
import BankAcount.SG.domain.AccountStatement;
import BankAcount.SG.service.BankingService;

import static org.junit.jupiter.api.Assertions.*; 
 
public class AccountTest {
	
	
    private Account account;
    private BankingService service; 

    @BeforeEach
    public void init()
    {
     account = new Account(); 
     service = new BankingService(account); 
    }
    
    @Test
    void depositException() {
        BigDecimal amountNull = null;
        BigDecimal amountZero= BigDecimal.ZERO ;
        BigDecimal amountNegative= new BigDecimal(-1) ;

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> service.deposit(amountNull) ,
                "Amount Not Valid"
        );

        RuntimeException thrown2 = assertThrows(
                RuntimeException.class,
                () -> service.deposit(amountZero),
                "Amount Not Valid"
        );

        RuntimeException thrown3 = assertThrows(
                RuntimeException.class,
                () -> service.deposit(amountNegative) ,
                "Amount Not Valid"
        );

        Assertions.assertEquals("Amount Not Valid",thrown.getMessage());
        Assertions.assertEquals("Amount Not Valid",thrown2.getMessage());
        Assertions.assertEquals("Amount Not Valid",thrown3.getMessage());
    }

    @Test
    void deposit() {
        BigDecimal amount = new BigDecimal(500) ;
        service.deposit(amount) ;
        Assertions.assertEquals(account.getBalance(), amount);
        Assertions.assertEquals(account.getStatements().size(), 1);
        Assertions.assertEquals(account.getStatements().get(0).getAmount(), amount);
        Assertions.assertEquals(account.getStatements().get(0).getType(), AccountStatement.Type.DEPOSIT);
        Assertions.assertEquals(account.getStatements().get(0).getBalance(), amount);
    }


    @Test
    void withdrawalException() {
        BigDecimal amountNull = null;
        BigDecimal amountZero= BigDecimal.ZERO ;
        BigDecimal amountNegative= new BigDecimal(-1) ;

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> service.withdrawal(amountNull) ,
                "Amount Not Valid"
        );

        RuntimeException thrown2 = assertThrows(
                RuntimeException.class,
                () -> service.withdrawal(amountZero),
                "Amount Not Valid"
        );

        RuntimeException thrown3 = assertThrows(
                RuntimeException.class,
                () -> service.withdrawal(amountNegative) ,
                "Amount Not Valid"
        );

        Assertions.assertEquals("Amount Not Valid",thrown.getMessage());
        Assertions.assertEquals("Amount Not Valid",thrown2.getMessage());
        Assertions.assertEquals("Amount Not Valid",thrown3.getMessage());
    }

    @Test
    void withdrawal() {
        account.setBalance(new BigDecimal(500));
        BigDecimal amount = new BigDecimal(200) ;
        BigDecimal expectedResult = new BigDecimal(300);
        service.withdrawal(amount) ;
        Assertions.assertEquals(account.getBalance(), expectedResult);
        Assertions.assertEquals(account.getStatements().size(), 1);
        Assertions.assertEquals(account.getStatements().get(0).getAmount(), amount);
        Assertions.assertEquals(account.getStatements().get(0).getType(), AccountStatement.Type.WITHDRAWAL);
        Assertions.assertEquals(account.getStatements().get(0).getBalance(), expectedResult);
    }

    @Test
    void withdrawalWithInsufficientBalance() {
        BigDecimal amount= new BigDecimal(200) ;
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> service.withdrawal(amount) ,
                "Insufficient balance"
        );
        Assertions.assertEquals("Insufficient balance",thrown.getMessage());
    }

    @Test
    void printStatements() {
        
        BigDecimal amount = new BigDecimal(200) ;
        service.deposit(amount);
        service.deposit(amount);
        service.withdrawal(amount);
        service.withdrawal(amount);

        Assertions.assertEquals(account.getStatements().size(), 4);
        Assertions.assertEquals(account.getStatements().get(0).getAmount(), amount);
        Assertions.assertEquals(account.getStatements().get(0).getType(), AccountStatement.Type.DEPOSIT);
        Assertions.assertEquals(account.getStatements().get(0).getBalance(), amount);

        Assertions.assertEquals(account.getStatements().get(1).getAmount(), amount);
        Assertions.assertEquals(account.getStatements().get(1).getType(), AccountStatement.Type.DEPOSIT);
        Assertions.assertEquals(account.getStatements().get(1).getBalance(), new BigDecimal(400));

        Assertions.assertEquals(account.getStatements().get(2).getAmount(), amount);
        Assertions.assertEquals(account.getStatements().get(2).getType(), AccountStatement.Type.WITHDRAWAL);
        Assertions.assertEquals(account.getStatements().get(2).getBalance(), amount);

        Assertions.assertEquals(account.getStatements().get(3).getAmount(), amount);
        Assertions.assertEquals(account.getStatements().get(3).getType(), AccountStatement.Type.WITHDRAWAL);
        Assertions.assertEquals(account.getStatements().get(3).getBalance(), BigDecimal.ZERO);

    }

}
