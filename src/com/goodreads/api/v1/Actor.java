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

public class Actor implements Serializable
{
	private static final long serialVersionUID = 0L;
	
	private int mId;
	private String mName;
	private String mImageUrl;
	private String mLink;
	
	public void clear()
	{
		this.setId(0);
		this.setName("");
		this.setImageUrl("");
		this.setLink("");
	}
	
	public Actor copy()
	{
		Actor actorCopy = new Actor();
		
		actorCopy.setId(this.getId());
		actorCopy.setName(this.getName());
		actorCopy.setImageUrl(this.getImageUrl());
		actorCopy.setLink(this.getLink());
		
		return actorCopy;
	}
	
	public static Actor appendListener(Element parentElement, int depth)
	{
		final Actor actor = new Actor();
		final Element actorElement = parentElement.getChild("actor");
		
		actorElement.getChild("id").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					actor.setId(Integer.parseInt(body));
				}
			}
		});
		
		actorElement.getChild("name").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				actor.setName(body);
			}
		});
		
		actorElement.getChild("image_url").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				actor.setImageUrl(body);
			}
		});
		
		actorElement.getChild("link").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				actor.setLink(body);
			}
		});
		
		return actor;
	}
	
	public int getId()
	{
		return mId;
	}
	public void setId(int id)
	{
		mId = id;
	}
	public String getName()
	{
		return mName;
	}
	public void setName(String name)
	{
		mName = name;
	}
	public String getImageUrl()
	{
		return mImageUrl;
	}
	public void setImageUrl(String imageUrl)
	{
		mImageUrl = imageUrl;
	}
	public String getLink()
	{
		return mLink;
	}
	public void setLink(String link)
	{
		mLink = link;
	}
}
