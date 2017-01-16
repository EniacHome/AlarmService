package com.eniacdevelopment.EniacHome.Features;

import com.eniacdevelopment.EniacHome.Business.Contracts.UserService;
import com.eniacdevelopment.EniacHome.DataModel.User.User;
import com.eniacdevelopment.EniacHome.DataModel.User.UserRole;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Objects.TokenAuthenticationResult;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by larsg on 12/16/2016.
 */
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private final UserService userService;

    public AuthenticationFilter(@Context UserService userService) {
        this.userService = userService;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException{
        // Get HTTP Authorization header
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader == null){
            return; /*If DynamicRoles is hit and no user is supplied it will return unauthorized */
        }
        String token = authorizationHeader.substring("Bearer".length()).trim();

        TokenAuthenticationResult result = this.userService.authenticateToken(token);
        if(!result.Authenticated) {
            return; /*If DynamicRoles is hit and no user is supplied it will return unauthorized */
        }

        User user = this.userService.getUser(result.UserId);
        if(user == null){
            this.userService.deleteUser(result.UserId); /*The token is no longer valid, get rid of it*/
            return; /*If DynamicRoles is hit and no user is supplied it will return unauthorized */
        }

        this.userService.updateToken(result.UserId);
        containerRequestContext.setSecurityContext(new SecurityContextImpl(containerRequestContext.getSecurityContext(), user));
    }

    public static class SecurityContextImpl implements SecurityContext {
        private final User user;
        private final Boolean isSecure;
        private final String authenticationScheme;

        public SecurityContextImpl(SecurityContext securityContext, User user){
            this.user = user;
            this.isSecure = securityContext.isSecure();
            this.authenticationScheme = securityContext.getAuthenticationScheme();
        }

        @Override
        public Principal getUserPrincipal() {
            return () -> user.Username;
        }

        @Override
        public boolean isUserInRole(String role) {
            if(this.user.Roles == null){
                return false;
            }
            for (UserRole userRole :  this.user.Roles) {
                if(userRole.toString().equals(role)){
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean isSecure() {
            return this.isSecure;
        }

        @Override
        public String getAuthenticationScheme() {
            return this.authenticationScheme;
        }
    }
}
