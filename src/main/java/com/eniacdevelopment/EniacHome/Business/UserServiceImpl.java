package com.eniacdevelopment.EniacHome.Business;

import com.eniacdevelopment.EniacHome.Business.Contracts.UserService;
import com.eniacdevelopment.EniacHome.DataModel.User.Credentials;
import com.eniacdevelopment.EniacHome.DataModel.User.Token;
import com.eniacdevelopment.EniacHome.DataModel.User.User;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Objects.TokenAuthenticationResult;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Objects.UserAuthenticationResult;
import com.eniacdevelopment.EniacHome.Repositories.Shared.TokenRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.UserRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Utils.TokenUtils;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Utils.UserUtils;

import javax.inject.Inject;

/**
 * Created by larsg on 1/15/2017.
 */
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final UserUtils userUtils;
    private final TokenUtils tokenUtils;

    @Inject
    public UserServiceImpl(UserRepository userRepository, TokenRepository tokenRepository, UserUtils userUtils, TokenUtils tokenUtils) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.userUtils = userUtils;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public Iterable<User> getUsers() {
        return this.userRepository.getAll();
    }

    @Override
    public User getUser(String id) {
        return this.userRepository.get(id);
    }

    @Override
    public void addUser(User user) {
        if (this.userRepository.getByUserName(user.Username) != null) {
            return;
        }

        user.Id = null;
        this.userRepository.add(user);
    }

    @Override
    public void updateUser(User user) {
        this.userRepository.update(user);
    }

    @Override
    public void deleteUser(String id) {
        this.userRepository.delete(id);
    }

    @Override
    public UserAuthenticationResult authenticateUser(Credentials credentials) {
        User user = this.userRepository.getByUserName(credentials.Username);

        if (user == null) {
            return new UserAuthenticationResult() {{
                Authenticated = false;
                user.Id = null;
            }};
        }

        Boolean authenticated = this.userUtils.AuthenticateUser(credentials, user);
        return new UserAuthenticationResult() {{
            Authenticated = authenticated;
            UserId = user.Id;
        }};
    }

    @Override
    public String issueToken(String userId) {
        Token token = this.tokenUtils.issueToken(userId);
        this.tokenRepository.add(token);
        return token.Token;
    }

    @Override
    public TokenAuthenticationResult authenticateToken(String token) {
        Token dbToken = this.tokenRepository.getByToken(token);

        Boolean authenticated = this.tokenUtils.AuthenticateToken(token, dbToken);
        return new TokenAuthenticationResult() {{
            Authenticated = authenticated;
            UserId = dbToken.Id;
        }};
    }

    @Override
    public void updateToken(String userId) {
        Token token = this.tokenUtils.updateToken(userId);
        this.tokenRepository.update(token);
    }
}
