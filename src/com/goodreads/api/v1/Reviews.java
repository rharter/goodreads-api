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
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import android.sax.Element;
import android.sax.StartElementListener;

public class Reviews implements Serializable
{
	private static final long serialVersionUID = 0L;
	
	private int mStart;
	private int mEnd;
	private int mTotal;
	private List<Review> mReviews = new ArrayList<Review>();
	
	public void clear()
	{
		this.setStart(0);
		this.setEnd(0);
		this.setTotal(0);
		this.mReviews.clear();
	}
	
	public Reviews copy()
	{
		Reviews reviewsCopy = new Reviews();
		
		reviewsCopy.setStart(this.getStart());
		reviewsCopy.setEnd(this.getEnd());
		reviewsCopy.setTotal(this.getTotal());
		
		List<Review> reviewList = this.getReviews();
		List<Review> reviewListCopy = reviewsCopy.getReviews();
		if (reviewList != null)
		{
			for ( int i = 0; i < reviewList.size(); i++ )
			{
				reviewListCopy.add(reviewList.get(i).copy());
			}
		}
		
		return reviewsCopy;
	}
	
	public static Reviews appendListener(Element parentElement, int depth)
	{
		final Reviews reviews = new Reviews();
		Element reviewsElement = parentElement.getChild("reviews");
	
		reviewsElement.setStartElementListener(new StartElementListener()
		{
			@Override
			public void start(Attributes attributes)
			{
				reviews.setStart(Integer.parseInt(attributes.getValue("start")));
				reviews.setEnd(Integer.parseInt(attributes.getValue("end")));
				reviews.setTotal(Integer.parseInt(attributes.getValue("total")));
			}
		});

		reviews.setReviews(Review.appendArrayListener(reviewsElement, depth + 1));
		
		return reviews;
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
	public List<Review> getReviews()
	{
		return mReviews;
	}
	public void setReviews(List<Review> reviews)
	{
		mReviews = reviews;
	}
}
