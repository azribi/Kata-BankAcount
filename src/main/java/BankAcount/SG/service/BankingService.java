package BankAcount.SG.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import BankAcount.SG.domain.Account;
import BankAcount.SG.domain.AccountStatement;

public class BankingService {
	private Account account; 
 
	
	public BankingService(Account account2) {
		 this.account = account2; 
	}

	
	public void deposit(BigDecimal amount) {
		checkIfAmountValid(amount);
		BigDecimal newBalance= account.getBalance().add(amount);
		account.setBalance(newBalance);
		account.getStatements().add(new AccountStatement(AccountStatement.Type.DEPOSIT, amount, LocalDate.now(), newBalance));
      
	}
	public void withdrawal(BigDecimal amount) {
		  checkIfAmountValid(amount);
	        if(account.getBalance().compareTo(amount) >= 0)
	        {
	        	BigDecimal newBalance= account.getBalance().subtract(amount);
	        	account.setBalance(newBalance);
	        	account.getStatements().add(new AccountStatement(AccountStatement.Type.WITHDRAWAL, amount, LocalDate.now(),newBalance));
	        }
	        else
	        {
	            throw new RuntimeException("Insufficient balance");
	        }
	}
	
	public List<AccountStatement> printStatements(){
		return account.getStatements(); 
	}
	private void checkIfAmountValid(BigDecimal amount)
    {
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new RuntimeException("Amount Not Valid");
    }
}
