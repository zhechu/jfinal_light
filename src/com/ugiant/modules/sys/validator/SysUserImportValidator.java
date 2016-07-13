package com.ugiant.modules.sys.validator;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.upload.UploadFile;
import com.ugiant.common.validator.BaseValidator;
/**
 * 用户导入  验证器
 * @author lingyuwang
 *
 */
public class SysUserImportValidator extends BaseValidator {

	@Override
	protected void validate(Controller c) {
		UploadFile uploadFile = c.getFile("file");
		if (uploadFile == null) {
			this.addError("message", "请选择上传文件");
		}
		// 判断文件是否为 excel
		File file = uploadFile.getFile();
		String fileName = file.getName();
		if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
			this.addError("message", "仅允许导入“xls”或“xlsx”格式文件！");
		}
		// 文件大小
		Long len = file.length();
		if (len > 5242880) { // 最大5M
			this.addError("message", "导入文件不能超过5M！");
		}
	}

	@Override
	protected void handleError(Controller c) {
		try {
			c.redirect(adminPath+"/sys/user/list?repage=true&type=error&message="+URLEncoder.encode(c.getAttr("message").toString(), PropKit.get("encoding")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
