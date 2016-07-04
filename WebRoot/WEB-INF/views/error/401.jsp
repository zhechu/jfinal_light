<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>401 - 未经授权</title>
</head>
<body>
	未登录
	<script type="text/javascript">
		window.setTimeout(function() {
			top.location.href = "/a/login";
		}, 3000);	
	</script>
</body>
</html>
