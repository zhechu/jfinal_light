/**
 * 自定义校验
 */

// 电话号码验证   
jQuery.validator.addMethod("telephone", function(value, element) {   
    var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
    return this.optional(element) || (tel.test(value));
}, "请输入正确格式的电话");

// 手机号码验证   
jQuery.validator.addMethod("mobilephone", function(value, element) {   
	var tel = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
	return this.optional(element) || (tel.test(value));
}, "请输入正确格式的手机");