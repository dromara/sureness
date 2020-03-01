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
 * 资源实体
 * @author tomsun28
 * @date 00:00 2019-07-26
 */
@Entity
@Table(name = "auth_resource")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResourceDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name can not null")
    @Length(min = 3, max = 100, message = "name length in 3-100")
    private String name;

    @NotBlank(message = "code can not null")
    @Length(min = 3, max = 100, message = "code length in 3-100")
    private String code;

    @NotBlank(message = "uri can not null")
    private String uri;

    private String type;

    @NotBlank(message = "method can not null")
    private String method;

    @Range(min = 0, max = 9, message = "1 enable, 9 disable")
    private Integer status;

    private String description;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtUpdate;
}
