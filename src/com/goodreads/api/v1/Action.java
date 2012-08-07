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

import org.xml.sax.Attributes;

import android.sax.Element;
import android.sax.EndTextElementListener;
import android.sax.StartElementListener;

public class Action
{
	private String mActionType;
	private String mShelfName;
	private int mRating;
	
	public void clear()
	{
		this.setActionType("");
		this.setShelfName("");
		this.setRating(0);
	}
	
	public Action copy()
	{
		Action actionCopy = new Action();
		
		actionCopy.setActionType(this.getActionType());
		actionCopy.setShelfName(this.getShelfName());
		actionCopy.setRating(this.getRating());
		
		return actionCopy;
	}
	
	public static Action appendListener(Element parentElement, int depth)
	{
		final Action action = new Action();
		final Element actionElement = parentElement.getChild("action");
		
		actionElement.setStartElementListener(new StartElementListener()
		{
			@Override
			public void start(Attributes attributes)
			{
				action.setActionType(attributes.getValue("type"));
			}
		});
		
		actionElement.getChild("rating").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					action.setRating(Integer.parseInt(body));
				}
			}
		});
		
		actionElement.getChild("shelf").setStartElementListener(new StartElementListener()
		{
			@Override
			public void start(Attributes attributes)
			{
				action.setShelfName(attributes.getValue("name"));
			}
		});
		
		return action;
	}
	
	public String getActionType()
	{
		return mActionType;
	}
	public void setActionType(String actionType)
	{
		mActionType = actionType;
	}
	public String getShelfName()
	{
		return mShelfName;
	}
	public void setShelfName(String shelfName)
	{
		mShelfName = shelfName;
	}

	public int getRating()
	{
		return mRating;
	}

	public void setRating(int rating)
	{
		mRating = rating;
	}
}
