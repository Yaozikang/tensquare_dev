package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 要子康
 * @description ManagerFilter
 * @since 2020/2/1 10:12
 */
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 执行在请求前或者后执行
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 多个过滤器的执行顺序，数字越小越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 当前过滤器是否开启
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行操作 return 任何object的值都表示继续执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过后台过滤器了");

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String header =request.getHeader("Authorization");

        if (request.getMethod().equals("OPTIONS")){
            return null;
        }

        if (request.getRequestURI().indexOf("login") > 0){
            return null;
        }

        if (header != null && !"".equals(header)){
            if (header.startsWith("Bearer ")){
                String token = header.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if (roles.equals("admin")){
                        requestContext.addZuulRequestHeader("Authorization", header);
                        return null;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    requestContext.setSendZuulResponse(false);
                }
            }
        }

        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(403);
        requestContext.setResponseBody("权限不足");
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        return null;
    }
}
