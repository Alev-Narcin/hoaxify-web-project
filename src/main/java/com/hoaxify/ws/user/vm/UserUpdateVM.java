package com.hoaxify.ws.user.vm;

import lombok.Data;

@Data
public class UserUpdateVM {   //sadece user ı update etmek için kullandığımız bir obje

    private String displayName;

    private String image;
}
