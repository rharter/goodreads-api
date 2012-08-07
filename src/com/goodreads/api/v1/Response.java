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

public class Response
{
	private Request mRequest;
	private User mUser;
	private Shelves mShelves;
	private Reviews mReviews;
	private Review mReview;
	private Friends mFriends;
	private Followers mFollowers;
	private Following mFollowing;
	private Search mSearch;
	private Book mBook;
	private Author mAuthor;
	private Comments mComments;
	private List<Event> mEvents = new ArrayList<Event>();
	private List<Update> mUpdates = new ArrayList<Update>();
	
	public void clear()
	{
		this.setRequest(new Request());
		this.getUser().clear();
		this.setShelves(new Shelves());
		this.setReviews(new Reviews());
		this.setFriends(new Friends());
		this.setFollowers(new Followers());
		this.setFollowing(new Following());
		this.getSearch().clear();
		this.getReview().clear();
		this.getUpdates().clear();
		this.getBook().clear();
		this.getAuthor().clear();
		this.getComments().clear();
		this.getEvents().clear();
	}
	
	public void copy()
	{
		Response responseCopy = new Response();
		
		responseCopy.setRequest(this.getRequest().copy());
		responseCopy.setUser(this.getUser().copy());
		responseCopy.setShelves(this.getShelves());
		responseCopy.setReviews(this.getReviews());
		responseCopy.setReview(this.getReview().copy());
		responseCopy.setFriends(this.getFriends());
		responseCopy.setFollowers(this.getFollowers());
		responseCopy.setFollowing(this.getFollowing());
		responseCopy.setSearch(getSearch().copy());
		responseCopy.setBook(getBook().copy());
		responseCopy.setAuthor(getAuthor().copy());
		responseCopy.setComments(getComments().copy());
		
		List<Update> updates = new ArrayList<Update>();
		for (int i = 0; i < this.getUpdates().size(); i++)
		{
			updates.add(this.getUpdates().get(i));
		}
		responseCopy.setUpdates(updates);

		List<Event> events = new ArrayList<Event>();
		for (int i = 0; i < this.getEvents().size(); i++)
		{
			events.add(this.getEvents().get(i));
		}
		responseCopy.setEvents(events);
}
	
	public Request getRequest()
	{
		return mRequest;
	}
	
	public void setRequest(Request request)
	{
		mRequest = request;
	}
	
	public User getUser()
	{
		return mUser;
	}
	
	public void setUser(User user)
	{
		mUser = user;
	}

	public Shelves getShelves()
	{
		return mShelves;
	}

	public void setShelves(Shelves shelves)
	{
		mShelves = shelves;
	}

	public Reviews getReviews()
	{
		return mReviews;
	}

	public void setReviews(Reviews reviews)
	{
		mReviews = reviews;
	}

	public Friends getFriends()
	{
		return mFriends;
	}

	public void setFriends(Friends friends)
	{
		mFriends = friends;
	}

	public Followers getFollowers()
	{
		return mFollowers;
	}

	public void setFollowers(Followers followers)
	{
		mFollowers = followers;
	}

	public Following getFollowing()
	{
		return mFollowing;
	}

	public void setFollowing(Following following)
	{
		mFollowing = following;
	}

	public Search getSearch()
	{
		return mSearch;
	}

	public void setSearch(Search search)
	{
		mSearch = search;
	}

	public List<Update> getUpdates()
	{
		return mUpdates;
	}
	
	public void setUpdates(List<Update> updates)
	{
		mUpdates = updates;
	}

	public Review getReview()
	{
		return mReview;
	}

	public void setReview(Review review)
	{
		mReview = review;
	}

	public Book getBook()
	{
		return mBook;
	}

	public void setBook(Book book)
	{
		mBook = book;
	}

	public void setAuthor(Author author)
	{
		mAuthor = author;
	}

	public Author getAuthor()
	{
		return mAuthor;
	}

	public void setComments(Comments comments)
	{
		mComments = comments;
	}

	public Comments getComments()
	{
		return mComments;
	}

	public void setEvents(List<Event> events)
	{
		mEvents = events;
	}

	public List<Event> getEvents()
	{
		return mEvents;
	}
}