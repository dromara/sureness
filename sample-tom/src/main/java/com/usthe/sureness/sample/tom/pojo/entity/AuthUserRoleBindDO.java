package com.usthe.sureness.sample.tom.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 用户与角色映射实体
 * @author tomsun28
 * @date 00:30 2019-07-27
 */
@Entity
@Table(name = "auth_user_role_bind")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserRoleBindDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "userId can not null")
    private Long userId;

    @NotNull(message = "roleId can not null")
    private Long roleId;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtUpdate;
}
