package com.usthe.sureness.sample.tom.controller;

import com.usthe.sureness.sample.tom.pojo.dto.Message;
import com.usthe.sureness.sample.tom.pojo.entity.AuthResourceDO;
import com.usthe.sureness.sample.tom.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author tomsun28
 * @date 00:24 2019-08-01
 */
@RequestMapping("/resource")
@RestController
@Slf4j
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping
    public ResponseEntity<Message> addResource(@RequestBody @Validated AuthResourceDO authResource) {
        if (resourceService.addResource(authResource)) {
            if (log.isDebugEnabled()) {
                log.debug("add resource success: {}", authResource);
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            Message message = Message.builder().errorMsg("resource already exist").build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }

    @PutMapping
    public ResponseEntity<Message> updateResource(@RequestBody @Validated AuthResourceDO authResource) {
        if (resourceService.updateResource(authResource)) {
            if (log.isDebugEnabled()) {
                log.debug("update resource success: {}", authResource);
            }
            return ResponseEntity.ok().build();
        } else {
            Message message = Message.builder().errorMsg("resource not exist").build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }

    @DeleteMapping("/{resourceId}")
    public ResponseEntity<Message> deleteResource(@PathVariable @NotBlank Long resourceId ) {
        if (resourceService.deleteResource(resourceId)) {
            if (log.isDebugEnabled()) {
                log.debug("delete resource success: {}", resourceId);
            }
            return ResponseEntity.ok().build();
        } else {
            Message message = Message.builder().errorMsg("delete resource fail, please try again later").build();
            log.error("delete resource fail: {}", resourceId);
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(message);
        }
    }

    @GetMapping("/{currentPage}/{pageSize}")
    public ResponseEntity<Message> getResource(@PathVariable Integer currentPage, @PathVariable Integer pageSize ) {
        if (Objects.isNull(currentPage) || Objects.isNull(pageSize)) {
            // 不分页,查询总
            Optional<List<AuthResourceDO>> resourceListOptional = resourceService.getAllResource();
            if (resourceListOptional.isPresent()) {
                Message message = Message.builder().data(resourceListOptional.get()).build();
                return ResponseEntity.ok().body(message);
            } else {
                Message message = Message.builder().data(new ArrayList<>(0)).build();
                return ResponseEntity.ok().body(message);
            }
        } else {
            // 分页查询
            Page<AuthResourceDO> resourcePage = resourceService.getPageResource(currentPage, pageSize);
            Message message = Message.builder().data(resourcePage.get()).build();
            return ResponseEntity.ok().body(message);
        }
    }

}
