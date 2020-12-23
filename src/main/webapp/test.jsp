<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+ request.getServerPort()+request.getContextPath()+"/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>

    </title>
</head>
<body>
    $.ajax({
        url:"",
        data:{

        },
        type:"",
        dataType:"json",
        success:function (data) {

        }
    })

    String createTime = DateTimeUtil.getSysTime();
    //创建人，当前登录用户
    String createBy = ((User)req.getSession().getAttribute("user")).getName();

    $(".time").datetimepicker({
    minView: "month",
    language:  'zh-CN',
    format: 'yyyy-mm-dd',
    autoclose: true,
    todayBtn: true,
    pickerPosition: "bottom-left"
    });

    String createTime = DateTimeUtil.getSysTime();
    String createBy = ((User)req.getSession().getAttribute("user")).getName();
</body>
</html>
