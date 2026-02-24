package co.id.gpay.wallet.header;

import co.id.gpay.wallet.exception.ServiceException;
import co.id.gpay.wallet.utils.ChannelType;
import co.id.gpay.wallet.utils.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Log4j2
public class HeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod method)) {
            return true;
        }

        Header annotation = method.getMethodAnnotation(Header.class);

        if (annotation == null) {
            return true;
        }

        String requestId = request.getHeader("X-Request-Id");
        String channelStr = request.getHeader("X-Channel");

        if (requestId == null || channelStr == null) {
            throw new ServiceException(ErrorCode.MISSING_REQUIRED_HEADER);
        }

        ChannelType channel = ChannelType.valueOf(channelStr.toUpperCase());
        RequestHeader header = RequestHeaderBuilder.builder().requestId(requestId).channel(channel).build();
        RequestContext.set(header);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        RequestContext.clear();
    }
}
