package kiwi.blog.common.config.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

@Slf4j
public class CustomRestErrorWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

	@Override
	public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
		log.debug("CustomRestErrorWebResponseExceptionTranslator translate");
		log.debug(e.getMessage());
		e.printStackTrace();
		
		return null;
	}

}