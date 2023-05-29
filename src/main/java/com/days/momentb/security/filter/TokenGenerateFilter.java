package com.days.momentb.security.filter;//package com.days.momentb.security.filter;

import com.days.momentb.member.dto.MemberDTO;
import com.days.momentb.security.util.JWTUtil;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class TokenGenerateFilter extends AbstractAuthenticationProcessingFilter {

    private JWTUtil jwtUtil;

    public TokenGenerateFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        //public으로 만들어서, 외부에서 호출 할수있게끔 수정
        //String-loginurl,, 그리고 인증매니저가필요!

        super(defaultFilterProcessesUrl, authenticationManager);
        this.jwtUtil = jwtUtil;

    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        String requestStr = extracted(request);

        log.info("try to login with json for api....");
        log.info(requestStr);

        JSONObject jObject = new JSONObject(requestStr);

        String userId = jObject.getString("userId");
        String password = jObject.getString("password");

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userId, password);

        Authentication result = getAuthenticationManager().authenticate(authToken);

        log.info("---------------------------");
        log.info(result);

        return result;
    }

    private String extracted(HttpServletRequest request)  {
        InputStream inputStream = null;
        ByteArrayOutputStream bos = null;

        try {
            inputStream = request.getInputStream();
            bos = new ByteArrayOutputStream();
            byte[] arr = new byte[1024];

            while (true) {
                int count = inputStream.read(arr);
                if (count == -1) {
                    break;
                }
                bos.write(arr, 0, count);
            }
        }catch(Exception e){

        }finally {
            try{inputStream.close(); }catch(Exception e){}
            try {bos.close();}catch(Exception e){}
        }
        return bos.toString();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
       log.info("successfulAuthentication:"+authResult);
       MemberDTO memberDTO = (MemberDTO) authResult.getPrincipal();

       String mid =memberDTO.getMid();

       log.info("MEMBER MID:" +mid);//사용자 정보를 얻어왔음

        String token = jwtUtil.generateToken(mid);//사용자 정보를 가지고 토큰만듬

        JSONObject res = new JSONObject(Map.of("ACCESS",token));

        response.setContentType("application/json");
        PrintWriter out  = response.getWriter();
        out.println(res.toString());
        out.close();

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("unsuccessfulAuthentication: " + failed);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // json 리턴
        response.setContentType("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        String message = failed.getMessage();
        json.put("code", "401");
        json.put("message", message);

        PrintWriter out = response.getWriter();
        out.print(json);
        out.close();
    }
}
