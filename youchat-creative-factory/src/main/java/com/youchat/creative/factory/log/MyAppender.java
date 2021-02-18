package com.youchat.creative.factory.log;

import com.youchat.creative.factory.exception.MyException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;

/**
 *
 * 代码说明：
 * 1.引入log4j2的插件注解，并声明相关属性的值
 * @Plugin(name = "MyAppender", category = "Core", elementType = "appender", printObject = true)
 *
 * name：插件的名称。请注意，此名称不区分大小写
 * category：用于放置插件的类别。类别名称区分大小写
 * elementType：此插件所属元素的相应类别的名称，当前扩展所属元素的类别是appender。
 * printObject：指示插件类是否实现Object.toString（）方法，消息中使用。
 * 2.如何自定义变量
 * createAppender方法，用于log4j2在扫描到插件之后根据配置文件中的配置穿件自定义的插件对象。
 *
 * @PluginAttribute：是指插件的属性，如@PluginAttribute("name") String name
 *        <MyAppender name="MyAppenderTest"></Realtimeval>会取标签内name的值
 *
 * @PluginElement：是指插件的子元素，如@PluginElement("AppenderRef") AppenderRef[] appenderRefs
 *      <MyAppender name="MyAppenderTest">
 *         <AppenderRef ref="AsyncMqLog"/>
 *         <AppenderRef ref="AsyncCONSOLE"/>
 *     </MyAppender>
 *
 *      会获取标签下AppenderRef 元素的值，如果是多个AppenderRef 子元素，将会获取都一个数组
 * 可以根据业务需要自定义元素或者属性。
 *
 */
@Plugin(name = "CatExtAppender", category = "Core", elementType = "appender", printObject = true)
public class MyAppender extends AbstractAppender {

    private Configuration configuration;
    private Property[] properties;
    private AppenderRef[] appenderRefs;

    protected MyAppender(String name, Filter filter, Layout<? extends Serializable> layout,
                         boolean ignoreExceptions, Property[] properties, AppenderRef[] appenderRefs) {
        super(name, filter, layout, ignoreExceptions);
        this.appenderRefs = appenderRefs;
        this.properties = properties;
    }


    @Override
    public void append(LogEvent event) {
        try {
            Level level = event.getLevel();
            if (level.isMoreSpecificThan(Level.ERROR)) {
                this.logError(event);
            }
        } catch (Exception ex) {
            if (!ignoreExceptions()) {
                throw new AppenderLoggingException(ex);
            }
        }
    }
    /**
     * logError
     * 自定义打印
     *
     * @param event event
     */
    private void logError(LogEvent event) {
        ThrowableProxy info = event.getThrownProxy();
        if (info != null) {
            Throwable exception = info.getThrowable();

            Object message = event.getMessage();
            if (message != null) {
                if (exception instanceof MyException) {
                    //自定义BusinessException打印
                    error(String.valueOf(message), exception);
                }
                error(String.valueOf(message), exception);
            } else {
                error("common", exception);
            }
        }
    }

    @PluginFactory
    public static MyAppender createAppender(@PluginAttribute("name") String name,
                                            @PluginElement("Filter") Filter filter,
                                            @PluginElement("Layout") Layout<? extends Serializable> layout,
                                            @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
                                            @PluginElement("property") Property[] properties,
                                            @PluginElement("AppenderRef") AppenderRef[] appenderRefs) {
        if (name == null) {
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new MyAppender(name, filter, layout, ignoreExceptions, properties, appenderRefs);
    }

}
