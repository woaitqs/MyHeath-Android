/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.actionbarsherlock.widget;

import java.util.List;

import android.content.Context;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.actionbarsherlock.R;

/**
 * Provides the contents for the suggestion drop-down list.in {@link SearchDialog}.
 *
 * @hide
 */
class SuggestionsAdapter extends ArrayAdapter<Spanned> implements OnClickListener {

    private static final boolean DBG = false;
    private static final String LOG_TAG = "SuggestionsAdapter";

    static final int REFINE_NONE = 0;
    static final int REFINE_BY_ENTRY = 1;
    static final int REFINE_ALL = 2;

    private SearchViewSherlock mSearchView;
    private int mQueryRefinement = REFINE_BY_ENTRY;
    
    private LayoutInflater mInflater;

    public SuggestionsAdapter(Context context, SearchViewSherlock searchView, List<Spanned> suggestions) {
        super(context, android.R.layout.simple_list_item_1, suggestions);
        mSearchView = searchView;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            view = mInflater.inflate(R.layout.search_dropdown_item_icons_2line, null);
        } else {
            view = convertView;
        }
        
        TextView textView = (TextView) view.findViewById(R.id.text1);
        textView.setText(getItem(position));
        view.setTag(getItem(position));
        
        return view;
    }



    /**
     * Enables query refinement for all suggestions. This means that an additional icon
     * will be shown for each entry. When clicked, the suggested text on that line will be
     * copied to the query text field.
     * <p>
     *
     * @param refine which queries to refine. Possible values are {@link #REFINE_NONE},
     * {@link #REFINE_BY_ENTRY}, and {@link #REFINE_ALL}.
     */
    public void setQueryRefinement(int refineWhat) {
        mQueryRefinement = refineWhat;
    }

    /**
     * Returns the current query refinement preference.
     * @return value of query refinement preference
     */
    public int getQueryRefinement() {
        return mQueryRefinement;
    }

    /**
     * Overridden to always return <code>false</code>, since we cannot be sure that
     * suggestion sources return stable IDs.
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag instanceof CharSequence) {
            mSearchView.onQueryRefine((CharSequence) tag);
        }
    }
}
