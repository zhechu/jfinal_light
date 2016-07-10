alter table sys_user modify column company_id bigint(20) DEFAULT NULL COMMENT '归属公司';
alter table sys_user modify column office_id bigint(20) DEFAULT NULL COMMENT '归属机构';
alter table sys_user modify column create_by bigint(20) DEFAULT NULL COMMENT '创建者';
alter table sys_user modify column update_by bigint(20) DEFAULT NULL COMMENT '更新者';