package com.usthe.sureness.sample.tom.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author tomsun28
 * @date 20:36 2019-08-01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @NotBlank(message = "username can not null")
    @Length(min = 3, max = 100, message = "username length in 3-100")
    private String username;

    @NotBlank(message = "password can not null")
    @Length(min = 3, max = 100, message = "password length in 3-100")
    private String password;

}
