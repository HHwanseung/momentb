package com.days.momentb.security.filter;//package com.days.momentb.security.filter;

import com.days.momentb.security.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
public class TokenCheckFilter extends OncePerRequestFilter {

    private JWTUtil jwtUtil;

    public TokenCheckFilter(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("------------TokenCheckFilter------------------");

        String path = request.getRequestURI();

        log.info(path);

        if(path.startsWith("/api/222")){
            //check token
            String authToken = request.getHeader("Authorization");//header 활용해서 토큰..

            if(authToken == null){ //토큰있는지 확인, 토큰이 없으면 fail이라고 확인해주는것

                log.info("authToken is null...../");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                // json 리턴
                response.setContentType("application/json;charset=utf-8");
                JSONObject json = new JSONObject();
                String message = "FAIL CHECK API TOKEN";
                json.put("code", "403");
                json.put("message", message);

                PrintWriter out = response.getWriter();
                out.print(json);
                out.close();
                return;
            }
            //jwt 검사(검증), 맨앞에 인증 타입 Bearer 토큰
            String tokenStr = authToken.substring(7);
            try {
                jwtUtil.validateToken(tokenStr);
            }catch(ExpiredJwtException ex){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                // json 리턴
                response.setContentType("application/json;charset=utf-8");
                JSONObject json = new JSONObject();
                String message = "EXPIRED API TOKEN.. TOO OLD";
                json.put("code", "401");
                json.put("message", message);

                PrintWriter out = response.getWriter();
                out.print(json);
                out.close();
                return;
            }catch(JwtException jex){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                // json 리턴
                response.setContentType("application/json;charset=utf-8");
                JSONObject json = new JSONObject();
                String message = "YOUR ACCESS TOKEN IS INVALID";
                json.put("code", "401");
                json.put("message", message);

                PrintWriter out = response.getWriter();
                out.print(json);
                out.close();
                return;
            }

            filterChain.doFilter(request,response);
        }else{
            log.info("------------TokenCheckFilter------------------");

            //다음 단계로 진행 하는 부분임
            filterChain.doFilter(request,response);

        }




    }




}
