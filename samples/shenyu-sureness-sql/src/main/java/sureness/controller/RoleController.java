package sureness.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import sureness.pojo.dto.Message;
import sureness.pojo.entity.AuthResourceDO;
import sureness.pojo.entity.AuthRoleDO;
import sureness.service.RoleService;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author tomsun28
 * @date 00:24 2019-08-01
 */
@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;


    @GetMapping("/resource/{roleId}/{currentPage}/{pageSize}")
    public ResponseEntity<Message> getResourceOwnByRole(@PathVariable @NotBlank Long roleId, @PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        if (currentPage == null){
            currentPage = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<AuthResourceDO> resourcePage = roleService.getPageResourceOwnRole(roleId, currentPage, pageSize);
        Message message = Message.builder().data(resourcePage).build();
        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/resource/-/{roleId}/{currentPage}/{pageSize}")
    public ResponseEntity<Message> getResourceNotOwnByRole(@PathVariable @NotBlank Long roleId, @PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        if (currentPage == null){
            currentPage = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<AuthResourceDO> resourcePage = roleService.getPageResourceNotOwnRole(roleId, currentPage, pageSize);
        Message message = Message.builder().data(resourcePage).build();
        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/authority/resource/{roleId}/{resourceId}")
    public ResponseEntity<Message> authorityRoleResource(@PathVariable @NotBlank Long roleId,
                                                         @PathVariable @NotBlank Long resourceId) {
        roleService.authorityRoleResource(roleId,resourceId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/authority/resource/{roleId}/{resourceId}")
    public ResponseEntity<Message> deleteAuthorityRoleResource(@PathVariable @NotBlank Long roleId,
                                                         @PathVariable @NotBlank Long resourceId) {
        roleService.deleteAuthorityRoleResource(roleId,resourceId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<Message> addRole(@RequestBody @Validated AuthRoleDO authRole) {
        if (roleService.addRole(authRole)) {
            if (log.isDebugEnabled()) {
                log.debug("add role success: {}", authRole);
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            Message message = Message.builder()
                    .errorMsg("role already exist").build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }

    @PutMapping
    public ResponseEntity<Message> updateRole(@RequestBody @Validated AuthRoleDO authRole) {
        if (roleService.updateRole(authRole)) {
            if (log.isDebugEnabled()) {
                log.debug("update role success: {}", authRole);
            }
            return ResponseEntity.ok().build();
        } else {
            Message message = Message.builder()
                    .errorMsg("role not exist").build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Message> deleteRole(@PathVariable @NotBlank Long roleId) {
        if (roleService.deleteRole(roleId)) {
            if (log.isDebugEnabled()) {
                log.debug("delete role success: {}", roleId);
            }
            return ResponseEntity.ok().build();
        } else {
            Message message = Message.builder()
                    .errorMsg("delete role fail, no this role here").build();
            log.debug("delete role fail: {}", roleId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }

    @GetMapping("/{currentPage}/{pageSize}")
    public ResponseEntity<Message> getRole(@PathVariable Integer currentPage, @PathVariable Integer pageSize ) {
        if (Objects.isNull(currentPage) || Objects.isNull(pageSize)) {
            // no pageable
            Optional<List<AuthRoleDO>> roleListOptional = roleService.getAllRole();
            if (roleListOptional.isPresent()) {
                Message message = Message.builder().data(roleListOptional.get()).build();
                return ResponseEntity.ok().body(message);
            } else {
                Message message = Message.builder().data(new ArrayList<>()).build();
                return ResponseEntity.ok().body(message);
            }
        } else {
            // pageable
            Page<AuthRoleDO> rolePage = roleService.getPageRole(currentPage, pageSize);
            Message message = Message.builder().data(rolePage).build();
            return ResponseEntity.ok().body(message);
        }
    }

}
