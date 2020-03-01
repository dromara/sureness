package com.usthe.sureness.sample.tom.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 用户实体
 * @author tomsun28
 * @date 00:29 2019-07-27
 */
@Entity
@Table(name = "auth_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "username can not null")
    @Length(min = 3, max = 100, message = "name length in 3-100")
    private String username;

    @NotBlank(message = "password can not null")
    @Length(min = 3, max = 100, message = "password length in 3-100")
    private String password;

    private String salt;

    private String avatar;

    private String phone;

    private String email;

    private Integer sex;

    @Range(min = 0, max = 9, message = "1 enable, 2 locked, 3 deleted, 4 illegal")
    private Integer status;

    private Integer createWhere;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtUpdate;
}
