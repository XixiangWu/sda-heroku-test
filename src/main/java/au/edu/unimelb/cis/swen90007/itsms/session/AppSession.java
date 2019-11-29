package au.edu.unimelb.cis.swen90007.itsms.session;
import au.edu.unimelb.cis.swen90007.itsms.domain.User;
import org.apache.shiro.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AppSession {

    public static final String USER_ATTRIBUTE_NAME = "currentSessionUser";
    public static final String EMPLOYEE_ROLE = "employee";
    public static final String TECH_ROLE = "tech";

    private HttpSession httpSession;

    private AppSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public static AppSession refreshSession(HttpSession httpSession) {
        if (httpSession.getAttribute(USER_ATTRIBUTE_NAME) == null) {
            User user = User.getUser((Integer) httpSession.getAttribute("userId"));
            httpSession.setMaxInactiveInterval(24 * 60 * 60);
        }
        return new AppSession(httpSession);
    }

    public static boolean hasRole(String role) {
        return SecurityUtils.getSubject().hasRole(role);
    }

    public static boolean isAuthenticated() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    public static void init(User user) {
        SecurityUtils.getSubject().getSession().setAttribute(USER_ATTRIBUTE_NAME, user);
    }

    public static User getUser() {
        return (User) SecurityUtils.getSubject().getSession().getAttribute(USER_ATTRIBUTE_NAME);
    }

//    public User getUser() {
//        return (User) httpSession.getAttribute(USER_ATTRIBUTE_NAME);
//    }

    public static User checkSession(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User user = User.getUser((Integer) session.getAttribute("userId"));

        AppSession.refreshSession(request.getSession());
        session.setAttribute(AppSession.USER_ATTRIBUTE_NAME, user);

        return user;
    }

}
