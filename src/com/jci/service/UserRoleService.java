package com.jci.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jci.model.UserRoleModel;
@Service
public interface UserRoleService {

	void create(UserRoleModel userrole);

	List<UserRoleModel> getAll();

}