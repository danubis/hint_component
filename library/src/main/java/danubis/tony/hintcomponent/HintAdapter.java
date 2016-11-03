package danubis.tony.hintcomponent;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;


class HintAdapter extends ArrayAdapter {

    private Context context;
    private int layoutResourceId;
    private ArrayList<String> collectionData = new ArrayList<>();
    private int rowHeight;


    HintAdapter(Context context, int layoutResourceId, ArrayList<String> data, int rowHeight) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.collectionData = data;
        this.rowHeight = rowHeight;

    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {
        View row = convertView;
        final ViewHolder holder;


        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            row.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, rowHeight));
            holder = new ViewHolder();
            holder.hint = (TextView) row.findViewById(R.id.hint_text);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        final String data = collectionData.get(position);

        //把itemName 链接到Imageview 然后把ImageView 传到异步task中
        holder.hint.setText(data);


        return row;
    }

    private static class ViewHolder {
        TextView hint;
    }
}
