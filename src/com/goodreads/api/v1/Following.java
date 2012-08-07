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

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import android.sax.Element;
import android.sax.StartElementListener;

public class Following
{
	private int mStart;
	private int mEnd;
	private int mTotal;
	private List<User> mFollowing = new ArrayList<User>();
	
	public int getStart()
	{
		return mStart;
	}
	public void setStart(int start)
	{
		mStart = start;
	}
	public int getEnd()
	{
		return mEnd;
	}
	public void setEnd(int end)
	{
		mEnd = end;
	}
	public int getTotal()
	{
		return mTotal;
	}
	public void setTotal(int total)
	{
		mTotal = total;
	}
	public List<User> getFollowing()
	{
		return mFollowing;
	}
	public void setFollowing(List<User> following)
	{
		mFollowing = following;
	}

	public static Following appendListener(final Element parentElement, int depth)
	{
		final Following following = new Following();
		Element followingElement = parentElement.getChild("following");
		followingElement.setStartElementListener(new StartElementListener()
		{
			@Override
			public void start(Attributes attributes)
			{
				following.setStart(Integer.parseInt(attributes.getValue("start")));
				following.setEnd(Integer.parseInt(attributes.getValue("end")));
				following.setTotal(Integer.parseInt(attributes.getValue("total")));
			}
		});
		
		following.setFollowing(User.appendArrayListener(followingElement, depth + 1));
		
		return following;
	}
}
