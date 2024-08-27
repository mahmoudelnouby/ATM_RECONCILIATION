//package com.example.ATM_RECONCILIATION.security.helpers;
//
//import jakarta.servlet.annotation.WebListener;
//import jakarta.servlet.http.HttpSessionEvent;
//import jakarta.servlet.http.HttpSessionListener;
//import com.example.ATM_RECONCILIATION.security.models.UserSession;
//import com.example.ATM_RECONCILIATION.security.repos.UserSessionsRepository;
//import com.example.ATM_RECONCILIATION.security.repos.UsersRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//
//import java.util.Objects;
//
//@WebListener
//public class SessionCleanupListener implements HttpSessionListener {
//
//	@Autowired
//    UsersRepository scUsersRepo;
//
//    @Autowired
//    UserSessionsRepository sessionUserRepo;
//
////    @Override
////    public void sessionCreated(HttpSessionEvent se) {
////       System.out.println("Session Created: " + se.getSession().getId());
////    }
//
//    @Override
//    public void sessionDestroyed(HttpSessionEvent se) {
//        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(se.getSession().getServletContext());
//        this.sessionUserRepo = context.getBean(UserSessionsRepository.class);
//        String sessionID = se.getSession().getId();
//        UserSession sessionUser = sessionUserRepo.findById(sessionID).orElse(null);
//        if(!Objects.isNull(sessionUser)){
//            sessionUserRepo.deleteById(sessionID);
//        }
//    }
//
//}
