package weinber.szakdolgozat.edzoterem.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;


public class MatchingPasswordValidator implements ConstraintValidator<MatchingPasswordValidation, Object>{

	private String firstPasswordName;
	private String secondPasswordName;
	private String message;
	
    @Override
    public void initialize(final MatchingPasswordValidation constraintAnnotation) {
    	firstPasswordName = constraintAnnotation.first();
    	secondPasswordName = constraintAnnotation.second();
	    message = constraintAnnotation.message();
    }
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean valid=true;
		
        try
        {
            final Object firstObj = new BeanWrapperImpl(value).getPropertyValue(firstPasswordName);
            final Object secondObj = new BeanWrapperImpl(value).getPropertyValue(secondPasswordName);

            valid =  firstObj != null && firstObj.equals(secondObj);
        }
        catch (final Exception ignore)
        {
            // we can ignore
        }
		
        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstPasswordName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }	
		
		return valid;
	}


}
