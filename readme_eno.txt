framework_2014-08-16.rar
功能也是11年那个最全，附件attachment文件夹里还有点代码模板（ssh_2011-12-07.rar）


Mapping：
URL对应Controller方法名：
http://localhost:8080/framework/SysUser/detail.html?id=1
对应SysUser controller的detail方法


改数据库连接用户名密码

登录用户 admin  123
加密方式？


初始化数据，导入SQL用哪个?
framework\WebRoot\upload\netdisk\14064016602313v342.sql
还是framework\doc\db\framework.sql

class SysRoleDAO Line239出错: return getQueryRunner().query(sql, new BeanListHandler<>(SysMenu.class), userId);
remove <>:	return getQueryRunner().query(sql, new BeanListHandler(SysMenu.class), userId);

启动出错，注释调framework\resource\spring下的jsonView和xmlResolver

web.xml添加：
  <error-page>
    <error-code>404</error-code>
    <location>/404.ftl</location>
  </error-page>

  <error-page>
    <error-code>500</error-code>
    <location>/500.ftl</location>
  </error-page>



json:
1.需要依赖spring-json.jar和sojo-optional.jar
2.直接responseutil.renderjson也可以返回json

-------------------------------------------------------------
create a new repository on the command line


touch README.md
git init
git add README.md
git commit -m "first commit"
git remote add origin https://github.com/enoy/moly2.git
git push -u origin master


-------------------------------------------------------------
push an existing repository from the command line

git remote add origin https://github.com/enoy/moly2.git
git push -u origin master