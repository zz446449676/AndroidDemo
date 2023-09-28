package com.android.testdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.testdemo.View.SlideRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecycleViewDemo extends AppCompatActivity {
    public LinearLayout delete_btn;
    static SlideRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_demo);
        mRecyclerView = findViewById(R.id.recyclerView);
        ConstraintLayout foot_layout = findViewById(R.id.foot_layout);
        delete_btn = findViewById(R.id.delete_btn);
        Button btn_edit = findViewById(R.id.btn_edit);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecycleViewDemoAdapter mAdapter = new RecycleViewDemoAdapter(prepareDataList());
        mAdapter.setOnItemClickListener(new RecycleViewDemoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mAdapter.getEditMode()) {
                    mAdapter.setViewSelected(view, position);
                    if (mAdapter.getDeleteIndexMap().isEmpty()) {
                        delete_btn.setClickable(false);
                        delete_btn.setBackgroundColor(Color.parseColor("#cecece"));
                    } else {
                        delete_btn.setClickable(true);
                        delete_btn.setBackgroundColor(getColor(R.color.purple_500));
                    }
                }
            }
        });
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        btn_edit.setOnClickListener(view -> {
            boolean editMode = mAdapter.getEditMode();
            Log.d("zhang", "editMode : " + editMode);
            // 初始化，清空Model数据的选中状态，只要点击了管理按钮后都是未选中
            if (!editMode) {
                HashMap<Integer, Model> deleteIndexMap = mAdapter.getDeleteIndexMap();
                deleteIndexMap.clear();
            }
            mAdapter.setEditMode(!editMode);
            mRecyclerView.setIsAllowSlide(editMode);
            mRecyclerView.closeMenu();

            // 设置按钮状态
            foot_layout.setVisibility(editMode ? View.GONE : View.VISIBLE);
            delete_btn.setBackgroundColor(Color.parseColor("#cecece"));
            delete_btn.setClickable(false);
            btn_edit.setText(editMode ? "管理" : "返回");

            // 通知RecyclerView改变
            mAdapter.notifyDataSetChanged();
        });

        delete_btn.setOnClickListener(view ->{
            // 设置删除数据并通知改变
            mAdapter.setEditMode(false);
            mRecyclerView.setIsAllowSlide(true);

            // 数据删除操作
            for (Integer key : mAdapter.getDeleteIndexMap().keySet()) {
                Log.d("zhang", "删除 item : " + key);
                mAdapter.getDataList().remove(mAdapter.deleteIndexMap.get(key));

            }
            mAdapter.notifyDataSetChanged();
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
            mAdapter.getDeleteIndexMap().clear();

            // 管理按钮状态
            foot_layout.setVisibility(View.GONE);
            btn_edit.setText("管理");
        });
    }

    public List<Model> prepareDataList() {
        List<Model> dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            dataList.add(new Model("这是第 " + i + " 个 title", "这是第 " + i + " 个 content"));
        }
        return dataList;
    }

    static class RecycleViewDemoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final List<Model> dataList;
        private boolean isEditMode = false;

        private OnItemClickListener onItemClickListener;

        // 用一个 HashSet 存储已经选择准备删除的索引 position
        private final HashMap<Integer, Model> deleteIndexMap = new HashMap<>();

        public RecycleViewDemoAdapter(List<Model> list) {
            dataList = list;
        }

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        public List<Model> getDataList() { return dataList; }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public HashMap<Integer, Model> getDeleteIndexMap() { return deleteIndexMap; }

        public boolean getEditMode() { return isEditMode; }
        public void setEditMode(boolean editMode) { this.isEditMode = editMode; }

        public void setViewSelected(View view, int position) {
            boolean isSelected = deleteIndexMap.get(position) != null;
            view.setSelected(!isSelected);
            if (isSelected) {
                deleteIndexMap.remove(position);
            } else {
                deleteIndexMap.put(position, dataList.get(position));
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item_layout, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                Model model = dataList.get(position);
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

                itemViewHolder.title.setText(model.getTitle());
                itemViewHolder.content.setText(model.getContent());
                itemViewHolder.checkbox.setVisibility(isEditMode ? View.VISIBLE : View.GONE);
                itemViewHolder.checkbox.setSelected(deleteIndexMap.get(position) != null);

                itemViewHolder.message_delete.setOnClickListener(view ->{
                    Log.d("zhang", "message_delete onClick position ： " + position);

                    dataList.remove(position);
                    this.notifyDataSetChanged();
                    mRecyclerView.closeMenu();
                    Toast.makeText(Application.getInstance(),"删除成功！", Toast.LENGTH_SHORT).show();
                });

                itemViewHolder.root_layout.setOnClickListener(view ->{
                    Log.d("zhang", "RecycleView OnClick position : " + position);
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(itemViewHolder.checkbox, position);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        View checkbox;
        TextView title, content;
        ConstraintLayout root_layout;
        LinearLayout message_delete;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkbox);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            root_layout = itemView.findViewById(R.id.root_layout);
            message_delete = itemView.findViewById(R.id.message_delete);
        }
    }

    public static class Model {
        private final String title;
        private final String content;

        public Model(String title, String content) {
            this.title = title;
            this.content = content;
        }
        public String getTitle() { return title; }

        public String getContent() { return content; }
    }

}