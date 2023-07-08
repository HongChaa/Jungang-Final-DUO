package site.woulduduo.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class CustomErrorPageConfig implements ErrorPageRegistrar {

    /**
     * 에러 페이지 등록
     * @param registry the error page registry
     */
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage errorPage429 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/429");

        registry.addErrorPages(errorPage429);
    }
}
