package com.youchat.creative.factory.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;

public class PojoV {
    @Autowired
    private SmartValidator validator;


    public static void main(String[] args) {
        PojoBase obj = new PojoBase();
        BindingResult bindingResult = new BeanPropertyBindingResult(obj, obj.getClass().getSimpleName());
        //  validator.validate(obj,bindingResult);

        //     List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        //    for(FieldError fieldError:fieldErrors){
        //       ExceptionUtils.throwBaseException(AnalysisMessage.ILLEGAL_ARGUMENT, fieldError.getField() + "：" + fieldError.getDefaultMessage());
        //    }
    }
}
