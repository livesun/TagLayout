
## 效果

![grad](http://livesunhexo.oss-cn-shanghai.aliyuncs.com/hexo/taglayout.gif)

## 如何使用

### 第一步
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
### 第二步
```
	dependencies {
	        compile 'com.github.livesun:TagLayout:V1.0'
	}

```
 ### 第三步
```
 tagLayout = (TagLayout) findViewById(R.id.tagview);

 tagLayout.setAdapter(new MyAdapter(getDatas()));

 class MyAdapter extends TagAdapter{

        private List<TagBean> datas;

        public MyAdapter(List<TagBean> datas){

            this.datas = datas;
        }
        @Override
        public int getItemCount() {
            return datas.size();
        }

        @Override
        public View getView(int position, ViewGroup parent) {
            TextView itmeView = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.tag_item, parent, false);
            itmeView.setText(datas.get(position).getSrc());
            GradientDrawable drawable=new GradientDrawable();
            drawable.setStroke(1,Color.parseColor("#99541D"));
            drawable.setCornerRadius(5);
            drawable.setColor(datas.get(position).getBgColor());
            itmeView.setBackgroundDrawable(drawable);
            
            //设置点击事件
             itmeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,datas.get(position).getSrc(),Toast.LENGTH_SHORT).show();
                }
            });
            return itmeView;
        }
    }
```
具体使用可以看博客 或者例子。
### 博客地址
https://livesun.github.io/2017/07/25/TagLayout/


