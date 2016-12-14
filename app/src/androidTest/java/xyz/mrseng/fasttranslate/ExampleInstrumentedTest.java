package xyz.mrseng.fasttranslate;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import xyz.mrseng.fasttranslate.dao.HistoryDao;
import xyz.mrseng.fasttranslate.domain.HistoryItemBean;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("xyz.mrseng.fasttranslate", appContext.getPackageName());
    }


    @Test
    public void testDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        HistoryDao dao = new HistoryDao(context);
        HistoryItemBean bean = new HistoryItemBean();
//        for (int i = 50; i < 100; i++) {
//            bean.marked = i % 3 == 0 ? 1 : 0;
//            bean.fromCode = "zh";
//            bean.fromWord = "你好" + i;
//            bean.toCode = "en";
//            bean.toWord = "hello" + i;
//            bean.time = System.currentTimeMillis();
//            dao.insert(bean);
//        }
//        ArrayList<HistoryItemBean> list = dao.queryMarkedList(0);
        bean = dao.queryOne(60);
        bean.marked = 1;
        dao.update(bean);
        System.out.println(bean);
    }

}