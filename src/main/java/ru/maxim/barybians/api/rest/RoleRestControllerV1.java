package ru.maxim.barybians.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maxim.barybians.api.dto.RoleDto;
import ru.maxim.barybians.api.service.RoleService;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/")
public class RoleRestControllerV1 {

    private RoleService roleService;

    @Autowired
    public RoleRestControllerV1(RoleService roleService) {
        this.roleService = roleService;
    }

    // Get roles list
    @GetMapping(value = "roles")
    public ResponseEntity getAll(){
        List<RoleDto> result = new ArrayList<>();
        roleService.getAll().forEach(role ->
            result.add(RoleDto.fromRole(role))
        );
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}