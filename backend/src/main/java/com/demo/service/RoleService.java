package com.demo.service;

import com.demo.entity.Role;
import lombok.Builder;

import java.util.List;

public interface RoleService {
   public Role getRoleByDescription(String roleDesc);
   public List<Role> getAllRoles();
}
