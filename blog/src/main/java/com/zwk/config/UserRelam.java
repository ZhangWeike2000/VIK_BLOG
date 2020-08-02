package com.zwk.config;


import com.zwk.pojo.User;
import com.zwk.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mr.z
 * @date 2020/7/2 - 11:56
 */
public class UserRelam extends AuthorizingRealm {
    @Autowired
    UserService userService;

    /***
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }

    /***
     * 验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //密码认证shiro去做
        //连接真实的数据库
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        //若是数据库中符合传入的条件的记录有多条，那就不能用这个方法，会报错
        User user = userService.checkUser(userToken.getUsername());
        //没有这个人return：null
        if(user==null){
            return  null;
        }
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("user", user);
        //密码认证shiro去做
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}
