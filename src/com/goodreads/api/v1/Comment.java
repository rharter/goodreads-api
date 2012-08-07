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

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;

public class Comment
{
	private String mId;
	private String mBody;
	private User mUser;
	private String mCreatedAt;
	private String mUpdatedAt;
	
	public void clear()
	{
		this.setId("");
		this.setBody("");
		this.setUpdatedAt("");
		this.setCreatedAt("");
		mUser.clear();
	}
	
	public Comment copy()
	{
		Comment copyComment = new Comment();
		
		copyComment.setId(this.getId());
		copyComment.setBody(this.getBody());
		copyComment.setCreatedAt(this.getCreatedAt());
		copyComment.setUpdatedAt(this.getUpdatedAt());
		copyComment.setUser(mUser.copy());

		return copyComment;
	}
	
	public static Comment appendListener(Element parentElement, int depth)
	{
		final Comment comment = new Comment();
		final Element commentElement = parentElement.getChild("comment");
		
		appendCommonListeners(commentElement, comment, depth);
		
		return comment;
	}
	
	public static final List<Comment> appendArrayListener(Element parentElement, int depth)
	{
		final List<Comment> comments = new ArrayList<Comment>();
		final Comment comment = new Comment();
		final Element commentElement = parentElement.getChild("comment");
		
		appendCommonListeners(commentElement, comment, depth);
		
		commentElement.setEndElementListener(new EndElementListener()
		{
			@Override
			public void end()
			{
				comments.add(comment.copy());
				comment.clear();
			}
		});
		
		return comments;
	}
	
	private static void appendCommonListeners(final Element commentElement, final Comment comment, int depth)
	{
		commentElement.getChild("id").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				comment.setId(body);
			}
		});

		commentElement.getChild("created_at").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				comment.setCreatedAt(body);
			}
		});

		commentElement.getChild("udpated_at").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				comment.setUpdatedAt(body);
			}
		});

		commentElement.getChild("body").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				comment.setBody(body);
			}
		});
		
		comment.setUser(User.appendListener(commentElement, depth + 1));
	}
	
	public String getId()
	{
		return mId;
	}
	public void setId(String id)
	{
		mId = id;
	}
	public String getBody()
	{
		return mBody;
	}
	public void setBody(String body)
	{
		mBody = body;
	}
	public User getUser()
	{
		return mUser;
	}
	public void setUser(User user)
	{
		mUser = user;
	}
	public String getCreatedAt()
	{
		return mCreatedAt;
	}
	public void setCreatedAt(String createdAt)
	{
		mCreatedAt = createdAt;
	}
	public String getUpdatedAt()
	{
		return mUpdatedAt;
	}
	public void setUpdatedAt(String updatedAt)
	{
		mUpdatedAt = updatedAt;
	}
}
