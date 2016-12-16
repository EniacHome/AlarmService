package com.eniacdevelopment.EniacHome.Features;

import com.eniacdevelopment.EniacHome.DataModel.User.User;
import com.eniacdevelopment.EniacHome.DataModel.User.UserRole;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Objects.TokenAuthenticationResult;
import com.eniacdevelopment.EniacHome.Repositories.Shared.TokenRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.UserRepository;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.Context;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by larsg on 12/16/2016.
 */
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public AuthenticationFilter(@Context TokenRepository tokenRepository, @Context UserRepository userRepository){
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException{
        // Get HTTP Authorization header
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader == null){
            return; /*If DynamicRoles is hit and no user is supplied it will return unauthorized */
        }
        String token = authorizationHeader.substring("Bearer".length()).trim();

        TokenAuthenticationResult result = this.tokenRepository.authenticateToken(token);
        if(!result.Authenticated) {
            return; /*If DynamicRoles is hit and no user is supplied it will return unauthorized */
        }

        User user = this.userRepository.get(result.UserId);
        if(user == null){
            return; /*If DynamicRoles is hit and no user is supplied it will return unauthorized */
        }
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
            return new Principal() {
                @Override
                public String getName() {
                    return user.Username;
                }
            };
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
