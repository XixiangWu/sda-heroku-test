package au.edu.unimelb.cis.swen90007.itsms.domain;

import au.edu.unimelb.cis.swen90007.itsms.session.AppSession;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;

import java.util.HashSet;
import java.util.Set;

public class AppRealm extends JdbcRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //identify account to log to
        UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
        final String username = userPassToken.getUsername();

        final User user = User.getUserByUsername(username);
        if (user == null) {
            System.out.println("No account found for user with username " + username);
            return null;
        }

        return new SimpleAuthenticationInfo(user.getId(), user.getPassword(), getName());
    }

    @Override
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        Set<String> roles = new HashSet<>();
        if (principals.isEmpty()) {
            System.out.println("Given principals to authorize are empty.");
            return null;
        }

        int username = (Integer) principals.getPrimaryPrincipal();
        final User user = User.getUser(username);

        if (user==null) {
            System.out.println("No account found for user with username " + username);
            return null;
        }

        // add roles of the user according to its type
        if (user instanceof Employee) {
            roles.add(AppSession.EMPLOYEE_ROLE);
        } else if (user instanceof  Tech) {
            roles.add(AppSession.TECH_ROLE);
        }

        return new SimpleAuthorizationInfo(roles);
    }

    public FilterChainResolver getFilterChainResolver() {
        FilterChainResolver filterChainResolver = null;
        if (filterChainResolver == null) {
            FormAuthenticationFilter authc = new FormAuthenticationFilter();
            AnonymousFilter anon = new AnonymousFilter();
            UserFilter user = new UserFilter();

            authc.setLoginUrl("/login.jsp");
            user.setLoginUrl("/login.jsp");

            FilterChainManager fcMan = new DefaultFilterChainManager();
            fcMan.addFilter("authc", authc);
            fcMan.addFilter("anon", anon);
            fcMan.addFilter("user", user);

            fcMan.createChain("/view", "anon");
            fcMan.createChain("/css/**", "anon");
            fcMan.createChain("/api/**", "anon");
            fcMan.createChain("/login.jsp", "authc");
            fcMan.createChain("/**", "user");

            PathMatchingFilterChainResolver resolver = new PathMatchingFilterChainResolver();
            resolver.setFilterChainManager(fcMan);
            filterChainResolver = resolver;
        }
        return filterChainResolver;
    }

}
