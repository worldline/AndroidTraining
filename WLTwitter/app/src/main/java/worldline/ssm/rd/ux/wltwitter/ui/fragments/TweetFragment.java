package worldline.ssm.rd.ux.wltwitter.ui.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.utils.Constants;

public class TweetFragment extends Fragment {


    public TweetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_wltwitter_tweet, container, false);

        // Set the name
        final String name = getArguments().getString(Constants.Tweet.EXTRA_NAME);
        ((TextView) view.findViewById(R.id.tweetNameTextView)).setText(name);

        // Set the alias
        final String alias = getArguments().getString(Constants.Tweet.EXTRA_ALIAS);
        ((TextView) view.findViewById(R.id.tweetAliasTextView)).setText("@" + alias);

        // Set the text
        final String text = getArguments().getString(Constants.Tweet.EXTRA_TEXT);
        ((TextView) view.findViewById(R.id.tweetTextTextView)).setText(text);

        return view;
    }


}
