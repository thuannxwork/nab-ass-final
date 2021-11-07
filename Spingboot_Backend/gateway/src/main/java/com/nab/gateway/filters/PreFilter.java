package com.nab.gateway.filters;

import com.nab.gateway.cache.CacheService;
import com.nab.gateway.model.Token;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

public class PreFilter extends ZuulFilter {

    @Autowired
    CacheService cacheService;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return null;
        }
        String uri = request.getRequestURI();
        if (uri.toLowerCase().contains("/ping")
                || uri.toLowerCase().contains("/api/sso/home/auth")
                || uri.toLowerCase().contains("/api/sso/home/signup")
                || uri.toLowerCase().contains("/api/sso/home/logout")
        ) {
            return null;
        }

        //Validate token
        String accessToken = getJwtFromRequest(request);
        if (StringUtils.isEmpty(accessToken)) {
            setFailedRequest("Token is required");
            return null;
        }
        Token token = cacheService.getTokenFromCache(accessToken);
        if (token == null) {
            setFailedRequest("Token not found or expired");
            return null;
        } else {
            ctx.addZuulRequestHeader("USERNAME", token.getUsername() + "");
            response.setHeader("USERNAME", token.getUsername() + "");
        }
        return null;
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void setFailedRequest(String body) {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        ctx.setResponseStatusCode(HttpStatus.OK.value());
        if (ctx.getResponseBody() == null) {
            response.setContentType("application/json;charset=UTF-8");
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
        }
    }
}
