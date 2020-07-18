package view.adroll.autowidthview.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import view.adroll.autowidthview.R;
import view.adroll.autowidthview.model.DescModel;
import view.adroll.autowidthview.recyclerview.adapter.AutoWidthAdapter;

/**
 * Created by Adroll
 * on 2020/3/25
 * Description: 获焦动画展示文字view
 * * * * * * * * * * * * * * * * * * * * * *
 * Thinking is more important than coding. *
 * * * * * * * * * * * * * * * * * * * * * *
 */
public class AutoWidthView extends RelativeLayout {

    private Context context;

    private AutoWidthAdapter adapter;

    private RecyclerView rv;

    private String[] nameArray = {"企鹅", "天猫", "飞猪", "蚂蚁"};
    private String[] descArray = {"拍了拍你，并叫了声", "摸了摸", "喂了坨屎给"};

    public static final int AUTO_WIDTH_VIEW_ANIMATION_DURATION = 450;

    public AutoWidthView(Context context) {
        this(context, null);
    }

    public AutoWidthView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AutoWidthView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);

        this.context = context;

        initView();
        initListener();
        initData();
    }

    private void initView(){
        inflate(context, R.layout.view_auto_width, this);
        rv = findViewById(R.id.view_auto_width_rv);
    }

    private void initListener(){
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                outRect.right = 20;
                if (parent.findContainingViewHolder(view).getAdapterPosition() == adapter.getItemCount() - 1){
                    outRect.right = 80;
                }
            }
        });
    }

    private void initData(){
        adapter = new AutoWidthAdapter(initValidData());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        rv.requestFocus();
    }

    private List<DescModel> initValidData(){
        List<DescModel> list = new ArrayList<>();

        for (int i=0; i<10; i++){
            DescModel m = new DescModel();
            m.setName(nameArray[i % 4]);
            m.setDesc(nameArray[i % 4] + descArray[i % 3] + nameArray[i % 3]);
            m.setIsVip(i % 2 == 0);
            list.add(m);
        }

        list.get(7).setDesc("");
        list.get(8).setDesc("");
        return list;
    }
}
