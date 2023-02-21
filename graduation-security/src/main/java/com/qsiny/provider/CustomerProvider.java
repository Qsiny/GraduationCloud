package com.qsiny.provider;

import com.qsiny.entity.CustomizeException;
import com.qsiny.service.VerifyService;
import com.qsiny.service.impl.CodeUserDetailsServiceImpl;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class CustomerProvider implements AuthenticationProvider {


    private final  CodeUserDetailsServiceImpl codeUserDetailsService;

    private final VerifyService verifyService;

    public CustomerProvider(CodeUserDetailsServiceImpl codeUserDetailsService, VerifyService verifyService) {
        this.codeUserDetailsService = codeUserDetailsService;
        this.verifyService = verifyService;
    }

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private final AccountStatusUserDetailsChecker accountStatusUserDetailsChecker = new AccountStatusUserDetailsChecker();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(CustomAuthenticationToken.class, authentication,
                () -> this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.onlySupports",
                        "Only CustomAuthenticationToken is supported"));
        String encodePhonenumber = authentication.getName();
        String phonenumber = verifyService.decode(encodePhonenumber);
        if(!StringUtils.hasText(phonenumber)){
            throw new CustomizeException("电话号码为空");
        }

        CustomAuthenticationToken principal = (CustomAuthenticationToken) authentication;
        UserDetails userDetails = retrieveUser(phonenumber, principal);
        //进行真正的比较操作
        additionalAuthenticationChecks(phonenumber,principal);

        //这里是对用户的5个配置进行检查
        this.accountStatusUserDetailsChecker.check(userDetails);

        //生成authentication
        return createSuccessAuthentication(principal,authentication,userDetails);
    }

    UserDetails retrieveUser(String phonenumber,CustomAuthenticationToken customAuthenticationToken){

        UserDetails userDetails = null;

        try {
            userDetails = this.codeUserDetailsService.loadUserByUsername(phonenumber);
        }catch (Exception e){
            //防止时间攻击
            mitigateAgainstTimingAttack(customAuthenticationToken);
            throw e;
        }
        return userDetails;
    }

    protected void additionalAuthenticationChecks(String phonenumber,
                                                  CustomAuthenticationToken authentication) throws AuthenticationException {
        //判断验证码是否为空
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
//        //获取解密过后验证码
//        String code = verifyService.decode((String) authentication.getCredentials());
        if (!this.verifyService.verifyPhoneCode(phonenumber, authentication.getCredentials().toString())) {
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }

    }
    private void mitigateAgainstTimingAttack(CustomAuthenticationToken authentication) {
        if (authentication.getCredentials() != null) {
            String phonenumber = authentication.getCredentials().toString();
            this.verifyService.verifyPhoneCode(phonenumber,"000000");
        }
    }

    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                                                         UserDetails user) {
        //这里创建一个real 的authentication对象
        CustomAuthenticationToken result = new CustomAuthenticationToken(principal,
                authentication.getCredentials(), user.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return (CustomAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
