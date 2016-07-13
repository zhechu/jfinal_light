package com.ugiant.modules.sys.baseModel;


import com.jfinal.plugin.activerecord.IBean;
import com.ugiant.common.model.BaseModel;

@SuppressWarnings("serial")
public abstract class BaseOffice<M extends BaseOffice<M>> extends BaseModel<M> implements IBean {

    public final static String TABLE_NAME = "sys_office";

    public void setId(Long id) {
        set("id", id);
    }

    public Long getId() {
        return get("id");
    }

    public void setParentId(Long parentId) {
        set("parent_id", parentId);
    }

    public Long getParentId() {
        return get("parent_id");
    }

    public void setParentIds(String parentIds) {
        set("parent_ids", parentIds);
    }

    public String getParentIds() {
        return get("parent_ids");
    }

    public void setName(String name) {
        set("name", name);
    }

    public String getName() {
        return get("name");
    }

    public void setSort(java.math.BigDecimal sort) {
        set("sort", sort);
    }

    public java.math.BigDecimal getSort() {
        return get("sort");
    }

    public void setAreaId(Long areaId) {
        set("area_id", areaId);
    }

    public Long getAreaId() {
        return get("area_id");
    }

    public void setCode(String code) {
        set("code", code);
    }

    public String getCode() {
        return get("code");
    }

    public void setType(String type) {
        set("type", type);
    }

    public String getType() {
        return get("type");
    }

    public void setGrade(String grade) {
        set("grade", grade);
    }

    public String getGrade() {
        return get("grade");
    }

    public void setAddress(String address) {
        set("address", address);
    }

    public String getAddress() {
        return get("address");
    }

    public void setZipCode(String zipCode) {
        set("zip_code", zipCode);
    }

    public String getZipCode() {
        return get("zip_code");
    }

    public void setMaster(Long master) {
        set("master", master);
    }

    public Long getMaster() {
        return get("master");
    }

    public void setPhone(String phone) {
        set("phone", phone);
    }

    public String getPhone() {
        return get("phone");
    }

    public void setFax(String fax) {
        set("fax", fax);
    }

    public String getFax() {
        return get("fax");
    }

    public void setEmail(String email) {
        set("email", email);
    }

    public String getEmail() {
        return get("email");
    }

    public void setUseable(String useable) {
        set("useable", useable);
    }

    public String getUseable() {
        return get("useable");
    }

    public void setPrimaryPerson(Long primaryPerson) {
        set("primary_person", primaryPerson);
    }

    public Long getPrimaryPerson() {
        return get("primary_person");
    }

    public void setDeputyPerson(Long deputyPerson) {
        set("deputy_person", deputyPerson);
    }

    public Long getDeputyPerson() {
        return get("deputy_person");
    }

    public void setCreateBy(Long createBy) {
        set("create_by", createBy);
    }

    public Long getCreateBy() {
        return get("create_by");
    }

    public void setCreateDate(java.util.Date createDate) {
        set("create_date", createDate);
    }

    public java.util.Date getCreateDate() {
        return get("create_date");
    }

    public void setUpdateBy(Long updateBy) {
        set("update_by", updateBy);
    }

    public Long getUpdateBy() {
        return get("update_by");
    }

    public void setUpdateDate(java.util.Date updateDate) {
        set("update_date", updateDate);
    }

    public java.util.Date getUpdateDate() {
        return get("update_date");
    }

    public void setRemarks(String remarks) {
        set("remarks", remarks);
    }

    public String getRemarks() {
        return get("remarks");
    }

}