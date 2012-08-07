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

public class Shelves
{
	private int mStart;
	private int mEnd;
	private int mTotal;
	private List<UserShelf> mUserShelves = new ArrayList<UserShelf>();
	
	public void clear()
	{
		this.setStart(0);
		this.setEnd(0);
		this.setTotal(0);
		mUserShelves.clear();
	}
	
	public Shelves copy()
	{
		Shelves shelvesCopy = new Shelves();
		
		shelvesCopy.setStart(this.getStart());
		shelvesCopy.setEnd(this.getEnd());
		shelvesCopy.setTotal(this.getTotal());
		
		List<UserShelf> userShelfList = new ArrayList<UserShelf>();
		for (int i = 0; i < mUserShelves.size(); i++ )
		{
			userShelfList.add(mUserShelves.get(i).copy());
		}
		shelvesCopy.setUserShelves(userShelfList);
		
		return shelvesCopy;
	}
	
	public static Shelves appendListener(Element parentElement, int depth)
	{
		final Shelves shelves = new Shelves();
		final Element shelvesElement = parentElement.getChild("shelves");
		
		shelvesElement.setStartElementListener(new StartElementListener()
		{
			@Override
			public void start(Attributes attributes)
			{
				shelves.setStart(Integer.parseInt(attributes.getValue("start")));
				shelves.setEnd(Integer.parseInt(attributes.getValue("end")));
				shelves.setTotal(Integer.parseInt(attributes.getValue("total")));
			}
		});
		
		shelves.setUserShelves(UserShelf.appendArrayListener(shelvesElement, depth + 1));

		return shelves;
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

	public List<UserShelf> getUserShelves()
	{
		return mUserShelves;
	}
	
	public void setUserShelves(List<UserShelf> userShelves)
	{
		mUserShelves = userShelves;
	}
}
