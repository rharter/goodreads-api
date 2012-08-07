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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.xml.sax.SAXException;

import android.location.Location;
import android.net.Uri;
import android.sax.RootElement;
import android.util.Xml;

public class GoodreadsService
{
	private static String sConsumerKey;
	private static String sConsumerSecret;
	
	private static CommonsHttpOAuthConsumer sConsumer;
	private static boolean sAuthenticated = false;
	
	public static void init(String apiKey, String apiSecret) {
		sConsumerKey = apiKey;
		sConsumerSecret = apiSecret;
		sConsumer = new CommonsHttpOAuthConsumer(apiKey, apiSecret);
	}

	public static Response parse(InputStream inputStream) throws IOException, SAXException
	{
		final Response response = new Response();
		
		RootElement root = new RootElement("GoodreadsResponse");
		
		response.setBook(Book.appendListener(root, 0));
		response.setRequest(Request.appendListener(root));
		response.setUser(User.appendListener(root, 0));
		response.setShelves(Shelves.appendListener(root, 0));
		response.setReviews(Reviews.appendListener(root, 0));
		response.setSearch(Search.appendListener(root, 0));
		response.setFollowers(Followers.appendListener(root, 0));
		response.setFriends(Friends.appendListener(root, 0));
		response.setFollowing(Following.appendListener(root, 0));
		response.setUpdates(Update.appendArrayListener(root, 0));
		response.setReview(Review.appendListener(root, 0));
		response.setAuthor(Author.appendListener(root, 0));
		response.setComments(Comments.appendListener(root, 0));
		response.setEvents(Event.appendArrayListener(root, 0));
		
		try
		{
			Xml.parse(inputStream, Xml.Encoding.UTF_8, root.getContentHandler());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return response;
	}
	
	public static User getAuthorizedUser() throws Exception
	{
		HttpGet getRequest = new HttpGet("http://www.goodreads.com/api/auth_user");
		sConsumer.sign(getRequest);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(getRequest);
		
		Response responseData = GoodreadsService.parse(response.getEntity().getContent());
		return responseData.getUser();
	}

	public static Reviews getBooksOnShelf(String shelfName, String userId) throws Exception
	{
		return getBooksOnShelf(shelfName, userId, 1);
	}
	
	public static Reviews getBooksOnShelf(String shelfName, String userId, int page) throws Exception
	{
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("http");
		builder.authority("www.goodreads.com");
		builder.path("review/list/" + userId + ".xml");
		builder.appendQueryParameter("key", sConsumerKey);
		builder.appendQueryParameter("shelf", shelfName);
		builder.appendQueryParameter("v", "2");
		builder.appendQueryParameter("sort", "date_updated");
		builder.appendQueryParameter("order", "d");
		builder.appendQueryParameter("page", Integer.toString(page));
		HttpGet getBooksOnShelfRequest = new HttpGet(builder.build().toString());
		if (isAuthenticated())
		{
			sConsumer.sign(getBooksOnShelfRequest);
		}
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;

		response = httpClient.execute(getBooksOnShelfRequest);
		Response responseData = GoodreadsService.parse(response.getEntity().getContent());
		return responseData.getReviews();		
//		InputStream is = response.getEntity().getContent();
//		BufferedReader br = new BufferedReader(new InputStreamReader(is));
//		StringBuilder sb = new StringBuilder();
//		while (true)
//		{
//			String nextByte = br.readLine();
//			if (nextByte == null)
//			{
//				break;
//			}
//			sb.append(nextByte);
//			Log.e("response", nextByte);
//		}
//		throw new Exception(sb.toString());
	}
	
	public static Review getReview(String reviewId) throws Exception
	{
		return getReview(reviewId, 1);
	}
	
	public static Review getReview(String reviewId, int page) throws Exception
	{
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("http");
		builder.authority("www.goodreads.com");
		builder.path("review/show/" + reviewId + ".xml");
		builder.appendQueryParameter("key", sConsumerKey);
		builder.appendQueryParameter("page", Integer.toString(page));
		HttpGet getReviewRequest = new HttpGet(builder.build().toString());
		if (isAuthenticated())
		{
			sConsumer.sign(getReviewRequest);
		}
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;

		response = httpClient.execute(getReviewRequest);
		
		Response responseData = GoodreadsService.parse(response.getEntity().getContent());
		
		return responseData.getReview();
	}
	
	public static List<UserShelf> getShelvesForUser(String userId) throws Exception
	{
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("http");
		builder.authority("www.goodreads.com");
		builder.path("shelf/list");
		builder.appendQueryParameter("format", "xml");
		builder.appendQueryParameter("user_id", userId);
		builder.appendQueryParameter("key", sConsumerKey);

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getShelvesRequest = new HttpGet(builder.build().toString());
		if (isAuthenticated())
		{
			sConsumer.sign(getShelvesRequest);
		}
		HttpResponse shelvesResponse = httpClient.execute(getShelvesRequest);
		
		Response shelvesResponseData = GoodreadsService.parse(shelvesResponse.getEntity().getContent());

		return shelvesResponseData.getShelves().getUserShelves();
	}
	
	public static List<Update> getFriendsUpdates() 
		throws 
			ClientProtocolException, 
			IOException, 
			IllegalStateException, 
			SAXException, 
			OAuthMessageSignerException, 
			OAuthExpectationFailedException, 
			OAuthCommunicationException
	{
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("http");
		builder.authority("www.goodreads.com");
		builder.path("updates/friends.xml");

		HttpGet getUpdatesRequest = new HttpGet(builder.build().toString());
		if (isAuthenticated())
		{
			sConsumer.sign(getUpdatesRequest);
		}
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(getUpdatesRequest);
		
		Response updatesResponse = GoodreadsService.parse(response.getEntity().getContent());
		return updatesResponse.getUpdates();
	}
	
	public static Followers getFollowers(String userId) throws Exception 
	{
		return getFollowers(userId, 1);
	}
	
	public static Followers getFollowers(String userId, int page) throws Exception
	{
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("http");
		builder.authority("www.goodreads.com");
		builder.path("user/" + userId + "/followers.xml");
		builder.appendQueryParameter("key", sConsumerKey);
		builder.appendQueryParameter("sort", "first_name");
		builder.appendQueryParameter("page", Integer.toString(page));

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getFriendsRequest = new HttpGet(builder.build().toString());
		if (isAuthenticated())
		{
			sConsumer.sign(getFriendsRequest);
		}
		HttpResponse followersResponse = httpClient.execute(getFriendsRequest);
			
		Response followersResponseData = GoodreadsService.parse(followersResponse.getEntity().getContent());
			
		return followersResponseData.getFollowers();
	}
	
	public static Comments getComments(String resourceId, String resourceType) throws Exception
	{
		return getComments(resourceId, resourceType, 1);
	}
	
	public static Comments getComments(String resourceId, String resourceType, int page) throws Exception
	{
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("http");
		builder.authority("www.goodreads.com");
		builder.path("comment/index.xml");
		builder.appendQueryParameter("key", sConsumerKey);
		builder.appendQueryParameter("id", resourceId);
		builder.appendQueryParameter("type", resourceType);
		builder.appendQueryParameter("page", Integer.toString(page));
	
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(builder.build().toString());
		if (isAuthenticated())
		{
			sConsumer.sign(getRequest);
		}
		
		HttpResponse response;
	
		response = httpClient.execute(getRequest);
			
		Response responseData = GoodreadsService.parse(response.getEntity().getContent());
			
		return responseData.getComments();
	}

	public static void addBookToShelf(String bookId, String shelfName) throws Exception
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://www.goodreads.com/shelf/add_to_shelf.xml");
		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("book_id", bookId));
		parameters.add(new BasicNameValuePair("name", shelfName));
		post.setEntity(new UrlEncodedFormEntity(parameters));
		sConsumer.sign(post);
		
		HttpResponse response = httpClient.execute(post);
		if (response.getStatusLine().getStatusCode() != 201)
		{
			throw new Exception(response.getStatusLine().toString());
		}
	}
	
	public static void sendFriendRequest(String userId) throws Exception
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://www.goodreads.com/friend/add_as_friend.xml");
		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("id", userId));
		post.setEntity(new UrlEncodedFormEntity(parameters));
		sConsumer.sign(post);
		
		HttpResponse response = httpClient.execute(post);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode < 200 || statusCode > 299)
		{
			throw new Exception(response.getStatusLine().toString());
		}
	}

	public static void followUser(String userId) throws Exception
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://www.goodreads.com/user/:user_id/followers?format=xml");
		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("id", userId));
		post.setEntity(new UrlEncodedFormEntity(parameters));
		sConsumer.sign(post);
		
		HttpResponse response = httpClient.execute(post);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode < 200 || statusCode > 299)
		{
			throw new Exception(response.getStatusLine().toString());
		}
	}

	public static void updateReview(
			String reviewId, 
			String review,
			String dateRead,
			List<String> shelves,
			int rating) throws Exception
	{
		if (shelves.size() == 0)
		{
			throw new Exception("Select at least one shelf.");
		}
		if (rating < 1 || rating > 5)
		{
			throw new Exception("Review rating must be 1-5 stars.");
		}
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://www.goodreads.com/review/" + reviewId + ".xml");
		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		
		StringBuilder shelvesString = new StringBuilder();
		for (int i = 0; i < shelves.size(); i++)
		{
			if (i > 0) {
				shelvesString.append("|");
			}
			shelvesString.append(shelves.get(i));
		}
		parameters.add(new BasicNameValuePair("shelf", shelvesString.toString()));
		parameters.add(new BasicNameValuePair("review[review]", review));
		parameters.add(new BasicNameValuePair("review[read_at]", dateRead));
		parameters.add(new BasicNameValuePair("review[rating]", Integer.toString(rating)));
		post.setEntity(new UrlEncodedFormEntity(parameters));
		sConsumer.sign(post);
		
		HttpResponse response = httpClient.execute(post);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode < 200 || statusCode > 299)
		{
			throw new Exception(response.getStatusLine().toString());
		}
	}

	public static void postReview(
			String bookId, 
			String review,
			String dateRead,
			List<String> shelves,
			int rating) throws Exception
	{
		if (shelves.size() == 0)
		{
			throw new Exception("Select at least one shelf.");
		}
		if (rating < 1 || rating > 5)
		{
			throw new Exception("Review rating must be 1-5 stars.");
		}
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://www.goodreads.com/review.xml");
		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("shelf", shelves.get(0)));
		parameters.add(new BasicNameValuePair("review[review]", review));
		parameters.add(new BasicNameValuePair("review[read_at]", dateRead));
		parameters.add(new BasicNameValuePair("book_id", bookId));
		parameters.add(new BasicNameValuePair("review[rating]", Integer.toString(rating)));
		post.setEntity(new UrlEncodedFormEntity(parameters));
		sConsumer.sign(post);
		
		HttpResponse response = httpClient.execute(post);
		if (response.getStatusLine().getStatusCode() != 201)
		{
			throw new Exception(response.getStatusLine().toString());
		}
	}
	
	public static void postStatusUpdate(String comment)	throws Exception
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://www.goodreads.com/user_status.xml");
		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("user_status[body]", comment));
		post.setEntity(new UrlEncodedFormEntity(parameters));
		sConsumer.sign(post);
		
		HttpResponse response = httpClient.execute(post);
		if (response.getStatusLine().getStatusCode() != 201)
		{
			throw new Exception(response.getStatusLine().toString());
		}
	}
	
	public static void postStatusUpdate(String book, String page, String comment) 
		throws 
			Exception
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://www.goodreads.com/user_status.xml");
		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("user_status[book_id]", book));
		parameters.add(new BasicNameValuePair("user_status[page]", page));
		parameters.add(new BasicNameValuePair("user_status[body]", comment));
		post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
		sConsumer.sign(post);
		
		HttpResponse response = httpClient.execute(post);
		if (response.getStatusLine().getStatusCode() != 201)
		{
			throw new Exception(response.getStatusLine().toString());
		}
	}
	
	public static void postComment(String resourceId, String resourceType, String comment) throws Exception
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://www.goodreads.com/comment.xml");
		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("comment[body]", comment));
		parameters.add(new BasicNameValuePair("id", resourceId));
		parameters.add(new BasicNameValuePair("type", resourceType));
		post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
		sConsumer.sign(post);
		
		HttpResponse response = httpClient.execute(post);
		if (response.getStatusLine().getStatusCode() != 201)
		{
			throw new Exception(response.getStatusLine().toString());
		}
	}

	public static User getUserDetails(String userId) throws Exception
	{
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("http");
		builder.authority("www.goodreads.com");
		builder.path("user/show/" + userId + ".xml");
		builder.appendQueryParameter("key", sConsumerKey);

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(builder.build().toString());
		if (isAuthenticated())
		{
			sConsumer.sign(getRequest);
		}
		HttpResponse response;

		response = httpClient.execute(getRequest);
			
		Response responseData = GoodreadsService.parse(response.getEntity().getContent());
			
		return responseData.getUser();
	}
	
	public static Following getFollowing(String userId) throws Exception
	{
		return getFollowing(userId, 1);
	}
	
	public static Following getFollowing(String userId, int page) throws Exception
	{
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("http");
		builder.authority("www.goodreads.com");
		builder.path("user/" + userId + "/following");
		builder.appendQueryParameter("format", "xml");
		builder.appendQueryParameter("key", sConsumerKey);
		builder.appendQueryParameter("sort", "first_name");
		builder.appendQueryParameter("page", Integer.toString(page));

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getFriendsRequest = new HttpGet(builder.build().toString());
		if (isAuthenticated())
		{
			sConsumer.sign(getFriendsRequest);
		}
		HttpResponse followingResponse;

		followingResponse = httpClient.execute(getFriendsRequest);
		
		Response followingResponseData = GoodreadsService.parse(followingResponse.getEntity().getContent());

		return followingResponseData.getFollowing();
	}
	
	public static Friends getFriends(String userId) throws Exception
	{
		return getFriends(userId, 1);
	}
	
	public static Friends getFriends(String userId, int page) throws Exception
	{
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("http");
		builder.authority("www.goodreads.com");
		builder.path("friend/user/" + userId);
		builder.appendQueryParameter("format", "xml");
		builder.appendQueryParameter("key", sConsumerKey);
		builder.appendQueryParameter("sort", "first_name");
		builder.appendQueryParameter("page", Integer.toString(page));

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getFriendsRequest = new HttpGet(builder.build().toString());
		if (isAuthenticated())
		{
			sConsumer.sign(getFriendsRequest);
		}
		HttpResponse friendsResponse;
		
		friendsResponse = httpClient.execute(getFriendsRequest);
		
		Response friendsResponseData = GoodreadsService.parse(friendsResponse.getEntity().getContent());
		
		return friendsResponseData.getFriends();
	}
	
	public static Search search(String searchString) throws Exception
	{
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("http");
		builder.authority("www.goodreads.com");
		builder.path("search/search");
		builder.appendQueryParameter("format", "xml");
		builder.appendQueryParameter("key", sConsumerKey);
		builder.appendQueryParameter("q", searchString);

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getSearchResponse = new HttpGet(builder.build().toString());
		HttpResponse searchResponse = httpClient.execute(getSearchResponse);
		
		Response searchResponseData = GoodreadsService.parse(searchResponse.getEntity().getContent());
		
		return searchResponseData.getSearch();
	}
	
	public static Book getReviewsForBook(String bookId) throws Exception
	{
		return getReviewsForBook(bookId, 1);
	}

	public static Book getReviewsForBook(String bookId, int page) throws Exception
	{
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("http");
		builder.authority("www.goodreads.com");
		builder.path("book/show");
		builder.appendQueryParameter("key", sConsumerKey);
		builder.appendQueryParameter("page", Integer.toString(page));
		builder.appendQueryParameter("id", bookId);
		builder.appendQueryParameter("format", "xml");

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(builder.build().toString());
		if (isAuthenticated())
		{
			sConsumer.sign(getRequest);
		}
		HttpResponse response = httpClient.execute(getRequest);
		
		Response responseData = GoodreadsService.parse(response.getEntity().getContent());
		
		return responseData.getBook();
	}
	
	public static Book getReviewsForBook(String bookId, int page, int rating) throws Exception
	{
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("http");
		builder.authority("www.goodreads.com");
		builder.path("book/show");
		builder.appendQueryParameter("key", sConsumerKey);
		builder.appendQueryParameter("page", Integer.toString(page));
		builder.appendQueryParameter("rating", Integer.toString(rating));
		builder.appendQueryParameter("id", bookId);

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(builder.build().toString());
		if (isAuthenticated())
		{
			sConsumer.sign(getRequest);
		}
		HttpResponse response = httpClient.execute(getRequest);
		
		Response responseData = GoodreadsService.parse(response.getEntity().getContent());
		
		return responseData.getBook();
	}
	
	public static Author getBooksByAuthor(String authorId) throws Exception
	{
		return getBooksByAuthor(authorId, 1);
	}

	public static Author getBooksByAuthor(String authorId, int page) throws Exception
	{
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("http");
		builder.authority("www.goodreads.com");
		builder.path("author/list/" + authorId + ".xml");
		builder.appendQueryParameter("key", sConsumerKey);
		builder.appendQueryParameter("page", Integer.toString(page));

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getResponse = new HttpGet(builder.build().toString());
		HttpResponse response = httpClient.execute(getResponse);
		
		Response responseData = GoodreadsService.parse(response.getEntity().getContent());
		
		return responseData.getAuthor();
	}

	public static List<Event> getNearbyEvents(Location location) throws Exception
	{
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("http");
		builder.authority("www.goodreads.com");
		builder.path("event/index.xml");
		builder.appendQueryParameter("key", sConsumerKey);
		builder.appendQueryParameter("lat", Double.toString(location.getLatitude()));
		builder.appendQueryParameter("lng", Double.toString(location.getLongitude()));

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(builder.build().toString());
		if (isAuthenticated())
		{
			sConsumer.sign(getRequest);
		}
		HttpResponse response = httpClient.execute(getRequest);
		
		Response responseData = GoodreadsService.parse(response.getEntity().getContent());
		
		return responseData.getEvents();
	}

	public static List<Event> getEvents() throws Exception
	{
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("http");
		builder.authority("www.goodreads.com");
		builder.path("event/index.xml");
		builder.appendQueryParameter("key", sConsumerKey);

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(builder.build().toString());
		if (isAuthenticated())
		{
			sConsumer.sign(getRequest);
		}
		HttpResponse response = httpClient.execute(getRequest);
		
		Response responseData = GoodreadsService.parse(response.getEntity().getContent());
		
		return responseData.getEvents();
	}

	private static void setAuthenticated(boolean authenticated)
	{
		GoodreadsService.sAuthenticated = authenticated;
	}

	public static boolean isAuthenticated()
	{
		return sAuthenticated;
	}
	
	public static void setTokenWithSecret(String token, String tokenSecret)
	{
		sConsumer.setTokenWithSecret(token, tokenSecret);
		setAuthenticated(true);
	}
	
	public static void clearAuthentication()
	{
		setAuthenticated(false);
	}
}
