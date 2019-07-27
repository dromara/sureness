package com.usthe.sureness.sample.tom.pojo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author tomsun28
 * @date 00:28 2019-07-27
 */
@Entity
@Table(name = "auth_role_resource_bind")
@Data
public class AuthRoleResourceBindDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roleId;

    private Long resourceId;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtUpdate;
}
