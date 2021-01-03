package weinber.szakdolgozat.edzoterem.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class EmailValidator implements ConstraintValidator<EmailValidation, String> {

	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		pattern=Pattern.compile(EMAIL_PATTERN);
		
		if(value==null) {
			return false;
		}
		
		matcher=pattern.matcher(value);
		return matcher.matches();
	}
}
