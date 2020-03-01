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
 * 资源与角色映射实体
 * @author tomsun28
 * @date 00:28 2019-07-27
 */
@Entity
@Table(name = "auth_role_resource_bind")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRoleResourceBindDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "roleId can not null")
    private Long roleId;

    @NotNull(message = "resourceId can not null")
    private Long resourceId;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtUpdate;
}
