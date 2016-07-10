alter table sys_role modify column office_id bigint(20) DEFAULT NULL COMMENT '归属机构';
alter table sys_role modify column create_by bigint(20) DEFAULT NULL COMMENT '创建者';
alter table sys_role modify column update_by bigint(20) DEFAULT NULL COMMENT '更新者';