package kiwi.blog.common.annotation;

import kiwi.blog.common.config.authentication.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private ExpressionParser parser = new SpelExpressionParser();
    private BeanResolver beanResolver;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;

        // OAuth2Authentication auth
       // boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
       // return isLoginUserAnnotation && isUserClass;
    return true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
       // getAuthenticationPrincipal();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null) {
//            return null;
//        }
//
//        Object principal = authentication.getPrincipal();
//
//        AuthenticationPrincipal authPrincipal = findMethodAnnotation(AuthenticationPrincipal.class, parameter);
//
//        //assert authPrincipal != null;
//        String expressionToParse = authPrincipal.expression();
//        if (StringUtils.hasLength(expressionToParse)) {
//            StandardEvaluationContext context = new StandardEvaluationContext();
//            context.setRootObject(principal);
//            context.setVariable("this", principal);
//            context.setBeanResolver(beanResolver);
//
//            Expression expression = this.parser.parseExpression(expressionToParse);
//            principal = expression.getValue(context);
//        }
//
//        if (principal != null
//                && !parameter.getParameterType().isAssignableFrom(principal.getClass())) {
//
//            if (authPrincipal.errorOnInvalidType()) {
//                throw new ClassCastException(principal + " is not assignable to "
//                        + parameter.getParameterType());
//            }
//            else {
//                return null;
//            }
//        }
//
//
       // return principal;
      //  return jwtTokenProvider.getJwtTokenByClientCredentialForUser();
        return parameter;
    }

    private void getAuthenticationPrincipal(@AuthenticationPrincipal OAuth2Authentication auth) {

    }

    private <T extends Annotation> T findMethodAnnotation(Class<T> annotationClass,
                                                          MethodParameter parameter) {
        T annotation = parameter.getParameterAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }
        Annotation[] annotationsToSearch = parameter.getParameterAnnotations();
        for (Annotation toSearch : annotationsToSearch) {
            annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(),
                    annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }
}