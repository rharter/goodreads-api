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

public class Followers
{
	private int mStart;
	private int mEnd;
	private int mTotal;
	private List<User> mFollowers = new ArrayList<User>();
	
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
	public List<User> getFollowers()
	{
		return mFollowers;
	}
	public void setFollowers(List<User> followers)
	{
		mFollowers = followers;
	}

	public static Followers appendListener(final Element parentElement, int depth)
	{
		final Followers followers = new Followers();
		Element followersElement = parentElement.getChild("followers");
		followersElement.setStartElementListener(new StartElementListener()
		{
			@Override
			public void start(Attributes attributes)
			{
				followers.setStart(Integer.parseInt(attributes.getValue("start")));
				followers.setEnd(Integer.parseInt(attributes.getValue("end")));
				followers.setTotal(Integer.parseInt(attributes.getValue("total")));
			}
		});
		
		followers.setFollowers(User.appendArrayListener(followersElement, depth + 1));
		
		return followers;
	}
}
