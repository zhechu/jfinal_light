alter table sys_user_role modify column role_id bigint(20) NOT NULL COMMENT '角色编号';
alter table sys_user_role modify column user_id bigint(20) NOT NULL COMMENT '用户编号';
