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
 * @date 00:00 2019-07-26
 */
@Entity
@Table(name = "auth_resource")
@Data
public class AuthResourceDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private String uri;

    private String type;

    private String method;

    private Integer status;

    private String description;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtUpdate;
}
