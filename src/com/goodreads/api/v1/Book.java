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

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;

public class Book implements Serializable
{
	private static final long serialVersionUID = 0L;
	
	private String mId;
	private String mIsbn;
	private String mIsbn13;
	private int mTextReviewsCount;
	private String mTitle;
	private String mImageUrl;
	private String mSmallImageUrl;
	private String mLink;
	private int mPages;
	private float mAverageRating;
	private int mRatingsCount;
	private String mDescription;
	private List<Author> mAuthors = new ArrayList<Author>();
	private int mYearPublished;
	private Reviews mReviews = new Reviews();
	
	public Book copy()
	{
		Book bookCopy = new Book();
		
		List<Author> authorsCopy = new ArrayList<Author>();
		for (int i = 0; i < this.mAuthors.size(); i++ )
		{
			authorsCopy.add(this.mAuthors.get(i).copy());
		}
		bookCopy.setAuthors(authorsCopy);
		
		bookCopy.setAverageRating(this.getAverageRating());
		bookCopy.setDescription(this.getDescription());
		bookCopy.setId(this.getId());
		bookCopy.setImageUrl(this.getImageUrl());
		bookCopy.setIsbn(this.getIsbn());
		bookCopy.setIsbn13(this.getIsbn13());
		bookCopy.setLink(this.getLink());
		bookCopy.setPages(this.getPages());
		bookCopy.setRatingsCount(this.getRatingsCount());
		bookCopy.setSmallImageUrl(this.getSmallImageUrl());
		bookCopy.setTextReviewsCount(this.getTextReviewsCount());
		bookCopy.setTitle(this.getTitle());
		bookCopy.setYearPublished(this.getYearPublished());
		bookCopy.setReviews(mReviews.copy());
		
		return bookCopy;
	}
	
	public void clear()
	{
		this.mAuthors.clear();
		this.setAverageRating(0);
		this.setDescription("");
		this.setId("");
		this.setImageUrl("");
		this.setIsbn("");
		this.setIsbn13("");
		this.setLink("");
		this.setPages(0);
		this.setRatingsCount(0);
		this.setSmallImageUrl("");
		this.setTextReviewsCount(0);
		this.setTitle("");
		this.setYearPublished(0);
		this.mReviews.clear();
	}

	public static Book appendListener(Element parentElement, int depth)
	{
		final Book book = new Book();
		final Element bookElement = parentElement.getChild("book");
		
		appendCommonListeners(bookElement, book, depth);

		return book;
	}
	

	public static List<Book> appendArrayListener(Element parentElement, int depth)
	{
		final List<Book> books = new ArrayList<Book>();
		final Book book = new Book();
		final Element bookElement = parentElement.getChild("book");
		
		appendCommonListeners(bookElement, book, depth);
		
		bookElement.setEndElementListener(new EndElementListener()
		{
			@Override
			public void end()
			{
				books.add(book.copy());
				book.clear();
			}
		});
		
		return books;
	}

	private static void appendCommonListeners(final Element bookElement, final Book book, int depth)
	{
		bookElement.getChild("id").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					book.setId(body);
				}
			}
		});
		
		bookElement.getChild("isbn").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				book.setIsbn(body);
			}
		});
		
		bookElement.getChild("isbn13").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				book.setIsbn13(body);
			}
		});
		
		bookElement.getChild("text_reviews_count").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					book.setTextReviewsCount(Integer.parseInt(body));
				}
			}
		});
		
		bookElement.getChild("title").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				book.setTitle(body);
			}
		});
		
		bookElement.getChild("image_url").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				book.setImageUrl(body);
			}
		});
		
		bookElement.getChild("small_image_url").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				book.setSmallImageUrl(body);
			}
		});
		
		bookElement.getChild("link").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				book.setLink(body);
			}
		});
		
		bookElement.getChild("num_pages").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					book.setPages(Integer.parseInt(body));
				}
			}
		});
		
		bookElement.getChild("average_rating").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					book.setAverageRating(Float.parseFloat(body));
				}
			}
		});
		
		bookElement.getChild("ratings_count").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					book.setRatingsCount(Integer.parseInt(body));
				}
			}
		});
		
		bookElement.getChild("description").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				book.setDescription(body);
			}
		});
		
		bookElement.getChild("published").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					book.setYearPublished(Integer.parseInt(body));
				}
			}
		});
		
		Element authorsElement = bookElement.getChild("authors"); 
		book.setAuthors(Author.appendArrayListener(authorsElement, depth + 1));
		
		if (depth < 2)
		{
			book.setReviews(Reviews.appendListener(bookElement, depth + 1));
		}
	}
	
	public String getId()
	{
		return mId;
	}
	public void setId(String id)
	{
		mId = id;
	}
	public String getIsbn()
	{
		return mIsbn;
	}
	public void setIsbn(String isbn)
	{
		mIsbn = isbn;
	}
	public String getIsbn13()
	{
		return mIsbn13;
	}
	public void setIsbn13(String isbn13)
	{
		mIsbn13 = isbn13;
	}
	public int getTextReviewsCount()
	{
		return mTextReviewsCount;
	}
	public void setTextReviewsCount(int textReviewsCount)
	{
		mTextReviewsCount = textReviewsCount;
	}
	public String getTitle()
	{
		return mTitle;
	}
	public void setTitle(String title)
	{
		mTitle = title;
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
	public String getLink()
	{
		return mLink;
	}
	public void setLink(String link)
	{
		mLink = link;
	}
	public int getPages()
	{
		return mPages;
	}
	public void setPages(int pages)
	{
		mPages = pages;
	}
	public float getAverageRating()
	{
		return mAverageRating;
	}
	public void setAverageRating(float averageRating)
	{
		mAverageRating = averageRating;
	}
	public int getRatingsCount()
	{
		return mRatingsCount;
	}
	public void setRatingsCount(int ratingsCount)
	{
		mRatingsCount = ratingsCount;
	}
	public String getDescription()
	{
		return mDescription;
	}
	public void setDescription(String description)
	{
		mDescription = description;
	}
	public List<Author> getAuthors()
	{
		return mAuthors;
	}
	public void setAuthors(List<Author> authors)
	{
		mAuthors = authors;
	}
	public int getYearPublished()
	{
		return mYearPublished;
	}
	public void setYearPublished(int yearPublished)
	{
		mYearPublished = yearPublished;
	}

	public Reviews getReviews()
	{
		return mReviews;
	}

	public void setReviews(Reviews reviews)
	{
		mReviews = reviews;
	}
}
