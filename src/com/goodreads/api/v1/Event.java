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

public class Event
{
	private String mAccess;
	private String mAddress;
	private boolean mCanInvite;
	private String mCity;
	private String mCountryCode;
	private String mCreatedAt;
	private String mEventAt;
	private int mEventAttendingCount;
	private int mEventResponseCount;
	private String mEventType;
	private String mId;
	private String mPostalCode;
	private int mPublicFlag;
	private String mReminderAt;
	private int mResourceId;
	private String mResourceType;
	private String mSource;
	private String mSourceUrl;
	private String mStateCode;
	private String mTitle;
	private String mUpdatedAt;
	private String mUserId;
	private String mVenue;
	private String mImageUrl;
	private String mLink;
	private String mDescription;
	
	public void clear()
	{
		this.setAccess("");
		this.setAddress("");
		this.setCanInvite(false);
		this.setCity("");
		this.setCountryCode("");
		this.setCreatedAt("");
		this.setEventAt("");
		this.setEventAttendingCount(0);
		this.setEventResponseCount(0);
		this.setEventType("");
		this.setId("");
		this.setPostalCode("");
		this.setPublicFlag(0);
		this.setReminderAt("");
		this.setResourceId(0);
		this.setResourceType("");
		this.setSource("");
		this.setSourceUrl("");
		this.setStateCode("");
		this.setTitle("");
		this.setUpdatedAt("");
		this.setUserId("");
		this.setVenue("");
		this.setImageUrl("");
		this.setLink("");
		this.setDescription("");
	}
	
	public Event copy()
	{
		Event eventCopy = new Event();
		
		eventCopy.setAccess(this.getAccess());
		eventCopy.setAddress(this.getAddress());
		eventCopy.setCanInvite(this.getCanInvite());
		eventCopy.setCity(this.getCity());
		eventCopy.setCountryCode(this.getCountryCode());
		eventCopy.setCreatedAt(this.getCreatedAt());
		eventCopy.setEventAt(this.getEventAt());
		eventCopy.setEventAttendingCount(this.getEventAttendingCount());
		eventCopy.setEventResponseCount(this.getEventResponseCount());
		eventCopy.setEventType(this.getEventType());
		eventCopy.setId(this.getId());
		eventCopy.setPostalCode(this.getPostalCode());
		eventCopy.setPublicFlag(this.getPublicFlag());
		eventCopy.setReminderAt(this.getReminderAt());
		eventCopy.setResourceId(this.getResourceId());
		eventCopy.setResourceType(this.getResourceType());
		eventCopy.setSource(this.getSource());
		eventCopy.setSourceUrl(this.getSourceUrl());
		eventCopy.setStateCode(this.getStateCode());
		eventCopy.setTitle(this.getTitle());
		eventCopy.setUpdatedAt(this.getUpdatedAt());
		eventCopy.setUserId(this.getUserId());
		eventCopy.setVenue(this.getVenue());
		eventCopy.setImageUrl(this.getImageUrl());
		eventCopy.setLink(this.getLink());
		eventCopy.setDescription(this.getDescription());
		
		return eventCopy;
	}
	
	public static Event appendListener(Element parentElement, int depth)
	{
		final Event event = new Event();
		final Element eventElement = parentElement.getChild("event");
		
		appendCommonListeners(eventElement, event, depth);
				
		return event;
	}
	
	public static List<Event> appendArrayListener(Element parentElement, int depth)
	{
		final List<Event> events = new ArrayList<Event>();
		final Element eventsElement = parentElement.getChild("events");
		
		final Event event = new Event();
		final Element eventElement = eventsElement.getChild("event");
		
		eventElement.setEndElementListener(new EndElementListener()
		{
			@Override
			public void end()
			{
				events.add(event.copy());
				event.clear();
			}
		});
		
		appendCommonListeners(eventElement, event, depth);
		
		return events;
	}
	
	private static void appendCommonListeners(final Element eventElement, final Event event, int depth)
	{
		eventElement.getChild("access").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setAccess(body);
			}
		});

		eventElement.getChild("address").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setAddress(body);
			}
		});

		eventElement.getChild("can_invite_flag").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				boolean parsedBoolean = false;
				try
				{
					parsedBoolean = Boolean.parseBoolean(body);
				}
				catch (Exception e){}
				event.setCanInvite(parsedBoolean);
			}
		});

		eventElement.getChild("city").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setCity(body);
			}
		});

		eventElement.getChild("country_code").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setCountryCode(body);
			}
		});

		eventElement.getChild("created_at").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setCreatedAt(body);
			}
		});

		eventElement.getChild("event_at").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setEventAt(body);
			}
		});

		eventElement.getChild("event_attending_count").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				int count = 0;
				try
				{
					count = Integer.parseInt(body);
				}
				catch (Exception e) {}
				
				event.setEventAttendingCount(count);
			}
		});

		eventElement.getChild("event_responses_count").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				int count = 0;
				try
				{
					count = Integer.parseInt(body);
				}
				catch (Exception e) {}
				
				event.setEventResponseCount(count);
			}
		});

		eventElement.getChild("event_type").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setEventType(body);
			}
		});

		eventElement.getChild("id").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setId(body);
			}
		});

		eventElement.getChild("postal_code").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setPostalCode(body);
			}
		});

		eventElement.getChild("public_flag").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				int count = 0;
				try
				{
					count = Integer.parseInt(body);
				}
				catch (Exception e) {}
				
				event.setPublicFlag(count);
			}
		});

		eventElement.getChild("reminder_at").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setReminderAt(body);
			}
		});

		eventElement.getChild("resource_id").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				int count = 0;
				try
				{
					count = Integer.parseInt(body);
				}
				catch (Exception e) {}
				
				event.setResourceId(count);
			}
		});

		eventElement.getChild("resource_type").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setResourceType(body);
			}
		});

		eventElement.getChild("source").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setSource(body);
			}
		});

		eventElement.getChild("source_url").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setSourceUrl(body);
			}
		});

		eventElement.getChild("state_code").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setStateCode(body);
			}
		});

		eventElement.getChild("title").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setTitle(body);
			}
		});

		eventElement.getChild("updated_at").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setUpdatedAt(body);
			}
		});

		eventElement.getChild("user_id").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setUserId(body);
			}
		});

		eventElement.getChild("venue").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setVenue(body);
			}
		});

		eventElement.getChild("image_url").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setImageUrl(body);
			}
		});

		eventElement.getChild("link").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setLink(body);
			}
		});

		eventElement.getChild("description").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				event.setDescription(body);
			}
		});
	}

	public String getAccess()
	{
		return mAccess;
	}

	public void setAccess(String access)
	{
		mAccess = access;
	}

	public String getAddress()
	{
		return mAddress;
	}

	public void setAddress(String address)
	{
		mAddress = address;
	}

	public boolean getCanInvite()
	{
		return mCanInvite;
	}

	public void setCanInvite(boolean canInvite)
	{
		mCanInvite = canInvite;
	}

	public String getCity()
	{
		return mCity;
	}

	public void setCity(String city)
	{
		mCity = city;
	}

	public String getCountryCode()
	{
		return mCountryCode;
	}

	public void setCountryCode(String countryCode)
	{
		mCountryCode = countryCode;
	}

	public String getCreatedAt()
	{
		return mCreatedAt;
	}

	public void setCreatedAt(String createdAt)
	{
		mCreatedAt = createdAt;
	}

	public String getEventAt()
	{
		return mEventAt;
	}

	public void setEventAt(String eventAt)
	{
		mEventAt = eventAt;
	}

	public int getEventAttendingCount()
	{
		return mEventAttendingCount;
	}

	public void setEventAttendingCount(int eventAttendingCount)
	{
		mEventAttendingCount = eventAttendingCount;
	}

	public int getEventResponseCount()
	{
		return mEventResponseCount;
	}

	public void setEventResponseCount(int eventResponseCount)
	{
		mEventResponseCount = eventResponseCount;
	}

	public String getId()
	{
		return mId;
	}

	public void setId(String id)
	{
		mId = id;
	}

	public String getPostalCode()
	{
		return mPostalCode;
	}

	public void setPostalCode(String postalCode)
	{
		mPostalCode = postalCode;
	}

	public int getPublicFlag()
	{
		return mPublicFlag;
	}

	public void setPublicFlag(int publicFlag)
	{
		mPublicFlag = publicFlag;
	}

	public String getReminderAt()
	{
		return mReminderAt;
	}

	public void setReminderAt(String reminderAt)
	{
		mReminderAt = reminderAt;
	}

	public int getResourceId()
	{
		return mResourceId;
	}

	public void setResourceId(int resourceId)
	{
		mResourceId = resourceId;
	}

	public String getResourceType()
	{
		return mResourceType;
	}

	public void setResourceType(String resourceType)
	{
		mResourceType = resourceType;
	}

	public String getSource()
	{
		return mSource;
	}

	public void setSource(String source)
	{
		mSource = source;
	}

	public String getSourceUrl()
	{
		return mSourceUrl;
	}

	public void setSourceUrl(String sourceUrl)
	{
		mSourceUrl = sourceUrl;
	}

	public String getStateCode()
	{
		return mStateCode;
	}

	public void setStateCode(String stateCode)
	{
		mStateCode = stateCode;
	}

	public String getTitle()
	{
		return mTitle;
	}

	public void setTitle(String title)
	{
		mTitle = title;
	}

	public String getUpdatedAt()
	{
		return mUpdatedAt;
	}

	public void setUpdatedAt(String updatedAt)
	{
		mUpdatedAt = updatedAt;
	}

	public String getUserId()
	{
		return mUserId;
	}

	public void setUserId(String userId)
	{
		mUserId = userId;
	}

	public String getVenue()
	{
		return mVenue;
	}

	public void setVenue(String venue)
	{
		mVenue = venue;
	}

	public String getImageUrl()
	{
		return mImageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		mImageUrl = imageUrl;
	}

	public String getLink()
	{
		return mLink;
	}

	public void setLink(String link)
	{
		mLink = link;
	}

	public String getDescription()
	{
		return mDescription;
	}

	public void setDescription(String description)
	{
		mDescription = description;
	}

	public String getEventType()
	{
		return mEventType;
	}

	public void setEventType(String eventType)
	{
		mEventType = eventType;
	}
}
