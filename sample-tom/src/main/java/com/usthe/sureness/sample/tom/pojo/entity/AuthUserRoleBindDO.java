package com.usthe.sureness.sample.tom.pojo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author tomsun28
 * @date 00:30 2019-07-27
 */
@Entity
@Table(name = "auth_user_role_bind")
@Data
public class AuthUserRoleBindDO {

    private Long id;

    private Long userId;

    private Long roleId;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtUpdate;
}
