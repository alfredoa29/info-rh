package com.demo.service.implementations;

import com.demo.entity.Role;
import com.demo.repository.RoleRepository;
import com.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {


    @Autowired
      RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleByDescription(String roleDesc) {

        return roleService.getRoleByDescription(roleDesc);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
