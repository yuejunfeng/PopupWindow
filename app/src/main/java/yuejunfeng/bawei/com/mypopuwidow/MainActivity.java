package yuejunfeng.bawei.com.mypopuwidow;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {
    /**
     * PopuWindow下拉列表，属于组合式控件
     * 1.添加Butternife的依赖，取消掉ActionBar,使用ToolBar代替
     * 2.完成整体的布局，初始化控件，设置点击事件
     * 3.初始化PopuWindow所要显示数据
     * 4.初始化PopuWindow控件的设置
     * 5.PopuWindow与listView相关联
     * 6.三个PopuWindow所依附的linearLayout，更加点击事件，做对应逻辑处理（改变TextView的颜色，显示效果）
     */
    @Bind(R.id.supplier_list_product_tv)
    TextView supplierListProductTv;
    @Bind(R.id.supplier_list_product)
    LinearLayout supplierListProduct;
    @Bind(R.id.supplier_list_sort_tv)
    TextView supplierListSortTv;
    @Bind(R.id.supplier_list_sort)
    LinearLayout supplierListSort;
    @Bind(R.id.supplier_list_activity_tv)
    TextView supplierListActivityTv;
    @Bind(R.id.supplier_list_activity)
    LinearLayout supplierListActivity;
    @Bind(R.id.supplier_list_lv)
    ListView supplierListLv;
    private ArrayList<Map<String, String>> menuData,menuData2,menuData3;
    private LinearLayout button;
    private ListView popupListview;
    private SimpleAdapter simpleAdapter,simpleAdapter2,simpleAdapter3;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //初始化PopuWindow所要显示的数据
        initData();
        //初始化PopuWindow控件
        initPopuWindow();
    }



    //初始化数据，PopuWindow所需，一共有三个，所以我要封装好三个数据
    private void initData() {
        //创建一个存放PopuWindow加载数据的大盒子，Map集合
        menuData = new ArrayList<>();
        //存放String的字符串数组
        String[] menuStr1 = new String[]{"全部", "粮油", "衣服", "图书", "电子产品",
                "酒水饮料", "水果"};
        //创建一个小盒子，放编号和值
        Map<String,String> map1;
        for (int i = 0; i < menuStr1.length; i++) {
            map1=new HashMap<String,String >();
            map1.put("name",menuStr1[i]);
            menuData.add(map1);
        }

        //创建一个存放PopuWindow加载数据的大盒子，Map集合
        menuData2 = new ArrayList<>();
        //存放String的字符串数组
        String[] menuStr2 = new String[]{"综合排序", "配送费最低"};
        //创建一个小盒子，放编号和值
        Map<String,String> map2;
        for (int i = 0; i < menuStr2.length; i++) {
            map2=new HashMap<String,String >();
            map2.put("name",menuStr2[i]);
            menuData2.add(map2);
        }

        //创建一个存放PopuWindow加载数据的大盒子，Map集合
        menuData3 = new ArrayList<>();
        //存放String的字符串数组
        String[] menuStr3 = new String[]{"优惠活动", "特价活动", "免配送费",
                "可在线支付"};
        //创建一个小盒子，放编号和值
        Map<String,String> map3;
        for (int i = 0; i < menuStr3.length; i++) {
            map3=new HashMap<String,String >();
            map3.put("name",menuStr3[i]);
            menuData3.add(map3);
        }
    }
    private int Count = 0;
    private void initPopuWindow() {
    //把包裹listView布局的XML，文件转换为View对象
        View popup = LayoutInflater.from(this).inflate(R.layout.popwin_supplier_list, null);
      //创建popupWindow对象，参数1，要显示的布局，2 3是宽高
        popupWindow = new PopupWindow(popup, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    //数组popupWindow外部可以点击
        popupWindow.setOutsideTouchable(true);
        //数组popupWindow里面的listview有焦点
        popupWindow.setFocusable(true);
        //如果想让popupWindow有动画效果，就必须有这段代码
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        //设置结束时监听事件
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //设置TextView的颜色,把所有LinearLayout的文本颜色该为灰色
                supplierListProductTv.setTextColor(Color.parseColor("#5a5959"));
                supplierListSortTv.setTextColor(Color.parseColor("#5a5959"));
                supplierListActivityTv.setTextColor(Color.parseColor("#5a5959"));

            }
        });
        //设置点击popupWindow以外的地方，使popupWindow消失
        button = (LinearLayout) popup.findViewById(R.id.popwin_supplier_list_bottom);
        //当点击到灰色区域时，popupWindow会消失
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        //获取listview对象
        popupListview = (ListView) popup.findViewById(R.id.popwin_supplier_list_lv);
        //创建SimpleAdapter,一个listview安卓原生封装的适配器
        simpleAdapter = new SimpleAdapter(this, menuData, R.layout.item_listview_popup, new String[]{"name"}, new int[]{R.id.listview_popwind_tv});

        simpleAdapter2 = new SimpleAdapter(this, menuData2, R.layout.item_listview_popup, new String[]{"name"}, new int[]{R.id.listview_popwind_tv});
        simpleAdapter3 = new SimpleAdapter(this, menuData3, R.layout.item_listview_popup, new String[]{"name"}, new int[]{R.id.listview_popwind_tv});

        popupListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                popupWindow.dismiss();
                switch (Count){
                    case 0:
                        String Product = menuData.get(i).get("name");
                        supplierListProductTv.setText(Product);
                        break;
                    case 1:
                        String Sort = menuData2.get(i).get("name");
                        supplierListSortTv.setText(Sort);
                        break;
                    case 2:
                        String activity = menuData3.get(i).get("name");
                        supplierListActivityTv.setText(activity);
                        break;
                }

            }
        });



    }
    @OnClick({R.id.supplier_list_product, R.id.supplier_list_sort, R.id.supplier_list_activity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //第一个popupWindow所执行的点击后的逻辑
            case R.id.supplier_list_product:
                //设置其TextView点击是为绿色
                supplierListProductTv.setTextColor(Color.GREEN);

                //设置popupWindow里的listview适配器
                    popupListview.setAdapter(simpleAdapter);

                //让popupWindow显示出来
                    popupWindow.showAsDropDown(supplierListProduct,0,2);
                    Count = 0;

                break;
            case R.id.supplier_list_sort:
                //设置其TextView点击是为绿色
                supplierListSortTv.setTextColor(Color.GREEN);
                //设置popupWindow里的listview适配器
                popupListview.setAdapter(simpleAdapter2);

                //让popupWindow显示出来
                popupWindow.showAsDropDown(supplierListSort,0,2);
                Count = 1;
                break;
            case R.id.supplier_list_activity:
                //设置其TextView点击是为绿色
                supplierListActivityTv.setTextColor(Color.GREEN);
                //设置popupWindow里的listview适配器
                popupListview.setAdapter(simpleAdapter3);

                //让popupWindow显示出来
                popupWindow.showAsDropDown(supplierListActivity,0,2);
                Count = 2;
                break;
        }
    }
}
