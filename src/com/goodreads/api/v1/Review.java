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
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.StartElementListener;

public class Review implements Serializable
{
	private static final long serialVersionUID = 0L;
	
	private String mId;
	private Book mBook = new Book();
	private int mRating;
	private int mVotes;
	private boolean mSpoilerFlag;
	private String mRecommendedFor;
	private String mRecommendedBy;
	private String mStartedAt;
	private String mReadAt;
	private String mDateAdded;
	private String mDateUpdated;
	private String mBody;
	private String mUrl;
	private String mLink;
	private String mCommentsCount;
	private List<String> mShelves = new ArrayList<String>();
	private User mUser = new User();
	private Comments mComments = new Comments();
	
	public Review copy()
	{
		Review reviewCopy = new Review();
		
		reviewCopy.setBody(this.getBody());
		reviewCopy.setBook(this.getBook().copy());
		reviewCopy.setDateAdded(this.getDateAdded());
		reviewCopy.setDateUpdated(this.getDateUpdated());
		reviewCopy.setId(this.getId());
		reviewCopy.setLink(this.getLink());
		reviewCopy.setRating(this.getRating());
		reviewCopy.setReadAt(this.getReadAt());
		reviewCopy.setRecommendedBy(this.getRecommendedBy());
		reviewCopy.setRecommendedFor(this.getRecommendedFor());
		reviewCopy.setSpoilerFlag(this.getSpoilerFlag());
		reviewCopy.setStartedAt(this.getStartedAt());
		reviewCopy.setUrl(this.getUrl());
		reviewCopy.setVotes(this.getVotes());
		reviewCopy.setUser(mUser.copy());
		reviewCopy.setCommentsCount(this.getCommentsCount());
		
		List<String> shelvesCopy = new ArrayList<String>();
		for (int i = 0; i < mShelves.size(); i++ )
		{
			shelvesCopy.add(mShelves.get(i));
		}
		reviewCopy.setShelves(shelvesCopy);
		
		reviewCopy.setComments(this.getComments().copy());
		
		return reviewCopy;
	}
	
	public void clear()
	{
		this.setBody("");
		this.getBook().clear();
		this.setDateAdded("");
		this.setDateUpdated("");
		this.setId("");
		this.setLink("");
		this.setRating(0);
		this.setReadAt("");
		this.setRecommendedBy("");
		this.setRecommendedFor("");
		this.setSpoilerFlag(false);
		this.setStartedAt("");
		this.setUrl("");
		this.setVotes(0);	
		this.setCommentsCount("");
		this.mShelves.clear();
		this.mUser.clear();
		this.mComments.clear();
	}
	
	public static Review appendListener(final Element parentElement, int depth)
	{
		final Element reviewElement = parentElement.getChild("review");
		final Review review = new Review();
		
		appendCommonListeners(reviewElement, review, depth);
		
		return review;
	}
	
	public static List<Review> appendArrayListener(final Element parentElement, int depth)
	{
		final Element reviewElement = parentElement.getChild("review");
		final List<Review> reviewList = new ArrayList<Review>();
		final Review review = new Review();
		
		appendCommonListeners(reviewElement, review, depth);
		
		reviewElement.setEndElementListener(new EndElementListener()
		{
			@Override
			public void end()
			{
				reviewList.add(review.copy());
				review.clear();
			}
		});
		
		return reviewList;
	}
	
	private static void appendCommonListeners(final Element reviewElement, final Review review, int depth)
	{
		reviewElement.getChild("id").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				review.setId(body);
			}
		});
		
		reviewElement.getChild("rating").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				review.setRating(Integer.parseInt(body));
			}
		});
		
		reviewElement.getChild("votes").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				review.setVotes(Integer.parseInt(body));
			}
		});
		
		reviewElement.getChild("spoiler_flag").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				review.setSpoilerFlag(Boolean.parseBoolean(body));
			}
		});
		
		reviewElement.getChild("recommended_for").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				review.setRecommendedFor(body);
			}
		});
		
		reviewElement.getChild("recommended_by").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				review.setRecommendedBy(body);
			}
		});
		
		reviewElement.getChild("started_at").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				review.setStartedAt(body);
			}
		});
		
		reviewElement.getChild("read_at").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				review.setReadAt(body);
			}
		});
		
		reviewElement.getChild("date_added").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				review.setDateAdded(body);
			}
		});
		
		reviewElement.getChild("date_updated").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				review.setDateUpdated(body);
			}
		});
		
		reviewElement.getChild("body").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				review.setBody(body);
			}
		});
		
		reviewElement.getChild("url").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				review.setUrl(body);
			}
		});
		
		reviewElement.getChild("link").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				review.setLink(body);
			}
		});
		
		reviewElement.getChild("comments_count").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				review.setCommentsCount(body);
			}
		});
		
		review.setBook(Book.appendListener(reviewElement, depth + 1));
		
		Element shelvesElement = reviewElement.getChild("shelves");
		shelvesElement.getChild("shelf").setStartElementListener(new StartElementListener()
		{
			@Override
			public void start(Attributes attributes)
			{
				review.getShelves().add(attributes.getValue("name"));
			}
		});
		
		review.setUser(User.appendListener(reviewElement, depth + 1));
		review.setComments(Comments.appendListener(reviewElement, depth +1));
	}
	
	public String getId()
	{
		return mId;
	}

	public void setId(String id)
	{
		mId = id;
	}

	public Book getBook()
	{
		return mBook;
	}

	public void setBook(Book book)
	{
		mBook = book;
	}

	public int getRating()
	{
		return mRating;
	}

	public void setRating(int rating)
	{
		mRating = rating;
	}

	public int getVotes()
	{
		return mVotes;
	}

	public void setVotes(int votes)
	{
		mVotes = votes;
	}

	public boolean getSpoilerFlag()
	{
		return mSpoilerFlag;
	}

	public void setSpoilerFlag(boolean spoilerFlag)
	{
		mSpoilerFlag = spoilerFlag;
	}

	public String getRecommendedFor()
	{
		return mRecommendedFor;
	}

	public void setRecommendedFor(String recommendedFor)
	{
		mRecommendedFor = recommendedFor;
	}

	public String getRecommendedBy()
	{
		return mRecommendedBy;
	}

	public void setRecommendedBy(String recommendedBy)
	{
		mRecommendedBy = recommendedBy;
	}

	public String getStartedAt()
	{
		return mStartedAt;
	}

	public void setStartedAt(String startedAt)
	{
		mStartedAt = startedAt;
	}

	public String getReadAt()
	{
		return mReadAt;
	}

	public void setReadAt(String readAt)
	{
		mReadAt = readAt;
	}

	public String getDateAdded()
	{
		return mDateAdded;
	}

	public void setDateAdded(String dateAdded)
	{
		mDateAdded = dateAdded;
	}

	public String getDateUpdated()
	{
		return mDateUpdated;
	}

	public void setDateUpdated(String dateUpdated)
	{
		mDateUpdated = dateUpdated;
	}

	public String getBody()
	{
		return mBody;
	}

	public void setBody(String body)
	{
		mBody = body;
	}

	public String getUrl()
	{
		return mUrl;
	}

	public void setUrl(String url)
	{
		mUrl = url;
	}

	public String getLink()
	{
		return mLink;
	}

	public void setLink(String link)
	{
		mLink = link;
	}

	public List<String> getShelves()
	{
		return mShelves;
	}

	public void setShelves(List<String> shelves)
	{
		mShelves = shelves;
	}

	public User getUser()
	{
		return mUser;
	}

	public void setUser(User user)
	{
		mUser = user;
	}

	public String getCommentsCount()
	{
		return mCommentsCount;
	}

	public void setCommentsCount(String commentsCount)
	{
		mCommentsCount = commentsCount;
	}

	public Comments getComments()
	{
		return mComments;
	}

	public void setComments(Comments comments)
	{
		mComments = comments;
	}	
}
