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
import java.time.LocalDateTime;

/**
 * @author tomsun28
 * @date 00:27 2019-07-27
 */
@Entity
@Table(name = "auth_role")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRoleDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private Integer status;

    private String description;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtUpdate;
}
