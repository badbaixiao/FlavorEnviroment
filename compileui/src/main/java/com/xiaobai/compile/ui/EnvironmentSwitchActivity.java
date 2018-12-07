package com.xiaobai.compile.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaobai.compile.Constants;
import com.xiaobai.compile.bean.EnvironmentBean;
import com.xiaobai.compile.bean.ModuleBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EnvironmentSwitchActivity extends Activity {

    private static final int TYPE_MODULE = 0;

    private static final int TYPE_ENVIRONMENT = 1;

    public static final int TYPE_SHOW_MODE_FREE = 0;
    public static final int TYPE_SHOW_MODE_ALL = 1;
    public static final int TYPE_SHOW_MODE_ONLY = 2;

    private static final String TYPE_SHOW_MODE = "showMode";

    private int showMode = TYPE_SHOW_MODE_FREE;

    private List<EnvironmentBean> environmentBeans = new ArrayList<>();
    private List<EnvironmentBean> realEnvironmentBeans = new ArrayList<>();

    private Adapter adapter;

    public static void launch(Context context) {
        launch(context, TYPE_SHOW_MODE_FREE);
    }

    public static void launch(Context context, int showMode) {
        Intent intent = new Intent(context, EnvironmentSwitchActivity.class);
        intent.putExtra(TYPE_SHOW_MODE, showMode);
        context.startActivity(intent);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.environment_switcher_activity);
        findViewById(R.id.bt_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showMode = getIntent().getIntExtra(TYPE_SHOW_MODE, TYPE_SHOW_MODE_FREE);
        try {
            Class<?> environmentSwitcherClass = Class.forName(Constants.PACKAGE_NAME + "." + Constants.ENVIRONMENT_SWITCHER_FILE_NAME);
            Method getEnvironmentConfigMethod = environmentSwitcherClass.getMethod(Constants.METHOD_NAME_GET_MODULE_LIST);
            ArrayList<ModuleBean> modules = (ArrayList<ModuleBean>) getEnvironmentConfigMethod.invoke(environmentSwitcherClass.newInstance());
            ArrayList<EnvironmentBean> environmentBeans = new ArrayList<>();

            if (showMode == TYPE_SHOW_MODE_ONLY && !modules.isEmpty()) {
                EnvironmentBean environmentModule = new EnvironmentBean("", "", "一键切换", "", modules.get(0), false);
                environmentBeans.add(environmentModule);
                environmentBeans.addAll(modules.get(0).getEnvironments());
            }
            for (ModuleBean module : modules) {
                if (showMode == TYPE_SHOW_MODE_ONLY) {
                    EnvironmentBean environmentModule = new EnvironmentBean("", "", module.getAlias(), "", module, false);
                    realEnvironmentBeans.add(environmentModule);
                    realEnvironmentBeans.addAll(module.getEnvironments());
                } else {
                    EnvironmentBean environmentModule = new EnvironmentBean("", "", module.getAlias(), "", module, false);
                    environmentBeans.add(environmentModule);
                    environmentBeans.addAll(module.getEnvironments());
                }
            }
            this.environmentBeans = environmentBeans;

            String currentModuleName = "";
            EnvironmentBean xxModuleCurrentEnvironment = null;

            for (EnvironmentBean environmentBean : this.environmentBeans) {
                if (!TextUtils.equals(environmentBean.getModule().getName(), currentModuleName) || xxModuleCurrentEnvironment == null) {
                    currentModuleName = environmentBean.getModule().getName();
                    Method getXXEnvironmentBeanMethod = environmentSwitcherClass.getMethod("get" + environmentBean.getModule().getName() + "EnvironmentBean", Context.class, boolean.class);
                    xxModuleCurrentEnvironment = (EnvironmentBean) getXXEnvironmentBeanMethod.invoke(environmentSwitcherClass.newInstance(), this, true);
                }
                environmentBean.setChecked(xxModuleCurrentEnvironment.equals(environmentBean));
            }
            ListView listView = findViewById(R.id.list_view);
            adapter = new Adapter();
            listView.setAdapter(adapter);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    class Adapter extends BaseAdapter {
        @Override
        public int getCount() {
            return environmentBeans.size();
        }

        @Override
        public EnvironmentBean getItem(int position) {
            return environmentBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final EnvironmentBean environmentBean = getItem(position);

            if (getItemViewType(position) == TYPE_MODULE) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.environment_switcher_item_module, parent, false);
                TextView tvName = convertView.findViewById(R.id.tv_name);

                String moduleName = environmentBean.getModule().getName();
                String alias = environmentBean.getAlias();
                tvName.setText(TextUtils.isEmpty(alias) ? moduleName : alias);
            } else {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.environment_switcher_item_environment, parent, false);
                TextView tvName = convertView.findViewById(R.id.tv_name);
                TextView tvUrl = convertView.findViewById(R.id.tv_url);
                ImageView ivMark = convertView.findViewById(R.id.iv_mark);

                if (showMode==TYPE_SHOW_MODE_ONLY){
                    tvUrl.setText(environmentBean.getAlias());
                    tvUrl.setTextColor(Color.BLACK);
                    ivMark.setVisibility(environmentBean.isChecked() ? View.VISIBLE : View.INVISIBLE);
                } else {
                    tvUrl.setText(environmentBean.getUrl());
                    String alias = environmentBean.getAlias();
                    tvName.setText(TextUtils.isEmpty(alias) ? environmentBean.getName() : alias);
                    ivMark.setVisibility(environmentBean.isChecked() ? View.VISIBLE : View.INVISIBLE);
                }

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (showMode == TYPE_SHOW_MODE_ALL) {
                                for (EnvironmentBean bean : environmentBeans) {
                                    if (bean.getFlavor().equals(environmentBean.getFlavor())) {
                                        Class<?> environmentSwitcher = Class.forName(Constants.PACKAGE_NAME + "." + Constants.ENVIRONMENT_SWITCHER_FILE_NAME);
                                        Method method = environmentSwitcher.getMethod("set" + bean.getModule().getName() + "Environment", Context.class, EnvironmentBean.class);
                                        method.invoke(environmentSwitcher.newInstance(), EnvironmentSwitchActivity.this, bean);
                                        bean.setChecked(true);
                                    } else {
                                        bean.setChecked(false);
                                    }
                                }
                            } else if (showMode == TYPE_SHOW_MODE_FREE) {
                                Class<?> environmentSwitcher = Class.forName(Constants.PACKAGE_NAME + "." + Constants.ENVIRONMENT_SWITCHER_FILE_NAME);
                                Method method = environmentSwitcher.getMethod("set" + environmentBean.getModule().getName() + "Environment", Context.class, EnvironmentBean.class);
                                method.invoke(environmentSwitcher.newInstance(), EnvironmentSwitchActivity.this, environmentBean);
                                for (EnvironmentBean bean : environmentBeans) {
                                    if (bean.getModule().equals(environmentBean.getModule())) {
                                        bean.setChecked(bean.equals(environmentBean));
                                    }
                                }
                            }else if (showMode == TYPE_SHOW_MODE_ONLY){
                                for (EnvironmentBean bean : realEnvironmentBeans) {
                                    if (bean.getFlavor().equals(environmentBean.getFlavor())) {
                                        Class<?> environmentSwitcher = Class.forName(Constants.PACKAGE_NAME + "." + Constants.ENVIRONMENT_SWITCHER_FILE_NAME);
                                        Method method = environmentSwitcher.getMethod("set" + bean.getModule().getName() + "Environment", Context.class, EnvironmentBean.class);
                                        method.invoke(environmentSwitcher.newInstance(), EnvironmentSwitchActivity.this, bean);
                                    }
                                }
                                for (EnvironmentBean bean : environmentBeans) {
                                    bean.setChecked(bean.equals(environmentBean));
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            if (TextUtils.isEmpty(getItem(position).getName())) {
                return TYPE_MODULE;
            } else {
                return TYPE_ENVIRONMENT;
            }
        }
    }
}
