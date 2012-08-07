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

public class Comments
{
	private int mStart;
	private int mEnd;
	private int mTotal;
	private List<Comment> mComments = new ArrayList<Comment>();
	
	public void clear()
	{
		this.setStart(0);
		this.setEnd(0);
		this.setTotal(0);
		mComments.clear();
	}
	
	public Comments copy()
	{
		Comments commentsCopy = new Comments();
		
		commentsCopy.setStart(this.getStart());
		commentsCopy.setEnd(this.getEnd());
		commentsCopy.setTotal(this.getTotal());
		
		List<Comment> commentsListCopy = new ArrayList<Comment>();
		for (int i = 0; i < mComments.size(); i++)
		{
			commentsListCopy.add(mComments.get(i).copy());
		}
		commentsCopy.setComments(commentsListCopy);
		
		return commentsCopy;
	}
	
	public static Comments appendListener(Element parentElement, int depth)
	{
		final Comments comments = new Comments();
		final Element commentsElement = parentElement.getChild("comments");
		
		commentsElement.setStartElementListener(new StartElementListener()
		{
			@Override
			public void start(Attributes attributes)
			{
				String value = attributes.getValue("start");
				if (value != null && value.length() != 0)
				{
					comments.setStart(Integer.parseInt(value));
				}
				value = attributes.getValue("end");
				if (value != null && value.length() != 0)
				{
					comments.setEnd(Integer.parseInt(value));
				}
				value = attributes.getValue("total");
				if (value != null && value.length() != 0)
				{
					comments.setTotal(Integer.parseInt(value));
				}
			}
		});
		
		comments.setComments(Comment.appendArrayListener(commentsElement, depth + 1));
		
		return comments;
	}
	
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

	public List<Comment> getComments()
	{
		return mComments;
	}

	public void setComments(List<Comment> _Comments)
	{
		mComments = _Comments;
	}
}
