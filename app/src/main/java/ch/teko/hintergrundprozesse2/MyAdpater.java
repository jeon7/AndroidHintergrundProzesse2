package ch.teko.hintergrundprozesse2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class MyAdpater extends RecyclerView.Adapter<MyAdpater.MyViewHolder>{

    private static final String LOG_TAG = "MyAdpater";
    private ArrayList<Transport> transportsList = null;

    // custom interface for Click event in recyclerView (see MyViewHolder constructor and ResultActivity)
    // in order to use activity method.
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private OnItemClickListener myListener = null;


    public void setOnItemClickListner(OnItemClickListener listner) {
        this.myListener = listner;
    }

    // inner ViewHolder class
    // 아이템 뷰를 저장하는 뷰홀더 클래스
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        String LOG_TAG = "MyViewHolder in MyAdpater";

        TextView tv_name;
        ImageView iv_icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(LOG_TAG, "MyViewHolder() created");

            tv_name = itemView.findViewById(R.id.tv_name);
            iv_icon = itemView.findViewById(R.id.iv_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        // ??
                        if(myListener != null) {
                            myListener.onItemClick(v,pos);

                        }
                    }
                }
            });
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음
    public MyAdpater(ArrayList<Transport> transportsList) {
        Log.d(LOG_TAG, "MyAdpater() created");
        this.transportsList = transportsList;
    }

    // Create new views (invoked by the layout manager)
    // 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public MyAdpater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(LOG_TAG, "onCreateViewHolder() called");

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Log.d(LOG_TAG, "onBindViewHolder() called");

        myViewHolder.iv_icon.setImageResource(transportsList.get(position).getDrawableId());
        myViewHolder.tv_name.setText(transportsList.get(position).getName());
    }

//    getItemCount() - 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        Log.d(LOG_TAG, "getItemCount() called");
        return transportsList.size();
    }
}
