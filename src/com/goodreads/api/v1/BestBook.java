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

public class BestBook implements Serializable
{
	private static final long serialVersionUID = 0L;
	
	private int mId;
	private String mTitle;
	private Author mAuthor = new Author();
	private String mImageUrl;
	private String mSmallImageUrl;
	
	public void clear()
	{
		this.setId(0);
		this.setTitle("");
		this.setImageUrl("");
		this.setSmallImageUrl("");
		this.mAuthor.clear();
	}
	
	public BestBook copy()
	{
		BestBook bestBookCopy = new BestBook();

		bestBookCopy.setId(this.getId());
		bestBookCopy.setTitle(this.getTitle());
		bestBookCopy.setAuthor(this.getAuthor().copy());
		bestBookCopy.setImageUrl(this.getImageUrl());
		bestBookCopy.setSmallImageUrl(this.getSmallImageUrl());

		return bestBookCopy;
	}
	
	public static BestBook appendListener(final Element parentElement, int depth)
	{
		final BestBook bestBook = new BestBook();
		
		Element bestBookElement = parentElement.getChild("best_book");
		
		bestBook.setAuthor(Author.appendListener(bestBookElement, depth + 1));
		appendCommonListeners(bestBookElement, bestBook);
		
		return bestBook;
	}
	
//	public static List<BestBook> appendArrayListener(final Element parentElement)
//	{
//		final List<BestBook> bestBooks = new ArrayList<BestBook>();
//		
//		return bestBooks;
//	}
	
	private static void appendCommonListeners(final Element bestBookElement, final BestBook bestBook)
	{
		bestBookElement.getChild("id").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					bestBook.setId(Integer.parseInt(body));
				}
			}
		});

		bestBookElement.getChild("title").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				bestBook.setTitle(body);
			}
		});

		bestBookElement.getChild("image_url").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				bestBook.setImageUrl(body);
			}
		});

		bestBookElement.getChild("small_image_url").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				bestBook.setSmallImageUrl(body);
			}
		});
	}

	public int getId()
	{
		return mId;
	}

	public void setId(int id)
	{
		mId = id;
	}

	public String getTitle()
	{
		return mTitle;
	}

	public void setTitle(String title)
	{
		mTitle = title;
	}

	public Author getAuthor()
	{
		return mAuthor;
	}

	public void setAuthor(Author author)
	{
		mAuthor = author;
	}

	public String getImageUrl()
	{
		return mImageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		mImageUrl = imageUrl;
	}

	public String getSmallImageUrl()
	{
		return mSmallImageUrl;
	}

	public void setSmallImageUrl(String smallImageUrl)
	{
		mSmallImageUrl = smallImageUrl;
	}
}
