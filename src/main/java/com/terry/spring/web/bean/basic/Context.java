package com.terry.spring.web.bean.basic;

import com.google.common.io.ByteStreams;
import com.terry.spring.web.util.IOUtils;
import com.terry.spring.web.util.JsonUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

/**
 * Created by TerryShisan on 2018/2/9.
 * It is about on http call
 */
@Data
@NoArgsConstructor
public class Context {

    private static final Logger logger = LoggerFactory.getLogger("Testlog");

    private HttpServletRequest request;
    private HttpServletResponse response;

    public Context(HttpServletRequest request, HttpServletResponse response) {

        logString("{0},request:{1}", SessionContext.getKey(),request.getRequestURL().toString());

        this.request = request;
        this.response = response;
    }

    public String getParameter(String key){
        return request.getParameter(key);
    }

    public Object getAttribute(String key){
        return request.getAttribute(key);
    }

    public String getMethod() {return request.getMethod();}

    public void responseMsg(String responseMsg) {
        try {
            response.getWriter().write(responseMsg);
            logString("{0},response:{1}", SessionContext.getKey(),responseMsg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void response(Object respObject) {
        responseMsg(JsonUtil.toJson(respObject));
    }

    public int getBodyLength() {
        int contentLength = request.getContentLength();
        return  contentLength;
    }

    public <T> T getBody(Class<T> clazz, boolean isLogBody) {
        InputStream inputStream = null;
        try {
            int contentLength = request.getContentLength();
            String encoding = request.getCharacterEncoding();
            if (contentLength < 0 && request.getMethod() == HttpMethod.POST) {
                throw new RuntimeException("content length error,must greater than zero");
            }
            if(contentLength <= 0){
                return null;
            }
            inputStream = request.getInputStream();
            byte[] buffer = IOUtils.readFully(inputStream);
            String jsonString = new String(buffer,encoding);
            if(jsonString == null || jsonString.length() == 0){
                return null;
            }
            logString("{0},request:{1}", SessionContext.getKey(),"getBody");
            if (isLogBody) {
                logger.debug("{},request:{}", SessionContext.getKey(),jsonString);
            }
            return JsonUtil.toObject(jsonString,clazz);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("character set encode error");
        } catch (IOException e) {
            throw new RuntimeException("read body error");
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     *
     * @param logInfo request id:{0}，at:{1}"
     * @param values
     */
    private void logString(String logInfo,String ... values) {
        if(logger.isDebugEnabled()){
            logger.debug(MessageFormat.format(logInfo,values));
        }
    }
    private void logTrace(String logInfo,String ... values) {
        if(logger.isTraceEnabled()){
            logger.trace(MessageFormat.format(logInfo,values));
        }
    }

}
