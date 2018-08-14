package top.akte.util;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class Validator {

    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static javax.validation.Validator validator = factory.getValidator();

    /**
     * 返回多个错误
     * @param obj
     * @throws ValidationException
     */
    public void validateMutil(Object obj) throws ValidationException {
        Set<ConstraintViolation<Object>> constraintViolations =
                validator.validate(obj);
        List<String> errList = new ArrayList<String>();

        if (constraintViolations == null || constraintViolations.size() == 0) {
            return;
        }
        for (ConstraintViolation<Object> error : constraintViolations) {
            String errorMsg = error.getPropertyPath() + ":" + error.getMessage();
            errList.add(errorMsg);
        }
        throw new ValidationException(errList);
    }

    /**
     * 返回单个错误
     * @param obj
     * @throws ValidationException
     */

    public void validate(Object obj) throws ValidationException {
        Set<ConstraintViolation<Object>> constraintViolations =
                validator.validate(obj);
        String errorMsg = null;
        if (constraintViolations != null && constraintViolations.size() != 0) {
            ConstraintViolation<Object> error = constraintViolations.iterator().next();
            errorMsg = error.getPropertyPath() + ":" + error.getMessage();

            throw new ValidationException(errorMsg);
        }
    }
}
