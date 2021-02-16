<%@page contentType="text/html;charset=utf-8"%>
<!-- 新增油画页面 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>新增皮肤</title>
<link rel="stylesheet" type="text/css" href="css/create.css">
<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/validation.js"></script>
<script type="text/javascript">
	function checkSubmit(){
		var result = true;
		var r1 = checkEmpty("#pname","#errPname");
		var r2 = checkCategory('#category', '#errCategory');
		var r3 = checkPrice('#price', '#errPrice');
		var r4 = checkFile('#painting', '#errPainting');
		var r5 = checkEmpty('#description', '#errDescription');
		if (r1 && r2 && r3 && r4 && r5){
			return true;
		}else{
			return false;
		}
	}
</script>
</head>
<body>
	<div class="container">
		<fieldset>
			<legend>新增皮肤</legend>
			<form action="/mmgallery_war_exploded/management?method=create" method="post"
				autocomplete="off" enctype="multipart/form-data"
				onsubmit = "return checkSubmit()">
				<ul class="ulform">
					<li>
						<span>皮肤名称</span>
						<span id="errPname"></span>
						<input id="pname" name="pname" onblur="checkEmpty('#pname','#errPname')"/>
					</li>
					<li>
						<span>皮肤类型</span>
						<span id="errCategory"></span>
						<select id="category" name="category" onchange="checkCategory('#category','#errCategory')">
							<option value="-1">请选择皮肤类型</option>
							<option value="1">荣耀典藏</option>
							<option value="2">限定皮肤</option>
						</select>
					</li>
					<li>
						<span>皮肤价格</span>
						<span id="errPrice"></span>
						<input id="price" name="price" onblur="checkPrice('#price','#errPrice')"/>
					</li>
					<li>
						<span>作品预览</span>
						<span id="errPainting"></span>
						<input id="painting" name="painting" type="file" 
							style="padding-left: 0px;" accept="image/*" onchange="checkFile('#painting','#errPainting')"/>
					</li>

					<li>
						<span>详细描述</span>
						<span id="errDescription"></span>
						<textarea
							id="description" name="description"
							onblur="checkEmpty('#description','#errDescription')"
							></textarea>
					</li>
					<li style="text-align: center;">
						<button type="submit" class="btn-button">提交表单</button>
					</li>
				</ul>
			</form>
		</fieldset>
	</div>

</body>
</html>
