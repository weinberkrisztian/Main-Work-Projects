package weinber.szakdolgozat.edzoterem.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Password {
	
	@NotNull(message = "kötelező")
	@Size(min = 4, message = "négy számból áll")
	private String passwordCode;
	
	
	public Password() {
		
	}

	public Password(String passwordCode) {
		this.passwordCode = passwordCode;
	}

	public String getPasswordCode() {
		return passwordCode;
	}

	public void setPasswordCode(String passwordCode) {
		this.passwordCode = passwordCode;
	}
	
	
	
	
}
