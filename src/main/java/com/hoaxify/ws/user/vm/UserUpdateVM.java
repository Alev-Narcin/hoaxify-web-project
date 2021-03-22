package com.hoaxify.ws.user.vm;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserUpdateVM {   //sadece user ı update etmek için kullandığımız bir obje

    @NotNull
    @Size(min = 4, max = 255)
    private String displayName;

    private String image;
}
