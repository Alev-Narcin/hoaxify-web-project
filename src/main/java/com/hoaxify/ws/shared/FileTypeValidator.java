package com.hoaxify.ws.shared;

import com.hoaxify.ws.file.FileService;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FileTypeValidator implements ConstraintValidator<FileType, String> {

    @Autowired
    FileService fileService;

    String[] types;

    @Override
    public void initialize(FileType constraintAnnotation) {
        this.types = constraintAnnotation.types();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {     //illa bi profil resmi yüklemek gerekmiyor null veya boşta olabilir.
            return true;
        }
        String fileType = fileService.detectType(value);
        for (String supportedType : this.types) {
            if (fileType.contains(supportedType)) {
                return true;
            }
        }

        String supportedTypes = Arrays.stream(this.types).collect(Collectors.joining(", "));  //desteklenen image tiplerini akış içinde virgül ile yanyana yazması için. Validate mesajın daha düzgün görünmesi için.Kısaca itemları birbirine virgül ile join ediyor.

        context.disableDefaultConstraintViolation();   //default oluşturulan mesajı engellemek için
        HibernateConstraintValidatorContext hibernateConstraintValidatorContext = context.unwrap(HibernateConstraintValidatorContext.class);
        hibernateConstraintValidatorContext.addMessageParameter("types", supportedTypes);
        hibernateConstraintValidatorContext.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();

        return false;
    }
}