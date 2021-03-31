package com.hoaxify.ws.user.vm;

import com.hoaxify.ws.shared.FileType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserUpdateVM {   //sadece user ı update etmek için kullandığımız bir obje

    @NotNull
    @Size(min = 4, max = 255)
    private String displayName;

    //profil resminde sadece aşağıdaki tipteki imageları kabul eder
    @FileType(types = {"jpeg", "png"})
    private String image;
}
