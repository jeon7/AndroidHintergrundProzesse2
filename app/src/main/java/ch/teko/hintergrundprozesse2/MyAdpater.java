package ch.teko.hintergrundprozesse2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdpater extends RecyclerView.Adapter<MyAdpater.ViewHolder>{

    private ArrayList<Transport> transportsList = null;

    // 생성자에서 데이터 리스트 객체를 전달받음
    public MyAdpater(ArrayList<Transport> transportsList) {
        this.transportsList = transportsList;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public MyAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        MyAdpater.ViewHolder viewHolder = new MyAdpater.ViewHolder(view);

        return viewHolder;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull MyAdpater.ViewHolder viewHolder, int position) {
        viewHolder.tv_name.setText(transportsList.get(position).getName());
        viewHolder.tv_icon.setText(transportsList.get(position).getIcon());
//        viewHolder.iv_icon.setImageResource(transportsList.get(position).getDrawableId());
    }

//    getItemCount() - 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return transportsList.size();
    }

    // inner ViewHolder class
    // 아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        TextView tv_icon;
        ImageView iv_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_icon = itemView.findViewById(R.id.tv_icon);
            iv_icon = itemView.findViewById(R.id.iv_icon);
        }
    }

}
