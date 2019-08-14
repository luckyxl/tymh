<html>
    <body>
<#--        <form action="${springMacroRequestContext.contextPath}/login/doLogin" method="post">-->
<#--            账号<input type="text" name="username" id="username"/>-->
<#--            密码<input type="password" name="password" id="password"/>-->
<#--            <button id="submit" type="submit">提交</button>-->
<#--        </form>-->
        账号<input type="text" name="username" id="username"/>
        密码<input type="password" name="password" id="password"/>
        <button id="submit" type="submit">提交</button>

    </body>
    <script type="text/javascript" src="${springMacroRequestContext.contextPath}/static/js/jquery/jquery.min.js"></script>
    <script type="text/javascript">
        $('#submit').on('click',function(){
            var username = $('#username').val();
            var password = $('#password').val();
            $.post('${springMacroRequestContext.contextPath}/login/login', {'username':username,"password":password}, function(res) {
                console.log(res);
                if(res.success){
                    location.href="${springMacroRequestContext.contextPath}"+res.url;
                }else{
                    alert(res.msg);
                }
            });
        });
        function kickout(){
            var href=location.href;
            if(href.indexOf("kickout")>0){
                alert("您的账号在另一台设备上登录,如非本人操作，请立即修改密码！");
            }
        }
        window.onload=kickout();
    </script>
</html>
