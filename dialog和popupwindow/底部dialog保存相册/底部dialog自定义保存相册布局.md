```
  private void showBottomDialog(){
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(context);
        //2、设置布局
        View view = View.inflate(context,R.layout.dialog_custom_layout,null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
//        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                picUrl = hitTestResult.getExtra();//获取图片链接
//                            保存图片到相册
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        urlToBitMap(picUrl);
                    }
                }).start();
            }
        });

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
```