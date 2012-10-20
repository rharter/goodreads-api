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
import android.sax.EndTextElementListener;

public class Search implements Serializable
{
	private static final long serialVersionUID = 0L;
	
	private String mQuery;
	private int mResultsStart;
	private int mResultsEnd;
	private int mTotalResults;
	private String mSource;
	private float mQueryTime;
	private List<Work> mResults = new ArrayList<Work>();
	
	public void clear()
	{
		this.setQuery("");
		this.setResultsStart(0);
		this.setResultsEnd(0);
		this.setTotalResults(0);
		this.setQueryTime(0);
		this.setSource("");
		this.mResults.clear();
	}
	
	public Search copy()
	{
		Search searchCopy = new Search();

		searchCopy.setQuery(this.getQuery());
		searchCopy.setResults(this.getResults());
		searchCopy.setResultsStart(this.getResultsStart());
		searchCopy.setResultsEnd(this.getResultsEnd());
		searchCopy.setTotalResults(this.getTotalResults());
		searchCopy.setQueryTime(this.getQueryTime());
		searchCopy.setSource(this.getSource());

		return searchCopy;
	}
	
	public static Search appendListener(final Element parentElement, int depth)
	{
		final Search search = new Search();
		
		Element searchElement = parentElement.getChild("search");

		appendCommonListeners(searchElement, search, depth);
		
		return search;
	}
	
	private static void appendCommonListeners(final Element searchElement, final Search search, int depth)
	{
		Element resultsElement = searchElement.getChild("results");
		search.setResults(Work.appendArrayListener(resultsElement, depth + 1));

		searchElement.getChild("query").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				search.setQuery(body);
			}
		});

		searchElement.getChild("results-start").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					search.setResultsStart(Integer.parseInt(body));
				}
			}
		});

		searchElement.getChild("results-end").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					search.setResultsEnd(Integer.parseInt(body));
				}
			}
		});

		searchElement.getChild("total-results").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					search.setTotalResults(Integer.parseInt(body));
				}
			}
		});

		searchElement.getChild("source").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				search.setSource(body);
			}
		});

		searchElement.getChild("query-time-seconds").setEndTextElementListener(new EndTextElementListener()
		{
			@Override
			public void end(String body)
			{
				if (body != null && body != "")
				{
					search.setQueryTime(Float.parseFloat(body));
				}
			}
		});
	}

	public String getQuery()
	{
		return mQuery;
	}

	public void setQuery(String query)
	{
		mQuery = query;
	}

	public int getResultsStart()
	{
		return mResultsStart;
	}

	public void setResultsStart(int resultsStart)
	{
		mResultsStart = resultsStart;
	}

	public int getResultsEnd()
	{
		return mResultsEnd;
	}

	public void setResultsEnd(int resultsEnd)
	{
		mResultsEnd = resultsEnd;
	}

	public int getTotalResults()
	{
		return mTotalResults;
	}

	public void setTotalResults(int totalResults)
	{
		mTotalResults = totalResults;
	}

	public String getSource()
	{
		return mSource;
	}

	public void setSource(String source)
	{
		mSource = source;
	}

	public float getQueryTime()
	{
		return mQueryTime;
	}

	public void setQueryTime(float queryTime)
	{
		mQueryTime = queryTime;
	}

	public List<Work> getResults()
	{
		return mResults;
	}

	public void setResults(List<Work> results)
	{
		mResults = results;
	}
}
