alter table sys_menu modify column parent_id bigint(20) NOT NULL COMMENT '父级编号';
alter table sys_menu modify column create_by bigint(20) DEFAULT NULL COMMENT '创建者';
alter table sys_menu modify column update_by bigint(20) DEFAULT NULL COMMENT '更新者';