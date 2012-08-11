package com.goodreads.api.v1;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

public class GoodreadsApi extends DefaultApi10a {
	private static final String AUTHORIZE_URL = 
			"http://www.goodreads.com/oauth/authorize?mobile=1&oauth_token=%s";
	
	@Override
	public String getAccessTokenEndpoint() {
		return "http://www.goodreads.com/oauth/access_token";
	}
	
	@Override
	public String getRequestTokenEndpoint() {
		return "http://www.goodreads.com/oauth/request_token";
	}
	
	@Override
	public String getAuthorizationUrl(Token requestToken) {
		return String.format(AUTHORIZE_URL, requestToken.getToken());
	}
}
