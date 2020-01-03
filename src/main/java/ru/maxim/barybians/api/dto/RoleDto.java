package ru.maxim.barybians.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.maxim.barybians.api.model.Role;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RoleDto {

    private Long id;
    private String name;

    public Role toRole(){
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        return role;
    }

    public static RoleDto fromRole(Role role){
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        return roleDto;
    }
}