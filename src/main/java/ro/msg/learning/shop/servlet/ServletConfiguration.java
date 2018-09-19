package ro.msg.learning.shop.servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfiguration {

    @Bean
    public ServletRegistrationBean oDataServlet(ShopODataServlet servlet) {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(servlet, "/odata/*");
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }
}
