package com.lvshandian.partylive.utils;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * 创建数据库
 * Created by zhang on 2016/10/12.
 */
public class XUtils {

    private static int DbVERDION = 1;
    private static String x_DB_NAME = "db_children";

    private static DbManager x_dbManager = null;

    /**
     * 创建数据库
     */
    public static DbManager oncreateUtils() {

        DbManager.DaoConfig x_daoConfig = new DbManager.DaoConfig().setDbName(x_DB_NAME)
                .setDbVersion(DbVERDION)
                .setTableCreateListener(new DbManager.TableCreateListener() {

                    @Override
                    public void onTableCreated(DbManager dbManager, TableEntity<?> tableEntity) {

                    }
                }).setDbUpgradeListener(new DbManager.DbUpgradeListener() {

                    @Override
                    public void onUpgrade(DbManager dbManager, int oldVersion,
                                          int newVersion) {


                    }
                });
//        // 创建xUtils3数据库

        x_dbManager = x.getDb(x_daoConfig);
        return x.getDb(x_daoConfig);
    }

    /**
     * 创建数据库表并且添加数据库对象：
     * 参数一：添加表
     */

    public static <T> void addTable(T bean) {

        if (x_dbManager == null) {

            x_dbManager = oncreateUtils();
        }
        try {
            x_dbManager.save(bean);
        } catch (DbException e) {
            e.printStackTrace();
        }


    }

    /**
     * 清空表
     * 参数一：清除表的一个对象类
     */

    public static void dropTable(Class classs) {

        if (x_dbManager == null) {

            x_dbManager = oncreateUtils();
        }
        try {
            x_dbManager.dropTable(classs);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据某一个字段删除表中的一条记录
     * 参数一：传入表名的类；
     * 参数二：传入要删除的字段
     * 参数三：传入要删除字段的名称
     */

    public static void deleteEntity(Class classs, String key, String value) {

        if (x_dbManager == null) {

            x_dbManager = oncreateUtils();
        }

        try {
            x_dbManager.delete(classs, WhereBuilder.b(key, "=", value));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据某一个字段修改表中的摸一个字段；
     * 参数一：传入表名的类；
     * 参数二：传入根据某个字段去修改表中的值；
     * 参数三：传入要修改信息的字段的值；
     * 参数四：传入要修改字段的名称
     * 参数五：传入修改后字段的值；
     * 注：最好以Id传进去是唯一的
     */

    public static void update(Class classs, String oldKey, String oldValue, String newKey, String newValue) {

        if (x_dbManager == null) {

            x_dbManager = oncreateUtils();
        }
        try {
            x_dbManager.update(classs,
                    WhereBuilder.b(oldKey, "=", oldValue), new KeyValue(
                            newKey, newValue));
        } catch (DbException e) {


        }

    }

    /**
     * 查询数据
     */
    public static <T> List findAll(Class<T> var1) {
        List<T> list = null;
        if (x_dbManager == null) {

            x_dbManager = oncreateUtils();
        }

        try {
            list = x_dbManager.findAll(var1);
            return list;
        } catch (DbException e) {
            e.printStackTrace();
        }

        return list;
    }


}
