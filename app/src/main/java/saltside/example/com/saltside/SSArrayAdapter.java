package saltside.example.com.saltside;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

/**
 * Created by Ajay.Davanam on 2/8/2016.
 */
public class SSArrayAdapter extends ArrayAdapter <SSItemDetailedInfo> {
    /**
     * Constructor
     *
     * @param context            The current context.
     * @param resource           The resource ID for a layout file containing a layout to use when
     *                           instantiating views.
     * @param textViewResourceId The id of the TextView within the layout resource to be populated
     * @param objects            The objects to represent in the ListView.
     */
    public SSArrayAdapter(Context context, int resource, int textViewResourceId, List<SSItemDetailedInfo> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    /**
     * {@inheritDoc}
     *
     * @param position
     * @param convertView
     * @param parent
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SSItemViewHolder itemViewHolder = null;
        View viewItem = convertView;

        if(null == convertView) {
            viewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);

            itemViewHolder = new SSItemViewHolder();
            itemViewHolder.mTextViewTitle = (TextView)viewItem.findViewById(R.id.item_title);
            itemViewHolder.mTextViewDescription = (TextView)viewItem.findViewById(R.id.item_description);

            viewItem.setTag(itemViewHolder);
        }
        else {
            itemViewHolder = (SSItemViewHolder) convertView.getTag();
        }

        updateView(itemViewHolder, position);

        return viewItem;
    }

    private void updateView(SSItemViewHolder ssItemViewHolder, int position) {
        if(null != ssItemViewHolder) {
            SSItemDetailedInfo itemDetailedInfo = getItem(position);

            if(null != itemDetailedInfo) {
                ssItemViewHolder.mTextViewTitle.setText(itemDetailedInfo.getTitle());
                ssItemViewHolder.mTextViewDescription.setText(itemDetailedInfo.getDescription());
            }
            else {
                ssItemViewHolder.mTextViewTitle.setText("");
                ssItemViewHolder.mTextViewDescription.setText("");
            }
        }
    }


    private class SSItemViewHolder {
        private TextView mTextViewTitle;

        private TextView mTextViewDescription;
    }
}
