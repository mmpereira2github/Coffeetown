package com.syncinfo.android.mvc.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syncinfo.mvc.uc.ListUC;
import com.syncinfo.util.Assert;

/**
 * Created by mmartins on 2018-02-10.
 */

public class ListUCAdapterAsBaseAdapter<ListItemType> extends
        ListUCAdapterAsAndroidAdapter<ListItemType> {

    private Context mContext;
    private LayoutInflater mInflater;
    private int mResource;
    private int mFieldId = 0;
    private ToCharSequenceConverter<ListItemType> toCharSequenceConverter = null;
    private ViewFromResourceHandler<ListItemType> viewFromResourceHandler = null;

    public ListUCAdapterAsBaseAdapter(ListUC.Adapter<ListItemType> listViewAdapter, Context context, int
            layoutResourceId, int itemResourceId) {
        this(listViewAdapter, context, layoutResourceId, itemResourceId, null);
    }

    public ListUCAdapterAsBaseAdapter(ListUC.Adapter<ListItemType> listViewAdapter, Context context,
                                      int layoutResourceId, int itemResourceId, ToCharSequenceConverter<ListItemType> converter) {
        super(listViewAdapter);
        if (null != converter) {
            this.toCharSequenceConverter = converter;
        }

        init(context, layoutResourceId, itemResourceId);
    }

    private void init(Context context, int layoutResourceId, int itemResourceId) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResource = layoutResourceId;
        mFieldId = itemResourceId;
    }

    public void setViewFromResourceHandler(ViewFromResourceHandler<ListItemType> viewFromResourceHandler) {
        this.viewFromResourceHandler = viewFromResourceHandler;
    }

    public void setToCharSequenceConverter(ToCharSequenceConverter<ListItemType> toCharSequenceConverter) {
        this.toCharSequenceConverter = toCharSequenceConverter;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return createViewFromResource(i, view, viewGroup, mResource);
    }

    protected View createViewFromResource(int position, View convertView, ViewGroup parent,
                                          int resource) {
        View view;

        if (convertView == null) {
            view = mInflater.inflate(0 == resource ? this.mResource : resource, parent, false);
        } else {
            view = convertView;
        }

        TextView text = null;
        if (mFieldId != 0) {
            View fieldView = view.findViewById(mFieldId);
            if (fieldView instanceof  TextView) {
                text = (TextView) fieldView;
            }
        }

        ListItemType item = getItem(position);
        if (null == this.viewFromResourceHandler) {
            if (null == text) {
                Log.e("ArrayAdapter", "You must supply a field ID for a TextView");
                throw new IllegalStateException("AndroidAdapter requires the field ID to be a TextView");
            }

            if (null == toCharSequenceConverter) {
                if (item instanceof CharSequence) {
                    text.setText((CharSequence) item);
                } else {
                    text.setText(null == item ? "" : item.toString());
                }
            } else {
                text.setText(this.toCharSequenceConverter.toCharSequence(getItem(position)));
            }
        } else {
            view = this.viewFromResourceHandler.handle(position, view, text, convertView != null, item);
        }

        return view;
    }

    /**
     * Functional interface to allow the handle of the view that represents the list item.
     */
    public interface ViewFromResourceHandler<ListItemType> {
        View handle(int position, View view, TextView textView, boolean isViewReused, ListItemType currentItem);
    }

    /**
     * Functional interface to convert the ListItemType instance into a string when the list item is a TextView
     *
     * @param <ListItemType>
     */
    public interface ToCharSequenceConverter<ListItemType> {
        CharSequence toCharSequence(ListItemType item);
    }
}
