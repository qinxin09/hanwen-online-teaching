package com.example.bilibili.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}

/**
 * 这个错误提示表示在设置 `Access-Control-Allow-Origin` 响应头时，`allowedOrigins` 参数不能设置为 `*`，因为它不能设置在响应头中。当 `allowCredentials` 设置为 `true` 时，浏览器将不允许使用 `*` 作为 `Access-Control-Allow-Origin` 的值。
 *
 * 为了解决这个问题，可以使用 `allowedOriginPatterns` 参数代替 `allowedOrigins` 参数。该参数允许使用通配符 `*`，从而允许匹配多个来源。你可以将上面的示例代码稍作修改，将 `allowedOrigins` 参数替换为 `allowedOriginPatterns` 参数，如下：
 *
 * ```
 * @Configuration
 * public class CorsConfig implements WebMvcConfigurer {
 *
 *     @Override
 *     public void addCorsMappings(CorsRegistry registry) {
 *         registry.addMapping("/**")
 *             .allowedOriginPatterns("*")
 *             .allowedMethods("*")
 *             .allowedHeaders("*")
 *             .allowCredentials(true);
 *     }
 * }
 * ```
 *
 * 在上面的代码中，我们将 `allowedOrigins`
 * 参数更改为 `allowedOriginPatterns`
 * 参数，并使用通配符 `*` 允许匹配所有来源。
 * 这样，之前的错误就能够得到解决了。
 * 注意，要在 `allowCredentials` 方法中将值设置为 `true`，
 * 以启用跨域请求时的 Cookie。
 */
