package com.lw.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lw.entity.dto.AdminModuleRole;
import com.lw.entity.vo.TreeView;

public interface AdminModuleRoleMapper extends BaseMapper<AdminModuleRole>{
		
	List<TreeView> getByAdminId(Long id);
	
}
