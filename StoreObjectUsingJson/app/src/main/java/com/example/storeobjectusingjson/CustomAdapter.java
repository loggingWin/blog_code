package com.example.storeobjectusingjson;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> implements ItemTouchHelperListener {
    ArrayList<Member> members;
    Context mContext;

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        return false;
    }

    @Override
    public void onItemSwipe(int position) {

    }

    @Override
    public void onLeftClick(int position, RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public void onRightClick(int position, RecyclerView.ViewHolder viewHolder) {
        BaseActivity activity = new BaseActivity();
        activity.deleteMember(members.get(position).getKey());

        members.remove(position);
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        //        adapter의 viewHolder에 대한 inner class (setContent()와 비슷한 역할)
        //        itemView를 저장하는 custom viewHolder 생성
//        findViewById & 각종 event 작업
        TextView tvName, tvAge, tvJob;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
//            item 에 대한 클릭 이벤트 설정
            tvName = itemView.findViewById(R.id.item_name);
            tvAge = itemView.findViewById(R.id.item_age);
            tvJob = itemView.findViewById(R.id.item_job);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(mContext, EditActivity.class);
                        intent.putExtra("member", members.get(position)); // 클릭한 member 객체 전달
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    public CustomAdapter(ArrayList<Member> members) {
//        adapter constructor
        this.members = members;
    }

    public CustomAdapter(ArrayList<Member> members, Context mContext) {
//        adapter constructor for needing context part
        this.members = members;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        onCreateViewHolder: make xml as an object using LayoutInflater & create viewHolder with the object
//        layoutInflater로 xml객체화. viewHolder 생성.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
//        onBindViewHolder: put data of item list into xml widgets
//        xml의 위젯과 데이터를 묶는(연결하는, setting하는) 작업.
//        position에 해당하는 data, viewHolder의 itemView에 표시함
        holder.tvName.setText(members.get(position).getName());
        holder.tvAge.setText(String.valueOf(members.get(position).getAge()));
        holder.tvJob.setText(members.get(position).getJob());
    }

    @Override
    public int getItemCount() {
//        getItemCount: return the size of the item list
//        item list의 전체 데이터 개수 return
        return (members != null ? members.size() : 0);
    }
}
