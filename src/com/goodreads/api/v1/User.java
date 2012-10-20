package com.goodreads.api.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import android.net.Uri;
import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.StartElementListener;

public class User implements Serializable
{
	private static final long serialVersionUID = 0L;
	
	private String mId;
	private String mName;
	private Uri mLink;
	private String mImageUrl;
	private String mSmallImageUrl;
	private int mFriendsCount;
	private int mReviewsCount;
	private String mUsername;
	private String mAbout;
	private String mAge;
	private String mGender;
	private String mLocation;
	private String mWebsite;
	private String mJoined;
	private String mLastActive;
	private String mInterests;
	private String mFavoriteBooks;
	private List<Author> mFavoriteAuthors = new ArrayList<Author>();
	private String mUpdatesRssUrl;
	private String mReviewsRssUrl;
	private List<UserShelf> mShelves;
	private List<Update> mUpdates;
	
	public static User appendListener(final Element parentElement, int depth) {
		final User user = new User();
		
		Element userElement = parentElement.getChild("user");
		
		appendCommonListeners(userElement, user, depth);
		
		return user;
	}
	
	public static List<User> appendArrayListener(final Element parentElement, int depth) {
		final List<User> users = new ArrayList<User>();
		final User user = new User();
		
		Element userElement = parentElement.getChild("user");
		
		userElement.setEndElementListener(new EndElementListener() {
			@Override
			public void end() {
				users.add(user.copy());
				user.clear();
			}
		});
		
		appendCommonListeners(userElement, user, depth);
		
		return users;
	}
	
	private static void appendCommonListeners(final Element userElement, final User user, int depth) {
		userElement.setStartElementListener(new StartElementListener() {
			@Override
			public void start(Attributes attributes) {
				String idAsAttribute = attributes.getValue("id");
				if (idAsAttribute != null) {
					user.setId(idAsAttribute);
				}
			}
		});
		
		userElement.getChild("id").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setId(body);
			}
		});
		
		userElement.getChild("name").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setName(body);
			}
		});
		
		userElement.getChild("link").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setLink(Uri.parse(body));
			}
		});
		
		userElement.getChild("image_url").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setImageUrl(body);
			}
		});
		
		userElement.getChild("small_image_url").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setSmallImageUrl(body);
			}
		});
		
		userElement.getChild("friends_count").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setFriendsCount(Integer.parseInt(body));
			}
		});
		
		userElement.getChild("reviews_count").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setReviewsCount(Integer.parseInt(body));
			}
		});
		
		userElement.getChild("user_name").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setUsername(body);
			}
		});
		
		userElement.getChild("about").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setAbout(body);
			}
		});
		
		userElement.getChild("age").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setAge(body);
			}
		});
		
		userElement.getChild("gender").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setGender(body);
			}
		});
		
		userElement.getChild("location").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setLocation(body);
			}
		});
		
		userElement.getChild("website").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setWebsite(body);
			}
		});
		
		userElement.getChild("joined").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setJoined(body);
			}
		});
		
		userElement.getChild("last_active").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setLastActive(body);
			}
		});
		
		userElement.getChild("interests").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setInterests(body);
			}
		});
		
		userElement.getChild("favorite_books").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setFavoriteBooks(body);
			}
		});
		
		userElement.getChild("updates_rss_url").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setUpdatesRssUrl(body);
			}
		});
		
		userElement.getChild("reviews_rss_url").setEndTextElementListener(new EndTextElementListener() {
			@Override
			public void end(String body) {
				user.setReviewsRssUrl(body);
			}
		});
		
		Element userShelves = userElement.getChild("user_shelves");
		user.setShelves(UserShelf.appendArrayListener(userShelves, depth + 1));
		
		user.setUpdates(Update.appendArrayListener(userElement, depth + 1));
		
		Element favoriteAuthorsElement = userElement.getChild("favorite_authors");
		user.setFavoriteAuthors(Author.appendArrayListener(favoriteAuthorsElement, depth + 1));
	}
	
	public String getId() {
		return mId;
	}
	
	public void setId(String id) {
		mId = id;
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		mName = name;
	}
	
	public Uri getLink() {
		return mLink;
	}
	
	public void setLink(Uri link) {
		mLink = link;
	}
	
	public String getImageUrl() {
		return mImageUrl;
	}
	
	public void setImageUrl(String url) {
		mImageUrl = url;
	}
	
	public String getSmallImageUrl() {
		return mSmallImageUrl;
	}
	
	public void setSmallImageUrl(String url) {
		mSmallImageUrl = url;
	}
	
	public int getFriendsCount() {
		return mFriendsCount;
	}
	
	public void setFriendsCount(int count) {
		mFriendsCount = count;
	}
	
	public int getReviewsCount() {
		return mReviewsCount;
	}
	
	public void setReviewsCount(int count) {
		mReviewsCount = count;
	}
	
	public String getUsername() {
		return mUsername;
	}
	
	public void setUsername(String username) {
		mUsername = username;
	}
	
	public String getAbout() {
		return mAbout;
	}
	
	public void setAbout(String about) {
		mAbout = about;
	}
	
	public String getAge() {
		return mAge;
	}
	
	public void setAge(String age) {
		mAge = age;
	}
	
	public String getGender() {
		return mGender;
	}
	
	public void setGender(String gender) {
		mGender = gender;
	}
	
	public String getLocation() {
		return mLocation;
	}
	
	public void setLocation(String location) {
		mLocation = location;
	}
	
	public String getWebsite() {
		return mWebsite;
	}
	
	public void setWebsite(String website) {
		mWebsite = website;
	}
	
	public String getJoined() {
		return mJoined;
	}
	
	public void setJoined(String joined) {
		mJoined = joined;
	}
	
	public String getLastActive() {
		return mLastActive;
	}
	
	public void setLastActive(String lastActive) {
		mLastActive = lastActive;
	}
	
	public String getInterests() {
		return mInterests;
	}
	
	public void setInterests(String interests) {
		mInterests = interests;
	}
	
	public String getFavoriteBooks() {
		return mFavoriteBooks;
	}
	
	public void setFavoriteBooks(String books) {
		mFavoriteBooks = books;
	}
	
	public List<Author> getFavoriteAuthors() {
		return mFavoriteAuthors;
	}
	
	public void setFavoriteAuthors(List<Author> favoriteAuthors) {
		mFavoriteAuthors = favoriteAuthors;
	}
	
	public String getUpdatesRssUrl() {
		return mUpdatesRssUrl;
	}
	
	public void setUpdatesRssUrl(String url) {
		mUpdatesRssUrl = url;
	}
	
	public String getReviewsRssUrl() {
		return mReviewsRssUrl;
	}
	
	public void setReviewsRssUrl(String url) {
		mReviewsRssUrl = url;
	}
	
	public List<UserShelf> getShelves() {
		return mShelves;
	}
	
	public void setShelves(List<UserShelf> shelves) {
		mShelves = shelves;
	}
	
	public List<Update> getUpdates() {
		return mUpdates;
	}
	
	public void setUpdates(List<Update> updates) {
		mUpdates = updates;
	}
	
	/**
	 * Creates a deep copy of this object.
	 */
	public User copy() {
		User userCopy = new User();
		
		userCopy.setFriendsCount(this.getFriendsCount());
		userCopy.setId(this.getId());
		userCopy.setImageUrl(this.getImageUrl());
		userCopy.setLink(this.getLink());
		userCopy.setName(this.getName());
		userCopy.setReviewsCount(this.getReviewsCount());
		userCopy.setSmallImageUrl(this.getSmallImageUrl());
		userCopy.setUsername(this.getUsername());
		userCopy.setAbout(this.getAbout());
		userCopy.setAge(this.getAge());
		userCopy.setGender(this.getGender());
		userCopy.setLocation(this.getLocation());
		userCopy.setWebsite(this.getWebsite());
		userCopy.setJoined(this.getJoined());
		userCopy.setLastActive(this.getLastActive());
		userCopy.setInterests(this.getInterests());
		userCopy.setFavoriteBooks(this.getFavoriteBooks());
		userCopy.setUpdatesRssUrl(this.getUpdatesRssUrl());
		userCopy.setReviewsRssUrl(this.getReviewsRssUrl());
		
		List<Author> favoriteAuthorsCopy = new ArrayList<Author>();
		for (Author a : this.getFavoriteAuthors()) {
			favoriteAuthorsCopy.add(a);
		}
		userCopy.setFavoriteAuthors(favoriteAuthorsCopy);
		
		List<UserShelf> userShelvesCopy = new ArrayList<UserShelf>();
		for (UserShelf s : this.getShelves()) {
			userShelvesCopy.add(s);
		}
		userCopy.setShelves(userShelvesCopy);
		
		List<Update> updatesCopy = new ArrayList<Update>();
		for (Update u : this.getUpdates()) {
			updatesCopy.add(u);
		}
		userCopy.setUpdates(updatesCopy);
		
		return userCopy;
	}
	
	/**
	 * Sets all fields to default values.
	 */
	public void clear() {
		this.setFriendsCount(0);
		this.setId("");
		this.setImageUrl("");
		this.setLink(null);
		this.setName("");
		this.setReviewsCount(0);
		this.setSmallImageUrl("");
		this.setUsername("");
		this.setAbout("");
		this.setAge("");
		this.setGender("");
		this.setLocation("");
		this.setWebsite("");
		this.setJoined("");
		this.setLastActive("");
		this.setInterests("");
		this.setFavoriteBooks("");
		this.setUpdatesRssUrl("");
		this.setReviewsRssUrl("");
		
		mFavoriteAuthors.clear();
		mShelves.clear();
		mUpdates.clear();
	}
}
