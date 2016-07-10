alter table sys_office modify column parent_id bigint(20) NOT NULL COMMENT '父级编号';
alter table sys_office modify column area_id bigint(20) NOT NULL COMMENT '归属区域';
alter table sys_office modify column master bigint(20) DEFAULT NULL COMMENT '负责人';
alter table sys_office modify column primary_person bigint(20) DEFAULT NULL;
alter table sys_office modify column deputy_person bigint(20) DEFAULT NULL;
alter table sys_office modify column create_by bigint(20) DEFAULT NULL COMMENT '创建者';
alter table sys_office modify column update_by bigint(20) DEFAULT NULL COMMENT '更新者';