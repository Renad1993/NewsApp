package com.example.android.newsapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {


    public NewsAdapter(Activity context, List<News> news) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, news);
    }

    //method to return different color for different list item
    public static int getListItemBackgroundColorFromPosition(Context context, int position) {
        switch (position) {
            case 0:
                return ContextCompat.getColor(context, R.color.material50Green);
            case 1:
                return ContextCompat.getColor(context, R.color.material100Green);
            case 2:
                return ContextCompat.getColor(context, R.color.material150Green);
            case 3:
                return ContextCompat.getColor(context, R.color.material200Green);
            case 4:
                return ContextCompat.getColor(context, R.color.material250Green);
            case 5:
                return ContextCompat.getColor(context, R.color.material300Green);
            case 6:
                return ContextCompat.getColor(context, R.color.material350Green);
            case 7:
                return ContextCompat.getColor(context, R.color.material400Green);
            case 8:
                return ContextCompat.getColor(context, R.color.material450Green);
            case 9:
                return ContextCompat.getColor(context, R.color.material500Green);
            case 10:
                return ContextCompat.getColor(context, R.color.material550Green);
            case 11:
                return ContextCompat.getColor(context, R.color.material600Green);
            case 12:
                return ContextCompat.getColor(context, R.color.material650Green);
            case 13:
                return ContextCompat.getColor(context, R.color.material700Green);
            case 14:
                return ContextCompat.getColor(context, R.color.material750Green);
            case 15:
                return ContextCompat.getColor(context, R.color.material800Green);
            case 16:
                return ContextCompat.getColor(context, R.color.material850Green);
            case 17:
                return ContextCompat.getColor(context, R.color.material900Green);

            default:
                return Color.WHITE;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        // Get the {@link News} object located at this position in the list
        News currentNews = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID artical_title
        TextView articalTitleView = (TextView) listItemView.findViewById(R.id.artical_title);

        // Get the artical title from the current News object and
        // set this text on the artical title TextView
        String articalTitle = currentNews.getmArticalTitle();
        // Display the artical title of the current news in that TextView
        articalTitleView.setText(articalTitle);

        // Find the TextView in the list_item.xml layout with the ID section_name
        TextView sectionNameView = (TextView) listItemView.findViewById(R.id.section_name);

        // Get the section name from the current News object and
        // set this text on the name TextView
        String sectionName = currentNews.getmSectionName();
        // Display the section name of the current news in that TextView
        sectionNameView.setText(sectionName);

        // Find the TextView in the list_item.xml layout with the ID author_name
        TextView authorNameView = (TextView) listItemView.findViewById(R.id.author_name);

        // Get the author name from the current News object and
        // set this text on the name TextView
        String authorName = currentNews.getmAuthorName();
        // Display the author name of the current news in that TextView
        authorNameView.setText(authorName);

        // Find the TextView in the list_item.xml layout with the ID date_published
        TextView datePublishedView = (TextView) listItemView.findViewById(R.id.date_published);

        // Get the date published from the current News object and
        // set this text on the date published TextView
        String datePublished = currentNews.getmDatePublished();

        // Display the author name of the current news in that TextView
        datePublishedView.setText(datePublished);

        //call getListItemBackgroundColorFromPosition and store the result in integer variable
        int color = getListItemBackgroundColorFromPosition(getContext(), position);
        //set the color at the background list item
        listItemView.setBackgroundColor(color);

        // Return the whole list item layout (containing 4 TextViews )
        // so that it can be shown in the ListView
        return listItemView;

    }

}
