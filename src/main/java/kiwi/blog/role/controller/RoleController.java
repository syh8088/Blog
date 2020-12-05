package kiwi.blog.role.controller;

import io.swagger.annotations.Api;
import kiwi.blog.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Role", description = "역활")
@RestController
@RequiredArgsConstructor
@RequestMapping("roles")
public class RoleController {

    private final RoleService roleService;
}
