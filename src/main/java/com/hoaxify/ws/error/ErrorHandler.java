package com.hoaxify.ws.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//Hem Authentication daki errorleri ApiError a modelleyen hemde validation daki fail durumlarını ApiError a modelleyen bir methodumuz oldu.
@RestController //error ile ilgili herşey bu fonk. düşsün anlamına geliyor.
public class ErrorHandler implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    //Spring in BasicErrorController ı yerine kendi projemize özel error controller yazdık.
    //Eklediğimiz bu ErrorHandler class ı sayesinde hata mesajlarının ApiError formatında olacağını garantilemiş olduk.
    @RequestMapping("/error")
    ApiError handleError(WebRequest webRequest) {

        Map<String, Object> attributes = this.errorAttributes.getErrorAttributes(webRequest, true);
        String message = (String) attributes.get("message");
        String path = (String) attributes.get("path");
        int status = (Integer) attributes.get("status");

        ApiError error = new ApiError(status, message, path);
        if (attributes.containsKey("errors")) {  //attributes lerin içerisinde errors varsa demek
            @SuppressWarnings("unchecked")  //(List<FieldError>) attributes.get("errors")  deki warning içinyazdık burası patlayana kadar sıkıntı yok
            List<FieldError> fieldErrors = (List<FieldError>) attributes.get("errors");  //error listesini aldık field lar gibi. bu listeyi ApiError içerisindeki validation errors objesine dönüştürüyoruz.
            Map<String, String> validationErrors = new HashMap<>();
            for (FieldError fieldError : fieldErrors) {
                validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());  //hangi field için hangi message geldi gibi bir mapping yapıyoruz
            }
            error.setValidationErrors(validationErrors);
        }
        return error;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
