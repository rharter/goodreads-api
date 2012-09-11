//===============================================================================
// Copyright (c) 2010 Adam C Jones
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
//===============================================================================

package com.goodreads.api.v1;

import java.io.Serializable;

import android.sax.Element;
import android.sax.EndTextElementListener;

public class Request implements Serializable
{
	private static final long serialVersionUID = 0L;
	
	private boolean mAuthentication;
	private String mKey;
	private String mMethod;
	
	public void clear()
	{
		this.setAuthentication(false);
		this.setKey("");
		this.setMethod("");
	}
	
	public Request copy()
	{
		Request requestCopy = new Request();
		
		requestCopy.setAuthentication(this.getAuthentication());
		requestCopy.setKey(this.getKey());
		requestCopy.setMethod(this.getMethod());
		
		return requestCopy;
	}
	
	public static Request appendListener(Element parentElement)
	{
		final Request request = new Request();
		final Element requestElement = parentElement.getChild("request");
		
		requestElement.getChild("authentication").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				request.setAuthentication(Boolean.parseBoolean(body));
			}
		});
		
		requestElement.getChild("key").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				request.setKey(body);
			}
		});
		
		requestElement.getChild("request").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				request.setMethod(body);
			}
		});

		return request;
	}
	
	public boolean getAuthentication()
	{
		return mAuthentication;
	}
	
	public void setAuthentication(boolean authentication)
	{
		mAuthentication = authentication;
	}

	public String getKey()
	{
		return mKey;
	}

	public void setKey(String key)
	{
		mKey = key;
	}

	public String getMethod()
	{
		return mMethod;
	}

	public void setMethod(String method)
	{
		mMethod = method;
	}
}
