<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>修改密码</title>
<meta name="decorator" content="default" />
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/info">个人信息</a></li>
		<li class="active"><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="user"
		action="${ctx}/sys/user/modifyPwd" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">旧密码:</label>
			<div class="controls">
				<input id="oldPassword" name="oldPassword" type="password" value=""
					maxlength="50" minlength="3" class="required" /> <span
					class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value=""
					maxlength="50" minlength="3" class="required" /> <span
					class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认新密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword"
					type="password" value="" maxlength="50" minlength="3"
					class="required" equalTo="#newPassword" /> <span
					class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit"
				value="保 存" />
		</div>
	</form:form>
	
	<div style="margin-left:20px">
    <table id="dataGrid"></table>
    <div id="jqGridPager"></div>
</div>
<script type="text/javascript">
	$(document).ready(function () {
    	$.jgrid.defaults.width = $(window).width();
    /* 	$.jgrid.defaults.styleUI = 'Bootstrap'; */
		$("#oldPassword").focus();
		$("#inputForm").validate({
			rules: {
			},
			messages: {
				confirmNewPassword: {equalTo: "输入与上面相同的密码"}
			},
			submitHandler: function(form){
				loading('正在提交，请稍等...');
				form.submit();
			},
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
 		var data = new DataGrid({
            url: '${ctx}/sys/user/testGrid',
            colModel: [
                { label: 'OrderID', name: 'id', key: true },
                { label: 'Customer ID', name: 'createDate' },
                { label: 'Order Date', name: 'OrderDate' },
                { label: 'Freight', name: 'Freight' },
                { label:'Ship Name', name: 'ShipName' }
            ],
			viewrecords: true,
            rowNum: 1,
            pager: "#jqGridPager",
			serializeGridData:function(postData){
				postData.ids="神话";
				return postData;
			},
			loadComplete:function(){
		 		var rowIdsData =  data.getDataIDs();
		 		console.log(rowIdsData);
		 		console.log($("#dataGrid").getDataIDs()); 
			}
        });
 		console.log(data);
/*        $("#dataGrid").jqGrid({
            url: '${ctx}/sys/user/testGrid',
            mtype: "post",
            datatype: "json",
            dataGrid:"dataGrid",
            colModel: [
                { label: 'OrderID', name: 'id', key: true },
                { label: 'Customer ID', name: 'createDate' },
                { label: 'Order Date', name: 'OrderDate' },
                { label: 'Freight', name: 'Freight' },
                { label:'Ship Name', name: 'ShipName' }
            ],
			viewrecords: true,
            rowNum: 1,
            pager: "#jqGridPager",
            prmNames :{
			 	page: "pageNo",
			 	rows: "pageSize",
			 	sort: "sidx",
			 	order: "orderBy1",
			 	search: "_search"
			},
			jsonReader: {
				root: "list",
				page: "pageNo",
				total: "totalPage",
				records: "count",
 				repeatitems: true,
				cell: "cell",
				id: "id",
				userdata: "userdata",
					subgrid: {
					root: "rows",
					repeatitems: true,
					cell: "cell"
				} 
			},
			serializeGridData:function(postData){
				postData.ids="神话";
				return postData;
			}
        });  */
	});

</script>
</body>
</html>