package sureness.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * user-role mapping entity
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
