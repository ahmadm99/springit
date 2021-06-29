package com.ahmad.springit.Service;

import com.ahmad.springit.domain.Role;
import com.ahmad.springit.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    public final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name){
        return roleRepository.findByName(name);
    }
}
