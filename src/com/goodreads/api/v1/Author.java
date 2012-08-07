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
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.StartElementListener;

public class Author
{
	private int mId;
	private String mName;
	private String mImageUrl;
	private String mSmallImageUrl;
	private String mLink;
	private float mAverageRating;
	private int mRatingsCount;
	private int mTextReviewsCount;
	private int mBooksStart;
	private int mBooksEnd;
	private int mBooksTotal;
	private int mFansCount;
	private String mAbout;
	private String mInfluences;
	private int mWorksCount;
	private String mGender;
	private String mHometown;
	private String mBornAt;
	private String mDiedAt;
	private String mUserId;
	private List<Book> mBooks = new ArrayList<Book>();
	
	public Author copy()
	{
		Author authorCopy = new Author();
		
		authorCopy.setAverageRating(this.getAverageRating());
		authorCopy.setId(this.getId());
		authorCopy.setImageUrl(this.getImageUrl());
		authorCopy.setLink(this.getLink());
		authorCopy.setName(this.getName());
		authorCopy.setRatingsCount(this.getRatingsCount());
		authorCopy.setSmallImageUrl(this.getSmallImageUrl());
		authorCopy.setTextReviewsCount(this.getTextReviewsCount());
		authorCopy.setBooksStart(this.getBooksStart());
		authorCopy.setBooksEnd(this.getBooksEnd());
		authorCopy.setBooksTotal(this.getBooksTotal());
		authorCopy.setFansCount(this.getFansCount());
		authorCopy.setAbout(this.getAbout());
		authorCopy.setInfluences(this.getInfluences());
		authorCopy.setWorksCount(this.getWorksCount());
		authorCopy.setGender(this.getGender());
		authorCopy.setHometown(this.getHometown());
		authorCopy.setBornAt(this.getBornAt());
		authorCopy.setDiedAt(this.getDiedAt());
		authorCopy.setUserId(this.getUserId());
		
		List<Book> booksCopy = new ArrayList<Book>();
		for (int i = 0; i < mBooks.size(); i++)
		{
			booksCopy.add(mBooks.get(i).copy());
		}
		authorCopy.setBooks(booksCopy);

		return authorCopy;
	}

	public void clear()
	{
		this.setAverageRating(0);
		this.setId(0);
		this.setImageUrl("");
		this.setLink("");
		this.setName("");
		this.setRatingsCount(0);
		this.setSmallImageUrl("");
		this.setTextReviewsCount(0);
		this.setBooksEnd(0);
		this.setBooksStart(0);
		this.setBooksTotal(0);
		this.setFansCount(0);
		this.setAbout("");
		this.setInfluences("");
		this.setWorksCount(0);
		this.setGender("");
		this.setHometown("");
		this.setBornAt("");
		this.setDiedAt("");
		this.setUserId("");
		mBooks.clear();
	}
	
	public static Author appendListener(final Element parentElement, int depth)
	{
		final Author author = new Author();
		
		Element authorElement = parentElement.getChild("author");
		
		appendCommonListeners(authorElement, author, depth);
		
		return author;
	}
	
	public static List<Author> appendArrayListener(final Element parentElement, int depth)
	{
		final List<Author> authors = new ArrayList<Author>();
		final Author author = new Author();
		
		Element authorElement = parentElement.getChild("author");
		
		authorElement.setEndElementListener(new EndElementListener()
		{
			@Override
			public void end()
			{
				authors.add(author.copy());
				author.clear();
			}
		});
		
		appendCommonListeners(authorElement, author, depth);
		
		return authors;
	}
	
	private static void appendCommonListeners(final Element authorElement, final Author author, int depth)
	{
		authorElement.getChild("id").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					author.setId(Integer.parseInt(body));
				}
			}
		});
		
		authorElement.getChild("name").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				author.setName(body);
			}
		});
		
		authorElement.getChild("image_url").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				author.setImageUrl(body);
			}
		});
		
		authorElement.getChild("small_image_url").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				author.setSmallImageUrl(body);
			}
		});
		
		authorElement.getChild("link").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				author.setLink(body);
			}
		});
		
		authorElement.getChild("average_rating").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					author.setAverageRating(Float.parseFloat(body));
				}
			}
		});
		
		authorElement.getChild("ratings_count").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					author.setRatingsCount(Integer.parseInt(body));
				}
			}
		});
		
		authorElement.getChild("text_reviews_count").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					author.setTextReviewsCount(Integer.parseInt(body));
				}
			}
		});
		
		authorElement.getChild("fans_count").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					author.setFansCount(Integer.parseInt(body));
				}
			}
		});

		authorElement.getChild("about").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				author.setAbout(body);
			}
		});

		authorElement.getChild("influences").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				author.setInfluences(body);
			}
		});

		authorElement.getChild("works_count").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					author.setWorksCount(Integer.parseInt(body));
				}
			}
		});

		authorElement.getChild("gender").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				author.setGender(body);
			}
		});

		authorElement.getChild("hometown").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				author.setHometown(body);
			}
		});

		authorElement.getChild("born_at").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				author.setBornAt(body);
			}
		});

		authorElement.getChild("died_at").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				author.setDiedAt(body);
			}
		});

		authorElement.getChild("user").getChild("id").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				author.setUserId(body);
			}
		});
		
		if (depth < 2 )
		{
			Element booksElement = authorElement.getChild("books");
			booksElement.setStartElementListener(new StartElementListener()
			{
				@Override
				public void start(Attributes attributes)
				{
					String value = attributes.getValue("start");
					if (value != null && value.length() != 0)
					{
						author.setBooksStart(Integer.parseInt(value));
					}
					value = attributes.getValue("end");
					if (value != null && value.length() != 0)
					{
						author.setBooksEnd(Integer.parseInt(value));
					}
					value = attributes.getValue("total");
					if (value != null && value.length() != 0)
					{
						author.setBooksTotal(Integer.parseInt(value));
					}
				}
			});
			author.setBooks(Book.appendArrayListener(booksElement, depth + 1));
		}
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
	public int getTextReviewsCount()
	{
		return mTextReviewsCount;
	}
	public void setTextReviewsCount(int textReviewsCount)
	{
		mTextReviewsCount = textReviewsCount;
	}

	public int getBooksStart()
	{
		return mBooksStart;
	}

	public void setBooksStart(int booksStart)
	{
		mBooksStart = booksStart;
	}

	public int getBooksEnd()
	{
		return mBooksEnd;
	}

	public void setBooksEnd(int booksEnd)
	{
		mBooksEnd = booksEnd;
	}

	public int getBooksTotal()
	{
		return mBooksTotal;
	}

	public void setBooksTotal(int booksTotal)
	{
		mBooksTotal = booksTotal;
	}

	public List<Book> getBooks()
	{
		return mBooks;
	}

	public void setBooks(List<Book> books)
	{
		mBooks = books;
	}

	public int getFansCount()
	{
		return mFansCount;
	}

	public void setFansCount(int fansCount)
	{
		mFansCount = fansCount;
	}

	public String getAbout()
	{
		return mAbout;
	}

	public void setAbout(String about)
	{
		mAbout = about;
	}

	public String getInfluences()
	{
		return mInfluences;
	}

	public void setInfluences(String influences)
	{
		mInfluences = influences;
	}

	public int getWorksCount()
	{
		return mWorksCount;
	}

	public void setWorksCount(int worksCount)
	{
		mWorksCount = worksCount;
	}

	public String getGender()
	{
		return mGender;
	}

	public void setGender(String gender)
	{
		mGender = gender;
	}

	public String getHometown()
	{
		return mHometown;
	}

	public void setHometown(String hometown)
	{
		mHometown = hometown;
	}

	public String getBornAt()
	{
		return mBornAt;
	}

	public void setBornAt(String bornAt)
	{
		mBornAt = bornAt;
	}

	public String getDiedAt()
	{
		return mDiedAt;
	}

	public void setDiedAt(String diedAt)
	{
		mDiedAt = diedAt;
	}

	public String getUserId()
	{
		return mUserId;
	}

	public void setUserId(String userId)
	{
		mUserId = userId;
	}
}
