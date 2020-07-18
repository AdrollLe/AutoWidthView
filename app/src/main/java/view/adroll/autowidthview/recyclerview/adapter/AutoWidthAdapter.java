package view.adroll.autowidthview.recyclerview.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import view.adroll.autowidthview.R;
import view.adroll.autowidthview.model.DescModel;
import view.adroll.autowidthview.recyclerview.viewholder.AutoWidthViewHolder;

/**
 * Created by Adroll
 * on 2020/3/25
 * Description:
 * * * * * * * * * * * * * * * * * * * * * *
 * Thinking is more important than coding. *
 * * * * * * * * * * * * * * * * * * * * * *
 */
public class AutoWidthAdapter extends RecyclerView.Adapter<AutoWidthViewHolder> {

    private List<DescModel> descModelList;

    public AutoWidthAdapter(List<DescModel> descModelList){
        this.descModelList = descModelList;
    }

    @NonNull
    @Override
    public AutoWidthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AutoWidthViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auto_width, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AutoWidthViewHolder holder, int position) {
        DescModel model = descModelList.get(position);
        holder.tv.setText(model.getName());
        holder.tvDesc.setText(model.getDesc());

        holder.setIsVip(model.getIsVip());
        holder.setDescWidth(calculate(model.getDesc(), 8));
    }

    /**
     * 计算展示文案对应的长度
     * @param str   展示文案
     * @param limit    文案展示最大字数
     * @return  展示文案对应的长度
     */
    private int calculate(String str, int limit){
        if (TextUtils.isEmpty(str)){
            return 0;
        }

        char[] chars = str.toCharArray();
        int distance = 0;
        int point = 0;

        // 限制显示字数
        while (point < limit && point < chars.length){
            distance = setDistance(chars[point++], distance);
        }

        return distance;
    }

    /**
     * 将大小写字母、特殊字符增加不同的长度
     * @param c：传入的字符
     * @param distance  动画展示的textview长度
     * @return  已经添加新长度的textview长度
     */
    private int setDistance(char c, int distance){
        int num = (int)c;
        if (num >= 32 && num <= 126){
            distance += 13;
        }else {
            distance += 27;
        }

        return distance;
    }

    @Override
    public int getItemCount() {
        return descModelList.size();
    }
}
