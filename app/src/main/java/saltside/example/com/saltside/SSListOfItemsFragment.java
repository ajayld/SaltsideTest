package saltside.example.com.saltside;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The main listing fragment showing a each item's title & description.
 */
public class SSListOfItemsFragment extends ListFragment implements SSNetworkCalls.ISSNetworkCallback{
    /** The ArrayAdapter storing the array of items to show*/
    private SSArrayAdapter mArrayAdapter;

    private static final String TAG = SSListOfItemsFragment.class.getSimpleName();

    public SSListOfItemsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        return view;
    }

    /**
     * Attach to list view once the view hierarchy has been created.
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeListView();

        SSNetworkCalls ssNetworkCalls = new SSNetworkCalls();

        ssNetworkCalls.downloadListOfItems(this);
    }

    /**
     * Initialize teh ListView & set the adapter
     */
    private void initializeListView() {
        ArrayList<SSItemDetailedInfo> arrayList = new ArrayList<SSItemDetailedInfo>();

        ListView listView = getListView();

        mArrayAdapter = new SSArrayAdapter(getActivity(), R.layout.list_item, R.id.item_title, arrayList);

        listView.setAdapter(mArrayAdapter);
    }

    @Override
    public void onListOfItemsCallback(final JSONArray jsonArray) {
        final ListView listView = getListView();
        final RelativeLayout loadingWheel = (RelativeLayout) getView().findViewById(R.id.list_loading);

        loadingWheel.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

        if (null != jsonArray) {
            addToAdapter(jsonArray);
        }
        else {
            setErrorText(listView, R.string.error_network);
        }
    }

    /**
     * Set the error text message
     *
     * @param listView - ListView on which the error text has to be set.
     */
    private void setErrorText(ListView listView, int resIdErrorText) {
        TextView textView = (TextView)listView.getEmptyView();

        textView.setText(resIdErrorText);
    }

    /**
     * Add all the JSON Array items into the ArrayAdapters
     *
     * @param jsonArray - The JSON array having the list of items.
     */
    private void addToAdapter(JSONArray jsonArray) {
        if ( (null != jsonArray) && (null != mArrayAdapter ) ){
            final long lNumItems = jsonArray.length();
            SSItemDetailedInfo itemDetailedInfo = new SSItemDetailedInfo();
            JSONObject jsonObject = null;

            if(lNumItems > 0) {
                for (long i = 0; i < lNumItems; ++i) {
                    try {
                        jsonObject = (JSONObject) jsonArray.get((int) i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (null != jsonObject) {
                        try {
                            itemDetailedInfo.setTitle((String) jsonObject.get(SSConstants.RES_KEY_TITLE));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            itemDetailedInfo.setTitle("");
                        }

                        try {
                            itemDetailedInfo.setDescription((String) jsonObject.get(SSConstants.RES_KEY_DESCRIPTION));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            itemDetailedInfo.setDescription("");
                        }

                        try {
                            itemDetailedInfo.setImageURL((String) jsonObject.get(SSConstants.RES_KEY_IMAGE));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            itemDetailedInfo.setImageURL("");
                        }

                        mArrayAdapter.add(itemDetailedInfo);
                    }
                }
                mArrayAdapter.notifyDataSetChanged();
            }
            else {
                setErrorText(getListView(), R.string.error_list_empty);
            }
        }
        else {
            Log.e(TAG, "jsonArray is " + jsonArray + " mArrayAdapter " + mArrayAdapter);
            setErrorText(getListView(), R.string.error_network);
        }
    }
}
