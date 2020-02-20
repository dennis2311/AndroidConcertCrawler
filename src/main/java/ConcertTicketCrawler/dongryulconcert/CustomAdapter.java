package ConcertTicketCrawler.dongryulconcert;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<Dictionary> mList;

    public CustomAdapter(ArrayList<Dictionary> list){
        this.mList = list;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView id;

        public CustomViewHolder(View view){
            super(view);
            this.id = (TextView) view.findViewById(R.id.id_listitem);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position){
        viewholder.id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

        viewholder.id.setGravity(Gravity.CENTER);

        viewholder.id.setText((CharSequence) mList.get(position));
    }
    @Override
    public int getItemCount(){
        return (null != mList ? mList.size() : 0);
    }
}
