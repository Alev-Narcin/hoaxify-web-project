package com.hoaxify.ws.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

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
        return new ApiError(status, message, path);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
