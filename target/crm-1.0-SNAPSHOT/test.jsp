<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/12/4
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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
</body>
</html>
